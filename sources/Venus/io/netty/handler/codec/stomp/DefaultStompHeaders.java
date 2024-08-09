/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.HeadersUtils;
import io.netty.handler.codec.stomp.StompHeaders;
import io.netty.util.AsciiString;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultStompHeaders
extends DefaultHeaders<CharSequence, CharSequence, StompHeaders>
implements StompHeaders {
    public DefaultStompHeaders() {
        super(AsciiString.CASE_SENSITIVE_HASHER, CharSequenceValueConverter.INSTANCE);
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

    public DefaultStompHeaders copy() {
        DefaultStompHeaders defaultStompHeaders = new DefaultStompHeaders();
        defaultStompHeaders.addImpl(this);
        return defaultStompHeaders;
    }

    @Override
    public DefaultHeaders copy() {
        return this.copy();
    }

    @Override
    public boolean contains(Object object, Object object2) {
        return this.contains((CharSequence)object, (CharSequence)object2);
    }
}

