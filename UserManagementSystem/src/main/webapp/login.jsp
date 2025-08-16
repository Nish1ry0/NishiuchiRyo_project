<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ログイン</title>
<link
	href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
	rel="stylesheet">
</head>
<body class="bg-gray-100 min-h-screen flex items-center justify-center">
	<div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
		<h2 class="text-2xl font-bold mb-6 text-center text-gray-800">ログイン</h2>

		<!-- エラーメッセージの表示 -->
		<%
		String errorMessage = (String) request.getAttribute("errorMessage");
		if (errorMessage != null) {
		%>
		<div class="bg-red-100 text-red-700 p-3 rounded mb-4 text-center">
			<%=errorMessage%>
		</div>
		<%
		}
		%>

		<form action="<%=request.getContextPath()%>/login" method="post"
			class="space-y-4">
			<div>
				<label for="username" class="block text-gray-700 font-medium">ユーザー名</label>
				<input type="text" id="username" name="username" required
					class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm">
			</div>
			<div>
				<label for="password" class="block text-gray-700 font-medium">パスワード</label>
				<input type="password" id="password" name="password" required
					class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm">
			</div>
			<div class="flex items-center justify-between">
				<button type="submit"
					class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 font-semibold">
					ログイン</button>
			</div>
		</form>
	</div>
</body>
</html>
