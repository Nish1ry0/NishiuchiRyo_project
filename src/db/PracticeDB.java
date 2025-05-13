package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PracticeDB {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/product_management"; // データベースURL
	private static final String DB_USER = "your_username"; // データベースユーザー名
	private static final String DB_PASSWORD = "Ryo12240130"; // データベースパスワード

	public static void main(String[] args) {
		Connection connection = connectDatabase();

		if (connection != null) {
			System.out.println("DB接続成功");
			retrieveAndDisplayProducts(connection);
			closeConnection(connection);
		} else {
			System.out.println("DB接続失敗");
		}
	}

	/**
	 * データベースに接続します。
	 * @return Connection 接続オブジェクト、失敗時はnull
	 */
	public static Connection connectDatabase() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // JDBCドライバのロード
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); // データベース接続
		} catch (ClassNotFoundException e) {
			System.err.println("JDBCドライバが見つかりません: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("データベース接続に失敗しました: " + e.getMessage());
		}
		return conn;
	}

	/**
	 * productsテーブルからデータを取得して表示します。
	 * @param connection データベース接続オブジェクト
	 */
	public static void retrieveAndDisplayProducts(Connection connection) {
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.createStatement();
			String sql = "SELECT * FROM products";
			resultSet = statement.executeQuery(sql);

			System.out.println("\nDB一覧情報取得");
			while (resultSet.next()) {
				int productId = resultSet.getInt("id");
				String productName = resultSet.getString("name");
				int price = resultSet.getInt("price");
				System.out.println("ID: " + productId + ", 名前: " + productName + ", 価格: " + price);
			}
		} catch (SQLException e) {
			System.err.println("データ取得に失敗しました: " + e.getMessage());
		} finally {
			closeResources(resultSet, statement);
		}
	}

	private static void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.err.println("DB接続のクローズに失敗しました: " + e.getMessage());
		}
	}

	private static void closeResources(ResultSet resultSet, Statement statement) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			System.err.println("ResultSetのクローズに失敗しました: " + e.getMessage());
		}
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			System.err.println("Statementのクローズに失敗しました: " + e.getMessage());
		}
	}
}