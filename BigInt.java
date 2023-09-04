package ExtraHW;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

public class BigInt {

	// ���� ����
	static void NewFile(String path) { // path�� ���ο� ���� ����, ��λ��� ������ ����� ������
		try {
			System.out.println(path);

			File f = new File(path);
			f.createNewFile();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// ���� ����
	static void UpdateFile(String path, String Text) { // path ������ ������ Text�� ����
		try {
			File f = new File(path); // ���� ����
			if (f.exists() == false) { // ������ ���°�� ����
				NewFile(path);
			}

			// ���� ����
			BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));
			buffWrite.write(Text, 0, Text.length());

			// ���� �ݱ�
			buffWrite.flush();
			buffWrite.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// String reverse(String str) : ���ڿ��� ���� String ����
	// ���ڿ� ���� �������� ����մϴ�.
	static String reverse(String str) {
		String tmp = "";
		for (int i = str.length() - 1; i >= 0; i--)
			tmp += str.charAt(i);
		return tmp;
	}

	// int getInt(String a, int i) : ���ڿ� a������ a[i]���� ������ �ٲ㼭 ��ȯ
	static int getInt(String a, int i) {
		if (a.length() <= i)
			return 0;
		else
			return (int) (a.charAt(i) - '0');
	}

	/*
	 * String Plus(String a, String b) : ���ڿ� ���� ���� ���� �ʱ⿡ reverse�� ���� ���;� �ϰ�, ��ȯ ����
	 * reverse�� ���Դϴ�. ��, ���ڿ� a + b �� ���ϰ��� �Ѵٸ� reverse(reverse(a), reverse(b)))�� ��������
	 * ����ؾ� �մϴ�.
	 */
	static String Plus(String a, String b) {

		String answer = ""; // ��ȯ�� ���� ���ڿ�
		int alen = a.length(), blen = b.length();
		int len = alen > blen ? alen : blen; // �� �� ���� ���̷� ����
		int carry = 0; // ĳ�� ���ϱ� ���� ���
		for (int i = 0; i < len; i++) {

			// index �ʰ� ����
			if (i >= alen) {
				a += "0";
				alen++;
			}
			if (i >= blen) {
				b += "0";
				blen++;
			}
			int res = carry + getInt(a, i) + getInt(b, i);
			answer += Integer.toString(res % 10);

			// ĳ�� ó��
			if (res >= 10)
				carry = 1;
			else
				carry = 0;
		}
		if (carry == 1)
			answer += "1"; // ������ �ڸ��� ĳ�� �߻� ��
		return answer; // reverse�� �� ��ȯ
	}

	/*
	 * String leftShift(String a) : ���ڿ� a�� �������� ����Ʈ ������ ���� ���ڿ� ���·� ��ȯ a<<1�� ����� �����
	 * �ϸ�, reverse(leftShift(reverse(a))�� ���·� ����ؾ� �մϴ�.
	 */
	static String leftShift(String a) {
		String answer = "";

		int carry = 0;
		for (int i = 0; i < a.length(); i++) {
			int res = (getInt(a, i) << 1) + carry;
			answer += Integer.toString(res % 10);
			if (res >= 10)
				carry = 1;
			else
				carry = 0;
		}

		// ������ ĳ�� ó��
		if (carry == 1)
			answer += "1";
		return answer;// reverse�� �� ��ȯ
	}

	/*
	 * String rightShift(String a) : ���ڿ� a�� ���������� ����Ʈ ������ ���� ���ڿ� ���·� ��ȯ a>>1�� �����
	 * ����� �ϸ�, reverse(rightShift(reverse(a))�� ���·� ����ؾ� �մϴ�.
	 */
	static String rightShift(String aa) { // a>>1 ��ȯ
		// reverse�� �� ����
		String a = reverse(aa);
		String answer = "";

		int carry = 0;
		for (int i = 0; i < a.length(); i++) {

			int res = getInt(a, i);
			answer += Integer.toString(carry + (res >> 1));
			if (res % 2 == 1)
				carry = 5;
			else
				carry = 0; // ĳ�� ���
		}

		if (answer.length() == 1)
			return answer;

		int cnt = 0; // �տ� 0�� ���� -> substring���� �ڸ���
		while (cnt < answer.length() && getInt(answer, cnt) == 0)
			cnt++;

		return reverse(answer.substring(cnt)); // reverse�� �� ��ȯ
	}

	/*
	 * String multiply(String a, String b) : a * b�� ������� ���ڿ� ���·� ��ȯ �ڸ����� ���� ���� ����������
	 * ������ �Լ��Դϴ�. a la russe �˰����� �̿��߽��ϴ�. ��� ��, a*b�� ���ϱ� ����
	 * reverse(multiply(reverse(a), reverse(b)))�� ���·� ����ؾ� �մϴ�.
	 */
	static String multiply(String a, String b) {
		// a la russe �˰���, ���� �� reverse�� �� ����
		String answer = "";

		while (!a.equals("0")) {
			if (getInt(a, 0) % 2 == 1)
				answer = Plus(answer, b);
			a = rightShift(a);
			b = leftShift(b);
		}
		return answer;
	}

	/*
	 * String Minus(String aa, String bb) : aa-bb�� ����� ���ڿ� ���·� ��ȯ ���ڿ��� ������ ������
	 * �κ��Դϴ�. ���������� �� ū ���� ���� ��찡 �����Ƿ�, �׻� aa�� bb���� ū ���̶�� �����߽��ϴ�.
	 */
	static String Minus(String aa, String bb) {

		String a = reverse(aa), b = reverse(bb);// ���Ǹ� ���� �������� �ٲ㼭 ���
		String answer = "";

		int carry = 0;
		for (int i = 0; i < a.length(); i++) {
			int res = getInt(a, i) - getInt(b, i) + carry;
			if (res < 0) {
				carry = -1;
				res += 10;
			} else
				carry = 0;
			answer += Integer.toString(res);
		}

		if (answer.length() == 1)
			return answer;

		answer = reverse(answer); // �������� �ǵ���
		int cnt = 0; // �տ� 0�� ���� -> substring���� �ڸ���
		while (cnt < answer.length() && getInt(answer, cnt) == 0)
			cnt++;

		String ret = answer.substring(cnt);
		if (ret.equals(""))
			return "0";
		return answer.substring(cnt); // �Ϸ�� ���� �� ��ȯ

	}

	/*
	 * String divideMul(Vector<String> list, int n) list�� �ִ� ���ڿ� �� ���� ��� ���� ����� ���ڿ�
	 * ���·� ��ȯ�ϴ� �Լ��Դϴ�. n�� ���� list ���� �ִ� ���ڿ����� �ּ� �ڸ����̸�, �̸� �������� divide and conquer��
	 * ���� ������ �����մϴ�. factorial�Լ��� ������ �� ����� �ڸ��� ������ ������ ���� ���۵Ǿ����ϴ�.
	 */
	static String divideMul(Vector<String> list, int n) { // list�� �ִ� ���� ������������ ���ϱ�, �ڸ��� �ּ� n�̻�

		if (list.size() == 1)
			return list.get(0); // ������� : ��� ���� ���
		System.out.println("���� ���ڿ� �ּ� ���� : " + n + ", listSize : " + list.size());
		Vector<String> over = new Vector<String>();

		String answer="1";
		for (int i = 0; i < list.size(); i++) {
			
			
			answer = karatsuba(answer, list.get(i));
				
			if (answer.length() >= 2 * n) { // ������ �ʰ��� ��� over�� ����
				over.add(answer);
				answer="1";
				continue;
			}
			
		}
		
		over.add(answer);	//���� �� over�� ����
		
		return divideMul(over, 2 * n); // over�� ���� ���

	}

	/*
	 * String Factorial(int n) : n!�� ������� ���ڿ� ���·� ��ȯ�ϴ� �Լ� �־��� ������ ���� ��ǥ�� 50000!��
	 * ������ִ� �Լ��Դϴ�.
	 */
	static String Factorial(int n) {

		String answer = "1";

		Vector<String> list = new Vector<String>();
		int maxLen = 10; // ó�� ����� ���ڿ����� �ּұ���

		for (int i = n; i >= 2; i--) {

			answer = karatsuba(answer, Integer.toString(i));

			if (answer.length() >= maxLen) {
				list.add(answer);
				answer = "1";
			}

			System.out.println("���� : " + i);
		}

		list.set(0, karatsuba(list.get(0), answer)); // ���� �� ���ϱ�

		System.out.println("�������� ����, listsize : " + list.size());

		return divideMul(list, maxLen);

	}

	/*
	 * String karatsuba(String a, String b) : a*b�� ����� ���ڿ� ���·� ��ȯ ī���߹� �˰����� �̿��ؼ� ��
	 * ��Ʈ���� ���� �����߽��ϴ�. �ڸ����� ª���� �Ϲ����� ������ �����մϴ�.
	 */
	static String karatsuba(String a, String b) {
		if (a.equals("0") || b.equals("0")) return "0";

		int alen = a.length(), blen = b.length();
		if (alen < blen) // a�� �׻� �� �浵��
			return karatsuba(b, a);

		// ������� : �� �� �ϳ��� �� ���, �� �� �ϳ��� 1�� ���
		if (alen == 0 || blen == 0) return "0";
		if (a.equals("1")) return b;
		if (b.equals("1")) return a;

		if (alen <= 50) // �ڸ����� ª���� a la russe�� ����
			return reverse(multiply(reverse(a), reverse(b)));

		int half = alen / 2;

		String a1 = a.substring(0, alen - half);
		String a0 = a.substring(alen - half, alen);

		String b1 = (blen > half) ? b.substring(0, blen - half) : "0";
		String b0 = (blen > half) ? b.substring(blen - half, blen) : b;

		int cnt = 0; // �տ� 0�� ���� -> substring���� �ڸ���
		while (cnt < a1.length() && getInt(a1, cnt) == 0)
			cnt++;
		a1 = a1.substring(cnt);
		if (a1.equals(""))
			a1 += "0";

		cnt = 0; // �տ� 0�� ���� -> substring���� �ڸ���
		while (cnt < a0.length() && getInt(a0, cnt) == 0)
			cnt++;
		a0 = a0.substring(cnt);
		if (a0.equals(""))
			a0 += "0";

		cnt = 0; // �տ� 0�� ���� -> substring���� �ڸ���
		while (cnt < b0.length() && getInt(b0, cnt) == 0)
			cnt++;
		b0 = b0.substring(cnt);
		if (b0.equals(""))
			b0 += "0";

		cnt = 0; // �տ� 0�� ���� -> substring���� �ڸ���
		while (cnt < b1.length() && getInt(b1, cnt) == 0)
			cnt++;
		b1 = b1.substring(cnt);
		if (b1.equals(""))
			b1 += "0";

		String z2 = karatsuba(a1, b1); // z2 = a1 * b1 = 0
		String z0 = karatsuba(a0, b0); // z0 = a0 * b0

		// z1 = ((a0 + a1) * (b0 + b1)) - z0 - z2
		String z1 = Minus(
				Minus(karatsuba(reverse(Plus(reverse(a0), reverse(a1))), reverse(Plus(reverse(b0), reverse(b1)))), z0),
				z2);

		// z2 + z1 + z0

		for (int i = 0; i < half + half; i++)
			z2 += "0";
		for (int i = 0; i < half; i++)
			z1 += "0";
		String ans = reverse(Plus(reverse(z1), reverse(z0)));
		return Minus(reverse(Plus(reverse(ans), reverse(z2))), "0");
	}

	/*
	 * �����Լ� path ��ο� Factorial(50000)�� ���� �����ϰ�, ����ð��� ����մϴ�.
	 */
	public static void main(String[] args) {

		String path = "C:\\homework\\answer.dat";

		long start = System.currentTimeMillis();
		UpdateFile(path, Factorial(50000));
		long end = System.currentTimeMillis();
		System.out.println(" string ���� �ð� : " + (end - start) / 1000.0);

	}
}
