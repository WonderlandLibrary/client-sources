/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBiomeReader;
import net.minecraft.world.IDayTimeReader;
import net.minecraft.world.ITickList;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.storage.IWorldInfo;

public interface IWorld
extends IBiomeReader,
IDayTimeReader {
    @Override
    default public long func_241851_ab() {
        return this.getWorldInfo().getDayTime();
    }

    public ITickList<Block> getPendingBlockTicks();

    public ITickList<Fluid> getPendingFluidTicks();

    public IWorldInfo getWorldInfo();

    public DifficultyInstance getDifficultyForLocation(BlockPos var1);

    default public Difficulty getDifficulty() {
        return this.getWorldInfo().getDifficulty();
    }

    public AbstractChunkProvider getChunkProvider();

    @Override
    default public boolean chunkExists(int n, int n2) {
        return this.getChunkProvider().chunkExists(n, n2);
    }

    public Random getRandom();

    default public void func_230547_a_(BlockPos blockPos, Block block) {
    }

    public void playSound(@Nullable PlayerEntity var1, BlockPos var2, SoundEvent var3, SoundCategory var4, float var5, float var6);

    public void addParticle(IParticleData var1, double var2, double var4, double var6, double var8, double var10, double var12);

    public void playEvent(@Nullable PlayerEntity var1, int var2, BlockPos var3, int var4);

    default public int func_234938_ad_() {
        return this.getDimensionType().getLogicalHeight();
    }

    default public void playEvent(int n, BlockPos blockPos, int n2) {
        this.playEvent(null, n, blockPos, n2);
    }
}

