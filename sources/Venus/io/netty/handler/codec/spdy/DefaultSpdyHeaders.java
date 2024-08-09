/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.spdy;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.HeadersUtils;
import io.netty.handler.codec.spdy.SpdyCodecUtil;
import io.netty.handler.codec.spdy.SpdyHeaders;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DefaultSpdyHeaders
extends DefaultHeaders<CharSequence, CharSequence, SpdyHeaders>
implements SpdyHeaders {
    private static final DefaultHeaders.NameValidator<CharSequence> SpdyNameValidator = new DefaultHeaders.NameValidator<CharSequence>(){

        @Override
        public void validateName(CharSequence charSequence) {
            SpdyCodecUtil.validateHeaderName(charSequence);
        }

        @Override
        public void validateName(Object object) {
            this.validateName((CharSequence)object);
        }
    };

    public DefaultSpdyHeaders() {
        this(true);
    }

    public DefaultSpdyHeaders(boolean bl) {
        super(AsciiString.CASE_INSENSITIVE_HASHER, bl ? HeaderValueConverterAndValidator.INSTANCE : CharSequenceValueConverter.INSTANCE, bl ? SpdyNameValidator : DefaultHeaders.NameValidator.NOT_NULL);
    }

    @Override
    public String getAsString(CharSequence charSequence) {
        return HeadersUtils.getAsString(this, charSequence);
    }

    @Override
    public List<String> getAllAsString(CharSequence charSequence) {
        return HeadersUtils.getAllAsString(this, charSequence);
    }

    @Override
    public Iterator<Map.Entry<String, String>> iteratorAsString() {
        return HeadersUtils.iteratorAsString(this);
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
    public boolean contains(Object object, Object object2) {
        return this.contains((CharSequence)object, (CharSequence)object2);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class HeaderValueConverterAndValidator
    extends CharSequenceValueConverter {
        public static final HeaderValueConverterAndValidator INSTANCE = new HeaderValueConverterAndValidator();

        private HeaderValueConverterAndValidator() {
        }

        @Override
        public CharSequence convertObject(Object object) {
            CharSequence charSequence = super.convertObject(object);
            SpdyCodecUtil.validateHeaderValue(charSequence);
            return charSequence;
        }

        @Override
        public Object convertObject(Object object) {
            return this.convertObject(object);
        }
    }
}

