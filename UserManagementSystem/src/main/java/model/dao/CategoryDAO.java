package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.entity.CategoryBean;

public class CategoryDAO {

	private static final Logger logger = Logger.getLogger(CategoryDAO.class.getName());

	public List<CategoryBean> findAllCategories() throws SQLException {
		List<CategoryBean> categoryList = new ArrayList<>();
		String sql = "SELECT id, name FROM categories ORDER BY id";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				int categoryId = rs.getInt("id");
				String categoryName = rs.getString("name");
				CategoryBean category = new CategoryBean(categoryId, categoryName);
				categoryList.add(category);
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "カテゴリリストの取得中にデータベースエラーが発生しました。", e);
			throw e;
		}
		return categoryList;
	}

	public CategoryBean findCategoryById(int categoryId) throws SQLException {
		String sql = "SELECT id, name FROM categories WHERE id = ?";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, categoryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new CategoryBean(rs.getInt("id"), rs.getString("name"));
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "カテゴリIDによるカテゴリ情報の取得中にデータベースエラーが発生しました。", e);
			throw e;
		}
		return null;
	}
}