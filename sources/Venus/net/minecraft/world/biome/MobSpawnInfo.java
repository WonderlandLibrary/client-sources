/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Util;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobSpawnInfo {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final MobSpawnInfo EMPTY = new MobSpawnInfo(0.1f, (Map<EntityClassification, List<Spawners>>)Stream.of(EntityClassification.values()).collect(ImmutableMap.toImmutableMap(MobSpawnInfo::lambda$static$0, MobSpawnInfo::lambda$static$1)), ImmutableMap.of(), false);
    public static final MapCodec<MobSpawnInfo> CODEC = RecordCodecBuilder.mapCodec(MobSpawnInfo::lambda$static$5);
    private final float creatureSpawnProbability;
    private final Map<EntityClassification, List<Spawners>> spawners;
    private final Map<EntityType<?>, SpawnCosts> spawnCosts;
    private final boolean validSpawnBiomeForPlayer;

    private MobSpawnInfo(float f, Map<EntityClassification, List<Spawners>> map, Map<EntityType<?>, SpawnCosts> map2, boolean bl) {
        this.creatureSpawnProbability = f;
        this.spawners = map;
        this.spawnCosts = map2;
        this.validSpawnBiomeForPlayer = bl;
    }

    public List<Spawners> getSpawners(EntityClassification entityClassification) {
        return this.spawners.getOrDefault(entityClassification, ImmutableList.of());
    }

    @Nullable
    public SpawnCosts getSpawnCost(EntityType<?> entityType) {
        return this.spawnCosts.get(entityType);
    }

    public float getCreatureSpawnProbability() {
        return this.creatureSpawnProbability;
    }

    public boolean isValidSpawnBiomeForPlayer() {
        return this.validSpawnBiomeForPlayer;
    }

    private static App lambda$static$5(RecordCodecBuilder.Instance instance) {
        return instance.group(Codec.FLOAT.optionalFieldOf("creature_spawn_probability", Float.valueOf(0.1f)).forGetter(MobSpawnInfo::lambda$static$2), Codec.simpleMap(EntityClassification.CODEC, Spawners.CODEC.listOf().promotePartial((Consumer)Util.func_240982_a_("Spawn data: ", LOGGER::error)), IStringSerializable.createKeyable(EntityClassification.values())).fieldOf("spawners").forGetter(MobSpawnInfo::lambda$static$3), Codec.simpleMap(Registry.ENTITY_TYPE, SpawnCosts.CODEC, Registry.ENTITY_TYPE).fieldOf("spawn_costs").forGetter(MobSpawnInfo::lambda$static$4), ((MapCodec)Codec.BOOL.fieldOf("player_spawn_friendly")).orElse(false).forGetter(MobSpawnInfo::isValidSpawnBiomeForPlayer)).apply(instance, MobSpawnInfo::new);
    }

    private static Map lambda$static$4(MobSpawnInfo mobSpawnInfo) {
        return mobSpawnInfo.spawnCosts;
    }

    private static Map lambda$static$3(MobSpawnInfo mobSpawnInfo) {
        return mobSpawnInfo.spawners;
    }

    private static Float lambda$static$2(MobSpawnInfo mobSpawnInfo) {
        return Float.valueOf(mobSpawnInfo.creatureSpawnProbability);
    }

    private static List lambda$static$1(EntityClassification entityClassification) {
        return ImmutableList.of();
    }

    private static EntityClassification lambda$static$0(EntityClassification entityClassification) {
        return entityClassification;
    }

    public static class SpawnCosts {
        public static final Codec<SpawnCosts> CODEC = RecordCodecBuilder.create(SpawnCosts::lambda$static$2);
        private final double maxSpawnCost;
        private final double entitySpawnCost;

        private SpawnCosts(double d, double d2) {
            this.maxSpawnCost = d;
            this.entitySpawnCost = d2;
        }

        public double getMaxSpawnCost() {
            return this.maxSpawnCost;
        }

        public double getEntitySpawnCost() {
            return this.entitySpawnCost;
        }

        private static App lambda$static$2(RecordCodecBuilder.Instance instance) {
            return instance.group(((MapCodec)Codec.DOUBLE.fieldOf("energy_budget")).forGetter(SpawnCosts::lambda$static$0), ((MapCodec)Codec.DOUBLE.fieldOf("charge")).forGetter(SpawnCosts::lambda$static$1)).apply(instance, SpawnCosts::new);
        }

        private static Double lambda$static$1(SpawnCosts spawnCosts) {
            return spawnCosts.entitySpawnCost;
        }

        private static Double lambda$static$0(SpawnCosts spawnCosts) {
            return spawnCosts.maxSpawnCost;
        }
    }

    public static class Spawners
    extends WeightedRandom.Item {
        public static final Codec<Spawners> CODEC = RecordCodecBuilder.create(Spawners::lambda$static$4);
        public final EntityType<?> type;
        public final int minCount;
        public final int maxCount;

        public Spawners(EntityType<?> entityType, int n, int n2, int n3) {
            super(n);
            this.type = entityType.getClassification() == EntityClassification.MISC ? EntityType.PIG : entityType;
            this.minCount = n2;
            this.maxCount = n3;
        }

        public String toString() {
            return EntityType.getKey(this.type) + "*(" + this.minCount + "-" + this.maxCount + "):" + this.itemWeight;
        }

        private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
            return instance.group(((MapCodec)Registry.ENTITY_TYPE.fieldOf("type")).forGetter(Spawners::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("weight")).forGetter(Spawners::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("minCount")).forGetter(Spawners::lambda$static$2), ((MapCodec)Codec.INT.fieldOf("maxCount")).forGetter(Spawners::lambda$static$3)).apply(instance, Spawners::new);
        }

        private static Integer lambda$static$3(Spawners spawners) {
            return spawners.maxCount;
        }

        private static Integer lambda$static$2(Spawners spawners) {
            return spawners.minCount;
        }

        private static Integer lambda$static$1(Spawners spawners) {
            return spawners.itemWeight;
        }

        private static EntityType lambda$static$0(Spawners spawners) {
            return spawners.type;
        }
    }

    public static class Builder {
        private final Map<EntityClassification, List<Spawners>> spawners = Stream.of(EntityClassification.values()).collect(ImmutableMap.toImmutableMap(Builder::lambda$new$0, Builder::lambda$new$1));
        private final Map<EntityType<?>, SpawnCosts> spawnCosts = Maps.newLinkedHashMap();
        private float creatureSpawnProbability = 0.1f;
        private boolean validSpawnBiomeForPlayer;

        public Builder withSpawner(EntityClassification entityClassification, Spawners spawners) {
            this.spawners.get(entityClassification).add(spawners);
            return this;
        }

        public Builder withSpawnCost(EntityType<?> entityType, double d, double d2) {
            this.spawnCosts.put(entityType, new SpawnCosts(d2, d));
            return this;
        }

        public Builder withCreatureSpawnProbability(float f) {
            this.creatureSpawnProbability = f;
            return this;
        }

        public Builder isValidSpawnBiomeForPlayer() {
            this.validSpawnBiomeForPlayer = true;
            return this;
        }

        public MobSpawnInfo copy() {
            return new MobSpawnInfo(this.creatureSpawnProbability, (Map<EntityClassification, List<Spawners>>)this.spawners.entrySet().stream().collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Builder::lambda$copy$2)), ImmutableMap.copyOf(this.spawnCosts), this.validSpawnBiomeForPlayer);
        }

        private static List lambda$copy$2(Map.Entry entry) {
            return ImmutableList.copyOf((Collection)entry.getValue());
        }

        private static List lambda$new$1(EntityClassification entityClassification) {
            return Lists.newArrayList();
        }

        private static EntityClassification lambda$new$0(EntityClassification entityClassification) {
            return entityClassification;
        }
    }
}

