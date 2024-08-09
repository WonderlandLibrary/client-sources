/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.render.RenderUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.TextureUtils;

public class EmissiveTextures {
    private static String suffixEmissive = null;
    private static String suffixEmissivePng = null;
    private static boolean active = false;
    private static boolean render = false;
    private static boolean hasEmissive = false;
    private static boolean renderEmissive = false;
    private static final String SUFFIX_PNG = ".png";
    private static final ResourceLocation LOCATION_TEXTURE_EMPTY = TextureUtils.LOCATION_TEXTURE_EMPTY;
    private static final ResourceLocation LOCATION_SPRITE_EMPTY = TextureUtils.LOCATION_SPRITE_EMPTY;
    private static TextureManager textureManager;
    private static int countRecursive;

    public static boolean isActive() {
        return active;
    }

    public static String getSuffixEmissive() {
        return suffixEmissive;
    }

    public static void beginRender() {
        if (render) {
            ++countRecursive;
        } else {
            render = true;
            hasEmissive = false;
        }
    }

    public static ResourceLocation getEmissiveTexture(ResourceLocation resourceLocation) {
        if (!render) {
            return resourceLocation;
        }
        Texture texture = textureManager.getTexture(resourceLocation);
        if (texture instanceof AtlasTexture) {
            return resourceLocation;
        }
        ResourceLocation resourceLocation2 = null;
        if (texture instanceof SimpleTexture) {
            resourceLocation2 = ((SimpleTexture)texture).locationEmissive;
        }
        if (!renderEmissive) {
            if (resourceLocation2 != null) {
                hasEmissive = true;
            }
            return resourceLocation;
        }
        if (resourceLocation2 == null) {
            resourceLocation2 = LOCATION_TEXTURE_EMPTY;
        }
        return resourceLocation2;
    }

    public static TextureAtlasSprite getEmissiveSprite(TextureAtlasSprite textureAtlasSprite) {
        if (!render) {
            return textureAtlasSprite;
        }
        TextureAtlasSprite textureAtlasSprite2 = textureAtlasSprite.spriteEmissive;
        if (!renderEmissive) {
            if (textureAtlasSprite2 != null) {
                hasEmissive = true;
            }
            return textureAtlasSprite;
        }
        if (textureAtlasSprite2 == null) {
            textureAtlasSprite2 = textureAtlasSprite.getAtlasTexture().getSprite(LOCATION_SPRITE_EMPTY);
        }
        return textureAtlasSprite2;
    }

    public static BakedQuad getEmissiveQuad(BakedQuad bakedQuad) {
        if (!render) {
            return bakedQuad;
        }
        BakedQuad bakedQuad2 = bakedQuad.getQuadEmissive();
        if (!renderEmissive) {
            if (bakedQuad2 != null) {
                hasEmissive = true;
            }
            return bakedQuad;
        }
        return bakedQuad2;
    }

    public static boolean hasEmissive() {
        return countRecursive > 0 ? false : hasEmissive;
    }

    public static void beginRenderEmissive() {
        renderEmissive = true;
    }

    public static boolean isRenderEmissive() {
        return renderEmissive;
    }

    public static void endRenderEmissive() {
        RenderUtils.flushRenderBuffers();
        renderEmissive = false;
    }

    public static void endRender() {
        if (countRecursive > 0) {
            --countRecursive;
        } else {
            render = false;
            hasEmissive = false;
        }
    }

    public static void update() {
        textureManager = Minecraft.getInstance().getTextureManager();
        active = false;
        suffixEmissive = null;
        suffixEmissivePng = null;
        if (Config.isEmissiveTextures()) {
            try {
                String string = "optifine/emissive.properties";
                ResourceLocation resourceLocation = new ResourceLocation(string);
                InputStream inputStream = Config.getResourceStream(resourceLocation);
                if (inputStream == null) {
                    return;
                }
                EmissiveTextures.dbg("Loading " + string);
                PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
                propertiesOrdered.load(inputStream);
                inputStream.close();
                suffixEmissive = propertiesOrdered.getProperty("suffix.emissive");
                if (suffixEmissive != null) {
                    suffixEmissivePng = suffixEmissive + SUFFIX_PNG;
                }
                active = suffixEmissive != null;
            } catch (FileNotFoundException fileNotFoundException) {
                return;
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }

    public static void updateIcons(AtlasTexture atlasTexture, Set<ResourceLocation> set) {
        if (active) {
            for (ResourceLocation resourceLocation : set) {
                EmissiveTextures.checkEmissive(atlasTexture, resourceLocation);
            }
        }
    }

    private static void checkEmissive(AtlasTexture atlasTexture, ResourceLocation resourceLocation) {
        ResourceLocation resourceLocation2;
        ResourceLocation resourceLocation3;
        String string = EmissiveTextures.getSuffixEmissive();
        if (string != null && !resourceLocation.getPath().endsWith(string) && Config.hasResource(resourceLocation3 = atlasTexture.getSpritePath(resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath() + string)))) {
            TextureAtlasSprite textureAtlasSprite = atlasTexture.registerSprite(resourceLocation);
            TextureAtlasSprite textureAtlasSprite2 = atlasTexture.registerSprite(resourceLocation2);
            textureAtlasSprite2.isSpriteEmissive = true;
            textureAtlasSprite.spriteEmissive = textureAtlasSprite2;
            atlasTexture.registerSprite(LOCATION_SPRITE_EMPTY);
        }
    }

    public static void refreshIcons(AtlasTexture atlasTexture) {
        for (TextureAtlasSprite textureAtlasSprite : atlasTexture.getRegisteredSprites()) {
            EmissiveTextures.refreshIcon(textureAtlasSprite, atlasTexture);
        }
    }

    private static void refreshIcon(TextureAtlasSprite textureAtlasSprite, AtlasTexture atlasTexture) {
        TextureAtlasSprite textureAtlasSprite2;
        TextureAtlasSprite textureAtlasSprite3;
        if (textureAtlasSprite.spriteEmissive != null && (textureAtlasSprite3 = atlasTexture.getUploadedSprite(textureAtlasSprite.getName())) != null && (textureAtlasSprite2 = atlasTexture.getUploadedSprite(textureAtlasSprite.spriteEmissive.getName())) != null) {
            textureAtlasSprite2.isSpriteEmissive = true;
            textureAtlasSprite3.spriteEmissive = textureAtlasSprite2;
        }
    }

    private static void dbg(String string) {
        Config.dbg("EmissiveTextures: " + string);
    }

    private static void warn(String string) {
        Config.warn("EmissiveTextures: " + string);
    }

    public static boolean isEmissive(ResourceLocation resourceLocation) {
        return suffixEmissivePng == null ? false : resourceLocation.getPath().endsWith(suffixEmissivePng);
    }

    public static void loadTexture(ResourceLocation resourceLocation, SimpleTexture simpleTexture) {
        if (resourceLocation != null && simpleTexture != null) {
            String string;
            simpleTexture.isEmissive = false;
            simpleTexture.locationEmissive = null;
            if (suffixEmissivePng != null && (string = resourceLocation.getPath()).endsWith(SUFFIX_PNG)) {
                if (string.endsWith(suffixEmissivePng)) {
                    simpleTexture.isEmissive = true;
                } else {
                    String string2 = string.substring(0, string.length() - 4) + suffixEmissivePng;
                    ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), string2);
                    if (Config.hasResource(resourceLocation2)) {
                        simpleTexture.locationEmissive = resourceLocation2;
                    }
                }
            }
        }
    }

    static {
        countRecursive = 0;
    }
}

