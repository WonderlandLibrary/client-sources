/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Locale;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.status.StatusLogger;

public class MessageFormatMessage
implements Message {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final long serialVersionUID = 1L;
    private static final int HASHVAL = 31;
    private String messagePattern;
    private transient Object[] parameters;
    private String[] serializedParameters;
    private transient String formattedMessage;
    private transient Throwable throwable;
    private final Locale locale;

    public MessageFormatMessage(Locale locale, String string, Object ... objectArray) {
        int n;
        this.locale = locale;
        this.messagePattern = string;
        this.parameters = objectArray;
        int n2 = n = objectArray == null ? 0 : objectArray.length;
        if (n > 0 && objectArray[n - 1] instanceof Throwable) {
            this.throwable = (Throwable)objectArray[n - 1];
        }
    }

    public MessageFormatMessage(String string, Object ... objectArray) {
        this(Locale.getDefault(Locale.Category.FORMAT), string, objectArray);
    }

    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage == null) {
            this.formattedMessage = this.formatMessage(this.messagePattern, this.parameters);
        }
        return this.formattedMessage;
    }

    @Override
    public String getFormat() {
        return this.messagePattern;
    }

    @Override
    public Object[] getParameters() {
        if (this.parameters != null) {
            return this.parameters;
        }
        return this.serializedParameters;
    }

    protected String formatMessage(String string, Object ... objectArray) {
        try {
            MessageFormat messageFormat = new MessageFormat(string, this.locale);
            return messageFormat.format(objectArray);
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
        MessageFormatMessage messageFormatMessage = (MessageFormatMessage)object;
        if (this.messagePattern != null ? !this.messagePattern.equals(messageFormatMessage.messagePattern) : messageFormatMessage.messagePattern != null) {
            return true;
        }
        return Arrays.equals(this.serializedParameters, messageFormatMessage.serializedParameters);
    }

    public int hashCode() {
        int n = this.messagePattern != null ? this.messagePattern.hashCode() : 0;
        n = 31 * n + (this.serializedParameters != null ? Arrays.hashCode(this.serializedParameters) : 0);
        return n;
    }

    public String toString() {
        return this.getFormattedMessage();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.getFormattedMessage();
        objectOutputStream.writeUTF(this.formattedMessage);
        objectOutputStream.writeUTF(this.messagePattern);
        int n = this.parameters == null ? 0 : this.parameters.length;
        objectOutputStream.writeInt(n);
        this.serializedParameters = new String[n];
        if (n > 0) {
            for (int i = 0; i < n; ++i) {
                this.serializedParameters[i] = String.valueOf(this.parameters[i]);
                objectOutputStream.writeUTF(this.serializedParameters[i]);
            }
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        this.parameters = null;
        this.throwable = null;
        this.formattedMessage = objectInputStream.readUTF();
        this.messagePattern = objectInputStream.readUTF();
        int n = objectInputStream.readInt();
        this.serializedParameters = new String[n];
        for (int i = 0; i < n; ++i) {
            this.serializedParameters[i] = objectInputStream.readUTF();
        }
    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }
}

