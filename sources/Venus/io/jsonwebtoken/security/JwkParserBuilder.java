/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.io.ParserBuilder;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.KeyOperationPolicied;

public interface JwkParserBuilder
extends ParserBuilder<Jwk<?>, JwkParserBuilder>,
KeyOperationPolicied<JwkParserBuilder> {
}

