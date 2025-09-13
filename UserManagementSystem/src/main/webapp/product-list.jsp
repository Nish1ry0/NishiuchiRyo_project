<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>商品リスト</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
}

table {
	width: 80%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: left;
}

th {
	background-color: #f2f2f2;
}

.error-message {
	color: red;
	font-weight: bold;
}

.success-message {
	color: green;
	font-weight: bold;
}

.register-link {
	margin-bottom: 20px;
}
</style>
<script>
	function confirmDelete(productId) {
		if (confirm("本当にこの商品を削除しますか？")) {
			window.location.href = "productDelete?id=" + productId;
		}
	}
</script>
</head>
<body>
	<h1>商品リスト</h1>

	<div class="register-link">
		<a href="<%=request.getContextPath()%>/productRegister">商品登録ページへ</a>
	</div>

	<c:if test="${not empty errorMessage}">
		<p class="error-message">${errorMessage}</p>
	</c:if>
	<c:if test="${not empty successMessage}">
		<p class="success-message">${successMessage}</p>
	</c:if>

	<c:choose>
		<c:when test="${not empty productList}">
			<table>
				<thead>
					<tr>
						<th>商品ID</th>
						<th>商品名</th>
						<th>価格</th>
						<th>在庫数</th>
						<th>カテゴリ</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="product" items="${productList}">
						<tr>
							<td><c:out value="${product.productId}" /></td>
							<td><c:out value="${product.productName}" /></td>
							<td><c:out value="${product.price}" /></td>
							<td><c:out value="${product.stock}" /></td>
							<td><c:out value="${product.category.categoryName}" /></td>
							<td><a
								href="productDeleteConfirm?id=<c:out value="${product.productId}"/>">削除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<p>商品が見つかりませんでした。</p>
		</c:otherwise>
	</c:choose>
	<p>
		<a href="<%=request.getContextPath()%>/welcome.jsp">トップページへ戻る</a>
	</p>
	<p>
		<a href="<%=request.getContextPath()%>/logout">ログアウト</a>
	</p>
</body>
</html>