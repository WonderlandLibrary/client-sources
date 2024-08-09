/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.protocol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.auth.AuthOption;
import org.apache.http.auth.AuthProtocolState;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Asserts;

@Deprecated
abstract class RequestAuthenticationBase
implements HttpRequestInterceptor {
    final Log log = LogFactory.getLog(this.getClass());

    void process(AuthState authState, HttpRequest httpRequest, HttpContext httpContext) {
        block13: {
            Object object;
            AuthScheme authScheme = authState.getAuthScheme();
            Credentials credentials = authState.getCredentials();
            switch (1.$SwitchMap$org$apache$http$auth$AuthProtocolState[authState.getState().ordinal()]) {
                case 1: {
                    return;
                }
                case 2: {
                    this.ensureAuthScheme(authScheme);
                    if (!authScheme.isConnectionBased()) break;
                    return;
                }
                case 3: {
                    object = authState.getAuthOptions();
                    if (object != null) {
                        while (!object.isEmpty()) {
                            AuthOption authOption = object.remove();
                            authScheme = authOption.getAuthScheme();
                            credentials = authOption.getCredentials();
                            authState.update(authScheme, credentials);
                            if (this.log.isDebugEnabled()) {
                                this.log.debug("Generating response to an authentication challenge using " + authScheme.getSchemeName() + " scheme");
                            }
                            try {
                                Header header = this.authenticate(authScheme, credentials, httpRequest, httpContext);
                                httpRequest.addHeader(header);
                                break;
                            } catch (AuthenticationException authenticationException) {
                                if (!this.log.isWarnEnabled()) continue;
                                this.log.warn(authScheme + " authentication error: " + authenticationException.getMessage());
                            }
                        }
                        return;
                    }
                    this.ensureAuthScheme(authScheme);
                }
            }
            if (authScheme != null) {
                try {
                    object = this.authenticate(authScheme, credentials, httpRequest, httpContext);
                    httpRequest.addHeader((Header)object);
                } catch (AuthenticationException authenticationException) {
                    if (!this.log.isErrorEnabled()) break block13;
                    this.log.error(authScheme + " authentication error: " + authenticationException.getMessage());
                }
            }
        }
    }

    private void ensureAuthScheme(AuthScheme authScheme) {
        Asserts.notNull(authScheme, "Auth scheme");
    }

    private Header authenticate(AuthScheme authScheme, Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        Asserts.notNull(authScheme, "Auth scheme");
        if (authScheme instanceof ContextAwareAuthScheme) {
            return ((ContextAwareAuthScheme)authScheme).authenticate(credentials, httpRequest, httpContext);
        }
        return authScheme.authenticate(credentials, httpRequest);
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$http$auth$AuthProtocolState = new int[AuthProtocolState.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.FAILURE.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$org$apache$http$auth$AuthProtocolState[AuthProtocolState.CHALLENGED.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }
}

