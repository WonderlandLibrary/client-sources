/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.end;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.server.ServerWorld;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public enum DragonSpawnState {
    START{

        @Override
        public void process(ServerWorld serverWorld, DragonFightManager dragonFightManager, List<EnderCrystalEntity> list, int n, BlockPos blockPos) {
            BlockPos blockPos2 = new BlockPos(0, 128, 0);
            for (EnderCrystalEntity enderCrystalEntity : list) {
                enderCrystalEntity.setBeamTarget(blockPos2);
            }
            dragonFightManager.setRespawnState(PREPARING_TO_SUMMON_PILLARS);
        }
    }
    ,
    PREPARING_TO_SUMMON_PILLARS{

        @Override
        public void process(ServerWorld serverWorld, DragonFightManager dragonFightManager, List<EnderCrystalEntity> list, int n, BlockPos blockPos) {
            if (n < 100) {
                if (n == 0 || n == 50 || n == 51 || n == 52 || n >= 95) {
                    serverWorld.playEvent(3001, new BlockPos(0, 128, 0), 0);
                }
            } else {
                dragonFightManager.setRespawnState(SUMMONING_PILLARS);
            }
        }
    }
    ,
    SUMMONING_PILLARS{

        @Override
        public void process(ServerWorld serverWorld, DragonFightManager dragonFightManager, List<EnderCrystalEntity> list, int n, BlockPos blockPos) {
            boolean bl;
            int n2 = 40;
            boolean bl2 = n % 40 == 0;
            boolean bl3 = bl = n % 40 == 39;
            if (bl2 || bl) {
                int n3 = n / 40;
                List<EndSpikeFeature.EndSpike> list2 = EndSpikeFeature.func_236356_a_(serverWorld);
                if (n3 < list2.size()) {
                    EndSpikeFeature.EndSpike endSpike = list2.get(n3);
                    if (bl2) {
                        for (EnderCrystalEntity enderCrystalEntity : list) {
                            enderCrystalEntity.setBeamTarget(new BlockPos(endSpike.getCenterX(), endSpike.getHeight() + 1, endSpike.getCenterZ()));
                        }
                    } else {
                        int n4 = 10;
                        for (BlockPos blockPos2 : BlockPos.getAllInBoxMutable(new BlockPos(endSpike.getCenterX() - 10, endSpike.getHeight() - 10, endSpike.getCenterZ() - 10), new BlockPos(endSpike.getCenterX() + 10, endSpike.getHeight() + 10, endSpike.getCenterZ() + 10))) {
                            serverWorld.removeBlock(blockPos2, true);
                        }
                        serverWorld.createExplosion(null, (float)endSpike.getCenterX() + 0.5f, endSpike.getHeight(), (float)endSpike.getCenterZ() + 0.5f, 5.0f, Explosion.Mode.DESTROY);
                        EndSpikeFeatureConfig endSpikeFeatureConfig = new EndSpikeFeatureConfig(true, ImmutableList.of(endSpike), new BlockPos(0, 128, 0));
                        Feature.END_SPIKE.withConfiguration(endSpikeFeatureConfig).func_242765_a(serverWorld, serverWorld.getChunkProvider().getChunkGenerator(), new Random(), new BlockPos(endSpike.getCenterX(), 45, endSpike.getCenterZ()));
                    }
                } else if (bl2) {
                    dragonFightManager.setRespawnState(SUMMONING_DRAGON);
                }
            }
        }
    }
    ,
    SUMMONING_DRAGON{

        @Override
        public void process(ServerWorld serverWorld, DragonFightManager dragonFightManager, List<EnderCrystalEntity> list, int n, BlockPos blockPos) {
            if (n >= 100) {
                dragonFightManager.setRespawnState(END);
                dragonFightManager.resetSpikeCrystals();
                for (EnderCrystalEntity enderCrystalEntity : list) {
                    enderCrystalEntity.setBeamTarget(null);
                    serverWorld.createExplosion(enderCrystalEntity, enderCrystalEntity.getPosX(), enderCrystalEntity.getPosY(), enderCrystalEntity.getPosZ(), 6.0f, Explosion.Mode.NONE);
                    enderCrystalEntity.remove();
                }
            } else if (n >= 80) {
                serverWorld.playEvent(3001, new BlockPos(0, 128, 0), 0);
            } else if (n == 0) {
                for (EnderCrystalEntity enderCrystalEntity : list) {
                    enderCrystalEntity.setBeamTarget(new BlockPos(0, 128, 0));
                }
            } else if (n < 5) {
                serverWorld.playEvent(3001, new BlockPos(0, 128, 0), 0);
            }
        }
    }
    ,
    END{

        @Override
        public void process(ServerWorld serverWorld, DragonFightManager dragonFightManager, List<EnderCrystalEntity> list, int n, BlockPos blockPos) {
        }
    };


    public abstract void process(ServerWorld var1, DragonFightManager var2, List<EnderCrystalEntity> var3, int var4, BlockPos var5);
}

