/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;
import java.lang.reflect.Proxy;

public class ClassLoaderObjectInputStream
extends ObjectInputStream {
    private final ClassLoader classLoader;

    public ClassLoaderObjectInputStream(ClassLoader classLoader, InputStream inputStream) throws IOException, StreamCorruptedException {
        super(inputStream);
        this.classLoader = classLoader;
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        try {
            return Class.forName(objectStreamClass.getName(), false, this.classLoader);
        } catch (ClassNotFoundException classNotFoundException) {
            return super.resolveClass(objectStreamClass);
        }
    }

    @Override
    protected Class<?> resolveProxyClass(String[] stringArray) throws IOException, ClassNotFoundException {
        Class[] classArray = new Class[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            classArray[i] = Class.forName(stringArray[i], false, this.classLoader);
        }
        try {
            return Proxy.getProxyClass(this.classLoader, classArray);
        } catch (IllegalArgumentException illegalArgumentException) {
            return super.resolveProxyClass(stringArray);
        }
    }
}

