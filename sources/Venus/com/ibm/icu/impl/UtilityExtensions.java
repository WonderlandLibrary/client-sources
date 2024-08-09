/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Utility;
import com.ibm.icu.text.Replaceable;
import com.ibm.icu.text.ReplaceableString;
import com.ibm.icu.text.Transliterator;
import com.ibm.icu.text.UnicodeMatcher;

public class UtilityExtensions {
    public static void appendToRule(StringBuffer stringBuffer, String string, boolean bl, boolean bl2, StringBuffer stringBuffer2) {
        for (int i = 0; i < string.length(); ++i) {
            Utility.appendToRule(stringBuffer, string.charAt(i), bl, bl2, stringBuffer2);
        }
    }

    public static void appendToRule(StringBuffer stringBuffer, UnicodeMatcher unicodeMatcher, boolean bl, StringBuffer stringBuffer2) {
        if (unicodeMatcher != null) {
            UtilityExtensions.appendToRule(stringBuffer, unicodeMatcher.toPattern(bl), true, bl, stringBuffer2);
        }
    }

    public static String formatInput(ReplaceableString replaceableString, Transliterator.Position position) {
        StringBuffer stringBuffer = new StringBuffer();
        UtilityExtensions.formatInput(stringBuffer, replaceableString, position);
        return Utility.escape(stringBuffer.toString());
    }

    public static StringBuffer formatInput(StringBuffer stringBuffer, ReplaceableString replaceableString, Transliterator.Position position) {
        if (0 <= position.contextStart && position.contextStart <= position.start && position.start <= position.limit && position.limit <= position.contextLimit && position.contextLimit <= replaceableString.length()) {
            String string = replaceableString.substring(position.contextStart, position.start);
            String string2 = replaceableString.substring(position.start, position.limit);
            String string3 = replaceableString.substring(position.limit, position.contextLimit);
            stringBuffer.append('{').append(string).append('|').append(string2).append('|').append(string3).append('}');
        } else {
            stringBuffer.append("INVALID Position {cs=" + position.contextStart + ", s=" + position.start + ", l=" + position.limit + ", cl=" + position.contextLimit + "} on " + replaceableString);
        }
        return stringBuffer;
    }

    public static String formatInput(Replaceable replaceable, Transliterator.Position position) {
        return UtilityExtensions.formatInput((ReplaceableString)replaceable, position);
    }

    public static StringBuffer formatInput(StringBuffer stringBuffer, Replaceable replaceable, Transliterator.Position position) {
        return UtilityExtensions.formatInput(stringBuffer, (ReplaceableString)replaceable, position);
    }
}

