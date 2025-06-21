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

    .info-card {
        background-color: #f6e7d4;
        border: 2px solid #b89c72;
        border-radius: 1rem;
        padding: 2.5rem 3rem;
        box-shadow: 0 4px 12px rgba(115, 84, 50, 0.2);
        max-width: 650px;
        margin: 3rem auto;
    }

    .info-title {
        font-weight: 700;
        font-size: 2rem;
        border-bottom: 2px solid #d2b48c;
        padding-bottom: 0.5rem;
        margin-bottom: 2rem;
        text-align: center;
    }

    .info-row {
        display: flex;
        justify-content: space-between;
        margin-bottom: 1.2rem;
        padding-bottom: 0.5rem;
        border-bottom: 1px dashed #c8b28e;
    }

    .info-label {
        font-weight: bold;
        color: #4a3424;
        width: 35%;
    }

    .info-value {
        color: #3b2a1c;
        text-align: right;
        width: 60%;
    }
    
    .btn-change-password {
    	background-color: #b89c72;
    	color: #4b3621;
    	font-weight: bold;
    	font-size: 1.1rem;
    	padding: 0.6rem 1.5rem;
    	border-radius: 0.75rem;
    	border: none;
    	transition: scale(1.03);
    }
    
    .btn-change-password:hover {
    	background-color: #a67c52;
    	transform: scale(1.03);
    }
    
</style>

<div class="info-card">
    <div class="info-title">會員資訊</div>

    <div class="info-row">
        <div class="info-label">帳號：</div>
        <div class="info-value">${user.username}</div>
    </div>

    <div class="info-row">
        <div class="info-label">Email：</div>
        <div class="info-value">${user.email}</div>
    </div>

    <div class="info-row">
        <div class="info-label">註冊時間：</div>
        <div class="info-value">${user.formattedCreatedAt}</div>
    </div>

    <div class="info-row">
        <div class="info-label">身份：</div>
        <div class="info-value">
            <c:choose>
                <c:when test="${user.role == 'ADMIN'}">管理員</c:when>
                <c:otherwise>一般會員</c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

	<c:if test="${not empty success}">
		<div class="alert alert-success text-center" 
			 role="alert" 
			 style="max-width:650px;margin:20px auto;">
			${success}
		</div>
	</c:if>
	
	<c:if test="${not empty error}">
		<div class="alert alert-danger text-center" 
			 role="alert" 
			 style="max-width:650px;margin:20px auto;">
			${error}
		</div>
	</c:if>

<div class="text-center mt-4">
	<button type="button" 
			class="btn btn-change-password" 
			data-bs-toggle="modal" 
			data-bs-target="#changePasswordModal">
		修改密碼
	</button>
</div>

<!-- 修改密碼 Modal -->
<div class="modal fade" id="changePasswordModal" tabindex="-1" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="border-radius: 1rem; background-color: #fffaf2; border: 2px solid #c7a17a;">
      
      <div class="modal-header" style="background-color: #f0e0c0; border-bottom: 2px solid #b89c72;">
        <h5 class="modal-title fw-bold" id="changePasswordModalLabel" style="color: #5a3e2b;">修改密碼</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      
      <form action="${pageContext.request.contextPath}/member/request-password-change" method="post">
        <div class="modal-body" style="font-family: 'Noto Serif TC', serif;">
          <div class="mb-3">
            <label for="newPassword" class="form-label" style="color: #5a3e2b;">新密碼</label>
            <input type="password" class="form-control" id="newPassword" name="newPassword" required />
          </div>
          <div class="mb-3">
            <label for="confirmPassword" class="form-label" style="color: #5a3e2b;">確認新密碼</label>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required />
          </div>
        </div>
        
        <div class="modal-footer" style="background-color: #f9f3ea; border-top: 1px solid #d6c3a1;">
          <button type="submit" class="btn" style="background-color: #b89c72; color: #4b3621; font-weight: bold;">送出</button>
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
        </div>
      </form>
      
    </div>
  </div>
</div>


