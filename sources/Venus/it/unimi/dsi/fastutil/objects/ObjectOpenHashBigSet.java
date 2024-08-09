/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.objects;

import it.unimi.dsi.fastutil.Hash;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.objects.AbstractObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import it.unimi.dsi.fastutil.objects.ObjectBigArrays;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ObjectOpenHashBigSet<K>
extends AbstractObjectSet<K>
implements Serializable,
Cloneable,
Hash,
Size64 {
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient K[][] key;
    protected transient long mask;
    protected transient int segmentMask;
    protected transient int baseMask;
    protected transient boolean containsNull;
    protected transient long n;
    protected transient long maxFill;
    protected final transient long minN;
    protected final float f;
    protected long size;

    private void initMasks() {
        this.mask = this.n - 1L;
        this.segmentMask = this.key[0].length - 1;
        this.baseMask = this.key.length - 1;
    }

    public ObjectOpenHashBigSet(long l, float f) {
        if (f <= 0.0f || f > 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than or equal to 1");
        }
        if (this.n < 0L) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        this.minN = this.n = HashCommon.bigArraySize(l, f);
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = ObjectBigArrays.newBigArray(this.n);
        this.initMasks();
    }

    public ObjectOpenHashBigSet(long l) {
        this(l, 0.75f);
    }

    public ObjectOpenHashBigSet() {
        this(16L, 0.75f);
    }

    public ObjectOpenHashBigSet(Collection<? extends K> collection, float f) {
        this(collection.size(), f);
        this.addAll(collection);
    }

    public ObjectOpenHashBigSet(Collection<? extends K> collection) {
        this(collection, 0.75f);
    }

    public ObjectOpenHashBigSet(ObjectCollection<? extends K> objectCollection, float f) {
        this(objectCollection.size(), f);
        this.addAll(objectCollection);
    }

    public ObjectOpenHashBigSet(ObjectCollection<? extends K> objectCollection) {
        this(objectCollection, 0.75f);
    }

    public ObjectOpenHashBigSet(Iterator<? extends K> iterator2, float f) {
        this(16L, f);
        while (iterator2.hasNext()) {
            this.add(iterator2.next());
        }
    }

    public ObjectOpenHashBigSet(Iterator<? extends K> iterator2) {
        this(iterator2, 0.75f);
    }

    public ObjectOpenHashBigSet(K[] KArray, int n, int n2, float f) {
        this(n2 < 0 ? 0L : (long)n2, f);
        ObjectArrays.ensureOffsetLength(KArray, n, n2);
        for (int i = 0; i < n2; ++i) {
            this.add(KArray[n + i]);
        }
    }

    public ObjectOpenHashBigSet(K[] KArray, int n, int n2) {
        this(KArray, n, n2, 0.75f);
    }

    public ObjectOpenHashBigSet(K[] KArray, float f) {
        this(KArray, 0, KArray.length, f);
    }

    public ObjectOpenHashBigSet(K[] KArray) {
        this(KArray, 0.75f);
    }

    private long realSize() {
        return this.containsNull ? this.size - 1L : this.size;
    }

    private void ensureCapacity(long l) {
        long l2 = HashCommon.bigArraySize(l, this.f);
        if (l2 > this.n) {
            this.rehash(l2);
        }
    }

    @Override
    public boolean addAll(Collection<? extends K> collection) {
        long l;
        long l2 = l = collection instanceof Size64 ? ((Size64)((Object)collection)).size64() : (long)collection.size();
        if ((double)this.f <= 0.5) {
            this.ensureCapacity(l);
        } else {
            this.ensureCapacity(this.size64() + l);
        }
        return super.addAll(collection);
    }

    @Override
    public boolean add(K k) {
        if (k == null) {
            if (this.containsNull) {
                return true;
            }
            this.containsNull = true;
        } else {
            int n;
            K[][] KArray = this.key;
            long l = HashCommon.mix((long)k.hashCode());
            int n2 = (int)((l & this.mask) >>> 27);
            K k2 = KArray[n2][n = (int)(l & (long)this.segmentMask)];
            if (k2 != null) {
                if (k2.equals(k)) {
                    return true;
                }
                while ((k2 = KArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != null) {
                    if (!k2.equals(k)) continue;
                    return true;
                }
            }
            KArray[n2][n] = k;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(2L * this.n);
        }
        return false;
    }

    public K addOrGet(K k) {
        if (k == null) {
            if (this.containsNull) {
                return null;
            }
            this.containsNull = true;
        } else {
            int n;
            K[][] KArray = this.key;
            long l = HashCommon.mix((long)k.hashCode());
            int n2 = (int)((l & this.mask) >>> 27);
            K k2 = KArray[n2][n = (int)(l & (long)this.segmentMask)];
            if (k2 != null) {
                if (k2.equals(k)) {
                    return k2;
                }
                while ((k2 = KArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != null) {
                    if (!k2.equals(k)) continue;
                    return k2;
                }
            }
            KArray[n2][n] = k;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(2L * this.n);
        }
        return k;
    }

    protected final void shiftKeys(long l) {
        K[][] KArray = this.key;
        while (true) {
            long l2 = l;
            l = l2 + 1L & this.mask;
            while (true) {
                if (ObjectBigArrays.get(KArray, l) == null) {
                    ObjectBigArrays.set(KArray, l2, null);
                    return;
                }
                long l3 = HashCommon.mix((long)ObjectBigArrays.get(KArray, l).hashCode()) & this.mask;
                if (l2 <= l ? l2 >= l3 || l3 > l : l2 >= l3 && l3 > l) break;
                l = l + 1L & this.mask;
            }
            ObjectBigArrays.set(KArray, l2, ObjectBigArrays.get(KArray, l));
        }
    }

    private boolean removeEntry(int n, int n2) {
        --this.size;
        this.shiftKeys((long)n * 0x8000000L + (long)n2);
        if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) {
            this.rehash(this.n / 2L);
        }
        return false;
    }

    private boolean removeNullEntry() {
        this.containsNull = false;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4L && this.n > 16L) {
            this.rehash(this.n / 2L);
        }
        return false;
    }

    @Override
    public boolean remove(Object object) {
        int n;
        if (object == null) {
            if (this.containsNull) {
                return this.removeNullEntry();
            }
            return true;
        }
        K[][] KArray = this.key;
        long l = HashCommon.mix((long)object.hashCode());
        int n2 = (int)((l & this.mask) >>> 27);
        K k = KArray[n2][n = (int)(l & (long)this.segmentMask)];
        if (k == null) {
            return true;
        }
        if (k.equals(object)) {
            return this.removeEntry(n2, n);
        }
        do {
            if ((k = KArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != null) continue;
            return true;
        } while (!k.equals(object));
        return this.removeEntry(n2, n);
    }

    @Override
    public boolean contains(Object object) {
        int n;
        if (object == null) {
            return this.containsNull;
        }
        K[][] KArray = this.key;
        long l = HashCommon.mix((long)object.hashCode());
        int n2 = (int)((l & this.mask) >>> 27);
        K k = KArray[n2][n = (int)(l & (long)this.segmentMask)];
        if (k == null) {
            return true;
        }
        if (k.equals(object)) {
            return false;
        }
        do {
            if ((k = KArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != null) continue;
            return true;
        } while (!k.equals(object));
        return false;
    }

    public K get(Object object) {
        int n;
        if (object == null) {
            return null;
        }
        K[][] KArray = this.key;
        long l = HashCommon.mix((long)object.hashCode());
        int n2 = (int)((l & this.mask) >>> 27);
        K k = KArray[n2][n = (int)(l & (long)this.segmentMask)];
        if (k == null) {
            return null;
        }
        if (k.equals(object)) {
            return k;
        }
        do {
            if ((k = KArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n]) != null) continue;
            return null;
        } while (!k.equals(object));
        return k;
    }

    @Override
    public void clear() {
        if (this.size == 0L) {
            return;
        }
        this.size = 0L;
        this.containsNull = false;
        ObjectBigArrays.fill(this.key, null);
    }

    @Override
    public ObjectIterator<K> iterator() {
        return new SetIterator(this, null);
    }

    public boolean trim() {
        long l = HashCommon.bigArraySize(this.size, this.f);
        if (l >= this.n || this.size > HashCommon.maxFill(l, this.f)) {
            return false;
        }
        try {
            this.rehash(l);
        } catch (OutOfMemoryError outOfMemoryError) {
            return true;
        }
        return false;
    }

    public boolean trim(long l) {
        long l2 = HashCommon.bigArraySize(l, this.f);
        if (this.n <= l2) {
            return false;
        }
        try {
            this.rehash(l2);
        } catch (OutOfMemoryError outOfMemoryError) {
            return true;
        }
        return false;
    }

    protected void rehash(long l) {
        K[][] KArray = this.key;
        Object[][] objectArray = ObjectBigArrays.newBigArray(l);
        long l2 = l - 1L;
        int n = objectArray[0].length - 1;
        int n2 = objectArray.length - 1;
        int n3 = 0;
        int n4 = 0;
        long l3 = this.realSize();
        while (l3-- != 0L) {
            int n5;
            while (KArray[n3][n4] == null) {
                n3 += (n4 = n4 + 1 & this.segmentMask) == 0 ? 1 : 0;
            }
            K k = KArray[n3][n4];
            long l4 = HashCommon.mix((long)k.hashCode());
            int n6 = (int)((l4 & l2) >>> 27);
            if (objectArray[n6][n5 = (int)(l4 & (long)n)] != null) {
                while (objectArray[n6 = n6 + ((n5 = n5 + 1 & n) == 0 ? 1 : 0) & n2][n5] != null) {
                }
            }
            objectArray[n6][n5] = k;
            n3 += (n4 = n4 + 1 & this.segmentMask) == 0 ? 1 : 0;
        }
        this.n = l;
        this.key = objectArray;
        this.initMasks();
        this.maxFill = HashCommon.maxFill(this.n, this.f);
    }

    @Override
    @Deprecated
    public int size() {
        return (int)Math.min(Integer.MAX_VALUE, this.size);
    }

    @Override
    public long size64() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0L;
    }

    public ObjectOpenHashBigSet<K> clone() {
        ObjectOpenHashBigSet objectOpenHashBigSet;
        try {
            objectOpenHashBigSet = (ObjectOpenHashBigSet)super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError();
        }
        objectOpenHashBigSet.key = ObjectBigArrays.copy(this.key);
        objectOpenHashBigSet.containsNull = this.containsNull;
        return objectOpenHashBigSet;
    }

    @Override
    public int hashCode() {
        K[][] KArray = this.key;
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        long l = this.realSize();
        while (l-- != 0L) {
            while (KArray[n2][n3] == null) {
                n2 += (n3 = n3 + 1 & this.segmentMask) == 0 ? 1 : 0;
            }
            if (this != KArray[n2][n3]) {
                n += KArray[n2][n3].hashCode();
            }
            n2 += (n3 = n3 + 1 & this.segmentMask) == 0 ? 1 : 0;
        }
        return n;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Iterator iterator2 = this.iterator();
        objectOutputStream.defaultWriteObject();
        long l = this.size;
        while (l-- != 0L) {
            objectOutputStream.writeObject(iterator2.next());
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.bigArraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = ObjectBigArrays.newBigArray(this.n);
        Object[][] objectArray = this.key;
        this.initMasks();
        long l = this.size;
        while (l-- != 0L) {
            int n;
            Object object = objectInputStream.readObject();
            if (object == null) {
                this.containsNull = true;
                continue;
            }
            long l2 = HashCommon.mix((long)object.hashCode());
            int n2 = (int)((l2 & this.mask) >>> 27);
            if (objectArray[n2][n = (int)(l2 & (long)this.segmentMask)] != null) {
                while (objectArray[n2 = n2 + ((n = n + 1 & this.segmentMask) == 0 ? 1 : 0) & this.baseMask][n] != null) {
                }
            }
            objectArray[n2][n] = object;
        }
    }

    private void checkTable() {
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private class SetIterator
    implements ObjectIterator<K> {
        int base;
        int displ;
        long last;
        long c;
        boolean mustReturnNull;
        ObjectArrayList<K> wrapped;
        final ObjectOpenHashBigSet this$0;

        private SetIterator(ObjectOpenHashBigSet objectOpenHashBigSet) {
            this.this$0 = objectOpenHashBigSet;
            this.base = this.this$0.key.length;
            this.last = -1L;
            this.c = this.this$0.size;
            this.mustReturnNull = this.this$0.containsNull;
        }

        @Override
        public boolean hasNext() {
            return this.c != 0L;
        }

        @Override
        public K next() {
            Object k;
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return null;
            }
            K[][] KArray = this.this$0.key;
            do {
                if (this.displ == 0 && this.base <= 0) {
                    this.last = Long.MIN_VALUE;
                    return this.wrapped.get(-(--this.base) - 1);
                }
                if (this.displ-- != 0) continue;
                this.displ = KArray[--this.base].length - 1;
            } while ((k = KArray[this.base][this.displ]) == null);
            this.last = (long)this.base * 0x8000000L + (long)this.displ;
            return k;
        }

        private final void shiftKeys(long l) {
            K[][] KArray = this.this$0.key;
            while (true) {
                Object k;
                long l2 = l;
                l = l2 + 1L & this.this$0.mask;
                while (true) {
                    if ((k = ObjectBigArrays.get(KArray, l)) == null) {
                        ObjectBigArrays.set(KArray, l2, null);
                        return;
                    }
                    long l3 = HashCommon.mix((long)k.hashCode()) & this.this$0.mask;
                    if (l2 <= l ? l2 >= l3 || l3 > l : l2 >= l3 && l3 > l) break;
                    l = l + 1L & this.this$0.mask;
                }
                if (l < l2) {
                    if (this.wrapped == null) {
                        this.wrapped = new ObjectArrayList();
                    }
                    this.wrapped.add(ObjectBigArrays.get(KArray, l));
                }
                ObjectBigArrays.set(KArray, l2, k);
            }
        }

        @Override
        public void remove() {
            if (this.last == -1L) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNull = false;
            } else if (this.base >= 0) {
                this.shiftKeys(this.last);
            } else {
                this.this$0.remove(this.wrapped.set(-this.base - 1, (Object)null));
                this.last = -1L;
                return;
            }
            --this.this$0.size;
            this.last = -1L;
        }

        SetIterator(ObjectOpenHashBigSet objectOpenHashBigSet, 1 var2_2) {
            this(objectOpenHashBigSet);
        }
    }
}

