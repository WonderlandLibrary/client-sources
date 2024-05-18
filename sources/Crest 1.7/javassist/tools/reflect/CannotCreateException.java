// 
// Decompiled by Procyon v0.5.30
// 

package javassist.tools.reflect;

public class CannotCreateException extends Exception
{
    public CannotCreateException(final String s) {
        super(s);
    }
    
    public CannotCreateException(final Exception e) {
        super("by " + e.toString());
    }
}
