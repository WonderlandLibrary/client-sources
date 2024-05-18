package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.refmap.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.injection.struct.*;

abstract static class ContextualInjectionPoint extends InjectionPoint
{
    protected final IMixinContext context;
    
    ContextualInjectionPoint(final IMixinContext context) {
        super();
        this.context = context;
    }
    
    @Override
    public boolean find(final String s, final InsnList list, final Collection<AbstractInsnNode> collection) {
        throw new InvalidInjectionException(this.context, this.getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
    }
    
    abstract boolean find(final Target p0, final Collection<AbstractInsnNode> p1);
}
