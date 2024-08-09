/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.security.Principal;
import javax.net.ssl.SSLSession;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.protocol.HttpContext;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class DefaultUserTokenHandler
implements UserTokenHandler {
    public static final DefaultUserTokenHandler INSTANCE = new DefaultUserTokenHandler();

    @Override
    public Object getUserToken(HttpContext httpContext) {
        SSLSession sSLSession;
        Object object;
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        Principal principal = null;
        AuthState authState = httpClientContext.getTargetAuthState();
        if (authState != null && (principal = DefaultUserTokenHandler.getAuthPrincipal(authState)) == null) {
            object = httpClientContext.getProxyAuthState();
            principal = DefaultUserTokenHandler.getAuthPrincipal((AuthState)object);
        }
        if (principal == null && (object = httpClientContext.getConnection()).isOpen() && object instanceof ManagedHttpClientConnection && (sSLSession = ((ManagedHttpClientConnection)object).getSSLSession()) != null) {
            principal = sSLSession.getLocalPrincipal();
        }
        return principal;
    }

    private static Principal getAuthPrincipal(AuthState authState) {
        Credentials credentials;
        AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme != null && authScheme.isComplete() && authScheme.isConnectionBased() && (credentials = authState.getCredentials()) != null) {
            return credentials.getUserPrincipal();
        }
        return null;
    }
}

