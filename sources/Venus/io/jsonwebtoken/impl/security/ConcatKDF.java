/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.lang.CheckedFunction;
import io.jsonwebtoken.impl.security.CryptoAlgorithm;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.SecurityException;
import io.jsonwebtoken.security.UnsupportedKeyException;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

final class ConcatKDF
extends CryptoAlgorithm {
    private static final long MAX_REP_COUNT = 0xFFFFFFFFL;
    private static final long MAX_HASH_INPUT_BYTE_LENGTH = Integer.MAX_VALUE;
    private static final long MAX_HASH_INPUT_BIT_LENGTH = 0x3FFFFFFF8L;
    private final int hashBitLength;
    private static final long MAX_DERIVED_KEY_BIT_LENGTH = 0x3FFFFFFF8L;

    ConcatKDF(String string) {
        super("ConcatKDF", string);
        int n = this.jca().withMessageDigest(new CheckedFunction<MessageDigest, Integer>(this){
            final ConcatKDF this$0;
            {
                this.this$0 = concatKDF;
            }

            @Override
            public Integer apply(MessageDigest messageDigest) {
                return messageDigest.getDigestLength();
            }

            @Override
            public Object apply(Object object) throws Exception {
                return this.apply((MessageDigest)object);
            }
        });
        this.hashBitLength = n * 8;
        Assert.state(this.hashBitLength > 0, "MessageDigest length must be a positive value.");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public SecretKey deriveKey(byte[] byArray, long l, byte[] byArray2) throws UnsupportedKeyException, SecurityException {
        Assert.notEmpty(byArray, "Z cannot be null or empty.");
        Assert.isTrue(l > 0L, "derivedKeyBitLength must be a positive integer.");
        if (l > 0x3FFFFFFF8L) {
            String string = "derivedKeyBitLength may not exceed " + Bytes.bitsMsg(0x3FFFFFFF8L) + ". Specified size: " + Bytes.bitsMsg(l) + ".";
            throw new IllegalArgumentException(string);
        }
        long l2 = l / 8L;
        byte[] byArray3 = byArray2 == null ? Bytes.EMPTY : byArray2;
        double d = (double)l / (double)this.hashBitLength;
        long l3 = (long)Math.ceil(d);
        boolean bl = d != (double)l3;
        Assert.state(l3 <= 0xFFFFFFFFL, "derivedKeyBitLength is too large.");
        byte[] byArray4 = new byte[]{0, 0, 0, 1};
        long l4 = Bytes.bitLength(byArray4) + Bytes.bitLength(byArray) + Bytes.bitLength(byArray3);
        Assert.state(l4 <= 0x3FFFFFFF8L, "Hash input is too large.");
        ClearableByteArrayOutputStream clearableByteArrayOutputStream = new ClearableByteArrayOutputStream((int)l2);
        byte[] byArray5 = Bytes.EMPTY;
        try {
            byArray5 = this.jca().withMessageDigest(new CheckedFunction<MessageDigest, byte[]>(this, l3, byArray4, byArray, byArray3, bl, l, clearableByteArrayOutputStream){
                final long val$reps;
                final byte[] val$counter;
                final byte[] val$Z;
                final byte[] val$OtherInfo;
                final boolean val$kLastPartial;
                final long val$derivedKeyBitLength;
                final ClearableByteArrayOutputStream val$stream;
                final ConcatKDF this$0;
                {
                    this.this$0 = concatKDF;
                    this.val$reps = l;
                    this.val$counter = byArray;
                    this.val$Z = byArray2;
                    this.val$OtherInfo = byArray3;
                    this.val$kLastPartial = bl;
                    this.val$derivedKeyBitLength = l2;
                    this.val$stream = clearableByteArrayOutputStream;
                }

                @Override
                public byte[] apply(MessageDigest messageDigest) throws Exception {
                    for (long i = 1L; i <= this.val$reps; ++i) {
                        messageDigest.update(this.val$counter);
                        messageDigest.update(this.val$Z);
                        messageDigest.update(this.val$OtherInfo);
                        byte[] byArray = messageDigest.digest();
                        Bytes.increment(this.val$counter);
                        if (i == this.val$reps && this.val$kLastPartial) {
                            long l = this.val$derivedKeyBitLength % (long)ConcatKDF.access$000(this.this$0);
                            int n = (int)(l / 8L);
                            byte[] byArray2 = new byte[n];
                            System.arraycopy(byArray, 0, byArray2, 0, byArray2.length);
                            byArray = byArray2;
                        }
                        this.val$stream.write(byArray);
                    }
                    return this.val$stream.toByteArray();
                }

                @Override
                public Object apply(Object object) throws Exception {
                    return this.apply((MessageDigest)object);
                }
            });
            SecretKeySpec secretKeySpec = new SecretKeySpec(byArray5, "AES");
            return secretKeySpec;
        } finally {
            Bytes.clear(byArray5);
            Bytes.clear(byArray4);
            clearableByteArrayOutputStream.reset();
        }
    }

    static int access$000(ConcatKDF concatKDF) {
        return concatKDF.hashBitLength;
    }

    private static class ClearableByteArrayOutputStream
    extends ByteArrayOutputStream {
        public ClearableByteArrayOutputStream(int n) {
            super(n);
        }

        @Override
        public synchronized void reset() {
            super.reset();
            Bytes.clear(this.buf);
        }
    }
}

