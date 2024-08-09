/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.scheme;

import java.util.Locale;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.LayeredSocketFactoryAdaptor;
import org.apache.http.conn.scheme.SchemeLayeredSocketFactory;
import org.apache.http.conn.scheme.SchemeLayeredSocketFactoryAdaptor;
import org.apache.http.conn.scheme.SchemeLayeredSocketFactoryAdaptor2;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.conn.scheme.SchemeSocketFactoryAdaptor;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.scheme.SocketFactoryAdaptor;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public final class Scheme {
    private final String name;
    private final SchemeSocketFactory socketFactory;
    private final int defaultPort;
    private final boolean layered;
    private String stringRep;

    public Scheme(String string, int n, SchemeSocketFactory schemeSocketFactory) {
        Args.notNull(string, "Scheme name");
        Args.check(n > 0 && n <= 65535, "Port is invalid");
        Args.notNull(schemeSocketFactory, "Socket factory");
        this.name = string.toLowerCase(Locale.ENGLISH);
        this.defaultPort = n;
        if (schemeSocketFactory instanceof SchemeLayeredSocketFactory) {
            this.layered = true;
            this.socketFactory = schemeSocketFactory;
        } else if (schemeSocketFactory instanceof LayeredSchemeSocketFactory) {
            this.layered = true;
            this.socketFactory = new SchemeLayeredSocketFactoryAdaptor2((LayeredSchemeSocketFactory)schemeSocketFactory);
        } else {
            this.layered = false;
            this.socketFactory = schemeSocketFactory;
        }
    }

    @Deprecated
    public Scheme(String string, SocketFactory socketFactory, int n) {
        Args.notNull(string, "Scheme name");
        Args.notNull(socketFactory, "Socket factory");
        Args.check(n > 0 && n <= 65535, "Port is invalid");
        this.name = string.toLowerCase(Locale.ENGLISH);
        if (socketFactory instanceof LayeredSocketFactory) {
            this.socketFactory = new SchemeLayeredSocketFactoryAdaptor((LayeredSocketFactory)socketFactory);
            this.layered = true;
        } else {
            this.socketFactory = new SchemeSocketFactoryAdaptor(socketFactory);
            this.layered = false;
        }
        this.defaultPort = n;
    }

    public int getDefaultPort() {
        return this.defaultPort;
    }

    @Deprecated
    public SocketFactory getSocketFactory() {
        if (this.socketFactory instanceof SchemeSocketFactoryAdaptor) {
            return ((SchemeSocketFactoryAdaptor)this.socketFactory).getFactory();
        }
        return this.layered ? new LayeredSocketFactoryAdaptor((LayeredSchemeSocketFactory)this.socketFactory) : new SocketFactoryAdaptor(this.socketFactory);
    }

    public SchemeSocketFactory getSchemeSocketFactory() {
        return this.socketFactory;
    }

    public String getName() {
        return this.name;
    }

    public boolean isLayered() {
        return this.layered;
    }

    public int resolvePort(int n) {
        return n <= 0 ? this.defaultPort : n;
    }

    public String toString() {
        if (this.stringRep == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.name);
            stringBuilder.append(':');
            stringBuilder.append(Integer.toString(this.defaultPort));
            this.stringRep = stringBuilder.toString();
        }
        return this.stringRep;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof Scheme) {
            Scheme scheme = (Scheme)object;
            return this.name.equals(scheme.name) && this.defaultPort == scheme.defaultPort && this.layered == scheme.layered;
        }
        return true;
    }

    public int hashCode() {
        int n = 17;
        n = LangUtils.hashCode(n, this.defaultPort);
        n = LangUtils.hashCode(n, this.name);
        n = LangUtils.hashCode(n, this.layered);
        return n;
    }
}

