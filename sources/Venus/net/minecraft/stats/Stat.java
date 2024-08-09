/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.stats;

import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.scoreboard.ScoreCriteria;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class Stat<T>
extends ScoreCriteria {
    private final IStatFormatter formatter;
    private final T value;
    private final StatType<T> type;

    protected Stat(StatType<T> statType, T t, IStatFormatter iStatFormatter) {
        super(Stat.buildName(statType, t));
        this.type = statType;
        this.formatter = iStatFormatter;
        this.value = t;
    }

    public static <T> String buildName(StatType<T> statType, T t) {
        return Stat.locationToKey(Registry.STATS.getKey(statType)) + ":" + Stat.locationToKey(statType.getRegistry().getKey(t));
    }

    private static <T> String locationToKey(@Nullable ResourceLocation resourceLocation) {
        return resourceLocation.toString().replace(':', '.');
    }

    public StatType<T> getType() {
        return this.type;
    }

    public T getValue() {
        return this.value;
    }

    public String format(int n) {
        return this.formatter.format(n);
    }

    public boolean equals(Object object) {
        return this == object || object instanceof Stat && Objects.equals(this.getName(), ((Stat)object).getName());
    }

    public int hashCode() {
        return this.getName().hashCode();
    }

    public String toString() {
        return "Stat{name=" + this.getName() + ", formatter=" + this.formatter + "}";
    }
}

