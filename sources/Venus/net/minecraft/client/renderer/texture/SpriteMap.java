/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class SpriteMap
implements AutoCloseable {
    private final Map<ResourceLocation, AtlasTexture> atlasTextures;

    public SpriteMap(Collection<AtlasTexture> collection) {
        this.atlasTextures = collection.stream().collect(Collectors.toMap(AtlasTexture::getTextureLocation, Function.identity()));
    }

    public AtlasTexture getAtlasTexture(ResourceLocation resourceLocation) {
        return this.atlasTextures.get(resourceLocation);
    }

    public TextureAtlasSprite getSprite(RenderMaterial renderMaterial) {
        return this.atlasTextures.get(renderMaterial.getAtlasLocation()).getSprite(renderMaterial.getTextureLocation());
    }

    @Override
    public void close() {
        this.atlasTextures.values().forEach(AtlasTexture::clear);
        this.atlasTextures.clear();
    }
}

