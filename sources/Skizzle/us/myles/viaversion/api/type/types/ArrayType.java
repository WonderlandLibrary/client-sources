/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 */
package us.myles.ViaVersion.api.type.types;

import io.netty.buffer.ByteBuf;
import java.lang.reflect.Array;
import us.myles.ViaVersion.api.type.Type;

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
        for (int i = 0; i < amount; ++i) {
            array[i] = this.elementType.read(buffer);
        }
        return array;
    }

    @Override
    public void write(ByteBuf buffer, T[] object) throws Exception {
        Type.VAR_INT.writePrimitive(buffer, object.length);
        for (T o : object) {
            this.elementType.write(buffer, o);
        }
    }
}

