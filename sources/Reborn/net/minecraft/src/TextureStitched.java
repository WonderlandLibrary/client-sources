package net.minecraft.src;

import java.io.*;
import java.util.*;
import java.awt.image.*;

public class TextureStitched implements Icon
{
    private final String textureName;
    protected Texture textureSheet;
    protected List textureList;
    private List listAnimationTuples;
    protected boolean rotated;
    protected int originX;
    protected int originY;
    private int width;
    private int height;
    private float minU;
    private float maxU;
    private float minV;
    private float maxV;
    private float widthNorm;
    private float heightNorm;
    protected int frameCounter;
    protected int tickCounter;
    private int indexInMap;
    public float baseU;
    public float baseV;
    public Texture tileTexture;
    private int currentAnimationIndex;
    
    public static TextureStitched makeTextureStitched(final String par0Str) {
        return "clock".equals(par0Str) ? new TextureClock() : ("compass".equals(par0Str) ? new TextureCompass() : new TextureStitched(par0Str));
    }
    
    protected TextureStitched(final String par1) {
        this.frameCounter = 0;
        this.tickCounter = 0;
        this.indexInMap = -1;
        this.tileTexture = null;
        this.currentAnimationIndex = -1;
        this.textureName = par1;
    }
    
    public void init(final Texture par1Texture, final List par2List, final int par3, final int par4, final int par5, final int par6, final boolean par7) {
        this.textureSheet = par1Texture;
        this.textureList = par2List;
        this.originX = par3;
        this.originY = par4;
        this.width = par5;
        this.height = par6;
        this.rotated = par7;
        final float var8 = 0.01f / par1Texture.getWidth();
        final float var9 = 0.01f / par1Texture.getHeight();
        this.minU = par3 / par1Texture.getWidth() + var8;
        this.maxU = (par3 + par5) / par1Texture.getWidth() - var8;
        this.minV = par4 / par1Texture.getHeight() + var9;
        this.maxV = (par4 + par6) / par1Texture.getHeight() - var9;
        this.widthNorm = par5 / 16.0f;
        this.heightNorm = par6 / 16.0f;
        this.baseU = Math.min(this.minU, this.maxU);
        this.baseV = Math.min(this.minV, this.maxV);
    }
    
    public void copyFrom(final TextureStitched par1TextureStitched) {
        this.init(par1TextureStitched.textureSheet, par1TextureStitched.textureList, par1TextureStitched.originX, par1TextureStitched.originY, par1TextureStitched.width, par1TextureStitched.height, par1TextureStitched.rotated);
    }
    
    @Override
    public int getOriginX() {
        return this.originX;
    }
    
    @Override
    public int getOriginY() {
        return this.originY;
    }
    
    @Override
    public float getMinU() {
        return this.minU;
    }
    
    @Override
    public float getMaxU() {
        return this.maxU;
    }
    
    @Override
    public float getInterpolatedU(final double par1) {
        final float var3 = this.maxU - this.minU;
        return this.minU + var3 * ((float)par1 / 16.0f);
    }
    
    @Override
    public float getMinV() {
        return this.minV;
    }
    
    @Override
    public float getMaxV() {
        return this.maxV;
    }
    
    @Override
    public float getInterpolatedV(final double par1) {
        final float var3 = this.maxV - this.minV;
        return this.minV + var3 * ((float)par1 / 16.0f);
    }
    
    @Override
    public String getIconName() {
        return this.textureName;
    }
    
    @Override
    public int getSheetWidth() {
        return this.textureSheet.getWidth();
    }
    
    @Override
    public int getSheetHeight() {
        return this.textureSheet.getHeight();
    }
    
    public void updateAnimation() {
        if (this.listAnimationTuples != null) {
            Tuple var1 = this.listAnimationTuples.get(this.frameCounter);
            ++this.tickCounter;
            if (this.tickCounter >= (int)var1.getSecond()) {
                final int var2 = (int)var1.getFirst();
                this.frameCounter = (this.frameCounter + 1) % this.listAnimationTuples.size();
                this.tickCounter = 0;
                var1 = this.listAnimationTuples.get(this.frameCounter);
                final int var3 = (int)var1.getFirst();
                if (var2 != var3 && var3 >= 0 && var3 < this.textureList.size()) {
                    this.textureSheet.func_104062_b(this.originX, this.originY, this.textureList.get(var3));
                    this.currentAnimationIndex = var3;
                }
            }
        }
        else {
            final int var4 = this.frameCounter;
            this.frameCounter = (this.frameCounter + 1) % this.textureList.size();
            if (var4 != this.frameCounter) {
                this.textureSheet.func_104062_b(this.originX, this.originY, this.textureList.get(this.frameCounter));
                this.currentAnimationIndex = this.frameCounter;
            }
        }
    }
    
    public void readAnimationInfo(final BufferedReader par1BufferedReader) {
        final ArrayList var2 = new ArrayList();
        try {
            for (String var3 = par1BufferedReader.readLine(); var3 != null; var3 = par1BufferedReader.readLine()) {
                var3 = var3.trim();
                if (var3.length() > 0) {
                    final String[] var5;
                    final String[] var4 = var5 = var3.split(",");
                    for (int var6 = var4.length, var7 = 0; var7 < var6; ++var7) {
                        final String var8 = var5[var7];
                        final int var9 = var8.indexOf(42);
                        if (var9 > 0) {
                            final Integer var10 = new Integer(var8.substring(0, var9));
                            final Integer var11 = new Integer(var8.substring(var9 + 1));
                            var2.add(new Tuple(var10, var11));
                        }
                        else {
                            var2.add(new Tuple(new Integer(var8), 1));
                        }
                    }
                }
            }
        }
        catch (Exception var12) {
            System.err.println("Failed to read animation info for " + this.textureName + ": " + var12.getMessage());
        }
        if (!var2.isEmpty() && var2.size() < 600) {
            this.listAnimationTuples = var2;
        }
    }
    
    public Texture getTexture() {
        return this.textureSheet;
    }
    
    public int getIndexInMap() {
        return this.indexInMap;
    }
    
    public void setIndexInMap(final int var1) {
        this.indexInMap = var1;
    }
    
    public void deleteTextures() {
        if (this.tileTexture != null) {
            this.tileTexture.deleteTexture();
        }
        if (this.textureList != null) {
            for (int var1 = 0; var1 < this.textureList.size(); ++var1) {
                final Texture var2 = this.textureList.get(var1);
                var2.deleteTexture();
            }
        }
    }
    
    public void createTileTexture() {
        if (this.tileTexture == null) {
            final Texture var1 = this.textureList.get(0);
            (this.tileTexture = var1.duplicate(3)).uploadTexture();
        }
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public String toString() {
        return "Icon: " + this.textureName + ", " + this.originX + "," + this.originY + ", " + this.width + "x" + this.height + ", " + this.rotated;
    }
    
    public void updateTileAnimation() {
        if (this.tileTexture != null && this.currentAnimationIndex >= 0) {
            final Texture var1 = this.textureList.get(this.currentAnimationIndex);
            this.tileTexture.bindTexture(0);
            this.tileTexture.setTextureBound(true);
            this.tileTexture.copyFrom(0, 0, var1, false);
            this.tileTexture.setTextureBound(false);
            this.currentAnimationIndex = -1;
        }
    }
    
    public boolean loadTexture(final TextureManager var1, final ITexturePack var2, final String var3, final String var4, final BufferedImage var5, final ArrayList var6) {
        return false;
    }
    
    public void createAndUploadTextures() {
        Config.dbg("Forge method not implemented: TextureStitched.createAndUploadTextures()");
    }
}
