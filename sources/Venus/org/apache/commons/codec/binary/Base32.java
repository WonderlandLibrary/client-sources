/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.codec.binary;

import org.apache.commons.codec.binary.BaseNCodec;
import org.apache.commons.codec.binary.StringUtils;

public class Base32
extends BaseNCodec {
    private static final int BITS_PER_ENCODED_BYTE = 5;
    private static final int BYTES_PER_ENCODED_BLOCK = 8;
    private static final int BYTES_PER_UNENCODED_BLOCK = 5;
    private static final byte[] CHUNK_SEPARATOR = new byte[]{13, 10};
    private static final byte[] DECODE_TABLE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
    private static final byte[] ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 50, 51, 52, 53, 54, 55};
    private static final byte[] HEX_DECODE_TABLE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
    private static final byte[] HEX_ENCODE_TABLE = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86};
    private static final int MASK_5BITS = 31;
    private final int decodeSize;
    private final byte[] decodeTable;
    private final int encodeSize;
    private final byte[] encodeTable;
    private final byte[] lineSeparator;

    public Base32() {
        this(false);
    }

    public Base32(byte by) {
        this(false, by);
    }

    public Base32(boolean bl) {
        this(0, null, bl, 61);
    }

    public Base32(boolean bl, byte by) {
        this(0, null, bl, by);
    }

    public Base32(int n) {
        this(n, CHUNK_SEPARATOR);
    }

    public Base32(int n, byte[] byArray) {
        this(n, byArray, false, 61);
    }

    public Base32(int n, byte[] byArray, boolean bl) {
        this(n, byArray, bl, 61);
    }

    public Base32(int n, byte[] byArray, boolean bl, byte by) {
        super(5, 8, n, byArray == null ? 0 : byArray.length, by);
        if (bl) {
            this.encodeTable = HEX_ENCODE_TABLE;
            this.decodeTable = HEX_DECODE_TABLE;
        } else {
            this.encodeTable = ENCODE_TABLE;
            this.decodeTable = DECODE_TABLE;
        }
        if (n > 0) {
            if (byArray == null) {
                throw new IllegalArgumentException("lineLength " + n + " > 0, but lineSeparator is null");
            }
            if (this.containsAlphabetOrPad(byArray)) {
                String string = StringUtils.newStringUtf8(byArray);
                throw new IllegalArgumentException("lineSeparator must not contain Base32 characters: [" + string + "]");
            }
            this.encodeSize = 8 + byArray.length;
            this.lineSeparator = new byte[byArray.length];
            System.arraycopy(byArray, 0, this.lineSeparator, 0, byArray.length);
        } else {
            this.encodeSize = 8;
            this.lineSeparator = null;
        }
        this.decodeSize = this.encodeSize - 1;
        if (this.isInAlphabet(by) || Base32.isWhiteSpace(by)) {
            throw new IllegalArgumentException("pad must not be in alphabet or whitespace");
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
            if ((by2 = byArray[n++]) == this.pad) {
                context.eof = true;
                break;
            }
            byte[] byArray2 = this.ensureBufferSize(this.decodeSize, context);
            if (by2 < 0 || by2 >= this.decodeTable.length || (by = this.decodeTable[by2]) < 0) continue;
            context.modulus = (context.modulus + 1) % 8;
            context.lbitWorkArea = (context.lbitWorkArea << 5) + (long)by;
            if (context.modulus != 0) continue;
            byArray2[context.pos++] = (byte)(context.lbitWorkArea >> 32 & 0xFFL);
            byArray2[context.pos++] = (byte)(context.lbitWorkArea >> 24 & 0xFFL);
            byArray2[context.pos++] = (byte)(context.lbitWorkArea >> 16 & 0xFFL);
            byArray2[context.pos++] = (byte)(context.lbitWorkArea >> 8 & 0xFFL);
            byArray2[context.pos++] = (byte)(context.lbitWorkArea & 0xFFL);
        }
        if (context.eof && context.modulus >= 2) {
            byte[] byArray3 = this.ensureBufferSize(this.decodeSize, context);
            switch (context.modulus) {
                case 2: {
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea >> 2 & 0xFFL);
                    break;
                }
                case 3: {
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea >> 7 & 0xFFL);
                    break;
                }
                case 4: {
                    context.lbitWorkArea >>= 4;
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea >> 8 & 0xFFL);
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea & 0xFFL);
                    break;
                }
                case 5: {
                    context.lbitWorkArea >>= 1;
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea >> 16 & 0xFFL);
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea >> 8 & 0xFFL);
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea & 0xFFL);
                    break;
                }
                case 6: {
                    context.lbitWorkArea >>= 6;
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea >> 16 & 0xFFL);
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea >> 8 & 0xFFL);
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea & 0xFFL);
                    break;
                }
                case 7: {
                    context.lbitWorkArea >>= 3;
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea >> 24 & 0xFFL);
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea >> 16 & 0xFFL);
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea >> 8 & 0xFFL);
                    byArray3[context.pos++] = (byte)(context.lbitWorkArea & 0xFFL);
                    break;
                }
                default: {
                    throw new IllegalStateException("Impossible modulus " + context.modulus);
                }
            }
        }
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
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 3) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea << 2) & 0x1F];
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    break;
                }
                case 2: {
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 11) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 6) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 1) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea << 4) & 0x1F];
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    break;
                }
                case 3: {
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 19) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 14) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 9) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 4) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea << 1) & 0x1F];
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    byArray2[context.pos++] = this.pad;
                    break;
                }
                case 4: {
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 27) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 22) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 17) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 12) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 7) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 2) & 0x1F];
                    byArray2[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea << 3) & 0x1F];
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
                context.modulus = (context.modulus + 1) % 5;
                if ((n4 = byArray[n++]) < 0) {
                    n4 += 256;
                }
                context.lbitWorkArea = (context.lbitWorkArea << 8) + (long)n4;
                if (0 != context.modulus) continue;
                byArray3[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 35) & 0x1F];
                byArray3[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 30) & 0x1F];
                byArray3[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 25) & 0x1F];
                byArray3[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 20) & 0x1F];
                byArray3[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 15) & 0x1F];
                byArray3[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 10) & 0x1F];
                byArray3[context.pos++] = this.encodeTable[(int)(context.lbitWorkArea >> 5) & 0x1F];
                byArray3[context.pos++] = this.encodeTable[(int)context.lbitWorkArea & 0x1F];
                context.currentLinePos += 8;
                if (this.lineLength <= 0 || this.lineLength > context.currentLinePos) continue;
                System.arraycopy(this.lineSeparator, 0, byArray3, context.pos, this.lineSeparator.length);
                context.pos += this.lineSeparator.length;
                context.currentLinePos = 0;
            }
        }
    }

    @Override
    public boolean isInAlphabet(byte by) {
        return by >= 0 && by < this.decodeTable.length && this.decodeTable[by] != -1;
    }
}

