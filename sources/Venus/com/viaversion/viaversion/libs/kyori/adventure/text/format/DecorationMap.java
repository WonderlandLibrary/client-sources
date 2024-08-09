/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
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

    static DecorationMap fromMap(Map<TextDecoration, TextDecoration.State> map) {
        if (map instanceof DecorationMap) {
            return (DecorationMap)map;
        }
        int n = 0;
        for (TextDecoration textDecoration : DECORATIONS) {
            n |= map.getOrDefault(textDecoration, TextDecoration.State.NOT_SET).ordinal() * DecorationMap.offset(textDecoration);
        }
        return DecorationMap.withBitSet(n);
    }

    static DecorationMap merge(Map<TextDecoration, TextDecoration.State> map, Map<TextDecoration, TextDecoration.State> map2) {
        int n = 0;
        for (TextDecoration textDecoration : DECORATIONS) {
            n |= map.getOrDefault(textDecoration, map2.getOrDefault(textDecoration, TextDecoration.State.NOT_SET)).ordinal() * DecorationMap.offset(textDecoration);
        }
        return DecorationMap.withBitSet(n);
    }

    private static DecorationMap withBitSet(int n) {
        return n == 0 ? EMPTY : new DecorationMap(n);
    }

    private static int offset(TextDecoration textDecoration) {
        return 1 << textDecoration.ordinal() * 2;
    }

    private DecorationMap(int n) {
        this.bitSet = n;
    }

    @NotNull
    public DecorationMap with(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(textDecoration, "decoration");
        int n = DecorationMap.offset(textDecoration);
        return DecorationMap.withBitSet(this.bitSet & ~(3 * n) | state.ordinal() * n);
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Arrays.stream(DECORATIONS).map(this::lambda$examinableProperties$0);
    }

    @Override
    public TextDecoration.State get(Object object) {
        if (object instanceof TextDecoration) {
            return STATES[this.bitSet >> ((TextDecoration)object).ordinal() * 2 & 3];
        }
        return null;
    }

    @Override
    public boolean containsKey(Object object) {
        return object instanceof TextDecoration;
    }

    @Override
    public int size() {
        return MAP_SIZE;
    }

    @Override
    public boolean isEmpty() {
        return true;
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
                    this.entrySet = new EntrySet(this);
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
                    this.values = new Values(this);
                }
            }
        }
        return this.values;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object == null || object.getClass() != DecorationMap.class) {
            return true;
        }
        return this.bitSet == ((DecorationMap)object).bitSet;
    }

    @Override
    public int hashCode() {
        return this.bitSet;
    }

    @Override
    public Object get(Object object) {
        return this.get(object);
    }

    private ExaminableProperty lambda$examinableProperties$0(TextDecoration textDecoration) {
        return ExaminableProperty.of(textDecoration.toString(), (Object)this.get(textDecoration));
    }

    static KeySet access$000() {
        return KEY_SET;
    }

    static int access$100() {
        return MAP_SIZE;
    }

    static TextDecoration.State[] access$200() {
        return EMPTY_STATE_ARRAY;
    }

    static final class KeySet
    extends AbstractSet<TextDecoration> {
        KeySet() {
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof TextDecoration;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Object @NotNull [] toArray() {
            return Arrays.copyOf(DECORATIONS, DecorationMap.access$100(), Object[].class);
        }

        @Override
        public <T> T @NotNull [] toArray(T @NotNull [] TArray) {
            if (TArray.length < DecorationMap.access$100()) {
                return Arrays.copyOf(DECORATIONS, DecorationMap.access$100(), TArray.getClass());
            }
            System.arraycopy(DECORATIONS, 0, TArray, 0, DecorationMap.access$100());
            if (TArray.length > DecorationMap.access$100()) {
                TArray[DecorationMap.access$100()] = null;
            }
            return TArray;
        }

        @Override
        @NotNull
        public Iterator<TextDecoration> iterator() {
            return Spliterators.iterator(Arrays.spliterator(DECORATIONS));
        }

        @Override
        public int size() {
            return DecorationMap.access$100();
        }
    }

    final class Values
    extends AbstractCollection<TextDecoration.State> {
        final DecorationMap this$0;

        Values(DecorationMap decorationMap) {
            this.this$0 = decorationMap;
        }

        @Override
        @NotNull
        public Iterator<TextDecoration.State> iterator() {
            return Spliterators.iterator(Arrays.spliterator(this.toArray(DecorationMap.access$200())));
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Object @NotNull [] toArray() {
            Object[] objectArray = new Object[DecorationMap.access$100()];
            for (int i = 0; i < DecorationMap.access$100(); ++i) {
                objectArray[i] = this.this$0.get(DECORATIONS[i]);
            }
            return objectArray;
        }

        @Override
        public <T> T @NotNull [] toArray(T @NotNull [] TArray) {
            if (TArray.length < DecorationMap.access$100()) {
                return Arrays.copyOf(this.toArray(), DecorationMap.access$100(), TArray.getClass());
            }
            System.arraycopy(this.toArray(), 0, TArray, 0, DecorationMap.access$100());
            if (TArray.length > DecorationMap.access$100()) {
                TArray[DecorationMap.access$100()] = null;
            }
            return TArray;
        }

        @Override
        public boolean contains(Object object) {
            return object instanceof TextDecoration.State && super.contains(object);
        }

        @Override
        public int size() {
            return DecorationMap.access$100();
        }
    }

    final class EntrySet
    extends AbstractSet<Map.Entry<TextDecoration, TextDecoration.State>> {
        final DecorationMap this$0;

        EntrySet(DecorationMap decorationMap) {
            this.this$0 = decorationMap;
        }

        @Override
        @NotNull
        public Iterator<Map.Entry<TextDecoration, TextDecoration.State>> iterator() {
            return new Iterator<Map.Entry<TextDecoration, TextDecoration.State>>(this){
                private final Iterator<TextDecoration> decorations;
                private final Iterator<TextDecoration.State> states;
                final EntrySet this$1;
                {
                    this.this$1 = entrySet;
                    this.decorations = DecorationMap.access$000().iterator();
                    this.states = this.this$1.this$0.values().iterator();
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

                @Override
                public Object next() {
                    return this.next();
                }
            };
        }

        @Override
        public int size() {
            return DecorationMap.access$100();
        }
    }
}

