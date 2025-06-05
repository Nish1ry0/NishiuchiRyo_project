package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import product.InputHandler;
import product.Product;

public class ProductDB {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/product_management";
	private static final String DB_USER = "your_username";
	private static final String DB_PASSWORD = "Ryo12240130";

	public static void main(String[] args) {
		try (Connection connection = connectDatabase()) {
			if (connection != null) {
				System.out.println("DB接続成功");
				runMenu(connection);
			} else {
				System.out.println("DB接続失敗");
			}
		} catch (SQLException e) {
			System.err.println("DBエラー: " + e.getMessage());
		} finally {
			InputHandler.closeScanner();
		}
	}

	public static Connection connectDatabase() throws SQLException {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			throw new SQLException("JDBCドライバが見つかりません: " + e.getMessage());
		}
	}

	public static void runMenu(Connection connection) throws SQLException {
		String choice;
		do {
			System.out.println("\n--- 商品管理メニュー (DB) ---");
			System.out.println("1. 商品登録");
			System.out.println("2. 商品情報取得（商品名から）");
			System.out.println("3. 商品検索（商品名で）");
			System.out.println("4. 全ての商品を表示");
			System.out.println("5. 商品削除（商品名で）");
			System.out.println("6. 商品情報更新");
			System.out.println("7. 複数商品の在庫更新（トランザクション）");
			System.out.println("0. 終了");
			System.out.print("操作を選択してください: ");
			choice = InputHandler.getStringInput("");

			switch (choice) {
			case "1":
				addProduct(connection);
				break;
			case "2":
				getProductByName(connection);
				break;
			case "3":
				searchProductsByName(connection);
				break;
			case "4":
				displayAllProducts(connection);
				break;
			case "5":
				removeProductByName(connection);
				break;
			case "6":
				updateProduct(connection);
				break;
			case "7":
				updateMultipleProductStocks(connection);
				break;
			case "0":
				System.out.println("プログラムを終了します。");
				break;
			default:
				System.out.println("無効な選択です。もう一度入力してください。");
			}
		} while (!choice.equals("0"));
	}

	private static void addProduct(Connection connection) throws SQLException {
		Product product = InputHandler.createProductFromInput();
		if (product != null) {

			String checkCategorySql = "SELECT COUNT(*) FROM categories WHERE id = ?";
			try (PreparedStatement checkStmt = connection.prepareStatement(checkCategorySql)) {
				checkStmt.setInt(1, product.getCategoryId());
				ResultSet rs = checkStmt.executeQuery();
				rs.next();
				if (rs.getInt(1) == 0) {
					System.out.println("エラー: 指定されたカテゴリIDは存在しません。");
					return;
				}
			}

			String sql = "INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?)";
			try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, product.getName());
				pstmt.setInt(2, product.getPrice());
				pstmt.setInt(3, product.getStock());
				pstmt.setInt(4, product.getCategoryId());
				int affectedRows = pstmt.executeUpdate();

				if (affectedRows > 0) {
					ResultSet generatedKeys = pstmt.getGeneratedKeys();
					if (generatedKeys.next()) {
						product.setId(generatedKeys.getInt(1));
					}
					System.out.println(product.getName() + " を登録しました。ID: " + product.getId());
				} else {
					System.out.println("商品の登録に失敗しました。");
				}
			}
		}
	}

	private static void getProductByName(Connection connection) throws SQLException {
		String name = InputHandler.getStringInput("取得したい商品名を入力してください: ");
		String sql = "SELECT p.id, p.name, p.price, p.stock, c.name AS category_name " +
				"FROM products p JOIN categories c ON p.category_id = c.id WHERE p.name = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, name);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					System.out.println("ID: " + rs.getInt("id") +
							", 商品名: " + rs.getString("name") +
							", 価格: " + rs.getInt("price") +
							", 在庫: " + rs.getInt("stock") +
							", カテゴリ: " + rs.getString("category_name"));
				} else {
					System.out.println("商品名 '" + name + "' は見つかりませんでした。");
				}
			}
		}
	}

	private static void searchProductsByName(Connection connection) throws SQLException {
		String keyword = InputHandler.getStringInput("検索したいキーワードを入力してください: ");
		String sql = "SELECT p.id, p.name, p.price, p.stock, c.name AS category_name " +
				"FROM products p JOIN categories c ON p.category_id = c.id WHERE p.name LIKE ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, "%" + keyword + "%");
			try (ResultSet rs = pstmt.executeQuery()) {
				boolean found = false;
				while (rs.next()) {
					System.out.println("ID: " + rs.getInt("id") +
							", 商品名: " + rs.getString("name") +
							", 価格: " + rs.getInt("price") +
							", 在庫: " + rs.getInt("stock") +
							", カテゴリ: " + rs.getString("category_name"));
					found = true;
				}
				if (!found) {
					System.out.println("キーワード '" + keyword + "' に一致する商品は見つかりませんでした。");
				}
			}
		}
	}

	private static void displayAllProducts(Connection connection) throws SQLException {
		String sql = "SELECT p.id, p.name, p.price, p.stock, c.name AS category_name " +
				"FROM products p JOIN categories c ON p.category_id = c.id";
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			System.out.println("\n--- 全ての商品 ---");
			boolean found = false;
			while (rs.next()) {
				System.out.println("ID: " + rs.getInt("id") +
						", 商品名: " + rs.getString("name") +
						", 価格: " + rs.getInt("price") +
						", 在庫: " + rs.getInt("stock") +
						", カテゴリ: " + rs.getString("category_name"));
				found = true;
			}
			if (!found) {
				System.out.println("商品が登録されていません。");
			}
		}
	}

	private static void removeProductByName(Connection connection) throws SQLException {
		String name = InputHandler.getStringInput("削除したい商品名を入力してください: ");

		String sql = "DELETE FROM products WHERE name = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, name);
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println(name + " を削除しました。");
			} else {
				System.out.println(name + " は見つかりませんでした。");
			}
		}
	}

	private static void updateProduct(Connection connection) throws SQLException {
		String name = InputHandler.getStringInput("更新したい商品名を入力してください: ");
		int newPrice = InputHandler.getIntInput("新しい価格を入力してください: ");
		int newStock = InputHandler.getIntInput("新しい在庫数を入力してください: ");
		int newCategoryId = InputHandler.getIntInput("新しいカテゴリIDを入力してください: ");

		String checkCategorySql = "SELECT COUNT(*) FROM categories WHERE id = ?";
		try (PreparedStatement checkStmt = connection.prepareStatement(checkCategorySql)) {
			checkStmt.setInt(1, newCategoryId);
			ResultSet rsCheck = checkStmt.executeQuery();
			rsCheck.next();
			int count = rsCheck.getInt(1);
			if (count == 0) {
				System.out.println("エラー: 指定されたカテゴリIDは存在しません。");
				return;
			}
		}

		String updateSql = "UPDATE products SET price = ?, stock = ?, category_id = ? WHERE name = ?";
		try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
			updateStmt.setInt(1, newPrice);
			updateStmt.setInt(2, newStock);
			updateStmt.setInt(3, newCategoryId);
			updateStmt.setString(4, name);
			int affectedRows = updateStmt.executeUpdate();

			if (affectedRows > 0) {
				System.out.println(name + " の情報を更新しました。");
			} else {
				System.out.println("商品の更新に失敗しました。商品名が正しいか確認してください。");
			}
		}
	}

	private static void updateMultipleProductStocks(Connection connection) throws SQLException {
		Map<Integer, Integer> productUpdates = new HashMap<>();
		System.out.println("\n--- 複数商品の在庫更新（トランザクション） ---");
		System.out.println("更新する商品情報を入力してください（終了する場合は商品IDに0を入力）。");

		while (true) {
			int productId = InputHandler.getIntInput("商品ID (0で終了): ");
			if (productId == 0) {
				break;
			}
			int newStock = InputHandler.getIntInput("新しい在庫数: ");
			productUpdates.put(productId, newStock);
		}

		if (productUpdates.isEmpty()) {
			System.out.println("更新する商品がありません。");
			return;
		}

		try {
			connection.setAutoCommit(false);

			String updateSql = "UPDATE products SET stock = ? WHERE id = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(updateSql)) {
				for (Map.Entry<Integer, Integer> entry : productUpdates.entrySet()) {
					int productId = entry.getKey();
					int newStock = entry.getValue();

					// 商品IDの存在チェック
					String checkProductSql = "SELECT COUNT(*) FROM products WHERE id = ?";
					try (PreparedStatement checkStmt = connection.prepareStatement(checkProductSql)) {
						checkStmt.setInt(1, productId);
						ResultSet rsCheck = checkStmt.executeQuery();
						rsCheck.next();
						if (rsCheck.getInt(1) == 0) {
							throw new SQLException("エラー: 商品ID " + productId + " は存在しません。トランザクションをロールバックします。");
						}
					}

					pstmt.setInt(1, newStock);
					pstmt.setInt(2, productId);
					pstmt.addBatch();
				}

				int[] affectedRows = pstmt.executeBatch();

				boolean allSuccess = true;
				for (int rows : affectedRows) {
					if (rows <= 0) {
						allSuccess = false;
						break;
					}
				}

				if (allSuccess) {
					connection.commit();
					System.out.println("トランザクション成功: 複数の商品の在庫を更新しました。");
				} else {
					connection.rollback();
					System.out.println("トランザクション失敗: 商品の在庫更新中に一部失敗しました。全ての変更が取り消されました。");
				}

			}
		} catch (SQLException e) {
			connection.rollback();
			System.err.println("トランザクション失敗: " + e.getMessage());
		} finally {
			connection.setAutoCommit(true);
		}
	}

	private static boolean categoryIdExists(Connection connection, int categoryId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM categories WHERE id = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, categoryId);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}
}