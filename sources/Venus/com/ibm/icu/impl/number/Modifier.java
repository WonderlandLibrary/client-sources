/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.number;

import com.ibm.icu.impl.FormattedStringBuilder;
import com.ibm.icu.impl.StandardPlural;
import com.ibm.icu.impl.number.ModifierStore;
import java.text.Format;

public interface Modifier {
    public int apply(FormattedStringBuilder var1, int var2, int var3);

    public int getPrefixLength();

    public int getCodePointCount();

    public boolean isStrong();

    public boolean containsField(Format.Field var1);

    public Parameters getParameters();

    public boolean semanticallyEquivalent(Modifier var1);

    public static class Parameters {
        public ModifierStore obj;
        public int signum;
        public StandardPlural plural;
    }
}

