/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.sctp;

import com.sun.nio.sctp.MessageInfo;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class SctpMessage
extends DefaultByteBufHolder {
    private final int streamIdentifier;
    private final int protocolIdentifier;
    private final boolean unordered;
    private final MessageInfo msgInfo;

    public SctpMessage(int n, int n2, ByteBuf byteBuf) {
        this(n, n2, false, byteBuf);
    }

    public SctpMessage(int n, int n2, boolean bl, ByteBuf byteBuf) {
        super(byteBuf);
        this.protocolIdentifier = n;
        this.streamIdentifier = n2;
        this.unordered = bl;
        this.msgInfo = null;
    }

    public SctpMessage(MessageInfo messageInfo, ByteBuf byteBuf) {
        super(byteBuf);
        if (messageInfo == null) {
            throw new NullPointerException("msgInfo");
        }
        this.msgInfo = messageInfo;
        this.streamIdentifier = messageInfo.streamNumber();
        this.protocolIdentifier = messageInfo.payloadProtocolID();
        this.unordered = messageInfo.isUnordered();
    }

    public int streamIdentifier() {
        return this.streamIdentifier;
    }

    public int protocolIdentifier() {
        return this.protocolIdentifier;
    }

    public boolean isUnordered() {
        return this.unordered;
    }

    public MessageInfo messageInfo() {
        return this.msgInfo;
    }

    public boolean isComplete() {
        if (this.msgInfo != null) {
            return this.msgInfo.isComplete();
        }
        return false;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        SctpMessage sctpMessage = (SctpMessage)object;
        if (this.protocolIdentifier != sctpMessage.protocolIdentifier) {
            return true;
        }
        if (this.streamIdentifier != sctpMessage.streamIdentifier) {
            return true;
        }
        if (this.unordered != sctpMessage.unordered) {
            return true;
        }
        return this.content().equals(sctpMessage.content());
    }

    @Override
    public int hashCode() {
        int n = this.streamIdentifier;
        n = 31 * n + this.protocolIdentifier;
        n = 31 * n + (this.unordered ? 1231 : 1237);
        n = 31 * n + this.content().hashCode();
        return n;
    }

    @Override
    public SctpMessage copy() {
        return (SctpMessage)super.copy();
    }

    @Override
    public SctpMessage duplicate() {
        return (SctpMessage)super.duplicate();
    }

    @Override
    public SctpMessage retainedDuplicate() {
        return (SctpMessage)super.retainedDuplicate();
    }

    @Override
    public SctpMessage replace(ByteBuf byteBuf) {
        if (this.msgInfo == null) {
            return new SctpMessage(this.protocolIdentifier, this.streamIdentifier, this.unordered, byteBuf);
        }
        return new SctpMessage(this.msgInfo, byteBuf);
    }

    @Override
    public SctpMessage retain() {
        super.retain();
        return this;
    }

    @Override
    public SctpMessage retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public SctpMessage touch() {
        super.touch();
        return this;
    }

    @Override
    public SctpMessage touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public String toString() {
        return "SctpFrame{streamIdentifier=" + this.streamIdentifier + ", protocolIdentifier=" + this.protocolIdentifier + ", unordered=" + this.unordered + ", data=" + this.contentToString() + '}';
    }

    @Override
    public ByteBufHolder touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ByteBufHolder touch() {
        return this.touch();
    }

    @Override
    public ByteBufHolder retain(int n) {
        return this.retain(n);
    }

    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }

    @Override
    public ByteBufHolder replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public ByteBufHolder retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }

    @Override
    public ByteBufHolder copy() {
        return this.copy();
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }

    @Override
    public ReferenceCounted retain(int n) {
        return this.retain(n);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
}

