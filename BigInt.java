package ExtraHW;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

public class BigInt {

	// 파일 생성
	static void NewFile(String path) { // path에 새로운 파일 생성, 경로상의 폴더는 만들어 놔야함
		try {
			System.out.println(path);

			File f = new File(path);
			f.createNewFile();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// 파일 수정
	static void UpdateFile(String path, String Text) { // path 파일의 내용을 Text로 수정
		try {
			File f = new File(path); // 파일 변수
			if (f.exists() == false) { // 파일이 없는경우 생성
				NewFile(path);
			}

			// 파일 쓰기
			BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));
			buffWrite.write(Text, 0, Text.length());

			// 파일 닫기
			buffWrite.flush();
			buffWrite.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	// String reverse(String str) : 문자열의 역순 String 리턴
	// 문자열 연산 과정에서 사용합니다.
	static String reverse(String str) {
		String tmp = "";
		for (int i = str.length() - 1; i >= 0; i--)
			tmp += str.charAt(i);
		return tmp;
	}

	// int getInt(String a, int i) : 문자열 a에대해 a[i]값을 정수로 바꿔서 반환
	static int getInt(String a, int i) {
		if (a.length() <= i)
			return 0;
		else
			return (int) (a.charAt(i) - '0');
	}

	/*
	 * String Plus(String a, String b) : 문자열 간의 덧셈 구현 초기에 reverse된 값이 들어와야 하고, 반환 값도
	 * reverse된 값입니다. 즉, 문자열 a + b 를 구하고자 한다면 reverse(reverse(a), reverse(b)))의 형식으로
	 * 사용해야 합니다.
	 */
	static String Plus(String a, String b) {

		String answer = ""; // 반환할 정답 문자열
		int alen = a.length(), blen = b.length();
		int len = alen > blen ? alen : blen; // 더 긴 값의 길이로 설정
		int carry = 0; // 캐리 더하기 위해 사용
		for (int i = 0; i < len; i++) {

			// index 초과 방지
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

			// 캐리 처리
			if (res >= 10)
				carry = 1;
			else
				carry = 0;
		}
		if (carry == 1)
			answer += "1"; // 마지막 자리수 캐리 발생 시
		return answer; // reverse된 값 봔환
	}

	/*
	 * String leftShift(String a) : 문자열 a를 왼쪽으로 시프트 연산한 값을 문자열 형태로 반환 a<<1의 결과를 얻고자
	 * 하면, reverse(leftShift(reverse(a))의 형태로 사용해야 합니다.
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

		// 마지막 캐리 처리
		if (carry == 1)
			answer += "1";
		return answer;// reverse된 값 반환
	}

	/*
	 * String rightShift(String a) : 문자열 a를 오른쪽으로 시프트 연산한 값을 문자열 형태로 반환 a>>1의 결과를
	 * 얻고자 하면, reverse(rightShift(reverse(a))의 형태로 사용해야 합니다.
	 */
	static String rightShift(String aa) { // a>>1 반환
		// reverse된 값 들어옴
		String a = reverse(aa);
		String answer = "";

		int carry = 0;
		for (int i = 0; i < a.length(); i++) {

			int res = getInt(a, i);
			answer += Integer.toString(carry + (res >> 1));
			if (res % 2 == 1)
				carry = 5;
			else
				carry = 0; // 캐리 계산
		}

		if (answer.length() == 1)
			return answer;

		int cnt = 0; // 앞에 0의 개수 -> substring으로 자를거
		while (cnt < answer.length() && getInt(answer, cnt) == 0)
			cnt++;

		return reverse(answer.substring(cnt)); // reverse된 값 반환
	}

	/*
	 * String multiply(String a, String b) : a * b의 결과값을 문자열 형태로 반환 자릿수가 적을 때의 곱셈연산을
	 * 구현한 함수입니다. a la russe 알고리즘을 이용했습니다. 사용 시, a*b를 구하기 위해
	 * reverse(multiply(reverse(a), reverse(b)))의 형태로 사용해야 합니다.
	 */
	static String multiply(String a, String b) {
		// a la russe 알고리즘, 들어올 때 reverse된 값 들어옴
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
	 * String Minus(String aa, String bb) : aa-bb의 결과를 문자열 형태로 반환 문자열의 뺄셈을 구현한
	 * 부분입니다. 과제에서는 더 큰 값을 빼는 경우가 없으므로, 항상 aa가 bb보다 큰 값이라고 가정했습니다.
	 */
	static String Minus(String aa, String bb) {

		String a = reverse(aa), b = reverse(bb);// 편의를 위해 역순으로 바꿔서 계산
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

		answer = reverse(answer); // 정상값으로 되돌림
		int cnt = 0; // 앞에 0의 개수 -> substring으로 자를거
		while (cnt < answer.length() && getInt(answer, cnt) == 0)
			cnt++;

		String ret = answer.substring(cnt);
		if (ret.equals(""))
			return "0";
		return answer.substring(cnt); // 완료된 정상 값 반환

	}

	/*
	 * String divideMul(Vector<String> list, int n) list에 있는 문자열 값 들을 모두 곱한 결과를 문자열
	 * 형태로 반환하는 함수입니다. n은 현재 list 내에 있는 문자열들의 최소 자릿수이며, 이를 기준으로 divide and conquer를
	 * 통한 곱셈을 수행합니다. factorial함수를 구현할 때 비슷한 자리수 끼리의 연산을 위해 제작되었습니다.
	 */
	static String divideMul(Vector<String> list, int n) { // list에 있는 값들 분할정복으로 곱하기, 자리수 최소 n이상

		if (list.size() == 1)
			return list.get(0); // 기저사례 : 모두 곱한 경우
		System.out.println("현재 문자열 최소 길이 : " + n + ", listSize : " + list.size());
		Vector<String> over = new Vector<String>();

		String answer="1";
		for (int i = 0; i < list.size(); i++) {
			
			
			answer = karatsuba(answer, list.get(i));
				
			if (answer.length() >= 2 * n) { // 사이즈 초과인 경우 over에 보냄
				over.add(answer);
				answer="1";
				continue;
			}
			
		}
		
		over.add(answer);	//남은 값 over로 보냄
		
		return divideMul(over, 2 * n); // over에 대한 계산

	}

	/*
	 * String Factorial(int n) : n!의 결과값을 문자열 형태로 반환하는 함수 주어진 과제의 최종 목표인 50000!을
	 * 계산해주는 함수입니다.
	 */
	static String Factorial(int n) {

		String answer = "1";

		Vector<String> list = new Vector<String>();
		int maxLen = 10; // 처음 저장될 문자열들의 최소길이

		for (int i = n; i >= 2; i--) {

			answer = karatsuba(answer, Integer.toString(i));

			if (answer.length() >= maxLen) {
				list.add(answer);
				answer = "1";
			}

			System.out.println("현재 : " + i);
		}

		list.set(0, karatsuba(list.get(0), answer)); // 남은 값 곱하기

		System.out.println("분할정복 시작, listsize : " + list.size());

		return divideMul(list, maxLen);

	}

	/*
	 * String karatsuba(String a, String b) : a*b의 결과를 문자열 형태로 반환 카라추바 알고리즘을 이용해서 두
	 * 스트링의 곱을 구현했습니다. 자리수가 짧으면 일반적인 곱셈을 수행합니다.
	 */
	static String karatsuba(String a, String b) {
		if (a.equals("0") || b.equals("0")) return "0";

		int alen = a.length(), blen = b.length();
		if (alen < blen) // a가 항상 더 길도록
			return karatsuba(b, a);

		// 기저사례 : 둘 중 하나가 빈 경우, 둘 중 하나가 1인 경우
		if (alen == 0 || blen == 0) return "0";
		if (a.equals("1")) return b;
		if (b.equals("1")) return a;

		if (alen <= 50) // 자리수가 짧으면 a la russe로 수행
			return reverse(multiply(reverse(a), reverse(b)));

		int half = alen / 2;

		String a1 = a.substring(0, alen - half);
		String a0 = a.substring(alen - half, alen);

		String b1 = (blen > half) ? b.substring(0, blen - half) : "0";
		String b0 = (blen > half) ? b.substring(blen - half, blen) : b;

		int cnt = 0; // 앞에 0의 개수 -> substring으로 자를거
		while (cnt < a1.length() && getInt(a1, cnt) == 0)
			cnt++;
		a1 = a1.substring(cnt);
		if (a1.equals(""))
			a1 += "0";

		cnt = 0; // 앞에 0의 개수 -> substring으로 자를거
		while (cnt < a0.length() && getInt(a0, cnt) == 0)
			cnt++;
		a0 = a0.substring(cnt);
		if (a0.equals(""))
			a0 += "0";

		cnt = 0; // 앞에 0의 개수 -> substring으로 자를거
		while (cnt < b0.length() && getInt(b0, cnt) == 0)
			cnt++;
		b0 = b0.substring(cnt);
		if (b0.equals(""))
			b0 += "0";

		cnt = 0; // 앞에 0의 개수 -> substring으로 자를거
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
	 * 메인함수 path 경로에 Factorial(50000)의 값을 저장하고, 수행시간을 출력합니다.
	 */
	public static void main(String[] args) {

		String path = "C:\\homework\\answer.dat";

		long start = System.currentTimeMillis();
		UpdateFile(path, Factorial(50000));
		long end = System.currentTimeMillis();
		System.out.println(" string 실행 시간 : " + (end - start) / 1000.0);

	}
}
