var iframeDocument = frames['media-frame'].document;


$( iframeDocument ).ready(function(){
    var $iframe =  $( "#media-frame");

        $iframe.contents().find('.close-img-preview').on("click", function() {
            $iframe.remove();
            $('#shadow-box').remove();

        });
    $iframe.on("click", function() {
        $iframe.remove();
        $('#shadow-box').remove();

    });

    var showShadow2 = function(){
        var $document = $( iframeDocument );
        var documentHeight = $document.height();
        var div =  $('<div>').attr('id', 'shadow-box').css({ opacity:.7,'background-color': '#000', width: '100%', height: '100%', position:'fixed',top:0, bottom: 0,'z-index': 2});
        $iframe.contents().find('body').append( div );
        $iframe.contents().find('#shadow-box').on("click", function() {
            $iframe.remove();
            $('#shadow-box').remove();

        });
    }

    showShadow2();

    /*$iframe.contents().find('#do-comment').on('click', function () {
        alert("click");
        var $commentTextArea = $iframe.contents().find('#comment-text-area') ;
        var $commentContainer = $iframe.contents().find('#comments-Container');
        var comment = stripTagsFromHtml($commentTextArea.val());
        $commentTextArea.val('');
        var dataId = $(this).attr('data-id');
        var dataType = $(this).attr('data-type');
        var o = {dataType:dataType, dataId:dataId, comment:comment};
        alert( o );
        if (comment) {
            ajaxAppendHtml('/addComment', $commentContainer, o, CommentObject);
        }
    });*/

});
