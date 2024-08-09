/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.stats;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;

public class StatisticsManager {
    protected final Object2IntMap<Stat<?>> statsData = Object2IntMaps.synchronize(new Object2IntOpenHashMap());

    public StatisticsManager() {
        this.statsData.defaultReturnValue(0);
    }

    public void increment(PlayerEntity playerEntity, Stat<?> stat, int n) {
        int n2 = (int)Math.min((long)this.getValue(stat) + (long)n, Integer.MAX_VALUE);
        this.setValue(playerEntity, stat, n2);
    }

    public void setValue(PlayerEntity playerEntity, Stat<?> stat, int n) {
        this.statsData.put(stat, n);
    }

    public <T> int getValue(StatType<T> statType, T t) {
        return statType.contains(t) ? this.getValue(statType.get(t)) : 0;
    }

    public int getValue(Stat<?> stat) {
        return this.statsData.getInt(stat);
    }
}

