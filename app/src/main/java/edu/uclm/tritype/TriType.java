package edu.uclm.tritype;

public class TriType {
	private int i, j, k;
	private int trityp;
	
	public static String SCALENE = "Scalene", ISOSCELES = "Isosceles", EQUILATERAL = "Equilateral",
			NOT_A_TRIANGLE = "Not a triangle";

	public TriType() {
	}

	public void setI(int v) {
		i = v;
	}

	public void setJ(int v) {
		j = v;
	}

	public void setK(int v) {
		k = v;
	}

	/**
	 * 
	 * @return 1 if scalene; 2 if isosceles; 3 if equilateral; 4 if not a triangle
	 */
	public String getType() {
		if (i == j) {
			trityp = trityp + 1;
		}
		if (i == k) {
			trityp = trityp + 2;
		}
		if (j == k) {
			trityp = trityp + 3;
		}

		if (i <= 0 || j <= 0 || k <= 0) {
			trityp = 4;
			return NOT_A_TRIANGLE;
		}
		if (trityp == 0) {
			if (i + j <= k || j + k <= i || i + k <= j) {
				return NOT_A_TRIANGLE; 
			}
			return SCALENE;
		}
		if (trityp > 3) {
			return EQUILATERAL; 
		} else if (trityp == 1 && i + j > k) {
			return ISOSCELES;
		} else if (trityp == 2 && i + k > j) {
			return ISOSCELES;
		} else if (trityp == 3 && j + k > i) {
			return ISOSCELES;
		} else {
			return NOT_A_TRIANGLE;
		}
	}
}
