package Easy;

import java.io.IOException;
import java.io.InputStream;
import java.util.InputMismatchException;

/*As you know, Malvika has created some n programming contests. Each of the contests has three problems, categorized as easy, 
medium and hard on difficulty level. For the ith contest, easy problem takes TE[i] hours and gives you PE[i] pleasure. 
Similarly, medium problem takes TM[i] hours, gives PM[i] pleasure, and a hard one has the values TH[i], PH[i].

Today, Animesh wanted to practice some of them. Animesh has a really bad habit of trying problems for only a few minutes 
and saying to Malvika "I am a noob, you are a pro. It's some weird shit I don't know. Please, tell me the solution, bro!" 
Having been irritated by this behaviour numerous times in the past, she granted him K special powers he can use before 
starting the practice session. By using a single power, Animesh can pick any two problems irrespective of their difficulty 
from two different contests and swap them.

Animesh has at max time hours before he gets frustrated by his noobness and ends the practice session. He wants to make the 
maximum use of it by getting as much pleasure out of this activity as possible. Animesh also gets bored with the contest themes
fairly quickly, so he does not want to solve more than one problem from any contest. Can you help Animesh in estimating the
maximum amount of pleasure he can achieve out of this activity?
	*/
public class AnimeshPractices {

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

	public static void main(String[] args) {
		InputReader in = new InputReader(System.in);
		int t = in.readInt();

		while (t != 0) {
			int n = in.readInt();
			int k = in.readInt();
			int time = in.readInt();
			int timeArray[][] = new int[n][3];
			int pleasureArray[][] = new int[n][3];
			for (int i = 0; i < n; i++) {
				timeArray[i][0] = in.readInt();
				timeArray[i][1] = in.readInt();
				timeArray[i][2] = in.readInt();
			}

			for (int i = 0; i < n; i++) {
				pleasureArray[i][0] = in.readInt();
				pleasureArray[i][1] = in.readInt();
				pleasureArray[i][2] = in.readInt();
			}

			System.out.println(findSolutionDP(n, k, time, timeArray, pleasureArray));
			t--;
		}

	}

	public static int findSolutionRecursive(int n, int k, int time, int[][] timeArray, int[][] pleasureArray, int count,
			int total) {
		if (n <= 0 || time <= 0 || count >= total)
			return 0;
		int max = 0;

		if (k == 0) {
			int temp = 0;
			for (int i = 0; i < 3; i++) {
				if (time - timeArray[n - 1][i] >= 0)
					temp = pleasureArray[n - 1][i] + findSolutionRecursive(n - 1, 0, time - timeArray[n - 1][i],
							timeArray, pleasureArray, count + 1, total);
				else
					temp = findSolutionRecursive(n - 1, 0, time, timeArray, pleasureArray, count, total);

				if (temp > max)
					max = temp;
			}
		} else if (k == 1) {
			int temp = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = i + 1; j < 3; j++) {
					if (time - timeArray[n - 1][i] - timeArray[n - 1][j] >= 0 && count + 2 <= total)
						temp = pleasureArray[n - 1][i] + pleasureArray[n - 1][j]
								+ findSolutionRecursive(n - 1, 0, time - timeArray[n - 1][i] - timeArray[n - 1][j],
										timeArray, pleasureArray, count + 2, total);
					if (temp > max)
						max = temp;
				}

				if (time - timeArray[n - 1][i] >= 0)
					temp = pleasureArray[n - 1][i] + findSolutionRecursive(n - 1, 1, time - timeArray[n - 1][i],
							timeArray, pleasureArray, count + 1, total);
				else
					temp = findSolutionRecursive(n - 1, 1, time, timeArray, pleasureArray, count, total);

				if (temp > max)
					max = temp;
			}

		} else {
			int temp = 0;

			if (time - timeArray[n - 1][0] - timeArray[n - 1][1] - timeArray[n - 1][2] >= 0 && count + 3 <= total)
				temp = pleasureArray[n - 1][0] + pleasureArray[n - 1][1] + pleasureArray[n - 1][2]
						+ findSolutionRecursive(n - 1, k - 2,
								time - timeArray[n - 1][0] - timeArray[n - 1][1] - timeArray[n - 1][2], timeArray,
								pleasureArray, count + 3, total);

			max = temp;
			for (int i = 0; i < 3; i++) {
				for (int j = i + 1; j < 3; j++) {
					if (time - timeArray[n - 1][i] - timeArray[n - 1][j] >= 0 && count + 3 <= total)
						temp = pleasureArray[n - 1][i] + pleasureArray[n - 1][j]
								+ findSolutionRecursive(n - 1, k - 1, time - timeArray[n - 1][i] - timeArray[n - 1][j],
										timeArray, pleasureArray, count + 2, total);
					if (temp > max)
						max = temp;
				}

				if (time - timeArray[n - 1][i] >= 0)
					temp = pleasureArray[n - 1][i] + findSolutionRecursive(n - 1, k, time - timeArray[n - 1][i],
							timeArray, pleasureArray, count + 1, total);
				else
					temp = findSolutionRecursive(n - 1, k, time, timeArray, pleasureArray, count, total);

				if (temp > max)
					max = temp;
			}
		}
		return max;
	}

	public static int findSolutionDP(int n, int k, int time, int[][] timeArray, int[][] pleasureArray) {
		int dp[][][] = new int[n + 1][time + 1][k + 1];
		int count[][][] = new int[n + 1][time + 1][k + 1];

		int max = 0;
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= time; j++) {

				for (int l = 0; l <= k; l++) {
					if (i == 0 || j == 0) {
						count[i][j][l] = 0;
						dp[i][j][l] = 0;
					} else {
						if (l == 0) {
							dp[i][j][l] = dp[i - 1][j][l];
							count[i][j][l] = count[i - 1][j][l];

							for (int index = 0; index < 3; index++) {
								if (j - timeArray[i - 1][index] >= 0
										&& count[i - 1][j - timeArray[i - 1][index]][0] + 1 <= n) {
									if (dp[i][j][l] < pleasureArray[i - 1][index]
											+ dp[i - 1][j - timeArray[i - 1][index]][0]) {
										dp[i][j][l] = pleasureArray[i - 1][index]
												+ dp[i - 1][j - timeArray[i - 1][index]][0];
										count[i][j][l] = count[i - 1][j - timeArray[i - 1][index]][0] + 1;
									}
								}
							}

							if (max < dp[i][j][l])
								max = dp[i][j][l];
						} else if (l == 1) {
							dp[i][j][l] = dp[i - 1][j][l];
							count[i][j][l] = count[i - 1][j][l];

							for (int index = 0; index < 3; index++) {
								for (int index2 = index + 1; index2 < 3; index2++) {
									if (j - timeArray[i - 1][index] - timeArray[i - 1][index2] >= 0
											&& count[i - 1][j - timeArray[i - 1][index] - timeArray[i - 1][index2]][0]
													+ 2 <= n) {
										if (dp[i][j][l] < pleasureArray[i - 1][index] + pleasureArray[i - 1][index2]
												+ dp[i - 1][j - timeArray[i - 1][index]
														- timeArray[i - 1][index2]][0]) {
											dp[i][j][l] = pleasureArray[i - 1][index] + pleasureArray[i - 1][index2]
													+ dp[i - 1][j - timeArray[i - 1][index]
															- timeArray[i - 1][index2]][0];
											count[i][j][l] = count[i - 1][j - timeArray[i - 1][index]
													- timeArray[i - 1][index2]][0] + 2;
										}
									}
								}
								if (j - timeArray[i - 1][index] >= 0
										&& count[i - 1][j - timeArray[i - 1][index]][1] + 1 <= n) {
									if (dp[i][j][l] < pleasureArray[i - 1][index]
											+ dp[i - 1][j - timeArray[i - 1][index]][1]) {
										dp[i][j][l] = pleasureArray[i - 1][index]
												+ dp[i - 1][j - timeArray[i - 1][index]][1];
										count[i][j][l] = count[i - 1][j - timeArray[i - 1][index]][1] + 1;
									}
								}
								if (max < dp[i][j][l])
									max = dp[i][j][l];
							}
						} else {
							dp[i][j][l] = dp[i - 1][j][l];
							count[i][j][l] = count[i - 1][j][l];
							if (j - timeArray[i - 1][0] - timeArray[i - 1][1] - timeArray[i - 1][2] >= 0
									&& count[i - 1][j - timeArray[i - 1][0] - timeArray[i - 1][1]
											- timeArray[i - 1][2]][l - 2] + 3 <= n) {
								if (dp[i][j][l] < pleasureArray[i - 1][0] + pleasureArray[i - 1][1]
										+ pleasureArray[i - 1][2] + dp[i - 1][j - timeArray[i - 1][0]
												- timeArray[i - 1][1] - timeArray[i - 1][2]][l - 2]) {
									dp[i][j][l] = pleasureArray[i - 1][0] + pleasureArray[i - 1][1]
											+ pleasureArray[i - 1][2] + dp[i - 1][j - timeArray[i - 1][0]
													- timeArray[i - 1][1] - timeArray[i - 1][2]][l - 2];
									count[i][j][l] = count[i - 1][j - timeArray[i - 1][0] - timeArray[i - 1][1]
											- timeArray[i - 1][2]][l - 2] + 3;
								}
							}

							for (int index = 0; index < 3; index++) {
								for (int index2 = index + 1; index2 < 3; index2++) {
									if (j - timeArray[i - 1][index] - timeArray[i - 1][index2] >= 0
											&& count[i - 1][j - timeArray[i - 1][index] - timeArray[i - 1][index2]][l
													- 1] + 2 <= n) {
										if (dp[i][j][l] < pleasureArray[i - 1][index] + pleasureArray[i - 1][index2]
												+ dp[i - 1][j - timeArray[i - 1][index] - timeArray[i - 1][index2]][l
														- 1]) {
											dp[i][j][l] = pleasureArray[i - 1][index] + pleasureArray[i - 1][index2]
													+ dp[i - 1][j - timeArray[i - 1][index]
															- timeArray[i - 1][index2]][l - 1];
											count[i][j][l] = count[i - 1][j - timeArray[i - 1][index]
													- timeArray[i - 1][index2]][l - 1] + 2;
										}
									}
								}
								if (j - timeArray[i - 1][index] >= 0
										&& count[i - 1][j - timeArray[i - 1][index]][l] + 1 <= n) {
									if (dp[i][j][l] < pleasureArray[i - 1][index]
											+ dp[i - 1][j - timeArray[i - 1][index]][l]) {
										dp[i][j][l] = pleasureArray[i - 1][index]
												+ dp[i - 1][j - timeArray[i - 1][index]][l];
										count[i][j][l] = count[i - 1][j - timeArray[i - 1][index]][l] + 1;
									}
								}
							}
							if (max < dp[i][j][l])
								max = dp[i][j][l];
						}
					}
				}
			}
		}
		return max;
	}

}
