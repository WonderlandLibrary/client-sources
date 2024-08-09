/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.turikhay.caf.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DualFingerprint {
    private final String sha1;
    private final String sha256;

    public static DualFingerprint compute(byte[] byArray) {
        return new DualFingerprint(DualFingerprint.computeWithAlgo(byArray, "SHA-1"), DualFingerprint.computeWithAlgo(byArray, "SHA-256"));
    }

    public String getSha1() {
        return this.sha1;
    }

    public String getSha256() {
        return this.sha256;
    }

    private DualFingerprint(String string, String string2) {
        this.sha1 = string;
        this.sha256 = string2;
    }

    private static String computeWithAlgo(byte[] byArray, String string) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(string);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new Error("Well-known algorithm is missing (" + string + ")", noSuchAlgorithmException);
        }
        messageDigest.update(byArray);
        return DualFingerprint.toHex(messageDigest.digest());
    }

    private static String toHex(byte[] byArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte by : byArray) {
            String string = Integer.toHexString(0xFF & by);
            if (string.length() == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }
}

