<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>付款結果</title>
</head>
<body>
    <h2>${message}</h2>
    
    <h3>歐付寶回傳參數：</h3>
    <table border="1">
        <tr><th>參數名稱</th><th>值</th></tr>
        <c:forEach var="entry" items="${result}">
            <tr>
                <td>${entry.key}</td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
