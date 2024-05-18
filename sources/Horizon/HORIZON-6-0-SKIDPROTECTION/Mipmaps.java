package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL11;
import java.util.ArrayList;
import java.awt.Dimension;
import java.nio.IntBuffer;

public class Mipmaps
{
    private final String HorizonCode_Horizon_È;
    private final int Â;
    private final int Ý;
    private final int[] Ø­áŒŠá;
    private final boolean Âµá€;
    private int[][] Ó;
    private IntBuffer[] à;
    private Dimension[] Ø;
    
    public Mipmaps(final String iconName, final int width, final int height, final int[] data, final boolean direct) {
        this.HorizonCode_Horizon_È = iconName;
        this.Â = width;
        this.Ý = height;
        this.Ø­áŒŠá = data;
        this.Âµá€ = direct;
        this.Ø = HorizonCode_Horizon_È(width, height, iconName);
        this.Ó = HorizonCode_Horizon_È(data, width, height, this.Ø);
        if (direct) {
            this.à = HorizonCode_Horizon_È(this.Ø, this.Ó);
        }
    }
    
    public static Dimension[] HorizonCode_Horizon_È(final int width, final int height, final String iconName) {
        final int texWidth = TextureUtils.HorizonCode_Horizon_È(width);
        final int texHeight = TextureUtils.HorizonCode_Horizon_È(height);
        if (texWidth == width && texHeight == height) {
            final ArrayList listDims = new ArrayList();
            int mipWidth = texWidth;
            int mipHeight = texHeight;
            while (true) {
                mipWidth /= 2;
                mipHeight /= 2;
                if (mipWidth <= 0 && mipHeight <= 0) {
                    break;
                }
                if (mipWidth <= 0) {
                    mipWidth = 1;
                }
                if (mipHeight <= 0) {
                    mipHeight = 1;
                }
                final int mipmapDimensions = mipWidth * mipHeight * 4;
                final Dimension dim = new Dimension(mipWidth, mipHeight);
                listDims.add(dim);
            }
            final Dimension[] mipmapDimensions2 = listDims.toArray(new Dimension[listDims.size()]);
            return mipmapDimensions2;
        }
        Config.Â("Mipmaps not possible (power of 2 dimensions needed), texture: " + iconName + ", dim: " + width + "x" + height);
        return new Dimension[0];
    }
    
    public static int[][] HorizonCode_Horizon_È(final int[] data, final int width, final int height, final Dimension[] mipmapDimensions) {
        int[] parMipData = data;
        int parWidth = width;
        boolean scale = true;
        final int[][] mipmapDatas = new int[mipmapDimensions.length][];
        for (int i = 0; i < mipmapDimensions.length; ++i) {
            final Dimension dim = mipmapDimensions[i];
            final int mipWidth = dim.width;
            final int mipHeight = dim.height;
            final int[] mipData = new int[mipWidth * mipHeight];
            mipmapDatas[i] = mipData;
            final int level = i + 1;
            if (scale) {
                for (int mipX = 0; mipX < mipWidth; ++mipX) {
                    for (int mipY = 0; mipY < mipHeight; ++mipY) {
                        final int p1 = parMipData[mipX * 2 + 0 + (mipY * 2 + 0) * parWidth];
                        final int p2 = parMipData[mipX * 2 + 1 + (mipY * 2 + 0) * parWidth];
                        final int p3 = parMipData[mipX * 2 + 1 + (mipY * 2 + 1) * parWidth];
                        final int p4 = parMipData[mipX * 2 + 0 + (mipY * 2 + 1) * parWidth];
                        final int pixel = HorizonCode_Horizon_È(p1, p2, p3, p4);
                        mipData[mipX + mipY * mipWidth] = pixel;
                    }
                }
            }
            parMipData = mipData;
            parWidth = mipWidth;
            if (mipWidth <= 1 || mipHeight <= 1) {
                scale = false;
            }
        }
        return mipmapDatas;
    }
    
    public static int HorizonCode_Horizon_È(final int c1, final int c2, final int c3, final int c4) {
        final int cx1 = HorizonCode_Horizon_È(c1, c2);
        final int cx2 = HorizonCode_Horizon_È(c3, c4);
        final int cx3 = HorizonCode_Horizon_È(cx1, cx2);
        return cx3;
    }
    
    private static int HorizonCode_Horizon_È(int c1, int c2) {
        int a1 = (c1 & 0xFF000000) >> 24 & 0xFF;
        int a2 = (c2 & 0xFF000000) >> 24 & 0xFF;
        int ax = (a1 + a2) / 2;
        if (a1 == 0 && a2 == 0) {
            a1 = 1;
            a2 = 1;
        }
        else {
            if (a1 == 0) {
                c1 = c2;
                ax /= 2;
            }
            if (a2 == 0) {
                c2 = c1;
                ax /= 2;
            }
        }
        final int r1 = (c1 >> 16 & 0xFF) * a1;
        final int g1 = (c1 >> 8 & 0xFF) * a1;
        final int b1 = (c1 & 0xFF) * a1;
        final int r2 = (c2 >> 16 & 0xFF) * a2;
        final int g2 = (c2 >> 8 & 0xFF) * a2;
        final int b2 = (c2 & 0xFF) * a2;
        final int rx = (r1 + r2) / (a1 + a2);
        final int gx = (g1 + g2) / (a1 + a2);
        final int bx = (b1 + b2) / (a1 + a2);
        return ax << 24 | rx << 16 | gx << 8 | bx;
    }
    
    private int Â(final int i, final int j) {
        final int k = (i & 0xFF000000) >> 24 & 0xFF;
        final int l = (j & 0xFF000000) >> 24 & 0xFF;
        return (k + l >> 1 << 24) + ((i & 0xFEFEFE) + (j & 0xFEFEFE) >> 1);
    }
    
    public static IntBuffer[] HorizonCode_Horizon_È(final Dimension[] mipmapDimensions, final int[][] mipmapDatas) {
        if (mipmapDimensions == null) {
            return null;
        }
        final IntBuffer[] mipmapBuffers = new IntBuffer[mipmapDimensions.length];
        for (int i = 0; i < mipmapDimensions.length; ++i) {
            final Dimension dim = mipmapDimensions[i];
            final int bufLen = dim.width * dim.height;
            final IntBuffer buf = GLAllocation.Ø­áŒŠá(bufLen);
            final int[] data = mipmapDatas[i];
            buf.clear();
            buf.put(data);
            buf.clear();
            mipmapBuffers[i] = buf;
        }
        return mipmapBuffers;
    }
    
    public static void Â(final int width, final int height, final String name) {
        final Dimension[] dims = HorizonCode_Horizon_È(width, height, name);
        for (int i = 0; i < dims.length; ++i) {
            final Dimension dim = dims[i];
            final int mipWidth = dim.width;
            final int mipHeight = dim.height;
            final int level = i + 1;
            GL11.glTexImage2D(3553, level, 6408, mipWidth, mipHeight, 0, 32993, 33639, (IntBuffer)null);
        }
    }
}
