<%@ page contentType="text/html;charset=UTF-8" %>
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

    div.dt-buttons {
        float: left;
        margin-bottom: 1rem;
    }
    
    .btn-memberList {
	    background-color: #e2b77e;
	    color: #2d1b00;
	    border: none;
    }

    .btn-memberList:hover {
        background-color: #d19b60;
        color: white;
    }
    
    #memberTable {
		border-radius: 0.75rem;
		overflow: hidden;
		box-shadow: 0 4px 8px rgba(0,0,0,0.1);
	}
	
</style>

<div class="container mt-5">
    <h2 class="fw-bold" style="color: #4b3621;">會員管理</h2>
    <a href="${pageContext.request.contextPath}/admin/members/create" class="btn btn-memberList mb-3">新增會員</a>

    <table id="memberTable" class="table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>會員編號</th>
                <th>帳號</th>
                <th>Email</th>
                <th>啟用</th>
                <th>註冊時間</th>
                <th>身份</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.userId}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>
                        <c:choose>
                            <c:when test="${user.active}">已啟用</c:when>
                            <c:otherwise>未啟用</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${user.formattedCreatedAt}</td>
                    <td>${user.role}</td>
                    <td>
                        <c:if test="${user.username != 'MasterAdmin'}">
                            <!-- 切換身份 -->
                            <form method="post" action="${pageContext.request.contextPath}/admin/members/${user.userId}/role/${user.role == 'USER' ? 'ADMIN' : 'USER'}" style="display:inline;">
                                <input type="hidden" name="_method" value="put" />
                                <button class="btn btn-secondary btn-sm">
                                    切換為 ${user.role == 'USER' ? 'ADMIN' : 'USER'}
                                </button>
                            </form>

                            <!-- 編輯 -->
                            <a href="${pageContext.request.contextPath}/admin/members/${user.userId}" class="btn btn-warning btn-sm">編輯</a>

                            <!-- 刪除 -->
                            <form method="post" action="${pageContext.request.contextPath}/admin/members/${user.userId}" style="display:inline;" onsubmit="return confirm('確認要刪除這位會員嗎？')">
                                <input type="hidden" name="_method" value="delete" />
                                <button class="btn btn-danger btn-sm">刪除</button>
                            </form>
                        </c:if>
                        <c:if test="${user.username == 'masterAdmin'}">
                            <span class="badge bg-secondary">保護中</span>
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
        $('#memberTable').DataTable({
            dom: 'Bfrtip',
            buttons: [
                {
                    extend: 'excelHtml5',
                    text: '匯出Excel',
                    className: 'btn btn-success',
                    title: '老式美好舊貨店會員列表'
                }
            ],
            language: {
                url: '${pageContext.request.contextPath}/static/datatables/zh-HANT.json'
            }
        });
    });
</script>