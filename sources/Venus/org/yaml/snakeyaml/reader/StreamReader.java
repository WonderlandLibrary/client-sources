/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.yaml.snakeyaml.reader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.reader.ReaderException;
import org.yaml.snakeyaml.scanner.Constant;

public class StreamReader {
    private String name;
    private final Reader stream;
    private int[] dataWindow;
    private int dataLength;
    private int pointer = 0;
    private boolean eof;
    private int index = 0;
    private int documentIndex = 0;
    private int line = 0;
    private int column = 0;
    private final char[] buffer;
    private static final int BUFFER_SIZE = 1025;

    public StreamReader(String string) {
        this(new StringReader(string));
        this.name = "'string'";
    }

    public StreamReader(Reader reader) {
        if (reader == null) {
            throw new NullPointerException("Reader must be provided.");
        }
        this.name = "'reader'";
        this.dataWindow = new int[0];
        this.dataLength = 0;
        this.stream = reader;
        this.eof = false;
        this.buffer = new char[1025];
    }

    public static boolean isPrintable(String string) {
        int n;
        int n2 = string.length();
        for (int i = 0; i < n2; i += Character.charCount(n)) {
            n = string.codePointAt(i);
            if (StreamReader.isPrintable(n)) continue;
            return true;
        }
        return false;
    }

    public static boolean isPrintable(int n) {
        return n >= 32 && n <= 126 || n == 9 || n == 10 || n == 13 || n == 133 || n >= 160 && n <= 55295 || n >= 57344 && n <= 65533 || n >= 65536 && n <= 0x10FFFF;
    }

    public Mark getMark() {
        return new Mark(this.name, this.index, this.line, this.column, this.dataWindow, this.pointer);
    }

    public void forward() {
        this.forward(1);
    }

    public void forward(int n) {
        for (int i = 0; i < n && this.ensureEnoughData(); ++i) {
            int n2 = this.dataWindow[this.pointer++];
            this.moveIndices(1);
            if (Constant.LINEBR.has(n2) || n2 == 13 && this.ensureEnoughData() && this.dataWindow[this.pointer] != 10) {
                ++this.line;
                this.column = 0;
                continue;
            }
            if (n2 == 65279) continue;
            ++this.column;
        }
    }

    public int peek() {
        return this.ensureEnoughData() ? this.dataWindow[this.pointer] : 0;
    }

    public int peek(int n) {
        return this.ensureEnoughData(n) ? this.dataWindow[this.pointer + n] : 0;
    }

    public String prefix(int n) {
        if (n == 0) {
            return "";
        }
        if (this.ensureEnoughData(n)) {
            return new String(this.dataWindow, this.pointer, n);
        }
        return new String(this.dataWindow, this.pointer, Math.min(n, this.dataLength - this.pointer));
    }

    public String prefixForward(int n) {
        String string = this.prefix(n);
        this.pointer += n;
        this.moveIndices(n);
        this.column += n;
        return string;
    }

    private boolean ensureEnoughData() {
        return this.ensureEnoughData(0);
    }

    private boolean ensureEnoughData(int n) {
        if (!this.eof && this.pointer + n >= this.dataLength) {
            this.update();
        }
        return this.pointer + n < this.dataLength;
    }

    private void update() {
        try {
            int n = this.stream.read(this.buffer, 0, 1024);
            if (n > 0) {
                int n2 = this.dataLength - this.pointer;
                this.dataWindow = Arrays.copyOfRange(this.dataWindow, this.pointer, this.dataLength + n);
                if (Character.isHighSurrogate(this.buffer[n - 1])) {
                    if (this.stream.read(this.buffer, n, 1) == -1) {
                        this.eof = true;
                    } else {
                        ++n;
                    }
                }
                int n3 = 32;
                int n4 = 0;
                while (n4 < n) {
                    int n5;
                    this.dataWindow[n2] = n5 = Character.codePointAt(this.buffer, n4);
                    if (StreamReader.isPrintable(n5)) {
                        n4 += Character.charCount(n5);
                    } else {
                        n3 = n5;
                        n4 = n;
                    }
                    ++n2;
                }
                this.dataLength = n2;
                this.pointer = 0;
                if (n3 != 32) {
                    throw new ReaderException(this.name, n2 - 1, n3, "special characters are not allowed");
                }
            } else {
                this.eof = true;
            }
        } catch (IOException iOException) {
            throw new YAMLException(iOException);
        }
    }

    public int getColumn() {
        return this.column;
    }

    private void moveIndices(int n) {
        this.index += n;
        this.documentIndex += n;
    }

    public int getDocumentIndex() {
        return this.documentIndex;
    }

    public void resetDocumentIndex() {
        this.documentIndex = 0;
    }

    public int getIndex() {
        return this.index;
    }

    public int getLine() {
        return this.line;
    }
}

