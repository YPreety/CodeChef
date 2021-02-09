package Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/*There is an array of length N consisting of non-negative integers. The array is sorted in non-decreasing order. 
Each number in the array appears exactly K times, except one element, which appears at least once, but less than K times. 
Your task is to identify that element.

This is an interactive problem. You are only given the integer N in the input. Both the array and the value of K are hidden. 
You are allowed to ask the judge the following queries: What is the value of the element at index i of the array? 
Identify the value of the element with frequency less than K by asking at most 60 such queries.
*/
public class FindElementInHiddenArray {
	public static void main(String[] args) {
		FastReader reader = new FastReader();
		int t = reader.nextInt();
		while (t-- > 0) {
			int n = reader.nextInt();
			ElementInHiddenArrayBinSearch finder = new ElementInHiddenArrayBinSearch();
			int k = finder.findSupposedK(reader, n);
			int kPlusOne = finder.ask(reader, k + 1);
			if (2 * k > n) {
				finder.tell(kPlusOne);
			} else {
				int kTwice = finder.ask(reader, 2 * k);
				if (kPlusOne == kTwice) {
					int kTwicePlusOne = finder.ask(reader, 2 * k + 1);
					if (kPlusOne == kTwicePlusOne) {
						finder.tell(finder.ask(reader, k));
					} else {
						finder.solve(reader, k, n);
					}
				} else {
					finder.tell(kPlusOne);
				}
			}
		}
	}

	static class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		long nextLong() {
			return Long.parseLong(next());
		}

		double nextDouble() {
			return Double.parseDouble(next());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}

	static class ElementInHiddenArrayBinSearch {
		Map<Integer, Integer> hiddenArrayMap = new HashMap<>();

		int ask(FastReader reader, int pos) {
			if (hiddenArrayMap.containsKey(pos)) {
				return hiddenArrayMap.get(pos);
			}
			System.out.println("1 " + pos);
			System.out.flush();
			int num = reader.nextInt();
			hiddenArrayMap.put(pos, num);
			return num;
		}

		void tell(int answer) {
			System.out.println("2 " + answer);
			System.out.flush();
		}

		int findSupposedK(FastReader reader, int arraySize) {
			int a1 = ask(reader, 1);
			int low = 1;
			int high = arraySize;
			int k = 0;

			while (low <= high) {
				int mid = low + (high - low) / 2;
				int midElem = ask(reader, mid);
				if (a1 == midElem) {
					k = mid;
					low = mid + 1;
				} else {
					high = mid - 1;
				}
			}
			return k;
		}

		void solve(FastReader reader, int k, int arraySize) {
			int grps = arraySize / k;
			int lowGrp = 1;
			int highGrp = grps;
			while (highGrp >= lowGrp) {
				int midGrp = lowGrp + (highGrp - lowGrp) / 2;
				if (ask(reader, midGrp * k) == ask(reader, midGrp * k + 1)) {
					highGrp = midGrp - 1;
				} else {
					lowGrp = midGrp + 1;
				}
			}
			tell(ask(reader, (lowGrp - 1) * k + 1));
		}
	}

}