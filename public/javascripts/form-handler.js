$(document).ready(function() {
    $("#submit").click(function(){
        var username = $("#username").val();
        var avaSource = $("#avasource").val();
        var numHorizontal = $("#numhorizontal").val();
        var path = "/" + avaSource + "/" + username + "/" + numHorizontal;
        $(".progress").css("display", "block");
        $.get( path, function( data ) {
            window.location.replace("/collage");
        })
        .fail(function() {
            alert( "Something bad happened!" );
            window.location.replace("/");

        });
    });
});
