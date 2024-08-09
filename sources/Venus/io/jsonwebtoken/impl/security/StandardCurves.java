/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.IdRegistry;
import io.jsonwebtoken.impl.security.AbstractCurve;
import io.jsonwebtoken.impl.security.ECCurve;
import io.jsonwebtoken.impl.security.EdwardsCurve;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.Curve;
import java.security.Key;

public final class StandardCurves
extends IdRegistry<Curve> {
    public StandardCurves() {
        super("Elliptic Curve", Collections.of(ECCurve.P256, ECCurve.P384, ECCurve.P521, EdwardsCurve.X25519, EdwardsCurve.X448, EdwardsCurve.Ed25519, EdwardsCurve.Ed448));
    }

    public static Curve findByKey(Key key) {
        if (key == null) {
            return null;
        }
        AbstractCurve abstractCurve = ECCurve.findByKey(key);
        if (abstractCurve == null) {
            abstractCurve = EdwardsCurve.findByKey(key);
        }
        return abstractCurve;
    }
}

