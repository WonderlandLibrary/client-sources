/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.HpackHeaderField;
import io.netty.handler.codec.http2.HpackHuffmanEncoder;
import io.netty.handler.codec.http2.HpackStaticTable;
import io.netty.handler.codec.http2.HpackUtil;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.MathUtil;
import java.util.Arrays;
import java.util.Map;

final class HpackEncoder {
    private final HeaderEntry[] headerFields;
    private final HeaderEntry head = new HeaderEntry(-1, AsciiString.EMPTY_STRING, AsciiString.EMPTY_STRING, Integer.MAX_VALUE, null);
    private final HpackHuffmanEncoder hpackHuffmanEncoder = new HpackHuffmanEncoder();
    private final byte hashMask;
    private final boolean ignoreMaxHeaderListSize;
    private long size;
    private long maxHeaderTableSize;
    private long maxHeaderListSize;
    static final boolean $assertionsDisabled = !HpackEncoder.class.desiredAssertionStatus();

    HpackEncoder() {
        this(false);
    }

    public HpackEncoder(boolean bl) {
        this(bl, 16);
    }

    public HpackEncoder(boolean bl, int n) {
        this.ignoreMaxHeaderListSize = bl;
        this.maxHeaderTableSize = 4096L;
        this.maxHeaderListSize = 0xFFFFFFFFL;
        this.headerFields = new HeaderEntry[MathUtil.findNextPositivePowerOfTwo(Math.max(2, Math.min(n, 128)))];
        this.hashMask = (byte)(this.headerFields.length - 1);
        this.head.before = this.head.after = this.head;
    }

    public void encodeHeaders(int n, ByteBuf byteBuf, Http2Headers http2Headers, Http2HeadersEncoder.SensitivityDetector sensitivityDetector) throws Http2Exception {
        if (this.ignoreMaxHeaderListSize) {
            this.encodeHeadersIgnoreMaxHeaderListSize(byteBuf, http2Headers, sensitivityDetector);
        } else {
            this.encodeHeadersEnforceMaxHeaderListSize(n, byteBuf, http2Headers, sensitivityDetector);
        }
    }

    private void encodeHeadersEnforceMaxHeaderListSize(int n, ByteBuf byteBuf, Http2Headers http2Headers, Http2HeadersEncoder.SensitivityDetector sensitivityDetector) throws Http2Exception {
        long l = 0L;
        for (Map.Entry<CharSequence, CharSequence> entry : http2Headers) {
            CharSequence charSequence;
            CharSequence charSequence2 = entry.getKey();
            if ((l += HpackHeaderField.sizeOf(charSequence2, charSequence = entry.getValue())) <= this.maxHeaderListSize) continue;
            Http2CodecUtil.headerListSizeExceeded(n, this.maxHeaderListSize, false);
        }
        this.encodeHeadersIgnoreMaxHeaderListSize(byteBuf, http2Headers, sensitivityDetector);
    }

    private void encodeHeadersIgnoreMaxHeaderListSize(ByteBuf byteBuf, Http2Headers http2Headers, Http2HeadersEncoder.SensitivityDetector sensitivityDetector) throws Http2Exception {
        for (Map.Entry<CharSequence, CharSequence> entry : http2Headers) {
            CharSequence charSequence = entry.getKey();
            CharSequence charSequence2 = entry.getValue();
            this.encodeHeader(byteBuf, charSequence, charSequence2, sensitivityDetector.isSensitive(charSequence, charSequence2), HpackHeaderField.sizeOf(charSequence, charSequence2));
        }
    }

    private void encodeHeader(ByteBuf byteBuf, CharSequence charSequence, CharSequence charSequence2, boolean bl, long l) {
        if (bl) {
            int n = this.getNameIndex(charSequence);
            this.encodeLiteral(byteBuf, charSequence, charSequence2, HpackUtil.IndexType.NEVER, n);
            return;
        }
        if (this.maxHeaderTableSize == 0L) {
            int n = HpackStaticTable.getIndex(charSequence, charSequence2);
            if (n == -1) {
                int n2 = HpackStaticTable.getIndex(charSequence);
                this.encodeLiteral(byteBuf, charSequence, charSequence2, HpackUtil.IndexType.NONE, n2);
            } else {
                HpackEncoder.encodeInteger(byteBuf, 128, 7, n);
            }
            return;
        }
        if (l > this.maxHeaderTableSize) {
            int n = this.getNameIndex(charSequence);
            this.encodeLiteral(byteBuf, charSequence, charSequence2, HpackUtil.IndexType.NONE, n);
            return;
        }
        HeaderEntry headerEntry = this.getEntry(charSequence, charSequence2);
        if (headerEntry != null) {
            int n = this.getIndex(headerEntry.index) + HpackStaticTable.length;
            HpackEncoder.encodeInteger(byteBuf, 128, 7, n);
        } else {
            int n = HpackStaticTable.getIndex(charSequence, charSequence2);
            if (n != -1) {
                HpackEncoder.encodeInteger(byteBuf, 128, 7, n);
            } else {
                this.ensureCapacity(l);
                this.encodeLiteral(byteBuf, charSequence, charSequence2, HpackUtil.IndexType.INCREMENTAL, this.getNameIndex(charSequence));
                this.add(charSequence, charSequence2, l);
            }
        }
    }

    public void setMaxHeaderTableSize(ByteBuf byteBuf, long l) throws Http2Exception {
        if (l < 0L || l > 0xFFFFFFFFL) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header Table Size must be >= %d and <= %d but was %d", 0L, 0xFFFFFFFFL, l);
        }
        if (this.maxHeaderTableSize == l) {
            return;
        }
        this.maxHeaderTableSize = l;
        this.ensureCapacity(0L);
        HpackEncoder.encodeInteger(byteBuf, 32, 5, l);
    }

    public long getMaxHeaderTableSize() {
        return this.maxHeaderTableSize;
    }

    public void setMaxHeaderListSize(long l) throws Http2Exception {
        if (l < 0L || l > 0xFFFFFFFFL) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header List Size must be >= %d and <= %d but was %d", 0L, 0xFFFFFFFFL, l);
        }
        this.maxHeaderListSize = l;
    }

    public long getMaxHeaderListSize() {
        return this.maxHeaderListSize;
    }

    private static void encodeInteger(ByteBuf byteBuf, int n, int n2, int n3) {
        HpackEncoder.encodeInteger(byteBuf, n, n2, (long)n3);
    }

    private static void encodeInteger(ByteBuf byteBuf, int n, int n2, long l) {
        if (!($assertionsDisabled || n2 >= 0 && n2 <= 8)) {
            throw new AssertionError((Object)("N: " + n2));
        }
        int n3 = 255 >>> 8 - n2;
        if (l < (long)n3) {
            byteBuf.writeByte((int)((long)n | l));
        } else {
            byteBuf.writeByte(n | n3);
            long l2 = l - (long)n3;
            while ((l2 & 0xFFFFFFFFFFFFFF80L) != 0L) {
                byteBuf.writeByte((int)(l2 & 0x7FL | 0x80L));
                l2 >>>= 7;
            }
            byteBuf.writeByte((int)l2);
        }
    }

    private void encodeStringLiteral(ByteBuf byteBuf, CharSequence charSequence) {
        int n = this.hpackHuffmanEncoder.getEncodedLength(charSequence);
        if (n < charSequence.length()) {
            HpackEncoder.encodeInteger(byteBuf, 128, 7, n);
            this.hpackHuffmanEncoder.encode(byteBuf, charSequence);
        } else {
            HpackEncoder.encodeInteger(byteBuf, 0, 7, charSequence.length());
            if (charSequence instanceof AsciiString) {
                AsciiString asciiString = (AsciiString)charSequence;
                byteBuf.writeBytes(asciiString.array(), asciiString.arrayOffset(), asciiString.length());
            } else {
                byteBuf.writeCharSequence(charSequence, CharsetUtil.ISO_8859_1);
            }
        }
    }

    private void encodeLiteral(ByteBuf byteBuf, CharSequence charSequence, CharSequence charSequence2, HpackUtil.IndexType indexType, int n) {
        boolean bl = n != -1;
        switch (1.$SwitchMap$io$netty$handler$codec$http2$HpackUtil$IndexType[indexType.ordinal()]) {
            case 1: {
                HpackEncoder.encodeInteger(byteBuf, 64, 6, bl ? n : 0);
                break;
            }
            case 2: {
                HpackEncoder.encodeInteger(byteBuf, 0, 4, bl ? n : 0);
                break;
            }
            case 3: {
                HpackEncoder.encodeInteger(byteBuf, 16, 4, bl ? n : 0);
                break;
            }
            default: {
                throw new Error("should not reach here");
            }
        }
        if (!bl) {
            this.encodeStringLiteral(byteBuf, charSequence);
        }
        this.encodeStringLiteral(byteBuf, charSequence2);
    }

    private int getNameIndex(CharSequence charSequence) {
        int n = HpackStaticTable.getIndex(charSequence);
        if (n == -1 && (n = this.getIndex(charSequence)) >= 0) {
            n += HpackStaticTable.length;
        }
        return n;
    }

    private void ensureCapacity(long l) {
        int n;
        while (this.maxHeaderTableSize - this.size < l && (n = this.length()) != 0) {
            this.remove();
        }
    }

    int length() {
        return this.size == 0L ? 0 : this.head.after.index - this.head.before.index + 1;
    }

    long size() {
        return this.size;
    }

    HpackHeaderField getHeaderField(int n) {
        HeaderEntry headerEntry = this.head;
        while (n-- >= 0) {
            headerEntry = headerEntry.before;
        }
        return headerEntry;
    }

    private HeaderEntry getEntry(CharSequence charSequence, CharSequence charSequence2) {
        if (this.length() == 0 || charSequence == null || charSequence2 == null) {
            return null;
        }
        int n = AsciiString.hashCode(charSequence);
        int n2 = this.index(n);
        HeaderEntry headerEntry = this.headerFields[n2];
        while (headerEntry != null) {
            if (headerEntry.hash == n && (HpackUtil.equalsConstantTime(charSequence, headerEntry.name) & HpackUtil.equalsConstantTime(charSequence2, headerEntry.value)) != 0) {
                return headerEntry;
            }
            headerEntry = headerEntry.next;
        }
        return null;
    }

    private int getIndex(CharSequence charSequence) {
        if (this.length() == 0 || charSequence == null) {
            return 1;
        }
        int n = AsciiString.hashCode(charSequence);
        int n2 = this.index(n);
        HeaderEntry headerEntry = this.headerFields[n2];
        while (headerEntry != null) {
            if (headerEntry.hash == n && HpackUtil.equalsConstantTime(charSequence, headerEntry.name) != 0) {
                return this.getIndex(headerEntry.index);
            }
            headerEntry = headerEntry.next;
        }
        return 1;
    }

    private int getIndex(int n) {
        return n == -1 ? -1 : n - this.head.before.index + 1;
    }

    private void add(CharSequence charSequence, CharSequence charSequence2, long l) {
        HeaderEntry headerEntry;
        if (l > this.maxHeaderTableSize) {
            this.clear();
            return;
        }
        while (this.maxHeaderTableSize - this.size < l) {
            this.remove();
        }
        int n = AsciiString.hashCode(charSequence);
        int n2 = this.index(n);
        HeaderEntry headerEntry2 = this.headerFields[n2];
        this.headerFields[n2] = headerEntry = new HeaderEntry(n, charSequence, charSequence2, this.head.before.index - 1, headerEntry2);
        HeaderEntry.access$000(headerEntry, this.head);
        this.size += l;
    }

    private HpackHeaderField remove() {
        HeaderEntry headerEntry;
        if (this.size == 0L) {
            return null;
        }
        HeaderEntry headerEntry2 = this.head.after;
        int n = headerEntry2.hash;
        int n2 = this.index(n);
        HeaderEntry headerEntry3 = headerEntry = this.headerFields[n2];
        while (headerEntry3 != null) {
            HeaderEntry headerEntry4 = headerEntry3.next;
            if (headerEntry3 == headerEntry2) {
                if (headerEntry == headerEntry2) {
                    this.headerFields[n2] = headerEntry4;
                } else {
                    headerEntry.next = headerEntry4;
                }
                HeaderEntry.access$100(headerEntry2);
                this.size -= (long)headerEntry2.size();
                return headerEntry2;
            }
            headerEntry = headerEntry3;
            headerEntry3 = headerEntry4;
        }
        return null;
    }

    private void clear() {
        Arrays.fill(this.headerFields, null);
        this.head.before = this.head.after = this.head;
        this.size = 0L;
    }

    private int index(int n) {
        return n & this.hashMask;
    }

    private static final class HeaderEntry
    extends HpackHeaderField {
        HeaderEntry before;
        HeaderEntry after;
        HeaderEntry next;
        int hash;
        int index;

        HeaderEntry(int n, CharSequence charSequence, CharSequence charSequence2, int n2, HeaderEntry headerEntry) {
            super(charSequence, charSequence2);
            this.index = n2;
            this.hash = n;
            this.next = headerEntry;
        }

        private void remove() {
            this.before.after = this.after;
            this.after.before = this.before;
            this.before = null;
            this.after = null;
            this.next = null;
        }

        private void addBefore(HeaderEntry headerEntry) {
            this.after = headerEntry;
            this.before = headerEntry.before;
            this.before.after = this;
            this.after.before = this;
        }

        static void access$000(HeaderEntry headerEntry, HeaderEntry headerEntry2) {
            headerEntry.addBefore(headerEntry2);
        }

        static void access$100(HeaderEntry headerEntry) {
            headerEntry.remove();
        }
    }
}

