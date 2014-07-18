
// wait for the document to be loaded
$( document).ready( function(){
    $( 'body').imagesLoaded( function() {
        grayScaleHtmlPage();


    });

    //unGrayScale(); // put the dom's into normal mode no black and white
});

$(function () {


    var $finputText = $('.finputText');
    var $frow = $('.frow');
    var $body = $('body');
        //$finputText.val("");
    $.each($finputText, function (i, element) {
        $(element).val().length > 0 ? $(element).siblings().hide() : $(element).siblings().fadeIn();
    });

    $body.on('click', '.flabel', function () {
        $finputText = $('.finputText');
        $.each($finputText, function (i, element) {
            $(element).val().length > 0 ? "" : $(element).siblings().fadeIn();
        });
        $(this).hide().siblings().focus();
    });

    $body.on('click', '.finputText', function () {
        $finputText = $('.finputText');
        $.each($finputText, function (i, element) {
           // console.log( 'test' );
            $(element).val().length > 0 ? "" : $(element).siblings().fadeIn();
        });
        $(this).focus().siblings().hide();
    });

    $body.on('focus', '.finputText, #comment-text-area', function () {
        $(this).siblings().hide();
    });

    $body.on('blur', '.finputText, #comment-text-area', function () {
        if ( $( this).val().length == 0 ){
            $(this).siblings().show();
        }
    });

    $body.on( 'click', '.tool-bar-btn', function(){
        $this = $( this );
        $('.tool-bar-btn').removeClass('btn-success');
        $this.addClass('btn-success');
    });

    //   @videos
   /* $(document).on('click', '#addVideo', function () {
        $("#addVideoScreen").slideDown();
    });*/

    $(document).on('click', '#savePersonalInfo', function () {
        //  alert( "Start api call" );
        var userName = $("#userName").val();
        var userCity = $("#city").val();
        var userState = $("#state").val();
        var userCountry = $("#country").val();
        var userVideoLink = $("#videoLink").val();
        var userVideoDescription = $("#videoDescription").val();
        var o = {};
        o.userName = userName;
        o.userCity = userCity;
        o.userState = userState;
        o.userCountry = userCountry;
        o.userVideoLink = userVideoLink;
        o.userVideoDescription = userVideoDescription;
       // alert( JSON.stringify( o ));
        ajaxHtml('/addPersonalInfo', "#stepsContentContainer", o);
    });

});



//
function getYouTubeVideoId ( url ){
    var videoId;
    if (url.indexOf('iframe') < 0 && url.indexOf('src') < 0){
        videoId = url.replace(/(?:https?:\/\/)?(?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com(?:\/embed\/|\/v\/|\/watch\?v=))([\w\-]{10,12})\b[?=&\w]*(?!['"][^<>]*>|<\/a>)/ig,'$1');
    }
    else if ( url.indexOf('iframe') < 0 && url.indexOf('src') > 0){
        return; // to be completed
    }
    else {
        var e = $( url );
        videoId =  e.attr('src').replace(/(?:https?:\/\/)?(?:\/\/)?(?:www\.)?(?:youtu\.be\/|youtube\.com(?:\/embed\/|\/v\/|\/watch\?v=))([\w\-]{10,12})\b[?=&\w]*(?!['"][^<>]*>|<\/a>)/ig,'$1');
    }
    return videoId;
}

// time ago function
function timeAgo(time){

    switch (typeof time) {
        case 'number': break;
        case 'string': time = +new Date(time); break;
        case 'object': if (time.constructor === Date) time = time.getTime(); break;
        default: time = +new Date();
    }
    var time_formats = [
        [60, 'seconds', 1], // 60
        [120, '1 minute ago', '1 minute from now'], // 60*2
        [3600, 'minutes', 60], // 60*60, 60
        [7200, '1 hour ago', '1 hour from now'], // 60*60*2
        [86400, 'hours', 3600], // 60*60*24, 60*60
        [172800, 'Yesterday', 'Tomorrow'], // 60*60*24*2
        [604800, 'days', 86400], // 60*60*24*7, 60*60*24
        [1209600, 'Last week', 'Next week'], // 60*60*24*7*4*2
        [2419200, 'weeks', 604800], // 60*60*24*7*4, 60*60*24*7
        [4838400, 'Last month', 'Next month'], // 60*60*24*7*4*2
        [29030400, 'months', 2419200], // 60*60*24*7*4*12, 60*60*24*7*4
        [58060800, 'Last year', 'Next year'], // 60*60*24*7*4*12*2
        [2903040000, 'years', 29030400], // 60*60*24*7*4*12*100, 60*60*24*7*4*12
        [5806080000, 'Last century', 'Next century'], // 60*60*24*7*4*12*100*2
        [58060800000, 'centuries', 2903040000] // 60*60*24*7*4*12*100*20, 60*60*24*7*4*12*100
    ];
    var seconds = (+new Date() - time) / 1000,
        token = 'ago', list_choice = 1;

    if (seconds == 0) {
        return 'Just now'
    }
    if (seconds < 0) {
        seconds = Math.abs(seconds);
        token = 'from now';
        list_choice = 2;
    }
    var i = 0, format;
    while (format = time_formats[i++])
        if (seconds < format[0]) {
            if (typeof format[2] == 'string')
                return format[list_choice];
            else
                return Math.floor(seconds / format[2]) + ' ' + format[1] + ' ' + token;
        }
    return time;
}


function grayScaleHtmlPage(){
    $('img').addClass( 'grayscale').css('visibility', 'visible').hover(
        function() {
            $( this ).removeClass( 'grayscale' );
        }, function() {
            $( this ).addClass( 'grayscale');
        }
    );
}
function unGrayScale( ){
    var s = 1000; // 1 second
    $( 'html' ).animate({
        opacity: 1
    }, 5 * s , function(){

        $( 'html').removeClass( 'grayscale' );

    });
}

// set animation - margin top for the main content

function slideDownMainContent(){
    var $topHeaderBar = $('#header-top-bar');
    var $contentWrapper = $('#content-container');
    var marginTop =  $topHeaderBar.height() + 6;
    //alert( marginTop)

    //console.log( $contentWrapper );
    $contentWrapper.css({opacity:0});
    $contentWrapper.animate({
        'margin-top' : marginTop + "px",
        'opacity': 1
    }, 'slow', function(){
        //alert(' done ');
    });

}

function stripTagsFromHtml( input ){
    var $d = $("<div></div>");
    $d.text(input.replace(/(<([^>]+)>)/ig,""))  ;
   return $d.text();
}

function displayPhotoMediaFrame( $this, loggedIn ){
    $('body').addClass('overflow-hidden');
    var $thisChild = $this.children(":first");
    var imgTitle =  $thisChild.attr("title") || "No title";
    var $commentsContainer = $('<div>');
    $commentsContainer.attr('id', 'comments-Container');
    var $mediaFrame = $("<div id='photo-media-frame' class='overflow-auto'>");
    $mediaFrame.css({ width:'100%', height:'100%', border:"solid 1px red", position: "fixed", top: "0", bottom : "0", 'z-index':"999"});
    var $imgWrapper = $("<div id='media-wrapper'>");
    var $bounderTopBar = $("<div class='container-fluid boundery-top-bar ' > <h3 class='img-title'>"+ imgTitle +"</h3></div>");
    var $bounderBottomBar = $("<div class='container-fluid boundery-bottom-bar' >");
    var $imgPreview = $("<div id='media-preview'>");
    $imgWrapper.append( $bounderTopBar, $imgPreview );
    $imgWrapper.css({ 'max-width': '600px','z-index' : 5, height: 'auto', display: 'block'});
    //$imgPreview.html("");

    var dataId = $thisChild.attr('data-id');
    var dataType = $thisChild.attr('data-type');
   // var getCommentsUrl = dataType == "profileImage" ? '/getProfileImageComment/' : '/getComments/';
    var getCommentsUrl = '/getComments/';

    ajaxReturnJson(getCommentsUrl + dataId, function (data) {
        //alert ( JSON.stringify(data) );
        $.each(data, function (i, e) {
           $commentsContainer.append(new CommentObject(e).render());

        });
    });
    var $img = $('<img>');
    $img.attr({'src':"/images/loader.gif", 'data-id':dataId});
    $img.attr({'src':$thisChild.attr('src'), 'data-id':dataId});
    $img.css({height:'auto'});
    var $imgContainer = $('<div>');
    $imgContainer.attr('class', 'div-img-top-curves');
    $imgContainer.css({ 'text-align':'center', margin:'auto', 'background-color':'#222'});
    $imgContainer.append($img);

    $imgPreview.html($imgContainer);
    $imgPreview.append($commentsContainer);
    if ( loggedIn ) {
        $imgPreview.append(forms.submitMyphotoComment(dataId, dataType));
    } else {
        var $signInBtn = $('<div>');
        $signInBtn.attr({'class':'btn btn-primary' , id: 'comment-signing-btn'});
        $signInBtn.css({'position':'absolute', 'left':0, 'right':0 });
        $signInBtn.text(" Please sign in to leave a comment");
        $imgPreview.append($signInBtn);
    }

    $imgPreview.append( $bounderBottomBar );
    $imgWrapper.append('<button class="close-icon close-img-preview" style="z-index:5; position: absolute;top: -40px; right: -11px"></button>');//.fadeIn();

    $mediaFrame.append( $imgWrapper );
    $( 'body' ).append( $mediaFrame );
    showShadow();

    $signInBtn.on( 'click', function(){
     //   alert(" clicked");
        $body = $('body');
        $bodyWidth = $body.width();
        $bodyHeight = $body.height();
        var $dropDownContainer = $("<div>");
        $dropDownContainer.css({left: 0, right: 0, 'position':'absolute', 'z-index': 1000,  'backgroundColor':'rgb(230, 230, 230)', 'width':"100%", 'height': "100%", padding: "2px", margin: "auto"});
        $dropDownContainer.html( new DropDownWindow( forms.logInPopUp(), "").render());
        $( 'body').append($dropDownContainer);
        $dropDownContainer.animate({
            top:0
        }, "slow", function(){
            $(".close-dropDown").on( 'click', function(){
                $( this).parent().parent().fadeOut();
            });
        });
    });


}

function displayVideoMediaFrame( $this ){
    var $videoMediaFrame = $("<div id='video-media-frame' class='overflow-auto'>");
    $videoMediaFrame.css({ width:'100%', height:'100%', border:"solid 1px red", position: "absolute", top: "0", bottom : "0", 'z-index':"999"});

    var $thisChild = $this.children(":first");
    // $videoPreview.append( $thisChild );
    var $iframe = $('<iframe>');
    $iframe.css({ width:'100%', height:'600px', border:0, position: "relative",'margin-top': '40px' , 'z-index':"999"});
    var youtubeQuery = "?wmode=opaque&rel=0&autoplay=1&controls=0&showinfo=0&enablejsapi=1&modestbranding=0";
    var videoUrl = "//www.youtube.com/embed/" + $thisChild.attr('id') + youtubeQuery;
    $iframe.attr('src', videoUrl);
    $videoMediaFrame.html($iframe);
    $videoMediaFrame.append('<div><button class="close-icon close-img-preview " style="z-index:10; position: absolute;top: -5px; right: 0px"></button></div>');
    $( 'body').append( $videoMediaFrame );
    showShadow();

}

//Email validation
function validateEmail(email) {
    //function IsEmail(email) {
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
   // alert( regex.test(email) );
        return regex.test(email);
   // }
}

