package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.entity.User;

public class UserDAO {

	private static final Logger logger = Logger.getLogger(UserDAO.class.getName());

	/**
	 * 指定されたユーザー名とパスワードでユーザーを検索
	 * @param username ユーザー名
	 * @param password パスワード
	 * @return 認証に成功した場合はUserオブジェクト、失敗した場合はnull
	 * @throws SQLException データベースエラーが発生した場合
	 */
	public User findUser(String username, String password) throws SQLException {
		User user = null;

		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement pstmt = con
						.prepareStatement("SELECT username FROM users WHERE username = ? AND password = ?")) {

			pstmt.setString(1, username);
			pstmt.setString(2, password);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					user = new User(rs.getString("username"));
					logger.info("ユーザーが見つかりました: " + user.getUsername());
				} else {
					logger.info("指定されたユーザーは見つかりませんでした。");
				}
			}

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "データベース操作中にエラーが発生しました。", e);
			throw e;
		} finally {

		}

		return user;
	}
}
