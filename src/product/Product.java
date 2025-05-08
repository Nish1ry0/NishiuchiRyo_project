package product;

public class Product {
	private int id;
	private String name;
	private int price;
	private int stock;

	// コンストラクタ
	public Product(int id, String name, int price, int stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public Product(String name, int price, int stock) {

		this.id = -1;
		this.name = name;
		this.price = price;
		this.stock = stock;

	}

	// getterメソッド
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getStock() {
		return stock;
	}

	// setterメソッド
	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "ID: " + id + ", 名前: " + name + ", 価格: " + price + "円, 在庫: " + stock + "個";
	}
}