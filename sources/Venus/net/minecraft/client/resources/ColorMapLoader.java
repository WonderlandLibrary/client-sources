/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class ColorMapLoader {
    @Deprecated
    public static int[] loadColors(IResourceManager iResourceManager, ResourceLocation resourceLocation) throws IOException {
        int[] nArray;
        try (IResource iResource = iResourceManager.getResource(resourceLocation);
             NativeImage nativeImage = NativeImage.read(iResource.getInputStream());){
            nArray = nativeImage.makePixelArray();
        }
        return nArray;
    }
}

