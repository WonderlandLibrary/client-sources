/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth.params;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public final class AuthParams {
    private AuthParams() {
    }

    public static String getCredentialCharset(HttpParams httpParams) {
        Args.notNull(httpParams, "HTTP parameters");
        String string = (String)httpParams.getParameter("http.auth.credential-charset");
        if (string == null) {
            string = HTTP.DEF_PROTOCOL_CHARSET.name();
        }
        return string;
    }

    public static void setCredentialCharset(HttpParams httpParams, String string) {
        Args.notNull(httpParams, "HTTP parameters");
        httpParams.setParameter("http.auth.credential-charset", string);
    }
}

