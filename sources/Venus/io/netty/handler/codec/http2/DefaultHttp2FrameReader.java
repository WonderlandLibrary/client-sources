/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http2.DefaultHttp2HeadersDecoder;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2FrameReader;
import io.netty.handler.codec.http2.Http2FrameSizePolicy;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersDecoder;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.util.internal.PlatformDependent;

public class DefaultHttp2FrameReader
implements Http2FrameReader,
Http2FrameSizePolicy,
Http2FrameReader.Configuration {
    private final Http2HeadersDecoder headersDecoder;
    private boolean readingHeaders = true;
    private boolean readError;
    private byte frameType;
    private int streamId;
    private Http2Flags flags;
    private int payloadLength;
    private HeadersContinuation headersContinuation;
    private int maxFrameSize;

    public DefaultHttp2FrameReader() {
        this(true);
    }

    public DefaultHttp2FrameReader(boolean bl) {
        this(new DefaultHttp2HeadersDecoder(bl));
    }

    public DefaultHttp2FrameReader(Http2HeadersDecoder http2HeadersDecoder) {
        this.headersDecoder = http2HeadersDecoder;
        this.maxFrameSize = 16384;
    }

    @Override
    public Http2HeadersDecoder.Configuration headersConfiguration() {
        return this.headersDecoder.configuration();
    }

    @Override
    public Http2FrameReader.Configuration configuration() {
        return this;
    }

    @Override
    public Http2FrameSizePolicy frameSizePolicy() {
        return this;
    }

    @Override
    public void maxFrameSize(int n) throws Http2Exception {
        if (!Http2CodecUtil.isMaxFrameSizeValid(n)) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Invalid MAX_FRAME_SIZE specified in sent settings: %d", n);
        }
        this.maxFrameSize = n;
    }

    @Override
    public int maxFrameSize() {
        return this.maxFrameSize;
    }

    @Override
    public void close() {
        this.closeHeadersContinuation();
    }

    private void closeHeadersContinuation() {
        if (this.headersContinuation != null) {
            this.headersContinuation.close();
            this.headersContinuation = null;
        }
    }

    @Override
    public void readFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        if (this.readError) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        try {
            do {
                if (this.readingHeaders) {
                    this.processHeaderState(byteBuf);
                    if (this.readingHeaders) {
                        return;
                    }
                }
                this.processPayloadState(channelHandlerContext, byteBuf, http2FrameListener);
                if (this.readingHeaders) continue;
                return;
            } while (byteBuf.isReadable());
        } catch (Http2Exception http2Exception) {
            this.readError = !Http2Exception.isStreamError(http2Exception);
            throw http2Exception;
        } catch (RuntimeException runtimeException) {
            this.readError = true;
            throw runtimeException;
        } catch (Throwable throwable) {
            this.readError = true;
            PlatformDependent.throwException(throwable);
        }
    }

    private void processHeaderState(ByteBuf byteBuf) throws Http2Exception {
        if (byteBuf.readableBytes() < 9) {
            return;
        }
        this.payloadLength = byteBuf.readUnsignedMedium();
        if (this.payloadLength > this.maxFrameSize) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length: %d exceeds maximum: %d", this.payloadLength, this.maxFrameSize);
        }
        this.frameType = byteBuf.readByte();
        this.flags = new Http2Flags(byteBuf.readUnsignedByte());
        this.streamId = Http2CodecUtil.readUnsignedInt(byteBuf);
        this.readingHeaders = false;
        switch (this.frameType) {
            case 0: {
                this.verifyDataFrame();
                break;
            }
            case 1: {
                this.verifyHeadersFrame();
                break;
            }
            case 2: {
                this.verifyPriorityFrame();
                break;
            }
            case 3: {
                this.verifyRstStreamFrame();
                break;
            }
            case 4: {
                this.verifySettingsFrame();
                break;
            }
            case 5: {
                this.verifyPushPromiseFrame();
                break;
            }
            case 6: {
                this.verifyPingFrame();
                break;
            }
            case 7: {
                this.verifyGoAwayFrame();
                break;
            }
            case 8: {
                this.verifyWindowUpdateFrame();
                break;
            }
            case 9: {
                this.verifyContinuationFrame();
                break;
            }
            default: {
                this.verifyUnknownFrame();
            }
        }
    }

    private void processPayloadState(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        if (byteBuf.readableBytes() < this.payloadLength) {
            return;
        }
        ByteBuf byteBuf2 = byteBuf.readSlice(this.payloadLength);
        this.readingHeaders = true;
        switch (this.frameType) {
            case 0: {
                this.readDataFrame(channelHandlerContext, byteBuf2, http2FrameListener);
                break;
            }
            case 1: {
                this.readHeadersFrame(channelHandlerContext, byteBuf2, http2FrameListener);
                break;
            }
            case 2: {
                this.readPriorityFrame(channelHandlerContext, byteBuf2, http2FrameListener);
                break;
            }
            case 3: {
                this.readRstStreamFrame(channelHandlerContext, byteBuf2, http2FrameListener);
                break;
            }
            case 4: {
                this.readSettingsFrame(channelHandlerContext, byteBuf2, http2FrameListener);
                break;
            }
            case 5: {
                this.readPushPromiseFrame(channelHandlerContext, byteBuf2, http2FrameListener);
                break;
            }
            case 6: {
                this.readPingFrame(channelHandlerContext, byteBuf2.readLong(), http2FrameListener);
                break;
            }
            case 7: {
                DefaultHttp2FrameReader.readGoAwayFrame(channelHandlerContext, byteBuf2, http2FrameListener);
                break;
            }
            case 8: {
                this.readWindowUpdateFrame(channelHandlerContext, byteBuf2, http2FrameListener);
                break;
            }
            case 9: {
                this.readContinuationFrame(byteBuf2, http2FrameListener);
                break;
            }
            default: {
                this.readUnknownFrame(channelHandlerContext, byteBuf2, http2FrameListener);
            }
        }
    }

    private void verifyDataFrame() throws Http2Exception {
        this.verifyAssociatedWithAStream();
        this.verifyNotProcessingHeaders();
        this.verifyPayloadLength(this.payloadLength);
        if (this.payloadLength < this.flags.getPaddingPresenceFieldLength()) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", this.payloadLength);
        }
    }

    private void verifyHeadersFrame() throws Http2Exception {
        this.verifyAssociatedWithAStream();
        this.verifyNotProcessingHeaders();
        this.verifyPayloadLength(this.payloadLength);
        int n = this.flags.getPaddingPresenceFieldLength() + this.flags.getNumPriorityBytes();
        if (this.payloadLength < n) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length too small." + this.payloadLength, new Object[0]);
        }
    }

    private void verifyPriorityFrame() throws Http2Exception {
        this.verifyAssociatedWithAStream();
        this.verifyNotProcessingHeaders();
        if (this.payloadLength != 5) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", this.payloadLength);
        }
    }

    private void verifyRstStreamFrame() throws Http2Exception {
        this.verifyAssociatedWithAStream();
        this.verifyNotProcessingHeaders();
        if (this.payloadLength != 4) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", this.payloadLength);
        }
    }

    private void verifySettingsFrame() throws Http2Exception {
        this.verifyNotProcessingHeaders();
        this.verifyPayloadLength(this.payloadLength);
        if (this.streamId != 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.", new Object[0]);
        }
        if (this.flags.ack() && this.payloadLength > 0) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Ack settings frame must have an empty payload.", new Object[0]);
        }
        if (this.payloadLength % 6 > 0) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d invalid.", this.payloadLength);
        }
    }

    private void verifyPushPromiseFrame() throws Http2Exception {
        this.verifyNotProcessingHeaders();
        this.verifyPayloadLength(this.payloadLength);
        int n = this.flags.getPaddingPresenceFieldLength() + 4;
        if (this.payloadLength < n) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", this.payloadLength);
        }
    }

    private void verifyPingFrame() throws Http2Exception {
        this.verifyNotProcessingHeaders();
        if (this.streamId != 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.", new Object[0]);
        }
        if (this.payloadLength != 8) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d incorrect size for ping.", this.payloadLength);
        }
    }

    private void verifyGoAwayFrame() throws Http2Exception {
        this.verifyNotProcessingHeaders();
        this.verifyPayloadLength(this.payloadLength);
        if (this.streamId != 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "A stream ID must be zero.", new Object[0]);
        }
        if (this.payloadLength < 8) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small.", this.payloadLength);
        }
    }

    private void verifyWindowUpdateFrame() throws Http2Exception {
        this.verifyNotProcessingHeaders();
        DefaultHttp2FrameReader.verifyStreamOrConnectionId(this.streamId, "Stream ID");
        if (this.payloadLength != 4) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Invalid frame length %d.", this.payloadLength);
        }
    }

    private void verifyContinuationFrame() throws Http2Exception {
        this.verifyAssociatedWithAStream();
        this.verifyPayloadLength(this.payloadLength);
        if (this.headersContinuation == null) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received %s frame but not currently processing headers.", this.frameType);
        }
        if (this.streamId != this.headersContinuation.getStreamId()) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Continuation stream ID does not match pending headers. Expected %d, but received %d.", this.headersContinuation.getStreamId(), this.streamId);
        }
        if (this.payloadLength < this.flags.getPaddingPresenceFieldLength()) {
            throw Http2Exception.streamError(this.streamId, Http2Error.FRAME_SIZE_ERROR, "Frame length %d too small for padding.", this.payloadLength);
        }
    }

    private void verifyUnknownFrame() throws Http2Exception {
        this.verifyNotProcessingHeaders();
    }

    private void readDataFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        int n = this.readPadding(byteBuf);
        this.verifyPadding(n);
        int n2 = DefaultHttp2FrameReader.lengthWithoutTrailingPadding(byteBuf.readableBytes(), n);
        ByteBuf byteBuf2 = byteBuf.readSlice(n2);
        http2FrameListener.onDataRead(channelHandlerContext, this.streamId, byteBuf2, n, this.flags.endOfStream());
        byteBuf.skipBytes(byteBuf.readableBytes());
    }

    private void readHeadersFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        int n = this.streamId;
        Http2Flags http2Flags = this.flags;
        int n2 = this.readPadding(byteBuf);
        this.verifyPadding(n2);
        if (this.flags.priorityPresent()) {
            long l = byteBuf.readUnsignedInt();
            boolean bl = (l & 0x80000000L) != 0L;
            int n3 = (int)(l & Integer.MAX_VALUE);
            if (n3 == this.streamId) {
                throw Http2Exception.streamError(this.streamId, Http2Error.PROTOCOL_ERROR, "A stream cannot depend on itself.", new Object[0]);
            }
            short s = (short)(byteBuf.readUnsignedByte() + 1);
            ByteBuf byteBuf2 = byteBuf.readSlice(DefaultHttp2FrameReader.lengthWithoutTrailingPadding(byteBuf.readableBytes(), n2));
            this.headersContinuation = new HeadersContinuation(this, n, channelHandlerContext, n3, s, bl, n2, http2Flags){
                final int val$headersStreamId;
                final ChannelHandlerContext val$ctx;
                final int val$streamDependency;
                final short val$weight;
                final boolean val$exclusive;
                final int val$padding;
                final Http2Flags val$headersFlags;
                final DefaultHttp2FrameReader this$0;
                {
                    this.this$0 = defaultHttp2FrameReader;
                    this.val$headersStreamId = n;
                    this.val$ctx = channelHandlerContext;
                    this.val$streamDependency = n2;
                    this.val$weight = s;
                    this.val$exclusive = bl;
                    this.val$padding = n3;
                    this.val$headersFlags = http2Flags;
                    super(defaultHttp2FrameReader, null);
                }

                @Override
                public int getStreamId() {
                    return this.val$headersStreamId;
                }

                @Override
                public void processFragment(boolean bl, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
                    HeadersBlockBuilder headersBlockBuilder = this.headersBlockBuilder();
                    headersBlockBuilder.addFragment(byteBuf, this.val$ctx.alloc(), bl);
                    if (bl) {
                        http2FrameListener.onHeadersRead(this.val$ctx, this.val$headersStreamId, headersBlockBuilder.headers(), this.val$streamDependency, this.val$weight, this.val$exclusive, this.val$padding, this.val$headersFlags.endOfStream());
                    }
                }
            };
            this.headersContinuation.processFragment(this.flags.endOfHeaders(), byteBuf2, http2FrameListener);
            this.resetHeadersContinuationIfEnd(this.flags.endOfHeaders());
            return;
        }
        this.headersContinuation = new HeadersContinuation(this, n, channelHandlerContext, n2, http2Flags){
            final int val$headersStreamId;
            final ChannelHandlerContext val$ctx;
            final int val$padding;
            final Http2Flags val$headersFlags;
            final DefaultHttp2FrameReader this$0;
            {
                this.this$0 = defaultHttp2FrameReader;
                this.val$headersStreamId = n;
                this.val$ctx = channelHandlerContext;
                this.val$padding = n2;
                this.val$headersFlags = http2Flags;
                super(defaultHttp2FrameReader, null);
            }

            @Override
            public int getStreamId() {
                return this.val$headersStreamId;
            }

            @Override
            public void processFragment(boolean bl, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
                HeadersBlockBuilder headersBlockBuilder = this.headersBlockBuilder();
                headersBlockBuilder.addFragment(byteBuf, this.val$ctx.alloc(), bl);
                if (bl) {
                    http2FrameListener.onHeadersRead(this.val$ctx, this.val$headersStreamId, headersBlockBuilder.headers(), this.val$padding, this.val$headersFlags.endOfStream());
                }
            }
        };
        ByteBuf byteBuf3 = byteBuf.readSlice(DefaultHttp2FrameReader.lengthWithoutTrailingPadding(byteBuf.readableBytes(), n2));
        this.headersContinuation.processFragment(this.flags.endOfHeaders(), byteBuf3, http2FrameListener);
        this.resetHeadersContinuationIfEnd(this.flags.endOfHeaders());
    }

    private void resetHeadersContinuationIfEnd(boolean bl) {
        if (bl) {
            this.closeHeadersContinuation();
        }
    }

    private void readPriorityFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        long l = byteBuf.readUnsignedInt();
        boolean bl = (l & 0x80000000L) != 0L;
        int n = (int)(l & Integer.MAX_VALUE);
        if (n == this.streamId) {
            throw Http2Exception.streamError(this.streamId, Http2Error.PROTOCOL_ERROR, "A stream cannot depend on itself.", new Object[0]);
        }
        short s = (short)(byteBuf.readUnsignedByte() + 1);
        http2FrameListener.onPriorityRead(channelHandlerContext, this.streamId, n, s, bl);
    }

    private void readRstStreamFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        long l = byteBuf.readUnsignedInt();
        http2FrameListener.onRstStreamRead(channelHandlerContext, this.streamId, l);
    }

    private void readSettingsFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        if (this.flags.ack()) {
            http2FrameListener.onSettingsAckRead(channelHandlerContext);
        } else {
            int n = this.payloadLength / 6;
            Http2Settings http2Settings = new Http2Settings();
            for (int i = 0; i < n; ++i) {
                char c = (char)byteBuf.readUnsignedShort();
                long l = byteBuf.readUnsignedInt();
                try {
                    http2Settings.put(c, l);
                    continue;
                } catch (IllegalArgumentException illegalArgumentException) {
                    switch (c) {
                        case '\u0005': {
                            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, illegalArgumentException, illegalArgumentException.getMessage(), new Object[0]);
                        }
                        case '\u0004': {
                            throw Http2Exception.connectionError(Http2Error.FLOW_CONTROL_ERROR, illegalArgumentException, illegalArgumentException.getMessage(), new Object[0]);
                        }
                    }
                    throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, illegalArgumentException, illegalArgumentException.getMessage(), new Object[0]);
                }
            }
            http2FrameListener.onSettingsRead(channelHandlerContext, http2Settings);
        }
    }

    private void readPushPromiseFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        int n = this.streamId;
        int n2 = this.readPadding(byteBuf);
        this.verifyPadding(n2);
        int n3 = Http2CodecUtil.readUnsignedInt(byteBuf);
        this.headersContinuation = new HeadersContinuation(this, n, channelHandlerContext, n3, n2){
            final int val$pushPromiseStreamId;
            final ChannelHandlerContext val$ctx;
            final int val$promisedStreamId;
            final int val$padding;
            final DefaultHttp2FrameReader this$0;
            {
                this.this$0 = defaultHttp2FrameReader;
                this.val$pushPromiseStreamId = n;
                this.val$ctx = channelHandlerContext;
                this.val$promisedStreamId = n2;
                this.val$padding = n3;
                super(defaultHttp2FrameReader, null);
            }

            @Override
            public int getStreamId() {
                return this.val$pushPromiseStreamId;
            }

            @Override
            public void processFragment(boolean bl, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
                this.headersBlockBuilder().addFragment(byteBuf, this.val$ctx.alloc(), bl);
                if (bl) {
                    http2FrameListener.onPushPromiseRead(this.val$ctx, this.val$pushPromiseStreamId, this.val$promisedStreamId, this.headersBlockBuilder().headers(), this.val$padding);
                }
            }
        };
        ByteBuf byteBuf2 = byteBuf.readSlice(DefaultHttp2FrameReader.lengthWithoutTrailingPadding(byteBuf.readableBytes(), n2));
        this.headersContinuation.processFragment(this.flags.endOfHeaders(), byteBuf2, http2FrameListener);
        this.resetHeadersContinuationIfEnd(this.flags.endOfHeaders());
    }

    private void readPingFrame(ChannelHandlerContext channelHandlerContext, long l, Http2FrameListener http2FrameListener) throws Http2Exception {
        if (this.flags.ack()) {
            http2FrameListener.onPingAckRead(channelHandlerContext, l);
        } else {
            http2FrameListener.onPingRead(channelHandlerContext, l);
        }
    }

    private static void readGoAwayFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        int n = Http2CodecUtil.readUnsignedInt(byteBuf);
        long l = byteBuf.readUnsignedInt();
        ByteBuf byteBuf2 = byteBuf.readSlice(byteBuf.readableBytes());
        http2FrameListener.onGoAwayRead(channelHandlerContext, n, l, byteBuf2);
    }

    private void readWindowUpdateFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        int n = Http2CodecUtil.readUnsignedInt(byteBuf);
        if (n == 0) {
            throw Http2Exception.streamError(this.streamId, Http2Error.PROTOCOL_ERROR, "Received WINDOW_UPDATE with delta 0 for stream: %d", this.streamId);
        }
        http2FrameListener.onWindowUpdateRead(channelHandlerContext, this.streamId, n);
    }

    private void readContinuationFrame(ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        ByteBuf byteBuf2 = byteBuf.readSlice(byteBuf.readableBytes());
        this.headersContinuation.processFragment(this.flags.endOfHeaders(), byteBuf2, http2FrameListener);
        this.resetHeadersContinuationIfEnd(this.flags.endOfHeaders());
    }

    private void readUnknownFrame(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, Http2FrameListener http2FrameListener) throws Http2Exception {
        byteBuf = byteBuf.readSlice(byteBuf.readableBytes());
        http2FrameListener.onUnknownFrame(channelHandlerContext, this.frameType, this.streamId, this.flags, byteBuf);
    }

    private int readPadding(ByteBuf byteBuf) {
        if (!this.flags.paddingPresent()) {
            return 1;
        }
        return byteBuf.readUnsignedByte() + 1;
    }

    private void verifyPadding(int n) throws Http2Exception {
        int n2 = DefaultHttp2FrameReader.lengthWithoutTrailingPadding(this.payloadLength, n);
        if (n2 < 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Frame payload too small for padding.", new Object[0]);
        }
    }

    private static int lengthWithoutTrailingPadding(int n, int n2) {
        return n2 == 0 ? n : n - (n2 - 1);
    }

    private void verifyNotProcessingHeaders() throws Http2Exception {
        if (this.headersContinuation != null) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Received frame of type %s while processing headers on stream %d.", this.frameType, this.headersContinuation.getStreamId());
        }
    }

    private void verifyPayloadLength(int n) throws Http2Exception {
        if (n > this.maxFrameSize) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Total payload length %d exceeds max frame length.", n);
        }
    }

    private void verifyAssociatedWithAStream() throws Http2Exception {
        if (this.streamId == 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Frame of type %s must be associated with a stream.", this.frameType);
        }
    }

    private static void verifyStreamOrConnectionId(int n, String string) throws Http2Exception {
        if (n < 0) {
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "%s must be >= 0", string);
        }
    }

    static Http2HeadersDecoder access$100(DefaultHttp2FrameReader defaultHttp2FrameReader) {
        return defaultHttp2FrameReader.headersDecoder;
    }

    static int access$200(DefaultHttp2FrameReader defaultHttp2FrameReader) {
        return defaultHttp2FrameReader.streamId;
    }

    static HeadersContinuation access$302(DefaultHttp2FrameReader defaultHttp2FrameReader, HeadersContinuation headersContinuation) {
        defaultHttp2FrameReader.headersContinuation = headersContinuation;
        return defaultHttp2FrameReader.headersContinuation;
    }

    protected class HeadersBlockBuilder {
        private ByteBuf headerBlock;
        final DefaultHttp2FrameReader this$0;

        protected HeadersBlockBuilder(DefaultHttp2FrameReader defaultHttp2FrameReader) {
            this.this$0 = defaultHttp2FrameReader;
        }

        private void headerSizeExceeded() throws Http2Exception {
            this.close();
            Http2CodecUtil.headerListSizeExceeded(DefaultHttp2FrameReader.access$100(this.this$0).configuration().maxHeaderListSizeGoAway());
        }

        final void addFragment(ByteBuf byteBuf, ByteBufAllocator byteBufAllocator, boolean bl) throws Http2Exception {
            if (this.headerBlock == null) {
                if ((long)byteBuf.readableBytes() > DefaultHttp2FrameReader.access$100(this.this$0).configuration().maxHeaderListSizeGoAway()) {
                    this.headerSizeExceeded();
                }
                if (bl) {
                    this.headerBlock = byteBuf.retain();
                } else {
                    this.headerBlock = byteBufAllocator.buffer(byteBuf.readableBytes());
                    this.headerBlock.writeBytes(byteBuf);
                }
                return;
            }
            if (DefaultHttp2FrameReader.access$100(this.this$0).configuration().maxHeaderListSizeGoAway() - (long)byteBuf.readableBytes() < (long)this.headerBlock.readableBytes()) {
                this.headerSizeExceeded();
            }
            if (this.headerBlock.isWritable(byteBuf.readableBytes())) {
                this.headerBlock.writeBytes(byteBuf);
            } else {
                ByteBuf byteBuf2 = byteBufAllocator.buffer(this.headerBlock.readableBytes() + byteBuf.readableBytes());
                byteBuf2.writeBytes(this.headerBlock);
                byteBuf2.writeBytes(byteBuf);
                this.headerBlock.release();
                this.headerBlock = byteBuf2;
            }
        }

        Http2Headers headers() throws Http2Exception {
            try {
                Http2Headers http2Headers = DefaultHttp2FrameReader.access$100(this.this$0).decodeHeaders(DefaultHttp2FrameReader.access$200(this.this$0), this.headerBlock);
                return http2Headers;
            } finally {
                this.close();
            }
        }

        void close() {
            if (this.headerBlock != null) {
                this.headerBlock.release();
                this.headerBlock = null;
            }
            DefaultHttp2FrameReader.access$302(this.this$0, null);
        }
    }

    private abstract class HeadersContinuation {
        private final HeadersBlockBuilder builder;
        final DefaultHttp2FrameReader this$0;

        private HeadersContinuation(DefaultHttp2FrameReader defaultHttp2FrameReader) {
            this.this$0 = defaultHttp2FrameReader;
            this.builder = new HeadersBlockBuilder(this.this$0);
        }

        abstract int getStreamId();

        abstract void processFragment(boolean var1, ByteBuf var2, Http2FrameListener var3) throws Http2Exception;

        final HeadersBlockBuilder headersBlockBuilder() {
            return this.builder;
        }

        final void close() {
            this.builder.close();
        }

        HeadersContinuation(DefaultHttp2FrameReader defaultHttp2FrameReader, 1 var2_2) {
            this(defaultHttp2FrameReader);
        }
    }
}

