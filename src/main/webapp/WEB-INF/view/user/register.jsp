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
        margin: 0;
    }

    .register-box {
        background-color: #f8f4f0;
        border-radius: 15px;
        padding: 30px;
        max-width: 500px;
        margin: 100px auto;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }

    h2 {
        color: #5a4033;
        margin-bottom: 20px;
        text-align: center;
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
    
    .form-control, .form-select {
		 border-color: #a1886e;
		 background-color: #fdfaf5;
		 color: #4b3621;
    }

    .form-control:focus, .form-select:focus {
        border-color: #d19b60;
        box-shadow: 0 0 0 0.2rem rgba(209, 155, 96, 0.25);
    }
    
</style>

<div class="container">
    <div class="register-box">
        <h2>會員註冊</h2>
        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="mb-3">
                <label class="form-label">帳號</label>
                <input type="text" name="username" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">密碼</label>
                <input type="password" name="password" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-custom">註冊</button>
            </div>
        </form>
        <c:if test="${not empty error}">
            <div class="alert alert-danger mt-3">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success mt-3">${success}</div>
        </c:if>
    </div>
</div>