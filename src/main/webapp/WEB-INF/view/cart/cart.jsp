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

    .btn-vintaga {
        background-color: #d9a066;
        border-color: #d9a066;
        color: #2d1b00;
    }

    .btn-vintaga:hover {
        background-color: #b37b3f;
        border-color: #b37b3f;
    }

    .btn-danger {
        background-color: #b35555;
        border-color: #b35555;
    }

    .btn-danger:hover {
        background-color: #8c2e2e;
        border-color: #8c2e2e;
    }

    .btn-warning {
        background-color: #e8c28d;
        border-color: #e8c28d;
        color: #4a3524;
    }

    .btn-warning:hover {
        background-color: #d4a96d;
        border-color: #d4a96d;
    }

    select.form-select {
        font-size: 0.95rem;
    }

    .text-danger.fw-bold {
        font-size: 1.2rem;
    }

    .table img {
        border: 1px solid #d2b48c;
        border-radius: 4px;
        background-color: #fffaf1;
    }
</style>

<div class="container mt-4">
    <h2 class="mb-4">購物車</h2>
</div>

<c:if test="${empty cartItems}">
    <div class="text-center mt-5 mb-5">
        <p class="fs-4 fw-bold text-muted">目前購物車是空的</p>
        
	    <form action="${pageContext.request.contextPath}/shop/products" method="get">
	            <button type="submit" class="btn btn-vintaga">前往商品列表</button>
	    </form>
    </div>
    
</c:if>

<c:if test="${not empty cartItems}">
	<div class="container">
    <table id="cartTable" class="table table-striped align-middle">
        <thead class="table-dark">
            <tr>
                <th>商品圖片</th>
                <th>商品名稱</th>
                <th>單價</th>
                <th>數量</th>
                <th>小計</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:set var="total" value="0" />
            <c:forEach var="item" items="${cartItems}">
                <c:set var="subtotal" value="${item.price * item.quantity}" />
                <c:set var="total" value="${total + subtotal}" />
                <tr>
                    <td><img src="${pageContext.request.contextPath}/${item.imagePath}" width="80"></td>
                    <td>${item.productName}</td>
                    <td>$ <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart/update" method="post" class="d-inline">
                            <input type="hidden" name="_method" value="put" />
                            <input type="hidden" name="cartItemId" value="${item.cartItemId}" />
                            <select name="quantity"
                                    class="form-select form-select-sm d-inline"
                                    style="width: 80px;"
                                    onchange="this.form.submit()">
                                <c:forEach begin="1" end="${item.stock}" var="i">
                                    <option value="${i}" <c:if test="${item.quantity == i}">selected</c:if>>${i}</option>
                                </c:forEach>
                            </select>
                        </form>
                    </td>
                    <td>$ <fmt:formatNumber value="${subtotal}" type="number" groupingUsed="true"/></td>
                    <td>
                        <form action="${pageContext.request.contextPath}/cart/delete" method="post" class="d-inline">
                            <input type="hidden" name="_method" value="delete" />
                            <input type="hidden" name="cartItemId" value="${item.cartItemId}" />
                            <button type="submit" class="btn btn-sm btn-danger">刪除</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 清空與總金額 -->
    <div class="d-flex justify-content-between align-items-center mt-4">
        <form action="${pageContext.request.contextPath}/cart/clear" method="post">
            <button type="submit" class="btn btn-warning btn-sm">清空購物車</button>
        </form>

        <h5>總金額：<span class="text-danger fw-bold">NT$ <fmt:formatNumber value="${total}" type="number" groupingUsed="true"/></span></h5>
    </div>

    <!-- 結帳按鈕 -->
    <div class="text-end mt-3">
        <form action="${pageContext.request.contextPath}/order/checkout" method="get">
            <button type="submit" class="btn btn-vintaga">前往結帳</button>
        </form>
    </div>
    </div>
</c:if>
<div style="height: 100px;"></div>