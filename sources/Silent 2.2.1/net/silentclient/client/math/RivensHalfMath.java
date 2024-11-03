package net.silentclient.client.math;

public class RivensHalfMath {

    private static final float BF_SIN_TO_COS = 1.5707964F;
    private static final int BF_SIN_BITS = 12;
    private static final int BF_SIN_MASK = ~(-1 << RivensHalfMath.BF_SIN_BITS);
    private static final int BF_SIN_MASK2 = RivensHalfMath.BF_SIN_MASK >> 1;
    private static final int BF_SIN_COUNT = RivensHalfMath.BF_SIN_MASK + 1;
    private static final int BF_SIN_COUNT2 = RivensHalfMath.BF_SIN_MASK2 + 1;
    private static final float BF_radFull = 6.2831855F;
    private static final float BF_radToIndex = (float) RivensHalfMath.BF_SIN_COUNT / RivensHalfMath.BF_radFull;
    private static final float[] BF_sinHalf = new float[RivensHalfMath.BF_SIN_COUNT2];

    public static float sin(float rad) {
        int index1 = (int) (rad * RivensHalfMath.BF_radToIndex) & RivensHalfMath.BF_SIN_MASK;
        int index2 = index1 & RivensHalfMath.BF_SIN_MASK2;
        int mul = index1 == index2 ? 1 : -1;

        return RivensHalfMath.BF_sinHalf[index2] * (float) mul;
    }

    public static float cos(float rad) {
        return sin(rad + RivensHalfMath.BF_SIN_TO_COS);
    }

    static {
        for (int i = 0; i < RivensHalfMath.BF_SIN_COUNT2; ++i) {
            RivensHalfMath.BF_sinHalf[i] = (float) Math.sin(((double) i + (double) Math.min(1, i % (RivensHalfMath.BF_SIN_COUNT / 4)) * 0.5D) / (double) RivensHalfMath.BF_SIN_COUNT * (double) RivensHalfMath.BF_radFull);
        }

    }
}
