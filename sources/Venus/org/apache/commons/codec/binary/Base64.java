/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

import java.math.BigInteger;
import org.apache.commons.codec.binary.BaseNCodec;
import org.apache.commons.codec.binary.StringUtils;

public class Base64
extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 6;
    private static final int BYTES_PER_UNENCODED_BLOCK = 3;
    private static final int BYTES_PER_ENCODED_BLOCK = 4;
    static final byte[] CHUNK_SEPARATOR = new byte[]{13, 10};
    private static final byte[] STANDARD_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    private static final byte[] URL_SAFE_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
    private static final byte[] DECODE_TABLE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};
    private static final int MASK_6BITS = 63;
    private final byte[] encodeTable;
    private final byte[] decodeTable = DECODE_TABLE;
    private final byte[] lineSeparator;
    private final int decodeSize;
    private final int encodeSize;

    public Base64() {
        this(0);
    }

    public Base64(boolean bl) {
        this(76, CHUNK_SEPARATOR, bl);
    }

    public Base64(int n) {
        this(n, CHUNK_SEPARATOR);
    }

    public Base64(int n, byte[] byArray) {
        this(n, byArray, false);
    }

    public Base64(int n, byte[] byArray, boolean bl) {
        super(3, 4, n, byArray == null ? 0 : byArray.length);
        if (byArray != null) {
            if (this.containsAlphabetOrPad(byArray)) {
                String string = StringUtils.newStringUtf8(byArray);
                throw new IllegalArgumentException("lineSeparator must not contain base64 characters: [" + string + "]");
            }
            if (n > 0) {
                this.encodeSize = 4 + byArray.length;
                this.lineSeparator = new byte[byArray.length];
                System.arraycopy(byArray, 0, this.lineSeparator, 0, byArray.length);
            } else {
                this.encodeSize = 4;
                this.lineSeparator = null;
            }
        } else {
            this.encodeSize = 4;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
        this.encodeTable = bl ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE;
    }

    public boolean isUrlSafe() {
        return this.encodeTable == URL_SAFE_ENCODE_TABLE;
    }

    @Override
    void encode(byte[] byArray, int n, int n2, BaseNCodec.Context context) {
        if (context.eof) {
            return;
        }
        if (n2 < 0) {
            context.eof = true;
            if (0 == context.modulus && this.lineLength == 0) {
                return;
            }
            byte[] byArray2 = this.ensureBufferSize(this.encodeSize, context);
            int n3 = context.pos;
            switch (context.modulus) {
                case 0: {
                    break;
                }
                case 1: {
                    byArray2[context.pos++] = this.encodeTable[context.ibitWorkArea >> 2 & 0x3F];
                    byArray2[context.pos++] = this.encodeTable[context.ibitWorkArea << 4 & 0x3F];
                    if (this.encodeTable != STANDARD_ENCODE_TABLE) break;
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    break;
                }
                case 2: {
                    byArray2[context.pos++] = this.encodeTable[context.ibitWorkArea >> 10 & 0x3F];
                    byArray2[context.pos++] = this.encodeTable[context.ibitWorkArea >> 4 & 0x3F];
                    byArray2[context.pos++] = this.encodeTable[context.ibitWorkArea << 2 & 0x3F];
                    if (this.encodeTable != STANDARD_ENCODE_TABLE) break;
                    byArray2[context.pos++] = this.pad;
                    break;
                }
                default: {
                    throw new IllegalStateException("Impossible modulus " + context.modulus);
                }
            }
            context.currentLinePos += context.pos - n3;
            if (this.lineLength > 0 && context.currentLinePos > 0) {
                System.arraycopy(this.lineSeparator, 0, byArray2, context.pos, this.lineSeparator.length);
                context.pos += this.lineSeparator.length;
            }
        } else {
            for (int i = 0; i < n2; ++i) {
                int n4;
                byte[] byArray3 = this.ensureBufferSize(this.encodeSize, context);
                context.modulus = (context.modulus + 1) % 3;
                if ((n4 = byArray[n++]) < 0) {
                    n4 += 256;
                }
                context.ibitWorkArea = (context.ibitWorkArea << 8) + n4;
                if (0 != context.modulus) continue;
                byArray3[context.pos++] = this.encodeTable[context.ibitWorkArea >> 18 & 0x3F];
                byArray3[context.pos++] = this.encodeTable[context.ibitWorkArea >> 12 & 0x3F];
                byArray3[context.pos++] = this.encodeTable[context.ibitWorkArea >> 6 & 0x3F];
                byArray3[context.pos++] = this.encodeTable[context.ibitWorkArea & 0x3F];
                context.currentLinePos += 4;
                if (this.lineLength <= 0 || this.lineLength > context.currentLinePos) continue;
                System.arraycopy(this.lineSeparator, 0, byArray3, context.pos, this.lineSeparator.length);
                context.pos += this.lineSeparator.length;
                context.currentLinePos = 0;
            }
        }
    }

    @Override
    void decode(byte[] byArray, int n, int n2, BaseNCodec.Context context) {
        if (context.eof) {
            return;
        }
        if (n2 < 0) {
            context.eof = true;
        }
        for (int i = 0; i < n2; ++i) {
            byte by;
            byte by2;
            byte[] byArray2 = this.ensureBufferSize(this.decodeSize, context);
            if ((by2 = byArray[n++]) == this.pad) {
                context.eof = true;
                break;
            }
            if (by2 < 0 || by2 >= DECODE_TABLE.length || (by = DECODE_TABLE[by2]) < 0) continue;
            context.modulus = (context.modulus + 1) % 4;
            context.ibitWorkArea = (context.ibitWorkArea << 6) + by;
            if (context.modulus != 0) continue;
            byArray2[context.pos++] = (byte)(context.ibitWorkArea >> 16 & 0xFF);
            byArray2[context.pos++] = (byte)(context.ibitWorkArea >> 8 & 0xFF);
            byArray2[context.pos++] = (byte)(context.ibitWorkArea & 0xFF);
        }
        if (context.eof && context.modulus != 0) {
            byte[] byArray3 = this.ensureBufferSize(this.decodeSize, context);
            switch (context.modulus) {
                case 1: {
                    break;
                }
                case 2: {
                    context.ibitWorkArea >>= 4;
                    byArray3[context.pos++] = (byte)(context.ibitWorkArea & 0xFF);
                    break;
                }
                case 3: {
                    context.ibitWorkArea >>= 2;
                    byArray3[context.pos++] = (byte)(context.ibitWorkArea >> 8 & 0xFF);
                    byArray3[context.pos++] = (byte)(context.ibitWorkArea & 0xFF);
                    break;
                }
                default: {
                    throw new IllegalStateException("Impossible modulus " + context.modulus);
                }
            }
        }
    }

    @Deprecated
    public static boolean isArrayByteBase64(byte[] byArray) {
        return Base64.isBase64(byArray);
    }

    public static boolean isBase64(byte by) {
        return by == 61 || by >= 0 && by < DECODE_TABLE.length && DECODE_TABLE[by] != -1;
    }

    public static boolean isBase64(String string) {
        return Base64.isBase64(StringUtils.getBytesUtf8(string));
    }

    public static boolean isBase64(byte[] byArray) {
        for (int i = 0; i < byArray.length; ++i) {
            if (Base64.isBase64(byArray[i]) || Base64.isWhiteSpace(byArray[i])) continue;
            return true;
        }
        return false;
    }

    public static byte[] encodeBase64(byte[] byArray) {
        return Base64.encodeBase64(byArray, false);
    }

    public static String encodeBase64String(byte[] byArray) {
        return StringUtils.newStringUsAscii(Base64.encodeBase64(byArray, false));
    }

    public static byte[] encodeBase64URLSafe(byte[] byArray) {
        return Base64.encodeBase64(byArray, false, true);
    }

    public static String encodeBase64URLSafeString(byte[] byArray) {
        return StringUtils.newStringUsAscii(Base64.encodeBase64(byArray, false, true));
    }

    public static byte[] encodeBase64Chunked(byte[] byArray) {
        return Base64.encodeBase64(byArray, true);
    }

    public static byte[] encodeBase64(byte[] byArray, boolean bl) {
        return Base64.encodeBase64(byArray, bl, false);
    }

    public static byte[] encodeBase64(byte[] byArray, boolean bl, boolean bl2) {
        return Base64.encodeBase64(byArray, bl, bl2, Integer.MAX_VALUE);
    }

    public static byte[] encodeBase64(byte[] byArray, boolean bl, boolean bl2, int n) {
        if (byArray == null || byArray.length == 0) {
            return byArray;
        }
        Base64 base64 = bl ? new Base64(bl2) : new Base64(0, CHUNK_SEPARATOR, bl2);
        long l = base64.getEncodedLength(byArray);
        if (l > (long)n) {
            throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + l + ") than the specified maximum size of " + n);
        }
        return base64.encode(byArray);
    }

    public static byte[] decodeBase64(String string) {
        return new Base64().decode(string);
    }

    public static byte[] decodeBase64(byte[] byArray) {
        return new Base64().decode(byArray);
    }

    public static BigInteger decodeInteger(byte[] byArray) {
        return new BigInteger(1, Base64.decodeBase64(byArray));
    }

    public static byte[] encodeInteger(BigInteger bigInteger) {
        if (bigInteger == null) {
            throw new NullPointerException("encodeInteger called with null parameter");
        }
        return Base64.encodeBase64(Base64.toIntegerBytes(bigInteger), false);
    }

    static byte[] toIntegerBytes(BigInteger bigInteger) {
        int n = bigInteger.bitLength();
        n = n + 7 >> 3 << 3;
        byte[] byArray = bigInteger.toByteArray();
        if (bigInteger.bitLength() % 8 != 0 && bigInteger.bitLength() / 8 + 1 == n / 8) {
            return byArray;
        }
        int n2 = 0;
        int n3 = byArray.length;
        if (bigInteger.bitLength() % 8 == 0) {
            n2 = 1;
            --n3;
        }
        int n4 = n / 8 - n3;
        byte[] byArray2 = new byte[n / 8];
        System.arraycopy(byArray, n2, byArray2, n4, n3);
        return byArray2;
    }

    @Override
    protected boolean isInAlphabet(byte by) {
        return by >= 0 && by < this.decodeTable.length && this.decodeTable[by] != -1;
    }
}

