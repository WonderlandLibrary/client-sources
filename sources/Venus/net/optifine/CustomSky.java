/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.optifine.Config;
import net.optifine.CustomSkyLayer;
import net.optifine.render.Blender;
import net.optifine.shaders.RenderStage;
import net.optifine.shaders.Shaders;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import net.optifine.util.WorldUtils;

public class CustomSky {
    private static CustomSkyLayer[][] worldSkyLayers = null;

    public static void reset() {
        worldSkyLayers = null;
    }

    public static void update() {
        CustomSky.reset();
        if (Config.isCustomSky()) {
            worldSkyLayers = CustomSky.readCustomSkies();
        }
    }

    private static CustomSkyLayer[][] readCustomSkies() {
        String string;
        int n;
        CustomSkyLayer[][] customSkyLayerArray = new CustomSkyLayer[10][0];
        String string2 = "optifine/sky/world";
        int n2 = -1;
        for (n = 0; n < customSkyLayerArray.length; ++n) {
            string = string2 + n;
            ArrayList<CustomSkyLayer> arrayList = new ArrayList<CustomSkyLayer>();
            for (int i = 0; i < 1000; ++i) {
                String string3 = string + "/sky" + i + ".properties";
                int n3 = 0;
                try {
                    ResourceLocation resourceLocation = new ResourceLocation(string3);
                    InputStream inputStream = Config.getResourceStream(resourceLocation);
                    if (inputStream == null && ++n3 > 10) break;
                    PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                    propertiesOrdered.load(inputStream);
                    inputStream.close();
                    Config.dbg("CustomSky properties: " + string3);
                    String string4 = i + ".png";
                    CustomSkyLayer customSkyLayer = new CustomSkyLayer(propertiesOrdered, string4);
                    if (!customSkyLayer.isValid(string3)) continue;
                    String string5 = StrUtils.addSuffixCheck(customSkyLayer.source, ".png");
                    ResourceLocation resourceLocation2 = new ResourceLocation(string5);
                    Texture texture = TextureUtils.getTexture(resourceLocation2);
                    if (texture == null) {
                        Config.log("CustomSky: Texture not found: " + resourceLocation2);
                        continue;
                    }
                    customSkyLayer.textureId = texture.getGlTextureId();
                    arrayList.add(customSkyLayer);
                    inputStream.close();
                    continue;
                } catch (FileNotFoundException fileNotFoundException) {
                    if (++n3 <= 10) continue;
                    break;
                } catch (IOException iOException) {
                    iOException.printStackTrace();
                }
            }
            if (arrayList.size() <= 0) continue;
            CustomSkyLayer[] customSkyLayerArray2 = arrayList.toArray(new CustomSkyLayer[arrayList.size()]);
            customSkyLayerArray[n] = customSkyLayerArray2;
            n2 = n;
        }
        if (n2 < 0) {
            return null;
        }
        n = n2 + 1;
        string = new CustomSkyLayer[n][0];
        for (int i = 0; i < ((CustomSkyLayer[][])string).length; ++i) {
            string[i] = customSkyLayerArray[i];
        }
        return string;
    }

    public static void renderSky(World world, TextureManager textureManager, MatrixStack matrixStack, float f) {
        if (worldSkyLayers != null) {
            CustomSkyLayer[] customSkyLayerArray;
            int n;
            if (Config.isShaders()) {
                Shaders.setRenderStage(RenderStage.CUSTOM_SKY);
            }
            if ((n = WorldUtils.getDimensionId(world)) >= 0 && n < worldSkyLayers.length && (customSkyLayerArray = worldSkyLayers[n]) != null) {
                long l = world.getDayTime();
                int n2 = (int)(l % 24000L);
                float f2 = world.func_242415_f(f);
                float f3 = world.getRainStrength(f);
                float f4 = world.getThunderStrength(f);
                if (f3 > 0.0f) {
                    f4 /= f3;
                }
                for (int i = 0; i < customSkyLayerArray.length; ++i) {
                    CustomSkyLayer customSkyLayer = customSkyLayerArray[i];
                    if (!customSkyLayer.isActive(world, n2)) continue;
                    customSkyLayer.render(world, matrixStack, n2, f2, f3, f4);
                }
                float f5 = 1.0f - f3;
                Blender.clearBlend(f5);
            }
        }
    }

    public static boolean hasSkyLayers(World world) {
        if (worldSkyLayers == null) {
            return true;
        }
        int n = WorldUtils.getDimensionId(world);
        if (n >= 0 && n < worldSkyLayers.length) {
            CustomSkyLayer[] customSkyLayerArray = worldSkyLayers[n];
            if (customSkyLayerArray == null) {
                return true;
            }
            return customSkyLayerArray.length > 0;
        }
        return true;
    }
}

