package a.a.a;

import java.awt.image.*;

public final class b extends d
{
    private float g;
    
    public b() {
        this.g = 0.5f;
        this.e = 2.0f;
    }
    
    public final void a(final float g) {
        this.g = g;
    }
    
    @Override
    public final BufferedImage filter(final BufferedImage bufferedImage, BufferedImage compatibleDestImage) {
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        if (compatibleDestImage == null) {
            compatibleDestImage = this.createCompatibleDestImage(bufferedImage, null);
        }
        final int[] array = new int[width * height];
        final int[] array2 = new int[width * height];
        bufferedImage.getRGB(0, 0, width, height, array, 0, width);
        if (this.e > 0.0f) {
            d.a(this.f, array, array2, width, height, this.c, this.c && this.d, false, b.a);
            d.a(this.f, array2, array, height, width, this.c, false, this.c && this.d, b.a);
        }
        bufferedImage.getRGB(0, 0, width, height, array2, 0, width);
        final float n = 4.0f * this.g;
        int n2 = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                final int n3;
                final int n4;
                array[n2] = ((n3 & 0xFF000000) | a.a.a.e.a((int)(((n3 = array2[n2]) >> 16 & 0xFF) + n * ((n4 = array[n2]) >> 16 & 0xFF))) << 16 | a.a.a.e.a((int)((n3 >> 8 & 0xFF) + n * (n4 >> 8 & 0xFF))) << 8 | a.a.a.e.a((int)((n3 & 0xFF) + n * (n4 & 0xFF))));
                ++n2;
            }
        }
        compatibleDestImage.setRGB(0, 0, width, height, array, 0, width);
        return compatibleDestImage;
    }
    
    @Override
    public final String toString() {
        return "Blur/Glow...";
    }
}
