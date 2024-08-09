/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.multipart;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelException;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.multipart.HttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.ReferenceCounted;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractHttpData
extends AbstractReferenceCounted
implements HttpData {
    private static final Pattern STRIP_PATTERN = Pattern.compile("(?:^\\s+|\\s+$|\\n)");
    private static final Pattern REPLACE_PATTERN = Pattern.compile("[\\r\\t]");
    private final String name;
    protected long definedSize;
    protected long size;
    private Charset charset = HttpConstants.DEFAULT_CHARSET;
    private boolean completed;
    private long maxSize = -1L;

    protected AbstractHttpData(String string, Charset charset, long l) {
        if (string == null) {
            throw new NullPointerException("name");
        }
        string = REPLACE_PATTERN.matcher(string).replaceAll(" ");
        if ((string = STRIP_PATTERN.matcher(string).replaceAll("")).isEmpty()) {
            throw new IllegalArgumentException("empty name");
        }
        this.name = string;
        if (charset != null) {
            this.setCharset(charset);
        }
        this.definedSize = l;
    }

    @Override
    public long getMaxSize() {
        return this.maxSize;
    }

    @Override
    public void setMaxSize(long l) {
        this.maxSize = l;
    }

    @Override
    public void checkSize(long l) throws IOException {
        if (this.maxSize >= 0L && l > this.maxSize) {
            throw new IOException("Size exceed allowed maximum capacity");
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isCompleted() {
        return this.completed;
    }

    protected void setCompleted() {
        this.completed = true;
    }

    @Override
    public Charset getCharset() {
        return this.charset;
    }

    @Override
    public void setCharset(Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }

    @Override
    public long length() {
        return this.size;
    }

    @Override
    public long definedLength() {
        return this.definedSize;
    }

    @Override
    public ByteBuf content() {
        try {
            return this.getByteBuf();
        } catch (IOException iOException) {
            throw new ChannelException(iOException);
        }
    }

    @Override
    protected void deallocate() {
        this.delete();
    }

    @Override
    public HttpData retain() {
        super.retain();
        return this;
    }

    @Override
    public HttpData retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public abstract HttpData touch();

    @Override
    public abstract HttpData touch(Object var1);

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
    public InterfaceHttpData touch(Object object) {
        return this.touch(object);
    }

    @Override
    public InterfaceHttpData touch() {
        return this.touch();
    }

    @Override
    public InterfaceHttpData retain(int n) {
        return this.retain(n);
    }

    @Override
    public InterfaceHttpData retain() {
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
}

