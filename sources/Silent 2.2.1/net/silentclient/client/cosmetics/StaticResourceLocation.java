package net.silentclient.client.cosmetics;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class StaticResourceLocation {
    private ResourceLocation location;

    public StaticResourceLocation(ResourceLocation location) {
        this.location = location;
    }

    public StaticResourceLocation(String path) {
        this.location = new ResourceLocation(path);
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public void bindTexture() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(location);
    }
}
