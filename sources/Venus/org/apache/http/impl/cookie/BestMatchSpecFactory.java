/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import java.util.Collection;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecFactory;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.cookie.BestMatchSpec;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class BestMatchSpecFactory
implements CookieSpecFactory,
CookieSpecProvider {
    private final CookieSpec cookieSpec;

    public BestMatchSpecFactory(String[] stringArray, boolean bl) {
        this.cookieSpec = new BestMatchSpec(stringArray, bl);
    }

    public BestMatchSpecFactory() {
        this(null, false);
    }

    @Override
    public CookieSpec newInstance(HttpParams httpParams) {
        if (httpParams != null) {
            String[] stringArray = null;
            Collection collection = (Collection)httpParams.getParameter("http.protocol.cookie-datepatterns");
            if (collection != null) {
                stringArray = new String[collection.size()];
                stringArray = collection.toArray(stringArray);
            }
            boolean bl = httpParams.getBooleanParameter("http.protocol.single-cookie-header", false);
            return new BestMatchSpec(stringArray, bl);
        }
        return new BestMatchSpec();
    }

    @Override
    public CookieSpec create(HttpContext httpContext) {
        return this.cookieSpec;
    }
}

