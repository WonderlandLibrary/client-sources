/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.CipherSuiteFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class SupportedCipherSuiteFilter
implements CipherSuiteFilter {
    public static final SupportedCipherSuiteFilter INSTANCE = new SupportedCipherSuiteFilter();

    private SupportedCipherSuiteFilter() {
    }

    @Override
    public String[] filterCipherSuites(Iterable<String> iterable, List<String> list, Set<String> set) {
        ArrayList<String> arrayList;
        if (list == null) {
            throw new NullPointerException("defaultCiphers");
        }
        if (set == null) {
            throw new NullPointerException("supportedCiphers");
        }
        if (iterable == null) {
            arrayList = new ArrayList<String>(list.size());
            iterable = list;
        } else {
            arrayList = new ArrayList(set.size());
        }
        for (String string : iterable) {
            if (string == null) break;
            if (!set.contains(string)) continue;
            arrayList.add(string);
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }
}

