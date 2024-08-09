/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.AbstractByteHasher;
import com.google.common.hash.AbstractStreamingHashFunction;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

final class MessageDigestHashFunction
extends AbstractStreamingHashFunction
implements Serializable {
    private final MessageDigest prototype;
    private final int bytes;
    private final boolean supportsClone;
    private final String toString;

    MessageDigestHashFunction(String string, String string2) {
        this.prototype = MessageDigestHashFunction.getMessageDigest(string);
        this.bytes = this.prototype.getDigestLength();
        this.toString = Preconditions.checkNotNull(string2);
        this.supportsClone = MessageDigestHashFunction.supportsClone(this.prototype);
    }

    MessageDigestHashFunction(String string, int n, String string2) {
        this.toString = Preconditions.checkNotNull(string2);
        this.prototype = MessageDigestHashFunction.getMessageDigest(string);
        int n2 = this.prototype.getDigestLength();
        Preconditions.checkArgument(n >= 4 && n <= n2, "bytes (%s) must be >= 4 and < %s", n, n2);
        this.bytes = n;
        this.supportsClone = MessageDigestHashFunction.supportsClone(this.prototype);
    }

    private static boolean supportsClone(MessageDigest messageDigest) {
        try {
            messageDigest.clone();
            return true;
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return true;
        }
    }

    @Override
    public int bits() {
        return this.bytes * 8;
    }

    public String toString() {
        return this.toString;
    }

    private static MessageDigest getMessageDigest(String string) {
        try {
            return MessageDigest.getInstance(string);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError((Object)noSuchAlgorithmException);
        }
    }

    @Override
    public Hasher newHasher() {
        if (this.supportsClone) {
            try {
                return new MessageDigestHasher((MessageDigest)this.prototype.clone(), this.bytes, null);
            } catch (CloneNotSupportedException cloneNotSupportedException) {
                // empty catch block
            }
        }
        return new MessageDigestHasher(MessageDigestHashFunction.getMessageDigest(this.prototype.getAlgorithm()), this.bytes, null);
    }

    Object writeReplace() {
        return new SerializedForm(this.prototype.getAlgorithm(), this.bytes, this.toString, null);
    }

    private static final class MessageDigestHasher
    extends AbstractByteHasher {
        private final MessageDigest digest;
        private final int bytes;
        private boolean done;

        private MessageDigestHasher(MessageDigest messageDigest, int n) {
            this.digest = messageDigest;
            this.bytes = n;
        }

        @Override
        protected void update(byte by) {
            this.checkNotDone();
            this.digest.update(by);
        }

        @Override
        protected void update(byte[] byArray) {
            this.checkNotDone();
            this.digest.update(byArray);
        }

        @Override
        protected void update(byte[] byArray, int n, int n2) {
            this.checkNotDone();
            this.digest.update(byArray, n, n2);
        }

        private void checkNotDone() {
            Preconditions.checkState(!this.done, "Cannot re-use a Hasher after calling hash() on it");
        }

        @Override
        public HashCode hash() {
            this.checkNotDone();
            this.done = true;
            return this.bytes == this.digest.getDigestLength() ? HashCode.fromBytesNoCopy(this.digest.digest()) : HashCode.fromBytesNoCopy(Arrays.copyOf(this.digest.digest(), this.bytes));
        }

        MessageDigestHasher(MessageDigest messageDigest, int n, 1 var3_3) {
            this(messageDigest, n);
        }
    }

    private static final class SerializedForm
    implements Serializable {
        private final String algorithmName;
        private final int bytes;
        private final String toString;
        private static final long serialVersionUID = 0L;

        private SerializedForm(String string, int n, String string2) {
            this.algorithmName = string;
            this.bytes = n;
            this.toString = string2;
        }

        private Object readResolve() {
            return new MessageDigestHashFunction(this.algorithmName, this.bytes, this.toString);
        }

        SerializedForm(String string, int n, String string2, 1 var4_4) {
            this(string, n, string2);
        }
    }
}

