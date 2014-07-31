package controllers;

import com.paypal.api.payments.*;
import com.paypal.core.rest.APIContext;
import com.paypal.core.rest.OAuthTokenCredential;
import com.paypal.core.rest.PayPalRESTException;
import models.*;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;



import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
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


    public static Result pay() throws PayPalRESTException, MalformedURLException, EmailException {
        String orderId ="";
        // Server Set up  (Live/ Sandbox environment)
        Map<String, String> sdkConfig = new HashMap<String, String>();
        sdkConfig.put("mode", "sandbox");

        // Get access Token
        OAuthTokenCredential response = new OAuthTokenCredential("AUFjWxDEBZ1HqK-eV6_f5sGFT7TbmuFTg3xHucqDiqTVMt-JBPXJIRxWf8G-", "EFjBgBBQbl2f2gZZSYBc_eQqJo0syHNZMan_tMdZsunpp4qDsnCB264g_tXM", sdkConfig);
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
                    amount.setTotal( ( new DecimalFormat("#0.00").format( order.getTotalAmount() ) ));

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
                 // Send EMail Confirmation to requestor
                SystemAccount systemAccount = SystemAccount.findSystemAccountBySystemUserId( u.getId() );
                String messagetoRequestor = "MaklaHub: Than you for your Order # " + order.getReference();
                sendHtmlasEmail( messagetoRequestor, u, systemAccount.getAccountEmail(), order);
                Cart cart = Cart.findCartBySystemUser( u.getId() );
                List<CartItem> cartItems = CartItem.findCartItemsByCart(  cart.getId() );
                for( CartItem cartItem:  cartItems){
                    cartItem.setCartItemPhoto( null );
                    cartItem.setCart( null );
                    cartItem.delete();

                }
                cart.save();
                order.setStatus( Order.OrderStatus.paid );
                order.update();
                String orderAsJson =  Json.toJson( order ).toString();
                      //  return ok("\n Approved Order - payment: " + createdPayment  );
                return ok( views.html.checkout.orderPaid.render(  orderAsJson  ) );
            }
            else {
                return ok("\n payment not approved : " + createdPayment  );
            }




          }
        else {
            return redirect("/");
        }
    }


    public static void sendHtmlasEmail( String message, SystemUser u, String accountEmail,  Order order ) throws EmailException, MalformedURLException {
        // Create the email message
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("artistahub@gmail.com", "acrobat8"));
        email.setSSLOnConnect(true);
        email.setFrom("artistahub@gmail.com");
        email.setSubject( message );
        String baseUrl = request().host();


        // embed the image and get the content id
       // URL url = new URL( photoUrl );
        //  String cid = email.embed(url, u.getFullName());

        // set the html message
        //email.setHtmlMsg("<html><h1>Welcome to ArtistaOne</h1>   <img src=\"cid:"+cid+"\">  <hr><hr></html>");
        email.setHtmlMsg(" Thank you for your Order : " + order.getReference());

        // set the alternative message
        email.setTextMsg("Your email client does not support HTML messages");
        //  email.addTo( "berberacrobat@gmail.com" );
        email.addTo( accountEmail );
        // send the email
        email.send();
    }


}
