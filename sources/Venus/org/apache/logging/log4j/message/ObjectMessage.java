/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.apache.logging.log4j.util.StringBuilders;

public class ObjectMessage
implements Message,
StringBuilderFormattable {
    private static final long serialVersionUID = -5903272448334166185L;
    private transient Object obj;
    private transient String objectString;

    public ObjectMessage(Object object) {
        this.obj = object == null ? "null" : object;
    }

    @Override
    public String getFormattedMessage() {
        if (this.objectString == null) {
            this.objectString = String.valueOf(this.obj);
        }
        return this.objectString;
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        if (this.objectString != null) {
            stringBuilder.append(this.objectString);
        } else {
            StringBuilders.appendValue(stringBuilder, this.obj);
        }
    }

    @Override
    public String getFormat() {
        return this.getFormattedMessage();
    }

    public Object getParameter() {
        return this.obj;
    }

    @Override
    public Object[] getParameters() {
        return new Object[]{this.obj};
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ObjectMessage objectMessage = (ObjectMessage)object;
        return this.obj == null ? objectMessage.obj == null : this.equalObjectsOrStrings(this.obj, objectMessage.obj);
    }

    private boolean equalObjectsOrStrings(Object object, Object object2) {
        return object.equals(object2) || String.valueOf(object).equals(String.valueOf(object2));
    }

    public int hashCode() {
        return this.obj != null ? this.obj.hashCode() : 0;
    }

    public String toString() {
        return this.getFormattedMessage();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        if (this.obj instanceof Serializable) {
            objectOutputStream.writeObject(this.obj);
        } else {
            objectOutputStream.writeObject(String.valueOf(this.obj));
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.obj = objectInputStream.readObject();
    }

    @Override
    public Throwable getThrowable() {
        return this.obj instanceof Throwable ? (Throwable)this.obj : null;
    }
}

