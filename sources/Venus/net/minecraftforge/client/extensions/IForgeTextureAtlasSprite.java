/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraftforge.client.extensions;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.function.Function;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public interface IForgeTextureAtlasSprite {
    default public boolean hasCustomLoader(IResourceManager iResourceManager, ResourceLocation resourceLocation) {
        return true;
    }

    default public boolean load(IResourceManager iResourceManager, ResourceLocation resourceLocation, Function<ResourceLocation, TextureAtlasSprite> function) {
        return false;
    }

    default public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.of();
    }
}

