package org.spongepowered.tools.obfuscation;

public static class ReferenceConflictException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private final String oldReference;
    private final String newReference;
    
    public ReferenceConflictException(final String oldReference, final String newReference) {
        super();
        this.oldReference = oldReference;
        this.newReference = newReference;
    }
    
    public String getOld() {
        return this.oldReference;
    }
    
    public String getNew() {
        return this.newReference;
    }
}
