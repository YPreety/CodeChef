package Easy;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/*Chef is attending a chess competition. There are n people take part in. Each pair of people compete once. Winner earns 1 point, 
loser earns 0 point. A game never ends with a draw. After all games are over, Organizer decide to award prize to each participant.
The prize of each participant is the square of his points. What is all possible total money the organizer will pay for all 
participants? Given k, determine if there is at least one scenario that the total money the organizers will pay is exactly k? 
If there's any, output one of them.
*/
public class WeirdCompetition {

	public static void main(String[] args) {
		PrintWriter out = new PrintWriter(System.out);
		Challenge challenge = readInput();

		for (Iterator<Competition> iterator = challenge.getCompetition().iterator(); iterator.hasNext();) {
			Competition comp = iterator.next();
			Solution s = comp.getSolution();
			s.printSolution(out);
		}
		out.flush();
	}

	private static Challenge readInput() {
		Challenge result = new Challenge();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			StringTokenizer input = new StringTokenizer(br.readLine());
			int T = Integer.parseInt(input.nextToken());
			for (int i = 0; i < T; i++) {
				input = new StringTokenizer(br.readLine());
				int n = Integer.parseInt(input.nextToken());
				int k = Integer.parseInt(input.nextToken());
				Competition comp = new Competition(n, k);
				result.addCompetition(comp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}

final class Boundaries {
	private static final int MAX_N = 1000;
	private static int[] maximum = new int[MAX_N + 1];
	private static int[] minimum = new int[MAX_N + 1];

	private Boundaries() {
		maximum[0] = 1; // last calculated position
	}

	public static int getMaximum(int n) {
		if (n < 2 || n > MAX_N) {
			System.err.println("Invalid value for N");
			return -1;
		}
		if (maximum[n] == 0) {
			fillMaxUntil(n);
		}
		return maximum[n];
	}

	private static void fillMaxUntil(int n) {
		for (int i = maximum[0] + 1; i <= n; i++) {
			maximum[i] = maximum[i - 1] + (i - 1) * (i - 1);
		}
		if (maximum[0] < n) {
			maximum[0] = n;
		}
	}

	public static int getMinimum(int n) {
		if (n < 2 || n > MAX_N) {
			System.err.println("Invalid value for N");
			return -1;
		}
		if (minimum[n] == 0) {
			return fillMin(n);
		} else {
			return minimum[n];
		}

	}

	private static int fillMin(int n) {
		if (n % 2 == 0) {
			minimum[n] = (n * n + (n - 2) * (n - 2)) * n / 8; // ==
																// (n/2)*(n/2)*n/2
																// +
																// ((n-2)/2)*((n-2)/2)*n/2
			return minimum[n];
		} else {
			minimum[n] = (n - 1) * (n - 1) * n / 4;
			return minimum[n];
		}
	}
}

class Challenge {
	private List<Competition> competitions;

	public Challenge() {
		competitions = new ArrayList<>();
	}

	public void addCompetition(Competition comp) {
		this.competitions.add(comp);
	}

	public List<Competition> getCompetition() {
		return competitions;
	}
}

class Competition {
	private int participantsQuantity;
	private int wantedPayment;
	private Solution solution;

	public Competition(int n, int k) {
		setParticipantsQuantity(n);
		setWantedPayment(k);
		solution = new Solution(n, k);
	}

	public int getParticipantsQuantity() {
		return participantsQuantity;
	}

	public void setParticipantsQuantity(int participantsQuantity) {
		this.participantsQuantity = participantsQuantity;
	}

	public int getWantedPayment() {
		return wantedPayment;
	}

	public void setWantedPayment(int wantedPayment) {
		this.wantedPayment = wantedPayment;
	}

	public Solution getSolution() {
		return solution;
	}
}

class Solution {

	private int[][] stateMatrix;
	private int payment;
	int n;
	int k;
	int max;
	int min;
	int nextWinnerChange;
	int nextLoserChange;
	boolean hasSolution;

	public Solution(int participantsQuantity, int wantedPayment) {
		n = participantsQuantity;
		k = wantedPayment;
		max = Boundaries.getMaximum(n);
		min = Boundaries.getMinimum(n);
		nextWinnerChange = 0;
		nextLoserChange = n - 1;
		hasSolution = false;
		stateMatrix = new int[participantsQuantity][participantsQuantity + 2];
		findSolution();
	}

	private boolean isImpossible() {
		if (k > max || k < min) {
			return true;
		}
		if ((max - k) % 2 == 1) { // See suppositions.txt
			return true;
		}
		return false;
	}

	private void findSolution() {
		if (isImpossible()) {
			return;
		}
		firstTrySolution();
		int paymentDifference;
		while (payment != k) {
			paymentDifference = 2 * (-stateMatrix[nextWinnerChange][n] + stateMatrix[nextLoserChange][n] + 1);
			if (k > payment + paymentDifference || paymentDifference > 0) {
				nextLoserChange = n - 1;
				nextWinnerChange++;
			} else {
				stateMatrix[nextWinnerChange][nextLoserChange] = 0;
				stateMatrix[nextLoserChange][nextWinnerChange] = 1;
				stateMatrix[nextWinnerChange][n]--;
				stateMatrix[nextLoserChange][n]++;
				nextLoserChange--;
				payment += paymentDifference;
				if (payment == k) { // Solution found
					hasSolution = true;
				}
			}
		}
	}

	private void firstTrySolution() {
		for (int i = 0; i < n; i++) {
			int j = 0;
			while (j < n) {
				if (j > i) {
					stateMatrix[i][j] = 1;
				}
				j++;
			}
			stateMatrix[i][j] = n - (i + 1);
			stateMatrix[i][j + 1] = -1;
			payment += (n - (i + 1)) * (n - (i + 1));
		}
		if (payment == k) {
			hasSolution = true;
		}
	}

	public void printSolution(PrintWriter out) {
		if (hasSolution) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					out.print(stateMatrix[i][j]);
				}
				out.println();
			}

		} else {
			out.println(-1);
		}

	}
}