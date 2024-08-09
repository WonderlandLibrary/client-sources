/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.collections.builders.ListBuilderKt;
import kotlin.collections.builders.MapBuilderEntries;
import kotlin.collections.builders.MapBuilderKeys;
import kotlin.collections.builders.MapBuilderValues;
import kotlin.collections.builders.SerializedMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMutableIterator;
import kotlin.jvm.internal.markers.KMutableMap;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u00a8\u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\b\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010&\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0000\u0018\u0000 }*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u00060\u0004j\u0002`\u0005:\t}~\u007f\u0080\u0001\u0081\u0001\u0082\u0001B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0006B\u000f\b\u0016\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tBE\b\u0002\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b\u0012\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0010\u001a\u00020\b\u0012\u0006\u0010\u0011\u001a\u00020\b\u00a2\u0006\u0002\u0010\u0012J\u0017\u00102\u001a\u00020\b2\u0006\u00103\u001a\u00028\u0000H\u0000\u00a2\u0006\u0004\b4\u00105J\u0013\u00106\u001a\b\u0012\u0004\u0012\u00028\u00010\u000bH\u0002\u00a2\u0006\u0002\u00107J\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000109J\r\u0010:\u001a\u00020;H\u0000\u00a2\u0006\u0002\b<J\b\u0010=\u001a\u00020;H\u0016J\b\u0010>\u001a\u00020;H\u0002J\u0019\u0010?\u001a\u00020!2\n\u0010@\u001a\u0006\u0012\u0002\b\u00030AH\u0000\u00a2\u0006\u0002\bBJ!\u0010C\u001a\u00020!2\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010EH\u0000\u00a2\u0006\u0002\bFJ\u0015\u0010G\u001a\u00020!2\u0006\u00103\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010HJ\u0015\u0010I\u001a\u00020!2\u0006\u0010J\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010HJ\u0018\u0010K\u001a\u00020!2\u000e\u0010L\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u000309H\u0002J\u0010\u0010M\u001a\u00020;2\u0006\u0010\u0013\u001a\u00020\bH\u0002J\u0010\u0010N\u001a\u00020;2\u0006\u0010O\u001a\u00020\bH\u0002J\u0019\u0010P\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010QH\u0000\u00a2\u0006\u0002\bRJ\u0013\u0010S\u001a\u00020!2\b\u0010L\u001a\u0004\u0018\u00010TH\u0096\u0002J\u0015\u0010U\u001a\u00020\b2\u0006\u00103\u001a\u00028\u0000H\u0002\u00a2\u0006\u0002\u00105J\u0015\u0010V\u001a\u00020\b2\u0006\u0010J\u001a\u00028\u0001H\u0002\u00a2\u0006\u0002\u00105J\u0018\u0010W\u001a\u0004\u0018\u00018\u00012\u0006\u00103\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010XJ\u0015\u0010Y\u001a\u00020\b2\u0006\u00103\u001a\u00028\u0000H\u0002\u00a2\u0006\u0002\u00105J\b\u0010Z\u001a\u00020\bH\u0016J\b\u0010[\u001a\u00020!H\u0016J\u0019\u0010\\\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010]H\u0000\u00a2\u0006\u0002\b^J\u001f\u0010_\u001a\u0004\u0018\u00018\u00012\u0006\u00103\u001a\u00028\u00002\u0006\u0010J\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010`J\u001e\u0010a\u001a\u00020;2\u0014\u0010b\u001a\u0010\u0012\u0006\b\u0001\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000109H\u0016J\"\u0010c\u001a\u00020!2\u0018\u0010b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010E0AH\u0002J\u001c\u0010d\u001a\u00020!2\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010EH\u0002J\u0010\u0010e\u001a\u00020!2\u0006\u0010f\u001a\u00020\bH\u0002J\u0010\u0010g\u001a\u00020;2\u0006\u0010h\u001a\u00020\bH\u0002J\u0017\u0010i\u001a\u0004\u0018\u00018\u00012\u0006\u00103\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010XJ!\u0010j\u001a\u00020!2\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010EH\u0000\u00a2\u0006\u0002\bkJ\u0010\u0010l\u001a\u00020;2\u0006\u0010m\u001a\u00020\bH\u0002J\u0017\u0010n\u001a\u00020\b2\u0006\u00103\u001a\u00028\u0000H\u0000\u00a2\u0006\u0004\bo\u00105J\u0010\u0010p\u001a\u00020;2\u0006\u0010q\u001a\u00020\bH\u0002J\u0017\u0010r\u001a\u00020!2\u0006\u0010s\u001a\u00028\u0001H\u0000\u00a2\u0006\u0004\bt\u0010HJ\u0010\u0010u\u001a\u00020!2\u0006\u0010v\u001a\u00020\bH\u0002J\b\u0010w\u001a\u00020xH\u0016J\u0019\u0010y\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010zH\u0000\u00a2\u0006\u0002\b{J\b\u0010|\u001a\u00020TH\u0002R\u0014\u0010\u0013\u001a\u00020\b8@X\u0080\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R&\u0010\u0016\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00180\u00178VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0010\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u0015R\u001e\u0010\"\u001a\u00020!2\u0006\u0010 \u001a\u00020!@BX\u0080\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u001a\u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\u00178VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b&\u0010\u001aR\u0016\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010'R\u0016\u0010(\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010)X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010*\u001a\u00020\b2\u0006\u0010 \u001a\u00020\b@RX\u0096\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u0015R\u001a\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00010-8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u0010/R\u0018\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010'R\u0016\u00100\u001a\n\u0012\u0004\u0012\u00028\u0001\u0018\u000101X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0083\u0001"}, d2={"Lkotlin/collections/builders/MapBuilder;", "K", "V", "", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "initialCapacity", "", "(I)V", "keysArray", "", "valuesArray", "presenceArray", "", "hashArray", "maxProbeDistance", "length", "([Ljava/lang/Object;[Ljava/lang/Object;[I[III)V", "capacity", "getCapacity$kotlin_stdlib", "()I", "entries", "", "", "getEntries", "()Ljava/util/Set;", "entriesView", "Lkotlin/collections/builders/MapBuilderEntries;", "hashShift", "hashSize", "getHashSize", "<set-?>", "", "isReadOnly", "isReadOnly$kotlin_stdlib", "()Z", "keys", "getKeys", "[Ljava/lang/Object;", "keysView", "Lkotlin/collections/builders/MapBuilderKeys;", "size", "getSize", "values", "", "getValues", "()Ljava/util/Collection;", "valuesView", "Lkotlin/collections/builders/MapBuilderValues;", "addKey", "key", "addKey$kotlin_stdlib", "(Ljava/lang/Object;)I", "allocateValuesArray", "()[Ljava/lang/Object;", "build", "", "checkIsMutable", "", "checkIsMutable$kotlin_stdlib", "clear", "compact", "containsAllEntries", "m", "", "containsAllEntries$kotlin_stdlib", "containsEntry", "entry", "", "containsEntry$kotlin_stdlib", "containsKey", "(Ljava/lang/Object;)Z", "containsValue", "value", "contentEquals", "other", "ensureCapacity", "ensureExtraCapacity", "n", "entriesIterator", "Lkotlin/collections/builders/MapBuilder$EntriesItr;", "entriesIterator$kotlin_stdlib", "equals", "", "findKey", "findValue", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "hash", "hashCode", "isEmpty", "keysIterator", "Lkotlin/collections/builders/MapBuilder$KeysItr;", "keysIterator$kotlin_stdlib", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "putAll", "from", "putAllEntries", "putEntry", "putRehash", "i", "rehash", "newHashSize", "remove", "removeEntry", "removeEntry$kotlin_stdlib", "removeHashAt", "removedHash", "removeKey", "removeKey$kotlin_stdlib", "removeKeyAt", "index", "removeValue", "element", "removeValue$kotlin_stdlib", "shouldCompact", "extraCapacity", "toString", "", "valuesIterator", "Lkotlin/collections/builders/MapBuilder$ValuesItr;", "valuesIterator$kotlin_stdlib", "writeReplace", "Companion", "EntriesItr", "EntryRef", "Itr", "KeysItr", "ValuesItr", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nMapBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MapBuilder.kt\nkotlin/collections/builders/MapBuilder\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,697:1\n1#2:698\n*E\n"})
public final class MapBuilder<K, V>
implements Map<K, V>,
Serializable,
KMutableMap {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private K[] keysArray;
    @Nullable
    private V[] valuesArray;
    @NotNull
    private int[] presenceArray;
    @NotNull
    private int[] hashArray;
    private int maxProbeDistance;
    private int length;
    private int hashShift;
    private int size;
    @Nullable
    private MapBuilderKeys<K> keysView;
    @Nullable
    private MapBuilderValues<V> valuesView;
    @Nullable
    private MapBuilderEntries<K, V> entriesView;
    private boolean isReadOnly;
    private static final int MAGIC = -1640531527;
    private static final int INITIAL_CAPACITY = 8;
    private static final int INITIAL_MAX_PROBE_DISTANCE = 2;
    private static final int TOMBSTONE = -1;
    @NotNull
    private static final MapBuilder Empty;

    private MapBuilder(K[] KArray, V[] VArray, int[] nArray, int[] nArray2, int n, int n2) {
        this.keysArray = KArray;
        this.valuesArray = VArray;
        this.presenceArray = nArray;
        this.hashArray = nArray2;
        this.maxProbeDistance = n;
        this.length = n2;
        this.hashShift = kotlin.collections.builders.MapBuilder$Companion.access$computeShift(Companion, this.getHashSize());
    }

    public int getSize() {
        return this.size;
    }

    public final boolean isReadOnly$kotlin_stdlib() {
        return this.isReadOnly;
    }

    public MapBuilder() {
        this(8);
    }

    public MapBuilder(int n) {
        this(ListBuilderKt.arrayOfUninitializedElements(n), null, new int[n], new int[kotlin.collections.builders.MapBuilder$Companion.access$computeHashSize(Companion, n)], 2, 0);
    }

    @NotNull
    public final Map<K, V> build() {
        Map map;
        this.checkIsMutable$kotlin_stdlib();
        this.isReadOnly = true;
        if (this.size() > 0) {
            map = this;
        } else {
            MapBuilder mapBuilder = Empty;
            Intrinsics.checkNotNull(mapBuilder, "null cannot be cast to non-null type kotlin.collections.Map<K of kotlin.collections.builders.MapBuilder, V of kotlin.collections.builders.MapBuilder>");
            map = mapBuilder;
        }
        return map;
    }

    private final Object writeReplace() {
        if (!this.isReadOnly) {
            throw new NotSerializableException("The map cannot be serialized while it is being built.");
        }
        return new SerializedMap(this);
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object object) {
        return this.findKey(object) >= 0;
    }

    @Override
    public boolean containsValue(Object object) {
        return this.findValue(object) >= 0;
    }

    @Override
    @Nullable
    public V get(Object object) {
        int n = this.findKey(object);
        if (n < 0) {
            return null;
        }
        Intrinsics.checkNotNull(this.valuesArray);
        return this.valuesArray[n];
    }

    @Override
    @Nullable
    public V put(K k, V v) {
        this.checkIsMutable$kotlin_stdlib();
        int n = this.addKey$kotlin_stdlib(k);
        V[] VArray = this.allocateValuesArray();
        if (n < 0) {
            V v2 = VArray[-n - 1];
            VArray[-n - 1] = v;
            return v2;
        }
        VArray[n] = v;
        return null;
    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> map) {
        Intrinsics.checkNotNullParameter(map, "from");
        this.checkIsMutable$kotlin_stdlib();
        this.putAllEntries((Collection)map.entrySet());
    }

    @Override
    @Nullable
    public V remove(Object object) {
        int n = this.removeKey$kotlin_stdlib(object);
        if (n < 0) {
            return null;
        }
        Intrinsics.checkNotNull(this.valuesArray);
        V[] VArray = this.valuesArray;
        V v = VArray[n];
        ListBuilderKt.resetAt(VArray, n);
        return v;
    }

    @Override
    public void clear() {
        this.checkIsMutable$kotlin_stdlib();
        IntIterator intIterator = new IntRange(0, this.length - 1).iterator();
        while (intIterator.hasNext()) {
            int n = intIterator.nextInt();
            int n2 = this.presenceArray[n];
            if (n2 < 0) continue;
            this.hashArray[n2] = 0;
            this.presenceArray[n] = -1;
        }
        ListBuilderKt.resetRange(this.keysArray, 0, this.length);
        if (this.valuesArray != null) {
            ListBuilderKt.resetRange(this.valuesArray, 0, this.length);
        }
        this.size = 0;
        this.length = 0;
    }

    @NotNull
    public Set<K> getKeys() {
        Set set;
        MapBuilderKeys<K> mapBuilderKeys = this.keysView;
        if (mapBuilderKeys == null) {
            MapBuilderKeys mapBuilderKeys2 = new MapBuilderKeys(this);
            this.keysView = mapBuilderKeys2;
            set = mapBuilderKeys2;
        } else {
            set = mapBuilderKeys;
        }
        return set;
    }

    @NotNull
    public Collection<V> getValues() {
        Collection collection;
        MapBuilderValues<V> mapBuilderValues = this.valuesView;
        if (mapBuilderValues == null) {
            MapBuilderValues mapBuilderValues2 = new MapBuilderValues(this);
            this.valuesView = mapBuilderValues2;
            collection = mapBuilderValues2;
        } else {
            collection = mapBuilderValues;
        }
        return collection;
    }

    @NotNull
    public Set<Map.Entry<K, V>> getEntries() {
        MapBuilderEntries<K, V> mapBuilderEntries = this.entriesView;
        if (mapBuilderEntries == null) {
            MapBuilderEntries mapBuilderEntries2 = new MapBuilderEntries(this);
            this.entriesView = mapBuilderEntries2;
            return mapBuilderEntries2;
        }
        return mapBuilderEntries;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || object instanceof Map && this.contentEquals((Map)object);
    }

    @Override
    public int hashCode() {
        int n = 0;
        EntriesItr<K, V> entriesItr = this.entriesIterator$kotlin_stdlib();
        while (entriesItr.hasNext()) {
            n += entriesItr.nextHashCode$kotlin_stdlib();
        }
        return n;
    }

    @NotNull
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(2 + this.size() * 3);
        stringBuilder.append("{");
        int n = 0;
        EntriesItr<K, V> entriesItr = this.entriesIterator$kotlin_stdlib();
        while (entriesItr.hasNext()) {
            if (n > 0) {
                stringBuilder.append(", ");
            }
            entriesItr.nextAppendString(stringBuilder);
            ++n;
        }
        stringBuilder.append("}");
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "sb.toString()");
        return string;
    }

    public final int getCapacity$kotlin_stdlib() {
        return this.keysArray.length;
    }

    private final int getHashSize() {
        return this.hashArray.length;
    }

    public final void checkIsMutable$kotlin_stdlib() {
        if (this.isReadOnly) {
            throw new UnsupportedOperationException();
        }
    }

    private final void ensureExtraCapacity(int n) {
        if (this.shouldCompact(n)) {
            this.rehash(this.getHashSize());
        } else {
            this.ensureCapacity(this.length + n);
        }
    }

    private final boolean shouldCompact(int n) {
        int n2 = this.getCapacity$kotlin_stdlib() - this.length;
        int n3 = this.length - this.size();
        return n2 < n && n3 + n2 >= n && n3 >= this.getCapacity$kotlin_stdlib() / 4;
    }

    private final void ensureCapacity(int n) {
        if (n < 0) {
            throw new OutOfMemoryError();
        }
        if (n > this.getCapacity$kotlin_stdlib()) {
            int n2 = this.getCapacity$kotlin_stdlib() * 3 / 2;
            if (n > n2) {
                n2 = n;
            }
            this.keysArray = ListBuilderKt.copyOfUninitializedElements(this.keysArray, n2);
            this.valuesArray = this.valuesArray != null ? ListBuilderKt.copyOfUninitializedElements(this.valuesArray, n2) : null;
            int[] nArray = Arrays.copyOf(this.presenceArray, n2);
            Intrinsics.checkNotNullExpressionValue(nArray, "copyOf(this, newSize)");
            this.presenceArray = nArray;
            int n3 = kotlin.collections.builders.MapBuilder$Companion.access$computeHashSize(Companion, n2);
            if (n3 > this.getHashSize()) {
                this.rehash(n3);
            }
        }
    }

    private final V[] allocateValuesArray() {
        V[] VArray = this.valuesArray;
        if (VArray != null) {
            return VArray;
        }
        E[] EArray = ListBuilderKt.arrayOfUninitializedElements(this.getCapacity$kotlin_stdlib());
        this.valuesArray = EArray;
        return EArray;
    }

    private final int hash(K k) {
        K k2 = k;
        return (k2 != null ? k2.hashCode() : 0) * -1640531527 >>> this.hashShift;
    }

    private final void compact() {
        int n = 0;
        V[] VArray = this.valuesArray;
        for (int i = 0; i < this.length; ++i) {
            if (this.presenceArray[i] < 0) continue;
            this.keysArray[n] = this.keysArray[i];
            if (VArray != null) {
                VArray[n] = VArray[i];
            }
            ++n;
        }
        ListBuilderKt.resetRange(this.keysArray, n, this.length);
        if (VArray != null) {
            ListBuilderKt.resetRange(VArray, n, this.length);
        }
        this.length = n;
    }

    private final void rehash(int n) {
        if (this.length > this.size()) {
            this.compact();
        }
        if (n != this.getHashSize()) {
            this.hashArray = new int[n];
            this.hashShift = kotlin.collections.builders.MapBuilder$Companion.access$computeShift(Companion, n);
        } else {
            ArraysKt.fill(this.hashArray, 0, 0, this.getHashSize());
        }
        int n2 = 0;
        while (n2 < this.length) {
            if (this.putRehash(n2++)) continue;
            throw new IllegalStateException("This cannot happen with fixed magic multiplier and grow-only hash array. Have object hashCodes changed?");
        }
    }

    private final boolean putRehash(int n) {
        int n2 = this.hash(this.keysArray[n]);
        int n3 = this.maxProbeDistance;
        while (true) {
            int n4;
            if ((n4 = this.hashArray[n2]) == 0) {
                this.hashArray[n2] = n + 1;
                this.presenceArray[n] = n2;
                return false;
            }
            if (--n3 < 0) {
                return true;
            }
            if (n2-- != 0) continue;
            n2 = this.getHashSize() - 1;
        }
    }

    private final int findKey(K k) {
        int n = this.hash(k);
        int n2 = this.maxProbeDistance;
        int n3;
        while ((n3 = this.hashArray[n]) != 0) {
            if (n3 > 0 && Intrinsics.areEqual(this.keysArray[n3 - 1], k)) {
                return n3 - 1;
            }
            if (--n2 < 0) {
                return 1;
            }
            if (n-- != 0) continue;
            n = this.getHashSize() - 1;
        }
        return 1;
    }

    private final int findValue(V v) {
        int n = this.length;
        while (--n >= 0) {
            if (this.presenceArray[n] < 0) continue;
            Intrinsics.checkNotNull(this.valuesArray);
            if (!Intrinsics.areEqual(this.valuesArray[n], v)) continue;
            return n;
        }
        return 1;
    }

    public final int addKey$kotlin_stdlib(K k) {
        this.checkIsMutable$kotlin_stdlib();
        block0: while (true) {
            int n = this.hash(k);
            int n2 = RangesKt.coerceAtMost(this.maxProbeDistance * 2, this.getHashSize() / 2);
            int n3 = 0;
            while (true) {
                int n4;
                if ((n4 = this.hashArray[n]) <= 0) {
                    if (this.length >= this.getCapacity$kotlin_stdlib()) {
                        this.ensureExtraCapacity(1);
                        continue block0;
                    }
                    int n5 = this.length;
                    this.length = n5 + 1;
                    int n6 = n5;
                    this.keysArray[n6] = k;
                    this.presenceArray[n6] = n;
                    this.hashArray[n] = n6 + 1;
                    n5 = this.size();
                    this.size = n5 + 1;
                    if (n3 > this.maxProbeDistance) {
                        this.maxProbeDistance = n3;
                    }
                    return n6;
                }
                if (Intrinsics.areEqual(this.keysArray[n4 - 1], k)) {
                    return -n4;
                }
                if (++n3 > n2) {
                    this.rehash(this.getHashSize() * 2);
                    continue block0;
                }
                if (n-- != 0) continue;
                n = this.getHashSize() - 1;
            }
            break;
        }
    }

    public final int removeKey$kotlin_stdlib(K k) {
        this.checkIsMutable$kotlin_stdlib();
        int n = this.findKey(k);
        if (n < 0) {
            return 1;
        }
        this.removeKeyAt(n);
        return n;
    }

    private final void removeKeyAt(int n) {
        ListBuilderKt.resetAt(this.keysArray, n);
        this.removeHashAt(this.presenceArray[n]);
        this.presenceArray[n] = -1;
        int n2 = this.size();
        this.size = n2 + -1;
    }

    private final void removeHashAt(int n) {
        int n2 = n;
        int n3 = n;
        int n4 = 0;
        int n5 = RangesKt.coerceAtMost(this.maxProbeDistance * 2, this.getHashSize() / 2);
        do {
            if (n2-- == 0) {
                n2 = this.getHashSize() - 1;
            }
            if (++n4 > this.maxProbeDistance) {
                this.hashArray[n3] = 0;
                return;
            }
            int n6 = this.hashArray[n2];
            if (n6 == 0) {
                this.hashArray[n3] = 0;
                return;
            }
            if (n6 < 0) {
                this.hashArray[n3] = -1;
                n3 = n2;
                n4 = 0;
                continue;
            }
            int n7 = this.hash(this.keysArray[n6 - 1]);
            if ((n7 - n2 & this.getHashSize() - 1) < n4) continue;
            this.hashArray[n3] = n6;
            this.presenceArray[n6 - 1] = n3;
            n3 = n2;
            n4 = 0;
        } while (--n5 >= 0);
        this.hashArray[n3] = -1;
    }

    public final boolean containsEntry$kotlin_stdlib(@NotNull Map.Entry<? extends K, ? extends V> entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        int n = this.findKey(entry.getKey());
        if (n < 0) {
            return true;
        }
        Intrinsics.checkNotNull(this.valuesArray);
        return Intrinsics.areEqual(this.valuesArray[n], entry.getValue());
    }

    private final boolean contentEquals(Map<?, ?> map) {
        return this.size() == map.size() && this.containsAllEntries$kotlin_stdlib((Collection)map.entrySet());
    }

    public final boolean containsAllEntries$kotlin_stdlib(@NotNull Collection<?> collection) {
        Intrinsics.checkNotNullParameter(collection, "m");
        for (Object obj : collection) {
            try {
                if (obj != null && this.containsEntry$kotlin_stdlib((Map.Entry)obj)) continue;
                return true;
            } catch (ClassCastException classCastException) {
                return true;
            }
        }
        return false;
    }

    private final boolean putEntry(Map.Entry<? extends K, ? extends V> entry) {
        int n = this.addKey$kotlin_stdlib(entry.getKey());
        V[] VArray = this.allocateValuesArray();
        if (n >= 0) {
            VArray[n] = entry.getValue();
            return false;
        }
        V v = VArray[-n - 1];
        if (!Intrinsics.areEqual(entry.getValue(), v)) {
            VArray[-n - 1] = entry.getValue();
            return false;
        }
        return true;
    }

    private final boolean putAllEntries(Collection<? extends Map.Entry<? extends K, ? extends V>> collection) {
        if (collection.isEmpty()) {
            return true;
        }
        this.ensureExtraCapacity(collection.size());
        Iterator<Map.Entry<K, V>> iterator2 = collection.iterator();
        boolean bl = false;
        while (iterator2.hasNext()) {
            if (!this.putEntry(iterator2.next())) continue;
            bl = true;
        }
        return bl;
    }

    public final boolean removeEntry$kotlin_stdlib(@NotNull Map.Entry<? extends K, ? extends V> entry) {
        Intrinsics.checkNotNullParameter(entry, "entry");
        this.checkIsMutable$kotlin_stdlib();
        int n = this.findKey(entry.getKey());
        if (n < 0) {
            return true;
        }
        Intrinsics.checkNotNull(this.valuesArray);
        if (!Intrinsics.areEqual(this.valuesArray[n], entry.getValue())) {
            return true;
        }
        this.removeKeyAt(n);
        return false;
    }

    public final boolean removeValue$kotlin_stdlib(V v) {
        this.checkIsMutable$kotlin_stdlib();
        int n = this.findValue(v);
        if (n < 0) {
            return true;
        }
        this.removeKeyAt(n);
        return false;
    }

    @NotNull
    public final KeysItr<K, V> keysIterator$kotlin_stdlib() {
        return new KeysItr(this);
    }

    @NotNull
    public final ValuesItr<K, V> valuesIterator$kotlin_stdlib() {
        return new ValuesItr(this);
    }

    @NotNull
    public final EntriesItr<K, V> entriesIterator$kotlin_stdlib() {
        return new EntriesItr(this);
    }

    @Override
    public final int size() {
        return this.getSize();
    }

    @Override
    public final Set<K> keySet() {
        return this.getKeys();
    }

    @Override
    public final Collection<V> values() {
        return this.getValues();
    }

    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        return this.getEntries();
    }

    public static final MapBuilder access$getEmpty$cp() {
        return Empty;
    }

    public static final int access$getLength$p(MapBuilder mapBuilder) {
        return mapBuilder.length;
    }

    public static final int[] access$getPresenceArray$p(MapBuilder mapBuilder) {
        return mapBuilder.presenceArray;
    }

    public static final void access$removeKeyAt(MapBuilder mapBuilder, int n) {
        mapBuilder.removeKeyAt(n);
    }

    public static final Object[] access$getKeysArray$p(MapBuilder mapBuilder) {
        return mapBuilder.keysArray;
    }

    public static final Object[] access$getValuesArray$p(MapBuilder mapBuilder) {
        return mapBuilder.valuesArray;
    }

    public static final Object[] access$allocateValuesArray(MapBuilder mapBuilder) {
        return mapBuilder.allocateValuesArray();
    }

    static {
        MapBuilder mapBuilder;
        Companion = new Companion(null);
        MapBuilder mapBuilder2 = mapBuilder = new MapBuilder(0);
        boolean bl = false;
        mapBuilder2.isReadOnly = true;
        Empty = mapBuilder;
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\tH\u0002J\u0010\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\tH\u0002R \u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lkotlin/collections/builders/MapBuilder$Companion;", "", "()V", "Empty", "Lkotlin/collections/builders/MapBuilder;", "", "getEmpty$kotlin_stdlib", "()Lkotlin/collections/builders/MapBuilder;", "INITIAL_CAPACITY", "", "INITIAL_MAX_PROBE_DISTANCE", "MAGIC", "TOMBSTONE", "computeHashSize", "capacity", "computeShift", "hashSize", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final MapBuilder getEmpty$kotlin_stdlib() {
            return MapBuilder.access$getEmpty$cp();
        }

        private final int computeHashSize(int n) {
            return Integer.highestOneBit(RangesKt.coerceAtLeast(n, 1) * 3);
        }

        private final int computeShift(int n) {
            return Integer.numberOfLeadingZeros(n) + 1;
        }

        public static final int access$computeShift(Companion companion, int n) {
            return companion.computeShift(n);
        }

        public static final int access$computeHashSize(Companion companion, int n) {
            return companion.computeHashSize(n);
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0002\u0010'\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00050\u0004B\u0019\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0007\u00a2\u0006\u0002\u0010\bJ\u0015\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\nH\u0096\u0002J\u0012\u0010\u000b\u001a\u00020\f2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000fJ\r\u0010\u0010\u001a\u00020\u0011H\u0000\u00a2\u0006\u0002\b\u0012\u00a8\u0006\u0013"}, d2={"Lkotlin/collections/builders/MapBuilder$EntriesItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "Lkotlin/collections/builders/MapBuilder$EntryRef;", "nextAppendString", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "nextHashCode", "", "nextHashCode$kotlin_stdlib", "kotlin-stdlib"})
    public static final class EntriesItr<K, V>
    extends Itr<K, V>
    implements Iterator<Map.Entry<K, V>>,
    KMutableIterator {
        public EntriesItr(@NotNull MapBuilder<K, V> mapBuilder) {
            Intrinsics.checkNotNullParameter(mapBuilder, "map");
            super(mapBuilder);
        }

        @Override
        @NotNull
        public EntryRef<K, V> next() {
            if (this.getIndex$kotlin_stdlib() >= MapBuilder.access$getLength$p(this.getMap$kotlin_stdlib())) {
                throw new NoSuchElementException();
            }
            int n = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(n + 1);
            this.setLastIndex$kotlin_stdlib(n);
            EntryRef entryRef = new EntryRef(this.getMap$kotlin_stdlib(), this.getLastIndex$kotlin_stdlib());
            this.initNext$kotlin_stdlib();
            return entryRef;
        }

        public final int nextHashCode$kotlin_stdlib() {
            if (this.getIndex$kotlin_stdlib() >= MapBuilder.access$getLength$p(this.getMap$kotlin_stdlib())) {
                throw new NoSuchElementException();
            }
            int n = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(n + 1);
            this.setLastIndex$kotlin_stdlib(n);
            Object object = MapBuilder.access$getKeysArray$p(this.getMap$kotlin_stdlib())[this.getLastIndex$kotlin_stdlib()];
            int n2 = object != null ? object.hashCode() : 0;
            Object[] objectArray = MapBuilder.access$getValuesArray$p(this.getMap$kotlin_stdlib());
            Intrinsics.checkNotNull(objectArray);
            Object object2 = objectArray[this.getLastIndex$kotlin_stdlib()];
            n = n2 ^ (object2 != null ? object2.hashCode() : 0);
            this.initNext$kotlin_stdlib();
            return n;
        }

        public final void nextAppendString(@NotNull StringBuilder stringBuilder) {
            Intrinsics.checkNotNullParameter(stringBuilder, "sb");
            if (this.getIndex$kotlin_stdlib() >= MapBuilder.access$getLength$p(this.getMap$kotlin_stdlib())) {
                throw new NoSuchElementException();
            }
            int n = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(n + 1);
            this.setLastIndex$kotlin_stdlib(n);
            Object object = MapBuilder.access$getKeysArray$p(this.getMap$kotlin_stdlib())[this.getLastIndex$kotlin_stdlib()];
            if (Intrinsics.areEqual(object, this.getMap$kotlin_stdlib())) {
                stringBuilder.append("(this Map)");
            } else {
                stringBuilder.append(object);
            }
            stringBuilder.append('=');
            Object[] objectArray = MapBuilder.access$getValuesArray$p(this.getMap$kotlin_stdlib());
            Intrinsics.checkNotNull(objectArray);
            Object object2 = objectArray[this.getLastIndex$kotlin_stdlib()];
            if (Intrinsics.areEqual(object2, this.getMap$kotlin_stdlib())) {
                stringBuilder.append("(this Map)");
            } else {
                stringBuilder.append(object2);
            }
            this.initNext$kotlin_stdlib();
        }

        @Override
        public Object next() {
            return this.next();
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003B!\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0007H\u0016J\u0015\u0010\u0013\u001a\u00028\u00032\u0006\u0010\u0014\u001a\u00028\u0003H\u0016\u00a2\u0006\u0002\u0010\u0015J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00028\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\u00028\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000b\u00a8\u0006\u0018"}, d2={"Lkotlin/collections/builders/MapBuilder$EntryRef;", "K", "V", "", "map", "Lkotlin/collections/builders/MapBuilder;", "index", "", "(Lkotlin/collections/builders/MapBuilder;I)V", "key", "getKey", "()Ljava/lang/Object;", "value", "getValue", "equals", "", "other", "", "hashCode", "setValue", "newValue", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "kotlin-stdlib"})
    public static final class EntryRef<K, V>
    implements Map.Entry<K, V>,
    KMutableMap.Entry {
        @NotNull
        private final MapBuilder<K, V> map;
        private final int index;

        public EntryRef(@NotNull MapBuilder<K, V> mapBuilder, int n) {
            Intrinsics.checkNotNullParameter(mapBuilder, "map");
            this.map = mapBuilder;
            this.index = n;
        }

        @Override
        public K getKey() {
            return (K)MapBuilder.access$getKeysArray$p(this.map)[this.index];
        }

        @Override
        public V getValue() {
            Object[] objectArray = MapBuilder.access$getValuesArray$p(this.map);
            Intrinsics.checkNotNull(objectArray);
            return (V)objectArray[this.index];
        }

        @Override
        public V setValue(V v) {
            this.map.checkIsMutable$kotlin_stdlib();
            Object[] objectArray = MapBuilder.access$allocateValuesArray(this.map);
            Object object = objectArray[this.index];
            objectArray[this.index] = v;
            return (V)object;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            return object instanceof Map.Entry && Intrinsics.areEqual(((Map.Entry)object).getKey(), this.getKey()) && Intrinsics.areEqual(((Map.Entry)object).getValue(), this.getValue());
        }

        @Override
        public int hashCode() {
            K k = this.getKey();
            V v = this.getValue();
            return (k != null ? k.hashCode() : 0) ^ (v != null ? v.hashCode() : 0);
        }

        @NotNull
        public String toString() {
            return "" + this.getKey() + '=' + this.getValue();
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0010\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u00020\u0003B\u0019\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0012\u001a\u00020\u0013J\r\u0010\u0014\u001a\u00020\u0015H\u0000\u00a2\u0006\u0002\b\u0016J\u0006\u0010\u0017\u001a\u00020\u0015R\u001a\u0010\u0007\u001a\u00020\bX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR \u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0005X\u0080\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0018"}, d2={"Lkotlin/collections/builders/MapBuilder$Itr;", "K", "V", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "index", "", "getIndex$kotlin_stdlib", "()I", "setIndex$kotlin_stdlib", "(I)V", "lastIndex", "getLastIndex$kotlin_stdlib", "setLastIndex$kotlin_stdlib", "getMap$kotlin_stdlib", "()Lkotlin/collections/builders/MapBuilder;", "hasNext", "", "initNext", "", "initNext$kotlin_stdlib", "remove", "kotlin-stdlib"})
    @SourceDebugExtension(value={"SMAP\nMapBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MapBuilder.kt\nkotlin/collections/builders/MapBuilder$Itr\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,697:1\n1#2:698\n*E\n"})
    public static class Itr<K, V> {
        @NotNull
        private final MapBuilder<K, V> map;
        private int index;
        private int lastIndex;

        public Itr(@NotNull MapBuilder<K, V> mapBuilder) {
            Intrinsics.checkNotNullParameter(mapBuilder, "map");
            this.map = mapBuilder;
            this.lastIndex = -1;
            this.initNext$kotlin_stdlib();
        }

        @NotNull
        public final MapBuilder<K, V> getMap$kotlin_stdlib() {
            return this.map;
        }

        public final int getIndex$kotlin_stdlib() {
            return this.index;
        }

        public final void setIndex$kotlin_stdlib(int n) {
            this.index = n;
        }

        public final int getLastIndex$kotlin_stdlib() {
            return this.lastIndex;
        }

        public final void setLastIndex$kotlin_stdlib(int n) {
            this.lastIndex = n;
        }

        public final void initNext$kotlin_stdlib() {
            while (this.index < MapBuilder.access$getLength$p(this.map) && MapBuilder.access$getPresenceArray$p(this.map)[this.index] < 0) {
                int n = this.index;
                this.index = n + 1;
            }
        }

        public final boolean hasNext() {
            return this.index < MapBuilder.access$getLength$p(this.map);
        }

        public final void remove() {
            boolean bl;
            boolean bl2 = bl = this.lastIndex != -1;
            if (!bl) {
                boolean bl3 = false;
                String string = "Call next() before removing element from the iterator.";
                throw new IllegalStateException(string.toString());
            }
            this.map.checkIsMutable$kotlin_stdlib();
            MapBuilder.access$removeKeyAt(this.map, this.lastIndex);
            this.lastIndex = -1;
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00010\u0004B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0006\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00028\u0002H\u0096\u0002\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/collections/builders/MapBuilder$KeysItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
    public static final class KeysItr<K, V>
    extends Itr<K, V>
    implements Iterator<K>,
    KMutableIterator {
        public KeysItr(@NotNull MapBuilder<K, V> mapBuilder) {
            Intrinsics.checkNotNullParameter(mapBuilder, "map");
            super(mapBuilder);
        }

        @Override
        public K next() {
            if (this.getIndex$kotlin_stdlib() >= MapBuilder.access$getLength$p(this.getMap$kotlin_stdlib())) {
                throw new NoSuchElementException();
            }
            int n = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(n + 1);
            this.setLastIndex$kotlin_stdlib(n);
            Object object = MapBuilder.access$getKeysArray$p(this.getMap$kotlin_stdlib())[this.getLastIndex$kotlin_stdlib()];
            this.initNext$kotlin_stdlib();
            return (K)object;
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u0000*\u0004\b\u0002\u0010\u0001*\u0004\b\u0003\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0004B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0002\u0012\u0004\u0012\u00028\u00030\u0006\u00a2\u0006\u0002\u0010\u0007J\u000e\u0010\b\u001a\u00028\u0003H\u0096\u0002\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2={"Lkotlin/collections/builders/MapBuilder$ValuesItr;", "K", "V", "Lkotlin/collections/builders/MapBuilder$Itr;", "", "map", "Lkotlin/collections/builders/MapBuilder;", "(Lkotlin/collections/builders/MapBuilder;)V", "next", "()Ljava/lang/Object;", "kotlin-stdlib"})
    public static final class ValuesItr<K, V>
    extends Itr<K, V>
    implements Iterator<V>,
    KMutableIterator {
        public ValuesItr(@NotNull MapBuilder<K, V> mapBuilder) {
            Intrinsics.checkNotNullParameter(mapBuilder, "map");
            super(mapBuilder);
        }

        @Override
        public V next() {
            if (this.getIndex$kotlin_stdlib() >= MapBuilder.access$getLength$p(this.getMap$kotlin_stdlib())) {
                throw new NoSuchElementException();
            }
            int n = this.getIndex$kotlin_stdlib();
            this.setIndex$kotlin_stdlib(n + 1);
            this.setLastIndex$kotlin_stdlib(n);
            Object[] objectArray = MapBuilder.access$getValuesArray$p(this.getMap$kotlin_stdlib());
            Intrinsics.checkNotNull(objectArray);
            Object object = objectArray[this.getLastIndex$kotlin_stdlib()];
            this.initNext$kotlin_stdlib();
            return (V)object;
        }
    }
}

