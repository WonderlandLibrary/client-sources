package org.spongepowered.asm.mixin.injection.invoke.util;

import org.spongepowered.asm.lib.tree.*;

static class AnalysisResultException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private AbstractInsnNode result;
    
    public AnalysisResultException(final AbstractInsnNode result) {
        super();
        this.result = result;
    }
    
    public AbstractInsnNode getResult() {
        return this.result;
    }
}
