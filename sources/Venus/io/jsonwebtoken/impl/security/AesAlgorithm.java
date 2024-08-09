/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.impl.lang.Bytes;
import io.jsonwebtoken.impl.security.CryptoAlgorithm;
import io.jsonwebtoken.impl.security.DefaultSecretKeyBuilder;
import io.jsonwebtoken.lang.Arrays;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.security.IvSupplier;
import io.jsonwebtoken.security.KeyBuilder;
import io.jsonwebtoken.security.KeyBuilderSupplier;
import io.jsonwebtoken.security.KeyLengthSupplier;
import io.jsonwebtoken.security.Request;
import io.jsonwebtoken.security.SecretKeyBuilder;
import io.jsonwebtoken.security.WeakKeyException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
abstract class AesAlgorithm
extends CryptoAlgorithm
implements KeyBuilderSupplier<SecretKey, SecretKeyBuilder>,
KeyLengthSupplier {
    protected static final String KEY_ALG_NAME = "AES";
    protected static final int BLOCK_SIZE = 128;
    protected static final int BLOCK_BYTE_SIZE = 16;
    protected static final int GCM_IV_SIZE = 96;
    protected static final String DECRYPT_NO_IV = "This algorithm implementation rejects decryption requests that do not include initialization vectors. AES ciphertext without an IV is weak and susceptible to attack.";
    protected final int keyBitLength;
    protected final int ivBitLength;
    protected final int tagBitLength;
    protected final boolean gcm;

    AesAlgorithm(String string, String string2, int n) {
        super(string, string2);
        Assert.isTrue(n == 128 || n == 192 || n == 256, "Invalid AES key length: it must equal 128, 192, or 256.");
        this.keyBitLength = n;
        this.gcm = string2.startsWith("AES/GCM");
        this.ivBitLength = string2.equals("AESWrap") ? 0 : (this.gcm ? 96 : 128);
        this.tagBitLength = this.gcm ? 128 : this.keyBitLength;
    }

    @Override
    public int getKeyBitLength() {
        return this.keyBitLength;
    }

    @Override
    public SecretKeyBuilder key() {
        return new DefaultSecretKeyBuilder(KEY_ALG_NAME, this.getKeyBitLength());
    }

    protected SecretKey assertKey(SecretKey secretKey) {
        Assert.notNull(secretKey, "Request key cannot be null.");
        this.validateLengthIfPossible(secretKey);
        return secretKey;
    }

    private void validateLengthIfPossible(SecretKey secretKey) {
        this.validateLength(secretKey, this.keyBitLength, true);
    }

    protected static String lengthMsg(String string, String string2, int n, long l) {
        return "The '" + string + "' algorithm requires " + string2 + " with a length of " + Bytes.bitsMsg(n) + ".  The provided key has a length of " + Bytes.bitsMsg(l) + ".";
    }

    protected byte[] validateLength(SecretKey secretKey, int n, boolean bl) {
        byte[] byArray;
        try {
            byArray = secretKey.getEncoded();
        } catch (RuntimeException runtimeException) {
            if (bl) {
                throw runtimeException;
            }
            return null;
        }
        long l = Bytes.bitLength(byArray);
        if (l < (long)n) {
            throw new WeakKeyException(AesAlgorithm.lengthMsg(this.getId(), "keys", n, l));
        }
        return byArray;
    }

    protected byte[] assertBytes(byte[] byArray, String string, int n) {
        long l = Bytes.bitLength(byArray);
        if ((long)n != l) {
            String string2 = AesAlgorithm.lengthMsg(this.getId(), string, n, l);
            throw new IllegalArgumentException(string2);
        }
        return byArray;
    }

    byte[] assertIvLength(byte[] byArray) {
        return this.assertBytes(byArray, "initialization vectors", this.ivBitLength);
    }

    byte[] assertTag(byte[] byArray) {
        return this.assertBytes(byArray, "authentication tags", this.tagBitLength);
    }

    byte[] assertDecryptionIv(IvSupplier ivSupplier) throws IllegalArgumentException {
        byte[] byArray = ivSupplier.getIv();
        Assert.notEmpty(byArray, DECRYPT_NO_IV);
        return this.assertIvLength(byArray);
    }

    protected byte[] ensureInitializationVector(Request<?> request) {
        byte[] byArray = null;
        if (request instanceof IvSupplier) {
            byArray = Arrays.clean(((IvSupplier)((Object)request)).getIv());
        }
        int n = this.ivBitLength / 8;
        if (byArray == null || byArray.length == 0) {
            byArray = new byte[n];
            SecureRandom secureRandom = AesAlgorithm.ensureSecureRandom(request);
            secureRandom.nextBytes(byArray);
        } else {
            this.assertIvLength(byArray);
        }
        return byArray;
    }

    protected AlgorithmParameterSpec getIvSpec(byte[] byArray) {
        Assert.notEmpty(byArray, "Initialization Vector byte array cannot be null or empty.");
        return this.gcm ? new GCMParameterSpec(128, byArray) : new IvParameterSpec(byArray);
    }

    protected void withCipher(Cipher cipher, InputStream inputStream, OutputStream outputStream) throws Exception {
        byte[] byArray = this.withCipher(cipher, inputStream, null, outputStream);
        outputStream.write(byArray);
    }

    private void updateAAD(Cipher cipher, InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return;
        }
        byte[] byArray = new byte[2048];
        int n = 0;
        while (n != -1) {
            n = inputStream.read(byArray);
            if (n <= 0) continue;
            cipher.updateAAD(byArray, 0, n);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected byte[] withCipher(Cipher cipher, InputStream inputStream, InputStream inputStream2, OutputStream outputStream) throws Exception {
        this.updateAAD(cipher, inputStream2);
        byte[] byArray = new byte[2048];
        try {
            byte[] byArray2;
            int n = 0;
            while (n != -1) {
                n = inputStream.read(byArray);
                if (n <= 0) continue;
                byArray2 = cipher.update(byArray, 0, n);
                Streams.write(outputStream, byArray2, "Unable to write Cipher output to OutputStream");
            }
            byArray2 = cipher.doFinal();
            return byArray2;
        } finally {
            Bytes.clear(byArray);
        }
    }

    @Override
    public KeyBuilder key() {
        return this.key();
    }
}

