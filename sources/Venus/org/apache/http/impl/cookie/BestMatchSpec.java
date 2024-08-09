/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.cookie;

import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.impl.cookie.DefaultCookieSpec;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE)
public class BestMatchSpec
extends DefaultCookieSpec {
    public BestMatchSpec(String[] stringArray, boolean bl) {
        super(stringArray, bl);
    }

    public BestMatchSpec() {
        this(null, false);
    }

    @Override
    public String toString() {
        return "best-match";
    }
}

