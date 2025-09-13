package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.ProductDAO;

@WebServlet("/productDelete")
public class ProductDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProductDeleteServlet.class.getName());

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String productIdStr = request.getParameter("productId");
		int productId = 0;
		String message = null;

		try {
			if (productIdStr != null && !productIdStr.isEmpty()) {
				productId = Integer.parseInt(productIdStr);
				ProductDAO productDAO = new ProductDAO();
				boolean isDeleted = productDAO.deleteProduct(productId);
				if (isDeleted) {
					message = "商品が正常に削除されました。";
				} else {
					message = "指定された商品は存在しません。";
				}
			} else {
				message = "商品IDが指定されていません。";
			}
		} catch (NumberFormatException e) {
			message = "商品IDの形式が不正です。";
			logger.log(Level.WARNING, "商品IDの形式が不正です。", e);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "商品の削除中にデータベースエラーが発生しました。", e);
			message = "商品の削除中にデータベースエラーが発生しました。管理者にお問い合わせください。";
		}

		String encodedMessage = java.net.URLEncoder.encode(message, "UTF-8");
		response.sendRedirect(request.getContextPath() + "/productList?message=" + encodedMessage);
	}
}