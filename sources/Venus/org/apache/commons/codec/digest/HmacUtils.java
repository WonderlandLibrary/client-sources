/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.digest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;

public final class HmacUtils {
    private static final int STREAM_BUFFER_LENGTH = 1024;
    private final Mac mac;

    public static boolean isAvailable(String string) {
        try {
            Mac.getInstance(string);
            return true;
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return true;
        }
    }

    public static boolean isAvailable(HmacAlgorithms hmacAlgorithms) {
        try {
            Mac.getInstance(hmacAlgorithms.getName());
            return true;
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return true;
        }
    }

    @Deprecated
    public static Mac getHmacMd5(byte[] byArray) {
        return HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_MD5, byArray);
    }

    @Deprecated
    public static Mac getHmacSha1(byte[] byArray) {
        return HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_1, byArray);
    }

    @Deprecated
    public static Mac getHmacSha256(byte[] byArray) {
        return HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, byArray);
    }

    @Deprecated
    public static Mac getHmacSha384(byte[] byArray) {
        return HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_384, byArray);
    }

    @Deprecated
    public static Mac getHmacSha512(byte[] byArray) {
        return HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_512, byArray);
    }

    public static Mac getInitializedMac(HmacAlgorithms hmacAlgorithms, byte[] byArray) {
        return HmacUtils.getInitializedMac(hmacAlgorithms.getName(), byArray);
    }

    public static Mac getInitializedMac(String string, byte[] byArray) {
        if (byArray == null) {
            throw new IllegalArgumentException("Null key");
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(byArray, string);
            Mac mac = Mac.getInstance(string);
            mac.init(secretKeySpec);
            return mac;
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new IllegalArgumentException(noSuchAlgorithmException);
        } catch (InvalidKeyException invalidKeyException) {
            throw new IllegalArgumentException(invalidKeyException);
        }
    }

    @Deprecated
    public static byte[] hmacMd5(byte[] byArray, byte[] byArray2) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, byArray).hmac(byArray2);
    }

    @Deprecated
    public static byte[] hmacMd5(byte[] byArray, InputStream inputStream) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, byArray).hmac(inputStream);
    }

    @Deprecated
    public static byte[] hmacMd5(String string, String string2) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, string).hmac(string2);
    }

    @Deprecated
    public static String hmacMd5Hex(byte[] byArray, byte[] byArray2) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, byArray).hmacHex(byArray2);
    }

    @Deprecated
    public static String hmacMd5Hex(byte[] byArray, InputStream inputStream) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, byArray).hmacHex(inputStream);
    }

    @Deprecated
    public static String hmacMd5Hex(String string, String string2) {
        return new HmacUtils(HmacAlgorithms.HMAC_MD5, string).hmacHex(string2);
    }

    @Deprecated
    public static byte[] hmacSha1(byte[] byArray, byte[] byArray2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, byArray).hmac(byArray2);
    }

    @Deprecated
    public static byte[] hmacSha1(byte[] byArray, InputStream inputStream) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, byArray).hmac(inputStream);
    }

    @Deprecated
    public static byte[] hmacSha1(String string, String string2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, string).hmac(string2);
    }

    @Deprecated
    public static String hmacSha1Hex(byte[] byArray, byte[] byArray2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, byArray).hmacHex(byArray2);
    }

    @Deprecated
    public static String hmacSha1Hex(byte[] byArray, InputStream inputStream) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, byArray).hmacHex(inputStream);
    }

    @Deprecated
    public static String hmacSha1Hex(String string, String string2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_1, string).hmacHex(string2);
    }

    @Deprecated
    public static byte[] hmacSha256(byte[] byArray, byte[] byArray2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, byArray).hmac(byArray2);
    }

    @Deprecated
    public static byte[] hmacSha256(byte[] byArray, InputStream inputStream) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, byArray).hmac(inputStream);
    }

    @Deprecated
    public static byte[] hmacSha256(String string, String string2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, string).hmac(string2);
    }

    @Deprecated
    public static String hmacSha256Hex(byte[] byArray, byte[] byArray2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, byArray).hmacHex(byArray2);
    }

    @Deprecated
    public static String hmacSha256Hex(byte[] byArray, InputStream inputStream) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, byArray).hmacHex(inputStream);
    }

    @Deprecated
    public static String hmacSha256Hex(String string, String string2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_256, string).hmacHex(string2);
    }

    @Deprecated
    public static byte[] hmacSha384(byte[] byArray, byte[] byArray2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, byArray).hmac(byArray2);
    }

    @Deprecated
    public static byte[] hmacSha384(byte[] byArray, InputStream inputStream) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, byArray).hmac(inputStream);
    }

    @Deprecated
    public static byte[] hmacSha384(String string, String string2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, string).hmac(string2);
    }

    @Deprecated
    public static String hmacSha384Hex(byte[] byArray, byte[] byArray2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, byArray).hmacHex(byArray2);
    }

    @Deprecated
    public static String hmacSha384Hex(byte[] byArray, InputStream inputStream) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, byArray).hmacHex(inputStream);
    }

    @Deprecated
    public static String hmacSha384Hex(String string, String string2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_384, string).hmacHex(string2);
    }

    @Deprecated
    public static byte[] hmacSha512(byte[] byArray, byte[] byArray2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, byArray).hmac(byArray2);
    }

    @Deprecated
    public static byte[] hmacSha512(byte[] byArray, InputStream inputStream) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, byArray).hmac(inputStream);
    }

    @Deprecated
    public static byte[] hmacSha512(String string, String string2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, string).hmac(string2);
    }

    @Deprecated
    public static String hmacSha512Hex(byte[] byArray, byte[] byArray2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, byArray).hmacHex(byArray2);
    }

    @Deprecated
    public static String hmacSha512Hex(byte[] byArray, InputStream inputStream) throws IOException {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, byArray).hmacHex(inputStream);
    }

    @Deprecated
    public static String hmacSha512Hex(String string, String string2) {
        return new HmacUtils(HmacAlgorithms.HMAC_SHA_512, string).hmacHex(string2);
    }

    public static Mac updateHmac(Mac mac, byte[] byArray) {
        mac.reset();
        mac.update(byArray);
        return mac;
    }

    public static Mac updateHmac(Mac mac, InputStream inputStream) throws IOException {
        mac.reset();
        byte[] byArray = new byte[1024];
        int n = inputStream.read(byArray, 0, 1024);
        while (n > -1) {
            mac.update(byArray, 0, n);
            n = inputStream.read(byArray, 0, 1024);
        }
        return mac;
    }

    public static Mac updateHmac(Mac mac, String string) {
        mac.reset();
        mac.update(StringUtils.getBytesUtf8(string));
        return mac;
    }

    @Deprecated
    public HmacUtils() {
        this(null);
    }

    private HmacUtils(Mac mac) {
        this.mac = mac;
    }

    public HmacUtils(String string, byte[] byArray) {
        this(HmacUtils.getInitializedMac(string, byArray));
    }

    public HmacUtils(String string, String string2) {
        this(string, StringUtils.getBytesUtf8(string2));
    }

    public HmacUtils(HmacAlgorithms hmacAlgorithms, String string) {
        this(hmacAlgorithms.getName(), StringUtils.getBytesUtf8(string));
    }

    public HmacUtils(HmacAlgorithms hmacAlgorithms, byte[] byArray) {
        this(hmacAlgorithms.getName(), byArray);
    }

    public byte[] hmac(byte[] byArray) {
        return this.mac.doFinal(byArray);
    }

    public String hmacHex(byte[] byArray) {
        return Hex.encodeHexString(this.hmac(byArray));
    }

    public byte[] hmac(String string) {
        return this.mac.doFinal(StringUtils.getBytesUtf8(string));
    }

    public String hmacHex(String string) {
        return Hex.encodeHexString(this.hmac(string));
    }

    public byte[] hmac(ByteBuffer byteBuffer) {
        this.mac.update(byteBuffer);
        return this.mac.doFinal();
    }

    public String hmacHex(ByteBuffer byteBuffer) {
        return Hex.encodeHexString(this.hmac(byteBuffer));
    }

    public byte[] hmac(InputStream inputStream) throws IOException {
        int n;
        byte[] byArray = new byte[1024];
        while ((n = inputStream.read(byArray, 0, 1024)) > -1) {
            this.mac.update(byArray, 0, n);
        }
        return this.mac.doFinal();
    }

    public String hmacHex(InputStream inputStream) throws IOException {
        return Hex.encodeHexString(this.hmac(inputStream));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public byte[] hmac(File file) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        try {
            byte[] byArray = this.hmac(bufferedInputStream);
            return byArray;
        } finally {
            bufferedInputStream.close();
        }
    }

    public String hmacHex(File file) throws IOException {
        return Hex.encodeHexString(this.hmac(file));
    }
}

