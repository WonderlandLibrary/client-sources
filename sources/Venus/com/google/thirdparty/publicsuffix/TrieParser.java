/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.thirdparty.publicsuffix.PublicSuffixType;
import java.util.List;

@GwtCompatible
final class TrieParser {
    private static final Joiner PREFIX_JOINER = Joiner.on("");

    TrieParser() {
    }

    static ImmutableMap<String, PublicSuffixType> parseTrie(CharSequence charSequence) {
        ImmutableMap.Builder<String, PublicSuffixType> builder = ImmutableMap.builder();
        int n = charSequence.length();
        for (int i = 0; i < n; i += TrieParser.doParseTrieToBuilder(Lists.newLinkedList(), charSequence.subSequence(i, n), builder)) {
        }
        return builder.build();
    }

    private static int doParseTrieToBuilder(List<CharSequence> list, CharSequence charSequence, ImmutableMap.Builder<String, PublicSuffixType> builder) {
        String string;
        int n;
        int n2 = charSequence.length();
        char c = '\u0000';
        for (n = 0; n < n2 && (c = charSequence.charAt(n)) != '&' && c != '?' && c != '!' && c != ':' && c != ','; ++n) {
        }
        list.add(0, TrieParser.reverse(charSequence.subSequence(0, n)));
        if ((c == '!' || c == '?' || c == ':' || c == ',') && (string = PREFIX_JOINER.join(list)).length() > 0) {
            builder.put(string, PublicSuffixType.fromCode(c));
        }
        ++n;
        if (c != '?' && c != ',') {
            while (n < n2) {
                if (charSequence.charAt(n += TrieParser.doParseTrieToBuilder(list, charSequence.subSequence(n, n2), builder)) != '?' && charSequence.charAt(n) != ',') continue;
                ++n;
                break;
            }
        }
        list.remove(0);
        return n;
    }

    private static CharSequence reverse(CharSequence charSequence) {
        return new StringBuilder(charSequence).reverse();
    }
}

