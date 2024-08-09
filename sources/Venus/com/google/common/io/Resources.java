/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.google.common.io.LineProcessor;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@Beta
@GwtIncompatible
public final class Resources {
    private Resources() {
    }

    public static ByteSource asByteSource(URL uRL) {
        return new UrlByteSource(uRL, null);
    }

    public static CharSource asCharSource(URL uRL, Charset charset) {
        return Resources.asByteSource(uRL).asCharSource(charset);
    }

    public static byte[] toByteArray(URL uRL) throws IOException {
        return Resources.asByteSource(uRL).read();
    }

    public static String toString(URL uRL, Charset charset) throws IOException {
        return Resources.asCharSource(uRL, charset).read();
    }

    @CanIgnoreReturnValue
    public static <T> T readLines(URL uRL, Charset charset, LineProcessor<T> lineProcessor) throws IOException {
        return Resources.asCharSource(uRL, charset).readLines(lineProcessor);
    }

    public static List<String> readLines(URL uRL, Charset charset) throws IOException {
        return Resources.readLines(uRL, charset, new LineProcessor<List<String>>(){
            final List<String> result = Lists.newArrayList();

            @Override
            public boolean processLine(String string) {
                this.result.add(string);
                return false;
            }

            @Override
            public List<String> getResult() {
                return this.result;
            }

            @Override
            public Object getResult() {
                return this.getResult();
            }
        });
    }

    public static void copy(URL uRL, OutputStream outputStream) throws IOException {
        Resources.asByteSource(uRL).copyTo(outputStream);
    }

    @CanIgnoreReturnValue
    public static URL getResource(String string) {
        ClassLoader classLoader = MoreObjects.firstNonNull(Thread.currentThread().getContextClassLoader(), Resources.class.getClassLoader());
        URL uRL = classLoader.getResource(string);
        Preconditions.checkArgument(uRL != null, "resource %s not found.", (Object)string);
        return uRL;
    }

    public static URL getResource(Class<?> clazz, String string) {
        URL uRL = clazz.getResource(string);
        Preconditions.checkArgument(uRL != null, "resource %s relative to %s not found.", (Object)string, (Object)clazz.getName());
        return uRL;
    }

    private static final class UrlByteSource
    extends ByteSource {
        private final URL url;

        private UrlByteSource(URL uRL) {
            this.url = Preconditions.checkNotNull(uRL);
        }

        @Override
        public InputStream openStream() throws IOException {
            return this.url.openStream();
        }

        public String toString() {
            return "Resources.asByteSource(" + this.url + ")";
        }

        UrlByteSource(URL uRL, 1 var2_2) {
            this(uRL);
        }
    }
}

