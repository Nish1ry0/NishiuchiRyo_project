package product;

public class DiscountedProduct extends Product {
	private double discountRate; // 割引率（例：0.1 は 10% 割引）

	// コンストラクタ
	public DiscountedProduct(int id, String name, int price, int stock, double discountRate) {
		// 親クラスのコンストラクタを呼び出す
		super(id, name, price, stock);
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

	// 割引価格を計算するメソッド
	public int calculateDiscountedPrice() {
		// 元の価格に割引率を適用して割引額を計算して、元の価格から引く
		return (int) (getPrice() * (1 - discountRate));
	}

	// 商品情報を文字列で返すメソッド
	@Override
	public String toString() {
		return super.toString() + ", 割引率: " + (discountRate * 100) + "%, 割引価格: " + calculateDiscountedPrice() + "円";
	}
}