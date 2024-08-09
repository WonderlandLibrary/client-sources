/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.io.AppendableWriter;
import com.google.common.io.LineProcessor;
import com.google.common.io.LineReader;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

@Beta
@GwtIncompatible
public final class CharStreams {
    static CharBuffer createBuffer() {
        return CharBuffer.allocate(2048);
    }

    private CharStreams() {
    }

    @CanIgnoreReturnValue
    public static long copy(Readable readable, Appendable appendable) throws IOException {
        Preconditions.checkNotNull(readable);
        Preconditions.checkNotNull(appendable);
        CharBuffer charBuffer = CharStreams.createBuffer();
        long l = 0L;
        while (readable.read(charBuffer) != -1) {
            charBuffer.flip();
            appendable.append(charBuffer);
            l += (long)charBuffer.remaining();
            charBuffer.clear();
        }
        return l;
    }

    public static String toString(Readable readable) throws IOException {
        return CharStreams.toStringBuilder(readable).toString();
    }

    private static StringBuilder toStringBuilder(Readable readable) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        CharStreams.copy(readable, stringBuilder);
        return stringBuilder;
    }

    public static List<String> readLines(Readable readable) throws IOException {
        String string;
        ArrayList<String> arrayList = new ArrayList<String>();
        LineReader lineReader = new LineReader(readable);
        while ((string = lineReader.readLine()) != null) {
            arrayList.add(string);
        }
        return arrayList;
    }

    @CanIgnoreReturnValue
    public static <T> T readLines(Readable readable, LineProcessor<T> lineProcessor) throws IOException {
        String string;
        Preconditions.checkNotNull(readable);
        Preconditions.checkNotNull(lineProcessor);
        LineReader lineReader = new LineReader(readable);
        while ((string = lineReader.readLine()) != null && lineProcessor.processLine(string)) {
        }
        return lineProcessor.getResult();
    }

    @CanIgnoreReturnValue
    public static long exhaust(Readable readable) throws IOException {
        long l;
        long l2 = 0L;
        CharBuffer charBuffer = CharStreams.createBuffer();
        while ((l = (long)readable.read(charBuffer)) != -1L) {
            l2 += l;
            charBuffer.clear();
        }
        return l2;
    }

    public static void skipFully(Reader reader, long l) throws IOException {
        Preconditions.checkNotNull(reader);
        while (l > 0L) {
            long l2 = reader.skip(l);
            if (l2 == 0L) {
                throw new EOFException();
            }
            l -= l2;
        }
    }

    public static Writer nullWriter() {
        return NullWriter.access$000();
    }

    public static Writer asWriter(Appendable appendable) {
        if (appendable instanceof Writer) {
            return (Writer)appendable;
        }
        return new AppendableWriter(appendable);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    private static final class NullWriter
    extends Writer {
        private static final NullWriter INSTANCE = new NullWriter();

        private NullWriter() {
        }

        @Override
        public void write(int n) {
        }

        @Override
        public void write(char[] cArray) {
            Preconditions.checkNotNull(cArray);
        }

        @Override
        public void write(char[] cArray, int n, int n2) {
            Preconditions.checkPositionIndexes(n, n + n2, cArray.length);
        }

        @Override
        public void write(String string) {
            Preconditions.checkNotNull(string);
        }

        @Override
        public void write(String string, int n, int n2) {
            Preconditions.checkPositionIndexes(n, n + n2, string.length());
        }

        @Override
        public Writer append(CharSequence charSequence) {
            Preconditions.checkNotNull(charSequence);
            return this;
        }

        @Override
        public Writer append(CharSequence charSequence, int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, charSequence.length());
            return this;
        }

        @Override
        public Writer append(char c) {
            return this;
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }

        public String toString() {
            return "CharStreams.nullWriter()";
        }

        @Override
        public Appendable append(char c) throws IOException {
            return this.append(c);
        }

        @Override
        public Appendable append(CharSequence charSequence, int n, int n2) throws IOException {
            return this.append(charSequence, n, n2);
        }

        @Override
        public Appendable append(CharSequence charSequence) throws IOException {
            return this.append(charSequence);
        }

        static NullWriter access$000() {
            return INSTANCE;
        }
    }
}

