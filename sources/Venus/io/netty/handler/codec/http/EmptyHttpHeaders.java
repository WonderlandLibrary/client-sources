/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.http.HttpHeaders;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmptyHttpHeaders
extends HttpHeaders {
    static final Iterator<Map.Entry<CharSequence, CharSequence>> EMPTY_CHARS_ITERATOR = Collections.emptyList().iterator();
    public static final EmptyHttpHeaders INSTANCE = EmptyHttpHeaders.instance();

    @Deprecated
    static EmptyHttpHeaders instance() {
        return InstanceInitializer.access$000();
    }

    protected EmptyHttpHeaders() {
    }

    @Override
    public String get(String string) {
        return null;
    }

    @Override
    public Integer getInt(CharSequence charSequence) {
        return null;
    }

    @Override
    public int getInt(CharSequence charSequence, int n) {
        return n;
    }

    @Override
    public Short getShort(CharSequence charSequence) {
        return null;
    }

    @Override
    public short getShort(CharSequence charSequence, short s) {
        return s;
    }

    @Override
    public Long getTimeMillis(CharSequence charSequence) {
        return null;
    }

    @Override
    public long getTimeMillis(CharSequence charSequence, long l) {
        return l;
    }

    @Override
    public List<String> getAll(String string) {
        return Collections.emptyList();
    }

    @Override
    public List<Map.Entry<String, String>> entries() {
        return Collections.emptyList();
    }

    @Override
    public boolean contains(String string) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public Set<String> names() {
        return Collections.emptySet();
    }

    @Override
    public HttpHeaders add(String string, Object object) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public HttpHeaders add(String string, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public HttpHeaders addInt(CharSequence charSequence, int n) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public HttpHeaders addShort(CharSequence charSequence, short s) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public HttpHeaders set(String string, Object object) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public HttpHeaders set(String string, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public HttpHeaders setInt(CharSequence charSequence, int n) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public HttpHeaders setShort(CharSequence charSequence, short s) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public HttpHeaders remove(String string) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public HttpHeaders clear() {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return this.entries().iterator();
    }

    @Override
    public Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence() {
        return EMPTY_CHARS_ITERATOR;
    }

    @Deprecated
    private static final class InstanceInitializer {
        @Deprecated
        private static final EmptyHttpHeaders EMPTY_HEADERS = new EmptyHttpHeaders();

        private InstanceInitializer() {
        }

        static EmptyHttpHeaders access$000() {
            return EMPTY_HEADERS;
        }
    }
}

