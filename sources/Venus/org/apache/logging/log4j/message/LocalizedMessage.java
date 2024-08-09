/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.logging.log4j.message.FormattedMessage;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.status.StatusLogger;

public class LocalizedMessage
implements Message,
LoggerNameAwareMessage {
    private static final long serialVersionUID = 3893703791567290742L;
    private String baseName;
    private transient ResourceBundle resourceBundle;
    private final Locale locale;
    private transient StatusLogger logger = StatusLogger.getLogger();
    private String loggerName;
    private String key;
    private String[] stringArgs;
    private transient Object[] argArray;
    private String formattedMessage;
    private transient Throwable throwable;

    public LocalizedMessage(String string, Object[] objectArray) {
        this((ResourceBundle)null, (Locale)null, string, objectArray);
    }

    public LocalizedMessage(String string, String string2, Object[] objectArray) {
        this(string, (Locale)null, string2, objectArray);
    }

    public LocalizedMessage(ResourceBundle resourceBundle, String string, Object[] objectArray) {
        this(resourceBundle, (Locale)null, string, objectArray);
    }

    public LocalizedMessage(String string, Locale locale, String string2, Object[] objectArray) {
        this.key = string2;
        this.argArray = objectArray;
        this.throwable = null;
        this.baseName = string;
        this.resourceBundle = null;
        this.locale = locale;
    }

    public LocalizedMessage(ResourceBundle resourceBundle, Locale locale, String string, Object[] objectArray) {
        this.key = string;
        this.argArray = objectArray;
        this.throwable = null;
        this.baseName = null;
        this.resourceBundle = resourceBundle;
        this.locale = locale;
    }

    public LocalizedMessage(Locale locale, String string, Object[] objectArray) {
        this((ResourceBundle)null, locale, string, objectArray);
    }

    public LocalizedMessage(String string, Object object) {
        this((ResourceBundle)null, (Locale)null, string, new Object[]{object});
    }

    public LocalizedMessage(String string, String string2, Object object) {
        this(string, (Locale)null, string2, new Object[]{object});
    }

    public LocalizedMessage(ResourceBundle resourceBundle, String string) {
        this(resourceBundle, (Locale)null, string, new Object[0]);
    }

    public LocalizedMessage(ResourceBundle resourceBundle, String string, Object object) {
        this(resourceBundle, (Locale)null, string, new Object[]{object});
    }

    public LocalizedMessage(String string, Locale locale, String string2, Object object) {
        this(string, locale, string2, new Object[]{object});
    }

    public LocalizedMessage(ResourceBundle resourceBundle, Locale locale, String string, Object object) {
        this(resourceBundle, locale, string, new Object[]{object});
    }

    public LocalizedMessage(Locale locale, String string, Object object) {
        this((ResourceBundle)null, locale, string, new Object[]{object});
    }

    public LocalizedMessage(String string, Object object, Object object2) {
        this((ResourceBundle)null, (Locale)null, string, new Object[]{object, object2});
    }

    public LocalizedMessage(String string, String string2, Object object, Object object2) {
        this(string, (Locale)null, string2, new Object[]{object, object2});
    }

    public LocalizedMessage(ResourceBundle resourceBundle, String string, Object object, Object object2) {
        this(resourceBundle, (Locale)null, string, new Object[]{object, object2});
    }

    public LocalizedMessage(String string, Locale locale, String string2, Object object, Object object2) {
        this(string, locale, string2, new Object[]{object, object2});
    }

    public LocalizedMessage(ResourceBundle resourceBundle, Locale locale, String string, Object object, Object object2) {
        this(resourceBundle, locale, string, new Object[]{object, object2});
    }

    public LocalizedMessage(Locale locale, String string, Object object, Object object2) {
        this((ResourceBundle)null, locale, string, new Object[]{object, object2});
    }

    @Override
    public void setLoggerName(String string) {
        this.loggerName = string;
    }

    @Override
    public String getLoggerName() {
        return this.loggerName;
    }

    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage != null) {
            return this.formattedMessage;
        }
        ResourceBundle resourceBundle = this.resourceBundle;
        if (resourceBundle == null) {
            resourceBundle = this.baseName != null ? this.getResourceBundle(this.baseName, this.locale, true) : this.getResourceBundle(this.loggerName, this.locale, false);
        }
        String string = this.getFormat();
        String string2 = resourceBundle == null || !resourceBundle.containsKey(string) ? string : resourceBundle.getString(string);
        Object[] objectArray = this.argArray == null ? this.stringArgs : this.argArray;
        FormattedMessage formattedMessage = new FormattedMessage(string2, objectArray);
        this.formattedMessage = formattedMessage.getFormattedMessage();
        this.throwable = formattedMessage.getThrowable();
        return this.formattedMessage;
    }

    @Override
    public String getFormat() {
        return this.key;
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
        return this.throwable;
    }

    protected ResourceBundle getResourceBundle(String string, Locale locale, boolean bl) {
        int n;
        ResourceBundle resourceBundle;
        block7: {
            resourceBundle = null;
            if (string == null) {
                return null;
            }
            try {
                resourceBundle = locale != null ? ResourceBundle.getBundle(string, locale) : ResourceBundle.getBundle(string);
            } catch (MissingResourceException missingResourceException) {
                if (bl) break block7;
                this.logger.debug("Unable to locate ResourceBundle " + string);
                return null;
            }
        }
        String string2 = string;
        while (resourceBundle == null && (n = string2.lastIndexOf(46)) > 0) {
            string2 = string2.substring(0, n);
            try {
                if (locale != null) {
                    resourceBundle = ResourceBundle.getBundle(string2, locale);
                    continue;
                }
                resourceBundle = ResourceBundle.getBundle(string2);
            } catch (MissingResourceException missingResourceException) {
                this.logger.debug("Unable to locate ResourceBundle " + string2);
            }
        }
        return resourceBundle;
    }

    public String toString() {
        return this.getFormattedMessage();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        this.getFormattedMessage();
        objectOutputStream.writeUTF(this.formattedMessage);
        objectOutputStream.writeUTF(this.key);
        objectOutputStream.writeUTF(this.baseName);
        objectOutputStream.writeInt(this.argArray.length);
        this.stringArgs = new String[this.argArray.length];
        int n = 0;
        for (Object object : this.argArray) {
            this.stringArgs[n] = object.toString();
            ++n;
        }
        objectOutputStream.writeObject(this.stringArgs);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.formattedMessage = objectInputStream.readUTF();
        this.key = objectInputStream.readUTF();
        this.baseName = objectInputStream.readUTF();
        objectInputStream.readInt();
        this.stringArgs = (String[])objectInputStream.readObject();
        this.logger = StatusLogger.getLogger();
        this.resourceBundle = null;
        this.argArray = null;
    }
}

