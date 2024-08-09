/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import javax.net.ssl.SSLException;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ssl.AbstractVerifier;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class StrictHostnameVerifier
extends AbstractVerifier {
    public static final StrictHostnameVerifier INSTANCE = new StrictHostnameVerifier();

    @Override
    public final void verify(String string, String[] stringArray, String[] stringArray2) throws SSLException {
        this.verify(string, stringArray, stringArray2, false);
    }

    public final String toString() {
        return "STRICT";
    }
}

