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

public class Md5Crypt {
    static final String APR1_PREFIX = "$apr1$";
    private static final int BLOCKSIZE = 16;
    static final String MD5_PREFIX = "$1$";
    private static final int ROUNDS = 1000;

    public static String apr1Crypt(byte[] byArray) {
        return Md5Crypt.apr1Crypt(byArray, APR1_PREFIX + B64.getRandomSalt(8));
    }

    public static String apr1Crypt(byte[] byArray, String string) {
        if (string != null && !string.startsWith(APR1_PREFIX)) {
            string = APR1_PREFIX + string;
        }
        return Md5Crypt.md5Crypt(byArray, string, APR1_PREFIX);
    }

    public static String apr1Crypt(String string) {
        return Md5Crypt.apr1Crypt(string.getBytes(Charsets.UTF_8));
    }

    public static String apr1Crypt(String string, String string2) {
        return Md5Crypt.apr1Crypt(string.getBytes(Charsets.UTF_8), string2);
    }

    public static String md5Crypt(byte[] byArray) {
        return Md5Crypt.md5Crypt(byArray, MD5_PREFIX + B64.getRandomSalt(8));
    }

    public static String md5Crypt(byte[] byArray, String string) {
        return Md5Crypt.md5Crypt(byArray, string, MD5_PREFIX);
    }

    public static String md5Crypt(byte[] byArray, String string, String string2) {
        int n;
        Object object;
        Object object2;
        String string3;
        int n2 = byArray.length;
        if (string == null) {
            string3 = B64.getRandomSalt(8);
        } else {
            object2 = Pattern.compile("^" + string2.replace("$", "\\$") + "([\\.\\/a-zA-Z0-9]{1,8}).*");
            object = ((Pattern)object2).matcher(string);
            if (!((Matcher)object).find()) {
                throw new IllegalArgumentException("Invalid salt value: " + string);
            }
            string3 = ((Matcher)object).group(1);
        }
        object2 = string3.getBytes(Charsets.UTF_8);
        object = DigestUtils.getMd5Digest();
        ((MessageDigest)object).update(byArray);
        ((MessageDigest)object).update(string2.getBytes(Charsets.UTF_8));
        ((MessageDigest)object).update((byte[])object2);
        MessageDigest messageDigest = DigestUtils.getMd5Digest();
        messageDigest.update(byArray);
        messageDigest.update((byte[])object2);
        messageDigest.update(byArray);
        byte[] byArray2 = messageDigest.digest();
        for (n = n2; n > 0; n -= 16) {
            ((MessageDigest)object).update(byArray2, 0, n > 16 ? 16 : n);
        }
        Arrays.fill(byArray2, (byte)0);
        boolean bl = false;
        for (n = n2; n > 0; n >>= 1) {
            if ((n & 1) == 1) {
                ((MessageDigest)object).update(byArray2[0]);
                continue;
            }
            ((MessageDigest)object).update(byArray[0]);
        }
        StringBuilder stringBuilder = new StringBuilder(string2 + string3 + "$");
        byArray2 = ((MessageDigest)object).digest();
        for (int i = 0; i < 1000; ++i) {
            messageDigest = DigestUtils.getMd5Digest();
            if ((i & 1) != 0) {
                messageDigest.update(byArray);
            } else {
                messageDigest.update(byArray2, 0, 16);
            }
            if (i % 3 != 0) {
                messageDigest.update((byte[])object2);
            }
            if (i % 7 != 0) {
                messageDigest.update(byArray);
            }
            if ((i & 1) != 0) {
                messageDigest.update(byArray2, 0, 16);
            } else {
                messageDigest.update(byArray);
            }
            byArray2 = messageDigest.digest();
        }
        B64.b64from24bit(byArray2[0], byArray2[6], byArray2[12], 4, stringBuilder);
        B64.b64from24bit(byArray2[1], byArray2[7], byArray2[13], 4, stringBuilder);
        B64.b64from24bit(byArray2[2], byArray2[8], byArray2[14], 4, stringBuilder);
        B64.b64from24bit(byArray2[3], byArray2[9], byArray2[15], 4, stringBuilder);
        B64.b64from24bit(byArray2[4], byArray2[10], byArray2[5], 4, stringBuilder);
        B64.b64from24bit((byte)0, (byte)0, byArray2[11], 2, stringBuilder);
        ((MessageDigest)object).reset();
        messageDigest.reset();
        Arrays.fill(byArray, (byte)0);
        Arrays.fill((byte[])object2, (byte)0);
        Arrays.fill(byArray2, (byte)0);
        return stringBuilder.toString();
    }
}

