package Easy;

import java.util.Scanner;
import java.io.*;
import java.lang.Math;
import java.util.*;

/*Taxis of Kharagpur are famous for making sharp turns. You are given the coordinates where a particular taxi was on a 2-D
planes at N different moments: (x1, y1), (x2, y2), ..., (xN, yN). In between these coordinates, the taxi moves on a straight line.
A turn at the i-th (2 ≤ i ≤ N-1) coordinate is said to be a sharp turn if the angle by which it turns at Point B = (xi, yi) 
when going from coordinates A = (xi-1, yi-1) to C = (xi+1, yi+1) via (xi, yi) is greater than 45 degrees. ie. 
suppose you extend the line segment AB further till a point D, then the angle DBC would be greater than 45 degrees.

You have to identify whether the taxi made a sharp turn somewhere or not (Please look at Output section for details). 
If it made a sharp turn, also identify whether it is possible to change the coordinates at one of the N moments to make 
sure that the taxi doesn't make any sharp turn. Note that all the N pairs of coordinates (including the new coordinates) 
should be integers and distinct and should have their x and y coordinates at least 0 and at most 50.
*/

public class TaxiMakingSharpTurns {

	public int checkcos(double[] x, double[] y, int x1, int y1, int x2, int y2) {
		double a, b, c, d, e, f = 0.7071067811865475;
		double cos[] = new double[4];
		int j = -1;
		for (int i = 0; i < 4; i++) {
			if (!((x[i] == x1 && y[i] == y1) || (x[i] == x2 && y[i] == y2))) {
				a = Math.pow((x2 - x[i]), 2);
				b = Math.pow((x[i] - x1), 2);
				c = Math.pow((y2 - y[i]), 2);
				d = Math.pow((y[i] - y1), 2);
				e = (Math.pow(a + c, 0.5)) * (Math.pow(b + d, 0.5));
				cos[i] = ((x2 - x[i]) * (x[i] - x1) + (y2 - y[i]) * (y[i] - y1)) / e;
				if (Math.abs(cos[i] - f) < Math.pow(10, -6))
					return i;
			} else
				return i;
		}
		return j;
	}

	public boolean find(int k, int[] X, int[] Y, int i, int minx, int miny, int maxx, int maxy) {
		double c, d, e, g, h;
		double cos, cos1, cos2, f = 0.7071067811865475;
		for (int a = minx; a <= maxx; a++) {
			for (int b = miny; b <= maxy; b++) {
				if (!(a < 0 || a > 50 || b < 0 || b > 50 || (a == X[i - 1] && b == Y[i - 1])
						|| (a == X[i + 1] && b == Y[i + 1]))) {
					g = Math.pow((X[i + 1] - a), 2);
					h = Math.pow((a - X[i - 1]), 2);
					c = Math.pow((Y[i + 1] - b), 2);
					d = Math.pow((b - Y[i - 1]), 2);
					e = (Math.pow(g + c, 0.5)) * (Math.pow(h + d, 0.5));
					cos = ((X[i + 1] - a) * (a - X[i - 1]) + (Y[i + 1] - b) * (b - Y[i - 1])) / e;
					if ((cos - f) > -Math.pow(10, -6)) {
						if (k > i + 2) {
							g = Math.pow((X[i + 2] - X[i + 1]), 2);
							h = Math.pow((X[i + 1] - a), 2);
							c = Math.pow((Y[i + 2] - Y[i + 1]), 2);
							d = Math.pow((Y[i + 1] - b), 2);
							e = (Math.pow(g + c, 0.5)) * (Math.pow(h + d, 0.5));
							cos1 = ((X[i + 2] - X[i + 1]) * (X[i + 1] - a) + (Y[i + 2] - Y[i + 1]) * (Y[i + 1] - b))
									/ e;
						} else
							cos1 = f;

						if (i >= 2) {
							g = Math.pow((a - X[i - 1]), 2);
							h = Math.pow((X[i - 1] - X[i - 2]), 2);
							c = Math.pow((b - Y[i - 1]), 2);
							d = Math.pow((Y[i - 1] - Y[i - 2]), 2);
							e = (Math.pow(g + c, 0.5)) * (Math.pow(h + d, 0.5));
							cos2 = ((a - X[i - 1]) * (X[i - 1] - X[i - 2]) + (b - Y[i - 1]) * (Y[i - 1] - Y[i - 2]))
									/ e;
						} else
							cos2 = f;
						if ((cos1 - f) > -Math.pow(10, -6) && (cos2 - f) > -Math.pow(10, -6)) {
							// System.out.println(a);
							// System.out.println(b);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {

		TaxiMakingSharpTurns A = new TaxiMakingSharpTurns();
		Scanner s = new Scanner(System.in);
		int N = s.nextInt();
		double a, b, c, d, e, s1, s2, x1, y1, x2, y2, dis, rad;
		int pos;
		double f = 0.7071067811865475;
		double possx[] = new double[4];
		double possy[] = new double[4];
		boolean ans = false, ans2 = false, ans1 = false;
		// Vector<String> ans= new Vector<String>();

		int X[] = new int[50];
		int Y[] = new int[50];
		double cos[] = new double[50];
		// int a=0;
		for (int j = 0; j < N; j++) {
			Vector<Integer> v = new Vector<Integer>();
			int k = s.nextInt();
			for (int i = 0; i < k; i++) {
				X[i] = s.nextInt();
				Y[i] = s.nextInt();
				// System.out.println("");
			}
			for (int i = 1; i < k - 1; i++) {
				a = Math.pow((X[i + 1] - X[i]), 2);
				b = Math.pow((X[i] - X[i - 1]), 2);
				c = Math.pow((Y[i + 1] - Y[i]), 2);
				d = Math.pow((Y[i] - Y[i - 1]), 2);
				e = (Math.pow(a + c, 0.5)) * (Math.pow(b + d, 0.5));
				cos[i] = ((X[i + 1] - X[i]) * (X[i] - X[i - 1]) + (Y[i + 1] - Y[i]) * (Y[i] - Y[i - 1])) / e;
				if (cos[i] < f)
					v.add(i);
			}
			// System.out.println(cos[1]);
			// System.out.println(v);
			if (v.size() > 3)
				System.out.println("no no");
			else if (v.size() == 2) {
				if ((v.get(0) - v.get(1)) == -1) {
					// System.out.println("no yes"); // DO IT
					if (X[v.get(1)] != X[v.get(0) - 1]) {
						s1 = (float) (Y[v.get(1)] - Y[v.get(0) - 1]) / (X[v.get(1)] - X[v.get(0) - 1]);
						s2 = Math.pow(s1, 2);
						dis = Math.pow(
								Math.pow(Y[v.get(1)] - Y[v.get(0) - 1], 2) + Math.pow(X[v.get(1)] - X[v.get(0) - 1], 2),
								0.5);
						rad = dis / Math.pow(2, 0.5);
						x1 = (float) (X[v.get(1)] + X[v.get(0) - 1]) / 2
								+ (X[v.get(1)] - X[v.get(0) - 1]) * (-s1) / (s2 + 1)
								+ (Y[v.get(1)] - Y[v.get(0) - 1]) * (1 - s2) / (2 * (s2 + 1));
						y2 = (float) (Y[v.get(1)] + Y[v.get(0) - 1]) / 2
								+ (X[v.get(1)] - X[v.get(0) - 1]) * (s2 - 1) / (2 * (s2 + 1))
								+ (Y[v.get(0) - 1] - Y[v.get(1)]) * (s1) / (s2 + 1);
						y1 = (float) (Y[v.get(1)] + Y[v.get(0) - 1]) / 2
								+ (Y[v.get(0) - 1] - Y[v.get(1)]) * (-s1) / (s2 + 1)
								+ (X[v.get(1)] - X[v.get(0) - 1]) * (1 - s2) / (2 * (s2 + 1));
						x2 = (float) (X[v.get(1)] + X[v.get(0) - 1]) / 2
								+ (Y[v.get(1)] - Y[v.get(0) - 1]) * (s2 - 1) / (2 * (s2 + 1))
								+ (X[v.get(1)] - X[v.get(0) - 1]) * (s1) / (s2 + 1);
					} else {
						dis = Math.pow(
								Math.pow(Y[v.get(1)] - Y[v.get(0) - 1], 2) + Math.pow(X[v.get(1)] - X[v.get(0) - 1], 2),
								0.5);
						rad = dis / Math.pow(2, 0.5);
						x1 = (float) (X[v.get(1)] + X[v.get(0) - 1]) / 2 + dis / 2;
						x2 = (float) (X[v.get(1)] + X[v.get(0) - 1]) / 2 - dis / 2;
						y1 = (float) (Y[v.get(1)] + Y[v.get(0) - 1]) / 2;
						y2 = (float) (Y[v.get(1)] + Y[v.get(0) - 1]) / 2;
					}
					possx[0] = x1 + rad;
					possx[1] = x1;
					possx[2] = x1 - rad;
					possx[3] = x1;
					// possy={y1,y1+rad,y1,y1-rad};
					possy[0] = y1;
					possy[1] = y1 + rad;
					possy[2] = y1;
					possy[3] = y1 - rad;

					pos = A.checkcos(possx, possy, X[v.get(0) - 1], Y[v.get(0) - 1], X[v.get(1)], Y[v.get(1)]);

					switch (pos) {
					case 0: // ans=find(X,Y,v.get(1),int minx,int miny,int
							// maxx,int maxy);
						ans = A.find(k, X, Y, v.get(0), (int) Math.ceil(x2 - rad),
								Math.min(Y[v.get(1)], Y[v.get(0) - 1]), (int) Math.floor(x1 + rad),
								Math.max(Y[v.get(1)], Y[v.get(0) - 1]));
						// Math.min(a, b) Math.floor(d)
						break;
					case 1:
						ans = A.find(k, X, Y, v.get(0), Math.min(X[v.get(1)], X[v.get(0) - 1]),
								(int) Math.ceil(y2 - rad), Math.max(X[v.get(1)], X[v.get(0) - 1]),
								(int) Math.floor(y1 + rad));
						break;
					case 2:
						ans = A.find(k, X, Y, v.get(0), (int) Math.ceil(x1 - rad),
								Math.min(Y[v.get(1)], Y[v.get(0) - 1]), (int) Math.floor(x2 + rad),
								Math.max(Y[v.get(1)], Y[v.get(0) - 1]));
						break;
					case 3:
						ans = A.find(k, X, Y, v.get(0), Math.min(X[v.get(1)], X[v.get(0) - 1]),
								(int) Math.ceil(y1 - rad), Math.max(X[v.get(1)], X[v.get(0) - 1]),
								(int) Math.floor(y2 + rad));
						break;
					default:
						System.out.println("WTF");
						break;
					}
					if (ans)
						System.out.println("no yes");
					else {
						// second part
						if (X[v.get(1) + 1] != X[v.get(0)]) {
							s1 = (float) (Y[v.get(1) + 1] - Y[v.get(0)]) / (X[v.get(1) + 1] - X[v.get(0)]);
							s2 = Math.pow(s1, 2);
							dis = Math.pow(Math.pow(Y[v.get(1) + 1] - Y[v.get(0)], 2)
									+ Math.pow(X[v.get(1) + 1] - X[v.get(0)], 2), 0.5);
							rad = dis / Math.pow(2, 0.5);
							x1 = (float) (X[v.get(1) + 1] + X[v.get(0)]) / 2
									+ (X[v.get(1) + 1] - X[v.get(0)]) * (-s1) / (s2 + 1)
									+ (Y[v.get(1) + 1] - Y[v.get(0)]) * (1 - s2) / (2 * (s2 + 1));
							y2 = (float) (Y[v.get(1) + 1] + Y[v.get(0)]) / 2
									+ (X[v.get(1) + 1] - X[v.get(0)]) * (s2 - 1) / (2 * (s2 + 1))
									+ (Y[v.get(0)] - Y[v.get(1) + 1]) * (s1) / (s2 + 1);
							y1 = (float) (Y[v.get(1) + 1] + Y[v.get(0)]) / 2
									+ (Y[v.get(0)] - Y[v.get(1) + 1]) * (-s1) / (s2 + 1)
									+ (X[v.get(1) + 1] - X[v.get(0)]) * (1 - s2) / (2 * (s2 + 1));
							x2 = (float) (X[v.get(1) + 1] + X[v.get(0)]) / 2
									+ (Y[v.get(1) + 1] - Y[v.get(0)]) * (s2 - 1) / (2 * (s2 + 1))
									+ (X[v.get(1) + 1] - X[v.get(0)]) * (s1) / (s2 + 1);
						} else {
							dis = Math.pow(Math.pow(Y[v.get(1) + 1] - Y[v.get(0)], 2)
									+ Math.pow(X[v.get(1) + 1] - X[v.get(0)], 2), 0.5);
							rad = dis / Math.pow(2, 0.5);
							x1 = (float) (X[v.get(1) + 1] + X[v.get(0)]) / 2 + dis / 2;
							x2 = (float) (X[v.get(1) + 1] + X[v.get(0)]) / 2 - dis / 2;
							y1 = (float) (Y[v.get(1) + 1] + Y[v.get(0)]) / 2;
							y2 = (float) (Y[v.get(1) + 1] + Y[v.get(0)]) / 2;
						}
						possx[0] = x1 + rad;
						possx[1] = x1;
						possx[2] = x1 - rad;
						possx[3] = x1;
						// possy={y1,y1+rad,y1,y1-rad};
						possy[0] = y1;
						possy[1] = y1 + rad;
						possy[2] = y1;
						possy[3] = y1 - rad;

						pos = A.checkcos(possx, possy, X[v.get(0)], Y[v.get(0)], X[v.get(1) + 1], Y[v.get(1) + 1]);

						switch (pos) {
						case 0: // ans=find(X,Y,v.get(1),int minx,int miny,int
								// maxx,int maxy);
							ans2 = A.find(k, X, Y, v.get(1), (int) Math.ceil(x2 - rad),
									Math.min(Y[v.get(1) + 1], Y[v.get(0)]), (int) Math.floor(x1 + rad),
									Math.max(Y[v.get(1) + 1], Y[v.get(0)]));
							// Math.min(a, b) Math.floor(d)
							break;
						case 1:
							ans2 = A.find(k, X, Y, v.get(1), Math.min(X[v.get(1) + 1], X[v.get(0)]),
									(int) Math.ceil(y2 - rad), Math.max(X[v.get(1) + 1], X[v.get(0)]),
									(int) Math.floor(y1 + rad));
							break;
						case 2:
							ans2 = A.find(k, X, Y, v.get(1), (int) Math.ceil(x1 - rad),
									Math.min(Y[v.get(1) + 1], Y[v.get(0)]), (int) Math.floor(x2 + rad),
									Math.max(Y[v.get(1) + 1], Y[v.get(0)]));
							break;
						case 3:
							ans2 = A.find(k, X, Y, v.get(1), Math.min(X[v.get(1) + 1], X[v.get(0)]),
									(int) Math.ceil(y1 - rad), Math.max(X[v.get(1) + 1], X[v.get(0)]),
									(int) Math.floor(y2 + rad));
							break;
						default:
							System.out.println("WTF");
							break;
						}
						if (ans2)
							System.out.println("no yes");
						else
							System.out.println("no no");
					}
				}

				else if ((v.get(0) - v.get(1)) == -2) {
					if (X[v.get(1)] != X[v.get(0)]) {
						s1 = (float) (Y[v.get(1)] - Y[v.get(0)]) / (X[v.get(1)] - X[v.get(0)]);
						// System.out.println("no yes");
						s2 = Math.pow(s1, 2);
						dis = Math.pow(Math.pow(Y[v.get(1)] - Y[v.get(0)], 2) + Math.pow(X[v.get(1)] - X[v.get(0)], 2),
								0.5);
						rad = dis / Math.pow(2, 0.5);
						x1 = (float) (X[v.get(1)] + X[v.get(0)]) / 2 + (X[v.get(1)] - X[v.get(0)]) * (-s1) / (s2 + 1)
								+ (Y[v.get(1)] - Y[v.get(0)]) * (1 - s2) / (2 * (s2 + 1));
						y2 = (float) (Y[v.get(1)] + Y[v.get(0)]) / 2
								+ (X[v.get(1)] - X[v.get(0)]) * (s2 - 1) / (2 * (s2 + 1))
								+ (Y[v.get(0)] - Y[v.get(1)]) * (s1) / (s2 + 1);
						y1 = (float) (Y[v.get(1)] + Y[v.get(0)]) / 2 + (Y[v.get(0)] - Y[v.get(1)]) * (-s1) / (s2 + 1)
								+ (X[v.get(1)] - X[v.get(0)]) * (1 - s2) / (2 * (s2 + 1));
						x2 = (float) (X[v.get(1)] + X[v.get(0)]) / 2
								+ (Y[v.get(1)] - Y[v.get(0)]) * (s2 - 1) / (2 * (s2 + 1))
								+ (X[v.get(1)] - X[v.get(0)]) * (s1) / (s2 + 1);
					} else {
						dis = Math.pow(Math.pow(Y[v.get(1)] - Y[v.get(0)], 2) + Math.pow(X[v.get(1)] - X[v.get(0)], 2),
								0.5);
						rad = dis / Math.pow(2, 0.5);
						x1 = (float) (X[v.get(1)] + X[v.get(0)]) / 2 + dis / 2;
						x2 = (float) (X[v.get(1)] + X[v.get(0)]) / 2 - dis / 2;
						y1 = (float) (Y[v.get(1)] + Y[v.get(0)]) / 2;
						y2 = (float) (Y[v.get(1)] + Y[v.get(0)]) / 2;
					}

					possx[0] = x1 + rad;
					possx[1] = x1;
					possx[2] = x1 - rad;
					possx[3] = x1;
					// possy={y1,y1+rad,y1,y1-rad};
					possy[0] = y1;
					possy[1] = y1 + rad;
					possy[2] = y1;
					possy[3] = y1 - rad;

					pos = A.checkcos(possx, possy, X[v.get(0)], Y[v.get(0)], X[v.get(1)], Y[v.get(1)]);

					switch (pos) {
					case 0: // ans=find(X,Y,v.get(1),int minx,int miny,int
							// maxx,int maxy);
						ans = A.find(k, X, Y, v.get(1) - 1, (int) Math.ceil(x2 - rad),
								Math.min(Y[v.get(1)], Y[v.get(0)]), (int) Math.floor(x1 + rad),
								Math.max(Y[v.get(1)], Y[v.get(0)]));
						// Math.min(a, b) Math.floor(d)
						break;
					case 1:
						ans = A.find(k, X, Y, v.get(1) - 1, Math.min(X[v.get(1)], X[v.get(0)]),
								(int) Math.ceil(y2 - rad), Math.max(X[v.get(1)], X[v.get(0)]),
								(int) Math.floor(y1 + rad));
						break;
					case 2:
						ans = A.find(k, X, Y, v.get(1) - 1, (int) Math.ceil(x1 - rad),
								Math.min(Y[v.get(1)], Y[v.get(0)]), (int) Math.floor(x2 + rad),
								Math.max(Y[v.get(1)], Y[v.get(0)]));
						break;
					case 3:
						ans = A.find(k, X, Y, v.get(1) - 1, Math.min(X[v.get(1)], X[v.get(0)]),
								(int) Math.ceil(y1 - rad), Math.max(X[v.get(1)], X[v.get(0)]),
								(int) Math.floor(y2 + rad));
						break;
					default:
						System.out.println("WTF");
						break;
					}
					if (ans)
						System.out.println("no yes");
					else
						System.out.println("no no");
				}

				else
					System.out.println("no no");
			} else if (v.size() == 3) {
				if ((v.get(0) - v.get(1)) == -1 && (v.get(1) - v.get(2)) == -1) {
					if (X[v.get(2)] != X[v.get(0)]) {
						s1 = (float) (Y[v.get(2)] - Y[v.get(0)]) / (X[v.get(2)] - X[v.get(0)]);
						s2 = Math.pow(s1, 2);
						dis = Math.pow(Math.pow(Y[v.get(2)] - Y[v.get(0)], 2) + Math.pow(X[v.get(2)] - X[v.get(0)], 2),
								0.5);
						rad = dis / Math.pow(2, 0.5);
						x1 = (float) (X[v.get(2)] + X[v.get(0)]) / 2 + (X[v.get(2)] - X[v.get(0)]) * (-s1) / (s2 + 1)
								+ (Y[v.get(2)] - Y[v.get(0)]) * (1 - s2) / (2 * (s2 + 1));
						y2 = (float) (Y[v.get(2)] + Y[v.get(0)]) / 2
								+ (X[v.get(2)] - X[v.get(0)]) * (s2 - 1) / (2 * (s2 + 1))
								+ (Y[v.get(0)] - Y[v.get(2)]) * (s1) / (s2 + 1);
						y1 = (float) (Y[v.get(2)] + Y[v.get(0)]) / 2 + (Y[v.get(0)] - Y[v.get(2)]) * (-s1) / (s2 + 1)
								+ (X[v.get(2)] - X[v.get(0)]) * (1 - s2) / (2 * (s2 + 1));
						x2 = (float) (X[v.get(2)] + X[v.get(0)]) / 2
								+ (Y[v.get(2)] - Y[v.get(0)]) * (s2 - 1) / (2 * (s2 + 1))
								+ (X[v.get(2)] - X[v.get(0)]) * (s1) / (s2 + 1);
						// possx={x1+rad,x1,x1-rad,x1};
					} else {
						dis = Math.pow(Math.pow(Y[v.get(2)] - Y[v.get(0)], 2) + Math.pow(X[v.get(2)] - X[v.get(0)], 2),
								0.5);
						rad = dis / Math.pow(2, 0.5);
						x1 = (float) (X[v.get(2)] + X[v.get(0)]) / 2 + dis / 2;
						x2 = (float) (X[v.get(2)] + X[v.get(0)]) / 2 - dis / 2;
						y1 = (float) (Y[v.get(2)] + Y[v.get(0)]) / 2;
						y2 = (float) (Y[v.get(2)] + Y[v.get(0)]) / 2;
					}
					possx[0] = x1 + rad;
					possx[1] = x1;
					possx[2] = x1 - rad;
					possx[3] = x1;
					// possy={y1,y1+rad,y1,y1-rad};
					possy[0] = y1;
					possy[1] = y1 + rad;
					possy[2] = y1;
					possy[3] = y1 - rad;

					pos = A.checkcos(possx, possy, X[v.get(0)], Y[v.get(0)], X[v.get(2)], Y[v.get(2)]);

					switch (pos) {
					case 0: // ans=find(X,Y,v.get(1),int minx,int miny,int
							// maxx,int maxy);
						ans = A.find(k, X, Y, v.get(1), (int) Math.ceil(x2 - rad), Math.min(Y[v.get(2)], Y[v.get(0)]),
								(int) Math.floor(x1 + rad), Math.max(Y[v.get(2)], Y[v.get(0)]));
						// Math.min(a, b) Math.floor(d)
						break;
					case 1:
						ans = A.find(k, X, Y, v.get(1), Math.min(X[v.get(2)], X[v.get(0)]), (int) Math.ceil(y2 - rad),
								Math.max(X[v.get(2)], X[v.get(0)]), (int) Math.floor(y1 + rad));
						break;
					case 2:
						ans = A.find(k, X, Y, v.get(1), (int) Math.ceil(x1 - rad), Math.min(Y[v.get(2)], Y[v.get(0)]),
								(int) Math.floor(x2 + rad), Math.max(Y[v.get(2)], Y[v.get(0)]));
						break;
					case 3:
						ans = A.find(k, X, Y, v.get(1), Math.min(X[v.get(2)], X[v.get(0)]), (int) Math.ceil(y1 - rad),
								Math.max(X[v.get(2)], X[v.get(0)]), (int) Math.floor(y2 + rad));
						break;
					default:
						System.out.println("WTF");
						break;
					}
					if (ans)
						System.out.println("no yes");
					else
						System.out.println("no no");
				} else
					System.out.println("no no");
			} else if (v.size() == 1) {
				if (v.get(0) == 1 || v.get(0) == k - 2)
					System.out.println("no yes");
				else {
					if (X[v.get(0) + 1] != X[v.get(0) - 1]) {
						s1 = (float) (Y[v.get(0) + 1] - Y[v.get(0) - 1]) / (X[v.get(0) + 1] - X[v.get(0) - 1]);
						s2 = Math.pow(s1, 2);

						dis = Math.pow(Math.pow(Y[v.get(0) + 1] - Y[v.get(0) - 1], 2)
								+ Math.pow(X[v.get(0) + 1] - X[v.get(0) - 1], 2), 0.5);
						rad = dis / Math.pow(2, 0.5);

						x1 = (float) (X[v.get(0) + 1] + X[v.get(0) - 1]) / 2
								+ (X[v.get(0) + 1] - X[v.get(0) - 1]) * (-s1) / (s2 + 1)
								+ (Y[v.get(0) + 1] - Y[v.get(0) - 1]) * (1 - s2) / (2 * (s2 + 1));
						y2 = (float) (Y[v.get(0) + 1] + Y[v.get(0) - 1]) / 2
								+ (X[v.get(0) + 1] - X[v.get(0) - 1]) * (s2 - 1) / (2 * (s2 + 1))
								+ (Y[v.get(0) - 1] - Y[v.get(0) + 1]) * (s1) / (s2 + 1);
						y1 = (float) (Y[v.get(0) + 1] + Y[v.get(0) - 1]) / 2
								+ (Y[v.get(0) - 1] - Y[v.get(0) + 1]) * (-s1) / (s2 + 1)
								+ (X[v.get(0) + 1] - X[v.get(0) - 1]) * (1 - s2) / (2 * (s2 + 1));
						x2 = (float) (X[v.get(0) + 1] + X[v.get(0) - 1]) / 2
								+ (Y[v.get(0) + 1] - Y[v.get(0) - 1]) * (s2 - 1) / (2 * (s2 + 1))
								+ (X[v.get(0) + 1] - X[v.get(0) - 1]) * (s1) / (s2 + 1);
						/*
						 * x1=(float)(X[v.get(2)] + X[v.get(0)])/2 +
						 * (X[v.get(2)] - X[v.get(0)])*(-s1)/(s2+1) +
						 * (Y[v.get(2)] - Y[v.get(0)])*(1-s2)/(2*(s2+1));
						 * y2=(float)(Y[v.get(2)] + Y[v.get(0)])/2 +
						 * (X[v.get(2)] - X[v.get(0)])*(s2-1)/(2*(s2+1)) +
						 * (Y[v.get(0)] - Y[v.get(2)])*(s1)/(s2+1);
						 * y1=(float)(Y[v.get(2)] + Y[v.get(0)])/2 +
						 * (Y[v.get(0)] - Y[v.get(2)])*(-s1)/(s2+1) +
						 * (X[v.get(2)] - X[v.get(0)])*(1-s2)/(2*(s2+1));
						 * x2=(float)(X[v.get(2)] + X[v.get(0)])/2 +
						 * (Y[v.get(2)] - Y[v.get(0)])*(s2-1)/(2*(s2+1)) +
						 * (X[v.get(2)] - X[v.get(0)])*(s1)/(s2+1);
						 */
					} else {
						dis = Math.pow(Math.pow(Y[v.get(0) + 1] - Y[v.get(0) - 1], 2)
								+ Math.pow(X[v.get(0) + 1] - X[v.get(0) - 1], 2), 0.5);
						rad = dis / Math.pow(2, 0.5);
						x1 = (float) (X[v.get(0) + 1] + X[v.get(0) - 1]) / 2 + dis / 2;
						x2 = (float) (X[v.get(0) + 1] + X[v.get(0) - 1]) / 2 - dis / 2;
						y1 = (float) (Y[v.get(0) + 1] + Y[v.get(0) - 1]) / 2;
						y2 = (float) (Y[v.get(0) + 1] + Y[v.get(0) - 1]) / 2;
					}
					possx[0] = x1 + rad;
					possx[1] = x1;
					possx[2] = x1 - rad;
					possx[3] = x1;
					// possy={y1,y1+rad,y1,y1-rad};
					possy[0] = y1;
					possy[1] = y1 + rad;
					possy[2] = y1;
					possy[3] = y1 - rad;

					pos = A.checkcos(possx, possy, X[v.get(0) - 1], Y[v.get(0) - 1], X[v.get(0) + 1], Y[v.get(0) + 1]);

					switch (pos) {
					case 0: // ans=find(X,Y,v.get(1),int minx,int miny,int
							// maxx,int maxy);
						ans = A.find(k, X, Y, v.get(0), (int) Math.ceil(x2 - rad),
								Math.min(Y[v.get(0) + 1], Y[v.get(0) - 1]), (int) Math.floor(x1 + rad),
								Math.max(Y[v.get(0) + 1], Y[v.get(0) - 1]));
						// Math.min(a, b) Math.floor(d)
						break;
					case 1:
						ans = A.find(k, X, Y, v.get(0), Math.min(X[v.get(0) + 1], X[v.get(0) - 1]),
								(int) Math.ceil(y2 - rad), Math.max(X[v.get(0) + 1], X[v.get(0) - 1]),
								(int) Math.floor(y1 + rad));
						break;
					case 2:
						ans = A.find(k, X, Y, v.get(0), (int) Math.ceil(x1 - rad),
								Math.min(Y[v.get(0) + 1], Y[v.get(0) - 1]), (int) Math.floor(x2 + rad),
								Math.max(Y[v.get(0) + 1], Y[v.get(0) - 1]));
						break;
					case 3:
						ans = A.find(k, X, Y, v.get(0), Math.min(X[v.get(0) + 1], X[v.get(0) - 1]),
								(int) Math.ceil(y1 - rad), Math.max(X[v.get(0) + 1], X[v.get(0) - 1]),
								(int) Math.floor(y2 + rad));
						break;
					default:
						System.out.println("WTF");
						break;
					}

					if (ans)
						System.out.println("no yes");
					else {
						// second part
						if (X[v.get(0)] != X[v.get(0) - 2]) {
							s1 = (float) (Y[v.get(0)] - Y[v.get(0) - 2]) / (X[v.get(0)] - X[v.get(0) - 2]);
							s2 = Math.pow(s1, 2);

							dis = Math.pow(Math.pow(Y[v.get(0)] - Y[v.get(0) - 2], 2)
									+ Math.pow(X[v.get(0)] - X[v.get(0) - 2], 2), 0.5);
							rad = dis / Math.pow(2, 0.5);

							x1 = (float) (X[v.get(0)] + X[v.get(0) - 2]) / 2
									+ (X[v.get(0)] - X[v.get(0) - 2]) * (-s1) / (s2 + 1)
									+ (Y[v.get(0)] - Y[v.get(0) - 2]) * (1 - s2) / (2 * (s2 + 1));
							y2 = (float) (Y[v.get(0)] + Y[v.get(0) - 2]) / 2
									+ (X[v.get(0)] - X[v.get(0) - 2]) * (s2 - 1) / (2 * (s2 + 1))
									+ (Y[v.get(0) - 2] - Y[v.get(0)]) * (s1) / (s2 + 1);
							y1 = (float) (Y[v.get(0)] + Y[v.get(0) - 2]) / 2
									+ (Y[v.get(0) - 2] - Y[v.get(0)]) * (-s1) / (s2 + 1)
									+ (X[v.get(0)] - X[v.get(0) - 2]) * (1 - s2) / (2 * (s2 + 1));
							x2 = (float) (X[v.get(0)] + X[v.get(0) - 2]) / 2
									+ (Y[v.get(0)] - Y[v.get(0) - 2]) * (s2 - 1) / (2 * (s2 + 1))
									+ (X[v.get(0)] - X[v.get(0) - 2]) * (s1) / (s2 + 1);
							/*
							 * x1=(float)(X[v.get(2)] + X[v.get(0)])/2 +
							 * (X[v.get(2)] - X[v.get(0)])*(-s1)/(s2+1) +
							 * (Y[v.get(2)] - Y[v.get(0)])*(1-s2)/(2*(s2+1));
							 * y2=(float)(Y[v.get(2)] + Y[v.get(0)])/2 +
							 * (X[v.get(2)] - X[v.get(0)])*(s2-1)/(2*(s2+1)) +
							 * (Y[v.get(0)] - Y[v.get(2)])*(s1)/(s2+1);
							 * y1=(float)(Y[v.get(2)] + Y[v.get(0)])/2 +
							 * (Y[v.get(0)] - Y[v.get(2)])*(-s1)/(s2+1) +
							 * (X[v.get(2)] - X[v.get(0)])*(1-s2)/(2*(s2+1));
							 * x2=(float)(X[v.get(2)] + X[v.get(0)])/2 +
							 * (Y[v.get(2)] - Y[v.get(0)])*(s2-1)/(2*(s2+1)) +
							 * (X[v.get(2)] - X[v.get(0)])*(s1)/(s2+1);
							 */
						} else {
							dis = Math.pow(Math.pow(Y[v.get(0)] - Y[v.get(0) - 2], 2)
									+ Math.pow(X[v.get(0)] - X[v.get(0) - 2], 2), 0.5);
							rad = dis / Math.pow(2, 0.5);
							x1 = (float) (X[v.get(0)] + X[v.get(0) - 2]) / 2 + dis / 2;
							x2 = (float) (X[v.get(0)] + X[v.get(0) - 2]) / 2 - dis / 2;
							y1 = (float) (Y[v.get(0)] + Y[v.get(0) - 2]) / 2;
							y2 = (float) (Y[v.get(0)] + Y[v.get(0) - 2]) / 2;
						}

						possx[0] = x1 + rad;
						possx[1] = x1;
						possx[2] = x1 - rad;
						possx[3] = x1;
						// possy={y1,y1+rad,y1,y1-rad};
						possy[0] = y1;
						possy[1] = y1 + rad;
						possy[2] = y1;
						possy[3] = y1 - rad;
						// System.out.println("no no");
						pos = A.checkcos(possx, possy, X[v.get(0) - 2], Y[v.get(0) - 2], X[v.get(0)], Y[v.get(0)]);

						switch (pos) {
						case 0: // ans=find(X,Y,v.get(1),int minx,int miny,int
								// maxx,int maxy);
							ans1 = A.find(k, X, Y, v.get(0) - 1, (int) Math.ceil(x2 - rad),
									Math.min(Y[v.get(0)], Y[v.get(0) - 2]), (int) Math.floor(x1 + rad),
									Math.max(Y[v.get(0)], Y[v.get(0) - 2]));
							// Math.min(a, b) Math.floor(d)
							break;
						case 1:
							ans1 = A.find(k, X, Y, v.get(0) - 1, Math.min(X[v.get(0)], X[v.get(0) - 2]),
									(int) Math.ceil(y2 - rad), Math.max(X[v.get(0)], X[v.get(0) - 2]),
									(int) Math.floor(y1 + rad));
							break;
						case 2:
							ans1 = A.find(k, X, Y, v.get(0) - 1, (int) Math.ceil(x1 - rad),
									Math.min(Y[v.get(0)], Y[v.get(0) - 2]), (int) Math.floor(x2 + rad),
									Math.max(Y[v.get(0)], Y[v.get(0) - 2]));
							break;
						case 3:
							ans1 = A.find(k, X, Y, v.get(0) - 1, Math.min(X[v.get(0)], X[v.get(0) - 2]),
									(int) Math.ceil(y1 - rad), Math.max(X[v.get(0)], X[v.get(0) - 2]),
									(int) Math.floor(y2 + rad));
							break;
						default:
							System.out.println("WTF");
							break;
						}

						if (ans1)
							System.out.println("no yes");
						else {
							// third part
							if (X[v.get(0) + 2] != X[v.get(0)]) {
								s1 = (float) (Y[v.get(0) + 2] - Y[v.get(0)]) / (X[v.get(0) + 2] - X[v.get(0)]);
								s2 = Math.pow(s1, 2);
								dis = Math.pow(Math.pow(Y[v.get(0) + 2] - Y[v.get(0)], 2)
										+ Math.pow(X[v.get(0) + 2] - X[v.get(0)], 2), 0.5);
								rad = dis / Math.pow(2, 0.5);
								x1 = (float) (X[v.get(0) + 2] + X[v.get(0)]) / 2
										+ (X[v.get(0) + 2] - X[v.get(0)]) * (-s1) / (s2 + 1)
										+ (Y[v.get(0) + 2] - Y[v.get(0)]) * (1 - s2) / (2 * (s2 + 1));
								y2 = (float) (Y[v.get(0) + 2] + Y[v.get(0)]) / 2
										+ (X[v.get(0) + 2] - X[v.get(0)]) * (s2 - 1) / (2 * (s2 + 1))
										+ (Y[v.get(0)] - Y[v.get(0) + 2]) * (s1) / (s2 + 1);
								y1 = (float) (Y[v.get(0) + 2] + Y[v.get(0)]) / 2
										+ (Y[v.get(0)] - Y[v.get(0) + 2]) * (-s1) / (s2 + 1)
										+ (X[v.get(0) + 2] - X[v.get(0)]) * (1 - s2) / (2 * (s2 + 1));
								x2 = (float) (X[v.get(0) + 2] + X[v.get(0)]) / 2
										+ (Y[v.get(0) + 2] - Y[v.get(0)]) * (s2 - 1) / (2 * (s2 + 1))
										+ (X[v.get(0) + 2] - X[v.get(0)]) * (s1) / (s2 + 1);
								/*
								 * x1=(float)(X[v.get(2)] + X[v.get(0)])/2 +
								 * (X[v.get(2)] - X[v.get(0)])*(-s1)/(s2+1) +
								 * (Y[v.get(2)] -
								 * Y[v.get(0)])*(1-s2)/(2*(s2+1));
								 * y2=(float)(Y[v.get(2)] + Y[v.get(0)])/2 +
								 * (X[v.get(2)] - X[v.get(0)])*(s2-1)/(2*(s2+1))
								 * + (Y[v.get(0)] - Y[v.get(2)])*(s1)/(s2+1);
								 * y1=(float)(Y[v.get(2)] + Y[v.get(0)])/2 +
								 * (Y[v.get(0)] - Y[v.get(2)])*(-s1)/(s2+1) +
								 * (X[v.get(2)] -
								 * X[v.get(0)])*(1-s2)/(2*(s2+1));
								 * x2=(float)(X[v.get(2)] + X[v.get(0)])/2 +
								 * (Y[v.get(2)] - Y[v.get(0)])*(s2-1)/(2*(s2+1))
								 * + (X[v.get(2)] - X[v.get(0)])*(s1)/(s2+1);
								 */
							} else {
								dis = Math.pow(Math.pow(Y[v.get(0) + 2] - Y[v.get(0)], 2)
										+ Math.pow(X[v.get(0) + 2] - X[v.get(0)], 2), 0.5);
								rad = dis / Math.pow(2, 0.5);
								x1 = (float) (X[v.get(0) + 2] + X[v.get(0)]) / 2 + dis / 2;
								x2 = (float) (X[v.get(0) + 2] + X[v.get(0)]) / 2 - dis / 2;
								y1 = (float) (Y[v.get(0) + 2] + Y[v.get(0)]) / 2;
								y2 = (float) (Y[v.get(0) + 2] + Y[v.get(0)]) / 2;
							}
							possx[0] = x1 + rad;
							possx[1] = x1;
							possx[2] = x1 - rad;
							possx[3] = x1;
							// possy={y1,y1+rad,y1,y1-rad};
							possy[0] = y1;
							possy[1] = y1 + rad;
							possy[2] = y1;
							possy[3] = y1 - rad;
							// System.out.println("no no");
							pos = A.checkcos(possx, possy, X[v.get(0)], Y[v.get(0)], X[v.get(0) + 2], Y[v.get(0) + 2]);
							// System.out.println(y1);
							switch (pos) {
							case 0: // ans=find(X,Y,v.get(1),int minx,int
									// miny,int maxx,int maxy);
								ans2 = A.find(k, X, Y, v.get(0) + 1, (int) Math.ceil(x2 - rad),
										Math.min(Y[v.get(0) + 2], Y[v.get(0)]), (int) Math.floor(x1 + rad),
										Math.max(Y[v.get(0) + 2], Y[v.get(0)]));
								// Math.min(a, b) Math.floor(d)
								break;
							case 1:
								ans2 = A.find(k, X, Y, v.get(0) + 1, Math.min(X[v.get(0) + 2], X[v.get(0)]),
										(int) Math.ceil(y2 - rad), Math.max(X[v.get(0) + 2], X[v.get(0)]),
										(int) Math.floor(y1 + rad));
								break;
							case 2:
								ans2 = A.find(k, X, Y, v.get(0) + 1, (int) Math.ceil(x1 - rad),
										Math.min(Y[v.get(0) + 2], Y[v.get(0)]), (int) Math.floor(x2 + rad),
										Math.max(Y[v.get(0) + 2], Y[v.get(0)]));
								break;
							case 3:
								ans2 = A.find(k, X, Y, v.get(0) + 1, Math.min(X[v.get(0) + 2], X[v.get(0)]),
										(int) Math.ceil(y1 - rad), Math.max(X[v.get(0) + 2], X[v.get(0)]),
										(int) Math.floor(y2 + rad));
								break;
							default:
								System.out.println("WTF");
								break;
							}

							if (ans2)
								System.out.println("no yes");
							else
								System.out.println("no no");
						}
					}

				} // else part of size =1

			} else
				System.out.println("yes yes");
		}
	}
}
