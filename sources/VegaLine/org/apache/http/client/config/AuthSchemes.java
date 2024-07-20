/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.config;

import org.apache.http.annotation.Immutable;

@Immutable
public final class AuthSchemes {
    public static final String BASIC = "Basic";
    public static final String DIGEST = "Digest";
    public static final String NTLM = "NTLM";
    public static final String SPNEGO = "negotiate";
    public static final String KERBEROS = "Kerberos";

    private AuthSchemes() {
    }
}

