<!-- webapp/welcome.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.entity.User"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ようこそ</title>
<link
	href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css"
	rel="stylesheet">
</head>
<body
	class="bg-gray-100 min-h-screen flex flex-col items-center justify-center">
	<%-- セッションからユーザー情報を取得 --%>
	<%
	User user = (User) session.getAttribute("user");
	%>

	<div
		class="bg-white p-8 rounded-lg shadow-lg text-center w-full max-w-md">
		<h2 class="text-3xl font-bold mb-4 text-gray-800">
			<%
			if (user != null) {
			%>
			ようこそ、<%=user.getUsername()%>さん！
			<%
			} else {
			%>
			ようこそ！
			<%
			}
			%>
		</h2>
		<p class="text-gray-600 mb-6">ログインに成功しました。</p>
		<form action="<%=request.getContextPath()%>/logout" method="get">
			<button type="submit"
				class="bg-red-600 text-white py-2 px-6 rounded-md hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 font-semibold">
				ログアウト</button>
		</form>
	</div>
</body>
</html>
