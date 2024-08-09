/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultHeader;
import io.jsonwebtoken.impl.DefaultJweHeader;
import io.jsonwebtoken.impl.DefaultJweHeaderBuilder;
import io.jsonwebtoken.impl.DefaultJweHeaderMutator;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.DefaultProtectedHeader;
import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.lang.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultJwtHeaderBuilder
extends DefaultJweHeaderBuilder<Jwts.HeaderBuilder>
implements Jwts.HeaderBuilder {
    public DefaultJwtHeaderBuilder() {
    }

    public DefaultJwtHeaderBuilder(DefaultJweHeaderMutator<?> defaultJweHeaderMutator) {
        super(defaultJweHeaderMutator);
    }

    private static ParameterMap sanitizeCrit(ParameterMap parameterMap, boolean bl) {
        Set<String> set = parameterMap.get(DefaultProtectedHeader.CRIT);
        if (set == null) {
            return parameterMap;
        }
        parameterMap = new ParameterMap(DefaultJweHeader.PARAMS, parameterMap, true);
        parameterMap.remove(DefaultProtectedHeader.CRIT.getId());
        if (!bl) {
            return parameterMap;
        }
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>(set);
        for (String string : set) {
            if (!DefaultJweHeader.PARAMS.containsKey(string) && parameterMap.containsKey(string)) continue;
            linkedHashSet.remove(string);
        }
        if (!Collections.isEmpty(linkedHashSet)) {
            parameterMap.put(DefaultProtectedHeader.CRIT, (Object)linkedHashSet);
        }
        return parameterMap;
    }

    @Override
    public Header build() {
        this.x509.apply();
        ParameterMap parameterMap = (ParameterMap)this.DELEGATE;
        if (DefaultJweHeader.isCandidate(parameterMap)) {
            return new DefaultJweHeader(DefaultJwtHeaderBuilder.sanitizeCrit(parameterMap, true));
        }
        if (DefaultProtectedHeader.isCandidate(parameterMap)) {
            return new DefaultJwsHeader(DefaultJwtHeaderBuilder.sanitizeCrit(parameterMap, true));
        }
        return new DefaultHeader(DefaultJwtHeaderBuilder.sanitizeCrit(parameterMap, false));
    }

    @Override
    public Object build() {
        return this.build();
    }
}

