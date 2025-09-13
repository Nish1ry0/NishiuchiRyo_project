<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>商品削除確認</title>
<style>
body {
	font-family: Arial, sans-serif;
	margin: 20px;
	text-align: center;
}

.confirm-box {
	border: 1px solid #ccc;
	padding: 20px;
	display: inline-block;
	margin-top: 50px;
}

.product-details {
	text-align: left;
	margin-bottom: 20px;
}

.product-details p {
	margin: 5px 0;
}

.buttons button {
	margin: 0 10px;
	padding: 10px 20px;
	cursor: pointer;
}

.delete-btn {
	background-color: #dc3545;
	color: white;
	border: none;
}

.cancel-btn {
	background-color: #6c757d;
	color: white;
	border: none;
}
</style>
</head>
<body>
	<div class="confirm-box">
		<h1>商品削除確認</h1>
		<p>本当に以下の商品を削除しますか？</p>
		<div class="product-details">
			<p>
				<strong>商品ID:</strong>
				<c:out value="${product.productId}" />
			</p>
			<p>
				<strong>商品名:</strong>
				<c:out value="${product.productName}" />
			</p>
			<p>
				<strong>価格:</strong>
				<c:out value="${product.price}" />
			</p>
			<p>
				<strong>在庫数:</strong>
				<c:out value="${product.stock}" />
			</p>
			<p>
				<strong>カテゴリ:</strong>
				<c:out value="${product.category.categoryName}" />
			</p>
		</div>

		<form action="<%=request.getContextPath()%>/productDelete"
			method="post" class="buttons">
			<input type="hidden" name="productId" value="${product.productId}">
			<button type="submit" class="delete-btn">削除確定</button>
			<a href="<%=request.getContextPath()%>/productList"
				class="cancel-btn">キャンセル</a>
		</form>
	</div>
</body>
</html>