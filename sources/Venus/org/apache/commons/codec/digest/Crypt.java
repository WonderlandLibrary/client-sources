/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.digest;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.codec.digest.Sha2Crypt;
import org.apache.commons.codec.digest.UnixCrypt;

public class Crypt {
    public static String crypt(byte[] byArray) {
        return Crypt.crypt(byArray, null);
    }

    public static String crypt(byte[] byArray, String string) {
        if (string == null) {
            return Sha2Crypt.sha512Crypt(byArray);
        }
        if (string.startsWith("$6$")) {
            return Sha2Crypt.sha512Crypt(byArray, string);
        }
        if (string.startsWith("$5$")) {
            return Sha2Crypt.sha256Crypt(byArray, string);
        }
        if (string.startsWith("$1$")) {
            return Md5Crypt.md5Crypt(byArray, string);
        }
        return UnixCrypt.crypt(byArray, string);
    }

    public static String crypt(String string) {
        return Crypt.crypt(string, null);
    }

    public static String crypt(String string, String string2) {
        return Crypt.crypt(string.getBytes(Charsets.UTF_8), string2);
    }
}

