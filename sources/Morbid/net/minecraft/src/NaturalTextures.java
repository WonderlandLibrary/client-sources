package net.minecraft.src;

import java.io.*;
import java.util.*;

public class NaturalTextures
{
    private static RenderEngine renderEngine;
    private static NaturalProperties[] propertiesByIndex;
    
    static {
        NaturalTextures.renderEngine = null;
        NaturalTextures.propertiesByIndex = new NaturalProperties[0];
    }
    
    public static void update(final RenderEngine var0) {
        NaturalTextures.propertiesByIndex = new NaturalProperties[0];
        NaturalTextures.renderEngine = var0;
        if (Config.isNaturalTextures()) {
            final String var = "/natural.properties";
            try {
                final InputStream var2 = var0.texturePack.getSelectedTexturePack().getResourceAsStream(var);
                if (var2 == null) {
                    Config.dbg("NaturalTextures: configuration \"" + var + "\" not found");
                    NaturalTextures.propertiesByIndex = makeDefaultProperties();
                    return;
                }
                final ArrayList var3 = new ArrayList(256);
                final String var4 = Config.readInputStream(var2);
                var2.close();
                final String[] var5 = Config.tokenize(var4, "\n\r");
                Config.dbg("Natural Textures: Parsing configuration \"" + var + "\"");
                for (int var6 = 0; var6 < var5.length; ++var6) {
                    final String var7 = var5[var6].trim();
                    if (!var7.startsWith("#")) {
                        final String[] var8 = Config.tokenize(var7, "=");
                        if (var8.length != 2) {
                            Config.dbg("Natural Textures: Invalid \"" + var + "\" line: " + var7);
                        }
                        else {
                            final String var9 = var8[0].trim();
                            final String var10 = var8[1].trim();
                            final TextureStitched var11 = var0.textureMapBlocks.getIconSafe(var9);
                            if (var11 == null) {
                                Config.dbg("Natural Textures: Texture not found: \"" + var + "\" line: " + var7);
                            }
                            else {
                                final int var12 = var11.getIndexInMap();
                                if (var12 < 0) {
                                    Config.dbg("Natural Textures: Invalid \"" + var + "\" line: " + var7);
                                }
                                else {
                                    final NaturalProperties var13 = new NaturalProperties(var10);
                                    if (var13.isValid()) {
                                        while (var3.size() <= var12) {
                                            var3.add(null);
                                        }
                                        var3.set(var12, var13);
                                    }
                                }
                            }
                        }
                    }
                }
                NaturalTextures.propertiesByIndex = var3.toArray(new NaturalProperties[var3.size()]);
            }
            catch (FileNotFoundException var15) {
                Config.dbg("NaturalTextures: configuration \"" + var + "\" not found");
                NaturalTextures.propertiesByIndex = makeDefaultProperties();
            }
            catch (Exception var14) {
                var14.printStackTrace();
            }
        }
    }
    
    public static NaturalProperties getNaturalProperties(final Icon var0) {
        if (!(var0 instanceof TextureStitched)) {
            return null;
        }
        final TextureStitched var = (TextureStitched)var0;
        final int var2 = var.getIndexInMap();
        if (var2 >= 0 && var2 < NaturalTextures.propertiesByIndex.length) {
            final NaturalProperties var3 = NaturalTextures.propertiesByIndex[var2];
            return var3;
        }
        return null;
    }
    
    private static NaturalProperties[] makeDefaultProperties() {
        if (!(NaturalTextures.renderEngine.texturePack.getSelectedTexturePack() instanceof TexturePackDefault)) {
            Config.dbg("NaturalTextures: Texture pack is not default, ignoring default configuration.");
            return new NaturalProperties[0];
        }
        Config.dbg("Natural Textures: Using default configuration.");
        final ArrayList var0 = new ArrayList();
        setIconProperties(var0, "grass_top", "4F");
        setIconProperties(var0, "stone", "2F");
        setIconProperties(var0, "dirt", "4F");
        setIconProperties(var0, "grass_side", "F");
        setIconProperties(var0, "grass_side_overlay", "F");
        setIconProperties(var0, "stoneslab_top", "F");
        setIconProperties(var0, "bedrock", "2F");
        setIconProperties(var0, "sand", "4F");
        setIconProperties(var0, "gravel", "2");
        setIconProperties(var0, "tree_side", "2F");
        setIconProperties(var0, "tree_top", "4F");
        setIconProperties(var0, "oreGold", "2F");
        setIconProperties(var0, "oreIron", "2F");
        setIconProperties(var0, "oreCoal", "2F");
        setIconProperties(var0, "oreDiamond", "2F");
        setIconProperties(var0, "oreRedstone", "2F");
        setIconProperties(var0, "oreLapis", "2F");
        setIconProperties(var0, "obsidian", "4F");
        setIconProperties(var0, "leaves", "2F");
        setIconProperties(var0, "leaves_opaque", "2F");
        setIconProperties(var0, "leaves_jungle", "2");
        setIconProperties(var0, "leaves_jungle_opaque", "2");
        setIconProperties(var0, "snow", "4F");
        setIconProperties(var0, "snow_side", "F");
        setIconProperties(var0, "cactus_side", "2F");
        setIconProperties(var0, "clay", "4F");
        setIconProperties(var0, "mycel_side", "F");
        setIconProperties(var0, "mycel_top", "4F");
        setIconProperties(var0, "farmland_wet", "2F");
        setIconProperties(var0, "farmland_dry", "2F");
        setIconProperties(var0, "hellrock", "4F");
        setIconProperties(var0, "hellsand", "4F");
        setIconProperties(var0, "lightgem", "4");
        setIconProperties(var0, "tree_spruce", "2F");
        setIconProperties(var0, "tree_birch", "F");
        setIconProperties(var0, "leaves_spruce", "2F");
        setIconProperties(var0, "leaves_spruce_opaque", "2F");
        setIconProperties(var0, "tree_jungle", "2F");
        setIconProperties(var0, "whiteStone", "4");
        setIconProperties(var0, "sandstone_top", "4");
        setIconProperties(var0, "sandstone_bottom", "4F");
        setIconProperties(var0, "redstoneLight_lit", "4F");
        final NaturalProperties[] var2 = var0.toArray(new NaturalProperties[var0.size()]);
        return var2;
    }
    
    private static void setIconProperties(final List var0, final String var1, final String var2) {
        final TextureMap var3 = NaturalTextures.renderEngine.textureMapBlocks;
        final Icon var4 = var3.registerIcon(var1);
        if (var4 == null) {
            Config.dbg("*** NaturalProperties: Icon not found: " + var1 + " ***");
        }
        else if (!(var4 instanceof TextureStitched)) {
            Config.dbg("*** NaturalProperties: Icon is not IconStitched: " + var1 + ": " + var4.getClass().getName() + " ***");
        }
        else {
            final TextureStitched var5 = (TextureStitched)var4;
            final int var6 = var5.getIndexInMap();
            if (var6 < 0) {
                Config.dbg("*** NaturalProperties: Invalid index for icon: " + var1 + ": " + var6 + " ***");
            }
            else {
                while (var6 >= var0.size()) {
                    var0.add(null);
                }
                final NaturalProperties var7 = new NaturalProperties(var2);
                var0.set(var6, var7);
            }
        }
    }
    
    public static void updateIcons(final TextureMap var0) {
    }
}
