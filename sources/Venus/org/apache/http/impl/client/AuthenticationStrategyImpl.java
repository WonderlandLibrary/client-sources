/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthOption;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.client.AuthCache;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
abstract class AuthenticationStrategyImpl
implements AuthenticationStrategy {
    private final Log log = LogFactory.getLog(this.getClass());
    private static final List<String> DEFAULT_SCHEME_PRIORITY = Collections.unmodifiableList(Arrays.asList("Negotiate", "Kerberos", "NTLM", "CredSSP", "Digest", "Basic"));
    private final int challengeCode;
    private final String headerName;

    AuthenticationStrategyImpl(int n, String string) {
        this.challengeCode = n;
        this.headerName = string;
    }

    @Override
    public boolean isAuthenticationRequested(HttpHost httpHost, HttpResponse httpResponse, HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        int n = httpResponse.getStatusLine().getStatusCode();
        return n == this.challengeCode;
    }

    @Override
    public Map<String, Header> getChallenges(HttpHost httpHost, HttpResponse httpResponse, HttpContext httpContext) throws MalformedChallengeException {
        Args.notNull(httpResponse, "HTTP response");
        Header[] headerArray = httpResponse.getHeaders(this.headerName);
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

    abstract Collection<String> getPreferredAuthSchemes(RequestConfig var1);

    @Override
    public Queue<AuthOption> select(Map<String, Header> map, HttpHost httpHost, HttpResponse httpResponse, HttpContext httpContext) throws MalformedChallengeException {
        Args.notNull(map, "Map of auth challenges");
        Args.notNull(httpHost, "Host");
        Args.notNull(httpResponse, "HTTP response");
        Args.notNull(httpContext, "HTTP context");
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        LinkedList<AuthOption> linkedList = new LinkedList<AuthOption>();
        Lookup<AuthSchemeProvider> lookup = httpClientContext.getAuthSchemeRegistry();
        if (lookup == null) {
            this.log.debug("Auth scheme registry not set in the context");
            return linkedList;
        }
        CredentialsProvider credentialsProvider = httpClientContext.getCredentialsProvider();
        if (credentialsProvider == null) {
            this.log.debug("Credentials provider not set in the context");
            return linkedList;
        }
        RequestConfig requestConfig = httpClientContext.getRequestConfig();
        Collection<String> collection = this.getPreferredAuthSchemes(requestConfig);
        if (collection == null) {
            collection = DEFAULT_SCHEME_PRIORITY;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Authentication schemes in the order of preference: " + collection);
        }
        for (String string : collection) {
            Header header = map.get(string.toLowerCase(Locale.ROOT));
            if (header != null) {
                AuthSchemeProvider authSchemeProvider = lookup.lookup(string);
                if (authSchemeProvider == null) {
                    if (!this.log.isWarnEnabled()) continue;
                    this.log.warn("Authentication scheme " + string + " not supported");
                    continue;
                }
                AuthScheme authScheme = authSchemeProvider.create(httpContext);
                authScheme.processChallenge(header);
                AuthScope authScope = new AuthScope(httpHost, authScheme.getRealm(), authScheme.getSchemeName());
                Credentials credentials = credentialsProvider.getCredentials(authScope);
                if (credentials == null) continue;
                linkedList.add(new AuthOption(authScheme, credentials));
                continue;
            }
            if (!this.log.isDebugEnabled()) continue;
            this.log.debug("Challenge for " + string + " authentication scheme not available");
        }
        return linkedList;
    }

    @Override
    public void authSucceeded(HttpHost httpHost, AuthScheme authScheme, HttpContext httpContext) {
        Args.notNull(httpHost, "Host");
        Args.notNull(authScheme, "Auth scheme");
        Args.notNull(httpContext, "HTTP context");
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        if (this.isCachable(authScheme)) {
            AuthCache authCache = httpClientContext.getAuthCache();
            if (authCache == null) {
                authCache = new BasicAuthCache();
                httpClientContext.setAuthCache(authCache);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Caching '" + authScheme.getSchemeName() + "' auth scheme for " + httpHost);
            }
            authCache.put(httpHost, authScheme);
        }
    }

    protected boolean isCachable(AuthScheme authScheme) {
        if (authScheme == null || !authScheme.isComplete()) {
            return true;
        }
        String string = authScheme.getSchemeName();
        return string.equalsIgnoreCase("Basic");
    }

    @Override
    public void authFailed(HttpHost httpHost, AuthScheme authScheme, HttpContext httpContext) {
        Args.notNull(httpHost, "Host");
        Args.notNull(httpContext, "HTTP context");
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        AuthCache authCache = httpClientContext.getAuthCache();
        if (authCache != null) {
            if (this.log.isDebugEnabled()) {
                this.log.debug("Clearing cached auth scheme for " + httpHost);
            }
            authCache.remove(httpHost);
        }
    }
}

