//var url = "http://localhost:9000";
$.ajaxSetup({ cache: false });
var url = '';

var o = {name:"hassan", age:33};
function apiCall(uri, o, successCallback, errorCallBack) {
    // alert(" Go AJax ");

    $.ajax({
        type:'POST',
        // dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(o),
        url:url + uri,
        success:function (data, textStatus, jqXHR) {
            // var obj = jQuery.parseJSON(jqXHR.responseText);
            successCallback( data );
          //  console.log(JSON.stringify(data));
            console.log(textStatus.toString());
        },
        error:function (data, textStatus, jqXHR) {
            errorCallBack( textStatus, data );
           // console.log(data);
           // console.log(textStatus);
           // console.log(jqXHR);
        }
    });

}

function apiCallGet(uri, o, callback) {
    // alert(" Go AJax ");

    $.ajax({
        type:'GET',
        // dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(o),
        url:url + uri,
        success:function (data, textStatus, jqXHR) {
            // var obj = jQuery.parseJSON(jqXHR.responseText);
            callback($.parseJSON(data) );
            console.log(JSON.stringify(data));
            //console.log(textStatus.toString());
        },
        error:function (data, textStatus, jqXHR) {
            console.log(textStatus);
        }
    });

}

function ajaxHtml1(uri, containerToFillIn, o) {
    //alert(" Go AJax ");
    $.ajax({
        url:url + uri,
        type:'POST',
        data:o,
        success:function (data, textStatus, jqXHR) {
            $(containerToFillIn).html( data  );
            console.log( JSON.stringify( data ) );
            // $(containerToFillIn).text( JSON.stringify( data ) );
            // $("#addVideoStep").removeClass("step-active").addClass("step-deactive");
            // $("#addPhotoStep").removeClass("step-deactive").addClass("step-active");
        },
        error:function (data, textStatus, jqXHR) {
            console.log(textStatus);
            alert(textStatus);
        }
    });

}

function ajaxReturnJson(uri, callback) {
    // alert(" Go AJax ");
    $.ajax({
        url:url + uri,
        type:'GET',
        success:function (data, textStatus, jqXHR) {
            console.log( JSON.stringify( data ) );
            //  alert( JSON.stringify( data ) );
            callback( data );
        },
        error:function (data, textStatus, jqXHR) {
            console.log(textStatus);
            alert(textStatus);
        }
    });

}


function ajaxAppendHtml(uri, containerToFillIn, o , fObject) {
    //alert(" Go AJax ");
    $.ajax({
        url:url + uri,
        type:'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(o),
        success:function (data, textStatus, jqXHR) {
            containerToFillIn.append( $(new  fObject( data ).render()).fadeIn(500)   );

        },
        error:function (data, textStatus, jqXHR) {
            console.log(textStatus);
            alert(textStatus);
        }
    });

}
function ajaxHtml(uri, containerToFillIn, o , fObject) {
    //alert(" Go AJax ");
    $.ajax({
        url:url + uri,
        type:'POST',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(o),
        success:function (data, textStatus, jqXHR) {
            containerToFillIn.html( $(new  fObject( data ).render()).fadeIn(50)   );

        },
        error:function (data, textStatus, jqXHR) {
            console.log(textStatus);
            alert(textStatus);
        }
    });

}

function ajaxAppendHtml2(uri, containerToFillIn, o) {
    //alert(" Go AJax ");
    $.ajax({
        url:url + uri,
        type:'POST',
        // dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(o),
        success:function (data, textStatus, jqXHR) {
            //alert( o );
            //alert( JSON.stringify(data) );
            // containerToFillIn.append( $(new  fObject( data ).render()).fadeIn(500)   );
            containerToFillIn.html( $(new Cart( data ).render()).fadeIn()  );

        },
        error:function (data, textStatus, jqXHR) {
            alert( JSON.stringify(data) );
            alert(textStatus);

        }
    });

}

function updateToolBarCart( itemsSize ,total){
       $("#cart-items-size").html( itemsSize );
       $("#cart-total").html( total );
}

function  Cart ( cart ){
    //alert( "cart.totalAmount: " + cart.totalAmount);
    if ( cart.totalAmount > 0 ){
        //alert("no empty");
        $("#tool-bart-checkout-btn").fadeIn();
        $("#show-cart-btn").text("View cart");
        $("#show-cart-btn").fadeIn();
        var name = cart.name;
        var cartItems = cart.cartItems || null;
        var cartOwner = cart.requestor != null?  cart.requestor.fullName : "";
        this.cartItems = cartItems;
        updateToolBarCart( cartItems.length, cart.totalAmountWithTax);
        this.cartWrapperOpener = '<div  id="cart-container">';
        this.cartHeader = ' <div class="row-fluid"><div class="span12"><h1 class="cart-title">My Cart</h1></div><span id="close-cart-btn" class="btn btn-mini"> </span></div>';
        this.cartItems = '<div class="row-fluid" id="cartItems"> '+ returnCartItems( cartItems ) + '</div>';
        this.ta = this.calculate( cartItems );
        console.log(" ta: " + this.ta );
        // this.footer = "<div class='cart-total-amount'> <span class='badge badge-success'>" +  " " + this.ta.toString() ;
        this.footer = '<div class="cart-total-amount"> <p style="font-weight: 600 !important;"> Tax: $'+ cart.totalTaxAmount +'</p><span class="cart-ta badge badge-success">$' + cart.totalAmountWithTax +'</span></div>';
        this.checkOutContainer =  cartItems != null ?'<div class="check-out"><form style="margin: 0" method="post" action="/checkout"><input type="hidden" name="cartId" value="'+cart.id +'"><input type="submit" class="checkout-btn" value="Checkout" id="'+ cart.id +'"></form></div>' : '';
        this.cartWrapperCloser = "</div>";
        // this.screenHtml = this.wrapperOpener + this.topBar + this.secondaryBar + this.body + this.footer; + this.wrapperCloser;
        this.screenHtml = this.cartWrapperOpener + this.cartHeader + this.cartItems +  this.footer +  this.cartWrapperCloser + this.checkOutContainer;

    }
    else{
        updateToolBarCart(0, "0.00");
        $("#cart").hide();
        $("#shadow-container").hide();
        $("#tool-bart-checkout-btn").hide();
        $("#show-cart-btn").text("View cart");
        $("#show-cart-btn").hide();
    }

}
Cart.prototype.render = function(){ return this.screenHtml };
Cart.prototype.calculate = function( cartItems  ){
    var ta = 0;
    if ( cartItems ){

        $.each( cartItems , function( i, e){
            ta = ta +  e.amount ;
        });
    }
    return parseFloat(Number( ta ).toPrecision(10));
};

function returnCartItems( cartItems ){

    var items = "<ul>";
    if ( cartItems ){
        $.each( cartItems , function ( i, element){
            console.log( element );
            var item = new CartItem( element ).render();
            // alert( item );
            items += item ;
        });
    }
    items += "</ul>";

    return items;
}


function CartItem( item ){
    // alert( item );
    this.itemOpener = '<li class="cart-item-li">';
    this.name = '<div class="padding5px"><div class="cart-item-img-desc"><span data-id= "'+ item.id+'" class="remove-cart-item" title="Remove Item"> remove</span> <span class="cart-item-qty">'+ item.quantity +
        'X</span><img class="cart-item-img" src="'+ item.cartItemPhoto.url+'" alt=""><span class="cart-item-desc">'+ item.name +"</span>";
    this.price = "<span class='cart-item-price'> $"+ item.amount +"</span><div class='clearfix'></div></div></div>";
    this.itemCloser = '</li>';
    this.html = this.itemOpener +  this.name + this.price + this.itemCloser;
}

CartItem.prototype.render = function(){ return this.html};

function ajaxGetContent(uri, containerToFillIn) {
    // alert(" Go AJax ");
    $.ajax({
        url:url + uri,
        type:'GET',
        success:function (data, textStatus, jqXHR) {
            $(containerToFillIn).html(data);
        },
        error:function (data, textStatus, jqXHR) {
            console.log(textStatus);
        }
    });

}

/**
 function ajaxImageUplaod(uri, containerToFillIn) {
 alert(" Go AJax ");

 $.ajax({
 url:url + uri,
 type:'POST',
 success:function (data, textStatus, jqXHR) {
 $(containerToFillIn).html(data);
 $("#addVideoStep").removeClass("step-active").addClass("step-deactive");
 $("#addPhotoStep").removeClass("step-active").addClass("step-deactive");
 $("#finishStep").removeClass("step-deactive").addClass("step-active");
 },
 error:function (data, textStatus, jqXHR) {
 console.log(textStatus);
 }
 });

 }
 **/