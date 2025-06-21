<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../layout.jsp" %>

<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+TC:wght@500;700&display=swap" rel="stylesheet">

<style>
    body {
        background-color: #e0cdb4;
        font-family: 'Noto Serif TC', serif;
        color: #4b3621;
        font-size: 1.15rem;
    }

    .verify-fail-box {
        background-color: #f8f4f0;
        border-radius: 15px;
        padding: 30px;
        max-width: 500px;
        margin: 100px auto;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        text-align: center;
    }

    .verify-fail-box h2 {
        color: #dc3545;
        font-weight: bold;
        margin-bottom: 20px;
    }

    .verify-fail-box p {
        color: #5a4033;
        font-size: 1.1rem;
        margin-bottom: 25px;
    }

    .btn-custom {
        background-color: #e2b77e;
        border-color: #e2b77e;
        color: #2d1b00;
    }

    .btn-custom:hover {
        background-color: #d19b60;
        border-color: #d19b60;
    }
</style>

<div class="container">
    <div class="verify-fail-box">
        <h2>驗證失敗</h2>
        <p>無效或已過期的驗證連結，請確認您點擊的網址是否正確，或此帳號已完成驗證。</p>
        <a href="${pageContext.request.contextPath}/login" class="btn btn-custom">前往登入頁面</a>
    </div>
</div>