package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.CategoryDAO;
import model.entity.CategoryBean;

/**
 * カテゴリ登録を処理するサーブレット
 */
@WebServlet("/categoryRegister")
public class CategoryRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CategoryRegisterServlet.class.getName());

	/**
	 * GETリクエストを処理します。カテゴリ登録フォームを表示します。
	 * 通常、フォーム表示はGETで行われます。
	 * @param request HttpServletRequestオブジェクト
	 * @param response HttpServletResponseオブジェクト
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力例外が発生した場合
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/category-register.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * POSTリクエストを処理します。フォームからのカテゴリ情報をデータベースに保存します。
	 * @param request HttpServletRequestオブジェクト
	 * @param response HttpServletResponseオブジェクト
	 * @throws ServletException サーブレット例外が発生した場合
	 * @throws IOException 入出力例外が発生した場合
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // リクエストのエンコーディングを設定

		String categoryIdStr = request.getParameter("categoryId");
		String categoryName = request.getParameter("categoryName");

		List<String> validationErrors = new ArrayList<>(); // バリデーションエラーを格納するリスト

		// --- サーバーサイドバリデーション ---
		// 1. 必須項目チェック
		if (categoryIdStr == null || categoryIdStr.isEmpty()) {
			validationErrors.add("カテゴリIDは必須項目です。");
		}
		if (categoryName == null || categoryName.isEmpty()) {
			validationErrors.add("カテゴリ名は必須項目です。");
		}

		// 2. カテゴリIDの数値チェック
		int categoryId = 0;
		if (validationErrors.isEmpty() && categoryIdStr != null && !categoryIdStr.isEmpty()) {
			try {
				categoryId = Integer.parseInt(categoryIdStr);
				if (categoryId <= 0) { // IDが正の数であることを確認
					validationErrors.add("カテゴリIDは1以上の数値を入力してください。");
				}
			} catch (NumberFormatException e) {
				validationErrors.add("カテゴリIDは数値を入力してください。");
			}
		}

		// バリデーションエラーがあった場合
		if (!validationErrors.isEmpty()) {
			request.setAttribute("validationErrors", validationErrors);
			// 入力値をフォームに再表示するためにrequestスコープにセット（getParameterで直接取得されるが、念のため）
			request.setAttribute("categoryId", categoryIdStr);
			request.setAttribute("categoryName", categoryName);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/category-register.jsp");
			dispatcher.forward(request, response);
			return; // 処理を中断
		}

		// バリデーション成功後の処理
		CategoryDAO categoryDAO = new CategoryDAO();
		CategoryBean category = new CategoryBean(categoryId, categoryName);
		String errorMessage = null;
		String successMessage = null;

		try {
			boolean result = categoryDAO.insertCategory(category); // DB登録処理

			if (result) {
				successMessage = "カテゴリが正常に登録されました。";
				// 登録成功後、カテゴリ一覧ページへリダイレクト
				// リダイレクトすることで、F5連打による二重登録を防ぐ
				response.sendRedirect(request.getContextPath() + "/categoryList?message="
						+ java.net.URLEncoder.encode(successMessage, "UTF-8"));
				return; // リダイレクト後、処理を終了
			} else {
				errorMessage = "カテゴリの登録に失敗しました。";
				request.setAttribute("errorMessage", errorMessage);
				// 登録失敗時はフォームに戻る
				doGet(request, response); // フォームを再表示
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "データベースエラーが発生しました。", e);
			errorMessage = "データベースエラーによりカテゴリ登録に失敗しました。管理者にお問い合わせください。";
			if (e.getMessage() != null && e.getMessage().contains("Duplicate entry")) { // ID重複エラーの簡易検出
				errorMessage = "入力されたカテゴリIDは既に存在します。別のIDを入力してください。";
			}
			request.setAttribute("errorMessage", errorMessage);
			// エラー時も入力フォームに戻す
			doGet(request, response); // フォームを再表示
		}
	}
}