package servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.dao.CategoryDAO;
import model.dao.ProductDAO;
import model.entity.CategoryBean;
import model.entity.ProductBean;

@WebServlet("/productRegister")
public class ProductRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProductRegisterServlet.class.getName());

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryDAO categoryDAO = new CategoryDAO();
		try {
			List<CategoryBean> categoryList = categoryDAO.findAllCategories();
			request.setAttribute("categoryList", categoryList);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "カテゴリ情報の取得中にデータベースエラーが発生しました。", e);
			request.setAttribute("errorMessage", "カテゴリ情報を取得できませんでした。管理者にお問い合わせください。");
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/product-register.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		List<String> validationErrors = new ArrayList<>();
		String productName = request.getParameter("productName");
		String priceStr = request.getParameter("price");
		String stockStr = request.getParameter("stock");
		String categoryIdStr = request.getParameter("categoryId");

		if (productName == null || productName.trim().isEmpty()) {
			validationErrors.add("商品名は必須項目です。");
		}
		BigDecimal price = null;
		if (priceStr == null || priceStr.trim().isEmpty()) {
			validationErrors.add("価格は必須項目です。");
		} else {
			try {
				price = new BigDecimal(priceStr);
				if (price.compareTo(BigDecimal.ZERO) < 0) {
					validationErrors.add("価格は0以上で入力してください。");
				}
			} catch (NumberFormatException e) {
				validationErrors.add("価格が不正な形式です。");
			}
		}

		Integer stock = null;
		if (stockStr == null || stockStr.trim().isEmpty()) {
			validationErrors.add("在庫数は必須項目です。");
		} else {
			try {
				stock = Integer.parseInt(stockStr);
				if (stock < 0) {
					validationErrors.add("在庫数は0以上で入力してください。");
				}
			} catch (NumberFormatException e) {
				validationErrors.add("在庫数が不正な形式です。");
			}
		}

		Integer categoryId = null;
		if (categoryIdStr == null || categoryIdStr.isEmpty()) {
			validationErrors.add("カテゴリは必須項目です。");
		} else {
			try {
				categoryId = Integer.parseInt(categoryIdStr);
			} catch (NumberFormatException e) {
				validationErrors.add("カテゴリIDが不正です。");
			}
		}

		if (!validationErrors.isEmpty()) {
			request.setAttribute("validationErrors", validationErrors);
			request.setAttribute("productName", productName);
			request.setAttribute("price", priceStr);
			request.setAttribute("stock", stockStr);
			request.setAttribute("categoryId", categoryIdStr);
			doGet(request, response);
			return;
		}

		ProductDAO productDAO = new ProductDAO();
		try {
			CategoryBean category = new CategoryDAO().findCategoryById(categoryId);
			if (category == null) {
				request.setAttribute("errorMessage", "指定されたカテゴリIDは存在しません。");
				doGet(request, response);
				return;
			}

			ProductBean product = new ProductBean(0, productName, price, stock, category);
			boolean result = productDAO.insertProduct(product);

			if (result) {
				response.sendRedirect(request.getContextPath() + "/productList?message=" +
						java.net.URLEncoder.encode("商品が正常に登録されました。", "UTF-8"));
			} else {
				request.setAttribute("errorMessage", "商品の登録に失敗しました。");
				doGet(request, response);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "データベースエラーが発生しました。", e);
			request.setAttribute("errorMessage", "データベース接続に失敗しました。管理者にお問い合わせください。");
			doGet(request, response);
		}
	}
}