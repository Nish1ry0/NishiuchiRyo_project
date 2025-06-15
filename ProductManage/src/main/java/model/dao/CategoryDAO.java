package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.entity.CategoryBean;

public class CategoryDAO {

	public List<CategoryBean> findAllCategories() throws SQLException {
		List<CategoryBean> categoryList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT id, name FROM categories ORDER BY id";

		try {
			con = ConnectionManager.getConnection(); // コネクションの取得
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int categoryId = rs.getInt("id");
				String categoryName = rs.getString("name");
				CategoryBean category = new CategoryBean(categoryId, categoryName);
				categoryList.add(category);
			}
		} finally {

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.err.println("ResultSetのクローズエラー: " + e.getMessage());
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					System.err.println("PreparedStatementのクローズエラー: " + e.getMessage());
				}
			}
			ConnectionManager.closeConnection(con); // コネクションのクローズ
		}
		return categoryList;
	}
}