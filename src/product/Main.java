package product;

import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ProductManager manager = new ProductManager();
		String choice;

		System.out.println("---商品を5つ追加して全てを表示---");
		manager.addProduct(new Product(1, "冷蔵庫", 50000, 10, 1));
		manager.addProduct(new Product(2, "ソファ", 30000, 5, 1));
		manager.addProduct(new Product(3, "米", 2000, 3, 2));
		manager.addProduct(new Product(4, "小説", 1500, 4, 2));
		manager.addProduct(new Product(5, "Tシャツ", 1500, 5, 3));
		manager.displayAllProducts();
		System.out.println();

		do {
			System.out.println("\n--- 商品管理メニュー ---");
			System.out.println("1. 商品登録");
			System.out.println("2. 商品情報取得（商品名から）");
			System.out.println("3. 商品検索（商品名で）");
			System.out.println("4. 全ての商品を表示");
			System.out.println("5. 商品削除（商品名で）");
			System.out.println("0. 終了");
			System.out.print("操作を選択してください: ");
			choice = scanner.nextLine();

			switch (choice) {
			case "1":
				Product newProduct = InputHandler.createProductFromInput();
				if (newProduct != null) {
					manager.addProduct(newProduct);
				}
				break;
			case "2":
				System.out.print("取得したい商品名を入力してください: ");
				String getName = scanner.nextLine();
				Product foundProduct = manager.getProductByName(getName);
				if (foundProduct != null) {
					System.out.println(foundProduct);
				}
				break;
			case "3":
				System.out.print("検索したいキーワードを入力してください: ");
				String searchKeyword = scanner.nextLine();
				List<Product> searchResults = manager.searchProductByName(searchKeyword);
				for (Product result : searchResults) {
					System.out.println(result);
				}
				break;
			case "4":
				manager.displayAllProducts();
				break;
			case "5":
				System.out.print("削除したい商品名を入力してください: ");
				String removeName = scanner.nextLine();
				manager.removeProductByName(removeName);
				break;
			case "0":
				System.out.println("プログラムを終了します。");
				break;
			default:
				System.out.println("無効な選択です。もう一度入力してください。");
			}
		} while (!choice.equals("0"));

		InputHandler.closeScanner();
		scanner.close();
	}
}