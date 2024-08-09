/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.impl.DefaultProtectedHeader;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Registry;
import java.util.Map;
import java.util.Set;

public class DefaultJwsHeader
extends DefaultProtectedHeader
implements JwsHeader {
    static final Parameter<Boolean> B64 = (Parameter)Parameters.builder(Boolean.class).setId("b64").setName("Base64url-Encode Payload").build();
    static final Registry<String, Parameter<?>> PARAMS = Parameters.registry(DefaultProtectedHeader.PARAMS, B64);

    public DefaultJwsHeader(Map<String, ?> map) {
        super(PARAMS, map);
    }

    @Override
    public String getName() {
        return "JWS header";
    }

    @Override
    public boolean isPayloadEncoded() {
        Set<String> set = Collections.nullSafe(this.getCritical());
        Boolean bl = this.get(B64);
        return bl == null || bl != false || !set.contains(B64.getId());
    }
}

