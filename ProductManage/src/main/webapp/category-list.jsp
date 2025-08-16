<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>カテゴリリスト</title>
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
    </style>
</head>
<body>
    <h1>カテゴリリスト</h1>

    <c:if test="${not empty errorMessage}">
        <p class="error-message">${errorMessage}</p>
    </c:if>

    <c:choose>
        <c:when test="${not empty categoryList}">
            <table>
                <thead>
                    <tr>
                        <th>カテゴリID</th>
                        <th>カテゴリ名</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="category" items="${categoryList}">
                        <tr>
                            <td><c:out value="${category.categoryId}"/></td>
                            <td><c:out value="${category.categoryName}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>カテゴリ情報はありません。</p>
        </c:otherwise>
    </c:choose>
</body>
</html>