package product;

import java.sql.Connection;
import java.sql.SQLException;

import db.ProductDB;

public class Main {
	public static void main(String[] args) {

		try (Connection connection = ProductDB.connectDatabase()) {
			if (connection == null) {
				System.out.println("DB接続に失敗しました。");
				return;
			}

			ProductDB.runMenu(connection);
		} catch (SQLException e) {
			System.err.println("アプリケーションエラー: " + e.getMessage());
		} finally {

		}
	}
}