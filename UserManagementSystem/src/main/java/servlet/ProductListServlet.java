package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;
import model.entity.ProductBean;

@WebServlet("/productList")
public class ProductListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProductListServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ProductDAO productDAO = new ProductDAO();
		List<ProductBean> productList = null;
		String errorMessage = null;
		String successMessage = request.getParameter("message");

		try {
			productList = productDAO.findAllProducts();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "商品リストの取得中にデータベースエラーが発生しました。", e);
			errorMessage = "商品リストを取得できませんでした。管理者にお問い合わせください。";
		}

		request.setAttribute("productList", productList);
		if (errorMessage != null) {
			request.setAttribute("errorMessage", errorMessage);
		}
		if (successMessage != null) {
			request.setAttribute("successMessage", successMessage);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/product-list.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}