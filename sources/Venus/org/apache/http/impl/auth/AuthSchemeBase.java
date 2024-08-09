/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import java.util.Locale;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ChallengeState;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public abstract class AuthSchemeBase
implements ContextAwareAuthScheme {
    protected ChallengeState challengeState;

    @Deprecated
    public AuthSchemeBase(ChallengeState challengeState) {
        this.challengeState = challengeState;
    }

    public AuthSchemeBase() {
    }

    @Override
    public void processChallenge(Header header) throws MalformedChallengeException {
        int n;
        CharArrayBuffer charArrayBuffer;
        Args.notNull(header, "Header");
        String string = header.getName();
        if (string.equalsIgnoreCase("WWW-Authenticate")) {
            this.challengeState = ChallengeState.TARGET;
        } else if (string.equalsIgnoreCase("Proxy-Authenticate")) {
            this.challengeState = ChallengeState.PROXY;
        } else {
            throw new MalformedChallengeException("Unexpected header name: " + string);
        }
        if (header instanceof FormattedHeader) {
            charArrayBuffer = ((FormattedHeader)header).getBuffer();
            n = ((FormattedHeader)header).getValuePos();
        } else {
            String string2 = header.getValue();
            if (string2 == null) {
                throw new MalformedChallengeException("Header value is null");
            }
            charArrayBuffer = new CharArrayBuffer(string2.length());
            charArrayBuffer.append(string2);
            n = 0;
        }
        while (n < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(n))) {
            ++n;
        }
        int n2 = n;
        while (n < charArrayBuffer.length() && !HTTP.isWhitespace(charArrayBuffer.charAt(n))) {
            ++n;
        }
        int n3 = n;
        String string3 = charArrayBuffer.substring(n2, n3);
        if (!string3.equalsIgnoreCase(this.getSchemeName())) {
            throw new MalformedChallengeException("Invalid scheme identifier: " + string3);
        }
        this.parseChallenge(charArrayBuffer, n, charArrayBuffer.length());
    }

    @Override
    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        return this.authenticate(credentials, httpRequest);
    }

    protected abstract void parseChallenge(CharArrayBuffer var1, int var2, int var3) throws MalformedChallengeException;

    public boolean isProxy() {
        return this.challengeState != null && this.challengeState == ChallengeState.PROXY;
    }

    public ChallengeState getChallengeState() {
        return this.challengeState;
    }

    public String toString() {
        String string = this.getSchemeName();
        return string != null ? string.toUpperCase(Locale.ROOT) : super.toString();
    }
}

