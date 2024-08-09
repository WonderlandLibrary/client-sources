/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.village;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.Util;
import net.minecraft.village.GossipType;

public class GossipManager {
    private final Map<UUID, Gossips> uuid_gossips_mapping = Maps.newHashMap();

    public void tick() {
        Iterator<Gossips> iterator2 = this.uuid_gossips_mapping.values().iterator();
        while (iterator2.hasNext()) {
            Gossips gossips = iterator2.next();
            gossips.decay();
            if (!gossips.isGossipTypeMapEmpty()) continue;
            iterator2.remove();
        }
    }

    private Stream<GossipEntry> getGossipEntries() {
        return this.uuid_gossips_mapping.entrySet().stream().flatMap(GossipManager::lambda$getGossipEntries$0);
    }

    private Collection<GossipEntry> selectGossipsForTransfer(Random random2, int n) {
        List list = this.getGossipEntries().collect(Collectors.toList());
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        int[] nArray = new int[list.size()];
        int n2 = 0;
        for (int i = 0; i < list.size(); ++i) {
            GossipEntry gossipEntry = (GossipEntry)list.get(i);
            nArray[i] = (n2 += Math.abs(gossipEntry.weightedValue())) - 1;
        }
        Set<GossipEntry> set = Sets.newIdentityHashSet();
        for (int i = 0; i < n; ++i) {
            int n3 = random2.nextInt(n2);
            int n4 = Arrays.binarySearch(nArray, n3);
            set.add((GossipEntry)list.get(n4 < 0 ? -n4 - 1 : n4));
        }
        return set;
    }

    private Gossips getOrCreate(UUID uUID) {
        return this.uuid_gossips_mapping.computeIfAbsent(uUID, GossipManager::lambda$getOrCreate$1);
    }

    public void transferFrom(GossipManager gossipManager, Random random2, int n) {
        Collection<GossipEntry> collection = gossipManager.selectGossipsForTransfer(random2, n);
        collection.forEach(this::lambda$transferFrom$2);
    }

    public int getReputation(UUID uUID, Predicate<GossipType> predicate) {
        Gossips gossips = this.uuid_gossips_mapping.get(uUID);
        return gossips != null ? gossips.weightedValue(predicate) : 0;
    }

    public void add(UUID uUID, GossipType gossipType, int n) {
        Gossips gossips = this.getOrCreate(uUID);
        gossips.gossipTypeMap.mergeInt(gossipType, n, (arg_0, arg_1) -> this.lambda$add$3(gossipType, arg_0, arg_1));
        gossips.putGossipType(gossipType);
        if (gossips.isGossipTypeMapEmpty()) {
            this.uuid_gossips_mapping.remove(uUID);
        }
    }

    public <T> Dynamic<T> write(DynamicOps<T> dynamicOps) {
        return new Dynamic<Object>(dynamicOps, dynamicOps.createList(this.getGossipEntries().map(arg_0 -> GossipManager.lambda$write$4(dynamicOps, arg_0)).map(Dynamic::getValue)));
    }

    public void read(Dynamic<?> dynamic) {
        dynamic.asStream().map(GossipEntry::read).flatMap(GossipManager::lambda$read$5).forEach(this::lambda$read$6);
    }

    private static int getMax(int n, int n2) {
        return Math.max(n, n2);
    }

    private int mergeValuesForAddition(GossipType gossipType, int n, int n2) {
        int n3 = n + n2;
        return n3 > gossipType.max ? Math.max(gossipType.max, n) : n3;
    }

    private void lambda$read$6(GossipEntry gossipEntry) {
        this.getOrCreate((UUID)gossipEntry.target).gossipTypeMap.put(gossipEntry.type, gossipEntry.value);
    }

    private static Stream lambda$read$5(DataResult dataResult) {
        return Util.streamOptional(dataResult.result());
    }

    private static Dynamic lambda$write$4(DynamicOps dynamicOps, GossipEntry gossipEntry) {
        return gossipEntry.write(dynamicOps);
    }

    private Integer lambda$add$3(GossipType gossipType, Integer n, Integer n2) {
        return this.mergeValuesForAddition(gossipType, n, n2);
    }

    private void lambda$transferFrom$2(GossipEntry gossipEntry) {
        int n = gossipEntry.value - gossipEntry.type.decayPerTransfer;
        if (n >= 2) {
            this.getOrCreate((UUID)gossipEntry.target).gossipTypeMap.mergeInt(gossipEntry.type, n, GossipManager::getMax);
        }
    }

    private static Gossips lambda$getOrCreate$1(UUID uUID) {
        return new Gossips();
    }

    private static Stream lambda$getGossipEntries$0(Map.Entry entry) {
        return ((Gossips)entry.getValue()).unpack((UUID)entry.getKey());
    }

    static class Gossips {
        private final Object2IntMap<GossipType> gossipTypeMap = new Object2IntOpenHashMap<GossipType>();

        private Gossips() {
        }

        public int weightedValue(Predicate<GossipType> predicate) {
            return this.gossipTypeMap.object2IntEntrySet().stream().filter(arg_0 -> Gossips.lambda$weightedValue$0(predicate, arg_0)).mapToInt(Gossips::lambda$weightedValue$1).sum();
        }

        public Stream<GossipEntry> unpack(UUID uUID) {
            return this.gossipTypeMap.object2IntEntrySet().stream().map(arg_0 -> Gossips.lambda$unpack$2(uUID, arg_0));
        }

        public void decay() {
            Iterator iterator2 = this.gossipTypeMap.object2IntEntrySet().iterator();
            while (iterator2.hasNext()) {
                Object2IntMap.Entry entry = (Object2IntMap.Entry)iterator2.next();
                int n = entry.getIntValue() - ((GossipType)((Object)entry.getKey())).decayPerDay;
                if (n < 2) {
                    iterator2.remove();
                    continue;
                }
                entry.setValue(n);
            }
        }

        public boolean isGossipTypeMapEmpty() {
            return this.gossipTypeMap.isEmpty();
        }

        public void putGossipType(GossipType gossipType) {
            int n = this.gossipTypeMap.getInt((Object)gossipType);
            if (n > gossipType.max) {
                this.gossipTypeMap.put(gossipType, gossipType.max);
            }
            if (n < 2) {
                this.removeGossipType(gossipType);
            }
        }

        public void removeGossipType(GossipType gossipType) {
            this.gossipTypeMap.removeInt((Object)gossipType);
        }

        private static GossipEntry lambda$unpack$2(UUID uUID, Object2IntMap.Entry entry) {
            return new GossipEntry(uUID, (GossipType)((Object)entry.getKey()), entry.getIntValue());
        }

        private static int lambda$weightedValue$1(Object2IntMap.Entry entry) {
            return entry.getIntValue() * ((GossipType)((Object)entry.getKey())).weight;
        }

        private static boolean lambda$weightedValue$0(Predicate predicate, Object2IntMap.Entry entry) {
            return predicate.test((GossipType)((Object)entry.getKey()));
        }
    }

    static class GossipEntry {
        public final UUID target;
        public final GossipType type;
        public final int value;

        public GossipEntry(UUID uUID, GossipType gossipType, int n) {
            this.target = uUID;
            this.type = gossipType;
            this.value = n;
        }

        public int weightedValue() {
            return this.value * this.type.weight;
        }

        public String toString() {
            return "GossipEntry{target=" + this.target + ", type=" + this.type + ", value=" + this.value + "}";
        }

        public <T> Dynamic<T> write(DynamicOps<T> dynamicOps) {
            return new Dynamic<T>(dynamicOps, dynamicOps.createMap(ImmutableMap.of(dynamicOps.createString("Target"), UUIDCodec.CODEC.encodeStart(dynamicOps, this.target).result().orElseThrow(RuntimeException::new), dynamicOps.createString("Type"), dynamicOps.createString(this.type.id), dynamicOps.createString("Value"), dynamicOps.createInt(this.value))));
        }

        public static DataResult<GossipEntry> read(Dynamic<?> dynamic) {
            return DataResult.unbox(DataResult.instance().group(dynamic.get("Target").read(UUIDCodec.CODEC), dynamic.get("Type").asString().map(GossipType::byId), dynamic.get("Value").asNumber().map(Number::intValue)).apply(DataResult.instance(), GossipEntry::new));
        }
    }
}

