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
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class DripParticle
extends SpriteTexturedParticle {
    private final Fluid fluid;
    protected boolean fullbright;

    private DripParticle(ClientWorld clientWorld, double d, double d2, double d3, Fluid fluid) {
        super(clientWorld, d, d2, d3);
        this.setSize(0.01f, 0.01f);
        this.particleGravity = 0.06f;
        this.fluid = fluid;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightnessForRender(float f) {
        return this.fullbright ? 240 : super.getBrightnessForRender(f);
    }

    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.ageParticle();
        if (!this.isExpired) {
            this.motionY -= (double)this.particleGravity;
            this.move(this.motionX, this.motionY, this.motionZ);
            this.updateMotion();
            if (!this.isExpired) {
                this.motionX *= (double)0.98f;
                this.motionY *= (double)0.98f;
                this.motionZ *= (double)0.98f;
                BlockPos blockPos = new BlockPos(this.posX, this.posY, this.posZ);
                FluidState fluidState = this.world.getFluidState(blockPos);
                if (fluidState.getFluid() == this.fluid && this.posY < (double)((float)blockPos.getY() + fluidState.getActualHeight(this.world, blockPos))) {
                    this.setExpired();
                }
            }
        }
    }

    protected void ageParticle() {
        if (this.maxAge-- <= 0) {
            this.setExpired();
        }
    }

    protected void updateMotion() {
    }

    public static class LandingObsidianTearFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public LandingObsidianTearFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Landing landing = new Landing(clientWorld, d, d2, d3, Fluids.EMPTY);
            landing.fullbright = true;
            landing.maxAge = (int)(28.0 / (Math.random() * 0.8 + 0.2));
            landing.setColor(0.51171875f, 0.03125f, 0.890625f);
            landing.selectSpriteRandomly(this.spriteSet);
            return landing;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class LandingLavaFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public LandingLavaFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Landing landing = new Landing(clientWorld, d, d2, d3, Fluids.LAVA);
            landing.setColor(1.0f, 0.2857143f, 0.083333336f);
            landing.selectSpriteRandomly(this.spriteSet);
            return landing;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class LandingHoneyFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public LandingHoneyFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Landing landing = new Landing(clientWorld, d, d2, d3, Fluids.EMPTY);
            landing.maxAge = (int)(128.0 / (Math.random() * 0.8 + 0.2));
            landing.setColor(0.522f, 0.408f, 0.082f);
            landing.selectSpriteRandomly(this.spriteSet);
            return landing;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    static class Landing
    extends DripParticle {
        private Landing(ClientWorld clientWorld, double d, double d2, double d3, Fluid fluid) {
            super(clientWorld, d, d2, d3, fluid);
            this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        }
    }

    public static class FallingWaterFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public FallingWaterFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            FallingLiquidParticle fallingLiquidParticle = new FallingLiquidParticle(clientWorld, d, d2, d3, Fluids.WATER, ParticleTypes.SPLASH);
            fallingLiquidParticle.setColor(0.2f, 0.3f, 1.0f);
            fallingLiquidParticle.selectSpriteRandomly(this.spriteSet);
            return fallingLiquidParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class FallingObsidianTearFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public FallingObsidianTearFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            FallingLiquidParticle fallingLiquidParticle = new FallingLiquidParticle(clientWorld, d, d2, d3, Fluids.EMPTY, ParticleTypes.LANDING_OBSIDIAN_TEAR);
            fallingLiquidParticle.fullbright = true;
            fallingLiquidParticle.particleGravity = 0.01f;
            fallingLiquidParticle.setColor(0.51171875f, 0.03125f, 0.890625f);
            fallingLiquidParticle.selectSpriteRandomly(this.spriteSet);
            return fallingLiquidParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    static class FallingNectarParticle
    extends DripParticle {
        private FallingNectarParticle(ClientWorld clientWorld, double d, double d2, double d3, Fluid fluid) {
            super(clientWorld, d, d2, d3, fluid);
            this.maxAge = (int)(64.0 / (Math.random() * 0.8 + 0.2));
        }

        @Override
        protected void updateMotion() {
            if (this.onGround) {
                this.setExpired();
            }
        }
    }

    public static class FallingNectarFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public FallingNectarFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            FallingNectarParticle fallingNectarParticle = new FallingNectarParticle(clientWorld, d, d2, d3, Fluids.EMPTY);
            fallingNectarParticle.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
            fallingNectarParticle.particleGravity = 0.007f;
            fallingNectarParticle.setColor(0.92f, 0.782f, 0.72f);
            fallingNectarParticle.selectSpriteRandomly(this.spriteSet);
            return fallingNectarParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    static class FallingLiquidParticle
    extends FallingNectarParticle {
        protected final IParticleData particleData;

        private FallingLiquidParticle(ClientWorld clientWorld, double d, double d2, double d3, Fluid fluid, IParticleData iParticleData) {
            super(clientWorld, d, d2, d3, fluid);
            this.particleData = iParticleData;
        }

        @Override
        protected void updateMotion() {
            if (this.onGround) {
                this.setExpired();
                this.world.addParticle(this.particleData, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
            }
        }
    }

    public static class FallingLavaFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public FallingLavaFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            FallingLiquidParticle fallingLiquidParticle = new FallingLiquidParticle(clientWorld, d, d2, d3, Fluids.LAVA, ParticleTypes.LANDING_LAVA);
            fallingLiquidParticle.setColor(1.0f, 0.2857143f, 0.083333336f);
            fallingLiquidParticle.selectSpriteRandomly(this.spriteSet);
            return fallingLiquidParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    static class FallingHoneyParticle
    extends FallingLiquidParticle {
        private FallingHoneyParticle(ClientWorld clientWorld, double d, double d2, double d3, Fluid fluid, IParticleData iParticleData) {
            super(clientWorld, d, d2, d3, fluid, iParticleData);
        }

        @Override
        protected void updateMotion() {
            if (this.onGround) {
                this.setExpired();
                this.world.addParticle(this.particleData, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
                this.world.playSound(this.posX + 0.5, this.posY, this.posZ + 0.5, SoundEvents.BLOCK_BEEHIVE_DROP, SoundCategory.BLOCKS, 0.3f + this.world.rand.nextFloat() * 2.0f / 3.0f, 1.0f, true);
            }
        }
    }

    public static class FallingHoneyFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public FallingHoneyFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            FallingHoneyParticle fallingHoneyParticle = new FallingHoneyParticle(clientWorld, d, d2, d3, Fluids.EMPTY, ParticleTypes.LANDING_HONEY);
            fallingHoneyParticle.particleGravity = 0.01f;
            fallingHoneyParticle.setColor(0.582f, 0.448f, 0.082f);
            fallingHoneyParticle.selectSpriteRandomly(this.spriteSet);
            return fallingHoneyParticle;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class DrippingWaterFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public DrippingWaterFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Dripping dripping = new Dripping(clientWorld, d, d2, d3, Fluids.WATER, ParticleTypes.FALLING_WATER);
            dripping.setColor(0.2f, 0.3f, 1.0f);
            dripping.selectSpriteRandomly(this.spriteSet);
            return dripping;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class DrippingObsidianTearFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public DrippingObsidianTearFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Dripping dripping = new Dripping(clientWorld, d, d2, d3, Fluids.EMPTY, ParticleTypes.FALLING_OBSIDIAN_TEAR);
            dripping.fullbright = true;
            dripping.particleGravity *= 0.01f;
            dripping.maxAge = 100;
            dripping.setColor(0.51171875f, 0.03125f, 0.890625f);
            dripping.selectSpriteRandomly(this.spriteSet);
            return dripping;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class DrippingLavaFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteSet;

        public DrippingLavaFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            DrippingLava drippingLava = new DrippingLava(clientWorld, d, d2, d3, Fluids.LAVA, ParticleTypes.FALLING_LAVA);
            drippingLava.selectSpriteRandomly(this.spriteSet);
            return drippingLava;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    static class DrippingLava
    extends Dripping {
        private DrippingLava(ClientWorld clientWorld, double d, double d2, double d3, Fluid fluid, IParticleData iParticleData) {
            super(clientWorld, d, d2, d3, fluid, iParticleData);
        }

        @Override
        protected void ageParticle() {
            this.particleRed = 1.0f;
            this.particleGreen = 16.0f / (float)(40 - this.maxAge + 16);
            this.particleBlue = 4.0f / (float)(40 - this.maxAge + 8);
            super.ageParticle();
        }
    }

    public static class DrippingHoneyFactory
    implements IParticleFactory<BasicParticleType> {
        protected final IAnimatedSprite spriteWithAge;

        public DrippingHoneyFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteWithAge = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Dripping dripping = new Dripping(clientWorld, d, d2, d3, Fluids.EMPTY, ParticleTypes.FALLING_HONEY);
            dripping.particleGravity *= 0.01f;
            dripping.maxAge = 100;
            dripping.setColor(0.622f, 0.508f, 0.082f);
            dripping.selectSpriteRandomly(this.spriteWithAge);
            return dripping;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    static class Dripping
    extends DripParticle {
        private final IParticleData particleData;

        private Dripping(ClientWorld clientWorld, double d, double d2, double d3, Fluid fluid, IParticleData iParticleData) {
            super(clientWorld, d, d2, d3, fluid);
            this.particleData = iParticleData;
            this.particleGravity *= 0.02f;
            this.maxAge = 40;
        }

        @Override
        protected void ageParticle() {
            if (this.maxAge-- <= 0) {
                this.setExpired();
                this.world.addParticle(this.particleData, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
            }
        }

        @Override
        protected void updateMotion() {
            this.motionX *= 0.02;
            this.motionY *= 0.02;
            this.motionZ *= 0.02;
        }
    }
}

