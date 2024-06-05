package org.jsoup.helper;

import java.util.*;

private class DescendingIterator<E> implements Iterator<E>
{
    private final ListIterator<E> iter;
    
    private DescendingIterator(final int index) {
        this.iter = DescendableLinkedList.this.listIterator(index);
    }
    
    public boolean hasNext() {
        return this.iter.hasPrevious();
    }
    
    public E next() {
        return this.iter.previous();
    }
    
    public void remove() {
        this.iter.remove();
    }
}
