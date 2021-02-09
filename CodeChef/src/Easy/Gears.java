package Easy;

import java.io.*;
import java.util.Arrays;

/*You are given N gears numbered 1 through N. For each valid i, gear i has Ai teeth. In the beginning, no gear is connected to 
any other. Your task is to process M queries and simulate the gears' mechanism. There are three types of queries:

Type 1: Change the number of teeth of gear X to C.
Type 2: Connect two gears X and Y.
Type 3: Find the speed of rotation of gear Y if gear X rotates with speed V.
It is known that if gear i is directly connected to gear j and gear i rotates with speed V, then gear j will rotate with speed 
−VAi/Aj, where the sign of rotation speed denotes the direction of rotation (so minus here denotes rotation in the opposite 
		direction). You may also notice that gears can be blocked in some cases. This happens when some gear would have to 
rotate in different directions. If a gear is connected to any blocked gear, it is also blocked. For example, if three gears 
are connected to each other, this configuration can not rotate at all, and if we connect a fourth gear to these three, 
it will also be blocked and not rotate.
*/
public class Gears {

	private static long euclidsGCD(long a, long b) {
		return b == 0 ? a : euclidsGCD(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder outputString = new StringBuilder();

		String data[] = br.readLine().split(" ");

		int nodes = Integer.parseInt(data[0]);
		int queries = Integer.parseInt(data[1]);

		long tooths[] = Arrays.stream(br.readLine().split(" ")).mapToLong(c -> Long.parseLong(c)).toArray();

		DisjointSet disjointSet = new DisjointSet(nodes);

		while (queries-- > 0) {
			data = br.readLine().split(" ");

			int queryType = Integer.parseInt(data[0]);

			if (queryType == 1) {
				int gear = Integer.parseInt(data[1]);
				int value = Integer.parseInt(data[2]);

				tooths[gear - 1] = value;
			} else if (queryType == 2) {
				int gear1 = Integer.parseInt(data[1]);
				int gear2 = Integer.parseInt(data[2]);

				int root1 = disjointSet.find(gear1);
				int root2 = disjointSet.find(gear2);

				if (root1 != root2) {
					if (disjointSet.rank[root1] < disjointSet.rank[root2]) {
						disjointSet.parent[root1] = disjointSet.parent[root2];

						disjointSet.rank[root2] += disjointSet.rank[root1];

						disjointSet.hasCycle[root2] = disjointSet.hasCycle[root2] | disjointSet.hasCycle[root1];

						if (!disjointSet.hasCycle[root2]) {
							int sign1 = disjointSet.findSign(gear1);
							int sign2 = disjointSet.findSign(gear2);

							if (sign1 * disjointSet.signs[gear1] == sign2 * disjointSet.signs[gear2]) {
								disjointSet.invertSign[root1] = -1 * disjointSet.invertSign[root1];
							}
						}
					} else {
						disjointSet.parent[root2] = disjointSet.parent[root1];

						disjointSet.rank[root1] += disjointSet.rank[root2];

						disjointSet.hasCycle[root1] = disjointSet.hasCycle[root1] | disjointSet.hasCycle[root2];

						if (!disjointSet.hasCycle[root1]) {
							int sign1 = disjointSet.findSign(gear1);
							int sign2 = disjointSet.findSign(gear2);

							if (sign1 * disjointSet.signs[gear1] == sign2 * disjointSet.signs[gear2]) {
								disjointSet.invertSign[root2] = -1 * disjointSet.invertSign[root2];
							}
						}
					}
				} else {
					if (disjointSet.hasCycle[root1] == false) {
						int sign1 = disjointSet.findSign(gear1);
						int sign2 = disjointSet.findSign(gear2);

						if (sign1 * disjointSet.signs[gear1] == sign2 * disjointSet.signs[gear2]) {
							disjointSet.hasCycle[root1] = true;
						}
					}
				}
			} else {
				int gear1 = Integer.parseInt(data[1]);
				int gear2 = Integer.parseInt(data[2]);
				long velocity = Long.parseLong(data[3]);

				int root1 = disjointSet.find(gear1);
				int root2 = disjointSet.find(gear2);

				if (root1 != root2) {
					outputString.append(0).append("\n");
				} else {
					if (disjointSet.hasCycle[root1]) {
						outputString.append(0).append("\n");
					} else {
						int sign1 = disjointSet.findSign(gear1);
						int sign2 = disjointSet.findSign(gear2);

						sign1 = sign1 * disjointSet.signs[gear1];
						sign2 = sign2 * disjointSet.signs[gear2];

						long numVelocity = (tooths[gear1 - 1] * velocity);
						long denVelocity = (tooths[gear2 - 1]);

						long gcd = euclidsGCD(numVelocity, denVelocity);

						numVelocity /= gcd;
						denVelocity /= gcd;

						if (sign1 == sign2) {
							outputString.append(numVelocity + "/" + denVelocity).append("\n");
						} else {
							outputString.append("-" + numVelocity + "/" + denVelocity).append("\n");
						}
					}
				}
			}
		}

		bw.write(outputString.toString());
		bw.close();
	}
}

class DisjointSet {
	public int vertices;

	public int parent[];

	public int rank[];

	public boolean hasCycle[];

	public int signs[];

	public int invertSign[];

	public DisjointSet(int vertices) {
		this.vertices = vertices;

		parent = new int[vertices + 1];
		rank = new int[vertices + 1];
		hasCycle = new boolean[vertices + 1];
		signs = new int[vertices + 1];
		invertSign = new int[vertices + 1];

		for (int i = 1; i <= vertices; i++) {
			parent[i] = i;
			rank[i] = 1;
			signs[i] = 1;
			invertSign[i] = 1;
		}
	}

	public int find(int x) {
		if (parent[x] == x) {
			return x;
		} else {
			int result = find(parent[x]);

			return result;
		}
	}

	public int findSign(int x) {
		if (parent[x] == x) {
			return invertSign[x];
		} else {
			int result = invertSign[x] * findSign(parent[x]);

			return result;
		}
	}
}
