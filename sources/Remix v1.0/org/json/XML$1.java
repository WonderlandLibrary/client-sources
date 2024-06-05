package org.json;

import java.util.*;

static final class XML$1 implements Iterable<Integer> {
    final /* synthetic */ String val$string;
    
    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int nextIndex = 0;
            private int length = Iterable.this.val$string.length();
            
            @Override
            public boolean hasNext() {
                return this.nextIndex < this.length;
            }
            
            @Override
            public Integer next() {
                final int result = Iterable.this.val$string.codePointAt(this.nextIndex);
                this.nextIndex += Character.charCount(result);
                return result;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}