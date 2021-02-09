package Easy;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;
//Given n and m, calculate 11 + 22 + 33 + ... + nn modulo m.

public class JustSimpleSum {

	public static void main(String[] args) throws Exception {
		Locale.setDefault(Locale.US);
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out, true);
		int N = in.nextInt();
		for (int ii = 0; ii < N; ii++) {
			long n = in.nextLong();
			int m = in.nextInt();
			if (m == 1) {
				out.println(0);
				continue;
			}
			int lim = (int) Math.sqrt(m);
			long partial = 0;
			long partialm = 0;
			for (int i = 0; primes[i] <= lim; i++) {
				int pk = 1;
				while (m % primes[i] == 0) {
					pk *= primes[i];
					m /= primes[i];
				}
				if (pk > 1) {
					long r = primepower(n, pk);
					if (partialm == 0) {
						partialm = pk;
						partial = r;
					} else {
						partial = merge(r * partialm, partialm, partial * pk, pk);
						partialm *= pk;
					}
				}
			}
			if (m > 1) {
				long r = primepower(n, m);
				if (partialm == 0) {
					partial = r;
					partialm = m;
				} else {
					partial = merge(r * partialm, partialm, partial * m, m);
					partialm *= m;
				}
			}
			partial = partial % partialm;
			if (partial < 0) {
				partial += partialm;
			}
			out.println(partial);
		}
		out.close();
	}

	static long modpow(long base, long exponent, long modulus) {
		long result = 1;
		while (exponent > 0) {
			if ((exponent & 1) == 1) {
				result = (result * base) % modulus;
			}
			exponent >>= 1;
			base = (base * base) % modulus;
		}
		return result;
	}

	static int[] primes = new int[] { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73,
			79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191,
			193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311,
			313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439,
			443, 449 };

	static long phi(int n) {
		long result = 1;
		int lim = (int) Math.sqrt(n);
		for (int i = 0; primes[i] <= lim; i++) {
			int pk = 1;
			while (n % primes[i] == 0) {
				pk *= primes[i];
				n /= primes[i];
			}
			if (pk != 1) {
				result *= (pk / primes[i]) * (primes[i] - 1);
			}
		}
		if (n > 1) {
			result *= n - 1;
		}
		return result;
	}

	static long merge(long r1, long m1, long r2, long m2) {
		if (m2 == 0) {
			return r1;
		} else {
			long newm = m1 % m2;
			return merge(r2, m2, r1 - r2 * (m1 / m2), newm);
		}
	}

	static long primepower(long n, long m) {
		long sum = 0;
		long q = n / m;
		long r = n % m;
		for (int i = 1; i < m; i++) {
			long val;
			long x = (i <= r) ? q : (q - 1);
			if (x < 0)
				break;
			long div = modpow(i, m, m) - 1;
			val = modpow(i, i, m);
			if ((i == 1) || (div == 0)) {
				val = val * (x + 1);
			} else {
				if (val != 0) {
					long zum = modpow(i, m * (x + 1), m) - 1;
					long gcd = gcd(div, m);
					long d = div / gcd;
					zum /= gcd;
					int newm = (int) (m / gcd);
					long invd = modpow(d, phi(newm) - 1, newm);
					zum = (zum * invd) % m;
					val = (val * zum) % m;
				}
			}
			sum = (sum + val) % m;
		}
		return sum;
	}

	static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		} else {
			return gcd(b, a % b);
		}
	}

}