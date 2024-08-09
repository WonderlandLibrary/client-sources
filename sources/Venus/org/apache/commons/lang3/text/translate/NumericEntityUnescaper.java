/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.EnumSet;
import org.apache.commons.lang3.text.translate.CharSequenceTranslator;

public class NumericEntityUnescaper
extends CharSequenceTranslator {
    private final EnumSet<OPTION> options;

    public NumericEntityUnescaper(OPTION ... oPTIONArray) {
        this.options = oPTIONArray.length > 0 ? EnumSet.copyOf(Arrays.asList(oPTIONArray)) : EnumSet.copyOf(Arrays.asList(OPTION.semiColonRequired));
    }

    public boolean isSet(OPTION oPTION) {
        return this.options == null ? false : this.options.contains((Object)oPTION);
    }

    @Override
    public int translate(CharSequence charSequence, int n, Writer writer) throws IOException {
        int n2 = charSequence.length();
        if (charSequence.charAt(n) == '&' && n < n2 - 2 && charSequence.charAt(n + 1) == '#') {
            int n3;
            boolean bl;
            int n4;
            int n5 = n + 2;
            boolean bl2 = false;
            char c = charSequence.charAt(n5);
            if (c == 'x' || c == 'X') {
                bl2 = true;
                if (++n5 == n2) {
                    return 1;
                }
            }
            for (n4 = n5; n4 < n2 && (charSequence.charAt(n4) >= '0' && charSequence.charAt(n4) <= '9' || charSequence.charAt(n4) >= 'a' && charSequence.charAt(n4) <= 'f' || charSequence.charAt(n4) >= 'A' && charSequence.charAt(n4) <= 'F'); ++n4) {
            }
            boolean bl3 = bl = n4 != n2 && charSequence.charAt(n4) == ';';
            if (!bl) {
                if (this.isSet(OPTION.semiColonRequired)) {
                    return 1;
                }
                if (this.isSet(OPTION.errorIfNoSemiColon)) {
                    throw new IllegalArgumentException("Semi-colon required at end of numeric entity");
                }
            }
            try {
                n3 = bl2 ? Integer.parseInt(charSequence.subSequence(n5, n4).toString(), 16) : Integer.parseInt(charSequence.subSequence(n5, n4).toString(), 10);
            } catch (NumberFormatException numberFormatException) {
                return 1;
            }
            if (n3 > 65535) {
                char[] cArray = Character.toChars(n3);
                writer.write(cArray[0]);
                writer.write(cArray[1]);
            } else {
                writer.write(n3);
            }
            return 2 + n4 - n5 + (bl2 ? 1 : 0) + (bl ? 1 : 0);
        }
        return 1;
    }

    public static enum OPTION {
        semiColonRequired,
        semiColonOptional,
        errorIfNoSemiColon;

    }
}

