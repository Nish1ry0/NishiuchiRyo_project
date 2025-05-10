package product;

public class DiscountedProduct extends Product {
	private double discountRate; // 割引率

	public DiscountedProduct(int id, String name, int price, int stock, double discountRate) {
		super(id, name, price, stock);
		this.discountRate = discountRate;
	}

	public double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

	public double calculateDiscountedPrice() {
		return getPrice() * (1 - discountRate); // 割引価格を計算
	}

	@Override
	public String toString() {
		return super.toString() + ", 割引率: " + (discountRate * 100) + "%, 割引価格: " + calculateDiscountedPrice() + "円";
	}
}