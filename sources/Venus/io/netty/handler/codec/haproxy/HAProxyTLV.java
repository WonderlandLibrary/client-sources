/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.haproxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class HAProxyTLV
extends DefaultByteBufHolder {
    private final Type type;
    private final byte typeByteValue;

    HAProxyTLV(Type type, byte by, ByteBuf byteBuf) {
        super(byteBuf);
        ObjectUtil.checkNotNull(type, "type");
        this.type = type;
        this.typeByteValue = by;
    }

    public Type type() {
        return this.type;
    }

    public byte typeByteValue() {
        return this.typeByteValue;
    }

    @Override
    public HAProxyTLV copy() {
        return this.replace(this.content().copy());
    }

    @Override
    public HAProxyTLV duplicate() {
        return this.replace(this.content().duplicate());
    }

    @Override
    public HAProxyTLV retainedDuplicate() {
        return this.replace(this.content().retainedDuplicate());
    }

    @Override
    public HAProxyTLV replace(ByteBuf byteBuf) {
        return new HAProxyTLV(this.type, this.typeByteValue, byteBuf);
    }

    @Override
    public HAProxyTLV retain() {
        super.retain();
        return this;
    }

    @Override
    public HAProxyTLV retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public HAProxyTLV touch() {
        super.touch();
        return this;
    }

    @Override
    public HAProxyTLV touch(Object object) {
        super.touch(object);
        return this;
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

    public static enum Type {
        PP2_TYPE_ALPN,
        PP2_TYPE_AUTHORITY,
        PP2_TYPE_SSL,
        PP2_TYPE_SSL_VERSION,
        PP2_TYPE_SSL_CN,
        PP2_TYPE_NETNS,
        OTHER;


        public static Type typeForByteValue(byte by) {
            switch (by) {
                case 1: {
                    return PP2_TYPE_ALPN;
                }
                case 2: {
                    return PP2_TYPE_AUTHORITY;
                }
                case 32: {
                    return PP2_TYPE_SSL;
                }
                case 33: {
                    return PP2_TYPE_SSL_VERSION;
                }
                case 34: {
                    return PP2_TYPE_SSL_CN;
                }
                case 48: {
                    return PP2_TYPE_NETNS;
                }
            }
            return OTHER;
        }
    }
}

