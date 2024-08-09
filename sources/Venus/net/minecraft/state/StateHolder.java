/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.state.Property;

public abstract class StateHolder<O, S> {
    private static final Function<Map.Entry<Property<?>, Comparable<?>>, String> field_235890_a_ = new Function<Map.Entry<Property<?>, Comparable<?>>, String>(){

        @Override
        public String apply(@Nullable Map.Entry<Property<?>, Comparable<?>> entry) {
            if (entry == null) {
                return "<NULL>";
            }
            Property<?> property = entry.getKey();
            return property.getName() + "=" + this.func_235905_a_(property, entry.getValue());
        }

        private <T extends Comparable<T>> String func_235905_a_(Property<T> property, Comparable<?> comparable) {
            return property.getName(comparable);
        }

        @Override
        public Object apply(@Nullable Object object) {
            return this.apply((Map.Entry)object);
        }
    };
    protected final O instance;
    private final ImmutableMap<Property<?>, Comparable<?>> properties;
    private Table<Property<?>, Comparable<?>, S> field_235894_e_;
    protected final MapCodec<S> field_235893_d_;

    protected StateHolder(O o, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<S> mapCodec) {
        this.instance = o;
        this.properties = immutableMap;
        this.field_235893_d_ = mapCodec;
    }

    public <T extends Comparable<T>> S func_235896_a_(Property<T> property) {
        return this.with(property, (Comparable)StateHolder.func_235898_a_(property.getAllowedValues(), this.get(property)));
    }

    protected static <T> T func_235898_a_(Collection<T> collection, T t) {
        Iterator<T> iterator2 = collection.iterator();
        while (iterator2.hasNext()) {
            if (!iterator2.next().equals(t)) continue;
            if (iterator2.hasNext()) {
                return iterator2.next();
            }
            return collection.iterator().next();
        }
        return iterator2.next();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.instance);
        if (!this.getValues().isEmpty()) {
            stringBuilder.append('[');
            stringBuilder.append(this.getValues().entrySet().stream().map(field_235890_a_).collect(Collectors.joining(",")));
            stringBuilder.append(']');
        }
        return stringBuilder.toString();
    }

    public Collection<Property> getProperties() {
        return Collections.unmodifiableCollection(this.properties.keySet());
    }

    public <T extends Comparable<T>> boolean hasProperty(Property<T> property) {
        return this.properties.containsKey(property);
    }

    public <T extends Comparable<T>> T get(Property<T> property) {
        Comparable<?> comparable = this.properties.get(property);
        if (comparable == null) {
            throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.instance);
        }
        return (T)((Comparable)property.getValueClass().cast(comparable));
    }

    public <T extends Comparable<T>> Optional<T> func_235903_d_(Property<T> property) {
        Comparable<?> comparable = this.properties.get(property);
        return comparable == null ? Optional.empty() : Optional.of((Comparable)property.getValueClass().cast(comparable));
    }

    public <T extends Comparable<T>, V extends T> S with(Property<T> property, V v) {
        Comparable<?> comparable = this.properties.get(property);
        if (comparable == null) {
            throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.instance);
        }
        if (comparable == v) {
            return (S)this;
        }
        S s = this.field_235894_e_.get(property, v);
        if (s == null) {
            throw new IllegalArgumentException("Cannot set property " + property + " to " + v + " on " + this.instance + ", it is not an allowed value");
        }
        return s;
    }

    public void func_235899_a_(Map<Map<Property<?>, Comparable<?>>, S> map) {
        if (this.field_235894_e_ != null) {
            throw new IllegalStateException();
        }
        HashBasedTable<Property, Comparable, S> hashBasedTable = HashBasedTable.create();
        for (Map.Entry entry : this.properties.entrySet()) {
            Property property = (Property)entry.getKey();
            for (Comparable comparable : property.getAllowedValues()) {
                if (comparable == entry.getValue()) continue;
                hashBasedTable.put(property, comparable, map.get(this.func_235902_b_(property, comparable)));
            }
        }
        this.field_235894_e_ = hashBasedTable.isEmpty() ? hashBasedTable : ArrayTable.create(hashBasedTable);
    }

    private Map<Property<?>, Comparable<?>> func_235902_b_(Property<?> property, Comparable<?> comparable) {
        HashMap<Property<?>, Comparable<?>> hashMap = Maps.newHashMap(this.properties);
        hashMap.put(property, comparable);
        return hashMap;
    }

    public ImmutableMap<Property<?>, Comparable<?>> getValues() {
        return this.properties;
    }

    protected static <O, S extends StateHolder<O, S>> Codec<S> func_235897_a_(Codec<O> codec, Function<O, S> function) {
        return codec.dispatch("Name", StateHolder::lambda$func_235897_a_$0, arg_0 -> StateHolder.lambda$func_235897_a_$1(function, arg_0));
    }

    private static Codec lambda$func_235897_a_$1(Function function, Object object) {
        StateHolder stateHolder = (StateHolder)function.apply(object);
        return stateHolder.getValues().isEmpty() ? Codec.unit(stateHolder) : stateHolder.field_235893_d_.fieldOf("Properties").codec();
    }

    private static Object lambda$func_235897_a_$0(StateHolder stateHolder) {
        return stateHolder.instance;
    }
}

