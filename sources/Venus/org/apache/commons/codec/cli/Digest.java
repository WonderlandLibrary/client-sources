/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.cli;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Locale;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class Digest {
    private final String algorithm;
    private final String[] args;
    private final String[] inputs;

    public static void main(String[] stringArray) throws IOException {
        new Digest(stringArray).run();
    }

    private Digest(String[] stringArray) {
        if (stringArray == null) {
            throw new IllegalArgumentException("args");
        }
        if (stringArray.length == 0) {
            throw new IllegalArgumentException(String.format("Usage: java %s [algorithm] [FILE|DIRECTORY|string] ...", Digest.class.getName()));
        }
        this.args = stringArray;
        this.algorithm = stringArray[0];
        if (stringArray.length <= 1) {
            this.inputs = null;
        } else {
            this.inputs = new String[stringArray.length - 1];
            System.arraycopy(stringArray, 1, this.inputs, 0, this.inputs.length);
        }
    }

    private void println(String string, byte[] byArray) {
        this.println(string, byArray, null);
    }

    private void println(String string, byte[] byArray, String string2) {
        System.out.println(string + Hex.encodeHexString(byArray) + (string2 != null ? "  " + string2 : ""));
    }

    private void run() throws IOException {
        if (this.algorithm.equalsIgnoreCase("ALL") || this.algorithm.equals("*")) {
            this.run(MessageDigestAlgorithms.values());
            return;
        }
        MessageDigest messageDigest = DigestUtils.getDigest(this.algorithm, null);
        if (messageDigest != null) {
            this.run("", messageDigest);
        } else {
            this.run("", DigestUtils.getDigest(this.algorithm.toUpperCase(Locale.ROOT)));
        }
    }

    private void run(String[] stringArray) throws IOException {
        for (String string : stringArray) {
            if (!DigestUtils.isAvailable(string)) continue;
            this.run(string + " ", string);
        }
    }

    private void run(String string, MessageDigest messageDigest) throws IOException {
        if (this.inputs == null) {
            this.println(string, DigestUtils.digest(messageDigest, System.in));
            return;
        }
        for (String string2 : this.inputs) {
            File[] fileArray;
            File file = new File(string2);
            if (file.isFile()) {
                this.println(string, DigestUtils.digest(messageDigest, file), string2);
                continue;
            }
            if (file.isDirectory()) {
                fileArray = file.listFiles();
                if (fileArray == null) continue;
                this.run(string, messageDigest, fileArray);
                continue;
            }
            fileArray = (File[])string2.getBytes(Charset.defaultCharset());
            this.println(string, DigestUtils.digest(messageDigest, (byte[])fileArray));
        }
    }

    private void run(String string, MessageDigest messageDigest, File[] fileArray) throws IOException {
        for (File file : fileArray) {
            if (!file.isFile()) continue;
            this.println(string, DigestUtils.digest(messageDigest, file), file.getName());
        }
    }

    private void run(String string, String string2) throws IOException {
        this.run(string, DigestUtils.getDigest(string2));
    }

    public String toString() {
        return String.format("%s %s", super.toString(), Arrays.toString(this.args));
    }
}

