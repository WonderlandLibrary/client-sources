/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.base64;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.base64.Base64Dialect;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteOrder;

public final class Base64 {
    private static final int MAX_LINE_LENGTH = 76;
    private static final byte EQUALS_SIGN = 61;
    private static final byte NEW_LINE = 10;
    private static final byte WHITE_SPACE_ENC = -5;
    private static final byte EQUALS_SIGN_ENC = -1;

    private static byte[] alphabet(Base64Dialect base64Dialect) {
        if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        }
        return base64Dialect.alphabet;
    }

    private static byte[] decodabet(Base64Dialect base64Dialect) {
        if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        }
        return base64Dialect.decodabet;
    }

    private static boolean breakLines(Base64Dialect base64Dialect) {
        if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        }
        return base64Dialect.breakLinesByDefault;
    }

    public static ByteBuf encode(ByteBuf byteBuf) {
        return Base64.encode(byteBuf, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, Base64Dialect base64Dialect) {
        return Base64.encode(byteBuf, Base64.breakLines(base64Dialect), base64Dialect);
    }

    public static ByteBuf encode(ByteBuf byteBuf, boolean bl) {
        return Base64.encode(byteBuf, bl, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, boolean bl, Base64Dialect base64Dialect) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        ByteBuf byteBuf2 = Base64.encode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), bl, base64Dialect);
        byteBuf.readerIndex(byteBuf.writerIndex());
        return byteBuf2;
    }

    public static ByteBuf encode(ByteBuf byteBuf, int n, int n2) {
        return Base64.encode(byteBuf, n, n2, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, int n, int n2, Base64Dialect base64Dialect) {
        return Base64.encode(byteBuf, n, n2, Base64.breakLines(base64Dialect), base64Dialect);
    }

    public static ByteBuf encode(ByteBuf byteBuf, int n, int n2, boolean bl) {
        return Base64.encode(byteBuf, n, n2, bl, Base64Dialect.STANDARD);
    }

    public static ByteBuf encode(ByteBuf byteBuf, int n, int n2, boolean bl, Base64Dialect base64Dialect) {
        return Base64.encode(byteBuf, n, n2, bl, base64Dialect, byteBuf.alloc());
    }

    public static ByteBuf encode(ByteBuf byteBuf, int n, int n2, boolean bl, Base64Dialect base64Dialect, ByteBufAllocator byteBufAllocator) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        }
        ByteBuf byteBuf2 = byteBufAllocator.buffer(Base64.encodedBufferSize(n2, bl)).order(byteBuf.order());
        byte[] byArray = Base64.alphabet(base64Dialect);
        int n3 = 0;
        int n4 = 0;
        int n5 = n2 - 2;
        int n6 = 0;
        while (n3 < n5) {
            Base64.encode3to4(byteBuf, n3 + n, 3, byteBuf2, n4, byArray);
            if (bl && (n6 += 4) == 76) {
                byteBuf2.setByte(n4 + 4, 10);
                ++n4;
                n6 = 0;
            }
            n3 += 3;
            n4 += 4;
        }
        if (n3 < n2) {
            Base64.encode3to4(byteBuf, n3 + n, n2 - n3, byteBuf2, n4, byArray);
            n4 += 4;
        }
        if (n4 > 1 && byteBuf2.getByte(n4 - 1) == 10) {
            --n4;
        }
        return byteBuf2.slice(0, n4);
    }

    private static void encode3to4(ByteBuf byteBuf, int n, int n2, ByteBuf byteBuf2, int n3, byte[] byArray) {
        if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
            int n4;
            switch (n2) {
                case 1: {
                    n4 = Base64.toInt(byteBuf.getByte(n));
                    break;
                }
                case 2: {
                    n4 = Base64.toIntBE(byteBuf.getShort(n));
                    break;
                }
                default: {
                    n4 = n2 <= 0 ? 0 : Base64.toIntBE(byteBuf.getMedium(n));
                }
            }
            Base64.encode3to4BigEndian(n4, n2, byteBuf2, n3, byArray);
        } else {
            int n5;
            switch (n2) {
                case 1: {
                    n5 = Base64.toInt(byteBuf.getByte(n));
                    break;
                }
                case 2: {
                    n5 = Base64.toIntLE(byteBuf.getShort(n));
                    break;
                }
                default: {
                    n5 = n2 <= 0 ? 0 : Base64.toIntLE(byteBuf.getMedium(n));
                }
            }
            Base64.encode3to4LittleEndian(n5, n2, byteBuf2, n3, byArray);
        }
    }

    static int encodedBufferSize(int n, boolean bl) {
        long l = ((long)n << 2) / 3L;
        long l2 = l + 3L & 0xFFFFFFFFFFFFFFFCL;
        if (bl) {
            l2 += l / 76L;
        }
        return l2 < Integer.MAX_VALUE ? (int)l2 : Integer.MAX_VALUE;
    }

    private static int toInt(byte by) {
        return (by & 0xFF) << 16;
    }

    private static int toIntBE(short s) {
        return (s & 0xFF00) << 8 | (s & 0xFF) << 8;
    }

    private static int toIntLE(short s) {
        return (s & 0xFF) << 16 | s & 0xFF00;
    }

    private static int toIntBE(int n) {
        return n & 0xFF0000 | n & 0xFF00 | n & 0xFF;
    }

    private static int toIntLE(int n) {
        return (n & 0xFF) << 16 | n & 0xFF00 | (n & 0xFF0000) >>> 16;
    }

    private static void encode3to4BigEndian(int n, int n2, ByteBuf byteBuf, int n3, byte[] byArray) {
        switch (n2) {
            case 3: {
                byteBuf.setInt(n3, byArray[n >>> 18] << 24 | byArray[n >>> 12 & 0x3F] << 16 | byArray[n >>> 6 & 0x3F] << 8 | byArray[n & 0x3F]);
                break;
            }
            case 2: {
                byteBuf.setInt(n3, byArray[n >>> 18] << 24 | byArray[n >>> 12 & 0x3F] << 16 | byArray[n >>> 6 & 0x3F] << 8 | 0x3D);
                break;
            }
            case 1: {
                byteBuf.setInt(n3, byArray[n >>> 18] << 24 | byArray[n >>> 12 & 0x3F] << 16 | 0x3D00 | 0x3D);
                break;
            }
        }
    }

    private static void encode3to4LittleEndian(int n, int n2, ByteBuf byteBuf, int n3, byte[] byArray) {
        switch (n2) {
            case 3: {
                byteBuf.setInt(n3, byArray[n >>> 18] | byArray[n >>> 12 & 0x3F] << 8 | byArray[n >>> 6 & 0x3F] << 16 | byArray[n & 0x3F] << 24);
                break;
            }
            case 2: {
                byteBuf.setInt(n3, byArray[n >>> 18] | byArray[n >>> 12 & 0x3F] << 8 | byArray[n >>> 6 & 0x3F] << 16 | 0x3D000000);
                break;
            }
            case 1: {
                byteBuf.setInt(n3, byArray[n >>> 18] | byArray[n >>> 12 & 0x3F] << 8 | 0x3D0000 | 0x3D000000);
                break;
            }
        }
    }

    public static ByteBuf decode(ByteBuf byteBuf) {
        return Base64.decode(byteBuf, Base64Dialect.STANDARD);
    }

    public static ByteBuf decode(ByteBuf byteBuf, Base64Dialect base64Dialect) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        ByteBuf byteBuf2 = Base64.decode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), base64Dialect);
        byteBuf.readerIndex(byteBuf.writerIndex());
        return byteBuf2;
    }

    public static ByteBuf decode(ByteBuf byteBuf, int n, int n2) {
        return Base64.decode(byteBuf, n, n2, Base64Dialect.STANDARD);
    }

    public static ByteBuf decode(ByteBuf byteBuf, int n, int n2, Base64Dialect base64Dialect) {
        return Base64.decode(byteBuf, n, n2, base64Dialect, byteBuf.alloc());
    }

    public static ByteBuf decode(ByteBuf byteBuf, int n, int n2, Base64Dialect base64Dialect, ByteBufAllocator byteBufAllocator) {
        if (byteBuf == null) {
            throw new NullPointerException("src");
        }
        if (base64Dialect == null) {
            throw new NullPointerException("dialect");
        }
        return new Decoder(null).decode(byteBuf, n, n2, byteBufAllocator, base64Dialect);
    }

    static int decodedBufferSize(int n) {
        return n - (n >>> 2);
    }

    private Base64() {
    }

    static byte[] access$100(Base64Dialect base64Dialect) {
        return Base64.decodabet(base64Dialect);
    }

    private static final class Decoder
    implements ByteProcessor {
        private final byte[] b4 = new byte[4];
        private int b4Posn;
        private byte sbiCrop;
        private byte sbiDecode;
        private byte[] decodabet;
        private int outBuffPosn;
        private ByteBuf dest;

        private Decoder() {
        }

        ByteBuf decode(ByteBuf byteBuf, int n, int n2, ByteBufAllocator byteBufAllocator, Base64Dialect base64Dialect) {
            this.dest = byteBufAllocator.buffer(Base64.decodedBufferSize(n2)).order(byteBuf.order());
            this.decodabet = Base64.access$100(base64Dialect);
            try {
                byteBuf.forEachByte(n, n2, this);
                return this.dest.slice(0, this.outBuffPosn);
            } catch (Throwable throwable) {
                this.dest.release();
                PlatformDependent.throwException(throwable);
                return null;
            }
        }

        @Override
        public boolean process(byte by) throws Exception {
            this.sbiCrop = (byte)(by & 0x7F);
            this.sbiDecode = this.decodabet[this.sbiCrop];
            if (this.sbiDecode >= -5) {
                if (this.sbiDecode >= -1) {
                    this.b4[this.b4Posn++] = this.sbiCrop;
                    if (this.b4Posn > 3) {
                        this.outBuffPosn += Decoder.decode4to3(this.b4, this.dest, this.outBuffPosn, this.decodabet);
                        this.b4Posn = 0;
                        if (this.sbiCrop == 61) {
                            return true;
                        }
                    }
                }
                return false;
            }
            throw new IllegalArgumentException("invalid bad Base64 input character: " + (short)(by & 0xFF) + " (decimal)");
        }

        private static int decode4to3(byte[] byArray, ByteBuf byteBuf, int n, byte[] byArray2) {
            int n2;
            byte by = byArray[0];
            byte by2 = byArray[1];
            byte by3 = byArray[2];
            if (by3 == 61) {
                int n3;
                try {
                    n3 = (byArray2[by] & 0xFF) << 2 | (byArray2[by2] & 0xFF) >>> 4;
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new IllegalArgumentException("not encoded in Base64");
                }
                byteBuf.setByte(n, n3);
                return 0;
            }
            byte by4 = byArray[3];
            if (by4 == 61) {
                int n4;
                byte by5 = byArray2[by2];
                try {
                    n4 = byteBuf.order() == ByteOrder.BIG_ENDIAN ? ((byArray2[by] & 0x3F) << 2 | (by5 & 0xF0) >> 4) << 8 | (by5 & 0xF) << 4 | (byArray2[by3] & 0xFC) >>> 2 : (byArray2[by] & 0x3F) << 2 | (by5 & 0xF0) >> 4 | ((by5 & 0xF) << 4 | (byArray2[by3] & 0xFC) >>> 2) << 8;
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new IllegalArgumentException("not encoded in Base64");
                }
                byteBuf.setShort(n, n4);
                return 1;
            }
            try {
                if (byteBuf.order() == ByteOrder.BIG_ENDIAN) {
                    n2 = (byArray2[by] & 0x3F) << 18 | (byArray2[by2] & 0xFF) << 12 | (byArray2[by3] & 0xFF) << 6 | byArray2[by4] & 0xFF;
                } else {
                    byte by6 = byArray2[by2];
                    byte by7 = byArray2[by3];
                    n2 = (byArray2[by] & 0x3F) << 2 | (by6 & 0xF) << 12 | (by6 & 0xF0) >>> 4 | (by7 & 3) << 22 | (by7 & 0xFC) << 6 | (byArray2[by4] & 0xFF) << 16;
                }
            } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                throw new IllegalArgumentException("not encoded in Base64");
            }
            byteBuf.setMedium(n, n2);
            return 0;
        }

        Decoder(1 var1_1) {
            this();
        }
    }
}

