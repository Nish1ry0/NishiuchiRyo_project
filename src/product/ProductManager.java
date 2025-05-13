package product;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
	private List<Product> products;

	public ProductManager() {
		this.products = new ArrayList<>();
	}

	// 商品登録
	public void addProduct(Product product) {
		if (product != null) {
			this.products.add(product);
			System.out.println(product.getName() + " を登録しました。");
		} else {
			System.out.println("商品の登録に失敗しました。入力内容を確認してください。");
		}
	}

	// 商品削除（商品名で削除）
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

	// 商品情報取得（商品名から情報を取得）
	public Product getProductByName(String name) {
		for (Product product : products) {
			if (product.getName().equals(name)) {
				return product;
			}
		}
		System.out.println("名前 '" + name + "' の商品は見つかりませんでした。");
		return null;
	}

	// 商品検索（商品名で検索）
	public List<Product> searchProductByName(String keyword) {
		List<Product> results = new ArrayList<>();
		for (Product product : products) {
			if (product.getName().contains(keyword)) {
				results.add(product);
			}
		}
		if (results.isEmpty()) {
			System.out.println("キーワード '" + keyword + "' を含む商品は見つかりませんでした。");
		}
		return results;
	}

	// 全ての商品を表示
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
}