package me.kaimson.melonclient.utils;

public class MathUtil
{
    private static final double[] a;
    private static final double[] b;
    
    public static double getAngle(int paramInt) {
        paramInt %= 360;
        return MathUtil.b[paramInt];
    }
    
    public static double getRightAngle(int paramInt) {
        paramInt += 90;
        paramInt %= 360;
        return MathUtil.b[paramInt];
    }
    
    private static float snapToStep(float value, final float valueStep) {
        if (valueStep > 0.0f) {
            value = valueStep * Math.round(value / valueStep);
        }
        return value;
    }
    
    public static float normalizeValue(final float value, final float valueMin, final float valueMax, final float valueStep) {
        return ns.a((snapToStepClamp(value, valueMin, valueMax, valueStep) - valueMin) / (valueMax - valueMin), 0.0f, 1.0f);
    }
    
    private static float snapToStepClamp(float value, final float valueMin, final float valueMax, final float valueStep) {
        value = snapToStep(value, valueStep);
        return ns.a(value, valueMin, valueMax);
    }
    
    public static float denormalizeValue(final float value, final float valueMin, final float valueMax, final float valueStep) {
        return snapToStepClamp(valueMin + (valueMax - valueMin) * ns.a(value, 0.0f, 1.0f), valueMin, valueMax, valueStep);
    }
    
    static {
        a = new double[65536];
        b = new double[360];
        for (int i = 0; i < 65536; ++i) {
            MathUtil.a[i] = Math.sin(i * 3.141592653589793 * 2.0 / 65536.0);
        }
        for (int i = 0; i < 360; ++i) {
            MathUtil.b[i] = Math.sin(Math.toRadians(i));
        }
    }
}
