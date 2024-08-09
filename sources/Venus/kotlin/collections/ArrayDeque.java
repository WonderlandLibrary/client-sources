/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.WasExperimental;
import kotlin.collections.AbstractList;
import kotlin.collections.AbstractMutableList;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u001b\b\u0007\u0018\u0000 P*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001PB\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0006B\u0015\b\u0016\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u00a2\u0006\u0002\u0010\tJ\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0016J\u001d\u0010\u0013\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0016\u0010\u001a\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0013\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u001cJ\u0013\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u001cJ\b\u0010\u001e\u001a\u00020\u0017H\u0016J\u0016\u0010\u001f\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010\u0016J\u001e\u0010 \u001a\u00020\u00172\u0006\u0010!\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0002J\u0010\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u0004H\u0002J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010%\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\u0004H\u0002J\u001d\u0010'\u001a\u00020\u00142\u0012\u0010(\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00140)H\u0082\bJ\u000b\u0010*\u001a\u00028\u0000\u00a2\u0006\u0002\u0010+J\r\u0010,\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010+J\u0016\u0010-\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0096\u0002\u00a2\u0006\u0002\u0010.J\u0010\u0010/\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u00100\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u00101J\u0016\u00102\u001a\u00028\u00002\u0006\u0010!\u001a\u00020\u0004H\u0083\b\u00a2\u0006\u0002\u0010.J\u0011\u0010!\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0083\bJM\u00103\u001a\u00020\u00172>\u00104\u001a:\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u000e\u0012\u001b\u0012\u0019\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000b\u00a2\u0006\f\b6\u0012\b\b7\u0012\u0004\b\b(\u0007\u0012\u0004\u0012\u00020\u001705H\u0000\u00a2\u0006\u0002\b8J\b\u00109\u001a\u00020\u0014H\u0016J\u000b\u0010:\u001a\u00028\u0000\u00a2\u0006\u0002\u0010+J\u0015\u0010;\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u00101J\r\u0010<\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010+J\u0010\u0010=\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0010\u0010>\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0004H\u0002J\u0015\u0010?\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010@\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u0015\u0010A\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0004H\u0016\u00a2\u0006\u0002\u0010.J\u000b\u0010B\u001a\u00028\u0000\u00a2\u0006\u0002\u0010+J\r\u0010C\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010+J\u000b\u0010D\u001a\u00028\u0000\u00a2\u0006\u0002\u0010+J\r\u0010E\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010+J\u0016\u0010F\u001a\u00020\u00142\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016J\u001e\u0010G\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010HJ\u0017\u0010I\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u0000\u00a2\u0006\u0004\bJ\u0010KJ)\u0010I\u001a\b\u0012\u0004\u0012\u0002HL0\u000b\"\u0004\b\u0001\u0010L2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002HL0\u000bH\u0000\u00a2\u0006\u0004\bJ\u0010NJ\u0015\u0010O\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bH\u0016\u00a2\u0006\u0002\u0010KJ'\u0010O\u001a\b\u0012\u0004\u0012\u0002HL0\u000b\"\u0004\b\u0001\u0010L2\f\u0010M\u001a\b\u0012\u0004\u0012\u0002HL0\u000bH\u0016\u00a2\u0006\u0002\u0010NR\u0018\u0010\n\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\f0\u000bX\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0004@RX\u0096\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006Q"}, d2={"Lkotlin/collections/ArrayDeque;", "E", "Lkotlin/collections/AbstractMutableList;", "initialCapacity", "", "(I)V", "()V", "elements", "", "(Ljava/util/Collection;)V", "elementData", "", "", "[Ljava/lang/Object;", "head", "<set-?>", "size", "getSize", "()I", "add", "", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "addFirst", "(Ljava/lang/Object;)V", "addLast", "clear", "contains", "copyCollectionElements", "internalIndex", "copyElements", "newCapacity", "decremented", "ensureCapacity", "minCapacity", "filterInPlace", "predicate", "Lkotlin/Function1;", "first", "()Ljava/lang/Object;", "firstOrNull", "get", "(I)Ljava/lang/Object;", "incremented", "indexOf", "(Ljava/lang/Object;)I", "internalGet", "internalStructure", "structure", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "internalStructure$kotlin_stdlib", "isEmpty", "last", "lastIndexOf", "lastOrNull", "negativeMod", "positiveMod", "remove", "removeAll", "removeAt", "removeFirst", "removeFirstOrNull", "removeLast", "removeLastOrNull", "retainAll", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "testToArray", "testToArray$kotlin_stdlib", "()[Ljava/lang/Object;", "T", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toArray", "Companion", "kotlin-stdlib"})
@SinceKotlin(version="1.4")
@WasExperimental(markerClass={ExperimentalStdlibApi.class})
@SourceDebugExtension(value={"SMAP\nArrayDeque.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ArrayDeque.kt\nkotlin/collections/ArrayDeque\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 3 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,583:1\n467#1,51:586\n467#1,51:637\n37#2,2:584\n26#3:688\n*S KotlinDebug\n*F\n+ 1 ArrayDeque.kt\nkotlin/collections/ArrayDeque\n*L\n462#1:586,51\n464#1:637,51\n47#1:584,2\n562#1:688\n*E\n"})
public final class ArrayDeque<E>
extends AbstractMutableList<E> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private int head;
    @NotNull
    private Object[] elementData;
    private int size;
    @NotNull
    private static final Object[] emptyElementData;
    private static final int maxArraySize = 0x7FFFFFF7;
    private static final int defaultMinCapacity = 10;

    @Override
    public int getSize() {
        return this.size;
    }

    public ArrayDeque(int n) {
        Object[] objectArray;
        if (n == 0) {
            objectArray = emptyElementData;
        } else if (n > 0) {
            objectArray = new Object[n];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + n);
        }
        this.elementData = objectArray;
    }

    public ArrayDeque() {
        this.elementData = emptyElementData;
    }

    public ArrayDeque(@NotNull Collection<? extends E> collection) {
        Intrinsics.checkNotNullParameter(collection, "elements");
        Collection<E> collection2 = collection;
        boolean bl = false;
        Collection<E> collection3 = collection2;
        this.elementData = collection3.toArray(new Object[0]);
        this.size = this.elementData.length;
        if (this.elementData.length == 0) {
            this.elementData = emptyElementData;
        }
    }

    private final void ensureCapacity(int n) {
        if (n < 0) {
            throw new IllegalStateException("Deque is too big.");
        }
        if (n <= this.elementData.length) {
            return;
        }
        if (this.elementData == emptyElementData) {
            this.elementData = new Object[RangesKt.coerceAtLeast(n, 10)];
            return;
        }
        int n2 = Companion.newCapacity$kotlin_stdlib(this.elementData.length, n);
        this.copyElements(n2);
    }

    private final void copyElements(int n) {
        Object[] objectArray = new Object[n];
        ArraysKt.copyInto(this.elementData, objectArray, 0, this.head, this.elementData.length);
        ArraysKt.copyInto(this.elementData, objectArray, this.elementData.length - this.head, 0, this.head);
        this.head = 0;
        this.elementData = objectArray;
    }

    @InlineOnly
    private final E internalGet(int n) {
        return (E)this.elementData[n];
    }

    private final int positiveMod(int n) {
        return n >= this.elementData.length ? n - this.elementData.length : n;
    }

    private final int negativeMod(int n) {
        return n < 0 ? n + this.elementData.length : n;
    }

    @InlineOnly
    private final int internalIndex(int n) {
        return this.positiveMod(this.head + n);
    }

    private final int incremented(int n) {
        return n == ArraysKt.getLastIndex(this.elementData) ? 0 : n + 1;
    }

    private final int decremented(int n) {
        return n == 0 ? ArraysKt.getLastIndex(this.elementData) : n - 1;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    public final E first() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        return (E)this.elementData[this.head];
    }

    @Nullable
    public final E firstOrNull() {
        return (E)(this.isEmpty() ? null : this.elementData[this.head]);
    }

    public final E last() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        ArrayDeque arrayDeque = this;
        return (E)this.elementData[arrayDeque.positiveMod(arrayDeque.head + CollectionsKt.getLastIndex(this))];
    }

    @Nullable
    public final E lastOrNull() {
        Object object;
        if (this.isEmpty()) {
            object = null;
        } else {
            ArrayDeque arrayDeque = this;
            object = this.elementData[arrayDeque.positiveMod(arrayDeque.head + CollectionsKt.getLastIndex(this))];
        }
        return (E)object;
    }

    public final void addFirst(E e) {
        this.ensureCapacity(this.size() + 1);
        this.head = this.decremented(this.head);
        this.elementData[this.head] = e;
        this.size = this.size() + 1;
    }

    public final void addLast(E e) {
        this.ensureCapacity(this.size() + 1);
        ArrayDeque arrayDeque = this;
        this.elementData[arrayDeque.positiveMod((int)(arrayDeque.head + this.size()))] = e;
        this.size = this.size() + 1;
    }

    public final E removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        Object object = this.elementData[this.head];
        this.elementData[this.head] = null;
        this.head = this.incremented(this.head);
        this.size = this.size() - 1;
        return (E)object;
    }

    @Nullable
    public final E removeFirstOrNull() {
        return this.isEmpty() ? null : (E)this.removeFirst();
    }

    public final E removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("ArrayDeque is empty.");
        }
        ArrayDeque arrayDeque = this;
        int n = arrayDeque.positiveMod(arrayDeque.head + CollectionsKt.getLastIndex(this));
        Object object = this.elementData[n];
        this.elementData[n] = null;
        this.size = this.size() - 1;
        return (E)object;
    }

    @Nullable
    public final E removeLastOrNull() {
        return this.isEmpty() ? null : (E)this.removeLast();
    }

    @Override
    public boolean add(E e) {
        this.addLast(e);
        return false;
    }

    @Override
    public void add(int n, E e) {
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(n, this.size());
        if (n == this.size()) {
            this.addLast(e);
            return;
        }
        if (n == 0) {
            this.addFirst(e);
            return;
        }
        this.ensureCapacity(this.size() + 1);
        ArrayDeque arrayDeque = this;
        int n2 = arrayDeque.positiveMod(arrayDeque.head + n);
        if (n < this.size() + 1 >> 1) {
            int n3 = this.decremented(n2);
            int n4 = this.decremented(this.head);
            if (n3 >= this.head) {
                this.elementData[n4] = this.elementData[this.head];
                ArraysKt.copyInto(this.elementData, this.elementData, this.head, this.head + 1, n3 + 1);
            } else {
                ArraysKt.copyInto(this.elementData, this.elementData, this.head - 1, this.head, this.elementData.length);
                this.elementData[this.elementData.length - 1] = this.elementData[0];
                ArraysKt.copyInto(this.elementData, this.elementData, 0, 1, n3 + 1);
            }
            this.elementData[n3] = e;
            this.head = n4;
        } else {
            ArrayDeque arrayDeque2 = this;
            int n5 = arrayDeque2.positiveMod(arrayDeque2.head + this.size());
            if (n2 < n5) {
                ArraysKt.copyInto(this.elementData, this.elementData, n2 + 1, n2, n5);
            } else {
                ArraysKt.copyInto(this.elementData, this.elementData, 1, 0, n5);
                this.elementData[0] = this.elementData[this.elementData.length - 1];
                ArraysKt.copyInto(this.elementData, this.elementData, n2 + 1, n2, this.elementData.length - 1);
            }
            this.elementData[n2] = e;
        }
        this.size = this.size() + 1;
    }

    private final void copyCollectionElements(int n, Collection<? extends E> collection) {
        int n2;
        Iterator<E> iterator2 = collection.iterator();
        int n3 = this.elementData.length;
        for (n2 = n; n2 < n3 && iterator2.hasNext(); ++n2) {
            this.elementData[n2] = iterator2.next();
        }
        n3 = this.head;
        for (n2 = 0; n2 < n3 && iterator2.hasNext(); ++n2) {
            this.elementData[n2] = iterator2.next();
        }
        this.size = this.size() + collection.size();
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> collection) {
        Intrinsics.checkNotNullParameter(collection, "elements");
        if (collection.isEmpty()) {
            return true;
        }
        this.ensureCapacity(this.size() + collection.size());
        ArrayDeque arrayDeque = this;
        this.copyCollectionElements(arrayDeque.positiveMod(arrayDeque.head + this.size()), collection);
        return false;
    }

    @Override
    public boolean addAll(int n, @NotNull Collection<? extends E> collection) {
        Intrinsics.checkNotNullParameter(collection, "elements");
        AbstractList.Companion.checkPositionIndex$kotlin_stdlib(n, this.size());
        if (collection.isEmpty()) {
            return true;
        }
        if (n == this.size()) {
            return this.addAll(collection);
        }
        this.ensureCapacity(this.size() + collection.size());
        ArrayDeque arrayDeque = this;
        int n2 = arrayDeque.positiveMod(arrayDeque.head + this.size());
        ArrayDeque arrayDeque2 = this;
        int n3 = arrayDeque2.positiveMod(arrayDeque2.head + n);
        int n4 = collection.size();
        if (n < this.size() + 1 >> 1) {
            int n5 = this.head - n4;
            if (n3 >= this.head) {
                if (n5 >= 0) {
                    ArraysKt.copyInto(this.elementData, this.elementData, n5, this.head, n3);
                } else {
                    int n6 = this.elementData.length - (n5 += this.elementData.length);
                    int n7 = n3 - this.head;
                    if (n6 >= n7) {
                        ArraysKt.copyInto(this.elementData, this.elementData, n5, this.head, n3);
                    } else {
                        ArraysKt.copyInto(this.elementData, this.elementData, n5, this.head, this.head + n6);
                        ArraysKt.copyInto(this.elementData, this.elementData, 0, this.head + n6, n3);
                    }
                }
            } else {
                ArraysKt.copyInto(this.elementData, this.elementData, n5, this.head, this.elementData.length);
                if (n4 >= n3) {
                    ArraysKt.copyInto(this.elementData, this.elementData, this.elementData.length - n4, 0, n3);
                } else {
                    ArraysKt.copyInto(this.elementData, this.elementData, this.elementData.length - n4, 0, n4);
                    ArraysKt.copyInto(this.elementData, this.elementData, 0, n4, n3);
                }
            }
            this.head = n5;
            this.copyCollectionElements(this.negativeMod(n3 - n4), collection);
        } else {
            int n8 = n3 + n4;
            if (n3 < n2) {
                if (n2 + n4 <= this.elementData.length) {
                    ArraysKt.copyInto(this.elementData, this.elementData, n8, n3, n2);
                } else if (n8 >= this.elementData.length) {
                    ArraysKt.copyInto(this.elementData, this.elementData, n8 - this.elementData.length, n3, n2);
                } else {
                    int n9 = n2 + n4 - this.elementData.length;
                    ArraysKt.copyInto(this.elementData, this.elementData, 0, n2 - n9, n2);
                    ArraysKt.copyInto(this.elementData, this.elementData, n8, n3, n2 - n9);
                }
            } else {
                ArraysKt.copyInto(this.elementData, this.elementData, n4, 0, n2);
                if (n8 >= this.elementData.length) {
                    ArraysKt.copyInto(this.elementData, this.elementData, n8 - this.elementData.length, n3, this.elementData.length);
                } else {
                    ArraysKt.copyInto(this.elementData, this.elementData, 0, this.elementData.length - n4, this.elementData.length);
                    ArraysKt.copyInto(this.elementData, this.elementData, n8, n3, this.elementData.length - n4);
                }
            }
            this.copyCollectionElements(n3, collection);
        }
        return false;
    }

    @Override
    public E get(int n) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.size());
        ArrayDeque arrayDeque = this;
        return (E)this.elementData[arrayDeque.positiveMod(arrayDeque.head + n)];
    }

    @Override
    public E set(int n, E e) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.size());
        ArrayDeque arrayDeque = this;
        int n2 = arrayDeque.positiveMod(arrayDeque.head + n);
        Object object = this.elementData[n2];
        this.elementData[n2] = e;
        return (E)object;
    }

    @Override
    public boolean contains(Object object) {
        return this.indexOf(object) != -1;
    }

    @Override
    public int indexOf(Object object) {
        block4: {
            int n;
            int n2;
            block3: {
                ArrayDeque arrayDeque = this;
                n2 = arrayDeque.positiveMod(arrayDeque.head + this.size());
                if (this.head >= n2) break block3;
                for (int i = this.head; i < n2; ++i) {
                    if (!Intrinsics.areEqual(object, this.elementData[i])) continue;
                    return i - this.head;
                }
                break block4;
            }
            if (this.head < n2) break block4;
            int n3 = this.elementData.length;
            for (n = this.head; n < n3; ++n) {
                if (!Intrinsics.areEqual(object, this.elementData[n])) continue;
                return n - this.head;
            }
            for (n = 0; n < n2; ++n) {
                if (!Intrinsics.areEqual(object, this.elementData[n])) continue;
                return n + this.elementData.length - this.head;
            }
        }
        return 1;
    }

    @Override
    public int lastIndexOf(Object object) {
        ArrayDeque arrayDeque = this;
        int n = arrayDeque.positiveMod(arrayDeque.head + this.size());
        if (this.head < n) {
            int n2 = this.head;
            int n3 = n - 1;
            if (n2 <= n3) {
                while (true) {
                    if (Intrinsics.areEqual(object, this.elementData[n3])) {
                        return n3 - this.head;
                    }
                    if (n3 != n2) {
                        --n3;
                        continue;
                    }
                    break;
                }
            }
        } else if (this.head > n) {
            int n4;
            for (n4 = n - 1; -1 < n4; --n4) {
                if (!Intrinsics.areEqual(object, this.elementData[n4])) continue;
                return n4 + this.elementData.length - this.head;
            }
            int n5 = this.head;
            n4 = ArraysKt.getLastIndex(this.elementData);
            if (n5 <= n4) {
                while (true) {
                    if (Intrinsics.areEqual(object, this.elementData[n4])) {
                        return n4 - this.head;
                    }
                    if (n4 == n5) break;
                    --n4;
                }
            }
        }
        return 1;
    }

    @Override
    public boolean remove(Object object) {
        int n = this.indexOf(object);
        if (n == -1) {
            return true;
        }
        this.remove(n);
        return false;
    }

    @Override
    public E removeAt(int n) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.size());
        if (n == CollectionsKt.getLastIndex(this)) {
            return this.removeLast();
        }
        if (n == 0) {
            return this.removeFirst();
        }
        ArrayDeque arrayDeque = this;
        int n2 = arrayDeque.positiveMod(arrayDeque.head + n);
        Object object = this.elementData[n2];
        if (n < this.size() >> 1) {
            if (n2 >= this.head) {
                ArraysKt.copyInto(this.elementData, this.elementData, this.head + 1, this.head, n2);
            } else {
                ArraysKt.copyInto(this.elementData, this.elementData, 1, 0, n2);
                this.elementData[0] = this.elementData[this.elementData.length - 1];
                ArraysKt.copyInto(this.elementData, this.elementData, this.head + 1, this.head, this.elementData.length - 1);
            }
            this.elementData[this.head] = null;
            this.head = this.incremented(this.head);
        } else {
            ArrayDeque arrayDeque2 = this;
            int n3 = arrayDeque2.positiveMod(arrayDeque2.head + CollectionsKt.getLastIndex(this));
            if (n2 <= n3) {
                ArraysKt.copyInto(this.elementData, this.elementData, n2, n2 + 1, n3 + 1);
            } else {
                ArraysKt.copyInto(this.elementData, this.elementData, n2, n2 + 1, this.elementData.length);
                this.elementData[this.elementData.length - 1] = this.elementData[0];
                ArraysKt.copyInto(this.elementData, this.elementData, 0, 1, n3 + 1);
            }
            this.elementData[n3] = null;
        }
        this.size = this.size() - 1;
        return (E)object;
    }

    @Override
    public boolean removeAll(@NotNull Collection<? extends Object> collection) {
        boolean bl;
        Intrinsics.checkNotNullParameter(collection, "elements");
        ArrayDeque arrayDeque = this;
        boolean bl2 = false;
        if (arrayDeque.isEmpty() || arrayDeque.elementData.length == 0) {
            bl = false;
        } else {
            ArrayDeque arrayDeque2 = arrayDeque;
            int n = arrayDeque2.positiveMod(arrayDeque2.head + arrayDeque.size());
            int n2 = arrayDeque.head;
            boolean bl3 = false;
            if (arrayDeque.head < n) {
                for (int i = arrayDeque.head; i < n; ++i) {
                    Object object;
                    Object object2 = object = arrayDeque.elementData[i];
                    boolean bl4 = false;
                    if (!collection.contains(object2)) {
                        arrayDeque.elementData[n2++] = object;
                        continue;
                    }
                    bl3 = true;
                }
                ArraysKt.fill(arrayDeque.elementData, null, n2, n);
            } else {
                boolean bl5;
                Object object;
                int n3;
                int n4 = arrayDeque.elementData.length;
                for (n3 = arrayDeque.head; n3 < n4; ++n3) {
                    Object object3 = arrayDeque.elementData[n3];
                    arrayDeque.elementData[n3] = null;
                    object = object3;
                    bl5 = false;
                    if (!collection.contains(object)) {
                        arrayDeque.elementData[n2++] = object3;
                        continue;
                    }
                    bl3 = true;
                }
                n2 = arrayDeque.positiveMod(n2);
                for (n3 = 0; n3 < n; ++n3) {
                    Object object4 = arrayDeque.elementData[n3];
                    arrayDeque.elementData[n3] = null;
                    object = object4;
                    bl5 = false;
                    if (!collection.contains(object)) {
                        arrayDeque.elementData[n2] = object4;
                        n2 = arrayDeque.incremented(n2);
                        continue;
                    }
                    bl3 = true;
                }
            }
            if (bl3) {
                arrayDeque.size = arrayDeque.negativeMod(n2 - arrayDeque.head);
            }
            bl = bl3;
        }
        return bl;
    }

    @Override
    public boolean retainAll(@NotNull Collection<? extends Object> collection) {
        boolean bl;
        Intrinsics.checkNotNullParameter(collection, "elements");
        ArrayDeque arrayDeque = this;
        boolean bl2 = false;
        if (arrayDeque.isEmpty() || arrayDeque.elementData.length == 0) {
            bl = false;
        } else {
            ArrayDeque arrayDeque2 = arrayDeque;
            int n = arrayDeque2.positiveMod(arrayDeque2.head + arrayDeque.size());
            int n2 = arrayDeque.head;
            boolean bl3 = false;
            if (arrayDeque.head < n) {
                for (int i = arrayDeque.head; i < n; ++i) {
                    Object object;
                    Object object2 = object = arrayDeque.elementData[i];
                    boolean bl4 = false;
                    if (collection.contains(object2)) {
                        arrayDeque.elementData[n2++] = object;
                        continue;
                    }
                    bl3 = true;
                }
                ArraysKt.fill(arrayDeque.elementData, null, n2, n);
            } else {
                boolean bl5;
                Object object;
                int n3;
                int n4 = arrayDeque.elementData.length;
                for (n3 = arrayDeque.head; n3 < n4; ++n3) {
                    Object object3 = arrayDeque.elementData[n3];
                    arrayDeque.elementData[n3] = null;
                    object = object3;
                    bl5 = false;
                    if (collection.contains(object)) {
                        arrayDeque.elementData[n2++] = object3;
                        continue;
                    }
                    bl3 = true;
                }
                n2 = arrayDeque.positiveMod(n2);
                for (n3 = 0; n3 < n; ++n3) {
                    Object object4 = arrayDeque.elementData[n3];
                    arrayDeque.elementData[n3] = null;
                    object = object4;
                    bl5 = false;
                    if (collection.contains(object)) {
                        arrayDeque.elementData[n2] = object4;
                        n2 = arrayDeque.incremented(n2);
                        continue;
                    }
                    bl3 = true;
                }
            }
            if (bl3) {
                arrayDeque.size = arrayDeque.negativeMod(n2 - arrayDeque.head);
            }
            bl = bl3;
        }
        return bl;
    }

    private final boolean filterInPlace(Function1<? super E, Boolean> function1) {
        boolean bl = false;
        if (this.isEmpty() || this.elementData.length == 0) {
            return true;
        }
        ArrayDeque arrayDeque = this;
        int n = arrayDeque.positiveMod(arrayDeque.head + this.size());
        int n2 = this.head;
        boolean bl2 = false;
        if (this.head < n) {
            for (int i = this.head; i < n; ++i) {
                Object object = this.elementData[i];
                if (function1.invoke(object).booleanValue()) {
                    this.elementData[n2++] = object;
                    continue;
                }
                bl2 = true;
            }
            ArraysKt.fill(this.elementData, null, n2, n);
        } else {
            int n3;
            int n4 = this.elementData.length;
            for (n3 = this.head; n3 < n4; ++n3) {
                Object object = this.elementData[n3];
                this.elementData[n3] = null;
                if (function1.invoke(object).booleanValue()) {
                    this.elementData[n2++] = object;
                    continue;
                }
                bl2 = true;
            }
            n2 = this.positiveMod(n2);
            for (n3 = 0; n3 < n; ++n3) {
                Object object = this.elementData[n3];
                this.elementData[n3] = null;
                if (function1.invoke(object).booleanValue()) {
                    this.elementData[n2] = object;
                    n2 = this.incremented(n2);
                    continue;
                }
                bl2 = true;
            }
        }
        if (bl2) {
            this.size = this.negativeMod(n2 - this.head);
        }
        return bl2;
    }

    @Override
    public void clear() {
        ArrayDeque arrayDeque = this;
        int n = arrayDeque.positiveMod(arrayDeque.head + this.size());
        if (this.head < n) {
            ArraysKt.fill(this.elementData, null, this.head, n);
        } else if (!((Collection)this).isEmpty()) {
            ArraysKt.fill(this.elementData, null, this.head, this.elementData.length);
            ArraysKt.fill(this.elementData, null, 0, n);
        }
        this.head = 0;
        this.size = 0;
    }

    @Override
    @NotNull
    public <T> T[] toArray(@NotNull T[] TArray) {
        Intrinsics.checkNotNullParameter(TArray, "array");
        Object[] objectArray = TArray.length >= this.size() ? TArray : ArraysKt.arrayOfNulls(TArray, this.size());
        ArrayDeque arrayDeque = this;
        int n = arrayDeque.positiveMod(arrayDeque.head + this.size());
        if (this.head < n) {
            ArraysKt.copyInto$default(this.elementData, objectArray, 0, this.head, n, 2, null);
        } else if (!((Collection)this).isEmpty()) {
            ArraysKt.copyInto(this.elementData, objectArray, 0, this.head, this.elementData.length);
            ArraysKt.copyInto(this.elementData, objectArray, this.elementData.length - this.head, 0, n);
        }
        if (objectArray.length > this.size()) {
            objectArray[this.size()] = null;
        }
        return objectArray;
    }

    @Override
    @NotNull
    public Object[] toArray() {
        return this.toArray(new Object[this.size()]);
    }

    @NotNull
    public final <T> T[] testToArray$kotlin_stdlib(@NotNull T[] TArray) {
        Intrinsics.checkNotNullParameter(TArray, "array");
        return this.toArray(TArray);
    }

    @NotNull
    public final Object[] testToArray$kotlin_stdlib() {
        return this.toArray();
    }

    public final void internalStructure$kotlin_stdlib(@NotNull Function2<? super Integer, ? super Object[], Unit> function2) {
        Intrinsics.checkNotNullParameter(function2, "structure");
        ArrayDeque arrayDeque = this;
        int n = arrayDeque.positiveMod(arrayDeque.head + this.size());
        int n2 = this.isEmpty() || this.head < n ? this.head : this.head - this.elementData.length;
        function2.invoke((Integer)n2, (Object[])this.toArray());
    }

    static {
        boolean bl = false;
        emptyElementData = new Object[0];
    }

    @Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0007\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001d\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0000\u00a2\u0006\u0002\b\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0018\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2={"Lkotlin/collections/ArrayDeque$Companion;", "", "()V", "defaultMinCapacity", "", "emptyElementData", "", "[Ljava/lang/Object;", "maxArraySize", "newCapacity", "oldCapacity", "minCapacity", "newCapacity$kotlin_stdlib", "kotlin-stdlib"})
    public static final class Companion {
        private Companion() {
        }

        public final int newCapacity$kotlin_stdlib(int n, int n2) {
            int n3 = n + (n >> 1);
            if (n3 - n2 < 0) {
                n3 = n2;
            }
            if (n3 - 0x7FFFFFF7 > 0) {
                n3 = n2 > 0x7FFFFFF7 ? Integer.MAX_VALUE : 0x7FFFFFF7;
            }
            return n3;
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}

