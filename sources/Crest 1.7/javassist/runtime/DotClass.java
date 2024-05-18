// 
// Decompiled by Procyon v0.5.30
// 

package javassist.runtime;

public class DotClass
{
    public static NoClassDefFoundError fail(final ClassNotFoundException e) {
        return new NoClassDefFoundError(e.getMessage());
    }
}
