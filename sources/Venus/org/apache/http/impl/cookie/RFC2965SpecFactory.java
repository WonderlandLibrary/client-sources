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
import org.apache.http.impl.cookie.RFC2965Spec;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class RFC2965SpecFactory
implements CookieSpecFactory,
CookieSpecProvider {
    private final CookieSpec cookieSpec;

    public RFC2965SpecFactory(String[] stringArray, boolean bl) {
        this.cookieSpec = new RFC2965Spec(stringArray, bl);
    }

    public RFC2965SpecFactory() {
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
            return new RFC2965Spec(stringArray, bl);
        }
        return new RFC2965Spec();
    }

    @Override
    public CookieSpec create(HttpContext httpContext) {
        return this.cookieSpec;
    }
}

