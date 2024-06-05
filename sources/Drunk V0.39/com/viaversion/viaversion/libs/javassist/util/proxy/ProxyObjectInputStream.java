/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.javassist.util.proxy;

import com.viaversion.viaversion.libs.javassist.util.proxy.ProxyFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class ProxyObjectInputStream
extends ObjectInputStream {
    private ClassLoader loader = Thread.currentThread().getContextClassLoader();

    public ProxyObjectInputStream(InputStream in) throws IOException {
        super(in);
        if (this.loader != null) return;
        this.loader = ClassLoader.getSystemClassLoader();
    }

    public void setClassLoader(ClassLoader loader) {
        if (loader != null) {
            this.loader = loader;
            return;
        }
        loader = ClassLoader.getSystemClassLoader();
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        boolean isProxy = this.readBoolean();
        if (!isProxy) return super.readClassDescriptor();
        String name = (String)this.readObject();
        Class<?> superClass = this.loader.loadClass(name);
        int length = this.readInt();
        Class[] interfaces = new Class[length];
        int i = 0;
        while (true) {
            if (i >= length) {
                length = this.readInt();
                byte[] signature = new byte[length];
                this.read(signature);
                ProxyFactory factory = new ProxyFactory();
                factory.setUseCache(true);
                factory.setUseWriteReplace(false);
                factory.setSuperclass(superClass);
                factory.setInterfaces(interfaces);
                Class<?> proxyClass = factory.createClass(signature);
                return ObjectStreamClass.lookup(proxyClass);
            }
            name = (String)this.readObject();
            interfaces[i] = this.loader.loadClass(name);
            ++i;
        }
    }
}

