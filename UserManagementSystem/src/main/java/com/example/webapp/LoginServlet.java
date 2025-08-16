package com.example.webapp;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if ("test".equals(username) && "pass".equals(password)) {
			// 認証成功
			HttpSession session = request.getSession(true);
			session.setAttribute("user", username);

			response.sendRedirect(request.getContextPath() + "/welcome.jsp");
		} else {
			// 認証失敗
			request.setAttribute("errorMessage", "ユーザー名またはパスワードが正しくありません。");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}
