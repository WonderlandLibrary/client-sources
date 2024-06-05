package com.ibm.icu.impl;

import com.ibm.icu.text.*;

public class UtilityExtensions
{
    public static void appendToRule(final StringBuffer rule, final String text, final boolean isLiteral, final boolean escapeUnprintable, final StringBuffer quoteBuf) {
        for (int i = 0; i < text.length(); ++i) {
            Utility.appendToRule(rule, text.charAt(i), isLiteral, escapeUnprintable, quoteBuf);
        }
    }
    
    public static void appendToRule(final StringBuffer rule, final UnicodeMatcher matcher, final boolean escapeUnprintable, final StringBuffer quoteBuf) {
        if (matcher != null) {
            appendToRule(rule, matcher.toPattern(escapeUnprintable), true, escapeUnprintable, quoteBuf);
        }
    }
    
    public static String formatInput(final ReplaceableString input, final Transliterator.Position pos) {
        final StringBuffer appendTo = new StringBuffer();
        formatInput(appendTo, input, pos);
        return Utility.escape(appendTo.toString());
    }
    
    public static StringBuffer formatInput(final StringBuffer appendTo, final ReplaceableString input, final Transliterator.Position pos) {
        if (0 <= pos.contextStart && pos.contextStart <= pos.start && pos.start <= pos.limit && pos.limit <= pos.contextLimit && pos.contextLimit <= input.length()) {
            final String b = input.substring(pos.contextStart, pos.start);
            final String c = input.substring(pos.start, pos.limit);
            final String d = input.substring(pos.limit, pos.contextLimit);
            appendTo.append('{').append(b).append('|').append(c).append('|').append(d).append('}');
        }
        else {
            appendTo.append("INVALID Position {cs=" + pos.contextStart + ", s=" + pos.start + ", l=" + pos.limit + ", cl=" + pos.contextLimit + "} on " + input);
        }
        return appendTo;
    }
    
    public static String formatInput(final Replaceable input, final Transliterator.Position pos) {
        return formatInput((ReplaceableString)input, pos);
    }
    
    public static StringBuffer formatInput(final StringBuffer appendTo, final Replaceable input, final Transliterator.Position pos) {
        return formatInput(appendTo, (ReplaceableString)input, pos);
    }
}
