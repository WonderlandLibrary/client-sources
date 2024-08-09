/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.impl.auth.NTLMScheme;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class NTLMSchemeFactory
implements AuthSchemeFactory,
AuthSchemeProvider {
    @Override
    public AuthScheme newInstance(HttpParams httpParams) {
        return new NTLMScheme();
    }

    @Override
    public AuthScheme create(HttpContext httpContext) {
        return new NTLMScheme();
    }
}

