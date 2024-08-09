/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import java.util.Random;
import org.apache.commons.lang3.RandomUtils;

public class RandomStringUtils {
    private static final Random RANDOM = new Random();

    public static String random(int n) {
        return RandomStringUtils.random(n, false, false);
    }

    public static String randomAscii(int n) {
        return RandomStringUtils.random(n, 32, 127, false, false);
    }

    public static String randomAscii(int n, int n2) {
        return RandomStringUtils.randomAscii(RandomUtils.nextInt(n, n2));
    }

    public static String randomAlphabetic(int n) {
        return RandomStringUtils.random(n, true, false);
    }

    public static String randomAlphabetic(int n, int n2) {
        return RandomStringUtils.randomAlphabetic(RandomUtils.nextInt(n, n2));
    }

    public static String randomAlphanumeric(int n) {
        return RandomStringUtils.random(n, true, true);
    }

    public static String randomAlphanumeric(int n, int n2) {
        return RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(n, n2));
    }

    public static String randomGraph(int n) {
        return RandomStringUtils.random(n, 33, 126, false, false);
    }

    public static String randomGraph(int n, int n2) {
        return RandomStringUtils.randomGraph(RandomUtils.nextInt(n, n2));
    }

    public static String randomNumeric(int n) {
        return RandomStringUtils.random(n, false, true);
    }

    public static String randomNumeric(int n, int n2) {
        return RandomStringUtils.randomNumeric(RandomUtils.nextInt(n, n2));
    }

    public static String randomPrint(int n) {
        return RandomStringUtils.random(n, 32, 126, false, false);
    }

    public static String randomPrint(int n, int n2) {
        return RandomStringUtils.randomPrint(RandomUtils.nextInt(n, n2));
    }

    public static String random(int n, boolean bl, boolean bl2) {
        return RandomStringUtils.random(n, 0, 0, bl, bl2);
    }

    public static String random(int n, int n2, int n3, boolean bl, boolean bl2) {
        return RandomStringUtils.random(n, n2, n3, bl, bl2, null, RANDOM);
    }

    public static String random(int n, int n2, int n3, boolean bl, boolean bl2, char ... cArray) {
        return RandomStringUtils.random(n, n2, n3, bl, bl2, cArray, RANDOM);
    }

    public static String random(int n, int n2, int n3, boolean bl, boolean bl2, char[] cArray, Random random2) {
        if (n == 0) {
            return "";
        }
        if (n < 0) {
            throw new IllegalArgumentException("Requested random string length " + n + " is less than 0.");
        }
        if (cArray != null && cArray.length == 0) {
            throw new IllegalArgumentException("The chars array must not be empty");
        }
        if (n2 == 0 && n3 == 0) {
            if (cArray != null) {
                n3 = cArray.length;
            } else if (!bl && !bl2) {
                n3 = Integer.MAX_VALUE;
            } else {
                n3 = 123;
                n2 = 32;
            }
        } else if (n3 <= n2) {
            throw new IllegalArgumentException("Parameter end (" + n3 + ") must be greater than start (" + n2 + ")");
        }
        char[] cArray2 = new char[n];
        int n4 = n3 - n2;
        while (n-- != 0) {
            char c = cArray == null ? (char)(random2.nextInt(n4) + n2) : cArray[random2.nextInt(n4) + n2];
            if (bl && Character.isLetter(c) || bl2 && Character.isDigit(c) || !bl && !bl2) {
                if (c >= '\udc00' && c <= '\udfff') {
                    if (n == 0) {
                        ++n;
                        continue;
                    }
                    cArray2[n] = c;
                    cArray2[--n] = (char)(55296 + random2.nextInt(128));
                    continue;
                }
                if (c >= '\ud800' && c <= '\udb7f') {
                    if (n == 0) {
                        ++n;
                        continue;
                    }
                    cArray2[n] = (char)(56320 + random2.nextInt(128));
                    cArray2[--n] = c;
                    continue;
                }
                if (c >= '\udb80' && c <= '\udbff') {
                    ++n;
                    continue;
                }
                cArray2[n] = c;
                continue;
            }
            ++n;
        }
        return new String(cArray2);
    }

    public static String random(int n, String string) {
        if (string == null) {
            return RandomStringUtils.random(n, 0, 0, false, false, null, RANDOM);
        }
        return RandomStringUtils.random(n, string.toCharArray());
    }

    public static String random(int n, char ... cArray) {
        if (cArray == null) {
            return RandomStringUtils.random(n, 0, 0, false, false, null, RANDOM);
        }
        return RandomStringUtils.random(n, 0, cArray.length, false, false, cArray, RANDOM);
    }
}

