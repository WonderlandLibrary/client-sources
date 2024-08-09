/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.AsciiString;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public final class ReadOnlyHttpHeaders
extends HttpHeaders {
    private final CharSequence[] nameValuePairs;

    public ReadOnlyHttpHeaders(boolean bl, CharSequence ... charSequenceArray) {
        if ((charSequenceArray.length & 1) != 0) {
            throw ReadOnlyHttpHeaders.newInvalidArraySizeException();
        }
        if (bl) {
            ReadOnlyHttpHeaders.validateHeaders(charSequenceArray);
        }
        this.nameValuePairs = charSequenceArray;
    }

    private static IllegalArgumentException newInvalidArraySizeException() {
        return new IllegalArgumentException("nameValuePairs must be arrays of [name, value] pairs");
    }

    private static void validateHeaders(CharSequence ... charSequenceArray) {
        for (int i = 0; i < charSequenceArray.length; i += 2) {
            DefaultHttpHeaders.HttpNameValidator.validateName(charSequenceArray[i]);
        }
    }

    private CharSequence get0(CharSequence charSequence) {
        int n = AsciiString.hashCode(charSequence);
        for (int i = 0; i < this.nameValuePairs.length; i += 2) {
            CharSequence charSequence2 = this.nameValuePairs[i];
            if (AsciiString.hashCode(charSequence2) != n || !AsciiString.contentEqualsIgnoreCase(charSequence2, charSequence)) continue;
            return this.nameValuePairs[i + 1];
        }
        return null;
    }

    @Override
    public String get(String string) {
        CharSequence charSequence = this.get0(string);
        return charSequence == null ? null : charSequence.toString();
    }

    @Override
    public Integer getInt(CharSequence charSequence) {
        CharSequence charSequence2 = this.get0(charSequence);
        return charSequence2 == null ? null : Integer.valueOf(CharSequenceValueConverter.INSTANCE.convertToInt(charSequence2));
    }

    @Override
    public int getInt(CharSequence charSequence, int n) {
        CharSequence charSequence2 = this.get0(charSequence);
        return charSequence2 == null ? n : CharSequenceValueConverter.INSTANCE.convertToInt(charSequence2);
    }

    @Override
    public Short getShort(CharSequence charSequence) {
        CharSequence charSequence2 = this.get0(charSequence);
        return charSequence2 == null ? null : Short.valueOf(CharSequenceValueConverter.INSTANCE.convertToShort(charSequence2));
    }

    @Override
    public short getShort(CharSequence charSequence, short s) {
        CharSequence charSequence2 = this.get0(charSequence);
        return charSequence2 == null ? s : CharSequenceValueConverter.INSTANCE.convertToShort(charSequence2);
    }

    @Override
    public Long getTimeMillis(CharSequence charSequence) {
        CharSequence charSequence2 = this.get0(charSequence);
        return charSequence2 == null ? null : Long.valueOf(CharSequenceValueConverter.INSTANCE.convertToTimeMillis(charSequence2));
    }

    @Override
    public long getTimeMillis(CharSequence charSequence, long l) {
        CharSequence charSequence2 = this.get0(charSequence);
        return charSequence2 == null ? l : CharSequenceValueConverter.INSTANCE.convertToTimeMillis(charSequence2);
    }

    @Override
    public List<String> getAll(String string) {
        if (this.isEmpty()) {
            return Collections.emptyList();
        }
        int n = AsciiString.hashCode(string);
        ArrayList<String> arrayList = new ArrayList<String>(4);
        for (int i = 0; i < this.nameValuePairs.length; i += 2) {
            CharSequence charSequence = this.nameValuePairs[i];
            if (AsciiString.hashCode(charSequence) != n || !AsciiString.contentEqualsIgnoreCase(charSequence, string)) continue;
            arrayList.add(this.nameValuePairs[i + 1].toString());
        }
        return arrayList;
    }

    @Override
    public List<Map.Entry<String, String>> entries() {
        if (this.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<Map.Entry<String, String>>(this.size());
        for (int i = 0; i < this.nameValuePairs.length; i += 2) {
            arrayList.add(new AbstractMap.SimpleImmutableEntry<String, String>(this.nameValuePairs[i].toString(), this.nameValuePairs[i + 1].toString()));
        }
        return arrayList;
    }

    @Override
    public boolean contains(String string) {
        return this.get0(string) != null;
    }

    @Override
    public boolean contains(String string, String string2, boolean bl) {
        return this.containsValue(string, string2, bl);
    }

    @Override
    public boolean containsValue(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        if (bl) {
            for (int i = 0; i < this.nameValuePairs.length; i += 2) {
                if (!AsciiString.contentEqualsIgnoreCase(this.nameValuePairs[i], charSequence) || !AsciiString.contentEqualsIgnoreCase(this.nameValuePairs[i + 1], charSequence2)) continue;
                return false;
            }
        } else {
            for (int i = 0; i < this.nameValuePairs.length; i += 2) {
                if (!AsciiString.contentEqualsIgnoreCase(this.nameValuePairs[i], charSequence) || !AsciiString.contentEquals(this.nameValuePairs[i + 1], charSequence2)) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<String> valueStringIterator(CharSequence charSequence) {
        return new ReadOnlyStringValueIterator(this, charSequence);
    }

    public Iterator<CharSequence> valueCharSequenceIterator(CharSequence charSequence) {
        return new ReadOnlyValueIterator(this, charSequence);
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return new ReadOnlyStringIterator(this, null);
    }

    @Override
    public Iterator<Map.Entry<CharSequence, CharSequence>> iteratorCharSequence() {
        return new ReadOnlyIterator(this, null);
    }

    @Override
    public boolean isEmpty() {
        return this.nameValuePairs.length == 0;
    }

    @Override
    public int size() {
        return this.nameValuePairs.length >>> 1;
    }

    @Override
    public Set<String> names() {
        if (this.isEmpty()) {
            return Collections.emptySet();
        }
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>(this.size());
        for (int i = 0; i < this.nameValuePairs.length; i += 2) {
            linkedHashSet.add(this.nameValuePairs[i].toString());
        }
        return linkedHashSet;
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

    static CharSequence[] access$200(ReadOnlyHttpHeaders readOnlyHttpHeaders) {
        return readOnlyHttpHeaders.nameValuePairs;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ReadOnlyValueIterator
    implements Iterator<CharSequence> {
        private final CharSequence name;
        private final int nameHash;
        private int nextNameIndex;
        final ReadOnlyHttpHeaders this$0;

        ReadOnlyValueIterator(ReadOnlyHttpHeaders readOnlyHttpHeaders, CharSequence charSequence) {
            this.this$0 = readOnlyHttpHeaders;
            this.name = charSequence;
            this.nameHash = AsciiString.hashCode(charSequence);
            this.nextNameIndex = this.findNextValue();
        }

        @Override
        public boolean hasNext() {
            return this.nextNameIndex != -1;
        }

        @Override
        public CharSequence next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            CharSequence charSequence = ReadOnlyHttpHeaders.access$200(this.this$0)[this.nextNameIndex + 1];
            this.nextNameIndex = this.findNextValue();
            return charSequence;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("read only");
        }

        private int findNextValue() {
            for (int i = this.nextNameIndex; i < ReadOnlyHttpHeaders.access$200(this.this$0).length; i += 2) {
                CharSequence charSequence = ReadOnlyHttpHeaders.access$200(this.this$0)[i];
                if (this.nameHash != AsciiString.hashCode(charSequence) || !AsciiString.contentEqualsIgnoreCase(this.name, charSequence)) continue;
                return i;
            }
            return 1;
        }

        @Override
        public Object next() {
            return this.next();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ReadOnlyStringValueIterator
    implements Iterator<String> {
        private final CharSequence name;
        private final int nameHash;
        private int nextNameIndex;
        final ReadOnlyHttpHeaders this$0;

        ReadOnlyStringValueIterator(ReadOnlyHttpHeaders readOnlyHttpHeaders, CharSequence charSequence) {
            this.this$0 = readOnlyHttpHeaders;
            this.name = charSequence;
            this.nameHash = AsciiString.hashCode(charSequence);
            this.nextNameIndex = this.findNextValue();
        }

        @Override
        public boolean hasNext() {
            return this.nextNameIndex != -1;
        }

        @Override
        public String next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            String string = ReadOnlyHttpHeaders.access$200(this.this$0)[this.nextNameIndex + 1].toString();
            this.nextNameIndex = this.findNextValue();
            return string;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("read only");
        }

        private int findNextValue() {
            for (int i = this.nextNameIndex; i < ReadOnlyHttpHeaders.access$200(this.this$0).length; i += 2) {
                CharSequence charSequence = ReadOnlyHttpHeaders.access$200(this.this$0)[i];
                if (this.nameHash != AsciiString.hashCode(charSequence) || !AsciiString.contentEqualsIgnoreCase(this.name, charSequence)) continue;
                return i;
            }
            return 1;
        }

        @Override
        public Object next() {
            return this.next();
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ReadOnlyStringIterator
    implements Map.Entry<String, String>,
    Iterator<Map.Entry<String, String>> {
        private String key;
        private String value;
        private int nextNameIndex;
        final ReadOnlyHttpHeaders this$0;

        private ReadOnlyStringIterator(ReadOnlyHttpHeaders readOnlyHttpHeaders) {
            this.this$0 = readOnlyHttpHeaders;
        }

        @Override
        public boolean hasNext() {
            return this.nextNameIndex != ReadOnlyHttpHeaders.access$200(this.this$0).length;
        }

        @Override
        public Map.Entry<String, String> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.key = ReadOnlyHttpHeaders.access$200(this.this$0)[this.nextNameIndex].toString();
            this.value = ReadOnlyHttpHeaders.access$200(this.this$0)[this.nextNameIndex + 1].toString();
            this.nextNameIndex += 2;
            return this;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public String getKey() {
            return this.key;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        @Override
        public String setValue(String string) {
            throw new UnsupportedOperationException("read only");
        }

        public String toString() {
            return this.key + '=' + this.value;
        }

        @Override
        public Object setValue(Object object) {
            return this.setValue((String)object);
        }

        @Override
        public Object getValue() {
            return this.getValue();
        }

        @Override
        public Object getKey() {
            return this.getKey();
        }

        @Override
        public Object next() {
            return this.next();
        }

        ReadOnlyStringIterator(ReadOnlyHttpHeaders readOnlyHttpHeaders, 1 var2_2) {
            this(readOnlyHttpHeaders);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ReadOnlyIterator
    implements Map.Entry<CharSequence, CharSequence>,
    Iterator<Map.Entry<CharSequence, CharSequence>> {
        private CharSequence key;
        private CharSequence value;
        private int nextNameIndex;
        final ReadOnlyHttpHeaders this$0;

        private ReadOnlyIterator(ReadOnlyHttpHeaders readOnlyHttpHeaders) {
            this.this$0 = readOnlyHttpHeaders;
        }

        @Override
        public boolean hasNext() {
            return this.nextNameIndex != ReadOnlyHttpHeaders.access$200(this.this$0).length;
        }

        @Override
        public Map.Entry<CharSequence, CharSequence> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.key = ReadOnlyHttpHeaders.access$200(this.this$0)[this.nextNameIndex];
            this.value = ReadOnlyHttpHeaders.access$200(this.this$0)[this.nextNameIndex + 1];
            this.nextNameIndex += 2;
            return this;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("read only");
        }

        @Override
        public CharSequence getKey() {
            return this.key;
        }

        @Override
        public CharSequence getValue() {
            return this.value;
        }

        @Override
        public CharSequence setValue(CharSequence charSequence) {
            throw new UnsupportedOperationException("read only");
        }

        public String toString() {
            return this.key.toString() + '=' + this.value.toString();
        }

        @Override
        public Object setValue(Object object) {
            return this.setValue((CharSequence)object);
        }

        @Override
        public Object getValue() {
            return this.getValue();
        }

        @Override
        public Object getKey() {
            return this.getKey();
        }

        @Override
        public Object next() {
            return this.next();
        }

        ReadOnlyIterator(ReadOnlyHttpHeaders readOnlyHttpHeaders, 1 var2_2) {
            this(readOnlyHttpHeaders);
        }
    }
}

