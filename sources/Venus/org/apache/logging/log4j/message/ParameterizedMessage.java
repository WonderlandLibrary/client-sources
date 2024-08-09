/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.util.Arrays;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterFormatter;
import org.apache.logging.log4j.util.StringBuilderFormattable;

public class ParameterizedMessage
implements Message,
StringBuilderFormattable {
    private static final int DEFAULT_STRING_BUILDER_SIZE = 255;
    public static final String RECURSION_PREFIX = "[...";
    public static final String RECURSION_SUFFIX = "...]";
    public static final String ERROR_PREFIX = "[!!!";
    public static final String ERROR_SEPARATOR = "=>";
    public static final String ERROR_MSG_SEPARATOR = ":";
    public static final String ERROR_SUFFIX = "!!!]";
    private static final long serialVersionUID = -665975803997290697L;
    private static final int HASHVAL = 31;
    private static ThreadLocal<StringBuilder> threadLocalStringBuilder = new ThreadLocal();
    private String messagePattern;
    private transient Object[] argArray;
    private String formattedMessage;
    private transient Throwable throwable;
    private int[] indices;
    private int usedCount;

    @Deprecated
    public ParameterizedMessage(String string, String[] stringArray, Throwable throwable) {
        this.argArray = stringArray;
        this.throwable = throwable;
        this.init(string);
    }

    public ParameterizedMessage(String string, Object[] objectArray, Throwable throwable) {
        this.argArray = objectArray;
        this.throwable = throwable;
        this.init(string);
    }

    public ParameterizedMessage(String string, Object ... objectArray) {
        this.argArray = objectArray;
        this.init(string);
    }

    public ParameterizedMessage(String string, Object object) {
        this(string, new Object[]{object});
    }

    public ParameterizedMessage(String string, Object object, Object object2) {
        this(string, new Object[]{object, object2});
    }

    private void init(String string) {
        this.messagePattern = string;
        int n = Math.max(1, string == null ? 0 : string.length() >> 1);
        this.indices = new int[n];
        int n2 = ParameterFormatter.countArgumentPlaceholders2(string, this.indices);
        this.initThrowable(this.argArray, n2);
        this.usedCount = Math.min(n2, this.argArray == null ? 0 : this.argArray.length);
    }

    private void initThrowable(Object[] objectArray, int n) {
        int n2;
        if (objectArray != null && n < (n2 = objectArray.length) && this.throwable == null && objectArray[n2 - 1] instanceof Throwable) {
            this.throwable = (Throwable)objectArray[n2 - 1];
        }
    }

    @Override
    public String getFormat() {
        return this.messagePattern;
    }

    @Override
    public Object[] getParameters() {
        return this.argArray;
    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }

    @Override
    public String getFormattedMessage() {
        if (this.formattedMessage == null) {
            StringBuilder stringBuilder = ParameterizedMessage.getThreadLocalStringBuilder();
            this.formatTo(stringBuilder);
            this.formattedMessage = stringBuilder.toString();
        }
        return this.formattedMessage;
    }

    private static StringBuilder getThreadLocalStringBuilder() {
        StringBuilder stringBuilder = threadLocalStringBuilder.get();
        if (stringBuilder == null) {
            stringBuilder = new StringBuilder(255);
            threadLocalStringBuilder.set(stringBuilder);
        }
        stringBuilder.setLength(0);
        return stringBuilder;
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        if (this.formattedMessage != null) {
            stringBuilder.append(this.formattedMessage);
        } else if (this.indices[0] < 0) {
            ParameterFormatter.formatMessage(stringBuilder, this.messagePattern, this.argArray, this.usedCount);
        } else {
            ParameterFormatter.formatMessage2(stringBuilder, this.messagePattern, this.argArray, this.usedCount, this.indices);
        }
    }

    public static String format(String string, Object[] objectArray) {
        return ParameterFormatter.format(string, objectArray);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        ParameterizedMessage parameterizedMessage = (ParameterizedMessage)object;
        if (this.messagePattern != null ? !this.messagePattern.equals(parameterizedMessage.messagePattern) : parameterizedMessage.messagePattern != null) {
            return true;
        }
        return !Arrays.equals(this.argArray, parameterizedMessage.argArray);
    }

    public int hashCode() {
        int n = this.messagePattern != null ? this.messagePattern.hashCode() : 0;
        n = 31 * n + (this.argArray != null ? Arrays.hashCode(this.argArray) : 0);
        return n;
    }

    public static int countArgumentPlaceholders(String string) {
        return ParameterFormatter.countArgumentPlaceholders(string);
    }

    public static String deepToString(Object object) {
        return ParameterFormatter.deepToString(object);
    }

    public static String identityToString(Object object) {
        return ParameterFormatter.identityToString(object);
    }

    public String toString() {
        return "ParameterizedMessage[messagePattern=" + this.messagePattern + ", stringArgs=" + Arrays.toString(this.argArray) + ", throwable=" + this.throwable + ']';
    }
}

