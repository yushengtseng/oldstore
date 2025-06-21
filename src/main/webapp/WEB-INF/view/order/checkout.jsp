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
        font-weight: bold;
        margin-bottom: 1rem;
    }

    .form-section {
        background-color: #fffaf2;
        padding: 1.5rem;
        border-radius: 12px;
        box-shadow: 0 0 10px rgba(90, 64, 51, 0.1);
    }

    .btn-custom {
        background-color: #e2b77e;
        color: #2d1b00;
        border-color: #e2b77e;
    }

    .btn-custom:hover {
        background-color: #d19b60;
        border-color: #d19b60;
    }
    
    .table-rounded {
	    border-radius: 0.75rem;
	    overflow: hidden;
	    box-shadow: 0 4px 8px rgba(0,0,0,0.08);
	}
	
	.table-rounded table {
	    margin-bottom: 0;
	    border-collapse: separate;
	    border-spacing: 0;
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

<div class="container mt-5">
    <h2>收件人資訊</h2>

    <div class="row">
        <div class="col-md-7 form-section">
            <form action="${pageContext.request.contextPath}/order/submit" method="post">
                <div class="mb-3">
                    <label for="recipient" class="form-label">收件人姓名</label>
                    <input type="text" class="form-control" name="recipient" required>
                </div>
                
                <div class="mb-3">
                    <label for="phone" class="form-label">收件人電話</label>
                    <input type="text" class="form-control" name="phone" required>
                </div>
                
                <div class="mb-3">
                    <label for="address" class="form-label">收件地址</label>
                    <input type="text" class="form-control" name="address" required>
                </div>

                <button type="submit" class="btn btn-custom">建立訂單</button>
                <a href="${pageContext.request.contextPath}/cart" class="btn btn-secondary ms-2">取消</a>
            </form>
        </div>

        <div class="col-md-5">
            <h5 class="mt-3">購物車內容</h5>
            	<div class="table-rounded">
		            <table class="table table-bordered table-striped">
		                <thead>
		                    <tr>
		                        <th>商品</th>
		                        <th>數量</th>
		                        <th>單價</th>
		                        <th>小計</th>
		                    </tr>
		                </thead>
		                
		                <tbody>
	                    <c:forEach var="item" items="${cartItems}">
	                        <tr>
	                            <td>${item.productName}</td>
	                            <td>${item.quantity}</td>
	                            <td>$ <fmt:formatNumber value="${item.price}" type="number"/></td>
	                        	<td>$ <fmt:formatNumber value="${item.price * item.quantity}" type="number"/></td>
	                        </tr>
	                    </c:forEach>
	                </tbody>
	            </table>
	        </div>
	        
		    <div class="mt-2 text-end">
		        <p>運費：<strong>$ <fmt:formatNumber value="${shippingFee}" type="number"/></strong></p>
		    </div>
		    
        </div>
    </div>
</div>
