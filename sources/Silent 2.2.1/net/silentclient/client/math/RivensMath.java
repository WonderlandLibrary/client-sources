package net.silentclient.client.math;

public class RivensMath {

    private static final int BF_SIN_BITS = 12;
    private static final int BF_SIN_MASK = ~(-1 << RivensMath.BF_SIN_BITS);
    private static final int BF_SIN_COUNT = RivensMath.BF_SIN_MASK + 1;
    private static final float BF_radFull = 6.2831855F;
    private static final float BF_radToIndex = (float) RivensMath.BF_SIN_COUNT / RivensMath.BF_radFull;
    private static final float BF_degFull = 360.0F;
    private static final float BF_degToIndex = (float) RivensMath.BF_SIN_COUNT / RivensMath.BF_degFull;
    private static final float[] BF_sin = new float[RivensMath.BF_SIN_COUNT];
    private static final float[] BF_cos = new float[RivensMath.BF_SIN_COUNT];

    public static float sin(float rad) {
        return RivensMath.BF_sin[(int) (rad * RivensMath.BF_radToIndex) & RivensMath.BF_SIN_MASK];
    }

    public static float cos(float rad) {
        return RivensMath.BF_cos[(int) (rad * RivensMath.BF_radToIndex) & RivensMath.BF_SIN_MASK];
    }

    static {
        int i;

        for (i = 0; i < RivensMath.BF_SIN_COUNT; ++i) {
            RivensMath.BF_sin[i] = (float) Math.sin((double) (((float) i + 0.5F) / (float) RivensMath.BF_SIN_COUNT * RivensMath.BF_radFull));
            RivensMath.BF_cos[i] = (float) Math.cos((double) (((float) i + 0.5F) / (float) RivensMath.BF_SIN_COUNT * RivensMath.BF_radFull));
        }

        for (i = 0; i < 360; i += 90) {
            RivensMath.BF_sin[(int) ((float) i * RivensMath.BF_degToIndex) & RivensMath.BF_SIN_MASK] = (float) Math.sin((double) i * 3.141592653589793D / 180.0D);
            RivensMath.BF_cos[(int) ((float) i * RivensMath.BF_degToIndex) & RivensMath.BF_SIN_MASK] = (float) Math.cos((double) i * 3.141592653589793D / 180.0D);
        }

    }
}
