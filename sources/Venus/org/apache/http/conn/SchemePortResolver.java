/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn;

import org.apache.http.HttpHost;
import org.apache.http.conn.UnsupportedSchemeException;

public interface SchemePortResolver {
    public int resolve(HttpHost var1) throws UnsupportedSchemeException;
}

