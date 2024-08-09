/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.DateFormatter;
import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.DefaultHeadersImpl;
import io.netty.handler.codec.HeadersUtils;
import io.netty.handler.codec.ValueConverter;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.AsciiString;
import io.netty.util.ByteProcessor;
import io.netty.util.internal.PlatformDependent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DefaultHttpHeaders
extends HttpHeaders {
    private static final int HIGHEST_INVALID_VALUE_CHAR_MASK = -16;
    private static final ByteProcessor HEADER_NAME_VALIDATOR = new ByteProcessor(){

        @Override
        public boolean process(byte by) throws Exception {
            DefaultHttpHeaders.access$000(by);
            return false;
        }
    };
    static final DefaultHeaders.NameValidator<CharSequence> HttpNameValidator = new DefaultHeaders.NameValidator<CharSequence>(){

        @Override
        public void validateName(CharSequence charSequence) {
            if (charSequence == null || charSequence.length() == 0) {
                throw new IllegalArgumentException("empty headers are not allowed [" + charSequence + "]");
            }
            if (charSequence instanceof AsciiString) {
                try {
                    ((AsciiString)charSequence).forEachByte(DefaultHttpHeaders.access$100());
                } catch (Exception exception) {
                    PlatformDependent.throwException(exception);
                }
            } else {
                for (int i = 0; i < charSequence.length(); ++i) {
                    DefaultHttpHeaders.access$200(charSequence.charAt(i));
                }
            }
        }

        @Override
        public void validateName(Object object) {
            this.validateName((CharSequence)object);
        }
    };
    private final DefaultHeaders<CharSequence, CharSequence, ?> headers;

    public DefaultHttpHeaders() {
        this(true);
    }

    public DefaultHttpHeaders(boolean bl) {
        this(bl, DefaultHttpHeaders.nameValidator(bl));
    }

    protected DefaultHttpHeaders(boolean bl, DefaultHeaders.NameValidator<CharSequence> nameValidator) {
        this(new DefaultHeadersImpl<CharSequence, CharSequence>(AsciiString.CASE_INSENSITIVE_HASHER, DefaultHttpHeaders.valueConverter(bl), nameValidator));
    }

    protected DefaultHttpHeaders(DefaultHeaders<CharSequence, CharSequence, ?> defaultHeaders) {
        this.headers = defaultHeaders;
    }

    @Override
    public HttpHeaders add(HttpHeaders httpHeaders) {
        if (httpHeaders instanceof DefaultHttpHeaders) {
            this.headers.add(((DefaultHttpHeaders)httpHeaders).headers);
            return this;
        }
        return super.add(httpHeaders);
    }

    @Override
    public HttpHeaders set(HttpHeaders httpHeaders) {
        if (httpHeaders instanceof DefaultHttpHeaders) {
            this.headers.set(((DefaultHttpHeaders)httpHeaders).headers);
            return this;
        }
        return super.set(httpHeaders);
    }

    @Override
    public HttpHeaders add(String string, Object object) {
        this.headers.addObject((CharSequence)string, object);
        return this;
    }

    @Override
    public HttpHeaders add(CharSequence charSequence, Object object) {
        this.headers.addObject(charSequence, object);
        return this;
    }

    @Override
    public HttpHeaders add(String string, Iterable<?> iterable) {
        this.headers.addObject((CharSequence)string, iterable);
        return this;
    }

    @Override
    public HttpHeaders add(CharSequence charSequence, Iterable<?> iterable) {
        this.headers.addObject(charSequence, iterable);
        return this;
    }

    @Override
    public HttpHeaders addInt(CharSequence charSequence, int n) {
        this.headers.addInt(charSequence, n);
        return this;
    }

    @Override
    public HttpHeaders addShort(CharSequence charSequence, short s) {
        this.headers.addShort(charSequence, s);
        return this;
    }

    @Override
    public HttpHeaders remove(String string) {
        this.headers.remove(string);
        return this;
    }

    @Override
    public HttpHeaders remove(CharSequence charSequence) {
        this.headers.remove(charSequence);
        return this;
    }

    @Override
    public HttpHeaders set(String string, Object object) {
        this.headers.setObject((CharSequence)string, object);
        return this;
    }

    @Override
    public HttpHeaders set(CharSequence charSequence, Object object) {
        this.headers.setObject(charSequence, object);
        return this;
    }

    @Override
    public HttpHeaders set(String string, Iterable<?> iterable) {
        this.headers.setObject((CharSequence)string, iterable);
        return this;
    }

    @Override
    public HttpHeaders set(CharSequence charSequence, Iterable<?> iterable) {
        this.headers.setObject(charSequence, iterable);
        return this;
    }

    @Override
    public HttpHeaders setInt(CharSequence charSequence, int n) {
        this.headers.setInt(charSequence, n);
        return this;
    }

    @Override
    public HttpHeaders setShort(CharSequence charSequence, short s) {
        this.headers.setShort(charSequence, s);
        return this;
    }

    @Override
    public HttpHeaders clear() {
        this.headers.clear();
        return this;
    }

    @Override
    public String get(String string) {
        return this.get((CharSequence)string);
    }

    @Override
    public String get(CharSequence charSequence) {
        return HeadersUtils.getAsString(this.headers, charSequence);
    }

    @Override
    public Integer getInt(CharSequence charSequence) {
        return this.headers.getInt(charSequence);
    }

    @Override
    public int getInt(CharSequence charSequence, int n) {
        return this.headers.getInt(charSequence, n);
    }

    @Override
    public Short getShort(CharSequence charSequence) {
        return this.headers.getShort(charSequence);
    }

    @Override
    public short getShort(CharSequence charSequence, short s) {
        return this.headers.getShort(charSequence, s);
    }

    @Override
    public Long getTimeMillis(CharSequence charSequence) {
        return this.headers.getTimeMillis(charSequence);
    }

    @Override
    public long getTimeMillis(CharSequence charSequence, long l) {
        return this.headers.getTimeMillis(charSequence, l);
    }

    @Override
    public List<String> getAll(String string) {
        return this.getAll((CharSequence)string);
    }

    @Override
    public List<String> getAll(CharSequence charSequence) {
        return HeadersUtils.getAllAsString(this.headers, charSequence);
    }

    @Override
    public List<Map.Entry<String, String>> entries() {
        if (this.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>(this.headers.size());
        for (Map.Entry<String, String> entry : this) {
            arrayList.add(entry);
        }
        return arrayList;
    }

    @Override
    @Deprecated
    public Iterator<Map.Entry<String, String>> iterator() {
        return HeadersUtils.iteratorAsString(this.headers);
    }

    @Override
    public Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence() {
        return this.headers.iterator();
    }

    @Override
    public Iterator<String> valueStringIterator(CharSequence charSequence) {
        Iterator<CharSequence> iterator2 = this.valueCharSequenceIterator(charSequence);
        return new Iterator<String>(this, iterator2){
            final Iterator val$itr;
            final DefaultHttpHeaders this$0;
            {
                this.this$0 = defaultHttpHeaders;
                this.val$itr = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.val$itr.hasNext();
            }

            @Override
            public String next() {
                return ((CharSequence)this.val$itr.next()).toString();
            }

            @Override
            public void remove() {
                this.val$itr.remove();
            }

            @Override
            public Object next() {
                return this.next();
            }
        };
    }

    public Iterator<CharSequence> valueCharSequenceIterator(CharSequence charSequence) {
        return this.headers.valueIterator(charSequence);
    }

    @Override
    public boolean contains(String string) {
        return this.contains((CharSequence)string);
    }

    @Override
    public boolean contains(CharSequence charSequence) {
        return this.headers.contains(charSequence);
    }

    @Override
    public boolean isEmpty() {
        return this.headers.isEmpty();
    }

    @Override
    public int size() {
        return this.headers.size();
    }

    @Override
    public boolean contains(String string, String string2, boolean bl) {
        return this.contains((CharSequence)string, (CharSequence)string2, bl);
    }

    @Override
    public boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        return this.headers.contains(charSequence, charSequence2, bl ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER);
    }

    @Override
    public Set<String> names() {
        return HeadersUtils.namesAsString(this.headers);
    }

    public boolean equals(Object object) {
        return object instanceof DefaultHttpHeaders && this.headers.equals(((DefaultHttpHeaders)object).headers, AsciiString.CASE_SENSITIVE_HASHER);
    }

    public int hashCode() {
        return this.headers.hashCode(AsciiString.CASE_SENSITIVE_HASHER);
    }

    @Override
    public HttpHeaders copy() {
        return new DefaultHttpHeaders(this.headers.copy());
    }

    private static void validateHeaderNameElement(byte by) {
        switch (by) {
            case 0: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 32: 
            case 44: 
            case 58: 
            case 59: 
            case 61: {
                throw new IllegalArgumentException("a header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + by);
            }
        }
        if (by < 0) {
            throw new IllegalArgumentException("a header name cannot contain non-ASCII character: " + by);
        }
    }

    private static void validateHeaderNameElement(char c) {
        switch (c) {
            case '\u0000': 
            case '\t': 
            case '\n': 
            case '\u000b': 
            case '\f': 
            case '\r': 
            case ' ': 
            case ',': 
            case ':': 
            case ';': 
            case '=': {
                throw new IllegalArgumentException("a header name cannot contain the following prohibited characters: =,;: \\t\\r\\n\\v\\f: " + c);
            }
        }
        if (c > '\u007f') {
            throw new IllegalArgumentException("a header name cannot contain non-ASCII character: " + c);
        }
    }

    static ValueConverter<CharSequence> valueConverter(boolean bl) {
        return bl ? HeaderValueConverterAndValidator.INSTANCE : HeaderValueConverter.INSTANCE;
    }

    static DefaultHeaders.NameValidator<CharSequence> nameValidator(boolean bl) {
        return bl ? HttpNameValidator : DefaultHeaders.NameValidator.NOT_NULL;
    }

    static void access$000(byte by) {
        DefaultHttpHeaders.validateHeaderNameElement(by);
    }

    static ByteProcessor access$100() {
        return HEADER_NAME_VALIDATOR;
    }

    static void access$200(char c) {
        DefaultHttpHeaders.validateHeaderNameElement(c);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class HeaderValueConverterAndValidator
    extends HeaderValueConverter {
        static final HeaderValueConverterAndValidator INSTANCE = new HeaderValueConverterAndValidator();

        private HeaderValueConverterAndValidator() {
            super(null);
        }

        @Override
        public CharSequence convertObject(Object object) {
            CharSequence charSequence = super.convertObject(object);
            int n = 0;
            for (int i = 0; i < charSequence.length(); ++i) {
                n = HeaderValueConverterAndValidator.validateValueChar(charSequence, n, charSequence.charAt(i));
            }
            if (n != 0) {
                throw new IllegalArgumentException("a header value must not end with '\\r' or '\\n':" + charSequence);
            }
            return charSequence;
        }

        private static int validateValueChar(CharSequence charSequence, int n, char c) {
            if ((c & 0xFFFFFFF0) == 0) {
                switch (c) {
                    case '\u0000': {
                        throw new IllegalArgumentException("a header value contains a prohibited character '\u0000': " + charSequence);
                    }
                    case '\u000b': {
                        throw new IllegalArgumentException("a header value contains a prohibited character '\\v': " + charSequence);
                    }
                    case '\f': {
                        throw new IllegalArgumentException("a header value contains a prohibited character '\\f': " + charSequence);
                    }
                }
            }
            switch (n) {
                case 0: {
                    switch (c) {
                        case '\r': {
                            return 0;
                        }
                        case '\n': {
                            return 1;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (c) {
                        case '\n': {
                            return 1;
                        }
                    }
                    throw new IllegalArgumentException("only '\\n' is allowed after '\\r': " + charSequence);
                }
                case 2: {
                    switch (c) {
                        case '\t': 
                        case ' ': {
                            return 1;
                        }
                    }
                    throw new IllegalArgumentException("only ' ' and '\\t' are allowed after '\\n': " + charSequence);
                }
            }
            return n;
        }

        @Override
        public Object convertObject(Object object) {
            return this.convertObject(object);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static class HeaderValueConverter
    extends CharSequenceValueConverter {
        static final HeaderValueConverter INSTANCE = new HeaderValueConverter();

        private HeaderValueConverter() {
        }

        @Override
        public CharSequence convertObject(Object object) {
            if (object instanceof CharSequence) {
                return (CharSequence)object;
            }
            if (object instanceof Date) {
                return DateFormatter.format((Date)object);
            }
            if (object instanceof Calendar) {
                return DateFormatter.format(((Calendar)object).getTime());
            }
            return object.toString();
        }

        @Override
        public Object convertObject(Object object) {
            return this.convertObject(object);
        }

        HeaderValueConverter(1 var1_1) {
            this();
        }
    }
}

