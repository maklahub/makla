package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.paypal.api.payments.*;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import models.MaklaCreditCard;
import models.SystemUser;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveCreditCard extends Controller {

    public static Result index() throws PayPalRESTException {
        String cardFirstName;
        String cardLastName ;
        String cardType ;
        String cardNumber ;
        int cardMonth ;
        int cardYear ;
        JsonNode json = request().body().asJson();
        SystemUser u = SystemUser.findUserById(session("currentUserId"));

        System.out.println("\n Json: >>>>>>>>>>>>>>>> \n" + json);

        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            cardFirstName = json.findPath("cardFirstName").textValue();
            cardLastName = json.findPath("cardLastName").textValue();
            cardType = json.findPath("cardType").textValue();
            cardNumber = json.findPath("cardNumber").textValue();
            cardMonth = json.findPath("cardMonth").asInt();
            cardYear = json.findPath("cardYear").asInt();

        }

        // Server Set up  (Live/ Sandbox environment)
        Map<String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "sandbox");

        // Get access Token
        //OAuthTokenCredential response = new OAuthTokenCredential("AUFjWxDEBZ1HqK-eV6_f5sGFT7TbmuFTg3xHucqDiqTVMt-JBPXJIRxWf8G-", "EFjBgBBQbl2f2gZZSYBc_eQqJo0syHNZMan_tMdZsunpp4qDsnCB264g_tXM", sdkConfig);
        OAuthTokenCredential response = new OAuthTokenCredential("AZIx_RAsEGHeW-Zvix0bSOBxwaQFmPFaYn5IPLoxszrIV_msZOf8wZ6keZcC", "ECQFAhBCM90jLbk7ioGRPz7XoF3W7jju9XgpISreoeWQYXS5pvbc30z-kxW1", sdkConfig);

        System.out.println( "Access token: " + Json.toJson( response ));
        String accessToken = response.getAccessToken();

        // Set access token for Api Requests ( API Context )
        APIContext apiContext = new APIContext( accessToken );
        apiContext.setConfigurationMap(sdkConfig);

        // Create/ Save credit Card
        CreditCard creditCard = new CreditCard();
        creditCard.setType( cardType );
        creditCard.setNumber( cardNumber );
       // creditCard.setNumber("4446283280247004");
        creditCard.setExpireMonth( cardMonth );
        creditCard.setExpireYear( cardYear );
        creditCard.setFirstName( cardFirstName );
        creditCard.setLastName( cardLastName );
        CreditCard createdCreditCard = creditCard.create(apiContext);
        String cardId = createdCreditCard.getId();

        MaklaCreditCard card = new MaklaCreditCard( u, cardId, createdCreditCard.getNumber() );
        card.save();


        /*
        // Make payment with the saved credit card
        CreditCardToken creditCardToken = new CreditCardToken();
        creditCardToken.setCreditCardId("CARD-6F541200MD035241TKO4FL3Y");

        FundingInstrument fundingInstrument = new FundingInstrument();
        fundingInstrument.setCreditCardToken(creditCardToken);

        List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
        fundingInstrumentList.add(fundingInstrument);

        Payer payer = new Payer();
        payer.setFundingInstruments(fundingInstrumentList);
        payer.setPaymentMethod("credit_card");

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("3");

        Transaction transaction = new Transaction();
        transaction.setDescription("creating a payment with saved credit card");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        Payment createdPayment = payment.create(apiContext);
        return ok( "Access Token " + accessToken + "\n card Id: " + cardId + "\n" +  " card: " + createdCreditCard + "\n payment: " + createdPayment  );
        */
        return ok( createdCreditCard.toJSON() );


    }

    public static String saveCard( String firstName, String lastName, String cardNumber, int exMonth, int expYear ) throws PayPalRESTException {
        // Server Set up  (Live/ Sandbox environment)
        Map <String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "sandbox");

        // Get access Token
        OAuthTokenCredential response = new OAuthTokenCredential("AUFjWxDEBZ1HqK-eV6_f5sGFT7TbmuFTg3xHucqDiqTVMt-JBPXJIRxWf8G-", "EFjBgBBQbl2f2gZZSYBc_eQqJo0syHNZMan_tMdZsunpp4qDsnCB264g_tXM", sdkConfig);
        System.out.println( "Access token: " + Json.toJson(response));
        String accessToken = response.getAccessToken();

        // Set access token for Api Requests ( API Context )
        APIContext apiContext = new APIContext( accessToken );
        apiContext.setConfigurationMap(sdkConfig);

        // Create/ Save credit Card
        // Create/ Save credit Card
        CreditCard creditCard = new CreditCard();
        creditCard.setType("visa");
        creditCard.setNumber( cardNumber );
        creditCard.setExpireMonth( exMonth );
        creditCard.setExpireYear( expYear );
        creditCard.setFirstName("James");
        creditCard.setLastName("Bond");
        CreditCard createdCreditCard = creditCard.create(apiContext);
        String cardId = createdCreditCard.getId();


       return createdCreditCard.toJSON();
    }

}


