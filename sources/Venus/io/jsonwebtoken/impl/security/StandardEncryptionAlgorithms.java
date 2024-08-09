/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.IdRegistry;
import io.jsonwebtoken.impl.security.GcmAesAeadAlgorithm;
import io.jsonwebtoken.impl.security.HmacAesAeadAlgorithm;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.AeadAlgorithm;

public final class StandardEncryptionAlgorithms
extends IdRegistry<AeadAlgorithm> {
    public static final String NAME = "JWE Encryption Algorithm";

    public StandardEncryptionAlgorithms() {
        super(NAME, Collections.of(new HmacAesAeadAlgorithm(128), new HmacAesAeadAlgorithm(192), new HmacAesAeadAlgorithm(256), new GcmAesAeadAlgorithm(128), new GcmAesAeadAlgorithm(192), new GcmAesAeadAlgorithm(256)));
    }
}

