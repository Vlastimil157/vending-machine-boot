$(document).ready(() => {
    $(".input-bar input").on("focus", function() {
        $(this).addClass("focus");
    });

    $(".input-bar input").on("blur", function() {
        if ($(this).val() === "") $(this).removeClass("focus");
    });

    if ($(".input-bar #username").val() !== "") {
        $(".input-bar #username").addClass("focus");
    }
});

function sendLoginMsg() {
    $(".login-button").addClass("not-display");
    $(".loading").removeClass("not-display");

    let requestBody = {
        name: $(".input-bar #username").val(),
        password: $(".input-bar #password").val()
    }

    let responseBody = $.ajax({
        url: "/login-check",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(requestBody),
        dataType: "json",
        success: () => {
            $('.fail-msg').addClass("not-display");
            $(".success-msg").removeClass("not-display");
            // TODO: redirect to main page
        },
        error: (msg) => {
            $(".login-button").removeClass("not-display");
            $(".loading").addClass("not-display");
            $(".fail-msg").removeClass("not-display");
            console.log(msg);
        }
    })
}