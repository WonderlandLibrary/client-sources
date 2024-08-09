/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render.font;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public final class IOUtils {
    private static final IResourceManager RES_MANAGER = Minecraft.getInstance().getResourceManager();
    private static final TextureManager TEX_MANAGER = Minecraft.getInstance().getTextureManager();
    private static final Gson GSON = new Gson();

    private IOUtils() {
    }

    public static String toString(ResourceLocation resourceLocation) {
        return IOUtils.toString(resourceLocation, "\n");
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public static String toString(ResourceLocation resourceLocation, String string) {
        try (IResource iResource = RES_MANAGER.getResource(resourceLocation);){
            String string2;
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iResource.getInputStream()));){
                string2 = bufferedReader.lines().collect(Collectors.joining(string));
            }
            return string2;
        } catch (IOException iOException) {
            iOException.printStackTrace();
            return "";
        }
    }

    public static <T> T fromJsonToInstance(ResourceLocation resourceLocation, Class<T> clazz) {
        try {
            return GSON.fromJson(IOUtils.toString(resourceLocation), clazz);
        } catch (JsonSyntaxException jsonSyntaxException) {
            jsonSyntaxException.printStackTrace();
            return null;
        }
    }

    public static Texture toTexture(ResourceLocation resourceLocation) {
        Texture texture = TEX_MANAGER.getTexture(resourceLocation);
        if (texture == null) {
            texture = new SimpleTexture(resourceLocation);
            TEX_MANAGER.loadTexture(resourceLocation, texture);
        }
        return texture;
    }
}

