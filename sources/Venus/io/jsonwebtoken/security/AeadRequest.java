/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.AssociatedDataSupplier;
import io.jsonwebtoken.security.SecureRequest;
import java.io.InputStream;
import javax.crypto.SecretKey;

public interface AeadRequest
extends SecureRequest<InputStream, SecretKey>,
AssociatedDataSupplier {
}

