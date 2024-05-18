package net.minecraft.src;

import java.awt.image.*;
import net.minecraft.client.*;
import java.util.*;
import java.io.*;

public class TextureMap implements IconRegister
{
    public final int textureType;
    public final String textureName;
    public final String basePath;
    public final String textureExt;
    private final HashMap mapTexturesStiched;
    private BufferedImage missingImage;
    private TextureStitched missingTextureStiched;
    private Texture atlasTexture;
    private final List listTextureStiched;
    private final Map textureStichedMap;
    private int iconGridSize;
    private int iconGridCountX;
    private int iconGridCountY;
    private double iconGridSizeU;
    private double iconGridSizeV;
    private TextureStitched[] iconGrid;
    
    public TextureMap(final int par1, final String par2, final String par3Str, final BufferedImage par4BufferedImage) {
        this.mapTexturesStiched = new HashMap();
        this.missingImage = new BufferedImage(64, 64, 2);
        this.listTextureStiched = new ArrayList();
        this.textureStichedMap = new HashMap();
        this.iconGridSize = -1;
        this.iconGridCountX = -1;
        this.iconGridCountY = -1;
        this.iconGridSizeU = -1.0;
        this.iconGridSizeV = -1.0;
        this.iconGrid = null;
        this.textureType = par1;
        this.textureName = par2;
        this.basePath = par3Str;
        this.textureExt = ".png";
        this.missingImage = par4BufferedImage;
    }
    
    public void refreshTextures() {
        Config.dbg("Creating texture map: " + this.textureName);
        if (this.atlasTexture != null) {
            this.atlasTexture.deleteTexture();
        }
        for (final TextureStitched var2 : this.textureStichedMap.values()) {
            var2.deleteTextures();
        }
        this.textureStichedMap.clear();
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, this);
        if (this.textureType == 0) {
            for (final Block var6 : Block.blocksList) {
                if (var6 != null) {
                    var6.registerIcons(this);
                }
            }
            Minecraft.getMinecraft().renderGlobal.registerDestroyBlockIcons(this);
            RenderManager.instance.updateIcons(this);
            ConnectedTextures.updateIcons(this);
            NaturalTextures.updateIcons(this);
        }
        for (final Item var8 : Item.itemsList) {
            if (var8 != null && var8.getSpriteNumber() == this.textureType) {
                var8.registerIcons(this);
            }
        }
        final HashMap var9 = new HashMap();
        final Stitcher var10 = TextureManager.instance().createStitcher(this.textureName);
        this.mapTexturesStiched.clear();
        this.listTextureStiched.clear();
        final Texture var11 = TextureManager.instance().makeTexture("missingno", 2, this.missingImage.getWidth(), this.missingImage.getHeight(), 10496, 6408, 9728, 9728, false, this.missingImage);
        final StitchHolder var12 = new StitchHolder(var11);
        var10.addStitchHolder(var12);
        var9.put(var12, Arrays.asList(var11));
        Iterator var13 = this.textureStichedMap.keySet().iterator();
        final ArrayList var14 = new ArrayList();
        while (var13.hasNext()) {
            final String var15 = var13.next();
            final String var16 = String.valueOf(this.makeFullTextureName(var15)) + this.textureExt;
            final List var17 = TextureManager.instance().createNewTexture(var15, var16, this.textureStichedMap.get(var15));
            var14.add(var17);
        }
        this.iconGridSize = this.getStandardTileSize(var14);
        Config.dbg("Icon grid size: " + this.textureName + ", " + this.iconGridSize);
        for (final List var19 : var14) {
            if (!var19.isEmpty()) {
                this.scaleTextures(var19, this.iconGridSize);
            }
        }
        for (final List var19 : var14) {
            if (!var19.isEmpty()) {
                final StitchHolder var20 = new StitchHolder(var19.get(0));
                var10.addStitchHolder(var20);
                var9.put(var20, var19);
            }
        }
        try {
            var10.doStitch();
        }
        catch (StitcherException var21) {
            throw var21;
        }
        this.atlasTexture = var10.getTexture();
        Config.dbg("Texture size: " + this.textureName + ", " + this.atlasTexture.getWidth() + "x" + this.atlasTexture.getHeight());
        this.atlasTexture.updateMipmapLevel(this.iconGridSize);
        var13 = var10.getStichSlots().iterator();
        while (var13.hasNext()) {
            final StitchSlot var22 = var13.next();
            final StitchHolder var23 = var22.getStitchHolder();
            final Texture var24 = var23.func_98150_a();
            final String var25 = var24.getTextureName();
            final List var26 = var9.get(var23);
            TextureStitched var27 = this.textureStichedMap.get(var25);
            boolean var28 = false;
            if (var27 == null) {
                var28 = true;
                var27 = TextureStitched.makeTextureStitched(var25);
                if (!var25.equals("missingno")) {
                    Minecraft.getMinecraft().getLogAgent().logWarning("Couldn't find premade icon for " + var25 + " doing " + this.textureName);
                }
            }
            var27.init(this.atlasTexture, var26, var22.getOriginX(), var22.getOriginY(), var23.func_98150_a().getWidth(), var23.func_98150_a().getHeight(), var23.isRotated());
            this.mapTexturesStiched.put(var25, var27);
            if (!var28) {
                this.textureStichedMap.remove(var25);
            }
            if (var26.size() > 1) {
                this.listTextureStiched.add(var27);
                final String var29 = String.valueOf(this.makeFullTextureName(var25)) + ".txt";
                final ITexturePack var30 = Minecraft.getMinecraft().texturePackList.getSelectedTexturePack();
                final boolean var31 = !var30.func_98138_b("/" + this.basePath + var25 + ".png", false);
                try {
                    final InputStream var32 = var30.func_98137_a("/" + var29, var31);
                    Minecraft.getMinecraft().getLogAgent().logInfo("Found animation info for: " + var29);
                    var27.readAnimationInfo(new BufferedReader(new InputStreamReader(var32)));
                }
                catch (IOException ex) {}
            }
        }
        this.missingTextureStiched = this.mapTexturesStiched.get("missingno");
        var13 = this.textureStichedMap.values().iterator();
        while (var13.hasNext()) {
            final TextureStitched var33 = var13.next();
            var33.copyFrom(this.missingTextureStiched);
        }
        this.textureStichedMap.putAll(this.mapTexturesStiched);
        this.mapTexturesStiched.clear();
        this.updateIconGrid();
        this.atlasTexture.writeImage("debug.stitched_" + this.textureName + ".png");
        Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, this);
        this.atlasTexture.uploadTexture();
        if (Config.isMultiTexture()) {
            for (final TextureStitched var33 : this.textureStichedMap.values()) {
                var33.createTileTexture();
            }
        }
    }
    
    public void updateAnimations() {
        if (this.listTextureStiched.size() > 0) {
            this.getTexture().bindTexture(0);
            this.atlasTexture.setTextureBound(true);
            for (final TextureStitched var2 : this.listTextureStiched) {
                if (this.textureType == 0) {
                    if (!this.isTerrainAnimationActive(var2)) {
                        continue;
                    }
                }
                else if (this.textureType == 1 && !Config.isAnimatedItems()) {
                    continue;
                }
                var2.updateAnimation();
            }
            this.atlasTexture.setTextureBound(false);
            if (Config.isMultiTexture()) {
                for (int var3 = 0; var3 < this.listTextureStiched.size(); ++var3) {
                    final TextureStitched var4 = this.listTextureStiched.get(var3);
                    if (this.isTerrainAnimationActive(var4)) {
                        var4.updateTileAnimation();
                    }
                }
            }
        }
    }
    
    public Texture getTexture() {
        return this.atlasTexture;
    }
    
    @Override
    public Icon registerIcon(String par1Str) {
        if (par1Str == null) {
            new RuntimeException("Don't register null!").printStackTrace();
            par1Str = "null";
        }
        TextureStitched var2 = this.textureStichedMap.get(par1Str);
        if (var2 == null) {
            var2 = TextureStitched.makeTextureStitched(par1Str);
            var2.setIndexInMap(this.textureStichedMap.size());
            this.textureStichedMap.put(par1Str, var2);
        }
        return var2;
    }
    
    public Icon getMissingIcon() {
        return this.missingTextureStiched;
    }
    
    private String makeFullTextureName(final String var1) {
        final int var2 = var1.indexOf(":");
        if (var2 > 0) {
            final String var3 = var1.substring(0, var2);
            final String var4 = var1.substring(var2 + 1);
            return "mods/" + var3 + "/" + this.basePath + var4;
        }
        return var1.startsWith("ctm/") ? var1 : (String.valueOf(this.basePath) + var1);
    }
    
    public TextureStitched getIconSafe(final String var1) {
        return this.textureStichedMap.get(var1);
    }
    
    private int getStandardTileSize(final List var1) {
        final int[] var2 = new int[16];
        for (final List var4 : var1) {
            if (!var4.isEmpty()) {
                final Texture var5 = var4.get(0);
                if (var5 == null) {
                    continue;
                }
                final int var6 = TextureUtils.getPowerOfTwo(var5.getWidth());
                final int var7 = TextureUtils.getPowerOfTwo(var5.getHeight());
                final int var8 = Math.max(var6, var7);
                if (var8 >= var2.length) {
                    continue;
                }
                final int[] array = var2;
                final int n = var8;
                ++array[n];
            }
        }
        int var9 = 4;
        int var10 = 0;
        for (int var7 = 0; var7 < var2.length; ++var7) {
            final int var6 = var2[var7];
            if (var6 > var10) {
                var9 = var7;
                var10 = var6;
            }
        }
        if (var9 < 4) {
            var9 = 4;
        }
        int var7 = TextureUtils.twoToPower(var9);
        return var7;
    }
    
    private void scaleTextures(final List var1, final int var2) {
        if (!var1.isEmpty()) {
            final Texture var3 = var1.get(0);
            final int var4 = Math.max(var3.getWidth(), var3.getHeight());
            if (var4 < var2) {
                for (int var5 = 0; var5 < var1.size(); ++var5) {
                    final Texture var6 = var1.get(var5);
                    var6.scaleUp(var2);
                }
            }
        }
    }
    
    public TextureStitched getTextureExtry(final String var1) {
        return this.textureStichedMap.get(var1);
    }
    
    public boolean setTextureEntry(final String var1, final TextureStitched var2) {
        if (!this.textureStichedMap.containsKey(var1)) {
            var2.setIndexInMap(this.textureStichedMap.size());
            this.textureStichedMap.put(var1, var2);
            return true;
        }
        return false;
    }
    
    private void updateIconGrid() {
        this.iconGridCountX = -1;
        this.iconGridCountY = -1;
        this.iconGrid = null;
        if (this.iconGridSize > 0) {
            this.iconGridCountX = this.atlasTexture.getWidth() / this.iconGridSize;
            this.iconGridCountY = this.atlasTexture.getHeight() / this.iconGridSize;
            this.iconGrid = new TextureStitched[this.iconGridCountX * this.iconGridCountY];
            this.iconGridSizeU = 1.0 / this.iconGridCountX;
            this.iconGridSizeV = 1.0 / this.iconGridCountY;
            for (final TextureStitched var2 : this.textureStichedMap.values()) {
                final double var3 = Math.min(var2.getMinU(), var2.getMaxU());
                final double var4 = Math.min(var2.getMinV(), var2.getMaxV());
                final double var5 = Math.max(var2.getMinU(), var2.getMaxU());
                final double var6 = Math.max(var2.getMinV(), var2.getMaxV());
                final int var7 = (int)(var3 / this.iconGridSizeU);
                final int var8 = (int)(var4 / this.iconGridSizeV);
                final int var9 = (int)(var5 / this.iconGridSizeU);
                final int var10 = (int)(var6 / this.iconGridSizeV);
                for (int var11 = var7; var11 <= var9; ++var11) {
                    if (var11 >= 0 && var11 < this.iconGridCountX) {
                        for (int var12 = var8; var12 <= var10; ++var12) {
                            if (var12 >= 0 && var12 < this.iconGridCountX) {
                                final int var13 = var12 * this.iconGridCountX + var11;
                                this.iconGrid[var13] = var2;
                            }
                            else {
                                Config.dbg("Invalid grid V: " + var12 + ", icon: " + var2.getIconName());
                            }
                        }
                    }
                    else {
                        Config.dbg("Invalid grid U: " + var11 + ", icon: " + var2.getIconName());
                    }
                }
            }
        }
    }
    
    public TextureStitched getIconByUV(final double var1, final double var3) {
        if (this.iconGrid == null) {
            return null;
        }
        final int var4 = (int)(var1 / this.iconGridSizeU);
        final int var5 = (int)(var3 / this.iconGridSizeV);
        final int var6 = var5 * this.iconGridCountX + var4;
        return (var6 >= 0 && var6 <= this.iconGrid.length) ? this.iconGrid[var6] : null;
    }
    
    public TextureStitched getMissingTextureStiched() {
        return this.missingTextureStiched;
    }
    
    public int getMaxTextureIndex() {
        return this.textureStichedMap.size();
    }
    
    private boolean isTerrainAnimationActive(final TextureStitched var1) {
        return (var1 != TextureUtils.iconWater && var1 != TextureUtils.iconWaterFlow) ? ((var1 != TextureUtils.iconLava && var1 != TextureUtils.iconLavaFlow) ? ((var1 != TextureUtils.iconFire0 && var1 != TextureUtils.iconFire1) ? ((var1 == TextureUtils.iconPortal) ? Config.isAnimatedPortal() : Config.isAnimatedTerrain()) : Config.isAnimatedFire()) : Config.isAnimatedLava()) : Config.isAnimatedWater();
    }
}
