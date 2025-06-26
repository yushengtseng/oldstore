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
        min-height: 85vh;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        text-align: center;
        padding: 2rem;
    }

    .title {
        font-size: 5.2rem;
        font-weight: 700;
        color: #4b3621;
        margin-bottom: 0.8rem;
        letter-spacing: 2px;
    }

    .slogan {
        font-size: 1.8rem;
        color: #5e4634;
        margin-bottom: 2.5rem;
    }

    .btn-home {
        font-size: 1.1rem;
        padding: 0.6rem 1.8rem;
        border-radius: 30px;
        background-color: #e2b77e;
        color: #2d1b00;
        border: none;
        text-decoration: none;
        transition: all 0.3s ease-in-out;
    }

    .btn-home:hover {
        background-color: #d19b60;
        transform: scale(1.05);
    }
</style>

<div class="home-wrapper">
    <h1 class="title">老式美好舊貨店</h1>
    <p class="slogan">拾起舊物的記憶，在這裡與時光相遇。</p>
    <a href="${pageContext.request.contextPath}/shop/products" class="btn btn-home">開始探索</a>
</div>