package org.jsoup.helper;

import java.util.*;

public abstract class ChangeNotifyingArrayList<E> extends ArrayList<E>
{
    public ChangeNotifyingArrayList(final int initialCapacity) {
        super(initialCapacity);
    }
    
    public abstract void onContentsChanged();
    
    @Override
    public E set(final int index, final E element) {
        this.onContentsChanged();
        return super.set(index, element);
    }
    
    @Override
    public boolean add(final E e) {
        this.onContentsChanged();
        return super.add(e);
    }
    
    @Override
    public void add(final int index, final E element) {
        this.onContentsChanged();
        super.add(index, element);
    }
    
    @Override
    public E remove(final int index) {
        this.onContentsChanged();
        return super.remove(index);
    }
    
    @Override
    public boolean remove(final Object o) {
        this.onContentsChanged();
        return super.remove(o);
    }
    
    @Override
    public void clear() {
        this.onContentsChanged();
        super.clear();
    }
    
    @Override
    public boolean addAll(final Collection<? extends E> c) {
        this.onContentsChanged();
        return super.addAll(c);
    }
    
    @Override
    public boolean addAll(final int index, final Collection<? extends E> c) {
        this.onContentsChanged();
        return super.addAll(index, c);
    }
    
    @Override
    protected void removeRange(final int fromIndex, final int toIndex) {
        this.onContentsChanged();
        super.removeRange(fromIndex, toIndex);
    }
    
    @Override
    public boolean removeAll(final Collection<?> c) {
        this.onContentsChanged();
        return super.removeAll(c);
    }
    
    @Override
    public boolean retainAll(final Collection<?> c) {
        this.onContentsChanged();
        return super.retainAll(c);
    }
}
