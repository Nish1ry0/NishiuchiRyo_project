package product;

public class Main {
	public static void main(String[] args) {
		// ProductsManagerのインスタンスを作成
		ProductManager manager = new ProductManager();

		System.out.println("---商品を5つ追加して全てを表示---");
		// Productオブジェクトを作成して追加
		manager.addProduct(new Product(1, "冷蔵庫", 50000, 10));
		manager.addProduct(new Product(2, "ソファ", 30000, 5));
		manager.addProduct(new Product(3, "米", 2000, 3));
		manager.addProduct(new Product(4, "小説", 1500, 4));
		manager.addProduct(new Product(5, "Tシャツ", 1500, 5));
		manager.displayAllProducts();
		System.out.println();

		System.out.println("---商品を1つ削除して全てを表示---");
		manager.removeProduct(1); // IDが1の商品を削除
		manager.displayAllProducts();
		System.out.println();

		System.out.println("---商品名「米」の情報を表示---");
		Product rice = manager.getProductByName("米");
		if (rice != null) {
			System.out.println(rice);
		}
	}
}