/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;

public class DiggingParticle
extends SpriteTexturedParticle {
    private final BlockState sourceState;
    private BlockPos sourcePos;
    private final float u;
    private final float v;

    public DiggingParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6, BlockState blockState) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
        this.sourceState = blockState;
        this.setSprite(Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelShapes().getTexture(blockState));
        this.particleGravity = 1.0f;
        this.particleRed = 0.6f;
        this.particleGreen = 0.6f;
        this.particleBlue = 0.6f;
        this.particleScale /= 2.0f;
        this.u = this.rand.nextFloat() * 3.0f;
        this.v = this.rand.nextFloat() * 3.0f;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.TERRAIN_SHEET;
    }

    public DiggingParticle setBlockPos(BlockPos blockPos) {
        this.sourcePos = blockPos;
        if (this.sourceState.isIn(Blocks.GRASS_BLOCK)) {
            return this;
        }
        this.multiplyColor(blockPos);
        return this;
    }

    public DiggingParticle init() {
        this.sourcePos = new BlockPos(this.posX, this.posY, this.posZ);
        if (this.sourceState.isIn(Blocks.GRASS_BLOCK)) {
            return this;
        }
        this.multiplyColor(this.sourcePos);
        return this;
    }

    protected void multiplyColor(@Nullable BlockPos blockPos) {
        int n = Minecraft.getInstance().getBlockColors().getColor(this.sourceState, this.world, blockPos, 0);
        this.particleRed *= (float)(n >> 16 & 0xFF) / 255.0f;
        this.particleGreen *= (float)(n >> 8 & 0xFF) / 255.0f;
        this.particleBlue *= (float)(n & 0xFF) / 255.0f;
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

    @Override
    public int getBrightnessForRender(float f) {
        int n = super.getBrightnessForRender(f);
        int n2 = 0;
        if (this.world.isBlockLoaded(this.sourcePos)) {
            n2 = WorldRenderer.getCombinedLight(this.world, this.sourcePos);
        }
        return n == 0 ? n2 : n;
    }

    public static class Factory
    implements IParticleFactory<BlockParticleData> {
        @Override
        public Particle makeParticle(BlockParticleData blockParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            BlockState blockState = blockParticleData.getBlockState();
            return !blockState.isAir() && !blockState.isIn(Blocks.MOVING_PISTON) ? new DiggingParticle(clientWorld, d, d2, d3, d4, d5, d6, blockState).init() : null;
        }

        @Override
        public Particle makeParticle(IParticleData iParticleData, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return this.makeParticle((BlockParticleData)iParticleData, clientWorld, d, d2, d3, d4, d5, d6);
        }
    }
}

