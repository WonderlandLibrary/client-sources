package net.minecraft.src;

import org.lwjgl.util.*;
import net.minecraft.client.*;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.util.*;
import org.lwjgl.opengl.*;
import org.lwjgl.*;
import java.nio.*;

public class Texture
{
    private int glTextureId;
    private int textureId;
    private int textureType;
    private int width;
    private int height;
    private final int textureDepth;
    private final int textureFormat;
    private final int textureTarget;
    private final int textureMinFilter;
    private final int textureMagFilter;
    private final int textureWrap;
    private final boolean mipmapActive;
    private final String textureName;
    private Rect2i textureRect;
    private boolean transferred;
    private boolean autoCreate;
    private boolean textureNotModified;
    private ByteBuffer textureData;
    private boolean textureBound;
    public ByteBuffer[] mipmapDatas;
    public Dimension[] mipmapDimensions;
    
    private Texture(final String par1Str, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7, int par8, final int par9) {
        this.textureName = par1Str;
        this.textureType = par2;
        this.width = par3;
        this.height = par4;
        this.textureDepth = par5;
        this.textureFormat = par7;
        if (Config.isUseMipmaps() && isMipMapTexture(this.textureType, this.textureName)) {
            par8 = Config.getMipmapType();
        }
        this.textureMinFilter = par8;
        this.textureMagFilter = par9;
        final char var10 = '\u812f';
        this.textureWrap = var10;
        this.textureRect = new Rect2i(0, 0, par3, par4);
        if (par4 == 1 && par5 == 1) {
            this.textureTarget = 3552;
        }
        else if (par5 == 1) {
            this.textureTarget = 3553;
        }
        else {
            this.textureTarget = 32879;
        }
        this.mipmapActive = ((par8 != 9728 && par8 != 9729) || (par9 != 9728 && par9 != 9729));
        if (par2 != 2) {
            this.glTextureId = GL11.glGenTextures();
            GL11.glBindTexture(this.textureTarget, this.glTextureId);
            GL11.glTexParameteri(this.textureTarget, 10241, par8);
            GL11.glTexParameteri(this.textureTarget, 10240, par9);
            if (this.mipmapActive) {
                this.updateMipmapLevel(-1);
            }
            GL11.glTexParameteri(this.textureTarget, 10242, var10);
            GL11.glTexParameteri(this.textureTarget, 10243, var10);
        }
        else {
            this.glTextureId = -1;
        }
        this.textureId = TextureManager.instance().getNextTextureId();
    }
    
    public Texture(final String par1Str, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8, final BufferedImage par9BufferedImage) {
        this(par1Str, par2, par3, par4, 1, par5, par6, par7, par8, par9BufferedImage);
    }
    
    public Texture(final String par1Str, final int par2, final int par3, final int par4, final int par5, final int par6, final int par7, final int par8, final int par9, final BufferedImage par10BufferedImage) {
        this(par1Str, par2, par3, par4, par5, par6, par7, par8, par9);
        if (par10BufferedImage == null) {
            if (par3 != -1 && par4 != -1) {
                final byte[] var11 = new byte[par3 * par4 * par5 * 4];
                for (int var12 = 0; var12 < var11.length; ++var12) {
                    var11[var12] = 0;
                }
                (this.textureData = GLAllocation.createDirectByteBuffer(var11.length)).clear();
                this.textureData.put(var11);
                this.textureData.position(0).limit(var11.length);
                if (this.autoCreate) {
                    this.uploadTexture();
                }
                else {
                    this.textureNotModified = false;
                }
            }
            else {
                this.transferred = false;
            }
        }
        else {
            this.transferred = true;
            this.transferFromImage(par10BufferedImage);
            if (par2 != 2) {
                this.uploadTexture();
                this.autoCreate = false;
            }
        }
    }
    
    public final Rect2i getTextureRect() {
        return this.textureRect;
    }
    
    public void fillRect(final Rect2i par1Rect2i, final int par2) {
        if (this.textureTarget != 32879) {
            final Rect2i var3 = new Rect2i(0, 0, this.width, this.height);
            var3.intersection(par1Rect2i);
            this.textureData.position(0);
            for (int var4 = var3.getRectY(); var4 < var3.getRectY() + var3.getRectHeight(); ++var4) {
                final int var5 = var4 * this.width * 4;
                for (int var6 = var3.getRectX(); var6 < var3.getRectX() + var3.getRectWidth(); ++var6) {
                    this.textureData.put(var5 + var6 * 4 + 0, (byte)(par2 >> 24 & 0xFF));
                    this.textureData.put(var5 + var6 * 4 + 1, (byte)(par2 >> 16 & 0xFF));
                    this.textureData.put(var5 + var6 * 4 + 2, (byte)(par2 >> 8 & 0xFF));
                    this.textureData.put(var5 + var6 * 4 + 3, (byte)(par2 >> 0 & 0xFF));
                }
            }
            if (par1Rect2i.getRectX() == 0 && par1Rect2i.getRectY() == 0 && par1Rect2i.getRectWidth() == this.width && par1Rect2i.getRectHeight() == this.height) {
                this.textureNotModified = false;
            }
            if (this.autoCreate) {
                this.uploadTexture();
            }
            else {
                this.textureNotModified = false;
            }
        }
    }
    
    public void writeImage(final String par1Str) {
        final BufferedImage var2 = new BufferedImage(this.width, this.height, 2);
        final ByteBuffer var3 = this.getTextureData();
        final byte[] var4 = new byte[this.width * this.height * 4];
        var3.position(0);
        var3.get(var4);
        for (int var5 = 0; var5 < this.width; ++var5) {
            for (int var6 = 0; var6 < this.height; ++var6) {
                final int var7 = var6 * this.width * 4 + var5 * 4;
                final byte var8 = 0;
                int var9 = var8 | (var4[var7 + 2] & 0xFF) << 0;
                var9 |= (var4[var7 + 1] & 0xFF) << 8;
                var9 |= (var4[var7 + 0] & 0xFF) << 16;
                var9 |= (var4[var7 + 3] & 0xFF) << 24;
                var2.setRGB(var5, var6, var9);
            }
        }
        this.textureData.position(this.width * this.height * 4);
        try {
            ImageIO.write(var2, "png", new File(Minecraft.getMinecraftDir(), par1Str));
        }
        catch (Exception var10) {
            var10.printStackTrace();
        }
    }
    
    public void copyFrom(final int par1, final int par2, final Texture par3Texture, final boolean par4) {
        if (this.textureTarget != 32879) {
            if (this.textureNotModified) {
                if (!this.textureBound) {
                    return;
                }
                final ByteBuffer var5 = par3Texture.getTextureData();
                var5.position(0);
                GL11.glTexSubImage2D(this.textureTarget, 0, par1, par2, par3Texture.getWidth(), par3Texture.getHeight(), this.textureFormat, 5121, var5);
                if (this.mipmapActive) {
                    if (par3Texture.mipmapDatas == null) {
                        par3Texture.generateMipMapData();
                    }
                    final ByteBuffer[] var6 = par3Texture.mipmapDatas;
                    final Dimension[] var7 = par3Texture.mipmapDimensions;
                    if (var6 != null && var7 != null) {
                        this.registerMipMapsSub(par1, par2, var6, var7);
                    }
                }
            }
            else {
                final ByteBuffer var5 = par3Texture.getTextureData();
                this.textureData.position(0);
                var5.position(0);
                for (int var8 = 0; var8 < par3Texture.getHeight(); ++var8) {
                    int var9 = par2 + var8;
                    final int var10 = var8 * par3Texture.getWidth() * 4;
                    final int var11 = var9 * this.width * 4;
                    if (par4) {
                        var9 = par2 + (par3Texture.getHeight() - var8);
                    }
                    for (int var12 = 0; var12 < par3Texture.getWidth(); ++var12) {
                        int var13 = var11 + (var12 + par1) * 4;
                        final int var14 = var10 + var12 * 4;
                        if (par4) {
                            var13 = par1 + var12 * this.width * 4 + var9 * 4;
                        }
                        this.textureData.put(var13 + 0, var5.get(var14 + 0));
                        this.textureData.put(var13 + 1, var5.get(var14 + 1));
                        this.textureData.put(var13 + 2, var5.get(var14 + 2));
                        this.textureData.put(var13 + 3, var5.get(var14 + 3));
                    }
                }
                this.textureData.position(this.width * this.height * 4);
                if (this.autoCreate) {
                    this.uploadTexture();
                }
                else {
                    this.textureNotModified = false;
                }
            }
        }
    }
    
    public void func_104062_b(final int par1, final int par2, final Texture par3Texture) {
        if (!this.textureBound) {
            Config.getRenderEngine().bindTexture(this.glTextureId);
        }
        GL11.glTexSubImage2D(this.textureTarget, 0, par1, par2, par3Texture.getWidth(), par3Texture.getHeight(), this.textureFormat, 5121, (ByteBuffer)par3Texture.getTextureData().position(0));
        this.textureNotModified = true;
        if (this.mipmapActive) {
            if (par3Texture.mipmapDatas == null) {
                par3Texture.generateMipMapData();
            }
            final ByteBuffer[] var4 = par3Texture.mipmapDatas;
            final Dimension[] var5 = par3Texture.mipmapDimensions;
            if (var4 != null && var5 != null) {
                this.registerMipMapsSub(par1, par2, var4, var5);
            }
        }
    }
    
    public void transferFromImage(final BufferedImage par1BufferedImage) {
        if (this.textureTarget != 32879) {
            final int var2 = par1BufferedImage.getWidth();
            final int var3 = par1BufferedImage.getHeight();
            if (var2 <= this.width && var3 <= this.height) {
                final int[] var4 = { 3, 0, 1, 2 };
                final int[] var5 = { 3, 2, 1, 0 };
                final int[] var6 = (this.textureFormat == 32993) ? var5 : var4;
                final int[] var7 = new int[this.width * this.height];
                final int var8 = par1BufferedImage.getTransparency();
                par1BufferedImage.getRGB(0, 0, this.width, this.height, var7, 0, var2);
                final byte[] var9 = new byte[this.width * this.height * 4];
                long var10 = 0L;
                long var11 = 0L;
                long var12 = 0L;
                long var13 = 0L;
                for (int var14 = 0; var14 < this.height; ++var14) {
                    for (int var15 = 0; var15 < this.width; ++var15) {
                        final int var16 = var14 * this.width + var15;
                        final int var17 = var7[var16];
                        final int var18 = var17 >> 24 & 0xFF;
                        if (var18 != 0) {
                            final int var19 = var17 >> 16 & 0xFF;
                            final int var20 = var17 >> 8 & 0xFF;
                            final int var21 = var17 & 0xFF;
                            var10 += var19;
                            var11 += var20;
                            var12 += var21;
                            ++var13;
                        }
                    }
                }
                int var14 = 0;
                int var15 = 0;
                int var16 = 0;
                if (var13 > 0L) {
                    var14 = (int)(var10 / var13);
                    var15 = (int)(var11 / var13);
                    var16 = (int)(var12 / var13);
                }
                for (int var17 = 0; var17 < this.height; ++var17) {
                    for (int var18 = 0; var18 < this.width; ++var18) {
                        final int var19 = var17 * this.width + var18;
                        final int var20 = var19 * 4;
                        var9[var20 + var6[0]] = (byte)(var7[var19] >> 24 & 0xFF);
                        var9[var20 + var6[1]] = (byte)(var7[var19] >> 16 & 0xFF);
                        var9[var20 + var6[2]] = (byte)(var7[var19] >> 8 & 0xFF);
                        var9[var20 + var6[3]] = (byte)(var7[var19] >> 0 & 0xFF);
                        final byte var22 = (byte)(var7[var19] >> 24 & 0xFF);
                        if (var22 == 0) {
                            var9[var20 + var6[1]] = (byte)var14;
                            var9[var20 + var6[2]] = (byte)var15;
                            var9[var20 + var6[3]] = (byte)var16;
                        }
                    }
                }
                (this.textureData = GLAllocation.createDirectByteBuffer(var9.length)).clear();
                this.textureData.put(var9);
                this.textureData.limit(var9.length);
                if (this.autoCreate) {
                    this.uploadTexture();
                }
                else {
                    this.textureNotModified = false;
                }
            }
            else {
                Minecraft.getMinecraft().getLogAgent().logWarning("transferFromImage called with a BufferedImage with dimensions (" + var2 + ", " + var3 + ") larger than the Texture dimensions (" + this.width + ", " + this.height + "). Ignoring.");
            }
        }
    }
    
    public int getTextureId() {
        return this.textureId;
    }
    
    public int getGlTextureId() {
        return this.glTextureId;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public String getTextureName() {
        return this.textureName;
    }
    
    public void bindTexture(final int par1) {
        Config.getRenderEngine().bindTexture(this.glTextureId);
    }
    
    public void uploadTexture() {
        if (this.glTextureId <= 0) {
            this.glTextureId = GL11.glGenTextures();
            GL11.glBindTexture(this.textureTarget, this.glTextureId);
            GL11.glTexParameteri(this.textureTarget, 10241, this.textureMinFilter);
            GL11.glTexParameteri(this.textureTarget, 10240, this.textureMagFilter);
            if (this.mipmapActive) {
                this.updateMipmapLevel(16);
            }
            GL11.glTexParameteri(this.textureTarget, 10242, 10496);
            GL11.glTexParameteri(this.textureTarget, 10243, 10496);
        }
        this.textureData.clear();
        if (this.height != 1 && this.textureDepth != 1) {
            GL12.glTexImage3D(this.textureTarget, 0, this.textureFormat, this.width, this.height, this.textureDepth, 0, this.textureFormat, 5121, this.textureData);
        }
        else if (this.height != 1) {
            GL11.glTexImage2D(this.textureTarget, 0, this.textureFormat, this.width, this.height, 0, this.textureFormat, 5121, this.textureData);
            if (this.mipmapActive) {
                this.generateMipMaps(true);
            }
        }
        else {
            GL11.glTexImage1D(this.textureTarget, 0, this.textureFormat, this.width, 0, this.textureFormat, 5121, this.textureData);
        }
        this.textureNotModified = true;
    }
    
    public ByteBuffer getTextureData() {
        return this.textureData;
    }
    
    public void generateMipMapData() {
        this.generateMipMaps(false);
    }
    
    private void generateMipMaps(final boolean var1) {
        if (this.mipmapDatas == null) {
            this.allocateMipmapDatas();
        }
        ByteBuffer var2 = this.textureData;
        int var3 = this.width;
        boolean var4 = true;
        for (int var5 = 0; var5 < this.mipmapDatas.length; ++var5) {
            final ByteBuffer var6 = this.mipmapDatas[var5];
            final int var7 = var5 + 1;
            final Dimension var8 = this.mipmapDimensions[var5];
            final int var9 = var8.getWidth();
            final int var10 = var8.getHeight();
            if (var4) {
                var6.clear();
                var2.clear();
                for (int var11 = 0; var11 < var9; ++var11) {
                    for (int var12 = 0; var12 < var10; ++var12) {
                        final int var13 = var2.getInt((var11 * 2 + 0 + (var12 * 2 + 0) * var3) * 4);
                        final int var14 = var2.getInt((var11 * 2 + 1 + (var12 * 2 + 0) * var3) * 4);
                        final int var15 = var2.getInt((var11 * 2 + 1 + (var12 * 2 + 1) * var3) * 4);
                        final int var16 = var2.getInt((var11 * 2 + 0 + (var12 * 2 + 1) * var3) * 4);
                        final int var17 = this.alphaBlend(var13, var14, var15, var16);
                        var6.putInt((var11 + var12 * var9) * 4, var17);
                    }
                }
                var6.clear();
                var2.clear();
            }
            if (var1) {
                GL11.glTexImage2D(this.textureTarget, var7, this.textureFormat, var9, var10, 0, this.textureFormat, 5121, var6);
            }
            var2 = var6;
            var3 = var9;
            if (var9 <= 1 || var10 <= 1) {
                var4 = false;
            }
        }
    }
    
    private void registerMipMapsSub(final int var1, final int var2, final ByteBuffer[] var3, final Dimension[] var4) {
        int var5 = var1 / 2;
        int var6 = var2 / 2;
        for (int var7 = 0; var7 < var3.length; ++var7) {
            final ByteBuffer var8 = var3[var7];
            final int var9 = var7 + 1;
            final Dimension var10 = var4[var7];
            final int var11 = var10.getWidth();
            final int var12 = var10.getHeight();
            var8.clear();
            GL11.glTexSubImage2D(this.textureTarget, var9, var5, var6, var11, var12, this.textureFormat, 5121, var8);
            var5 /= 2;
            var6 /= 2;
        }
    }
    
    private int alphaBlend(final int var1, final int var2, final int var3, final int var4) {
        final int var5 = this.alphaBlend(var1, var2);
        final int var6 = this.alphaBlend(var3, var4);
        final int var7 = this.alphaBlend(var5, var6);
        return var7;
    }
    
    private int alphaBlend(int var1, int var2) {
        int var3 = (var1 & 0xFF000000) >> 24 & 0xFF;
        int var4 = (var2 & 0xFF000000) >> 24 & 0xFF;
        int var5 = (var3 + var4) / 2;
        if (var3 == 0 && var4 == 0) {
            var3 = 1;
            var4 = 1;
        }
        else {
            if (var3 == 0) {
                var1 = var2;
                var5 /= 2;
            }
            if (var4 == 0) {
                var2 = var1;
                var5 /= 2;
            }
        }
        final int var6 = (var1 >> 16 & 0xFF) * var3;
        final int var7 = (var1 >> 8 & 0xFF) * var3;
        final int var8 = (var1 & 0xFF) * var3;
        final int var9 = (var2 >> 16 & 0xFF) * var4;
        final int var10 = (var2 >> 8 & 0xFF) * var4;
        final int var11 = (var2 & 0xFF) * var4;
        final int var12 = (var6 + var9) / (var3 + var4);
        final int var13 = (var7 + var10) / (var3 + var4);
        final int var14 = (var8 + var11) / (var3 + var4);
        return var5 << 24 | var12 << 16 | var13 << 8 | var14;
    }
    
    private int averageColor(final int var1, final int var2) {
        final int var3 = (var1 & 0xFF000000) >> 24 & 0xFF;
        final int var4 = (var2 & 0xFF000000) >> 24 & 0xFF;
        return (var3 + var4 >> 1 << 24) + ((var1 & 0xFEFEFE) + (var2 & 0xFEFEFE) >> 1);
    }
    
    private void allocateMipmapDatas() {
        final int var1 = TextureUtils.ceilPowerOfTwo(this.width);
        final int var2 = TextureUtils.ceilPowerOfTwo(this.height);
        if (var1 == this.width && var2 == this.height) {
            final int var3 = var1 * var2 * 4;
            final ArrayList var4 = new ArrayList();
            final ArrayList var5 = new ArrayList();
            int var6 = var1;
            int var7 = var2;
            while (true) {
                var6 /= 2;
                var7 /= 2;
                if (var6 <= 0 && var7 <= 0) {
                    break;
                }
                if (var6 <= 0) {
                    var6 = 1;
                }
                if (var7 <= 0) {
                    var7 = 1;
                }
                final int var8 = var6 * var7 * 4;
                final ByteBuffer var9 = GLAllocation.createDirectByteBuffer(var8);
                var4.add(var9);
                final Dimension var10 = new Dimension(var6, var7);
                var5.add(var10);
            }
            this.mipmapDatas = var4.toArray(new ByteBuffer[var4.size()]);
            this.mipmapDimensions = var5.toArray(new Dimension[var5.size()]);
            return;
        }
        Config.dbg("Mipmaps not possible (power of 2 dimensions needed), texture: " + this.textureName + ", dim: " + this.width + "x" + this.height);
        this.mipmapDatas = new ByteBuffer[0];
        this.mipmapDimensions = new Dimension[0];
    }
    
    private int getMaxMipmapLevel(int var1) {
        int var2;
        for (var2 = 0; var1 > 0; var1 /= 2, ++var2) {}
        return var2 - 1;
    }
    
    public static boolean isMipMapTexture(final int var0, final String var1) {
        return var0 == 3 || (var0 != 2 && var1.equals("terrain"));
    }
    
    public void scaleUp(final int var1) {
        if (this.textureTarget == 3553) {
            final int var2 = TextureUtils.ceilPowerOfTwo(var1);
            final int var3 = Math.max(this.width, this.height);
            for (int var4 = TextureUtils.ceilPowerOfTwo(var3); var4 < var2; var4 *= 2) {
                this.scale2x();
            }
        }
    }
    
    private void scale2x() {
        final int var1 = this.width;
        final int var2 = this.height;
        final byte[] var3 = new byte[this.width * this.height * 4];
        this.textureData.position(0);
        this.textureData.get(var3);
        this.width *= 2;
        this.height *= 2;
        this.textureRect = new Rect2i(0, 0, this.width, this.height);
        this.copyScaled(var3, var1, this.textureData = GLAllocation.createDirectByteBuffer(this.width * this.height * 4), this.width);
    }
    
    private void copyScaled(final byte[] var1, final int var2, final ByteBuffer var3, final int var4) {
        final int var5 = var4 / var2;
        final byte[] var6 = new byte[4];
        final int var7 = var4 * var4;
        var3.clear();
        if (var5 > 1) {
            for (int var8 = 0; var8 < var2; ++var8) {
                final int var9 = var8 * var2;
                final int var10 = var8 * var5;
                final int var11 = var10 * var4;
                for (int var12 = 0; var12 < var2; ++var12) {
                    final int var13 = (var12 + var9) * 4;
                    var6[0] = var1[var13];
                    var6[1] = var1[var13 + 1];
                    var6[2] = var1[var13 + 2];
                    var6[3] = var1[var13 + 3];
                    final int var14 = var12 * var5;
                    final int var15 = var14 + var11;
                    for (int var16 = 0; var16 < var5; ++var16) {
                        final int var17 = var15 + var16 * var4;
                        var3.position(var17 * 4);
                        for (int var18 = 0; var18 < var5; ++var18) {
                            var3.put(var6);
                        }
                    }
                }
            }
        }
        var3.position(0).limit(var4 * var4 * 4);
    }
    
    public void updateMipmapLevel(final int var1) {
        if (this.mipmapActive) {
            if (GLContext.getCapabilities().OpenGL12) {
                GL11.glTexParameteri(3553, 33084, 0);
                int var2 = Config.getMipmapLevel();
                if (var2 >= 4) {
                    final int var3 = Math.min(this.width, this.height);
                    var2 = this.getMaxMipmapLevel(var3);
                    if (var1 > 1) {
                        final int var4 = var2 = TextureUtils.getPowerOfTwo(var1);
                    }
                    if (var2 < 0) {
                        var2 = 0;
                    }
                }
                GL11.glTexParameteri(3553, 33085, var2);
            }
            if (Config.getAnisotropicFilterLevel() > 1 && GLContext.getCapabilities().GL_EXT_texture_filter_anisotropic) {
                final FloatBuffer var5 = BufferUtils.createFloatBuffer(16);
                var5.rewind();
                GL11.glGetFloat(34047, var5);
                final float var6 = var5.get(0);
                float var7 = Config.getAnisotropicFilterLevel();
                var7 = Math.min(var7, var6);
                GL11.glTexParameterf(3553, 34046, var7);
            }
        }
    }
    
    public void setTextureBound(final boolean var1) {
        this.textureBound = var1;
    }
    
    public boolean isTextureBound() {
        return this.textureBound;
    }
    
    public void deleteTexture() {
        if (this.glTextureId > 0) {
            GL11.glDeleteTextures(this.glTextureId);
            this.glTextureId = 0;
        }
        this.textureData = null;
        this.mipmapDatas = null;
        this.mipmapDimensions = null;
    }
    
    @Override
    public String toString() {
        return "Texture: " + this.textureName + ", dim: " + this.width + "x" + this.height + ", gl: " + this.glTextureId + ", created: " + this.textureNotModified;
    }
    
    public Texture duplicate(final int var1) {
        final Texture var2 = new Texture(this.textureName, var1, this.width, this.height, this.textureDepth, this.textureWrap, this.textureFormat, this.textureMinFilter, this.textureMagFilter);
        this.textureData.clear();
        (var2.textureData = GLAllocation.createDirectByteBuffer(this.textureData.capacity())).put(this.textureData);
        this.textureData.clear();
        var2.textureData.clear();
        return var2;
    }
    
    public void createAndUploadTexture() {
        Config.dbg("Forge method not implemented: TextureStitched.createAndUploadTexture()");
    }
}
