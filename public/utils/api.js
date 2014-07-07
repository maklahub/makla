//var url = "http://localhost:9000";
$.ajaxSetup({ cache: false });
var url = '';

var o = {name:"hassan", age:33};
function apiCall(uri, o, callback) {
    // alert(" Go AJax ");

    $.ajax({
        type:'POST',
        // dataType: 'json',
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(o),
        url:url + uri,
        success:function (data, textStatus, jqXHR) {
            // var obj = jQuery.parseJSON(jqXHR.responseText);
            callback();
            console.log(JSON.stringify(data));
            //console.log(textStatus.toString());
        },
        error:function (data, textStatus, jqXHR) {
            console.log(textStatus);
        }
    });

}

function ajaxHtml(uri, containerToFillIn, o) {
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


function ajaxAppendHtml12(uri, containerToFillIn, o , fObject) {
     //alert(" Go AJax ");
    $.ajax({
        url:url + uri,
        type:'POST',
        data:o,
        success:function (data, textStatus, jqXHR) {
            containerToFillIn.append( $(new  fObject( data ).render()).fadeIn(500)   );

        },
        error:function (data, textStatus, jqXHR) {
            console.log(textStatus);
            alert(textStatus);
        }
    });

}

function ajaxAppendHtml(uri, containerToFillIn, o) {
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
            containerToFillIn.append( $(new CartItem( data ).render()).fadeIn()  );

        },
        error:function (data, textStatus, jqXHR) {
            alert( JSON.stringify(data) );
            alert(textStatus);

        }
    });

}

function Cart ( cart ){
    var name = cart.name;
    var cartItems = cart.cartItems;
    this.cartItems = cartItems;
    this.cartWrapperOpener = '<div  id="cart-container">';
    this.cartHeader = ' <div class="row-fluid"><div class="span12"><h1><span class="sub-title">'+ cart.owner.fullName +' Cart</span></h1></div></div>';
    this.cartItems = '<div class="row-fluid" id="cartItems"> '+ returnCartItems( cartItems ) + '</div>';
    this.ta = this.calculate( cartItems );
    console.log(" ta: " + this.ta );
   // this.footer = "<div class='cart-total-amount'> <span class='badge badge-success'>" +  " " + this.ta.toString() ;
    this.footer = '<div class="cart-total-amount"><hr class="hr"> <p>Total Amount: </p><span class="cart-ta badge badge-success">' + this.ta +'</span></div>';

    this.cartWrapperCloser = "</div>";
    // this.screenHtml = this.wrapperOpener + this.topBar + this.secondaryBar + this.body + this.footer; + this.wrapperCloser;
    this.screenHtml = this.cartWrapperOpener + this.cartHeader + this.cartItems +  this.footer +  this.cartWrapperCloser;

}
Cart.prototype.render = function(){ return this.screenHtml };
Cart.prototype.calculate = function( cartItems  ){
       var ta = 0;
       $.each( cartItems , function( i, e){
             ta = ta + e.price;
       });
    return ta + "";
};

function returnCartItems( cartItems ){
    var items = "";
    $.each( cartItems , function ( i, element){
        console.log( element );
        var item = new CartItem( element ).render();
        // alert( item );
        items += item ;
    });
   // alert( items );
    return items;
}


function CartItem( item ){
    //alert( item );
    this.name = '<div id="'+ item.id+'"><div><img style="width: 25px" src="'+ item.cartItemPhoto.url+'" alt=""><span data-id= "'+ item.id+'" class="remove-cart-item"> remove</span> '+ item.name +"</div>";
    this.price = "<div> "+ item.price +"</div></div>";
    this.html = this.name + this.price;
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