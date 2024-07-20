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

    public ObjectMessage(Object obj) {
        this.obj = obj == null ? "null" : obj;
    }

    @Override
    public String getFormattedMessage() {
        if (this.objectString == null) {
            this.objectString = String.valueOf(this.obj);
        }
        return this.objectString;
    }

    @Override
    public void formatTo(StringBuilder buffer) {
        if (this.objectString != null) {
            buffer.append(this.objectString);
        } else {
            StringBuilders.appendValue(buffer, this.obj);
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        ObjectMessage that = (ObjectMessage)o;
        return this.obj == null ? that.obj == null : this.equalObjectsOrStrings(this.obj, that.obj);
    }

    private boolean equalObjectsOrStrings(Object left, Object right) {
        return left.equals(right) || String.valueOf(left).equals(String.valueOf(right));
    }

    public int hashCode() {
        return this.obj != null ? this.obj.hashCode() : 0;
    }

    public String toString() {
        return this.getFormattedMessage();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if (this.obj instanceof Serializable) {
            out.writeObject(this.obj);
        } else {
            out.writeObject(String.valueOf(this.obj));
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.obj = in.readObject();
    }

    @Override
    public Throwable getThrowable() {
        return this.obj instanceof Throwable ? (Throwable)this.obj : null;
    }
}

