/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.PrematureChannelClosureException;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.DefaultHttpContent;
import io.netty.handler.codec.http.DefaultLastHttpContent;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpExpectationFailedEvent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.AppendableCharSequence;
import java.util.List;

public abstract class HttpObjectDecoder
extends ByteToMessageDecoder {
    private static final String EMPTY_VALUE = "";
    private final int maxChunkSize;
    private final boolean chunkedSupported;
    protected final boolean validateHeaders;
    private final HeaderParser headerParser;
    private final LineParser lineParser;
    private HttpMessage message;
    private long chunkSize;
    private long contentLength = Long.MIN_VALUE;
    private volatile boolean resetRequested;
    private CharSequence name;
    private CharSequence value;
    private LastHttpContent trailer;
    private State currentState = State.SKIP_CONTROL_CHARS;
    static final boolean $assertionsDisabled = !HttpObjectDecoder.class.desiredAssertionStatus();

    protected HttpObjectDecoder() {
        this(4096, 8192, 8192, true);
    }

    protected HttpObjectDecoder(int n, int n2, int n3, boolean bl) {
        this(n, n2, n3, bl, true);
    }

    protected HttpObjectDecoder(int n, int n2, int n3, boolean bl, boolean bl2) {
        this(n, n2, n3, bl, bl2, 128);
    }

    protected HttpObjectDecoder(int n, int n2, int n3, boolean bl, boolean bl2, int n4) {
        if (n <= 0) {
            throw new IllegalArgumentException("maxInitialLineLength must be a positive integer: " + n);
        }
        if (n2 <= 0) {
            throw new IllegalArgumentException("maxHeaderSize must be a positive integer: " + n2);
        }
        if (n3 <= 0) {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + n3);
        }
        AppendableCharSequence appendableCharSequence = new AppendableCharSequence(n4);
        this.lineParser = new LineParser(appendableCharSequence, n);
        this.headerParser = new HeaderParser(appendableCharSequence, n2);
        this.maxChunkSize = n3;
        this.chunkedSupported = bl;
        this.validateHeaders = bl2;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.resetRequested) {
            this.resetNow();
        }
        switch (1.$SwitchMap$io$netty$handler$codec$http$HttpObjectDecoder$State[this.currentState.ordinal()]) {
            case 1: {
                if (!HttpObjectDecoder.skipControlCharacters(byteBuf)) {
                    return;
                }
                this.currentState = State.READ_INITIAL;
            }
            case 3: {
                Object object;
                try {
                    object = this.lineParser.parse(byteBuf);
                    if (object == null) {
                        return;
                    }
                    String[] stringArray = HttpObjectDecoder.splitInitialLine((AppendableCharSequence)object);
                    if (stringArray.length < 3) {
                        this.currentState = State.SKIP_CONTROL_CHARS;
                        return;
                    }
                    this.message = this.createMessage(stringArray);
                    this.currentState = State.READ_HEADER;
                } catch (Exception exception) {
                    list.add(this.invalidMessage(byteBuf, exception));
                    return;
                }
            }
            case 4: {
                Object object;
                try {
                    object = this.readHeaders(byteBuf);
                    if (object == null) {
                        return;
                    }
                    this.currentState = object;
                    switch (1.$SwitchMap$io$netty$handler$codec$http$HttpObjectDecoder$State[((Enum)object).ordinal()]) {
                        case 1: {
                            list.add(this.message);
                            list.add(LastHttpContent.EMPTY_LAST_CONTENT);
                            this.resetNow();
                            return;
                        }
                        case 2: {
                            if (!this.chunkedSupported) {
                                throw new IllegalArgumentException("Chunked messages not supported");
                            }
                            list.add(this.message);
                            return;
                        }
                    }
                    long l = this.contentLength();
                    if (l == 0L || l == -1L && this.isDecodingRequest()) {
                        list.add(this.message);
                        list.add(LastHttpContent.EMPTY_LAST_CONTENT);
                        this.resetNow();
                        return;
                    }
                    if (!$assertionsDisabled && object != State.READ_FIXED_LENGTH_CONTENT && object != State.READ_VARIABLE_LENGTH_CONTENT) {
                        throw new AssertionError();
                    }
                    list.add(this.message);
                    if (object == State.READ_FIXED_LENGTH_CONTENT) {
                        this.chunkSize = l;
                    }
                    return;
                } catch (Exception exception) {
                    list.add(this.invalidMessage(byteBuf, exception));
                    return;
                }
            }
            case 5: {
                int n = Math.min(byteBuf.readableBytes(), this.maxChunkSize);
                if (n > 0) {
                    ByteBuf byteBuf2 = byteBuf.readRetainedSlice(n);
                    list.add(new DefaultHttpContent(byteBuf2));
                }
                return;
            }
            case 6: {
                int n = byteBuf.readableBytes();
                if (n == 0) {
                    return;
                }
                int n2 = Math.min(n, this.maxChunkSize);
                if ((long)n2 > this.chunkSize) {
                    n2 = (int)this.chunkSize;
                }
                ByteBuf byteBuf3 = byteBuf.readRetainedSlice(n2);
                this.chunkSize -= (long)n2;
                if (this.chunkSize == 0L) {
                    list.add(new DefaultLastHttpContent(byteBuf3, this.validateHeaders));
                    this.resetNow();
                } else {
                    list.add(new DefaultHttpContent(byteBuf3));
                }
                return;
            }
            case 2: {
                try {
                    AppendableCharSequence appendableCharSequence = this.lineParser.parse(byteBuf);
                    if (appendableCharSequence == null) {
                        return;
                    }
                    int n = HttpObjectDecoder.getChunkSize(appendableCharSequence.toString());
                    this.chunkSize = n;
                    if (n == 0) {
                        this.currentState = State.READ_CHUNK_FOOTER;
                        return;
                    }
                    this.currentState = State.READ_CHUNKED_CONTENT;
                } catch (Exception exception) {
                    list.add(this.invalidChunk(byteBuf, exception));
                    return;
                }
            }
            case 7: {
                if (!$assertionsDisabled && this.chunkSize > Integer.MAX_VALUE) {
                    throw new AssertionError();
                }
                int n = Math.min((int)this.chunkSize, this.maxChunkSize);
                if ((n = Math.min(n, byteBuf.readableBytes())) == 0) {
                    return;
                }
                DefaultHttpContent defaultHttpContent = new DefaultHttpContent(byteBuf.readRetainedSlice(n));
                this.chunkSize -= (long)n;
                list.add(defaultHttpContent);
                if (this.chunkSize != 0L) {
                    return;
                }
                this.currentState = State.READ_CHUNK_DELIMITER;
            }
            case 8: {
                int n = byteBuf.writerIndex();
                int n3 = byteBuf.readerIndex();
                while (n > n3) {
                    byte by;
                    if ((by = byteBuf.getByte(n3++)) != 10) continue;
                    this.currentState = State.READ_CHUNK_SIZE;
                    break;
                }
                byteBuf.readerIndex(n3);
                return;
            }
            case 9: {
                try {
                    LastHttpContent lastHttpContent = this.readTrailingHeaders(byteBuf);
                    if (lastHttpContent == null) {
                        return;
                    }
                    list.add(lastHttpContent);
                    this.resetNow();
                    return;
                } catch (Exception exception) {
                    list.add(this.invalidChunk(byteBuf, exception));
                    return;
                }
            }
            case 10: {
                byteBuf.skipBytes(byteBuf.readableBytes());
                break;
            }
            case 11: {
                int n = byteBuf.readableBytes();
                if (n <= 0) break;
                list.add(byteBuf.readBytes(n));
                break;
            }
        }
    }

    @Override
    protected void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        super.decodeLast(channelHandlerContext, byteBuf, list);
        if (this.resetRequested) {
            this.resetNow();
        }
        if (this.message != null) {
            boolean bl;
            boolean bl2 = HttpUtil.isTransferEncodingChunked(this.message);
            if (this.currentState == State.READ_VARIABLE_LENGTH_CONTENT && !byteBuf.isReadable() && !bl2) {
                list.add(LastHttpContent.EMPTY_LAST_CONTENT);
                this.resetNow();
                return;
            }
            if (this.currentState == State.READ_HEADER) {
                list.add(this.invalidMessage(Unpooled.EMPTY_BUFFER, new PrematureChannelClosureException("Connection closed before received headers")));
                this.resetNow();
                return;
            }
            if (this.isDecodingRequest() || bl2) {
                bl = true;
            } else {
                boolean bl3 = bl = this.contentLength() > 0L;
            }
            if (!bl) {
                list.add(LastHttpContent.EMPTY_LAST_CONTENT);
            }
            this.resetNow();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (object instanceof HttpExpectationFailedEvent) {
            switch (1.$SwitchMap$io$netty$handler$codec$http$HttpObjectDecoder$State[this.currentState.ordinal()]) {
                case 2: 
                case 5: 
                case 6: {
                    this.reset();
                    break;
                }
            }
        }
        super.userEventTriggered(channelHandlerContext, object);
    }

    protected boolean isContentAlwaysEmpty(HttpMessage httpMessage) {
        if (httpMessage instanceof HttpResponse) {
            HttpResponse httpResponse = (HttpResponse)httpMessage;
            int n = httpResponse.status().code();
            if (n >= 100 && n < 200) {
                return n != 101 || httpResponse.headers().contains(HttpHeaderNames.SEC_WEBSOCKET_ACCEPT) || !httpResponse.headers().contains(HttpHeaderNames.UPGRADE, HttpHeaderValues.WEBSOCKET, false);
            }
            switch (n) {
                case 204: 
                case 304: {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean isSwitchingToNonHttp1Protocol(HttpResponse httpResponse) {
        if (httpResponse.status().code() != HttpResponseStatus.SWITCHING_PROTOCOLS.code()) {
            return true;
        }
        String string = httpResponse.headers().get(HttpHeaderNames.UPGRADE);
        return string == null || !string.contains(HttpVersion.HTTP_1_0.text()) && !string.contains(HttpVersion.HTTP_1_1.text());
    }

    public void reset() {
        this.resetRequested = true;
    }

    private void resetNow() {
        HttpResponse httpResponse;
        HttpMessage httpMessage = this.message;
        this.message = null;
        this.name = null;
        this.value = null;
        this.contentLength = Long.MIN_VALUE;
        this.lineParser.reset();
        this.headerParser.reset();
        this.trailer = null;
        if (!this.isDecodingRequest() && (httpResponse = (HttpResponse)httpMessage) != null && this.isSwitchingToNonHttp1Protocol(httpResponse)) {
            this.currentState = State.UPGRADED;
            return;
        }
        this.resetRequested = false;
        this.currentState = State.SKIP_CONTROL_CHARS;
    }

    private HttpMessage invalidMessage(ByteBuf byteBuf, Exception exception) {
        this.currentState = State.BAD_MESSAGE;
        byteBuf.skipBytes(byteBuf.readableBytes());
        if (this.message == null) {
            this.message = this.createInvalidMessage();
        }
        this.message.setDecoderResult(DecoderResult.failure(exception));
        HttpMessage httpMessage = this.message;
        this.message = null;
        return httpMessage;
    }

    private HttpContent invalidChunk(ByteBuf byteBuf, Exception exception) {
        this.currentState = State.BAD_MESSAGE;
        byteBuf.skipBytes(byteBuf.readableBytes());
        DefaultLastHttpContent defaultLastHttpContent = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER);
        defaultLastHttpContent.setDecoderResult(DecoderResult.failure(exception));
        this.message = null;
        this.trailer = null;
        return defaultLastHttpContent;
    }

    private static boolean skipControlCharacters(ByteBuf byteBuf) {
        boolean bl = false;
        int n = byteBuf.writerIndex();
        int n2 = byteBuf.readerIndex();
        while (n > n2) {
            short s;
            if (Character.isISOControl(s = byteBuf.getUnsignedByte(n2++)) || Character.isWhitespace(s)) continue;
            --n2;
            bl = true;
            break;
        }
        byteBuf.readerIndex(n2);
        return bl;
    }

    private State readHeaders(ByteBuf byteBuf) {
        State state;
        HttpMessage httpMessage = this.message;
        HttpHeaders httpHeaders = httpMessage.headers();
        AppendableCharSequence appendableCharSequence = this.headerParser.parse(byteBuf);
        if (appendableCharSequence == null) {
            return null;
        }
        if (appendableCharSequence.length() > 0) {
            do {
                char c = appendableCharSequence.charAt(0);
                if (this.name != null && (c == ' ' || c == '\t')) {
                    String string = appendableCharSequence.toString().trim();
                    String string2 = String.valueOf(this.value);
                    this.value = string2 + ' ' + string;
                } else {
                    if (this.name != null) {
                        httpHeaders.add(this.name, (Object)this.value);
                    }
                    this.splitHeader(appendableCharSequence);
                }
                appendableCharSequence = this.headerParser.parse(byteBuf);
                if (appendableCharSequence != null) continue;
                return null;
            } while (appendableCharSequence.length() > 0);
        }
        if (this.name != null) {
            httpHeaders.add(this.name, (Object)this.value);
        }
        this.name = null;
        this.value = null;
        if (this.isContentAlwaysEmpty(httpMessage)) {
            HttpUtil.setTransferEncodingChunked(httpMessage, false);
            state = State.SKIP_CONTROL_CHARS;
        } else {
            state = HttpUtil.isTransferEncodingChunked(httpMessage) ? State.READ_CHUNK_SIZE : (this.contentLength() >= 0L ? State.READ_FIXED_LENGTH_CONTENT : State.READ_VARIABLE_LENGTH_CONTENT);
        }
        return state;
    }

    private long contentLength() {
        if (this.contentLength == Long.MIN_VALUE) {
            this.contentLength = HttpUtil.getContentLength(this.message, -1L);
        }
        return this.contentLength;
    }

    private LastHttpContent readTrailingHeaders(ByteBuf byteBuf) {
        AppendableCharSequence appendableCharSequence = this.headerParser.parse(byteBuf);
        if (appendableCharSequence == null) {
            return null;
        }
        CharSequence charSequence = null;
        if (appendableCharSequence.length() > 0) {
            LastHttpContent lastHttpContent = this.trailer;
            if (lastHttpContent == null) {
                lastHttpContent = this.trailer = new DefaultLastHttpContent(Unpooled.EMPTY_BUFFER, this.validateHeaders);
            }
            do {
                List<String> list;
                char c = appendableCharSequence.charAt(0);
                if (charSequence != null && (c == ' ' || c == '\t')) {
                    list = lastHttpContent.trailingHeaders().getAll(charSequence);
                    if (!list.isEmpty()) {
                        int n = list.size() - 1;
                        String string = appendableCharSequence.toString().trim();
                        String string2 = (String)list.get(n);
                        list.set(n, string2 + string);
                    }
                } else {
                    this.splitHeader(appendableCharSequence);
                    list = this.name;
                    if (!(HttpHeaderNames.CONTENT_LENGTH.contentEqualsIgnoreCase((CharSequence)((Object)list)) || HttpHeaderNames.TRANSFER_ENCODING.contentEqualsIgnoreCase((CharSequence)((Object)list)) || HttpHeaderNames.TRAILER.contentEqualsIgnoreCase((CharSequence)((Object)list)))) {
                        lastHttpContent.trailingHeaders().add((CharSequence)((Object)list), (Object)this.value);
                    }
                    charSequence = this.name;
                    this.name = null;
                    this.value = null;
                }
                appendableCharSequence = this.headerParser.parse(byteBuf);
                if (appendableCharSequence != null) continue;
                return null;
            } while (appendableCharSequence.length() > 0);
            this.trailer = null;
            return lastHttpContent;
        }
        return LastHttpContent.EMPTY_LAST_CONTENT;
    }

    protected abstract boolean isDecodingRequest();

    protected abstract HttpMessage createMessage(String[] var1) throws Exception;

    protected abstract HttpMessage createInvalidMessage();

    private static int getChunkSize(String string) {
        string = string.trim();
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c != ';' && !Character.isWhitespace(c) && !Character.isISOControl(c)) continue;
            string = string.substring(0, i);
            break;
        }
        return Integer.parseInt(string, 16);
    }

    private static String[] splitInitialLine(AppendableCharSequence appendableCharSequence) {
        int n = HttpObjectDecoder.findNonWhitespace(appendableCharSequence, 0);
        int n2 = HttpObjectDecoder.findWhitespace(appendableCharSequence, n);
        int n3 = HttpObjectDecoder.findNonWhitespace(appendableCharSequence, n2);
        int n4 = HttpObjectDecoder.findWhitespace(appendableCharSequence, n3);
        int n5 = HttpObjectDecoder.findNonWhitespace(appendableCharSequence, n4);
        int n6 = HttpObjectDecoder.findEndOfString(appendableCharSequence);
        return new String[]{appendableCharSequence.subStringUnsafe(n, n2), appendableCharSequence.subStringUnsafe(n3, n4), n5 < n6 ? appendableCharSequence.subStringUnsafe(n5, n6) : EMPTY_VALUE};
    }

    private void splitHeader(AppendableCharSequence appendableCharSequence) {
        int n;
        int n2;
        char c;
        int n3;
        int n4 = appendableCharSequence.length();
        for (n3 = n2 = HttpObjectDecoder.findNonWhitespace(appendableCharSequence, 0); n3 < n4 && (c = appendableCharSequence.charAt(n3)) != ':' && !Character.isWhitespace(c); ++n3) {
        }
        for (n = n3; n < n4; ++n) {
            if (appendableCharSequence.charAt(n) != ':') continue;
            ++n;
            break;
        }
        this.name = appendableCharSequence.subStringUnsafe(n2, n3);
        int n5 = HttpObjectDecoder.findNonWhitespace(appendableCharSequence, n);
        if (n5 == n4) {
            this.value = EMPTY_VALUE;
        } else {
            int n6 = HttpObjectDecoder.findEndOfString(appendableCharSequence);
            this.value = appendableCharSequence.subStringUnsafe(n5, n6);
        }
    }

    private static int findNonWhitespace(AppendableCharSequence appendableCharSequence, int n) {
        for (int i = n; i < appendableCharSequence.length(); ++i) {
            if (Character.isWhitespace(appendableCharSequence.charAtUnsafe(i))) continue;
            return i;
        }
        return appendableCharSequence.length();
    }

    private static int findWhitespace(AppendableCharSequence appendableCharSequence, int n) {
        for (int i = n; i < appendableCharSequence.length(); ++i) {
            if (!Character.isWhitespace(appendableCharSequence.charAtUnsafe(i))) continue;
            return i;
        }
        return appendableCharSequence.length();
    }

    private static int findEndOfString(AppendableCharSequence appendableCharSequence) {
        for (int i = appendableCharSequence.length() - 1; i > 0; --i) {
            if (Character.isWhitespace(appendableCharSequence.charAtUnsafe(i))) continue;
            return i + 1;
        }
        return 1;
    }

    private static final class LineParser
    extends HeaderParser {
        LineParser(AppendableCharSequence appendableCharSequence, int n) {
            super(appendableCharSequence, n);
        }

        @Override
        public AppendableCharSequence parse(ByteBuf byteBuf) {
            this.reset();
            return super.parse(byteBuf);
        }

        @Override
        protected TooLongFrameException newException(int n) {
            return new TooLongFrameException("An HTTP line is larger than " + n + " bytes.");
        }
    }

    private static class HeaderParser
    implements ByteProcessor {
        private final AppendableCharSequence seq;
        private final int maxLength;
        private int size;

        HeaderParser(AppendableCharSequence appendableCharSequence, int n) {
            this.seq = appendableCharSequence;
            this.maxLength = n;
        }

        public AppendableCharSequence parse(ByteBuf byteBuf) {
            int n = this.size;
            this.seq.reset();
            int n2 = byteBuf.forEachByte(this);
            if (n2 == -1) {
                this.size = n;
                return null;
            }
            byteBuf.readerIndex(n2 + 1);
            return this.seq;
        }

        public void reset() {
            this.size = 0;
        }

        @Override
        public boolean process(byte by) throws Exception {
            char c = (char)(by & 0xFF);
            if (c == '\r') {
                return false;
            }
            if (c == '\n') {
                return true;
            }
            if (++this.size > this.maxLength) {
                throw this.newException(this.maxLength);
            }
            this.seq.append(c);
            return false;
        }

        protected TooLongFrameException newException(int n) {
            return new TooLongFrameException("HTTP header is larger than " + n + " bytes.");
        }
    }

    private static enum State {
        SKIP_CONTROL_CHARS,
        READ_INITIAL,
        READ_HEADER,
        READ_VARIABLE_LENGTH_CONTENT,
        READ_FIXED_LENGTH_CONTENT,
        READ_CHUNK_SIZE,
        READ_CHUNKED_CONTENT,
        READ_CHUNK_DELIMITER,
        READ_CHUNK_FOOTER,
        BAD_MESSAGE,
        UPGRADED;

    }
}

