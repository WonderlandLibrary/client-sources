/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthState;
import org.apache.http.client.protocol.RequestAuthenticationBase;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RequestTargetAuthentication
extends RequestAuthenticationBase {
    @Override
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        Args.notNull(httpRequest, "HTTP request");
        Args.notNull(httpContext, "HTTP context");
        String string = httpRequest.getRequestLine().getMethod();
        if (string.equalsIgnoreCase("CONNECT")) {
            return;
        }
        if (httpRequest.containsHeader("Authorization")) {
            return;
        }
        AuthState authState = (AuthState)httpContext.getAttribute("http.auth.target-scope");
        if (authState == null) {
            this.log.debug("Target auth state not set in the context");
            return;
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Target auth state: " + (Object)((Object)authState.getState()));
        }
        this.process(authState, httpRequest, httpContext);
    }
}

