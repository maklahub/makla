package controllers;

import com.paypal.api.payments.*;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import models.Menu;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveCreditCard extends Controller {

    public static Result index() throws PayPalRESTException {

        // Server Set up  (Live/ Sandbox environment)
        Map<String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "sandbox");

        // Get access Token
        OAuthTokenCredential response = new OAuthTokenCredential("AQkquBDf1zctJOWGKWUEtKXm6qVhueUEMvXO_-MCI4DQQ4-LWvkDLIN2fGsd", "EL1tVxAjhT7cJimnz5-Nsx9k2reTKSVfErNQF-CmrwJgxRtylkGTKlU4RvrX", sdkConfig);
        System.out.println( "Access token: " + Json.toJson( response ));
        String accessToken = response.getAccessToken();

        // Set access token for Api Requests ( API Context )
        APIContext apiContext = new APIContext( accessToken );
        apiContext.setConfigurationMap(sdkConfig);

        // Create/ Save credit Card
        CreditCard creditCard = new CreditCard();
        creditCard.setType("visa");
        creditCard.setNumber("4446283280247004");
        creditCard.setExpireMonth(11);
        creditCard.setExpireYear(2018);
        creditCard.setFirstName("James");
        creditCard.setLastName("Bond");
        CreditCard createdCreditCard = creditCard.create(apiContext);
        String cardId = createdCreditCard.getId();

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


    }

}


