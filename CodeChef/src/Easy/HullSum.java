package Easy;

import java.util.Scanner;

/*Chef prepares the following problem for a programming contest:

You are given N points with integer coordinates (-1000 ≤ xi, yi ≤ 1000). No two points collide and no three points are collinear.
For each of the 2N-1 non-empty subsets of points, find the size (the number of points) of its convex hull. Print the sum of
those sizes.

Chef has already prepared some tests for his problem, including a test with the maximum possible answer. He now needs a test
with a quite small answer (but not necessarily the minimum possible one). For given N, your task is to find any valid set of 
N points for which the answer doesn't exceed 4 * 1015 (4,000,000,000,000,000).
*/
public class HullSum {

	private static Point topLeft, bottomLeft, bottomRight;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();

		topLeft = new Point(-1000, 1000);
		System.out.println(topLeft);
		bottomLeft = new Point(-1000, -1000);
		System.out.println(bottomLeft);
		bottomRight = new Point(1000, -1000);
		System.out.println(bottomRight);

		for (int i = 0; i < n - 3; i++) {
			switch (i % 3) {
			case 0:
				bottomLeft.x += (20 + i);
				bottomLeft.y += 20;
				System.out.println(bottomLeft);
				break;
			case 1:
				bottomRight.x -= (40 + i);
				bottomRight.y += 20;
				System.out.println(bottomRight);
				break;
			case 2:
				topLeft.x += 20;
				topLeft.y -= (40 + i);
				System.out.println(topLeft);
				break;
			}
		}
	}

	static class Point {
		int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "" + x + " " + y + "";
		}
	}
}