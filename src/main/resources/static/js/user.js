function user_login() {
    $.ajax({
        url : "user/login",
        method : "post",
        data : {
            "username" : $("#input_username").val(),
            "userpass" : $("#input_userpass").val()
        },
        success : function (result) {
            if (result != null && result != "") {
                $("#div_user_login").attr("color", "red");
                $("#div_user_login").html(
                    "欢迎你<br/>你的用户名：" + result.username
                );
            } else {
                $("#div_user_login").append("<div style='color: red'>账号/密码错误</div>")
            }
        }
    });
}
function user_register() {
    $.ajax({
        url : "user/register",
        method : "post",
        data : {
            "username" : $("#input_username").val(),
            "userpass" : $("#input_userpass").val()
        },
        success : function (result) {
            if (result) {
                alert("注册成功");
                user_login();
            }
        }
    });
}