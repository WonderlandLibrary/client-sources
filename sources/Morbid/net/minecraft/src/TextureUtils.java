package net.minecraft.src;

import java.util.*;
import java.awt.image.*;
import java.awt.*;

public class TextureUtils
{
    public static final String texGrassTop = "grass_top";
    public static final String texStone = "stone";
    public static final String texDirt = "dirt";
    public static final String texGrassSide = "grass_side";
    public static final String texStoneslabSide = "stoneslab_side";
    public static final String texStoneslabTop = "stoneslab_top";
    public static final String texBedrock = "bedrock";
    public static final String texSand = "sand";
    public static final String texGravel = "gravel";
    public static final String texTreeSide = "tree_side";
    public static final String texTreeTop = "tree_top";
    public static final String texOreGold = "oreGold";
    public static final String texOreIron = "oreIron";
    public static final String texOreCoal = "oreCoal";
    public static final String texObsidian = "obsidian";
    public static final String texGrassSideOverlay = "grass_side_overlay";
    public static final String texSnow = "snow";
    public static final String texSnowSide = "snow_side";
    public static final String texMycelSide = "mycel_side";
    public static final String texMycelTop = "mycel_top";
    public static final String texOreDiamond = "oreDiamond";
    public static final String texOreRedstone = "oreRedstone";
    public static final String texOreLapis = "oreLapis";
    public static final String texLeaves = "leaves";
    public static final String texLeavesOpaque = "leaves_opaque";
    public static final String texLeavesJungle = "leaves_jungle";
    public static final String texLeavesJungleOpaque = "leaves_jungle_opaque";
    public static final String texCactusSide = "cactus_side";
    public static final String texClay = "clay";
    public static final String texFarmlandWet = "farmland_wet";
    public static final String texFarmlandDry = "farmland_dry";
    public static final String texHellrock = "hellrock";
    public static final String texHellsand = "hellsand";
    public static final String texLightgem = "lightgem";
    public static final String texTreeSpruce = "tree_spruce";
    public static final String texTreeBirch = "tree_birch";
    public static final String texLeavesSpruce = "leaves_spruce";
    public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
    public static final String texTreeJungle = "tree_jungle";
    public static final String texWhiteStone = "whiteStone";
    public static final String texSandstoneTop = "sandstone_top";
    public static final String texSandstoneBottom = "sandstone_bottom";
    public static final String texRedstoneLight = "redstoneLight";
    public static final String texRedstoneLightLit = "redstoneLight_lit";
    public static final String texWater = "water";
    public static final String texWaterFlow = "water_flow";
    public static final String texLava = "lava";
    public static final String texLavaFlow = "lava_flow";
    public static final String texFire0 = "fire_0";
    public static final String texFire1 = "fire_1";
    public static final String texPortal = "portal";
    public static Icon iconGrassTop;
    public static Icon iconGrassSide;
    public static Icon iconGrassSideOverlay;
    public static Icon iconSnow;
    public static Icon iconSnowSide;
    public static Icon iconMycelSide;
    public static Icon iconMycelTop;
    public static Icon iconWater;
    public static Icon iconWaterFlow;
    public static Icon iconLava;
    public static Icon iconLavaFlow;
    public static Icon iconPortal;
    public static Icon iconFire0;
    public static Icon iconFire1;
    private static Set atlasNames;
    private static Set atlasIds;
    
    static {
        TextureUtils.atlasNames = makeAtlasNames();
        TextureUtils.atlasIds = new HashSet();
    }
    
    private static Set makeAtlasNames() {
        final HashSet var0 = new HashSet();
        var0.add("/terrain.png");
        var0.add("/gui/items.png");
        var0.add("/ctm.png");
        var0.add("/eloraam/world/world1.png");
        var0.add("/gfx/buildcraft/blocks/blocks.png");
        return var0;
    }
    
    public static void update(final RenderEngine var0) {
        final TextureMap var = var0.textureMapBlocks;
        TextureUtils.iconGrassTop = var.registerIcon("grass_top");
        TextureUtils.iconGrassSide = var.registerIcon("grass_side");
        TextureUtils.iconGrassSideOverlay = var.registerIcon("grass_side_overlay");
        TextureUtils.iconSnow = var.registerIcon("snow");
        TextureUtils.iconSnowSide = var.registerIcon("snow_side");
        TextureUtils.iconMycelSide = var.registerIcon("mycel_side");
        TextureUtils.iconMycelTop = var.registerIcon("mycel_top");
        TextureUtils.iconWater = var.registerIcon("water");
        TextureUtils.iconWaterFlow = var.registerIcon("water_flow");
        TextureUtils.iconLava = var.registerIcon("lava");
        TextureUtils.iconLavaFlow = var.registerIcon("lava_flow");
        TextureUtils.iconFire0 = var.registerIcon("fire_0");
        TextureUtils.iconFire1 = var.registerIcon("fire_1");
        TextureUtils.iconPortal = var.registerIcon("portal");
    }
    
    public static void textureCreated(final String var0, final int var1) {
        if (TextureUtils.atlasNames.contains(var0)) {
            TextureUtils.atlasIds.add(var1);
        }
    }
    
    public static void addAtlasName(final String var0) {
        if (var0 != null) {
            TextureUtils.atlasNames.add(var0);
            Config.dbg("TextureAtlas: " + var0);
        }
    }
    
    public static boolean isAtlasId(final int var0) {
        return TextureUtils.atlasIds.contains(var0);
    }
    
    public static boolean isAtlasName(final String var0) {
        return TextureUtils.atlasNames.contains(var0);
    }
    
    public static BufferedImage fixTextureDimensions(final String var0, final BufferedImage var1) {
        if (var0.startsWith("/mob/zombie") || var0.startsWith("/mob/pigzombie")) {
            final int var2 = var1.getWidth();
            final int var3 = var1.getHeight();
            if (var2 == var3 * 2) {
                final BufferedImage var4 = new BufferedImage(var2, var3 * 2, 2);
                final Graphics2D var5 = var4.createGraphics();
                var5.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                var5.drawImage(var1, 0, 0, var2, var3, null);
                return var4;
            }
        }
        return var1;
    }
    
    public static TextureStitched getTextureStitched(final Icon var0) {
        return (var0 instanceof TextureStitched) ? ((TextureStitched)var0) : null;
    }
    
    public static int ceilPowerOfTwo(final int var0) {
        int var;
        for (var = 1; var < var0; var *= 2) {}
        return var;
    }
    
    public static int getPowerOfTwo(final int var0) {
        int var;
        int var2;
        for (var = 1, var2 = 0; var < var0; var *= 2, ++var2) {}
        return var2;
    }
    
    public static int twoToPower(final int var0) {
        int var = 1;
        for (int var2 = 0; var2 < var0; ++var2) {
            var *= 2;
        }
        return var;
    }
}
