/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;

public class AggregateTranslator
extends CharSequenceTranslator {
    private final CharSequenceTranslator[] translators;

    public AggregateTranslator(CharSequenceTranslator ... charSequenceTranslatorArray) {
        this.translators = ArrayUtils.clone(charSequenceTranslatorArray);
    }

    @Override
    public int translate(CharSequence charSequence, int n, Writer writer) throws IOException {
        for (CharSequenceTranslator charSequenceTranslator : this.translators) {
            int n2 = charSequenceTranslator.translate(charSequence, n, writer);
            if (n2 == 0) continue;
            return n2;
        }
        return 1;
    }
}

