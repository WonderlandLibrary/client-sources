/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Locale;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.status.StatusLogger;

public class StringFormattedMessage
implements Message {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final long serialVersionUID = -665975803997290697L;
    private static final int HASHVAL = 31;
    private String messagePattern;
    private transient Object[] argArray;
    private String[] stringArgs;
    private transient String formattedMessage;
    private transient Throwable throwable;
    private final Locale locale;

    public StringFormattedMessage(Locale locale, String string, Object ... objectArray) {
        this.locale = locale;
        this.messagePattern = string;
        this.argArray = objectArray;
        if (objectArray != null && objectArray.length > 0 && objectArray[objectArray.length - 1] instanceof Throwable) {
            this.throwable = (Throwable)objectArray[objectArray.length - 1];
        }
    }

    public StringFormattedMessage(String string, Object ... objectArray) {
        this(Locale.getDefault(Locale.Category.FORMAT), string, objectArray);
    }

    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage == null) {
            this.formattedMessage = this.formatMessage(this.messagePattern, this.argArray);
        }
        return this.formattedMessage;
    }

    @Override
    public String getFormat() {
        return this.messagePattern;
    }

    @Override
    public Object[] getParameters() {
        if (this.argArray != null) {
            return this.argArray;
        }
        return this.stringArgs;
    }

    protected String formatMessage(String string, Object ... objectArray) {
        try {
            return String.format(this.locale, string, objectArray);
        } catch (IllegalFormatException illegalFormatException) {
            LOGGER.error("Unable to format msg: " + string, (Throwable)illegalFormatException);
            return string;
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        StringFormattedMessage stringFormattedMessage = (StringFormattedMessage)object;
        if (this.messagePattern != null ? !this.messagePattern.equals(stringFormattedMessage.messagePattern) : stringFormattedMessage.messagePattern != null) {
            return true;
        }
        return Arrays.equals(this.stringArgs, stringFormattedMessage.stringArgs);
    }

    public int hashCode() {
        int n = this.messagePattern != null ? this.messagePattern.hashCode() : 0;
        n = 31 * n + (this.stringArgs != null ? Arrays.hashCode(this.stringArgs) : 0);
        return n;
    }

    public String toString() {
        return this.getFormattedMessage();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        this.getFormattedMessage();
        objectOutputStream.writeUTF(this.formattedMessage);
        objectOutputStream.writeUTF(this.messagePattern);
        objectOutputStream.writeInt(this.argArray.length);
        this.stringArgs = new String[this.argArray.length];
        int n = 0;
        for (Object object : this.argArray) {
            String string;
            this.stringArgs[n] = string = String.valueOf(object);
            objectOutputStream.writeUTF(string);
            ++n;
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.formattedMessage = objectInputStream.readUTF();
        this.messagePattern = objectInputStream.readUTF();
        int n = objectInputStream.readInt();
        this.stringArgs = new String[n];
        for (int i = 0; i < n; ++i) {
            this.stringArgs[i] = objectInputStream.readUTF();
        }
    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }
}

