package org.spongepowered.asm.lib.tree;

import java.util.*;

class MethodNode$1 extends ArrayList<Object> {
    final MethodNode this$0;
    
    MethodNode$1(final MethodNode this$0, final int n) {
        this.this$0 = this$0;
        super(n);
    }
    
    @Override
    public boolean add(final Object annotationDefault) {
        this.this$0.annotationDefault = annotationDefault;
        return super.add(annotationDefault);
    }
}