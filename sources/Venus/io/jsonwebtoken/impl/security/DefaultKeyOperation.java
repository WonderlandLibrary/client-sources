/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Collections;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.KeyOperation;
import java.util.Set;

final class DefaultKeyOperation
implements KeyOperation {
    private static final String CUSTOM_DESCRIPTION = "Custom key operation";
    static final KeyOperation SIGN = DefaultKeyOperation.of("sign", "Compute digital signature or MAC", "verify");
    static final KeyOperation VERIFY = DefaultKeyOperation.of("verify", "Verify digital signature or MAC", "sign");
    static final KeyOperation ENCRYPT = DefaultKeyOperation.of("encrypt", "Encrypt content", "decrypt");
    static final KeyOperation DECRYPT = DefaultKeyOperation.of("decrypt", "Decrypt content and validate decryption, if applicable", "encrypt");
    static final KeyOperation WRAP = DefaultKeyOperation.of("wrapKey", "Encrypt key", "unwrapKey");
    static final KeyOperation UNWRAP = DefaultKeyOperation.of("unwrapKey", "Decrypt key and validate decryption, if applicable", "wrapKey");
    static final KeyOperation DERIVE_KEY = DefaultKeyOperation.of("deriveKey", "Derive key", null);
    static final KeyOperation DERIVE_BITS = DefaultKeyOperation.of("deriveBits", "Derive bits not to be used as a key", null);
    final String id;
    final String description;
    final Set<String> related;

    static KeyOperation of(String string, String string2, String string3) {
        return new DefaultKeyOperation(string, string2, Collections.setOf(string3));
    }

    DefaultKeyOperation(String string) {
        this(string, null, null);
    }

    DefaultKeyOperation(String string, String string2, Set<String> set) {
        this.id = Assert.hasText(string, "id cannot be null or empty.");
        this.description = Strings.hasText(string2) ? string2 : CUSTOM_DESCRIPTION;
        this.related = set != null ? Collections.immutable(set) : Collections.emptySet();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean isRelated(KeyOperation keyOperation) {
        return this.equals(keyOperation) || keyOperation != null && this.related.contains(keyOperation.getId());
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public boolean equals(Object object) {
        return object == this || object instanceof KeyOperation && this.id.equals(((KeyOperation)object).getId());
    }

    public String toString() {
        return "'" + this.id + "' (" + this.description + ")";
    }
}

