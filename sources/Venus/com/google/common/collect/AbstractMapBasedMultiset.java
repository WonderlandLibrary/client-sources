/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Count;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.ObjIntConsumer;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
abstract class AbstractMapBasedMultiset<E>
extends AbstractMultiset<E>
implements Serializable {
    private transient Map<E, Count> backingMap;
    private transient long size;
    @GwtIncompatible
    private static final long serialVersionUID = -2250766705698539974L;

    protected AbstractMapBasedMultiset(Map<E, Count> map) {
        this.backingMap = Preconditions.checkNotNull(map);
        this.size = super.size();
    }

    void setBackingMap(Map<E, Count> map) {
        this.backingMap = map;
    }

    @Override
    public Set<Multiset.Entry<E>> entrySet() {
        return super.entrySet();
    }

    @Override
    Iterator<Multiset.Entry<E>> entryIterator() {
        Iterator<Map.Entry<E, Count>> iterator2 = this.backingMap.entrySet().iterator();
        return new Iterator<Multiset.Entry<E>>(this, iterator2){
            Map.Entry<E, Count> toRemove;
            final Iterator val$backingEntries;
            final AbstractMapBasedMultiset this$0;
            {
                this.this$0 = abstractMapBasedMultiset;
                this.val$backingEntries = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.val$backingEntries.hasNext();
            }

            @Override
            public Multiset.Entry<E> next() {
                Map.Entry entry;
                this.toRemove = entry = (Map.Entry)this.val$backingEntries.next();
                return new Multisets.AbstractEntry<E>(this, entry){
                    final Map.Entry val$mapEntry;
                    final 1 this$1;
                    {
                        this.this$1 = var1_1;
                        this.val$mapEntry = entry;
                    }

                    @Override
                    public E getElement() {
                        return this.val$mapEntry.getKey();
                    }

                    @Override
                    public int getCount() {
                        Count count;
                        Count count2 = (Count)this.val$mapEntry.getValue();
                        if ((count2 == null || count2.get() == 0) && (count = (Count)AbstractMapBasedMultiset.access$000(this.this$1.this$0).get(this.getElement())) != null) {
                            return count.get();
                        }
                        return count2 == null ? 0 : count2.get();
                    }
                };
            }

            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.toRemove != null);
                AbstractMapBasedMultiset.access$102(this.this$0, AbstractMapBasedMultiset.access$100(this.this$0) - (long)this.toRemove.getValue().getAndSet(0));
                this.val$backingEntries.remove();
                this.toRemove = null;
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    @Override
    public void forEachEntry(ObjIntConsumer<? super E> objIntConsumer) {
        Preconditions.checkNotNull(objIntConsumer);
        this.backingMap.forEach((arg_0, arg_1) -> AbstractMapBasedMultiset.lambda$forEachEntry$0(objIntConsumer, arg_0, arg_1));
    }

    @Override
    public void clear() {
        for (Count count : this.backingMap.values()) {
            count.set(0);
        }
        this.backingMap.clear();
        this.size = 0L;
    }

    @Override
    int distinctElements() {
        return this.backingMap.size();
    }

    @Override
    public int size() {
        return Ints.saturatedCast(this.size);
    }

    @Override
    public Iterator<E> iterator() {
        return new MapBasedMultisetIterator(this);
    }

    @Override
    public int count(@Nullable Object object) {
        Count count = Maps.safeGet(this.backingMap, object);
        return count == null ? 0 : count.get();
    }

    @Override
    @CanIgnoreReturnValue
    public int add(@Nullable E e, int n) {
        int n2;
        if (n == 0) {
            return this.count(e);
        }
        Preconditions.checkArgument(n > 0, "occurrences cannot be negative: %s", n);
        Count count = this.backingMap.get(e);
        if (count == null) {
            n2 = 0;
            this.backingMap.put(e, new Count(n));
        } else {
            n2 = count.get();
            long l = (long)n2 + (long)n;
            Preconditions.checkArgument(l <= Integer.MAX_VALUE, "too many occurrences: %s", l);
            count.add(n);
        }
        this.size += (long)n;
        return n2;
    }

    @Override
    @CanIgnoreReturnValue
    public int remove(@Nullable Object object, int n) {
        int n2;
        if (n == 0) {
            return this.count(object);
        }
        Preconditions.checkArgument(n > 0, "occurrences cannot be negative: %s", n);
        Count count = this.backingMap.get(object);
        if (count == null) {
            return 1;
        }
        int n3 = count.get();
        if (n3 > n) {
            n2 = n;
        } else {
            n2 = n3;
            this.backingMap.remove(object);
        }
        count.add(-n2);
        this.size -= (long)n2;
        return n3;
    }

    @Override
    @CanIgnoreReturnValue
    public int setCount(@Nullable E e, int n) {
        int n2;
        CollectPreconditions.checkNonnegative(n, "count");
        if (n == 0) {
            Count count = this.backingMap.remove(e);
            n2 = AbstractMapBasedMultiset.getAndSet(count, n);
        } else {
            Count count = this.backingMap.get(e);
            n2 = AbstractMapBasedMultiset.getAndSet(count, n);
            if (count == null) {
                this.backingMap.put(e, new Count(n));
            }
        }
        this.size += (long)(n - n2);
        return n2;
    }

    private static int getAndSet(@Nullable Count count, int n) {
        if (count == null) {
            return 1;
        }
        return count.getAndSet(n);
    }

    @GwtIncompatible
    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }

    private static void lambda$forEachEntry$0(ObjIntConsumer objIntConsumer, Object object, Count count) {
        objIntConsumer.accept(object, count.get());
    }

    static Map access$000(AbstractMapBasedMultiset abstractMapBasedMultiset) {
        return abstractMapBasedMultiset.backingMap;
    }

    static long access$100(AbstractMapBasedMultiset abstractMapBasedMultiset) {
        return abstractMapBasedMultiset.size;
    }

    static long access$102(AbstractMapBasedMultiset abstractMapBasedMultiset, long l) {
        abstractMapBasedMultiset.size = l;
        return abstractMapBasedMultiset.size;
    }

    static long access$110(AbstractMapBasedMultiset abstractMapBasedMultiset) {
        return abstractMapBasedMultiset.size--;
    }

    private class MapBasedMultisetIterator
    implements Iterator<E> {
        final Iterator<Map.Entry<E, Count>> entryIterator;
        Map.Entry<E, Count> currentEntry;
        int occurrencesLeft;
        boolean canRemove;
        final AbstractMapBasedMultiset this$0;

        MapBasedMultisetIterator(AbstractMapBasedMultiset abstractMapBasedMultiset) {
            this.this$0 = abstractMapBasedMultiset;
            this.entryIterator = AbstractMapBasedMultiset.access$000(abstractMapBasedMultiset).entrySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return this.occurrencesLeft > 0 || this.entryIterator.hasNext();
        }

        @Override
        public E next() {
            if (this.occurrencesLeft == 0) {
                this.currentEntry = this.entryIterator.next();
                this.occurrencesLeft = this.currentEntry.getValue().get();
            }
            --this.occurrencesLeft;
            this.canRemove = true;
            return this.currentEntry.getKey();
        }

        @Override
        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            int n = this.currentEntry.getValue().get();
            if (n <= 0) {
                throw new ConcurrentModificationException();
            }
            if (this.currentEntry.getValue().addAndGet(-1) == 0) {
                this.entryIterator.remove();
            }
            AbstractMapBasedMultiset.access$110(this.this$0);
            this.canRemove = false;
        }
    }
}

