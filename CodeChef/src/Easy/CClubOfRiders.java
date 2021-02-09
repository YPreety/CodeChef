package Easy;

import java.util.*;
import java.io.*;

/*At a shopping mall, every now and then, a naughty kid steals some chocolates from different chocolate shops and runs away. 
The guards in the shopping mall were unable to catch the kid as he has got a fast scooter.
Each scooter has a performance value. Also, this kind of scooter needs a skilled rider to perform well. The total performance 
of a rider is measured by the value of scooter performance multiplied by rider’s skill. A rider X can catch a rider Y only if 
X’s total performance is more than Y’s.The shopping mall manager has bought one scooter for each guard. However, as he has 
bought them from an wholesale offer, their performance values need not be the same.

Each guard gets a scooter and of course, a scooter can be assigned to only one guard. Now, the manager wants to assign the 
scooters to the guards. Everyday the guards are on duty in different places and that's why each has to be able to catch the 
kid and only then an assignment will be valid.

So in short, given the skill of that kid, his scooter’s performance value, each guard’s skill, and each scooter’s performance 
value, find out the number of valid assignments possible. In a valid assignment, each guard will be able to catch the kid. 
As the result can be very large, find the mod 109 + 7 of the result.*/
public class CClubOfRiders implements Runnable {

	static class pair {

		int i, w;

		public pair(int i, int w) {
			this.i = i;
			this.w = w;
		}
	}

	public void run() {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);

		long mod = (long) 1e9 + 7;

		int t = s.nextInt();

		for (int ti = 1; ti <= t; ti++) {

			w.print("Case " + ti + ": ");

			long kp = s.nextLong();
			long ks = s.nextLong();

			int n = s.nextInt();

			ArrayList<Long> gp = new ArrayList<Long>();
			ArrayList<Long> sp = new ArrayList<Long>();

			for (int i = 0; i < n; i++)
				gp.add(s.nextLong());

			for (int i = 0; i < n; i++)
				sp.add(s.nextLong());

			Collections.sort(gp);
			Collections.sort(sp);

			long res = 1;
			int j = sp.size() - 1;

			for (int i = 0; i < gp.size(); i++) {

				while (j >= 0 && sp.get(j) * gp.get(i) > kp * ks)
					j--;

				res = (res * Math.max(0, sp.size() - (j + 1) - i)) % mod;
			}

			w.println(res);
		}

		w.close();
	}

	static class InputReader {

		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;

		public InputReader(InputStream stream) {
			this.stream = stream;
		}

		public int read() {
			if (numChars == -1)
				throw new InputMismatchException();

			if (curChar >= numChars) {
				curChar = 0;
				try {
					numChars = stream.read(buf);
				} catch (IOException e) {
					throw new InputMismatchException();
				}

				if (numChars <= 0)
					return -1;
			}
			return buf[curChar++];
		}

		public String nextLine() {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}

		public int nextInt() {
			int c = read();

			while (isSpaceChar(c))
				c = read();

			int sgn = 1;

			if (c == '-') {
				sgn = -1;
				c = read();
			}

			int res = 0;
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));

			return res * sgn;
		}

		public long nextLong() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			long res = 0;

			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			} while (!isSpaceChar(c));
			return res * sgn;
		}

		public double nextDouble() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') {
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') {
				c = read();
				double m = 1;
				while (!isSpaceChar(c)) {
					if (c == 'e' || c == 'E')
						return res * Math.pow(10, nextInt());
					if (c < '0' || c > '9')
						throw new InputMismatchException();
					m /= 10;
					res += (c - '0') * m;
					c = read();
				}
			}
			return res * sgn;
		}

		public String readString() {
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do {
				res.appendCodePoint(c);
				c = read();
			} while (!isSpaceChar(c));

			return res.toString();
		}

		public boolean isSpaceChar(int c) {
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}

		public String next() {
			return readString();
		}

		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	}

	public static void main(String args[]) throws Exception {
		new Thread(null, new CClubOfRiders(), "cf1", 1 << 26).start();
	}
}