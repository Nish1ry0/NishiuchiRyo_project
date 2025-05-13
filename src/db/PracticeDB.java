package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PracticeDB {

    // MySQLの接続情報
    private static final String DB_URL = "jdbc:mysql://localhost:3306/product_management"; // データベース名を product_management に修正
    private static final String DB_USER = "your_username"; // 正しいユーザー名に修正 // ユーザー名を適宜変更
    private static final String DB_PASSWORD = "Ryo12240130"; // パスワードを適宜変更

    public static void main(String[] args) {
        // パート1：データベース接続
        Connection connection = connectDatabase();

        if (connection != null) {
            System.out.println("DB接続成功");

            // パート2：データの取得と表示
            retrieveAndDisplayProducts(connection);

            // 接続のクローズ
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("DB接続のクローズに失敗しました: " + e.getMessage());
            }
        } else {
            System.out.println("DB接続失敗");
        }
    }

    /**
     * データベースに接続するメソッド
     * @return Connection 接続オブジェクト、接続失敗時はnull
     */
    public static Connection connectDatabase() {
        Connection conn = null;
        try {
            // JDBCドライバのロード (MySQL Connector/Jを使用する場合)
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバが見つかりません: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("データベース接続に失敗しました: " + e.getMessage());
        }
        return conn;
    }

    /**
     * productsテーブルからデータを取得して表示するメソッド
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
                int productId = resultSet.getInt("id"); // "product_id" を "id" に修正
                String productName = resultSet.getString("name"); // "product_name" を "name" に修正
                int price = resultSet.getInt("price"); // price の型を int に合わせる
                // 必要に応じて他のカラムも取得

                System.out.println("ID: " + productId + ", 名前: " + productName + ", 価格: " + price);
            }

        } catch (SQLException e) {
            System.err.println("データ取得に失敗しました: " + e.getMessage());
        } finally {
            // ResultSetとStatementのリソース解放を忘れずに行う
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetのクローズに失敗しました: " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Statementのクローズに失敗しました: " + e.getMessage());
                }
            }
        }
    }
}