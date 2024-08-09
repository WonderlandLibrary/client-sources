/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import io.jsonwebtoken.security.KeyRequest;
import io.jsonwebtoken.security.SecureRequest;
import java.security.Key;

public interface DecryptionKeyRequest<K extends Key>
extends SecureRequest<byte[], K>,
KeyRequest<byte[]> {
}

