/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.spawner;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.merchant.villager.WanderingTraderEntity;
import net.minecraft.entity.passive.horse.TraderLlamaEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.ISpecialSpawner;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraft.world.storage.IServerWorldInfo;

public class WanderingTraderSpawner
implements ISpecialSpawner {
    private final Random random = new Random();
    private final IServerWorldInfo field_234559_b_;
    private int field_221248_c;
    private int field_221249_d;
    private int field_221250_e;

    public WanderingTraderSpawner(IServerWorldInfo iServerWorldInfo) {
        this.field_234559_b_ = iServerWorldInfo;
        this.field_221248_c = 1200;
        this.field_221249_d = iServerWorldInfo.getWanderingTraderSpawnDelay();
        this.field_221250_e = iServerWorldInfo.getWanderingTraderSpawnChance();
        if (this.field_221249_d == 0 && this.field_221250_e == 0) {
            this.field_221249_d = 24000;
            iServerWorldInfo.setWanderingTraderSpawnDelay(this.field_221249_d);
            this.field_221250_e = 25;
            iServerWorldInfo.setWanderingTraderSpawnChance(this.field_221250_e);
        }
    }

    @Override
    public int func_230253_a_(ServerWorld serverWorld, boolean bl, boolean bl2) {
        if (!serverWorld.getGameRules().getBoolean(GameRules.DO_TRADER_SPAWNING)) {
            return 1;
        }
        if (--this.field_221248_c > 0) {
            return 1;
        }
        this.field_221248_c = 1200;
        this.field_221249_d -= 1200;
        this.field_234559_b_.setWanderingTraderSpawnDelay(this.field_221249_d);
        if (this.field_221249_d > 0) {
            return 1;
        }
        this.field_221249_d = 24000;
        if (!serverWorld.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
            return 1;
        }
        int n = this.field_221250_e;
        this.field_221250_e = MathHelper.clamp(this.field_221250_e + 25, 25, 75);
        this.field_234559_b_.setWanderingTraderSpawnChance(this.field_221250_e);
        if (this.random.nextInt(100) > n) {
            return 1;
        }
        if (this.func_234562_a_(serverWorld)) {
            this.field_221250_e = 25;
            return 0;
        }
        return 1;
    }

    private boolean func_234562_a_(ServerWorld serverWorld) {
        ServerPlayerEntity serverPlayerEntity = serverWorld.getRandomPlayer();
        if (serverPlayerEntity == null) {
            return false;
        }
        if (this.random.nextInt(10) != 0) {
            return true;
        }
        BlockPos blockPos = serverPlayerEntity.getPosition();
        int n = 48;
        PointOfInterestManager pointOfInterestManager = serverWorld.getPointOfInterestManager();
        Optional<BlockPos> optional = pointOfInterestManager.find(PointOfInterestType.MEETING.getPredicate(), WanderingTraderSpawner::lambda$func_234562_a_$0, blockPos, 48, PointOfInterestManager.Status.ANY);
        BlockPos blockPos2 = optional.orElse(blockPos);
        BlockPos blockPos3 = this.func_234561_a_(serverWorld, blockPos2, 48);
        if (blockPos3 != null && this.func_234560_a_(serverWorld, blockPos3)) {
            if (serverWorld.func_242406_i(blockPos3).equals(Optional.of(Biomes.THE_VOID))) {
                return true;
            }
            WanderingTraderEntity wanderingTraderEntity = EntityType.WANDERING_TRADER.spawn(serverWorld, null, null, null, blockPos3, SpawnReason.EVENT, false, true);
            if (wanderingTraderEntity != null) {
                for (int i = 0; i < 2; ++i) {
                    this.func_242373_a(serverWorld, wanderingTraderEntity, 4);
                }
                this.field_234559_b_.setWanderingTraderID(wanderingTraderEntity.getUniqueID());
                wanderingTraderEntity.setDespawnDelay(48000);
                wanderingTraderEntity.setWanderTarget(blockPos2);
                wanderingTraderEntity.setHomePosAndDistance(blockPos2, 16);
                return false;
            }
        }
        return true;
    }

    private void func_242373_a(ServerWorld serverWorld, WanderingTraderEntity wanderingTraderEntity, int n) {
        TraderLlamaEntity traderLlamaEntity;
        BlockPos blockPos = this.func_234561_a_(serverWorld, wanderingTraderEntity.getPosition(), n);
        if (blockPos != null && (traderLlamaEntity = EntityType.TRADER_LLAMA.spawn(serverWorld, null, null, null, blockPos, SpawnReason.EVENT, false, true)) != null) {
            traderLlamaEntity.setLeashHolder(wanderingTraderEntity, false);
        }
    }

    @Nullable
    private BlockPos func_234561_a_(IWorldReader iWorldReader, BlockPos blockPos, int n) {
        BlockPos blockPos2 = null;
        for (int i = 0; i < 10; ++i) {
            int n2;
            int n3;
            int n4 = blockPos.getX() + this.random.nextInt(n * 2) - n;
            BlockPos blockPos3 = new BlockPos(n4, n3 = iWorldReader.getHeight(Heightmap.Type.WORLD_SURFACE, n4, n2 = blockPos.getZ() + this.random.nextInt(n * 2) - n), n2);
            if (!WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, iWorldReader, blockPos3, EntityType.WANDERING_TRADER)) continue;
            blockPos2 = blockPos3;
            break;
        }
        return blockPos2;
    }

    private boolean func_234560_a_(IBlockReader iBlockReader, BlockPos blockPos) {
        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(blockPos, blockPos.add(1, 2, 1))) {
            if (iBlockReader.getBlockState(blockPos2).getCollisionShape(iBlockReader, blockPos2).isEmpty()) continue;
            return true;
        }
        return false;
    }

    private static boolean lambda$func_234562_a_$0(BlockPos blockPos) {
        return false;
    }
}

