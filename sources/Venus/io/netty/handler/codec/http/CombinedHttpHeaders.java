/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.DefaultHeaders;
import io.netty.handler.codec.Headers;
import io.netty.handler.codec.ValueConverter;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.util.AsciiString;
import io.netty.util.HashingStrategy;
import io.netty.util.internal.StringUtil;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CombinedHttpHeaders
extends DefaultHttpHeaders {
    public CombinedHttpHeaders(boolean bl) {
        super(new CombinedHttpHeadersImpl(AsciiString.CASE_INSENSITIVE_HASHER, CombinedHttpHeaders.valueConverter(bl), CombinedHttpHeaders.nameValidator(bl)));
    }

    @Override
    public boolean containsValue(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        return super.containsValue(charSequence, StringUtil.trimOws(charSequence2), bl);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class CombinedHttpHeadersImpl
    extends DefaultHeaders<CharSequence, CharSequence, CombinedHttpHeadersImpl> {
        private static final int VALUE_LENGTH_ESTIMATE = 10;
        private CsvValueEscaper<Object> objectEscaper;
        private CsvValueEscaper<CharSequence> charSequenceEscaper;

        private CsvValueEscaper<Object> objectEscaper() {
            if (this.objectEscaper == null) {
                this.objectEscaper = new CsvValueEscaper<Object>(this){
                    final CombinedHttpHeadersImpl this$0;
                    {
                        this.this$0 = combinedHttpHeadersImpl;
                    }

                    @Override
                    public CharSequence escape(Object object) {
                        return StringUtil.escapeCsv((CharSequence)CombinedHttpHeadersImpl.access$000(this.this$0).convertObject(object), true);
                    }
                };
            }
            return this.objectEscaper;
        }

        private CsvValueEscaper<CharSequence> charSequenceEscaper() {
            if (this.charSequenceEscaper == null) {
                this.charSequenceEscaper = new CsvValueEscaper<CharSequence>(this){
                    final CombinedHttpHeadersImpl this$0;
                    {
                        this.this$0 = combinedHttpHeadersImpl;
                    }

                    @Override
                    public CharSequence escape(CharSequence charSequence) {
                        return StringUtil.escapeCsv(charSequence, true);
                    }

                    @Override
                    public CharSequence escape(Object object) {
                        return this.escape((CharSequence)object);
                    }
                };
            }
            return this.charSequenceEscaper;
        }

        public CombinedHttpHeadersImpl(HashingStrategy<CharSequence> hashingStrategy, ValueConverter<CharSequence> valueConverter, DefaultHeaders.NameValidator<CharSequence> nameValidator) {
            super(hashingStrategy, valueConverter, nameValidator);
        }

        @Override
        public Iterator<CharSequence> valueIterator(CharSequence charSequence) {
            Iterator<CharSequence> iterator2 = super.valueIterator(charSequence);
            if (!iterator2.hasNext()) {
                return iterator2;
            }
            Iterator<CharSequence> iterator3 = StringUtil.unescapeCsvFields(iterator2.next()).iterator();
            if (iterator2.hasNext()) {
                throw new IllegalStateException("CombinedHttpHeaders should only have one value");
            }
            return iterator3;
        }

        @Override
        public List<CharSequence> getAll(CharSequence charSequence) {
            List<CharSequence> list = super.getAll(charSequence);
            if (list.isEmpty()) {
                return list;
            }
            if (list.size() != 1) {
                throw new IllegalStateException("CombinedHttpHeaders should only have one value");
            }
            return StringUtil.unescapeCsvFields(list.get(0));
        }

        @Override
        public CombinedHttpHeadersImpl add(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                throw new IllegalArgumentException("can't add to itself.");
            }
            if (headers instanceof CombinedHttpHeadersImpl) {
                if (this.isEmpty()) {
                    this.addImpl(headers);
                } else {
                    for (Map.Entry<CharSequence, CharSequence> entry : headers) {
                        this.addEscapedValue(entry.getKey(), entry.getValue());
                    }
                }
            } else {
                for (Map.Entry<CharSequence, CharSequence> entry : headers) {
                    this.add(entry.getKey(), entry.getValue());
                }
            }
            return this;
        }

        @Override
        public CombinedHttpHeadersImpl set(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                return this;
            }
            this.clear();
            return this.add((Headers)headers);
        }

        @Override
        public CombinedHttpHeadersImpl setAll(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
            if (headers == this) {
                return this;
            }
            for (CharSequence charSequence : headers.names()) {
                this.remove(charSequence);
            }
            return this.add(headers);
        }

        @Override
        public CombinedHttpHeadersImpl add(CharSequence charSequence, CharSequence charSequence2) {
            return this.addEscapedValue(charSequence, this.charSequenceEscaper().escape(charSequence2));
        }

        @Override
        public CombinedHttpHeadersImpl add(CharSequence charSequence, CharSequence ... charSequenceArray) {
            return this.addEscapedValue(charSequence, CombinedHttpHeadersImpl.commaSeparate(this.charSequenceEscaper(), charSequenceArray));
        }

        @Override
        public CombinedHttpHeadersImpl add(CharSequence charSequence, Iterable<? extends CharSequence> iterable) {
            return this.addEscapedValue(charSequence, CombinedHttpHeadersImpl.commaSeparate(this.charSequenceEscaper(), iterable));
        }

        @Override
        public CombinedHttpHeadersImpl addObject(CharSequence charSequence, Object object) {
            return this.addEscapedValue(charSequence, CombinedHttpHeadersImpl.commaSeparate(this.objectEscaper(), object));
        }

        @Override
        public CombinedHttpHeadersImpl addObject(CharSequence charSequence, Iterable<?> iterable) {
            return this.addEscapedValue(charSequence, CombinedHttpHeadersImpl.commaSeparate(this.objectEscaper(), iterable));
        }

        @Override
        public CombinedHttpHeadersImpl addObject(CharSequence charSequence, Object ... objectArray) {
            return this.addEscapedValue(charSequence, CombinedHttpHeadersImpl.commaSeparate(this.objectEscaper(), objectArray));
        }

        @Override
        public CombinedHttpHeadersImpl set(CharSequence charSequence, CharSequence ... charSequenceArray) {
            super.set(charSequence, CombinedHttpHeadersImpl.commaSeparate(this.charSequenceEscaper(), charSequenceArray));
            return this;
        }

        @Override
        public CombinedHttpHeadersImpl set(CharSequence charSequence, Iterable<? extends CharSequence> iterable) {
            super.set(charSequence, CombinedHttpHeadersImpl.commaSeparate(this.charSequenceEscaper(), iterable));
            return this;
        }

        @Override
        public CombinedHttpHeadersImpl setObject(CharSequence charSequence, Object object) {
            super.set(charSequence, CombinedHttpHeadersImpl.commaSeparate(this.objectEscaper(), object));
            return this;
        }

        @Override
        public CombinedHttpHeadersImpl setObject(CharSequence charSequence, Object ... objectArray) {
            super.set(charSequence, CombinedHttpHeadersImpl.commaSeparate(this.objectEscaper(), objectArray));
            return this;
        }

        @Override
        public CombinedHttpHeadersImpl setObject(CharSequence charSequence, Iterable<?> iterable) {
            super.set(charSequence, CombinedHttpHeadersImpl.commaSeparate(this.objectEscaper(), iterable));
            return this;
        }

        private CombinedHttpHeadersImpl addEscapedValue(CharSequence charSequence, CharSequence charSequence2) {
            CharSequence charSequence3 = (CharSequence)super.get(charSequence);
            if (charSequence3 == null) {
                super.add(charSequence, charSequence2);
            } else {
                super.set(charSequence, CombinedHttpHeadersImpl.commaSeparateEscapedValues(charSequence3, charSequence2));
            }
            return this;
        }

        private static <T> CharSequence commaSeparate(CsvValueEscaper<T> csvValueEscaper, T ... TArray) {
            StringBuilder stringBuilder = new StringBuilder(TArray.length * 10);
            if (TArray.length > 0) {
                int n = TArray.length - 1;
                for (int i = 0; i < n; ++i) {
                    stringBuilder.append(csvValueEscaper.escape(TArray[i])).append(',');
                }
                stringBuilder.append(csvValueEscaper.escape(TArray[n]));
            }
            return stringBuilder;
        }

        private static <T> CharSequence commaSeparate(CsvValueEscaper<T> csvValueEscaper, Iterable<? extends T> iterable) {
            StringBuilder stringBuilder = iterable instanceof Collection ? new StringBuilder(((Collection)iterable).size() * 10) : new StringBuilder();
            Iterator<T> iterator2 = iterable.iterator();
            if (iterator2.hasNext()) {
                T t = iterator2.next();
                while (iterator2.hasNext()) {
                    stringBuilder.append(csvValueEscaper.escape(t)).append(',');
                    t = iterator2.next();
                }
                stringBuilder.append(csvValueEscaper.escape(t));
            }
            return stringBuilder;
        }

        private static CharSequence commaSeparateEscapedValues(CharSequence charSequence, CharSequence charSequence2) {
            return new StringBuilder(charSequence.length() + 1 + charSequence2.length()).append(charSequence).append(',').append(charSequence2);
        }

        @Override
        public Headers setAll(Headers headers) {
            return this.setAll(headers);
        }

        @Override
        public Headers set(Headers headers) {
            return this.set(headers);
        }

        @Override
        public Headers setObject(Object object, Object[] objectArray) {
            return this.setObject((CharSequence)object, objectArray);
        }

        @Override
        public Headers setObject(Object object, Iterable iterable) {
            return this.setObject((CharSequence)object, iterable);
        }

        @Override
        public Headers setObject(Object object, Object object2) {
            return this.setObject((CharSequence)object, object2);
        }

        @Override
        public Headers set(Object object, Object[] objectArray) {
            return this.set((CharSequence)object, (CharSequence[])objectArray);
        }

        @Override
        public Headers set(Object object, Iterable iterable) {
            return this.set((CharSequence)object, (Iterable<? extends CharSequence>)iterable);
        }

        @Override
        public Headers add(Headers headers) {
            return this.add(headers);
        }

        @Override
        public Headers addObject(Object object, Object[] objectArray) {
            return this.addObject((CharSequence)object, objectArray);
        }

        @Override
        public Headers addObject(Object object, Iterable iterable) {
            return this.addObject((CharSequence)object, iterable);
        }

        @Override
        public Headers addObject(Object object, Object object2) {
            return this.addObject((CharSequence)object, object2);
        }

        @Override
        public Headers add(Object object, Object[] objectArray) {
            return this.add((CharSequence)object, (CharSequence[])objectArray);
        }

        @Override
        public Headers add(Object object, Iterable iterable) {
            return this.add((CharSequence)object, (Iterable<? extends CharSequence>)iterable);
        }

        @Override
        public Headers add(Object object, Object object2) {
            return this.add((CharSequence)object, (CharSequence)object2);
        }

        @Override
        public Iterator valueIterator(Object object) {
            return this.valueIterator((CharSequence)object);
        }

        @Override
        public List getAll(Object object) {
            return this.getAll((CharSequence)object);
        }

        static ValueConverter access$000(CombinedHttpHeadersImpl combinedHttpHeadersImpl) {
            return combinedHttpHeadersImpl.valueConverter();
        }

        private static interface CsvValueEscaper<T> {
            public CharSequence escape(T var1);
        }
    }
}

