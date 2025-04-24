package method.q09;

public class Even {
	public static void main(String[] args) {
		checkEven(10);
		checkEven(5);
	}

	public static boolean checkEven(int num) {
		if (num % 2 == 0) {
			System.out.println(num + "は偶数です。");
			return true;
		} else {
			System.out.println(num + "は奇数です。");
			return false;
		}
	}
}