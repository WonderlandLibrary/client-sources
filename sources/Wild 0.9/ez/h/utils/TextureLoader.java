package ez.h.utils;

import java.awt.image.*;
import net.minecraft.client.main.*;
import javax.imageio.*;
import java.io.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import java.nio.*;

public class TextureLoader
{
    private static final int BYTES_PER_PIXEL = 4;
    
    public static BufferedImage loadImage(final String s) {
        try {
            return ImageIO.read(Main.class.getResource(s));
        }
        catch (IOException ex) {
            return null;
        }
    }
    
    public static int loadTexture(final BufferedImage bufferedImage) {
        final int[] array = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), array, 0, bufferedImage.getWidth());
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(bufferedImage.getWidth() * bufferedImage.getHeight() * 4);
        for (int i = 0; i < bufferedImage.getHeight(); ++i) {
            for (int j = 0; j < bufferedImage.getWidth(); ++j) {
                final int n = array[i * bufferedImage.getWidth() + j];
                byteBuffer.put((byte)(n >> (0xB2 ^ 0xA2) & 241 + 136 - 332 + 210));
                byteBuffer.put((byte)(n >> 8 & 108 + 76 - 87 + 158));
                byteBuffer.put((byte)(n & 161 + 19 - 41 + 116));
                byteBuffer.put((byte)(n >> (0x7B ^ 0x63) & 53 + 55 - 78 + 225));
            }
        }
        byteBuffer.flip();
        final int glGenTextures = GL11.glGenTextures();
        GL11.glBindTexture(2207 + 2299 - 3604 + 2651, glGenTextures);
        GL11.glTexParameteri(3501 + 3528 - 6365 + 2889, 5558 + 3615 - 1572 + 2641, 33040 + 3522 - 12359 + 8868);
        GL11.glTexParameteri(2747 + 169 - 1598 + 2235, 5798 + 6817 - 8439 + 6067, 2216 + 30843 - 16146 + 16158);
        GL11.glTexParameteri(2654 + 1791 - 2641 + 1749, 5164 + 1174 + 1442 + 2461, 7487 + 9260 - 12179 + 5160);
        GL11.glTexParameteri(0 + 2329 - 1918 + 3142, 9474 + 4708 - 10120 + 6178, 4344 + 923 - 4496 + 8957);
        GL11.glTexImage2D(698 + 1699 + 248 + 908, 0, 6454 + 2089 - 1474 + 25787, bufferedImage.getWidth(), bufferedImage.getHeight(), 0, 3618 + 1784 - 3074 + 4080, 32 + 1013 + 3479 + 597, byteBuffer);
        return glGenTextures;
    }
}
