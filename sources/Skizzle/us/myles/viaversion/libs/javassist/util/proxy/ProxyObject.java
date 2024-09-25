/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.javassist.util.proxy;

import us.myles.viaversion.libs.javassist.util.proxy.MethodHandler;
import us.myles.viaversion.libs.javassist.util.proxy.Proxy;

public interface ProxyObject
extends Proxy {
    @Override
    public void setHandler(MethodHandler var1);

    public MethodHandler getHandler();
}

