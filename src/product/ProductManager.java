package product;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
	private List<Product> products;

	public ProductManager() {
		this.products = new ArrayList<>();
	}

	public void addProduct(Product product) {
		if (product != null) {
			this.products.add(product);
			System.out.println(product.getName() + " を登録しました。");
		} else {
			System.out.println("商品の登録に失敗しました。入力内容を確認してください。");
		}
	}

	public void removeProductByName(String name) {
		boolean removed = false;
		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getName().equals(name)) {
				System.out.println(products.get(i).getName() + " を削除しました。");
				products.remove(i);
				removed = true;
				break;
			}
		}
		if (!removed) {
			System.out.println(name + " は見つかりませんでした。");
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

	public List<Product> searchProductByName(String keyword) {
		List<Product> results = new ArrayList<>();
		for (Product product : products) {
			if (product.getName().contains(keyword)) {
				results.add(product);
			}
		}
		return results;
	}

	public void displayAllProducts() {
		System.out.println("\n--- 商品一覧 ---");
		for (Product product : products) {
			System.out.println(product);
		}
		System.out.println("----------------");
	}
}