<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>FreeMarker</title>
</head>
<body>

<h1 align="center">用户管理>>用户列表</h1>
<table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#FFFFEE" class="newfont03">
    <tr bgcolor="#EEEEEE">
        <td width="15%" height="30">用户编号</td>
        <td width="15%" height="30">用户姓名</td>
        <td width="15%" height="30">所属部门</td>
        <td width="15%" height="30">所属职务</td>
        <td width="15%" height="30">所属组ID</td>
        <td width="15%" height="30">所属组名称</td>
        <td width="">操作</td>
    </tr>

    <#list userList as user>
        <tr>
            <td height="30">${user.userId}</td>
            <td height="30">${user.username}</td>
            <td height="30">${user.departmentName}</td>
            <td height="30">${user.roleName}</td>
            <td height="30">${user.roleId}:${user.departmentId}</td>
            <td height="30">${user.departmentName}:${user.roleName}</td>
            <td><a href="/user/delete/${user.userId}">删除</a></td>
        </tr>
    </#list>

    <tr>
        <td><input type="button" name="Submit2" value="返回" class="button" onclick="window.history.go(-1);"></td>
    </tr>

</table>
</body>
</html>