<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	    margin-bottom: 1rem;
	    color: #4a3424;
	    border-bottom: 2px solid #d2b48c;
	    padding-bottom: 0.5rem;
		}

    label {
        font-weight: 600;
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

    .text-danger {
        font-size: 0.9rem;
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

<main class="container">
    <h2 class="fw-bold" style="color: #4b3621;">編輯商品</h2>
    <form:form method="post"
           modelAttribute="productDto"
           action="${pageContext.request.contextPath}/admin/products/${productDto.productId}"
           enctype="multipart/form-data">
    	<input type="hidden" name="_method" value="put" />
        <div class="mb-3">
            <label>商品名稱</label>
            <form:input path="name" cssClass="form-control" />
            <form:errors path="name" cssClass="text-danger" />
        </div>
        <div class="mb-3">
            <label>單價</label>
            <form:input path="price" cssClass="form-control" />
            <form:errors path="price" cssClass="text-danger" />
        </div>
        <div class="mb-3">
            <label>庫存</label>
            <form:input path="stock" cssClass="form-control" />
            <form:errors path="stock" cssClass="text-danger" />
        </div>
        <div class="mb-3">
            <label>描述</label>
            <form:textarea path="description" cssClass="form-control" />
            <form:errors path="description" cssClass="text-danger" />
        </div>
        <!-- 加入圖片預覽 -->
		<div class="mb-3">
		    <label>目前圖片</label><br>
		    <img src="${pageContext.request.contextPath}/${productDto.imagePath}" width="100" height="100" />
		</div>
		
		<!-- 隱藏欄位保留原圖片路徑 -->
		<form:hidden path="imagePath" />
		
		<!-- 圖片上傳欄位 -->
		<div class="mb-3">
		    <label>上傳新圖片（若不變更可略過。）</label>
    <input type="file" name="imageFile" class="form-control" />
</div>
        <button type="submit" class="btn btn-custom">更新</button>
        <a href="${pageContext.request.contextPath}/admin/products" class="btn btn-secondary ms-2">取消</a>
    </form:form>
</main>