<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>商品登録</title>
<link
	href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
	rel="stylesheet">
</head>
<body
	class="bg-gray-100 min-h-screen flex flex-col items-center justify-center p-4">
	<div
		class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md md:max-w-lg">
		<h2 class="text-3xl font-bold mb-6 text-gray-800 text-center">商品登録</h2>

		<%-- エラーメッセージの表示 --%>
		<c:if test="${not empty errorMessage}">
			<div
				class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4"
				role="alert">
				<strong class="font-bold">エラー:</strong> <span
					class="block sm:inline">${errorMessage}</span>
			</div>
		</c:if>

		<%-- バリデーションエラーの表示 --%>
		<c:if test="${not empty validationErrors}">
			<div
				class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4"
				role="alert">
				<strong class="font-bold">入力エラー:</strong>
				<ul class="list-disc list-inside mt-2">
					<c:forEach var="error" items="${validationErrors}">
						<li><c:out value="${error}" /></li>
					</c:forEach>
				</ul>
			</div>
		</c:if>

		<form action="<%=request.getContextPath()%>/productRegister"
			method="post" class="space-y-6">
			<div class="mb-4">
				<label for="productName"
					class="block text-gray-700 text-sm font-bold mb-2">商品名:</label> <input
					type="text" id="productName" name="productName"
					value="${productName}"
					class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
					placeholder="商品名を入力" required>
			</div>
			<div class="mb-4">
				<label for="price"
					class="block text-gray-700 text-sm font-bold mb-2">価格:</label> <input
					type="text" id="price" name="price" value="${price}"
					class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
					placeholder="価格を入力" required>
			</div>
			<div class="mb-4">
				<label for="stock"
					class="block text-gray-700 text-sm font-bold mb-2">在庫数:</label> <input
					type="text" id="stock" name="stock" value="${stock}"
					class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
					placeholder="在庫数を入力" required>
			</div>
			<div class="mb-6">
				<label for="categoryId"
					class="block text-gray-700 text-sm font-bold mb-2">カテゴリ:</label>
				<div class="relative">
					<select id="categoryId" name="categoryId" required
						class="block appearance-none w-full bg-white border border-gray-400 hover:border-gray-500 px-4 py-2 pr-8 rounded shadow leading-tight focus:outline-none focus:ring-2 focus:ring-blue-500">
						<option value="">選択してください</option>
						<c:forEach var="category" items="${categoryList}">
							<option value="${category.categoryId}"
								<c:if test="${categoryId eq category.categoryId}">selected</c:if>>
								<c:out value="${category.categoryName}" />
							</option>
						</c:forEach>
					</select>
					<div
						class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700">
						<svg class="fill-current h-4 w-4"
							xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                            <path
								d="M9.293 12.95l-.707.707L13.707 18 18 13.707l-.707-.707L13 16.293 9.293 12.95z" /></svg>
					</div>
				</div>
			</div>
			<div class="flex items-center justify-between">
				<button type="submit"
					class="bg-blue-600 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline w-full">
					商品を登録</button>
			</div>
		</form>
		<div class="mt-6 text-center">
			<a href="<%=request.getContextPath()%>/productList"
				class="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800">
				商品一覧へ戻る </a>
		</div>
	</div>
</body>
</html>