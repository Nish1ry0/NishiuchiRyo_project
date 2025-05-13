package product;

public class DiscountedProduct extends Product {
	private double discountRate; // 割引率

	// コンストラクタ
	public DiscountedProduct(int id, String name, int price, int stock, double discountRate) {
		super(id, name, price, stock); // 親クラスのコンストラクタ呼び出し
		this.discountRate = discountRate;
	}

	// getterメソッド
	public double getDiscountRate() {
		return discountRate;
	}

	// setterメソッド
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

	// 割引価格計算メソッド
	public int calculateDiscountedPrice() {
		return (int) (getPrice() * (1 - discountRate)); // 割引価格を計算
	}

	// 商品情報を文字列で返すメソッド（オーバーライド）
	@Override
	public String toString() {
		return super.toString() + ", 割引率: " + (discountRate * 100) + "%, 割引価格: " + calculateDiscountedPrice() + "円";
	}
}