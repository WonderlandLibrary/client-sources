/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package optfine;

import java.awt.Dimension;
import java.nio.IntBuffer;
import java.util.ArrayList;
import net.minecraft.client.renderer.GLAllocation;
import optfine.Config;
import optfine.TextureUtils;
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

    public Mipmaps(String p_i42_1_, int p_i42_2_, int p_i42_3_, int[] p_i42_4_, boolean p_i42_5_) {
        this.iconName = p_i42_1_;
        this.width = p_i42_2_;
        this.height = p_i42_3_;
        this.data = p_i42_4_;
        this.direct = p_i42_5_;
        this.mipmapDimensions = Mipmaps.makeMipmapDimensions(p_i42_2_, p_i42_3_, p_i42_1_);
        this.mipmapDatas = Mipmaps.generateMipMapData(p_i42_4_, p_i42_2_, p_i42_3_, this.mipmapDimensions);
        if (!p_i42_5_) return;
        this.mipmapBuffers = Mipmaps.makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
    }

    public static Dimension[] makeMipmapDimensions(int p_makeMipmapDimensions_0_, int p_makeMipmapDimensions_1_, String p_makeMipmapDimensions_2_) {
        int i = TextureUtils.ceilPowerOfTwo(p_makeMipmapDimensions_0_);
        int j = TextureUtils.ceilPowerOfTwo(p_makeMipmapDimensions_1_);
        if (i != p_makeMipmapDimensions_0_ || j != p_makeMipmapDimensions_1_) {
            Config.warn("Mipmaps not possible (power of 2 dimensions needed), texture: " + p_makeMipmapDimensions_2_ + ", dim: " + p_makeMipmapDimensions_0_ + "x" + p_makeMipmapDimensions_1_);
            return new Dimension[0];
        }
        ArrayList<Dimension> list = new ArrayList<Dimension>();
        int k = i;
        int l = j;
        while ((k /= 2) > 0 || (l /= 2) > 0) {
            if (k <= 0) {
                k = 1;
            }
            if (l <= 0) {
                l = 1;
            }
            int i1 = k * l * 4;
            Dimension dimension = new Dimension(k, l);
            list.add(dimension);
        }
        return list.toArray(new Dimension[list.size()]);
    }

    public static int[][] generateMipMapData(int[] p_generateMipMapData_0_, int p_generateMipMapData_1_, int p_generateMipMapData_2_, Dimension[] p_generateMipMapData_3_) {
        int[] aint = p_generateMipMapData_0_;
        int i = p_generateMipMapData_1_;
        boolean flag = true;
        int[][] aint1 = new int[p_generateMipMapData_3_.length][];
        int j = 0;
        while (j < p_generateMipMapData_3_.length) {
            Dimension dimension = p_generateMipMapData_3_[j];
            int k = dimension.width;
            int l = dimension.height;
            int[] aint2 = new int[k * l];
            aint1[j] = aint2;
            int i1 = j + 1;
            if (flag) {
                for (int j1 = 0; j1 < k; ++j1) {
                    for (int k1 = 0; k1 < l; ++k1) {
                        int l2;
                        int l1 = aint[j1 * 2 + 0 + (k1 * 2 + 0) * i];
                        int i2 = aint[j1 * 2 + 1 + (k1 * 2 + 0) * i];
                        int j2 = aint[j1 * 2 + 1 + (k1 * 2 + 1) * i];
                        int k2 = aint[j1 * 2 + 0 + (k1 * 2 + 1) * i];
                        aint2[j1 + k1 * k] = l2 = Mipmaps.alphaBlend(l1, i2, j2, k2);
                    }
                }
            }
            aint = aint2;
            i = k;
            if (k <= 1 || l <= 1) {
                flag = false;
            }
            ++j;
        }
        return aint1;
    }

    public static int alphaBlend(int p_alphaBlend_0_, int p_alphaBlend_1_, int p_alphaBlend_2_, int p_alphaBlend_3_) {
        int i = Mipmaps.alphaBlend(p_alphaBlend_0_, p_alphaBlend_1_);
        int j = Mipmaps.alphaBlend(p_alphaBlend_2_, p_alphaBlend_3_);
        return Mipmaps.alphaBlend(i, j);
    }

    private static int alphaBlend(int p_alphaBlend_0_, int p_alphaBlend_1_) {
        int i = (p_alphaBlend_0_ & 0xFF000000) >> 24 & 0xFF;
        int j = (p_alphaBlend_1_ & 0xFF000000) >> 24 & 0xFF;
        int k = (i + j) / 2;
        if (i == 0 && j == 0) {
            i = 1;
            j = 1;
        } else {
            if (i == 0) {
                p_alphaBlend_0_ = p_alphaBlend_1_;
                k /= 2;
            }
            if (j == 0) {
                p_alphaBlend_1_ = p_alphaBlend_0_;
                k /= 2;
            }
        }
        int l = (p_alphaBlend_0_ >> 16 & 0xFF) * i;
        int i1 = (p_alphaBlend_0_ >> 8 & 0xFF) * i;
        int j1 = (p_alphaBlend_0_ & 0xFF) * i;
        int k1 = (p_alphaBlend_1_ >> 16 & 0xFF) * j;
        int l1 = (p_alphaBlend_1_ >> 8 & 0xFF) * j;
        int i2 = (p_alphaBlend_1_ & 0xFF) * j;
        int j2 = (l + k1) / (i + j);
        int k2 = (i1 + l1) / (i + j);
        int l2 = (j1 + i2) / (i + j);
        return k << 24 | j2 << 16 | k2 << 8 | l2;
    }

    private int averageColor(int p_averageColor_1_, int p_averageColor_2_) {
        int i = (p_averageColor_1_ & 0xFF000000) >> 24 & 0xFF;
        int j = (p_averageColor_2_ & 0xFF000000) >> 24 & 0xFF;
        return (i + j >> 1 << 24) + ((p_averageColor_1_ & 0xFEFEFE) + (p_averageColor_2_ & 0xFEFEFE) >> 1);
    }

    public static IntBuffer[] makeMipmapBuffers(Dimension[] p_makeMipmapBuffers_0_, int[][] p_makeMipmapBuffers_1_) {
        if (p_makeMipmapBuffers_0_ == null) {
            return null;
        }
        IntBuffer[] aintbuffer = new IntBuffer[p_makeMipmapBuffers_0_.length];
        int i = 0;
        while (i < p_makeMipmapBuffers_0_.length) {
            Dimension dimension = p_makeMipmapBuffers_0_[i];
            int j = dimension.width * dimension.height;
            IntBuffer intbuffer = GLAllocation.createDirectIntBuffer(j);
            int[] aint = p_makeMipmapBuffers_1_[i];
            intbuffer.clear();
            intbuffer.put(aint);
            intbuffer.clear();
            aintbuffer[i] = intbuffer;
            ++i;
        }
        return aintbuffer;
    }

    public static void allocateMipmapTextures(int p_allocateMipmapTextures_0_, int p_allocateMipmapTextures_1_, String p_allocateMipmapTextures_2_) {
        Dimension[] adimension = Mipmaps.makeMipmapDimensions(p_allocateMipmapTextures_0_, p_allocateMipmapTextures_1_, p_allocateMipmapTextures_2_);
        int i = 0;
        while (i < adimension.length) {
            Dimension dimension = adimension[i];
            int j = dimension.width;
            int k = dimension.height;
            int l = i + 1;
            GL11.glTexImage2D((int)3553, (int)l, (int)6408, (int)j, (int)k, (int)0, (int)32993, (int)33639, (IntBuffer)null);
            ++i;
        }
    }
}

