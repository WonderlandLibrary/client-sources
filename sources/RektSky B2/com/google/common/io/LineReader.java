package com.google.common.io;

import com.google.common.annotations.*;
import java.nio.*;
import java.util.*;
import com.google.common.base.*;
import java.io.*;

@Beta
public final class LineReader
{
    private final Readable readable;
    private final Reader reader;
    private final char[] buf;
    private final CharBuffer cbuf;
    private final Queue<String> lines;
    private final LineBuffer lineBuf;
    
    public LineReader(final Readable readable) {
        this.buf = new char[4096];
        this.cbuf = CharBuffer.wrap(this.buf);
        this.lines = new LinkedList<String>();
        this.lineBuf = new LineBuffer() {
            @Override
            protected void handleLine(final String line, final String end) {
                LineReader.this.lines.add(line);
            }
        };
        this.readable = Preconditions.checkNotNull(readable);
        this.reader = ((readable instanceof Reader) ? ((Reader)readable) : null);
    }
    
    public String readLine() throws IOException {
        while (this.lines.peek() == null) {
            this.cbuf.clear();
            final int read = (this.reader != null) ? this.reader.read(this.buf, 0, this.buf.length) : this.readable.read(this.cbuf);
            if (read == -1) {
                this.lineBuf.finish();
                break;
            }
            this.lineBuf.add(this.buf, 0, read);
        }
        return this.lines.poll();
    }
}
