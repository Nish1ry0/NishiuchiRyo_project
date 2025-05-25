package product;

public class Product {
	private int id;
	private String name;
	private int price;
	private int stock;
	private int categoryId;

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

	public Product(String name, int price, int stock, int categoryId) {
		this.id = -1;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.categoryId = categoryId;
	}

	public Product(int id, String name, int price, int stock, int categoryId) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.categoryId = categoryId;
	}

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

	public int getCategoryId() {
		return categoryId;
	}

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

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	@Override
	public String toString() {
		return "ID: " + id + ", 名前: " + name + ", 価格: " + price + "円, 在庫: " + stock + "個, カテゴリID: " + categoryId;
	}
}