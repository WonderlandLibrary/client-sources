// 
// Decompiled by Procyon v0.5.30
// 

package javassist.util.proxy;

import java.lang.reflect.Method;

public interface MethodFilter
{
    boolean isHandled(final Method p0);
}
