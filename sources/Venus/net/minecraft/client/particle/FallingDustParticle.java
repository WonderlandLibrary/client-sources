/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class FallingDustParticle
extends SpriteTexturedParticle {
    private final float rotSpeed;
    private final IAnimatedSprite spriteWithAge;

    private FallingDustParticle(ClientWorld clientWorld, double d, double d2, double d3, float f, float f2, float f3, IAnimatedSprite iAnimatedSprite) {
        super(clientWorld, d, d2, d3);
        this.spriteWithAge = iAnimatedSprite;
        this.particleRed = f;
        this.particleGreen = f2;
        this.particleBlue = f3;
        float f4 = 0.9f;
        this.particleScale *= 0.67499995f;
        int n = (int)(32.0 / (Math.random() * 0.8 + 0.2));
        this.maxAge = (int)Math.max((float)n * 0.9f, 1.0f);
        this.selectSpriteWithAge(iAnimatedSprite);
        this.rotSpeed = ((float)Math.random() - 0.5f) * 0.1f;
        this.particleAngle = (float)Math.random() * ((float)Math.PI * 2);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getScale(float f) {
        return this.particleScale * MathHelper.clamp(((float)this.age + f) / (float)this.maxAge * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.selectSpriteWithAge(this.spriteWithAge);
            this.prevParticleAngle = this.particleAngle;
            this.particleAngle += (float)Math.PI * this.rotSpeed * 2.0f;
            if (this.onGround) {
                this.particleAngle = 0.0f;
                this.prevParticleAngle = 0.0f;
            }
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionY -= (double)0.003f;
            this.motionY = Math.max(this.motionY, (double)-0.14f);
        }
    }

    public static class Factory
    implements IParticleFactory<BlockParticleData> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        @Nullable
        public Particle makeParticle(BlockParticleData blockParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            BlockState blockState = blockParticleData.getBlockState();
            if (!blockState.isAir() && blockState.getRenderType() == BlockRenderType.INVISIBLE) {
                return null;
            }
            BlockPos blockPos = new BlockPos(d, d2, d3);
            int n = Minecraft.getInstance().getBlockColors().getColorOrMaterialColor(blockState, clientWorld, blockPos);
            if (blockState.getBlock() instanceof FallingBlock) {
                n = ((FallingBlock)blockState.getBlock()).getDustColor(blockState, clientWorld, blockPos);
            }
            float f = (float)(n >> 16 & 0xFF) / 255.0f;
            float f2 = (float)(n >> 8 & 0xFF) / 255.0f;
            float f3 = (float)(n & 0xFF) / 255.0f;
            return new FallingDustParticle(clientWorld, d, d2, d3, f, f2, f3, this.spriteSet);
        }

        @Override
        @Nullable
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BlockParticleData)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}

