/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import org.apache.http.cookie.CookieSpec;
import org.apache.http.params.HttpParams;

@Deprecated
public interface CookieSpecFactory {
    public CookieSpec newInstance(HttpParams var1);
}

