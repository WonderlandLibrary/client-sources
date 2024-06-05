/*
 * Decompiled with CFR 0.152.
 */
package optfine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import optfine.Blender;
import optfine.Config;
import optfine.CustomSkyLayer;
import optfine.TextureUtils;

public class CustomSky {
    private static CustomSkyLayer[][] worldSkyLayers = null;

    public static void reset() {
        worldSkyLayers = null;
    }

    public static void update() {
        CustomSky.reset();
        if (!Config.isCustomSky()) return;
        worldSkyLayers = CustomSky.readCustomSkies();
    }

    private static CustomSkyLayer[][] readCustomSkies() {
        CustomSkyLayer[][] acustomskylayer = new CustomSkyLayer[10][0];
        String s = "mcpatcher/sky/world";
        int i = -1;
        int j = 0;
        while (true) {
            ArrayList<CustomSkyLayer> list;
            String s1;
            if (j < acustomskylayer.length) {
                s1 = s + j + "/sky";
                list = new ArrayList<CustomSkyLayer>();
            } else {
                if (i < 0) {
                    return null;
                }
                int l = i + 1;
                CustomSkyLayer[][] acustomskylayer1 = new CustomSkyLayer[l][0];
                int i1 = 0;
                while (i1 < acustomskylayer1.length) {
                    acustomskylayer1[i1] = acustomskylayer[i1];
                    ++i1;
                }
                return acustomskylayer1;
            }
            for (int k = 1; k < 1000; ++k) {
                String s2 = s1 + k + ".properties";
                try {
                    ResourceLocation resourcelocation = new ResourceLocation(s2);
                    InputStream inputstream = Config.getResourceStream(resourcelocation);
                    if (inputstream == null) break;
                    Properties properties = new Properties();
                    properties.load(inputstream);
                    Config.dbg("CustomSky properties: " + s2);
                    String s3 = s1 + k + ".png";
                    CustomSkyLayer customskylayer = new CustomSkyLayer(properties, s3);
                    if (!customskylayer.isValid(s2)) continue;
                    ResourceLocation resourcelocation1 = new ResourceLocation(customskylayer.source);
                    ITextureObject itextureobject = TextureUtils.getTexture(resourcelocation1);
                    if (itextureobject == null) {
                        Config.log("CustomSky: Texture not found: " + resourcelocation1);
                        continue;
                    }
                    customskylayer.textureId = itextureobject.getGlTextureId();
                    list.add(customskylayer);
                    inputstream.close();
                    continue;
                }
                catch (FileNotFoundException var15) {
                    break;
                }
                catch (IOException ioexception) {
                    ioexception.printStackTrace();
                }
            }
            if (list.size() > 0) {
                CustomSkyLayer[] acustomskylayer2 = list.toArray(new CustomSkyLayer[list.size()]);
                acustomskylayer[j] = acustomskylayer2;
                i = j;
            }
            ++j;
        }
    }

    public static void renderSky(World p_renderSky_0_, TextureManager p_renderSky_1_, float p_renderSky_2_, float p_renderSky_3_) {
        if (worldSkyLayers == null) return;
        if (Config.getGameSettings().renderDistanceChunks < 8) return;
        int i = p_renderSky_0_.provider.getDimensionId();
        if (i < 0) return;
        if (i >= worldSkyLayers.length) return;
        CustomSkyLayer[] acustomskylayer = worldSkyLayers[i];
        if (acustomskylayer == null) return;
        long j = p_renderSky_0_.getWorldTime();
        int k = (int)(j % 24000L);
        int l = 0;
        while (true) {
            if (l >= acustomskylayer.length) {
                Blender.clearBlend(p_renderSky_3_);
                return;
            }
            CustomSkyLayer customskylayer = acustomskylayer[l];
            if (customskylayer.isActive(k)) {
                customskylayer.render(k, p_renderSky_2_, p_renderSky_3_);
            }
            ++l;
        }
    }

    public static boolean hasSkyLayers(World p_hasSkyLayers_0_) {
        if (worldSkyLayers == null) {
            return false;
        }
        if (Config.getGameSettings().renderDistanceChunks < 8) {
            return false;
        }
        int i = p_hasSkyLayers_0_.provider.getDimensionId();
        if (i < 0) return false;
        if (i >= worldSkyLayers.length) return false;
        CustomSkyLayer[] acustomskylayer = worldSkyLayers[i];
        if (acustomskylayer == null) {
            return false;
        }
        if (acustomskylayer.length <= 0) return false;
        return true;
    }
}

