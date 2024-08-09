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

    public FormattingInfo(boolean bl, int n, int n2, boolean bl2) {
        this.leftAlign = bl;
        this.minLength = n;
        this.maxLength = n2;
        this.leftTruncate = bl2;
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

    public void format(int n, StringBuilder stringBuilder) {
        int n2 = stringBuilder.length() - n;
        if (n2 > this.maxLength) {
            if (this.leftTruncate) {
                stringBuilder.delete(n, stringBuilder.length() - this.maxLength);
            } else {
                stringBuilder.delete(n + this.maxLength, n + stringBuilder.length());
            }
        } else if (n2 < this.minLength) {
            if (this.leftAlign) {
                int n3 = stringBuilder.length();
                stringBuilder.setLength(n + this.minLength);
                for (int i = n3; i < stringBuilder.length(); ++i) {
                    stringBuilder.setCharAt(i, ' ');
                }
            } else {
                int n4;
                for (n4 = this.minLength - n2; n4 > SPACES.length; n4 -= SPACES.length) {
                    stringBuilder.insert(n, SPACES);
                }
                stringBuilder.insert(n, SPACES, 0, n4);
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("[leftAlign=");
        stringBuilder.append(this.leftAlign);
        stringBuilder.append(", maxLength=");
        stringBuilder.append(this.maxLength);
        stringBuilder.append(", minLength=");
        stringBuilder.append(this.minLength);
        stringBuilder.append(", leftTruncate=");
        stringBuilder.append(this.leftTruncate);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

