/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client;

import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.protocol.HttpContext;

@Deprecated
public interface AuthenticationHandler {
    public boolean isAuthenticationRequested(HttpResponse var1, HttpContext var2);

    public Map<String, Header> getChallenges(HttpResponse var1, HttpContext var2) throws MalformedChallengeException;

    public AuthScheme selectScheme(Map<String, Header> var1, HttpResponse var2, HttpContext var3) throws AuthenticationException;
}

