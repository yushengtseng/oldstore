<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

    .table thead {
        background-color: #d3bfa3;
        color: #2d1b00;
    }
    
	.table-rounded {
	    border-radius: 0.75rem;
	    overflow: hidden;
	    box-shadow: 0 4px 8px rgba(0,0,0,0.08);
	}
	
	/* 可加強表格間距的整齊度 */
	.table-rounded table {
	    margin-bottom: 0;
	    border-collapse: separate;
	    border-spacing: 0;
	}

    .status-paid {
        color: green;
        font-weight: bold;
    }

    .status-pending {
        color: orange;
        font-weight: bold;
    }

    .status-shipped {
        color: blue;
        font-weight: bold;
    }
    
    .btn-back {
        background-color: #5a4033;
        color: #fff;
        border: none;
    }

    .btn-back:hover {
        background-color: #3d2b22;
    }

    .btn-ship {
        background-color: #e2b77e;
        color: #2d1b00;
        border: none;
    }

    .btn-ship:hover {
        background-color: #d19b60;
        color: white;
    }

    .btn-complete {
        background-color: #6c9a8b;
        color: #fff;
        border: none;
    }

    .btn-complete:hover {
        background-color: #588274;
    }

    .btn-cancel {
        background-color: #a94442;
        color: #fff;
        border: none;
    }

    .btn-cancel:hover {
        background-color: #843534;
    }
</style>

<div class="container mt-5">
    <h2 class="fw-bold" style="color: #4b3621;">訂單明細</h2>

    <p><strong>訂單編號：</strong>${order.orderId}</p>
    <p><strong>會員帳號：</strong>${order.username}</p>
    <p><strong>建立訂單時間：</strong>${order.formattedOrderDate}</p>
	<p><strong>結帳時間：</strong>
	    <c:choose>
	        <c:when test="${order.paidAt != null}">
	            ${order.formattedPaidAt}
	        </c:when>
	        <c:otherwise>尚未結帳</c:otherwise>
	    </c:choose>
	</p>
	
	<p><strong>出貨時間：</strong>
	    <c:choose>
	        <c:when test="${order.shippedAt != null}">
	            ${order.formattedShippedAt}
	        </c:when>
	        <c:otherwise>尚未出貨</c:otherwise>
	    </c:choose>
	</p>
	
	<p><strong>收貨時間：</strong>
	    <c:choose>
	        <c:when test="${order.receivedAt != null}">
	            ${order.formattedReceivedAt}
	        </c:when>
	        <c:otherwise>尚未收貨</c:otherwise>
	    </c:choose>
	</p>

    <p><strong>狀態：</strong>
        <span class="status-${order.status.toString().toLowerCase()}">${order.status.displayName}</span>
    </p>
    <p><strong>收件人：</strong>${order.recipient}</p>
    <p><strong>電話：</strong>${order.phone}</p>
    <p><strong>地址：</strong>${order.address}</p>

    <hr>

    <h5>商品明細</h5>
	    <div class="table-rounded">
		    <table class="table table-bordered table-striped">
		        <thead>
		            <tr>
		                <th>商品名稱</th>
		                <th>數量</th>
		                <th>單價</th>
		                <th>小計</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:forEach var="item" items="${order.items}">
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
	    
	    <div style="height: 10px;"></div>
    
    <h5 class="text-end mt-3">
	    運費：<strong>$ <fmt:formatNumber value="${order.shippingFee}" type="number" /></strong>
	</h5> 
    
    <h5 class="text-end">
    	總金額：<strong>NT$ <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/></strong>
    </h5>

	<div class="text-end mt-4">
	
	    <a class="btn btn-back w-25 mb-2" href="${pageContext.request.contextPath}/admin/orders">返回清單</a>
	
	    <c:if test="${order.status == 'PAID'}">
	        <form method="post" action="${pageContext.request.contextPath}/admin/orders/update-status/${order.orderId}/SHIPPED">
	            <input type="hidden" name="_method" value="put" />
	            <button type="submit" class="btn btn-ship w-25 mb-2">出貨</button>
	        </form>
	    </c:if>
	
	    <c:if test="${order.status == 'RECEIVED'}">
	        <form method="post" action="${pageContext.request.contextPath}/admin/orders/update-status/${order.orderId}/COMPLETED">
	            <input type="hidden" name="_method" value="put" />
	            <button type="submit" class="btn btn-complete w-25 mb-2">完成</button>
	        </form>
	    </c:if>
	
	    <c:if test="${order.status != 'COMPLETED' && order.status != 'CANCELED'}">
	        <form method="post" action="${pageContext.request.contextPath}/admin/orders/update-status/${order.orderId}/CANCELED">
	            <input type="hidden" name="_method" value="put" />
	            <button type="submit" class="btn btn-cancel w-25">取消訂單</button>
	        </form>
	    </c:if>
	
	</div>

</div>

<div style="height: 100px;"></div>