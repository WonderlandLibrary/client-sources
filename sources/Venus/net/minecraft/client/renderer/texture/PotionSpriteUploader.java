/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import java.util.stream.Stream;
import net.minecraft.client.renderer.texture.SpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class PotionSpriteUploader
extends SpriteUploader {
    public PotionSpriteUploader(TextureManager textureManager) {
        super(textureManager, new ResourceLocation("textures/atlas/mob_effects.png"), "mob_effect");
    }

    @Override
    protected Stream<ResourceLocation> getResourceLocations() {
        return Registry.EFFECTS.keySet().stream();
    }

    public TextureAtlasSprite getSprite(Effect effect) {
        return this.getSprite(Registry.EFFECTS.getKey(effect));
    }
}

