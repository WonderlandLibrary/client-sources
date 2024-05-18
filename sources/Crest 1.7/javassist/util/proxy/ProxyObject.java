// 
// Decompiled by Procyon v0.5.30
// 

package javassist.util.proxy;

public interface ProxyObject extends Proxy
{
    void setHandler(final MethodHandler p0);
    
    MethodHandler getHandler();
}
