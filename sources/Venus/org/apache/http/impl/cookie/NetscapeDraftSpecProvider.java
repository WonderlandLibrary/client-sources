/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.Obsolete;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.cookie.NetscapeDraftSpec;
import org.apache.http.protocol.HttpContext;

@Obsolete
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class NetscapeDraftSpecProvider
implements CookieSpecProvider {
    private final String[] datepatterns;
    private volatile CookieSpec cookieSpec;

    public NetscapeDraftSpecProvider(String[] stringArray) {
        this.datepatterns = stringArray;
    }

    public NetscapeDraftSpecProvider() {
        this(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public CookieSpec create(HttpContext httpContext) {
        if (this.cookieSpec == null) {
            NetscapeDraftSpecProvider netscapeDraftSpecProvider = this;
            synchronized (netscapeDraftSpecProvider) {
                if (this.cookieSpec == null) {
                    this.cookieSpec = new NetscapeDraftSpec(this.datepatterns);
                }
            }
        }
        return this.cookieSpec;
    }
}

