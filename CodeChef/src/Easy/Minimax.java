package Easy;

import java.util.*;
import java.io.*;

/*Let's consider a square grid with N rows and N columns, both numbered 1 through N. Each cell contains one integer. 
Let ri denote the minimum value in the i-th row, and let Ci denote the maximum value in the i-th column.
A grid is called nice if and only if the following condition holds true:

max(r1, r2, ..., rN) = min(C1, C2, ..., CN)

Chef has a square grid and can change some of its elements (numbers in the cells). Find the minimum possible number of 
elements Chef should change, so that the new grid is nice.

An element can be changed to any integer.
*/
public class Minimax {

	public static void main(String[] args) throws java.lang.Exception {
		Matrix m = new Matrix();
		int min_changes = m.getN();
		if (m.get_r_max() == m.get_C_min()) {
			min_changes = 0;
		} else {
			flag: for (int z = 1; z < m.getN(); z++) {
				for (int x = 0; x <= z; x++) {
					int y = z - x;
					if (m.isSolution(x, y)) {
						min_changes = z;
						break flag;
					}
				}
			}
		}

		System.out.println(min_changes);
	}
}

class Reader {
	final private int BUFFER_SIZE = 1 << 16;
	private DataInputStream din;
	private byte[] buffer;
	private int bufferPointer, bytesRead;

	Reader() {
		din = new DataInputStream(System.in);
		buffer = new byte[BUFFER_SIZE];
		bufferPointer = bytesRead = 0;
	}

	public Reader(String file_name) throws IOException {
		din = new DataInputStream(new FileInputStream(file_name));
		buffer = new byte[BUFFER_SIZE];
		bufferPointer = bytesRead = 0;
	}

	public String readLine() throws IOException {
		byte[] buf = new byte[64]; // line length
		int cnt = 0, c;
		while ((c = read()) != -1) {
			if (c == '\n')
				break;
			buf[cnt++] = (byte) c;
		}
		return new String(buf, 0, cnt);
	}

	int nextInt() throws IOException {
		int ret = 0;
		byte c = read();
		while (c <= ' ')
			c = read();
		boolean neg = (c == '-');
		if (neg)
			c = read();
		do {
			ret = ret * 10 + c - '0';
		} while ((c = read()) >= '0' && c <= '9');

		if (neg)
			return -ret;
		return ret;
	}

	public long nextLong() throws IOException {
		long ret = 0;
		byte c = read();
		while (c <= ' ')
			c = read();
		boolean neg = (c == '-');
		if (neg)
			c = read();
		do {
			ret = ret * 10 + c - '0';
		} while ((c = read()) >= '0' && c <= '9');
		if (neg)
			return -ret;
		return ret;
	}

	public double nextDouble() throws IOException {
		double ret = 0, div = 1;
		byte c = read();
		while (c <= ' ')
			c = read();
		boolean neg = (c == '-');
		if (neg)
			c = read();

		do {
			ret = ret * 10 + c - '0';
		} while ((c = read()) >= '0' && c <= '9');

		if (c == '.') {
			while ((c = read()) >= '0' && c <= '9')
				ret += (c - '0') / (div *= 10);
		}

		if (neg)
			return -ret;
		return ret;
	}

	private void fillBuffer() throws IOException {
		bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
		if (bytesRead == -1)
			buffer[0] = -1;
	}

	private byte read() throws IOException {
		if (bufferPointer == bytesRead)
			fillBuffer();
		return buffer[bufferPointer++];
	}
}

class Matrix {
	Reader in = new Reader();
	private int N;
	private int[][] ar;
	private int[][] sorted_rows;
	private int[][] sorted_cols;
	private int[] new_r_max;
	private int[] new_C_min;

	private int r_max;
	private int C_min;

	Matrix() throws IOException {
		N = in.nextInt();
		ar = new int[N][N];
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				ar[row][col] = in.nextInt();
			}
		}

		new_r_max = new int[N];
		new_C_min = new int[N];
		sortRows();
		sortCols();
		r_max = find_r_max();
		C_min = find_C_min();
	}

	int getN() {
		return N;
	}

	static int[] getColumn(int[][] arr, int n) {
		int len = arr.length;
		int[] column = new int[len];
		for (int m = 0; m < len; m++) {
			column[m] = arr[m][n];
		}
		return column;
	}

	private int find_r_max() {
		int max = 1;
		for (int m = 0; m < N; m++) {
			int r = sorted_rows[m][0];
			if (r > max)
				max = r;
		}
		return max;
	}

	int get_r_max() {
		return r_max;
	}

	private int find_C_min() {
		int min = Integer.MAX_VALUE;
		for (int n = 0; n < N; n++) {
			int C = sorted_cols[n][N - 1];
			if (C < min)
				min = C;
		}
		return min;
	}

	int get_C_min() {
		return C_min;
	}

	private void sortRows() {
		sorted_rows = new int[N][N];
		for (int m = 0; m < N; m++) {
			sorted_rows[m] = Arrays.copyOf(ar[m], N);
			Arrays.sort(sorted_rows[m]);
		}
	}

	private void sortCols() {
		sorted_cols = new int[N][N];
		for (int n = 0; n < N; n++) {
			sorted_cols[n] = getColumn(ar, n);
			Arrays.sort(sorted_cols[n]);
		}
	}

	boolean isSolution(int row_changes, int col_changes) {
		int max_r = r_max;
		int min_C = C_min;
		if (row_changes > 0) {
			if (new_r_max[row_changes] == 0) {
				for (int row = 0; row < N; row++) {
					int val;
					if ((val = sorted_rows[row][row_changes]) > max_r)
						max_r = val;
				}
				new_r_max[row_changes] = max_r;
			} else {
				max_r = new_r_max[row_changes];
			}
		}
		if (col_changes > 0) {
			if (new_C_min[col_changes] == 0) {
				for (int col = 0; col < N; col++) {
					int val;
					if ((val = sorted_cols[col][N - 1 - col_changes]) < min_C)
						min_C = val;
				}
				new_C_min[col_changes] = min_C;
			} else {
				min_C = new_C_min[col_changes];
			}
		}
		return max_r >= min_C;
	}
}