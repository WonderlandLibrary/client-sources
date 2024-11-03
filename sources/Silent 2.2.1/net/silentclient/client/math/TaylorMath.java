package net.silentclient.client.math;

public class TaylorMath {

    private static final float BF_SIN_TO_COS = 1.5707964F;

    public static float sin(float rad) {
        double x = (double) rad;
        double x2 = x * x;
        double x3 = x2 * x;
        double x5 = x2 * x3;
        double x7 = x2 * x5;
        double x9 = x2 * x7;
        double x11 = x2 * x9;
        double x13 = x2 * x11;
        double x15 = x2 * x13;
        double x17 = x2 * x15;
        double val = x - x3 * 0.16666666666666666D;

        val += x5 * 0.008333333333333333D;
        val -= x7 * 1.984126984126984E-4D;
        val += x9 * 2.7557319223985893E-6D;
        val -= x11 * 2.505210838544172E-8D;
        val += x13 * 1.6059043836821613E-10D;
        val -= x15 * 7.647163731819816E-13D;
        val += x17 * 2.8114572543455206E-15D;
        return (float) val;
    }

    public static float cos(float rad) {
        return sin(rad + TaylorMath.BF_SIN_TO_COS);
    }
}
