/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.handler.codec.CharSequenceValueConverter;
import io.netty.handler.codec.Headers;
import io.netty.handler.codec.http2.DefaultHttp2Headers;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.util.AsciiString;
import io.netty.util.HashingStrategy;
import io.netty.util.internal.EmptyArrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ReadOnlyHttp2Headers
implements Http2Headers {
    private static final byte PSEUDO_HEADER_TOKEN = 58;
    private final AsciiString[] pseudoHeaders;
    private final AsciiString[] otherHeaders;
    static final boolean $assertionsDisabled = !ReadOnlyHttp2Headers.class.desiredAssertionStatus();

    public static ReadOnlyHttp2Headers trailers(boolean bl, AsciiString ... asciiStringArray) {
        return new ReadOnlyHttp2Headers(bl, EmptyArrays.EMPTY_ASCII_STRINGS, asciiStringArray);
    }

    public static ReadOnlyHttp2Headers clientHeaders(boolean bl, AsciiString asciiString, AsciiString asciiString2, AsciiString asciiString3, AsciiString asciiString4, AsciiString ... asciiStringArray) {
        return new ReadOnlyHttp2Headers(bl, new AsciiString[]{Http2Headers.PseudoHeaderName.METHOD.value(), asciiString, Http2Headers.PseudoHeaderName.PATH.value(), asciiString2, Http2Headers.PseudoHeaderName.SCHEME.value(), asciiString3, Http2Headers.PseudoHeaderName.AUTHORITY.value(), asciiString4}, asciiStringArray);
    }

    public static ReadOnlyHttp2Headers serverHeaders(boolean bl, AsciiString asciiString, AsciiString ... asciiStringArray) {
        return new ReadOnlyHttp2Headers(bl, new AsciiString[]{Http2Headers.PseudoHeaderName.STATUS.value(), asciiString}, asciiStringArray);
    }

    private ReadOnlyHttp2Headers(boolean bl, AsciiString[] asciiStringArray, AsciiString ... asciiStringArray2) {
        if (!$assertionsDisabled && (asciiStringArray.length & 1) != 0) {
            throw new AssertionError();
        }
        if ((asciiStringArray2.length & 1) != 0) {
            throw ReadOnlyHttp2Headers.newInvalidArraySizeException();
        }
        if (bl) {
            ReadOnlyHttp2Headers.validateHeaders(asciiStringArray, asciiStringArray2);
        }
        this.pseudoHeaders = asciiStringArray;
        this.otherHeaders = asciiStringArray2;
    }

    private static IllegalArgumentException newInvalidArraySizeException() {
        return new IllegalArgumentException("pseudoHeaders and otherHeaders must be arrays of [name, value] pairs");
    }

    private static void validateHeaders(AsciiString[] asciiStringArray, AsciiString ... asciiStringArray2) {
        int n;
        for (n = 1; n < asciiStringArray.length; n += 2) {
            if (asciiStringArray[n] != null) continue;
            throw new IllegalArgumentException("pseudoHeaders value at index " + n + " is null");
        }
        n = 0;
        int n2 = asciiStringArray2.length - 1;
        for (int i = 0; i < n2; i += 2) {
            AsciiString asciiString = asciiStringArray2[i];
            DefaultHttp2Headers.HTTP2_NAME_VALIDATOR.validateName(asciiString);
            if (n == 0 && !asciiString.isEmpty() && asciiString.byteAt(0) != 58) {
                n = 1;
            } else if (n != 0 && !asciiString.isEmpty() && asciiString.byteAt(0) == 58) {
                throw new IllegalArgumentException("otherHeaders name at index " + i + " is a pseudo header that appears after non-pseudo headers.");
            }
            if (asciiStringArray2[i + 1] != null) continue;
            throw new IllegalArgumentException("otherHeaders value at index " + (i + 1) + " is null");
        }
    }

    private AsciiString get0(CharSequence charSequence) {
        int n;
        int n2 = AsciiString.hashCode(charSequence);
        int n3 = this.pseudoHeaders.length - 1;
        for (n = 0; n < n3; n += 2) {
            AsciiString asciiString = this.pseudoHeaders[n];
            if (asciiString.hashCode() != n2 || !asciiString.contentEqualsIgnoreCase(charSequence)) continue;
            return this.pseudoHeaders[n + 1];
        }
        n = this.otherHeaders.length - 1;
        for (int i = 0; i < n; i += 2) {
            AsciiString asciiString = this.otherHeaders[i];
            if (asciiString.hashCode() != n2 || !asciiString.contentEqualsIgnoreCase(charSequence)) continue;
            return this.otherHeaders[i + 1];
        }
        return null;
    }

    @Override
    public CharSequence get(CharSequence charSequence) {
        return this.get0(charSequence);
    }

    @Override
    public CharSequence get(CharSequence charSequence, CharSequence charSequence2) {
        CharSequence charSequence3 = this.get(charSequence);
        return charSequence3 != null ? charSequence3 : charSequence2;
    }

    @Override
    public CharSequence getAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public CharSequence getAndRemove(CharSequence charSequence, CharSequence charSequence2) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public List<CharSequence> getAll(CharSequence charSequence) {
        int n;
        int n2 = AsciiString.hashCode(charSequence);
        ArrayList<CharSequence> arrayList = new ArrayList<CharSequence>();
        int n3 = this.pseudoHeaders.length - 1;
        for (n = 0; n < n3; n += 2) {
            AsciiString asciiString = this.pseudoHeaders[n];
            if (asciiString.hashCode() != n2 || !asciiString.contentEqualsIgnoreCase(charSequence)) continue;
            arrayList.add(this.pseudoHeaders[n + 1]);
        }
        n = this.otherHeaders.length - 1;
        for (int i = 0; i < n; i += 2) {
            AsciiString asciiString = this.otherHeaders[i];
            if (asciiString.hashCode() != n2 || !asciiString.contentEqualsIgnoreCase(charSequence)) continue;
            arrayList.add(this.otherHeaders[i + 1]);
        }
        return arrayList;
    }

    @Override
    public List<CharSequence> getAllAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Boolean getBoolean(CharSequence charSequence) {
        AsciiString asciiString = this.get0(charSequence);
        return asciiString != null ? Boolean.valueOf(CharSequenceValueConverter.INSTANCE.convertToBoolean(asciiString)) : null;
    }

    @Override
    public boolean getBoolean(CharSequence charSequence, boolean bl) {
        Boolean bl2 = this.getBoolean(charSequence);
        return bl2 != null ? bl2 : bl;
    }

    @Override
    public Byte getByte(CharSequence charSequence) {
        AsciiString asciiString = this.get0(charSequence);
        return asciiString != null ? Byte.valueOf(CharSequenceValueConverter.INSTANCE.convertToByte(asciiString)) : null;
    }

    @Override
    public byte getByte(CharSequence charSequence, byte by) {
        Byte by2 = this.getByte(charSequence);
        return by2 != null ? by2 : by;
    }

    @Override
    public Character getChar(CharSequence charSequence) {
        AsciiString asciiString = this.get0(charSequence);
        return asciiString != null ? Character.valueOf(CharSequenceValueConverter.INSTANCE.convertToChar(asciiString)) : null;
    }

    @Override
    public char getChar(CharSequence charSequence, char c) {
        Character c2 = this.getChar(charSequence);
        return c2 != null ? c2.charValue() : c;
    }

    @Override
    public Short getShort(CharSequence charSequence) {
        AsciiString asciiString = this.get0(charSequence);
        return asciiString != null ? Short.valueOf(CharSequenceValueConverter.INSTANCE.convertToShort(asciiString)) : null;
    }

    @Override
    public short getShort(CharSequence charSequence, short s) {
        Short s2 = this.getShort(charSequence);
        return s2 != null ? s2 : s;
    }

    @Override
    public Integer getInt(CharSequence charSequence) {
        AsciiString asciiString = this.get0(charSequence);
        return asciiString != null ? Integer.valueOf(CharSequenceValueConverter.INSTANCE.convertToInt(asciiString)) : null;
    }

    @Override
    public int getInt(CharSequence charSequence, int n) {
        Integer n2 = this.getInt(charSequence);
        return n2 != null ? n2 : n;
    }

    @Override
    public Long getLong(CharSequence charSequence) {
        AsciiString asciiString = this.get0(charSequence);
        return asciiString != null ? Long.valueOf(CharSequenceValueConverter.INSTANCE.convertToLong(asciiString)) : null;
    }

    @Override
    public long getLong(CharSequence charSequence, long l) {
        Long l2 = this.getLong(charSequence);
        return l2 != null ? l2 : l;
    }

    @Override
    public Float getFloat(CharSequence charSequence) {
        AsciiString asciiString = this.get0(charSequence);
        return asciiString != null ? Float.valueOf(CharSequenceValueConverter.INSTANCE.convertToFloat(asciiString)) : null;
    }

    @Override
    public float getFloat(CharSequence charSequence, float f) {
        Float f2 = this.getFloat(charSequence);
        return f2 != null ? f2.floatValue() : f;
    }

    @Override
    public Double getDouble(CharSequence charSequence) {
        AsciiString asciiString = this.get0(charSequence);
        return asciiString != null ? Double.valueOf(CharSequenceValueConverter.INSTANCE.convertToDouble(asciiString)) : null;
    }

    @Override
    public double getDouble(CharSequence charSequence, double d) {
        Double d2 = this.getDouble(charSequence);
        return d2 != null ? d2 : d;
    }

    @Override
    public Long getTimeMillis(CharSequence charSequence) {
        AsciiString asciiString = this.get0(charSequence);
        return asciiString != null ? Long.valueOf(CharSequenceValueConverter.INSTANCE.convertToTimeMillis(asciiString)) : null;
    }

    @Override
    public long getTimeMillis(CharSequence charSequence, long l) {
        Long l2 = this.getTimeMillis(charSequence);
        return l2 != null ? l2 : l;
    }

    @Override
    public Boolean getBooleanAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public boolean getBooleanAndRemove(CharSequence charSequence, boolean bl) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Byte getByteAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public byte getByteAndRemove(CharSequence charSequence, byte by) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Character getCharAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public char getCharAndRemove(CharSequence charSequence, char c) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Short getShortAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public short getShortAndRemove(CharSequence charSequence, short s) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Integer getIntAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public int getIntAndRemove(CharSequence charSequence, int n) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Long getLongAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public long getLongAndRemove(CharSequence charSequence, long l) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Float getFloatAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public float getFloatAndRemove(CharSequence charSequence, float f) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Double getDoubleAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public double getDoubleAndRemove(CharSequence charSequence, double d) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Long getTimeMillisAndRemove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public long getTimeMillisAndRemove(CharSequence charSequence, long l) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public boolean contains(CharSequence charSequence) {
        return this.get(charSequence) != null;
    }

    @Override
    public boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        return this.contains(charSequence, charSequence2, true);
    }

    @Override
    public boolean containsObject(CharSequence charSequence, Object object) {
        if (object instanceof CharSequence) {
            return this.contains(charSequence, (CharSequence)object);
        }
        return this.contains(charSequence, object.toString());
    }

    @Override
    public boolean containsBoolean(CharSequence charSequence, boolean bl) {
        return this.contains(charSequence, String.valueOf(bl));
    }

    @Override
    public boolean containsByte(CharSequence charSequence, byte by) {
        return this.contains(charSequence, String.valueOf(by));
    }

    @Override
    public boolean containsChar(CharSequence charSequence, char c) {
        return this.contains(charSequence, String.valueOf(c));
    }

    @Override
    public boolean containsShort(CharSequence charSequence, short s) {
        return this.contains(charSequence, String.valueOf(s));
    }

    @Override
    public boolean containsInt(CharSequence charSequence, int n) {
        return this.contains(charSequence, String.valueOf(n));
    }

    @Override
    public boolean containsLong(CharSequence charSequence, long l) {
        return this.contains(charSequence, String.valueOf(l));
    }

    @Override
    public boolean containsFloat(CharSequence charSequence, float f) {
        return true;
    }

    @Override
    public boolean containsDouble(CharSequence charSequence, double d) {
        return this.contains(charSequence, String.valueOf(d));
    }

    @Override
    public boolean containsTimeMillis(CharSequence charSequence, long l) {
        return this.contains(charSequence, String.valueOf(l));
    }

    @Override
    public int size() {
        return this.pseudoHeaders.length + this.otherHeaders.length >>> 1;
    }

    @Override
    public boolean isEmpty() {
        return this.pseudoHeaders.length == 0 && this.otherHeaders.length == 0;
    }

    @Override
    public Set<CharSequence> names() {
        int n;
        if (this.isEmpty()) {
            return Collections.emptySet();
        }
        LinkedHashSet<CharSequence> linkedHashSet = new LinkedHashSet<CharSequence>(this.size());
        int n2 = this.pseudoHeaders.length - 1;
        for (n = 0; n < n2; n += 2) {
            linkedHashSet.add(this.pseudoHeaders[n]);
        }
        n = this.otherHeaders.length - 1;
        for (int i = 0; i < n; i += 2) {
            linkedHashSet.add(this.otherHeaders[i]);
        }
        return linkedHashSet;
    }

    @Override
    public Http2Headers add(CharSequence charSequence, CharSequence charSequence2) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers add(CharSequence charSequence, Iterable<? extends CharSequence> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers add(CharSequence charSequence, CharSequence ... charSequenceArray) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addObject(CharSequence charSequence, Object object) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addObject(CharSequence charSequence, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addObject(CharSequence charSequence, Object ... objectArray) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addBoolean(CharSequence charSequence, boolean bl) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addByte(CharSequence charSequence, byte by) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addChar(CharSequence charSequence, char c) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addShort(CharSequence charSequence, short s) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addInt(CharSequence charSequence, int n) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addLong(CharSequence charSequence, long l) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addFloat(CharSequence charSequence, float f) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addDouble(CharSequence charSequence, double d) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers addTimeMillis(CharSequence charSequence, long l) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers add(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers set(CharSequence charSequence, CharSequence charSequence2) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers set(CharSequence charSequence, Iterable<? extends CharSequence> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers set(CharSequence charSequence, CharSequence ... charSequenceArray) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setObject(CharSequence charSequence, Object object) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setObject(CharSequence charSequence, Iterable<?> iterable) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setObject(CharSequence charSequence, Object ... objectArray) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setBoolean(CharSequence charSequence, boolean bl) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setByte(CharSequence charSequence, byte by) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setChar(CharSequence charSequence, char c) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setShort(CharSequence charSequence, short s) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setInt(CharSequence charSequence, int n) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setLong(CharSequence charSequence, long l) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setFloat(CharSequence charSequence, float f) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setDouble(CharSequence charSequence, double d) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setTimeMillis(CharSequence charSequence, long l) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers set(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers setAll(Headers<? extends CharSequence, ? extends CharSequence, ?> headers) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public boolean remove(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers clear() {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Iterator<Map.Entry<CharSequence, CharSequence>> iterator() {
        return new ReadOnlyIterator(this, null);
    }

    @Override
    public Iterator<CharSequence> valueIterator(CharSequence charSequence) {
        return new ReadOnlyValueIterator(this, charSequence);
    }

    @Override
    public Http2Headers method(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers scheme(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers authority(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers path(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public Http2Headers status(CharSequence charSequence) {
        throw new UnsupportedOperationException("read only");
    }

    @Override
    public CharSequence method() {
        return this.get(Http2Headers.PseudoHeaderName.METHOD.value());
    }

    @Override
    public CharSequence scheme() {
        return this.get(Http2Headers.PseudoHeaderName.SCHEME.value());
    }

    @Override
    public CharSequence authority() {
        return this.get(Http2Headers.PseudoHeaderName.AUTHORITY.value());
    }

    @Override
    public CharSequence path() {
        return this.get(Http2Headers.PseudoHeaderName.PATH.value());
    }

    @Override
    public CharSequence status() {
        return this.get(Http2Headers.PseudoHeaderName.STATUS.value());
    }

    @Override
    public boolean contains(CharSequence charSequence, CharSequence charSequence2, boolean bl) {
        int n = AsciiString.hashCode(charSequence);
        HashingStrategy<CharSequence> hashingStrategy = bl ? AsciiString.CASE_INSENSITIVE_HASHER : AsciiString.CASE_SENSITIVE_HASHER;
        int n2 = hashingStrategy.hashCode(charSequence2);
        return ReadOnlyHttp2Headers.contains(charSequence, n, charSequence2, n2, hashingStrategy, this.otherHeaders) || ReadOnlyHttp2Headers.contains(charSequence, n, charSequence2, n2, hashingStrategy, this.pseudoHeaders);
    }

    private static boolean contains(CharSequence charSequence, int n, CharSequence charSequence2, int n2, HashingStrategy<CharSequence> hashingStrategy, AsciiString[] asciiStringArray) {
        int n3 = asciiStringArray.length - 1;
        for (int i = 0; i < n3; i += 2) {
            AsciiString asciiString = asciiStringArray[i];
            AsciiString asciiString2 = asciiStringArray[i + 1];
            if (asciiString.hashCode() != n || asciiString2.hashCode() != n2 || !asciiString.contentEqualsIgnoreCase(charSequence) || !hashingStrategy.equals(asciiString2, charSequence2)) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.getClass().getSimpleName()).append('[');
        String string = "";
        for (Map.Entry<CharSequence, CharSequence> entry : this) {
            stringBuilder.append(string);
            stringBuilder.append(entry.getKey()).append(": ").append(entry.getValue());
            string = ", ";
        }
        return stringBuilder.append(']').toString();
    }

    @Override
    public Headers clear() {
        return this.clear();
    }

    @Override
    public boolean remove(Object object) {
        return this.remove((CharSequence)object);
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
    public Headers setTimeMillis(Object object, long l) {
        return this.setTimeMillis((CharSequence)object, l);
    }

    @Override
    public Headers setDouble(Object object, double d) {
        return this.setDouble((CharSequence)object, d);
    }

    @Override
    public Headers setFloat(Object object, float f) {
        return this.setFloat((CharSequence)object, f);
    }

    @Override
    public Headers setLong(Object object, long l) {
        return this.setLong((CharSequence)object, l);
    }

    @Override
    public Headers setInt(Object object, int n) {
        return this.setInt((CharSequence)object, n);
    }

    @Override
    public Headers setShort(Object object, short s) {
        return this.setShort((CharSequence)object, s);
    }

    @Override
    public Headers setChar(Object object, char c) {
        return this.setChar((CharSequence)object, c);
    }

    @Override
    public Headers setByte(Object object, byte by) {
        return this.setByte((CharSequence)object, by);
    }

    @Override
    public Headers setBoolean(Object object, boolean bl) {
        return this.setBoolean((CharSequence)object, bl);
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
    public Headers set(Object object, Object object2) {
        return this.set((CharSequence)object, (CharSequence)object2);
    }

    @Override
    public Headers add(Headers headers) {
        return this.add(headers);
    }

    @Override
    public Headers addTimeMillis(Object object, long l) {
        return this.addTimeMillis((CharSequence)object, l);
    }

    @Override
    public Headers addDouble(Object object, double d) {
        return this.addDouble((CharSequence)object, d);
    }

    @Override
    public Headers addFloat(Object object, float f) {
        return this.addFloat((CharSequence)object, f);
    }

    @Override
    public Headers addLong(Object object, long l) {
        return this.addLong((CharSequence)object, l);
    }

    @Override
    public Headers addInt(Object object, int n) {
        return this.addInt((CharSequence)object, n);
    }

    @Override
    public Headers addShort(Object object, short s) {
        return this.addShort((CharSequence)object, s);
    }

    @Override
    public Headers addChar(Object object, char c) {
        return this.addChar((CharSequence)object, c);
    }

    @Override
    public Headers addByte(Object object, byte by) {
        return this.addByte((CharSequence)object, by);
    }

    @Override
    public Headers addBoolean(Object object, boolean bl) {
        return this.addBoolean((CharSequence)object, bl);
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
    public boolean containsTimeMillis(Object object, long l) {
        return this.containsTimeMillis((CharSequence)object, l);
    }

    @Override
    public boolean containsDouble(Object object, double d) {
        return this.containsDouble((CharSequence)object, d);
    }

    @Override
    public boolean containsFloat(Object object, float f) {
        return this.containsFloat((CharSequence)object, f);
    }

    @Override
    public boolean containsLong(Object object, long l) {
        return this.containsLong((CharSequence)object, l);
    }

    @Override
    public boolean containsInt(Object object, int n) {
        return this.containsInt((CharSequence)object, n);
    }

    @Override
    public boolean containsShort(Object object, short s) {
        return this.containsShort((CharSequence)object, s);
    }

    @Override
    public boolean containsChar(Object object, char c) {
        return this.containsChar((CharSequence)object, c);
    }

    @Override
    public boolean containsByte(Object object, byte by) {
        return this.containsByte((CharSequence)object, by);
    }

    @Override
    public boolean containsBoolean(Object object, boolean bl) {
        return this.containsBoolean((CharSequence)object, bl);
    }

    @Override
    public boolean containsObject(Object object, Object object2) {
        return this.containsObject((CharSequence)object, object2);
    }

    @Override
    public boolean contains(Object object, Object object2) {
        return this.contains((CharSequence)object, (CharSequence)object2);
    }

    @Override
    public boolean contains(Object object) {
        return this.contains((CharSequence)object);
    }

    @Override
    public long getTimeMillisAndRemove(Object object, long l) {
        return this.getTimeMillisAndRemove((CharSequence)object, l);
    }

    @Override
    public Long getTimeMillisAndRemove(Object object) {
        return this.getTimeMillisAndRemove((CharSequence)object);
    }

    @Override
    public double getDoubleAndRemove(Object object, double d) {
        return this.getDoubleAndRemove((CharSequence)object, d);
    }

    @Override
    public Double getDoubleAndRemove(Object object) {
        return this.getDoubleAndRemove((CharSequence)object);
    }

    @Override
    public float getFloatAndRemove(Object object, float f) {
        return this.getFloatAndRemove((CharSequence)object, f);
    }

    @Override
    public Float getFloatAndRemove(Object object) {
        return this.getFloatAndRemove((CharSequence)object);
    }

    @Override
    public long getLongAndRemove(Object object, long l) {
        return this.getLongAndRemove((CharSequence)object, l);
    }

    @Override
    public Long getLongAndRemove(Object object) {
        return this.getLongAndRemove((CharSequence)object);
    }

    @Override
    public int getIntAndRemove(Object object, int n) {
        return this.getIntAndRemove((CharSequence)object, n);
    }

    @Override
    public Integer getIntAndRemove(Object object) {
        return this.getIntAndRemove((CharSequence)object);
    }

    @Override
    public short getShortAndRemove(Object object, short s) {
        return this.getShortAndRemove((CharSequence)object, s);
    }

    @Override
    public Short getShortAndRemove(Object object) {
        return this.getShortAndRemove((CharSequence)object);
    }

    @Override
    public char getCharAndRemove(Object object, char c) {
        return this.getCharAndRemove((CharSequence)object, c);
    }

    @Override
    public Character getCharAndRemove(Object object) {
        return this.getCharAndRemove((CharSequence)object);
    }

    @Override
    public byte getByteAndRemove(Object object, byte by) {
        return this.getByteAndRemove((CharSequence)object, by);
    }

    @Override
    public Byte getByteAndRemove(Object object) {
        return this.getByteAndRemove((CharSequence)object);
    }

    @Override
    public boolean getBooleanAndRemove(Object object, boolean bl) {
        return this.getBooleanAndRemove((CharSequence)object, bl);
    }

    @Override
    public Boolean getBooleanAndRemove(Object object) {
        return this.getBooleanAndRemove((CharSequence)object);
    }

    @Override
    public long getTimeMillis(Object object, long l) {
        return this.getTimeMillis((CharSequence)object, l);
    }

    @Override
    public Long getTimeMillis(Object object) {
        return this.getTimeMillis((CharSequence)object);
    }

    @Override
    public double getDouble(Object object, double d) {
        return this.getDouble((CharSequence)object, d);
    }

    @Override
    public Double getDouble(Object object) {
        return this.getDouble((CharSequence)object);
    }

    @Override
    public float getFloat(Object object, float f) {
        return this.getFloat((CharSequence)object, f);
    }

    @Override
    public Float getFloat(Object object) {
        return this.getFloat((CharSequence)object);
    }

    @Override
    public long getLong(Object object, long l) {
        return this.getLong((CharSequence)object, l);
    }

    @Override
    public Long getLong(Object object) {
        return this.getLong((CharSequence)object);
    }

    @Override
    public int getInt(Object object, int n) {
        return this.getInt((CharSequence)object, n);
    }

    @Override
    public Integer getInt(Object object) {
        return this.getInt((CharSequence)object);
    }

    @Override
    public short getShort(Object object, short s) {
        return this.getShort((CharSequence)object, s);
    }

    @Override
    public Short getShort(Object object) {
        return this.getShort((CharSequence)object);
    }

    @Override
    public char getChar(Object object, char c) {
        return this.getChar((CharSequence)object, c);
    }

    @Override
    public Character getChar(Object object) {
        return this.getChar((CharSequence)object);
    }

    @Override
    public byte getByte(Object object, byte by) {
        return this.getByte((CharSequence)object, by);
    }

    @Override
    public Byte getByte(Object object) {
        return this.getByte((CharSequence)object);
    }

    @Override
    public boolean getBoolean(Object object, boolean bl) {
        return this.getBoolean((CharSequence)object, bl);
    }

    @Override
    public Boolean getBoolean(Object object) {
        return this.getBoolean((CharSequence)object);
    }

    @Override
    public List getAllAndRemove(Object object) {
        return this.getAllAndRemove((CharSequence)object);
    }

    @Override
    public List getAll(Object object) {
        return this.getAll((CharSequence)object);
    }

    @Override
    public Object getAndRemove(Object object, Object object2) {
        return this.getAndRemove((CharSequence)object, (CharSequence)object2);
    }

    @Override
    public Object getAndRemove(Object object) {
        return this.getAndRemove((CharSequence)object);
    }

    @Override
    public Object get(Object object, Object object2) {
        return this.get((CharSequence)object, (CharSequence)object2);
    }

    @Override
    public Object get(Object object) {
        return this.get((CharSequence)object);
    }

    static AsciiString[] access$100(ReadOnlyHttp2Headers readOnlyHttp2Headers) {
        return readOnlyHttp2Headers.pseudoHeaders;
    }

    static AsciiString[] access$200(ReadOnlyHttp2Headers readOnlyHttp2Headers) {
        return readOnlyHttp2Headers.otherHeaders;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ReadOnlyIterator
    implements Map.Entry<CharSequence, CharSequence>,
    Iterator<Map.Entry<CharSequence, CharSequence>> {
        private int i;
        private AsciiString[] current;
        private AsciiString key;
        private AsciiString value;
        final ReadOnlyHttp2Headers this$0;

        private ReadOnlyIterator(ReadOnlyHttp2Headers readOnlyHttp2Headers) {
            this.this$0 = readOnlyHttp2Headers;
            this.current = ReadOnlyHttp2Headers.access$100(this.this$0).length != 0 ? ReadOnlyHttp2Headers.access$100(this.this$0) : ReadOnlyHttp2Headers.access$200(this.this$0);
        }

        @Override
        public boolean hasNext() {
            return this.i != this.current.length;
        }

        @Override
        public Map.Entry<CharSequence, CharSequence> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.key = this.current[this.i];
            this.value = this.current[this.i + 1];
            this.i += 2;
            if (this.i == this.current.length && this.current == ReadOnlyHttp2Headers.access$100(this.this$0)) {
                this.current = ReadOnlyHttp2Headers.access$200(this.this$0);
                this.i = 0;
            }
            return this;
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

        @Override
        public void remove() {
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

        ReadOnlyIterator(ReadOnlyHttp2Headers readOnlyHttp2Headers, 1 var2_2) {
            this(readOnlyHttp2Headers);
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private final class ReadOnlyValueIterator
    implements Iterator<CharSequence> {
        private int i;
        private final int nameHash;
        private final CharSequence name;
        private AsciiString[] current;
        private AsciiString next;
        final ReadOnlyHttp2Headers this$0;

        ReadOnlyValueIterator(ReadOnlyHttp2Headers readOnlyHttp2Headers, CharSequence charSequence) {
            this.this$0 = readOnlyHttp2Headers;
            this.current = ReadOnlyHttp2Headers.access$100(this.this$0).length != 0 ? ReadOnlyHttp2Headers.access$100(this.this$0) : ReadOnlyHttp2Headers.access$200(this.this$0);
            this.nameHash = AsciiString.hashCode(charSequence);
            this.name = charSequence;
            this.calculateNext();
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public CharSequence next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            AsciiString asciiString = this.next;
            this.calculateNext();
            return asciiString;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("read only");
        }

        private void calculateNext() {
            while (this.i < this.current.length) {
                AsciiString asciiString = this.current[this.i];
                if (asciiString.hashCode() == this.nameHash && asciiString.contentEqualsIgnoreCase(this.name)) {
                    this.next = this.current[this.i + 1];
                    this.i += 2;
                    return;
                }
                this.i += 2;
            }
            if (this.i >= this.current.length && this.current == ReadOnlyHttp2Headers.access$100(this.this$0)) {
                this.i = 0;
                this.current = ReadOnlyHttp2Headers.access$200(this.this$0);
                this.calculateNext();
            } else {
                this.next = null;
            }
        }

        @Override
        public Object next() {
            return this.next();
        }
    }
}

