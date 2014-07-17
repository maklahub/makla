package controllers;

import com.paypal.api.payments.*;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import models.*;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.data.Form.form;


public class Checkout extends Controller {

    public static Result index(){
        SystemUser u = SystemUser.findUserById(session("currentUserId"));
        DynamicForm requestData = form().bindFromRequest();
        String cartId = requestData.get("cartId");
        Cart cart = Cart.findCartById( cartId );
        String cartAsJson = Json.toJson( cart ).toString();
        Order order = new Order( u );
        order.createOrderItems( cart );

        List<MaklaCreditCard> creditCards = new ArrayList<MaklaCreditCard>();
        creditCards = MaklaCreditCard.findCreditCardsBySystemUser( u.getId() );
        String creditCardsAsJson = Json.toJson( creditCards ).toString();

       // List<OrderItem> orderItems = OrderItem.findOrderItemsByOrder( order.getId() );
       // String orderItemsAsJason = Json.toJson( orderItems ).toString();
        // return ok( "Do the checkout " + cartAsJson) ;
        String orderAsJson = Json.toJson( order ).toString();
        return ok( views.html.checkout.checkout.render( creditCardsAsJson, cartId, orderAsJson  ) ) ;

    }

    public static Result getOrder( String orderId ){
        SystemUser u = SystemUser.findUserById(session("currentUserId"));
        Order order = Order.findOrderById( orderId );
        String orderAsJson = Json.toJson( order ).toString();
        if ( order != null ){
           ok(  orderAsJson );
        }
        else {

        }
        return ok( " No order found "  ) ;

    }


    public static Result pay() throws PayPalRESTException {
        String orderId ="";
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
        if ( session("sessionUser") != null){
            SystemUser u = SystemUser.findUserById(session("currentUserId"));
            DynamicForm requestData = form().bindFromRequest();
            orderId = requestData.get("orderId");
            Order order = Order.findOrderById( orderId );



                    // Make payment with the saved credit card
                    CreditCardToken creditCardToken = new CreditCardToken();
                    String creditCardId = u.getMainCreditCard().getPayPalCreditCardId();
                    creditCardToken.setCreditCardId( creditCardId );
                   // creditCardToken.setCreditCardId("CARD-6F541200MD035241TKO4FL3Y");

                    FundingInstrument fundingInstrument = new FundingInstrument();
                    fundingInstrument.setCreditCardToken(creditCardToken);

                    List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
                    fundingInstrumentList.add(fundingInstrument);

                    Payer payer = new Payer();
                    payer.setFundingInstruments(fundingInstrumentList);
                    payer.setPaymentMethod("credit_card");

                    Amount amount = new Amount();
                    amount.setCurrency("USD");
                    amount.setTotal( Double.toString( order.getTotalAmount() ));

                    Transaction transaction = new Transaction();
                    transaction.setDescription("New Order: " + orderId + " creating a payment with saved credit card ");
                    transaction.setAmount(amount);

                    List<Transaction> transactions = new ArrayList<Transaction>();
                    transactions.add(transaction);

                    Payment payment = new Payment();
                    payment.setIntent("sale");
                    payment.setPayer(payer);
                    payment.setTransactions(transactions);

                    Payment createdPayment = payment.create(apiContext);
                    String paymentStatus = createdPayment.getState();

            if ( paymentStatus.equalsIgnoreCase( "approved" )){
                        System.out.println("\n\n\n order: ---->" + order );

                        order.setStatus( Order.OrderStatus.paid );
                        order.update();
                    }
                    return ok("\n payment: " + createdPayment  );



                    }
                    else {
                        return redirect("/");
                    }
            }

}
