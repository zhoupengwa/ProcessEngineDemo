<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>FreeMarker</title>
</head>
<body>

<h1 align="center">登录</h1>

<form action="/paiche/login" method="post" id="form" name="form">

    <table border="0" cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>

            <td width="100%">
                <fieldset style="height: 100%;">
                    <legend>用户信息</legend>
                    <table border="0" cellpadding="2" cellspacing="1" style="width: 100%">

                        <tr>
                            <td nowrap align="right" width="13%">用户ID：</td>
                            <td width="41%"><input name="userId" class="text" style="width: 250px" type="text"
                                                   size="40"></td>
                        </tr>

                        <tr>
                            <td nowrap align="right" width="13%">密码：</td>
                            <td width="41%"><input name="psd" class="password" style="width: 250px" type="password"
                        </tr>

                    </table>
                </fieldset>
            </td>

        </tr>

        <tr>
            <td colspan="2" align="center" height="50px">
                <input type="submit" name="Submit" value="登录" class="button">
                <input type="button" name="Submit2" value="返回" class="button" onclick="window.history.go(-1);">
            </td>
        </tr>
    </table>
</form>
</body>
</html>