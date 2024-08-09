/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.security.DefaultKeyPairBuilder;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.Curve;
import io.jsonwebtoken.security.KeyPairBuilder;
import java.security.Key;

abstract class AbstractCurve
implements Curve {
    private final String ID;
    private final String JCA_NAME;

    AbstractCurve(String string, String string2) {
        this.ID = Assert.notNull(Strings.clean(string), "Curve ID cannot be null or empty.");
        this.JCA_NAME = Assert.notNull(Strings.clean(string2), "Curve jcaName cannot be null or empty.");
    }

    @Override
    public String getId() {
        return this.ID;
    }

    public String getJcaName() {
        return this.JCA_NAME;
    }

    public int hashCode() {
        return this.ID.hashCode();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof Curve) {
            Curve curve = (Curve)object;
            return this.ID.equals(curve.getId());
        }
        return true;
    }

    public String toString() {
        return this.ID;
    }

    @Override
    public KeyPairBuilder keyPair() {
        return new DefaultKeyPairBuilder(this.JCA_NAME);
    }

    abstract boolean contains(Key var1);
}

