/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.auth.KerberosCredentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.auth.AuthSchemeBase;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.GSSManager;
import org.ietf.jgss.GSSName;
import org.ietf.jgss.Oid;

public abstract class GGSSchemeBase
extends AuthSchemeBase {
    private final Log log = LogFactory.getLog(this.getClass());
    private final Base64 base64codec = new Base64(0);
    private final boolean stripPort;
    private final boolean useCanonicalHostname;
    private State state;
    private byte[] token;

    GGSSchemeBase(boolean bl, boolean bl2) {
        this.stripPort = bl;
        this.useCanonicalHostname = bl2;
        this.state = State.UNINITIATED;
    }

    GGSSchemeBase(boolean bl) {
        this(bl, true);
    }

    GGSSchemeBase() {
        this(true, true);
    }

    protected GSSManager getManager() {
        return GSSManager.getInstance();
    }

    protected byte[] generateGSSToken(byte[] byArray, Oid oid, String string) throws GSSException {
        return this.generateGSSToken(byArray, oid, string, null);
    }

    protected byte[] generateGSSToken(byte[] byArray, Oid oid, String string, Credentials credentials) throws GSSException {
        GSSManager gSSManager = this.getManager();
        GSSName gSSName = gSSManager.createName("HTTP@" + string, GSSName.NT_HOSTBASED_SERVICE);
        GSSCredential gSSCredential = credentials instanceof KerberosCredentials ? ((KerberosCredentials)credentials).getGSSCredential() : null;
        GSSContext gSSContext = this.createGSSContext(gSSManager, oid, gSSName, gSSCredential);
        return byArray != null ? gSSContext.initSecContext(byArray, 0, byArray.length) : gSSContext.initSecContext(new byte[0], 0, 0);
    }

    GSSContext createGSSContext(GSSManager gSSManager, Oid oid, GSSName gSSName, GSSCredential gSSCredential) throws GSSException {
        GSSContext gSSContext = gSSManager.createContext(gSSName.canonicalize(oid), oid, gSSCredential, 0);
        gSSContext.requestMutualAuth(true);
        return gSSContext;
    }

    @Deprecated
    protected byte[] generateToken(byte[] byArray, String string) throws GSSException {
        return null;
    }

    protected byte[] generateToken(byte[] byArray, String string, Credentials credentials) throws GSSException {
        return this.generateToken(byArray, string);
    }

    @Override
    public boolean isComplete() {
        return this.state == State.TOKEN_GENERATED || this.state == State.FAILED;
    }

    @Override
    @Deprecated
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest, null);
    }

    @Override
    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        Args.notNull(httpRequest, "HTTP request");
        switch (1.$SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State[this.state.ordinal()]) {
            case 1: {
                throw new AuthenticationException(this.getSchemeName() + " authentication has not been initiated");
            }
            case 2: {
                throw new AuthenticationException(this.getSchemeName() + " authentication has failed");
            }
            case 3: {
                Serializable serializable;
                Object object;
                try {
                    object = (HttpRoute)httpContext.getAttribute("http.route");
                    if (object == null) {
                        throw new AuthenticationException("Connection route is not available");
                    }
                    if (this.isProxy()) {
                        serializable = ((HttpRoute)object).getProxyHost();
                        if (serializable == null) {
                            serializable = ((HttpRoute)object).getTargetHost();
                        }
                    } else {
                        serializable = ((HttpRoute)object).getTargetHost();
                    }
                    String string = ((HttpHost)serializable).getHostName();
                    if (this.useCanonicalHostname) {
                        try {
                            string = this.resolveCanonicalHostname(string);
                        } catch (UnknownHostException unknownHostException) {
                            // empty catch block
                        }
                    }
                    String string2 = this.stripPort ? string : string + ":" + ((HttpHost)serializable).getPort();
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("init " + string2);
                    }
                    this.token = this.generateToken(this.token, string2, credentials);
                    this.state = State.TOKEN_GENERATED;
                } catch (GSSException gSSException) {
                    this.state = State.FAILED;
                    if (gSSException.getMajor() == 9 || gSSException.getMajor() == 8) {
                        throw new InvalidCredentialsException(gSSException.getMessage(), gSSException);
                    }
                    if (gSSException.getMajor() == 13) {
                        throw new InvalidCredentialsException(gSSException.getMessage(), gSSException);
                    }
                    if (gSSException.getMajor() == 10 || gSSException.getMajor() == 19 || gSSException.getMajor() == 20) {
                        throw new AuthenticationException(gSSException.getMessage(), gSSException);
                    }
                    throw new AuthenticationException(gSSException.getMessage());
                }
            }
            case 4: {
                Object object = new String(this.base64codec.encode(this.token));
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Sending response '" + (String)object + "' back to the auth server");
                }
                Serializable serializable = new CharArrayBuffer(32);
                if (this.isProxy()) {
                    ((CharArrayBuffer)serializable).append("Proxy-Authorization");
                } else {
                    ((CharArrayBuffer)serializable).append("Authorization");
                }
                ((CharArrayBuffer)serializable).append(": Negotiate ");
                ((CharArrayBuffer)serializable).append((String)object);
                return new BufferedHeader((CharArrayBuffer)serializable);
            }
        }
        throw new IllegalStateException("Illegal state: " + (Object)((Object)this.state));
    }

    @Override
    protected void parseChallenge(CharArrayBuffer charArrayBuffer, int n, int n2) throws MalformedChallengeException {
        String string = charArrayBuffer.substringTrimmed(n, n2);
        if (this.log.isDebugEnabled()) {
            this.log.debug("Received challenge '" + string + "' from the auth server");
        }
        if (this.state == State.UNINITIATED) {
            this.token = Base64.decodeBase64(string.getBytes());
            this.state = State.CHALLENGE_RECEIVED;
        } else {
            this.log.debug("Authentication already attempted");
            this.state = State.FAILED;
        }
    }

    private String resolveCanonicalHostname(String string) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName(string);
        String string2 = inetAddress.getCanonicalHostName();
        if (inetAddress.getHostAddress().contentEquals(string2)) {
            return string;
        }
        return string2;
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State = new int[State.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State[State.UNINITIATED.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State[State.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State[State.CHALLENGE_RECEIVED.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$http$impl$auth$GGSSchemeBase$State[State.TOKEN_GENERATED.ordinal()] = 4;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }

    static enum State {
        UNINITIATED,
        CHALLENGE_RECEIVED,
        TOKEN_GENERATED,
        FAILED;

    }
}

