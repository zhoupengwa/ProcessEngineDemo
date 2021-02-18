<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>FreeMarker</title>
</head>
<body>

<h1 align="center">用户管理>>新增用户</h1>

<form action="/user/save" method="post" id="form" name="form">

    <table border="0" cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>

            <td width="100%">
                <fieldset style="height: 100%;">
                    <legend>添加用户</legend>
                    <table border="0" cellpadding="2" cellspacing="1" style="width: 100%">

                        <tr>
                            <td nowrap align="right" width="13%">用户ID：</td>
                            <td width="41%"><input name="userId" class="text" style="width: 250px" type="text"
                                                   size="40"></td>
                        </tr>

                        <tr>
                            <td nowrap align="right" width="13%">用户名称：</td>
                            <td width="41%"><input name="username" class="text" style="width: 250px" type="text"
                        </tr>


                        <tr>
                            <td nowrap align="right" width="13%">密码：</td>
                            <td width="41%"><input name="psd" class="password" style="width: 250px" type="password"
                        </tr>

                        <tr>
                            <td nowrap align="right" width="13%">所属部门ID：</td>
                            <td width="41%"><input name="departmentId" class="text" style="width: 250px" type="text"
                        </tr>

                        <tr>
                            <td nowrap align="right" width="13%">所属职务ID：</td>
                            <td width="41%"><input name="roleId" class="text" style="width: 250px" type="text"
                        </tr>

                    </table>
                </fieldset>
            </td>

        </tr>

        <tr>
            <td colspan="2" align="center" height="50px">
                <input type="submit" name="Submit" value="保存" class="button">
                <input type="button" name="Submit2" value="返回" class="button" onclick="window.history.go(-1);">
            </td>
        </tr>
    </table>
</form>
</body>
</html>