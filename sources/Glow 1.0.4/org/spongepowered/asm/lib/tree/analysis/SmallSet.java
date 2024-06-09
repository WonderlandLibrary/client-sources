package org.spongepowered.asm.lib.tree.analysis;

import java.util.*;

class SmallSet<E> extends AbstractSet<E> implements Iterator<E>
{
    E e1;
    E e2;
    
    static final <T> Set<T> emptySet() {
        return new SmallSet<T>(null, null);
    }
    
    SmallSet(final E e1, final E e2) {
        super();
        this.e1 = e1;
        this.e2 = e2;
    }
    
    @Override
    public Iterator<E> iterator() {
        return new SmallSet(this.e1, this.e2);
    }
    
    @Override
    public int size() {
        return (this.e1 == null) ? 0 : ((this.e2 == null) ? 1 : 2);
    }
    
    public boolean hasNext() {
        return this.e1 != null;
    }
    
    public E next() {
        if (this.e1 == null) {
            throw new NoSuchElementException();
        }
        final E e1 = this.e1;
        this.e1 = this.e2;
        this.e2 = null;
        return e1;
    }
    
    public void remove() {
    }
    
    Set<E> union(final SmallSet<E> set) {
        if ((set.e1 == this.e1 && set.e2 == this.e2) || (set.e1 == this.e2 && set.e2 == this.e1)) {
            return this;
        }
        if (set.e1 == null) {
            return this;
        }
        if (this.e1 == null) {
            return set;
        }
        if (set.e2 == null) {
            if (this.e2 == null) {
                return new SmallSet(this.e1, set.e1);
            }
            if (set.e1 == this.e1 || set.e1 == this.e2) {
                return this;
            }
        }
        if (this.e2 == null && (this.e1 == set.e1 || this.e1 == set.e2)) {
            return set;
        }
        final HashSet<E> set2 = new HashSet<E>(4);
        set2.add(this.e1);
        if (this.e2 != null) {
            set2.add(this.e2);
        }
        set2.add(set.e1);
        if (set.e2 != null) {
            set2.add(set.e2);
        }
        return set2;
    }
}
