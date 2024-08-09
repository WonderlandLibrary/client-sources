/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ssl.AbstractVerifier;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class AllowAllHostnameVerifier
extends AbstractVerifier {
    public static final AllowAllHostnameVerifier INSTANCE = new AllowAllHostnameVerifier();

    @Override
    public final void verify(String string, String[] stringArray, String[] stringArray2) {
    }

    public final String toString() {
        return "ALLOW_ALL";
    }
}

