<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>FreeMarker</title>
</head>
<body>

<h1 align="center">用户管理>>新增部门</h1>

<form action="/department/save" method="post" id="form" name="form">

    <table border="0" cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>

            <td width="100%">
                <fieldset style="height: 100%;">
                    <legend>添加部门</legend>
                    <table border="0" cellpadding="2" cellspacing="1" style="width: 100%">

                        <tr>
                            <td nowrap align="right" width="13%">部门ID：</td>
                            <td width="41%"><input name="departmentId" class="text" style="width: 250px" type="text"
                                                   size="40"></td>
                        </tr>

                        <tr>
                            <td nowrap align="right" width="13%">部门名称：</td>
                            <td width="41%"><input name="departmentName" class="text" style="width: 250px" type="text"
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