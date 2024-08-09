/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.system;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import org.lwjgl.system.Checks;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Pointer;
import org.lwjgl.system.Struct;

public abstract class StructBuffer<T extends Struct, SELF extends StructBuffer<T, SELF>>
extends CustomBuffer<SELF>
implements Iterable<T> {
    protected StructBuffer(ByteBuffer byteBuffer, int n) {
        super(MemoryUtil.memAddress(byteBuffer), byteBuffer, -1, 0, n, n);
    }

    protected StructBuffer(long l, @Nullable ByteBuffer byteBuffer, int n, int n2, int n3, int n4) {
        super(l, byteBuffer, n, n2, n3, n4);
    }

    @Override
    public int sizeof() {
        return ((Struct)this.getElementFactory()).sizeof();
    }

    public T get() {
        return ((Struct)this.getElementFactory()).wrap(this.address, this.nextGetIndex(), this.container);
    }

    public SELF get(T t) {
        int n = ((Struct)this.getElementFactory()).sizeof();
        MemoryUtil.memCopy(this.address + Integer.toUnsignedLong(this.nextGetIndex()) * (long)n, ((Pointer.Default)t).address(), n);
        return (SELF)((StructBuffer)this.self());
    }

    @Override
    public SELF put(T t) {
        int n = ((Struct)this.getElementFactory()).sizeof();
        MemoryUtil.memCopy(((Pointer.Default)t).address(), this.address + Integer.toUnsignedLong(this.nextPutIndex()) * (long)n, n);
        return (SELF)((StructBuffer)this.self());
    }

    public T get(int n) {
        return ((Struct)this.getElementFactory()).wrap(this.address, StructBuffer.check(n, this.limit), this.container);
    }

    public SELF get(int n, T t) {
        int n2 = ((Struct)this.getElementFactory()).sizeof();
        MemoryUtil.memCopy(this.address + Checks.check(n, this.limit) * (long)n2, ((Pointer.Default)t).address(), n2);
        return (SELF)((StructBuffer)this.self());
    }

    public SELF put(int n, T t) {
        int n2 = ((Struct)this.getElementFactory()).sizeof();
        MemoryUtil.memCopy(((Pointer.Default)t).address(), this.address + Checks.check(n, this.limit) * (long)n2, n2);
        return (SELF)((StructBuffer)this.self());
    }

    @Override
    public Iterator<T> iterator() {
        return new StructIterator(this.address, this.container, this.getElementFactory(), this.position, this.limit);
    }

    @Override
    public void forEach(Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        T t = this.getElementFactory();
        int n = this.limit;
        for (int i = this.position; i < n; ++i) {
            consumer.accept(((Struct)t).wrap(this.address, i, this.container));
        }
    }

    @Override
    public Spliterator<T> spliterator() {
        return new StructSpliterator(this.address, this.container, this.getElementFactory(), this.position, this.limit);
    }

    public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Stream<T> parallelStream() {
        return StreamSupport.stream(this.spliterator(), true);
    }

    protected abstract T getElementFactory();

    private static int check(int n, int n2) {
        if (Checks.CHECKS && (n < 0 || n2 <= n)) {
            throw new IndexOutOfBoundsException();
        }
        return n;
    }

    private static class StructSpliterator<T extends Struct, SELF extends StructBuffer<T, SELF>>
    implements Spliterator<T> {
        private long address;
        @Nullable
        private ByteBuffer container;
        private T factory;
        private int index;
        private int fence;

        StructSpliterator(long l, @Nullable ByteBuffer byteBuffer, T t, int n, int n2) {
            this.address = l;
            this.container = byteBuffer;
            this.factory = t;
            this.index = n;
            this.fence = n2;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> consumer) {
            Objects.requireNonNull(consumer);
            if (this.index < this.fence) {
                consumer.accept(((Struct)this.factory).wrap(this.address, this.index++, this.container));
                return false;
            }
            return true;
        }

        @Override
        @Nullable
        public Spliterator<T> trySplit() {
            StructSpliterator<T, SELF> structSpliterator;
            int n = this.index;
            int n2 = n + this.fence >>> 1;
            if (n < n2) {
                this.index = n2;
                StructSpliterator<T, SELF> structSpliterator2 = new StructSpliterator<T, SELF>(this.address, this.container, this.factory, n, this.index);
                structSpliterator = structSpliterator2;
            } else {
                structSpliterator = null;
            }
            return structSpliterator;
        }

        @Override
        public long estimateSize() {
            return this.fence - this.index;
        }

        @Override
        public int characteristics() {
            return 1;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            int n;
            Objects.requireNonNull(consumer);
            try {
                for (n = this.index; n < this.fence; ++n) {
                    consumer.accept(((Struct)this.factory).wrap(this.address, n, this.container));
                }
            } finally {
                this.index = n;
            }
        }

        @Override
        public Comparator<? super T> getComparator() {
            throw new IllegalStateException();
        }
    }

    private static class StructIterator<T extends Struct, SELF extends StructBuffer<T, SELF>>
    implements Iterator<T> {
        private long address;
        @Nullable
        private ByteBuffer container;
        private T factory;
        private int index;
        private int fence;

        StructIterator(long l, @Nullable ByteBuffer byteBuffer, T t, int n, int n2) {
            this.address = l;
            this.container = byteBuffer;
            this.factory = t;
            this.index = n;
            this.fence = n2;
        }

        @Override
        public boolean hasNext() {
            return this.index < this.fence;
        }

        @Override
        public T next() {
            if (Checks.CHECKS && this.fence <= this.index) {
                throw new NoSuchElementException();
            }
            return ((Struct)this.factory).wrap(this.address, this.index++, this.container);
        }

        @Override
        public void forEachRemaining(Consumer<? super T> consumer) {
            int n;
            Objects.requireNonNull(consumer);
            try {
                for (n = this.index; n < this.fence; ++n) {
                    consumer.accept(((Struct)this.factory).wrap(this.address, n, this.container));
                }
            } finally {
                this.index = n;
            }
        }

        @Override
        public Object next() {
            return this.next();
        }
    }
}

