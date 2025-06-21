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

    .login-box {
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
    
    .forgot-password-link {
    	color: #6b4e2f;
    	font-weight: bold;
    	font-size: 1rem;
    	text-decoration: none;
    	transition: color 0.2s;
    }
    
    .forgot-password-link:hover {
    	color: #a67c52;
    }
    
    .btn-auth-refresh {
    	background-color: #d6bfa2;
    	color: none;
    	border-radius: 50%;
    	width: 36px;
    	height: 36px;
    	font-size: 1rem;
    	line-height: 1;
    	display: flex;
    	align-items: center;
    	box-shadow: 1px 1px 4px rgba(0,0,0,0.1);
    	transition: background-color 0.2s;  
    }
    
    .btn-auth-refresh:hover {
    	background-color: #c5a782;
    	cursor: pointer;
    }
    
    .btn-auth-refresh i {
    	transition: transform 0.5s ease;
    }
    
    .btn-auth-refresh:hover i {
    	transform: rotate(360deg);
    }
    
    .form-check-input {
	    border-color: #a1886e;
	    background-color: #fdfaf5;
	}
	
	.form-check-input:checked {
	    background-color: #c59e6d;
	    border-color: #c59e6d;
	}
	
	.form-check-label {
	    color: #4b3621;
	    font-weight: bold;
	}
	
	.remember-me {
		margin-top: -11px;
	}
    
</style>

<div class="container">
    <div class="login-box">
        <h2>會員登入</h2>
        <form action="${pageContext.request.contextPath}/login" 
        	  method="post">
        	  
            <div class="mb-3">
                <label class="form-label">帳號</label>
                <input type="text" 
	                   name="username" 
	                   class="form-control"
	                   value="${rememberedUsername}"
	                   required>
            </div>
                          
			<div class="form-check mb-2 remember-me">
				<input class="form-check-input"
					   type="checkbox"
					   name="rememberMe"
					   id="rememberMe"
					   ${not empty rememberedUsername ? "checked" : ""}>
				
				<label class="form-check-label" 
					   for="rememberMe">
					記住我
				</label>
			</div>
			
            <div class="mb-3">
                <label class="form-label">密碼</label>
                <input type="password" 
	                   name="password" 
	                   class="form-control" 
	                   required>
            </div>


            <div class="text-end mt-3">
            	<a href="${pageContext.request.contextPath}/forgot-password"
            	   class="forgot-password-link">
            	   忘記密碼？</a>
            </div>
   
            <div class="mb-3">
                <label class="form-label">驗證碼</label><br>
                
			<div class="d-flex align-items-center gap-2">
			    <img id="authImage" 
			         src="${pageContext.request.contextPath}/authcode" 
			         alt="驗證碼圖片" 
			         style="height:40px; border: 1px solid #a1886e; border-radius: 6px;">
			    
			    <button type="button" 
			            class="btn-auth-refresh"
			            onclick="refreshAuthCode()" 
			            title="重新產生驗證碼">
			        <i class="fas fa-rotate-right"></i>
			    </button>
			</div>
						
			    <input type="text" 
			           name="authcode" 
			           class="form-control mt-2" 
			           required>
			</div>
                       
            <div class="text-center">
                <button type="submit" 
                		class="btn btn-custom">
                		登入</button>
            </div>
            
        </form>
        
        <c:if test="${not empty success}">
            <div class="alert alert-success mt-3">${success}</div>
        </c:if>
        
        <!-- 錯誤訊息（優先 sessionScope.error > sessionScope.errorMsg > requestScope.error） -->
		<c:choose>
		    <c:when test="${not empty sessionScope.error}">
		        <div class="alert alert-danger mt-3">${sessionScope.error}</div>
		        <c:remove var="error" 
		        		  scope="session"/>
		        		  
		    </c:when>
		    <c:when test="${not empty sessionScope.errorMsg}">
		        <div class="alert alert-danger mt-3">${sessionScope.errorMsg}</div>
		        <c:remove var="errorMsg" 
		        		  scope="session"/>
		        		  
		    </c:when>
		    <c:when test="${not empty error}">
		        <div class="alert alert-danger mt-3">${error}</div>
		    </c:when>
		</c:choose>
		
    </div>
</div>

<script>

    function refreshAuthCode() {
        const img = document.getElementById('authImage');
        img.src = '${pageContext.request.contextPath}/authcode?ts=' + new Date().getTime();
    }
    
</script>
