package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

static final class InsnListSlice extends ReadOnlyInsnList
{
    private final int start;
    private final int end;
    
    protected InsnListSlice(final InsnList list, final int start, final int end) {
        super(list);
        this.start = start;
        this.end = end;
    }
    
    @Override
    public ListIterator<AbstractInsnNode> iterator() {
        return this.iterator(0);
    }
    
    @Override
    public ListIterator<AbstractInsnNode> iterator(final int n) {
        return new SliceIterator(super.iterator(this.start + n), this.start, this.end, this.start + n);
    }
    
    @Override
    public AbstractInsnNode[] toArray() {
        final AbstractInsnNode[] array = super.toArray();
        final AbstractInsnNode[] array2 = new AbstractInsnNode[this.size()];
        System.arraycopy(array, this.start, array2, 0, array2.length);
        return array2;
    }
    
    @Override
    public int size() {
        return this.end - this.start + 1;
    }
    
    @Override
    public AbstractInsnNode getFirst() {
        return super.get(this.start);
    }
    
    @Override
    public AbstractInsnNode getLast() {
        return super.get(this.end);
    }
    
    @Override
    public AbstractInsnNode get(final int n) {
        return super.get(this.start + n);
    }
    
    @Override
    public boolean contains(final AbstractInsnNode abstractInsnNode) {
        final AbstractInsnNode[] array = this.toArray();
        for (int length = array.length, i = 0; i < length; ++i) {
            if (array[i] == abstractInsnNode) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int indexOf(final AbstractInsnNode abstractInsnNode) {
        final int index = super.indexOf(abstractInsnNode);
        return (index >= this.start && index <= this.end) ? (index - this.start) : -1;
    }
    
    public int realIndexOf(final AbstractInsnNode abstractInsnNode) {
        return super.indexOf(abstractInsnNode);
    }
    
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
}
