package net.minecraft.client.renderer;

import java.awt.image.*;
import java.awt.*;

public class ImageBufferDownload implements IImageBuffer
{
    private int[] imageData;
    private int imageWidth;
    private static final String[] I;
    private int imageHeight;
    private static final String __OBFID;
    
    private boolean hasTransparency(final int n, final int n2, final int n3, final int n4) {
        int i = n;
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < n3) {
            int j = n2;
            "".length();
            if (1 == 2) {
                throw null;
            }
            while (j < n4) {
                if ((this.imageData[i + j * this.imageWidth] >> (0x37 ^ 0x2F) & 39 + 183 - 165 + 198) < 2 + 100 - 81 + 107) {
                    return " ".length() != 0;
                }
                ++j;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    @Override
    public BufferedImage parseUserSkin(final BufferedImage bufferedImage) {
        if (bufferedImage == null) {
            return null;
        }
        this.imageWidth = (0xFD ^ 0xBD);
        this.imageHeight = (0x48 ^ 0x8);
        final int width = bufferedImage.getWidth();
        final int height = bufferedImage.getHeight();
        int length = " ".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (this.imageWidth < width || this.imageHeight < height) {
            this.imageWidth *= "  ".length();
            this.imageHeight *= "  ".length();
            length *= "  ".length();
        }
        final BufferedImage bufferedImage2 = new BufferedImage(this.imageWidth, this.imageHeight, "  ".length());
        final Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, "".length(), "".length(), null);
        if (bufferedImage.getHeight() == (0x2A ^ 0xA) * length) {
            graphics.drawImage(bufferedImage2, (0x6B ^ 0x73) * length, (0x41 ^ 0x71) * length, (0xA7 ^ 0xB3) * length, (0x33 ^ 0x7) * length, (0x60 ^ 0x64) * length, (0x8E ^ 0x9E) * length, (0x16 ^ 0x1E) * length, (0x62 ^ 0x76) * length, null);
            graphics.drawImage(bufferedImage2, (0xDC ^ 0xC0) * length, (0xB6 ^ 0x86) * length, (0xDB ^ 0xC3) * length, (0x70 ^ 0x44) * length, (0x21 ^ 0x29) * length, (0x4F ^ 0x5F) * length, (0xAD ^ 0xA1) * length, (0x13 ^ 0x7) * length, null);
            graphics.drawImage(bufferedImage2, (0x58 ^ 0x4C) * length, (0x16 ^ 0x22) * length, (0x7A ^ 0x6A) * length, (0x3 ^ 0x43) * length, (0x56 ^ 0x5E) * length, (0x58 ^ 0x4C) * length, (0x10 ^ 0x1C) * length, (0x72 ^ 0x52) * length, null);
            graphics.drawImage(bufferedImage2, (0xBA ^ 0xA2) * length, (0x2B ^ 0x1F) * length, (0x8E ^ 0x9A) * length, (0x2 ^ 0x42) * length, (0x2F ^ 0x2B) * length, (0xD7 ^ 0xC3) * length, (0x9E ^ 0x96) * length, (0x18 ^ 0x38) * length, null);
            graphics.drawImage(bufferedImage2, (0xA7 ^ 0xBB) * length, (0x22 ^ 0x16) * length, (0x45 ^ 0x5D) * length, (0x6A ^ 0x2A) * length, "".length() * length, (0x7A ^ 0x6E) * length, (0x37 ^ 0x33) * length, (0x7 ^ 0x27) * length, null);
            graphics.drawImage(bufferedImage2, (0x9A ^ 0xBA) * length, (0x2A ^ 0x1E) * length, (0xB2 ^ 0xAE) * length, (0x39 ^ 0x79) * length, (0x7B ^ 0x77) * length, (0x7 ^ 0x13) * length, (0x9E ^ 0x8E) * length, (0x21 ^ 0x1) * length, null);
            graphics.drawImage(bufferedImage2, (0xE ^ 0x26) * length, (0x27 ^ 0x17) * length, (0x9F ^ 0xBB) * length, (0x81 ^ 0xB5) * length, (0x39 ^ 0x15) * length, (0x73 ^ 0x63) * length, (0x4E ^ 0x7E) * length, (0x75 ^ 0x61) * length, null);
            graphics.drawImage(bufferedImage2, (0xEC ^ 0xC0) * length, (0x59 ^ 0x69) * length, (0xE8 ^ 0xC0) * length, (0x7 ^ 0x33) * length, (0x4D ^ 0x7D) * length, (0x50 ^ 0x40) * length, (0x3F ^ 0xB) * length, (0xB8 ^ 0xAC) * length, null);
            graphics.drawImage(bufferedImage2, (0x5 ^ 0x21) * length, (0x21 ^ 0x15) * length, (0xC ^ 0x2C) * length, (0xF6 ^ 0xB6) * length, (0x55 ^ 0x65) * length, (0x81 ^ 0x95) * length, (0x26 ^ 0x12) * length, (0xB4 ^ 0x94) * length, null);
            graphics.drawImage(bufferedImage2, (0x74 ^ 0x5C) * length, (0x7F ^ 0x4B) * length, (0x4D ^ 0x69) * length, (0x13 ^ 0x53) * length, (0x97 ^ 0xBB) * length, (0x73 ^ 0x67) * length, (0x87 ^ 0xB7) * length, (0x2B ^ 0xB) * length, null);
            graphics.drawImage(bufferedImage2, (0x47 ^ 0x6B) * length, (0x92 ^ 0xA6) * length, (0xB7 ^ 0x9F) * length, (0xE1 ^ 0xA1) * length, (0x3B ^ 0x13) * length, (0x4C ^ 0x58) * length, (0xA0 ^ 0x8C) * length, (0x15 ^ 0x35) * length, null);
            graphics.drawImage(bufferedImage2, (0xA5 ^ 0x95) * length, (0xBE ^ 0x8A) * length, (0x49 ^ 0x65) * length, (0x4A ^ 0xA) * length, (0x57 ^ 0x63) * length, (0x6C ^ 0x78) * length, (0x1F ^ 0x27) * length, (0x3F ^ 0x1F) * length, null);
        }
        graphics.dispose();
        this.imageData = ((DataBufferInt)bufferedImage2.getRaster().getDataBuffer()).getData();
        this.setAreaOpaque("".length(), "".length(), (0x93 ^ 0xB3) * length, (0xBF ^ 0xAF) * length);
        this.setAreaTransparent((0x98 ^ 0xB8) * length, "".length(), (0x2 ^ 0x42) * length, (0x2E ^ 0xE) * length);
        this.setAreaOpaque("".length(), (0xA4 ^ 0xB4) * length, (0xF7 ^ 0xB7) * length, (0x54 ^ 0x74) * length);
        this.setAreaTransparent("".length(), (0x5E ^ 0x7E) * length, (0x4D ^ 0x5D) * length, (0x8A ^ 0xBA) * length);
        this.setAreaTransparent((0x25 ^ 0x35) * length, (0x8D ^ 0xAD) * length, (0x2 ^ 0x2A) * length, (0x87 ^ 0xB7) * length);
        this.setAreaTransparent((0x34 ^ 0x1C) * length, (0xB ^ 0x2B) * length, (0x76 ^ 0x4E) * length, (0x34 ^ 0x4) * length);
        this.setAreaTransparent("".length(), (0xF2 ^ 0xC2) * length, (0x77 ^ 0x67) * length, (0x70 ^ 0x30) * length);
        this.setAreaOpaque((0x32 ^ 0x22) * length, (0x44 ^ 0x74) * length, (0x46 ^ 0x76) * length, (0x1 ^ 0x41) * length);
        this.setAreaTransparent((0x13 ^ 0x23) * length, (0xF4 ^ 0xC4) * length, (0x2A ^ 0x6A) * length, (0x61 ^ 0x21) * length);
        return bufferedImage2;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(",\u0004\u0007RW_xh[RY", "oHXbg");
    }
    
    @Override
    public void skinAvailable() {
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        __OBFID = ImageBufferDownload.I["".length()];
    }
    
    private void setAreaOpaque(final int n, final int n2, final int n3, final int n4) {
        int i = n;
        "".length();
        if (4 < 3) {
            throw null;
        }
        while (i < n3) {
            int j = n2;
            "".length();
            if (0 < 0) {
                throw null;
            }
            while (j < n4) {
                final int[] imageData = this.imageData;
                final int n5 = i + j * this.imageWidth;
                imageData[n5] |= -(7984001 + 7470257 - 4355137 + 5678095);
                ++j;
            }
            ++i;
        }
    }
    
    private void setAreaTransparent(final int n, final int n2, final int n3, final int n4) {
        if (!this.hasTransparency(n, n2, n3, n4)) {
            int i = n;
            "".length();
            if (1 < 0) {
                throw null;
            }
            while (i < n3) {
                int j = n2;
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                while (j < n4) {
                    final int[] imageData = this.imageData;
                    final int n5 = i + j * this.imageWidth;
                    imageData[n5] &= 1126614 + 10240701 - 4866243 + 10276143;
                    ++j;
                }
                ++i;
            }
        }
    }
}
