package net.silentclient.client.math;

public class LibGDXMath {

    public static final float BF_PI = 3.1415927F;
    public static final float BF_degreesToRadians = 0.017453292F;
    private static final float[] BF_table = new float[16384];

    public static float sin(float radians) {
        return LibGDXMath.BF_table[(int) (radians * 2607.5945F) & 16383];
    }

    public static float cos(float radians) {
        return LibGDXMath.BF_table[(int) ((radians + 1.5707964F) * 2607.5945F) & 16383];
    }

    static {
        int i;

        for (i = 0; i < 16384; ++i) {
            LibGDXMath.BF_table[i] = (float) Math.sin((double) (((float) i + 0.5F) / 16384.0F * 6.2831855F));
        }

        for (i = 0; i < 360; i += 90) {
            LibGDXMath.BF_table[(int) ((float) i * 45.511112F) & 16383] = (float) Math.sin((double) ((float) i * 0.017453292F));
        }

    }
}
