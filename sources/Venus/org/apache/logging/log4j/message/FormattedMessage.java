/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.Format;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFormatMessage;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.StringFormattedMessage;

public class FormattedMessage
implements Message {
    private static final long serialVersionUID = -665975803997290697L;
    private static final int HASHVAL = 31;
    private static final String FORMAT_SPECIFIER = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
    private static final Pattern MSG_PATTERN = Pattern.compile("%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])");
    private String messagePattern;
    private transient Object[] argArray;
    private String[] stringArgs;
    private transient String formattedMessage;
    private final Throwable throwable;
    private Message message;
    private final Locale locale;

    public FormattedMessage(Locale locale, String string, Object object) {
        this(locale, string, new Object[]{object}, null);
    }

    public FormattedMessage(Locale locale, String string, Object object, Object object2) {
        this(locale, string, new Object[]{object, object2});
    }

    public FormattedMessage(Locale locale, String string, Object ... objectArray) {
        this(locale, string, objectArray, null);
    }

    public FormattedMessage(Locale locale, String string, Object[] objectArray, Throwable throwable) {
        this.locale = locale;
        this.messagePattern = string;
        this.argArray = objectArray;
        this.throwable = throwable;
    }

    public FormattedMessage(String string, Object object) {
        this(string, new Object[]{object}, null);
    }

    public FormattedMessage(String string, Object object, Object object2) {
        this(string, new Object[]{object, object2});
    }

    public FormattedMessage(String string, Object ... objectArray) {
        this(string, objectArray, null);
    }

    public FormattedMessage(String string, Object[] objectArray, Throwable throwable) {
        this.locale = Locale.getDefault(Locale.Category.FORMAT);
        this.messagePattern = string;
        this.argArray = objectArray;
        this.throwable = throwable;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        FormattedMessage formattedMessage = (FormattedMessage)object;
        if (this.messagePattern != null ? !this.messagePattern.equals(formattedMessage.messagePattern) : formattedMessage.messagePattern != null) {
            return true;
        }
        return !Arrays.equals(this.stringArgs, formattedMessage.stringArgs);
    }

    @Override
    public String getFormat() {
        return this.messagePattern;
    }

    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage == null) {
            if (this.message == null) {
                this.message = this.getMessage(this.messagePattern, this.argArray, this.throwable);
            }
            this.formattedMessage = this.message.getFormattedMessage();
        }
        return this.formattedMessage;
    }

    protected Message getMessage(String string, Object[] objectArray, Throwable throwable) {
        try {
            MessageFormat messageFormat = new MessageFormat(string);
            Format[] formatArray = messageFormat.getFormats();
            if (formatArray != null && formatArray.length > 0) {
                return new MessageFormatMessage(this.locale, string, objectArray);
            }
        } catch (Exception exception) {
            // empty catch block
        }
        try {
            if (MSG_PATTERN.matcher(string).find()) {
                return new StringFormattedMessage(this.locale, string, objectArray);
            }
        } catch (Exception exception) {
            // empty catch block
        }
        return new ParameterizedMessage(string, objectArray, throwable);
    }

    @Override
    public Object[] getParameters() {
        if (this.argArray != null) {
            return this.argArray;
        }
        return this.stringArgs;
    }

    @Override
    public Throwable getThrowable() {
        if (this.throwable != null) {
            return this.throwable;
        }
        if (this.message == null) {
            this.message = this.getMessage(this.messagePattern, this.argArray, null);
        }
        return this.message.getThrowable();
    }

    public int hashCode() {
        int n = this.messagePattern != null ? this.messagePattern.hashCode() : 0;
        n = 31 * n + (this.stringArgs != null ? Arrays.hashCode(this.stringArgs) : 0);
        return n;
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
            this.stringArgs[n] = object.toString();
            ++n;
        }
    }
}

