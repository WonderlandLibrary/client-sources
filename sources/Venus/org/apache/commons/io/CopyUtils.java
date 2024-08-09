/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;

@Deprecated
public class CopyUtils {
    private static final int DEFAULT_BUFFER_SIZE = 4096;

    public static void copy(byte[] byArray, OutputStream outputStream) throws IOException {
        outputStream.write(byArray);
    }

    @Deprecated
    public static void copy(byte[] byArray, Writer writer) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
        CopyUtils.copy((InputStream)byteArrayInputStream, writer);
    }

    public static void copy(byte[] byArray, Writer writer, String string) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
        CopyUtils.copy(byteArrayInputStream, writer, string);
    }

    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] byArray = new byte[4096];
        int n = 0;
        int n2 = 0;
        while (-1 != (n2 = inputStream.read(byArray))) {
            outputStream.write(byArray, 0, n2);
            n += n2;
        }
        return n;
    }

    public static int copy(Reader reader, Writer writer) throws IOException {
        char[] cArray = new char[4096];
        int n = 0;
        int n2 = 0;
        while (-1 != (n2 = reader.read(cArray))) {
            writer.write(cArray, 0, n2);
            n += n2;
        }
        return n;
    }

    @Deprecated
    public static void copy(InputStream inputStream, Writer writer) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.defaultCharset());
        CopyUtils.copy((Reader)inputStreamReader, writer);
    }

    public static void copy(InputStream inputStream, Writer writer, String string) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, string);
        CopyUtils.copy((Reader)inputStreamReader, writer);
    }

    @Deprecated
    public static void copy(Reader reader, OutputStream outputStream) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charset.defaultCharset());
        CopyUtils.copy(reader, (Writer)outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static void copy(Reader reader, OutputStream outputStream, String string) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, string);
        CopyUtils.copy(reader, (Writer)outputStreamWriter);
        outputStreamWriter.flush();
    }

    @Deprecated
    public static void copy(String string, OutputStream outputStream) throws IOException {
        StringReader stringReader = new StringReader(string);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, Charset.defaultCharset());
        CopyUtils.copy((Reader)stringReader, (Writer)outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static void copy(String string, OutputStream outputStream, String string2) throws IOException {
        StringReader stringReader = new StringReader(string);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, string2);
        CopyUtils.copy((Reader)stringReader, (Writer)outputStreamWriter);
        outputStreamWriter.flush();
    }

    public static void copy(String string, Writer writer) throws IOException {
        writer.write(string);
    }
}

