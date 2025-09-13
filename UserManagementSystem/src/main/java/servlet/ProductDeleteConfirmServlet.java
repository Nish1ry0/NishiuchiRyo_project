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
import model.dao.ProductDAO;
import model.entity.ProductBean;

@WebServlet("/productDeleteConfirm")
public class ProductDeleteConfirmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProductDeleteConfirmServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String productIdStr = request.getParameter("id");
		int productId = 0;
		String errorMessage = null;

		try {
			if (productIdStr != null && !productIdStr.isEmpty()) {
				productId = Integer.parseInt(productIdStr);
			} else {
				errorMessage = "商品IDが指定されていません。";
			}
		} catch (NumberFormatException e) {
			errorMessage = "商品IDの形式が不正です。";
			logger.log(Level.WARNING, "商品IDの形式が不正です。", e);
		}

		if (errorMessage == null) {
			ProductDAO productDAO = new ProductDAO();
			try {
				ProductBean product = productDAO.findProductById(productId);
				if (product != null) {
					request.setAttribute("product", product);
				} else {
					errorMessage = "指定された商品は存在しません。";
				}
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "データベースエラーが発生しました。", e);
				errorMessage = "データベースに接続できませんでした。管理者にお問い合わせください。";
			}
		}

		if (errorMessage != null) {
			request.setAttribute("errorMessage", errorMessage);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/productList");
			dispatcher.forward(request, response);
			return;
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/product-delete-confirm.jsp");
		dispatcher.forward(request, response);
	}
}