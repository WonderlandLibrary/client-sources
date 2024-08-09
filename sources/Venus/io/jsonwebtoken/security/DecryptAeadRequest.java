/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.AeadRequest;
import io.jsonwebtoken.security.DigestSupplier;
import io.jsonwebtoken.security.IvSupplier;

public interface DecryptAeadRequest
extends AeadRequest,
IvSupplier,
DigestSupplier {
}

