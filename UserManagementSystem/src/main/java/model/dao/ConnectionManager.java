package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionManager {

	private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());

	private static final String JDBC_URL = "jdbc:mysql://localhost:3306/product_management";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "Ryo12240130";
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

	public static Connection getConnection() throws SQLException {
		Connection con = null;
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
			logger.info("データベースに接続しました。");
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "JDBCドライバが見つかりません。", e);
			throw new SQLException("JDBCドライバのロードに失敗しました。", e);
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "データベース接続エラー。", e);
			throw e;
		}
		return con;
	}

	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
				logger.info("データベースコネクションをクローズしました。");
			} catch (SQLException e) {
				logger.log(Level.SEVERE, "コネクションクローズエラー。", e);
			}
		}
	}
}
