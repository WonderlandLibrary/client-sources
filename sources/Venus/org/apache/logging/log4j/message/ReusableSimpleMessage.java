/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.util.PerformanceSensitive;

@PerformanceSensitive(value={"allocation"})
public class ReusableSimpleMessage
implements ReusableMessage,
CharSequence {
    private static final long serialVersionUID = -9199974506498249809L;
    private static Object[] EMPTY_PARAMS = new Object[0];
    private CharSequence charSequence;

    public void set(String string) {
        this.charSequence = string;
    }

    public void set(CharSequence charSequence) {
        this.charSequence = charSequence;
    }

    @Override
    public String getFormattedMessage() {
        return String.valueOf(this.charSequence);
    }

    @Override
    public String getFormat() {
        return this.getFormattedMessage();
    }

    @Override
    public Object[] getParameters() {
        return EMPTY_PARAMS;
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }

    @Override
    public void formatTo(StringBuilder stringBuilder) {
        stringBuilder.append(this.charSequence);
    }

    @Override
    public Object[] swapParameters(Object[] objectArray) {
        return objectArray;
    }

    @Override
    public short getParameterCount() {
        return 1;
    }

    @Override
    public Message memento() {
        return new SimpleMessage(this.charSequence);
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
}

