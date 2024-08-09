/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.EmptyHeaders;
import io.netty.handler.codec.http2.Http2Headers;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class EmptyHttp2Headers
extends EmptyHeaders<CharSequence, CharSequence, Http2Headers>
implements Http2Headers {
    public static final EmptyHttp2Headers INSTANCE = new EmptyHttp2Headers();

    private EmptyHttp2Headers() {
    }

    @Override
    public EmptyHttp2Headers method(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EmptyHttp2Headers scheme(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EmptyHttp2Headers authority(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EmptyHttp2Headers path(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override
    public EmptyHttp2Headers status(CharSequence charSequence) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharSequence method() {
        return (CharSequence)this.get(Http2Headers.PseudoHeaderName.METHOD.value());
    }

    @Override
    public CharSequence scheme() {
        return (CharSequence)this.get(Http2Headers.PseudoHeaderName.SCHEME.value());
    }

    @Override
    public CharSequence authority() {
        return (CharSequence)this.get(Http2Headers.PseudoHeaderName.AUTHORITY.value());
    }

    @Override
    public CharSequence path() {
        return (CharSequence)this.get(Http2Headers.PseudoHeaderName.PATH.value());
    }

    @Override
    public CharSequence status() {
        return (CharSequence)this.get(Http2Headers.PseudoHeaderName.STATUS.value());
    }

    @Override
    public boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        return true;
    }

    @Override
    public Http2Headers status(CharSequence charSequence) {
        return this.status(charSequence);
    }

    @Override
    public Http2Headers path(CharSequence charSequence) {
        return this.path(charSequence);
    }

    @Override
    public Http2Headers authority(CharSequence charSequence) {
        return this.authority(charSequence);
    }

    @Override
    public Http2Headers scheme(CharSequence charSequence) {
        return this.scheme(charSequence);
    }

    @Override
    public Http2Headers method(CharSequence charSequence) {
        return this.method(charSequence);
    }

    @Override
    public Iterator valueIterator(CharSequence charSequence) {
        return super.valueIterator(charSequence);
    }
}

