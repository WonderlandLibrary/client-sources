/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Ascii;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSequenceReader;
import com.google.common.io.CharSink;
import com.google.common.io.CharStreams;
import com.google.common.io.Closer;
import com.google.common.io.LineProcessor;
import com.google.common.io.MultiReader;
import com.google.common.io.ReaderInputStream;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;

@GwtIncompatible
public abstract class CharSource {
    protected CharSource() {
    }

    @Beta
    public ByteSource asByteSource(Charset charset) {
        return new AsByteSource(this, charset);
    }

    public abstract Reader openStream() throws IOException;

    public BufferedReader openBufferedStream() throws IOException {
        Reader reader = this.openStream();
        return reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
    }

    @Beta
    public Optional<Long> lengthIfKnown() {
        return Optional.absent();
    }

    @Beta
    public long length() throws IOException {
        Optional<Long> optional = this.lengthIfKnown();
        if (optional.isPresent()) {
            return optional.get();
        }
        try (Closer closer = Closer.create();){
            Reader reader = closer.register(this.openStream());
            long l = this.countBySkipping(reader);
            return l;
        }
    }

    private long countBySkipping(Reader reader) throws IOException {
        long l;
        long l2 = 0L;
        while ((l = reader.skip(Long.MAX_VALUE)) != 0L) {
            l2 += l;
        }
        return l2;
    }

    @CanIgnoreReturnValue
    public long copyTo(Appendable appendable) throws IOException {
        Preconditions.checkNotNull(appendable);
        try (Closer closer = Closer.create();){
            Reader reader = closer.register(this.openStream());
            long l = CharStreams.copy(reader, appendable);
            return l;
        }
    }

    @CanIgnoreReturnValue
    public long copyTo(CharSink charSink) throws IOException {
        Preconditions.checkNotNull(charSink);
        try (Closer closer = Closer.create();){
            Reader reader = closer.register(this.openStream());
            Writer writer = closer.register(charSink.openStream());
            long l = CharStreams.copy(reader, writer);
            return l;
        }
    }

    public String read() throws IOException {
        try (Closer closer = Closer.create();){
            Reader reader = closer.register(this.openStream());
            String string = CharStreams.toString(reader);
            return string;
        }
    }

    @Nullable
    public String readFirstLine() throws IOException {
        try (Closer closer = Closer.create();){
            BufferedReader bufferedReader = closer.register(this.openBufferedStream());
            String string = bufferedReader.readLine();
            return string;
        }
    }

    public ImmutableList<String> readLines() throws IOException {
        try (Closer closer = Closer.create();){
            String string;
            BufferedReader bufferedReader = closer.register(this.openBufferedStream());
            ArrayList<String> arrayList = Lists.newArrayList();
            while ((string = bufferedReader.readLine()) != null) {
                arrayList.add(string);
            }
            ImmutableList<String> immutableList = ImmutableList.copyOf(arrayList);
            return immutableList;
        }
    }

    @Beta
    @CanIgnoreReturnValue
    public <T> T readLines(LineProcessor<T> lineProcessor) throws IOException {
        Preconditions.checkNotNull(lineProcessor);
        try (Closer closer = Closer.create();){
            Reader reader = closer.register(this.openStream());
            T t = CharStreams.readLines(reader, lineProcessor);
            return t;
        }
    }

    public boolean isEmpty() throws IOException {
        Optional<Long> optional = this.lengthIfKnown();
        if (optional.isPresent() && optional.get() == 0L) {
            return false;
        }
        try (Closer closer = Closer.create();){
            Reader reader = closer.register(this.openStream());
            boolean bl = reader.read() == -1;
            return bl;
        }
    }

    public static CharSource concat(Iterable<? extends CharSource> iterable) {
        return new ConcatenatedCharSource(iterable);
    }

    public static CharSource concat(Iterator<? extends CharSource> iterator2) {
        return CharSource.concat(ImmutableList.copyOf(iterator2));
    }

    public static CharSource concat(CharSource ... charSourceArray) {
        return CharSource.concat(ImmutableList.copyOf(charSourceArray));
    }

    public static CharSource wrap(CharSequence charSequence) {
        return new CharSequenceCharSource(charSequence);
    }

    public static CharSource empty() {
        return EmptyCharSource.access$000();
    }

    private static final class ConcatenatedCharSource
    extends CharSource {
        private final Iterable<? extends CharSource> sources;

        ConcatenatedCharSource(Iterable<? extends CharSource> iterable) {
            this.sources = Preconditions.checkNotNull(iterable);
        }

        @Override
        public Reader openStream() throws IOException {
            return new MultiReader(this.sources.iterator());
        }

        @Override
        public boolean isEmpty() throws IOException {
            for (CharSource charSource : this.sources) {
                if (charSource.isEmpty()) continue;
                return true;
            }
            return false;
        }

        @Override
        public Optional<Long> lengthIfKnown() {
            long l = 0L;
            for (CharSource charSource : this.sources) {
                Optional<Long> optional = charSource.lengthIfKnown();
                if (!optional.isPresent()) {
                    return Optional.absent();
                }
                l += optional.get().longValue();
            }
            return Optional.of(l);
        }

        @Override
        public long length() throws IOException {
            long l = 0L;
            for (CharSource charSource : this.sources) {
                l += charSource.length();
            }
            return l;
        }

        public String toString() {
            return "CharSource.concat(" + this.sources + ")";
        }
    }

    private static final class EmptyCharSource
    extends CharSequenceCharSource {
        private static final EmptyCharSource INSTANCE = new EmptyCharSource();

        private EmptyCharSource() {
            super("");
        }

        @Override
        public String toString() {
            return "CharSource.empty()";
        }

        static EmptyCharSource access$000() {
            return INSTANCE;
        }
    }

    private static class CharSequenceCharSource
    extends CharSource {
        private static final Splitter LINE_SPLITTER = Splitter.onPattern("\r\n|\n|\r");
        private final CharSequence seq;

        protected CharSequenceCharSource(CharSequence charSequence) {
            this.seq = Preconditions.checkNotNull(charSequence);
        }

        @Override
        public Reader openStream() {
            return new CharSequenceReader(this.seq);
        }

        @Override
        public String read() {
            return this.seq.toString();
        }

        @Override
        public boolean isEmpty() {
            return this.seq.length() == 0;
        }

        @Override
        public long length() {
            return this.seq.length();
        }

        @Override
        public Optional<Long> lengthIfKnown() {
            return Optional.of(Long.valueOf(this.seq.length()));
        }

        private Iterable<String> lines() {
            return new Iterable<String>(this){
                final CharSequenceCharSource this$0;
                {
                    this.this$0 = charSequenceCharSource;
                }

                @Override
                public Iterator<String> iterator() {
                    return new AbstractIterator<String>(this){
                        Iterator<String> lines;
                        final 1 this$1;
                        {
                            this.this$1 = var1_1;
                            this.lines = CharSequenceCharSource.access$200().split(CharSequenceCharSource.access$100(this.this$1.this$0)).iterator();
                        }

                        @Override
                        protected String computeNext() {
                            if (this.lines.hasNext()) {
                                String string = this.lines.next();
                                if (this.lines.hasNext() || !string.isEmpty()) {
                                    return string;
                                }
                            }
                            return (String)this.endOfData();
                        }

                        @Override
                        protected Object computeNext() {
                            return this.computeNext();
                        }
                    };
                }
            };
        }

        @Override
        public String readFirstLine() {
            Iterator<String> iterator2 = this.lines().iterator();
            return iterator2.hasNext() ? iterator2.next() : null;
        }

        @Override
        public ImmutableList<String> readLines() {
            return ImmutableList.copyOf(this.lines());
        }

        @Override
        public <T> T readLines(LineProcessor<T> lineProcessor) throws IOException {
            for (String string : this.lines()) {
                if (!lineProcessor.processLine(string)) break;
            }
            return lineProcessor.getResult();
        }

        public String toString() {
            return "CharSource.wrap(" + Ascii.truncate(this.seq, 30, "...") + ")";
        }

        static CharSequence access$100(CharSequenceCharSource charSequenceCharSource) {
            return charSequenceCharSource.seq;
        }

        static Splitter access$200() {
            return LINE_SPLITTER;
        }
    }

    private final class AsByteSource
    extends ByteSource {
        final Charset charset;
        final CharSource this$0;

        AsByteSource(CharSource charSource, Charset charset) {
            this.this$0 = charSource;
            this.charset = Preconditions.checkNotNull(charset);
        }

        @Override
        public CharSource asCharSource(Charset charset) {
            if (charset.equals(this.charset)) {
                return this.this$0;
            }
            return super.asCharSource(charset);
        }

        @Override
        public InputStream openStream() throws IOException {
            return new ReaderInputStream(this.this$0.openStream(), this.charset, 8192);
        }

        public String toString() {
            return this.this$0.toString() + ".asByteSource(" + this.charset + ")";
        }
    }
}

