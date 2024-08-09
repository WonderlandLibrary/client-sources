/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.image.BufferedImage;

public class DeinterlaceFilter
extends AbstractBufferedImageOp {
    public static final int EVEN = 0;
    public static final int ODD = 1;
    public static final int AVERAGE = 2;
    private int mode = 0;

    public void setMode(int n) {
        this.mode = n;
    }

    public int getMode() {
        return this.mode;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        block8: {
            int[] nArray;
            int n;
            int n2;
            block9: {
                block7: {
                    n2 = bufferedImage.getWidth();
                    n = bufferedImage.getHeight();
                    if (bufferedImage2 == null) {
                        bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
                    }
                    nArray = null;
                    if (this.mode != 0) break block7;
                    for (int i = 0; i < n - 1; i += 2) {
                        nArray = this.getRGB(bufferedImage, 0, i, n2, 1, nArray);
                        if (bufferedImage != bufferedImage2) {
                            this.setRGB(bufferedImage2, 0, i, n2, 1, nArray);
                        }
                        this.setRGB(bufferedImage2, 0, i + 1, n2, 1, nArray);
                    }
                    break block8;
                }
                if (this.mode != 1) break block9;
                for (int i = 1; i < n; i += 2) {
                    nArray = this.getRGB(bufferedImage, 0, i, n2, 1, nArray);
                    if (bufferedImage != bufferedImage2) {
                        this.setRGB(bufferedImage2, 0, i, n2, 1, nArray);
                    }
                    this.setRGB(bufferedImage2, 0, i - 1, n2, 1, nArray);
                }
                break block8;
            }
            if (this.mode != 2) break block8;
            int[] nArray2 = null;
            for (int i = 0; i < n - 1; i += 2) {
                nArray = this.getRGB(bufferedImage, 0, i, n2, 1, nArray);
                nArray2 = this.getRGB(bufferedImage, 0, i + 1, n2, 1, nArray2);
                for (int j = 0; j < n2; ++j) {
                    int n3 = nArray[j];
                    int n4 = nArray2[j];
                    int n5 = n3 >> 24 & 0xFF;
                    int n6 = n3 >> 16 & 0xFF;
                    int n7 = n3 >> 8 & 0xFF;
                    int n8 = n3 & 0xFF;
                    int n9 = n4 >> 24 & 0xFF;
                    int n10 = n4 >> 16 & 0xFF;
                    int n11 = n4 >> 8 & 0xFF;
                    int n12 = n4 & 0xFF;
                    n5 = (n5 + n9) / 2;
                    n6 = (n6 + n10) / 2;
                    n7 = (n7 + n11) / 2;
                    n8 = (n8 + n12) / 2;
                    nArray[j] = n5 << 24 | n6 << 16 | n7 << 8 | n8;
                }
                this.setRGB(bufferedImage2, 0, i, n2, 1, nArray);
                this.setRGB(bufferedImage2, 0, i + 1, n2, 1, nArray);
            }
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Video/De-Interlace";
    }
}

