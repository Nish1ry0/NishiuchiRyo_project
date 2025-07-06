<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリ登録</title>
<style>
.error {
	color: red;
	font-weight: bold;
}

table {
	border-collapse: collapse;
	width: 30%;
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
</style>
</head>
<body>
	<h1>カテゴリ登録</h1>

	<%-- エラーメッセージの表示 --%>
	<%
	if (request.getAttribute("errorMessage") != null) {
	%>
	<p class="error"><%=request.getAttribute("errorMessage")%></p>
	<%
	}
	%>
	<%
	if (request.getAttribute("validationErrors") != null) {
	%>
	<%-- バリデーションエラーがリストで渡されることを想定 --%>
	<ul>
		<%
		for (String error : (java.util.List<String>) request.getAttribute("validationErrors")) {
		%>
		<li class="error"><%=error%></li>
		<%
		}
		%>
	</ul>
	<%
	}
	%>
	<%
	if (request.getAttribute("successMessage") != null) {
	%>
	<p style="color: green; font-weight: bold;"><%=request.getAttribute("successMessage")%></p>
	<%
	}
	%>

	<form action="<%=request.getContextPath()%>/categoryRegister"
		method="post">
		<table>
			<tr>
				<th>カテゴリID:</th>
				<td><input type="number" name="categoryId"
					value="<%=request.getParameter("categoryId") != null ? request.getParameter("categoryId") : ""%>"
					required> <%-- HTML5のrequired属性で必須チェック（ブラウザ側） --%></td>
			</tr>
			<tr>
				<th>カテゴリ名:</th>
				<td><input type="text" name="categoryName"
					value="<%=request.getParameter("categoryName") != null ? request.getParameter("categoryName") : ""%>"
					required></td>
			</tr>
		</table>
		<br> <input type="submit" value="登録">
	</form>

	<br>
	<a href="<%=request.getContextPath()%>/categoryList">カテゴリ一覧へ戻る</a>

</body>
</html>