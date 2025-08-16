package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.entity.CategoryBean;

@WebServlet("/categoryList") // マッピングURL
public class CategoryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CategoryListServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryDAO categoryDAO = new CategoryDAO();
		List<CategoryBean> categoryList = null;
		String errorMessage = null;

		try {
			categoryList = categoryDAO.findAllCategories();
			request.setAttribute("categoryList", categoryList);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "データベースエラーが発生しました。", e);
			errorMessage = "データベースに接続できませんでした。管理者にお問い合わせください。";
			request.setAttribute("errorMessage", errorMessage);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/category-list.jsp");
		dispatcher.forward(request, response);
	}
}