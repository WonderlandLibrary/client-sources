/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections.builders;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractMutableList;
import kotlin.collections.ArrayDeque;
import kotlin.collections.ArraysKt;
import kotlin.collections.builders.ListBuilderKt;
import kotlin.collections.builders.SerializedCollection;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMutableList;
import kotlin.jvm.internal.markers.KMutableListIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0010)\n\u0002\b\u0002\n\u0002\u0010+\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0000\u0018\u0000 V*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u00042\b\u0012\u0004\u0012\u0002H\u00010\u00052\u00060\u0006j\u0002`\u0007:\u0002VWB\u0007\b\u0016\u00a2\u0006\u0002\u0010\bB\u000f\b\u0016\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bBM\b\u0002\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\r\u0012\u0006\u0010\u000e\u001a\u00020\n\u0012\u0006\u0010\u000f\u001a\u00020\n\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u000e\u0010\u0012\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000\u0012\u000e\u0010\u0013\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000\u00a2\u0006\u0002\u0010\u0014J\u0015\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u001dJ\u001d\u0010\u001b\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010 J\u001e\u0010!\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\n2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#H\u0016J\u0016\u0010!\u001a\u00020\u00112\f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#H\u0016J&\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\n2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#2\u0006\u0010&\u001a\u00020\nH\u0002J\u001d\u0010'\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00028\u0000H\u0002\u00a2\u0006\u0002\u0010 J\f\u0010(\u001a\b\u0012\u0004\u0012\u00028\u00000)J\b\u0010*\u001a\u00020\u001eH\u0002J\b\u0010+\u001a\u00020\u001eH\u0016J\u0014\u0010,\u001a\u00020\u00112\n\u0010-\u001a\u0006\u0012\u0002\b\u00030)H\u0002J\u0010\u0010.\u001a\u00020\u001e2\u0006\u0010/\u001a\u00020\nH\u0002J\u0010\u00100\u001a\u00020\u001e2\u0006\u0010&\u001a\u00020\nH\u0002J\u0013\u00101\u001a\u00020\u00112\b\u0010-\u001a\u0004\u0018\u000102H\u0096\u0002J\u0016\u00103\u001a\u00028\u00002\u0006\u0010\u001f\u001a\u00020\nH\u0096\u0002\u00a2\u0006\u0002\u00104J\b\u00105\u001a\u00020\nH\u0016J\u0015\u00106\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u00107J\u0018\u00108\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\n2\u0006\u0010&\u001a\u00020\nH\u0002J\b\u00109\u001a\u00020\u0011H\u0016J\u000f\u0010:\u001a\b\u0012\u0004\u0012\u00028\u00000;H\u0096\u0002J\u0015\u0010<\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u00107J\u000e\u0010=\u001a\b\u0012\u0004\u0012\u00028\u00000>H\u0016J\u0016\u0010=\u001a\b\u0012\u0004\u0012\u00028\u00000>2\u0006\u0010\u001f\u001a\u00020\nH\u0016J\u0015\u0010?\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u001dJ\u0016\u0010@\u001a\u00020\u00112\f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#H\u0016J\u0015\u0010A\u001a\u00028\u00002\u0006\u0010\u001f\u001a\u00020\nH\u0016\u00a2\u0006\u0002\u00104J\u0015\u0010B\u001a\u00028\u00002\u0006\u0010%\u001a\u00020\nH\u0002\u00a2\u0006\u0002\u00104J\u0018\u0010C\u001a\u00020\u001e2\u0006\u0010D\u001a\u00020\n2\u0006\u0010E\u001a\u00020\nH\u0002J\u0016\u0010F\u001a\u00020\u00112\f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#H\u0016J.\u0010G\u001a\u00020\n2\u0006\u0010D\u001a\u00020\n2\u0006\u0010E\u001a\u00020\n2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#2\u0006\u0010H\u001a\u00020\u0011H\u0002J\u001e\u0010I\u001a\u00028\u00002\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010JJ\u001e\u0010K\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010L\u001a\u00020\n2\u0006\u0010M\u001a\u00020\nH\u0016J\u0015\u0010N\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001020\rH\u0016\u00a2\u0006\u0002\u0010OJ'\u0010N\u001a\b\u0012\u0004\u0012\u0002HP0\r\"\u0004\b\u0001\u0010P2\f\u0010Q\u001a\b\u0012\u0004\u0012\u0002HP0\rH\u0016\u00a2\u0006\u0002\u0010RJ\b\u0010S\u001a\u00020TH\u0016J\b\u0010U\u001a\u000202H\u0002R\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\rX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0015R\u0016\u0010\u0012\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00118BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0000X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006X"}, d2={"Lkotlin/collections/builders/ListBuilder;", "E", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "Lkotlin/collections/AbstractMutableList;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "()V", "initialCapacity", "", "(I)V", "array", "", "offset", "length", "isReadOnly", "", "backing", "root", "([Ljava/lang/Object;IIZLkotlin/collections/builders/ListBuilder;Lkotlin/collections/builders/ListBuilder;)V", "[Ljava/lang/Object;", "isEffectivelyReadOnly", "()Z", "size", "getSize", "()I", "add", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "elements", "", "addAllInternal", "i", "n", "addAtInternal", "build", "", "checkIsMutable", "clear", "contentEquals", "other", "ensureCapacity", "minCapacity", "ensureExtraCapacity", "equals", "", "get", "(I)Ljava/lang/Object;", "hashCode", "indexOf", "(Ljava/lang/Object;)I", "insertAtInternal", "isEmpty", "iterator", "", "lastIndexOf", "listIterator", "", "remove", "removeAll", "removeAt", "removeAtInternal", "removeRangeInternal", "rangeOffset", "rangeLength", "retainAll", "retainOrRemoveAllInternal", "retain", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "subList", "fromIndex", "toIndex", "toArray", "()[Ljava/lang/Object;", "T", "destination", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toString", "", "writeReplace", "Companion", "Itr", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nListBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ListBuilder.kt\nkotlin/collections/builders/ListBuilder\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,432:1\n1#2:433\n*E\n"})
public final class ListBuilder<E>
extends AbstractMutableList<E>
implements List<E>,
RandomAccess,
Serializable,
KMutableList {
    @NotNull
    private static final Companion Companion;
    @NotNull
    private E[] array;
    private int offset;
    private int length;
    private boolean isReadOnly;
    @Nullable
    private final ListBuilder<E> backing;
    @Nullable
    private final ListBuilder<E> root;
    @NotNull
    private static final ListBuilder Empty;

    private ListBuilder(E[] EArray, int n, int n2, boolean bl, ListBuilder<E> listBuilder, ListBuilder<E> listBuilder2) {
        this.array = EArray;
        this.offset = n;
        this.length = n2;
        this.isReadOnly = bl;
        this.backing = listBuilder;
        this.root = listBuilder2;
    }

    public ListBuilder() {
        this(10);
    }

    public ListBuilder(int n) {
        this(ListBuilderKt.arrayOfUninitializedElements(n), 0, 0, false, null, null);
    }

    @NotNull
    public final List<E> build() {
        if (this.backing != null) {
            throw new IllegalStateException();
        }
        this.checkIsMutable();
        this.isReadOnly = true;
        return this.length > 0 ? (List)this : (List)Empty;
    }

    private final Object writeReplace() {
        if (!this.isEffectivelyReadOnly()) {
            throw new NotSerializableException("The list cannot be serialized while it is being built.");
        }
        return new SerializedCollection(this, 0);
    }

    @Override
    public int getSize() {
        return this.length;
    }

    @Override
    public boolean isEmpty() {
        return this.length == 0;
    }

    @Override
    public E get(int n) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.length);
        return this.array[this.offset + n];
    }

    @Override
    public E set(int n, E e) {
        this.checkIsMutable();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.length);
        E e2 = this.array[this.offset + n];
        this.array[this.offset + n] = e;
        return e2;
    }

    @Override
    public int indexOf(Object object) {
        for (int i = 0; i < this.length; ++i) {
            if (!Intrinsics.areEqual(this.array[this.offset + i], object)) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(Object object) {
        for (int i = this.length - 1; i >= 0; --i) {
            if (!Intrinsics.areEqual(this.array[this.offset + i], object)) continue;
            return i;
        }
        return 1;
    }

    @Override
    @NotNull
    public Iterator<E> iterator() {
        return new Itr(this, 0);
    }

    @Override
    @NotNull
    public ListIterator<E> listIterator() {
        return new Itr(this, 0);
    }

    @Override
    @NotNull
    public ListIterator<E> listIterator(int n) {
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(n, this.length);
        return new Itr(this, n);
    }

    @Override
    public boolean add(E e) {
        this.checkIsMutable();
        this.addAtInternal(this.offset + this.length, e);
        return false;
    }

    @Override
    public void add(int n, E e) {
        this.checkIsMutable();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(n, this.length);
        this.addAtInternal(this.offset + n, e);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> collection) {
        Intrinsics.checkNotNullParameter(collection, "elements");
        this.checkIsMutable();
        int n = collection.size();
        this.addAllInternal(this.offset + this.length, collection, n);
        return n > 0;
    }

    @Override
    public boolean addAll(int n, @NotNull Collection<? extends E> collection) {
        Intrinsics.checkNotNullParameter(collection, "elements");
        this.checkIsMutable();
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(n, this.length);
        int n2 = collection.size();
        this.addAllInternal(this.offset + n, collection, n2);
        return n2 > 0;
    }

    @Override
    public void clear() {
        this.checkIsMutable();
        this.removeRangeInternal(this.offset, this.length);
    }

    @Override
    public E removeAt(int n) {
        this.checkIsMutable();
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.length);
        return this.removeAtInternal(this.offset + n);
    }

    @Override
    public boolean remove(Object object) {
        this.checkIsMutable();
        int n = this.indexOf(object);
        if (n >= 0) {
            this.remove(n);
        }
        return n >= 0;
    }

    @Override
    public boolean removeAll(@NotNull Collection<? extends Object> collection) {
        Intrinsics.checkNotNullParameter(collection, "elements");
        this.checkIsMutable();
        return this.retainOrRemoveAllInternal(this.offset, this.length, collection, false) > 0;
    }

    @Override
    public boolean retainAll(@NotNull Collection<? extends Object> collection) {
        Intrinsics.checkNotNullParameter(collection, "elements");
        this.checkIsMutable();
        return this.retainOrRemoveAllInternal(this.offset, this.length, collection, true) > 0;
    }

    @Override
    @NotNull
    public List<E> subList(int n, int n2) {
        AbstractList.Companion.checkRangeIndexes$kotlin_stdlib(n, n2, this.length);
        ListBuilder listBuilder = this.root;
        if (listBuilder == null) {
            listBuilder = this;
        }
        return new ListBuilder<E>(this.array, this.offset + n, n2 - n, this.isReadOnly, this, listBuilder);
    }

    @Override
    @NotNull
    public <T> T[] toArray(@NotNull T[] TArray) {
        Intrinsics.checkNotNullParameter(TArray, "destination");
        if (TArray.length < this.length) {
            T[] TArray2 = Arrays.copyOfRange(this.array, this.offset, this.offset + this.length, TArray.getClass());
            Intrinsics.checkNotNullExpressionValue(TArray2, "copyOfRange(array, offse\u2026h, destination.javaClass)");
            return TArray2;
        }
        ArraysKt.copyInto(this.array, TArray, 0, this.offset, this.offset + this.length);
        if (TArray.length > this.length) {
            TArray[this.length] = null;
        }
        return TArray;
    }

    @Override
    @NotNull
    public Object[] toArray() {
        E[] EArray = this.array;
        int n = this.offset;
        int n2 = this.offset + this.length;
        return ArraysKt.copyOfRange(EArray, n, n2);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        return object == this || object instanceof List && this.contentEquals((List)object);
    }

    @Override
    public int hashCode() {
        return ListBuilderKt.access$subarrayContentHashCode(this.array, this.offset, this.length);
    }

    @Override
    @NotNull
    public String toString() {
        return ListBuilderKt.access$subarrayContentToString(this.array, this.offset, this.length);
    }

    private final void ensureCapacity(int n) {
        if (this.backing != null) {
            throw new IllegalStateException();
        }
        if (n < 0) {
            throw new OutOfMemoryError();
        }
        if (n > this.array.length) {
            int n2 = ArrayDeque.Companion.newCapacity$kotlin_stdlib(this.array.length, n);
            this.array = ListBuilderKt.copyOfUninitializedElements(this.array, n2);
        }
    }

    private final void checkIsMutable() {
        if (this.isEffectivelyReadOnly()) {
            throw new UnsupportedOperationException();
        }
    }

    private final boolean isEffectivelyReadOnly() {
        return this.isReadOnly || this.root != null && this.root.isReadOnly;
    }

    private final void ensureExtraCapacity(int n) {
        this.ensureCapacity(this.length + n);
    }

    private final boolean contentEquals(List<?> list) {
        return ListBuilderKt.access$subarrayContentEquals(this.array, this.offset, this.length, list);
    }

    private final void insertAtInternal(int n, int n2) {
        this.ensureExtraCapacity(n2);
        E[] EArray = this.array;
        E[] EArray2 = this.array;
        int n3 = this.offset + this.length;
        int n4 = n + n2;
        ArraysKt.copyInto(EArray, EArray2, n4, n, n3);
        this.length += n2;
    }

    private final void addAtInternal(int n, E e) {
        if (this.backing != null) {
            super.addAtInternal(n, e);
            this.array = this.backing.array;
            int n2 = this.length;
            this.length = n2 + 1;
        } else {
            this.insertAtInternal(n, 1);
            this.array[n] = e;
        }
    }

    private final void addAllInternal(int n, Collection<? extends E> collection, int n2) {
        if (this.backing != null) {
            super.addAllInternal(n, collection, n2);
            this.array = this.backing.array;
            this.length += n2;
        } else {
            this.insertAtInternal(n, n2);
            Iterator<E> iterator2 = collection.iterator();
            for (int i = 0; i < n2; ++i) {
                this.array[n + i] = iterator2.next();
            }
        }
    }

    private final E removeAtInternal(int n) {
        if (this.backing != null) {
            E e = super.removeAtInternal(n);
            int n2 = this.length;
            this.length = n2 + -1;
            return e;
        }
        E e = this.array[n];
        E[] EArray = this.array;
        E[] EArray2 = this.array;
        int n3 = n + 1;
        int n4 = this.offset + this.length;
        ArraysKt.copyInto(EArray, EArray2, n, n3, n4);
        ListBuilderKt.resetAt(this.array, this.offset + this.length - 1);
        int n5 = this.length;
        this.length = n5 + -1;
        return e;
    }

    private final void removeRangeInternal(int n, int n2) {
        if (this.backing != null) {
            super.removeRangeInternal(n, n2);
        } else {
            E[] EArray = this.array;
            E[] EArray2 = this.array;
            int n3 = n + n2;
            int n4 = this.length;
            ArraysKt.copyInto(EArray, EArray2, n, n3, n4);
            ListBuilderKt.resetRange(this.array, this.length - n2, this.length);
        }
        this.length -= n2;
    }

    private final int retainOrRemoveAllInternal(int n, int n2, Collection<? extends E> collection, boolean bl) {
        if (this.backing != null) {
            int n3 = super.retainOrRemoveAllInternal(n, n2, collection, bl);
            this.length -= n3;
            return n3;
        }
        int n4 = 0;
        int n5 = 0;
        while (n4 < n2) {
            if (collection.contains(this.array[n + n4]) == bl) {
                this.array[n + n5++] = this.array[n + n4++];
                continue;
            }
            ++n4;
        }
        int n6 = n2 - n5;
        E[] EArray = this.array;
        E[] EArray2 = this.array;
        int n7 = n + n2;
        int n8 = this.length;
        int n9 = n + n5;
        ArraysKt.copyInto(EArray, EArray2, n9, n7, n8);
        ListBuilderKt.resetRange(this.array, this.length - n6, this.length);
        this.length -= n6;
        return n6;
    }

    public static final int access$getLength$p(ListBuilder listBuilder) {
        return listBuilder.length;
    }

    public static final Object[] access$getArray$p(ListBuilder listBuilder) {
        return listBuilder.array;
    }

    public static final int access$getOffset$p(ListBuilder listBuilder) {
        return listBuilder.offset;
    }

    static {
        ListBuilder listBuilder;
        Companion = new Companion(null);
        ListBuilder listBuilder2 = listBuilder = new ListBuilder(0);
        boolean bl = false;
        listBuilder2.isReadOnly = true;
        Empty = listBuilder;
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0006"}, d2={"Lkotlin/collections/builders/ListBuilder$Companion;", "", "()V", "Empty", "Lkotlin/collections/builders/ListBuilder;", "", "kotlin-stdlib"})
    private static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010+\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\t\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\b\u0016\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u0015\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\fJ\t\u0010\r\u001a\u00020\u000eH\u0096\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u000e\u0010\u0010\u001a\u00028\u0001H\u0096\u0002\u00a2\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0006H\u0016J\r\u0010\u0013\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\u0011J\b\u0010\u0014\u001a\u00020\u0006H\u0016J\b\u0010\u0015\u001a\u00020\nH\u0016J\u0015\u0010\u0016\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00028\u0001H\u0016\u00a2\u0006\u0002\u0010\fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lkotlin/collections/builders/ListBuilder$Itr;", "E", "", "list", "Lkotlin/collections/builders/ListBuilder;", "index", "", "(Lkotlin/collections/builders/ListBuilder;I)V", "lastIndex", "add", "", "element", "(Ljava/lang/Object;)V", "hasNext", "", "hasPrevious", "next", "()Ljava/lang/Object;", "nextIndex", "previous", "previousIndex", "remove", "set", "kotlin-stdlib"})
    @SourceDebugExtension(value={"SMAP\nListBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ListBuilder.kt\nkotlin/collections/builders/ListBuilder$Itr\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,432:1\n1#2:433\n*E\n"})
    private static final class Itr<E>
    implements ListIterator<E>,
    KMutableListIterator {
        @NotNull
        private final ListBuilder<E> list;
        private int index;
        private int lastIndex;

        public Itr(@NotNull ListBuilder<E> listBuilder, int n) {
            Intrinsics.checkNotNullParameter(listBuilder, "list");
            this.list = listBuilder;
            this.index = n;
            this.lastIndex = -1;
        }

        @Override
        public boolean hasPrevious() {
            return this.index > 0;
        }

        @Override
        public boolean hasNext() {
            return this.index < ListBuilder.access$getLength$p(this.list);
        }

        @Override
        public int previousIndex() {
            return this.index - 1;
        }

        @Override
        public int nextIndex() {
            return this.index;
        }

        @Override
        public E previous() {
            if (this.index <= 0) {
                throw new NoSuchElementException();
            }
            this.index += -1;
            this.lastIndex = this.index;
            return (E)ListBuilder.access$getArray$p(this.list)[ListBuilder.access$getOffset$p(this.list) + this.lastIndex];
        }

        @Override
        public E next() {
            if (this.index >= ListBuilder.access$getLength$p(this.list)) {
                throw new NoSuchElementException();
            }
            int n = this.index;
            this.index = n + 1;
            this.lastIndex = n;
            return (E)ListBuilder.access$getArray$p(this.list)[ListBuilder.access$getOffset$p(this.list) + this.lastIndex];
        }

        @Override
        public void set(E e) {
            boolean bl;
            boolean bl2 = bl = this.lastIndex != -1;
            if (!bl) {
                boolean bl3 = false;
                String string = "Call next() or previous() before replacing element from the iterator.";
                throw new IllegalStateException(string.toString());
            }
            this.list.set(this.lastIndex, e);
        }

        @Override
        public void add(E e) {
            int n = this.index;
            this.index = n + 1;
            this.list.add(n, e);
            this.lastIndex = -1;
        }

        @Override
        public void remove() {
            boolean bl;
            boolean bl2 = bl = this.lastIndex != -1;
            if (!bl) {
                boolean bl3 = false;
                String string = "Call next() or previous() before removing element from the iterator.";
                throw new IllegalStateException(string.toString());
            }
            this.list.remove(this.lastIndex);
            this.index = this.lastIndex;
            this.lastIndex = -1;
        }
    }
}

