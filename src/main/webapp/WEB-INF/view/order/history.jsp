<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../layout.jsp" %>

<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@500;700&display=swap" rel="stylesheet">

<style>
    body {
        background-color: #e0cdb4;
        font-family: 'Noto Serif TC', serif;
        color: #4b3621;
        font-size: 1.15rem;
    }

    h2 {
        font-weight: 700;
        margin-top: 1rem;
        margin-bottom: 1rem;
        color: #4a3424;
        border-bottom: 2px solid #d2b48c;
        padding-bottom: 0.5rem;
    }

    table {
        background-color: #f6e7d4;
        border-radius: 10px;
        overflow: hidden;
        border: 2px solid #b89c72;
        box-shadow: 0 2px 8px rgba(115, 84, 50, 0.2);
    }

    thead.table-dark {
        background-color: #a6794d !important;
        color: #fff;
    }

    .table-striped > tbody > tr:nth-of-type(odd) {
        background-color: #f2dbc4;
    }

    table tr:hover {
        background-color: #ead2b2;
    }

    .btn-outline-primary {
        border-color: #b0845f;
        color: #b0845f;
    }

    .btn-outline-primary:hover {
        background-color: #b0845f;
        color: #fff;
    }
    
</style>

<div class="container mt-4">
    <h2 class="mb-4">訂單資訊</h2>

    <c:if test="${empty orders}">
        <div class="text-center mt-5 mb-5">
            <p class="fs-4 fw-bold text-muted">尚無訂單紀錄</p>
        </div>
    </c:if>

    <c:if test="${not empty orders}">
        <table class="table table-bordered table-striped align-middle">
            <thead class="table-dark">
                <tr>
                    <th>訂單編號</th>
                    <th>建立訂單時間</th>
                    <th>總金額</th>
                    <th>狀態</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${order.formattedOrderDate}</td>
                        <td>NT$ <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/></td>
                        <td>${order.status.displayName}</td>
                        <td>
                            <a class="btn btn-sm btn-outline-primary"
                               href="${pageContext.request.contextPath}/order/detail/${order.orderId}">
                               查看明細
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>

<div style="height: 100px;"></div>
