package com.ibm.icu.impl;

import java.text.*;
import com.ibm.icu.text.*;

public class UCharacterIteratorWrapper implements CharacterIterator
{
    private UCharacterIterator iterator;
    
    public UCharacterIteratorWrapper(final UCharacterIterator iter) {
        this.iterator = iter;
    }
    
    @Override
    public char first() {
        this.iterator.setToStart();
        return (char)this.iterator.current();
    }
    
    @Override
    public char last() {
        this.iterator.setToLimit();
        return (char)this.iterator.previous();
    }
    
    @Override
    public char current() {
        return (char)this.iterator.current();
    }
    
    @Override
    public char next() {
        this.iterator.next();
        return (char)this.iterator.current();
    }
    
    @Override
    public char previous() {
        return (char)this.iterator.previous();
    }
    
    @Override
    public char setIndex(final int position) {
        this.iterator.setIndex(position);
        return (char)this.iterator.current();
    }
    
    @Override
    public int getBeginIndex() {
        return 0;
    }
    
    @Override
    public int getEndIndex() {
        return this.iterator.getLength();
    }
    
    @Override
    public int getIndex() {
        return this.iterator.getIndex();
    }
    
    @Override
    public Object clone() {
        try {
            final UCharacterIteratorWrapper result = (UCharacterIteratorWrapper)super.clone();
            result.iterator = (UCharacterIterator)this.iterator.clone();
            return result;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
