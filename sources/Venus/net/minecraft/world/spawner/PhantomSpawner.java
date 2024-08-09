/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.spawner;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.stats.ServerStatisticsManager;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.ISpecialSpawner;
import net.minecraft.world.spawner.WorldEntitySpawner;

public class PhantomSpawner
implements ISpecialSpawner {
    private int ticksUntilSpawn;

    @Override
    public int func_230253_a_(ServerWorld serverWorld, boolean bl, boolean bl2) {
        if (!bl) {
            return 1;
        }
        if (!serverWorld.getGameRules().getBoolean(GameRules.DO_INSOMNIA)) {
            return 1;
        }
        Random random2 = serverWorld.rand;
        --this.ticksUntilSpawn;
        if (this.ticksUntilSpawn > 0) {
            return 1;
        }
        this.ticksUntilSpawn += (60 + random2.nextInt(60)) * 20;
        if (serverWorld.getSkylightSubtracted() < 5 && serverWorld.getDimensionType().hasSkyLight()) {
            return 1;
        }
        int n = 0;
        for (PlayerEntity playerEntity : serverWorld.getPlayers()) {
            FluidState fluidState;
            BlockState blockState;
            BlockPos blockPos;
            DifficultyInstance difficultyInstance;
            if (playerEntity.isSpectator()) continue;
            BlockPos blockPos2 = playerEntity.getPosition();
            if (serverWorld.getDimensionType().hasSkyLight() && (blockPos2.getY() < serverWorld.getSeaLevel() || !serverWorld.canSeeSky(blockPos2)) || !(difficultyInstance = serverWorld.getDifficultyForLocation(blockPos2)).isHarderThan(random2.nextFloat() * 3.0f)) continue;
            ServerStatisticsManager serverStatisticsManager = ((ServerPlayerEntity)playerEntity).getStats();
            int n2 = MathHelper.clamp(serverStatisticsManager.getValue(Stats.CUSTOM.get(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE);
            int n3 = 24000;
            if (random2.nextInt(n2) < 72000 || !WorldEntitySpawner.func_234968_a_(serverWorld, blockPos = blockPos2.up(20 + random2.nextInt(15)).east(-10 + random2.nextInt(21)).south(-10 + random2.nextInt(21)), blockState = serverWorld.getBlockState(blockPos), fluidState = serverWorld.getFluidState(blockPos), EntityType.PHANTOM)) continue;
            ILivingEntityData iLivingEntityData = null;
            int n4 = 1 + random2.nextInt(difficultyInstance.getDifficulty().getId() + 1);
            for (int i = 0; i < n4; ++i) {
                PhantomEntity phantomEntity = EntityType.PHANTOM.create(serverWorld);
                phantomEntity.moveToBlockPosAndAngles(blockPos, 0.0f, 0.0f);
                iLivingEntityData = phantomEntity.onInitialSpawn(serverWorld, difficultyInstance, SpawnReason.NATURAL, iLivingEntityData, null);
                serverWorld.func_242417_l(phantomEntity);
            }
            n += n4;
        }
        return n;
    }
}

