package basic.q08;

import java.util.Scanner;

public class InputProduct {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("商品名を入力してください：");
		String productName = scanner.nextLine();

		System.out.println("価格を入力してください：");
		int price = scanner.nextInt();
		scanner.nextLine(); // nextInt() の後の改行文字を読み飛ばす

		System.out.println("商品名は" + productName + "です。価格は" + price + "円です。");

		scanner.close();
	}
}