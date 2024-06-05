package com.mpatric.mp3agic;

public class MutableInteger
{
    private int value;
    
    public MutableInteger(final int value) {
        this.value = value;
    }
    
    public void increment() {
        ++this.value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public void setValue(final int value) {
        this.value = value;
    }
    
    @Override
    public int hashCode() {
        return 31 * 1 + this.value;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o != null && this.getClass() == o.getClass() && this.value == ((MutableInteger)o).value);
    }
}
