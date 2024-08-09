/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.text.translate.CodePointTranslator;

public class UnicodeEscaper
extends CodePointTranslator {
    private final int below;
    private final int above;
    private final boolean between;

    public UnicodeEscaper() {
        this(0, Integer.MAX_VALUE, true);
    }

    protected UnicodeEscaper(int n, int n2, boolean bl) {
        this.below = n;
        this.above = n2;
        this.between = bl;
    }

    public static UnicodeEscaper below(int n) {
        return UnicodeEscaper.outsideOf(n, Integer.MAX_VALUE);
    }

    public static UnicodeEscaper above(int n) {
        return UnicodeEscaper.outsideOf(0, n);
    }

    public static UnicodeEscaper outsideOf(int n, int n2) {
        return new UnicodeEscaper(n, n2, false);
    }

    public static UnicodeEscaper between(int n, int n2) {
        return new UnicodeEscaper(n, n2, true);
    }

    @Override
    public boolean translate(int n, Writer writer) throws IOException {
        if (this.between ? n < this.below || n > this.above : n >= this.below && n <= this.above) {
            return true;
        }
        if (n > 65535) {
            writer.write(this.toUtf16Escape(n));
        } else {
            writer.write("\\u");
            writer.write(HEX_DIGITS[n >> 12 & 0xF]);
            writer.write(HEX_DIGITS[n >> 8 & 0xF]);
            writer.write(HEX_DIGITS[n >> 4 & 0xF]);
            writer.write(HEX_DIGITS[n & 0xF]);
        }
        return false;
    }

    protected String toUtf16Escape(int n) {
        return "\\u" + UnicodeEscaper.hex(n);
    }
}

