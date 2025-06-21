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

    h2 {
        font-weight: 700;
        margin-bottom: 1rem;
        color: #4a3424;
        border-bottom: 2px solid #d2b48c;
        padding-bottom: 0.5rem;
    }

    .card {
	    background-color: #f6e6d1;
	    border: 1px solid #d1a86c;
	    box-shadow: 2px 2px 6px rgba(90, 64, 51, 0.2);
	    transition: transform 0.2s;
	    transform: scale(0.85);
	    transform-origin: top center;
	    font-size: 1.05rem;
	
	.card-title {
	    font-size: 1.5rem;
	    font-weight: bold;
	    color: #3e2b1c;
	}
	
	.card-text {
	    font-size: 1.2rem;
	    color: #5a4033;
	}
	
	.card-body {
	    padding: 1rem;
	}

    .btn-success {
        background-color: #d9a066;
        border-color: #d9a066;
        color: #2d1b00;
    }

    .btn-success:hover {
        background-color: #b37b3f;
        border-color: #b37b3f;
    }

    .btn-secondary[disabled] {
        background-color: #ccc2b7;
        color: #6e6255;
        border: none;
    }

    .alert-success {
        background-color: #e3caa8;
        border-color: #d1a86c;
        color: #4a3424;
    }

    .btn-close {
        background-color: #c8a878;
    }
    
    .card-img-top {
	    transition: transform 0.3s ease;
	    border-bottom: 1px solid #d1a86c;
	}
	
	.card-img-top:hover {
	    transform: scale(1.1);
	    z-index: 2;
	    position: relative;
	}
	
	.product-img-container {
	    width: 100%;
	    height: 200px; /* 或依你想要的高度調整 */
	    background-color: #e0cdb4;
	    overflow: hidden;
	    border-bottom: 1px solid #d1a86c;
	}
	
	.product-img-container img {
	    width: 100%;
	    height: 100%;
	    object-fit: cover; /* 關鍵：自動裁切填滿 */
	    transition: transform 0.3s ease;
	}
	
	.product-img-container img:hover {
	    transform: scale(1.1);
	}
	
</style>


<div class="container mt-4">
    <h2 class="mb-4">商品列表</h2>
</div>

<c:if test="${empty products}">
    <p>目前尚無商品上架</p>
</c:if>

<!-- 加入成功提示訊息區塊 -->
<c:if test="${param.success == '1'}">
    <div class="alert alert-success alert-dismissible fade show mx-auto" role="alert" style="max-width: 400px;">
        商品已成功加入購物車
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<c:if test="${not empty products}">
    <div class="container">
        <div class="row">
            <c:forEach var="product" items="${products}">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="product-img-container">
					    <img src="${pageContext.request.contextPath}/${product.imagePath}"
					         alt="${product.name}"
					         style="cursor: zoom-in;"
					         data-bs-toggle="modal"
					         data-bs-target="#imageModal${product.productId}" />
					</div>
                    
                    <!-- 商品卡片內容 -->
                    <div class="card-body d-flex flex-column">
                        <h5 class="card-title">${product.name}</h5>

                        <!-- 商品敘述 -->
                        <p class="card-text mb-2" style="height: 60px; overflow: hidden; text-overflow: ellipsis;">
                            ${product.description}
                        </p>

                        <!-- 價格固定在底部 -->
                        <p class="card-text fw-bold mt-auto">
                            NT$ <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true" />
                        </p>

                        <!-- 加入購物車或完售按鈕 -->
                        <c:choose>
                            <c:when test="${product.stock > 0}">
                                <form action="${pageContext.request.contextPath}/cart/add" method="post" class="d-flex align-items-center">
                                    <input type="hidden" name="productId" value="${product.productId}" />
                                    <button type="submit" class="btn btn-success btn-lg px-3 py-1">加入購物車</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <p class="text-danger fw-bold fs-5 mb-2">完售</p>
                                <button class="btn btn-secondary btn-lg ps-4 ps-2" disabled style="cursor: not-allowed;">無法購買</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
             
		        <!-- 檢視商品圖片 -->
		        <div class="modal fade" id="imageModal${product.productId}" tabindex="-1"
		             aria-labelledby="imageModalLabel${product.productId}" aria-hidden="true">
		          <div class="modal-dialog modal-dialog-centered modal-lg">
		            <div class="modal-content" style="background-color: #f6e6d1;">
		              <div class="modal-header">
		                <h5 class="modal-title" id="imageModalLabel${product.productId}">
		                  ${product.name}
		                </h5>
		                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
		                        style="background-color: #c8a878;"></button>
		              </div>
		              <div class="modal-body text-center">
		                <img src="${pageContext.request.contextPath}/${product.imagePath}"
		                     alt="${product.name}" class="img-fluid rounded"
		                     style="max-height: 500px;" />
		              </div>
		            </div>
		          </div>
		        </div>
		    </div>
		</c:forEach>
    </div>
</c:if>
<div style="height: 100px;"></div>
