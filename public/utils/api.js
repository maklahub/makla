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

function  Cart ( cart ){
    if ( cart.totalAmount > 0 ){

        var name = cart.name;
        var cartItems = cart.cartItems || null;
        var cartOwner = cart.owner != null?  cart.owner.fullName : "";
        this.cartItems = cartItems;

        this.cartWrapperOpener = '<div  id="cart-container">';
        this.cartHeader = ' <div class="row-fluid"><div class="span12"><h1 class="cart-title">'+ cartOwner +' Cart</h1></div></div>';
        this.cartItems = '<div class="row-fluid" id="cartItems"> '+ returnCartItems( cartItems ) + '</div>';
        this.ta = this.calculate( cartItems );
        console.log(" ta: " + this.ta );
        // this.footer = "<div class='cart-total-amount'> <span class='badge badge-success'>" +  " " + this.ta.toString() ;
        this.footer = '<div class="cart-total-amount"> <p>Total Amount:'+ cart.totalAmount +' </p><span class="cart-ta badge badge-success">$' + this.ta +'</span></div>';
        this.checkOutContainer =  cartItems != null ?'<div class="check-out"><form style="margin: 0" method="post" action="checkout"><input type="hidden" name="cartId" value="'+cart.id +'"><input type="submit" class="checkout-btn" value="Checkout" id="'+ cart.id +'"></form></div>' : '';
        this.cartWrapperCloser = "</div>";
        // this.screenHtml = this.wrapperOpener + this.topBar + this.secondaryBar + this.body + this.footer; + this.wrapperCloser;
        this.screenHtml = this.cartWrapperOpener + this.cartHeader + this.cartItems +  this.footer +  this.cartWrapperCloser + this.checkOutContainer;

    }




}
Cart.prototype.render = function(){ return this.screenHtml };
Cart.prototype.calculate = function( cartItems  ){
    var ta = 0;
    if ( cartItems ){

        $.each( cartItems , function( i, e){
            ta = ta +  e.price  ;
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
    this.name = '<div class="padding10px"><div class="cart-item-img-desc"><span data-id= "'+ item.id+'" class="remove-cart-item"> remove</span> <img class="cart-item-img" src="'+ item.cartItemPhoto.url+'" alt=""><span class="cart-item-desc">'+ item.name +"</span></div>";
    this.price = "<div class='cart-item-price'> $"+ item.price +"</div><div class='clearfix'></div></div>";
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