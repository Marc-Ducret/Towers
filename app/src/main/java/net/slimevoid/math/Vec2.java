package net.slimevoid.math;

import static java.lang.Math.sqrt;

public class Vec2 {
	
	public static final Vec2 NULL = new Vec2(0, 0);
	
	private Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public final double x;
	public final double y;
	
	public Vec2 add(Vec2 v) {
		return add(v.x, v.y);
	}
	
	public Vec2 add(double x, double y) {
		return new Vec2(this.x + x, this.y + y);
	}
	
	public Vec2 subst(Vec2 v) {
		return add(-v.x, -v.y);
	}
	
	public Vec2 mul(double a) {
		return new Vec2(x*a, y*a);
	}
	
	public double normeSq() {
		return x*x + y*y;
	}
	
	public double norme() {
		return sqrt(normeSq());
	}
	
	public Vec2 normalize()	{
		return this.mul(1/this.norme());
	}
	
	@Override
	public String toString() {
		return "( "+x+"; "+y+" )";
	}
	
	public static double dsq(Vec2 u, Vec2 v) {
		return u.subst(v).normeSq();
	}
}
