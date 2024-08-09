/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.Mapping;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.StringUtil;
import java.net.IDN;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class DomainNameMapping<V>
implements Mapping<String, V> {
    final V defaultValue;
    private final Map<String, V> map;
    private final Map<String, V> unmodifiableMap;

    @Deprecated
    public DomainNameMapping(V v) {
        this(4, v);
    }

    @Deprecated
    public DomainNameMapping(int n, V v) {
        this(new LinkedHashMap(n), v);
    }

    DomainNameMapping(Map<String, V> map, V v) {
        this.defaultValue = ObjectUtil.checkNotNull(v, "defaultValue");
        this.map = map;
        this.unmodifiableMap = map != null ? Collections.unmodifiableMap(map) : null;
    }

    @Deprecated
    public DomainNameMapping<V> add(String string, V v) {
        this.map.put(DomainNameMapping.normalizeHostname(ObjectUtil.checkNotNull(string, "hostname")), ObjectUtil.checkNotNull(v, "output"));
        return this;
    }

    static boolean matches(String string, String string2) {
        if (string.startsWith("*.")) {
            return string.regionMatches(2, string2, 0, string2.length()) || StringUtil.commonSuffixOfLength(string2, string, string.length() - 1);
        }
        return string.equals(string2);
    }

    static String normalizeHostname(String string) {
        if (DomainNameMapping.needsNormalization(string)) {
            string = IDN.toASCII(string, 1);
        }
        return string.toLowerCase(Locale.US);
    }

    private static boolean needsNormalization(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c <= '\u007f') continue;
            return false;
        }
        return true;
    }

    @Override
    public V map(String string) {
        if (string != null) {
            string = DomainNameMapping.normalizeHostname(string);
            for (Map.Entry<String, V> entry : this.map.entrySet()) {
                if (!DomainNameMapping.matches(entry.getKey(), string)) continue;
                return entry.getValue();
            }
        }
        return this.defaultValue;
    }

    public Map<String, V> asMap() {
        return this.unmodifiableMap;
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + "(default: " + this.defaultValue + ", map: " + this.map + ')';
    }

    @Override
    public Object map(Object object) {
        return this.map((String)object);
    }
}

