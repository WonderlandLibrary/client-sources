/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.state;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.state.Property;
import net.minecraft.state.StateHolder;

public class StateContainer<O, S extends StateHolder<O, S>> {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
    private final O owner;
    private final ImmutableSortedMap<String, Property<?>> properties;
    private final ImmutableList<S> validStates;

    protected StateContainer(Function<O, S> function, O o, IFactory<O, S> iFactory, Map<String, Property<?>> map) {
        Object object22;
        this.owner = o;
        this.properties = ImmutableSortedMap.copyOf(map);
        Supplier<StateHolder> supplier = () -> StateContainer.lambda$new$0(function, o);
        MapCodec<StateHolder> mapCodec = MapCodec.of(Encoder.empty(), Decoder.unit(supplier));
        for (Object object22 : this.properties.entrySet()) {
            mapCodec = StateContainer.func_241487_a_(mapCodec, supplier, (String)object22.getKey(), (Property)object22.getValue());
        }
        MapCodec<StateHolder> mapCodec2 = mapCodec;
        object22 = Maps.newLinkedHashMap();
        ArrayList<Object> arrayList = Lists.newArrayList();
        Stream<List<List<Object>>> stream = Stream.of(Collections.emptyList());
        for (Object object3 : this.properties.values()) {
            stream = stream.flatMap(arg_0 -> StateContainer.lambda$new$2((Property)object3, arg_0));
        }
        stream.forEach(arg_0 -> StateContainer.lambda$new$3(iFactory, o, mapCodec2, (Map)object22, arrayList, arg_0));
        for (Object object3 : arrayList) {
            ((StateHolder)object3).func_235899_a_(object22);
        }
        this.validStates = ImmutableList.copyOf(arrayList);
    }

    private static <S extends StateHolder<?, S>, T extends Comparable<T>> MapCodec<S> func_241487_a_(MapCodec<S> mapCodec, Supplier<S> supplier, String string, Property<T> property) {
        return Codec.mapPair(mapCodec, ((MapCodec)property.func_241492_e_().fieldOf(string)).setPartial(() -> StateContainer.lambda$func_241487_a_$4(property, supplier))).xmap(arg_0 -> StateContainer.lambda$func_241487_a_$5(property, arg_0), arg_0 -> StateContainer.lambda$func_241487_a_$6(property, arg_0));
    }

    public ImmutableList<S> getValidStates() {
        return this.validStates;
    }

    public S getBaseState() {
        return (S)((StateHolder)this.validStates.get(0));
    }

    public O getOwner() {
        return this.owner;
    }

    public Collection<Property<?>> getProperties() {
        return this.properties.values();
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("block", this.owner).add("properties", this.properties.values().stream().map(Property::getName).collect(Collectors.toList())).toString();
    }

    @Nullable
    public Property<?> getProperty(String string) {
        return this.properties.get(string);
    }

    private static Pair lambda$func_241487_a_$6(Property property, StateHolder stateHolder) {
        return Pair.of(stateHolder, property.func_241489_a_(stateHolder));
    }

    private static StateHolder lambda$func_241487_a_$5(Property property, Pair pair) {
        return (StateHolder)((StateHolder)pair.getFirst()).with(property, ((Property.ValuePair)pair.getSecond()).func_241493_b_());
    }

    private static Property.ValuePair lambda$func_241487_a_$4(Property property, Supplier supplier) {
        return property.func_241489_a_((StateHolder)supplier.get());
    }

    private static void lambda$new$3(IFactory iFactory, Object object, MapCodec mapCodec, Map map, List list, List list2) {
        ImmutableMap<Property<?>, Comparable<?>> immutableMap = list2.stream().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
        StateHolder stateHolder = (StateHolder)iFactory.create(object, immutableMap, mapCodec);
        map.put(immutableMap, stateHolder);
        list.add(stateHolder);
    }

    private static Stream lambda$new$2(Property property, List list) {
        return property.getAllowedValues().stream().map(arg_0 -> StateContainer.lambda$new$1(list, property, arg_0));
    }

    private static List lambda$new$1(List list, Property property, Comparable comparable) {
        ArrayList<Pair<Property, Comparable>> arrayList = Lists.newArrayList(list);
        arrayList.add(Pair.of(property, comparable));
        return arrayList;
    }

    private static StateHolder lambda$new$0(Function function, Object object) {
        return (StateHolder)function.apply(object);
    }

    public static interface IFactory<O, S> {
        public S create(O var1, ImmutableMap<Property<?>, Comparable<?>> var2, MapCodec<S> var3);
    }

    public static class Builder<O, S extends StateHolder<O, S>> {
        private final O owner;
        private final Map<String, Property<?>> properties = Maps.newHashMap();

        public Builder(O o) {
            this.owner = o;
        }

        public Builder<O, S> add(Property<?> ... propertyArray) {
            for (Property<?> property : propertyArray) {
                this.validateProperty(property);
                this.properties.put(property.getName(), property);
            }
            return this;
        }

        private <T extends Comparable<T>> void validateProperty(Property<T> property) {
            String string = property.getName();
            if (!NAME_PATTERN.matcher(string).matches()) {
                throw new IllegalArgumentException(this.owner + " has invalidly named property: " + string);
            }
            Collection<T> collection = property.getAllowedValues();
            if (collection.size() <= 1) {
                throw new IllegalArgumentException(this.owner + " attempted use property " + string + " with <= 1 possible values");
            }
            for (Comparable comparable : collection) {
                String string2 = property.getName(comparable);
                if (NAME_PATTERN.matcher(string2).matches()) continue;
                throw new IllegalArgumentException(this.owner + " has property: " + string + " with invalidly named value: " + string2);
            }
            if (this.properties.containsKey(string)) {
                throw new IllegalArgumentException(this.owner + " has duplicate property: " + string);
            }
        }

        public StateContainer<O, S> func_235882_a_(Function<O, S> function, IFactory<O, S> iFactory) {
            return new StateContainer<O, S>(function, this.owner, iFactory, this.properties);
        }
    }
}

