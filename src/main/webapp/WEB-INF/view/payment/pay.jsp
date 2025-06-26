<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>歐付寶付款中...</title>
</head>
<body>
    <h2>導向歐付寶中，請稍候...</h2>

    <form id="opayForm" method="post" action="https://payment-stage.opay.tw/Cashier/AioCheckOut/V5" accept-charset="UTF-8">
        <input type="hidden" name="MerchantID" value="${MerchantID}" />
        <input type="hidden" name="MerchantTradeNo" value="${MerchantTradeNo}" />
        <input type="hidden" name="MerchantTradeDate" value="${MerchantTradeDate}" />
        <input type="hidden" name="PaymentType" value="${PaymentType}" />
        <input type="hidden" name="TotalAmount" value="${TotalAmount}" />
        <input type="hidden" name="TradeDesc" value="${TradeDesc}" />
        <input type="hidden" name="ItemName" value="${ItemName}" />
        <input type="hidden" name="ReturnURL" value="${ReturnURL}" />
        <input type="hidden" name="ClientBackURL" value="${ClientBackURL}" />
        <input type="hidden" name="ChoosePayment" value="${ChoosePayment}" />
        <input type="hidden" name="EncryptType" value="${EncryptType}" />
        <input type="hidden" name="CheckMacValue" value="${CheckMacValue}" />
    </form>

    <script>
        document.getElementById("opayForm").submit();
    </script>
</body>
</html>