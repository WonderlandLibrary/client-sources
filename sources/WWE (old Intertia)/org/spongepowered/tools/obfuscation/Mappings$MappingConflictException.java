package org.spongepowered.tools.obfuscation;

import org.spongepowered.asm.obfuscation.mapping.*;

public static class MappingConflictException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private final IMapping<?> oldMapping;
    private final IMapping<?> newMapping;
    
    public MappingConflictException(final IMapping<?> oldMapping, final IMapping<?> newMapping) {
        super();
        this.oldMapping = oldMapping;
        this.newMapping = newMapping;
    }
    
    public IMapping<?> getOld() {
        return this.oldMapping;
    }
    
    public IMapping<?> getNew() {
        return this.newMapping;
    }
}
