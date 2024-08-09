/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.tar;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import org.apache.commons.compress.archivers.zip.ZipEncoding;
import org.apache.commons.compress.archivers.zip.ZipEncodingHelper;

public class TarUtils {
    private static final int BYTE_MASK = 255;
    static final ZipEncoding DEFAULT_ENCODING = ZipEncodingHelper.getZipEncoding(null);
    static final ZipEncoding FALLBACK_ENCODING = new ZipEncoding(){

        public boolean canEncode(String string) {
            return false;
        }

        public ByteBuffer encode(String string) {
            int n = string.length();
            byte[] byArray = new byte[n];
            for (int i = 0; i < n; ++i) {
                byArray[i] = (byte)string.charAt(i);
            }
            return ByteBuffer.wrap(byArray);
        }

        public String decode(byte[] byArray) {
            byte by;
            int n = byArray.length;
            StringBuilder stringBuilder = new StringBuilder(n);
            for (int i = 0; i < n && (by = byArray[i]) != 0; ++i) {
                stringBuilder.append((char)(by & 0xFF));
            }
            return stringBuilder.toString();
        }
    };

    private TarUtils() {
    }

    public static long parseOctal(byte[] byArray, int n, int n2) {
        int n3;
        long l = 0L;
        int n4 = n + n2;
        if (n2 < 2) {
            throw new IllegalArgumentException("Length " + n2 + " must be at least 2");
        }
        if (byArray[n3] == 0) {
            return 0L;
        }
        for (n3 = n; n3 < n4 && byArray[n3] == 32; ++n3) {
        }
        byte by = byArray[n4 - 1];
        while (n3 < n4 && (by == 0 || by == 32)) {
            by = byArray[--n4 - 1];
        }
        while (n3 < n4) {
            byte by2 = byArray[n3];
            if (by2 < 48 || by2 > 55) {
                throw new IllegalArgumentException(TarUtils.exceptionMessage(byArray, n, n2, n3, by2));
            }
            l = (l << 3) + (long)(by2 - 48);
            ++n3;
        }
        return l;
    }

    public static long parseOctalOrBinary(byte[] byArray, int n, int n2) {
        boolean bl;
        if ((byArray[n] & 0x80) == 0) {
            return TarUtils.parseOctal(byArray, n, n2);
        }
        boolean bl2 = bl = byArray[n] == -1;
        if (n2 < 9) {
            return TarUtils.parseBinaryLong(byArray, n, n2, bl);
        }
        return TarUtils.parseBinaryBigInteger(byArray, n, n2, bl);
    }

    private static long parseBinaryLong(byte[] byArray, int n, int n2, boolean bl) {
        if (n2 >= 9) {
            throw new IllegalArgumentException("At offset " + n + ", " + n2 + " byte binary number" + " exceeds maximum signed long" + " value");
        }
        long l = 0L;
        for (int i = 1; i < n2; ++i) {
            l = (l << 8) + (long)(byArray[n + i] & 0xFF);
        }
        if (bl) {
            --l;
            l ^= (long)Math.pow(2.0, (n2 - 1) * 8) - 1L;
        }
        return bl ? -l : l;
    }

    private static long parseBinaryBigInteger(byte[] byArray, int n, int n2, boolean bl) {
        byte[] byArray2 = new byte[n2 - 1];
        System.arraycopy(byArray, n + 1, byArray2, 0, n2 - 1);
        BigInteger bigInteger = new BigInteger(byArray2);
        if (bl) {
            bigInteger = bigInteger.add(BigInteger.valueOf(-1L)).not();
        }
        if (bigInteger.bitLength() > 63) {
            throw new IllegalArgumentException("At offset " + n + ", " + n2 + " byte binary number" + " exceeds maximum signed long" + " value");
        }
        return bl ? -bigInteger.longValue() : bigInteger.longValue();
    }

    public static boolean parseBoolean(byte[] byArray, int n) {
        return byArray[n] == 1;
    }

    private static String exceptionMessage(byte[] byArray, int n, int n2, int n3, byte by) {
        String string = new String(byArray, n, n2);
        string = string.replaceAll("\u0000", "{NUL}");
        String string2 = "Invalid byte " + by + " at offset " + (n3 - n) + " in '" + string + "' len=" + n2;
        return string2;
    }

    public static String parseName(byte[] byArray, int n, int n2) {
        try {
            return TarUtils.parseName(byArray, n, n2, DEFAULT_ENCODING);
        } catch (IOException iOException) {
            try {
                return TarUtils.parseName(byArray, n, n2, FALLBACK_ENCODING);
            } catch (IOException iOException2) {
                throw new RuntimeException(iOException2);
            }
        }
    }

    public static String parseName(byte[] byArray, int n, int n2, ZipEncoding zipEncoding) throws IOException {
        int n3;
        for (n3 = n2; n3 > 0 && byArray[n + n3 - 1] == 0; --n3) {
        }
        if (n3 > 0) {
            byte[] byArray2 = new byte[n3];
            System.arraycopy(byArray, n, byArray2, 0, n3);
            return zipEncoding.decode(byArray2);
        }
        return "";
    }

    public static int formatNameBytes(String string, byte[] byArray, int n, int n2) {
        try {
            return TarUtils.formatNameBytes(string, byArray, n, n2, DEFAULT_ENCODING);
        } catch (IOException iOException) {
            try {
                return TarUtils.formatNameBytes(string, byArray, n, n2, FALLBACK_ENCODING);
            } catch (IOException iOException2) {
                throw new RuntimeException(iOException2);
            }
        }
    }

    public static int formatNameBytes(String string, byte[] byArray, int n, int n2, ZipEncoding zipEncoding) throws IOException {
        int n3 = string.length();
        ByteBuffer byteBuffer = zipEncoding.encode(string);
        while (byteBuffer.limit() > n2 && n3 > 0) {
            byteBuffer = zipEncoding.encode(string.substring(0, --n3));
        }
        int n4 = byteBuffer.limit() - byteBuffer.position();
        System.arraycopy(byteBuffer.array(), byteBuffer.arrayOffset(), byArray, n, n4);
        for (int i = n4; i < n2; ++i) {
            byArray[n + i] = 0;
        }
        return n + n2;
    }

    public static void formatUnsignedOctalString(long l, byte[] byArray, int n, int n2) {
        int n3 = n2;
        --n3;
        if (l == 0L) {
            byArray[n + n3--] = 48;
        } else {
            long l2;
            for (l2 = l; n3 >= 0 && l2 != 0L; l2 >>>= 3, --n3) {
                byArray[n + n3] = (byte)(48 + (byte)(l2 & 7L));
            }
            if (l2 != 0L) {
                throw new IllegalArgumentException(l + "=" + Long.toOctalString(l) + " will not fit in octal number buffer of length " + n2);
            }
        }
        while (n3 >= 0) {
            byArray[n + n3] = 48;
            --n3;
        }
    }

    public static int formatOctalBytes(long l, byte[] byArray, int n, int n2) {
        int n3 = n2 - 2;
        TarUtils.formatUnsignedOctalString(l, byArray, n, n3);
        byArray[n + n3++] = 32;
        byArray[n + n3] = 0;
        return n + n2;
    }

    public static int formatLongOctalBytes(long l, byte[] byArray, int n, int n2) {
        int n3 = n2 - 1;
        TarUtils.formatUnsignedOctalString(l, byArray, n, n3);
        byArray[n + n3] = 32;
        return n + n2;
    }

    public static int formatLongOctalOrBinaryBytes(long l, byte[] byArray, int n, int n2) {
        boolean bl;
        long l2 = n2 == 8 ? 0x1FFFFFL : 0x1FFFFFFFFL;
        boolean bl2 = bl = l < 0L;
        if (!bl && l <= l2) {
            return TarUtils.formatLongOctalBytes(l, byArray, n, n2);
        }
        if (n2 < 9) {
            TarUtils.formatLongBinary(l, byArray, n, n2, bl);
        }
        TarUtils.formatBigIntegerBinary(l, byArray, n, n2, bl);
        byArray[n] = (byte)(bl ? 255 : 128);
        return n + n2;
    }

    private static void formatLongBinary(long l, byte[] byArray, int n, int n2, boolean bl) {
        int n3 = (n2 - 1) * 8;
        long l2 = 1L << n3;
        long l3 = Math.abs(l);
        if (l3 >= l2) {
            throw new IllegalArgumentException("Value " + l + " is too large for " + n2 + " byte field.");
        }
        if (bl) {
            l3 ^= l2 - 1L;
            l3 |= (long)(255 << n3);
            ++l3;
        }
        for (int i = n + n2 - 1; i >= n; --i) {
            byArray[i] = (byte)l3;
            l3 >>= 8;
        }
    }

    private static void formatBigIntegerBinary(long l, byte[] byArray, int n, int n2, boolean bl) {
        BigInteger bigInteger = BigInteger.valueOf(l);
        byte[] byArray2 = bigInteger.toByteArray();
        int n3 = byArray2.length;
        int n4 = n + n2 - n3;
        System.arraycopy(byArray2, 0, byArray, n4, n3);
        byte by = (byte)(bl ? 255 : 0);
        for (int i = n + 1; i < n4; ++i) {
            byArray[i] = by;
        }
    }

    public static int formatCheckSumOctalBytes(long l, byte[] byArray, int n, int n2) {
        int n3 = n2 - 2;
        TarUtils.formatUnsignedOctalString(l, byArray, n, n3);
        byArray[n + n3++] = 0;
        byArray[n + n3] = 32;
        return n + n2;
    }

    public static long computeCheckSum(byte[] byArray) {
        long l = 0L;
        for (byte by : byArray) {
            l += (long)(0xFF & by);
        }
        return l;
    }

    public static boolean verifyCheckSum(byte[] byArray) {
        long l = 0L;
        long l2 = 0L;
        long l3 = 0L;
        int n = 0;
        for (int i = 0; i < byArray.length; ++i) {
            int n2 = byArray[i];
            if (148 <= i && i < 156) {
                if (48 <= n2 && n2 <= 55 && n++ < 6) {
                    l = l * 8L + (long)n2 - 48L;
                } else if (n > 0) {
                    n = 6;
                }
                n2 = 32;
            }
            l2 += (long)(0xFF & n2);
            l3 += (long)n2;
        }
        return l == l2 || l == l3 || l > l2;
    }
}

