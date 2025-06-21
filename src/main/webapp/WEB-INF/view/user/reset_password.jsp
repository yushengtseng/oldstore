<%@ page contentType="text/html;charset=UTF-8" %>
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
    .reset-box {
        background-color: #fefaf4;
        border: 2px solid #d6bfa2;
        border-radius: 12px;
        padding: 30px;
        max-width: 500px;
        margin: 80px auto;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        font-family: 'Noto Serif TC', serif;
        color: #4b3621;
    }

    .reset-box h3 {
        text-align: center;
        margin-bottom: 25px;
        font-weight: bold;
    }

    .btn-reset {
        background-color: #c59e6d;
        color: #4b3621;
        font-weight: bold;
    }

    .btn-reset:hover {
        background-color: #a67c52;
    }

	.btn-back {
		background-color: #fefaf4;
		border: 2px solid #c59e6d;
		color: #4b3621;
		font-wight: bold;
		text-align: center;
		padding: 12px 10;
		display: inline-block;
		text-decoration: none;
		boeder-radius: 6px;
		transition: 0.3s;
		height: 40px;
	}
	
	.btn-back:hover {
		background-color: #e8d8c3;
		border-color: #a67c52;
		color: #2d1b00;
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

<div class="reset-box">
    <h3>重設密碼</h3>
    <form action="${pageContext.request.contextPath}/reset-password" method="post">
        <input type="hidden" name="code" value="${code}" />
        <div class="mb-3">
            <label class="form-label">新密碼</label>
            <input type="password" name="newPassword" class="form-control" required>
        </div>
        <div class="mb-3">
            <label class="form-label">再次輸入密碼</label>
            <input type="password" name="confirmPassword" class="form-control" required>
        </div>
        <div class="text-center mt-4">
            <button type="submit" class="btn btn-reset w-50">重設密碼</button>
        </div>
    </form>

    <c:if test="${not empty success}">
        <div class="alert alert-success mt-3 text-center">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3 text-center">${error}</div>
    </c:if>

    <!-- 返回登入頁面按鈕 -->
    <div class="text-center">
        <a href="${pageContext.request.contextPath}/login" 
           class="btn btn-back w-35 mt-3">返回登入頁面</a>
    </div>
</div>
