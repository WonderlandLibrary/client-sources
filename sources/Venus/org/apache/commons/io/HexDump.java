/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class HexDump {
    public static final String EOL = System.getProperty("line.separator");
    private static final char[] _hexcodes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final int[] _shifts = new int[]{28, 24, 20, 16, 12, 8, 4, 0};

    public static void dump(byte[] byArray, long l, OutputStream outputStream, int n) throws IOException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        if (n < 0 || n >= byArray.length) {
            throw new ArrayIndexOutOfBoundsException("illegal index: " + n + " into array of length " + byArray.length);
        }
        if (outputStream == null) {
            throw new IllegalArgumentException("cannot write to nullstream");
        }
        long l2 = l + (long)n;
        StringBuilder stringBuilder = new StringBuilder(74);
        for (int i = n; i < byArray.length; i += 16) {
            int n2;
            int n3 = byArray.length - i;
            if (n3 > 16) {
                n3 = 16;
            }
            HexDump.dump(stringBuilder, l2).append(' ');
            for (n2 = 0; n2 < 16; ++n2) {
                if (n2 < n3) {
                    HexDump.dump(stringBuilder, byArray[n2 + i]);
                } else {
                    stringBuilder.append("  ");
                }
                stringBuilder.append(' ');
            }
            for (n2 = 0; n2 < n3; ++n2) {
                if (byArray[n2 + i] >= 32 && byArray[n2 + i] < 127) {
                    stringBuilder.append((char)byArray[n2 + i]);
                    continue;
                }
                stringBuilder.append('.');
            }
            stringBuilder.append(EOL);
            outputStream.write(stringBuilder.toString().getBytes(Charset.defaultCharset()));
            outputStream.flush();
            stringBuilder.setLength(0);
            l2 += (long)n3;
        }
    }

    private static StringBuilder dump(StringBuilder stringBuilder, long l) {
        for (int i = 0; i < 8; ++i) {
            stringBuilder.append(_hexcodes[(int)(l >> _shifts[i]) & 0xF]);
        }
        return stringBuilder;
    }

    private static StringBuilder dump(StringBuilder stringBuilder, byte by) {
        for (int i = 0; i < 2; ++i) {
            stringBuilder.append(_hexcodes[by >> _shifts[i + 6] & 0xF]);
        }
        return stringBuilder;
    }
}

