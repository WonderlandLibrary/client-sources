/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DelegatingClaimsMutator;
import io.jsonwebtoken.impl.ParameterMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class DefaultClaimsBuilder
extends DelegatingClaimsMutator<ClaimsBuilder>
implements ClaimsBuilder {
    @Override
    public Claims build() {
        return new DefaultClaims((ParameterMap)this.DELEGATE);
    }

    @Override
    public Object build() {
        return this.build();
    }
}

