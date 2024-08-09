package im.expensive.utils.render.font;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public final class IOUtils {

    private static final IResourceManager RES_MANAGER = Minecraft.getInstance().getResourceManager(); // Minecraft.getResourceManager();
    private static final TextureManager TEX_MANAGER = Minecraft.getInstance().getTextureManager();  // Minecraft.getTextureManager();
    private static final Gson GSON = new Gson();

    private IOUtils() {}

    public static String toString(ResourceLocation location) {
        return toString(location, "\n");
    }

    public static String toString(ResourceLocation location, String delimiter) {
        try (IResource resource = RES_MANAGER.getResource(location);
             BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines().collect(Collectors.joining(delimiter));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static <T> T fromJsonToInstance(ResourceLocation location, Class<T> clazz) {
        try {
            return GSON.fromJson(toString(location), clazz);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Texture toTexture(ResourceLocation location) {
        Texture texture = TEX_MANAGER.getTexture(location);
        if (texture == null) {
            texture = new SimpleTexture(location);
            TEX_MANAGER.loadTexture(location, texture);
        }

        return texture;
    }

}