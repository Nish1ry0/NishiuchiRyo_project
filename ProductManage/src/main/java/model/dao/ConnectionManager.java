package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	// データベース接続情報
	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/product_management";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "Ryo12240130";
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // MySQL 8.xの場合

	public static Connection getConnection() throws SQLException {
		Connection con = null;
		try {

			Class.forName(JDBC_DRIVER);
			// データベース接続の確立
			con = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			System.out.println("データベースに接続しました。");
		} catch (ClassNotFoundException e) {
			System.err.println("JDBCドライバが見つかりません: " + e.getMessage());
			throw new SQLException("JDBCドライバのロードに失敗しました。", e);
		} catch (SQLException e) {
			System.err.println("データベース接続エラー: " + e.getMessage());
			throw e;
		}
		return con;
	}

	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
				System.out.println("データベースコネクションをクローズしました。");
			} catch (SQLException e) {
				System.err.println("コネクションクローズエラー: " + e.getMessage());
			}
		}
	}
}