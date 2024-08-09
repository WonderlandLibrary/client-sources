/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.impl.client.AbstractAuthenticationHandler;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class DefaultTargetAuthenticationHandler
extends AbstractAuthenticationHandler {
    @Override
    public boolean isAuthenticationRequested(HttpResponse httpResponse, HttpContext httpContext) {
        Args.notNull(httpResponse, "HTTP response");
        int n = httpResponse.getStatusLine().getStatusCode();
        return n == 401;
    }

    @Override
    public Map<String, Header> getChallenges(HttpResponse httpResponse, HttpContext httpContext) throws MalformedChallengeException {
        Args.notNull(httpResponse, "HTTP response");
        Header[] headerArray = httpResponse.getHeaders("WWW-Authenticate");
        return this.parseChallenges(headerArray);
    }

    @Override
    protected List<String> getAuthPreferences(HttpResponse httpResponse, HttpContext httpContext) {
        List list = (List)httpResponse.getParams().getParameter("http.auth.target-scheme-pref");
        if (list != null) {
            return list;
        }
        return super.getAuthPreferences(httpResponse, httpContext);
    }
}

