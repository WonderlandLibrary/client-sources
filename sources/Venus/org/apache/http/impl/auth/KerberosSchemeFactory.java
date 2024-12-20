/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.impl.auth.KerberosScheme;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class KerberosSchemeFactory
implements AuthSchemeFactory,
AuthSchemeProvider {
    private final boolean stripPort;
    private final boolean useCanonicalHostname;

    public KerberosSchemeFactory(boolean bl, boolean bl2) {
        this.stripPort = bl;
        this.useCanonicalHostname = bl2;
    }

    public KerberosSchemeFactory(boolean bl) {
        this.stripPort = bl;
        this.useCanonicalHostname = true;
    }

    public KerberosSchemeFactory() {
        this(true, true);
    }

    public boolean isStripPort() {
        return this.stripPort;
    }

    public boolean isUseCanonicalHostname() {
        return this.useCanonicalHostname;
    }

    @Override
    public AuthScheme newInstance(HttpParams httpParams) {
        return new KerberosScheme(this.stripPort, this.useCanonicalHostname);
    }

    @Override
    public AuthScheme create(HttpContext httpContext) {
        return new KerberosScheme(this.stripPort, this.useCanonicalHostname);
    }
}

