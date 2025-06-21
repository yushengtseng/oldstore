<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <title>Old Soul Store</title>

    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- DataTables Bootstrap 5 CDN -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/dataTables.bootstrap5.min.css">

    <!-- DataTables 匯出用 CDN -->
    <link rel="stylesheet" href="https://cdn.datatables.net/buttons/2.4.1/css/buttons.bootstrap5.min.css">

    <!-- FontAwesome Icon -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    
    <!-- 自訂樣式 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style.css">
    
    
</head>
<body>
	
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark px-3 sticky-top">
  		<div class="container-fluid">
    
    <!-- 品牌名稱 -->
    <div class="d-flex align-items-center">
    
      <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/home">
        <span class="fw-bold fs-3" style="letter-spacing: 2px; color: #e0cdb4; margin-right: 5px;">
          老式美好舊貨店
        </span>
      </a>
      
      <a href="https://www.instagram.com/oldsoul166/" target="_blank" style="margin-right: 3px;">
        <img src="${pageContext.request.contextPath}/static/images/ig.png" 
             alt="IG" style="height: 30px;">
      </a>

      <a href="https://shopee.tw/latteabd" target="_blank">
        <img src="${pageContext.request.contextPath}/static/images/shopee.png" 
             alt="Shopee" style="height: 40px;">
      </a>
      
      	<!-- 使用者功能 -->
      	<div class="ms-5">
	        <ul class="navbar-nav">
			   <li class="nav-item">
			     <a class="nav-link nav-link-subtle" href="${pageContext.request.contextPath}/shop/products">商品列表</a>
			   </li>
			   <li class="nav-item">
			     <a class="nav-link nav-link-subtle" href="${pageContext.request.contextPath}/cart">購物車</a>
			   </li>
			   <li class="nav-item">
			     <a class="nav-link nav-link-subtle" href="${pageContext.request.contextPath}/member/info">會員資訊</a>
			   </li>
			   <li class="nav-item">
			     <a class="nav-link nav-link-subtle" href="${pageContext.request.contextPath}/order/history">訂單資訊</a>
			   </li>
			</ul>
      	</div>
      	
    </div>

	<div class="d-flex ms-auto align-items-center">
		<!-- 管理者功能（僅限 ADMIN 顯示） -->
	    <c:if test="${sessionScope.role == 'ADMIN'}">
	        <div class="dropdown me-3">
	            <button class="btn btn-outline-warning dropdown-toggle" type="button" data-bs-toggle="dropdown">
	                後台管理
	            </button>
	            <ul class="dropdown-menu dropdown-menu-end">
	                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/products">商品管理</a></li>
	                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/members">會員管理</a></li>
	                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/admin/orders">訂單管理</a></li>
	            </ul>
	        </div>
	    </c:if>
	
	    	<!-- "會員登入/會員註冊" 與 "登出"交互顯示 -->
	    <c:choose>
	        <c:when test="${not empty sessionScope.username}">     
	            <span class="me-3 fw-bold" style="color: #f8f4ec;">
	                ${sessionScope.username}
	            </span>
	            <a class="btn btn-custom btn-sm" href="${pageContext.request.contextPath}/logout">登出</a>
	        </c:when>
	        <c:otherwise>
	            <a class="btn btn-custom btn-sm me-2" href="${pageContext.request.contextPath}/login">會員登入</a>
	            <a class="btn btn-outline-custom btn-sm" href="${pageContext.request.contextPath}/register">會員註冊</a>
	        </c:otherwise>
	    </c:choose>
	</div>
  </div>
</nav>
	

    <div class="container mt-4"></div>
    
    <!-- jQuery CDN -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

    <!-- Bootstrap Bundle CDN -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- DataTables 主程式 CDN -->
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.6/js/dataTables.bootstrap5.min.js"></script>

    <!-- DataTables 匯出功能 CDN（選擇性） -->
    <script src="https://cdn.datatables.net/buttons/2.4.1/js/dataTables.buttons.min.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.4.1/js/buttons.bootstrap5.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/pdfmake.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.2.7/vfs_fonts.js"></script>
    <script src="https://cdn.datatables.net/buttons/2.4.1/js/buttons.html5.min.js"></script>

</body>
</html>