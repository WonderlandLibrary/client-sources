/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.IdRegistry;
import io.jsonwebtoken.impl.security.DefaultKeyOperation;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.security.KeyOperation;

public final class StandardKeyOperations
extends IdRegistry<KeyOperation> {
    public StandardKeyOperations() {
        super("JSON Web Key Operation", Collections.of(DefaultKeyOperation.SIGN, DefaultKeyOperation.VERIFY, DefaultKeyOperation.ENCRYPT, DefaultKeyOperation.DECRYPT, DefaultKeyOperation.WRAP, DefaultKeyOperation.UNWRAP, DefaultKeyOperation.DERIVE_KEY, DefaultKeyOperation.DERIVE_BITS));
    }
}

