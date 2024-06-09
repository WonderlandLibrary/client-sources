package org.spongepowered.asm.util.throwables;

public class InvalidConstraintException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidConstraintException() {
        super();
    }
    
    public InvalidConstraintException(final String s) {
        super(s);
    }
    
    public InvalidConstraintException(final Throwable t) {
        super(t);
    }
    
    public InvalidConstraintException(final String s, final Throwable t) {
        super(s, t);
    }
}
