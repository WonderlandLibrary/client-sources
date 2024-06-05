package net.java.games.input;

import java.lang.reflect.*;

final class DataQueue<T>
{
    private final T[] elements;
    private int position;
    private int limit;
    
    public DataQueue(final int size, final Class<T> element_type) {
        this.elements = (T[])Array.newInstance(element_type, size);
        for (int i = 0; i < this.elements.length; ++i) {
            try {
                this.elements[i] = element_type.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
            }
            catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex2) {
                final ReflectiveOperationException ex;
                final ReflectiveOperationException e = ex;
                throw new RuntimeException(e);
            }
        }
        this.clear();
    }
    
    public final void clear() {
        this.position = 0;
        this.limit = this.elements.length;
    }
    
    public final int position() {
        return this.position;
    }
    
    public final int limit() {
        return this.limit;
    }
    
    public final T get(final int index) {
        assert index < this.limit;
        return this.elements[index];
    }
    
    public final T get() {
        if (!this.hasRemaining()) {
            return null;
        }
        return this.get(this.position++);
    }
    
    public final void compact() {
        int index = 0;
        while (this.hasRemaining()) {
            this.swap(this.position, index);
            ++this.position;
            ++index;
        }
        this.position = index;
        this.limit = this.elements.length;
    }
    
    private final void swap(final int index1, final int index2) {
        final T temp = this.elements[index1];
        this.elements[index1] = this.elements[index2];
        this.elements[index2] = temp;
    }
    
    public final void flip() {
        this.limit = this.position;
        this.position = 0;
    }
    
    public final boolean hasRemaining() {
        return this.remaining() > 0;
    }
    
    public final int remaining() {
        return this.limit - this.position;
    }
    
    public final void position(final int position) {
        this.position = position;
    }
    
    public final T[] getElements() {
        return this.elements;
    }
}
