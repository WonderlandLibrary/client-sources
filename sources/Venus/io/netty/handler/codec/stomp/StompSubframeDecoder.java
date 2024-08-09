/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.stomp.DefaultLastStompContentSubframe;
import io.netty.handler.codec.stomp.DefaultStompContentSubframe;
import io.netty.handler.codec.stomp.DefaultStompHeadersSubframe;
import io.netty.handler.codec.stomp.LastStompContentSubframe;
import io.netty.handler.codec.stomp.StompCommand;
import io.netty.handler.codec.stomp.StompHeaders;
import io.netty.util.internal.AppendableCharSequence;
import java.util.List;
import java.util.Locale;

public class StompSubframeDecoder
extends ReplayingDecoder<State> {
    private static final int DEFAULT_CHUNK_SIZE = 8132;
    private static final int DEFAULT_MAX_LINE_LENGTH = 1024;
    private final int maxLineLength;
    private final int maxChunkSize;
    private final boolean validateHeaders;
    private int alreadyReadChunkSize;
    private LastStompContentSubframe lastContent;
    private long contentLength = -1L;

    public StompSubframeDecoder() {
        this(1024, 8132);
    }

    public StompSubframeDecoder(boolean bl) {
        this(1024, 8132, bl);
    }

    public StompSubframeDecoder(int n, int n2) {
        this(n, n2, false);
    }

    public StompSubframeDecoder(int n, int n2, boolean bl) {
        super(State.SKIP_CONTROL_CHARACTERS);
        if (n <= 0) {
            throw new IllegalArgumentException("maxLineLength must be a positive integer: " + n);
        }
        if (n2 <= 0) {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + n2);
        }
        this.maxChunkSize = n2;
        this.maxLineLength = n;
        this.validateHeaders = bl;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    protected void decode(ChannelHandlerContext var1_1, ByteBuf var2_2, List<Object> var3_3) throws Exception {
        switch (1.$SwitchMap$io$netty$handler$codec$stomp$StompSubframeDecoder$State[((State)this.state()).ordinal()]) {
            case 1: {
                StompSubframeDecoder.skipControlCharacters(var2_2);
                this.checkpoint(State.READ_HEADERS);
            }
            case 2: {
                var4_4 = StompCommand.UNKNOWN;
                var5_7 = null;
                try {
                    var4_4 = this.readCommand(var2_2);
                    var5_7 = new DefaultStompHeadersSubframe(var4_4);
                    this.checkpoint(this.readHeaders(var2_2, var5_7.headers()));
                    var3_3.add(var5_7);
                    break;
                } catch (Exception var6_10) {
                    if (var5_7 == null) {
                        var5_7 = new DefaultStompHeadersSubframe(var4_4);
                    }
                    var5_7.setDecoderResult(DecoderResult.failure(var6_10));
                    var3_3.add(var5_7);
                    this.checkpoint(State.BAD_FRAME);
                    return;
                }
            }
            case 3: {
                var2_2.skipBytes(this.actualReadableBytes());
                return;
            }
        }
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$stomp$StompSubframeDecoder$State[((State)this.state()).ordinal()]) {
                case 4: {
                    var4_5 = var2_2.readableBytes();
                    if (var4_5 == 0) {
                        return;
                    }
                    if (var4_5 > this.maxChunkSize) {
                        var4_5 = this.maxChunkSize;
                    }
                    if (this.contentLength < 0L) ** GOTO lbl47
                    var5_8 = (int)(this.contentLength - (long)this.alreadyReadChunkSize);
                    if (var4_5 > var5_8) {
                        var4_5 = var5_8;
                    }
                    var6_11 = ByteBufUtil.readBytes(var1_1.alloc(), var2_2, var4_5);
                    if ((long)(this.alreadyReadChunkSize += var4_5) < this.contentLength) ** GOTO lbl44
                    this.lastContent = new DefaultLastStompContentSubframe(var6_11);
                    this.checkpoint(State.FINALIZE_FRAME_READ);
                    ** GOTO lbl61
lbl44:
                    // 1 sources

                    var3_3.add(new DefaultStompContentSubframe(var6_11));
                    return;
lbl47:
                    // 1 sources

                    var5_9 = ByteBufUtil.indexOf(var2_2, var2_2.readerIndex(), var2_2.writerIndex(), (byte)0);
                    if (var5_9 != var2_2.readerIndex()) ** GOTO lbl51
                    this.checkpoint(State.FINALIZE_FRAME_READ);
                    ** GOTO lbl61
lbl51:
                    // 1 sources

                    var4_5 = var5_9 > 0 ? var5_9 - var2_2.readerIndex() : var2_2.writerIndex() - var2_2.readerIndex();
                    var6_12 = ByteBufUtil.readBytes(var1_1.alloc(), var2_2, var4_5);
                    this.alreadyReadChunkSize += var4_5;
                    if (var5_9 > 0) {
                        this.lastContent = new DefaultLastStompContentSubframe(var6_12);
                        this.checkpoint(State.FINALIZE_FRAME_READ);
                    } else {
                        var3_3.add(new DefaultStompContentSubframe(var6_12));
                        return;
                    }
                }
lbl61:
                // 4 sources

                case 5: {
                    StompSubframeDecoder.skipNullCharacter(var2_2);
                    if (this.lastContent == null) {
                        this.lastContent = LastStompContentSubframe.EMPTY_LAST_CONTENT;
                    }
                    var3_3.add(this.lastContent);
                    this.resetDecoder();
                }
            }
        } catch (Exception var4_6) {
            var5_7 = new DefaultLastStompContentSubframe(Unpooled.EMPTY_BUFFER);
            var5_7.setDecoderResult(DecoderResult.failure(var4_6));
            var3_3.add(var5_7);
            this.checkpoint(State.BAD_FRAME);
        }
    }

    private StompCommand readCommand(ByteBuf byteBuf) {
        String string = this.readLine(byteBuf, 16);
        StompCommand stompCommand = null;
        try {
            stompCommand = StompCommand.valueOf(string);
        } catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        if (stompCommand == null) {
            string = string.toUpperCase(Locale.US);
            try {
                stompCommand = StompCommand.valueOf(string);
            } catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        if (stompCommand == null) {
            throw new DecoderException("failed to read command from channel");
        }
        return stompCommand;
    }

    private State readHeaders(ByteBuf byteBuf, StompHeaders stompHeaders) {
        boolean bl;
        AppendableCharSequence appendableCharSequence = new AppendableCharSequence(128);
        while (bl = this.readHeader(stompHeaders, appendableCharSequence, byteBuf)) {
        }
        if (stompHeaders.contains(StompHeaders.CONTENT_LENGTH)) {
            this.contentLength = StompSubframeDecoder.getContentLength(stompHeaders, 0L);
            if (this.contentLength == 0L) {
                return State.FINALIZE_FRAME_READ;
            }
        }
        return State.READ_CONTENT;
    }

    private static long getContentLength(StompHeaders stompHeaders, long l) {
        long l2 = stompHeaders.getLong(StompHeaders.CONTENT_LENGTH, l);
        if (l2 < 0L) {
            throw new DecoderException(StompHeaders.CONTENT_LENGTH + " must be non-negative");
        }
        return l2;
    }

    private static void skipNullCharacter(ByteBuf byteBuf) {
        byte by = byteBuf.readByte();
        if (by != 0) {
            throw new IllegalStateException("unexpected byte in buffer " + by + " while expecting NULL byte");
        }
    }

    private static void skipControlCharacters(ByteBuf byteBuf) {
        byte by;
        while ((by = byteBuf.readByte()) == 13 || by == 10) {
        }
        byteBuf.readerIndex(byteBuf.readerIndex() - 1);
    }

    private String readLine(ByteBuf byteBuf, int n) {
        AppendableCharSequence appendableCharSequence = new AppendableCharSequence(n);
        int n2 = 0;
        while (true) {
            byte by;
            if ((by = byteBuf.readByte()) == 13) {
                continue;
            }
            if (by == 10) {
                return appendableCharSequence.toString();
            }
            if (n2 >= this.maxLineLength) {
                this.invalidLineLength();
            }
            ++n2;
            appendableCharSequence.append((char)by);
        }
    }

    private boolean readHeader(StompHeaders stompHeaders, AppendableCharSequence appendableCharSequence, ByteBuf byteBuf) {
        appendableCharSequence.reset();
        int n = 0;
        String string = null;
        boolean bl = false;
        while (true) {
            byte by;
            if ((by = byteBuf.readByte()) == 58 && string == null) {
                string = appendableCharSequence.toString();
                bl = true;
                appendableCharSequence.reset();
                continue;
            }
            if (by == 13) continue;
            if (by == 10) {
                if (string == null && n == 0) {
                    return true;
                }
                if (bl) {
                    stompHeaders.add(string, appendableCharSequence.toString());
                } else if (this.validateHeaders) {
                    this.invalidHeader(string, appendableCharSequence.toString());
                }
                return false;
            }
            if (n >= this.maxLineLength) {
                this.invalidLineLength();
            }
            if (by == 58 && string != null) {
                bl = false;
            }
            ++n;
            appendableCharSequence.append((char)by);
        }
    }

    private void invalidHeader(String string, String string2) {
        String string3 = string != null ? string + ":" + string2 : string2;
        throw new IllegalArgumentException("a header value or name contains a prohibited character ':', " + string3);
    }

    private void invalidLineLength() {
        throw new TooLongFrameException("An STOMP line is larger than " + this.maxLineLength + " bytes.");
    }

    private void resetDecoder() {
        this.checkpoint(State.SKIP_CONTROL_CHARACTERS);
        this.contentLength = -1L;
        this.alreadyReadChunkSize = 0;
        this.lastContent = null;
    }

    static enum State {
        SKIP_CONTROL_CHARACTERS,
        READ_HEADERS,
        READ_CONTENT,
        FINALIZE_FRAME_READ,
        BAD_FRAME,
        INVALID_CHUNK;

    }
}

