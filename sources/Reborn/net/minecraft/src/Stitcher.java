package net.minecraft.src;

import java.util.*;

public class Stitcher
{
    private final Set setStitchHolders;
    private final List stitchSlots;
    private int currentWidth;
    private int currentHeight;
    private final int maxWidth;
    private final int maxHeight;
    private final boolean forcePowerOf2;
    private final int maxTileDimension;
    private Texture atlasTexture;
    private final String textureName;
    
    public Stitcher(final String par1Str, final int par2, final int par3, final boolean par4) {
        this(par1Str, par2, par3, par4, 0);
    }
    
    public Stitcher(final String par1, final int par2, final int par3, final boolean par4, final int par5) {
        this.setStitchHolders = new HashSet(256);
        this.stitchSlots = new ArrayList(256);
        this.currentWidth = 0;
        this.currentHeight = 0;
        this.textureName = par1;
        this.maxWidth = par2;
        this.maxHeight = par3;
        this.forcePowerOf2 = par4;
        this.maxTileDimension = par5;
    }
    
    public void addStitchHolder(final StitchHolder par1StitchHolder) {
        if (this.maxTileDimension > 0) {
            par1StitchHolder.setNewDimension(this.maxTileDimension);
        }
        this.setStitchHolders.add(par1StitchHolder);
    }
    
    public Texture getTexture() {
        if (this.forcePowerOf2) {
            this.currentWidth = this.getCeilPowerOf2(this.currentWidth);
            this.currentHeight = this.getCeilPowerOf2(this.currentHeight);
        }
        (this.atlasTexture = TextureManager.instance().createEmptyTexture(this.textureName, 1, this.currentWidth, this.currentHeight, 6408)).fillRect(this.atlasTexture.getTextureRect(), -65536);
        final List var1 = this.getStichSlots();
        for (int var2 = 0; var2 < var1.size(); ++var2) {
            final StitchSlot var3 = var1.get(var2);
            final StitchHolder var4 = var3.getStitchHolder();
            this.atlasTexture.copyFrom(var3.getOriginX(), var3.getOriginY(), var4.func_98150_a(), var4.isRotated());
        }
        TextureManager.instance().registerTexture(this.textureName, this.atlasTexture);
        return this.atlasTexture;
    }
    
    public void doStitch() {
        final StitchHolder[] var1 = this.setStitchHolders.toArray(new StitchHolder[this.setStitchHolders.size()]);
        Arrays.sort(var1);
        this.atlasTexture = null;
        for (int var2 = 0; var2 < var1.length; ++var2) {
            final StitchHolder var3 = var1[var2];
            if (!this.allocateSlot(var3)) {
                throw new StitcherException(var3);
            }
        }
    }
    
    public List getStichSlots() {
        final ArrayList var1 = new ArrayList();
        for (final StitchSlot var3 : this.stitchSlots) {
            var3.getAllStitchSlots(var1);
        }
        return var1;
    }
    
    private int getCeilPowerOf2(final int par1) {
        int var2 = par1 - 1;
        var2 |= var2 >> 1;
        var2 |= var2 >> 2;
        var2 |= var2 >> 4;
        var2 |= var2 >> 8;
        var2 |= var2 >> 16;
        return var2 + 1;
    }
    
    private boolean allocateSlot(final StitchHolder par1StitchHolder) {
        for (int var2 = 0; var2 < this.stitchSlots.size(); ++var2) {
            if (this.stitchSlots.get(var2).func_94182_a(par1StitchHolder)) {
                return true;
            }
            par1StitchHolder.rotate();
            if (this.stitchSlots.get(var2).func_94182_a(par1StitchHolder)) {
                return true;
            }
            par1StitchHolder.rotate();
        }
        return this.expandAndAllocateSlot(par1StitchHolder);
    }
    
    private boolean expandAndAllocateSlot(final StitchHolder par1StitchHolder) {
        final int var2 = Math.min(par1StitchHolder.getHeight(), par1StitchHolder.getWidth());
        final boolean var3 = this.currentWidth == 0 && this.currentHeight == 0;
        boolean var16;
        if (this.forcePowerOf2) {
            final int var4 = this.getCeilPowerOf2(this.currentWidth);
            final int var5 = this.getCeilPowerOf2(this.currentHeight);
            final int var6 = this.getCeilPowerOf2(this.currentWidth + var2);
            final int var7 = this.getCeilPowerOf2(this.currentHeight + var2);
            final boolean var8 = var6 <= this.maxWidth;
            final boolean var9 = var7 <= this.maxHeight;
            if (!var8 && !var9) {
                return false;
            }
            final int var10 = Math.max(par1StitchHolder.getHeight(), par1StitchHolder.getWidth());
            if (var3 && !var8 && this.getCeilPowerOf2(this.currentHeight + var10) > this.maxHeight) {
                return false;
            }
            final boolean var11 = var4 != var6;
            final boolean var12 = var5 != var7;
            if (var11 ^ var12) {
                if (var11 && var8) {
                    final boolean var13 = true;
                }
                else {
                    final boolean var13 = false;
                }
            }
            else if (var8 && var4 <= var5) {
                final boolean var13 = true;
            }
            else {
                final boolean var13 = false;
            }
            final int var14 = this.getCeilPowerOf2(this.currentWidth + var2);
            final int var15 = this.getCeilPowerOf2(this.currentHeight + var2);
            var16 = (var14 <= var15);
        }
        else {
            final boolean var17 = this.currentWidth + var2 <= this.maxWidth;
            final boolean var18 = this.currentHeight + var2 <= this.maxHeight;
            if (!var17 && !var18) {
                return false;
            }
            var16 = ((var3 || this.currentWidth <= this.currentHeight) && var17);
        }
        StitchSlot var19;
        if (var16) {
            if (par1StitchHolder.getWidth() > par1StitchHolder.getHeight()) {
                par1StitchHolder.rotate();
            }
            if (this.currentHeight == 0) {
                this.currentHeight = par1StitchHolder.getHeight();
            }
            var19 = new StitchSlot(this.currentWidth, 0, par1StitchHolder.getWidth(), this.currentHeight);
            this.currentWidth += par1StitchHolder.getWidth();
        }
        else {
            var19 = new StitchSlot(0, this.currentHeight, this.currentWidth, par1StitchHolder.getHeight());
            this.currentHeight += par1StitchHolder.getHeight();
        }
        var19.func_94182_a(par1StitchHolder);
        this.stitchSlots.add(var19);
        return true;
    }
    
    private int floorPowerOf2(final int var1) {
        final int var2 = this.getCeilPowerOf2(var1);
        return (var1 < var2) ? (var2 / 2) : var2;
    }
}
