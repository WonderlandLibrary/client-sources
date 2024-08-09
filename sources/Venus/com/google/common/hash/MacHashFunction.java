/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractByteHasher;
import com.google.common.hash.AbstractStreamingHashFunction;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

final class MacHashFunction
extends AbstractStreamingHashFunction {
    private final Mac prototype;
    private final Key key;
    private final String toString;
    private final int bits;
    private final boolean supportsClone;

    MacHashFunction(String string, Key key, String string2) {
        this.prototype = MacHashFunction.getMac(string, key);
        this.key = Preconditions.checkNotNull(key);
        this.toString = Preconditions.checkNotNull(string2);
        this.bits = this.prototype.getMacLength() * 8;
        this.supportsClone = MacHashFunction.supportsClone(this.prototype);
    }

    @Override
    public int bits() {
        return this.bits;
    }

    private static boolean supportsClone(Mac mac) {
        try {
            mac.clone();
            return true;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return true;
        }
    }

    private static Mac getMac(String string, Key key) {
        try {
            Mac mac = Mac.getInstance(string);
            mac.init(key);
            return mac;
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new IllegalStateException(noSuchAlgorithmException);
        } catch (InvalidKeyException invalidKeyException) {
            throw new IllegalArgumentException(invalidKeyException);
        }
    }

    @Override
    public Hasher newHasher() {
        if (this.supportsClone) {
            try {
                return new MacHasher((Mac)this.prototype.clone(), null);
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                // empty catch block
            }
        }
        return new MacHasher(MacHashFunction.getMac(this.prototype.getAlgorithm(), this.key), null);
    }

    public String toString() {
        return this.toString;
    }

    private static final class MacHasher
    extends AbstractByteHasher {
        private final Mac mac;
        private boolean done;

        private MacHasher(Mac mac) {
            this.mac = mac;
        }

        @Override
        protected void update(byte by) {
            this.checkNotDone();
            this.mac.update(by);
        }

        @Override
        protected void update(byte[] byArray) {
            this.checkNotDone();
            this.mac.update(byArray);
        }

        @Override
        protected void update(byte[] byArray, int n, int n2) {
            this.checkNotDone();
            this.mac.update(byArray, n, n2);
        }

        private void checkNotDone() {
            Preconditions.checkState(!this.done, "Cannot re-use a Hasher after calling hash() on it");
        }

        @Override
        public HashCode hash() {
            this.checkNotDone();
            this.done = true;
            return HashCode.fromBytesNoCopy(this.mac.doFinal());
        }

        MacHasher(Mac mac, 1 var2_2) {
            this(mac);
        }
    }
}

