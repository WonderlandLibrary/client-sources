/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import java.awt.Dimension;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import net.minecraft.client.renderer.GLAllocation;
import optifine.Config;
import optifine.TextureUtils;
import org.lwjgl.opengl.GL11;

public class Mipmaps {
    private final String iconName;
    private final int width;
    private final int height;
    private final int[] data;
    private final boolean direct;
    private int[][] mipmapDatas;
    private IntBuffer[] mipmapBuffers;
    private Dimension[] mipmapDimensions;

    public Mipmaps(String iconName, int width, int height, int[] data, boolean direct) {
        this.iconName = iconName;
        this.width = width;
        this.height = height;
        this.data = data;
        this.direct = direct;
        this.mipmapDimensions = Mipmaps.makeMipmapDimensions(width, height, iconName);
        this.mipmapDatas = Mipmaps.generateMipMapData(data, width, height, this.mipmapDimensions);
        if (direct) {
            this.mipmapBuffers = Mipmaps.makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
        }
    }

    public static Dimension[] makeMipmapDimensions(int width, int height, String iconName) {
        int texWidth = TextureUtils.ceilPowerOfTwo(width);
        int texHeight = TextureUtils.ceilPowerOfTwo(height);
        if (texWidth == width && texHeight == height) {
            ArrayList<Dimension> listDims = new ArrayList<Dimension>();
            int mipWidth = texWidth;
            int mipHeight = texHeight;
            do {
                if ((mipWidth /= 2) <= 0 && (mipHeight /= 2) <= 0) {
                    Dimension[] mipmapDimensions1 = listDims.toArray(new Dimension[listDims.size()]);
                    return mipmapDimensions1;
                }
                if (mipWidth <= 0) {
                    mipWidth = 1;
                }
                if (mipHeight <= 0) {
                    mipHeight = 1;
                }
                int mipmapDimensions = mipWidth * mipHeight * 4;
                Dimension dim = new Dimension(mipWidth, mipHeight);
                listDims.add(dim);
            } while (true);
        }
        Config.warn("Mipmaps not possible (power of 2 dimensions needed), texture: " + iconName + ", dim: " + width + "x" + height);
        return new Dimension[0];
    }

    public static int[][] generateMipMapData(int[] data, int width, int height, Dimension[] mipmapDimensions) {
        int[] parMipData = data;
        int parWidth = width;
        boolean scale = true;
        int[][] mipmapDatas = new int[mipmapDimensions.length][];
        for (int i2 = 0; i2 < mipmapDimensions.length; ++i2) {
            Dimension dim = mipmapDimensions[i2];
            int mipWidth = dim.width;
            int mipHeight = dim.height;
            int[] mipData = new int[mipWidth * mipHeight];
            mipmapDatas[i2] = mipData;
            int level = i2 + 1;
            if (scale) {
                for (int mipX = 0; mipX < mipWidth; ++mipX) {
                    for (int mipY = 0; mipY < mipHeight; ++mipY) {
                        int pixel;
                        int p1 = parMipData[mipX * 2 + 0 + (mipY * 2 + 0) * parWidth];
                        int p2 = parMipData[mipX * 2 + 1 + (mipY * 2 + 0) * parWidth];
                        int p3 = parMipData[mipX * 2 + 1 + (mipY * 2 + 1) * parWidth];
                        int p4 = parMipData[mipX * 2 + 0 + (mipY * 2 + 1) * parWidth];
                        mipData[mipX + mipY * mipWidth] = pixel = Mipmaps.alphaBlend(p1, p2, p3, p4);
                    }
                }
            }
            parMipData = mipData;
            parWidth = mipWidth;
            if (mipWidth > 1 && mipHeight > 1) continue;
            scale = false;
        }
        return mipmapDatas;
    }

    public static int alphaBlend(int c1, int c2, int c3, int c4) {
        int cx1 = Mipmaps.alphaBlend(c1, c2);
        int cx2 = Mipmaps.alphaBlend(c3, c4);
        int cx3 = Mipmaps.alphaBlend(cx1, cx2);
        return cx3;
    }

    private static int alphaBlend(int c1, int c2) {
        int a1 = (c1 & -16777216) >> 24 & 255;
        int a2 = (c2 & -16777216) >> 24 & 255;
        int ax2 = (a1 + a2) / 2;
        if (a1 == 0 && a2 == 0) {
            a1 = 1;
            a2 = 1;
        } else {
            if (a1 == 0) {
                c1 = c2;
                ax2 /= 2;
            }
            if (a2 == 0) {
                c2 = c1;
                ax2 /= 2;
            }
        }
        int r1 = (c1 >> 16 & 255) * a1;
        int g1 = (c1 >> 8 & 255) * a1;
        int b1 = (c1 & 255) * a1;
        int r2 = (c2 >> 16 & 255) * a2;
        int g2 = (c2 >> 8 & 255) * a2;
        int b2 = (c2 & 255) * a2;
        int rx = (r1 + r2) / (a1 + a2);
        int gx2 = (g1 + g2) / (a1 + a2);
        int bx2 = (b1 + b2) / (a1 + a2);
        return ax2 << 24 | rx << 16 | gx2 << 8 | bx2;
    }

    private int averageColor(int i2, int j2) {
        int k2 = (i2 & -16777216) >> 24 & 255;
        int l2 = (j2 & -16777216) >> 24 & 255;
        return (k2 + l2 >> 1 << 24) + ((i2 & 16711422) + (j2 & 16711422) >> 1);
    }

    public static IntBuffer[] makeMipmapBuffers(Dimension[] mipmapDimensions, int[][] mipmapDatas) {
        if (mipmapDimensions == null) {
            return null;
        }
        IntBuffer[] mipmapBuffers = new IntBuffer[mipmapDimensions.length];
        for (int i2 = 0; i2 < mipmapDimensions.length; ++i2) {
            Dimension dim = mipmapDimensions[i2];
            int bufLen = dim.width * dim.height;
            IntBuffer buf = GLAllocation.createDirectIntBuffer(bufLen);
            int[] data = mipmapDatas[i2];
            buf.clear();
            buf.put(data);
            buf.clear();
            mipmapBuffers[i2] = buf;
        }
        return mipmapBuffers;
    }

    public static void allocateMipmapTextures(int width, int height, String name) {
        Dimension[] dims = Mipmaps.makeMipmapDimensions(width, height, name);
        for (int i2 = 0; i2 < dims.length; ++i2) {
            Dimension dim = dims[i2];
            int mipWidth = dim.width;
            int mipHeight = dim.height;
            int level = i2 + 1;
            GL11.glTexImage2D(3553, level, 6408, mipWidth, mipHeight, 0, 32993, 33639, null);
        }
    }
}

