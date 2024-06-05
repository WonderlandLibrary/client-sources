package org.jsoup.helper;

import java.util.*;

public class DescendableLinkedList<E> extends LinkedList<E>
{
    @Override
    public void push(final E e) {
        this.addFirst(e);
    }
    
    @Override
    public E peekLast() {
        return (this.size() == 0) ? null : this.getLast();
    }
    
    @Override
    public E pollLast() {
        return (this.size() == 0) ? null : this.removeLast();
    }
    
    @Override
    public Iterator<E> descendingIterator() {
        return new DescendingIterator<E>(this.size());
    }
    
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
}
