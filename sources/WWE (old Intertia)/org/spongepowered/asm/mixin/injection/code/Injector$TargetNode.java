package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.*;

public static final class TargetNode
{
    final AbstractInsnNode insn;
    final Set<InjectionPoint> nominators;
    
    TargetNode(final AbstractInsnNode insn) {
        super();
        this.nominators = new HashSet<InjectionPoint>();
        this.insn = insn;
    }
    
    public AbstractInsnNode getNode() {
        return this.insn;
    }
    
    public Set<InjectionPoint> getNominators() {
        return Collections.unmodifiableSet((Set<? extends InjectionPoint>)this.nominators);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o != null && o.getClass() == TargetNode.class && ((TargetNode)o).insn == this.insn;
    }
    
    @Override
    public int hashCode() {
        return this.insn.hashCode();
    }
}
