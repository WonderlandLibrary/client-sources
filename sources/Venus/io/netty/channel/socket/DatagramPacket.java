/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.AddressedEnvelope;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.util.ReferenceCounted;
import java.net.InetSocketAddress;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class DatagramPacket
extends DefaultAddressedEnvelope<ByteBuf, InetSocketAddress>
implements ByteBufHolder {
    public DatagramPacket(ByteBuf byteBuf, InetSocketAddress inetSocketAddress) {
        super(byteBuf, inetSocketAddress);
    }

    public DatagramPacket(ByteBuf byteBuf, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2) {
        super(byteBuf, inetSocketAddress, inetSocketAddress2);
    }

    @Override
    public DatagramPacket copy() {
        return this.replace(((ByteBuf)this.content()).copy());
    }

    @Override
    public DatagramPacket duplicate() {
        return this.replace(((ByteBuf)this.content()).duplicate());
    }

    @Override
    public DatagramPacket retainedDuplicate() {
        return this.replace(((ByteBuf)this.content()).retainedDuplicate());
    }

    @Override
    public DatagramPacket replace(ByteBuf byteBuf) {
        return new DatagramPacket(byteBuf, (InetSocketAddress)this.recipient(), (InetSocketAddress)this.sender());
    }

    @Override
    public DatagramPacket retain() {
        super.retain();
        return this;
    }

    @Override
    public DatagramPacket retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public DatagramPacket touch() {
        super.touch();
        return this;
    }

    @Override
    public DatagramPacket touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public AddressedEnvelope touch(Object object) {
        return this.touch(object);
    }

    @Override
    public AddressedEnvelope touch() {
        return this.touch();
    }

    @Override
    public AddressedEnvelope retain(int n) {
        return this.retain(n);
    }

    @Override
    public AddressedEnvelope retain() {
        return this.retain();
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
    public ByteBuf content() {
        return (ByteBuf)super.content();
    }
}

