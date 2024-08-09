/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

public final class Http2Flags {
    public static final short END_STREAM = 1;
    public static final short END_HEADERS = 4;
    public static final short ACK = 1;
    public static final short PADDED = 8;
    public static final short PRIORITY = 32;
    private short value;

    public Http2Flags() {
    }

    public Http2Flags(short s) {
        this.value = s;
    }

    public short value() {
        return this.value;
    }

    public boolean endOfStream() {
        return this.isFlagSet((short)0);
    }

    public boolean endOfHeaders() {
        return this.isFlagSet((short)1);
    }

    public boolean priorityPresent() {
        return this.isFlagSet((short)1);
    }

    public boolean ack() {
        return this.isFlagSet((short)0);
    }

    public boolean paddingPresent() {
        return this.isFlagSet((short)1);
    }

    public int getNumPriorityBytes() {
        return this.priorityPresent() ? 5 : 0;
    }

    public int getPaddingPresenceFieldLength() {
        return this.paddingPresent() ? 1 : 0;
    }

    public Http2Flags endOfStream(boolean bl) {
        return this.setFlag(bl, (short)0);
    }

    public Http2Flags endOfHeaders(boolean bl) {
        return this.setFlag(bl, (short)1);
    }

    public Http2Flags priorityPresent(boolean bl) {
        return this.setFlag(bl, (short)1);
    }

    public Http2Flags paddingPresent(boolean bl) {
        return this.setFlag(bl, (short)1);
    }

    public Http2Flags ack(boolean bl) {
        return this.setFlag(bl, (short)0);
    }

    public Http2Flags setFlag(boolean bl, short s) {
        this.value = bl ? (short)(this.value | s) : (short)(this.value & ~s);
        return this;
    }

    public boolean isFlagSet(short s) {
        return (this.value & s) != 0;
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + this.value;
        return n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        return this.value == ((Http2Flags)object).value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("value = ").append(this.value).append(" (");
        if (this.ack()) {
            stringBuilder.append("ACK,");
        }
        if (this.endOfHeaders()) {
            stringBuilder.append("END_OF_HEADERS,");
        }
        if (this.endOfStream()) {
            stringBuilder.append("END_OF_STREAM,");
        }
        if (this.priorityPresent()) {
            stringBuilder.append("PRIORITY_PRESENT,");
        }
        if (this.paddingPresent()) {
            stringBuilder.append("PADDING_PRESENT,");
        }
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

