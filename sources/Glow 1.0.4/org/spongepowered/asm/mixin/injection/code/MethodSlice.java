package org.spongepowered.asm.mixin.injection.code;

import com.google.common.base.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import org.apache.logging.log4j.*;
import java.util.*;

public final class MethodSlice
{
    private static final Logger logger;
    private final ISliceContext owner;
    private final String id;
    private final InjectionPoint from;
    private final InjectionPoint to;
    private final String name;
    
    private MethodSlice(final ISliceContext owner, final String s, final InjectionPoint from, final InjectionPoint to) {
        super();
        if (from == null && to == null) {
            throw new InvalidSliceException(owner, String.format("%s is redundant. No 'from' or 'to' value specified", this));
        }
        this.owner = owner;
        this.id = Strings.nullToEmpty(s);
        this.from = from;
        this.to = to;
        this.name = getSliceName(s);
    }
    
    public String getId() {
        return this.id;
    }
    
    public ReadOnlyInsnList getSlice(final MethodNode methodNode) {
        final int n = methodNode.instructions.size() - 1;
        final int find = this.find(methodNode, this.from, 0, 0, this.name + "(from)");
        final int find2 = this.find(methodNode, this.to, n, find, this.name + "(to)");
        if (find > find2) {
            throw new InvalidSliceException(this.owner, String.format("%s is negative size. Range(%d -> %d)", this.describe(), find, find2));
        }
        if (find < 0 || find2 < 0 || find > n || find2 > n) {
            throw new InjectionError("Unexpected critical error in " + this + ": out of bounds start=" + find + " end=" + find2 + " lim=" + n);
        }
        if (find == 0 && find2 == n) {
            return new ReadOnlyInsnList(methodNode.instructions);
        }
        return new InsnListSlice(methodNode.instructions, find, find2);
    }
    
    private int find(final MethodNode methodNode, final InjectionPoint injectionPoint, final int n, final int n2, final String s) {
        if (injectionPoint == null) {
            return n;
        }
        final LinkedList<AbstractInsnNode> list = new LinkedList<AbstractInsnNode>();
        final boolean find = injectionPoint.find(methodNode.desc, new ReadOnlyInsnList(methodNode.instructions), list);
        final InjectionPoint.Selector selector = injectionPoint.getSelector();
        if (list.size() != 1 && selector == InjectionPoint.Selector.ONE) {
            throw new InvalidSliceException(this.owner, String.format("%s requires 1 result but found %d", this.describe(s), list.size()));
        }
        if (!find) {
            if (this.owner.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                MethodSlice.logger.warn("{} did not match any instructions", new Object[] { this.describe(s) });
            }
            return n2;
        }
        return methodNode.instructions.indexOf((selector == InjectionPoint.Selector.FIRST) ? list.getFirst() : list.getLast());
    }
    
    @Override
    public String toString() {
        return this.describe();
    }
    
    private String describe() {
        return this.describe(this.name);
    }
    
    private String describe(final String s) {
        return describeSlice(s, this.owner);
    }
    
    private static String describeSlice(final String s, final ISliceContext sliceContext) {
        final String simpleName = Bytecode.getSimpleName(sliceContext.getAnnotation());
        final MethodNode method = sliceContext.getMethod();
        return String.format("%s->%s(%s)::%s%s", sliceContext.getContext(), simpleName, s, method.name, method.desc);
    }
    
    private static String getSliceName(final String s) {
        return String.format("@Slice[%s]", Strings.nullToEmpty(s));
    }
    
    public static MethodSlice parse(final ISliceContext sliceContext, final Slice slice) {
        final String id = slice.id();
        final At from = slice.from();
        final At to = slice.to();
        return new MethodSlice(sliceContext, id, (from != null) ? InjectionPoint.parse(sliceContext, from) : null, (to != null) ? InjectionPoint.parse(sliceContext, to) : null);
    }
    
    public static MethodSlice parse(final ISliceContext sliceContext, final AnnotationNode annotationNode) {
        final String s = Annotations.getValue(annotationNode, "id");
        final AnnotationNode annotationNode2 = Annotations.getValue(annotationNode, "from");
        final AnnotationNode annotationNode3 = Annotations.getValue(annotationNode, "to");
        return new MethodSlice(sliceContext, s, (annotationNode2 != null) ? InjectionPoint.parse(sliceContext, annotationNode2) : null, (annotationNode3 != null) ? InjectionPoint.parse(sliceContext, annotationNode3) : null);
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
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
}
