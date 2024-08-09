/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.impl.auth.RFC2617Scheme;
import org.apache.http.message.BufferedHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EncodingUtils;

public class BasicScheme
extends RFC2617Scheme {
    private static final long serialVersionUID = -1931571557597830536L;
    private boolean complete;

    public BasicScheme(Charset charset) {
        super(charset);
        this.complete = false;
    }

    @Deprecated
    public BasicScheme(ChallengeState challengeState) {
        super(challengeState);
    }

    public BasicScheme() {
        this(Consts.ASCII);
    }

    @Override
    public String getSchemeName() {
        return "basic";
    }

    @Override
    public void processChallenge(Header header) throws MalformedChallengeException {
        super.processChallenge(header);
        this.complete = true;
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }

    @Override
    public boolean isConnectionBased() {
        return true;
    }

    @Override
    @Deprecated
    public Header authenticate(Credentials credentials, HttpRequest httpRequest) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest, new BasicHttpContext());
    }

    @Override
    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        Args.notNull(credentials, "Credentials");
        Args.notNull(httpRequest, "HTTP request");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(credentials.getUserPrincipal().getName());
        stringBuilder.append(":");
        stringBuilder.append(credentials.getPassword() == null ? "null" : credentials.getPassword());
        Base64 base64 = new Base64(0);
        byte[] byArray = base64.encode(EncodingUtils.getBytes(stringBuilder.toString(), this.getCredentialsCharset(httpRequest)));
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(32);
        if (this.isProxy()) {
            charArrayBuffer.append("Proxy-Authorization");
        } else {
            charArrayBuffer.append("Authorization");
        }
        charArrayBuffer.append(": Basic ");
        charArrayBuffer.append(byArray, 0, byArray.length);
        return new BufferedHeader(charArrayBuffer);
    }

    @Deprecated
    public static Header authenticate(Credentials credentials, String string, boolean bl) {
        Args.notNull(credentials, "Credentials");
        Args.notNull(string, "charset");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(credentials.getUserPrincipal().getName());
        stringBuilder.append(":");
        stringBuilder.append(credentials.getPassword() == null ? "null" : credentials.getPassword());
        byte[] byArray = Base64.encodeBase64(EncodingUtils.getBytes(stringBuilder.toString(), string), false);
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(32);
        if (bl) {
            charArrayBuffer.append("Proxy-Authorization");
        } else {
            charArrayBuffer.append("Authorization");
        }
        charArrayBuffer.append(": Basic ");
        charArrayBuffer.append(byArray, 0, byArray.length);
        return new BufferedHeader(charArrayBuffer);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("BASIC [complete=").append(this.complete).append("]");
        return stringBuilder.toString();
    }
}

