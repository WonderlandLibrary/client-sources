/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE)
public class UriPatternMatcher<T> {
    private final Map<String, T> map = new LinkedHashMap<String, T>();

    public synchronized Set<Map.Entry<String, T>> entrySet() {
        return new HashSet<Map.Entry<String, T>>(this.map.entrySet());
    }

    public synchronized void register(String string, T t) {
        Args.notNull(string, "URI request pattern");
        this.map.put(string, t);
    }

    public synchronized void unregister(String string) {
        if (string == null) {
            return;
        }
        this.map.remove(string);
    }

    @Deprecated
    public synchronized void setHandlers(Map<String, T> map) {
        Args.notNull(map, "Map of handlers");
        this.map.clear();
        this.map.putAll(map);
    }

    @Deprecated
    public synchronized void setObjects(Map<String, T> map) {
        Args.notNull(map, "Map of handlers");
        this.map.clear();
        this.map.putAll(map);
    }

    @Deprecated
    public synchronized Map<String, T> getObjects() {
        return this.map;
    }

    public synchronized T lookup(String string) {
        Args.notNull(string, "Request path");
        T t = this.map.get(string);
        if (t == null) {
            String string2 = null;
            for (String string3 : this.map.keySet()) {
                if (!this.matchUriRequestPattern(string3, string) || string2 != null && string2.length() >= string3.length() && (string2.length() != string3.length() || !string3.endsWith("*"))) continue;
                t = this.map.get(string3);
                string2 = string3;
            }
        }
        return t;
    }

    protected boolean matchUriRequestPattern(String string, String string2) {
        if (string.equals("*")) {
            return false;
        }
        return string.endsWith("*") && string2.startsWith(string.substring(0, string.length() - 1)) || string.startsWith("*") && string2.endsWith(string.substring(1, string.length()));
    }

    public String toString() {
        return this.map.toString();
    }
}

