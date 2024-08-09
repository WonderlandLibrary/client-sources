/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.util.AsciiString;
import io.netty.util.internal.ConstantTimeUtils;
import io.netty.util.internal.PlatformDependent;

final class HpackUtil {
    static final int[] HUFFMAN_CODES = new int[]{8184, 8388568, 0xFFFFFE2, 0xFFFFFE3, 0xFFFFFE4, 0xFFFFFE5, 0xFFFFFE6, 0xFFFFFE7, 0xFFFFFE8, 0xFFFFEA, 0x3FFFFFFC, 0xFFFFFE9, 0xFFFFFEA, 0x3FFFFFFD, 0xFFFFFEB, 0xFFFFFEC, 0xFFFFFED, 0xFFFFFEE, 0xFFFFFEF, 0xFFFFFF0, 0xFFFFFF1, 0xFFFFFF2, 0x3FFFFFFE, 0xFFFFFF3, 0xFFFFFF4, 0xFFFFFF5, 0xFFFFFF6, 0xFFFFFF7, 0xFFFFFF8, 0xFFFFFF9, 0xFFFFFFA, 0xFFFFFFB, 20, 1016, 1017, 4090, 8185, 21, 248, 2042, 1018, 1019, 249, 2043, 250, 22, 23, 24, 0, 1, 2, 25, 26, 27, 28, 29, 30, 31, 92, 251, 32764, 32, 4091, 1020, 8186, 33, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 252, 115, 253, 8187, 524272, 8188, 16380, 34, 32765, 3, 35, 4, 36, 5, 37, 38, 39, 6, 116, 117, 40, 41, 42, 7, 43, 118, 44, 8, 9, 45, 119, 120, 121, 122, 123, 32766, 2044, 16381, 8189, 0xFFFFFFC, 1048550, 4194258, 1048551, 1048552, 0x3FFFD3, 4194260, 4194261, 8388569, 4194262, 8388570, 8388571, 8388572, 0x7FFFDD, 8388574, 0xFFFFEB, 0x7FFFDF, 0xFFFFEC, 0xFFFFED, 4194263, 8388576, 0xFFFFEE, 8388577, 8388578, 8388579, 8388580, 2097116, 4194264, 8388581, 4194265, 8388582, 0x7FFFE7, 0xFFFFEF, 4194266, 0x1FFFDD, 1048553, 4194267, 4194268, 8388584, 8388585, 2097118, 8388586, 0x3FFFDD, 4194270, 0xFFFFF0, 0x1FFFDF, 0x3FFFDF, 8388587, 8388588, 2097120, 0x1FFFE1, 4194272, 2097122, 8388589, 4194273, 0x7FFFEE, 0x7FFFEF, 1048554, 4194274, 0x3FFFE3, 4194276, 0x7FFFF0, 4194277, 4194278, 0x7FFFF1, 67108832, 67108833, 1048555, 524273, 4194279, 0x7FFFF2, 4194280, 33554412, 67108834, 0x3FFFFE3, 67108836, 134217694, 0x7FFFFDF, 67108837, 0xFFFFF1, 33554413, 524274, 2097123, 67108838, 134217696, 134217697, 67108839, 134217698, 0xFFFFF2, 2097124, 2097125, 67108840, 67108841, 0xFFFFFFD, 134217699, 134217700, 134217701, 1048556, 0xFFFFF3, 1048557, 2097126, 4194281, 2097127, 2097128, 0x7FFFF3, 4194282, 4194283, 0x1FFFFEE, 0x1FFFFEF, 0xFFFFF4, 0xFFFFF5, 67108842, 0x7FFFF4, 67108843, 134217702, 67108844, 67108845, 0x7FFFFE7, 134217704, 134217705, 134217706, 134217707, 0xFFFFFFE, 134217708, 134217709, 0x7FFFFEE, 0x7FFFFEF, 0x7FFFFF0, 0x3FFFFEE, 0x3FFFFFFF};
    static final byte[] HUFFMAN_CODE_LENGTHS = new byte[]{13, 23, 28, 28, 28, 28, 28, 28, 28, 24, 30, 28, 28, 30, 28, 28, 28, 28, 28, 28, 28, 28, 30, 28, 28, 28, 28, 28, 28, 28, 28, 28, 6, 10, 10, 12, 13, 6, 8, 11, 10, 10, 8, 11, 8, 6, 6, 6, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 8, 15, 6, 12, 10, 13, 6, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 8, 7, 8, 13, 19, 13, 14, 6, 15, 5, 6, 5, 6, 5, 6, 6, 6, 5, 7, 7, 6, 6, 6, 5, 6, 7, 6, 5, 5, 6, 7, 7, 7, 7, 7, 15, 11, 14, 13, 28, 20, 22, 20, 20, 22, 22, 22, 23, 22, 23, 23, 23, 23, 23, 24, 23, 24, 24, 22, 23, 24, 23, 23, 23, 23, 21, 22, 23, 22, 23, 23, 24, 22, 21, 20, 22, 22, 23, 23, 21, 23, 22, 22, 24, 21, 22, 23, 23, 21, 21, 22, 21, 23, 22, 23, 23, 20, 22, 22, 22, 23, 22, 22, 23, 26, 26, 20, 19, 22, 23, 22, 25, 26, 26, 26, 27, 27, 26, 24, 25, 19, 21, 26, 27, 27, 26, 27, 24, 21, 21, 26, 26, 28, 27, 27, 27, 20, 24, 20, 21, 22, 21, 21, 23, 22, 22, 25, 25, 24, 24, 26, 23, 26, 27, 26, 26, 27, 27, 27, 27, 27, 28, 27, 27, 27, 27, 27, 26, 30};
    static final int HUFFMAN_EOS = 256;

    static int equalsConstantTime(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence instanceof AsciiString && charSequence2 instanceof AsciiString) {
            if (charSequence.length() != charSequence2.length()) {
                return 1;
            }
            AsciiString asciiString = (AsciiString)charSequence;
            AsciiString asciiString2 = (AsciiString)charSequence2;
            return PlatformDependent.equalsConstantTime(asciiString.array(), asciiString.arrayOffset(), asciiString2.array(), asciiString2.arrayOffset(), charSequence.length());
        }
        return ConstantTimeUtils.equalsConstantTime(charSequence, charSequence2);
    }

    private HpackUtil() {
    }

    static enum IndexType {
        INCREMENTAL,
        NONE,
        NEVER;

    }
}

