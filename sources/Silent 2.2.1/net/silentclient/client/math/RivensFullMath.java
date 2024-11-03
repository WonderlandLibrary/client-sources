package net.silentclient.client.math;

public class RivensFullMath {

    private static final float BF_SIN_TO_COS = 1.5707964F;
    private static final int BF_SIN_BITS = 12;
    private static final int BF_SIN_MASK = ~(-1 << RivensFullMath.BF_SIN_BITS);
    private static final int BF_SIN_COUNT = RivensFullMath.BF_SIN_MASK + 1;
    private static final float BF_radFull = 6.2831855F;
    private static final float BF_radToIndex = (float) RivensFullMath.BF_SIN_COUNT / RivensFullMath.BF_radFull;
    private static final float[] BF_sinFull = new float[RivensFullMath.BF_SIN_COUNT];

    public static float sin(float rad) {
        return RivensFullMath.BF_sinFull[(int) (rad * RivensFullMath.BF_radToIndex) & RivensFullMath.BF_SIN_MASK];
    }

    public static float cos(float rad) {
        return sin(rad + RivensFullMath.BF_SIN_TO_COS);
    }

    static {
        for (int i = 0; i < RivensFullMath.BF_SIN_COUNT; ++i) {
            RivensFullMath.BF_sinFull[i] = (float) Math.sin(((double) i + (double) Math.min(1, i % (RivensFullMath.BF_SIN_COUNT / 4)) * 0.5D) / (double) RivensFullMath.BF_SIN_COUNT * (double) RivensFullMath.BF_radFull);
        }

    }
}
