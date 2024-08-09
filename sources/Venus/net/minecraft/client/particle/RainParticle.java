/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class RainParticle
extends SpriteTexturedParticle {
    protected RainParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3, 0.0, 0.0, 0.0);
        this.motionX *= (double)0.3f;
        this.motionY = Math.random() * (double)0.2f + (double)0.1f;
        this.motionZ *= (double)0.3f;
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.maxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.maxAge-- <= 0) {
            this.setExpired();
        } else {
            BlockPos blockPos;
            double d;
            this.motionY -= (double)this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)0.98f;
            this.motionY *= (double)0.98f;
            this.motionZ *= (double)0.98f;
            if (this.onGround) {
                if (Math.random() < 0.5) {
                    this.setExpired();
                }
                this.motionX *= (double)0.7f;
                this.motionZ *= (double)0.7f;
            }
            if ((d = Math.max(this.world.getBlockState(blockPos = new BlockPos(this.posX, this.posY, this.posZ)).getCollisionShape(this.world, blockPos).max(Direction.Axis.Y, this.posX - (double)blockPos.getX(), this.posZ - (double)blockPos.getZ()), (double)this.world.getFluidState(blockPos).getActualHeight(this.world, blockPos))) > 0.0 && this.posY < (double)blockPos.getY() + d) {
                this.setExpired();
            }
        }
    }

    public static class Factory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            RainParticle rainParticle = new RainParticle(clientWorld, d, d2, d3);
            rainParticle.selectSpriteRandomly(this.spriteSet);
            return rainParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}

