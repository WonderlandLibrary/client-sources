/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import java.util.ArrayList;
import java.util.List;

final class ApplicationProtocolUtil {
    private static final int DEFAULT_LIST_SIZE = 2;

    private ApplicationProtocolUtil() {
    }

    static List<String> toList(Iterable<String> iterable) {
        return ApplicationProtocolUtil.toList(2, iterable);
    }

    static List<String> toList(int n, Iterable<String> iterable) {
        if (iterable == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>(n);
        for (String string : iterable) {
            if (string == null || string.isEmpty()) {
                throw new IllegalArgumentException("protocol cannot be null or empty");
            }
            arrayList.add(string);
        }
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("protocols cannot empty");
        }
        return arrayList;
    }

    static List<String> toList(String ... stringArray) {
        return ApplicationProtocolUtil.toList(2, stringArray);
    }

    static List<String> toList(int n, String ... stringArray) {
        if (stringArray == null) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<String>(n);
        for (String string : stringArray) {
            if (string == null || string.isEmpty()) {
                throw new IllegalArgumentException("protocol cannot be null or empty");
            }
            arrayList.add(string);
        }
        if (arrayList.isEmpty()) {
            throw new IllegalArgumentException("protocols cannot empty");
        }
        return arrayList;
    }
}

