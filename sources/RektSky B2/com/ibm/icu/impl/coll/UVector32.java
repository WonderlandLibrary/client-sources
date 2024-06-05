package com.ibm.icu.impl.coll;

public final class UVector32
{
    private int[] buffer;
    private int length;
    
    public UVector32() {
        this.buffer = new int[32];
        this.length = 0;
    }
    
    public boolean isEmpty() {
        return this.length == 0;
    }
    
    public int size() {
        return this.length;
    }
    
    public int elementAti(final int i) {
        return this.buffer[i];
    }
    
    public int[] getBuffer() {
        return this.buffer;
    }
    
    public void addElement(final int e) {
        this.ensureAppendCapacity();
        this.buffer[this.length++] = e;
    }
    
    public void setElementAt(final int elem, final int index) {
        this.buffer[index] = elem;
    }
    
    public void insertElementAt(final int elem, final int index) {
        this.ensureAppendCapacity();
        System.arraycopy(this.buffer, index, this.buffer, index + 1, this.length - index);
        this.buffer[index] = elem;
        ++this.length;
    }
    
    public void removeAllElements() {
        this.length = 0;
    }
    
    private void ensureAppendCapacity() {
        if (this.length >= this.buffer.length) {
            final int newCapacity = (this.buffer.length <= 65535) ? (4 * this.buffer.length) : (2 * this.buffer.length);
            final int[] newBuffer = new int[newCapacity];
            System.arraycopy(this.buffer, 0, newBuffer, 0, this.length);
            this.buffer = newBuffer;
        }
    }
}
