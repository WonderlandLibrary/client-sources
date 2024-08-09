package net.minecraft.client.resources;

import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class ColorMapLoader {

    public static int[] loadColors(IResourceManager manager, ResourceLocation location) throws IOException {
        int[] aint;

        try (
                IResource iresource = manager.getResource(location);
                NativeImage nativeimage = NativeImage.read(iresource.getInputStream())
        ) {
            aint = nativeimage.makePixelArray();
        }

        return aint;
    }
}
