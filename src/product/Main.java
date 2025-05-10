package product;

public class Main {
	public static void main(String[] args) {
		// Productのインスタンス
		Product product1 = new Product(1, "普通の製品", 1000, 100);
		System.out.println(product1);

		// DiscountedProductのインスタンス
		DiscountedProduct discountedProduct1 = new DiscountedProduct(2, "割引製品A", 2000, 50, 0.2);
		System.out.println(discountedProduct1);
		System.out.println("割引価格: " + discountedProduct1.calculateDiscountedPrice());

		// ポリモーフィズム
		Product product2 = new DiscountedProduct(3, "割引製品B", 3000, 30, 0.3);
		System.out.println(product2);

		// 型チェックとキャスト
		if (product2 instanceof DiscountedProduct) {
			DiscountedProduct discountedProduct2 = (DiscountedProduct) product2;
			System.out.println("割引価格 (キャスト後): " + discountedProduct2.calculateDiscountedPrice());
		}
	}
}