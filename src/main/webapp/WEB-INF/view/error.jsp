<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="layout.jsp" %>

<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@500;700&display=swap" rel="stylesheet">

<style>
    body {
        background-color: #e0cdb4;
        font-family: 'Noto Serif TC', serif;
        color: #4b3621;
        font-size: 1.15rem;
    }

    .error-container {
        margin-top: 100px;
        padding: 40px;
        background-color: #fffaf2;
        border: 2px solid #c7a17a;
        border-radius: 12px;
        box-shadow: 2px 2px 12px rgba(0,0,0,0.1);
        text-align: center;
    }

    .error-title {
        font-size: 2.5rem;
        font-weight: bold;
        color: #a94442;
        margin-bottom: 20px;
    }

    .error-message {
        font-size: 1.2rem;
        margin-bottom: 30px;
    }

    .btn-custom {
        background-color: #e2b77e;
        border-color: #e2b77e;
        color: #2d1b00;
        font-weight: bold;
        padding: 10px 24px;
    }

    .btn-custom:hover {
        background-color: #d19b60;
        border-color: #d19b60;
    }
</style>

<main class="container">
    <div class="error-container">
        <div class="error-title">哎呀！發生錯誤</div>
        <div class="error-message">${error}</div>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-custom">返回首頁</a>
    </div>
</main>