package product;

import java.util.ArrayList;
import java.util.List;

public class ProductManager implements Searchable {
	private List<Product> products;

	public ProductManager() {
		this.products = new ArrayList<>();
	}

	public void addProduct(Product product) {
		this.products.add(product);
		System.out.println("商品を追加しました: " + product.getName());
	}

	public void removeProduct(int id) {
		boolean removed = false;
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getId() == id) {
				System.out.println("商品を削除しました: " + products.get(i).getName());
				products.remove(i);
				removed = true;
				break;
			}
		}
		if (!removed) {
			System.out.println("ID " + id + " の商品は見つかりませんでした。");
		}
	}

	public Product getProductByName(String name) {
		for (Product product : products) {
			if (product.getName().equals(name)) {
				return product;
			}
		}
		System.out.println("名前 '" + name + "' の商品は見つかりませんでした。");
		return null;
	}

	public void displayAllProducts() {
		if (products.isEmpty()) {
			System.out.println("登録されている商品はありません。");
			return;
		}
		System.out.println("--- 商品一覧 ---");
		for (Product product : products) {
			System.out.println(product);
		}
		System.out.println("----------------");
	}

	@Override
	public List<Product> search(String keyword) {
		List<Product> results = new ArrayList<>();
		for (Product product : products) {
			if (product.getName().contains(keyword)) {
				results.add(product);
			}
		}
		return results;
	}
}