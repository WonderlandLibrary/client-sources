/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util;

import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;
import io.netty.util.HashingStrategy;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.MathUtil;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class AsciiString
implements CharSequence,
Comparable<CharSequence> {
    public static final AsciiString EMPTY_STRING = AsciiString.cached("");
    private static final char MAX_CHAR_VALUE = '\u00ff';
    public static final int INDEX_NOT_FOUND = -1;
    private final byte[] value;
    private final int offset;
    private final int length;
    private int hash;
    private String string;
    public static final HashingStrategy<CharSequence> CASE_INSENSITIVE_HASHER = new HashingStrategy<CharSequence>(){

        @Override
        public int hashCode(CharSequence charSequence) {
            return AsciiString.hashCode(charSequence);
        }

        @Override
        public boolean equals(CharSequence charSequence, CharSequence charSequence2) {
            return AsciiString.contentEqualsIgnoreCase(charSequence, charSequence2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((CharSequence)object, (CharSequence)object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((CharSequence)object);
        }
    };
    public static final HashingStrategy<CharSequence> CASE_SENSITIVE_HASHER = new HashingStrategy<CharSequence>(){

        @Override
        public int hashCode(CharSequence charSequence) {
            return AsciiString.hashCode(charSequence);
        }

        @Override
        public boolean equals(CharSequence charSequence, CharSequence charSequence2) {
            return AsciiString.contentEquals(charSequence, charSequence2);
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return this.equals((CharSequence)object, (CharSequence)object2);
        }

        @Override
        public int hashCode(Object object) {
            return this.hashCode((CharSequence)object);
        }
    };

    public AsciiString(byte[] byArray) {
        this(byArray, true);
    }

    public AsciiString(byte[] byArray, boolean bl) {
        this(byArray, 0, byArray.length, bl);
    }

    public AsciiString(byte[] byArray, int n, int n2, boolean bl) {
        if (bl) {
            this.value = Arrays.copyOfRange(byArray, n, n + n2);
            this.offset = 0;
        } else {
            if (MathUtil.isOutOfBounds(n, n2, byArray.length)) {
                throw new IndexOutOfBoundsException("expected: 0 <= start(" + n + ") <= start + length(" + n2 + ") <= value.length(" + byArray.length + ')');
            }
            this.value = byArray;
            this.offset = n;
        }
        this.length = n2;
    }

    public AsciiString(ByteBuffer byteBuffer) {
        this(byteBuffer, true);
    }

    public AsciiString(ByteBuffer byteBuffer, boolean bl) {
        this(byteBuffer, byteBuffer.position(), byteBuffer.remaining(), bl);
    }

    public AsciiString(ByteBuffer byteBuffer, int n, int n2, boolean bl) {
        if (MathUtil.isOutOfBounds(n, n2, byteBuffer.capacity())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + n + ") <= start + length(" + n2 + ") <= value.capacity(" + byteBuffer.capacity() + ')');
        }
        if (byteBuffer.hasArray()) {
            if (bl) {
                int n3 = byteBuffer.arrayOffset() + n;
                this.value = Arrays.copyOfRange(byteBuffer.array(), n3, n3 + n2);
                this.offset = 0;
            } else {
                this.value = byteBuffer.array();
                this.offset = n;
            }
        } else {
            this.value = new byte[n2];
            int n4 = byteBuffer.position();
            byteBuffer.get(this.value, 0, n2);
            byteBuffer.position(n4);
            this.offset = 0;
        }
        this.length = n2;
    }

    public AsciiString(char[] cArray) {
        this(cArray, 0, cArray.length);
    }

    public AsciiString(char[] cArray, int n, int n2) {
        if (MathUtil.isOutOfBounds(n, n2, cArray.length)) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + n + ") <= start + length(" + n2 + ") <= value.length(" + cArray.length + ')');
        }
        this.value = new byte[n2];
        int n3 = 0;
        int n4 = n;
        while (n3 < n2) {
            this.value[n3] = AsciiString.c2b(cArray[n4]);
            ++n3;
            ++n4;
        }
        this.offset = 0;
        this.length = n2;
    }

    public AsciiString(char[] cArray, Charset charset) {
        this(cArray, charset, 0, cArray.length);
    }

    public AsciiString(char[] cArray, Charset charset, int n, int n2) {
        CharBuffer charBuffer = CharBuffer.wrap(cArray, n, n2);
        CharsetEncoder charsetEncoder = CharsetUtil.encoder(charset);
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)(charsetEncoder.maxBytesPerChar() * (float)n2));
        charsetEncoder.encode(charBuffer, byteBuffer, false);
        int n3 = byteBuffer.arrayOffset();
        this.value = Arrays.copyOfRange(byteBuffer.array(), n3, n3 + byteBuffer.position());
        this.offset = 0;
        this.length = this.value.length;
    }

    public AsciiString(CharSequence charSequence) {
        this(charSequence, 0, charSequence.length());
    }

    public AsciiString(CharSequence charSequence, int n, int n2) {
        if (MathUtil.isOutOfBounds(n, n2, charSequence.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + n + ") <= start + length(" + n2 + ") <= value.length(" + charSequence.length() + ')');
        }
        this.value = new byte[n2];
        int n3 = 0;
        int n4 = n;
        while (n3 < n2) {
            this.value[n3] = AsciiString.c2b(charSequence.charAt(n4));
            ++n3;
            ++n4;
        }
        this.offset = 0;
        this.length = n2;
    }

    public AsciiString(CharSequence charSequence, Charset charset) {
        this(charSequence, charset, 0, charSequence.length());
    }

    public AsciiString(CharSequence charSequence, Charset charset, int n, int n2) {
        CharBuffer charBuffer = CharBuffer.wrap(charSequence, n, n + n2);
        CharsetEncoder charsetEncoder = CharsetUtil.encoder(charset);
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)(charsetEncoder.maxBytesPerChar() * (float)n2));
        charsetEncoder.encode(charBuffer, byteBuffer, false);
        int n3 = byteBuffer.arrayOffset();
        this.value = Arrays.copyOfRange(byteBuffer.array(), n3, n3 + byteBuffer.position());
        this.offset = 0;
        this.length = this.value.length;
    }

    public int forEachByte(ByteProcessor byteProcessor) throws Exception {
        return this.forEachByte0(0, this.length(), byteProcessor);
    }

    public int forEachByte(int n, int n2, ByteProcessor byteProcessor) throws Exception {
        if (MathUtil.isOutOfBounds(n, n2, this.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= index(" + n + ") <= start + length(" + n2 + ") <= length(" + this.length() + ')');
        }
        return this.forEachByte0(n, n2, byteProcessor);
    }

    private int forEachByte0(int n, int n2, ByteProcessor byteProcessor) throws Exception {
        int n3 = this.offset + n + n2;
        for (int i = this.offset + n; i < n3; ++i) {
            if (byteProcessor.process(this.value[i])) continue;
            return i - this.offset;
        }
        return 1;
    }

    public int forEachByteDesc(ByteProcessor byteProcessor) throws Exception {
        return this.forEachByteDesc0(0, this.length(), byteProcessor);
    }

    public int forEachByteDesc(int n, int n2, ByteProcessor byteProcessor) throws Exception {
        if (MathUtil.isOutOfBounds(n, n2, this.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= index(" + n + ") <= start + length(" + n2 + ") <= length(" + this.length() + ')');
        }
        return this.forEachByteDesc0(n, n2, byteProcessor);
    }

    private int forEachByteDesc0(int n, int n2, ByteProcessor byteProcessor) throws Exception {
        int n3 = this.offset + n;
        for (int i = this.offset + n + n2 - 1; i >= n3; --i) {
            if (byteProcessor.process(this.value[i])) continue;
            return i - this.offset;
        }
        return 1;
    }

    public byte byteAt(int n) {
        if (n < 0 || n >= this.length) {
            throw new IndexOutOfBoundsException("index: " + n + " must be in the range [0," + this.length + ")");
        }
        if (PlatformDependent.hasUnsafe()) {
            return PlatformDependent.getByte(this.value, n + this.offset);
        }
        return this.value[n + this.offset];
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    @Override
    public int length() {
        return this.length;
    }

    public void arrayChanged() {
        this.string = null;
        this.hash = 0;
    }

    public byte[] array() {
        return this.value;
    }

    public int arrayOffset() {
        return this.offset;
    }

    public boolean isEntireArrayUsed() {
        return this.offset == 0 && this.length == this.value.length;
    }

    public byte[] toByteArray() {
        return this.toByteArray(0, this.length());
    }

    public byte[] toByteArray(int n, int n2) {
        return Arrays.copyOfRange(this.value, n + this.offset, n2 + this.offset);
    }

    public void copy(int n, byte[] byArray, int n2, int n3) {
        if (MathUtil.isOutOfBounds(n, n3, this.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + n + ") <= srcIdx + length(" + n3 + ") <= srcLen(" + this.length() + ')');
        }
        System.arraycopy(this.value, n + this.offset, ObjectUtil.checkNotNull(byArray, "dst"), n2, n3);
    }

    @Override
    public char charAt(int n) {
        return AsciiString.b2c(this.byteAt(n));
    }

    public boolean contains(CharSequence charSequence) {
        return this.indexOf(charSequence) >= 0;
    }

    @Override
    public int compareTo(CharSequence charSequence) {
        if (this == charSequence) {
            return 1;
        }
        int n = this.length();
        int n2 = charSequence.length();
        int n3 = Math.min(n, n2);
        int n4 = 0;
        int n5 = this.arrayOffset();
        while (n4 < n3) {
            int n6 = AsciiString.b2c(this.value[n5]) - charSequence.charAt(n4);
            if (n6 != 0) {
                return n6;
            }
            ++n4;
            ++n5;
        }
        return n - n2;
    }

    public AsciiString concat(CharSequence charSequence) {
        int n = this.length();
        int n2 = charSequence.length();
        if (n2 == 0) {
            return this;
        }
        if (charSequence.getClass() == AsciiString.class) {
            AsciiString asciiString = (AsciiString)charSequence;
            if (this.isEmpty()) {
                return asciiString;
            }
            byte[] byArray = new byte[n + n2];
            System.arraycopy(this.value, this.arrayOffset(), byArray, 0, n);
            System.arraycopy(asciiString.value, asciiString.arrayOffset(), byArray, n, n2);
            return new AsciiString(byArray, false);
        }
        if (this.isEmpty()) {
            return new AsciiString(charSequence);
        }
        byte[] byArray = new byte[n + n2];
        System.arraycopy(this.value, this.arrayOffset(), byArray, 0, n);
        int n3 = n;
        int n4 = 0;
        while (n3 < byArray.length) {
            byArray[n3] = AsciiString.c2b(charSequence.charAt(n4));
            ++n3;
            ++n4;
        }
        return new AsciiString(byArray, false);
    }

    public boolean endsWith(CharSequence charSequence) {
        int n = charSequence.length();
        return this.regionMatches(this.length() - n, charSequence, 0, n);
    }

    public boolean contentEqualsIgnoreCase(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() != this.length()) {
            return true;
        }
        if (charSequence.getClass() == AsciiString.class) {
            AsciiString asciiString = (AsciiString)charSequence;
            int n = this.arrayOffset();
            int n2 = asciiString.arrayOffset();
            while (n < this.length()) {
                if (!AsciiString.equalsIgnoreCase(this.value[n], asciiString.value[n2])) {
                    return true;
                }
                ++n;
                ++n2;
            }
            return false;
        }
        int n = this.arrayOffset();
        int n3 = 0;
        while (n < this.length()) {
            if (!AsciiString.equalsIgnoreCase(AsciiString.b2c(this.value[n]), charSequence.charAt(n3))) {
                return true;
            }
            ++n;
            ++n3;
        }
        return false;
    }

    public char[] toCharArray() {
        return this.toCharArray(0, this.length());
    }

    public char[] toCharArray(int n, int n2) {
        int n3 = n2 - n;
        if (n3 == 0) {
            return EmptyArrays.EMPTY_CHARS;
        }
        if (MathUtil.isOutOfBounds(n, n3, this.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + n + ") <= srcIdx + length(" + n3 + ") <= srcLen(" + this.length() + ')');
        }
        char[] cArray = new char[n3];
        int n4 = 0;
        int n5 = n + this.arrayOffset();
        while (n4 < n3) {
            cArray[n4] = AsciiString.b2c(this.value[n5]);
            ++n4;
            ++n5;
        }
        return cArray;
    }

    public void copy(int n, char[] cArray, int n2, int n3) {
        if (cArray == null) {
            throw new NullPointerException("dst");
        }
        if (MathUtil.isOutOfBounds(n, n3, this.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + n + ") <= srcIdx + length(" + n3 + ") <= srcLen(" + this.length() + ')');
        }
        int n4 = n2 + n3;
        int n5 = n2;
        int n6 = n + this.arrayOffset();
        while (n5 < n4) {
            cArray[n5] = AsciiString.b2c(this.value[n6]);
            ++n5;
            ++n6;
        }
    }

    public AsciiString subSequence(int n) {
        return this.subSequence(n, this.length());
    }

    @Override
    public AsciiString subSequence(int n, int n2) {
        return this.subSequence(n, n2, false);
    }

    public AsciiString subSequence(int n, int n2, boolean bl) {
        if (MathUtil.isOutOfBounds(n, n2 - n, this.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + n + ") <= end (" + n2 + ") <= length(" + this.length() + ')');
        }
        if (n == 0 && n2 == this.length()) {
            return this;
        }
        if (n2 == n) {
            return EMPTY_STRING;
        }
        return new AsciiString(this.value, n + this.offset, n2 - n, bl);
    }

    public int indexOf(CharSequence charSequence) {
        return this.indexOf(charSequence, 0);
    }

    public int indexOf(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        if (n < 0) {
            n = 0;
        }
        if (n2 <= 0) {
            return n < this.length ? n : this.length;
        }
        if (n2 > this.length - n) {
            return 1;
        }
        char c = charSequence.charAt(0);
        if (c > '\u00ff') {
            return 1;
        }
        byte by = AsciiString.c2b0(c);
        int n3 = this.offset + this.length - n2;
        for (int i = n + this.offset; i <= n3; ++i) {
            if (this.value[i] != by) continue;
            int n4 = i;
            int n5 = 0;
            while (++n5 < n2 && AsciiString.b2c(this.value[++n4]) == charSequence.charAt(n5)) {
            }
            if (n5 != n2) continue;
            return i - this.offset;
        }
        return 1;
    }

    public int indexOf(char c, int n) {
        if (c > '\u00ff') {
            return 1;
        }
        if (n < 0) {
            n = 0;
        }
        byte by = AsciiString.c2b0(c);
        int n2 = this.offset + n + this.length;
        for (int i = n + this.offset; i < n2; ++i) {
            if (this.value[i] != by) continue;
            return i - this.offset;
        }
        return 1;
    }

    public int lastIndexOf(CharSequence charSequence) {
        return this.lastIndexOf(charSequence, this.length());
    }

    public int lastIndexOf(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        if (n < 0) {
            n = 0;
        }
        if (n2 <= 0) {
            return n < this.length ? n : this.length;
        }
        if (n2 > this.length - n) {
            return 1;
        }
        char c = charSequence.charAt(0);
        if (c > '\u00ff') {
            return 1;
        }
        byte by = AsciiString.c2b0(c);
        int n3 = this.offset + n;
        for (int i = this.offset + this.length - n2; i >= n3; --i) {
            if (this.value[i] != by) continue;
            int n4 = i;
            int n5 = 0;
            while (++n5 < n2 && AsciiString.b2c(this.value[++n4]) == charSequence.charAt(n5)) {
            }
            if (n5 != n2) continue;
            return i - this.offset;
        }
        return 1;
    }

    public boolean regionMatches(int n, CharSequence charSequence, int n2, int n3) {
        if (charSequence == null) {
            throw new NullPointerException("string");
        }
        if (n2 < 0 || charSequence.length() - n2 < n3) {
            return true;
        }
        int n4 = this.length();
        if (n < 0 || n4 - n < n3) {
            return true;
        }
        if (n3 <= 0) {
            return false;
        }
        int n5 = n2 + n3;
        int n6 = n2;
        int n7 = n + this.arrayOffset();
        while (n6 < n5) {
            if (AsciiString.b2c(this.value[n7]) != charSequence.charAt(n6)) {
                return true;
            }
            ++n6;
            ++n7;
        }
        return false;
    }

    public boolean regionMatches(boolean bl, int n, CharSequence charSequence, int n2, int n3) {
        if (!bl) {
            return this.regionMatches(n, charSequence, n2, n3);
        }
        if (charSequence == null) {
            throw new NullPointerException("string");
        }
        int n4 = this.length();
        if (n < 0 || n3 > n4 - n) {
            return true;
        }
        if (n2 < 0 || n3 > charSequence.length() - n2) {
            return true;
        }
        int n5 = (n += this.arrayOffset()) + n3;
        while (n < n5) {
            if (AsciiString.equalsIgnoreCase(AsciiString.b2c(this.value[n++]), charSequence.charAt(n2++))) continue;
            return true;
        }
        return false;
    }

    public AsciiString replace(char c, char c2) {
        if (c > '\u00ff') {
            return this;
        }
        byte by = AsciiString.c2b0(c);
        byte by2 = AsciiString.c2b(c2);
        int n = this.offset + this.length;
        for (int i = this.offset; i < n; ++i) {
            if (this.value[i] != by) continue;
            byte[] byArray = new byte[this.length()];
            System.arraycopy(this.value, this.offset, byArray, 0, i - this.offset);
            byArray[i - this.offset] = by2;
            ++i;
            while (i < n) {
                byte by3 = this.value[i];
                byArray[i - this.offset] = by3 != by ? by3 : by2;
                ++i;
            }
            return new AsciiString(byArray, false);
        }
        return this;
    }

    public boolean startsWith(CharSequence charSequence) {
        return this.startsWith(charSequence, 1);
    }

    public boolean startsWith(CharSequence charSequence, int n) {
        return this.regionMatches(n, charSequence, 0, charSequence.length());
    }

    public AsciiString toLowerCase() {
        int n;
        boolean bl = true;
        int n2 = this.length() + this.arrayOffset();
        for (n = this.arrayOffset(); n < n2; ++n) {
            byte by = this.value[n];
            if (by < 65 || by > 90) continue;
            bl = false;
            break;
        }
        if (bl) {
            return this;
        }
        byte[] byArray = new byte[this.length()];
        n = 0;
        int n3 = this.arrayOffset();
        while (n < byArray.length) {
            byArray[n] = AsciiString.toLowerCase(this.value[n3]);
            ++n;
            ++n3;
        }
        return new AsciiString(byArray, false);
    }

    public AsciiString toUpperCase() {
        int n;
        boolean bl = true;
        int n2 = this.length() + this.arrayOffset();
        for (n = this.arrayOffset(); n < n2; ++n) {
            byte by = this.value[n];
            if (by < 97 || by > 122) continue;
            bl = false;
            break;
        }
        if (bl) {
            return this;
        }
        byte[] byArray = new byte[this.length()];
        n = 0;
        int n3 = this.arrayOffset();
        while (n < byArray.length) {
            byArray[n] = AsciiString.toUpperCase(this.value[n3]);
            ++n;
            ++n3;
        }
        return new AsciiString(byArray, false);
    }

    public static CharSequence trim(CharSequence charSequence) {
        int n;
        int n2;
        if (charSequence.getClass() == AsciiString.class) {
            return ((AsciiString)charSequence).trim();
        }
        if (charSequence instanceof String) {
            return ((String)charSequence).trim();
        }
        int n3 = n2 = charSequence.length() - 1;
        for (n = 0; n <= n3 && charSequence.charAt(n) <= ' '; ++n) {
        }
        while (n3 >= n && charSequence.charAt(n3) <= ' ') {
            --n3;
        }
        if (n == 0 && n3 == n2) {
            return charSequence;
        }
        return charSequence.subSequence(n, n3);
    }

    public AsciiString trim() {
        int n;
        int n2;
        int n3 = n2 = this.arrayOffset() + this.length() - 1;
        for (n = this.arrayOffset(); n <= n3 && this.value[n] <= 32; ++n) {
        }
        while (n3 >= n && this.value[n3] <= 32) {
            --n3;
        }
        if (n == 0 && n3 == n2) {
            return this;
        }
        return new AsciiString(this.value, n, n3 - n + 1, false);
    }

    public boolean contentEquals(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() != this.length()) {
            return true;
        }
        if (charSequence.getClass() == AsciiString.class) {
            return this.equals(charSequence);
        }
        int n = this.arrayOffset();
        for (int i = 0; i < charSequence.length(); ++i) {
            if (AsciiString.b2c(this.value[n]) != charSequence.charAt(i)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    public boolean matches(String string) {
        return Pattern.matches(string, this);
    }

    public AsciiString[] split(String string, int n) {
        return AsciiString.toAsciiStringArray(Pattern.compile(string).split(this, n));
    }

    public AsciiString[] split(char c) {
        int n;
        ArrayList<AsciiString> arrayList = InternalThreadLocalMap.get().arrayList();
        int n2 = 0;
        int n3 = this.length();
        for (n = n2; n < n3; ++n) {
            if (this.charAt(n) != c) continue;
            if (n2 == n) {
                arrayList.add(EMPTY_STRING);
            } else {
                arrayList.add(new AsciiString(this.value, n2 + this.arrayOffset(), n - n2, false));
            }
            n2 = n + 1;
        }
        if (n2 == 0) {
            arrayList.add(this);
        } else if (n2 != n3) {
            arrayList.add(new AsciiString(this.value, n2 + this.arrayOffset(), n3 - n2, false));
        } else {
            for (n = arrayList.size() - 1; n >= 0 && ((AsciiString)arrayList.get(n)).isEmpty(); --n) {
                arrayList.remove(n);
            }
        }
        return arrayList.toArray(new AsciiString[arrayList.size()]);
    }

    public int hashCode() {
        int n = this.hash;
        if (n == 0) {
            this.hash = n = PlatformDependent.hashCodeAscii(this.value, this.offset, this.length);
        }
        return n;
    }

    public boolean equals(Object object) {
        if (object == null || object.getClass() != AsciiString.class) {
            return true;
        }
        if (this == object) {
            return false;
        }
        AsciiString asciiString = (AsciiString)object;
        return this.length() == asciiString.length() && this.hashCode() == asciiString.hashCode() && PlatformDependent.equals(this.array(), this.arrayOffset(), asciiString.array(), asciiString.arrayOffset(), this.length());
    }

    @Override
    public String toString() {
        String string = this.string;
        if (string == null) {
            this.string = string = this.toString(0);
        }
        return string;
    }

    public String toString(int n) {
        return this.toString(n, this.length());
    }

    public String toString(int n, int n2) {
        int n3 = n2 - n;
        if (n3 == 0) {
            return "";
        }
        if (MathUtil.isOutOfBounds(n, n3, this.length())) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + n + ") <= srcIdx + length(" + n3 + ") <= srcLen(" + this.length() + ')');
        }
        String string = new String(this.value, 0, n + this.offset, n3);
        return string;
    }

    public boolean parseBoolean() {
        return this.length >= 1 && this.value[this.offset] != 0;
    }

    public char parseChar() {
        return this.parseChar(0);
    }

    public char parseChar(int n) {
        if (n + 1 >= this.length()) {
            throw new IndexOutOfBoundsException("2 bytes required to convert to character. index " + n + " would go out of bounds.");
        }
        int n2 = n + this.offset;
        return (char)(AsciiString.b2c(this.value[n2]) << 8 | AsciiString.b2c(this.value[n2 + 1]));
    }

    public short parseShort() {
        return this.parseShort(0, this.length(), 10);
    }

    public short parseShort(int n) {
        return this.parseShort(0, this.length(), n);
    }

    public short parseShort(int n, int n2) {
        return this.parseShort(n, n2, 10);
    }

    public short parseShort(int n, int n2, int n3) {
        int n4 = this.parseInt(n, n2, n3);
        short s = (short)n4;
        if (s != n4) {
            throw new NumberFormatException(this.subSequence(n, n2, true).toString());
        }
        return s;
    }

    public int parseInt() {
        return this.parseInt(0, this.length(), 10);
    }

    public int parseInt(int n) {
        return this.parseInt(0, this.length(), n);
    }

    public int parseInt(int n, int n2) {
        return this.parseInt(n, n2, 10);
    }

    public int parseInt(int n, int n2, int n3) {
        boolean bl;
        if (n3 < 2 || n3 > 36) {
            throw new NumberFormatException();
        }
        if (n == n2) {
            throw new NumberFormatException();
        }
        int n4 = n;
        boolean bl2 = bl = this.byteAt(n4) == 45;
        if (bl && ++n4 == n2) {
            throw new NumberFormatException(this.subSequence(n, n2, true).toString());
        }
        return this.parseInt(n4, n2, n3, bl);
    }

    private int parseInt(int n, int n2, int n3, boolean bl) {
        int n4 = Integer.MIN_VALUE / n3;
        int n5 = 0;
        int n6 = n;
        while (n6 < n2) {
            int n7;
            if ((n7 = Character.digit((char)(this.value[n6++ + this.offset] & 0xFF), n3)) == -1) {
                throw new NumberFormatException(this.subSequence(n, n2, true).toString());
            }
            if (n4 > n5) {
                throw new NumberFormatException(this.subSequence(n, n2, true).toString());
            }
            int n8 = n5 * n3 - n7;
            if (n8 > n5) {
                throw new NumberFormatException(this.subSequence(n, n2, true).toString());
            }
            n5 = n8;
        }
        if (!bl && (n5 = -n5) < 0) {
            throw new NumberFormatException(this.subSequence(n, n2, true).toString());
        }
        return n5;
    }

    public long parseLong() {
        return this.parseLong(0, this.length(), 10);
    }

    public long parseLong(int n) {
        return this.parseLong(0, this.length(), n);
    }

    public long parseLong(int n, int n2) {
        return this.parseLong(n, n2, 10);
    }

    public long parseLong(int n, int n2, int n3) {
        boolean bl;
        if (n3 < 2 || n3 > 36) {
            throw new NumberFormatException();
        }
        if (n == n2) {
            throw new NumberFormatException();
        }
        int n4 = n;
        boolean bl2 = bl = this.byteAt(n4) == 45;
        if (bl && ++n4 == n2) {
            throw new NumberFormatException(this.subSequence(n, n2, true).toString());
        }
        return this.parseLong(n4, n2, n3, bl);
    }

    private long parseLong(int n, int n2, int n3, boolean bl) {
        long l = Long.MIN_VALUE / (long)n3;
        long l2 = 0L;
        int n4 = n;
        while (n4 < n2) {
            int n5;
            if ((n5 = Character.digit((char)(this.value[n4++ + this.offset] & 0xFF), n3)) == -1) {
                throw new NumberFormatException(this.subSequence(n, n2, true).toString());
            }
            if (l > l2) {
                throw new NumberFormatException(this.subSequence(n, n2, true).toString());
            }
            long l3 = l2 * (long)n3 - (long)n5;
            if (l3 > l2) {
                throw new NumberFormatException(this.subSequence(n, n2, true).toString());
            }
            l2 = l3;
        }
        if (!bl && (l2 = -l2) < 0L) {
            throw new NumberFormatException(this.subSequence(n, n2, true).toString());
        }
        return l2;
    }

    public float parseFloat() {
        return this.parseFloat(0, this.length());
    }

    public float parseFloat(int n, int n2) {
        return Float.parseFloat(this.toString(n, n2));
    }

    public double parseDouble() {
        return this.parseDouble(0, this.length());
    }

    public double parseDouble(int n, int n2) {
        return Double.parseDouble(this.toString(n, n2));
    }

    public static AsciiString of(CharSequence charSequence) {
        return charSequence.getClass() == AsciiString.class ? (AsciiString)charSequence : new AsciiString(charSequence);
    }

    public static AsciiString cached(String string) {
        AsciiString asciiString = new AsciiString(string);
        asciiString.string = string;
        return asciiString;
    }

    public static int hashCode(CharSequence charSequence) {
        if (charSequence == null) {
            return 1;
        }
        if (charSequence.getClass() == AsciiString.class) {
            return charSequence.hashCode();
        }
        return PlatformDependent.hashCodeAscii(charSequence);
    }

    public static boolean contains(CharSequence charSequence, CharSequence charSequence2) {
        return AsciiString.contains(charSequence, charSequence2, DefaultCharEqualityComparator.INSTANCE);
    }

    public static boolean containsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        return AsciiString.contains(charSequence, charSequence2, AsciiCaseInsensitiveCharEqualityComparator.INSTANCE);
    }

    public static boolean contentEqualsIgnoreCase(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == charSequence2;
        }
        if (charSequence.getClass() == AsciiString.class) {
            return ((AsciiString)charSequence).contentEqualsIgnoreCase(charSequence2);
        }
        if (charSequence2.getClass() == AsciiString.class) {
            return ((AsciiString)charSequence2).contentEqualsIgnoreCase(charSequence);
        }
        if (charSequence.length() != charSequence2.length()) {
            return true;
        }
        int n = 0;
        int n2 = 0;
        while (n < charSequence.length()) {
            if (!AsciiString.equalsIgnoreCase(charSequence.charAt(n), charSequence2.charAt(n2))) {
                return true;
            }
            ++n;
            ++n2;
        }
        return false;
    }

    public static boolean containsContentEqualsIgnoreCase(Collection<CharSequence> collection, CharSequence charSequence) {
        for (CharSequence charSequence2 : collection) {
            if (!AsciiString.contentEqualsIgnoreCase(charSequence, charSequence2)) continue;
            return false;
        }
        return true;
    }

    public static boolean containsAllContentEqualsIgnoreCase(Collection<CharSequence> collection, Collection<CharSequence> collection2) {
        for (CharSequence charSequence : collection2) {
            if (AsciiString.containsContentEqualsIgnoreCase(collection, charSequence)) continue;
            return true;
        }
        return false;
    }

    public static boolean contentEquals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null || charSequence2 == null) {
            return charSequence == charSequence2;
        }
        if (charSequence.getClass() == AsciiString.class) {
            return ((AsciiString)charSequence).contentEquals(charSequence2);
        }
        if (charSequence2.getClass() == AsciiString.class) {
            return ((AsciiString)charSequence2).contentEquals(charSequence);
        }
        if (charSequence.length() != charSequence2.length()) {
            return true;
        }
        for (int i = 0; i < charSequence.length(); ++i) {
            if (charSequence.charAt(i) == charSequence2.charAt(i)) continue;
            return true;
        }
        return false;
    }

    private static AsciiString[] toAsciiStringArray(String[] stringArray) {
        AsciiString[] asciiStringArray = new AsciiString[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            asciiStringArray[i] = new AsciiString(stringArray[i]);
        }
        return asciiStringArray;
    }

    private static boolean contains(CharSequence charSequence, CharSequence charSequence2, CharEqualityComparator charEqualityComparator) {
        if (charSequence == null || charSequence2 == null || charSequence.length() < charSequence2.length()) {
            return true;
        }
        if (charSequence2.length() == 0) {
            return false;
        }
        int n = 0;
        for (int i = 0; i < charSequence.length(); ++i) {
            if (charEqualityComparator.equals(charSequence2.charAt(n), charSequence.charAt(i))) {
                if (++n != charSequence2.length()) continue;
                return false;
            }
            if (charSequence.length() - i < charSequence2.length()) {
                return true;
            }
            n = 0;
        }
        return true;
    }

    private static boolean regionMatchesCharSequences(CharSequence charSequence, int n, CharSequence charSequence2, int n2, int n3, CharEqualityComparator charEqualityComparator) {
        if (n < 0 || n3 > charSequence.length() - n) {
            return true;
        }
        if (n2 < 0 || n3 > charSequence2.length() - n2) {
            return true;
        }
        int n4 = n;
        int n5 = n4 + n3;
        int n6 = n2;
        while (n4 < n5) {
            char c;
            char c2;
            if (charEqualityComparator.equals(c2 = charSequence.charAt(n4++), c = charSequence2.charAt(n6++))) continue;
            return true;
        }
        return false;
    }

    public static boolean regionMatches(CharSequence charSequence, boolean bl, int n, CharSequence charSequence2, int n2, int n3) {
        if (charSequence == null || charSequence2 == null) {
            return true;
        }
        if (charSequence instanceof String && charSequence2 instanceof String) {
            return ((String)charSequence).regionMatches(bl, n, (String)charSequence2, n2, n3);
        }
        if (charSequence instanceof AsciiString) {
            return ((AsciiString)charSequence).regionMatches(bl, n, charSequence2, n2, n3);
        }
        return AsciiString.regionMatchesCharSequences(charSequence, n, charSequence2, n2, n3, bl ? GeneralCaseInsensitiveCharEqualityComparator.INSTANCE : DefaultCharEqualityComparator.INSTANCE);
    }

    public static boolean regionMatchesAscii(CharSequence charSequence, boolean bl, int n, CharSequence charSequence2, int n2, int n3) {
        if (charSequence == null || charSequence2 == null) {
            return true;
        }
        if (!bl && charSequence instanceof String && charSequence2 instanceof String) {
            return ((String)charSequence).regionMatches(false, n, (String)charSequence2, n2, n3);
        }
        if (charSequence instanceof AsciiString) {
            return ((AsciiString)charSequence).regionMatches(bl, n, charSequence2, n2, n3);
        }
        return AsciiString.regionMatchesCharSequences(charSequence, n, charSequence2, n2, n3, bl ? AsciiCaseInsensitiveCharEqualityComparator.INSTANCE : DefaultCharEqualityComparator.INSTANCE);
    }

    public static int indexOfIgnoreCase(CharSequence charSequence, CharSequence charSequence2, int n) {
        if (charSequence == null || charSequence2 == null) {
            return 1;
        }
        if (n < 0) {
            n = 0;
        }
        int n2 = charSequence2.length();
        int n3 = charSequence.length() - n2 + 1;
        if (n > n3) {
            return 1;
        }
        if (n2 == 0) {
            return n;
        }
        for (int i = n; i < n3; ++i) {
            if (!AsciiString.regionMatches(charSequence, true, i, charSequence2, 0, n2)) continue;
            return i;
        }
        return 1;
    }

    public static int indexOfIgnoreCaseAscii(CharSequence charSequence, CharSequence charSequence2, int n) {
        if (charSequence == null || charSequence2 == null) {
            return 1;
        }
        if (n < 0) {
            n = 0;
        }
        int n2 = charSequence2.length();
        int n3 = charSequence.length() - n2 + 1;
        if (n > n3) {
            return 1;
        }
        if (n2 == 0) {
            return n;
        }
        for (int i = n; i < n3; ++i) {
            if (!AsciiString.regionMatchesAscii(charSequence, true, i, charSequence2, 0, n2)) continue;
            return i;
        }
        return 1;
    }

    public static int indexOf(CharSequence charSequence, char c, int n) {
        int n2;
        if (charSequence instanceof String) {
            return ((String)charSequence).indexOf(c, n);
        }
        if (charSequence instanceof AsciiString) {
            return ((AsciiString)charSequence).indexOf(c, n);
        }
        if (charSequence == null) {
            return 1;
        }
        int n3 = charSequence.length();
        int n4 = n2 = n < 0 ? 0 : n;
        while (n2 < n3) {
            if (charSequence.charAt(n2) == c) {
                return n2;
            }
            ++n2;
        }
        return 1;
    }

    private static boolean equalsIgnoreCase(byte by, byte by2) {
        return by == by2 || AsciiString.toLowerCase(by) == AsciiString.toLowerCase(by2);
    }

    private static boolean equalsIgnoreCase(char c, char c2) {
        return c == c2 || AsciiString.toLowerCase(c) == AsciiString.toLowerCase(c2);
    }

    private static byte toLowerCase(byte by) {
        return AsciiString.isUpperCase(by) ? (byte)(by + 32) : by;
    }

    private static char toLowerCase(char c) {
        return AsciiString.isUpperCase(c) ? (char)(c + 32) : c;
    }

    private static byte toUpperCase(byte by) {
        return AsciiString.isLowerCase(by) ? (byte)(by - 32) : by;
    }

    private static boolean isLowerCase(byte by) {
        return by >= 97 && by <= 122;
    }

    public static boolean isUpperCase(byte by) {
        return by >= 65 && by <= 90;
    }

    public static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    public static byte c2b(char c) {
        return (byte)(c > '\u00ff' ? 63 : (int)c);
    }

    private static byte c2b0(char c) {
        return (byte)c;
    }

    public static char b2c(byte by) {
        return (char)(by & 0xFF);
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return this.subSequence(n, n2);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((CharSequence)object);
    }

    static boolean access$000(char c, char c2) {
        return AsciiString.equalsIgnoreCase(c, c2);
    }

    private static final class GeneralCaseInsensitiveCharEqualityComparator
    implements CharEqualityComparator {
        static final GeneralCaseInsensitiveCharEqualityComparator INSTANCE = new GeneralCaseInsensitiveCharEqualityComparator();

        private GeneralCaseInsensitiveCharEqualityComparator() {
        }

        @Override
        public boolean equals(char c, char c2) {
            return Character.toUpperCase(c) == Character.toUpperCase(c2) || Character.toLowerCase(c) == Character.toLowerCase(c2);
        }
    }

    private static final class AsciiCaseInsensitiveCharEqualityComparator
    implements CharEqualityComparator {
        static final AsciiCaseInsensitiveCharEqualityComparator INSTANCE = new AsciiCaseInsensitiveCharEqualityComparator();

        private AsciiCaseInsensitiveCharEqualityComparator() {
        }

        @Override
        public boolean equals(char c, char c2) {
            return AsciiString.access$000(c, c2);
        }
    }

    private static final class DefaultCharEqualityComparator
    implements CharEqualityComparator {
        static final DefaultCharEqualityComparator INSTANCE = new DefaultCharEqualityComparator();

        private DefaultCharEqualityComparator() {
        }

        @Override
        public boolean equals(char c, char c2) {
            return c == c2;
        }
    }

    private static interface CharEqualityComparator {
        public boolean equals(char var1, char var2);
    }
}

