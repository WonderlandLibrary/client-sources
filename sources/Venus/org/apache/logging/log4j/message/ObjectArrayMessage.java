/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import org.apache.logging.log4j.message.Message;

public final class ObjectArrayMessage
implements Message {
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final long serialVersionUID = -5903272448334166185L;
    private transient Object[] array;
    private transient String arrayString;

    public ObjectArrayMessage(Object ... objectArray) {
        this.array = objectArray == null ? EMPTY_OBJECT_ARRAY : objectArray;
    }

    private boolean equalObjectsOrStrings(Object[] objectArray, Object[] objectArray2) {
        return Arrays.equals(objectArray, objectArray2) || Arrays.toString(objectArray).equals(Arrays.toString(objectArray2));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ObjectArrayMessage objectArrayMessage = (ObjectArrayMessage)object;
        return this.array == null ? objectArrayMessage.array == null : this.equalObjectsOrStrings(this.array, objectArrayMessage.array);
    }

    @Override
    public String getFormat() {
        return this.getFormattedMessage();
    }

    @Override
    public String getFormattedMessage() {
        if (this.arrayString == null) {
            this.arrayString = Arrays.toString(this.array);
        }
        return this.arrayString;
    }

    @Override
    public Object[] getParameters() {
        return this.array;
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    public int hashCode() {
        return Arrays.hashCode(this.array);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.array = (Object[])objectInputStream.readObject();
    }

    public String toString() {
        return this.getFormattedMessage();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.array);
    }
}

