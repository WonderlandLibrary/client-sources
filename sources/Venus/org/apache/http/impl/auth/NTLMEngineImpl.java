/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Consts;
import org.apache.http.impl.auth.NTLMEngine;
import org.apache.http.impl.auth.NTLMEngineException;

final class NTLMEngineImpl
implements NTLMEngine {
    private static final Charset UNICODE_LITTLE_UNMARKED = Charset.forName("UnicodeLittleUnmarked");
    private static final Charset DEFAULT_CHARSET = Consts.ASCII;
    static final int FLAG_REQUEST_UNICODE_ENCODING = 1;
    static final int FLAG_REQUEST_OEM_ENCODING = 2;
    static final int FLAG_REQUEST_TARGET = 4;
    static final int FLAG_REQUEST_SIGN = 16;
    static final int FLAG_REQUEST_SEAL = 32;
    static final int FLAG_REQUEST_LAN_MANAGER_KEY = 128;
    static final int FLAG_REQUEST_NTLMv1 = 512;
    static final int FLAG_DOMAIN_PRESENT = 4096;
    static final int FLAG_WORKSTATION_PRESENT = 8192;
    static final int FLAG_REQUEST_ALWAYS_SIGN = 32768;
    static final int FLAG_REQUEST_NTLM2_SESSION = 524288;
    static final int FLAG_REQUEST_VERSION = 0x2000000;
    static final int FLAG_TARGETINFO_PRESENT = 0x800000;
    static final int FLAG_REQUEST_128BIT_KEY_EXCH = 0x20000000;
    static final int FLAG_REQUEST_EXPLICIT_KEY_EXCH = 0x40000000;
    static final int FLAG_REQUEST_56BIT_ENCRYPTION = Integer.MIN_VALUE;
    static final int MSV_AV_EOL = 0;
    static final int MSV_AV_NB_COMPUTER_NAME = 1;
    static final int MSV_AV_NB_DOMAIN_NAME = 2;
    static final int MSV_AV_DNS_COMPUTER_NAME = 3;
    static final int MSV_AV_DNS_DOMAIN_NAME = 4;
    static final int MSV_AV_DNS_TREE_NAME = 5;
    static final int MSV_AV_FLAGS = 6;
    static final int MSV_AV_TIMESTAMP = 7;
    static final int MSV_AV_SINGLE_HOST = 8;
    static final int MSV_AV_TARGET_NAME = 9;
    static final int MSV_AV_CHANNEL_BINDINGS = 10;
    static final int MSV_AV_FLAGS_ACCOUNT_AUTH_CONSTAINED = 1;
    static final int MSV_AV_FLAGS_MIC = 2;
    static final int MSV_AV_FLAGS_UNTRUSTED_TARGET_SPN = 4;
    private static final SecureRandom RND_GEN;
    private static final byte[] SIGNATURE;
    private static final byte[] SIGN_MAGIC_SERVER;
    private static final byte[] SIGN_MAGIC_CLIENT;
    private static final byte[] SEAL_MAGIC_SERVER;
    private static final byte[] SEAL_MAGIC_CLIENT;
    private static final byte[] MAGIC_TLS_SERVER_ENDPOINT;
    private static final String TYPE_1_MESSAGE;

    private static byte[] getNullTerminatedAsciiString(String string) {
        byte[] byArray = string.getBytes(Consts.ASCII);
        byte[] byArray2 = new byte[byArray.length + 1];
        System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        byArray2[byArray.length] = 0;
        return byArray2;
    }

    NTLMEngineImpl() {
    }

    static String getType1Message(String string, String string2) {
        return TYPE_1_MESSAGE;
    }

    static String getType3Message(String string, String string2, String string3, String string4, byte[] byArray, int n, String string5, byte[] byArray2) throws NTLMEngineException {
        return new Type3Message(string4, string3, string, string2, byArray, n, string5, byArray2).getResponse();
    }

    static String getType3Message(String string, String string2, String string3, String string4, byte[] byArray, int n, String string5, byte[] byArray2, Certificate certificate, byte[] byArray3, byte[] byArray4) throws NTLMEngineException {
        return new Type3Message(string4, string3, string, string2, byArray, n, string5, byArray2, certificate, byArray3, byArray4).getResponse();
    }

    private static int readULong(byte[] byArray, int n) {
        if (byArray.length < n + 4) {
            return 1;
        }
        return byArray[n] & 0xFF | (byArray[n + 1] & 0xFF) << 8 | (byArray[n + 2] & 0xFF) << 16 | (byArray[n + 3] & 0xFF) << 24;
    }

    private static int readUShort(byte[] byArray, int n) {
        if (byArray.length < n + 2) {
            return 1;
        }
        return byArray[n] & 0xFF | (byArray[n + 1] & 0xFF) << 8;
    }

    private static byte[] readSecurityBuffer(byte[] byArray, int n) {
        int n2 = NTLMEngineImpl.readUShort(byArray, n);
        int n3 = NTLMEngineImpl.readULong(byArray, n + 4);
        if (byArray.length < n3 + n2) {
            return new byte[n2];
        }
        byte[] byArray2 = new byte[n2];
        System.arraycopy(byArray, n3, byArray2, 0, n2);
        return byArray2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static byte[] makeRandomChallenge(Random random2) {
        byte[] byArray = new byte[8];
        Random random3 = random2;
        synchronized (random3) {
            random2.nextBytes(byArray);
        }
        return byArray;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static byte[] makeSecondaryKey(Random random2) {
        byte[] byArray = new byte[16];
        Random random3 = random2;
        synchronized (random3) {
            random2.nextBytes(byArray);
        }
        return byArray;
    }

    static byte[] hmacMD5(byte[] byArray, byte[] byArray2) throws NTLMEngineException {
        HMACMD5 hMACMD5 = new HMACMD5(byArray2);
        hMACMD5.update(byArray);
        return hMACMD5.getOutput();
    }

    static byte[] RC4(byte[] byArray, byte[] byArray2) throws NTLMEngineException {
        try {
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(1, new SecretKeySpec(byArray2, "RC4"));
            return cipher.doFinal(byArray);
        } catch (Exception exception) {
            throw new NTLMEngineException(exception.getMessage(), exception);
        }
    }

    static byte[] ntlm2SessionResponse(byte[] byArray, byte[] byArray2, byte[] byArray3) throws NTLMEngineException {
        try {
            MessageDigest messageDigest = NTLMEngineImpl.getMD5();
            messageDigest.update(byArray2);
            messageDigest.update(byArray3);
            byte[] byArray4 = messageDigest.digest();
            byte[] byArray5 = new byte[8];
            System.arraycopy(byArray4, 0, byArray5, 0, 8);
            return NTLMEngineImpl.lmResponse(byArray, byArray5);
        } catch (Exception exception) {
            if (exception instanceof NTLMEngineException) {
                throw (NTLMEngineException)exception;
            }
            throw new NTLMEngineException(exception.getMessage(), exception);
        }
    }

    private static byte[] lmHash(String string) throws NTLMEngineException {
        try {
            byte[] byArray = string.toUpperCase(Locale.ROOT).getBytes(Consts.ASCII);
            int n = Math.min(byArray.length, 14);
            byte[] byArray2 = new byte[14];
            System.arraycopy(byArray, 0, byArray2, 0, n);
            Key key = NTLMEngineImpl.createDESKey(byArray2, 0);
            Key key2 = NTLMEngineImpl.createDESKey(byArray2, 7);
            byte[] byArray3 = "KGS!@#$%".getBytes(Consts.ASCII);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, key);
            byte[] byArray4 = cipher.doFinal(byArray3);
            cipher.init(1, key2);
            byte[] byArray5 = cipher.doFinal(byArray3);
            byte[] byArray6 = new byte[16];
            System.arraycopy(byArray4, 0, byArray6, 0, 8);
            System.arraycopy(byArray5, 0, byArray6, 8, 8);
            return byArray6;
        } catch (Exception exception) {
            throw new NTLMEngineException(exception.getMessage(), exception);
        }
    }

    private static byte[] ntlmHash(String string) throws NTLMEngineException {
        if (UNICODE_LITTLE_UNMARKED == null) {
            throw new NTLMEngineException("Unicode not supported");
        }
        byte[] byArray = string.getBytes(UNICODE_LITTLE_UNMARKED);
        MD4 mD4 = new MD4();
        mD4.update(byArray);
        return mD4.getOutput();
    }

    private static byte[] lmv2Hash(String string, String string2, byte[] byArray) throws NTLMEngineException {
        if (UNICODE_LITTLE_UNMARKED == null) {
            throw new NTLMEngineException("Unicode not supported");
        }
        HMACMD5 hMACMD5 = new HMACMD5(byArray);
        hMACMD5.update(string2.toUpperCase(Locale.ROOT).getBytes(UNICODE_LITTLE_UNMARKED));
        if (string != null) {
            hMACMD5.update(string.toUpperCase(Locale.ROOT).getBytes(UNICODE_LITTLE_UNMARKED));
        }
        return hMACMD5.getOutput();
    }

    private static byte[] ntlmv2Hash(String string, String string2, byte[] byArray) throws NTLMEngineException {
        if (UNICODE_LITTLE_UNMARKED == null) {
            throw new NTLMEngineException("Unicode not supported");
        }
        HMACMD5 hMACMD5 = new HMACMD5(byArray);
        hMACMD5.update(string2.toUpperCase(Locale.ROOT).getBytes(UNICODE_LITTLE_UNMARKED));
        if (string != null) {
            hMACMD5.update(string.getBytes(UNICODE_LITTLE_UNMARKED));
        }
        return hMACMD5.getOutput();
    }

    private static byte[] lmResponse(byte[] byArray, byte[] byArray2) throws NTLMEngineException {
        try {
            byte[] byArray3 = new byte[21];
            System.arraycopy(byArray, 0, byArray3, 0, 16);
            Key key = NTLMEngineImpl.createDESKey(byArray3, 0);
            Key key2 = NTLMEngineImpl.createDESKey(byArray3, 7);
            Key key3 = NTLMEngineImpl.createDESKey(byArray3, 14);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            cipher.init(1, key);
            byte[] byArray4 = cipher.doFinal(byArray2);
            cipher.init(1, key2);
            byte[] byArray5 = cipher.doFinal(byArray2);
            cipher.init(1, key3);
            byte[] byArray6 = cipher.doFinal(byArray2);
            byte[] byArray7 = new byte[24];
            System.arraycopy(byArray4, 0, byArray7, 0, 8);
            System.arraycopy(byArray5, 0, byArray7, 8, 8);
            System.arraycopy(byArray6, 0, byArray7, 16, 8);
            return byArray7;
        } catch (Exception exception) {
            throw new NTLMEngineException(exception.getMessage(), exception);
        }
    }

    private static byte[] lmv2Response(byte[] byArray, byte[] byArray2, byte[] byArray3) {
        HMACMD5 hMACMD5 = new HMACMD5(byArray);
        hMACMD5.update(byArray2);
        hMACMD5.update(byArray3);
        byte[] byArray4 = hMACMD5.getOutput();
        byte[] byArray5 = new byte[byArray4.length + byArray3.length];
        System.arraycopy(byArray4, 0, byArray5, 0, byArray4.length);
        System.arraycopy(byArray3, 0, byArray5, byArray4.length, byArray3.length);
        return byArray5;
    }

    private static byte[] encodeLong(int n) {
        byte[] byArray = new byte[4];
        NTLMEngineImpl.encodeLong(byArray, 0, n);
        return byArray;
    }

    private static void encodeLong(byte[] byArray, int n, int n2) {
        byArray[n + 0] = (byte)(n2 & 0xFF);
        byArray[n + 1] = (byte)(n2 >> 8 & 0xFF);
        byArray[n + 2] = (byte)(n2 >> 16 & 0xFF);
        byArray[n + 3] = (byte)(n2 >> 24 & 0xFF);
    }

    private static byte[] createBlob(byte[] byArray, byte[] byArray2, byte[] byArray3) {
        byte[] byArray4 = new byte[]{1, 1, 0, 0};
        byte[] byArray5 = new byte[]{0, 0, 0, 0};
        byte[] byArray6 = new byte[]{0, 0, 0, 0};
        byte[] byArray7 = new byte[]{0, 0, 0, 0};
        byte[] byArray8 = new byte[byArray4.length + byArray5.length + byArray3.length + 8 + byArray6.length + byArray2.length + byArray7.length];
        int n = 0;
        System.arraycopy(byArray4, 0, byArray8, n, byArray4.length);
        System.arraycopy(byArray5, 0, byArray8, n += byArray4.length, byArray5.length);
        System.arraycopy(byArray3, 0, byArray8, n += byArray5.length, byArray3.length);
        System.arraycopy(byArray, 0, byArray8, n += byArray3.length, 8);
        System.arraycopy(byArray6, 0, byArray8, n += 8, byArray6.length);
        System.arraycopy(byArray2, 0, byArray8, n += byArray6.length, byArray2.length);
        System.arraycopy(byArray7, 0, byArray8, n += byArray2.length, byArray7.length);
        n += byArray7.length;
        return byArray8;
    }

    private static Key createDESKey(byte[] byArray, int n) {
        byte[] byArray2 = new byte[7];
        System.arraycopy(byArray, n, byArray2, 0, 7);
        byte[] byArray3 = new byte[]{byArray2[0], (byte)(byArray2[0] << 7 | (byArray2[1] & 0xFF) >>> 1), (byte)(byArray2[1] << 6 | (byArray2[2] & 0xFF) >>> 2), (byte)(byArray2[2] << 5 | (byArray2[3] & 0xFF) >>> 3), (byte)(byArray2[3] << 4 | (byArray2[4] & 0xFF) >>> 4), (byte)(byArray2[4] << 3 | (byArray2[5] & 0xFF) >>> 5), (byte)(byArray2[5] << 2 | (byArray2[6] & 0xFF) >>> 6), (byte)(byArray2[6] << 1)};
        NTLMEngineImpl.oddParity(byArray3);
        return new SecretKeySpec(byArray3, "DES");
    }

    private static void oddParity(byte[] byArray) {
        for (int i = 0; i < byArray.length; ++i) {
            boolean bl;
            byte by = byArray[i];
            boolean bl2 = bl = ((by >>> 7 ^ by >>> 6 ^ by >>> 5 ^ by >>> 4 ^ by >>> 3 ^ by >>> 2 ^ by >>> 1) & 1) == 0;
            if (bl) {
                int n = i;
                byArray[n] = (byte)(byArray[n] | 1);
                continue;
            }
            int n = i;
            byArray[n] = (byte)(byArray[n] & 0xFFFFFFFE);
        }
    }

    private static Charset getCharset(int n) throws NTLMEngineException {
        if ((n & 1) == 0) {
            return DEFAULT_CHARSET;
        }
        if (UNICODE_LITTLE_UNMARKED == null) {
            throw new NTLMEngineException("Unicode not supported");
        }
        return UNICODE_LITTLE_UNMARKED;
    }

    private static String stripDotSuffix(String string) {
        if (string == null) {
            return null;
        }
        int n = string.indexOf(46);
        if (n != -1) {
            return string.substring(0, n);
        }
        return string;
    }

    private static String convertHost(String string) {
        return NTLMEngineImpl.stripDotSuffix(string);
    }

    private static String convertDomain(String string) {
        return NTLMEngineImpl.stripDotSuffix(string);
    }

    static void writeUShort(byte[] byArray, int n, int n2) {
        byArray[n2] = (byte)(n & 0xFF);
        byArray[n2 + 1] = (byte)(n >> 8 & 0xFF);
    }

    static void writeULong(byte[] byArray, int n, int n2) {
        byArray[n2] = (byte)(n & 0xFF);
        byArray[n2 + 1] = (byte)(n >> 8 & 0xFF);
        byArray[n2 + 2] = (byte)(n >> 16 & 0xFF);
        byArray[n2 + 3] = (byte)(n >> 24 & 0xFF);
    }

    static int F(int n, int n2, int n3) {
        return n & n2 | ~n & n3;
    }

    static int G(int n, int n2, int n3) {
        return n & n2 | n & n3 | n2 & n3;
    }

    static int H(int n, int n2, int n3) {
        return n ^ n2 ^ n3;
    }

    static int rotintlft(int n, int n2) {
        return n << n2 | n >>> 32 - n2;
    }

    static MessageDigest getMD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException("MD5 message digest doesn't seem to exist - fatal error: " + noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
        }
    }

    @Override
    public String generateType1Msg(String string, String string2) throws NTLMEngineException {
        return NTLMEngineImpl.getType1Message(string2, string);
    }

    @Override
    public String generateType3Msg(String string, String string2, String string3, String string4, String string5) throws NTLMEngineException {
        Type2Message type2Message = new Type2Message(string5);
        return NTLMEngineImpl.getType3Message(string, string2, string4, string3, type2Message.getChallenge(), type2Message.getFlags(), type2Message.getTarget(), type2Message.getTargetInfo());
    }

    static SecureRandom access$000() {
        return RND_GEN;
    }

    static byte[] access$100(Random random2) {
        return NTLMEngineImpl.makeRandomChallenge(random2);
    }

    static byte[] access$200(Random random2) {
        return NTLMEngineImpl.makeSecondaryKey(random2);
    }

    static byte[] access$300(String string) throws NTLMEngineException {
        return NTLMEngineImpl.lmHash(string);
    }

    static byte[] access$400(byte[] byArray, byte[] byArray2) throws NTLMEngineException {
        return NTLMEngineImpl.lmResponse(byArray, byArray2);
    }

    static byte[] access$500(String string) throws NTLMEngineException {
        return NTLMEngineImpl.ntlmHash(string);
    }

    static byte[] access$600(String string, String string2, byte[] byArray) throws NTLMEngineException {
        return NTLMEngineImpl.lmv2Hash(string, string2, byArray);
    }

    static byte[] access$700(String string, String string2, byte[] byArray) throws NTLMEngineException {
        return NTLMEngineImpl.ntlmv2Hash(string, string2, byArray);
    }

    static byte[] access$800(byte[] byArray, byte[] byArray2, byte[] byArray3) {
        return NTLMEngineImpl.createBlob(byArray, byArray2, byArray3);
    }

    static byte[] access$900(byte[] byArray, byte[] byArray2, byte[] byArray3) {
        return NTLMEngineImpl.lmv2Response(byArray, byArray2, byArray3);
    }

    static Key access$1000(byte[] byArray, int n) {
        return NTLMEngineImpl.createDESKey(byArray, n);
    }

    static byte[] access$1100() {
        return SIGN_MAGIC_CLIENT;
    }

    static byte[] access$1200() {
        return SEAL_MAGIC_CLIENT;
    }

    static byte[] access$1300() {
        return SIGN_MAGIC_SERVER;
    }

    static byte[] access$1400() {
        return SEAL_MAGIC_SERVER;
    }

    static byte[] access$1500(int n) {
        return NTLMEngineImpl.encodeLong(n);
    }

    static void access$1600(byte[] byArray, int n, int n2) {
        NTLMEngineImpl.encodeLong(byArray, n, n2);
    }

    static Charset access$1700() {
        return DEFAULT_CHARSET;
    }

    static byte[] access$1800() {
        return SIGNATURE;
    }

    static int access$1900(byte[] byArray, int n) {
        return NTLMEngineImpl.readUShort(byArray, n);
    }

    static int access$2000(byte[] byArray, int n) {
        return NTLMEngineImpl.readULong(byArray, n);
    }

    static byte[] access$2100(byte[] byArray, int n) {
        return NTLMEngineImpl.readSecurityBuffer(byArray, n);
    }

    static String access$2200(String string) {
        return NTLMEngineImpl.convertHost(string);
    }

    static String access$2300(String string) {
        return NTLMEngineImpl.convertDomain(string);
    }

    static Charset access$2400() {
        return UNICODE_LITTLE_UNMARKED;
    }

    static Charset access$2500(int n) throws NTLMEngineException {
        return NTLMEngineImpl.getCharset(n);
    }

    static byte[] access$2600() {
        return MAGIC_TLS_SERVER_ENDPOINT;
    }

    static {
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception exception) {
            // empty catch block
        }
        RND_GEN = secureRandom;
        SIGNATURE = NTLMEngineImpl.getNullTerminatedAsciiString("NTLMSSP");
        SIGN_MAGIC_SERVER = NTLMEngineImpl.getNullTerminatedAsciiString("session key to server-to-client signing key magic constant");
        SIGN_MAGIC_CLIENT = NTLMEngineImpl.getNullTerminatedAsciiString("session key to client-to-server signing key magic constant");
        SEAL_MAGIC_SERVER = NTLMEngineImpl.getNullTerminatedAsciiString("session key to server-to-client sealing key magic constant");
        SEAL_MAGIC_CLIENT = NTLMEngineImpl.getNullTerminatedAsciiString("session key to client-to-server sealing key magic constant");
        MAGIC_TLS_SERVER_ENDPOINT = "tls-server-end-point:".getBytes(Consts.ASCII);
        TYPE_1_MESSAGE = new Type1Message().getResponse();
    }

    static class HMACMD5 {
        protected final byte[] ipad;
        protected final byte[] opad;
        protected final MessageDigest md5;

        HMACMD5(byte[] byArray) {
            int n;
            byte[] byArray2 = byArray;
            this.md5 = NTLMEngineImpl.getMD5();
            this.ipad = new byte[64];
            this.opad = new byte[64];
            int n2 = byArray2.length;
            if (n2 > 64) {
                this.md5.update(byArray2);
                byArray2 = this.md5.digest();
                n2 = byArray2.length;
            }
            for (n = 0; n < n2; ++n) {
                this.ipad[n] = (byte)(byArray2[n] ^ 0x36);
                this.opad[n] = (byte)(byArray2[n] ^ 0x5C);
            }
            while (n < 64) {
                this.ipad[n] = 54;
                this.opad[n] = 92;
                ++n;
            }
            this.md5.reset();
            this.md5.update(this.ipad);
        }

        byte[] getOutput() {
            byte[] byArray = this.md5.digest();
            this.md5.update(this.opad);
            return this.md5.digest(byArray);
        }

        void update(byte[] byArray) {
            this.md5.update(byArray);
        }

        void update(byte[] byArray, int n, int n2) {
            this.md5.update(byArray, n, n2);
        }
    }

    static class MD4 {
        protected int A = 1732584193;
        protected int B = -271733879;
        protected int C = -1732584194;
        protected int D = 271733878;
        protected long count = 0L;
        protected final byte[] dataBuffer = new byte[64];

        MD4() {
        }

        void update(byte[] byArray) {
            int n;
            int n2 = (int)(this.count & 0x3FL);
            int n3 = 0;
            while (byArray.length - n3 + n2 >= this.dataBuffer.length) {
                n = this.dataBuffer.length - n2;
                System.arraycopy(byArray, n3, this.dataBuffer, n2, n);
                this.count += (long)n;
                n2 = 0;
                n3 += n;
                this.processBuffer();
            }
            if (n3 < byArray.length) {
                n = byArray.length - n3;
                System.arraycopy(byArray, n3, this.dataBuffer, n2, n);
                this.count += (long)n;
                n2 += n;
            }
        }

        byte[] getOutput() {
            int n = (int)(this.count & 0x3FL);
            int n2 = n < 56 ? 56 - n : 120 - n;
            byte[] byArray = new byte[n2 + 8];
            byArray[0] = -128;
            for (int i = 0; i < 8; ++i) {
                byArray[n2 + i] = (byte)(this.count * 8L >>> 8 * i);
            }
            this.update(byArray);
            byte[] byArray2 = new byte[16];
            NTLMEngineImpl.writeULong(byArray2, this.A, 0);
            NTLMEngineImpl.writeULong(byArray2, this.B, 4);
            NTLMEngineImpl.writeULong(byArray2, this.C, 8);
            NTLMEngineImpl.writeULong(byArray2, this.D, 12);
            return byArray2;
        }

        protected void processBuffer() {
            int n;
            int[] nArray = new int[16];
            for (n = 0; n < 16; ++n) {
                nArray[n] = (this.dataBuffer[n * 4] & 0xFF) + ((this.dataBuffer[n * 4 + 1] & 0xFF) << 8) + ((this.dataBuffer[n * 4 + 2] & 0xFF) << 16) + ((this.dataBuffer[n * 4 + 3] & 0xFF) << 24);
            }
            n = this.A;
            int n2 = this.B;
            int n3 = this.C;
            int n4 = this.D;
            this.round1(nArray);
            this.round2(nArray);
            this.round3(nArray);
            this.A += n;
            this.B += n2;
            this.C += n3;
            this.D += n4;
        }

        protected void round1(int[] nArray) {
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + nArray[0], 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + nArray[1], 7);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + nArray[2], 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + nArray[3], 19);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + nArray[4], 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + nArray[5], 7);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + nArray[6], 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + nArray[7], 19);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + nArray[8], 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + nArray[9], 7);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + nArray[10], 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + nArray[11], 19);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.F(this.B, this.C, this.D) + nArray[12], 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.F(this.A, this.B, this.C) + nArray[13], 7);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.F(this.D, this.A, this.B) + nArray[14], 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.F(this.C, this.D, this.A) + nArray[15], 19);
        }

        protected void round2(int[] nArray) {
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + nArray[0] + 1518500249, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + nArray[4] + 1518500249, 5);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + nArray[8] + 1518500249, 9);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + nArray[12] + 1518500249, 13);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + nArray[1] + 1518500249, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + nArray[5] + 1518500249, 5);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + nArray[9] + 1518500249, 9);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + nArray[13] + 1518500249, 13);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + nArray[2] + 1518500249, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + nArray[6] + 1518500249, 5);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + nArray[10] + 1518500249, 9);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + nArray[14] + 1518500249, 13);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.G(this.B, this.C, this.D) + nArray[3] + 1518500249, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.G(this.A, this.B, this.C) + nArray[7] + 1518500249, 5);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.G(this.D, this.A, this.B) + nArray[11] + 1518500249, 9);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.G(this.C, this.D, this.A) + nArray[15] + 1518500249, 13);
        }

        protected void round3(int[] nArray) {
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + nArray[0] + 1859775393, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + nArray[8] + 1859775393, 9);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + nArray[4] + 1859775393, 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + nArray[12] + 1859775393, 15);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + nArray[2] + 1859775393, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + nArray[10] + 1859775393, 9);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + nArray[6] + 1859775393, 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + nArray[14] + 1859775393, 15);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + nArray[1] + 1859775393, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + nArray[9] + 1859775393, 9);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + nArray[5] + 1859775393, 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + nArray[13] + 1859775393, 15);
            this.A = NTLMEngineImpl.rotintlft(this.A + NTLMEngineImpl.H(this.B, this.C, this.D) + nArray[3] + 1859775393, 3);
            this.D = NTLMEngineImpl.rotintlft(this.D + NTLMEngineImpl.H(this.A, this.B, this.C) + nArray[11] + 1859775393, 9);
            this.C = NTLMEngineImpl.rotintlft(this.C + NTLMEngineImpl.H(this.D, this.A, this.B) + nArray[7] + 1859775393, 11);
            this.B = NTLMEngineImpl.rotintlft(this.B + NTLMEngineImpl.H(this.C, this.D, this.A) + nArray[15] + 1859775393, 15);
        }
    }

    static class Type3Message
    extends NTLMMessage {
        protected final byte[] type1Message;
        protected final byte[] type2Message;
        protected final int type2Flags;
        protected final byte[] domainBytes;
        protected final byte[] hostBytes;
        protected final byte[] userBytes;
        protected byte[] lmResp;
        protected byte[] ntResp;
        protected final byte[] sessionKey;
        protected final byte[] exportedSessionKey;
        protected final boolean computeMic;

        Type3Message(String string, String string2, String string3, String string4, byte[] byArray, int n, String string5, byte[] byArray2) throws NTLMEngineException {
            this(string, string2, string3, string4, byArray, n, string5, byArray2, null, null, null);
        }

        Type3Message(Random random2, long l, String string, String string2, String string3, String string4, byte[] byArray, int n, String string5, byte[] byArray2) throws NTLMEngineException {
            this(random2, l, string, string2, string3, string4, byArray, n, string5, byArray2, null, null, null);
        }

        Type3Message(String string, String string2, String string3, String string4, byte[] byArray, int n, String string5, byte[] byArray2, Certificate certificate, byte[] byArray3, byte[] byArray4) throws NTLMEngineException {
            this(NTLMEngineImpl.access$000(), System.currentTimeMillis(), string, string2, string3, string4, byArray, n, string5, byArray2, certificate, byArray3, byArray4);
        }

        Type3Message(Random random2, long l, String string, String string2, String string3, String string4, byte[] byArray, int n, String string5, byte[] byArray2, Certificate certificate, byte[] byArray3, byte[] byArray4) throws NTLMEngineException {
            byte[] byArray5;
            if (random2 == null) {
                throw new NTLMEngineException("Random generator not available");
            }
            this.type2Flags = n;
            this.type1Message = byArray3;
            this.type2Message = byArray4;
            String string6 = NTLMEngineImpl.access$2200(string2);
            String string7 = NTLMEngineImpl.access$2300(string);
            byte[] byArray6 = byArray2;
            if (certificate != null) {
                byArray6 = this.addGssMicAvsToTargetInfo(byArray2, certificate);
                this.computeMic = true;
            } else {
                this.computeMic = false;
            }
            CipherGen cipherGen = new CipherGen(random2, l, string7, string3, string4, byArray, string5, byArray6);
            try {
                if ((n & 0x800000) != 0 && byArray2 != null && string5 != null) {
                    this.ntResp = cipherGen.getNTLMv2Response();
                    this.lmResp = cipherGen.getLMv2Response();
                    byArray5 = (n & 0x80) != 0 ? cipherGen.getLanManagerSessionKey() : cipherGen.getNTLMv2UserSessionKey();
                } else if ((n & 0x80000) != 0) {
                    this.ntResp = cipherGen.getNTLM2SessionResponse();
                    this.lmResp = cipherGen.getLM2SessionResponse();
                    byArray5 = (n & 0x80) != 0 ? cipherGen.getLanManagerSessionKey() : cipherGen.getNTLM2SessionResponseUserSessionKey();
                } else {
                    this.ntResp = cipherGen.getNTLMResponse();
                    this.lmResp = cipherGen.getLMResponse();
                    byArray5 = (n & 0x80) != 0 ? cipherGen.getLanManagerSessionKey() : cipherGen.getNTLMUserSessionKey();
                }
            } catch (NTLMEngineException nTLMEngineException) {
                this.ntResp = new byte[0];
                this.lmResp = cipherGen.getLMResponse();
                byArray5 = (n & 0x80) != 0 ? cipherGen.getLanManagerSessionKey() : cipherGen.getLMUserSessionKey();
            }
            if ((n & 0x10) != 0) {
                if ((n & 0x40000000) != 0) {
                    this.exportedSessionKey = cipherGen.getSecondaryKey();
                    this.sessionKey = NTLMEngineImpl.RC4(this.exportedSessionKey, byArray5);
                } else {
                    this.sessionKey = byArray5;
                    this.exportedSessionKey = this.sessionKey;
                }
            } else {
                if (this.computeMic) {
                    throw new NTLMEngineException("Cannot sign/seal: no exported session key");
                }
                this.sessionKey = null;
                this.exportedSessionKey = null;
            }
            Charset charset = NTLMEngineImpl.access$2500(n);
            this.hostBytes = string6 != null ? string6.getBytes(charset) : null;
            this.domainBytes = string7 != null ? string7.toUpperCase(Locale.ROOT).getBytes(charset) : null;
            this.userBytes = string3.getBytes(charset);
        }

        public byte[] getEncryptedRandomSessionKey() {
            return this.sessionKey;
        }

        public byte[] getExportedSessionKey() {
            return this.exportedSessionKey;
        }

        @Override
        protected void buildMessage() {
            int n = this.ntResp.length;
            int n2 = this.lmResp.length;
            int n3 = this.domainBytes != null ? this.domainBytes.length : 0;
            int n4 = this.hostBytes != null ? this.hostBytes.length : 0;
            int n5 = this.userBytes.length;
            int n6 = this.sessionKey != null ? this.sessionKey.length : 0;
            int n7 = 72 + (this.computeMic ? 16 : 0);
            int n8 = n7 + n2;
            int n9 = n8 + n;
            int n10 = n9 + n3;
            int n11 = n10 + n5;
            int n12 = n11 + n4;
            int n13 = n12 + n6;
            this.prepareResponse(n13, 3);
            this.addUShort(n2);
            this.addUShort(n2);
            this.addULong(n7);
            this.addUShort(n);
            this.addUShort(n);
            this.addULong(n8);
            this.addUShort(n3);
            this.addUShort(n3);
            this.addULong(n9);
            this.addUShort(n5);
            this.addUShort(n5);
            this.addULong(n10);
            this.addUShort(n4);
            this.addUShort(n4);
            this.addULong(n11);
            this.addUShort(n6);
            this.addUShort(n6);
            this.addULong(n12);
            this.addULong(this.type2Flags);
            this.addUShort(261);
            this.addULong(2600);
            this.addUShort(3840);
            int n14 = -1;
            if (this.computeMic) {
                n14 = this.currentOutputPosition;
                this.currentOutputPosition += 16;
            }
            this.addBytes(this.lmResp);
            this.addBytes(this.ntResp);
            this.addBytes(this.domainBytes);
            this.addBytes(this.userBytes);
            this.addBytes(this.hostBytes);
            if (this.sessionKey != null) {
                this.addBytes(this.sessionKey);
            }
            if (this.computeMic) {
                HMACMD5 hMACMD5 = new HMACMD5(this.exportedSessionKey);
                hMACMD5.update(this.type1Message);
                hMACMD5.update(this.type2Message);
                hMACMD5.update(this.messageContents);
                byte[] byArray = hMACMD5.getOutput();
                System.arraycopy(byArray, 0, this.messageContents, n14, byArray.length);
            }
        }

        private byte[] addGssMicAvsToTargetInfo(byte[] byArray, Certificate certificate) throws NTLMEngineException {
            byte[] byArray2;
            byte[] byArray3 = new byte[byArray.length + 8 + 20];
            int n = byArray.length - 4;
            System.arraycopy(byArray, 0, byArray3, 0, n);
            NTLMEngineImpl.writeUShort(byArray3, 6, n);
            NTLMEngineImpl.writeUShort(byArray3, 4, n + 2);
            NTLMEngineImpl.writeULong(byArray3, 2, n + 4);
            NTLMEngineImpl.writeUShort(byArray3, 10, n + 8);
            NTLMEngineImpl.writeUShort(byArray3, 16, n + 10);
            try {
                byte[] byArray4 = certificate.getEncoded();
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                byte[] byArray5 = messageDigest.digest(byArray4);
                byte[] byArray6 = new byte[20 + NTLMEngineImpl.access$2600().length + byArray5.length];
                NTLMEngineImpl.writeULong(byArray6, 53, 16);
                System.arraycopy(NTLMEngineImpl.access$2600(), 0, byArray6, 20, NTLMEngineImpl.access$2600().length);
                System.arraycopy(byArray5, 0, byArray6, 20 + NTLMEngineImpl.access$2600().length, byArray5.length);
                MessageDigest messageDigest2 = NTLMEngineImpl.getMD5();
                byArray2 = messageDigest2.digest(byArray6);
            } catch (CertificateEncodingException certificateEncodingException) {
                throw new NTLMEngineException(certificateEncodingException.getMessage(), certificateEncodingException);
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new NTLMEngineException(noSuchAlgorithmException.getMessage(), noSuchAlgorithmException);
            }
            System.arraycopy(byArray2, 0, byArray3, n + 12, 16);
            return byArray3;
        }
    }

    static class Type2Message
    extends NTLMMessage {
        protected final byte[] challenge = new byte[8];
        protected String target;
        protected byte[] targetInfo;
        protected final int flags;

        Type2Message(String string) throws NTLMEngineException {
            this(Base64.decodeBase64(string.getBytes(NTLMEngineImpl.access$1700())));
        }

        Type2Message(byte[] byArray) throws NTLMEngineException {
            super(byArray, 2);
            byte[] byArray2;
            this.readBytes(this.challenge, 24);
            this.flags = this.readULong(20);
            this.target = null;
            if (this.getMessageLength() >= 20 && (byArray2 = this.readSecurityBuffer(12)).length != 0) {
                this.target = new String(byArray2, NTLMEngineImpl.access$2500(this.flags));
            }
            this.targetInfo = null;
            if (this.getMessageLength() >= 48 && (byArray2 = this.readSecurityBuffer(40)).length != 0) {
                this.targetInfo = byArray2;
            }
        }

        byte[] getChallenge() {
            return this.challenge;
        }

        String getTarget() {
            return this.target;
        }

        byte[] getTargetInfo() {
            return this.targetInfo;
        }

        int getFlags() {
            return this.flags;
        }
    }

    static class Type1Message
    extends NTLMMessage {
        private final byte[] hostBytes;
        private final byte[] domainBytes;
        private final int flags;

        Type1Message(String string, String string2) throws NTLMEngineException {
            this(string, string2, null);
        }

        Type1Message(String string, String string2, Integer n) throws NTLMEngineException {
            this.flags = n == null ? this.getDefaultFlags() : n.intValue();
            String string3 = NTLMEngineImpl.access$2200(string2);
            String string4 = NTLMEngineImpl.access$2300(string);
            this.hostBytes = string3 != null ? string3.getBytes(NTLMEngineImpl.access$2400()) : null;
            this.domainBytes = string4 != null ? string4.toUpperCase(Locale.ROOT).getBytes(NTLMEngineImpl.access$2400()) : null;
        }

        Type1Message() {
            this.hostBytes = null;
            this.domainBytes = null;
            this.flags = this.getDefaultFlags();
        }

        private int getDefaultFlags() {
            return 1;
        }

        @Override
        protected void buildMessage() {
            int n = 0;
            if (this.domainBytes != null) {
                n = this.domainBytes.length;
            }
            int n2 = 0;
            if (this.hostBytes != null) {
                n2 = this.hostBytes.length;
            }
            int n3 = 40 + n2 + n;
            this.prepareResponse(n3, 1);
            this.addULong(this.flags);
            this.addUShort(n);
            this.addUShort(n);
            this.addULong(n2 + 32 + 8);
            this.addUShort(n2);
            this.addUShort(n2);
            this.addULong(40);
            this.addUShort(261);
            this.addULong(2600);
            this.addUShort(3840);
            if (this.hostBytes != null) {
                this.addBytes(this.hostBytes);
            }
            if (this.domainBytes != null) {
                this.addBytes(this.domainBytes);
            }
        }
    }

    static class NTLMMessage {
        protected byte[] messageContents = null;
        protected int currentOutputPosition = 0;

        NTLMMessage() {
        }

        NTLMMessage(String string, int n) throws NTLMEngineException {
            this(Base64.decodeBase64(string.getBytes(NTLMEngineImpl.access$1700())), n);
        }

        NTLMMessage(byte[] byArray, int n) throws NTLMEngineException {
            this.messageContents = byArray;
            if (this.messageContents.length < NTLMEngineImpl.access$1800().length) {
                throw new NTLMEngineException("NTLM message decoding error - packet too short");
            }
            for (int i = 0; i < NTLMEngineImpl.access$1800().length; ++i) {
                if (this.messageContents[i] == NTLMEngineImpl.access$1800()[i]) continue;
                throw new NTLMEngineException("NTLM message expected - instead got unrecognized bytes");
            }
            int n2 = this.readULong(NTLMEngineImpl.access$1800().length);
            if (n2 != n) {
                throw new NTLMEngineException("NTLM type " + Integer.toString(n) + " message expected - instead got type " + Integer.toString(n2));
            }
            this.currentOutputPosition = this.messageContents.length;
        }

        protected int getPreambleLength() {
            return NTLMEngineImpl.access$1800().length + 4;
        }

        protected int getMessageLength() {
            return this.currentOutputPosition;
        }

        protected byte readByte(int n) throws NTLMEngineException {
            if (this.messageContents.length < n + 1) {
                throw new NTLMEngineException("NTLM: Message too short");
            }
            return this.messageContents[n];
        }

        protected void readBytes(byte[] byArray, int n) throws NTLMEngineException {
            if (this.messageContents.length < n + byArray.length) {
                throw new NTLMEngineException("NTLM: Message too short");
            }
            System.arraycopy(this.messageContents, n, byArray, 0, byArray.length);
        }

        protected int readUShort(int n) throws NTLMEngineException {
            return NTLMEngineImpl.access$1900(this.messageContents, n);
        }

        protected int readULong(int n) throws NTLMEngineException {
            return NTLMEngineImpl.access$2000(this.messageContents, n);
        }

        protected byte[] readSecurityBuffer(int n) throws NTLMEngineException {
            return NTLMEngineImpl.access$2100(this.messageContents, n);
        }

        protected void prepareResponse(int n, int n2) {
            this.messageContents = new byte[n];
            this.currentOutputPosition = 0;
            this.addBytes(NTLMEngineImpl.access$1800());
            this.addULong(n2);
        }

        protected void addByte(byte by) {
            this.messageContents[this.currentOutputPosition] = by;
            ++this.currentOutputPosition;
        }

        protected void addBytes(byte[] byArray) {
            if (byArray == null) {
                return;
            }
            byte[] byArray2 = byArray;
            int n = byArray2.length;
            for (int i = 0; i < n; ++i) {
                byte by;
                this.messageContents[this.currentOutputPosition] = by = byArray2[i];
                ++this.currentOutputPosition;
            }
        }

        protected void addUShort(int n) {
            this.addByte((byte)(n & 0xFF));
            this.addByte((byte)(n >> 8 & 0xFF));
        }

        protected void addULong(int n) {
            this.addByte((byte)(n & 0xFF));
            this.addByte((byte)(n >> 8 & 0xFF));
            this.addByte((byte)(n >> 16 & 0xFF));
            this.addByte((byte)(n >> 24 & 0xFF));
        }

        public String getResponse() {
            return new String(Base64.encodeBase64(this.getBytes()), Consts.ASCII);
        }

        public byte[] getBytes() {
            if (this.messageContents == null) {
                this.buildMessage();
            }
            if (this.messageContents.length > this.currentOutputPosition) {
                byte[] byArray = new byte[this.currentOutputPosition];
                System.arraycopy(this.messageContents, 0, byArray, 0, this.currentOutputPosition);
                this.messageContents = byArray;
            }
            return this.messageContents;
        }

        protected void buildMessage() {
            throw new RuntimeException("Message builder not implemented for " + this.getClass().getName());
        }
    }

    static class Handle {
        private final byte[] exportedSessionKey;
        private byte[] signingKey;
        private byte[] sealingKey;
        private final Cipher rc4;
        final Mode mode;
        private final boolean isConnection;
        int sequenceNumber = 0;

        Handle(byte[] byArray, Mode mode, boolean bl) throws NTLMEngineException {
            this.exportedSessionKey = byArray;
            this.isConnection = bl;
            this.mode = mode;
            try {
                MessageDigest messageDigest = NTLMEngineImpl.getMD5();
                MessageDigest messageDigest2 = NTLMEngineImpl.getMD5();
                messageDigest.update(byArray);
                messageDigest2.update(byArray);
                if (mode == Mode.CLIENT) {
                    messageDigest.update(NTLMEngineImpl.access$1100());
                    messageDigest2.update(NTLMEngineImpl.access$1200());
                } else {
                    messageDigest.update(NTLMEngineImpl.access$1300());
                    messageDigest2.update(NTLMEngineImpl.access$1400());
                }
                this.signingKey = messageDigest.digest();
                this.sealingKey = messageDigest2.digest();
            } catch (Exception exception) {
                throw new NTLMEngineException(exception.getMessage(), exception);
            }
            this.rc4 = this.initCipher();
        }

        public byte[] getSigningKey() {
            return this.signingKey;
        }

        public byte[] getSealingKey() {
            return this.sealingKey;
        }

        private Cipher initCipher() throws NTLMEngineException {
            Cipher cipher;
            try {
                cipher = Cipher.getInstance("RC4");
                if (this.mode == Mode.CLIENT) {
                    cipher.init(1, new SecretKeySpec(this.sealingKey, "RC4"));
                } else {
                    cipher.init(2, new SecretKeySpec(this.sealingKey, "RC4"));
                }
            } catch (Exception exception) {
                throw new NTLMEngineException(exception.getMessage(), exception);
            }
            return cipher;
        }

        private void advanceMessageSequence() throws NTLMEngineException {
            if (!this.isConnection) {
                MessageDigest messageDigest = NTLMEngineImpl.getMD5();
                messageDigest.update(this.sealingKey);
                byte[] byArray = new byte[4];
                NTLMEngineImpl.writeULong(byArray, this.sequenceNumber, 0);
                messageDigest.update(byArray);
                this.sealingKey = messageDigest.digest();
                this.initCipher();
            }
            ++this.sequenceNumber;
        }

        private byte[] encrypt(byte[] byArray) {
            return this.rc4.update(byArray);
        }

        private byte[] decrypt(byte[] byArray) {
            return this.rc4.update(byArray);
        }

        private byte[] computeSignature(byte[] byArray) {
            byte[] byArray2 = new byte[16];
            byArray2[0] = 1;
            byArray2[1] = 0;
            byArray2[2] = 0;
            byArray2[3] = 0;
            HMACMD5 hMACMD5 = new HMACMD5(this.signingKey);
            hMACMD5.update(NTLMEngineImpl.access$1500(this.sequenceNumber));
            hMACMD5.update(byArray);
            byte[] byArray3 = hMACMD5.getOutput();
            byte[] byArray4 = new byte[8];
            System.arraycopy(byArray3, 0, byArray4, 0, 8);
            byte[] byArray5 = this.encrypt(byArray4);
            System.arraycopy(byArray5, 0, byArray2, 4, 8);
            NTLMEngineImpl.access$1600(byArray2, 12, this.sequenceNumber);
            return byArray2;
        }

        private boolean validateSignature(byte[] byArray, byte[] byArray2) {
            byte[] byArray3 = this.computeSignature(byArray2);
            return Arrays.equals(byArray, byArray3);
        }

        public byte[] signAndEncryptMessage(byte[] byArray) throws NTLMEngineException {
            byte[] byArray2 = this.encrypt(byArray);
            byte[] byArray3 = this.computeSignature(byArray);
            byte[] byArray4 = new byte[byArray3.length + byArray2.length];
            System.arraycopy(byArray3, 0, byArray4, 0, byArray3.length);
            System.arraycopy(byArray2, 0, byArray4, byArray3.length, byArray2.length);
            this.advanceMessageSequence();
            return byArray4;
        }

        public byte[] decryptAndVerifySignedMessage(byte[] byArray) throws NTLMEngineException {
            byte[] byArray2 = new byte[16];
            System.arraycopy(byArray, 0, byArray2, 0, byArray2.length);
            byte[] byArray3 = new byte[byArray.length - 16];
            System.arraycopy(byArray, 16, byArray3, 0, byArray3.length);
            byte[] byArray4 = this.decrypt(byArray3);
            if (!this.validateSignature(byArray2, byArray4)) {
                throw new NTLMEngineException("Wrong signature");
            }
            this.advanceMessageSequence();
            return byArray4;
        }
    }

    static enum Mode {
        CLIENT,
        SERVER;

    }

    protected static class CipherGen {
        protected final Random random;
        protected final long currentTime;
        protected final String domain;
        protected final String user;
        protected final String password;
        protected final byte[] challenge;
        protected final String target;
        protected final byte[] targetInformation;
        protected byte[] clientChallenge;
        protected byte[] clientChallenge2;
        protected byte[] secondaryKey;
        protected byte[] timestamp;
        protected byte[] lmHash = null;
        protected byte[] lmResponse = null;
        protected byte[] ntlmHash = null;
        protected byte[] ntlmResponse = null;
        protected byte[] ntlmv2Hash = null;
        protected byte[] lmv2Hash = null;
        protected byte[] lmv2Response = null;
        protected byte[] ntlmv2Blob = null;
        protected byte[] ntlmv2Response = null;
        protected byte[] ntlm2SessionResponse = null;
        protected byte[] lm2SessionResponse = null;
        protected byte[] lmUserSessionKey = null;
        protected byte[] ntlmUserSessionKey = null;
        protected byte[] ntlmv2UserSessionKey = null;
        protected byte[] ntlm2SessionResponseUserSessionKey = null;
        protected byte[] lanManagerSessionKey = null;

        @Deprecated
        public CipherGen(String string, String string2, String string3, byte[] byArray, String string4, byte[] byArray2, byte[] byArray3, byte[] byArray4, byte[] byArray5, byte[] byArray6) {
            this(NTLMEngineImpl.access$000(), System.currentTimeMillis(), string, string2, string3, byArray, string4, byArray2, byArray3, byArray4, byArray5, byArray6);
        }

        public CipherGen(Random random2, long l, String string, String string2, String string3, byte[] byArray, String string4, byte[] byArray2, byte[] byArray3, byte[] byArray4, byte[] byArray5, byte[] byArray6) {
            this.random = random2;
            this.currentTime = l;
            this.domain = string;
            this.target = string4;
            this.user = string2;
            this.password = string3;
            this.challenge = byArray;
            this.targetInformation = byArray2;
            this.clientChallenge = byArray3;
            this.clientChallenge2 = byArray4;
            this.secondaryKey = byArray5;
            this.timestamp = byArray6;
        }

        @Deprecated
        public CipherGen(String string, String string2, String string3, byte[] byArray, String string4, byte[] byArray2) {
            this(NTLMEngineImpl.access$000(), System.currentTimeMillis(), string, string2, string3, byArray, string4, byArray2);
        }

        public CipherGen(Random random2, long l, String string, String string2, String string3, byte[] byArray, String string4, byte[] byArray2) {
            this(random2, l, string, string2, string3, byArray, string4, byArray2, null, null, null, null);
        }

        public byte[] getClientChallenge() throws NTLMEngineException {
            if (this.clientChallenge == null) {
                this.clientChallenge = NTLMEngineImpl.access$100(this.random);
            }
            return this.clientChallenge;
        }

        public byte[] getClientChallenge2() throws NTLMEngineException {
            if (this.clientChallenge2 == null) {
                this.clientChallenge2 = NTLMEngineImpl.access$100(this.random);
            }
            return this.clientChallenge2;
        }

        public byte[] getSecondaryKey() throws NTLMEngineException {
            if (this.secondaryKey == null) {
                this.secondaryKey = NTLMEngineImpl.access$200(this.random);
            }
            return this.secondaryKey;
        }

        public byte[] getLMHash() throws NTLMEngineException {
            if (this.lmHash == null) {
                this.lmHash = NTLMEngineImpl.access$300(this.password);
            }
            return this.lmHash;
        }

        public byte[] getLMResponse() throws NTLMEngineException {
            if (this.lmResponse == null) {
                this.lmResponse = NTLMEngineImpl.access$400(this.getLMHash(), this.challenge);
            }
            return this.lmResponse;
        }

        public byte[] getNTLMHash() throws NTLMEngineException {
            if (this.ntlmHash == null) {
                this.ntlmHash = NTLMEngineImpl.access$500(this.password);
            }
            return this.ntlmHash;
        }

        public byte[] getNTLMResponse() throws NTLMEngineException {
            if (this.ntlmResponse == null) {
                this.ntlmResponse = NTLMEngineImpl.access$400(this.getNTLMHash(), this.challenge);
            }
            return this.ntlmResponse;
        }

        public byte[] getLMv2Hash() throws NTLMEngineException {
            if (this.lmv2Hash == null) {
                this.lmv2Hash = NTLMEngineImpl.access$600(this.domain, this.user, this.getNTLMHash());
            }
            return this.lmv2Hash;
        }

        public byte[] getNTLMv2Hash() throws NTLMEngineException {
            if (this.ntlmv2Hash == null) {
                this.ntlmv2Hash = NTLMEngineImpl.access$700(this.domain, this.user, this.getNTLMHash());
            }
            return this.ntlmv2Hash;
        }

        public byte[] getTimestamp() {
            if (this.timestamp == null) {
                long l = this.currentTime;
                l += 11644473600000L;
                l *= 10000L;
                this.timestamp = new byte[8];
                for (int i = 0; i < 8; ++i) {
                    this.timestamp[i] = (byte)l;
                    l >>>= 8;
                }
            }
            return this.timestamp;
        }

        public byte[] getNTLMv2Blob() throws NTLMEngineException {
            if (this.ntlmv2Blob == null) {
                this.ntlmv2Blob = NTLMEngineImpl.access$800(this.getClientChallenge2(), this.targetInformation, this.getTimestamp());
            }
            return this.ntlmv2Blob;
        }

        public byte[] getNTLMv2Response() throws NTLMEngineException {
            if (this.ntlmv2Response == null) {
                this.ntlmv2Response = NTLMEngineImpl.access$900(this.getNTLMv2Hash(), this.challenge, this.getNTLMv2Blob());
            }
            return this.ntlmv2Response;
        }

        public byte[] getLMv2Response() throws NTLMEngineException {
            if (this.lmv2Response == null) {
                this.lmv2Response = NTLMEngineImpl.access$900(this.getLMv2Hash(), this.challenge, this.getClientChallenge());
            }
            return this.lmv2Response;
        }

        public byte[] getNTLM2SessionResponse() throws NTLMEngineException {
            if (this.ntlm2SessionResponse == null) {
                this.ntlm2SessionResponse = NTLMEngineImpl.ntlm2SessionResponse(this.getNTLMHash(), this.challenge, this.getClientChallenge());
            }
            return this.ntlm2SessionResponse;
        }

        public byte[] getLM2SessionResponse() throws NTLMEngineException {
            if (this.lm2SessionResponse == null) {
                byte[] byArray = this.getClientChallenge();
                this.lm2SessionResponse = new byte[24];
                System.arraycopy(byArray, 0, this.lm2SessionResponse, 0, byArray.length);
                Arrays.fill(this.lm2SessionResponse, byArray.length, this.lm2SessionResponse.length, (byte)0);
            }
            return this.lm2SessionResponse;
        }

        public byte[] getLMUserSessionKey() throws NTLMEngineException {
            if (this.lmUserSessionKey == null) {
                this.lmUserSessionKey = new byte[16];
                System.arraycopy(this.getLMHash(), 0, this.lmUserSessionKey, 0, 8);
                Arrays.fill(this.lmUserSessionKey, 8, 16, (byte)0);
            }
            return this.lmUserSessionKey;
        }

        public byte[] getNTLMUserSessionKey() throws NTLMEngineException {
            if (this.ntlmUserSessionKey == null) {
                MD4 mD4 = new MD4();
                mD4.update(this.getNTLMHash());
                this.ntlmUserSessionKey = mD4.getOutput();
            }
            return this.ntlmUserSessionKey;
        }

        public byte[] getNTLMv2UserSessionKey() throws NTLMEngineException {
            if (this.ntlmv2UserSessionKey == null) {
                byte[] byArray = this.getNTLMv2Hash();
                byte[] byArray2 = new byte[16];
                System.arraycopy(this.getNTLMv2Response(), 0, byArray2, 0, 16);
                this.ntlmv2UserSessionKey = NTLMEngineImpl.hmacMD5(byArray2, byArray);
            }
            return this.ntlmv2UserSessionKey;
        }

        public byte[] getNTLM2SessionResponseUserSessionKey() throws NTLMEngineException {
            if (this.ntlm2SessionResponseUserSessionKey == null) {
                byte[] byArray = this.getLM2SessionResponse();
                byte[] byArray2 = new byte[this.challenge.length + byArray.length];
                System.arraycopy(this.challenge, 0, byArray2, 0, this.challenge.length);
                System.arraycopy(byArray, 0, byArray2, this.challenge.length, byArray.length);
                this.ntlm2SessionResponseUserSessionKey = NTLMEngineImpl.hmacMD5(byArray2, this.getNTLMUserSessionKey());
            }
            return this.ntlm2SessionResponseUserSessionKey;
        }

        public byte[] getLanManagerSessionKey() throws NTLMEngineException {
            if (this.lanManagerSessionKey == null) {
                try {
                    byte[] byArray = new byte[14];
                    System.arraycopy(this.getLMHash(), 0, byArray, 0, 8);
                    Arrays.fill(byArray, 8, byArray.length, (byte)-67);
                    Key key = NTLMEngineImpl.access$1000(byArray, 0);
                    Key key2 = NTLMEngineImpl.access$1000(byArray, 7);
                    byte[] byArray2 = new byte[8];
                    System.arraycopy(this.getLMResponse(), 0, byArray2, 0, byArray2.length);
                    Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
                    cipher.init(1, key);
                    byte[] byArray3 = cipher.doFinal(byArray2);
                    cipher = Cipher.getInstance("DES/ECB/NoPadding");
                    cipher.init(1, key2);
                    byte[] byArray4 = cipher.doFinal(byArray2);
                    this.lanManagerSessionKey = new byte[16];
                    System.arraycopy(byArray3, 0, this.lanManagerSessionKey, 0, byArray3.length);
                    System.arraycopy(byArray4, 0, this.lanManagerSessionKey, byArray3.length, byArray4.length);
                } catch (Exception exception) {
                    throw new NTLMEngineException(exception.getMessage(), exception);
                }
            }
            return this.lanManagerSessionKey;
        }
    }
}

