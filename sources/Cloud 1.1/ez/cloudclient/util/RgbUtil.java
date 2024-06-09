package ez.cloudclient.util;

import java.awt.*;

public class RgbUtil {

    private static float ColorCalculation(final float float1, final float float2, float float3) {
        if (float3 < 0.0f) {
            ++float3;
        }
        if (float3 > 1.0f) {
            --float3;
        }
        if (6.0f * float3 < 1.0f) {
            return float1 + (float2 - float1) * 6.0f * float3;
        }
        if (2.0f * float3 < 1.0f) {
            return float2;
        }
        if (3.0f * float3 < 2.0f) {
            return float1 + (float2 - float1) * 6.0f * (0.6666667f - float3);
        }
        return float1;
    }

    public static Color GetRainbowColor(float hue, float saturation, float lightness, final float alpha) {
        if (saturation < 0.0f || saturation > 100.0f) {
            throw new IllegalArgumentException("Color parameter outside of expected range - Saturation");
        }
        if (lightness < 0.0f || lightness > 100.0f) {
            throw new IllegalArgumentException("Color parameter outside of expected range - Lightness");
        }
        if (alpha < 0.0f || alpha > 1.0f) {
            throw new IllegalArgumentException("Color parameter outside of expected range - Alpha");
        }
        hue = (hue %= 360.0f) / 360.0f;
        saturation /= 100.0f;
        lightness /= 100.0f;
        float float1;
        if (lightness < 0.0) {
            float1 = lightness * (1.0f + saturation);
        } else {
            float1 = lightness + saturation - saturation * lightness;
        }
        saturation = 2.0f * lightness - float1;
        lightness = Math.max(0.0f, ColorCalculation(saturation, float1, hue + 0.33333334f));
        final float max = Math.max(0.0f, ColorCalculation(saturation, float1, hue));
        saturation = Math.max(0.0f, ColorCalculation(saturation, float1, hue - 0.33333334f));
        lightness = Math.min(lightness, 1.0f);
        final float min = Math.min(max, 1.0f);
        saturation = Math.min(saturation, 1.0f);
        return new Color(lightness, min, saturation, alpha);
    }
}
