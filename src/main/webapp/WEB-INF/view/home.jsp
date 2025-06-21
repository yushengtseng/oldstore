<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="layout.jsp" %>

<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@500;700&display=swap" rel="stylesheet">

<style>
    body {
        background-color: #e0cdb4;
        font-family: 'Noto Serif TC', serif;
        color: #4b3621;
        font-size: 1.15rem;
        margin: 0;
    }

    .home-wrapper {
   		background-color: #e0cdb4;
        min-height: 89vh;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        text-align: center;
        padding: 2rem;
    }

    h1 {
        font-size: 3.5rem;
        font-weight: 700;
        margin-bottom: 1rem;
        color: #5a4033;
    }

    p.lead {
        font-size: 1.5rem;
        color: #6b4c3b;
        margin-bottom: 2rem;
    }

    .btn-home {
        font-size: 1.2rem;
        padding: 0.75rem 2rem;
        border-radius: 50px;
        background-color: #e2b77e;
        color: #2d1b00;
        border: none;
        margin: 0 10px;
        transition: all 0.3s ease-in-out;
    }

    .btn-home:hover {
        background-color: #d19b60;
        transform: scale(1.05);
    }
</style>

<div class="home-wrapper">
    <h1>歡迎光臨 老式美好舊貨店</h1>
    <p class="lead">在這裡，每一件舊貨都有它的故事。</p>

    <div>
        <a href="${pageContext.request.contextPath}/shop/products" class="btn btn-home">商品列表</a>
        <a href="${pageContext.request.contextPath}/cart" class="btn btn-home">購物車</a>
        <a href="${pageContext.request.contextPath}/member/info" class="btn btn-home">會員資訊</a>
        <a href="${pageContext.request.contextPath}/order/history" class="btn btn-home">訂單資訊</a>
    </div>
    
    <c:if test="${not empty error}">
    	<div class="alert alert-danger mt-3 text-center">${error}</div>
    	<c:remove var="error" scope="session"/>
	</c:if>
    
</div>