/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.util.StringBuilderFormattable;

public class SimpleMessage
implements Message,
StringBuilderFormattable,
CharSequence {
    private static final long serialVersionUID = -8398002534962715992L;
    private String message;
    private transient CharSequence charSequence;

    public SimpleMessage() {
        this(null);
    }

    public SimpleMessage(String string) {
        this.message = string;
        this.charSequence = string;
    }

    public SimpleMessage(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    @Override
    public String getFormattedMessage() {
        if (this.message == null) {
            this.message = String.valueOf(this.charSequence);
        }
        return this.message;
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        if (this.message != null) {
            stringBuilder.append(this.message);
        } else {
            stringBuilder.append(this.charSequence);
        }
    }

    @Override
    public String getFormat() {
        return this.getFormattedMessage();
    }

    @Override
    public Object[] getParameters() {
        return null;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        SimpleMessage simpleMessage = (SimpleMessage)object;
        return !(this.charSequence == null ? simpleMessage.charSequence != null : !this.charSequence.equals(simpleMessage.charSequence));
    }

    public int hashCode() {
        return this.charSequence != null ? this.charSequence.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.getFormattedMessage();
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    @Override
    public int length() {
        return this.charSequence == null ? 0 : this.charSequence.length();
    }

    @Override
    public char charAt(int n) {
        return this.charSequence.charAt(n);
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return this.charSequence.subSequence(n, n2);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.getFormattedMessage();
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.charSequence = this.message;
    }
}

