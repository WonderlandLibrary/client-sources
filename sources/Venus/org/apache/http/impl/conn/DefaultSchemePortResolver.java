/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class DefaultSchemePortResolver
implements SchemePortResolver {
    public static final DefaultSchemePortResolver INSTANCE = new DefaultSchemePortResolver();

    @Override
    public int resolve(HttpHost httpHost) throws UnsupportedSchemeException {
        Args.notNull(httpHost, "HTTP host");
        int n = httpHost.getPort();
        if (n > 0) {
            return n;
        }
        String string = httpHost.getSchemeName();
        if (string.equalsIgnoreCase("http")) {
            return 1;
        }
        if (string.equalsIgnoreCase("https")) {
            return 0;
        }
        throw new UnsupportedSchemeException(string + " protocol is not supported");
    }
}

