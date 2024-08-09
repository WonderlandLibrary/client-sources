/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.TexturedParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;

public abstract class SpriteTexturedParticle
extends TexturedParticle {
    protected TextureAtlasSprite sprite;

    protected SpriteTexturedParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3);
    }

    protected SpriteTexturedParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
    }

    protected void setSprite(TextureAtlasSprite textureAtlasSprite) {
        this.sprite = textureAtlasSprite;
    }

    @Override
    protected float getMinU() {
        return this.sprite.getMinU();
    }

    @Override
    protected float getMaxU() {
        return this.sprite.getMaxU();
    }

    @Override
    protected float getMinV() {
        return this.sprite.getMinV();
    }

    @Override
    protected float getMaxV() {
        return this.sprite.getMaxV();
    }

    public void selectSpriteRandomly(IAnimatedSprite iAnimatedSprite) {
        this.setSprite(iAnimatedSprite.get(this.rand));
    }

    public void selectSpriteWithAge(IAnimatedSprite iAnimatedSprite) {
        this.setSprite(iAnimatedSprite.get(this.age, this.maxAge));
    }
}

