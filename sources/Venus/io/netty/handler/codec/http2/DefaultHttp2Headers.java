/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.Headers;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.PlatformDependent;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultHttp2Headers
extends DefaultHeaders<CharSequence, CharSequence, Http2Headers>
implements Http2Headers {
    private static final ByteProcessor HTTP2_NAME_VALIDATOR_PROCESSOR = new ByteProcessor(){

        @Override
        public boolean process(byte by) {
            return !AsciiString.isUpperCase(by);
        }
    };
    static final DefaultHeaders.NameValidator<CharSequence> HTTP2_NAME_VALIDATOR = new DefaultHeaders.NameValidator<CharSequence>(){

        @Override
        public void validateName(CharSequence charSequence) {
            if (charSequence == null || charSequence.length() == 0) {
                PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "empty headers are not allowed [%s]", charSequence));
            }
            if (charSequence instanceof AsciiString) {
                int n;
                try {
                    n = ((AsciiString)charSequence).forEachByte(DefaultHttp2Headers.access$000());
                } catch (Http2Exception http2Exception) {
                    PlatformDependent.throwException(http2Exception);
                    return;
                } catch (Throwable throwable) {
                    PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, throwable, "unexpected error. invalid header name [%s]", charSequence));
                    return;
                }
                if (n != -1) {
                    PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "invalid header name [%s]", charSequence));
                }
            } else {
                for (int i = 0; i < charSequence.length(); ++i) {
                    if (!AsciiString.isUpperCase(charSequence.charAt(i))) continue;
                    PlatformDependent.throwException(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "invalid header name [%s]", charSequence));
                }
            }
        }

        @Override
        public void validateName(Object object) {
            this.validateName((CharSequence)object);
        }
    };
    private DefaultHeaders.HeaderEntry<CharSequence, CharSequence> firstNonPseudo;

    public DefaultHttp2Headers() {
        this(true);
    }

    public DefaultHttp2Headers(boolean bl) {
        super(AsciiString.CASE_SENSITIVE_HASHER, CharSequenceValueConverter.INSTANCE, bl ? HTTP2_NAME_VALIDATOR : DefaultHeaders.NameValidator.NOT_NULL);
        this.firstNonPseudo = this.head;
    }

    public DefaultHttp2Headers(boolean bl, int n) {
        super(AsciiString.CASE_SENSITIVE_HASHER, CharSequenceValueConverter.INSTANCE, bl ? HTTP2_NAME_VALIDATOR : DefaultHeaders.NameValidator.NOT_NULL, n);
        this.firstNonPseudo = this.head;
    }

    @Override
    public Http2Headers clear() {
        this.firstNonPseudo = this.head;
        return (Http2Headers)super.clear();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Http2Headers && this.equals((Http2Headers)object, AsciiString.CASE_SENSITIVE_HASHER);
    }

    @Override
    public int hashCode() {
        return this.hashCode(AsciiString.CASE_SENSITIVE_HASHER);
    }

    @Override
    public Http2Headers method(CharSequence charSequence) {
        this.set(Http2Headers.PseudoHeaderName.METHOD.value(), charSequence);
        return this;
    }

    @Override
    public Http2Headers scheme(CharSequence charSequence) {
        this.set(Http2Headers.PseudoHeaderName.SCHEME.value(), charSequence);
        return this;
    }

    @Override
    public Http2Headers authority(CharSequence charSequence) {
        this.set(Http2Headers.PseudoHeaderName.AUTHORITY.value(), charSequence);
        return this;
    }

    @Override
    public Http2Headers path(CharSequence charSequence) {
        this.set(Http2Headers.PseudoHeaderName.PATH.value(), charSequence);
        return this;
    }

    @Override
    public Http2Headers status(CharSequence charSequence) {
        this.set(Http2Headers.PseudoHeaderName.STATUS.value(), charSequence);
        return this;
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
    public boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        return this.contains(charSequence, charSequence2, true);
    }

    @Override
    public boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        return this.contains(charSequence, charSequence2, bl ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER);
    }

    @Override
    protected final DefaultHeaders.HeaderEntry<CharSequence, CharSequence> newHeaderEntry(int n, CharSequence charSequence, CharSequence charSequence2, DefaultHeaders.HeaderEntry<CharSequence, CharSequence> headerEntry) {
        return new Http2HeaderEntry(this, n, charSequence, charSequence2, headerEntry);
    }

    @Override
    protected DefaultHeaders.HeaderEntry newHeaderEntry(int n, Object object, Object object2, DefaultHeaders.HeaderEntry headerEntry) {
        return this.newHeaderEntry(n, (CharSequence)object, (CharSequence)object2, (DefaultHeaders.HeaderEntry<CharSequence, CharSequence>)headerEntry);
    }

    @Override
    public Headers clear() {
        return this.clear();
    }

    @Override
    public boolean contains(Object object, Object object2) {
        return this.contains((CharSequence)object, (CharSequence)object2);
    }

    @Override
    public Iterator valueIterator(CharSequence charSequence) {
        return super.valueIterator(charSequence);
    }

    static ByteProcessor access$000() {
        return HTTP2_NAME_VALIDATOR_PROCESSOR;
    }

    static DefaultHeaders.HeaderEntry access$100(DefaultHttp2Headers defaultHttp2Headers) {
        return defaultHttp2Headers.firstNonPseudo;
    }

    static DefaultHeaders.HeaderEntry access$200(DefaultHttp2Headers defaultHttp2Headers) {
        return defaultHttp2Headers.head;
    }

    static DefaultHeaders.HeaderEntry access$300(DefaultHttp2Headers defaultHttp2Headers) {
        return defaultHttp2Headers.head;
    }

    static DefaultHeaders.HeaderEntry access$400(DefaultHttp2Headers defaultHttp2Headers) {
        return defaultHttp2Headers.head;
    }

    static DefaultHeaders.HeaderEntry access$102(DefaultHttp2Headers defaultHttp2Headers, DefaultHeaders.HeaderEntry headerEntry) {
        defaultHttp2Headers.firstNonPseudo = headerEntry;
        return defaultHttp2Headers.firstNonPseudo;
    }

    private final class Http2HeaderEntry
    extends DefaultHeaders.HeaderEntry<CharSequence, CharSequence> {
        final DefaultHttp2Headers this$0;

        protected Http2HeaderEntry(DefaultHttp2Headers defaultHttp2Headers, int n, CharSequence charSequence, CharSequence charSequence2, DefaultHeaders.HeaderEntry<CharSequence, CharSequence> headerEntry) {
            this.this$0 = defaultHttp2Headers;
            super(n, charSequence);
            this.value = charSequence2;
            this.next = headerEntry;
            if (Http2Headers.PseudoHeaderName.hasPseudoHeaderFormat(charSequence)) {
                this.after = DefaultHttp2Headers.access$100(defaultHttp2Headers);
                this.before = DefaultHttp2Headers.access$100(defaultHttp2Headers).before();
            } else {
                this.after = DefaultHttp2Headers.access$200(defaultHttp2Headers);
                this.before = DefaultHttp2Headers.access$300(defaultHttp2Headers).before();
                if (DefaultHttp2Headers.access$100(defaultHttp2Headers) == DefaultHttp2Headers.access$400(defaultHttp2Headers)) {
                    DefaultHttp2Headers.access$102(defaultHttp2Headers, this);
                }
            }
            this.pointNeighborsToThis();
        }

        @Override
        protected void remove() {
            if (this == DefaultHttp2Headers.access$100(this.this$0)) {
                DefaultHttp2Headers.access$102(this.this$0, DefaultHttp2Headers.access$100(this.this$0).after());
            }
            super.remove();
        }
    }
}

