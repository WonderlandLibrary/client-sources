package org.spongepowered.asm.mixin.injection.callback;

public class CancellationException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public CancellationException() {
        super();
    }
    
    public CancellationException(final String s) {
        super(s);
    }
    
    public CancellationException(final Throwable t) {
        super(t);
    }
    
    public CancellationException(final String s, final Throwable t) {
        super(s, t);
    }
}
