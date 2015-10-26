package net.slimevoid.math;

public class MathUtils {

    public static int pow(int x, int a) {
        if(a == 0) return 1;
        else if(a % 2 == 0) {
            return pow(x*x, a/2);
        }
        else return pow(x, a-1)*x;
    }
}
