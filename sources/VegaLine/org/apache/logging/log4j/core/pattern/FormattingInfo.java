/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.util.PerformanceSensitive;

@PerformanceSensitive(value={"allocation"})
public final class FormattingInfo {
    private static final char[] SPACES = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    private static final FormattingInfo DEFAULT = new FormattingInfo(false, 0, Integer.MAX_VALUE, true);
    private final int minLength;
    private final int maxLength;
    private final boolean leftAlign;
    private final boolean leftTruncate;

    public FormattingInfo(boolean leftAlign, int minLength, int maxLength, boolean leftTruncate) {
        this.leftAlign = leftAlign;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.leftTruncate = leftTruncate;
    }

    public static FormattingInfo getDefault() {
        return DEFAULT;
    }

    public boolean isLeftAligned() {
        return this.leftAlign;
    }

    public boolean isLeftTruncate() {
        return this.leftTruncate;
    }

    public int getMinLength() {
        return this.minLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void format(int fieldStart, StringBuilder buffer) {
        int rawLength = buffer.length() - fieldStart;
        if (rawLength > this.maxLength) {
            if (this.leftTruncate) {
                buffer.delete(fieldStart, buffer.length() - this.maxLength);
            } else {
                buffer.delete(fieldStart + this.maxLength, fieldStart + buffer.length());
            }
        } else if (rawLength < this.minLength) {
            if (this.leftAlign) {
                int fieldEnd = buffer.length();
                buffer.setLength(fieldStart + this.minLength);
                for (int i = fieldEnd; i < buffer.length(); ++i) {
                    buffer.setCharAt(i, ' ');
                }
            } else {
                int padLength;
                for (padLength = this.minLength - rawLength; padLength > SPACES.length; padLength -= SPACES.length) {
                    buffer.insert(fieldStart, SPACES);
                }
                buffer.insert(fieldStart, SPACES, 0, padLength);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[leftAlign=");
        sb.append(this.leftAlign);
        sb.append(", maxLength=");
        sb.append(this.maxLength);
        sb.append(", minLength=");
        sb.append(this.minLength);
        sb.append(", leftTruncate=");
        sb.append(this.leftTruncate);
        sb.append(']');
        return sb.toString();
    }
}

