/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.spawner;

import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.PatrollerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.ISpecialSpawner;
import net.minecraft.world.spawner.WorldEntitySpawner;

public class PatrolSpawner
implements ISpecialSpawner {
    private int field_222698_b;

    @Override
    public int func_230253_a_(ServerWorld serverWorld, boolean bl, boolean bl2) {
        if (!bl) {
            return 1;
        }
        if (!serverWorld.getGameRules().getBoolean(GameRules.DO_PATROL_SPAWNING)) {
            return 1;
        }
        Random random2 = serverWorld.rand;
        --this.field_222698_b;
        if (this.field_222698_b > 0) {
            return 1;
        }
        this.field_222698_b += 12000 + random2.nextInt(1200);
        long l = serverWorld.getDayTime() / 24000L;
        if (l >= 5L && serverWorld.isDaytime()) {
            if (random2.nextInt(5) != 0) {
                return 1;
            }
            int n = serverWorld.getPlayers().size();
            if (n < 1) {
                return 1;
            }
            PlayerEntity playerEntity = serverWorld.getPlayers().get(random2.nextInt(n));
            if (playerEntity.isSpectator()) {
                return 1;
            }
            if (serverWorld.func_241119_a_(playerEntity.getPosition(), 1)) {
                return 1;
            }
            int n2 = (24 + random2.nextInt(24)) * (random2.nextBoolean() ? -1 : 1);
            int n3 = (24 + random2.nextInt(24)) * (random2.nextBoolean() ? -1 : 1);
            BlockPos.Mutable mutable = playerEntity.getPosition().toMutable().move(n2, 0, n3);
            if (!serverWorld.isAreaLoaded(mutable.getX() - 10, mutable.getY() - 10, mutable.getZ() - 10, mutable.getX() + 10, mutable.getY() + 10, mutable.getZ() + 10)) {
                return 1;
            }
            Biome biome = serverWorld.getBiome(mutable);
            Biome.Category category = biome.getCategory();
            if (category == Biome.Category.MUSHROOM) {
                return 1;
            }
            int n4 = 0;
            int n5 = (int)Math.ceil(serverWorld.getDifficultyForLocation(mutable).getAdditionalDifficulty()) + 1;
            for (int i = 0; i < n5; ++i) {
                ++n4;
                mutable.setY(serverWorld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, mutable).getY());
                if (i == 0) {
                    if (!this.spawnPatroller(serverWorld, mutable, random2, false)) {
                        break;
                    }
                } else {
                    this.spawnPatroller(serverWorld, mutable, random2, true);
                }
                mutable.setX(mutable.getX() + random2.nextInt(5) - random2.nextInt(5));
                mutable.setZ(mutable.getZ() + random2.nextInt(5) - random2.nextInt(5));
            }
            return n4;
        }
        return 1;
    }

    private boolean spawnPatroller(ServerWorld serverWorld, BlockPos blockPos, Random random2, boolean bl) {
        BlockState blockState = serverWorld.getBlockState(blockPos);
        if (!WorldEntitySpawner.func_234968_a_(serverWorld, blockPos, blockState, blockState.getFluidState(), EntityType.PILLAGER)) {
            return true;
        }
        if (!PatrollerEntity.func_223330_b(EntityType.PILLAGER, serverWorld, SpawnReason.PATROL, blockPos, random2)) {
            return true;
        }
        PatrollerEntity patrollerEntity = EntityType.PILLAGER.create(serverWorld);
        if (patrollerEntity != null) {
            if (bl) {
                patrollerEntity.setLeader(false);
                patrollerEntity.resetPatrolTarget();
            }
            patrollerEntity.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            patrollerEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(blockPos), SpawnReason.PATROL, null, null);
            serverWorld.func_242417_l(patrollerEntity);
            return false;
        }
        return true;
    }
}

