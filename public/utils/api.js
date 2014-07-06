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