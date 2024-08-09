/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.utils;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.utils.Idn;
import org.apache.http.client.utils.JdkIdn;
import org.apache.http.client.utils.Rfc3492Idn;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class Punycode {
    private static final Idn impl;

    public static String toUnicode(String string) {
        return impl.toUnicode(string);
    }

    static {
        Idn idn;
        try {
            idn = new JdkIdn();
        } catch (Exception exception) {
            idn = new Rfc3492Idn();
        }
        impl = idn;
    }
}

