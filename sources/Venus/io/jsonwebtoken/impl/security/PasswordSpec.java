/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.security.Password;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class PasswordSpec
implements Password,
KeySpec {
    private static final String NONE_ALGORITHM = "NONE";
    private static final String DESTROYED_MSG = "Password has been destroyed. Password character array may not be obtained.";
    private static final String ENCODED_DISABLED_MSG = "getEncoded() is disabled for Password instances as they are intended to be used with key derivation algorithms only. Because passwords rarely have the length or entropy necessary for secure cryptographic operations such as authenticated hashing or encryption, they are disabled as direct inputs for these operations to help avoid accidental misuse; if you see this exception message, it is likely that the associated Password instance is being used incorrectly.";
    private volatile boolean destroyed;
    private final char[] password;

    public PasswordSpec(char[] cArray) {
        this.password = Assert.notEmpty(cArray, "Password character array cannot be null or empty.");
    }

    private void assertActive() {
        if (this.destroyed) {
            throw new IllegalStateException(DESTROYED_MSG);
        }
    }

    @Override
    public char[] toCharArray() {
        this.assertActive();
        return (char[])this.password.clone();
    }

    @Override
    public String getAlgorithm() {
        return NONE_ALGORITHM;
    }

    @Override
    public String getFormat() {
        return null;
    }

    @Override
    public byte[] getEncoded() {
        throw new UnsupportedOperationException(ENCODED_DISABLED_MSG);
    }

    @Override
    public void destroy() {
        this.destroyed = true;
        Arrays.fill(this.password, '\u0000');
    }

    @Override
    public boolean isDestroyed() {
        return this.destroyed;
    }

    public int hashCode() {
        return Objects.nullSafeHashCode(this.password);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof PasswordSpec) {
            PasswordSpec passwordSpec = (PasswordSpec)object;
            return Objects.nullSafeEquals(this.password, passwordSpec.password);
        }
        return true;
    }

    public final String toString() {
        return "<redacted>";
    }
}

