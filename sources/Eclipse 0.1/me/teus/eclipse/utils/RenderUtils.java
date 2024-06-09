package me.teus.eclipse.utils;

public class RenderUtils {
    public static int fadeBetween(int color1, int color2, float index) {
        if(index > 1.0F)
            index = 1.0F - index % 1.0F;

        double d = (1.0F - index);
        int i = (int)((color1 >> 16 & 0xFF) * d + ((color2 >> 16 & 0xFF) * index));
        int j = (int)((color1 >> 8 & 0xFF) * d + ((color2 >> 8 & 0xFF) * index));
        int k = (int)((color1 & 0xFF) * d + ((color2 & 0xFF) * index));
        int m = (int)((color1 >> 24 & 0xFF) * d + ((color2 >> 24 & 0xFF) * index));
        return (m & 0xFF) << 24 | (i & 0xFF) << 16 | (j & 0xFF) << 8 | k & 0xFF;
    }
}
