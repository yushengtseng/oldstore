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

    div.dt-buttons {
        float: left;
        margin-bottom: 1rem;
    }
    
    th {
	    white-space: nowrap !important;
	    writing-mode: horizontal-tb !important;
	    text-align: center;
	    vertical-align: middle;
	}
	
	th.price-column,
	td.price-column {
	    width: 100px;
	    white-space: nowrap;
	    text-align: left;
	}
	
    .btn-productList {
	    background-color: #e2b77e;
	    color: #2d1b00;
	    border: none;
    }

    .btn-productList:hover {
        background-color: #d19b60;
        color: white;
    }
	
	#productTable {
		border-radius: 0.75rem;
		overflow: hidden;
		box-shadow: 0 4px 8px rgba(0,0,0,0.1);
	}
	
</style>

<div class="container mt-5">
    <h2 class="fw-bold" style="color: #4b3621;">商品管理</h2>
    
    <div class="mb-3">
        <a href="${pageContext.request.contextPath}/admin/products/create" class="btn btn-productList">新增商品</a>
    </div>

    <table id="productTable" class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>商品編號</th>
                <th>名稱</th>
                <th class="price-column">單價</th>
                <th>庫存</th>
                <th>描述</th>
                <th>圖片</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${productDtos}">
                <tr>
                    <td>${p.productId}</td>
                    <td>${p.name}</td>
                    <td class="price-column">$&nbsp;<fmt:formatNumber value="${p.price}" type="number"/></td>
                    <td>${p.stock}</td>
                    <td>${p.description}</td>
                    <td>
                        <img src="${pageContext.request.contextPath}/${p.imagePath}" width="80" height="80" />
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/products/${p.productId}" class="btn btn-warning btn-sm">編輯</a>
                        <form action="${pageContext.request.contextPath}/admin/products/${p.productId}" method="post" style="display:inline;">
                            <input type="hidden" name="_method" value="delete" />
                            <button type="submit" class="btn btn-danger btn-sm">刪除</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<div style="height: 100px;"></div>

<script>
    $(document).ready(function () {
    	$('#productTable').DataTable({
            dom: 'Bfrtip',
            buttons: [
                {
                    extend: 'excelHtml5',
                    text: '匯出Excel',
                    className: 'btn btn-success',
                    title: '老式美好舊貨店商品列表'
                }
            ],
            language: {
                url: '${pageContext.request.contextPath}/static/datatables/zh-HANT.json'
            }
        });
    });
</script>