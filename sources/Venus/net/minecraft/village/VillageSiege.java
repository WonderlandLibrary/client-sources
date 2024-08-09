/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.village;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.ISpecialSpawner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VillageSiege
implements ISpecialSpawner {
    private static final Logger LOGGER = LogManager.getLogger();
    private boolean hasSetupSiege;
    private State siegeState = State.SIEGE_DONE;
    private int siegeCount;
    private int nextSpawnTime;
    private int spawnX;
    private int spawnY;
    private int spawnZ;

    @Override
    public int func_230253_a_(ServerWorld serverWorld, boolean bl, boolean bl2) {
        if (!serverWorld.isDaytime() && bl) {
            float f = serverWorld.func_242415_f(0.0f);
            if ((double)f == 0.5) {
                State state = this.siegeState = serverWorld.rand.nextInt(10) == 0 ? State.SIEGE_TONIGHT : State.SIEGE_DONE;
            }
            if (this.siegeState == State.SIEGE_DONE) {
                return 1;
            }
            if (!this.hasSetupSiege) {
                if (!this.trySetupSiege(serverWorld)) {
                    return 1;
                }
                this.hasSetupSiege = true;
            }
            if (this.nextSpawnTime > 0) {
                --this.nextSpawnTime;
                return 1;
            }
            this.nextSpawnTime = 2;
            if (this.siegeCount > 0) {
                this.spawnZombie(serverWorld);
                --this.siegeCount;
            } else {
                this.siegeState = State.SIEGE_DONE;
            }
            return 0;
        }
        this.siegeState = State.SIEGE_DONE;
        this.hasSetupSiege = false;
        return 1;
    }

    private boolean trySetupSiege(ServerWorld serverWorld) {
        for (PlayerEntity playerEntity : serverWorld.getPlayers()) {
            BlockPos blockPos;
            if (playerEntity.isSpectator() || !serverWorld.isVillage(blockPos = playerEntity.getPosition()) || serverWorld.getBiome(blockPos).getCategory() == Biome.Category.MUSHROOM) continue;
            for (int i = 0; i < 10; ++i) {
                float f = serverWorld.rand.nextFloat() * ((float)Math.PI * 2);
                this.spawnX = blockPos.getX() + MathHelper.floor(MathHelper.cos(f) * 32.0f);
                this.spawnY = blockPos.getY();
                this.spawnZ = blockPos.getZ() + MathHelper.floor(MathHelper.sin(f) * 32.0f);
                if (this.findRandomSpawnPos(serverWorld, new BlockPos(this.spawnX, this.spawnY, this.spawnZ)) == null) continue;
                this.nextSpawnTime = 0;
                this.siegeCount = 20;
                break;
            }
            return false;
        }
        return true;
    }

    private void spawnZombie(ServerWorld serverWorld) {
        Vector3d vector3d = this.findRandomSpawnPos(serverWorld, new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
        if (vector3d != null) {
            ZombieEntity zombieEntity;
            try {
                zombieEntity = new ZombieEntity(serverWorld);
                zombieEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(zombieEntity.getPosition()), SpawnReason.EVENT, null, null);
            } catch (Exception exception) {
                LOGGER.warn("Failed to create zombie for village siege at {}", (Object)vector3d, (Object)exception);
                return;
            }
            zombieEntity.setLocationAndAngles(vector3d.x, vector3d.y, vector3d.z, serverWorld.rand.nextFloat() * 360.0f, 0.0f);
            serverWorld.func_242417_l(zombieEntity);
        }
    }

    @Nullable
    private Vector3d findRandomSpawnPos(ServerWorld serverWorld, BlockPos blockPos) {
        for (int i = 0; i < 10; ++i) {
            int n;
            int n2;
            int n3 = blockPos.getX() + serverWorld.rand.nextInt(16) - 8;
            BlockPos blockPos2 = new BlockPos(n3, n2 = serverWorld.getHeight(Heightmap.Type.WORLD_SURFACE, n3, n = blockPos.getZ() + serverWorld.rand.nextInt(16) - 8), n);
            if (!serverWorld.isVillage(blockPos2) || !MonsterEntity.canMonsterSpawnInLight(EntityType.ZOMBIE, serverWorld, SpawnReason.EVENT, blockPos2, serverWorld.rand)) continue;
            return Vector3d.copyCenteredHorizontally(blockPos2);
        }
        return null;
    }

    static enum State {
        SIEGE_CAN_ACTIVATE,
        SIEGE_TONIGHT,
        SIEGE_DONE;

    }
}

