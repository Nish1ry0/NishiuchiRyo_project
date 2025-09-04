package model.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.entity.CategoryBean;
import model.entity.ProductBean;

public class ProductDAO {

	private static final Logger logger = Logger.getLogger(ProductDAO.class.getName());

	public boolean insertProduct(ProductBean product) throws SQLException {
		String sql = "INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?)";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, product.getProductName());
			pstmt.setBigDecimal(2, product.getPrice());
			pstmt.setInt(3, product.getStock());
			pstmt.setInt(4, product.getCategory().getCategoryId());
			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "商品の登録中にデータベースエラーが発生しました。", e);
			throw e;
		}
	}

	public List<ProductBean> findAllProducts() throws SQLException {
		List<ProductBean> productList = new ArrayList<>();
		String sql = "SELECT p.id, p.name, p.price, p.stock, c.id AS category_id, c.name AS category_name " +
				"FROM products p JOIN categories c ON p.category_id = c.id ORDER BY p.id";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				int productId = rs.getInt("id");
				String productName = rs.getString("name");
				BigDecimal price = rs.getBigDecimal("price");
				int stock = rs.getInt("stock");
				int categoryId = rs.getInt("category_id");
				String categoryName = rs.getString("category_name");
				CategoryBean category = new CategoryBean(categoryId, categoryName);
				ProductBean product = new ProductBean(productId, productName, price, stock, category);
				productList.add(product);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "商品リストの取得中にデータベースエラーが発生しました。", e);
			throw e;
		}
		return productList;
	}
}