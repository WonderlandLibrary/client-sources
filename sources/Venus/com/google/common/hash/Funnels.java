/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.hash;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

@Beta
public final class Funnels {
    private Funnels() {
    }

    public static Funnel<byte[]> byteArrayFunnel() {
        return ByteArrayFunnel.INSTANCE;
    }

    public static Funnel<CharSequence> unencodedCharsFunnel() {
        return UnencodedCharsFunnel.INSTANCE;
    }

    public static Funnel<CharSequence> stringFunnel(Charset charset) {
        return new StringCharsetFunnel(charset);
    }

    public static Funnel<Integer> integerFunnel() {
        return IntegerFunnel.INSTANCE;
    }

    public static <E> Funnel<Iterable<? extends E>> sequentialFunnel(Funnel<E> funnel) {
        return new SequentialFunnel<E>(funnel);
    }

    public static Funnel<Long> longFunnel() {
        return LongFunnel.INSTANCE;
    }

    public static OutputStream asOutputStream(PrimitiveSink primitiveSink) {
        return new SinkAsStream(primitiveSink);
    }

    private static class SinkAsStream
    extends OutputStream {
        final PrimitiveSink sink;

        SinkAsStream(PrimitiveSink primitiveSink) {
            this.sink = Preconditions.checkNotNull(primitiveSink);
        }

        @Override
        public void write(int n) {
            this.sink.putByte((byte)n);
        }

        @Override
        public void write(byte[] byArray) {
            this.sink.putBytes(byArray);
        }

        @Override
        public void write(byte[] byArray, int n, int n2) {
            this.sink.putBytes(byArray, n, n2);
        }

        public String toString() {
            return "Funnels.asOutputStream(" + this.sink + ")";
        }
    }

    private static enum LongFunnel implements Funnel<Long>
    {
        INSTANCE;


        @Override
        public void funnel(Long l, PrimitiveSink primitiveSink) {
            primitiveSink.putLong(l);
        }

        public String toString() {
            return "Funnels.longFunnel()";
        }

        @Override
        public void funnel(Object object, PrimitiveSink primitiveSink) {
            this.funnel((Long)object, primitiveSink);
        }
    }

    private static class SequentialFunnel<E>
    implements Funnel<Iterable<? extends E>>,
    Serializable {
        private final Funnel<E> elementFunnel;

        SequentialFunnel(Funnel<E> funnel) {
            this.elementFunnel = Preconditions.checkNotNull(funnel);
        }

        @Override
        public void funnel(Iterable<? extends E> iterable, PrimitiveSink primitiveSink) {
            for (E e : iterable) {
                this.elementFunnel.funnel(e, primitiveSink);
            }
        }

        public String toString() {
            return "Funnels.sequentialFunnel(" + this.elementFunnel + ")";
        }

        public boolean equals(@Nullable Object object) {
            if (object instanceof SequentialFunnel) {
                SequentialFunnel sequentialFunnel = (SequentialFunnel)object;
                return this.elementFunnel.equals(sequentialFunnel.elementFunnel);
            }
            return true;
        }

        public int hashCode() {
            return SequentialFunnel.class.hashCode() ^ this.elementFunnel.hashCode();
        }

        @Override
        public void funnel(Object object, PrimitiveSink primitiveSink) {
            this.funnel((Iterable)object, primitiveSink);
        }
    }

    private static enum IntegerFunnel implements Funnel<Integer>
    {
        INSTANCE;


        @Override
        public void funnel(Integer n, PrimitiveSink primitiveSink) {
            primitiveSink.putInt(n);
        }

        public String toString() {
            return "Funnels.integerFunnel()";
        }

        @Override
        public void funnel(Object object, PrimitiveSink primitiveSink) {
            this.funnel((Integer)object, primitiveSink);
        }
    }

    private static class StringCharsetFunnel
    implements Funnel<CharSequence>,
    Serializable {
        private final Charset charset;

        StringCharsetFunnel(Charset charset) {
            this.charset = Preconditions.checkNotNull(charset);
        }

        @Override
        public void funnel(CharSequence charSequence, PrimitiveSink primitiveSink) {
            primitiveSink.putString(charSequence, this.charset);
        }

        public String toString() {
            return "Funnels.stringFunnel(" + this.charset.name() + ")";
        }

        public boolean equals(@Nullable Object object) {
            if (object instanceof StringCharsetFunnel) {
                StringCharsetFunnel stringCharsetFunnel = (StringCharsetFunnel)object;
                return this.charset.equals(stringCharsetFunnel.charset);
            }
            return true;
        }

        public int hashCode() {
            return StringCharsetFunnel.class.hashCode() ^ this.charset.hashCode();
        }

        Object writeReplace() {
            return new SerializedForm(this.charset);
        }

        @Override
        public void funnel(Object object, PrimitiveSink primitiveSink) {
            this.funnel((CharSequence)object, primitiveSink);
        }

        private static class SerializedForm
        implements Serializable {
            private final String charsetCanonicalName;
            private static final long serialVersionUID = 0L;

            SerializedForm(Charset charset) {
                this.charsetCanonicalName = charset.name();
            }

            private Object readResolve() {
                return Funnels.stringFunnel(Charset.forName(this.charsetCanonicalName));
            }
        }
    }

    private static enum UnencodedCharsFunnel implements Funnel<CharSequence>
    {
        INSTANCE;


        @Override
        public void funnel(CharSequence charSequence, PrimitiveSink primitiveSink) {
            primitiveSink.putUnencodedChars(charSequence);
        }

        public String toString() {
            return "Funnels.unencodedCharsFunnel()";
        }

        @Override
        public void funnel(Object object, PrimitiveSink primitiveSink) {
            this.funnel((CharSequence)object, primitiveSink);
        }
    }

    private static enum ByteArrayFunnel implements Funnel<byte[]>
    {
        INSTANCE;


        @Override
        public void funnel(byte[] byArray, PrimitiveSink primitiveSink) {
            primitiveSink.putBytes(byArray);
        }

        public String toString() {
            return "Funnels.byteArrayFunnel()";
        }

        @Override
        public void funnel(Object object, PrimitiveSink primitiveSink) {
            this.funnel((byte[])object, primitiveSink);
        }
    }
}

