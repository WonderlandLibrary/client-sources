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

    CompactObjectInputStream(InputStream in, ClassResolver classResolver) throws IOException {
        super(in);
        this.classResolver = classResolver;
    }

    @Override
    protected void readStreamHeader() throws IOException {
        int version = this.readByte() & 0xFF;
        if (version != 5) {
            throw new StreamCorruptedException("Unsupported version: " + version);
        }
    }

    @Override
    protected ObjectStreamClass readClassDescriptor() throws IOException, ClassNotFoundException {
        int type2 = this.read();
        if (type2 < 0) {
            throw new EOFException();
        }
        switch (type2) {
            case 0: {
                return super.readClassDescriptor();
            }
            case 1: {
                String className = this.readUTF();
                Class<?> clazz = this.classResolver.resolve(className);
                return ObjectStreamClass.lookupAny(clazz);
            }
        }
        throw new StreamCorruptedException("Unexpected class descriptor type: " + type2);
    }

    @Override
    protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
        Class<?> clazz;
        try {
            clazz = this.classResolver.resolve(desc.getName());
        } catch (ClassNotFoundException ignored) {
            clazz = super.resolveClass(desc);
        }
        return clazz;
    }
}

