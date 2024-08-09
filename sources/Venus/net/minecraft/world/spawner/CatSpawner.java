/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.spawner;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.GameRules;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.ISpecialSpawner;
import net.minecraft.world.spawner.WorldEntitySpawner;

public class CatSpawner
implements ISpecialSpawner {
    private int field_221125_a;

    @Override
    public int func_230253_a_(ServerWorld serverWorld, boolean bl, boolean bl2) {
        if (bl2 && serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            --this.field_221125_a;
            if (this.field_221125_a > 0) {
                return 1;
            }
            this.field_221125_a = 1200;
            ServerPlayerEntity serverPlayerEntity = serverWorld.getRandomPlayer();
            if (serverPlayerEntity == null) {
                return 1;
            }
            Random random2 = serverWorld.rand;
            int n = (8 + random2.nextInt(24)) * (random2.nextBoolean() ? -1 : 1);
            int n2 = (8 + random2.nextInt(24)) * (random2.nextBoolean() ? -1 : 1);
            BlockPos blockPos = serverPlayerEntity.getPosition().add(n, 0, n2);
            if (!serverWorld.isAreaLoaded(blockPos.getX() - 10, blockPos.getY() - 10, blockPos.getZ() - 10, blockPos.getX() + 10, blockPos.getY() + 10, blockPos.getZ() + 10)) {
                return 1;
            }
            if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, serverWorld, blockPos, EntityType.CAT)) {
                if (serverWorld.func_241119_a_(blockPos, 1)) {
                    return this.func_221121_a(serverWorld, blockPos);
                }
                if (serverWorld.func_241112_a_().func_235010_a_(blockPos, true, Structure.field_236374_j_).isValid()) {
                    return this.func_221123_a(serverWorld, blockPos);
                }
            }
            return 1;
        }
        return 1;
    }

    private int func_221121_a(ServerWorld serverWorld, BlockPos blockPos) {
        List<CatEntity> list;
        int n = 48;
        if (serverWorld.getPointOfInterestManager().getCountInRange(PointOfInterestType.HOME.getPredicate(), blockPos, 48, PointOfInterestManager.Status.IS_OCCUPIED) > 4L && (list = serverWorld.getEntitiesWithinAABB(CatEntity.class, new AxisAlignedBB(blockPos).grow(48.0, 8.0, 48.0))).size() < 5) {
            return this.spawnCat(blockPos, serverWorld);
        }
        return 1;
    }

    private int func_221123_a(ServerWorld serverWorld, BlockPos blockPos) {
        int n = 16;
        List<CatEntity> list = serverWorld.getEntitiesWithinAABB(CatEntity.class, new AxisAlignedBB(blockPos).grow(16.0, 8.0, 16.0));
        return list.size() < 1 ? this.spawnCat(blockPos, serverWorld) : 0;
    }

    private int spawnCat(BlockPos blockPos, ServerWorld serverWorld) {
        CatEntity catEntity = EntityType.CAT.create(serverWorld);
        if (catEntity == null) {
            return 1;
        }
        catEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(blockPos), SpawnReason.NATURAL, null, null);
        catEntity.moveToBlockPosAndAngles(blockPos, 0.0f, 0.0f);
        serverWorld.func_242417_l(catEntity);
        return 0;
    }
}

