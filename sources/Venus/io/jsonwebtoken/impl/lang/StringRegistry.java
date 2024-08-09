/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.DefaultRegistry;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.impl.lang.Functions;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Strings;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class StringRegistry<V>
extends DefaultRegistry<String, V> {
    private final Function<String, String> CASE_FN;
    private final Map<String, V> CI_VALUES;

    public StringRegistry(String string, String string2, Collection<V> collection, Function<V, String> function, boolean bl) {
        this(string, string2, collection, function, bl ? Functions.identity() : CaseInsensitiveFunction.access$000());
    }

    public StringRegistry(String string, String string2, Collection<V> collection, Function<V, String> function, Function<String, String> function2) {
        super(string, string2, collection, function);
        this.CASE_FN = Assert.notNull(function2, "Case function cannot be null.");
        LinkedHashMap<String, V> linkedHashMap = new LinkedHashMap<String, V>(this.values().size());
        for (V v : collection) {
            String string3 = function.apply(v);
            string3 = this.CASE_FN.apply(string3);
            linkedHashMap.put(string3, v);
        }
        this.CI_VALUES = Collections.immutable(linkedHashMap);
    }

    @Override
    public V get(Object object) {
        String string = (String)object;
        Assert.hasText(string, "id argument cannot be null or empty.");
        Object v = super.get(string);
        if (v == null) {
            string = this.CASE_FN.apply(string);
            v = this.CI_VALUES.get(string);
        }
        return v;
    }

    private static final class CaseInsensitiveFunction
    implements Function<String, String> {
        private static final CaseInsensitiveFunction ENGLISH = new CaseInsensitiveFunction(Locale.ENGLISH);
        private final Locale LOCALE;

        private CaseInsensitiveFunction(Locale locale) {
            this.LOCALE = Assert.notNull(locale, "Case insensitive Locale argument cannot be null.");
        }

        @Override
        public String apply(String string) {
            string = Assert.notNull(Strings.clean(string), "String identifier cannot be null or empty.");
            return string.toUpperCase(this.LOCALE);
        }

        @Override
        public Object apply(Object object) {
            return this.apply((String)object);
        }

        static CaseInsensitiveFunction access$000() {
            return ENGLISH;
        }
    }
}

