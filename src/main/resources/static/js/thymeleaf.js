function testFunction() {
    alert("test Thymeleaf.js!");
}

function getQueryInfo() {
    let value = $("#query-balance").val();
    let data = $.ajax({
        method: "POST",
        url: "/balance-query?name=" + value,
        data: {},
        async: true,
        success: (users) => {
            $("#show-balance").text(users.length === 0 ? "User not found!" : "Current Balance: " + users[0].balance);
        }
    });

    console.log(data);
}