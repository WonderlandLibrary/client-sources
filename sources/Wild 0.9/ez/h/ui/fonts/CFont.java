package ez.h.ui.fonts;

import org.lwjgl.opengl.*;
import java.awt.*;
import java.awt.image.*;
import org.lwjgl.*;
import java.nio.*;

public class CFont
{
    final IntObject[] chars;
    int IMAGE_WIDTH;
    int charOffset;
    boolean antiAlias;
    int fontHeight;
    final Font font;
    int texID;
    int IMAGE_HEIGHT;
    
    public void glColor(final Color color) {
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
    }
    
    private BufferedImage getFontImage(final char c, final boolean b) {
        final Graphics2D graphics2D = (Graphics2D)new BufferedImage(1, 1, 2).getGraphics();
        if (b) {
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        else {
            graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
        graphics2D.setFont(this.font);
        final FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int n = fontMetrics.charWidth(c) + 8;
        if (n <= 0) {
            n = 7;
        }
        int size = fontMetrics.getHeight() + 3;
        if (size <= 0) {
            size = this.font.getSize();
        }
        final BufferedImage bufferedImage = new BufferedImage(n, size, 2);
        final Graphics2D graphics2D2 = (Graphics2D)bufferedImage.getGraphics();
        if (b) {
            graphics2D2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
        else {
            graphics2D2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }
        graphics2D2.setFont(this.font);
        graphics2D2.setColor(Color.WHITE);
        graphics2D2.drawString(String.valueOf(c), 3, 1 + fontMetrics.getAscent());
        return bufferedImage;
    }
    
    private void setupTexture(final boolean b) {
        if (this.font.getSize() <= (0xA4 ^ 0xAB)) {
            this.IMAGE_WIDTH = 38 + 187 - 134 + 165;
            this.IMAGE_HEIGHT = 11 + 67 + 160 + 18;
        }
        if (this.font.getSize() <= (0xA4 ^ 0x8F)) {
            this.IMAGE_WIDTH = 194 + 491 - 509 + 336;
            this.IMAGE_HEIGHT = 176 + 41 + 159 + 136;
        }
        else if (this.font.getSize() <= (0x1 ^ 0x5A)) {
            this.IMAGE_WIDTH = 66 + 124 + 708 + 126;
            this.IMAGE_HEIGHT = 903 + 99 - 302 + 324;
        }
        else {
            this.IMAGE_WIDTH = 35 + 658 - 151 + 1506;
            this.IMAGE_HEIGHT = 561 + 27 + 1197 + 263;
        }
        final BufferedImage bufferedImage = new BufferedImage(this.IMAGE_WIDTH, this.IMAGE_HEIGHT, 2);
        final Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setFont(this.font);
        graphics2D.setColor(new Color(172 + 138 - 166 + 111, 97 + 87 - 118 + 189, 127 + 19 + 83 + 26, 0));
        graphics2D.fillRect(0, 0, this.IMAGE_WIDTH, this.IMAGE_HEIGHT);
        graphics2D.setColor(Color.white);
        int height = 0;
        int storedX = 0;
        int storedY = 0;
        for (int i = 0; i < 1321 + 815 - 1734 + 1646; ++i) {
            final BufferedImage fontImage = this.getFontImage((char)i, b);
            final IntObject intObject = new IntObject();
            intObject.width = fontImage.getWidth();
            intObject.height = fontImage.getHeight();
            if (storedX + intObject.width >= this.IMAGE_WIDTH) {
                storedX = 0;
                storedY += height;
                height = 0;
            }
            intObject.storedX = storedX;
            intObject.storedY = storedY;
            if (intObject.height > this.fontHeight) {
                this.fontHeight = intObject.height;
            }
            if (intObject.height > height) {
                height = intObject.height;
            }
            this.chars[i] = intObject;
            graphics2D.drawImage(fontImage, storedX, storedY, null);
            storedX += intObject.width;
        }
        try {
            this.texID = loadTexture(bufferedImage);
        }
        catch (NullPointerException ex) {}
    }
    
    public int getStringWidth(final String s) {
        int n = 0;
        for (final char c : s.toCharArray()) {
            if (c < this.chars.length && c >= '\0') {
                n += this.chars[c].width - this.charOffset;
            }
        }
        return n / 2;
    }
    
    public int getStringHeight(final String s) {
        int n = 1;
        final char[] charArray = s.toCharArray();
        for (int length = charArray.length, i = 0; i < length; ++i) {
            if (charArray[i] == (0xB2 ^ 0xB8)) {
                ++n;
            }
        }
        return (this.fontHeight - this.charOffset) / 2 * n;
    }
    
    public boolean isAntiAlias() {
        return this.antiAlias;
    }
    
    private void drawQuad(final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7, final float n8) {
        final float n9 = n5 / this.IMAGE_WIDTH;
        final float n10 = n6 / this.IMAGE_HEIGHT;
        final float n11 = n7 / this.IMAGE_WIDTH;
        final float n12 = n8 / this.IMAGE_HEIGHT;
        GL11.glPushMatrix();
        GL11.glBegin(4);
        GL11.glTexCoord2f(n9 + n11, n10);
        GL11.glVertex2d((double)(n + n3), (double)n2);
        GL11.glTexCoord2f(n9, n10);
        GL11.glVertex2d((double)n, (double)n2);
        GL11.glTexCoord2f(n9, n10 + n12);
        GL11.glVertex2d((double)n, (double)(n2 + n4));
        GL11.glTexCoord2f(n9, n10 + n12);
        GL11.glVertex2d((double)n, (double)(n2 + n4));
        GL11.glTexCoord2f(n9 + n11, n10 + n12);
        GL11.glVertex2d((double)(n + n3), (double)(n2 + n4));
        GL11.glTexCoord2f(n9 + n11, n10);
        GL11.glVertex2d((double)(n + n3), (double)n2);
        GL11.glEnd();
        bus.I();
        GL11.glPopMatrix();
    }
    
    public Font getFont() {
        return this.font;
    }
    
    public int getHeight() {
        return (this.fontHeight - this.charOffset) / 2;
    }
    
    public void drawChar(final char c, final float n, final float n2) throws ArrayIndexOutOfBoundsException {
        try {
            this.drawQuad(n, n2, (float)this.chars[c].width, (float)this.chars[c].height, (float)this.chars[c].storedX, (float)this.chars[c].storedY, (float)this.chars[c].width, (float)this.chars[c].height);
        }
        catch (Exception ex) {}
    }
    
    public CFont(final Font font, final boolean antiAlias) {
        this.chars = new IntObject[1037 + 1493 - 2009 + 1527];
        this.IMAGE_WIDTH = 930 + 646 - 676 + 124;
        this.IMAGE_HEIGHT = 745 + 342 - 323 + 260;
        this.fontHeight = -1;
        this.charOffset = 8;
        this.font = font;
        this.antiAlias = antiAlias;
        this.charOffset = 8;
        this.setupTexture(antiAlias);
    }
    
    public void setAntiAlias(final boolean antiAlias) {
        if (this.antiAlias != antiAlias) {
            this.setupTexture(this.antiAlias = antiAlias);
        }
    }
    
    public void drawString(final String s, double n, double n2, final Color color, final boolean b) {
        n *= 2.0;
        n2 = n2 * 2.0 - 2.0;
        GL11.glPushMatrix();
        GL11.glHint(804 + 2281 - 2775 + 2845, 2942 + 3806 - 4754 + 2360);
        GL11.glScaled(0.25, 0.25, 0.25);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        bus.i(this.texID);
        this.glColor(b ? new Color(0.05f, 0.05f, 0.05f, color.getAlpha() / 255.0f) : color);
        for (int length = s.length(), i = 0; i < length; ++i) {
            final char char1 = s.charAt(i);
            if (char1 < this.chars.length && char1 >= '\0') {
                this.drawChar(char1, (float)n, (float)n2);
                n += this.chars[char1].width - this.charOffset;
            }
        }
        GL11.glPopMatrix();
    }
    
    public CFont(final Font font, final boolean antiAlias, final int charOffset) {
        this.chars = new IntObject[1928 + 874 - 1440 + 686];
        this.IMAGE_WIDTH = 772 + 1008 - 1034 + 278;
        this.IMAGE_HEIGHT = 505 + 214 - 182 + 487;
        this.fontHeight = -1;
        this.charOffset = 8;
        this.font = font;
        this.antiAlias = antiAlias;
        this.charOffset = charOffset;
        this.setupTexture(antiAlias);
    }
    
    public static int loadTexture(final BufferedImage bufferedImage) {
        final int[] array = new int[bufferedImage.getWidth() * bufferedImage.getHeight()];
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), array, 0, bufferedImage.getWidth());
        final ByteBuffer byteBuffer = BufferUtils.createByteBuffer(bufferedImage.getWidth() * bufferedImage.getHeight() * 4);
        for (int i = 0; i < bufferedImage.getHeight(); ++i) {
            for (int j = 0; j < bufferedImage.getWidth(); ++j) {
                final int n = array[i * bufferedImage.getWidth() + j];
                byteBuffer.put((byte)(n >> (0x18 ^ 0x8) & 19 + 107 + 10 + 119));
                byteBuffer.put((byte)(n >> 8 & 43 + 206 - 145 + 151));
                byteBuffer.put((byte)(n & 239 + 45 - 72 + 43));
                byteBuffer.put((byte)(n >> (0x80 ^ 0x98) & 236 + 33 - 126 + 112));
            }
        }
        byteBuffer.flip();
        final int glGenTextures = GL11.glGenTextures();
        GL11.glBindTexture(3083 + 2553 - 5250 + 3167, glGenTextures);
        GL11.glTexParameteri(2129 + 643 - 1095 + 1876, 4808 + 5191 - 2052 + 2295, 22116 + 14001 - 9814 + 6768);
        GL11.glTexParameteri(2986 + 2309 - 2819 + 1077, 3049 + 2713 - 213 + 4694, 31236 + 22057 - 26480 + 6258);
        GL11.glTexParameteri(872 + 1009 + 717 + 955, 6240 + 5873 - 5994 + 4122, 8522 + 6113 - 5085 + 179);
        GL11.glTexParameteri(2615 + 1586 - 2015 + 1367, 9635 + 2442 - 7539 + 5702, 8550 + 2910 - 9416 + 7685);
        GL11.glTexImage2D(3359 + 897 - 1970 + 1267, 0, 6869 + 4174 + 13415 + 8398, bufferedImage.getWidth(), bufferedImage.getHeight(), 0, 3593 + 3639 - 3486 + 2662, 2048 + 3789 - 1173 + 457, byteBuffer);
        return glGenTextures;
    }
    
    private static class IntObject
    {
        public int height;
        public int storedY;
        public int storedX;
        public int width;
    }
}
