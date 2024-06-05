/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;

public class ArrayType<T>
extends Type<T[]> {
    private final Type<T> elementType;

    public ArrayType(Type<T> type) {
        super(type.getTypeName() + " Array", ArrayType.getArrayClass(type.getOutputClass()));
        this.elementType = type;
    }

    public static Class<?> getArrayClass(Class<?> componentType) {
        return Array.newInstance(componentType, 0).getClass();
    }

    @Override
    public T[] read(ByteBuf buffer) throws Exception {
        int amount = Type.VAR_INT.readPrimitive(buffer);
        Object[] array = (Object[])Array.newInstance(this.elementType.getOutputClass(), amount);
        int i = 0;
        while (i < amount) {
            array[i] = this.elementType.read(buffer);
            ++i;
        }
        return array;
    }

    @Override
    public void write(ByteBuf buffer, T[] object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.length);
        T[] TArray = object;
        int n = TArray.length;
        int n2 = 0;
        while (n2 < n) {
            T o = TArray[n2];
            this.elementType.write(buffer, o);
            ++n2;
        }
    }
}

