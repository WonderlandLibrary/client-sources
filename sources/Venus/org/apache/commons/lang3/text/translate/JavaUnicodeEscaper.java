/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text.translate;

import org.apache.commons.lang3.text.translate.UnicodeEscaper;

public class JavaUnicodeEscaper
extends UnicodeEscaper {
    public static JavaUnicodeEscaper above(int n) {
        return JavaUnicodeEscaper.outsideOf(0, n);
    }

    public static JavaUnicodeEscaper below(int n) {
        return JavaUnicodeEscaper.outsideOf(n, Integer.MAX_VALUE);
    }

    public static JavaUnicodeEscaper between(int n, int n2) {
        return new JavaUnicodeEscaper(n, n2, true);
    }

    public static JavaUnicodeEscaper outsideOf(int n, int n2) {
        return new JavaUnicodeEscaper(n, n2, false);
    }

    public JavaUnicodeEscaper(int n, int n2, boolean bl) {
        super(n, n2, bl);
    }

    @Override
    protected String toUtf16Escape(int n) {
        char[] cArray = Character.toChars(n);
        return "\\u" + JavaUnicodeEscaper.hex(cArray[0]) + "\\u" + JavaUnicodeEscaper.hex(cArray[1]);
    }
}

