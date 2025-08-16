package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level; // Loggerのために追加
import java.util.logging.Logger; // Loggerのために追加

import model.entity.CategoryBean;

/**
 * カテゴリ情報のデータベース操作を行うDAOクラス
 */
public class CategoryDAO {

	// ロガーを追加
	private static final Logger logger = Logger.getLogger(CategoryDAO.class.getName());

	/**
	 * 全てのカテゴリ情報を取得します。
	 * @return 全てのカテゴリ情報を含むCategoryBeanのリスト
	 * @throws SQLException データベースアクセスエラーが発生した場合
	 */
	public List<CategoryBean> findAllCategories() throws SQLException {
		List<CategoryBean> categoryList = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// SQL文：idとnameカラムを取得し、idでソート
		String sql = "SELECT id, name FROM categories ORDER BY id";

		try {
			con = ConnectionManager.getConnection(); // コネクションの取得
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int categoryId = rs.getInt("id"); // データベースのカラム名 'id' に合わせる
				String categoryName = rs.getString("name"); // データベースのカラム名 'name' に合わせる
				CategoryBean category = new CategoryBean(categoryId, categoryName);
				categoryList.add(category);
			}
		} finally {
			// リソースの解放
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "ResultSetのクローズエラー: ", e); // ロガーを使用
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "PreparedStatementのクローズエラー: ", e); // ロガーを使用
				}
			}
			ConnectionManager.closeConnection(con); // コネクションのクローズ
		}
		return categoryList;
	}

	/**
	 * 新しいカテゴリ情報をデータベースに保存します。
	 * @param category 登録するカテゴリ情報を含むCategoryBeanオブジェクト
	 * @return 登録が成功した場合はtrue、失敗した場合はfalse
	 * @throws SQLException データベースアクセスエラーが発生した場合
	 */
	public boolean insertCategory(CategoryBean category) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		// SQL INSERT文
		// categoriesテーブルのidとnameカラムに値を挿入
		String sql = "INSERT INTO categories (id, name) VALUES (?, ?)";

		try {
			con = ConnectionManager.getConnection(); // コネクションの取得
			pstmt = con.prepareStatement(sql);

			// プレースホルダに値をセット
			pstmt.setInt(1, category.getCategoryId());
			pstmt.setString(2, category.getCategoryName());

			// SQLを実行し、更新された行数を確認
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				result = true; // 1行以上更新されれば成功
				logger.info("カテゴリID: " + category.getCategoryId() + " を登録しました。");
			}

		} finally {
			// リソースの解放
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "PreparedStatementのクローズエラー: ", e);
				}
			}
			ConnectionManager.closeConnection(con); // コネクションのクローズ
		}
		return result;
	}
}