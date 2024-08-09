/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.impl.auth.GGSSchemeBase;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;

public class KerberosScheme
extends GGSSchemeBase {
    private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";

    public KerberosScheme(boolean bl, boolean bl2) {
        super(bl, bl2);
    }

    public KerberosScheme(boolean bl) {
        super(bl);
    }

    public KerberosScheme() {
    }

    @Override
    public String getSchemeName() {
        return "Kerberos";
    }

    @Override
    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        return super.authenticate(credentials, httpRequest, httpContext);
    }

    @Override
    protected byte[] generateToken(byte[] byArray, String string) throws GSSException {
        return super.generateToken(byArray, string);
    }

    @Override
    protected byte[] generateToken(byte[] byArray, String string, Credentials credentials) throws GSSException {
        return this.generateGSSToken(byArray, new Oid(KERBEROS_OID), string, credentials);
    }

    @Override
    public String getParameter(String string) {
        Args.notNull(string, "Parameter name");
        return null;
    }

    @Override
    public String getRealm() {
        return null;
    }

    @Override
    public boolean isConnectionBased() {
        return false;
    }
}

