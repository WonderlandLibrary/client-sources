/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import java.util.stream.Stream;
import net.minecraft.client.renderer.texture.SpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class PaintingSpriteUploader
extends SpriteUploader {
    private static final ResourceLocation LOCATION_BACK_SPRITE = new ResourceLocation("back");

    public PaintingSpriteUploader(TextureManager textureManager) {
        super(textureManager, new ResourceLocation("textures/atlas/paintings.png"), "painting");
    }

    @Override
    protected Stream<ResourceLocation> getResourceLocations() {
        return Stream.concat(Registry.MOTIVE.keySet().stream(), Stream.of(LOCATION_BACK_SPRITE));
    }

    public TextureAtlasSprite getSpriteForPainting(PaintingType paintingType) {
        return this.getSprite(Registry.MOTIVE.getKey(paintingType));
    }

    public TextureAtlasSprite getBackSprite() {
        return this.getSprite(LOCATION_BACK_SPRITE);
    }
}

