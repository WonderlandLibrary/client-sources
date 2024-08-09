/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class InternalAttribute
extends AbstractReferenceCounted
implements InterfaceHttpData {
    private final List<ByteBuf> value = new ArrayList<ByteBuf>();
    private final Charset charset;
    private int size;

    InternalAttribute(Charset charset) {
        this.charset = charset;
    }

    @Override
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return InterfaceHttpData.HttpDataType.InternalAttribute;
    }

    public void addValue(String string) {
        if (string == null) {
            throw new NullPointerException("value");
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(string, this.charset);
        this.value.add(byteBuf);
        this.size += byteBuf.readableBytes();
    }

    public void addValue(String string, int n) {
        if (string == null) {
            throw new NullPointerException("value");
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(string, this.charset);
        this.value.add(n, byteBuf);
        this.size += byteBuf.readableBytes();
    }

    public void setValue(String string, int n) {
        if (string == null) {
            throw new NullPointerException("value");
        }
        ByteBuf byteBuf = Unpooled.copiedBuffer(string, this.charset);
        ByteBuf byteBuf2 = this.value.set(n, byteBuf);
        if (byteBuf2 != null) {
            this.size -= byteBuf2.readableBytes();
            byteBuf2.release();
        }
        this.size += byteBuf.readableBytes();
    }

    public int hashCode() {
        return this.getName().hashCode();
    }

    public boolean equals(Object object) {
        if (!(object instanceof InternalAttribute)) {
            return true;
        }
        InternalAttribute internalAttribute = (InternalAttribute)object;
        return this.getName().equalsIgnoreCase(internalAttribute.getName());
    }

    @Override
    public int compareTo(InterfaceHttpData interfaceHttpData) {
        if (!(interfaceHttpData instanceof InternalAttribute)) {
            throw new ClassCastException("Cannot compare " + (Object)((Object)this.getHttpDataType()) + " with " + (Object)((Object)interfaceHttpData.getHttpDataType()));
        }
        return this.compareTo((InternalAttribute)interfaceHttpData);
    }

    @Override
    public int compareTo(InternalAttribute internalAttribute) {
        return this.getName().compareToIgnoreCase(internalAttribute.getName());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ByteBuf byteBuf : this.value) {
            stringBuilder.append(byteBuf.toString(this.charset));
        }
        return stringBuilder.toString();
    }

    public int size() {
        return this.size;
    }

    public ByteBuf toByteBuf() {
        return Unpooled.compositeBuffer().addComponents(this.value).writerIndex(this.size()).readerIndex(0);
    }

    @Override
    public String getName() {
        return "InternalAttribute";
    }

    @Override
    protected void deallocate() {
    }

    @Override
    public InterfaceHttpData retain() {
        for (ByteBuf byteBuf : this.value) {
            byteBuf.retain();
        }
        return this;
    }

    @Override
    public InterfaceHttpData retain(int n) {
        for (ByteBuf byteBuf : this.value) {
            byteBuf.retain(n);
        }
        return this;
    }

    @Override
    public InterfaceHttpData touch() {
        for (ByteBuf byteBuf : this.value) {
            byteBuf.touch();
        }
        return this;
    }

    @Override
    public InterfaceHttpData touch(Object object) {
        for (ByteBuf byteBuf : this.value) {
            byteBuf.touch(object);
        }
        return this;
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
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((InterfaceHttpData)object);
    }
}

