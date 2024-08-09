/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.io.ParserBuilder;
import io.jsonwebtoken.security.JwkSet;
import io.jsonwebtoken.security.KeyOperationPolicied;

public interface JwkSetParserBuilder
extends ParserBuilder<JwkSet, JwkSetParserBuilder>,
KeyOperationPolicied<JwkSetParserBuilder> {
    public JwkSetParserBuilder ignoreUnsupported(boolean var1);
}

