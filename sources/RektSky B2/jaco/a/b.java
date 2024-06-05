package jaco.a;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public final class b
{
    static {
        try {
            final float[] array = new float[9];
            for (int i = 0; i < array.length; ++i) {
                array[i] = 0.11111111f;
            }
            final ConvolveOp convolveOp = new ConvolveOp(new Kernel(3, 3, array), 1, null);
        }
        catch (Throwable t) {
            throw new ExceptionInInitializerError(t);
        }
    }
    
    private static BufferedImage a(final int n, final int n2, final boolean b) {
        try {
            return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(n, n2, b ? 3 : 1);
        }
        catch (Throwable t) {
            return new BufferedImage(n, n2, b ? 2 : 1);
        }
    }
    
    public static BufferedImage a(final URL url) {
        final Image image;
        if ((image = new ImageIcon(url).getImage()).getWidth(null) == -1 || image.getHeight(null) == -1) {
            return null;
        }
        final Image image2 = image;
        final BufferedImage a;
        final Graphics2D graphics;
        (graphics = (a = a(0 + image2.getWidth(null), 0 + image2.getHeight(null), a(image2))).createGraphics()).drawImage(image2, 0, 0, null);
        graphics.dispose();
        return a;
    }
    
    private static BufferedImage c(final BufferedImage bufferedImage, final float n) {
        final BufferedImage a = a(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getColorModel().hasAlpha());
        final a.a.a.b b;
        (b = new a.a.a.b()).a(n);
        b.filter(bufferedImage, a);
        return a;
    }
    
    public static BufferedImage a(final BufferedImage bufferedImage, final float n) {
        return c(bufferedImage, n);
    }
    
    public static BufferedImage b(final BufferedImage bufferedImage, final float n) {
        return c(bufferedImage, -n);
    }
    
    private static boolean a(Image image) {
        if (image instanceof BufferedImage) {
            return ((BufferedImage)image).getColorModel().hasAlpha();
        }
        image = (Image)new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            ((PixelGrabber)image).grabPixels();
        }
        catch (InterruptedException ex) {}
        return ((PixelGrabber)image).getColorModel().hasAlpha();
    }
}
