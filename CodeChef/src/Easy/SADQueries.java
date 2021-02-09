package Easy;

import java.io.*;
import java.util.*;

/*"The Mean Absolute Difference is a measure of statistical dispersion equal to the average absolute difference of two independent"
+ " values drawn from a probability distribution." - Wikipedia

In this problem we are concerned not with Mean Absolute Difference (MAD) of two distributions but rather with the Summed 
Absolute Difference (SAD) of two arrays. Given arrays (1-indexed) P and Q of lengths p and q, define
SAD(P,Q)= |Pi-Qj|
Given a collection of K arrays A1, ..., AK, report SAD(Ai, Aj) for several queries (i, j).
*/
public class SADQueries {

	static int a[][], len[];
	static long sum[][];

	public static void main(String[] args) throws Exception {
		FastReader in = new FastReader(System.in);
		PrintWriter pw = new PrintWriter(System.out);

		int k = in.nextInt();

		a = new int[k][];
		len = new int[k];
		sum = new long[k][];

		for (int i = 0; i < k; i++) {
			int n = in.nextInt();
			len[i] = n;
			a[i] = new int[n];
			sum[i] = new long[n];
			for (int p = 0; p < n; p++) {
				a[i][p] = in.nextInt();
			}

			// shuffle(a[i]);
			Arrays.sort(a[i]);
			for (int p = 0; p < n; p++) {
				sum[i][p] = a[i][p];
				if (p > 0)
					sum[i][p] += sum[i][p - 1];
			}
		}

		int m = in.nextInt();
		for (int i = 0; i < m; i++) {
			int x = in.nextInt(), y = in.nextInt();
			pw.println(getRes(x, y));
		}

		pw.close();
	}

	public static void shuffle(int[] a) {
		Random rnd = new Random();
		for (int i = a.length - 1; i >= 1; i--) {
			int j = rnd.nextInt(i + 1);
			int t = a[i];
			a[i] = a[j];
			a[j] = t;
		}
	}

	static HashMap<Long, Long> map = new HashMap<>();

	static long getRes(int x, int y) {
		if (x < y) {
			int tmp = x;
			x = y;
			y = tmp;
		}
		long p = x;
		p = (p << 30l) | y;
		if (map.containsKey(p))
			return map.get(p);
		long ans = 0;
		x--;
		y--;
		if (len[y] < len[x]) {
			int tmp = x;
			x = y;
			y = tmp;
		}

		for (int i : a[x]) {
			int idx = binarySearch(y, i);
			{
				long mul = idx + 1;
				mul *= i;
				ans += (mul - getSum(y, 0, idx));
			}
			{
				long mul = len[y] - (idx + 1);
				mul *= i;
				ans += (getSum(y, idx + 1, len[y] - 1) - mul);
			}
		}

		map.put(p, ans);
		return ans;
	}

	static long getSum(int p, int i, int j) {
		if (i > j || j < 0)
			return 0;
		if (i == 0)
			return sum[p][j];
		return sum[p][j] - sum[p][i - 1];
	}

	static int binarySearch(int b, int key) {
		int lo = 0, hi = len[b] - 1, mid;
		while (lo < hi) {
			mid = (lo + hi) / 2;
			if (a[b][mid] > key)
				hi = mid;
			else
				lo = mid + 1;
		}
		if (a[b][lo] > key)
			return lo - 1;
		return lo;
	}

	public static int[] radixSort(int[] f) {
		return radixSort(f, f.length);
	}

	public static int[] radixSort(int[] f, int n) {
		int[] to = new int[n];
		{
			int[] b = new int[65537];
			for (int i = 0; i < n; i++)
				b[1 + (f[i] & 0xffff)]++;
			for (int i = 1; i <= 65536; i++)
				b[i] += b[i - 1];
			for (int i = 0; i < n; i++)
				to[b[f[i] & 0xffff]++] = f[i];
			int[] d = f;
			f = to;
			to = d;
		}
		{
			int[] b = new int[65537];
			for (int i = 0; i < n; i++)
				b[1 + (f[i] >>> 16)]++;
			for (int i = 1; i <= 65536; i++)
				b[i] += b[i - 1];
			for (int i = 0; i < n; i++)
				to[b[f[i] >>> 16]++] = f[i];
			int[] d = f;
			f = to;
			to = d;
		}
		return f;
	}

	static void debug(Object... obj) {
		System.err.println(Arrays.deepToString(obj));
	}

	static class FastReader {
		InputStream is;
		private byte[] inbuf = new byte[1024];
		private int lenbuf = 0, ptrbuf = 0;
		static final int ints[] = new int[128];

		public FastReader(InputStream is) {
			for (int i = '0'; i <= '9'; i++)
				ints[i] = i - '0';
			this.is = is;
		}

		public int readByte() {
			if (lenbuf == -1)
				throw new InputMismatchException();
			if (ptrbuf >= lenbuf) {
				ptrbuf = 0;
				try {
					lenbuf = is.read(inbuf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}
				if (lenbuf <= 0)
					return -1;
			}
			return inbuf[ptrbuf++];
		}

		public boolean isSpaceChar(int c) {
			return !(c >= 33 && c <= 126);
		}

		public int skip() {
			int b;
			while ((b = readByte()) != -1 && isSpaceChar(b))
				;
			return b;
		}

		public String next() {
			int b = skip();
			StringBuilder sb = new StringBuilder();
			while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b
										// != ' ')
				sb.appendCodePoint(b);
				b = readByte();
			}
			return sb.toString();
		}

		public int nextInt() {
			int num = 0, b;
			boolean minus = false;
			while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
				;
			if (b == '-') {
				minus = true;
				b = readByte();
			}

			while (true) {
				if (b >= '0' && b <= '9') {
					num = (num << 3) + (num << 1) + ints[b];
				} else {
					return minus ? -num : num;
				}
				b = readByte();
			}
		}

		public long nextLong() {
			long num = 0;
			int b;
			boolean minus = false;
			while ((b = readByte()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
				;
			if (b == '-') {
				minus = true;
				b = readByte();
			}

			while (true) {
				if (b >= '0' && b <= '9') {
					num = (num << 3) + (num << 1) + ints[b];
				} else {
					return minus ? -num : num;
				}
				b = readByte();
			}
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}

		public char[] next(int n) {
			char[] buf = new char[n];
			int b = skip(), p = 0;
			while (p < n && !(isSpaceChar(b))) {
				buf[p++] = (char) b;
				b = readByte();
			}
			return n == p ? buf : Arrays.copyOf(buf, p);
		}
	}
}