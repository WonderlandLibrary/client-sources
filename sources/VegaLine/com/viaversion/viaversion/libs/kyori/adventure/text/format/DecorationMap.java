/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

@Unmodifiable
final class DecorationMap
extends AbstractMap<TextDecoration, TextDecoration.State>
implements Examinable {
    static final TextDecoration[] DECORATIONS = TextDecoration.values();
    private static final TextDecoration.State[] STATES = TextDecoration.State.values();
    private static final int MAP_SIZE = DECORATIONS.length;
    private static final TextDecoration.State[] EMPTY_STATE_ARRAY = new TextDecoration.State[0];
    static final DecorationMap EMPTY = new DecorationMap(0);
    private static final KeySet KEY_SET = new KeySet();
    private final int bitSet;
    private volatile EntrySet entrySet = null;
    private volatile Values values = null;

    static DecorationMap fromMap(Map<TextDecoration, TextDecoration.State> decorationMap) {
        if (decorationMap instanceof DecorationMap) {
            return (DecorationMap)decorationMap;
        }
        int bitSet = 0;
        for (TextDecoration decoration : DECORATIONS) {
            bitSet |= decorationMap.getOrDefault(decoration, TextDecoration.State.NOT_SET).ordinal() * DecorationMap.offset(decoration);
        }
        return DecorationMap.withBitSet(bitSet);
    }

    static DecorationMap merge(Map<TextDecoration, TextDecoration.State> first, Map<TextDecoration, TextDecoration.State> second) {
        int bitSet = 0;
        for (TextDecoration decoration : DECORATIONS) {
            bitSet |= first.getOrDefault(decoration, second.getOrDefault(decoration, TextDecoration.State.NOT_SET)).ordinal() * DecorationMap.offset(decoration);
        }
        return DecorationMap.withBitSet(bitSet);
    }

    private static DecorationMap withBitSet(int bitSet) {
        return bitSet == 0 ? EMPTY : new DecorationMap(bitSet);
    }

    private static int offset(TextDecoration decoration) {
        return 1 << decoration.ordinal() * 2;
    }

    private DecorationMap(int bitSet) {
        this.bitSet = bitSet;
    }

    @NotNull
    public DecorationMap with(@NotNull TextDecoration decoration, @NotNull TextDecoration.State state) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(decoration, "decoration");
        int offset = DecorationMap.offset(decoration);
        return DecorationMap.withBitSet(this.bitSet & ~(3 * offset) | state.ordinal() * offset);
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Arrays.stream(DECORATIONS).map(decoration -> ExaminableProperty.of(decoration.toString(), (Object)this.get(decoration)));
    }

    @Override
    public TextDecoration.State get(Object o) {
        if (o instanceof TextDecoration) {
            return STATES[this.bitSet >> ((TextDecoration)o).ordinal() * 2 & 3];
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return key instanceof TextDecoration;
    }

    @Override
    public int size() {
        return MAP_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @NotNull
    public Set<Map.Entry<TextDecoration, TextDecoration.State>> entrySet() {
        if (this.entrySet == null) {
            DecorationMap decorationMap = this;
            synchronized (decorationMap) {
                if (this.entrySet == null) {
                    this.entrySet = new EntrySet();
                }
            }
        }
        return this.entrySet;
    }

    @Override
    @NotNull
    public Set<TextDecoration> keySet() {
        return KEY_SET;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @NotNull
    public Collection<TextDecoration.State> values() {
        if (this.values == null) {
            DecorationMap decorationMap = this;
            synchronized (decorationMap) {
                if (this.values == null) {
                    this.values = new Values();
                }
            }
        }
        return this.values;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != DecorationMap.class) {
            return false;
        }
        return this.bitSet == ((DecorationMap)other).bitSet;
    }

    @Override
    public int hashCode() {
        return this.bitSet;
    }

    static /* synthetic */ KeySet access$000() {
        return KEY_SET;
    }

    static final class KeySet
    extends AbstractSet<TextDecoration> {
        KeySet() {
        }

        @Override
        public boolean contains(Object o) {
            return o instanceof TextDecoration;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Object @NotNull [] toArray() {
            return Arrays.copyOf(DECORATIONS, MAP_SIZE, Object[].class);
        }

        @Override
        public <T> T @NotNull [] toArray(T @NotNull [] dest) {
            if (dest.length < MAP_SIZE) {
                return Arrays.copyOf(DECORATIONS, MAP_SIZE, dest.getClass());
            }
            System.arraycopy(DECORATIONS, 0, dest, 0, MAP_SIZE);
            if (dest.length > MAP_SIZE) {
                dest[MAP_SIZE] = null;
            }
            return dest;
        }

        @Override
        @NotNull
        public Iterator<TextDecoration> iterator() {
            return Spliterators.iterator(Arrays.spliterator(DECORATIONS));
        }

        @Override
        public int size() {
            return MAP_SIZE;
        }
    }

    final class Values
    extends AbstractCollection<TextDecoration.State> {
        Values() {
        }

        @Override
        @NotNull
        public Iterator<TextDecoration.State> iterator() {
            return Spliterators.iterator(Arrays.spliterator(this.toArray(EMPTY_STATE_ARRAY)));
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Object @NotNull [] toArray() {
            Object[] states = new Object[MAP_SIZE];
            for (int i = 0; i < MAP_SIZE; ++i) {
                states[i] = DecorationMap.this.get(DECORATIONS[i]);
            }
            return states;
        }

        @Override
        public <T> T @NotNull [] toArray(T @NotNull [] dest) {
            if (dest.length < MAP_SIZE) {
                return Arrays.copyOf(this.toArray(), MAP_SIZE, dest.getClass());
            }
            System.arraycopy(this.toArray(), 0, dest, 0, MAP_SIZE);
            if (dest.length > MAP_SIZE) {
                dest[MAP_SIZE] = null;
            }
            return dest;
        }

        @Override
        public boolean contains(Object o) {
            return o instanceof TextDecoration.State && super.contains(o);
        }

        @Override
        public int size() {
            return MAP_SIZE;
        }
    }

    final class EntrySet
    extends AbstractSet<Map.Entry<TextDecoration, TextDecoration.State>> {
        EntrySet() {
        }

        @Override
        @NotNull
        public Iterator<Map.Entry<TextDecoration, TextDecoration.State>> iterator() {
            return new Iterator<Map.Entry<TextDecoration, TextDecoration.State>>(){
                private final Iterator<TextDecoration> decorations = DecorationMap.access$000().iterator();
                private final Iterator<TextDecoration.State> states;
                {
                    this.states = DecorationMap.this.values().iterator();
                }

                @Override
                public boolean hasNext() {
                    return this.decorations.hasNext() && this.states.hasNext();
                }

                @Override
                public Map.Entry<TextDecoration, TextDecoration.State> next() {
                    if (this.hasNext()) {
                        return new AbstractMap.SimpleImmutableEntry<TextDecoration, TextDecoration.State>(this.decorations.next(), this.states.next());
                    }
                    throw new NoSuchElementException();
                }
            };
        }

        @Override
        public int size() {
            return MAP_SIZE;
        }
    }
}

