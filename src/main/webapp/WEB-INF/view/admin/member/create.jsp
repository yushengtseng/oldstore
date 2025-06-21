<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="/WEB-INF/view/layout.jsp" %>

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
        margin-bottom: 1.5rem;
        color: #4a3424;
        border-bottom: 2px solid #d2b48c;
        padding-bottom: 0.5rem;
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

    .form-label {
        font-weight: bold;
    }

    .btn-primary {
        background-color: #e2b77e;
        border-color: #e2b77e;
        color: #2d1b00;
    }

    .btn-primary:hover {
        background-color: #d19b60;
        border-color: #d19b60;
    }

</style>

<div class="container mt-5">
    <h2 class="fw-bold">新增會員</h2>

    <form:form method="post" 
		       modelAttribute="userDto" 
		       action="${pageContext.request.contextPath}/admin/members">
        <div class="mb-3">
            <label class="form-label">帳號</label>
            <form:input path="username" cssClass="form-control" />
            <form:errors path="username" cssClass="text-danger" />
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <form:input path="email" cssClass="form-control" />
            <form:errors path="email" cssClass="text-danger" />
        </div>

        <div class="mb-3">
            <label class="form-label">啟用狀態</label>
            <form:select path="active" cssClass="form-select">
                <form:option value="true">啟用</form:option>
                <form:option value="false">未啟用</form:option>
            </form:select>
            <form:errors path="active" cssClass="text-danger" />
        </div>

        <div class="mb-3">
            <label class="form-label">角色</label>
            <form:select path="role" cssClass="form-select">
                <form:option value="USER">USER</form:option>
                <form:option value="ADMIN">ADMIN</form:option>
            </form:select>
            <form:errors path="role" cssClass="text-danger" />
        </div>

        <div class="mt-4">
            <button type="submit" class="btn btn-primary">新增</button>
            <a href="${pageContext.request.contextPath}/admin/members" class="btn btn-secondary ms-2">取消</a>
        </div>
    </form:form>
</div>
