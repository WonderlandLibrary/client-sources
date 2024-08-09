/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.RuleBasedTransliterator;
import com.ibm.icu.text.UTF16;
import com.ibm.icu.text.UnicodeReplacer;
import com.ibm.icu.text.UnicodeSet;

class StringReplacer
implements UnicodeReplacer {
    private String output;
    private int cursorPos;
    private boolean hasCursor;
    private boolean isComplex;
    private final RuleBasedTransliterator.Data data;

    public StringReplacer(String string, int n, RuleBasedTransliterator.Data data) {
        this.output = string;
        this.cursorPos = n;
        this.hasCursor = true;
        this.data = data;
        this.isComplex = true;
    }

    public StringReplacer(String string, RuleBasedTransliterator.Data data) {
        this.output = string;
        this.cursorPos = 0;
        this.hasCursor = false;
        this.data = data;
        this.isComplex = true;
    }

    @Override
    public int replace(Replaceable replaceable, int n, int n2, int[] nArray) {
        int n3;
        int n4 = 0;
        if (!this.isComplex) {
            replaceable.replace(n, n2, this.output);
            n3 = this.output.length();
            n4 = this.cursorPos;
        } else {
            int n5;
            int n6;
            StringBuffer stringBuffer = new StringBuffer();
            this.isComplex = false;
            int n7 = n6 = replaceable.length();
            if (n > 0) {
                n5 = UTF16.getCharCount(replaceable.char32At(n - 1));
                replaceable.copy(n - n5, n, n6);
                n7 += n5;
            } else {
                replaceable.replace(n6, n6, "\uffff");
                ++n7;
            }
            n5 = n7;
            int n8 = 0;
            int n9 = 0;
            while (n9 < this.output.length()) {
                UnicodeReplacer unicodeReplacer;
                int n10;
                int n11;
                if (n9 == this.cursorPos) {
                    n4 = stringBuffer.length() + n5 - n7;
                }
                if ((n11 = n9 + UTF16.getCharCount(n10 = UTF16.charAt(this.output, n9))) == this.output.length()) {
                    n8 = UTF16.getCharCount(replaceable.char32At(n2));
                    replaceable.copy(n2, n2 + n8, n5);
                }
                if ((unicodeReplacer = this.data.lookupReplacer(n10)) == null) {
                    UTF16.append(stringBuffer, n10);
                } else {
                    this.isComplex = true;
                    if (stringBuffer.length() > 0) {
                        replaceable.replace(n5, n5, stringBuffer.toString());
                        n5 += stringBuffer.length();
                        stringBuffer.setLength(0);
                    }
                    int n12 = unicodeReplacer.replace(replaceable, n5, n5, nArray);
                    n5 += n12;
                }
                n9 = n11;
            }
            if (stringBuffer.length() > 0) {
                replaceable.replace(n5, n5, stringBuffer.toString());
                n5 += stringBuffer.length();
            }
            if (n9 == this.cursorPos) {
                n4 = n5 - n7;
            }
            n3 = n5 - n7;
            replaceable.copy(n7, n5, n);
            replaceable.replace(n6 + n3, n5 + n8 + n3, "");
            replaceable.replace(n + n3, n2 + n3, "");
        }
        if (this.hasCursor) {
            if (this.cursorPos < 0) {
                int n13;
                n4 = n;
                for (n13 = this.cursorPos; n13 < 0 && n4 > 0; n4 -= UTF16.getCharCount(replaceable.char32At(n4 - 1)), ++n13) {
                }
                n4 += n13;
            } else if (this.cursorPos > this.output.length()) {
                int n14;
                n4 = n + n3;
                for (n14 = this.cursorPos - this.output.length(); n14 > 0 && n4 < replaceable.length(); n4 += UTF16.getCharCount(replaceable.char32At(n4)), --n14) {
                }
                n4 += n14;
            } else {
                n4 += n;
            }
            nArray[0] = n4;
        }
        return n3;
    }

    @Override
    public String toReplacerPattern(boolean bl) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int n = this.cursorPos;
        if (this.hasCursor && n < 0) {
            while (n++ < 0) {
                Utility.appendToRule(stringBuffer, 64, true, bl, stringBuffer2);
            }
        }
        for (int i = 0; i < this.output.length(); ++i) {
            char c;
            UnicodeReplacer unicodeReplacer;
            if (this.hasCursor && i == n) {
                Utility.appendToRule(stringBuffer, 124, true, bl, stringBuffer2);
            }
            if ((unicodeReplacer = this.data.lookupReplacer(c = this.output.charAt(i))) == null) {
                Utility.appendToRule(stringBuffer, c, false, bl, stringBuffer2);
                continue;
            }
            StringBuffer stringBuffer3 = new StringBuffer(" ");
            stringBuffer3.append(unicodeReplacer.toReplacerPattern(bl));
            stringBuffer3.append(' ');
            Utility.appendToRule(stringBuffer, stringBuffer3.toString(), true, bl, stringBuffer2);
        }
        if (this.hasCursor && n > this.output.length()) {
            n -= this.output.length();
            while (n-- > 0) {
                Utility.appendToRule(stringBuffer, 64, true, bl, stringBuffer2);
            }
            Utility.appendToRule(stringBuffer, 124, true, bl, stringBuffer2);
        }
        Utility.appendToRule(stringBuffer, -1, true, bl, stringBuffer2);
        return stringBuffer.toString();
    }

    @Override
    public void addReplacementSetTo(UnicodeSet unicodeSet) {
        int n;
        for (int i = 0; i < this.output.length(); i += UTF16.getCharCount(n)) {
            n = UTF16.charAt(this.output, i);
            UnicodeReplacer unicodeReplacer = this.data.lookupReplacer(n);
            if (unicodeReplacer == null) {
                unicodeSet.add(n);
                continue;
            }
            unicodeReplacer.addReplacementSetTo(unicodeSet);
        }
    }
}

