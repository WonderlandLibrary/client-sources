package org.spongepowered.asm.mixin.injection.invoke.arg;

public class ArgumentCountException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1L;
    
    public ArgumentCountException(final int n, final int n2, final String s) {
        super("Invalid number of arguments for setAll, received " + n + " but expected " + n2 + ": " + s);
    }
}
