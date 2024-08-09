/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.handler.ssl.CipherSuiteFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class IdentityCipherSuiteFilter
implements CipherSuiteFilter {
    public static final IdentityCipherSuiteFilter INSTANCE = new IdentityCipherSuiteFilter(true);
    public static final IdentityCipherSuiteFilter INSTANCE_DEFAULTING_TO_SUPPORTED_CIPHERS = new IdentityCipherSuiteFilter(false);
    private final boolean defaultToDefaultCiphers;

    private IdentityCipherSuiteFilter(boolean bl) {
        this.defaultToDefaultCiphers = bl;
    }

    @Override
    public String[] filterCipherSuites(Iterable<String> iterable, List<String> list, Set<String> set) {
        if (iterable == null) {
            return this.defaultToDefaultCiphers ? list.toArray(new String[list.size()]) : set.toArray(new String[set.size()]);
        }
        ArrayList<String> arrayList = new ArrayList<String>(set.size());
        for (String string : iterable) {
            if (string == null) break;
            arrayList.add(string);
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }
}

