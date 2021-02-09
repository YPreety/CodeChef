package Easy;

import java.util.Arrays;
import java.util.Scanner;

public class ChefAndParty {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		while (t-- > 0) {
			int n = sc.nextInt();
			int ar[] = new int[n];
			for (int i = 0; i < n; i++) {
				ar[i] = sc.nextInt();
			}
			Arrays.sort(ar);
			int i = 0;
			for (i = 0; i < n; i++) {
				if (i < ar[i])
					break;
			}
			System.out.println(i);
		}
	}
}
