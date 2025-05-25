package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	private static void runMenu(Connection connection) throws SQLException {
		String choice;
		do {
			System.out.println("\n--- 商品管理メニュー (DB) ---");
			System.out.println("1. 商品登録");
			System.out.println("2. 商品情報取得（商品名から）");
			System.out.println("3. 商品検索（商品名で）");
			System.out.println("4. 全ての商品を表示");
			System.out.println("5. 商品削除（商品名で）");
			System.out.println("6. 商品情報更新");
			System.out.println("0. 終了");
			System.out.print("操作を選択してください: ");
			choice = InputHandler.getStringInput("操作を選択してください: ");

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
			case "0":
				System.out.println("プログラムを終了します。");
				break;
			default:
				System.out.println("無効な選択です。もう一度入力してください。");
			}
		} while (!choice.equals("0"));
	}

	private static void addProduct(Connection connection) throws SQLException {
		Product newProduct = InputHandler.createProductFromInput();

		String checkSql = "SELECT COUNT(*) FROM categories WHERE id = ?";
		try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
			checkStmt.setInt(1, newProduct.getCategoryId());
			ResultSet rs = checkStmt.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			if (count == 0) {
				System.out.println("エラー: 指定されたカテゴリIDは存在しません。");
				return;
			}
		}

		String sql = "INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, newProduct.getName());
			pstmt.setInt(2, newProduct.getPrice());
			pstmt.setInt(3, newProduct.getStock());
			pstmt.setInt(4, newProduct.getCategoryId());
			pstmt.executeUpdate();

			ResultSet generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				newProduct.setId(id);
			}
			System.out.println(newProduct.getName() + " を登録しました。");
		} catch (SQLException e) {
			System.err.println("DBエラー: " + e.getMessage());
		}
	}

	private static void getProductByName(Connection connection) throws SQLException {
		String name = InputHandler.getStringInput("取得したい商品名を入力してください: ");

		String sql = "SELECT id, name, price, stock, category_id FROM products WHERE name = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price"),
						rs.getInt("stock"), rs.getInt("category_id"));
				System.out.println(product);
			} else {
				System.out.println("名前 '" + name + "' の商品は見つかりませんでした。");
			}
		} catch (SQLException e) {
			System.err.println("DBエラー: " + e.getMessage());
		}
	}

	private static void searchProductsByName(Connection connection) throws SQLException {
		String keyword = InputHandler.getStringInput("検索したいキーワードを入力してください: ");

		String sql = "SELECT id, name, price, stock, category_id FROM products WHERE name LIKE ?";
		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();

			List<Product> results = new ArrayList<>();
			while (rs.next()) {
				results.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price"),
						rs.getInt("stock"), rs.getInt("category_id")));
			}

			if (results.isEmpty()) {
				System.out.println("キーワード '" + keyword + "' を含む商品は見つかりませんでした。");
			} else {
				for (Product product : results) {
					System.out.println(product);
				}
			}
		} catch (SQLException e) {
			System.err.println("DBエラー: " + e.getMessage());
		}
	}

	private static void displayAllProducts(Connection connection) throws SQLException {
		String sql = "SELECT id, name, price, stock, category_id FROM products";
		try (Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			System.out.println("\n--- 商品一覧 ---");
			while (rs.next()) {
				Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getInt("price"),
						rs.getInt("stock"), rs.getInt("category_id"));
				System.out.println(product);
			}
			System.out.println("----------------");
		} catch (SQLException e) {
			System.err.println("DBエラー: " + e.getMessage());
		}
	}

	private static void updateProduct(Connection connection) throws SQLException {
		String name = InputHandler.getStringInput("更新する商品名を入力してください: ");

		String selectSql = "SELECT id, name, price, stock, category_id FROM products WHERE name = ?";
		try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
			selectStmt.setString(1, name);
			ResultSet rs = selectStmt.executeQuery();

			if (!rs.next()) {
				System.out.println("商品名 '" + name + "' は見つかりませんでした。");
				return;
			}

			int newPrice = InputHandler.getIntInput("新しい価格を入力してください: ");
			int newStock = InputHandler.getIntInput("新しい在庫数を入力してください: ");
			int newCategoryId = InputHandler.getIntInput("新しいカテゴリIDを入力してください: ");

			String checkSql = "SELECT COUNT(*) FROM categories WHERE id = ?";
			try (PreparedStatement checkStmt = connection.prepareStatement(checkSql)) {
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
					System.out.println("商品の更新に失敗しました。");
				}
			} catch (SQLException e) {
				System.err.println("DBエラー: " + e.getMessage());
			}
		} catch (SQLException e) {
			System.err.println("DBエラー: " + e.getMessage());
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
		} catch (SQLException e) {
			System.err.println("DBエラー: " + e.getMessage());
		}
	}
}