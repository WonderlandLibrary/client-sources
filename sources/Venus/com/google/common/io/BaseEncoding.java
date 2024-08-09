/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.math.IntMath;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.RoundingMode;
import java.util.Arrays;
import javax.annotation.Nullable;

@GwtCompatible(emulated=true)
public abstract class BaseEncoding {
    private static final BaseEncoding BASE64 = new Base64Encoding("base64()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", Character.valueOf('='));
    private static final BaseEncoding BASE64_URL = new Base64Encoding("base64Url()", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_", Character.valueOf('='));
    private static final BaseEncoding BASE32 = new StandardBaseEncoding("base32()", "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", Character.valueOf('='));
    private static final BaseEncoding BASE32_HEX = new StandardBaseEncoding("base32Hex()", "0123456789ABCDEFGHIJKLMNOPQRSTUV", Character.valueOf('='));
    private static final BaseEncoding BASE16 = new Base16Encoding("base16()", "0123456789ABCDEF");

    BaseEncoding() {
    }

    public String encode(byte[] byArray) {
        return this.encode(byArray, 0, byArray.length);
    }

    public final String encode(byte[] byArray, int n, int n2) {
        Preconditions.checkPositionIndexes(n, n + n2, byArray.length);
        StringBuilder stringBuilder = new StringBuilder(this.maxEncodedSize(n2));
        try {
            this.encodeTo(stringBuilder, byArray, n, n2);
        } catch (IOException iOException) {
            throw new AssertionError((Object)iOException);
        }
        return stringBuilder.toString();
    }

    @GwtIncompatible
    public abstract OutputStream encodingStream(Writer var1);

    @GwtIncompatible
    public final ByteSink encodingSink(CharSink charSink) {
        Preconditions.checkNotNull(charSink);
        return new ByteSink(this, charSink){
            final CharSink val$encodedSink;
            final BaseEncoding this$0;
            {
                this.this$0 = baseEncoding;
                this.val$encodedSink = charSink;
            }

            @Override
            public OutputStream openStream() throws IOException {
                return this.this$0.encodingStream(this.val$encodedSink.openStream());
            }
        };
    }

    private static byte[] extract(byte[] byArray, int n) {
        if (n == byArray.length) {
            return byArray;
        }
        byte[] byArray2 = new byte[n];
        System.arraycopy(byArray, 0, byArray2, 0, n);
        return byArray2;
    }

    public abstract boolean canDecode(CharSequence var1);

    public final byte[] decode(CharSequence charSequence) {
        try {
            return this.decodeChecked(charSequence);
        } catch (DecodingException decodingException) {
            throw new IllegalArgumentException(decodingException);
        }
    }

    final byte[] decodeChecked(CharSequence charSequence) throws DecodingException {
        charSequence = this.padding().trimTrailingFrom(charSequence);
        byte[] byArray = new byte[this.maxDecodedSize(charSequence.length())];
        int n = this.decodeTo(byArray, charSequence);
        return BaseEncoding.extract(byArray, n);
    }

    @GwtIncompatible
    public abstract InputStream decodingStream(Reader var1);

    @GwtIncompatible
    public final ByteSource decodingSource(CharSource charSource) {
        Preconditions.checkNotNull(charSource);
        return new ByteSource(this, charSource){
            final CharSource val$encodedSource;
            final BaseEncoding this$0;
            {
                this.this$0 = baseEncoding;
                this.val$encodedSource = charSource;
            }

            @Override
            public InputStream openStream() throws IOException {
                return this.this$0.decodingStream(this.val$encodedSource.openStream());
            }
        };
    }

    abstract int maxEncodedSize(int var1);

    abstract void encodeTo(Appendable var1, byte[] var2, int var3, int var4) throws IOException;

    abstract int maxDecodedSize(int var1);

    abstract int decodeTo(byte[] var1, CharSequence var2) throws DecodingException;

    abstract CharMatcher padding();

    public abstract BaseEncoding omitPadding();

    public abstract BaseEncoding withPadChar(char var1);

    public abstract BaseEncoding withSeparator(String var1, int var2);

    public abstract BaseEncoding upperCase();

    public abstract BaseEncoding lowerCase();

    public static BaseEncoding base64() {
        return BASE64;
    }

    public static BaseEncoding base64Url() {
        return BASE64_URL;
    }

    public static BaseEncoding base32() {
        return BASE32;
    }

    public static BaseEncoding base32Hex() {
        return BASE32_HEX;
    }

    public static BaseEncoding base16() {
        return BASE16;
    }

    @GwtIncompatible
    static Reader ignoringReader(Reader reader, CharMatcher charMatcher) {
        Preconditions.checkNotNull(reader);
        Preconditions.checkNotNull(charMatcher);
        return new Reader(reader, charMatcher){
            final Reader val$delegate;
            final CharMatcher val$toIgnore;
            {
                this.val$delegate = reader;
                this.val$toIgnore = charMatcher;
            }

            @Override
            public int read() throws IOException {
                int n;
                while ((n = this.val$delegate.read()) != -1 && this.val$toIgnore.matches((char)n)) {
                }
                return n;
            }

            @Override
            public int read(char[] cArray, int n, int n2) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void close() throws IOException {
                this.val$delegate.close();
            }
        };
    }

    static Appendable separatingAppendable(Appendable appendable, String string, int n) {
        Preconditions.checkNotNull(appendable);
        Preconditions.checkNotNull(string);
        Preconditions.checkArgument(n > 0);
        return new Appendable(n, appendable, string){
            int charsUntilSeparator;
            final int val$afterEveryChars;
            final Appendable val$delegate;
            final String val$separator;
            {
                this.val$afterEveryChars = n;
                this.val$delegate = appendable;
                this.val$separator = string;
                this.charsUntilSeparator = this.val$afterEveryChars;
            }

            @Override
            public Appendable append(char c) throws IOException {
                if (this.charsUntilSeparator == 0) {
                    this.val$delegate.append(this.val$separator);
                    this.charsUntilSeparator = this.val$afterEveryChars;
                }
                this.val$delegate.append(c);
                --this.charsUntilSeparator;
                return this;
            }

            @Override
            public Appendable append(CharSequence charSequence, int n, int n2) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public Appendable append(CharSequence charSequence) throws IOException {
                throw new UnsupportedOperationException();
            }
        };
    }

    @GwtIncompatible
    static Writer separatingWriter(Writer writer, String string, int n) {
        Appendable appendable = BaseEncoding.separatingAppendable(writer, string, n);
        return new Writer(appendable, writer){
            final Appendable val$seperatingAppendable;
            final Writer val$delegate;
            {
                this.val$seperatingAppendable = appendable;
                this.val$delegate = writer;
            }

            @Override
            public void write(int n) throws IOException {
                this.val$seperatingAppendable.append((char)n);
            }

            @Override
            public void write(char[] cArray, int n, int n2) throws IOException {
                throw new UnsupportedOperationException();
            }

            @Override
            public void flush() throws IOException {
                this.val$delegate.flush();
            }

            @Override
            public void close() throws IOException {
                this.val$delegate.close();
            }
        };
    }

    static final class SeparatedBaseEncoding
    extends BaseEncoding {
        private final BaseEncoding delegate;
        private final String separator;
        private final int afterEveryChars;
        private final CharMatcher separatorChars;

        SeparatedBaseEncoding(BaseEncoding baseEncoding, String string, int n) {
            this.delegate = Preconditions.checkNotNull(baseEncoding);
            this.separator = Preconditions.checkNotNull(string);
            this.afterEveryChars = n;
            Preconditions.checkArgument(n > 0, "Cannot add a separator after every %s chars", n);
            this.separatorChars = CharMatcher.anyOf(string).precomputed();
        }

        @Override
        CharMatcher padding() {
            return this.delegate.padding();
        }

        @Override
        int maxEncodedSize(int n) {
            int n2 = this.delegate.maxEncodedSize(n);
            return n2 + this.separator.length() * IntMath.divide(Math.max(0, n2 - 1), this.afterEveryChars, RoundingMode.FLOOR);
        }

        @Override
        @GwtIncompatible
        public OutputStream encodingStream(Writer writer) {
            return this.delegate.encodingStream(SeparatedBaseEncoding.separatingWriter(writer, this.separator, this.afterEveryChars));
        }

        @Override
        void encodeTo(Appendable appendable, byte[] byArray, int n, int n2) throws IOException {
            this.delegate.encodeTo(SeparatedBaseEncoding.separatingAppendable(appendable, this.separator, this.afterEveryChars), byArray, n, n2);
        }

        @Override
        int maxDecodedSize(int n) {
            return this.delegate.maxDecodedSize(n);
        }

        @Override
        public boolean canDecode(CharSequence charSequence) {
            return this.delegate.canDecode(this.separatorChars.removeFrom(charSequence));
        }

        @Override
        int decodeTo(byte[] byArray, CharSequence charSequence) throws DecodingException {
            return this.delegate.decodeTo(byArray, this.separatorChars.removeFrom(charSequence));
        }

        @Override
        @GwtIncompatible
        public InputStream decodingStream(Reader reader) {
            return this.delegate.decodingStream(SeparatedBaseEncoding.ignoringReader(reader, this.separatorChars));
        }

        @Override
        public BaseEncoding omitPadding() {
            return this.delegate.omitPadding().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override
        public BaseEncoding withPadChar(char c) {
            return this.delegate.withPadChar(c).withSeparator(this.separator, this.afterEveryChars);
        }

        @Override
        public BaseEncoding withSeparator(String string, int n) {
            throw new UnsupportedOperationException("Already have a separator");
        }

        @Override
        public BaseEncoding upperCase() {
            return this.delegate.upperCase().withSeparator(this.separator, this.afterEveryChars);
        }

        @Override
        public BaseEncoding lowerCase() {
            return this.delegate.lowerCase().withSeparator(this.separator, this.afterEveryChars);
        }

        public String toString() {
            return this.delegate + ".withSeparator(\"" + this.separator + "\", " + this.afterEveryChars + ")";
        }
    }

    static final class Base64Encoding
    extends StandardBaseEncoding {
        Base64Encoding(String string, String string2, @Nullable Character c) {
            this(new Alphabet(string, string2.toCharArray()), c);
        }

        private Base64Encoding(Alphabet alphabet, @Nullable Character c) {
            super(alphabet, c);
            Preconditions.checkArgument(Alphabet.access$000(alphabet).length == 64);
        }

        @Override
        void encodeTo(Appendable appendable, byte[] byArray, int n, int n2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(n, n + n2, byArray.length);
            int n3 = n;
            for (int i = n2; i >= 3; i -= 3) {
                int n4 = (byArray[n3++] & 0xFF) << 16 | (byArray[n3++] & 0xFF) << 8 | byArray[n3++] & 0xFF;
                appendable.append(this.alphabet.encode(n4 >>> 18));
                appendable.append(this.alphabet.encode(n4 >>> 12 & 0x3F));
                appendable.append(this.alphabet.encode(n4 >>> 6 & 0x3F));
                appendable.append(this.alphabet.encode(n4 & 0x3F));
            }
            if (n3 < n + n2) {
                this.encodeChunkTo(appendable, byArray, n3, n + n2 - n3);
            }
        }

        @Override
        int decodeTo(byte[] byArray, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(byArray);
            charSequence = this.padding().trimTrailingFrom(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequence.length())) {
                throw new DecodingException("Invalid input length " + charSequence.length());
            }
            int n = 0;
            int n2 = 0;
            while (n2 < charSequence.length()) {
                int n3 = this.alphabet.decode(charSequence.charAt(n2++)) << 18;
                byArray[n++] = (byte)((n3 |= this.alphabet.decode(charSequence.charAt(n2++)) << 12) >>> 16);
                if (n2 >= charSequence.length()) continue;
                byArray[n++] = (byte)((n3 |= this.alphabet.decode(charSequence.charAt(n2++)) << 6) >>> 8 & 0xFF);
                if (n2 >= charSequence.length()) continue;
                byArray[n++] = (byte)((n3 |= this.alphabet.decode(charSequence.charAt(n2++))) & 0xFF);
            }
            return n;
        }

        @Override
        BaseEncoding newInstance(Alphabet alphabet, @Nullable Character c) {
            return new Base64Encoding(alphabet, c);
        }
    }

    static final class Base16Encoding
    extends StandardBaseEncoding {
        final char[] encoding = new char[512];

        Base16Encoding(String string, String string2) {
            this(new Alphabet(string, string2.toCharArray()));
        }

        private Base16Encoding(Alphabet alphabet) {
            super(alphabet, null);
            Preconditions.checkArgument(Alphabet.access$000(alphabet).length == 16);
            for (int i = 0; i < 256; ++i) {
                this.encoding[i] = alphabet.encode(i >>> 4);
                this.encoding[i | 0x100] = alphabet.encode(i & 0xF);
            }
        }

        @Override
        void encodeTo(Appendable appendable, byte[] byArray, int n, int n2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(n, n + n2, byArray.length);
            for (int i = 0; i < n2; ++i) {
                int n3 = byArray[n + i] & 0xFF;
                appendable.append(this.encoding[n3]);
                appendable.append(this.encoding[n3 | 0x100]);
            }
        }

        @Override
        int decodeTo(byte[] byArray, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(byArray);
            if (charSequence.length() % 2 == 1) {
                throw new DecodingException("Invalid input length " + charSequence.length());
            }
            int n = 0;
            for (int i = 0; i < charSequence.length(); i += 2) {
                int n2 = this.alphabet.decode(charSequence.charAt(i)) << 4 | this.alphabet.decode(charSequence.charAt(i + 1));
                byArray[n++] = (byte)n2;
            }
            return n;
        }

        @Override
        BaseEncoding newInstance(Alphabet alphabet, @Nullable Character c) {
            return new Base16Encoding(alphabet);
        }
    }

    static class StandardBaseEncoding
    extends BaseEncoding {
        final Alphabet alphabet;
        @Nullable
        final Character paddingChar;
        private transient BaseEncoding upperCase;
        private transient BaseEncoding lowerCase;

        StandardBaseEncoding(String string, String string2, @Nullable Character c) {
            this(new Alphabet(string, string2.toCharArray()), c);
        }

        StandardBaseEncoding(Alphabet alphabet, @Nullable Character c) {
            this.alphabet = Preconditions.checkNotNull(alphabet);
            Preconditions.checkArgument(c == null || !alphabet.matches(c.charValue()), "Padding character %s was already in alphabet", (Object)c);
            this.paddingChar = c;
        }

        @Override
        CharMatcher padding() {
            return this.paddingChar == null ? CharMatcher.none() : CharMatcher.is(this.paddingChar.charValue());
        }

        @Override
        int maxEncodedSize(int n) {
            return this.alphabet.charsPerChunk * IntMath.divide(n, this.alphabet.bytesPerChunk, RoundingMode.CEILING);
        }

        @Override
        @GwtIncompatible
        public OutputStream encodingStream(Writer writer) {
            Preconditions.checkNotNull(writer);
            return new OutputStream(this, writer){
                int bitBuffer;
                int bitBufferLength;
                int writtenChars;
                final Writer val$out;
                final StandardBaseEncoding this$0;
                {
                    this.this$0 = standardBaseEncoding;
                    this.val$out = writer;
                    this.bitBuffer = 0;
                    this.bitBufferLength = 0;
                    this.writtenChars = 0;
                }

                @Override
                public void write(int n) throws IOException {
                    this.bitBuffer <<= 8;
                    this.bitBuffer |= n & 0xFF;
                    this.bitBufferLength += 8;
                    while (this.bitBufferLength >= this.this$0.alphabet.bitsPerChar) {
                        int n2 = this.bitBuffer >> this.bitBufferLength - this.this$0.alphabet.bitsPerChar & this.this$0.alphabet.mask;
                        this.val$out.write(this.this$0.alphabet.encode(n2));
                        ++this.writtenChars;
                        this.bitBufferLength -= this.this$0.alphabet.bitsPerChar;
                    }
                }

                @Override
                public void flush() throws IOException {
                    this.val$out.flush();
                }

                @Override
                public void close() throws IOException {
                    if (this.bitBufferLength > 0) {
                        int n = this.bitBuffer << this.this$0.alphabet.bitsPerChar - this.bitBufferLength & this.this$0.alphabet.mask;
                        this.val$out.write(this.this$0.alphabet.encode(n));
                        ++this.writtenChars;
                        if (this.this$0.paddingChar != null) {
                            while (this.writtenChars % this.this$0.alphabet.charsPerChunk != 0) {
                                this.val$out.write(this.this$0.paddingChar.charValue());
                                ++this.writtenChars;
                            }
                        }
                    }
                    this.val$out.close();
                }
            };
        }

        @Override
        void encodeTo(Appendable appendable, byte[] byArray, int n, int n2) throws IOException {
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(n, n + n2, byArray.length);
            for (int i = 0; i < n2; i += this.alphabet.bytesPerChunk) {
                this.encodeChunkTo(appendable, byArray, n + i, Math.min(this.alphabet.bytesPerChunk, n2 - i));
            }
        }

        void encodeChunkTo(Appendable appendable, byte[] byArray, int n, int n2) throws IOException {
            int n3;
            int n4;
            Preconditions.checkNotNull(appendable);
            Preconditions.checkPositionIndexes(n, n + n2, byArray.length);
            Preconditions.checkArgument(n2 <= this.alphabet.bytesPerChunk);
            long l = 0L;
            for (n4 = 0; n4 < n2; ++n4) {
                l |= (long)(byArray[n + n4] & 0xFF);
                l <<= 8;
            }
            n4 = (n2 + 1) * 8 - this.alphabet.bitsPerChar;
            for (n3 = 0; n3 < n2 * 8; n3 += this.alphabet.bitsPerChar) {
                int n5 = (int)(l >>> n4 - n3) & this.alphabet.mask;
                appendable.append(this.alphabet.encode(n5));
            }
            if (this.paddingChar != null) {
                while (n3 < this.alphabet.bytesPerChunk * 8) {
                    appendable.append(this.paddingChar.charValue());
                    n3 += this.alphabet.bitsPerChar;
                }
            }
        }

        @Override
        int maxDecodedSize(int n) {
            return (int)(((long)this.alphabet.bitsPerChar * (long)n + 7L) / 8L);
        }

        @Override
        public boolean canDecode(CharSequence charSequence) {
            charSequence = this.padding().trimTrailingFrom(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequence.length())) {
                return true;
            }
            for (int i = 0; i < charSequence.length(); ++i) {
                if (this.alphabet.canDecode(charSequence.charAt(i))) continue;
                return true;
            }
            return false;
        }

        @Override
        int decodeTo(byte[] byArray, CharSequence charSequence) throws DecodingException {
            Preconditions.checkNotNull(byArray);
            charSequence = this.padding().trimTrailingFrom(charSequence);
            if (!this.alphabet.isValidPaddingStartPosition(charSequence.length())) {
                throw new DecodingException("Invalid input length " + charSequence.length());
            }
            int n = 0;
            for (int i = 0; i < charSequence.length(); i += this.alphabet.charsPerChunk) {
                int n2;
                long l = 0L;
                int n3 = 0;
                for (n2 = 0; n2 < this.alphabet.charsPerChunk; ++n2) {
                    l <<= this.alphabet.bitsPerChar;
                    if (i + n2 >= charSequence.length()) continue;
                    l |= (long)this.alphabet.decode(charSequence.charAt(i + n3++));
                }
                n2 = this.alphabet.bytesPerChunk * 8 - n3 * this.alphabet.bitsPerChar;
                for (int j = (this.alphabet.bytesPerChunk - 1) * 8; j >= n2; j -= 8) {
                    byArray[n++] = (byte)(l >>> j & 0xFFL);
                }
            }
            return n;
        }

        @Override
        @GwtIncompatible
        public InputStream decodingStream(Reader reader) {
            Preconditions.checkNotNull(reader);
            return new InputStream(this, reader){
                int bitBuffer;
                int bitBufferLength;
                int readChars;
                boolean hitPadding;
                final CharMatcher paddingMatcher;
                final Reader val$reader;
                final StandardBaseEncoding this$0;
                {
                    this.this$0 = standardBaseEncoding;
                    this.val$reader = reader;
                    this.bitBuffer = 0;
                    this.bitBufferLength = 0;
                    this.readChars = 0;
                    this.hitPadding = false;
                    this.paddingMatcher = this.this$0.padding();
                }

                @Override
                public int read() throws IOException {
                    while (true) {
                        int n;
                        if ((n = this.val$reader.read()) == -1) {
                            if (!this.hitPadding && !this.this$0.alphabet.isValidPaddingStartPosition(this.readChars)) {
                                throw new DecodingException("Invalid input length " + this.readChars);
                            }
                            return 1;
                        }
                        ++this.readChars;
                        char c = (char)n;
                        if (this.paddingMatcher.matches(c)) {
                            if (!(this.hitPadding || this.readChars != 1 && this.this$0.alphabet.isValidPaddingStartPosition(this.readChars - 1))) {
                                throw new DecodingException("Padding cannot start at index " + this.readChars);
                            }
                            this.hitPadding = true;
                            continue;
                        }
                        if (this.hitPadding) {
                            throw new DecodingException("Expected padding character but found '" + c + "' at index " + this.readChars);
                        }
                        this.bitBuffer <<= this.this$0.alphabet.bitsPerChar;
                        this.bitBuffer |= this.this$0.alphabet.decode(c);
                        this.bitBufferLength += this.this$0.alphabet.bitsPerChar;
                        if (this.bitBufferLength >= 8) break;
                    }
                    this.bitBufferLength -= 8;
                    return this.bitBuffer >> this.bitBufferLength & 0xFF;
                }

                @Override
                public void close() throws IOException {
                    this.val$reader.close();
                }
            };
        }

        @Override
        public BaseEncoding omitPadding() {
            return this.paddingChar == null ? this : this.newInstance(this.alphabet, null);
        }

        @Override
        public BaseEncoding withPadChar(char c) {
            if (8 % this.alphabet.bitsPerChar == 0 || this.paddingChar != null && this.paddingChar.charValue() == c) {
                return this;
            }
            return this.newInstance(this.alphabet, Character.valueOf(c));
        }

        @Override
        public BaseEncoding withSeparator(String string, int n) {
            Preconditions.checkArgument(this.padding().or(this.alphabet).matchesNoneOf(string), "Separator (%s) cannot contain alphabet or padding characters", (Object)string);
            return new SeparatedBaseEncoding(this, string, n);
        }

        @Override
        public BaseEncoding upperCase() {
            BaseEncoding baseEncoding = this.upperCase;
            if (baseEncoding == null) {
                Alphabet alphabet = this.alphabet.upperCase();
                this.upperCase = alphabet == this.alphabet ? this : this.newInstance(alphabet, this.paddingChar);
                baseEncoding = this.upperCase;
            }
            return baseEncoding;
        }

        @Override
        public BaseEncoding lowerCase() {
            BaseEncoding baseEncoding = this.lowerCase;
            if (baseEncoding == null) {
                Alphabet alphabet = this.alphabet.lowerCase();
                this.lowerCase = alphabet == this.alphabet ? this : this.newInstance(alphabet, this.paddingChar);
                baseEncoding = this.lowerCase;
            }
            return baseEncoding;
        }

        BaseEncoding newInstance(Alphabet alphabet, @Nullable Character c) {
            return new StandardBaseEncoding(alphabet, c);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("BaseEncoding.");
            stringBuilder.append(this.alphabet.toString());
            if (8 % this.alphabet.bitsPerChar != 0) {
                if (this.paddingChar == null) {
                    stringBuilder.append(".omitPadding()");
                } else {
                    stringBuilder.append(".withPadChar('").append(this.paddingChar).append("')");
                }
            }
            return stringBuilder.toString();
        }

        public boolean equals(@Nullable Object object) {
            if (object instanceof StandardBaseEncoding) {
                StandardBaseEncoding standardBaseEncoding = (StandardBaseEncoding)object;
                return this.alphabet.equals(standardBaseEncoding.alphabet) && Objects.equal(this.paddingChar, standardBaseEncoding.paddingChar);
            }
            return true;
        }

        public int hashCode() {
            return this.alphabet.hashCode() ^ Objects.hashCode(this.paddingChar);
        }
    }

    private static final class Alphabet
    extends CharMatcher {
        private final String name;
        private final char[] chars;
        final int mask;
        final int bitsPerChar;
        final int charsPerChunk;
        final int bytesPerChunk;
        private final byte[] decodabet;
        private final boolean[] validPadding;

        Alphabet(String string, char[] cArray) {
            int n;
            this.name = Preconditions.checkNotNull(string);
            this.chars = Preconditions.checkNotNull(cArray);
            try {
                this.bitsPerChar = IntMath.log2(cArray.length, RoundingMode.UNNECESSARY);
            } catch (ArithmeticException arithmeticException) {
                throw new IllegalArgumentException("Illegal alphabet length " + cArray.length, arithmeticException);
            }
            int n2 = Math.min(8, Integer.lowestOneBit(this.bitsPerChar));
            try {
                this.charsPerChunk = 8 / n2;
                this.bytesPerChunk = this.bitsPerChar / n2;
            } catch (ArithmeticException arithmeticException) {
                throw new IllegalArgumentException("Illegal alphabet " + new String(cArray), arithmeticException);
            }
            this.mask = cArray.length - 1;
            byte[] byArray = new byte[128];
            Arrays.fill(byArray, (byte)-1);
            for (int i = 0; i < cArray.length; ++i) {
                n = cArray[i];
                Preconditions.checkArgument(CharMatcher.ascii().matches((char)n), "Non-ASCII character: %s", (char)n);
                Preconditions.checkArgument(byArray[n] == -1, "Duplicate character: %s", (char)n);
                byArray[n] = (byte)i;
            }
            this.decodabet = byArray;
            boolean[] blArray = new boolean[this.charsPerChunk];
            for (n = 0; n < this.bytesPerChunk; ++n) {
                blArray[IntMath.divide((int)(n * 8), (int)this.bitsPerChar, (RoundingMode)RoundingMode.CEILING)] = true;
            }
            this.validPadding = blArray;
        }

        char encode(int n) {
            return this.chars[n];
        }

        boolean isValidPaddingStartPosition(int n) {
            return this.validPadding[n % this.charsPerChunk];
        }

        boolean canDecode(char c) {
            return c <= '\u007f' && this.decodabet[c] != -1;
        }

        int decode(char c) throws DecodingException {
            if (c > '\u007f' || this.decodabet[c] == -1) {
                throw new DecodingException("Unrecognized character: " + (CharMatcher.invisible().matches(c) ? "0x" + Integer.toHexString(c) : Character.valueOf(c)));
            }
            return this.decodabet[c];
        }

        private boolean hasLowerCase() {
            for (char c : this.chars) {
                if (!Ascii.isLowerCase(c)) continue;
                return false;
            }
            return true;
        }

        private boolean hasUpperCase() {
            for (char c : this.chars) {
                if (!Ascii.isUpperCase(c)) continue;
                return false;
            }
            return true;
        }

        Alphabet upperCase() {
            if (!this.hasLowerCase()) {
                return this;
            }
            Preconditions.checkState(!this.hasUpperCase(), "Cannot call upperCase() on a mixed-case alphabet");
            char[] cArray = new char[this.chars.length];
            for (int i = 0; i < this.chars.length; ++i) {
                cArray[i] = Ascii.toUpperCase(this.chars[i]);
            }
            return new Alphabet(this.name + ".upperCase()", cArray);
        }

        Alphabet lowerCase() {
            if (!this.hasUpperCase()) {
                return this;
            }
            Preconditions.checkState(!this.hasLowerCase(), "Cannot call lowerCase() on a mixed-case alphabet");
            char[] cArray = new char[this.chars.length];
            for (int i = 0; i < this.chars.length; ++i) {
                cArray[i] = Ascii.toLowerCase(this.chars[i]);
            }
            return new Alphabet(this.name + ".lowerCase()", cArray);
        }

        @Override
        public boolean matches(char c) {
            return CharMatcher.ascii().matches(c) && this.decodabet[c] != -1;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public boolean equals(@Nullable Object object) {
            if (object instanceof Alphabet) {
                Alphabet alphabet = (Alphabet)object;
                return Arrays.equals(this.chars, alphabet.chars);
            }
            return true;
        }

        public int hashCode() {
            return Arrays.hashCode(this.chars);
        }

        static char[] access$000(Alphabet alphabet) {
            return alphabet.chars;
        }
    }

    public static final class DecodingException
    extends IOException {
        DecodingException(String string) {
            super(string);
        }

        DecodingException(Throwable throwable) {
            super(throwable);
        }
    }
}

