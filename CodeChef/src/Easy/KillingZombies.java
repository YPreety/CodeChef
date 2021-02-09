package Easy;

import java.util.*;

/*The zombie apocalypse is here and Chef is stuck at an old construction site. The site is surrounded by walls on three sides. 
On the fourth side is a fence where N zombies are standing in a row. The zombies are numbered from 1 to N from left to right. 
Zombie i has Zi health points initially.

Luckily there are M cranes Chef has access to. The ith crane carries Ki construction beams. Chef can drop any number ≥0 and ≤Ki 
of these beams from crane i and they will hit all zombies whose numbers lie between Li to Ri both inclusive. 
A single beam decreases the health of all zombies it hits by 1 point. If a zombie's health equals 0 after being hit it is 
considered dead.

Can Chef kill all the zombies using the beams? If yes, what is the minimum number of beams that Chef has to drop to achieve 
this?

Warning: Large I/O, it is advisable to use fast I/O methods.
*/
public class KillingZombies {

	public static void main(String args[]) {
		int T;
		Scanner reader = new Scanner(System.in);
		T = reader.nextInt();
		KillZombies cases[] = new KillZombies[T];
		for (int i = 0; i < T; i++) {
			cases[i] = new KillZombies();
			cases[i].takeSituation(reader);

		}
		for (int i = 0; i < T; i++) {
			cases[i].killZombies();
			if (cases[i].numberOfZombiesKilled == cases[i].N)
				System.out.println("YES " + cases[i].numberOfBeams);
			else
				System.out.println("NO");
		}
		reader.close();
	}
}

class Zombie {
	int N;
	long Z[];

	Zombie(int N, long[] Z) {
		this.N = N;
		this.Z = Z;
	}
}

class KillZombies {
	Zombie zombie;
	Crane cranes;
	int N, M;
	long Z[];
	int numberOfZombiesKilled = 0;
	long numberOfBeams = 0;
	List<Crane>[] beams;

	@SuppressWarnings("unchecked")
	void takeSituation(Scanner reader) {
		N = reader.nextInt();
		M = reader.nextInt();
		beams = new ArrayList[N + 1];
		for (int i = 0; i <= N; i++) {
			beams[i] = new ArrayList<>();
		}
		Z = new long[N];
		for (int j = 0; j < N; j++) {
			Z[j] = reader.nextLong();
		}
		zombie = new Zombie(N, Z);
		for (int j = 0; j < M; j++) {
			cranes = new Crane(j, reader.nextInt(), reader.nextInt(), reader.nextLong());
			beams[cranes.L].add(cranes);
		}
	}

	void killZombies() {
		SortedSet<Crane> ts = new TreeSet<>(new Comparator<Crane>() {

			@Override
			public int compare(Crane o1, Crane o2) {
				int kPreferO1 = -1;
				int kPreferO2 = 1;
				if (o1.R > o2.R) {
					return kPreferO1;
				} else if (o1.R < o2.R) {
					return kPreferO2;
				} else {
					if (o1.L < o2.L) {
						return kPreferO1;
					} else if (o1.L > o2.L) {
						return kPreferO2;
					} else {
						return o1.id.compareTo(o2.id);
					}
				}
			}
		});

		for (int i = 0; i < N; i++) {
			ts.addAll(beams[i + 1]);

			while (Z[i] != 0) {
				if (ts.isEmpty() || ts.first().R < (i + 1))
					return;
				long numberOfBeamsThrown = ts.first().K < Z[i] ? ts.first().K : Z[i];
				Crane.throwBeam(Z, ts.first(), numberOfBeamsThrown);
				numberOfBeams += numberOfBeamsThrown;
				if (ts.first().K == 0) {
					ts.remove(ts.first());
				}
			}
			numberOfZombiesKilled++;
		}

	}

}

class Crane {
	Integer id;
	int L, R;
	long K;
	static int cranePointer = 0;

	Crane(Integer id, int L, int R, long K) {
		this.id = id;
		this.L = L;
		this.R = R;
		this.K = K;
	}

	static void throwBeam(long[] Z, Crane crane, long numbOfBeams) {
		for (int i = crane.L; i <= crane.R; i++) {
			Z[i - 1] = Math.max(0, Z[i - 1] - numbOfBeams);

		}
		crane.K = crane.K - numbOfBeams;
	}
}
