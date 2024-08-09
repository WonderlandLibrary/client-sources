/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ClaimsMutator;
import io.jsonwebtoken.lang.Builder;
import io.jsonwebtoken.lang.MapMutator;

public interface ClaimsBuilder
extends MapMutator<String, Object, ClaimsBuilder>,
ClaimsMutator<ClaimsBuilder>,
Builder<Claims> {
}

