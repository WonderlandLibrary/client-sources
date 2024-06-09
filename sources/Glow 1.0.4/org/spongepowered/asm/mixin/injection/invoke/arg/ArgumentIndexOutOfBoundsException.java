package org.spongepowered.asm.mixin.injection.invoke.arg;

public class ArgumentIndexOutOfBoundsException extends IndexOutOfBoundsException
{
    private static final long serialVersionUID = 1L;
    
    public ArgumentIndexOutOfBoundsException(final int n) {
        super("Argument index is out of bounds: " + n);
    }
}
