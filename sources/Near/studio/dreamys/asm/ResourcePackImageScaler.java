package studio.dreamys.asm;

import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("unused")
public class ResourcePackImageScaler {
    public static final int SIZE = 64;

    public static BufferedImage scalePackImage(BufferedImage image) {
        if (image == null) {
            return null;
        }
        System.out.println("Scaling resource pack icon from " + image.getWidth() + " to " + SIZE);
        BufferedImage smallImage = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = smallImage.getGraphics();
        graphics.drawImage(image, 0, 0, SIZE, SIZE, null);
        graphics.dispose();
        return smallImage;
    }
}