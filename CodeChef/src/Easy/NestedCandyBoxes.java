package Easy;

import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;

/*Andy got a box of candies for Christmas. In fact, he discovered that the box contained several identical smaller boxes, and they 
could contain even smaller boxes, and so on. Formally, we say that candies are boxes of level 0, and for 1 ≤ i ≤ n, a level i box
contains ai boxes of level i - 1. The largest box has level n. Andy realized that it can take quite a long time to open all 
the boxes before he actually gets to eat some candies, so he put the box aside in frustration.

But today being his birthday, some friends came to visit Andy, and Andy decided to share some candies with them. 
In order to do that, he must open some of the boxes. Naturally, Andy can not open a box that is still inside an unopened box. 
If Andy wants to retrieve X candies, what is the least number of boxes he must open? You must help him answer many such queries.
Each query is independent.
*/
public class NestedCandyBoxes {

	final long mod = (int) 1e9 + 7, IINF = (long) 1e19;
	final int MAX = (int) 1e6 + 1, MX = (int) 1e7 + 1, INF = (int) 1e9, root = 3;
	DecimalFormat df = new DecimalFormat("0.0000000000000");
	double eps = 1e-9, PI = 3.141592653589793238462643383279502884197169399375105820974944;
	static boolean multipleTC = false, memory = false;
	FastReader in;
	PrintWriter out;

	public static void main(String[] args) throws Exception {
		if (memory)
			new Thread(null, new Runnable() {
				public void run() {
					try {
						new NestedCandyBoxes().run();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, "1", 1 << 28).start();
		else
			new NestedCandyBoxes().run();
	}

	void run() throws Exception {
		in = new FastReader();
		out = new PrintWriter(System.out);
		for (int i = 1, T = (multipleTC) ? ni() : 1; i <= T; i++)
			solve(i);
		out.flush();
		out.close();
	}

	void solve(int TC) throws Exception {
		int n = ni(), m = ni();
		long[] a = new long[n], q = new long[m];
		for (int i = 0; i < n; i++)
			a[i] = nl();
		for (int i = 0; i < m; i++)
			q[i] = nl();
		long[] b = new long[n], w = new long[n];
		long c = 0;
		int x = 0;
		for (int i = 0; i < n; i++) {
			if (a[i] == 1)
				c++;
			if ((i == n - 1 || a[i + 1] != 1) && c > 0) {
				b[x] = 1;
				w[x] = c;
				c = 0;
				x++;
			}
			if (a[i] != 1) {
				b[x] = a[i];
				w[x] = 1;
				x++;
			}
		}
		long[] suf = new long[x];
		for (int i = x - 1; i >= 0; i--)
			suf[i] = (i + 1 == x ? 0 : suf[i + 1]) + w[i];
		long[] ans = new long[m];
		for (int i = 0; i < m; i++) {
			long prod = 1;
			for (int j = 0; j < x; j++) {
				if (prod <= q[i] / b[j]) {
					prod *= b[j];
					ans[i] += ((q[i] + prod - 1) / prod) * w[j];
				} else {
					ans[i] += suf[j];
					break;
				}
			}
		}
		for (long l : ans)
			pn(l);
	}

	int lis(long[] a) {
		TreeMap<Long, Integer> map = new TreeMap<>();
		int ans = 0;
		for (int i = 0; i < a.length; i++) {
			int l = 0;
			if (map.isEmpty() || map.floorEntry(a[i]) == null)
				l = 1;
			else
				l = map.floorEntry(a[i]).getValue() + 1;
			Map.Entry<Long, Integer> e = null;
			while ((e = map.ceilingEntry(a[i])) != null && Integer.compare(e.getValue(), l) <= 0)
				map.remove(e.getKey());
			map.put(a[i], l);
			ans = Math.max(ans, l);
		}
		return ans;
	}

	int[] sort(int[] a) {
		if (a.length == 1)
			return a;
		int mid = a.length / 2;
		int[] b = sort(Arrays.copyOfRange(a, 0, mid)), c = sort(Arrays.copyOfRange(a, mid, a.length));
		for (int i = 0, j = 0, k = 0; i < a.length; i++) {
			if (j < b.length && k < c.length) {
				if (b[j] < c[k])
					a[i] = b[j++];
				else
					a[i] = c[k++];
			} else if (j < b.length)
				a[i] = b[j++];
			else
				a[i] = c[k++];
		}
		return a;
	}

	long[] sort(long[] a) {
		if (a.length == 1)
			return a;
		int mid = a.length / 2;
		long[] b = sort(Arrays.copyOfRange(a, 0, mid)), c = sort(Arrays.copyOfRange(a, mid, a.length));
		for (int i = 0, j = 0, k = 0; i < a.length; i++) {
			if (j < b.length && k < c.length) {
				if (b[j] < c[k])
					a[i] = b[j++];
				else
					a[i] = c[k++];
			} else if (j < b.length)
				a[i] = b[j++];
			else
				a[i] = c[k++];
		}
		return a;
	}

	long gcd(long a, long b) {
		return (b == 0) ? a : gcd(b, a % b);
	}

	int gcd(int a, int b) {
		return (b == 0) ? a : gcd(b, a % b);
	}

	int bitcount(long n) {
		return (n == 0) ? 0 : (1 + bitcount(n & (n - 1)));
	}

	void p(Object o) {
		out.print(o);
	}

	void pn(Object o) {
		out.println(o);
	}

	void pni(Object o) {
		out.println(o);
		out.flush();
	}

	String n() {
		return in.next();
	}

	String nln() {
		return in.nextLine();
	}

	int ni() {
		return Integer.parseInt(in.next());
	}

	long nl() {
		return Long.parseLong(in.next());
	}

	double nd() {
		return Double.parseDouble(in.next());
	}

	class FastReader {
		BufferedReader br;
		StringTokenizer st;

		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		public FastReader(String s) throws Exception {
			br = new BufferedReader(new FileReader(s));
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
}