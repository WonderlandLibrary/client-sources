/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.floats.AbstractFloatSet;
import it.unimi.dsi.fastutil.floats.FloatArrays;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FloatArraySet
extends AbstractFloatSet
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private transient float[] a;
    private int size;

    public FloatArraySet(float[] fArray) {
        this.a = fArray;
        this.size = fArray.length;
    }

    public FloatArraySet() {
        this.a = FloatArrays.EMPTY_ARRAY;
    }

    public FloatArraySet(int n) {
        this.a = new float[n];
    }

    public FloatArraySet(FloatCollection floatCollection) {
        this(floatCollection.size());
        this.addAll(floatCollection);
    }

    public FloatArraySet(Collection<? extends Float> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    public FloatArraySet(float[] fArray, int n) {
        this.a = fArray;
        this.size = n;
        if (n > fArray.length) {
            throw new IllegalArgumentException("The provided size (" + n + ") is larger than or equal to the array size (" + fArray.length + ")");
        }
    }

    private int findKey(float f) {
        int n = this.size;
        while (n-- != 0) {
            if (Float.floatToIntBits(this.a[n]) != Float.floatToIntBits(f)) continue;
            return n;
        }
        return 1;
    }

    @Override
    public FloatIterator iterator() {
        return new FloatIterator(this){
            int next;
            final FloatArraySet this$0;
            {
                this.this$0 = floatArraySet;
                this.next = 0;
            }

            @Override
            public boolean hasNext() {
                return this.next < FloatArraySet.access$000(this.this$0);
            }

            @Override
            public float nextFloat() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return FloatArraySet.access$100(this.this$0)[this.next++];
            }

            @Override
            public void remove() {
                int n = FloatArraySet.access$010(this.this$0) - this.next--;
                System.arraycopy(FloatArraySet.access$100(this.this$0), this.next + 1, FloatArraySet.access$100(this.this$0), this.next, n);
            }
        };
    }

    @Override
    public boolean contains(float f) {
        return this.findKey(f) != -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(float f) {
        int n = this.findKey(f);
        if (n == -1) {
            return true;
        }
        int n2 = this.size - n - 1;
        for (int i = 0; i < n2; ++i) {
            this.a[n + i] = this.a[n + i + 1];
        }
        --this.size;
        return false;
    }

    @Override
    public boolean add(float f) {
        int n = this.findKey(f);
        if (n != -1) {
            return true;
        }
        if (this.size == this.a.length) {
            float[] fArray = new float[this.size == 0 ? 2 : this.size * 2];
            int n2 = this.size;
            while (n2-- != 0) {
                fArray[n2] = this.a[n2];
            }
            this.a = fArray;
        }
        this.a[this.size++] = f;
        return false;
    }

    @Override
    public void clear() {
        this.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    public FloatArraySet clone() {
        FloatArraySet floatArraySet;
        try {
            floatArraySet = (FloatArraySet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        floatArraySet.a = (float[])this.a.clone();
        return floatArraySet;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        for (int i = 0; i < this.size; ++i) {
            objectOutputStream.writeFloat(this.a[i]);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new float[this.size];
        for (int i = 0; i < this.size; ++i) {
            this.a[i] = objectInputStream.readFloat();
        }
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    static int access$000(FloatArraySet floatArraySet) {
        return floatArraySet.size;
    }

    static float[] access$100(FloatArraySet floatArraySet) {
        return floatArraySet.a;
    }

    static int access$010(FloatArraySet floatArraySet) {
        return floatArraySet.size--;
    }
}

