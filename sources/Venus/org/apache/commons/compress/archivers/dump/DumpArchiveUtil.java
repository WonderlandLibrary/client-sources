/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.archivers.dump;

import java.io.IOException;
import org.apache.commons.compress.archivers.zip.ZipEncoding;

class DumpArchiveUtil {
    private DumpArchiveUtil() {
    }

    public static int calculateChecksum(byte[] byArray) {
        int n = 0;
        for (int i = 0; i < 256; ++i) {
            n += DumpArchiveUtil.convert32(byArray, 4 * i);
        }
        return 84446 - (n - DumpArchiveUtil.convert32(byArray, 28));
    }

    public static final boolean verify(byte[] byArray) {
        int n = DumpArchiveUtil.convert32(byArray, 24);
        if (n != 60012) {
            return true;
        }
        int n2 = DumpArchiveUtil.convert32(byArray, 28);
        return n2 != DumpArchiveUtil.calculateChecksum(byArray);
    }

    public static final int getIno(byte[] byArray) {
        return DumpArchiveUtil.convert32(byArray, 20);
    }

    public static final long convert64(byte[] byArray, int n) {
        long l = 0L;
        l += (long)byArray[n + 7] << 56;
        l += (long)byArray[n + 6] << 48 & 0xFF000000000000L;
        l += (long)byArray[n + 5] << 40 & 0xFF0000000000L;
        l += (long)byArray[n + 4] << 32 & 0xFF00000000L;
        l += (long)byArray[n + 3] << 24 & 0xFF000000L;
        l += (long)byArray[n + 2] << 16 & 0xFF0000L;
        l += (long)byArray[n + 1] << 8 & 0xFF00L;
        return l += (long)byArray[n] & 0xFFL;
    }

    public static final int convert32(byte[] byArray, int n) {
        int n2 = 0;
        n2 = byArray[n + 3] << 24;
        n2 += byArray[n + 2] << 16 & 0xFF0000;
        n2 += byArray[n + 1] << 8 & 0xFF00;
        return n2 += byArray[n] & 0xFF;
    }

    public static final int convert16(byte[] byArray, int n) {
        int n2 = 0;
        n2 += byArray[n + 1] << 8 & 0xFF00;
        return n2 += byArray[n] & 0xFF;
    }

    static String decode(ZipEncoding zipEncoding, byte[] byArray, int n, int n2) throws IOException {
        byte[] byArray2 = new byte[n2];
        System.arraycopy(byArray, n, byArray2, 0, n2);
        return zipEncoding.decode(byArray2);
    }
}

