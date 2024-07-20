/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import org.apache.http.annotation.Immutable;
import org.apache.http.conn.ssl.AbstractVerifier;

@Immutable
public class AllowAllHostnameVerifier
extends AbstractVerifier {
    public final void verify(String host, String[] cns, String[] subjectAlts) {
    }

    public final String toString() {
        return "ALLOW_ALL";
    }
}

