package product;

import java.util.Scanner;

public class InputHandler {
	private static Scanner scanner = new Scanner(System.in); // 入力用 Scanner

	public static Product createProductFromInput() {
		String name = "";
		int price = 0;
		int stock = 0;

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
				scanner.next(); // 数値以外の入力を読み飛ばす
				throw new IllegalArgumentException("価格には数値を入力してください。");
			}

			System.out.print("在庫数を入力してください: ");
			if (scanner.hasNextInt()) {
				stock = scanner.nextInt();
				if (stock < 0) {
					throw new IllegalArgumentException("在庫数がマイナスの値です。");
				}
			} else {
				scanner.next(); // 数値以外の入力を読み飛ばす
				throw new IllegalArgumentException("在庫数には数値を入力してください。");
			}

			return new Product(name, price, stock);

		} catch (IllegalArgumentException e) {
			System.err.println("入力エラー: " + e.getMessage());
			return null; // 入力エラー時は null を返す
		}
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
		closeScanner(); // プログラム終了時に Scanner を閉じる
	}
}