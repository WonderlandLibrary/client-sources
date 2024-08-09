/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

@Beta
@GwtCompatible
public abstract class MultimapBuilder<K0, V0> {
    private static final int DEFAULT_EXPECTED_KEYS = 8;

    private MultimapBuilder() {
    }

    public static MultimapBuilderWithKeys<Object> hashKeys() {
        return MultimapBuilder.hashKeys(8);
    }

    public static MultimapBuilderWithKeys<Object> hashKeys(int n) {
        CollectPreconditions.checkNonnegative(n, "expectedKeys");
        return new MultimapBuilderWithKeys<Object>(n){
            final int val$expectedKeys;
            {
                this.val$expectedKeys = n;
            }

            @Override
            <K, V> Map<K, Collection<V>> createMap() {
                return Maps.newHashMapWithExpectedSize(this.val$expectedKeys);
            }
        };
    }

    public static MultimapBuilderWithKeys<Object> linkedHashKeys() {
        return MultimapBuilder.linkedHashKeys(8);
    }

    public static MultimapBuilderWithKeys<Object> linkedHashKeys(int n) {
        CollectPreconditions.checkNonnegative(n, "expectedKeys");
        return new MultimapBuilderWithKeys<Object>(n){
            final int val$expectedKeys;
            {
                this.val$expectedKeys = n;
            }

            @Override
            <K, V> Map<K, Collection<V>> createMap() {
                return Maps.newLinkedHashMapWithExpectedSize(this.val$expectedKeys);
            }
        };
    }

    public static MultimapBuilderWithKeys<Comparable> treeKeys() {
        return MultimapBuilder.treeKeys(Ordering.natural());
    }

    public static <K0> MultimapBuilderWithKeys<K0> treeKeys(Comparator<K0> comparator) {
        Preconditions.checkNotNull(comparator);
        return new MultimapBuilderWithKeys<K0>(comparator){
            final Comparator val$comparator;
            {
                this.val$comparator = comparator;
            }

            @Override
            <K extends K0, V> Map<K, Collection<V>> createMap() {
                return new TreeMap(this.val$comparator);
            }
        };
    }

    public static <K0 extends Enum<K0>> MultimapBuilderWithKeys<K0> enumKeys(Class<K0> clazz) {
        Preconditions.checkNotNull(clazz);
        return new MultimapBuilderWithKeys<K0>(clazz){
            final Class val$keyClass;
            {
                this.val$keyClass = clazz;
            }

            @Override
            <K extends K0, V> Map<K, Collection<V>> createMap() {
                return new EnumMap(this.val$keyClass);
            }
        };
    }

    public abstract <K extends K0, V extends V0> Multimap<K, V> build();

    public <K extends K0, V extends V0> Multimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
        Multimap<? extends K, ? extends V> multimap2 = this.build();
        multimap2.putAll(multimap);
        return multimap2;
    }

    MultimapBuilder(1 var1_1) {
        this();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static abstract class SortedSetMultimapBuilder<K0, V0>
    extends SetMultimapBuilder<K0, V0> {
        SortedSetMultimapBuilder() {
        }

        @Override
        public abstract <K extends K0, V extends V0> SortedSetMultimap<K, V> build();

        @Override
        public <K extends K0, V extends V0> SortedSetMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
            return (SortedSetMultimap)super.build((Multimap)multimap);
        }

        @Override
        public SetMultimap build(Multimap multimap) {
            return this.build(multimap);
        }

        @Override
        public SetMultimap build() {
            return this.build();
        }

        @Override
        public Multimap build(Multimap multimap) {
            return this.build(multimap);
        }

        @Override
        public Multimap build() {
            return this.build();
        }
    }

    public static abstract class SetMultimapBuilder<K0, V0>
    extends MultimapBuilder<K0, V0> {
        SetMultimapBuilder() {
            super(null);
        }

        @Override
        public abstract <K extends K0, V extends V0> SetMultimap<K, V> build();

        @Override
        public <K extends K0, V extends V0> SetMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
            return (SetMultimap)super.build(multimap);
        }

        @Override
        public Multimap build(Multimap multimap) {
            return this.build(multimap);
        }

        @Override
        public Multimap build() {
            return this.build();
        }
    }

    public static abstract class ListMultimapBuilder<K0, V0>
    extends MultimapBuilder<K0, V0> {
        ListMultimapBuilder() {
            super(null);
        }

        @Override
        public abstract <K extends K0, V extends V0> ListMultimap<K, V> build();

        @Override
        public <K extends K0, V extends V0> ListMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
            return (ListMultimap)super.build(multimap);
        }

        @Override
        public Multimap build(Multimap multimap) {
            return this.build(multimap);
        }

        @Override
        public Multimap build() {
            return this.build();
        }
    }

    public static abstract class MultimapBuilderWithKeys<K0> {
        private static final int DEFAULT_EXPECTED_VALUES_PER_KEY = 2;

        MultimapBuilderWithKeys() {
        }

        abstract <K extends K0, V> Map<K, Collection<V>> createMap();

        public ListMultimapBuilder<K0, Object> arrayListValues() {
            return this.arrayListValues(2);
        }

        public ListMultimapBuilder<K0, Object> arrayListValues(int n) {
            CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
            return new ListMultimapBuilder<K0, Object>(this, n){
                final int val$expectedValuesPerKey;
                final MultimapBuilderWithKeys this$0;
                {
                    this.this$0 = multimapBuilderWithKeys;
                    this.val$expectedValuesPerKey = n;
                }

                @Override
                public <K extends K0, V> ListMultimap<K, V> build() {
                    return Multimaps.newListMultimap(this.this$0.createMap(), new ArrayListSupplier(this.val$expectedValuesPerKey));
                }

                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }

        public ListMultimapBuilder<K0, Object> linkedListValues() {
            return new ListMultimapBuilder<K0, Object>(this){
                final MultimapBuilderWithKeys this$0;
                {
                    this.this$0 = multimapBuilderWithKeys;
                }

                @Override
                public <K extends K0, V> ListMultimap<K, V> build() {
                    return Multimaps.newListMultimap(this.this$0.createMap(), LinkedListSupplier.instance());
                }

                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }

        public SetMultimapBuilder<K0, Object> hashSetValues() {
            return this.hashSetValues(2);
        }

        public SetMultimapBuilder<K0, Object> hashSetValues(int n) {
            CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
            return new SetMultimapBuilder<K0, Object>(this, n){
                final int val$expectedValuesPerKey;
                final MultimapBuilderWithKeys this$0;
                {
                    this.this$0 = multimapBuilderWithKeys;
                    this.val$expectedValuesPerKey = n;
                }

                @Override
                public <K extends K0, V> SetMultimap<K, V> build() {
                    return Multimaps.newSetMultimap(this.this$0.createMap(), new HashSetSupplier(this.val$expectedValuesPerKey));
                }

                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }

        public SetMultimapBuilder<K0, Object> linkedHashSetValues() {
            return this.linkedHashSetValues(2);
        }

        public SetMultimapBuilder<K0, Object> linkedHashSetValues(int n) {
            CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
            return new SetMultimapBuilder<K0, Object>(this, n){
                final int val$expectedValuesPerKey;
                final MultimapBuilderWithKeys this$0;
                {
                    this.this$0 = multimapBuilderWithKeys;
                    this.val$expectedValuesPerKey = n;
                }

                @Override
                public <K extends K0, V> SetMultimap<K, V> build() {
                    return Multimaps.newSetMultimap(this.this$0.createMap(), new LinkedHashSetSupplier(this.val$expectedValuesPerKey));
                }

                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }

        public SortedSetMultimapBuilder<K0, Comparable> treeSetValues() {
            return this.treeSetValues(Ordering.natural());
        }

        public <V0> SortedSetMultimapBuilder<K0, V0> treeSetValues(Comparator<V0> comparator) {
            Preconditions.checkNotNull(comparator, "comparator");
            return new SortedSetMultimapBuilder<K0, V0>(this, comparator){
                final Comparator val$comparator;
                final MultimapBuilderWithKeys this$0;
                {
                    this.this$0 = multimapBuilderWithKeys;
                    this.val$comparator = comparator;
                }

                @Override
                public <K extends K0, V extends V0> SortedSetMultimap<K, V> build() {
                    return Multimaps.newSortedSetMultimap(this.this$0.createMap(), new TreeSetSupplier(this.val$comparator));
                }

                @Override
                public SetMultimap build() {
                    return this.build();
                }

                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }

        public <V0 extends Enum<V0>> SetMultimapBuilder<K0, V0> enumSetValues(Class<V0> clazz) {
            Preconditions.checkNotNull(clazz, "valueClass");
            return new SetMultimapBuilder<K0, V0>(this, clazz){
                final Class val$valueClass;
                final MultimapBuilderWithKeys this$0;
                {
                    this.this$0 = multimapBuilderWithKeys;
                    this.val$valueClass = clazz;
                }

                @Override
                public <K extends K0, V extends V0> SetMultimap<K, V> build() {
                    EnumSetSupplier enumSetSupplier = new EnumSetSupplier(this.val$valueClass);
                    return Multimaps.newSetMultimap(this.this$0.createMap(), enumSetSupplier);
                }

                @Override
                public Multimap build() {
                    return this.build();
                }
            };
        }
    }

    private static final class EnumSetSupplier<V extends Enum<V>>
    implements Supplier<Set<V>>,
    Serializable {
        private final Class<V> clazz;

        EnumSetSupplier(Class<V> clazz) {
            this.clazz = Preconditions.checkNotNull(clazz);
        }

        @Override
        public Set<V> get() {
            return EnumSet.noneOf(this.clazz);
        }

        @Override
        public Object get() {
            return this.get();
        }
    }

    private static final class TreeSetSupplier<V>
    implements Supplier<SortedSet<V>>,
    Serializable {
        private final Comparator<? super V> comparator;

        TreeSetSupplier(Comparator<? super V> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }

        @Override
        public SortedSet<V> get() {
            return new TreeSet<V>(this.comparator);
        }

        @Override
        public Object get() {
            return this.get();
        }
    }

    private static final class LinkedHashSetSupplier<V>
    implements Supplier<Set<V>>,
    Serializable {
        private final int expectedValuesPerKey;

        LinkedHashSetSupplier(int n) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
        }

        @Override
        public Set<V> get() {
            return Sets.newLinkedHashSetWithExpectedSize(this.expectedValuesPerKey);
        }

        @Override
        public Object get() {
            return this.get();
        }
    }

    private static final class HashSetSupplier<V>
    implements Supplier<Set<V>>,
    Serializable {
        private final int expectedValuesPerKey;

        HashSetSupplier(int n) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
        }

        @Override
        public Set<V> get() {
            return Sets.newHashSetWithExpectedSize(this.expectedValuesPerKey);
        }

        @Override
        public Object get() {
            return this.get();
        }
    }

    private static enum LinkedListSupplier implements Supplier<List<Object>>
    {
        INSTANCE;


        public static <V> Supplier<List<V>> instance() {
            LinkedListSupplier linkedListSupplier = INSTANCE;
            return linkedListSupplier;
        }

        @Override
        public List<Object> get() {
            return new LinkedList<Object>();
        }

        @Override
        public Object get() {
            return this.get();
        }
    }

    private static final class ArrayListSupplier<V>
    implements Supplier<List<V>>,
    Serializable {
        private final int expectedValuesPerKey;

        ArrayListSupplier(int n) {
            this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(n, "expectedValuesPerKey");
        }

        @Override
        public List<V> get() {
            return new ArrayList(this.expectedValuesPerKey);
        }

        @Override
        public Object get() {
            return this.get();
        }
    }
}

