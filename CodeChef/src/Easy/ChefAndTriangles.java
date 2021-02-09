package Easy;

import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;

/*Chef has come to a 2 dimensional garden in which there are N points. Each point has coordinates (x, y), where x can either be
1 or 2 or 3. Chef will now choose every triplet of these N points and make a triangle from it. You need to tell the sum of 
areas of all the triangles the Chef makes.

Note that some of the triplets might not form proper triangles, and would end up as a line or a point (ie. degenerate), 
but that is fine because their area will be zero.
*/
public class ChefAndTriangles {

	public static void main(String[] args) {
		InputStream inputStream = System.in;
		OutputStream outputStream = System.out;
		InputReader in = new InputReader(inputStream);
		PrintWriter out = new PrintWriter(outputStream);
		TRICHEF solver = new TRICHEF();
		int testCount = Integer.parseInt(in.next());
		for (int i = 1; i <= testCount; i++)
			solver.solve(i, in, out);
		out.close();
	}

	static class TRICHEF {
		int[] cnt = new int[2000100];

		public void solve(int testNumber, InputReader in, PrintWriter out) {
			int n = in.nextInt();
			ArrayList<ArrayList<Integer>> inp = new ArrayList<>();
			for (int i = 0; i < 3; ++i)
				inp.add(new ArrayList<>());
			for (int i = 0; i < n; ++i) {
				int x = in.nextInt() - 1;
				int y = in.nextInt();
				inp.get(x).add(y);
			}
			for (int i = 0; i < 3; ++i)
				Collections.sort(inp.get(i));
			long totalArea = 0;
			for (int i = 0; i < 3; ++i) {
				long totalSum = 0, prefixSum = 0;
				for (int j = 0; j < inp.get(i).size(); ++j) {
					prefixSum += j * (long) (inp.get(i).get(j) - inp.get(i).get(j > 0 ? j - 1 : j));
					totalSum += prefixSum;
				}
				int[] c = new int[3];
				for (int j = 0; j < 3; ++j)
					c[j] = inp.get(j).size();
				totalArea += totalSum * (long) (n - c[i] + (i == 0 ? c[2] : i == 2 ? c[0] : 0));
			}
			int totalPairs = 0;
			long totalSum = 0;
			Arrays.fill(cnt, 0);
			for (int i = 0; i < inp.get(0).size(); ++i) {
				for (int j = 0; j < inp.get(2).size(); ++j) {
					int c = inp.get(0).get(i) + inp.get(2).get(j);
					cnt[c] += 1;
					totalPairs += 1;
					totalSum += c;
				}
			}

			long prefixSum = 0;
			int prefixCnt = 0;
			for (int i = 0, ii = 0; i < inp.get(1).size(); ++i) {
				int y2 = inp.get(1).get(i) * 2;
				while (ii <= y2) {
					prefixCnt += cnt[ii];
					prefixSum += cnt[ii] * ii;
					++ii;
				}
				totalArea += (y2 * (long) prefixCnt - prefixSum)
						+ (totalSum - prefixSum - (totalPairs - prefixCnt) * (long) y2);
			}
			out.printf("%.1f09\n", totalArea * 0.5);
		}

	}

	static class InputReader {
		public BufferedReader reader;
		public StringTokenizer tokenizer;

		public InputReader(InputStream inputStream) {
			reader = new BufferedReader(new InputStreamReader(inputStream));
			tokenizer = null;
		}

		public InputReader(String inputFile) throws FileNotFoundException {
			reader = new BufferedReader(new FileReader(inputFile));
			tokenizer = null;
		}

		public String nextLine() {
			String curr = "";
			try {
				curr = reader.readLine();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return curr;
		}

		public String next() {
			return nextString();
		}

		public String nextString() {
			while (tokenizer == null || !tokenizer.hasMoreTokens()) {
				try {
					tokenizer = new StringTokenizer(nextLine());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			return tokenizer.nextToken();
		}

		public int nextInt() {
			return Integer.parseInt(nextString());
		}

	}
}