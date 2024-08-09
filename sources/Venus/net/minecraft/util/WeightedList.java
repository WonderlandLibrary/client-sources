/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class WeightedList<U> {
    protected final List<Entry<U>> field_220658_a;
    private final Random random = new Random();

    public WeightedList() {
        this(Lists.newArrayList());
    }

    private WeightedList(List<Entry<U>> list) {
        this.field_220658_a = Lists.newArrayList(list);
    }

    public static <U> Codec<WeightedList<U>> func_234002_a_(Codec<U> codec) {
        return Entry.func_234008_a_(codec).listOf().xmap(WeightedList::new, WeightedList::lambda$func_234002_a_$0);
    }

    public WeightedList<U> func_226313_a_(U u, int n) {
        this.field_220658_a.add(new Entry<U>(u, n));
        return this;
    }

    public WeightedList<U> func_226309_a_() {
        return this.func_226314_a_(this.random);
    }

    public WeightedList<U> func_226314_a_(Random random2) {
        this.field_220658_a.forEach(arg_0 -> WeightedList.lambda$func_226314_a_$1(random2, arg_0));
        this.field_220658_a.sort(Comparator.comparingDouble(WeightedList::lambda$func_226314_a_$2));
        return this;
    }

    public boolean func_234005_b_() {
        return this.field_220658_a.isEmpty();
    }

    public Stream<U> func_220655_b() {
        return this.field_220658_a.stream().map(Entry::func_220647_b);
    }

    public U func_226318_b_(Random random2) {
        return this.func_226314_a_(random2).func_220655_b().findFirst().orElseThrow(RuntimeException::new);
    }

    public String toString() {
        return "WeightedList[" + this.field_220658_a + "]";
    }

    private static double lambda$func_226314_a_$2(Entry entry) {
        return entry.func_220649_a();
    }

    private static void lambda$func_226314_a_$1(Random random2, Entry entry) {
        entry.func_220648_a(random2.nextFloat());
    }

    private static List lambda$func_234002_a_$0(WeightedList weightedList) {
        return weightedList.field_220658_a;
    }

    public static class Entry<T> {
        private final T field_220651_b;
        private final int field_220652_c;
        private double field_220653_d;

        private Entry(T t, int n) {
            this.field_220652_c = n;
            this.field_220651_b = t;
        }

        private double func_220649_a() {
            return this.field_220653_d;
        }

        private void func_220648_a(float f) {
            this.field_220653_d = -Math.pow(f, 1.0f / (float)this.field_220652_c);
        }

        public T func_220647_b() {
            return this.field_220651_b;
        }

        public String toString() {
            return this.field_220652_c + ":" + this.field_220651_b;
        }

        public static <E> Codec<Entry<E>> func_234008_a_(Codec<E> codec) {
            return new Codec<Entry<E>>(codec){
                final Codec val$p_234008_0_;
                {
                    this.val$p_234008_0_ = codec;
                }

                @Override
                public <T> DataResult<Pair<Entry<E>, T>> decode(DynamicOps<T> dynamicOps, T t) {
                    Dynamic<T> dynamic = new Dynamic<T>(dynamicOps, t);
                    return dynamic.get("data").flatMap(this.val$p_234008_0_::parse).map(arg_0 -> 1.lambda$decode$0(dynamic, arg_0)).map(arg_0 -> 1.lambda$decode$1(dynamicOps, arg_0));
                }

                @Override
                public <T> DataResult<T> encode(Entry<E> entry, DynamicOps<T> dynamicOps, T t) {
                    return dynamicOps.mapBuilder().add("weight", dynamicOps.createInt(entry.field_220652_c)).add("data", this.val$p_234008_0_.encodeStart(dynamicOps, entry.field_220651_b)).build(t);
                }

                @Override
                public DataResult encode(Object object, DynamicOps dynamicOps, Object object2) {
                    return this.encode((Entry)object, dynamicOps, object2);
                }

                private static Pair lambda$decode$1(DynamicOps dynamicOps, Entry entry) {
                    return Pair.of(entry, dynamicOps.empty());
                }

                private static Entry lambda$decode$0(Dynamic dynamic, Object object) {
                    return new Entry<Object>(object, dynamic.get("weight").asInt(1));
                }
            };
        }
    }
}

