/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.text.translate.CodePointTranslator;

public class NumericEntityEscaper
extends CodePointTranslator {
    private final int below;
    private final int above;
    private final boolean between;

    private NumericEntityEscaper(int n, int n2, boolean bl) {
        this.below = n;
        this.above = n2;
        this.between = bl;
    }

    public NumericEntityEscaper() {
        this(0, Integer.MAX_VALUE, true);
    }

    public static NumericEntityEscaper below(int n) {
        return NumericEntityEscaper.outsideOf(n, Integer.MAX_VALUE);
    }

    public static NumericEntityEscaper above(int n) {
        return NumericEntityEscaper.outsideOf(0, n);
    }

    public static NumericEntityEscaper between(int n, int n2) {
        return new NumericEntityEscaper(n, n2, true);
    }

    public static NumericEntityEscaper outsideOf(int n, int n2) {
        return new NumericEntityEscaper(n, n2, false);
    }

    @Override
    public boolean translate(int n, Writer writer) throws IOException {
        if (this.between ? n < this.below || n > this.above : n >= this.below && n <= this.above) {
            return true;
        }
        writer.write("&#");
        writer.write(Integer.toString(n, 10));
        writer.write(59);
        return false;
    }
}

