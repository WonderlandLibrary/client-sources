/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.stomp.DefaultStompHeaders;
import io.netty.handler.codec.stomp.StompCommand;
import io.netty.handler.codec.stomp.StompHeaders;
import io.netty.handler.codec.stomp.StompHeadersSubframe;

public class DefaultStompHeadersSubframe
implements StompHeadersSubframe {
    protected final StompCommand command;
    protected DecoderResult decoderResult = DecoderResult.SUCCESS;
    protected final DefaultStompHeaders headers;

    public DefaultStompHeadersSubframe(StompCommand stompCommand) {
        this(stompCommand, null);
    }

    DefaultStompHeadersSubframe(StompCommand stompCommand, DefaultStompHeaders defaultStompHeaders) {
        if (stompCommand == null) {
            throw new NullPointerException("command");
        }
        this.command = stompCommand;
        this.headers = defaultStompHeaders == null ? new DefaultStompHeaders() : defaultStompHeaders;
    }

    @Override
    public StompCommand command() {
        return this.command;
    }

    @Override
    public StompHeaders headers() {
        return this.headers;
    }

    @Override
    public DecoderResult decoderResult() {
        return this.decoderResult;
    }

    @Override
    public void setDecoderResult(DecoderResult decoderResult) {
        this.decoderResult = decoderResult;
    }

    public String toString() {
        return "StompFrame{command=" + (Object)((Object)this.command) + ", headers=" + this.headers + '}';
    }
}

