package method.q09;

public class Even {
	public boolean checkEven(int num) {
		return num % 2 == 0;
	}

	public static void main(String[] args) {
		Even evenChecker = new Even();

		int num1 = 10;
		if (evenChecker.checkEven(num1)) {
			System.out.println(num1 + "は偶数です。");
		} else {
			System.out.println(num1 + "は奇数です。");
		}

		int num2 = 5;
		if (evenChecker.checkEven(num2)) {
			System.out.println(num2 + "は偶数です。");
		} else {
			System.out.println(num2 + "は奇数です。");
		}
	}
}