/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

public class UnicodeReader
extends Reader {
    private static final Charset UTF8 = StandardCharsets.UTF_8;
    private static final Charset UTF16BE = StandardCharsets.UTF_16BE;
    private static final Charset UTF16LE = StandardCharsets.UTF_16LE;
    PushbackInputStream internalIn;
    InputStreamReader internalIn2 = null;
    private static final int BOM_SIZE = 3;

    public UnicodeReader(InputStream inputStream) {
        this.internalIn = new PushbackInputStream(inputStream, 3);
    }

    public String getEncoding() {
        return this.internalIn2.getEncoding();
    }

    protected void init() throws IOException {
        int n;
        Charset charset;
        if (this.internalIn2 != null) {
            return;
        }
        byte[] byArray = new byte[3];
        int n2 = this.internalIn.read(byArray, 0, byArray.length);
        if (byArray[0] == -17 && byArray[1] == -69 && byArray[2] == -65) {
            charset = UTF8;
            n = n2 - 3;
        } else if (byArray[0] == -2 && byArray[1] == -1) {
            charset = UTF16BE;
            n = n2 - 2;
        } else if (byArray[0] == -1 && byArray[1] == -2) {
            charset = UTF16LE;
            n = n2 - 2;
        } else {
            charset = UTF8;
            n = n2;
        }
        if (n > 0) {
            this.internalIn.unread(byArray, n2 - n, n);
        }
        CharsetDecoder charsetDecoder = charset.newDecoder().onUnmappableCharacter(CodingErrorAction.REPORT);
        this.internalIn2 = new InputStreamReader((InputStream)this.internalIn, charsetDecoder);
    }

    @Override
    public void close() throws IOException {
        this.init();
        this.internalIn2.close();
    }

    @Override
    public int read(char[] cArray, int n, int n2) throws IOException {
        this.init();
        return this.internalIn2.read(cArray, n, n2);
    }
}

