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

public class DefaultStompHeaders
extends DefaultHeaders<CharSequence, CharSequence, StompHeaders>
implements StompHeaders {
    public DefaultStompHeaders() {
        super(AsciiString.CASE_SENSITIVE_HASHER, CharSequenceValueConverter.INSTANCE);
    }

    @Override
    public String getAsString(CharSequence name) {
        return HeadersUtils.getAsString(this, name);
    }

    @Override
    public List<String> getAllAsString(CharSequence name) {
        return HeadersUtils.getAllAsString(this, name);
    }

    @Override
    public Iterator<Map.Entry<String, String>> iteratorAsString() {
        return HeadersUtils.iteratorAsString(this);
    }

    @Override
    public boolean contains(CharSequence name, CharSequence value) {
        return this.contains(name, value, false);
    }

    @Override
    public boolean contains(CharSequence name, CharSequence value, boolean ignoreCase) {
        return this.contains(name, value, ignoreCase ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER);
    }
}

