/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.scheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.util.Args;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public final class SchemeRegistry {
    private final ConcurrentHashMap<String, Scheme> registeredSchemes = new ConcurrentHashMap();

    public Scheme getScheme(String string) {
        Scheme scheme = this.get(string);
        if (scheme == null) {
            throw new IllegalStateException("Scheme '" + string + "' not registered.");
        }
        return scheme;
    }

    public Scheme getScheme(HttpHost httpHost) {
        Args.notNull(httpHost, "Host");
        return this.getScheme(httpHost.getSchemeName());
    }

    public Scheme get(String string) {
        Args.notNull(string, "Scheme name");
        Scheme scheme = this.registeredSchemes.get(string);
        return scheme;
    }

    public Scheme register(Scheme scheme) {
        Args.notNull(scheme, "Scheme");
        Scheme scheme2 = this.registeredSchemes.put(scheme.getName(), scheme);
        return scheme2;
    }

    public Scheme unregister(String string) {
        Args.notNull(string, "Scheme name");
        Scheme scheme = this.registeredSchemes.remove(string);
        return scheme;
    }

    public List<String> getSchemeNames() {
        return new ArrayList<String>(this.registeredSchemes.keySet());
    }

    public void setItems(Map<String, Scheme> map) {
        if (map == null) {
            return;
        }
        this.registeredSchemes.clear();
        this.registeredSchemes.putAll(map);
    }
}

