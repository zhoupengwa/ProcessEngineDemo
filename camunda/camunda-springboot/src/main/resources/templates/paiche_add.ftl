<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>FreeMarker</title>
</head>
<body>

<h1 align="center">派车管理>>新增派车流程</h1>

<form action="/paiche/start/save" method="post" id="form" name="form">

    <table border="0" cellpadding="0" cellspacing="0" style="width: 100%">
        <tr>

            <td width="100%">
                <fieldset style="height: 100%;">
                    <legend>添加职务</legend>
                    <table border="0" cellpadding="2" cellspacing="1" style="width: 100%">

                        <tr>
                            <td nowrap align="right" width="13%">派车时间：</td>
                            <td>
                                <input type="text" class="form-control" name="startDateTime"/>
                            </td>
                        </tr>

                        <tr>
                            <td nowrap align="right" width="13%">乘车地点：</td>
                            <td><input name="startPosition" class="text" style="width: 250px" type="text"/></td>
                        </tr>

                        <tr>
                            <td nowrap align="right" width="13%">目的地：</td>
                            <td><input name="endPosition" class="text" style="width: 250px" type="text"/></td>
                        </tr>


                        <tr>
                            <td nowrap align="right" width="13%">乘车人数：</td>
                            <td><input name="persons" class="text" style="width: 250px" type="text"/></td>
                        </tr>


                        <tr>
                            <td nowrap align="right" width="13%">电话：</td>
                            <td><input name="phone" class="text" style="width: 250px" type="text"/></td>
                        </tr>


                        <tr>
                            <td nowrap align="right" width="13%">备注</td>
                            <td><input name="bzhu" class="text" style="width: 250px" type="text"/></td>
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