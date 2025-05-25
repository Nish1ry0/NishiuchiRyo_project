package product;

import java.util.Scanner;

public class InputHandler {
	private static Scanner scanner = new Scanner(System.in);

	public static Product createProductFromInput() {
		String name = "";
		int price = 0;
		int stock = 0;
		int categoryId = 0;
		Product product = null;

		try {
			System.out.print("商品名を入力してください: ");
			name = scanner.nextLine();
			if (name.trim().isEmpty()) {
				throw new IllegalArgumentException("商品名が空です。");
			}

			System.out.print("価格を入力してください: ");
			if (scanner.hasNextInt()) {
				price = scanner.nextInt();
				if (price < 0) {
					throw new IllegalArgumentException("価格がマイナスの値です。");
				}
			} else {
				scanner.next();
				throw new IllegalArgumentException("価格には数値を入力してください。");
			}

			System.out.print("在庫数を入力してください: ");
			if (scanner.hasNextInt()) {
				stock = scanner.nextInt();
				if (stock < 0) {
					throw new IllegalArgumentException("在庫数がマイナスの値です。");
				}
			} else {
				scanner.next();
				throw new IllegalArgumentException("在庫数には数値を入力してください。");
			}

			System.out.print("カテゴリIDを入力してください: ");
			if (scanner.hasNextInt()) {
				categoryId = scanner.nextInt();
				if (categoryId < 0) {
					throw new IllegalArgumentException("カテゴリIDは0以上の数値を入力してください。");
				}
			} else {
				scanner.next();
				throw new IllegalArgumentException("カテゴリIDには数値を入力してください。");
			}

			product = new Product(name, price, stock, categoryId);

		} catch (IllegalArgumentException e) {
			System.err.println("入力エラー: " + e.getMessage());
		} finally {
			scanner.nextLine();
		}

		return product;
	}

	public static void closeScanner() {
		if (scanner != null) {
			scanner.close();
			System.out.println("Scanner を閉じました。");
		}
	}

	public static void main(String[] args) {
		Product newProduct = createProductFromInput();
		if (newProduct != null) {
			System.out.println("入力された商品情報: " + newProduct);
		} else {
			System.out.println("商品の登録に失敗しました。");
		}
		closeScanner();
	}

	public static String getStringInput(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}

	public static int getIntInput(String prompt) {
		int value = 0;
		while (true) {
			try {
				System.out.print(prompt);
				value = scanner.nextInt();
				if (value < 0) {
					throw new IllegalArgumentException("0以上の数値を入力してください。");
				}
				break;
			} catch (java.util.InputMismatchException e) {
				System.err.println("無効な入力です。数値を入力してください。");
				scanner.next();
			} catch (IllegalArgumentException e) {
				System.err.println("入力エラー: " + e.getMessage());
			} finally {
				scanner.nextLine();
			}
		}
		return value;
	}
}