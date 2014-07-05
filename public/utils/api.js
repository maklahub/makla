//var url = "http://localhost:9000";
$.ajaxSetup({ cache: false });
var url = '';

var o = {name:"hassan", age:33};
function apiCall(uri) {
    // alert(" Go AJax ");

    $.ajax({
        url:url + uri,
        type:'post',
        data:JSON.stringify(o),
        dataType:"json",
        contentType:"application/json",
        success:function (data, textStatus, jqXHR) {
            // var obj = jQuery.parseJSON(jqXHR.responseText);
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


function ajaxAppendHtml(uri, containerToFillIn, o , fObject) {
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