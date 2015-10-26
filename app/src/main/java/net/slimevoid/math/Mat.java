package net.slimevoid.math;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Mat {
	
	public static final Mat ID = new Mat(new double[]{1,0,0, 0,1,0, 0,0,1});
	
	/**
	 * Tableau des coefficients avec la convention suivante :
	 * Ligne1, Ligne2, Ligne3
	 */
	public final double[] m;
	
	public Mat(double[] m) {
		this.m = m;
		if(m.length != 3*3) throw new IllegalArgumentException("Invalid Mat Size");
	}
	
	public static int getId(int i, int j) {
		if(i <= 0 || i > 3 || j <= 0 || j > 3) throw new IllegalArgumentException("Index out of matrix");
		return (j-1) + 3 * (i-1);
	}
	
	public double coef(int i, int j) {
		return m[getId(i, j)];
	}
	
	public Mat add(Mat a) {
		double[] b = new double[3*3];
		for(int i = 0; i < 3*3; i++) {
			b[i] = a.m[i] + this.m[i];
		}
		return new Mat(b);
	}

	public Mat mul(double lambda) {
		double[] b = new double[3*3];
		for(int i = 0; i < 3*3; i++) {
			b[i] = lambda * this.m[i];
		}
		return new Mat(b);
	}

	public Mat mul(Mat a) {
		double[] b = new double[3*3];
		for(int i = 1; i <= 3; i ++) {
			for(int j = 1; j <= 3; j ++) {
				double s = 0;
				for(int k = 1; k <= 3; k ++) {
					s += this.coef(i, k) * a.coef(k, j);
				}
				b[getId(i, j)] = s;
			}
		}
		return new Mat(b);
	}
	
	public Vec2 mul(Vec2 v, double z) {
		return Vec2.NULL.add(v.x * coef(1, 1) + v.y * coef(1, 2) + z * coef(1, 3),
							 v.x * coef(2, 1) + v.y * coef(2, 2) + z * coef(2, 3));
	}
	
	public double det() { // Calcul via le d�veloppement par rapport � la premi�re colonne
		return   coef(1,1)*(coef(2,2)*coef(3,3) - coef(2,3)*coef(3,2))
				-coef(2,1)*(coef(1,2)*coef(3,3) - coef(3,2)*coef(1,3))
				+coef(3,1)*(coef(1,2)*coef(2,3) - coef(2,2)*coef(1,3));
	}
	
	public Mat transpose() {
		double[] b = new double[3*3];
		for(int i = 1; i <= 3; i ++) {
			for(int j = 1; j <= 3; j ++) {
				b[getId(i, j)] = coef(j, i);
			}
		}
		return new Mat(b);
	}
	
	public Mat inv() {
		double d = det();
		double invD = 1/d;
		if(d == 0) throw new IllegalArgumentException("det = 0");
		double[] b = new double[3*3];
		double sign = 1;
		for(int i = 1; i <= 3; i ++) {
			for(int j = 1; j <= 3; j ++) {
				int im = i == 1 ? 2 : 1;
				int iM = i == 3 ? 2 : 3;
				int jm = j == 1 ? 2 : 1;
				int jM = j == 3 ? 2 : 3;
				b[getId(i,j)] = invD * sign * (coef(im,jm)*coef(iM,jM) - coef(iM,jm)*coef(im,jM));
				sign = -sign;
			}
		}	
		return new Mat(b).transpose();
	}
	
	public String shrinkNB(double x, int car) {
		String s = x+"";
		if(s.length() > car) {
			s = s.substring(0, car);
		}
		while(s.length() < car) {
			s += "0";
		}
		return s;
	}
	
	public static Mat trans(Vec2 d) {
		double b[] = new double[3*3];
		b[getId(1, 3)] = d.x;
		b[getId(2, 3)] = d.y;
		return ID.add(new Mat(b));
	}
	
	public static Mat rot(double a) {
		double b[] = new double[3*3];
		b[getId(1, 1)] = cos(a);
		b[getId(1, 2)] = -sin(a);
		b[getId(2, 1)] = sin(a);
		b[getId(2, 2)] = cos(a);
		b[getId(3,3)] = 1;
		return new Mat(b);
	}

	public static Mat scale(double sx, double sy) {
		double b[] = new double[3*3];
		b[getId(1, 1)] = sx;
		b[getId(2, 2)] = sy;
		b[getId(3,3)] = 1;
		return new Mat(b);
	}

	@Override
	public String toString() {
		String s = "[";
		for(int i = 1; i <= 3; i++) {
			if(i != 1) s += " "; 
			for(int j = 1; j <= 3; j++) {
				s += " "+shrinkNB(coef(i, j), 6);
			}
			if(i != 3) s += "\n";
			else s += " ]";
		}
		return s;
	}
}
