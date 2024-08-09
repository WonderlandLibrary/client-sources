/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;

public class BreakingParticle
extends SpriteTexturedParticle {
    private final float u;
    private final float v;

    private BreakingParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, ItemStack itemStack) {
        this(clientWorld, d, d2, d3, itemStack);
        this.motionX *= (double)0.1f;
        this.motionY *= (double)0.1f;
        this.motionZ *= (double)0.1f;
        this.motionX += d4;
        this.motionY += d5;
        this.motionZ += d6;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.TERRAIN_SHEET;
    }

    protected BreakingParticle(ClientWorld clientWorld, double d, double d2, double d3, ItemStack itemStack) {
        super(clientWorld, d, d2, d3, 0.0, 0.0, 0.0);
        this.setSprite(Minecraft.getInstance().getItemRenderer().getItemModelWithOverrides(itemStack, clientWorld, null).getParticleTexture());
        this.particleGravity = 1.0f;
        this.particleScale /= 2.0f;
        this.u = this.rand.nextFloat() * 3.0f;
        this.v = this.rand.nextFloat() * 3.0f;
    }

    @Override
    protected float getMinU() {
        return this.sprite.getInterpolatedU((this.u + 1.0f) / 4.0f * 16.0f);
    }

    @Override
    protected float getMaxU() {
        return this.sprite.getInterpolatedU(this.u / 4.0f * 16.0f);
    }

    @Override
    protected float getMinV() {
        return this.sprite.getInterpolatedV(this.v / 4.0f * 16.0f);
    }

    @Override
    protected float getMaxV() {
        return this.sprite.getInterpolatedV((this.v + 1.0f) / 4.0f * 16.0f);
    }

    public static class SnowballFactory
    implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new BreakingParticle(clientWorld, d, d2, d3, new ItemStack(Items.SNOWBALL));
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class SlimeFactory
    implements IParticleFactory<BasicParticleType> {
        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new BreakingParticle(clientWorld, d, d2, d3, new ItemStack(Items.SLIME_BALL));
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class Factory
    implements IParticleFactory<ItemParticleData> {
        @Override
        public Particle makeParticle(ItemParticleData itemParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new BreakingParticle(clientWorld, d, d2, d3, d4, d5, d6, itemParticleData.getItemStack());
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((ItemParticleData)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}

