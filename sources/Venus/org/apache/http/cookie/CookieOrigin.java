/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie;

import java.util.Locale;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public final class CookieOrigin {
    private final String host;
    private final int port;
    private final String path;
    private final boolean secure;

    public CookieOrigin(String string, int n, String string2, boolean bl) {
        Args.notBlank(string, "Host");
        Args.notNegative(n, "Port");
        Args.notNull(string2, "Path");
        this.host = string.toLowerCase(Locale.ROOT);
        this.port = n;
        this.path = !TextUtils.isBlank(string2) ? string2 : "/";
        this.secure = bl;
    }

    public String getHost() {
        return this.host;
    }

    public String getPath() {
        return this.path;
    }

    public int getPort() {
        return this.port;
    }

    public boolean isSecure() {
        return this.secure;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        if (this.secure) {
            stringBuilder.append("(secure)");
        }
        stringBuilder.append(this.host);
        stringBuilder.append(':');
        stringBuilder.append(Integer.toString(this.port));
        stringBuilder.append(this.path);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

