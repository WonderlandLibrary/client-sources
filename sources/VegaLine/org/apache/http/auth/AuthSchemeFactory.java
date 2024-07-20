/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth;

import org.apache.http.auth.AuthScheme;
import org.apache.http.params.HttpParams;

@Deprecated
public interface AuthSchemeFactory {
    public AuthScheme newInstance(HttpParams var1);
}

