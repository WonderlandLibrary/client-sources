/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.serialization;

import io.netty.handler.codec.serialization.ClassResolver;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.StreamCorruptedException;

class CompactObjectInputStream
extends ObjectInputStream {
    private final ClassResolver classResolver;

    CompactObjectInputStream(InputStream inputStream, ClassResolver classResolver) throws IOException {
        super(inputStream);
        this.classResolver = classResolver;
    }

    @Override
    protected void readStreamHeader() throws IOException {
        int n = this.readByte() & 0xFF;
        if (n != 5) {
            throw new StreamCorruptedException("Unsupported version: " + n);
        }
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        int n = this.read();
        if (n < 0) {
            throw new EOFException();
        }
        switch (n) {
            case 0: {
                return super.readClassDescriptor();
            }
            case 1: {
                String string = this.readUTF();
                Class<?> clazz = this.classResolver.resolve(string);
                return ObjectStreamClass.lookupAny(clazz);
            }
        }
        throw new StreamCorruptedException("Unexpected class descriptor type: " + n);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
        Class<?> clazz;
        try {
            clazz = this.classResolver.resolve(objectStreamClass.getName());
        } catch (ClassNotFoundException classNotFoundException) {
            clazz = super.resolveClass(objectStreamClass);
        }
        return clazz;
    }
}

