/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.PatternProps;
import com.ibm.icu.text.MessagePattern;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class SelectFormat
extends Format {
    private static final long serialVersionUID = 2993154333257524984L;
    private String pattern = null;
    private transient MessagePattern msgPattern;
    static final boolean $assertionsDisabled = !SelectFormat.class.desiredAssertionStatus();

    public SelectFormat(String string) {
        this.applyPattern(string);
    }

    private void reset() {
        this.pattern = null;
        if (this.msgPattern != null) {
            this.msgPattern.clear();
        }
    }

    public void applyPattern(String string) {
        this.pattern = string;
        if (this.msgPattern == null) {
            this.msgPattern = new MessagePattern();
        }
        try {
            this.msgPattern.parseSelectStyle(string);
        } catch (RuntimeException runtimeException) {
            this.reset();
            throw runtimeException;
        }
    }

    public String toPattern() {
        return this.pattern;
    }

    static int findSubMessage(MessagePattern messagePattern, int n, String string) {
        MessagePattern.Part part;
        MessagePattern.Part.Type type;
        int n2 = messagePattern.countParts();
        int n3 = 0;
        while ((type = (part = messagePattern.getPart(n++)).getType()) != MessagePattern.Part.Type.ARG_LIMIT) {
            if (!$assertionsDisabled && type != MessagePattern.Part.Type.ARG_SELECTOR) {
                throw new AssertionError();
            }
            if (messagePattern.partSubstringMatches(part, string)) {
                return n;
            }
            if (n3 == 0 && messagePattern.partSubstringMatches(part, "other")) {
                n3 = n;
            }
            n = messagePattern.getLimitPartIndex(n);
            if (++n < n2) continue;
        }
        return n3;
    }

    public final String format(String string) {
        if (!PatternProps.isIdentifier(string)) {
            throw new IllegalArgumentException("Invalid formatting argument.");
        }
        if (this.msgPattern == null || this.msgPattern.countParts() == 0) {
            throw new IllegalStateException("Invalid format error.");
        }
        int n = SelectFormat.findSubMessage(this.msgPattern, 0, string);
        if (!this.msgPattern.jdkAposMode()) {
            int n2 = this.msgPattern.getLimitPartIndex(n);
            return this.msgPattern.getPatternString().substring(this.msgPattern.getPart(n).getLimit(), this.msgPattern.getPatternIndex(n2));
        }
        StringBuilder stringBuilder = null;
        int n3 = this.msgPattern.getPart(n).getLimit();
        int n4 = n;
        while (true) {
            MessagePattern.Part part = this.msgPattern.getPart(++n4);
            MessagePattern.Part.Type type = part.getType();
            int n5 = part.getIndex();
            if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                if (stringBuilder == null) {
                    return this.pattern.substring(n3, n5);
                }
                return stringBuilder.append(this.pattern, n3, n5).toString();
            }
            if (type == MessagePattern.Part.Type.SKIP_SYNTAX) {
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                }
                stringBuilder.append(this.pattern, n3, n5);
                n3 = part.getLimit();
                continue;
            }
            if (type != MessagePattern.Part.Type.ARG_START) continue;
            if (stringBuilder == null) {
                stringBuilder = new StringBuilder();
            }
            stringBuilder.append(this.pattern, n3, n5);
            n3 = n5;
            n4 = this.msgPattern.getLimitPartIndex(n4);
            n5 = this.msgPattern.getPart(n4).getLimit();
            MessagePattern.appendReducedApostrophes(this.pattern, n3, n5, stringBuilder);
            n3 = n5;
        }
    }

    @Override
    public StringBuffer format(Object object, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        if (!(object instanceof String)) {
            throw new IllegalArgumentException("'" + object + "' is not a String");
        }
        stringBuffer.append(this.format((String)object));
        return stringBuffer;
    }

    @Override
    public Object parseObject(String string, ParsePosition parsePosition) {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        SelectFormat selectFormat = (SelectFormat)object;
        return this.msgPattern == null ? selectFormat.msgPattern == null : this.msgPattern.equals(selectFormat.msgPattern);
    }

    public int hashCode() {
        if (this.pattern != null) {
            return this.pattern.hashCode();
        }
        return 1;
    }

    public String toString() {
        return "pattern='" + this.pattern + "'";
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.pattern != null) {
            this.applyPattern(this.pattern);
        }
    }
}

