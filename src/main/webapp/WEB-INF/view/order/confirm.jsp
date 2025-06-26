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

    .confirm-box {
        background-color: #fffaf2;
        padding: 2rem;
        border-radius: 15px;
        box-shadow: 0 0 10px rgba(90, 64, 51, 0.1);
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
	
</style>

<div class="container mt-5">
    <div class="confirm-box">
        <h2>訂單已成功送出</h2>
        <p><strong>訂單編號：</strong>${order.orderId}</p>
        <p><strong>建立訂單時間：</strong>${order.formattedOrderDate}</p>
        <p><strong>收件人：</strong>${order.recipient}</p>
        <p><strong>電話：</strong>${order.phone}</p>
        <p><strong>地址：</strong>${order.address}</p>
        <p><strong>訂單狀態：</strong>${order.status.displayName}</p>
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
		                        <td>$ <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/></td>
		                        <td>$ <fmt:formatNumber value="${item.price * item.quantity}" type="number" groupingUsed="true"/></td>
		                    </tr>
		                </c:forEach>
		            </tbody>
		        </table>
	        </div>
	        
	    <h5 class="text-end mt-3">
		    運費：<strong>$ <fmt:formatNumber value="${order.shippingFee}" type="number" /></strong>
		</h5>   
	        
        <h5 class="mt-3 text-end">
		    總金額：
		    <strong>
		        NT$ <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/>
		    </strong>
		</h5>
        <div class="text-end mt-4">
		    <form method="get" action="${pageContext.request.contextPath}/payment/pay/${order.orderId}">
		        <button type="submit" class="btn btn-warning w-25 mb-2">結帳</button>
		    </form>
		    <a href="${pageContext.request.contextPath}/home" class="btn btn-custom w-25">回到首頁</a>
		</div>
    </div>
</div>
