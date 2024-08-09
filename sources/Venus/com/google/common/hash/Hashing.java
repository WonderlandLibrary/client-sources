/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.hash.AbstractCompositeHashFunction;
import com.google.common.hash.ChecksumHashFunction;
import com.google.common.hash.Crc32cHashFunction;
import com.google.common.hash.FarmHashFingerprint64;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.MacHashFunction;
import com.google.common.hash.MessageDigestHashFunction;
import com.google.common.hash.Murmur3_128HashFunction;
import com.google.common.hash.Murmur3_32HashFunction;
import com.google.common.hash.SipHashFunction;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.zip.Adler32;
import java.util.zip.CRC32;
import java.util.zip.Checksum;
import javax.annotation.Nullable;
import javax.crypto.spec.SecretKeySpec;

@Beta
public final class Hashing {
    private static final int GOOD_FAST_HASH_SEED = (int)System.currentTimeMillis();

    public static HashFunction goodFastHash(int n) {
        int n2 = Hashing.checkPositiveAndMakeMultipleOf32(n);
        if (n2 == 32) {
            return Murmur3_32Holder.GOOD_FAST_HASH_FUNCTION_32;
        }
        if (n2 <= 128) {
            return Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
        }
        int n3 = (n2 + 127) / 128;
        HashFunction[] hashFunctionArray = new HashFunction[n3];
        hashFunctionArray[0] = Murmur3_128Holder.GOOD_FAST_HASH_FUNCTION_128;
        int n4 = GOOD_FAST_HASH_SEED;
        for (int i = 1; i < n3; ++i) {
            hashFunctionArray[i] = Hashing.murmur3_128(n4 += 1500450271);
        }
        return new ConcatenatedHashFunction(hashFunctionArray, null);
    }

    public static HashFunction murmur3_32(int n) {
        return new Murmur3_32HashFunction(n);
    }

    public static HashFunction murmur3_32() {
        return Murmur3_32Holder.MURMUR3_32;
    }

    public static HashFunction murmur3_128(int n) {
        return new Murmur3_128HashFunction(n);
    }

    public static HashFunction murmur3_128() {
        return Murmur3_128Holder.MURMUR3_128;
    }

    public static HashFunction sipHash24() {
        return SipHash24Holder.SIP_HASH_24;
    }

    public static HashFunction sipHash24(long l, long l2) {
        return new SipHashFunction(2, 4, l, l2);
    }

    public static HashFunction md5() {
        return Md5Holder.MD5;
    }

    public static HashFunction sha1() {
        return Sha1Holder.SHA_1;
    }

    public static HashFunction sha256() {
        return Sha256Holder.SHA_256;
    }

    public static HashFunction sha384() {
        return Sha384Holder.SHA_384;
    }

    public static HashFunction sha512() {
        return Sha512Holder.SHA_512;
    }

    public static HashFunction hmacMd5(Key key) {
        return new MacHashFunction("HmacMD5", key, Hashing.hmacToString("hmacMd5", key));
    }

    public static HashFunction hmacMd5(byte[] byArray) {
        return Hashing.hmacMd5(new SecretKeySpec(Preconditions.checkNotNull(byArray), "HmacMD5"));
    }

    public static HashFunction hmacSha1(Key key) {
        return new MacHashFunction("HmacSHA1", key, Hashing.hmacToString("hmacSha1", key));
    }

    public static HashFunction hmacSha1(byte[] byArray) {
        return Hashing.hmacSha1(new SecretKeySpec(Preconditions.checkNotNull(byArray), "HmacSHA1"));
    }

    public static HashFunction hmacSha256(Key key) {
        return new MacHashFunction("HmacSHA256", key, Hashing.hmacToString("hmacSha256", key));
    }

    public static HashFunction hmacSha256(byte[] byArray) {
        return Hashing.hmacSha256(new SecretKeySpec(Preconditions.checkNotNull(byArray), "HmacSHA256"));
    }

    public static HashFunction hmacSha512(Key key) {
        return new MacHashFunction("HmacSHA512", key, Hashing.hmacToString("hmacSha512", key));
    }

    public static HashFunction hmacSha512(byte[] byArray) {
        return Hashing.hmacSha512(new SecretKeySpec(Preconditions.checkNotNull(byArray), "HmacSHA512"));
    }

    private static String hmacToString(String string, Key key) {
        return String.format("Hashing.%s(Key[algorithm=%s, format=%s])", string, key.getAlgorithm(), key.getFormat());
    }

    public static HashFunction crc32c() {
        return Crc32cHolder.CRC_32_C;
    }

    public static HashFunction crc32() {
        return Crc32Holder.CRC_32;
    }

    public static HashFunction adler32() {
        return Adler32Holder.ADLER_32;
    }

    private static HashFunction checksumHashFunction(ChecksumType checksumType, String string) {
        return new ChecksumHashFunction(checksumType, ChecksumType.access$300(checksumType), string);
    }

    public static HashFunction farmHashFingerprint64() {
        return FarmHashFingerprint64Holder.FARMHASH_FINGERPRINT_64;
    }

    public static int consistentHash(HashCode hashCode, int n) {
        return Hashing.consistentHash(hashCode.padToLong(), n);
    }

    public static int consistentHash(long l, int n) {
        int n2;
        Preconditions.checkArgument(n > 0, "buckets must be positive: %s", n);
        LinearCongruentialGenerator linearCongruentialGenerator = new LinearCongruentialGenerator(l);
        int n3 = 0;
        while ((n2 = (int)((double)(n3 + 1) / linearCongruentialGenerator.nextDouble())) >= 0 && n2 < n) {
            n3 = n2;
        }
        return n3;
    }

    public static HashCode combineOrdered(Iterable<HashCode> iterable) {
        Iterator<HashCode> iterator2 = iterable.iterator();
        Preconditions.checkArgument(iterator2.hasNext(), "Must be at least 1 hash code to combine.");
        int n = iterator2.next().bits();
        byte[] byArray = new byte[n / 8];
        for (HashCode hashCode : iterable) {
            byte[] byArray2 = hashCode.asBytes();
            Preconditions.checkArgument(byArray2.length == byArray.length, "All hashcodes must have the same bit length.");
            for (int i = 0; i < byArray2.length; ++i) {
                byArray[i] = (byte)(byArray[i] * 37 ^ byArray2[i]);
            }
        }
        return HashCode.fromBytesNoCopy(byArray);
    }

    public static HashCode combineUnordered(Iterable<HashCode> iterable) {
        Iterator<HashCode> iterator2 = iterable.iterator();
        Preconditions.checkArgument(iterator2.hasNext(), "Must be at least 1 hash code to combine.");
        byte[] byArray = new byte[iterator2.next().bits() / 8];
        for (HashCode hashCode : iterable) {
            byte[] byArray2 = hashCode.asBytes();
            Preconditions.checkArgument(byArray2.length == byArray.length, "All hashcodes must have the same bit length.");
            for (int i = 0; i < byArray2.length; ++i) {
                int n = i;
                byArray[n] = (byte)(byArray[n] + byArray2[i]);
            }
        }
        return HashCode.fromBytesNoCopy(byArray);
    }

    static int checkPositiveAndMakeMultipleOf32(int n) {
        Preconditions.checkArgument(n > 0, "Number of bits must be positive");
        return n + 31 & 0xFFFFFFE0;
    }

    public static HashFunction concatenating(HashFunction hashFunction, HashFunction hashFunction2, HashFunction ... hashFunctionArray) {
        ArrayList<HashFunction> arrayList = new ArrayList<HashFunction>();
        arrayList.add(hashFunction);
        arrayList.add(hashFunction2);
        for (HashFunction hashFunction3 : hashFunctionArray) {
            arrayList.add(hashFunction3);
        }
        return new ConcatenatedHashFunction(arrayList.toArray(new HashFunction[0]), null);
    }

    public static HashFunction concatenating(Iterable<HashFunction> iterable) {
        Preconditions.checkNotNull(iterable);
        ArrayList<HashFunction> arrayList = new ArrayList<HashFunction>();
        for (HashFunction hashFunction : iterable) {
            arrayList.add(hashFunction);
        }
        Preconditions.checkArgument(arrayList.size() > 0, "number of hash functions (%s) must be > 0", arrayList.size());
        return new ConcatenatedHashFunction(arrayList.toArray(new HashFunction[0]), null);
    }

    private Hashing() {
    }

    static int access$100() {
        return GOOD_FAST_HASH_SEED;
    }

    static HashFunction access$200(ChecksumType checksumType, String string) {
        return Hashing.checksumHashFunction(checksumType, string);
    }

    private static final class LinearCongruentialGenerator {
        private long state;

        public LinearCongruentialGenerator(long l) {
            this.state = l;
        }

        public double nextDouble() {
            this.state = 2862933555777941757L * this.state + 1L;
            return (double)((int)(this.state >>> 33) + 1) / 2.147483648E9;
        }
    }

    private static final class ConcatenatedHashFunction
    extends AbstractCompositeHashFunction {
        private final int bits;

        private ConcatenatedHashFunction(HashFunction ... hashFunctionArray) {
            super(hashFunctionArray);
            int n = 0;
            for (HashFunction hashFunction : hashFunctionArray) {
                n += hashFunction.bits();
                Preconditions.checkArgument(hashFunction.bits() % 8 == 0, "the number of bits (%s) in hashFunction (%s) must be divisible by 8", hashFunction.bits(), (Object)hashFunction);
            }
            this.bits = n;
        }

        @Override
        HashCode makeHash(Hasher[] hasherArray) {
            byte[] byArray = new byte[this.bits / 8];
            int n = 0;
            for (Hasher hasher : hasherArray) {
                HashCode hashCode = hasher.hash();
                n += hashCode.writeBytesTo(byArray, n, hashCode.bits() / 8);
            }
            return HashCode.fromBytesNoCopy(byArray);
        }

        @Override
        public int bits() {
            return this.bits;
        }

        public boolean equals(@Nullable Object object) {
            if (object instanceof ConcatenatedHashFunction) {
                ConcatenatedHashFunction concatenatedHashFunction = (ConcatenatedHashFunction)object;
                return Arrays.equals(this.functions, concatenatedHashFunction.functions);
            }
            return true;
        }

        public int hashCode() {
            return Arrays.hashCode(this.functions) * 31 + this.bits;
        }

        ConcatenatedHashFunction(HashFunction[] hashFunctionArray, 1 var2_2) {
            this(hashFunctionArray);
        }
    }

    private static class FarmHashFingerprint64Holder {
        static final HashFunction FARMHASH_FINGERPRINT_64 = new FarmHashFingerprint64();

        private FarmHashFingerprint64Holder() {
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static enum ChecksumType implements Supplier<Checksum>
    {
        CRC_32(32){

            @Override
            public Checksum get() {
                return new CRC32();
            }

            @Override
            public Object get() {
                return this.get();
            }
        }
        ,
        ADLER_32(32){

            @Override
            public Checksum get() {
                return new Adler32();
            }

            @Override
            public Object get() {
                return this.get();
            }
        };

        private final int bits;

        private ChecksumType(int n2) {
            this.bits = n2;
        }

        @Override
        public abstract Checksum get();

        @Override
        public Object get() {
            return this.get();
        }

        static int access$300(ChecksumType checksumType) {
            return checksumType.bits;
        }

        ChecksumType(int n2, 1 var4_4) {
            this(n2);
        }
    }

    private static class Adler32Holder {
        static final HashFunction ADLER_32 = Hashing.access$200(ChecksumType.ADLER_32, "Hashing.adler32()");

        private Adler32Holder() {
        }
    }

    private static class Crc32Holder {
        static final HashFunction CRC_32 = Hashing.access$200(ChecksumType.CRC_32, "Hashing.crc32()");

        private Crc32Holder() {
        }
    }

    private static final class Crc32cHolder {
        static final HashFunction CRC_32_C = new Crc32cHashFunction();

        private Crc32cHolder() {
        }
    }

    private static class Sha512Holder {
        static final HashFunction SHA_512 = new MessageDigestHashFunction("SHA-512", "Hashing.sha512()");

        private Sha512Holder() {
        }
    }

    private static class Sha384Holder {
        static final HashFunction SHA_384 = new MessageDigestHashFunction("SHA-384", "Hashing.sha384()");

        private Sha384Holder() {
        }
    }

    private static class Sha256Holder {
        static final HashFunction SHA_256 = new MessageDigestHashFunction("SHA-256", "Hashing.sha256()");

        private Sha256Holder() {
        }
    }

    private static class Sha1Holder {
        static final HashFunction SHA_1 = new MessageDigestHashFunction("SHA-1", "Hashing.sha1()");

        private Sha1Holder() {
        }
    }

    private static class Md5Holder {
        static final HashFunction MD5 = new MessageDigestHashFunction("MD5", "Hashing.md5()");

        private Md5Holder() {
        }
    }

    private static class SipHash24Holder {
        static final HashFunction SIP_HASH_24 = new SipHashFunction(2, 4, 506097522914230528L, 1084818905618843912L);

        private SipHash24Holder() {
        }
    }

    private static class Murmur3_128Holder {
        static final HashFunction MURMUR3_128 = new Murmur3_128HashFunction(0);
        static final HashFunction GOOD_FAST_HASH_FUNCTION_128 = Hashing.murmur3_128(Hashing.access$100());

        private Murmur3_128Holder() {
        }
    }

    private static class Murmur3_32Holder {
        static final HashFunction MURMUR3_32 = new Murmur3_32HashFunction(0);
        static final HashFunction GOOD_FAST_HASH_FUNCTION_32 = Hashing.murmur3_32(Hashing.access$100());

        private Murmur3_32Holder() {
        }
    }
}

