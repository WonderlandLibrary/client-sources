package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public final class MathUtils_589431969
{
    private static final Random HorizonCode_Horizon_È;
    
    static {
        HorizonCode_Horizon_È = new Random();
    }
    
    public static Random HorizonCode_Horizon_È() {
        return MathUtils_589431969.HorizonCode_Horizon_È;
    }
    
    public static float Â() {
        return MathUtils_589431969.HorizonCode_Horizon_È.nextFloat();
    }
    
    public static int HorizonCode_Horizon_È(final int cap) {
        return MathUtils_589431969.HorizonCode_Horizon_È.nextInt(cap);
    }
    
    public static int HorizonCode_Horizon_È(final int floor, final int cap) {
        return floor + MathUtils_589431969.HorizonCode_Horizon_È.nextInt(cap - floor + 1);
    }
    
    public static float HorizonCode_Horizon_È(final float value, final float floor, final float cap) {
        if (value < floor) {
            return floor;
        }
        if (value > cap) {
            return cap;
        }
        return value;
    }
    
    public static float HorizonCode_Horizon_È(final String string1, final String string2) {
        final int halflen = Math.min(string1.length(), string2.length()) / 2 + Math.min(string1.length(), string2.length()) % 2;
        final StringBuffer common1 = HorizonCode_Horizon_È(string1, string2, halflen);
        final StringBuffer common2 = HorizonCode_Horizon_È(string2, string1, halflen);
        if (common1.length() == 0 || common2.length() == 0) {
            return 0.0f;
        }
        if (common1.length() != common2.length()) {
            return 0.0f;
        }
        int transpositions = 0;
        for (int n = common1.length(), i = 0; i < n; ++i) {
            if (common1.charAt(i) != common2.charAt(i)) {
                ++transpositions;
            }
        }
        transpositions /= 2;
        return (common1.length() / string1.length() + common2.length() / string2.length() + (common1.length() - transpositions) / common1.length()) / 3.0f;
    }
    
    private static StringBuffer HorizonCode_Horizon_È(final String string1, final String string2, final int distanceSep) {
        final StringBuffer returnCommons = new StringBuffer();
        final StringBuffer copy = new StringBuffer(string2);
        final int n = string1.length();
        final int m = string2.length();
        for (int i = 0; i < n; ++i) {
            final char ch = string1.charAt(i);
            boolean foundIt = false;
            for (int j = Math.max(0, i - distanceSep); !foundIt && j < Math.min(i + distanceSep, m - 1); ++j) {
                if (copy.charAt(j) == ch) {
                    foundIt = true;
                    returnCommons.append(ch);
                    copy.setCharAt(j, '\0');
                }
            }
        }
        return returnCommons;
    }
}
