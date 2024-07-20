/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import org.apache.http.annotation.Immutable;
import org.apache.http.cookie.MalformedCookieException;

@Immutable
public class CookieRestrictionViolationException
extends MalformedCookieException {
    private static final long serialVersionUID = 7371235577078589013L;

    public CookieRestrictionViolationException() {
    }

    public CookieRestrictionViolationException(String message) {
        super(message);
    }
}

