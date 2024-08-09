/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class FileWriterWithEncoding
extends Writer {
    private final Writer out;

    public FileWriterWithEncoding(String string, String string2) throws IOException {
        this(new File(string), string2, false);
    }

    public FileWriterWithEncoding(String string, String string2, boolean bl) throws IOException {
        this(new File(string), string2, bl);
    }

    public FileWriterWithEncoding(String string, Charset charset) throws IOException {
        this(new File(string), charset, false);
    }

    public FileWriterWithEncoding(String string, Charset charset, boolean bl) throws IOException {
        this(new File(string), charset, bl);
    }

    public FileWriterWithEncoding(String string, CharsetEncoder charsetEncoder) throws IOException {
        this(new File(string), charsetEncoder, false);
    }

    public FileWriterWithEncoding(String string, CharsetEncoder charsetEncoder, boolean bl) throws IOException {
        this(new File(string), charsetEncoder, bl);
    }

    public FileWriterWithEncoding(File file, String string) throws IOException {
        this(file, string, false);
    }

    public FileWriterWithEncoding(File file, String string, boolean bl) throws IOException {
        this.out = FileWriterWithEncoding.initWriter(file, string, bl);
    }

    public FileWriterWithEncoding(File file, Charset charset) throws IOException {
        this(file, charset, false);
    }

    public FileWriterWithEncoding(File file, Charset charset, boolean bl) throws IOException {
        this.out = FileWriterWithEncoding.initWriter(file, charset, bl);
    }

    public FileWriterWithEncoding(File file, CharsetEncoder charsetEncoder) throws IOException {
        this(file, charsetEncoder, false);
    }

    public FileWriterWithEncoding(File file, CharsetEncoder charsetEncoder, boolean bl) throws IOException {
        this.out = FileWriterWithEncoding.initWriter(file, charsetEncoder, bl);
    }

    private static Writer initWriter(File file, Object object, boolean bl) throws IOException {
        if (file == null) {
            throw new NullPointerException("File is missing");
        }
        if (object == null) {
            throw new NullPointerException("Encoding is missing");
        }
        boolean bl2 = file.exists();
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            fileOutputStream = new FileOutputStream(file, bl);
            outputStreamWriter = object instanceof Charset ? new OutputStreamWriter((OutputStream)fileOutputStream, (Charset)object) : (object instanceof CharsetEncoder ? new OutputStreamWriter((OutputStream)fileOutputStream, (CharsetEncoder)object) : new OutputStreamWriter((OutputStream)fileOutputStream, (String)object));
        } catch (IOException iOException) {
            IOUtils.closeQuietly(outputStreamWriter);
            IOUtils.closeQuietly(fileOutputStream);
            if (!bl2) {
                FileUtils.deleteQuietly(file);
            }
            throw iOException;
        } catch (RuntimeException runtimeException) {
            IOUtils.closeQuietly(outputStreamWriter);
            IOUtils.closeQuietly(fileOutputStream);
            if (!bl2) {
                FileUtils.deleteQuietly(file);
            }
            throw runtimeException;
        }
        return outputStreamWriter;
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
    }

    @Override
    public void write(char[] cArray) throws IOException {
        this.out.write(cArray);
    }

    @Override
    public void write(char[] cArray, int n, int n2) throws IOException {
        this.out.write(cArray, n, n2);
    }

    @Override
    public void write(String string) throws IOException {
        this.out.write(string);
    }

    @Override
    public void write(String string, int n, int n2) throws IOException {
        this.out.write(string, n, n2);
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public void close() throws IOException {
        this.out.close();
    }
}

