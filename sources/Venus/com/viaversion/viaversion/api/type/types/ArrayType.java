/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ArrayType<T>
extends Type<T[]> {
    private final Type<T> elementType;

    public ArrayType(Type<T> type) {
        super(type.getTypeName() + " Array", ArrayType.getArrayClass(type.getOutputClass()));
        this.elementType = type;
    }

    public static Class<?> getArrayClass(Class<?> clazz) {
        return Array.newInstance(clazz, 0).getClass();
    }

    @Override
    public T[] read(ByteBuf byteBuf) throws Exception {
        int n = Type.VAR_INT.readPrimitive(byteBuf);
        Object[] objectArray = (Object[])Array.newInstance(this.elementType.getOutputClass(), n);
        for (int i = 0; i < n; ++i) {
            objectArray[i] = this.elementType.read(byteBuf);
        }
        return objectArray;
    }

    @Override
    public void write(ByteBuf byteBuf, T[] TArray) throws Exception {
        Type.VAR_INT.writePrimitive(byteBuf, TArray.length);
        for (T t : TArray) {
            this.elementType.write(byteBuf, t);
        }
    }

    @Override
    public Object read(ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }

    @Override
    public void write(ByteBuf byteBuf, Object object) throws Exception {
        this.write(byteBuf, (Object[])object);
    }
}

