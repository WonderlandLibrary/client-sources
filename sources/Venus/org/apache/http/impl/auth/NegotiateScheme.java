/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.impl.auth.GGSSchemeBase;
import org.apache.http.impl.auth.SpnegoTokenGenerator;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;

@Deprecated
public class NegotiateScheme
extends GGSSchemeBase {
    private final Log log = LogFactory.getLog(this.getClass());
    private static final String SPNEGO_OID = "1.3.6.1.5.5.2";
    private static final String KERBEROS_OID = "1.2.840.113554.1.2.2";
    private final SpnegoTokenGenerator spengoGenerator;

    public NegotiateScheme(SpnegoTokenGenerator spnegoTokenGenerator, boolean bl) {
        super(bl);
        this.spengoGenerator = spnegoTokenGenerator;
    }

    public NegotiateScheme(SpnegoTokenGenerator spnegoTokenGenerator) {
        this(spnegoTokenGenerator, false);
    }

    public NegotiateScheme() {
        this(null, false);
    }

    @Override
    public String getSchemeName() {
        return "Negotiate";
    }

    @Override
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest, null);
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
        Oid oid = new Oid(SPNEGO_OID);
        byte[] byArray2 = byArray;
        boolean bl = false;
        try {
            byArray2 = this.generateGSSToken(byArray2, oid, string, credentials);
        } catch (GSSException gSSException) {
            if (gSSException.getMajor() == 2) {
                this.log.debug("GSSException BAD_MECH, retry with Kerberos MECH");
                bl = true;
            }
            throw gSSException;
        }
        if (bl) {
            this.log.debug("Using Kerberos MECH 1.2.840.113554.1.2.2");
            oid = new Oid(KERBEROS_OID);
            byArray2 = this.generateGSSToken(byArray2, oid, string, credentials);
            if (byArray2 != null && this.spengoGenerator != null) {
                try {
                    byArray2 = this.spengoGenerator.generateSpnegoDERObject(byArray2);
                } catch (IOException iOException) {
                    this.log.error(iOException.getMessage(), iOException);
                }
            }
        }
        return byArray2;
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

