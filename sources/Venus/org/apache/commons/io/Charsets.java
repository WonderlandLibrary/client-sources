/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

public class Charsets {
    @Deprecated
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    @Deprecated
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    @Deprecated
    public static final Charset UTF_16 = Charset.forName("UTF-16");
    @Deprecated
    public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
    @Deprecated
    public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
    @Deprecated
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static SortedMap<String, Charset> requiredCharsets() {
        TreeMap<String, Charset> treeMap = new TreeMap<String, Charset>(String.CASE_INSENSITIVE_ORDER);
        treeMap.put(ISO_8859_1.name(), ISO_8859_1);
        treeMap.put(US_ASCII.name(), US_ASCII);
        treeMap.put(UTF_16.name(), UTF_16);
        treeMap.put(UTF_16BE.name(), UTF_16BE);
        treeMap.put(UTF_16LE.name(), UTF_16LE);
        treeMap.put(UTF_8.name(), UTF_8);
        return Collections.unmodifiableSortedMap(treeMap);
    }

    public static Charset toCharset(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    public static Charset toCharset(String string) {
        return string == null ? Charset.defaultCharset() : Charset.forName(string);
    }
}

