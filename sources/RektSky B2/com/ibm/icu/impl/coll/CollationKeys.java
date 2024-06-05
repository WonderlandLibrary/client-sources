package com.ibm.icu.impl.coll;

public final class CollationKeys
{
    public static final LevelCallback SIMPLE_LEVEL_FALLBACK;
    private static final int SEC_COMMON_LOW = 5;
    private static final int SEC_COMMON_MIDDLE = 37;
    static final int SEC_COMMON_HIGH = 69;
    private static final int SEC_COMMON_MAX_COUNT = 33;
    private static final int CASE_LOWER_FIRST_COMMON_LOW = 1;
    private static final int CASE_LOWER_FIRST_COMMON_MIDDLE = 7;
    private static final int CASE_LOWER_FIRST_COMMON_HIGH = 13;
    private static final int CASE_LOWER_FIRST_COMMON_MAX_COUNT = 7;
    private static final int CASE_UPPER_FIRST_COMMON_LOW = 3;
    private static final int CASE_UPPER_FIRST_COMMON_HIGH = 15;
    private static final int CASE_UPPER_FIRST_COMMON_MAX_COUNT = 13;
    private static final int TER_ONLY_COMMON_LOW = 5;
    private static final int TER_ONLY_COMMON_MIDDLE = 101;
    private static final int TER_ONLY_COMMON_HIGH = 197;
    private static final int TER_ONLY_COMMON_MAX_COUNT = 97;
    private static final int TER_LOWER_FIRST_COMMON_LOW = 5;
    private static final int TER_LOWER_FIRST_COMMON_MIDDLE = 37;
    private static final int TER_LOWER_FIRST_COMMON_HIGH = 69;
    private static final int TER_LOWER_FIRST_COMMON_MAX_COUNT = 33;
    private static final int TER_UPPER_FIRST_COMMON_LOW = 133;
    private static final int TER_UPPER_FIRST_COMMON_MIDDLE = 165;
    private static final int TER_UPPER_FIRST_COMMON_HIGH = 197;
    private static final int TER_UPPER_FIRST_COMMON_MAX_COUNT = 33;
    private static final int QUAT_COMMON_LOW = 28;
    private static final int QUAT_COMMON_MIDDLE = 140;
    private static final int QUAT_COMMON_HIGH = 252;
    private static final int QUAT_COMMON_MAX_COUNT = 113;
    private static final int QUAT_SHIFTED_LIMIT_BYTE = 27;
    private static final int[] levelMasks;
    
    private static SortKeyLevel getSortKeyLevel(final int levels, final int level) {
        return ((levels & level) != 0x0) ? new SortKeyLevel() : null;
    }
    
    private CollationKeys() {
    }
    
    public static void writeSortKeyUpToQuaternary(final CollationIterator iter, final boolean[] compressibleBytes, final CollationSettings settings, final SortKeyByteSink sink, final int minLevel, final LevelCallback callback, final boolean preflight) {
        final int options = settings.options;
        int levels = CollationKeys.levelMasks[CollationSettings.getStrength(options)];
        if ((options & 0x400) != 0x0) {
            levels |= 0x8;
        }
        levels &= ~((1 << minLevel) - 1);
        if (levels == 0) {
            return;
        }
        long variableTop;
        if ((options & 0xC) == 0x0) {
            variableTop = 0L;
        }
        else {
            variableTop = settings.variableTop + 1L;
        }
        final int tertiaryMask = CollationSettings.getTertiaryMask(options);
        final byte[] p234 = new byte[3];
        final SortKeyLevel cases = getSortKeyLevel(levels, 8);
        final SortKeyLevel secondaries = getSortKeyLevel(levels, 4);
        final SortKeyLevel tertiaries = getSortKeyLevel(levels, 16);
        final SortKeyLevel quaternaries = getSortKeyLevel(levels, 32);
        long prevReorderedPrimary = 0L;
        int commonCases = 0;
        int commonSecondaries = 0;
        int commonTertiaries = 0;
        int commonQuaternaries = 0;
        int prevSecondary = 0;
        int secSegmentStart = 0;
        while (true) {
            iter.clearCEsIfNoneRemaining();
            long ce = iter.nextCE();
            long p235 = ce >>> 32;
            if (p235 < variableTop && p235 > 33554432L) {
                if (commonQuaternaries != 0) {
                    --commonQuaternaries;
                    while (commonQuaternaries >= 113) {
                        quaternaries.appendByte(140);
                        commonQuaternaries -= 113;
                    }
                    quaternaries.appendByte(28 + commonQuaternaries);
                    commonQuaternaries = 0;
                }
                do {
                    if ((levels & 0x20) != 0x0) {
                        if (settings.hasReordering()) {
                            p235 = settings.reorder(p235);
                        }
                        if ((int)p235 >>> 24 >= 27) {
                            quaternaries.appendByte(27);
                        }
                        quaternaries.appendWeight32(p235);
                    }
                    do {
                        ce = iter.nextCE();
                        p235 = ce >>> 32;
                    } while (p235 == 0L);
                } while (p235 < variableTop && p235 > 33554432L);
            }
            if (p235 > 1L && (levels & 0x2) != 0x0) {
                final boolean isCompressible = compressibleBytes[(int)p235 >>> 24];
                if (settings.hasReordering()) {
                    p235 = settings.reorder(p235);
                }
                final int p236 = (int)p235 >>> 24;
                if (!isCompressible || p236 != (int)prevReorderedPrimary >>> 24) {
                    if (prevReorderedPrimary != 0L) {
                        if (p235 < prevReorderedPrimary) {
                            if (p236 > 2) {
                                sink.Append(3);
                            }
                        }
                        else {
                            sink.Append(255);
                        }
                    }
                    sink.Append(p236);
                    if (isCompressible) {
                        prevReorderedPrimary = p235;
                    }
                    else {
                        prevReorderedPrimary = 0L;
                    }
                }
                final byte p237 = (byte)(p235 >>> 16);
                if (p237 != 0) {
                    p234[0] = p237;
                    p234[1] = (byte)(p235 >>> 8);
                    p234[2] = (byte)p235;
                    sink.Append(p234, (p234[1] == 0) ? 1 : ((p234[2] == 0) ? 2 : 3));
                }
                if (!preflight && sink.Overflowed()) {
                    return;
                }
            }
            final int lower32 = (int)ce;
            if (lower32 == 0) {
                continue;
            }
            if ((levels & 0x4) != 0x0) {
                final int s = lower32 >>> 16;
                if (s != 0) {
                    if (s == 1280 && ((options & 0x800) == 0x0 || p235 != 33554432L)) {
                        ++commonSecondaries;
                    }
                    else if ((options & 0x800) == 0x0) {
                        if (commonSecondaries != 0) {
                            --commonSecondaries;
                            while (commonSecondaries >= 33) {
                                secondaries.appendByte(37);
                                commonSecondaries -= 33;
                            }
                            int b;
                            if (s < 1280) {
                                b = 5 + commonSecondaries;
                            }
                            else {
                                b = 69 - commonSecondaries;
                            }
                            secondaries.appendByte(b);
                            commonSecondaries = 0;
                        }
                        secondaries.appendWeight16(s);
                    }
                    else {
                        if (commonSecondaries != 0) {
                            final int remainder = --commonSecondaries % 33;
                            int b2;
                            if (prevSecondary < 1280) {
                                b2 = 5 + remainder;
                            }
                            else {
                                b2 = 69 - remainder;
                            }
                            secondaries.appendByte(b2);
                            for (commonSecondaries -= remainder; commonSecondaries > 0; commonSecondaries -= 33) {
                                secondaries.appendByte(37);
                            }
                        }
                        if (0L < p235 && p235 <= 33554432L) {
                            final byte[] secs = secondaries.data();
                            byte b3;
                            for (int last = secondaries.length() - 1; secSegmentStart < last; secs[secSegmentStart++] = secs[last], secs[last--] = b3) {
                                b3 = secs[secSegmentStart];
                            }
                            secondaries.appendByte((p235 == 1L) ? 1 : 2);
                            prevSecondary = 0;
                            secSegmentStart = secondaries.length();
                        }
                        else {
                            secondaries.appendReverseWeight16(s);
                            prevSecondary = s;
                        }
                    }
                }
            }
            Label_1103: {
                if ((levels & 0x8) != 0x0) {
                    if (CollationSettings.getStrength(options) == 0) {
                        if (p235 == 0L) {
                            break Label_1103;
                        }
                    }
                    else if (lower32 >>> 16 == 0) {
                        break Label_1103;
                    }
                    int c = lower32 >>> 8 & 0xFF;
                    assert (c & 0xC0) != 0xC0;
                    if ((c & 0xC0) == 0x0 && c > 1) {
                        ++commonCases;
                    }
                    else {
                        if ((options & 0x100) == 0x0) {
                            if (commonCases != 0 && (c > 1 || !cases.isEmpty())) {
                                --commonCases;
                                while (commonCases >= 7) {
                                    cases.appendByte(112);
                                    commonCases -= 7;
                                }
                                int b;
                                if (c <= 1) {
                                    b = 1 + commonCases;
                                }
                                else {
                                    b = 13 - commonCases;
                                }
                                cases.appendByte(b << 4);
                                commonCases = 0;
                            }
                            if (c > 1) {
                                c = 13 + (c >>> 6) << 4;
                            }
                        }
                        else {
                            if (commonCases != 0) {
                                --commonCases;
                                while (commonCases >= 13) {
                                    cases.appendByte(48);
                                    commonCases -= 13;
                                }
                                cases.appendByte(3 + commonCases << 4);
                                commonCases = 0;
                            }
                            if (c > 1) {
                                c = 3 - (c >>> 6) << 4;
                            }
                        }
                        cases.appendByte(c);
                    }
                }
            }
            if ((levels & 0x10) != 0x0) {
                int t = lower32 & tertiaryMask;
                assert (lower32 & 0xC000) != 0xC000;
                if (t == 1280) {
                    ++commonTertiaries;
                }
                else if ((tertiaryMask & 0x8000) == 0x0) {
                    if (commonTertiaries != 0) {
                        --commonTertiaries;
                        while (commonTertiaries >= 97) {
                            tertiaries.appendByte(101);
                            commonTertiaries -= 97;
                        }
                        int b;
                        if (t < 1280) {
                            b = 5 + commonTertiaries;
                        }
                        else {
                            b = 197 - commonTertiaries;
                        }
                        tertiaries.appendByte(b);
                        commonTertiaries = 0;
                    }
                    if (t > 1280) {
                        t += 49152;
                    }
                    tertiaries.appendWeight16(t);
                }
                else if ((options & 0x100) == 0x0) {
                    if (commonTertiaries != 0) {
                        --commonTertiaries;
                        while (commonTertiaries >= 33) {
                            tertiaries.appendByte(37);
                            commonTertiaries -= 33;
                        }
                        int b;
                        if (t < 1280) {
                            b = 5 + commonTertiaries;
                        }
                        else {
                            b = 69 - commonTertiaries;
                        }
                        tertiaries.appendByte(b);
                        commonTertiaries = 0;
                    }
                    if (t > 1280) {
                        t += 16384;
                    }
                    tertiaries.appendWeight16(t);
                }
                else {
                    if (t > 256) {
                        if (lower32 >>> 16 != 0) {
                            t ^= 0xC000;
                            if (t < 50432) {
                                t -= 16384;
                            }
                        }
                        else {
                            assert 34304 <= t && t <= 49151;
                            t += 16384;
                        }
                    }
                    if (commonTertiaries != 0) {
                        --commonTertiaries;
                        while (commonTertiaries >= 33) {
                            tertiaries.appendByte(165);
                            commonTertiaries -= 33;
                        }
                        int b;
                        if (t < 34048) {
                            b = 133 + commonTertiaries;
                        }
                        else {
                            b = 197 - commonTertiaries;
                        }
                        tertiaries.appendByte(b);
                        commonTertiaries = 0;
                    }
                    tertiaries.appendWeight16(t);
                }
            }
            if ((levels & 0x20) != 0x0) {
                int q = lower32 & 0xFFFF;
                if ((q & 0xC0) == 0x0 && q > 256) {
                    ++commonQuaternaries;
                }
                else if (q == 256 && (options & 0xC) == 0x0 && quaternaries.isEmpty()) {
                    quaternaries.appendByte(1);
                }
                else {
                    if (q == 256) {
                        q = 1;
                    }
                    else {
                        q = 252 + (q >>> 6 & 0x3);
                    }
                    if (commonQuaternaries != 0) {
                        --commonQuaternaries;
                        while (commonQuaternaries >= 113) {
                            quaternaries.appendByte(140);
                            commonQuaternaries -= 113;
                        }
                        int b;
                        if (q < 28) {
                            b = 28 + commonQuaternaries;
                        }
                        else {
                            b = 252 - commonQuaternaries;
                        }
                        quaternaries.appendByte(b);
                        commonQuaternaries = 0;
                    }
                    quaternaries.appendByte(q);
                }
            }
            if (lower32 >>> 24 == 1) {
                if ((levels & 0x4) != 0x0) {
                    if (!callback.needToWrite(2)) {
                        return;
                    }
                    sink.Append(1);
                    secondaries.appendTo(sink);
                }
                if ((levels & 0x8) != 0x0) {
                    if (!callback.needToWrite(3)) {
                        return;
                    }
                    sink.Append(1);
                    final int length = cases.length() - 1;
                    byte b4 = 0;
                    for (int i = 0; i < length; ++i) {
                        final byte c2 = cases.getAt(i);
                        assert (c2 & 0xF) == 0x0 && c2 != 0;
                        if (b4 == 0) {
                            b4 = c2;
                        }
                        else {
                            sink.Append(b4 | (c2 >> 4 & 0xF));
                            b4 = 0;
                        }
                    }
                    if (b4 != 0) {
                        sink.Append(b4);
                    }
                }
                if ((levels & 0x10) != 0x0) {
                    if (!callback.needToWrite(4)) {
                        return;
                    }
                    sink.Append(1);
                    tertiaries.appendTo(sink);
                }
                if ((levels & 0x20) != 0x0) {
                    if (!callback.needToWrite(5)) {
                        return;
                    }
                    sink.Append(1);
                    quaternaries.appendTo(sink);
                }
            }
        }
    }
    
    static {
        SIMPLE_LEVEL_FALLBACK = new LevelCallback();
        levelMasks = new int[] { 2, 6, 22, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 54 };
    }
    
    public abstract static class SortKeyByteSink
    {
        protected byte[] buffer_;
        private int appended_;
        
        public SortKeyByteSink(final byte[] dest) {
            this.appended_ = 0;
            this.buffer_ = dest;
        }
        
        public void setBufferAndAppended(final byte[] dest, final int app) {
            this.buffer_ = dest;
            this.appended_ = app;
        }
        
        public void Append(final byte[] bytes, final int n) {
            if (n <= 0 || bytes == null) {
                return;
            }
            final int length = this.appended_;
            this.appended_ += n;
            final int available = this.buffer_.length - length;
            if (n <= available) {
                System.arraycopy(bytes, 0, this.buffer_, length, n);
            }
            else {
                this.AppendBeyondCapacity(bytes, 0, n, length);
            }
        }
        
        public void Append(final int b) {
            if (this.appended_ < this.buffer_.length || this.Resize(1, this.appended_)) {
                this.buffer_[this.appended_] = (byte)b;
            }
            ++this.appended_;
        }
        
        public int NumberOfBytesAppended() {
            return this.appended_;
        }
        
        public int GetRemainingCapacity() {
            return this.buffer_.length - this.appended_;
        }
        
        public boolean Overflowed() {
            return this.appended_ > this.buffer_.length;
        }
        
        protected abstract void AppendBeyondCapacity(final byte[] p0, final int p1, final int p2, final int p3);
        
        protected abstract boolean Resize(final int p0, final int p1);
    }
    
    public static class LevelCallback
    {
        boolean needToWrite(final int level) {
            return true;
        }
    }
    
    private static final class SortKeyLevel
    {
        private static final int INITIAL_CAPACITY = 40;
        byte[] buffer;
        int len;
        
        SortKeyLevel() {
            this.buffer = new byte[40];
            this.len = 0;
        }
        
        boolean isEmpty() {
            return this.len == 0;
        }
        
        int length() {
            return this.len;
        }
        
        byte getAt(final int index) {
            return this.buffer[index];
        }
        
        byte[] data() {
            return this.buffer;
        }
        
        void appendByte(final int b) {
            if (this.len < this.buffer.length || this.ensureCapacity(1)) {
                this.buffer[this.len++] = (byte)b;
            }
        }
        
        void appendWeight16(final int w) {
            assert (w & 0xFFFF) != 0x0;
            final byte b0 = (byte)(w >>> 8);
            final byte b2 = (byte)w;
            final int appendLength = (b2 == 0) ? 1 : 2;
            if (this.len + appendLength <= this.buffer.length || this.ensureCapacity(appendLength)) {
                this.buffer[this.len++] = b0;
                if (b2 != 0) {
                    this.buffer[this.len++] = b2;
                }
            }
        }
        
        void appendWeight32(final long w) {
            assert w != 0L;
            final byte[] bytes = { (byte)(w >>> 24), (byte)(w >>> 16), (byte)(w >>> 8), (byte)w };
            final int appendLength = (bytes[1] == 0) ? 1 : ((bytes[2] == 0) ? 2 : ((bytes[3] == 0) ? 3 : 4));
            if (this.len + appendLength <= this.buffer.length || this.ensureCapacity(appendLength)) {
                this.buffer[this.len++] = bytes[0];
                if (bytes[1] != 0) {
                    this.buffer[this.len++] = bytes[1];
                    if (bytes[2] != 0) {
                        this.buffer[this.len++] = bytes[2];
                        if (bytes[3] != 0) {
                            this.buffer[this.len++] = bytes[3];
                        }
                    }
                }
            }
        }
        
        void appendReverseWeight16(final int w) {
            assert (w & 0xFFFF) != 0x0;
            final byte b0 = (byte)(w >>> 8);
            final byte b2 = (byte)w;
            final int appendLength = (b2 == 0) ? 1 : 2;
            if (this.len + appendLength <= this.buffer.length || this.ensureCapacity(appendLength)) {
                if (b2 == 0) {
                    this.buffer[this.len++] = b0;
                }
                else {
                    this.buffer[this.len] = b2;
                    this.buffer[this.len + 1] = b0;
                    this.len += 2;
                }
            }
        }
        
        void appendTo(final SortKeyByteSink sink) {
            assert this.len > 0 && this.buffer[this.len - 1] == 1;
            sink.Append(this.buffer, this.len - 1);
        }
        
        private boolean ensureCapacity(final int appendCapacity) {
            int newCapacity = 2 * this.buffer.length;
            final int altCapacity = this.len + 2 * appendCapacity;
            if (newCapacity < altCapacity) {
                newCapacity = altCapacity;
            }
            if (newCapacity < 200) {
                newCapacity = 200;
            }
            final byte[] newbuf = new byte[newCapacity];
            System.arraycopy(this.buffer, 0, newbuf, 0, this.len);
            this.buffer = newbuf;
            return true;
        }
    }
}
