package net.silentclient.client.utils;

import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class CustomSkin {
    public static Map<String, CustomSkin> skins = new HashMap();

    public static boolean loading = false;
    private boolean loaded = false;
    private BufferedImage image;
    private ResourceLocation location;
    private boolean initialized = false;

    public BufferedImage getImage() {
        return image;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public void setLocation(ResourceLocation location) {
        this.location = location;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
