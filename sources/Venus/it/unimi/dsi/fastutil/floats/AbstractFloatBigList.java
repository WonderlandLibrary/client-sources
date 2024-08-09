/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.floats;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.floats.AbstractFloatCollection;
import it.unimi.dsi.fastutil.floats.FloatBigArrays;
import it.unimi.dsi.fastutil.floats.FloatBigList;
import it.unimi.dsi.fastutil.floats.FloatBigListIterator;
import it.unimi.dsi.fastutil.floats.FloatCollection;
import it.unimi.dsi.fastutil.floats.FloatIterator;
import it.unimi.dsi.fastutil.floats.FloatStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractFloatBigList
extends AbstractFloatCollection
implements FloatBigList,
FloatStack {
    protected AbstractFloatBigList() {
    }

    protected void ensureIndex(long l) {
        if (l < 0L) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is negative");
        }
        if (l > this.size64()) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than list size (" + this.size64() + ")");
        }
    }

    protected void ensureRestrictedIndex(long l) {
        if (l < 0L) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is negative");
        }
        if (l >= this.size64()) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size64() + ")");
        }
    }

    @Override
    public void add(long l, float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(float f) {
        this.add(this.size64(), f);
        return false;
    }

    @Override
    public float removeFloat(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public float set(long l, float f) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(long l, Collection<? extends Float> collection) {
        this.ensureIndex(l);
        Iterator<? extends Float> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(l++, iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Float> collection) {
        return this.addAll(this.size64(), collection);
    }

    @Override
    public FloatBigListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public FloatBigListIterator listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public FloatBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new FloatBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final AbstractFloatBigList this$0;
            {
                this.this$0 = abstractFloatBigList;
                this.val$index = l;
                this.pos = this.val$index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size64();
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0L;
            }

            @Override
            public float nextFloat() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.getFloat(this.last);
            }

            @Override
            public float previousFloat() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.getFloat(this.pos);
            }

            @Override
            public long nextIndex() {
                return this.pos;
            }

            @Override
            public long previousIndex() {
                return this.pos - 1L;
            }

            @Override
            public void add(float f) {
                this.this$0.add(this.pos++, f);
                this.last = -1L;
            }

            @Override
            public void set(float f) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, f);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeFloat(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    @Override
    public boolean contains(float f) {
        return this.indexOf(f) >= 0L;
    }

    @Override
    public long indexOf(float f) {
        FloatBigListIterator floatBigListIterator = this.listIterator();
        while (floatBigListIterator.hasNext()) {
            float f2 = floatBigListIterator.nextFloat();
            if (Float.floatToIntBits(f) != Float.floatToIntBits(f2)) continue;
            return floatBigListIterator.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(float f) {
        FloatBigListIterator floatBigListIterator = this.listIterator(this.size64());
        while (floatBigListIterator.hasPrevious()) {
            float f2 = floatBigListIterator.previousFloat();
            if (Float.floatToIntBits(f) != Float.floatToIntBits(f2)) continue;
            return floatBigListIterator.nextIndex();
        }
        return -1L;
    }

    @Override
    public void size(long l) {
        long l2 = this.size64();
        if (l > l2) {
            while (l2++ < l) {
                this.add(0.0f);
            }
        } else {
            while (l2-- != l) {
                this.remove(l2);
            }
        }
    }

    @Override
    public FloatBigList subList(long l, long l2) {
        this.ensureIndex(l);
        this.ensureIndex(l2);
        if (l > l2) {
            throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        return new FloatSubList(this, l, l2);
    }

    @Override
    public void removeElements(long l, long l2) {
        this.ensureIndex(l2);
        FloatBigListIterator floatBigListIterator = this.listIterator(l);
        long l3 = l2 - l;
        if (l3 < 0L) {
            throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        while (l3-- != 0L) {
            floatBigListIterator.nextFloat();
            floatBigListIterator.remove();
        }
    }

    @Override
    public void addElements(long l, float[][] fArray, long l2, long l3) {
        this.ensureIndex(l);
        FloatBigArrays.ensureOffsetLength(fArray, l2, l3);
        while (l3-- != 0L) {
            this.add(l++, FloatBigArrays.get(fArray, l2++));
        }
    }

    @Override
    public void addElements(long l, float[][] fArray) {
        this.addElements(l, fArray, 0L, FloatBigArrays.length(fArray));
    }

    @Override
    public void getElements(long l, float[][] fArray, long l2, long l3) {
        FloatBigListIterator floatBigListIterator = this.listIterator(l);
        FloatBigArrays.ensureOffsetLength(fArray, l2, l3);
        if (l + l3 > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (l + l3) + ") is greater than list size (" + this.size64() + ")");
        }
        while (l3-- != 0L) {
            FloatBigArrays.set(fArray, l2++, floatBigListIterator.nextFloat());
        }
    }

    @Override
    public void clear() {
        this.removeElements(0L, this.size64());
    }

    @Override
    @Deprecated
    public int size() {
        return (int)Math.min(Integer.MAX_VALUE, this.size64());
    }

    private boolean valEquals(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    @Override
    public int hashCode() {
        FloatBigListIterator floatBigListIterator = this.iterator();
        int n = 1;
        long l = this.size64();
        while (l-- != 0L) {
            float f = floatBigListIterator.nextFloat();
            n = 31 * n + HashCommon.float2int(f);
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof BigList)) {
            return true;
        }
        BigList bigList = (BigList)object;
        long l = this.size64();
        if (l != bigList.size64()) {
            return true;
        }
        if (bigList instanceof FloatBigList) {
            FloatBigListIterator floatBigListIterator = this.listIterator();
            FloatBigListIterator floatBigListIterator2 = ((FloatBigList)bigList).listIterator();
            while (l-- != 0L) {
                if (floatBigListIterator.nextFloat() == floatBigListIterator2.nextFloat()) continue;
                return true;
            }
            return false;
        }
        FloatBigListIterator floatBigListIterator = this.listIterator();
        BigListIterator bigListIterator = bigList.listIterator();
        while (l-- != 0L) {
            if (this.valEquals(floatBigListIterator.next(), bigListIterator.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BigList<? extends Float> bigList) {
        if (bigList == this) {
            return 1;
        }
        if (bigList instanceof FloatBigList) {
            FloatBigListIterator floatBigListIterator = this.listIterator();
            FloatBigListIterator floatBigListIterator2 = ((FloatBigList)bigList).listIterator();
            while (floatBigListIterator.hasNext() && floatBigListIterator2.hasNext()) {
                float f;
                float f2 = floatBigListIterator.nextFloat();
                int n = Float.compare(f2, f = floatBigListIterator2.nextFloat());
                if (n == 0) continue;
                return n;
            }
            return floatBigListIterator2.hasNext() ? -1 : (floatBigListIterator.hasNext() ? 1 : 0);
        }
        FloatBigListIterator floatBigListIterator = this.listIterator();
        BigListIterator<? extends Float> bigListIterator = bigList.listIterator();
        while (floatBigListIterator.hasNext() && bigListIterator.hasNext()) {
            int n = ((Comparable)floatBigListIterator.next()).compareTo(bigListIterator.next());
            if (n == 0) continue;
            return n;
        }
        return bigListIterator.hasNext() ? -1 : (floatBigListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(float f) {
        this.add(f);
    }

    @Override
    public float popFloat() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeFloat(this.size64() - 1L);
    }

    @Override
    public float topFloat() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getFloat(this.size64() - 1L);
    }

    @Override
    public float peekFloat(int n) {
        return this.getFloat(this.size64() - 1L - (long)n);
    }

    @Override
    public boolean rem(float f) {
        long l = this.indexOf(f);
        if (l == -1L) {
            return true;
        }
        this.removeFloat(l);
        return false;
    }

    @Override
    public boolean addAll(long l, FloatCollection floatCollection) {
        return this.addAll(l, (Collection<? extends Float>)floatCollection);
    }

    @Override
    public boolean addAll(long l, FloatBigList floatBigList) {
        return this.addAll(l, (FloatCollection)floatBigList);
    }

    @Override
    public boolean addAll(FloatCollection floatCollection) {
        return this.addAll(this.size64(), floatCollection);
    }

    @Override
    public boolean addAll(FloatBigList floatBigList) {
        return this.addAll(this.size64(), floatBigList);
    }

    @Override
    @Deprecated
    public void add(long l, Float f) {
        this.add(l, f.floatValue());
    }

    @Override
    @Deprecated
    public Float set(long l, Float f) {
        return Float.valueOf(this.set(l, f.floatValue()));
    }

    @Override
    @Deprecated
    public Float get(long l) {
        return Float.valueOf(this.getFloat(l));
    }

    @Override
    @Deprecated
    public long indexOf(Object object) {
        return this.indexOf(((Float)object).floatValue());
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object object) {
        return this.lastIndexOf(((Float)object).floatValue());
    }

    @Override
    @Deprecated
    public Float remove(long l) {
        return Float.valueOf(this.removeFloat(l));
    }

    @Override
    @Deprecated
    public void push(Float f) {
        this.push(f.floatValue());
    }

    @Override
    @Deprecated
    public Float pop() {
        return Float.valueOf(this.popFloat());
    }

    @Override
    @Deprecated
    public Float top() {
        return Float.valueOf(this.topFloat());
    }

    @Override
    @Deprecated
    public Float peek(int n) {
        return Float.valueOf(this.peekFloat(n));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        FloatBigListIterator floatBigListIterator = this.iterator();
        long l = this.size64();
        boolean bl = true;
        stringBuilder.append("[");
        while (l-- != 0L) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            float f = floatBigListIterator.nextFloat();
            stringBuilder.append(String.valueOf(f));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public FloatIterator iterator() {
        return this.iterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public BigList subList(long l, long l2) {
        return this.subList(l, l2);
    }

    @Override
    public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    @Override
    public BigListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    public void add(long l, Object object) {
        this.add(l, (Float)object);
    }

    @Override
    @Deprecated
    public Object set(long l, Object object) {
        return this.set(l, (Float)object);
    }

    @Override
    @Deprecated
    public Object remove(long l) {
        return this.remove(l);
    }

    @Override
    @Deprecated
    public Object get(long l) {
        return this.get(l);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((BigList)object);
    }

    @Override
    @Deprecated
    public Object peek(int n) {
        return this.peek(n);
    }

    @Override
    @Deprecated
    public Object top() {
        return this.top();
    }

    @Override
    @Deprecated
    public Object pop() {
        return this.pop();
    }

    @Override
    @Deprecated
    public void push(Object object) {
        this.push((Float)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class FloatSubList
    extends AbstractFloatBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final FloatBigList l;
        protected final long from;
        protected long to;
        static final boolean $assertionsDisabled = !AbstractFloatBigList.class.desiredAssertionStatus();

        public FloatSubList(FloatBigList floatBigList, long l, long l2) {
            this.l = floatBigList;
            this.from = l;
            this.to = l2;
        }

        private boolean assertRange() {
            if (!$assertionsDisabled && this.from > this.l.size64()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to > this.l.size64()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to < this.from) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean add(float f) {
            this.l.add(this.to, f);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(long l, float f) {
            this.ensureIndex(l);
            this.l.add(this.from + l, f);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(long l, Collection<? extends Float> collection) {
            this.ensureIndex(l);
            this.to += (long)collection.size();
            return this.l.addAll(this.from + l, collection);
        }

        @Override
        public float getFloat(long l) {
            this.ensureRestrictedIndex(l);
            return this.l.getFloat(this.from + l);
        }

        @Override
        public float removeFloat(long l) {
            this.ensureRestrictedIndex(l);
            --this.to;
            return this.l.removeFloat(this.from + l);
        }

        @Override
        public float set(long l, float f) {
            this.ensureRestrictedIndex(l);
            return this.l.set(this.from + l, f);
        }

        @Override
        public long size64() {
            return this.to - this.from;
        }

        @Override
        public void getElements(long l, float[][] fArray, long l2, long l3) {
            this.ensureIndex(l);
            if (l + l3 > this.size64()) {
                throw new IndexOutOfBoundsException("End index (" + l + l3 + ") is greater than list size (" + this.size64() + ")");
            }
            this.l.getElements(this.from + l, fArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            this.l.removeElements(this.from + l, this.from + l2);
            this.to -= l2 - l;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public void addElements(long l, float[][] fArray, long l2, long l3) {
            this.ensureIndex(l);
            this.l.addElements(this.from + l, fArray, l2, l3);
            this.to += l3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public FloatBigListIterator listIterator(long l) {
            this.ensureIndex(l);
            return new FloatBigListIterator(this, l){
                long pos;
                long last;
                static final boolean $assertionsDisabled = !AbstractFloatBigList.class.desiredAssertionStatus();
                final long val$index;
                final FloatSubList this$0;
                {
                    this.this$0 = floatSubList;
                    this.val$index = l;
                    this.pos = this.val$index;
                    this.last = -1L;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < this.this$0.size64();
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > 0L;
                }

                @Override
                public float nextFloat() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return this.this$0.l.getFloat(this.this$0.from + this.last);
                }

                @Override
                public float previousFloat() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.getFloat(this.this$0.from + this.pos);
                }

                @Override
                public long nextIndex() {
                    return this.pos;
                }

                @Override
                public long previousIndex() {
                    return this.pos - 1L;
                }

                @Override
                public void add(float f) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, f);
                    this.last = -1L;
                    if (!$assertionsDisabled && !FloatSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(float f) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, f);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeFloat(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                    if (!$assertionsDisabled && !FloatSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public FloatBigList subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            return new FloatSubList(this, l, l2);
        }

        @Override
        public boolean rem(float f) {
            long l = this.indexOf(f);
            if (l == -1L) {
                return true;
            }
            --this.to;
            this.l.removeFloat(this.from + l);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(long l, FloatCollection floatCollection) {
            this.ensureIndex(l);
            return super.addAll(l, floatCollection);
        }

        @Override
        public boolean addAll(long l, FloatBigList floatBigList) {
            this.ensureIndex(l);
            return super.addAll(l, floatBigList);
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return super.listIterator();
        }

        @Override
        @Deprecated
        public void add(long l, Object object) {
            super.add(l, (Float)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return super.set(l, (Float)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return super.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return super.get(l);
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }

        @Override
        public FloatIterator iterator() {
            return super.iterator();
        }

        @Override
        public int compareTo(Object object) {
            return super.compareTo((BigList)object);
        }

        @Override
        @Deprecated
        public Object peek(int n) {
            return super.peek(n);
        }

        @Override
        @Deprecated
        public Object top() {
            return super.top();
        }

        @Override
        @Deprecated
        public Object pop() {
            return super.pop();
        }

        @Override
        @Deprecated
        public void push(Object object) {
            super.push((Float)object);
        }

        static boolean access$000(FloatSubList floatSubList) {
            return floatSubList.assertRange();
        }
    }
}

