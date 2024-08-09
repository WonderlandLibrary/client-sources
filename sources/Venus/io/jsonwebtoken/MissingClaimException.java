/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.InvalidClaimException;

public class MissingClaimException
extends InvalidClaimException {
    public MissingClaimException(Header header, Claims claims, String string, Object object, String string2) {
        super(header, claims, string, object, string2);
    }

    @Deprecated
    public MissingClaimException(Header header, Claims claims, String string, Object object, String string2, Throwable throwable) {
        super(header, claims, string, object, string2, throwable);
    }
}

