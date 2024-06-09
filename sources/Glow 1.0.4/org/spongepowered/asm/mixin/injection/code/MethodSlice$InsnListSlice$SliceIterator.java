package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

static class SliceIterator implements ListIterator<AbstractInsnNode>
{
    private final ListIterator<AbstractInsnNode> iter;
    private int start;
    private int end;
    private int index;
    
    public SliceIterator(final ListIterator<AbstractInsnNode> iter, final int start, final int end, final int index) {
        super();
        this.iter = iter;
        this.start = start;
        this.end = end;
        this.index = index;
    }
    
    @Override
    public boolean hasNext() {
        return this.index <= this.end && this.iter.hasNext();
    }
    
    @Override
    public AbstractInsnNode next() {
        if (this.index > this.end) {
            throw new NoSuchElementException();
        }
        ++this.index;
        return this.iter.next();
    }
    
    @Override
    public boolean hasPrevious() {
        return this.index > this.start;
    }
    
    @Override
    public AbstractInsnNode previous() {
        if (this.index <= this.start) {
            throw new NoSuchElementException();
        }
        --this.index;
        return this.iter.previous();
    }
    
    @Override
    public int nextIndex() {
        return this.index - this.start;
    }
    
    @Override
    public int previousIndex() {
        return this.index - this.start - 1;
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Cannot remove insn from slice");
    }
    
    @Override
    public void set(final AbstractInsnNode abstractInsnNode) {
        throw new UnsupportedOperationException("Cannot set insn using slice");
    }
    
    @Override
    public void add(final AbstractInsnNode abstractInsnNode) {
        throw new UnsupportedOperationException("Cannot add insn using slice");
    }
    
    @Override
    public void add(final Object o) {
        this.add((AbstractInsnNode)o);
    }
    
    @Override
    public void set(final Object o) {
        this.set((AbstractInsnNode)o);
    }
    
    @Override
    public Object previous() {
        return this.previous();
    }
    
    @Override
    public Object next() {
        return this.next();
    }
}
