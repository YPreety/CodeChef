package Easy;

import java.util.*;
import java.io.*;

/*Chef has a tree with N nodes. Each node of the tree has an integer weight associated with it.

Let's define the cost of a sequence of numbers as the arithmetic mean of all elements of the sequence.

Next, let's define the cost of a path in the tree as the cost of the sequence of weights of all nodes belonging to the path. 
(It's possible for a path to contain only one node.)

A set of paths in the tree is called a correct path decomposition if each node of the tree belongs to exactly one of the paths 
from this set. The cost of a correct decomposition is defined as the minimum of costs of all paths in this decomposition.

Chef would like to find the maximum cost of a correct decomposition. Can you help him?
*/

public class ChefAndAverageOnTree {

	void solve() {
		int t = ni();
		while (t-- > 0) {
			int n = ni();
			a = new long[n + 1];
			for (int i = 1; i <= n; i++)
				a[i] = nl();
			graph = new ArrayList[n + 1];
			for (int i = 1; i <= n; i++)
				graph[i] = new ArrayList<>();
			for (int i = 1; i < n; i++) {
				int x = ni(), y = ni();
				graph[x].add(y);
				graph[y].add(x);
			}
			dp = new double[n + 1][2];
			double l = 0, h = 1e7;
			b = new double[n + 1];
			double ans = 0;
			for (int k = 1; k <= 100; k++) {
				double mid = (l + h) / 2.0;
				// pw.println(mid);
				for (int i = 0; i <= n; i++)
					dp[i][0] = dp[i][1] = 0;
				for (int i = 1; i <= n; i++)
					b[i] = a[i] - mid;
				boolean is = dfs(1, -1);
				if (dp[1][0] < 0 && dp[1][1] < 0)
					is = false;
				// pw.println(is+" "+mid);
				if (is) {
					ans = mid;
					l = mid;
				} else
					h = mid;
			}
			pw.println(ans);

		}
	}

	long a[];
	double b[];
	ArrayList<Integer> graph[];
	double dp[][];
	PriorityQueue<Double> q = new PriorityQueue<>(Collections.reverseOrder());

	boolean dfs(int v, int pr) {
		for (int u : graph[v]) {
			if (u != pr) {
				if (!dfs(u, v))
					return false;
			}
		}
		int cc = 0;
		q.clear();
		double c1 = 0, c2 = 0;
		for (int u : graph[v]) {
			if (u != pr) {
				if (dp[u][0] < 0) {
					cc++;
					if (c1 == 0)
						c1 = dp[u][1];
					else
						c2 = dp[u][1];
				}
				q.offer(dp[u][1]);
			}
		}

		if (cc == 0) {
			double cc1 = 0, cc2 = 0;
			if (!q.isEmpty())
				cc1 = q.poll();
			if (!q.isEmpty())
				cc2 = q.poll();
			dp[v][0] = b[v] + Math.max(0, cc1) + Math.max(0, cc2);
			dp[v][1] = b[v] + Math.max(0, cc1);
		} else if (cc == 1) {
			double cc1 = 0;
			if (!q.isEmpty())
				cc1 = q.poll();
			dp[v][0] = b[v] + c1 + Math.max(0, cc1);
			dp[v][1] = b[v] + c1;
		} else if (cc == 2) {
			dp[v][1] = Integer.MIN_VALUE;
			dp[v][0] = b[v] + c1 + c2;
			if (dp[v][0] < 0)
				return false;
		}

		return true;

	}

	long M = (long) 1e9 + 7;
	InputStream is;
	PrintWriter pw;
	String INPUT = "";

	void run() throws Exception {
		is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
		pw = new PrintWriter(System.out);
		long s = System.currentTimeMillis();
		solve();
		pw.flush();
		if (!INPUT.isEmpty())
			tr(System.currentTimeMillis() - s + "ms");
	}

	public static void main(String[] args) throws Exception {
		new ChefAndAverageOnTree().run();
	}

	private byte[] inbuf = new byte[1024];
	public int lenbuf = 0, ptrbuf = 0;

	private int readByte() {
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

	private boolean isSpaceChar(int c) {
		return !(c >= 33 && c <= 126);
	}

	private int skip() {
		int b;
		while ((b = readByte()) != -1 && isSpaceChar(b))
			;
		return b;
	}

	private double nd() {
		return Double.parseDouble(ns());
	}

	private char nc() {
		return (char) skip();
	}

	private String ns() {
		int b = skip();
		StringBuilder sb = new StringBuilder();
		while (!(isSpaceChar(b))) { // when nextLine, (isSpaceChar(b) && b != '
									// ')
			sb.appendCodePoint(b);
			b = readByte();
		}
		return sb.toString();
	}

	private char[] ns(int n) {
		char[] buf = new char[n];
		int b = skip(), p = 0;
		while (p < n && !(isSpaceChar(b))) {
			buf[p++] = (char) b;
			b = readByte();
		}
		return n == p ? buf : Arrays.copyOf(buf, p);
	}

	private char[][] nm(int n, int m) {
		char[][] map = new char[n][];
		for (int i = 0; i < n; i++)
			map[i] = ns(m);
		return map;
	}

	private int[] na(int n) {
		int[] a = new int[n];
		for (int i = 0; i < n; i++)
			a[i] = ni();
		return a;
	}

	private int ni() {
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
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private long nl() {
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
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = readByte();
		}
	}

	private void tr(Object... o) {
		if (INPUT.length() > 0)
			System.out.println(Arrays.deepToString(o));
	}

}
