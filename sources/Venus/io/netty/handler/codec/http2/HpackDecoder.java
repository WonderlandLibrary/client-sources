/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http2.HpackDynamicTable;
import io.netty.handler.codec.http2.HpackHeaderField;
import io.netty.handler.codec.http2.HpackHuffmanDecoder;
import io.netty.handler.codec.http2.HpackStaticTable;
import io.netty.handler.codec.http2.HpackUtil;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.util.AsciiString;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.ThrowableUtil;

final class HpackDecoder {
    private static final Http2Exception DECODE_ULE_128_DECOMPRESSION_EXCEPTION;
    private static final Http2Exception DECODE_ULE_128_TO_LONG_DECOMPRESSION_EXCEPTION;
    private static final Http2Exception DECODE_ULE_128_TO_INT_DECOMPRESSION_EXCEPTION;
    private static final Http2Exception DECODE_ILLEGAL_INDEX_VALUE;
    private static final Http2Exception INDEX_HEADER_ILLEGAL_INDEX_VALUE;
    private static final Http2Exception READ_NAME_ILLEGAL_INDEX_VALUE;
    private static final Http2Exception INVALID_MAX_DYNAMIC_TABLE_SIZE;
    private static final Http2Exception MAX_DYNAMIC_TABLE_SIZE_CHANGE_REQUIRED;
    private static final byte READ_HEADER_REPRESENTATION = 0;
    private static final byte READ_MAX_DYNAMIC_TABLE_SIZE = 1;
    private static final byte READ_INDEXED_HEADER = 2;
    private static final byte READ_INDEXED_HEADER_NAME = 3;
    private static final byte READ_LITERAL_HEADER_NAME_LENGTH_PREFIX = 4;
    private static final byte READ_LITERAL_HEADER_NAME_LENGTH = 5;
    private static final byte READ_LITERAL_HEADER_NAME = 6;
    private static final byte READ_LITERAL_HEADER_VALUE_LENGTH_PREFIX = 7;
    private static final byte READ_LITERAL_HEADER_VALUE_LENGTH = 8;
    private static final byte READ_LITERAL_HEADER_VALUE = 9;
    private final HpackDynamicTable hpackDynamicTable;
    private final HpackHuffmanDecoder hpackHuffmanDecoder;
    private long maxHeaderListSizeGoAway;
    private long maxHeaderListSize;
    private long maxDynamicTableSize;
    private long encoderMaxDynamicTableSize;
    private boolean maxDynamicTableSizeChangeRequired;
    static final boolean $assertionsDisabled;

    HpackDecoder(long l, int n) {
        this(l, n, 4096);
    }

    HpackDecoder(long l, int n, int n2) {
        this.maxHeaderListSize = ObjectUtil.checkPositive(l, "maxHeaderListSize");
        this.maxHeaderListSizeGoAway = Http2CodecUtil.calculateMaxHeaderListSizeGoAway(l);
        this.maxDynamicTableSize = this.encoderMaxDynamicTableSize = (long)n2;
        this.maxDynamicTableSizeChangeRequired = false;
        this.hpackDynamicTable = new HpackDynamicTable(n2);
        this.hpackHuffmanDecoder = new HpackHuffmanDecoder(n);
    }

    public void decode(int n, ByteBuf byteBuf, Http2Headers http2Headers, boolean bl) throws Http2Exception {
        int n2 = 0;
        long l = 0L;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        boolean bl2 = false;
        CharSequence charSequence = null;
        HeaderType headerType = null;
        HpackUtil.IndexType indexType = HpackUtil.IndexType.NONE;
        block28: while (byteBuf.isReadable()) {
            switch (n5) {
                case 0: {
                    HpackHeaderField hpackHeaderField;
                    byte by = byteBuf.readByte();
                    if (this.maxDynamicTableSizeChangeRequired && (by & 0xE0) != 32) {
                        throw MAX_DYNAMIC_TABLE_SIZE_CHANGE_REQUIRED;
                    }
                    if (by < 0) {
                        n2 = by & 0x7F;
                        switch (n2) {
                            case 0: {
                                throw DECODE_ILLEGAL_INDEX_VALUE;
                            }
                            case 127: {
                                n5 = 2;
                                continue block28;
                            }
                        }
                        hpackHeaderField = this.getIndexedHeader(n2);
                        headerType = this.validate(hpackHeaderField.name, headerType, bl);
                        l = this.addHeader(http2Headers, hpackHeaderField.name, hpackHeaderField.value, l);
                        continue block28;
                    }
                    if ((by & 0x40) == 64) {
                        indexType = HpackUtil.IndexType.INCREMENTAL;
                        n2 = by & 0x3F;
                        switch (n2) {
                            case 0: {
                                n5 = 4;
                                continue block28;
                            }
                            case 63: {
                                n5 = 3;
                                continue block28;
                            }
                        }
                        charSequence = this.readName(n2);
                        headerType = this.validate(charSequence, headerType, bl);
                        n3 = charSequence.length();
                        n5 = 7;
                        continue block28;
                    }
                    if ((by & 0x20) == 32) {
                        n2 = by & 0x1F;
                        if (n2 == 31) {
                            n5 = 1;
                            continue block28;
                        }
                        this.setDynamicTableSize(n2);
                        n5 = 0;
                        continue block28;
                    }
                    indexType = (by & 0x10) == 16 ? HpackUtil.IndexType.NEVER : HpackUtil.IndexType.NONE;
                    n2 = by & 0xF;
                    switch (n2) {
                        case 0: {
                            n5 = 4;
                            continue block28;
                        }
                        case 15: {
                            n5 = 3;
                            continue block28;
                        }
                    }
                    charSequence = this.readName(n2);
                    headerType = this.validate(charSequence, headerType, bl);
                    n3 = charSequence.length();
                    n5 = 7;
                    continue block28;
                }
                case 1: {
                    this.setDynamicTableSize(HpackDecoder.decodeULE128(byteBuf, (long)n2));
                    n5 = 0;
                    continue block28;
                }
                case 2: {
                    HpackHeaderField hpackHeaderField = this.getIndexedHeader(HpackDecoder.decodeULE128(byteBuf, n2));
                    headerType = this.validate(hpackHeaderField.name, headerType, bl);
                    l = this.addHeader(http2Headers, hpackHeaderField.name, hpackHeaderField.value, l);
                    n5 = 0;
                    continue block28;
                }
                case 3: {
                    charSequence = this.readName(HpackDecoder.decodeULE128(byteBuf, n2));
                    headerType = this.validate(charSequence, headerType, bl);
                    n3 = charSequence.length();
                    n5 = 7;
                    continue block28;
                }
                case 4: {
                    byte by = byteBuf.readByte();
                    bl2 = (by & 0x80) == 128;
                    n2 = by & 0x7F;
                    if (n2 == 127) {
                        n5 = 5;
                        continue block28;
                    }
                    if ((long)n2 > this.maxHeaderListSizeGoAway - l) {
                        Http2CodecUtil.headerListSizeExceeded(this.maxHeaderListSizeGoAway);
                    }
                    n3 = n2;
                    n5 = 6;
                    continue block28;
                }
                case 5: {
                    n3 = HpackDecoder.decodeULE128(byteBuf, n2);
                    if ((long)n3 > this.maxHeaderListSizeGoAway - l) {
                        Http2CodecUtil.headerListSizeExceeded(this.maxHeaderListSizeGoAway);
                    }
                    n5 = 6;
                    continue block28;
                }
                case 6: {
                    if (byteBuf.readableBytes() < n3) {
                        throw HpackDecoder.notEnoughDataException(byteBuf);
                    }
                    charSequence = this.readStringLiteral(byteBuf, n3, bl2);
                    headerType = this.validate(charSequence, headerType, bl);
                    n5 = 7;
                    continue block28;
                }
                case 7: {
                    byte by = byteBuf.readByte();
                    bl2 = (by & 0x80) == 128;
                    n2 = by & 0x7F;
                    switch (n2) {
                        case 127: {
                            n5 = 8;
                            continue block28;
                        }
                        case 0: {
                            headerType = this.validate(charSequence, headerType, bl);
                            l = this.insertHeader(http2Headers, charSequence, AsciiString.EMPTY_STRING, indexType, l);
                            n5 = 0;
                            continue block28;
                        }
                    }
                    if ((long)n2 + (long)n3 > this.maxHeaderListSizeGoAway - l) {
                        Http2CodecUtil.headerListSizeExceeded(this.maxHeaderListSizeGoAway);
                    }
                    n4 = n2;
                    n5 = 9;
                    continue block28;
                }
                case 8: {
                    n4 = HpackDecoder.decodeULE128(byteBuf, n2);
                    if ((long)n4 + (long)n3 > this.maxHeaderListSizeGoAway - l) {
                        Http2CodecUtil.headerListSizeExceeded(this.maxHeaderListSizeGoAway);
                    }
                    n5 = 9;
                    continue block28;
                }
                case 9: {
                    if (byteBuf.readableBytes() < n4) {
                        throw HpackDecoder.notEnoughDataException(byteBuf);
                    }
                    CharSequence charSequence2 = this.readStringLiteral(byteBuf, n4, bl2);
                    headerType = this.validate(charSequence, headerType, bl);
                    l = this.insertHeader(http2Headers, charSequence, charSequence2, indexType, l);
                    n5 = 0;
                    continue block28;
                }
            }
            throw new Error("should not reach here state: " + n5);
        }
        if (l > this.maxHeaderListSize) {
            Http2CodecUtil.headerListSizeExceeded(n, this.maxHeaderListSize, true);
        }
        if (n5 != 0) {
            throw Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "Incomplete header block fragment.", new Object[0]);
        }
    }

    public void setMaxHeaderTableSize(long l) throws Http2Exception {
        if (l < 0L || l > 0xFFFFFFFFL) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header Table Size must be >= %d and <= %d but was %d", 0L, 0xFFFFFFFFL, l);
        }
        this.maxDynamicTableSize = l;
        if (this.maxDynamicTableSize < this.encoderMaxDynamicTableSize) {
            this.maxDynamicTableSizeChangeRequired = true;
            this.hpackDynamicTable.setCapacity(this.maxDynamicTableSize);
        }
    }

    public void setMaxHeaderListSize(long l, long l2) throws Http2Exception {
        if (l2 < l || l2 < 0L) {
            throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, "Header List Size GO_AWAY %d must be positive and >= %d", l2, l);
        }
        if (l < 0L || l > 0xFFFFFFFFL) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header List Size must be >= %d and <= %d but was %d", 0L, 0xFFFFFFFFL, l);
        }
        this.maxHeaderListSize = l;
        this.maxHeaderListSizeGoAway = l2;
    }

    public long getMaxHeaderListSize() {
        return this.maxHeaderListSize;
    }

    public long getMaxHeaderListSizeGoAway() {
        return this.maxHeaderListSizeGoAway;
    }

    public long getMaxHeaderTableSize() {
        return this.hpackDynamicTable.capacity();
    }

    int length() {
        return this.hpackDynamicTable.length();
    }

    long size() {
        return this.hpackDynamicTable.size();
    }

    HpackHeaderField getHeaderField(int n) {
        return this.hpackDynamicTable.getEntry(n + 1);
    }

    private void setDynamicTableSize(long l) throws Http2Exception {
        if (l > this.maxDynamicTableSize) {
            throw INVALID_MAX_DYNAMIC_TABLE_SIZE;
        }
        this.encoderMaxDynamicTableSize = l;
        this.maxDynamicTableSizeChangeRequired = false;
        this.hpackDynamicTable.setCapacity(l);
    }

    private HeaderType validate(CharSequence charSequence, HeaderType headerType, boolean bl) throws Http2Exception {
        if (!bl) {
            return null;
        }
        if (Http2Headers.PseudoHeaderName.hasPseudoHeaderFormat(charSequence)) {
            HeaderType headerType2;
            if (headerType == HeaderType.REGULAR_HEADER) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Pseudo-header field '%s' found after regular header.", charSequence);
            }
            Http2Headers.PseudoHeaderName pseudoHeaderName = Http2Headers.PseudoHeaderName.getPseudoHeader(charSequence);
            if (pseudoHeaderName == null) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Invalid HTTP/2 pseudo-header '%s' encountered.", charSequence);
            }
            HeaderType headerType3 = headerType2 = pseudoHeaderName.isRequestOnly() ? HeaderType.REQUEST_PSEUDO_HEADER : HeaderType.RESPONSE_PSEUDO_HEADER;
            if (headerType != null && headerType2 != headerType) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Mix of request and response pseudo-headers.", new Object[0]);
            }
            return headerType2;
        }
        return HeaderType.REGULAR_HEADER;
    }

    private CharSequence readName(int n) throws Http2Exception {
        if (n <= HpackStaticTable.length) {
            HpackHeaderField hpackHeaderField = HpackStaticTable.getEntry(n);
            return hpackHeaderField.name;
        }
        if (n - HpackStaticTable.length <= this.hpackDynamicTable.length()) {
            HpackHeaderField hpackHeaderField = this.hpackDynamicTable.getEntry(n - HpackStaticTable.length);
            return hpackHeaderField.name;
        }
        throw READ_NAME_ILLEGAL_INDEX_VALUE;
    }

    private HpackHeaderField getIndexedHeader(int n) throws Http2Exception {
        if (n <= HpackStaticTable.length) {
            return HpackStaticTable.getEntry(n);
        }
        if (n - HpackStaticTable.length <= this.hpackDynamicTable.length()) {
            return this.hpackDynamicTable.getEntry(n - HpackStaticTable.length);
        }
        throw INDEX_HEADER_ILLEGAL_INDEX_VALUE;
    }

    private long insertHeader(Http2Headers http2Headers, CharSequence charSequence, CharSequence charSequence2, HpackUtil.IndexType indexType, long l) throws Http2Exception {
        l = this.addHeader(http2Headers, charSequence, charSequence2, l);
        switch (1.$SwitchMap$io$netty$handler$codec$http2$HpackUtil$IndexType[indexType.ordinal()]) {
            case 1: 
            case 2: {
                break;
            }
            case 3: {
                this.hpackDynamicTable.add(new HpackHeaderField(charSequence, charSequence2));
                break;
            }
            default: {
                throw new Error("should not reach here");
            }
        }
        return l;
    }

    private long addHeader(Http2Headers http2Headers, CharSequence charSequence, CharSequence charSequence2, long l) throws Http2Exception {
        if ((l += HpackHeaderField.sizeOf(charSequence, charSequence2)) > this.maxHeaderListSizeGoAway) {
            Http2CodecUtil.headerListSizeExceeded(this.maxHeaderListSizeGoAway);
        }
        http2Headers.add(charSequence, charSequence2);
        return l;
    }

    private CharSequence readStringLiteral(ByteBuf byteBuf, int n, boolean bl) throws Http2Exception {
        if (bl) {
            return this.hpackHuffmanDecoder.decode(byteBuf, n);
        }
        byte[] byArray = new byte[n];
        byteBuf.readBytes(byArray);
        return new AsciiString(byArray, false);
    }

    private static IllegalArgumentException notEnoughDataException(ByteBuf byteBuf) {
        return new IllegalArgumentException("decode only works with an entire header block! " + byteBuf);
    }

    static int decodeULE128(ByteBuf byteBuf, int n) throws Http2Exception {
        int n2 = byteBuf.readerIndex();
        long l = HpackDecoder.decodeULE128(byteBuf, (long)n);
        if (l > Integer.MAX_VALUE) {
            byteBuf.readerIndex(n2);
            throw DECODE_ULE_128_TO_INT_DECOMPRESSION_EXCEPTION;
        }
        return (int)l;
    }

    static long decodeULE128(ByteBuf byteBuf, long l) throws Http2Exception {
        if (!($assertionsDisabled || l <= 127L && l >= 0L)) {
            throw new AssertionError();
        }
        boolean bl = l == 0L;
        int n = byteBuf.writerIndex();
        int n2 = byteBuf.readerIndex();
        int n3 = 0;
        while (n2 < n) {
            byte by = byteBuf.getByte(n2);
            if (n3 == 56 && ((by & 0x80) != 0 || by == 127 && !bl)) {
                throw DECODE_ULE_128_TO_LONG_DECOMPRESSION_EXCEPTION;
            }
            if ((by & 0x80) == 0) {
                byteBuf.readerIndex(n2 + 1);
                return l + (((long)by & 0x7FL) << n3);
            }
            l += ((long)by & 0x7FL) << n3;
            ++n2;
            n3 += 7;
        }
        throw DECODE_ULE_128_DECOMPRESSION_EXCEPTION;
    }

    static {
        $assertionsDisabled = !HpackDecoder.class.desiredAssertionStatus();
        DECODE_ULE_128_DECOMPRESSION_EXCEPTION = ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - decompression failure", new Object[0]), HpackDecoder.class, "decodeULE128(..)");
        DECODE_ULE_128_TO_LONG_DECOMPRESSION_EXCEPTION = ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - long overflow", new Object[0]), HpackDecoder.class, "decodeULE128(..)");
        DECODE_ULE_128_TO_INT_DECOMPRESSION_EXCEPTION = ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - int overflow", new Object[0]), HpackDecoder.class, "decodeULE128ToInt(..)");
        DECODE_ILLEGAL_INDEX_VALUE = ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - illegal index value", new Object[0]), HpackDecoder.class, "decode(..)");
        INDEX_HEADER_ILLEGAL_INDEX_VALUE = ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - illegal index value", new Object[0]), HpackDecoder.class, "indexHeader(..)");
        READ_NAME_ILLEGAL_INDEX_VALUE = ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - illegal index value", new Object[0]), HpackDecoder.class, "readName(..)");
        INVALID_MAX_DYNAMIC_TABLE_SIZE = ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - invalid max dynamic table size", new Object[0]), HpackDecoder.class, "setDynamicTableSize(..)");
        MAX_DYNAMIC_TABLE_SIZE_CHANGE_REQUIRED = ThrowableUtil.unknownStackTrace(Http2Exception.connectionError(Http2Error.COMPRESSION_ERROR, "HPACK - max dynamic table size change required", new Object[0]), HpackDecoder.class, "decode(..)");
    }

    private static enum HeaderType {
        REGULAR_HEADER,
        REQUEST_PSEUDO_HEADER,
        RESPONSE_PSEUDO_HEADER;

    }
}

