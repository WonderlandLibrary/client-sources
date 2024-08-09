/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.collections.AbstractIterator;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\b\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\u000f\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007B\u001d\u0012\u000e\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\fJ\u0013\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0016J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u0019\u001a\u00028\u00002\u0006\u0010\u001a\u001a\u00020\u0006H\u0096\u0002\u00a2\u0006\u0002\u0010\u001bJ\u0006\u0010\u001c\u001a\u00020\u001dJ\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u001fH\u0096\u0002J\u000e\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\u0006J\u0015\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tH\u0014\u00a2\u0006\u0002\u0010#J'\u0010\"\u001a\b\u0012\u0004\u0012\u0002H\u00010\t\"\u0004\b\u0001\u0010\u00012\f\u0010$\u001a\b\u0012\u0004\u0012\u0002H\u00010\tH\u0014\u00a2\u0006\u0002\u0010%J\u0015\u0010&\u001a\u00020\u0006*\u00020\u00062\u0006\u0010!\u001a\u00020\u0006H\u0082\bR\u0018\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u0006@RX\u0096\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006'"}, d2={"Lkotlin/collections/RingBuffer;", "T", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "capacity", "", "(I)V", "buffer", "", "", "filledSize", "([Ljava/lang/Object;I)V", "[Ljava/lang/Object;", "<set-?>", "size", "getSize", "()I", "startIndex", "add", "", "element", "(Ljava/lang/Object;)V", "expanded", "maxCapacity", "get", "index", "(I)Ljava/lang/Object;", "isFull", "", "iterator", "", "removeFirst", "n", "toArray", "()[Ljava/lang/Object;", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "forward", "kotlin-stdlib"})
@SourceDebugExtension(value={"SMAP\nSlidingWindow.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SlidingWindow.kt\nkotlin/collections/RingBuffer\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,207:1\n205#1:209\n205#1:210\n205#1:211\n1#2:208\n*S KotlinDebug\n*F\n+ 1 SlidingWindow.kt\nkotlin/collections/RingBuffer\n*L\n106#1:209\n176#1:210\n189#1:211\n*E\n"})
final class RingBuffer<T>
extends AbstractList<T>
implements RandomAccess {
    @NotNull
    private final Object[] buffer;
    private final int capacity;
    private int startIndex;
    private int size;

    public RingBuffer(@NotNull Object[] objectArray, int n) {
        boolean bl;
        Intrinsics.checkNotNullParameter(objectArray, "buffer");
        this.buffer = objectArray;
        boolean bl2 = bl = n >= 0;
        if (!bl) {
            boolean bl3 = false;
            String string = "ring buffer filled size should not be negative but it is " + n;
            throw new IllegalArgumentException(string.toString());
        }
        boolean bl4 = bl = n <= this.buffer.length;
        if (!bl) {
            boolean bl5 = false;
            String string = "ring buffer filled size: " + n + " cannot be larger than the buffer size: " + this.buffer.length;
            throw new IllegalArgumentException(string.toString());
        }
        this.capacity = this.buffer.length;
        this.size = n;
    }

    public RingBuffer(int n) {
        this(new Object[n], 0);
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public T get(int n) {
        AbstractList.Companion.checkElementIndex$kotlin_stdlib(n, this.size());
        RingBuffer ringBuffer = this;
        int n2 = this.startIndex;
        boolean bl = false;
        return (T)this.buffer[(n2 + n) % RingBuffer.access$getCapacity$p(ringBuffer)];
    }

    public final boolean isFull() {
        return this.size() == this.capacity;
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new AbstractIterator<T>(this){
            private int count;
            private int index;
            final RingBuffer<T> this$0;
            {
                this.this$0 = ringBuffer;
                this.count = ringBuffer.size();
                this.index = RingBuffer.access$getStartIndex$p(ringBuffer);
            }

            protected void computeNext() {
                if (this.count == 0) {
                    this.done();
                } else {
                    this.setNext(RingBuffer.access$getBuffer$p(this.this$0)[this.index]);
                    RingBuffer<T> ringBuffer = this.this$0;
                    int n = this.index;
                    int n2 = 1;
                    boolean bl = false;
                    this.index = (n + n2) % RingBuffer.access$getCapacity$p(ringBuffer);
                    int n3 = this.count;
                    this.count = n3 + -1;
                }
            }
        };
    }

    @Override
    @NotNull
    public <T> T[] toArray(@NotNull T[] TArray) {
        int n;
        T[] TArray2;
        Intrinsics.checkNotNullParameter(TArray, "array");
        if (TArray.length < this.size()) {
            T[] TArray3 = Arrays.copyOf(TArray, this.size());
            TArray2 = TArray3;
            Intrinsics.checkNotNullExpressionValue(TArray3, "copyOf(this, newSize)");
        } else {
            TArray2 = TArray;
        }
        T[] TArray4 = TArray2;
        int n2 = this.size();
        int n3 = 0;
        for (n = this.startIndex; n3 < n2 && n < this.capacity; ++n3, ++n) {
            TArray4[n3] = this.buffer[n];
        }
        n = 0;
        while (n3 < n2) {
            TArray4[n3] = this.buffer[n];
            ++n3;
            ++n;
        }
        if (TArray4.length > this.size()) {
            TArray4[this.size()] = null;
        }
        return TArray4;
    }

    @Override
    @NotNull
    public Object[] toArray() {
        return this.toArray(new Object[this.size()]);
    }

    @NotNull
    public final RingBuffer<T> expanded(int n) {
        Object[] objectArray;
        int n2 = RangesKt.coerceAtMost(this.capacity + (this.capacity >> 1) + 1, n);
        if (this.startIndex == 0) {
            Object[] objectArray2 = Arrays.copyOf(this.buffer, n2);
            objectArray = objectArray2;
            Intrinsics.checkNotNullExpressionValue(objectArray2, "copyOf(this, newSize)");
        } else {
            objectArray = this.toArray(new Object[n2]);
        }
        Object[] objectArray3 = objectArray;
        return new RingBuffer<T>(objectArray3, this.size());
    }

    public final void add(T t) {
        if (this.isFull()) {
            throw new IllegalStateException("ring buffer is full");
        }
        RingBuffer ringBuffer = this;
        int n = this.startIndex;
        int n2 = this.size();
        boolean bl = false;
        this.buffer[(n + n2) % RingBuffer.access$getCapacity$p((RingBuffer)ringBuffer)] = t;
        int n3 = this.size();
        this.size = n3 + 1;
    }

    public final void removeFirst(int n) {
        int n2;
        int n3 = n2 = n >= 0 ? 1 : 0;
        if (n2 == 0) {
            boolean bl = false;
            String string = "n shouldn't be negative but it is " + n;
            throw new IllegalArgumentException(string.toString());
        }
        int n4 = n2 = n <= this.size() ? 1 : 0;
        if (n2 == 0) {
            boolean bl = false;
            String string = "n shouldn't be greater than the buffer size: n = " + n + ", size = " + this.size();
            throw new IllegalArgumentException(string.toString());
        }
        if (n > 0) {
            n2 = this.startIndex;
            RingBuffer ringBuffer = this;
            int n5 = n2;
            boolean bl = false;
            int n6 = (n5 + n) % RingBuffer.access$getCapacity$p(ringBuffer);
            if (n2 > n6) {
                ArraysKt.fill(this.buffer, null, n2, this.capacity);
                ArraysKt.fill(this.buffer, null, 0, n6);
            } else {
                ArraysKt.fill(this.buffer, null, n2, n6);
            }
            this.startIndex = n6;
            this.size = this.size() - n;
        }
    }

    private final int forward(int n, int n2) {
        boolean bl = false;
        return (n + n2) % RingBuffer.access$getCapacity$p(this);
    }

    public static final int access$getStartIndex$p(RingBuffer ringBuffer) {
        return ringBuffer.startIndex;
    }

    public static final Object[] access$getBuffer$p(RingBuffer ringBuffer) {
        return ringBuffer.buffer;
    }

    public static final int access$getCapacity$p(RingBuffer ringBuffer) {
        return ringBuffer.capacity;
    }
}

