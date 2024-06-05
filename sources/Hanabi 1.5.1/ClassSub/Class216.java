package ClassSub;

import java.util.*;

class Class216 implements Iterator
{
    final ListIterator val$iter;
    final Class20 this$0;
    
    
    Class216(final Class20 this$0, final ListIterator val$iter) {
        this.this$0 = this$0;
        this.val$iter = val$iter;
    }
    
    @Override
    public boolean hasNext() {
        return this.val$iter.hasPrevious();
    }
    
    @Override
    public Object next() {
        return this.val$iter.previous();
    }
    
    @Override
    public void remove() {
        this.val$iter.remove();
    }
}
