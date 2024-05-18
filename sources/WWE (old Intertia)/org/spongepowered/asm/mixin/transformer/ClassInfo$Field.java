package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.*;

class Field extends Member
{
    final ClassInfo this$0;
    
    public Field(final ClassInfo this$0, final Member member) {
        this.this$0 = this$0;
        super(member);
    }
    
    public Field(final ClassInfo classInfo, final FieldNode fieldNode) {
        this(classInfo, fieldNode, false);
    }
    
    public Field(final ClassInfo this$0, final FieldNode fieldNode, final boolean b) {
        this.this$0 = this$0;
        super(Type.FIELD, fieldNode.name, fieldNode.desc, fieldNode.access, b);
        this.setUnique(Annotations.getVisible(fieldNode, Unique.class) != null);
        if (Annotations.getVisible(fieldNode, Shadow.class) != null) {
            this.setDecoratedFinal(Annotations.getVisible(fieldNode, Final.class) != null, Annotations.getVisible(fieldNode, Mutable.class) != null);
        }
    }
    
    public Field(final ClassInfo this$0, final String s, final String s2, final int n) {
        this.this$0 = this$0;
        super(Type.FIELD, s, s2, n, false);
    }
    
    public Field(final ClassInfo this$0, final String s, final String s2, final int n, final boolean b) {
        this.this$0 = this$0;
        super(Type.FIELD, s, s2, n, b);
    }
    
    @Override
    public ClassInfo getOwner() {
        return this.this$0;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof Field && super.equals(o);
    }
    
    @Override
    protected String getDisplayFormat() {
        return "%s:%s";
    }
}
