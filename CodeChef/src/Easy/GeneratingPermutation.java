package Easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.StringTokenizer;
import java.lang.Math;

/*For a permutation P = (p1, p2, ..., pN) of numbers [1, 2, ..., N], we define the function f(P) = max(p1, p2) + max(p2, p3) + ... 
+ max(pN-1, pN).

You are given N and an integer K. Find and report a permutation P of [1, 2, ..., N] such that f(P) = K, if such a permutation 
exists.

Note f([1]) = 0.
*/
public class GeneratingPermutation {

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		OutputStream out = new BufferedOutputStream(System.out);

		int T = Integer.parseInt(br.readLine());

		long a, b, x, ans, count, t, i;

		for (int j = 0; j < T; j++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			long N = Long.parseLong(st.nextToken());
			long y = Long.parseLong(st.nextToken());
			a = (long) ((Math.pow(N, 2) + N - 2) / 2);
			if (N % 2 == 0)
				b = (long) (.75 * N * N - 1);
			else
				b = (long) (.75 * (N * N - 1));
			t = y - a;
			if (t < 0 || y > b) {

				out.write(("-1").getBytes());
				out.write(("\n").getBytes());
				out.flush();

			} else if (t == 0) {

				out.write(("1").getBytes());

				for (i = 2; i <= N; i++)
					out.write((" " + i).getBytes());

				out.write(("\n").getBytes());

				out.flush();
			} else {
				x = (long) Math.ceil((N - 1 - Math.pow(N * N - 2 * N + 1 - 4 * t, 0.5)) / 2);
				count = x + 1;
				ans = x * N - x * (x + 1) - t;

				out.write(("1").getBytes());
				for (i = x + 2; i <= N - ans - x; i++) {

					out.write(("\n" + i).getBytes());
				}

				out.write((" " + (N - ans - x + 1) + " " + count).getBytes());
				count--;
				for (i = N - ans - x + 2; i <= N + 1 - x; i++) {

					out.write((" " + i).getBytes());
				}
				for (i = N - x + 2; i <= N; i++) {

					out.write((" " + i + " " + count).getBytes());
					count--;
				}

				out.write(("\n").getBytes());
				out.flush();
			}
		}
	}
}