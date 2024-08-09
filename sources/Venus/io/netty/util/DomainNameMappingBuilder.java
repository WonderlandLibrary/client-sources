/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.DomainNameMapping;
import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class DomainNameMappingBuilder<V> {
    private final V defaultValue;
    private final Map<String, V> map;

    public DomainNameMappingBuilder(V v) {
        this(4, v);
    }

    public DomainNameMappingBuilder(int n, V v) {
        this.defaultValue = ObjectUtil.checkNotNull(v, "defaultValue");
        this.map = new LinkedHashMap<String, V>(n);
    }

    public DomainNameMappingBuilder<V> add(String string, V v) {
        this.map.put(ObjectUtil.checkNotNull(string, "hostname"), ObjectUtil.checkNotNull(v, "output"));
        return this;
    }

    public DomainNameMapping<V> build() {
        return new ImmutableDomainNameMapping(this.defaultValue, this.map, null);
    }

    private static final class ImmutableDomainNameMapping<V>
    extends DomainNameMapping<V> {
        private static final String REPR_HEADER = "ImmutableDomainNameMapping(default: ";
        private static final String REPR_MAP_OPENING = ", map: {";
        private static final String REPR_MAP_CLOSING = "})";
        private static final int REPR_CONST_PART_LENGTH = 46;
        private final String[] domainNamePatterns;
        private final V[] values;
        private final Map<String, V> map;

        private ImmutableDomainNameMapping(V v, Map<String, V> map) {
            super(null, v);
            Set<Map.Entry<String, V>> set = map.entrySet();
            int n = set.size();
            this.domainNamePatterns = new String[n];
            this.values = new Object[n];
            LinkedHashMap<String, V> linkedHashMap = new LinkedHashMap<String, V>(map.size());
            int n2 = 0;
            for (Map.Entry<String, V> entry : set) {
                String string = ImmutableDomainNameMapping.normalizeHostname(entry.getKey());
                V v2 = entry.getValue();
                this.domainNamePatterns[n2] = string;
                this.values[n2] = v2;
                linkedHashMap.put(string, v2);
                ++n2;
            }
            this.map = Collections.unmodifiableMap(linkedHashMap);
        }

        @Override
        @Deprecated
        public DomainNameMapping<V> add(String string, V v) {
            throw new UnsupportedOperationException("Immutable DomainNameMapping does not support modification after initial creation");
        }

        @Override
        public V map(String string) {
            if (string != null) {
                string = ImmutableDomainNameMapping.normalizeHostname(string);
                int n = this.domainNamePatterns.length;
                for (int i = 0; i < n; ++i) {
                    if (!ImmutableDomainNameMapping.matches(this.domainNamePatterns[i], string)) continue;
                    return this.values[i];
                }
            }
            return (V)this.defaultValue;
        }

        @Override
        public Map<String, V> asMap() {
            return this.map;
        }

        @Override
        public String toString() {
            String string = this.defaultValue.toString();
            int n = this.domainNamePatterns.length;
            if (n == 0) {
                return REPR_HEADER + string + REPR_MAP_OPENING + REPR_MAP_CLOSING;
            }
            String string2 = this.domainNamePatterns[0];
            String string3 = this.values[0].toString();
            int n2 = string2.length() + string3.length() + 3;
            int n3 = ImmutableDomainNameMapping.estimateBufferSize(string.length(), n, n2);
            StringBuilder stringBuilder = new StringBuilder(n3).append(REPR_HEADER).append(string).append(REPR_MAP_OPENING);
            ImmutableDomainNameMapping.appendMapping(stringBuilder, string2, string3);
            for (int i = 1; i < n; ++i) {
                stringBuilder.append(", ");
                this.appendMapping(stringBuilder, i);
            }
            return stringBuilder.append(REPR_MAP_CLOSING).toString();
        }

        private static int estimateBufferSize(int n, int n2, int n3) {
            return REPR_CONST_PART_LENGTH + n + (int)((double)(n3 * n2) * 1.1);
        }

        private StringBuilder appendMapping(StringBuilder stringBuilder, int n) {
            return ImmutableDomainNameMapping.appendMapping(stringBuilder, this.domainNamePatterns[n], this.values[n].toString());
        }

        private static StringBuilder appendMapping(StringBuilder stringBuilder, String string, String string2) {
            return stringBuilder.append(string).append('=').append(string2);
        }

        @Override
        public Object map(Object object) {
            return this.map((String)object);
        }

        ImmutableDomainNameMapping(Object object, Map map, 1 var3_3) {
            this(object, map);
        }
    }
}

