/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.multipart.AbstractDiskHttpData;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import java.io.IOException;
import java.nio.charset.Charset;

public class DiskAttribute
extends AbstractDiskHttpData
implements Attribute {
    public static String baseDirectory;
    public static boolean deleteOnExitTemporaryFile;
    public static final String prefix = "Attr_";
    public static final String postfix = ".att";

    public DiskAttribute(String name) {
        this(name, HttpConstants.DEFAULT_CHARSET);
    }

    public DiskAttribute(String name, long definedSize) {
        this(name, definedSize, HttpConstants.DEFAULT_CHARSET);
    }

    public DiskAttribute(String name, Charset charset) {
        super(name, charset, 0L);
    }

    public DiskAttribute(String name, long definedSize, Charset charset) {
        super(name, charset, definedSize);
    }

    public DiskAttribute(String name, String value) throws IOException {
        this(name, value, HttpConstants.DEFAULT_CHARSET);
    }

    public DiskAttribute(String name, String value, Charset charset) throws IOException {
        super(name, charset, 0L);
        this.setValue(value);
    }

    @Override
    public InterfaceHttpData.HttpDataType getHttpDataType() {
        return InterfaceHttpData.HttpDataType.Attribute;
    }

    @Override
    public String getValue() throws IOException {
        byte[] bytes = this.get();
        return new String(bytes, this.getCharset());
    }

    @Override
    public void setValue(String value) throws IOException {
        if (value == null) {
            throw new NullPointerException("value");
        }
        byte[] bytes = value.getBytes(this.getCharset());
        this.checkSize(bytes.length);
        ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
        if (this.definedSize > 0L) {
            this.definedSize = buffer.readableBytes();
        }
        this.setContent(buffer);
    }

    @Override
    public void addContent(ByteBuf buffer, boolean last) throws IOException {
        long newDefinedSize = this.size + (long)buffer.readableBytes();
        this.checkSize(newDefinedSize);
        if (this.definedSize > 0L && this.definedSize < newDefinedSize) {
            this.definedSize = newDefinedSize;
        }
        super.addContent(buffer, last);
    }

    public int hashCode() {
        return this.getName().hashCode();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Attribute)) {
            return false;
        }
        Attribute attribute = (Attribute)o;
        return this.getName().equalsIgnoreCase(attribute.getName());
    }

    @Override
    public int compareTo(InterfaceHttpData o) {
        if (!(o instanceof Attribute)) {
            throw new ClassCastException("Cannot compare " + (Object)((Object)this.getHttpDataType()) + " with " + (Object)((Object)o.getHttpDataType()));
        }
        return this.compareTo((Attribute)o);
    }

    @Override
    public int compareTo(Attribute o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }

    public String toString() {
        try {
            return this.getName() + '=' + this.getValue();
        } catch (IOException e) {
            return this.getName() + '=' + e;
        }
    }

    @Override
    protected boolean deleteOnExit() {
        return deleteOnExitTemporaryFile;
    }

    @Override
    protected String getBaseDirectory() {
        return baseDirectory;
    }

    @Override
    protected String getDiskFilename() {
        return this.getName() + postfix;
    }

    @Override
    protected String getPostfix() {
        return postfix;
    }

    @Override
    protected String getPrefix() {
        return prefix;
    }

    @Override
    public Attribute copy() {
        ByteBuf content = this.content();
        return this.replace(content != null ? content.copy() : null);
    }

    @Override
    public Attribute duplicate() {
        ByteBuf content = this.content();
        return this.replace(content != null ? content.duplicate() : null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Attribute retainedDuplicate() {
        ByteBuf content = this.content();
        if (content != null) {
            content = content.retainedDuplicate();
            boolean success = false;
            try {
                Attribute duplicate = this.replace(content);
                success = true;
                Attribute attribute = duplicate;
                return attribute;
            } finally {
                if (!success) {
                    content.release();
                }
            }
        }
        return this.replace(null);
    }

    @Override
    public Attribute replace(ByteBuf content) {
        DiskAttribute attr = new DiskAttribute(this.getName());
        attr.setCharset(this.getCharset());
        if (content != null) {
            try {
                attr.setContent(content);
            } catch (IOException e) {
                throw new ChannelException(e);
            }
        }
        return attr;
    }

    @Override
    public Attribute retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public Attribute retain() {
        super.retain();
        return this;
    }

    @Override
    public Attribute touch() {
        super.touch();
        return this;
    }

    @Override
    public Attribute touch(Object hint) {
        super.touch(hint);
        return this;
    }

    static {
        deleteOnExitTemporaryFile = true;
    }
}

