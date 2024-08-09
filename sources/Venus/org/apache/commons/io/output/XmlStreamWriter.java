/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.input.XmlStreamReader;

public class XmlStreamWriter
extends Writer {
    private static final int BUFFER_SIZE = 4096;
    private final OutputStream out;
    private final String defaultEncoding;
    private StringWriter xmlPrologWriter = new StringWriter(4096);
    private Writer writer;
    private String encoding;
    static final Pattern ENCODING_PATTERN = XmlStreamReader.ENCODING_PATTERN;

    public XmlStreamWriter(OutputStream outputStream) {
        this(outputStream, null);
    }

    public XmlStreamWriter(OutputStream outputStream, String string) {
        this.out = outputStream;
        this.defaultEncoding = string != null ? string : "UTF-8";
    }

    public XmlStreamWriter(File file) throws FileNotFoundException {
        this(file, null);
    }

    public XmlStreamWriter(File file, String string) throws FileNotFoundException {
        this(new FileOutputStream(file), string);
    }

    public String getEncoding() {
        return this.encoding;
    }

    public String getDefaultEncoding() {
        return this.defaultEncoding;
    }

    @Override
    public void close() throws IOException {
        if (this.writer == null) {
            this.encoding = this.defaultEncoding;
            this.writer = new OutputStreamWriter(this.out, this.encoding);
            this.writer.write(this.xmlPrologWriter.toString());
        }
        this.writer.close();
    }

    @Override
    public void flush() throws IOException {
        if (this.writer != null) {
            this.writer.flush();
        }
    }

    private void detectEncoding(char[] cArray, int n, int n2) throws IOException {
        int n3 = n2;
        StringBuffer stringBuffer = this.xmlPrologWriter.getBuffer();
        if (stringBuffer.length() + n2 > 4096) {
            n3 = 4096 - stringBuffer.length();
        }
        this.xmlPrologWriter.write(cArray, n, n3);
        if (stringBuffer.length() >= 5) {
            if (stringBuffer.substring(0, 5).equals("<?xml")) {
                int n4 = stringBuffer.indexOf("?>");
                if (n4 > 0) {
                    Matcher matcher = ENCODING_PATTERN.matcher(stringBuffer.substring(0, n4));
                    if (matcher.find()) {
                        this.encoding = matcher.group(1).toUpperCase();
                        this.encoding = this.encoding.substring(1, this.encoding.length() - 1);
                    } else {
                        this.encoding = this.defaultEncoding;
                    }
                } else if (stringBuffer.length() >= 4096) {
                    this.encoding = this.defaultEncoding;
                }
            } else {
                this.encoding = this.defaultEncoding;
            }
            if (this.encoding != null) {
                this.xmlPrologWriter = null;
                this.writer = new OutputStreamWriter(this.out, this.encoding);
                this.writer.write(stringBuffer.toString());
                if (n2 > n3) {
                    this.writer.write(cArray, n + n3, n2 - n3);
                }
            }
        }
    }

    @Override
    public void write(char[] cArray, int n, int n2) throws IOException {
        if (this.xmlPrologWriter != null) {
            this.detectEncoding(cArray, n, n2);
        } else {
            this.writer.write(cArray, n, n2);
        }
    }
}

