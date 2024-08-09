/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.config;

import org.apache.http.util.Args;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class MessageConstraints
implements Cloneable {
    public static final MessageConstraints DEFAULT = new Builder().build();
    private final int maxLineLength;
    private final int maxHeaderCount;

    MessageConstraints(int n, int n2) {
        this.maxLineLength = n;
        this.maxHeaderCount = n2;
    }

    public int getMaxLineLength() {
        return this.maxLineLength;
    }

    public int getMaxHeaderCount() {
        return this.maxHeaderCount;
    }

    protected MessageConstraints clone() throws CloneNotSupportedException {
        return (MessageConstraints)super.clone();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[maxLineLength=").append(this.maxLineLength).append(", maxHeaderCount=").append(this.maxHeaderCount).append("]");
        return stringBuilder.toString();
    }

    public static MessageConstraints lineLen(int n) {
        return new MessageConstraints(Args.notNegative(n, "Max line length"), -1);
    }

    public static Builder custom() {
        return new Builder();
    }

    public static Builder copy(MessageConstraints messageConstraints) {
        Args.notNull(messageConstraints, "Message constraints");
        return new Builder().setMaxHeaderCount(messageConstraints.getMaxHeaderCount()).setMaxLineLength(messageConstraints.getMaxLineLength());
    }

    protected Object clone() throws CloneNotSupportedException {
        return this.clone();
    }

    public static class Builder {
        private int maxLineLength = -1;
        private int maxHeaderCount = -1;

        Builder() {
        }

        public Builder setMaxLineLength(int n) {
            this.maxLineLength = n;
            return this;
        }

        public Builder setMaxHeaderCount(int n) {
            this.maxHeaderCount = n;
            return this;
        }

        public MessageConstraints build() {
            return new MessageConstraints(this.maxLineLength, this.maxHeaderCount);
        }
    }
}

