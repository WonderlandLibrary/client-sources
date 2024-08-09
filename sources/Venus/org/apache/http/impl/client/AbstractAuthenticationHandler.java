/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeRegistry;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Asserts;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public abstract class AbstractAuthenticationHandler
implements AuthenticationHandler {
    private final Log log = LogFactory.getLog(this.getClass());
    private static final List<String> DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList(Arrays.asList("Negotiate", "NTLM", "Digest", "Basic"));

    protected Map<String, Header> parseChallenges(Header[] headerArray) throws MalformedChallengeException {
        HashMap<String, Header> hashMap = new HashMap<String, Header>(headerArray.length);
        for (Header header : headerArray) {
            int n;
            CharArrayBuffer charArrayBuffer;
            if (header instanceof FormattedHeader) {
                charArrayBuffer = ((FormattedHeader)header).getBuffer();
                n = ((FormattedHeader)header).getValuePos();
            } else {
                String string = header.getValue();
                if (string == null) {
                    throw new MalformedChallengeException("Header value is null");
                }
                charArrayBuffer = new CharArrayBuffer(string.length());
                charArrayBuffer.append(string);
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
            String string = charArrayBuffer.substring(n2, n3);
            hashMap.put(string.toLowerCase(Locale.ROOT), header);
        }
        return hashMap;
    }

    protected List<String> getAuthPreferences() {
        return DEFAULT_SCHEME_PRIORITY;
    }

    protected List<String> getAuthPreferences(HttpResponse httpResponse, HttpContext httpContext) {
        return this.getAuthPreferences();
    }

    @Override
    public AuthScheme selectScheme(Map<String, Header> map, HttpResponse httpResponse, HttpContext httpContext) throws AuthenticationException {
        AuthSchemeRegistry authSchemeRegistry = (AuthSchemeRegistry)httpContext.getAttribute("http.authscheme-registry");
        Asserts.notNull(authSchemeRegistry, "AuthScheme registry");
        List<String> list = this.getAuthPreferences(httpResponse, httpContext);
        if (list == null) {
            list = DEFAULT_SCHEME_PRIORITY;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Authentication schemes in the order of preference: " + list);
        }
        AuthScheme authScheme = null;
        for (String string : list) {
            Header header = map.get(string.toLowerCase(Locale.ENGLISH));
            if (header != null) {
                if (this.log.isDebugEnabled()) {
                    this.log.debug(string + " authentication scheme selected");
                }
                try {
                    authScheme = authSchemeRegistry.getAuthScheme(string, httpResponse.getParams());
                    break;
                } catch (IllegalStateException illegalStateException) {
                    if (!this.log.isWarnEnabled()) continue;
                    this.log.warn("Authentication scheme " + string + " not supported");
                    continue;
                }
            }
            if (!this.log.isDebugEnabled()) continue;
            this.log.debug("Challenge for " + string + " authentication scheme not available");
        }
        if (authScheme == null) {
            throw new AuthenticationException("Unable to respond to any of these challenges: " + map);
        }
        return authScheme;
    }
}

