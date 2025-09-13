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
		// SQL INSERT statement
		String sql = "INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?)";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, product.getProductName());
			pstmt.setBigDecimal(2, product.getPrice());
			pstmt.setInt(3, product.getStock());
			pstmt.setInt(4, product.getCategory().getCategoryId());

			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				logger.info("Product \"" + product.getProductName() + "\" has been registered.");
				return true;
			}

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Database error occurred during product registration.", e);
			throw e;
		}
		return false;
	}

	public List<ProductBean> findAllProducts() throws SQLException {
		List<ProductBean> productList = new ArrayList<>();

		String sql = "SELECT p.id, p.name, p.price, p.stock, c.id AS category_id, c.name AS category_name " +
				"FROM products p JOIN categories c ON p.category_id = c.id " +
				"ORDER BY p.id";

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
			logger.log(Level.SEVERE, "Database error occurred while fetching the product list.", e);
			throw e;
		}
		return productList;
	}

	public ProductBean findProductById(int productId) throws SQLException {
		String sql = "SELECT p.id, p.name, p.price, p.stock, c.id AS category_id, c.name AS category_name " +
				"FROM products p JOIN categories c ON p.category_id = c.id " +
				"WHERE p.id = ?";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, productId);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					int pId = rs.getInt("id");
					String productName = rs.getString("name");
					BigDecimal price = rs.getBigDecimal("price");
					int stock = rs.getInt("stock");
					int categoryId = rs.getInt("category_id");
					String categoryName = rs.getString("category_name");

					CategoryBean category = new CategoryBean(categoryId, categoryName);
					return new ProductBean(pId, productName, price, stock, category);
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Database error occurred while fetching product by ID.", e);
			throw e;
		}
		return null;
	}

	public boolean deleteProduct(int productId) throws SQLException {
		String sql = "DELETE FROM products WHERE id = ?";

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setInt(1, productId);

			int rowsAffected = pstmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Database error occurred during product deletion.", e);
			throw e;
		}
	}
}