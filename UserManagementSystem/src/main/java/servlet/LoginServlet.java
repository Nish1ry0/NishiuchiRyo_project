package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.dao.UserDAO;
import model.entity.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UserDAO userDAO = new UserDAO();
		User user = null;
		String errorMessage = null;

		try {
			// データベースからユーザーを検索
			user = userDAO.findUser(username, password);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "データベースエラーが発生しました。", e);
			errorMessage = "データベースに接続できませんでした。管理者にお問い合わせください。";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;
		}

		if (user != null) {
			// 認証成功
			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);

			response.sendRedirect(request.getContextPath() + "/welcome.jsp");
		} else {
			// 認証失敗
			errorMessage = "ユーザー名またはパスワードが正しくありません。";
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
}
