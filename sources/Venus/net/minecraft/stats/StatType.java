/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.stats;

import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stat;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class StatType<T>
implements Iterable<Stat<T>> {
    private final Registry<T> registry;
    private final Map<T, Stat<T>> map = new IdentityHashMap<T, Stat<T>>();
    @Nullable
    private ITextComponent field_242169_c;

    public StatType(Registry<T> registry) {
        this.registry = registry;
    }

    public boolean contains(T t) {
        return this.map.containsKey(t);
    }

    public Stat<T> get(T t, IStatFormatter iStatFormatter) {
        return this.map.computeIfAbsent(t, arg_0 -> this.lambda$get$0(iStatFormatter, arg_0));
    }

    public Registry<T> getRegistry() {
        return this.registry;
    }

    @Override
    public Iterator<Stat<T>> iterator() {
        return this.map.values().iterator();
    }

    public Stat<T> get(T t) {
        return this.get(t, IStatFormatter.DEFAULT);
    }

    public String getTranslationKey() {
        return "stat_type." + Registry.STATS.getKey(this).toString().replace(':', '.');
    }

    public ITextComponent func_242170_d() {
        if (this.field_242169_c == null) {
            this.field_242169_c = new TranslationTextComponent(this.getTranslationKey());
        }
        return this.field_242169_c;
    }

    private Stat lambda$get$0(IStatFormatter iStatFormatter, Object object) {
        return new Stat<Object>(this, object, iStatFormatter);
    }
}

