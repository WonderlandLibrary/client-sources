/*
 * Decompiled with CFR 0_118.
 */
package optifine;

public class MathUtils {
    public static int getAverage(int[] vals) {
        if (vals.length <= 0) {
            return 0;
        }
        int sum = 0;
        int avg = 0;
        while (avg < vals.length) {
            int val = vals[avg];
            sum += val;
            ++avg;
        }
        avg = sum / vals.length;
        return avg;
    }
}

