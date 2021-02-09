package Easy;

import java.io.*;
import java.util.*;
import java.math.*;

/*Chef had an array A with length N, but some of its elements got lost. Now, each element of this array is either unknown 
(denoted by -1) or a positive integer not exceeding K.

Chef decided to restore the array A by replacing each unknown element by a positive integer not exceeding K.

However, Chef has M restrictions that must hold for the restored array. There are two types of restrictions:

I L R, meaning that for each i such that L < i ≤ R, the condition Ai - Ai-1 = 1 should be satisfied.
D L R, meaning that for each i such that L < i ≤ R, the condition Ai - Ai-1 = -1 should be satisfied.
Chef would like to know the number of ways to restore the array while satisfying all restrictions, modulo 109+7.
*/
public class ChefRestoresArray {

	public static void main(String args[]) {
		try {
			InputReader in = new InputReader(System.in);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
			int tc = in.readInt();
			for (int t = 0; t < tc; t++) {
				int n = in.readInt();
				int m = in.readInt();
				int k = in.readInt();
				int ar[] = new int[n];
				boolean unkown[] = new boolean[n];
				for (int i = 0; i < n; i++) {
					ar[i] = in.readInt();
					if (ar[i] < 0) {
						unkown[i] = true;
					}
				}
				int inc[] = new int[n + 3];
				int dec[] = new int[n + 3];
				for (int i = 0; i < m; i++) {
					String line[] = in.readLine().split(" ");
					String ops = line[0];
					int l = Integer.parseInt(line[1]) - 1;
					int r = Integer.parseInt(line[2]) - 1;
					if (ops.equals("I")) {
						inc[l]++;
						inc[r]--;
					} else {
						dec[l]++;
						dec[r]--;
					}
				}
				boolean ans = true;
				for (int i = 1; i <= n + 1; i++) {
					inc[i] += inc[i - 1];
					dec[i] += dec[i - 1];
				}
				for (int i = 0; i <= n - 1; i++) {
					if (inc[i] > 0 && dec[i] > 0) {
						ans = false;
					}
				}
				long MOD = (long) 1e9 + 7;
				long total = 1L;
				if (ans) {
					for (int i = 0; i <= n - 1; i++) {
						if (ar[i] > 0 && inc[i] > 0) {
							if (ar[i + 1] > 0 && ar[i + 1] != ar[i] + 1) {
								ans = false;
								break;
							}
							ar[i + 1] = ar[i] + 1;
						}
						if (ar[i] > 0 && dec[i] > 0) {
							if (ar[i + 1] > 0 && ar[i + 1] != ar[i] - 1) {
								ans = false;
								break;
							}
							ar[i + 1] = ar[i] - 1;
						}
					}
					for (int i = n - 1; i >= 1; i--) {
						if (ar[i] > 0 && inc[i - 1] > 0) {
							if (ar[i - 1] > 0 && ar[i] != ar[i - 1] + 1) {
								ans = false;
								break;
							}
							ar[i - 1] = ar[i] - 1;
						}
						if (ar[i] > 0 && dec[i - 1] > 0) {
							if (ar[i - 1] > 0 && ar[i] != ar[i - 1] - 1) {
								ans = false;
								break;
							}
							ar[i - 1] = ar[i] + 1;
						}
					}
					if (ans) {
						for (int i = 0; i < n; i++) {
							if (ar[i] < 0 && unkown[i]) {
								int j = i;
								int max = 0, min = 0;
								int h = 0;
								for (; j < n; j++) {
									if (inc[j] > 0) {
										h++;
									} else if (dec[j] > 0) {
										h--;
									} else {
										break;
									}
									max = Math.max(h, max);
									min = Math.min(h, min);
								}
								i = j;
								// we have a range of number from min to max
								// we have to adjust it between 1 to k
								int w = max - min;
								if (k - w > 0) {
									total = total * (k - w);
									total %= MOD;
								} else {
									ans = false;
									break;
								}
							} else if (ar[i] <= 0 || ar[i] > k) {
								ans = false;
								break;
							}
						}
					}
				}
				if (ans) {
					out.write(Long.toString(total % MOD));
				} else {
					out.write(Integer.toString(0));
				}
				out.newLine();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class InputReader {
	private boolean finished = false;

	private InputStream stream;
	private byte[] buf = new byte[1024];
	private int curChar;
	private int numChars;

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

	public int peek() {
		if (numChars == -1)
			return -1;
		if (curChar >= numChars) {
			curChar = 0;
			try {
				numChars = stream.read(buf);
			} catch (IOException e) {
				return -1;
			}
			if (numChars <= 0)
				return -1;
		}
		return buf[curChar];
	}

	public int readInt() {
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

	public long readLong() {
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

	public String readString() {
		int length = readInt();
		if (length < 0)
			return null;
		byte[] bytes = new byte[length];
		for (int i = 0; i < length; i++)
			bytes[i] = (byte) read();
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return new String(bytes);
		}
	}

	public static boolean isSpaceChar(int c) {
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	private String readLine0() {
		StringBuffer buf = new StringBuffer();
		int c = read();
		while (c != '\n' && c != -1) {
			if (c != '\r')
				buf.appendCodePoint(c);
			c = read();
		}
		return buf.toString();
	}

	public String readLine() {
		String s = readLine0();
		while (s.trim().length() == 0)
			s = readLine0();
		return s;
	}

	public String readLine(boolean ignoreEmptyLines) {
		if (ignoreEmptyLines)
			return readLine();
		else
			return readLine0();
	}

	public BigInteger readBigInteger() {
		try {
			return new BigInteger(readString());
		} catch (NumberFormatException e) {
			throw new InputMismatchException();
		}
	}

	public char readCharacter() {
		int c = read();
		while (isSpaceChar(c))
			c = read();
		return (char) c;
	}

	public double readDouble() {
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
				return res * Math.pow(10, readInt());
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
					return res * Math.pow(10, readInt());
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				m /= 10;
				res += (c - '0') * m;
				c = read();
			}
		}
		return res * sgn;
	}

	public boolean isExhausted() {
		int value;
		while (isSpaceChar(value = peek()) && value != -1)
			read();
		return value == -1;
	}

	public String next() {
		return readString();
	}

	public boolean readBoolean() {
		return readInt() == 1;
	}
}