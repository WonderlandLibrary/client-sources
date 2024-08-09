/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.util.CodePointMap;
import com.ibm.icu.util.ICUUncheckedIOException;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class CodePointTrie
extends CodePointMap {
    private static final int MAX_UNICODE = 0x10FFFF;
    private static final int ASCII_LIMIT = 128;
    static final int FAST_SHIFT = 6;
    static final int FAST_DATA_BLOCK_LENGTH = 64;
    private static final int FAST_DATA_MASK = 63;
    private static final int SMALL_MAX = 4095;
    private static final int ERROR_VALUE_NEG_DATA_OFFSET = 1;
    private static final int HIGH_VALUE_NEG_DATA_OFFSET = 2;
    private static final int BMP_INDEX_LENGTH = 1024;
    static final int SMALL_LIMIT = 4096;
    private static final int SMALL_INDEX_LENGTH = 64;
    static final int SHIFT_3 = 4;
    private static final int SHIFT_2 = 9;
    private static final int SHIFT_1 = 14;
    static final int SHIFT_2_3 = 5;
    static final int SHIFT_1_2 = 5;
    private static final int OMITTED_BMP_INDEX_1_LENGTH = 4;
    static final int INDEX_2_BLOCK_LENGTH = 32;
    static final int INDEX_2_MASK = 31;
    static final int CP_PER_INDEX_2_ENTRY = 512;
    static final int INDEX_3_BLOCK_LENGTH = 32;
    private static final int INDEX_3_MASK = 31;
    static final int SMALL_DATA_BLOCK_LENGTH = 16;
    static final int SMALL_DATA_MASK = 15;
    private static final int OPTIONS_DATA_LENGTH_MASK = 61440;
    private static final int OPTIONS_DATA_NULL_OFFSET_MASK = 3840;
    private static final int OPTIONS_RESERVED_MASK = 56;
    private static final int OPTIONS_VALUE_BITS_MASK = 7;
    static final int NO_INDEX3_NULL_OFFSET = Short.MAX_VALUE;
    static final int NO_DATA_NULL_OFFSET = 1048575;
    private final int[] ascii = new int[128];
    private final char[] index;
    @Deprecated
    protected final Data data;
    @Deprecated
    protected final int dataLength;
    @Deprecated
    protected final int highStart;
    private final int index3NullOffset;
    private final int dataNullOffset;
    private final int nullValue;
    static final boolean $assertionsDisabled = !CodePointTrie.class.desiredAssertionStatus();

    private CodePointTrie(char[] cArray, Data data, int n, int n2, int n3) {
        int n4;
        this.index = cArray;
        this.data = data;
        this.dataLength = data.getDataLength();
        this.highStart = n;
        this.index3NullOffset = n2;
        this.dataNullOffset = n3;
        for (n4 = 0; n4 < 128; ++n4) {
            this.ascii[n4] = data.getFromIndex(n4);
        }
        n4 = n3;
        if (n4 >= this.dataLength) {
            n4 = this.dataLength - 2;
        }
        this.nullValue = data.getFromIndex(n4);
    }

    public static CodePointTrie fromBinary(Type type, ValueWidth valueWidth, ByteBuffer byteBuffer) {
        ByteOrder byteOrder = byteBuffer.order();
        try {
            ValueWidth valueWidth2;
            Type type2;
            char c;
            if (byteBuffer.remaining() < 16) {
                throw new ICUUncheckedIOException("Buffer too short for a CodePointTrie header");
            }
            int n = byteBuffer.getInt();
            switch (n) {
                case 1416784179: {
                    break;
                }
                case 862548564: {
                    c = byteOrder == ByteOrder.BIG_ENDIAN ? (char)'\u0001' : '\u0000';
                    byteBuffer.order(c != '\u0000' ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
                    n = 1416784179;
                    break;
                }
                default: {
                    throw new ICUUncheckedIOException("Buffer does not contain a serialized CodePointTrie");
                }
            }
            c = byteBuffer.getChar();
            char c2 = byteBuffer.getChar();
            int n2 = byteBuffer.getChar();
            char c3 = byteBuffer.getChar();
            int n3 = byteBuffer.getChar();
            char c4 = byteBuffer.getChar();
            int n4 = c >> 6 & 3;
            switch (n4) {
                case 0: {
                    type2 = Type.FAST;
                    break;
                }
                case 1: {
                    type2 = Type.SMALL;
                    break;
                }
                default: {
                    throw new ICUUncheckedIOException("CodePointTrie data header has an unsupported type");
                }
            }
            int n5 = c & 7;
            switch (n5) {
                case 0: {
                    valueWidth2 = ValueWidth.BITS_16;
                    break;
                }
                case 1: {
                    valueWidth2 = ValueWidth.BITS_32;
                    break;
                }
                case 2: {
                    valueWidth2 = ValueWidth.BITS_8;
                    break;
                }
                default: {
                    throw new ICUUncheckedIOException("CodePointTrie data header has an unsupported value width");
                }
            }
            if ((c & 0x38) != 0) {
                throw new ICUUncheckedIOException("CodePointTrie data header has unsupported options");
            }
            if (type == null) {
                type = type2;
            }
            if (valueWidth == null) {
                valueWidth = valueWidth2;
            }
            if (type != type2 || valueWidth != valueWidth2) {
                throw new ICUUncheckedIOException("CodePointTrie data header has a different type or value width than required");
            }
            n3 |= (c & 0xF00) << 8;
            int n6 = c4 << 9;
            int n7 = c2 * 2;
            n7 = valueWidth == ValueWidth.BITS_16 ? (n7 += n2 * 2) : (valueWidth == ValueWidth.BITS_32 ? (n7 += n2 * 4) : (n7 += (n2 |= (c & 0xF000) << 4)));
            if (byteBuffer.remaining() < n7) {
                throw new ICUUncheckedIOException("Buffer too short for the CodePointTrie data");
            }
            char[] cArray = ICUBinary.getChars(byteBuffer, c2, 0);
            switch (1.$SwitchMap$com$ibm$icu$util$CodePointTrie$ValueWidth[valueWidth.ordinal()]) {
                case 1: {
                    char[] cArray2 = ICUBinary.getChars(byteBuffer, n2, 0);
                    CodePointTrie codePointTrie = type == Type.FAST ? new Fast16(cArray, cArray2, n6, (int)c3, n3) : new Small16(cArray, cArray2, n6, (int)c3, n3);
                    return codePointTrie;
                }
                case 2: {
                    int[] nArray = ICUBinary.getInts(byteBuffer, n2, 0);
                    CodePointTrie codePointTrie = type == Type.FAST ? new Fast32(cArray, nArray, n6, (int)c3, n3) : new Small32(cArray, nArray, n6, (int)c3, n3);
                    return codePointTrie;
                }
                case 3: {
                    byte[] byArray = ICUBinary.getBytes(byteBuffer, n2, 0);
                    CodePointTrie codePointTrie = type == Type.FAST ? new Fast8(cArray, byArray, n6, (int)c3, n3) : new Small8(cArray, byArray, n6, (int)c3, n3);
                    return codePointTrie;
                }
            }
            throw new AssertionError((Object)"should be unreachable");
        } finally {
            byteBuffer.order(byteOrder);
        }
    }

    public abstract Type getType();

    public final ValueWidth getValueWidth() {
        return this.data.getValueWidth();
    }

    @Override
    public int get(int n) {
        return this.data.getFromIndex(this.cpIndex(n));
    }

    public final int asciiGet(int n) {
        return this.ascii[n];
    }

    private static final int maybeFilterValue(int n, int n2, int n3, CodePointMap.ValueFilter valueFilter) {
        if (n == n2) {
            n = n3;
        } else if (valueFilter != null) {
            n = valueFilter.apply(n);
        }
        return n;
    }

    @Override
    public final boolean getRange(int n, CodePointMap.ValueFilter valueFilter, CodePointMap.Range range) {
        int n2;
        int n3;
        if (n < 0 || 0x10FFFF < n) {
            return true;
        }
        if (n >= this.highStart) {
            int n4 = this.dataLength - 2;
            int n5 = this.data.getFromIndex(n4);
            if (valueFilter != null) {
                n5 = valueFilter.apply(n5);
            }
            range.set(n, 0x10FFFF, n5);
            return false;
        }
        int n6 = this.nullValue;
        if (valueFilter != null) {
            n6 = valueFilter.apply(n6);
        }
        Type type = this.getType();
        int n7 = -1;
        int n8 = -1;
        int n9 = n;
        int n10 = 0;
        int n11 = 0;
        boolean bl = false;
        do {
            int n12;
            int n13;
            int n14;
            if (n9 <= 65535 && (type == Type.FAST || n9 <= 4095)) {
                n3 = 0;
                n2 = n9 >> 6;
                n14 = type == Type.FAST ? 1024 : 64;
                n13 = 64;
            } else {
                n12 = n9 >> 14;
                if (type == Type.FAST) {
                    if (!($assertionsDisabled || 65535 < n9 && n9 < this.highStart)) {
                        throw new AssertionError();
                    }
                    n12 += 1020;
                } else {
                    if (!($assertionsDisabled || n9 < this.highStart && this.highStart > 4096)) {
                        throw new AssertionError();
                    }
                    n12 += 64;
                }
                n3 = this.index[this.index[n12] + (n9 >> 9 & 0x1F)];
                if (n3 == n7 && n9 - n >= 512) {
                    if (!$assertionsDisabled && (n9 & 0x1FF) != 0) {
                        throw new AssertionError();
                    }
                    n9 += 512;
                    continue;
                }
                n7 = n3;
                if (n3 == this.index3NullOffset) {
                    if (bl) {
                        if (n6 != n11) {
                            range.set(n, n9 - 1, n11);
                            return false;
                        }
                    } else {
                        n10 = this.nullValue;
                        n11 = n6;
                        bl = true;
                    }
                    n8 = this.dataNullOffset;
                    n9 = n9 + 512 & 0xFFFFFE00;
                    continue;
                }
                n2 = n9 >> 4 & 0x1F;
                n14 = 32;
                n13 = 16;
            }
            do {
                int n15;
                int n16;
                if ((n3 & 0x8000) == 0) {
                    n12 = this.index[n3 + n2];
                } else {
                    n16 = (n3 & Short.MAX_VALUE) + (n2 & 0xFFFFFFF8) + (n2 >> 3);
                    n15 = n2 & 7;
                    n12 = this.index[n16++] << 2 + 2 * n15 & 0x30000;
                    n12 |= this.index[n16 + n15];
                }
                if (n12 == n8 && n9 - n >= n13) {
                    if (!$assertionsDisabled && (n9 & n13 - 1) != 0) {
                        throw new AssertionError();
                    }
                    n9 += n13;
                    continue;
                }
                n16 = n13 - 1;
                n8 = n12;
                if (n12 == this.dataNullOffset) {
                    if (bl) {
                        if (n6 != n11) {
                            range.set(n, n9 - 1, n11);
                            return false;
                        }
                    } else {
                        n10 = this.nullValue;
                        n11 = n6;
                        bl = true;
                    }
                    n9 = n9 + n13 & ~n16;
                    continue;
                }
                n15 = n12 + (n9 & n16);
                int n17 = this.data.getFromIndex(n15);
                if (bl) {
                    if (n17 != n10) {
                        if (valueFilter == null || CodePointTrie.maybeFilterValue(n17, this.nullValue, n6, valueFilter) != n11) {
                            range.set(n, n9 - 1, n11);
                            return false;
                        }
                        n10 = n17;
                    }
                } else {
                    n10 = n17;
                    n11 = CodePointTrie.maybeFilterValue(n17, this.nullValue, n6, valueFilter);
                    bl = true;
                }
                while ((++n9 & n16) != 0) {
                    if ((n17 = this.data.getFromIndex(++n15)) == n10) continue;
                    if (valueFilter == null || CodePointTrie.maybeFilterValue(n17, this.nullValue, n6, valueFilter) != n11) {
                        range.set(n, n9 - 1, n11);
                        return false;
                    }
                    n10 = n17;
                }
            } while (++n2 < n14);
        } while (n9 < this.highStart);
        if (!$assertionsDisabled && !bl) {
            throw new AssertionError();
        }
        n3 = this.dataLength - 2;
        n2 = this.data.getFromIndex(n3);
        n9 = CodePointTrie.maybeFilterValue(n2, this.nullValue, n6, valueFilter) != n11 ? --n9 : 0x10FFFF;
        range.set(n, n9, n11);
        return false;
    }

    public final int toBinary(OutputStream outputStream) {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeInt(1416784179);
            dataOutputStream.writeChar((this.dataLength & 0xF0000) >> 4 | (this.dataNullOffset & 0xF0000) >> 8 | this.getType().ordinal() << 6 | this.getValueWidth().ordinal());
            dataOutputStream.writeChar(this.index.length);
            dataOutputStream.writeChar(this.dataLength);
            dataOutputStream.writeChar(this.index3NullOffset);
            dataOutputStream.writeChar(this.dataNullOffset);
            dataOutputStream.writeChar(this.highStart >> 9);
            int n = 16;
            for (char c : this.index) {
                dataOutputStream.writeChar(c);
            }
            n += this.index.length * 2;
            return n += this.data.write(dataOutputStream);
        } catch (IOException iOException) {
            throw new ICUUncheckedIOException(iOException);
        }
    }

    @Deprecated
    protected final int fastIndex(int n) {
        return this.index[n >> 6] + (n & 0x3F);
    }

    @Deprecated
    protected final int smallIndex(Type type, int n) {
        if (n >= this.highStart) {
            return this.dataLength - 2;
        }
        return this.internalSmallIndex(type, n);
    }

    private final int internalSmallIndex(Type type, int n) {
        int n2;
        int n3 = n >> 14;
        if (type == Type.FAST) {
            if (!($assertionsDisabled || 65535 < n && n < this.highStart)) {
                throw new AssertionError();
            }
            n3 += 1020;
        } else {
            if (!($assertionsDisabled || 0 <= n && n < this.highStart && this.highStart > 4096)) {
                throw new AssertionError();
            }
            n3 += 64;
        }
        int n4 = this.index[this.index[n3] + (n >> 9 & 0x1F)];
        int n5 = n >> 4 & 0x1F;
        if ((n4 & 0x8000) == 0) {
            n2 = this.index[n4 + n5];
        } else {
            n4 = (n4 & Short.MAX_VALUE) + (n5 & 0xFFFFFFF8) + (n5 >> 3);
            n2 = this.index[n4++] << 2 + 2 * (n5 &= 7) & 0x30000;
            n2 |= this.index[n4 + n5];
        }
        return n2 + (n & 0xF);
    }

    @Deprecated
    protected abstract int cpIndex(int var1);

    CodePointTrie(char[] cArray, Data data, int n, int n2, int n3, 1 var6_6) {
        this(cArray, data, n, n2, n3);
    }

    public static final class Small8
    extends Small {
        Small8(char[] cArray, byte[] byArray, int n, int n2, int n3) {
            super(cArray, new Data8(byArray), n, n2, n3, null);
        }

        public static Small8 fromBinary(ByteBuffer byteBuffer) {
            return (Small8)CodePointTrie.fromBinary(Type.SMALL, ValueWidth.BITS_8, byteBuffer);
        }
    }

    public static final class Small32
    extends Small {
        Small32(char[] cArray, int[] nArray, int n, int n2, int n3) {
            super(cArray, new Data32(nArray), n, n2, n3, null);
        }

        public static Small32 fromBinary(ByteBuffer byteBuffer) {
            return (Small32)CodePointTrie.fromBinary(Type.SMALL, ValueWidth.BITS_32, byteBuffer);
        }
    }

    public static final class Small16
    extends Small {
        Small16(char[] cArray, char[] cArray2, int n, int n2, int n3) {
            super(cArray, new Data16(cArray2), n, n2, n3, null);
        }

        public static Small16 fromBinary(ByteBuffer byteBuffer) {
            return (Small16)CodePointTrie.fromBinary(Type.SMALL, ValueWidth.BITS_16, byteBuffer);
        }
    }

    public static final class Fast8
    extends Fast {
        private final byte[] dataArray;
        static final boolean $assertionsDisabled = !CodePointTrie.class.desiredAssertionStatus();

        Fast8(char[] cArray, byte[] byArray, int n, int n2, int n3) {
            super(cArray, new Data8(byArray), n, n2, n3, null);
            this.dataArray = byArray;
        }

        public static Fast8 fromBinary(ByteBuffer byteBuffer) {
            return (Fast8)CodePointTrie.fromBinary(Type.FAST, ValueWidth.BITS_8, byteBuffer);
        }

        @Override
        public final int get(int n) {
            return this.dataArray[this.cpIndex(n)] & 0xFF;
        }

        @Override
        public final int bmpGet(int n) {
            if (!($assertionsDisabled || 0 <= n && n <= 65535)) {
                throw new AssertionError();
            }
            return this.dataArray[this.fastIndex(n)] & 0xFF;
        }

        @Override
        public final int suppGet(int n) {
            if (!($assertionsDisabled || 65536 <= n && n <= 0x10FFFF)) {
                throw new AssertionError();
            }
            return this.dataArray[this.smallIndex(Type.FAST, n)] & 0xFF;
        }
    }

    public static final class Fast32
    extends Fast {
        private final int[] dataArray;
        static final boolean $assertionsDisabled = !CodePointTrie.class.desiredAssertionStatus();

        Fast32(char[] cArray, int[] nArray, int n, int n2, int n3) {
            super(cArray, new Data32(nArray), n, n2, n3, null);
            this.dataArray = nArray;
        }

        public static Fast32 fromBinary(ByteBuffer byteBuffer) {
            return (Fast32)CodePointTrie.fromBinary(Type.FAST, ValueWidth.BITS_32, byteBuffer);
        }

        @Override
        public final int get(int n) {
            return this.dataArray[this.cpIndex(n)];
        }

        @Override
        public final int bmpGet(int n) {
            if (!($assertionsDisabled || 0 <= n && n <= 65535)) {
                throw new AssertionError();
            }
            return this.dataArray[this.fastIndex(n)];
        }

        @Override
        public final int suppGet(int n) {
            if (!($assertionsDisabled || 65536 <= n && n <= 0x10FFFF)) {
                throw new AssertionError();
            }
            return this.dataArray[this.smallIndex(Type.FAST, n)];
        }
    }

    public static final class Fast16
    extends Fast {
        private final char[] dataArray;
        static final boolean $assertionsDisabled = !CodePointTrie.class.desiredAssertionStatus();

        Fast16(char[] cArray, char[] cArray2, int n, int n2, int n3) {
            super(cArray, new Data16(cArray2), n, n2, n3, null);
            this.dataArray = cArray2;
        }

        public static Fast16 fromBinary(ByteBuffer byteBuffer) {
            return (Fast16)CodePointTrie.fromBinary(Type.FAST, ValueWidth.BITS_16, byteBuffer);
        }

        @Override
        public final int get(int n) {
            return this.dataArray[this.cpIndex(n)];
        }

        @Override
        public final int bmpGet(int n) {
            if (!($assertionsDisabled || 0 <= n && n <= 65535)) {
                throw new AssertionError();
            }
            return this.dataArray[this.fastIndex(n)];
        }

        @Override
        public final int suppGet(int n) {
            if (!($assertionsDisabled || 65536 <= n && n <= 0x10FFFF)) {
                throw new AssertionError();
            }
            return this.dataArray[this.smallIndex(Type.FAST, n)];
        }
    }

    public static abstract class Small
    extends CodePointTrie {
        private Small(char[] cArray, Data data, int n, int n2, int n3) {
            super(cArray, data, n, n2, n3, null);
        }

        public static Small fromBinary(ValueWidth valueWidth, ByteBuffer byteBuffer) {
            return (Small)CodePointTrie.fromBinary(Type.SMALL, valueWidth, byteBuffer);
        }

        @Override
        public final Type getType() {
            return Type.SMALL;
        }

        @Override
        @Deprecated
        protected final int cpIndex(int n) {
            if (n >= 0) {
                if (n <= 4095) {
                    return this.fastIndex(n);
                }
                if (n <= 0x10FFFF) {
                    return this.smallIndex(Type.SMALL, n);
                }
            }
            return this.dataLength - 1;
        }

        @Override
        public final CodePointMap.StringIterator stringIterator(CharSequence charSequence, int n) {
            return new SmallStringIterator(this, charSequence, n, null);
        }

        Small(char[] cArray, Data data, int n, int n2, int n3, 1 var6_6) {
            this(cArray, data, n, n2, n3);
        }

        private final class SmallStringIterator
        extends CodePointMap.StringIterator {
            final Small this$0;

            private SmallStringIterator(Small small, CharSequence charSequence, int n) {
                this.this$0 = small;
                super(small, charSequence, n);
            }

            @Override
            public boolean next() {
                char c;
                int n;
                if (this.sIndex >= this.s.length()) {
                    return true;
                }
                char c2 = this.s.charAt(this.sIndex++);
                this.c = c2;
                if (!Character.isSurrogate(c2)) {
                    n = this.this$0.cpIndex(this.c);
                } else if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c2) && this.sIndex < this.s.length() && Character.isLowSurrogate(c = this.s.charAt(this.sIndex))) {
                    ++this.sIndex;
                    this.c = Character.toCodePoint(c2, c);
                    n = this.this$0.smallIndex(Type.SMALL, this.c);
                } else {
                    n = this.this$0.dataLength - 1;
                }
                this.value = this.this$0.data.getFromIndex(n);
                return false;
            }

            @Override
            public boolean previous() {
                char c;
                int n;
                if (this.sIndex <= 0) {
                    return true;
                }
                char c2 = this.s.charAt(--this.sIndex);
                this.c = c2;
                if (!Character.isSurrogate(c2)) {
                    n = this.this$0.cpIndex(this.c);
                } else if (!Normalizer2Impl.UTF16Plus.isSurrogateLead(c2) && this.sIndex > 0 && Character.isHighSurrogate(c = this.s.charAt(this.sIndex - 1))) {
                    --this.sIndex;
                    this.c = Character.toCodePoint(c, c2);
                    n = this.this$0.smallIndex(Type.SMALL, this.c);
                } else {
                    n = this.this$0.dataLength - 1;
                }
                this.value = this.this$0.data.getFromIndex(n);
                return false;
            }

            SmallStringIterator(Small small, CharSequence charSequence, int n, 1 var4_4) {
                this(small, charSequence, n);
            }
        }
    }

    public static abstract class Fast
    extends CodePointTrie {
        private Fast(char[] cArray, Data data, int n, int n2, int n3) {
            super(cArray, data, n, n2, n3, null);
        }

        public static Fast fromBinary(ValueWidth valueWidth, ByteBuffer byteBuffer) {
            return (Fast)CodePointTrie.fromBinary(Type.FAST, valueWidth, byteBuffer);
        }

        @Override
        public final Type getType() {
            return Type.FAST;
        }

        public abstract int bmpGet(int var1);

        public abstract int suppGet(int var1);

        @Override
        @Deprecated
        protected final int cpIndex(int n) {
            if (n >= 0) {
                if (n <= 65535) {
                    return this.fastIndex(n);
                }
                if (n <= 0x10FFFF) {
                    return this.smallIndex(Type.FAST, n);
                }
            }
            return this.dataLength - 1;
        }

        @Override
        public final CodePointMap.StringIterator stringIterator(CharSequence charSequence, int n) {
            return new FastStringIterator(this, charSequence, n, null);
        }

        Fast(char[] cArray, Data data, int n, int n2, int n3, 1 var6_6) {
            this(cArray, data, n, n2, n3);
        }

        private final class FastStringIterator
        extends CodePointMap.StringIterator {
            final Fast this$0;

            private FastStringIterator(Fast fast, CharSequence charSequence, int n) {
                this.this$0 = fast;
                super(fast, charSequence, n);
            }

            @Override
            public boolean next() {
                char c;
                int n;
                if (this.sIndex >= this.s.length()) {
                    return true;
                }
                char c2 = this.s.charAt(this.sIndex++);
                this.c = c2;
                if (!Character.isSurrogate(c2)) {
                    n = this.this$0.fastIndex(this.c);
                } else if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c2) && this.sIndex < this.s.length() && Character.isLowSurrogate(c = this.s.charAt(this.sIndex))) {
                    ++this.sIndex;
                    this.c = Character.toCodePoint(c2, c);
                    n = this.this$0.smallIndex(Type.FAST, this.c);
                } else {
                    n = this.this$0.dataLength - 1;
                }
                this.value = this.this$0.data.getFromIndex(n);
                return false;
            }

            @Override
            public boolean previous() {
                char c;
                int n;
                if (this.sIndex <= 0) {
                    return true;
                }
                char c2 = this.s.charAt(--this.sIndex);
                this.c = c2;
                if (!Character.isSurrogate(c2)) {
                    n = this.this$0.fastIndex(this.c);
                } else if (!Normalizer2Impl.UTF16Plus.isSurrogateLead(c2) && this.sIndex > 0 && Character.isHighSurrogate(c = this.s.charAt(this.sIndex - 1))) {
                    --this.sIndex;
                    this.c = Character.toCodePoint(c, c2);
                    n = this.this$0.smallIndex(Type.FAST, this.c);
                } else {
                    n = this.this$0.dataLength - 1;
                }
                this.value = this.this$0.data.getFromIndex(n);
                return false;
            }

            FastStringIterator(Fast fast, CharSequence charSequence, int n, 1 var4_4) {
                this(fast, charSequence, n);
            }
        }
    }

    private static final class Data8
    extends Data {
        byte[] array;

        Data8(byte[] byArray) {
            super(null);
            this.array = byArray;
        }

        @Override
        ValueWidth getValueWidth() {
            return ValueWidth.BITS_8;
        }

        @Override
        int getDataLength() {
            return this.array.length;
        }

        @Override
        int getFromIndex(int n) {
            return this.array[n] & 0xFF;
        }

        @Override
        int write(DataOutputStream dataOutputStream) throws IOException {
            for (byte by : this.array) {
                dataOutputStream.writeByte(by);
            }
            return this.array.length;
        }
    }

    private static final class Data32
    extends Data {
        int[] array;

        Data32(int[] nArray) {
            super(null);
            this.array = nArray;
        }

        @Override
        ValueWidth getValueWidth() {
            return ValueWidth.BITS_32;
        }

        @Override
        int getDataLength() {
            return this.array.length;
        }

        @Override
        int getFromIndex(int n) {
            return this.array[n];
        }

        @Override
        int write(DataOutputStream dataOutputStream) throws IOException {
            for (int n : this.array) {
                dataOutputStream.writeInt(n);
            }
            return this.array.length * 4;
        }
    }

    private static final class Data16
    extends Data {
        char[] array;

        Data16(char[] cArray) {
            super(null);
            this.array = cArray;
        }

        @Override
        ValueWidth getValueWidth() {
            return ValueWidth.BITS_16;
        }

        @Override
        int getDataLength() {
            return this.array.length;
        }

        @Override
        int getFromIndex(int n) {
            return this.array[n];
        }

        @Override
        int write(DataOutputStream dataOutputStream) throws IOException {
            for (char c : this.array) {
                dataOutputStream.writeChar(c);
            }
            return this.array.length * 2;
        }
    }

    private static abstract class Data {
        private Data() {
        }

        abstract ValueWidth getValueWidth();

        abstract int getDataLength();

        abstract int getFromIndex(int var1);

        abstract int write(DataOutputStream var1) throws IOException;

        Data(1 var1_1) {
            this();
        }
    }

    public static enum ValueWidth {
        BITS_16,
        BITS_32,
        BITS_8;

    }

    public static enum Type {
        FAST,
        SMALL;

    }
}

