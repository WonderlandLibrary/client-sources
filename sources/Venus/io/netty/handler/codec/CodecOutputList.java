/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import java.util.AbstractList;
import java.util.RandomAccess;

final class CodecOutputList
extends AbstractList<Object>
implements RandomAccess {
    private static final CodecOutputListRecycler NOOP_RECYCLER = new CodecOutputListRecycler(){

        @Override
        public void recycle(CodecOutputList codecOutputList) {
        }
    };
    private static final FastThreadLocal<CodecOutputLists> CODEC_OUTPUT_LISTS_POOL = new FastThreadLocal<CodecOutputLists>(){

        @Override
        protected CodecOutputLists initialValue() throws Exception {
            return new CodecOutputLists(16);
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    };
    private final CodecOutputListRecycler recycler;
    private int size;
    private Object[] array;
    private boolean insertSinceRecycled;

    static CodecOutputList newInstance() {
        return CODEC_OUTPUT_LISTS_POOL.get().getOrCreate();
    }

    private CodecOutputList(CodecOutputListRecycler codecOutputListRecycler, int n) {
        this.recycler = codecOutputListRecycler;
        this.array = new Object[n];
    }

    @Override
    public Object get(int n) {
        this.checkIndex(n);
        return this.array[n];
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean add(Object object) {
        ObjectUtil.checkNotNull(object, "element");
        try {
            this.insert(this.size, object);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            this.expandArray();
            this.insert(this.size, object);
        }
        ++this.size;
        return false;
    }

    @Override
    public Object set(int n, Object object) {
        ObjectUtil.checkNotNull(object, "element");
        this.checkIndex(n);
        Object object2 = this.array[n];
        this.insert(n, object);
        return object2;
    }

    @Override
    public void add(int n, Object object) {
        ObjectUtil.checkNotNull(object, "element");
        this.checkIndex(n);
        if (this.size == this.array.length) {
            this.expandArray();
        }
        if (n != this.size - 1) {
            System.arraycopy(this.array, n, this.array, n + 1, this.size - n);
        }
        this.insert(n, object);
        ++this.size;
    }

    @Override
    public Object remove(int n) {
        this.checkIndex(n);
        Object object = this.array[n];
        int n2 = this.size - n - 1;
        if (n2 > 0) {
            System.arraycopy(this.array, n + 1, this.array, n, n2);
        }
        this.array[--this.size] = null;
        return object;
    }

    @Override
    public void clear() {
        this.size = 0;
    }

    boolean insertSinceRecycled() {
        return this.insertSinceRecycled;
    }

    void recycle() {
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = null;
        }
        this.size = 0;
        this.insertSinceRecycled = false;
        this.recycler.recycle(this);
    }

    Object getUnsafe(int n) {
        return this.array[n];
    }

    private void checkIndex(int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void insert(int n, Object object) {
        this.array[n] = object;
        this.insertSinceRecycled = true;
    }

    private void expandArray() {
        int n = this.array.length << 1;
        if (n < 0) {
            throw new OutOfMemoryError();
        }
        Object[] objectArray = new Object[n];
        System.arraycopy(this.array, 0, objectArray, 0, this.array.length);
        this.array = objectArray;
    }

    CodecOutputList(CodecOutputListRecycler codecOutputListRecycler, int n, 1 var3_3) {
        this(codecOutputListRecycler, n);
    }

    static CodecOutputListRecycler access$100() {
        return NOOP_RECYCLER;
    }

    private static final class CodecOutputLists
    implements CodecOutputListRecycler {
        private final CodecOutputList[] elements;
        private final int mask;
        private int currentIdx;
        private int count;
        static final boolean $assertionsDisabled = !CodecOutputList.class.desiredAssertionStatus();

        CodecOutputLists(int n) {
            this.elements = new CodecOutputList[MathUtil.safeFindNextPositivePowerOfTwo(n)];
            for (int i = 0; i < this.elements.length; ++i) {
                this.elements[i] = new CodecOutputList(this, 16, null);
            }
            this.count = this.elements.length;
            this.currentIdx = this.elements.length;
            this.mask = this.elements.length - 1;
        }

        public CodecOutputList getOrCreate() {
            if (this.count == 0) {
                return new CodecOutputList(CodecOutputList.access$100(), 4, null);
            }
            --this.count;
            int n = this.currentIdx - 1 & this.mask;
            CodecOutputList codecOutputList = this.elements[n];
            this.currentIdx = n;
            return codecOutputList;
        }

        @Override
        public void recycle(CodecOutputList codecOutputList) {
            int n = this.currentIdx;
            this.elements[n] = codecOutputList;
            this.currentIdx = n + 1 & this.mask;
            ++this.count;
            if (!$assertionsDisabled && this.count > this.elements.length) {
                throw new AssertionError();
            }
        }
    }

    private static interface CodecOutputListRecycler {
        public void recycle(CodecOutputList var1);
    }
}

