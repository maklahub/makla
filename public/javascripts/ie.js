

    if (navigator.appName == 'Microsoft Internet Explorer') {
        $link = $("<link>");
        $link.attr({"href" : "/stylesheets/ie.css", "rel" : "stylesheet"});
        $('head').append( $link );
    }
