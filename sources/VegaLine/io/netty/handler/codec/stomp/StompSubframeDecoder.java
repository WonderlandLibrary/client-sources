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
    private int alreadyReadChunkSize;
    private LastStompContentSubframe lastContent;
    private long contentLength = -1L;

    public StompSubframeDecoder() {
        this(1024, 8132);
    }

    public StompSubframeDecoder(int maxLineLength, int maxChunkSize) {
        super(State.SKIP_CONTROL_CHARACTERS);
        if (maxLineLength <= 0) {
            throw new IllegalArgumentException("maxLineLength must be a positive integer: " + maxLineLength);
        }
        if (maxChunkSize <= 0) {
            throw new IllegalArgumentException("maxChunkSize must be a positive integer: " + maxChunkSize);
        }
        this.maxChunkSize = maxChunkSize;
        this.maxLineLength = maxLineLength;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (1.$SwitchMap$io$netty$handler$codec$stomp$StompSubframeDecoder$State[((State)this.state()).ordinal()]) {
            case 1: {
                StompSubframeDecoder.skipControlCharacters(in);
                this.checkpoint(State.READ_HEADERS);
            }
            case 2: {
                command = StompCommand.UNKNOWN;
                frame = null;
                try {
                    command = this.readCommand(in);
                    frame = new DefaultStompHeadersSubframe(command);
                    this.checkpoint(this.readHeaders(in, frame.headers()));
                    out.add(frame);
                    break;
                } catch (Exception e) {
                    if (frame == null) {
                        frame = new DefaultStompHeadersSubframe(command);
                    }
                    frame.setDecoderResult(DecoderResult.failure(e));
                    out.add(frame);
                    this.checkpoint(State.BAD_FRAME);
                    return;
                }
            }
            case 3: {
                in.skipBytes(this.actualReadableBytes());
                return;
            }
        }
        try {
            switch (1.$SwitchMap$io$netty$handler$codec$stomp$StompSubframeDecoder$State[((State)this.state()).ordinal()]) {
                case 4: {
                    toRead = in.readableBytes();
                    if (toRead == 0) {
                        return;
                    }
                    if (toRead > this.maxChunkSize) {
                        toRead = this.maxChunkSize;
                    }
                    if (this.contentLength < 0L) ** GOTO lbl47
                    remainingLength = (int)(this.contentLength - (long)this.alreadyReadChunkSize);
                    if (toRead > remainingLength) {
                        toRead = remainingLength;
                    }
                    chunkBuffer = ByteBufUtil.readBytes(ctx.alloc(), in, toRead);
                    if ((long)(this.alreadyReadChunkSize += toRead) < this.contentLength) ** GOTO lbl44
                    this.lastContent = new DefaultLastStompContentSubframe(chunkBuffer);
                    this.checkpoint(State.FINALIZE_FRAME_READ);
                    ** GOTO lbl61
lbl44:
                    // 1 sources

                    out.add(new DefaultStompContentSubframe(chunkBuffer));
                    return;
lbl47:
                    // 1 sources

                    nulIndex = ByteBufUtil.indexOf(in, in.readerIndex(), in.writerIndex(), (byte)0);
                    if (nulIndex != in.readerIndex()) ** GOTO lbl51
                    this.checkpoint(State.FINALIZE_FRAME_READ);
                    ** GOTO lbl61
lbl51:
                    // 1 sources

                    toRead = nulIndex > 0 ? nulIndex - in.readerIndex() : in.writerIndex() - in.readerIndex();
                    chunkBuffer = ByteBufUtil.readBytes(ctx.alloc(), in, toRead);
                    this.alreadyReadChunkSize += toRead;
                    if (nulIndex > 0) {
                        this.lastContent = new DefaultLastStompContentSubframe(chunkBuffer);
                        this.checkpoint(State.FINALIZE_FRAME_READ);
                    } else {
                        out.add(new DefaultStompContentSubframe(chunkBuffer));
                        return;
                    }
                }
lbl61:
                // 4 sources

                case 5: {
                    StompSubframeDecoder.skipNullCharacter(in);
                    if (this.lastContent == null) {
                        this.lastContent = LastStompContentSubframe.EMPTY_LAST_CONTENT;
                    }
                    out.add(this.lastContent);
                    this.resetDecoder();
                }
            }
        } catch (Exception e) {
            errorContent = new DefaultLastStompContentSubframe(Unpooled.EMPTY_BUFFER);
            errorContent.setDecoderResult(DecoderResult.failure(e));
            out.add(errorContent);
            this.checkpoint(State.BAD_FRAME);
        }
    }

    private StompCommand readCommand(ByteBuf in) {
        String commandStr = StompSubframeDecoder.readLine(in, this.maxLineLength);
        StompCommand command = null;
        try {
            command = StompCommand.valueOf(commandStr);
        } catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        if (command == null) {
            commandStr = commandStr.toUpperCase(Locale.US);
            try {
                command = StompCommand.valueOf(commandStr);
            } catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        if (command == null) {
            throw new DecoderException("failed to read command from channel");
        }
        return command;
    }

    private State readHeaders(ByteBuf buffer, StompHeaders headers) {
        String line;
        while (!(line = StompSubframeDecoder.readLine(buffer, this.maxLineLength)).isEmpty()) {
            String[] split = line.split(":");
            if (split.length != 2) continue;
            headers.add(split[0], split[1]);
        }
        if (headers.contains(StompHeaders.CONTENT_LENGTH)) {
            this.contentLength = StompSubframeDecoder.getContentLength(headers, 0L);
            if (this.contentLength == 0L) {
                return State.FINALIZE_FRAME_READ;
            }
        }
        return State.READ_CONTENT;
    }

    private static long getContentLength(StompHeaders headers, long defaultValue) {
        long contentLength = headers.getLong(StompHeaders.CONTENT_LENGTH, defaultValue);
        if (contentLength < 0L) {
            throw new DecoderException(StompHeaders.CONTENT_LENGTH + " must be non-negative");
        }
        return contentLength;
    }

    private static void skipNullCharacter(ByteBuf buffer) {
        byte b = buffer.readByte();
        if (b != 0) {
            throw new IllegalStateException("unexpected byte in buffer " + b + " while expecting NULL byte");
        }
    }

    private static void skipControlCharacters(ByteBuf buffer) {
        byte b;
        while ((b = buffer.readByte()) == 13 || b == 10) {
        }
        buffer.readerIndex(buffer.readerIndex() - 1);
    }

    private static String readLine(ByteBuf buffer, int maxLineLength) {
        AppendableCharSequence buf = new AppendableCharSequence(128);
        int lineLength = 0;
        while (true) {
            byte nextByte;
            if ((nextByte = buffer.readByte()) == 13) {
                nextByte = buffer.readByte();
                if (nextByte != 10) continue;
                return buf.toString();
            }
            if (nextByte == 10) {
                return buf.toString();
            }
            if (lineLength >= maxLineLength) {
                throw new TooLongFrameException("An STOMP line is larger than " + maxLineLength + " bytes.");
            }
            ++lineLength;
            buf.append((char)nextByte);
        }
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

