package net.minecraft.src;

import java.util.*;
import java.io.*;
import org.lwjgl.opengl.*;

public class CustomSky
{
    private static CustomSkyLayer[][] worldSkyLayers;
    
    static {
        CustomSky.worldSkyLayers = null;
    }
    
    public static void reset() {
        CustomSky.worldSkyLayers = null;
    }
    
    public static void update(final RenderEngine var0) {
        reset();
        if (Config.isCustomSky() && var0 != null) {
            CustomSky.worldSkyLayers = readCustomSkies(var0);
        }
    }
    
    private static CustomSkyLayer[][] readCustomSkies(final RenderEngine var0) {
        final CustomSkyLayer[][] var = new CustomSkyLayer[10][0];
        final String var2 = "/environment/sky";
        int var3 = -1;
        for (int var4 = 0; var4 < var.length; ++var4) {
            final String var5 = String.valueOf(var2) + var4 + "/sky";
            final ArrayList var6 = new ArrayList();
            for (int var7 = 1; var7 < 1000; ++var7) {
                final String var8 = String.valueOf(var5) + var7 + ".properties";
                try {
                    final InputStream var9 = var0.texturePack.getSelectedTexturePack().getResourceAsStream(var8);
                    if (var9 == null) {
                        break;
                    }
                    final Properties var10 = new Properties();
                    var10.load(var9);
                    Config.dbg("CustomSky properties: " + var8);
                    final String var11 = String.valueOf(var5) + var7 + ".png";
                    final CustomSkyLayer var12 = new CustomSkyLayer(var10, var11);
                    if (var12.isValid(var8)) {
                        var12.textureId = var0.getTexture(var12.source);
                        var6.add(var12);
                        var9.close();
                    }
                }
                catch (FileNotFoundException var17) {
                    break;
                }
                catch (IOException var13) {
                    var13.printStackTrace();
                }
            }
            if (var6.size() > 0) {
                final CustomSkyLayer[] var14 = var6.toArray(new CustomSkyLayer[var6.size()]);
                var[var4] = var14;
                var3 = var4;
            }
        }
        if (var3 < 0) {
            return null;
        }
        int var4 = var3 + 1;
        final CustomSkyLayer[][] var15 = new CustomSkyLayer[var4][0];
        for (int var16 = 0; var16 < var15.length; ++var16) {
            var15[var16] = var[var16];
        }
        return var15;
    }
    
    public static void renderSky(final World var0, final RenderEngine var1, final float var2, final float var3) {
        if (CustomSky.worldSkyLayers != null && Config.getGameSettings().ofRenderDistanceFine >= 128) {
            final int var4 = var0.provider.dimensionId;
            if (var4 >= 0 && var4 < CustomSky.worldSkyLayers.length) {
                final CustomSkyLayer[] var5 = CustomSky.worldSkyLayers[var4];
                if (var5 != null) {
                    final long var6 = var0.getWorldTime();
                    final int var7 = (int)(var6 % 24000L);
                    for (int var8 = 0; var8 < var5.length; ++var8) {
                        final CustomSkyLayer var9 = var5[var8];
                        if (var9.isActive(var7)) {
                            var9.render(var7, var1, var2, var3);
                        }
                    }
                    clearBlend(var3);
                }
            }
        }
    }
    
    public static boolean hasSkyLayers(final World var0) {
        if (CustomSky.worldSkyLayers == null) {
            return false;
        }
        if (Config.getGameSettings().ofRenderDistanceFine < 128) {
            return false;
        }
        final int var = var0.provider.dimensionId;
        if (var >= 0 && var < CustomSky.worldSkyLayers.length) {
            final CustomSkyLayer[] var2 = CustomSky.worldSkyLayers[var];
            return var2 != null && var2.length > 0;
        }
        return false;
    }
    
    private static void clearBlend(final float var0) {
        GL11.glDisable(3008);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, var0);
    }
}
