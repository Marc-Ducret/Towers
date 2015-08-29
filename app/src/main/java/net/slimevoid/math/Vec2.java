package net.slimevoid.math;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.round;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Vec2 {

    public static final Vec2 NULL = new Vec2();

    public float x, y;

    public Vec2() {
        this(0, 0);
    }
    
    public Vec2(double a) {
    	this((float)cos(a), (float)sin(a));
    }

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Vec2 add(Vec2 v) {
        return add(v.x, v.y);
    }

    public Vec2 add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vec2 subst(Vec2 v) {
        return subst(v.x, v.y);
    }

    public Vec2 subst(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vec2 mul(double a) {
        x *= a;
        y *= a;
        return this;
    }

    public Vec2 mul(Vec2 a) {
        x *= a.x;
        y *= a.y;
        return this;
    }

    public Vec2 inverse() {
        x = 1 / x;
        y = 1 / y;
        return this;
    }

    public float getNormeSq() {
        return (float) (pow(x, 2) + pow(y, 2));
    }

    public float getNorme() {
        return (float) sqrt(getNormeSq());
    }

    public Vec2 normalize() {
        double n = getNorme();
        if(n == 0) {
            x = 0;
            y = 0;
        } else {
            x /= n;
            y /= n;
        }
        return this;
    }
    
    public Vec2 rotate(double a) {
    	float nx = (float) (x*cos(a) - y*sin(a));
    	y = (float) (x*sin(a) + y*cos(a));
    	x = nx;
    	return this;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void set(Vec2 v) {
		set(v.x, v.y);
	}

    public boolean isInside(Vec2 pos, Vec2 size) {
        return x > pos.x - size.x / 2 && x < pos.x + size.x / 2 && y > pos.y - size.y / 2 && y < pos.y + size.y / 2;
    }

    public void setNul() {
        set(0, 0);
    }

    public Vec2 copy() {
        return new Vec2(x, y);
    }

    public boolean isNul() {
        return x == 0 && y == 0;
    }

    public static double dot(Vec2 u, Vec2 v) {
        Vec2 u0 = u.copy().normalize();
        Vec2 v0 = v.copy().normalize();
        return u0.x * v0.x + u0.y * v0.y;
    }

    @Override
    public String toString() {
//        return "x: "+x+" y: "+y;
    	return toString(2);
    }
    
    public String toString(int precision) {
    	double n = pow(10, precision);
    	return "x: "+round(x*n)/n+" y: "+round(y*n)/n;
    }
    
    public boolean equals(Object obj) {
    	if(obj instanceof Vec2) {
    		Vec2 v = (Vec2) obj;
    		return v.x == x && v.y == y;
    	}
    	return false;
	}
}