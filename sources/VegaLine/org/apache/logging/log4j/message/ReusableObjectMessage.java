/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ObjectMessage;
import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.StringBuilderFormattable;

@PerformanceSensitive(value={"allocation"})
public class ReusableObjectMessage
implements ReusableMessage {
    private static final long serialVersionUID = 6922476812535519960L;
    private transient Object obj;
    private transient String objectString;

    public void set(Object object) {
        this.obj = object;
    }

    @Override
    public String getFormattedMessage() {
        return String.valueOf(this.obj);
    }

    @Override
    public void formatTo(StringBuilder buffer) {
        if (this.obj == null || this.obj instanceof String) {
            buffer.append((String)this.obj);
        } else if (this.obj instanceof StringBuilderFormattable) {
            ((StringBuilderFormattable)this.obj).formatTo(buffer);
        } else if (this.obj instanceof CharSequence) {
            buffer.append((CharSequence)this.obj);
        } else if (this.obj instanceof Integer) {
            buffer.append((Integer)this.obj);
        } else if (this.obj instanceof Long) {
            buffer.append((Long)this.obj);
        } else if (this.obj instanceof Double) {
            buffer.append((Double)this.obj);
        } else if (this.obj instanceof Boolean) {
            buffer.append((Boolean)this.obj);
        } else if (this.obj instanceof Character) {
            buffer.append(((Character)this.obj).charValue());
        } else if (this.obj instanceof Short) {
            buffer.append(((Short)this.obj).shortValue());
        } else if (this.obj instanceof Float) {
            buffer.append(((Float)this.obj).floatValue());
        } else {
            buffer.append(this.obj);
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

    public String toString() {
        return this.getFormattedMessage();
    }

    @Override
    public Throwable getThrowable() {
        return this.obj instanceof Throwable ? (Throwable)this.obj : null;
    }

    @Override
    public Object[] swapParameters(Object[] emptyReplacement) {
        return emptyReplacement;
    }

    @Override
    public short getParameterCount() {
        return 0;
    }

    @Override
    public Message memento() {
        return new ObjectMessage(this.obj);
    }
}

