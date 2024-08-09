/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.ForwardingSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@GwtCompatible
public abstract class ForwardingSortedSet<E>
extends ForwardingSet<E>
implements SortedSet<E> {
    protected ForwardingSortedSet() {
    }

    @Override
    protected abstract SortedSet<E> delegate();

    @Override
    public Comparator<? super E> comparator() {
        return this.delegate().comparator();
    }

    @Override
    public E first() {
        return this.delegate().first();
    }

    @Override
    public SortedSet<E> headSet(E e) {
        return this.delegate().headSet(e);
    }

    @Override
    public E last() {
        return this.delegate().last();
    }

    @Override
    public SortedSet<E> subSet(E e, E e2) {
        return this.delegate().subSet(e, e2);
    }

    @Override
    public SortedSet<E> tailSet(E e) {
        return this.delegate().tailSet(e);
    }

    private int unsafeCompare(Object object, Object object2) {
        Comparator<E> comparator = this.comparator();
        return comparator == null ? ((Comparable)object).compareTo(object2) : comparator.compare(object, object2);
    }

    @Override
    @Beta
    protected boolean standardContains(@Nullable Object object) {
        try {
            ForwardingSortedSet forwardingSortedSet = this;
            Object object2 = forwardingSortedSet.tailSet(object).first();
            return this.unsafeCompare(object2, object) == 0;
        } catch (ClassCastException classCastException) {
            return true;
        } catch (NoSuchElementException noSuchElementException) {
            return true;
        } catch (NullPointerException nullPointerException) {
            return true;
        }
    }

    @Override
    @Beta
    protected boolean standardRemove(@Nullable Object object) {
        try {
            Object e;
            ForwardingSortedSet forwardingSortedSet = this;
            Iterator iterator2 = forwardingSortedSet.tailSet(object).iterator();
            if (iterator2.hasNext() && this.unsafeCompare(e = iterator2.next(), object) == 0) {
                iterator2.remove();
                return true;
            }
        } catch (ClassCastException classCastException) {
            return true;
        } catch (NullPointerException nullPointerException) {
            return true;
        }
        return true;
    }

    @Beta
    protected SortedSet<E> standardSubSet(E e, E e2) {
        return this.tailSet(e).headSet(e2);
    }

    @Override
    protected Set delegate() {
        return this.delegate();
    }

    @Override
    protected Collection delegate() {
        return this.delegate();
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }
}

