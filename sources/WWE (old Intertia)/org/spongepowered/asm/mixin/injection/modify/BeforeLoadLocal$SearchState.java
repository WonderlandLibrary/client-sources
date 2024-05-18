package org.spongepowered.asm.mixin.injection.modify;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;

static class SearchState
{
    private final boolean print;
    private final int targetOrdinal;
    private int ordinal;
    private boolean pendingCheck;
    private boolean found;
    private VarInsnNode varNode;
    
    SearchState(final int targetOrdinal, final boolean print) {
        super();
        this.ordinal = 0;
        this.pendingCheck = false;
        this.found = false;
        this.targetOrdinal = targetOrdinal;
        this.print = print;
    }
    
    boolean success() {
        return this.found;
    }
    
    boolean isPendingCheck() {
        return this.pendingCheck;
    }
    
    void setPendingCheck() {
        this.pendingCheck = true;
    }
    
    void register(final VarInsnNode varNode) {
        this.varNode = varNode;
    }
    
    void check(final Collection<AbstractInsnNode> collection, final AbstractInsnNode abstractInsnNode, final int n) {
        this.pendingCheck = false;
        if (n != this.varNode.var && (n > -2 || !this.print)) {
            return;
        }
        if (this.targetOrdinal == -1 || this.targetOrdinal == this.ordinal) {
            collection.add(abstractInsnNode);
            this.found = true;
        }
        ++this.ordinal;
        this.varNode = null;
    }
}
