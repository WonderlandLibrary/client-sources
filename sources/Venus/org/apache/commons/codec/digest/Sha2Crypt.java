/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.digest;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.B64;
import org.apache.commons.codec.digest.DigestUtils;

public class Sha2Crypt {
    private static final int ROUNDS_DEFAULT = 5000;
    private static final int ROUNDS_MAX = 999999999;
    private static final int ROUNDS_MIN = 1000;
    private static final String ROUNDS_PREFIX = "rounds=";
    private static final int SHA256_BLOCKSIZE = 32;
    static final String SHA256_PREFIX = "$5$";
    private static final int SHA512_BLOCKSIZE = 64;
    static final String SHA512_PREFIX = "$6$";
    private static final Pattern SALT_PATTERN = Pattern.compile("^\\$([56])\\$(rounds=(\\d+)\\$)?([\\.\\/a-zA-Z0-9]{1,16}).*");

    public static String sha256Crypt(byte[] byArray) {
        return Sha2Crypt.sha256Crypt(byArray, null);
    }

    public static String sha256Crypt(byte[] byArray, String string) {
        if (string == null) {
            string = SHA256_PREFIX + B64.getRandomSalt(8);
        }
        return Sha2Crypt.sha2Crypt(byArray, string, SHA256_PREFIX, 32, "SHA-256");
    }

    private static String sha2Crypt(byte[] byArray, String string, String string2, int n, String string3) {
        int n2;
        int n3;
        int n4 = byArray.length;
        int n5 = 5000;
        boolean bl = false;
        if (string == null) {
            throw new IllegalArgumentException("Salt must not be null");
        }
        Matcher matcher = SALT_PATTERN.matcher(string);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid salt value: " + string);
        }
        if (matcher.group(3) != null) {
            n5 = Integer.parseInt(matcher.group(3));
            n5 = Math.max(1000, Math.min(999999999, n5));
            bl = true;
        }
        String string4 = matcher.group(4);
        byte[] byArray2 = string4.getBytes(Charsets.UTF_8);
        int n6 = byArray2.length;
        MessageDigest messageDigest = DigestUtils.getDigest(string3);
        messageDigest.update(byArray);
        messageDigest.update(byArray2);
        MessageDigest messageDigest2 = DigestUtils.getDigest(string3);
        messageDigest2.update(byArray);
        messageDigest2.update(byArray2);
        messageDigest2.update(byArray);
        byte[] byArray3 = messageDigest2.digest();
        for (n3 = byArray.length; n3 > n; n3 -= n) {
            messageDigest.update(byArray3, 0, n);
        }
        messageDigest.update(byArray3, 0, n3);
        for (n3 = byArray.length; n3 > 0; n3 >>= 1) {
            if ((n3 & 1) != 0) {
                messageDigest.update(byArray3, 0, n);
                continue;
            }
            messageDigest.update(byArray);
        }
        byArray3 = messageDigest.digest();
        messageDigest2 = DigestUtils.getDigest(string3);
        for (int i = 1; i <= n4; ++i) {
            messageDigest2.update(byArray);
        }
        byte[] byArray4 = messageDigest2.digest();
        byte[] byArray5 = new byte[n4];
        for (n2 = 0; n2 < n4 - n; n2 += n) {
            System.arraycopy(byArray4, 0, byArray5, n2, n);
        }
        System.arraycopy(byArray4, 0, byArray5, n2, n4 - n2);
        messageDigest2 = DigestUtils.getDigest(string3);
        for (int i = 1; i <= 16 + (byArray3[0] & 0xFF); ++i) {
            messageDigest2.update(byArray2);
        }
        byArray4 = messageDigest2.digest();
        byte[] byArray6 = new byte[n6];
        for (n2 = 0; n2 < n6 - n; n2 += n) {
            System.arraycopy(byArray4, 0, byArray6, n2, n);
        }
        System.arraycopy(byArray4, 0, byArray6, n2, n6 - n2);
        for (int i = 0; i <= n5 - 1; ++i) {
            messageDigest = DigestUtils.getDigest(string3);
            if ((i & 1) != 0) {
                messageDigest.update(byArray5, 0, n4);
            } else {
                messageDigest.update(byArray3, 0, n);
            }
            if (i % 3 != 0) {
                messageDigest.update(byArray6, 0, n6);
            }
            if (i % 7 != 0) {
                messageDigest.update(byArray5, 0, n4);
            }
            if ((i & 1) != 0) {
                messageDigest.update(byArray3, 0, n);
            } else {
                messageDigest.update(byArray5, 0, n4);
            }
            byArray3 = messageDigest.digest();
        }
        StringBuilder stringBuilder = new StringBuilder(string2);
        if (bl) {
            stringBuilder.append(ROUNDS_PREFIX);
            stringBuilder.append(n5);
            stringBuilder.append("$");
        }
        stringBuilder.append(string4);
        stringBuilder.append("$");
        if (n == 32) {
            B64.b64from24bit(byArray3[0], byArray3[10], byArray3[20], 4, stringBuilder);
            B64.b64from24bit(byArray3[21], byArray3[1], byArray3[11], 4, stringBuilder);
            B64.b64from24bit(byArray3[12], byArray3[22], byArray3[2], 4, stringBuilder);
            B64.b64from24bit(byArray3[3], byArray3[13], byArray3[23], 4, stringBuilder);
            B64.b64from24bit(byArray3[24], byArray3[4], byArray3[14], 4, stringBuilder);
            B64.b64from24bit(byArray3[15], byArray3[25], byArray3[5], 4, stringBuilder);
            B64.b64from24bit(byArray3[6], byArray3[16], byArray3[26], 4, stringBuilder);
            B64.b64from24bit(byArray3[27], byArray3[7], byArray3[17], 4, stringBuilder);
            B64.b64from24bit(byArray3[18], byArray3[28], byArray3[8], 4, stringBuilder);
            B64.b64from24bit(byArray3[9], byArray3[19], byArray3[29], 4, stringBuilder);
            B64.b64from24bit((byte)0, byArray3[31], byArray3[30], 3, stringBuilder);
        } else {
            B64.b64from24bit(byArray3[0], byArray3[21], byArray3[42], 4, stringBuilder);
            B64.b64from24bit(byArray3[22], byArray3[43], byArray3[1], 4, stringBuilder);
            B64.b64from24bit(byArray3[44], byArray3[2], byArray3[23], 4, stringBuilder);
            B64.b64from24bit(byArray3[3], byArray3[24], byArray3[45], 4, stringBuilder);
            B64.b64from24bit(byArray3[25], byArray3[46], byArray3[4], 4, stringBuilder);
            B64.b64from24bit(byArray3[47], byArray3[5], byArray3[26], 4, stringBuilder);
            B64.b64from24bit(byArray3[6], byArray3[27], byArray3[48], 4, stringBuilder);
            B64.b64from24bit(byArray3[28], byArray3[49], byArray3[7], 4, stringBuilder);
            B64.b64from24bit(byArray3[50], byArray3[8], byArray3[29], 4, stringBuilder);
            B64.b64from24bit(byArray3[9], byArray3[30], byArray3[51], 4, stringBuilder);
            B64.b64from24bit(byArray3[31], byArray3[52], byArray3[10], 4, stringBuilder);
            B64.b64from24bit(byArray3[53], byArray3[11], byArray3[32], 4, stringBuilder);
            B64.b64from24bit(byArray3[12], byArray3[33], byArray3[54], 4, stringBuilder);
            B64.b64from24bit(byArray3[34], byArray3[55], byArray3[13], 4, stringBuilder);
            B64.b64from24bit(byArray3[56], byArray3[14], byArray3[35], 4, stringBuilder);
            B64.b64from24bit(byArray3[15], byArray3[36], byArray3[57], 4, stringBuilder);
            B64.b64from24bit(byArray3[37], byArray3[58], byArray3[16], 4, stringBuilder);
            B64.b64from24bit(byArray3[59], byArray3[17], byArray3[38], 4, stringBuilder);
            B64.b64from24bit(byArray3[18], byArray3[39], byArray3[60], 4, stringBuilder);
            B64.b64from24bit(byArray3[40], byArray3[61], byArray3[19], 4, stringBuilder);
            B64.b64from24bit(byArray3[62], byArray3[20], byArray3[41], 4, stringBuilder);
            B64.b64from24bit((byte)0, (byte)0, byArray3[63], 2, stringBuilder);
        }
        Arrays.fill(byArray4, (byte)0);
        Arrays.fill(byArray5, (byte)0);
        Arrays.fill(byArray6, (byte)0);
        messageDigest.reset();
        messageDigest2.reset();
        Arrays.fill(byArray, (byte)0);
        Arrays.fill(byArray2, (byte)0);
        return stringBuilder.toString();
    }

    public static String sha512Crypt(byte[] byArray) {
        return Sha2Crypt.sha512Crypt(byArray, null);
    }

    public static String sha512Crypt(byte[] byArray, String string) {
        if (string == null) {
            string = SHA512_PREFIX + B64.getRandomSalt(8);
        }
        return Sha2Crypt.sha2Crypt(byArray, string, SHA512_PREFIX, 64, "SHA-512");
    }
}

