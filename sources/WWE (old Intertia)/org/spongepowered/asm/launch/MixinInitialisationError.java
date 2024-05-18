package org.spongepowered.asm.launch;

public class MixinInitialisationError extends Error
{
    private static final long serialVersionUID = 1L;
    
    public MixinInitialisationError() {
        super();
    }
    
    public MixinInitialisationError(final String s) {
        super(s);
    }
    
    public MixinInitialisationError(final Throwable t) {
        super(t);
    }
    
    public MixinInitialisationError(final String s, final Throwable t) {
        super(s, t);
    }
}
