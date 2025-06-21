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

    .btn-ship {
        background-color: #e2b77e;
        border: none;
        color: #fff;
    }

    .btn-ship:hover {
        background-color: #d19b60;
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
    
    #orderTable {
		border-radius: 0.75rem;
		overflow: hidden;
		box-shadow: 0 4px 8px rgba(0,0,0,0.1);
	}
	
</style>

<div class="container mt-5">
    <h2 class="fw-bold" style="color: #4b3621;">訂單管理</h2>

    <table id="orderTable" class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>訂單編號</th>
                <th>會員帳號</th>
                <th>總金額</th>
                <th>建立訂單時間</th>
                <th>結帳時間</th>
                <th>狀態</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.orderId}</td>
                    <td>${order.username}</td>
                    <td>NT$ <fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true"/></td>
                    <td>${order.formattedOrderDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${order.paidAt != null}">
                                ${order.formattedPaidAt}
                            </c:when>
                            <c:otherwise>尚未結帳</c:otherwise>
                        </c:choose>
                    </td>
                    <td class="status-${order.status.toString().toLowerCase()}">
                        ${order.status.displayName}
                    </td>
                    <td>
                        <a class="btn btn-sm btn-outline-primary"
                           href="${pageContext.request.contextPath}/admin/orders/detail/${order.orderId}">
                           查看明細
                        </a>
                        <c:if test="${order.status == 'PAID'}">
                            <span class="badge bg-warning text -darl">待出貨</span>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<div style="height: 100px;"></div>

<script>
	$(document).ready(function () {
	    $('#orderTable').DataTable({
	        language: {
	            url: '${pageContext.request.contextPath}/static/datatables/zh-HANT.json'
	        },
	        order: [[3, 'desc']], // 以第 4 欄位，下單時間開始排序
	        dom: 'Bfrtip', // 顯示 Buttons
	        buttons: [
	            {
	                extend: 'excelHtml5',
	                text: '匯出Excel',
	                className: 'btn btn-success',
	                title: '老式美好舊貨店訂單列表'
	            }
	        ]
	    });
	});
</script>