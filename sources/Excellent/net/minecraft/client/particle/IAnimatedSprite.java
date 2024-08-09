package net.minecraft.client.particle;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import java.util.Random;

public interface IAnimatedSprite
{
    TextureAtlasSprite get(int particleAge, int particleMaxAge);

    TextureAtlasSprite get(Random rand);
}
