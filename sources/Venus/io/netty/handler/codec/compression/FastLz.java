/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.handler.codec.compression.DecompressionException;

final class FastLz {
    private static final int MAX_DISTANCE = 8191;
    private static final int MAX_FARDISTANCE = 73725;
    private static final int HASH_LOG = 13;
    private static final int HASH_SIZE = 8192;
    private static final int HASH_MASK = 8191;
    private static final int MAX_COPY = 32;
    private static final int MAX_LEN = 264;
    private static final int MIN_RECOMENDED_LENGTH_FOR_LEVEL_2 = 65536;
    static final int MAGIC_NUMBER = 4607066;
    static final byte BLOCK_TYPE_NON_COMPRESSED = 0;
    static final byte BLOCK_TYPE_COMPRESSED = 1;
    static final byte BLOCK_WITHOUT_CHECKSUM = 0;
    static final byte BLOCK_WITH_CHECKSUM = 16;
    static final int OPTIONS_OFFSET = 3;
    static final int CHECKSUM_OFFSET = 4;
    static final int MAX_CHUNK_LENGTH = 65535;
    static final int MIN_LENGTH_TO_COMPRESSION = 32;
    static final int LEVEL_AUTO = 0;
    static final int LEVEL_1 = 1;
    static final int LEVEL_2 = 2;

    static int calculateOutputBufferLength(int n) {
        int n2 = (int)((double)n * 1.06);
        return Math.max(n2, 66);
    }

    static int compress(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
        int n5;
        int n6 = n4 == 0 ? (n2 < 65536 ? 1 : 2) : n4;
        int n7 = 0;
        int n8 = n7 + n2 - 2;
        int n9 = n7 + n2 - 12;
        int n10 = 0;
        int[] nArray = new int[8192];
        if (n2 < 4) {
            if (n2 != 0) {
                byArray2[n3 + n10++] = (byte)(n2 - 1);
                ++n8;
                while (n7 <= n8) {
                    byArray2[n3 + n10++] = byArray[n + n7++];
                }
                return n2 + 1;
            }
            return 1;
        }
        for (n5 = 0; n5 < 8192; ++n5) {
            nArray[n5] = n7;
        }
        int n11 = 2;
        byArray2[n3 + n10++] = 31;
        byArray2[n3 + n10++] = byArray[n + n7++];
        byArray2[n3 + n10++] = byArray[n + n7++];
        while (n7 < n9) {
            int n12;
            int n13;
            long l;
            int n14;
            block37: {
                int n15;
                block39: {
                    block38: {
                        n14 = 0;
                        l = 0L;
                        n13 = 3;
                        n15 = n7;
                        boolean bl = false;
                        if (n6 == 2 && byArray[n + n7] == byArray[n + n7 - 1] && FastLz.readU16(byArray, n + n7 - 1) == FastLz.readU16(byArray, n + n7 + 1)) {
                            l = 1L;
                            n7 += 3;
                            n14 = n15 + 2;
                            bl = true;
                        }
                        if (bl) break block37;
                        n5 = n12 = FastLz.hashFunction(byArray, n + n7);
                        n14 = nArray[n12];
                        l = n15 - n14;
                        nArray[n5] = n15;
                        if (l == 0L || (n6 != 1 ? l >= 73725L : l >= 8191L)) break block38;
                        if (byArray[n + n14++] == byArray[n + n7++] && byArray[n + n14++] == byArray[n + n7++] && byArray[n + n14++] == byArray[n + n7++]) break block39;
                    }
                    byArray2[n3 + n10++] = byArray[n + n15++];
                    n7 = n15;
                    if (++n11 != 32) continue;
                    n11 = 0;
                    byArray2[n3 + n10++] = 31;
                    continue;
                }
                if (n6 == 2 && l >= 8191L) {
                    if (byArray[n + n7++] != byArray[n + n14++] || byArray[n + n7++] != byArray[n + n14++]) {
                        byArray2[n3 + n10++] = byArray[n + n15++];
                        n7 = n15;
                        if (++n11 != 32) continue;
                        n11 = 0;
                        byArray2[n3 + n10++] = 31;
                        continue;
                    }
                    n13 += 2;
                }
            }
            if (--l == 0L) {
                byte by = byArray[n + n7 - 1];
                for (n7 = n15 + n13; n7 < n8 && byArray[n + n14++] == by; ++n7) {
                }
            } else if (byArray[n + n14++] == byArray[n + n7++] && byArray[n + n14++] == byArray[n + n7++] && byArray[n + n14++] == byArray[n + n7++] && byArray[n + n14++] == byArray[n + n7++] && byArray[n + n14++] == byArray[n + n7++] && byArray[n + n14++] == byArray[n + n7++] && byArray[n + n14++] == byArray[n + n7++] && byArray[n + n14++] == byArray[n + n7++]) {
                while (n7 < n8 && byArray[n + n14++] == byArray[n + n7++]) {
                }
            }
            if (n11 != 0) {
                byArray2[n3 + n10 - n11 - 1] = (byte)(n11 - 1);
            } else {
                --n10;
            }
            n11 = 0;
            if (n6 == 2) {
                if (l < 8191L) {
                    if (n13 < 7) {
                        byArray2[n3 + n10++] = (byte)((long)(n13 << 5) + (l >>> 8));
                        byArray2[n3 + n10++] = (byte)(l & 0xFFL);
                    } else {
                        byArray2[n3 + n10++] = (byte)(224L + (l >>> 8));
                        n13 -= 7;
                        while (n13 >= 255) {
                            byArray2[n3 + n10++] = -1;
                            n13 -= 255;
                        }
                        byArray2[n3 + n10++] = (byte)n13;
                        byArray2[n3 + n10++] = (byte)(l & 0xFFL);
                    }
                } else if (n13 < 7) {
                    byArray2[n3 + n10++] = (byte)((n13 << 5) + 31);
                    byArray2[n3 + n10++] = -1;
                    byArray2[n3 + n10++] = (byte)((l -= 8191L) >>> 8);
                    byArray2[n3 + n10++] = (byte)(l & 0xFFL);
                } else {
                    l -= 8191L;
                    byArray2[n3 + n10++] = -1;
                    n13 -= 7;
                    while (n13 >= 255) {
                        byArray2[n3 + n10++] = -1;
                        n13 -= 255;
                    }
                    byArray2[n3 + n10++] = (byte)n13;
                    byArray2[n3 + n10++] = -1;
                    byArray2[n3 + n10++] = (byte)(l >>> 8);
                    byArray2[n3 + n10++] = (byte)(l & 0xFFL);
                }
            } else {
                if (n13 > 262) {
                    for (n13 = (n7 -= 3) - n15; n13 > 262; n13 -= 262) {
                        byArray2[n3 + n10++] = (byte)(224L + (l >>> 8));
                        byArray2[n3 + n10++] = -3;
                        byArray2[n3 + n10++] = (byte)(l & 0xFFL);
                    }
                }
                if (n13 < 7) {
                    byArray2[n3 + n10++] = (byte)((long)(n13 << 5) + (l >>> 8));
                    byArray2[n3 + n10++] = (byte)(l & 0xFFL);
                } else {
                    byArray2[n3 + n10++] = (byte)(224L + (l >>> 8));
                    byArray2[n3 + n10++] = (byte)(n13 - 7);
                    byArray2[n3 + n10++] = (byte)(l & 0xFFL);
                }
            }
            n12 = FastLz.hashFunction(byArray, n + n7);
            nArray[n12] = n7++;
            n12 = FastLz.hashFunction(byArray, n + n7);
            nArray[n12] = n7++;
            byArray2[n3 + n10++] = 31;
        }
        ++n8;
        while (n7 <= n8) {
            byArray2[n3 + n10++] = byArray[n + n7++];
            if (++n11 != 32) continue;
            n11 = 0;
            byArray2[n3 + n10++] = 31;
        }
        if (n11 != 0) {
            byArray2[n3 + n10 - n11 - 1] = (byte)(n11 - 1);
        } else {
            --n10;
        }
        if (n6 == 2) {
            int n16 = n3;
            byArray2[n16] = (byte)(byArray2[n16] | 0x20);
        }
        return n10;
    }

    static int decompress(byte[] byArray, int n, int n2, byte[] byArray2, int n3, int n4) {
        int n5 = (byArray[n] >> 5) + 1;
        if (n5 != 1 && n5 != 2) {
            throw new DecompressionException(String.format("invalid level: %d (expected: %d or %d)", n5, 1, 2));
        }
        int n6 = 0;
        int n7 = 0;
        long l = byArray[n + n6++] & 0x1F;
        boolean bl = true;
        do {
            int n8 = n7;
            long l2 = l >> 5;
            long l3 = (l & 0x1FL) << 8;
            if (l >= 32L) {
                int n9;
                n8 = (int)((long)n8 - l3);
                if (--l2 == 6L) {
                    if (n5 == 1) {
                        l2 += (long)(byArray[n + n6++] & 0xFF);
                    } else {
                        do {
                            n9 = byArray[n + n6++] & 0xFF;
                            l2 += (long)n9;
                        } while (n9 == 255);
                    }
                }
                if (n5 == 1) {
                    n8 -= byArray[n + n6++] & 0xFF;
                } else {
                    n9 = byArray[n + n6++] & 0xFF;
                    n8 -= n9;
                    if (n9 == 255 && l3 == 7936L) {
                        l3 = (byArray[n + n6++] & 0xFF) << 8;
                        n8 = (int)((long)n7 - (l3 += (long)(byArray[n + n6++] & 0xFF)) - 8191L);
                    }
                }
                if ((long)n7 + l2 + 3L > (long)n4) {
                    return 1;
                }
                if (n8 - 1 < 0) {
                    return 1;
                }
                if (n6 < n2) {
                    l = byArray[n + n6++] & 0xFF;
                } else {
                    bl = false;
                }
                if (n8 == n7) {
                    byte by = byArray2[n3 + n8 - 1];
                    byArray2[n3 + n7++] = by;
                    byArray2[n3 + n7++] = by;
                    byArray2[n3 + n7++] = by;
                    while (l2 != 0L) {
                        byArray2[n3 + n7++] = by;
                        --l2;
                    }
                } else {
                    int n10 = n7++;
                    int n11 = --n8;
                    byArray2[n3 + n10] = byArray2[n3 + n11];
                    int n12 = n7++;
                    int n13 = ++n8;
                    byArray2[n3 + n12] = byArray2[n3 + n13];
                    int n14 = n7++;
                    int n15 = ++n8;
                    ++n8;
                    byArray2[n3 + n14] = byArray2[n3 + n15];
                    while (l2 != 0L) {
                        byArray2[n3 + n7++] = byArray2[n3 + n8++];
                        --l2;
                    }
                }
            } else {
                if ((long)n7 + ++l > (long)n4) {
                    return 1;
                }
                if ((long)n6 + l > (long)n2) {
                    return 1;
                }
                byArray2[n3 + n7++] = byArray[n + n6++];
                --l;
                while (l != 0L) {
                    byArray2[n3 + n7++] = byArray[n + n6++];
                    --l;
                }
                boolean bl2 = bl = n6 < n2;
                if (!bl) continue;
                l = byArray[n + n6++] & 0xFF;
            }
        } while (bl);
        return n7;
    }

    private static int hashFunction(byte[] byArray, int n) {
        int n2 = FastLz.readU16(byArray, n);
        n2 ^= FastLz.readU16(byArray, n + 1) ^ n2 >> 3;
        return n2 &= 0x1FFF;
    }

    private static int readU16(byte[] byArray, int n) {
        if (n + 1 >= byArray.length) {
            return byArray[n] & 0xFF;
        }
        return (byArray[n + 1] & 0xFF) << 8 | byArray[n] & 0xFF;
    }

    private FastLz() {
    }
}

