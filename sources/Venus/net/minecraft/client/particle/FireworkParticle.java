/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.DyeColor;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class FireworkParticle {

    public static class Starter
    extends MetaParticle {
        private int fireworkAge;
        private final ParticleManager manager;
        private ListNBT fireworkExplosions;
        private boolean twinkle;

        public Starter(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, ParticleManager particleManager, @Nullable CompoundNBT compoundNBT) {
            super(clientWorld, d, d2, d3);
            this.motionX = d4;
            this.motionY = d5;
            this.motionZ = d6;
            this.manager = particleManager;
            this.maxAge = 8;
            if (compoundNBT != null) {
                this.fireworkExplosions = compoundNBT.getList("Explosions", 10);
                if (this.fireworkExplosions.isEmpty()) {
                    this.fireworkExplosions = null;
                } else {
                    this.maxAge = this.fireworkExplosions.size() * 2 - 1;
                    for (int i = 0; i < this.fireworkExplosions.size(); ++i) {
                        CompoundNBT compoundNBT2 = this.fireworkExplosions.getCompound(i);
                        if (!compoundNBT2.getBoolean("Flicker")) continue;
                        this.twinkle = true;
                        this.maxAge += 15;
                        break;
                    }
                }
            }
        }

        @Override
        public void tick() {
            Object object;
            int n;
            if (this.fireworkAge == 0 && this.fireworkExplosions != null) {
                n = this.isFarFromCamera();
                boolean bl = false;
                if (this.fireworkExplosions.size() >= 3) {
                    bl = true;
                } else {
                    for (int i = 0; i < this.fireworkExplosions.size(); ++i) {
                        CompoundNBT compoundNBT = this.fireworkExplosions.getCompound(i);
                        if (FireworkRocketItem.Shape.get(compoundNBT.getByte("Type")) != FireworkRocketItem.Shape.LARGE_BALL) continue;
                        bl = true;
                        break;
                    }
                }
                object = bl ? (n != 0 ? SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST) : (n != 0 ? SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST);
                this.world.playSound(this.posX, this.posY, this.posZ, (SoundEvent)object, SoundCategory.AMBIENT, 20.0f, 0.95f + this.rand.nextFloat() * 0.1f, false);
            }
            if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.size()) {
                n = this.fireworkAge / 2;
                CompoundNBT compoundNBT = this.fireworkExplosions.getCompound(n);
                object = FireworkRocketItem.Shape.get(compoundNBT.getByte("Type"));
                boolean bl = compoundNBT.getBoolean("Trail");
                boolean bl2 = compoundNBT.getBoolean("Flicker");
                int[] nArray = compoundNBT.getIntArray("Colors");
                int[] nArray2 = compoundNBT.getIntArray("FadeColors");
                if (nArray.length == 0) {
                    nArray = new int[]{DyeColor.BLACK.getFireworkColor()};
                }
                switch (1.$SwitchMap$net$minecraft$item$FireworkRocketItem$Shape[((Enum)object).ordinal()]) {
                    default: {
                        this.createBall(0.25, 2, nArray, nArray2, bl, bl2);
                        break;
                    }
                    case 2: {
                        this.createBall(0.5, 4, nArray, nArray2, bl, bl2);
                        break;
                    }
                    case 3: {
                        this.createShaped(0.5, new double[][]{{0.0, 1.0}, {0.3455, 0.309}, {0.9511, 0.309}, {0.3795918367346939, -0.12653061224489795}, {0.6122448979591837, -0.8040816326530612}, {0.0, -0.35918367346938773}}, nArray, nArray2, bl, bl2, true);
                        break;
                    }
                    case 4: {
                        this.createShaped(0.5, new double[][]{{0.0, 0.2}, {0.2, 0.2}, {0.2, 0.6}, {0.6, 0.6}, {0.6, 0.2}, {0.2, 0.2}, {0.2, 0.0}, {0.4, 0.0}, {0.4, -0.6}, {0.2, -0.6}, {0.2, -0.4}, {0.0, -0.4}}, nArray, nArray2, bl, bl2, false);
                        break;
                    }
                    case 5: {
                        this.createBurst(nArray, nArray2, bl, bl2);
                    }
                }
                int n2 = nArray[0];
                float f = (float)((n2 & 0xFF0000) >> 16) / 255.0f;
                float f2 = (float)((n2 & 0xFF00) >> 8) / 255.0f;
                float f3 = (float)((n2 & 0xFF) >> 0) / 255.0f;
                Particle particle = this.manager.addParticle(ParticleTypes.FLASH, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
                particle.setColor(f, f2, f3);
            }
            ++this.fireworkAge;
            if (this.fireworkAge > this.maxAge) {
                if (this.twinkle) {
                    n = this.isFarFromCamera() ? 1 : 0;
                    SoundEvent soundEvent = n != 0 ? SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR : SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE;
                    this.world.playSound(this.posX, this.posY, this.posZ, soundEvent, SoundCategory.AMBIENT, 20.0f, 0.9f + this.rand.nextFloat() * 0.15f, false);
                }
                this.setExpired();
            }
        }

        private boolean isFarFromCamera() {
            Minecraft minecraft = Minecraft.getInstance();
            return minecraft.gameRenderer.getActiveRenderInfo().getProjectedView().squareDistanceTo(this.posX, this.posY, this.posZ) >= 256.0;
        }

        private void createParticle(double d, double d2, double d3, double d4, double d5, double d6, int[] nArray, int[] nArray2, boolean bl, boolean bl2) {
            Spark spark = (Spark)this.manager.addParticle(ParticleTypes.FIREWORK, d, d2, d3, d4, d5, d6);
            spark.setTrail(bl);
            spark.setTwinkle(bl2);
            spark.setAlphaF(0.99f);
            int n = this.rand.nextInt(nArray.length);
            spark.setColor(nArray[n]);
            if (nArray2.length > 0) {
                spark.setColorFade(Util.getRandomInt(nArray2, this.rand));
            }
        }

        private void createBall(double d, int n, int[] nArray, int[] nArray2, boolean bl, boolean bl2) {
            double d2 = this.posX;
            double d3 = this.posY;
            double d4 = this.posZ;
            for (int i = -n; i <= n; ++i) {
                for (int j = -n; j <= n; ++j) {
                    for (int k = -n; k <= n; ++k) {
                        double d5 = (double)j + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        double d6 = (double)i + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        double d7 = (double)k + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        double d8 = (double)MathHelper.sqrt(d5 * d5 + d6 * d6 + d7 * d7) / d + this.rand.nextGaussian() * 0.05;
                        this.createParticle(d2, d3, d4, d5 / d8, d6 / d8, d7 / d8, nArray, nArray2, bl, bl2);
                        if (i == -n || i == n || j == -n || j == n) continue;
                        k += n * 2 - 1;
                    }
                }
            }
        }

        private void createShaped(double d, double[][] dArray, int[] nArray, int[] nArray2, boolean bl, boolean bl2, boolean bl3) {
            double d2 = dArray[0][0];
            double d3 = dArray[0][1];
            this.createParticle(this.posX, this.posY, this.posZ, d2 * d, d3 * d, 0.0, nArray, nArray2, bl, bl2);
            float f = this.rand.nextFloat() * (float)Math.PI;
            double d4 = bl3 ? 0.034 : 0.34;
            for (int i = 0; i < 3; ++i) {
                double d5 = (double)f + (double)((float)i * (float)Math.PI) * d4;
                double d6 = d2;
                double d7 = d3;
                for (int j = 1; j < dArray.length; ++j) {
                    double d8 = dArray[j][0];
                    double d9 = dArray[j][1];
                    for (double d10 = 0.25; d10 <= 1.0; d10 += 0.25) {
                        double d11 = MathHelper.lerp(d10, d6, d8) * d;
                        double d12 = MathHelper.lerp(d10, d7, d9) * d;
                        double d13 = d11 * Math.sin(d5);
                        d11 *= Math.cos(d5);
                        for (double d14 = -1.0; d14 <= 1.0; d14 += 2.0) {
                            this.createParticle(this.posX, this.posY, this.posZ, d11 * d14, d12, d13 * d14, nArray, nArray2, bl, bl2);
                        }
                    }
                    d6 = d8;
                    d7 = d9;
                }
            }
        }

        private void createBurst(int[] nArray, int[] nArray2, boolean bl, boolean bl2) {
            double d = this.rand.nextGaussian() * 0.05;
            double d2 = this.rand.nextGaussian() * 0.05;
            for (int i = 0; i < 70; ++i) {
                double d3 = this.motionX * 0.5 + this.rand.nextGaussian() * 0.15 + d;
                double d4 = this.motionZ * 0.5 + this.rand.nextGaussian() * 0.15 + d2;
                double d5 = this.motionY * 0.5 + this.rand.nextDouble() * 0.5;
                this.createParticle(this.posX, this.posY, this.posZ, d3, d5, d4, nArray, nArray2, bl, bl2);
            }
        }
    }

    public static class SparkFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public SparkFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Spark spark = new Spark(clientWorld, d, d2, d3, d4, d5, d6, Minecraft.getInstance().particles, this.spriteSet);
            spark.setAlphaF(0.99f);
            return spark;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    static class Spark
    extends SimpleAnimatedParticle {
        private boolean trail;
        private boolean twinkle;
        private final ParticleManager effectRenderer;
        private float fadeColourRed;
        private float fadeColourGreen;
        private float fadeColourBlue;
        private boolean hasFadeColour;

        private Spark(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, ParticleManager particleManager, IAnimatedSprite iAnimatedSprite) {
            super(clientWorld, d, d2, d3, iAnimatedSprite, -0.004f);
            this.motionX = d4;
            this.motionY = d5;
            this.motionZ = d6;
            this.effectRenderer = particleManager;
            this.particleScale *= 0.75f;
            this.maxAge = 48 + this.rand.nextInt(12);
            this.selectSpriteWithAge(iAnimatedSprite);
        }

        public void setTrail(boolean bl) {
            this.trail = bl;
        }

        public void setTwinkle(boolean bl) {
            this.twinkle = bl;
        }

        @Override
        public void renderParticle(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float f) {
            if (!this.twinkle || this.age < this.maxAge / 3 || (this.age + this.maxAge) / 3 % 2 == 0) {
                super.renderParticle(iVertexBuilder, activeRenderInfo, f);
            }
        }

        @Override
        public void tick() {
            super.tick();
            if (this.trail && this.age < this.maxAge / 2 && (this.age + this.maxAge) % 2 == 0) {
                Spark spark = new Spark(this.world, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0, this.effectRenderer, this.spriteWithAge);
                spark.setAlphaF(0.99f);
                spark.setColor(this.particleRed, this.particleGreen, this.particleBlue);
                spark.age = spark.maxAge / 2;
                if (this.hasFadeColour) {
                    spark.hasFadeColour = true;
                    spark.fadeColourRed = this.fadeColourRed;
                    spark.fadeColourGreen = this.fadeColourGreen;
                    spark.fadeColourBlue = this.fadeColourBlue;
                }
                spark.twinkle = this.twinkle;
                this.effectRenderer.addEffect(spark);
            }
        }
    }

    public static class OverlayFactory
    implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public OverlayFactory(IAnimatedSprite iAnimatedSprite) {
            this.spriteSet = iAnimatedSprite;
        }

        @Override
        public Particle makeParticle(BasicParticleType basicParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Overlay overlay = new Overlay(clientWorld, d, d2, d3);
            overlay.selectSpriteRandomly(this.spriteSet);
            return overlay;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BasicParticleType)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }

    public static class Overlay
    extends SpriteTexturedParticle {
        private Overlay(ClientWorld clientWorld, double d, double d2, double d3) {
            super(clientWorld, d, d2, d3);
            this.maxAge = 4;
        }

        @Override
        public IParticleRenderType getRenderType() {
            return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }

        @Override
        public void renderParticle(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float f) {
            this.setAlphaF(0.6f - ((float)this.age + f - 1.0f) * 0.25f * 0.5f);
            super.renderParticle(iVertexBuilder, activeRenderInfo, f);
        }

        @Override
        public float getScale(float f) {
            return 7.1f * MathHelper.sin(((float)this.age + f - 1.0f) * 0.25f * (float)Math.PI);
        }
    }
}

