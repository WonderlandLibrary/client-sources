/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.SafeMath;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrayList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollection;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectCollections;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectIterators;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectList;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectListIterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectLists;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterator;
import com.viaversion.viaversion.libs.fastutil.objects.ObjectSpliterators;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class ObjectImmutableList<K>
extends ObjectLists.ImmutableListBase<K>
implements ObjectList<K>,
RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = 0L;
    static final ObjectImmutableList EMPTY = new ObjectImmutableList<Object>(ObjectArrays.EMPTY_ARRAY);
    private final K[] a;
    private static final Collector<Object, ?, ObjectImmutableList<Object>> TO_LIST_COLLECTOR = Collector.of(ObjectArrayList::new, ObjectArrayList::add, ObjectArrayList::combine, ObjectImmutableList::convertTrustedToImmutableList, new Collector.Characteristics[0]);

    private static final <K> K[] emptyArray() {
        return ObjectArrays.EMPTY_ARRAY;
    }

    public ObjectImmutableList(K[] KArray) {
        this.a = KArray;
    }

    public ObjectImmutableList(Collection<? extends K> collection) {
        this(collection.isEmpty() ? ObjectImmutableList.emptyArray() : ObjectIterators.unwrap(collection.iterator()));
    }

    public ObjectImmutableList(ObjectCollection<? extends K> objectCollection) {
        this(objectCollection.isEmpty() ? ObjectImmutableList.emptyArray() : ObjectIterators.unwrap(objectCollection.iterator()));
    }

    public ObjectImmutableList(ObjectList<? extends K> objectList) {
        this(objectList.isEmpty() ? ObjectImmutableList.emptyArray() : new Object[objectList.size()]);
        objectList.getElements(0, this.a, 0, objectList.size());
    }

    public ObjectImmutableList(K[] KArray, int n, int n2) {
        this(n2 == 0 ? ObjectImmutableList.emptyArray() : new Object[n2]);
        System.arraycopy(KArray, n, this.a, 0, n2);
    }

    public ObjectImmutableList(ObjectIterator<? extends K> objectIterator) {
        this(objectIterator.hasNext() ? ObjectIterators.unwrap(objectIterator) : ObjectImmutableList.emptyArray());
    }

    public static <K> ObjectImmutableList<K> of() {
        return EMPTY;
    }

    @SafeVarargs
    public static <K> ObjectImmutableList<K> of(K ... KArray) {
        return KArray.length == 0 ? ObjectImmutableList.of() : new ObjectImmutableList<K>(KArray);
    }

    private static <K> ObjectImmutableList<K> convertTrustedToImmutableList(ObjectArrayList<K> objectArrayList) {
        if (objectArrayList.isEmpty()) {
            return ObjectImmutableList.of();
        }
        K[] KArray = objectArrayList.elements();
        if (objectArrayList.size() != KArray.length) {
            KArray = Arrays.copyOf(KArray, objectArrayList.size());
        }
        return new ObjectImmutableList<K>(KArray);
    }

    public static <K> Collector<K, ?, ObjectImmutableList<K>> toList() {
        return TO_LIST_COLLECTOR;
    }

    public static <K> Collector<K, ?, ObjectImmutableList<K>> toListWithExpectedSize(int n) {
        if (n <= 10) {
            return ObjectImmutableList.toList();
        }
        return Collector.of(new ObjectCollections.SizeDecreasingSupplier(n, ObjectImmutableList::lambda$toListWithExpectedSize$0), ObjectArrayList::add, ObjectArrayList::combine, ObjectImmutableList::convertTrustedToImmutableList, new Collector.Characteristics[0]);
    }

    @Override
    public K get(int n) {
        if (n >= this.a.length) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.a.length + ")");
        }
        return this.a[n];
    }

    @Override
    public int indexOf(Object object) {
        int n = this.a.length;
        for (int i = 0; i < n; ++i) {
            if (!Objects.equals(object, this.a[i])) continue;
            return i;
        }
        return 1;
    }

    @Override
    public int lastIndexOf(Object object) {
        int n = this.a.length;
        while (n-- != 0) {
            if (!Objects.equals(object, this.a[n])) continue;
            return n;
        }
        return 1;
    }

    @Override
    public int size() {
        return this.a.length;
    }

    @Override
    public boolean isEmpty() {
        return this.a.length == 0;
    }

    @Override
    public void getElements(int n, Object[] objectArray, int n2, int n3) {
        ObjectArrays.ensureOffsetLength(objectArray, n2, n3);
        System.arraycopy(this.a, n, objectArray, n2, n3);
    }

    @Override
    public void forEach(Consumer<? super K> consumer) {
        for (int i = 0; i < this.a.length; ++i) {
            consumer.accept(this.a[i]);
        }
    }

    @Override
    public Object[] toArray() {
        if (this.a.length == 0) {
            return ObjectArrays.EMPTY_ARRAY;
        }
        if (this.a.getClass() == Object[].class) {
            return (Object[])this.a.clone();
        }
        return Arrays.copyOf(this.a, this.a.length, Object[].class);
    }

    @Override
    public <T> T[] toArray(T[] objectArray) {
        if (objectArray == null) {
            objectArray = new Object[this.size()];
        } else if (objectArray.length < this.size()) {
            objectArray = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), this.size());
        }
        System.arraycopy(this.a, 0, objectArray, 0, this.size());
        if (objectArray.length > this.size()) {
            objectArray[this.size()] = null;
        }
        return objectArray;
    }

    @Override
    public ObjectListIterator<K> listIterator(int n) {
        this.ensureIndex(n);
        return new ObjectListIterator<K>(){
            int pos;
            final int val$index;
            final ObjectImmutableList this$0;
            {
                this.this$0 = objectImmutableList;
                this.val$index = n;
                this.pos = this.val$index;
            }

            @Override
            public boolean hasNext() {
                return this.pos < ObjectImmutableList.access$000(this.this$0).length;
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override
            public K next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return ObjectImmutableList.access$000(this.this$0)[this.pos++];
            }

            @Override
            public K previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                return ObjectImmutableList.access$000(this.this$0)[--this.pos];
            }

            @Override
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void forEachRemaining(Consumer<? super K> consumer) {
                while (this.pos < ObjectImmutableList.access$000(this.this$0).length) {
                    consumer.accept(ObjectImmutableList.access$000(this.this$0)[this.pos++]);
                }
            }

            @Override
            public void add(K k) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(K k) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = ObjectImmutableList.access$000(this.this$0).length - this.pos;
                if (n < n2) {
                    this.pos -= n;
                } else {
                    n = n2;
                    this.pos = 0;
                }
                return n;
            }

            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                int n2 = ObjectImmutableList.access$000(this.this$0).length - this.pos;
                if (n < n2) {
                    this.pos += n;
                } else {
                    n = n2;
                    this.pos = ObjectImmutableList.access$000(this.this$0).length;
                }
                return n;
            }
        };
    }

    @Override
    public ObjectSpliterator<K> spliterator() {
        return new Spliterator(this);
    }

    @Override
    public ObjectList<K> subList(int n, int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n == n2) {
            return EMPTY;
        }
        if (n > n2) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new ImmutableSubList(this, n, n2);
    }

    public ObjectImmutableList<K> clone() {
        return this;
    }

    public boolean equals(ObjectImmutableList<K> objectImmutableList) {
        if (objectImmutableList == this) {
            return false;
        }
        if (this.a == objectImmutableList.a) {
            return false;
        }
        int n = this.size();
        if (n != objectImmutableList.size()) {
            return true;
        }
        Object[] objectArray = this.a;
        Object[] objectArray2 = objectImmutableList.a;
        return Arrays.equals(objectArray, objectArray2);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof List)) {
            return true;
        }
        if (object instanceof ObjectImmutableList) {
            return this.equals((ObjectImmutableList)object);
        }
        if (object instanceof ImmutableSubList) {
            return ((ImmutableSubList)object).equals(this);
        }
        return super.equals(object);
    }

    @Override
    public int compareTo(ObjectImmutableList<? extends K> objectImmutableList) {
        int n;
        int n2 = this.size();
        int n3 = objectImmutableList.size();
        K[] KArray = this.a;
        K[] KArray2 = objectImmutableList.a;
        for (n = 0; n < n2 && n < n3; ++n) {
            K k = KArray[n];
            K k2 = KArray2[n];
            int n4 = ((Comparable)k).compareTo(k2);
            if (n4 == 0) continue;
            return n4;
        }
        return n < n3 ? -1 : (n < n2 ? 1 : 0);
    }

    @Override
    public int compareTo(List<? extends K> list) {
        if (list instanceof ObjectImmutableList) {
            return this.compareTo((ObjectImmutableList)list);
        }
        if (list instanceof ImmutableSubList) {
            ImmutableSubList immutableSubList = (ImmutableSubList)list;
            return -immutableSubList.compareTo(this);
        }
        return super.compareTo(list);
    }

    @Override
    public java.util.Spliterator spliterator() {
        return this.spliterator();
    }

    @Override
    public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((List)object);
    }

    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    private static ObjectArrayList lambda$toListWithExpectedSize$0(int n) {
        return n <= 10 ? new ObjectArrayList() : new ObjectArrayList(n);
    }

    static Object[] access$000(ObjectImmutableList objectImmutableList) {
        return objectImmutableList.a;
    }

    private final class Spliterator
    implements ObjectSpliterator<K> {
        int pos;
        int max;
        static final boolean $assertionsDisabled = !ObjectImmutableList.class.desiredAssertionStatus();
        final ObjectImmutableList this$0;

        public Spliterator(ObjectImmutableList objectImmutableList) {
            this(objectImmutableList, 0, ObjectImmutableList.access$000(objectImmutableList).length);
        }

        private Spliterator(ObjectImmutableList objectImmutableList, int n, int n2) {
            this.this$0 = objectImmutableList;
            if (!$assertionsDisabled && n > n2) {
                throw new AssertionError((Object)("pos " + n + " must be <= max " + n2));
            }
            this.pos = n;
            this.max = n2;
        }

        @Override
        public int characteristics() {
            return 1;
        }

        @Override
        public long estimateSize() {
            return this.max - this.pos;
        }

        @Override
        public boolean tryAdvance(Consumer<? super K> consumer) {
            if (this.pos >= this.max) {
                return true;
            }
            consumer.accept(ObjectImmutableList.access$000(this.this$0)[this.pos++]);
            return false;
        }

        @Override
        public void forEachRemaining(Consumer<? super K> consumer) {
            while (this.pos < this.max) {
                consumer.accept(ObjectImmutableList.access$000(this.this$0)[this.pos]);
                ++this.pos;
            }
        }

        @Override
        public long skip(long l) {
            if (l < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + l);
            }
            if (this.pos >= this.max) {
                return 0L;
            }
            int n = this.max - this.pos;
            if (l < (long)n) {
                this.pos = SafeMath.safeLongToInt((long)this.pos + l);
                return l;
            }
            l = n;
            this.pos = this.max;
            return l;
        }

        @Override
        public ObjectSpliterator<K> trySplit() {
            int n;
            int n2 = this.max - this.pos >> 1;
            if (n2 <= 1) {
                return null;
            }
            int n3 = n = this.pos + n2;
            int n4 = this.pos;
            this.pos = n;
            return new Spliterator(this.this$0, n4, n3);
        }

        @Override
        public java.util.Spliterator trySplit() {
            return this.trySplit();
        }
    }

    private static final class ImmutableSubList<K>
    extends ObjectLists.ImmutableListBase<K>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 7054639518438982401L;
        final ObjectImmutableList<K> innerList;
        final int from;
        final int to;
        final transient K[] a;

        ImmutableSubList(ObjectImmutableList<K> objectImmutableList, int n, int n2) {
            this.innerList = objectImmutableList;
            this.from = n;
            this.to = n2;
            this.a = ObjectImmutableList.access$000(objectImmutableList);
        }

        @Override
        public K get(int n) {
            this.ensureRestrictedIndex(n);
            return this.a[n + this.from];
        }

        @Override
        public int indexOf(Object object) {
            for (int i = this.from; i < this.to; ++i) {
                if (!Objects.equals(object, this.a[i])) continue;
                return i - this.from;
            }
            return 1;
        }

        @Override
        public int lastIndexOf(Object object) {
            int n = this.to;
            while (n-- != this.from) {
                if (!Objects.equals(object, this.a[n])) continue;
                return n - this.from;
            }
            return 1;
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public boolean isEmpty() {
            return this.to <= this.from;
        }

        @Override
        public void getElements(int n, Object[] objectArray, int n2, int n3) {
            ObjectArrays.ensureOffsetLength(objectArray, n2, n3);
            this.ensureRestrictedIndex(n);
            if (this.from + n3 > this.to) {
                throw new IndexOutOfBoundsException("Final index " + (this.from + n3) + " (startingIndex: " + this.from + " + length: " + n3 + ") is greater then list length " + this.size());
            }
            System.arraycopy(this.a, n + this.from, objectArray, n2, n3);
        }

        @Override
        public void forEach(Consumer<? super K> consumer) {
            for (int i = this.from; i < this.to; ++i) {
                consumer.accept(this.a[i]);
            }
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOfRange(this.a, this.from, this.to, Object[].class);
        }

        @Override
        public <K> K[] toArray(K[] objectArray) {
            int n = this.size();
            if (objectArray == null) {
                objectArray = new Object[n];
            } else if (objectArray.length < n) {
                objectArray = (Object[])Array.newInstance(objectArray.getClass().getComponentType(), n);
            }
            System.arraycopy(this.a, this.from, objectArray, 0, n);
            if (objectArray.length > n) {
                objectArray[n] = null;
            }
            return objectArray;
        }

        @Override
        public ObjectListIterator<K> listIterator(int n) {
            this.ensureIndex(n);
            return new ObjectListIterator<K>(){
                int pos;
                final int val$index;
                final ImmutableSubList this$0;
                {
                    this.this$0 = immutableSubList;
                    this.val$index = n;
                    this.pos = this.val$index;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < this.this$0.to;
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > this.this$0.from;
                }

                @Override
                public K next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return this.this$0.a[this.pos++ + this.this$0.from];
                }

                @Override
                public K previous() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    return this.this$0.a[--this.pos + this.this$0.from];
                }

                @Override
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void forEachRemaining(Consumer<? super K> consumer) {
                    while (this.pos < this.this$0.to) {
                        consumer.accept(this.this$0.a[this.pos++ + this.this$0.from]);
                    }
                }

                @Override
                public void add(K k) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void set(K k) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int back(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    int n2 = this.this$0.to - this.pos;
                    if (n < n2) {
                        this.pos -= n;
                    } else {
                        n = n2;
                        this.pos = 0;
                    }
                    return n;
                }

                @Override
                public int skip(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    int n2 = this.this$0.to - this.pos;
                    if (n < n2) {
                        this.pos += n;
                    } else {
                        n = n2;
                        this.pos = this.this$0.to;
                    }
                    return n;
                }
            };
        }

        @Override
        public ObjectSpliterator<K> spliterator() {
            return new SubListSpliterator(this);
        }

        boolean contentsEquals(K[] KArray, int n, int n2) {
            if (this.a == KArray && this.from == n && this.to == n2) {
                return false;
            }
            if (n2 - n != this.size()) {
                return true;
            }
            int n3 = this.from;
            int n4 = n;
            while (n3 < this.to) {
                if (Objects.equals(this.a[n3++], KArray[n4++])) continue;
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return false;
            }
            if (object == null) {
                return true;
            }
            if (!(object instanceof List)) {
                return true;
            }
            if (object instanceof ObjectImmutableList) {
                ObjectImmutableList objectImmutableList = (ObjectImmutableList)object;
                return this.contentsEquals(ObjectImmutableList.access$000(objectImmutableList), 0, objectImmutableList.size());
            }
            if (object instanceof ImmutableSubList) {
                ImmutableSubList immutableSubList = (ImmutableSubList)object;
                return this.contentsEquals(immutableSubList.a, immutableSubList.from, immutableSubList.to);
            }
            return super.equals(object);
        }

        int contentsCompareTo(K[] KArray, int n, int n2) {
            int n3 = this.from;
            int n4 = n;
            while (n3 < this.to && n3 < n2) {
                K k = this.a[n3];
                K k2 = KArray[n4];
                int n5 = ((Comparable)k).compareTo(k2);
                if (n5 != 0) {
                    return n5;
                }
                ++n3;
                ++n4;
            }
            return n3 < n2 ? -1 : (n3 < this.to ? 1 : 0);
        }

        @Override
        public int compareTo(List<? extends K> list) {
            if (list instanceof ObjectImmutableList) {
                ObjectImmutableList objectImmutableList = (ObjectImmutableList)list;
                return this.contentsCompareTo(ObjectImmutableList.access$000(objectImmutableList), 0, objectImmutableList.size());
            }
            if (list instanceof ImmutableSubList) {
                ImmutableSubList immutableSubList = (ImmutableSubList)list;
                return this.contentsCompareTo(immutableSubList.a, immutableSubList.from, immutableSubList.to);
            }
            return super.compareTo(list);
        }

        private Object readResolve() throws ObjectStreamException {
            try {
                return this.innerList.subList(this.from, this.to);
            } catch (IllegalArgumentException | IndexOutOfBoundsException runtimeException) {
                throw (InvalidObjectException)new InvalidObjectException(runtimeException.getMessage()).initCause(runtimeException);
            }
        }

        @Override
        public ObjectList<K> subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n == n2) {
                return EMPTY;
            }
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new ImmutableSubList<K>(this.innerList, n + this.from, n2 + this.from);
        }

        @Override
        public java.util.Spliterator spliterator() {
            return this.spliterator();
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((List)object);
        }

        /*
         * Duplicate member names - consider using --renamedupmembers true
         */
        private final class SubListSpliterator
        extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator<K> {
            final ImmutableSubList this$0;

            SubListSpliterator(ImmutableSubList immutableSubList) {
                this.this$0 = immutableSubList;
                super(immutableSubList.from, immutableSubList.to);
            }

            private SubListSpliterator(ImmutableSubList immutableSubList, int n, int n2) {
                this.this$0 = immutableSubList;
                super(n, n2);
            }

            @Override
            protected final K get(int n) {
                return this.this$0.a[n];
            }

            protected final SubListSpliterator makeForSplit(int n, int n2) {
                return new SubListSpliterator(this.this$0, n, n2);
            }

            @Override
            public boolean tryAdvance(Consumer<? super K> consumer) {
                if (this.pos >= this.maxPos) {
                    return true;
                }
                consumer.accept(this.this$0.a[this.pos++]);
                return false;
            }

            @Override
            public void forEachRemaining(Consumer<? super K> consumer) {
                int n = this.maxPos;
                while (this.pos < n) {
                    consumer.accept(this.this$0.a[this.pos++]);
                }
            }

            @Override
            public int characteristics() {
                return 1;
            }

            @Override
            protected ObjectSpliterator makeForSplit(int n, int n2) {
                return this.makeForSplit(n, n2);
            }
        }
    }
}

