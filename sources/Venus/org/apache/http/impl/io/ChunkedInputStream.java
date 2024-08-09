/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.ConnectionClosedException;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.MalformedChunkCodingException;
import org.apache.http.TruncatedChunkException;
import org.apache.http.config.MessageConstraints;
import org.apache.http.impl.io.AbstractMessageParser;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

public class ChunkedInputStream
extends InputStream {
    private static final int CHUNK_LEN = 1;
    private static final int CHUNK_DATA = 2;
    private static final int CHUNK_CRLF = 3;
    private static final int CHUNK_INVALID = Integer.MAX_VALUE;
    private static final int BUFFER_SIZE = 2048;
    private final SessionInputBuffer in;
    private final CharArrayBuffer buffer;
    private final MessageConstraints constraints;
    private int state;
    private long chunkSize;
    private long pos;
    private boolean eof = false;
    private boolean closed = false;
    private Header[] footers = new Header[0];

    public ChunkedInputStream(SessionInputBuffer sessionInputBuffer, MessageConstraints messageConstraints) {
        this.in = Args.notNull(sessionInputBuffer, "Session input buffer");
        this.pos = 0L;
        this.buffer = new CharArrayBuffer(16);
        this.constraints = messageConstraints != null ? messageConstraints : MessageConstraints.DEFAULT;
        this.state = 1;
    }

    public ChunkedInputStream(SessionInputBuffer sessionInputBuffer) {
        this(sessionInputBuffer, null);
    }

    @Override
    public int available() throws IOException {
        if (this.in instanceof BufferInfo) {
            int n = ((BufferInfo)((Object)this.in)).length();
            return (int)Math.min((long)n, this.chunkSize - this.pos);
        }
        return 1;
    }

    @Override
    public int read() throws IOException {
        int n;
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (this.eof) {
            return 1;
        }
        if (this.state != 2) {
            this.nextChunk();
            if (this.eof) {
                return 1;
            }
        }
        if ((n = this.in.read()) != -1) {
            ++this.pos;
            if (this.pos >= this.chunkSize) {
                this.state = 3;
            }
        }
        return n;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3;
        if (this.closed) {
            throw new IOException("Attempted read from closed stream.");
        }
        if (this.eof) {
            return 1;
        }
        if (this.state != 2) {
            this.nextChunk();
            if (this.eof) {
                return 1;
            }
        }
        if ((n3 = this.in.read(byArray, n, (int)Math.min((long)n2, this.chunkSize - this.pos))) != -1) {
            this.pos += (long)n3;
            if (this.pos >= this.chunkSize) {
                this.state = 3;
            }
            return n3;
        }
        this.eof = true;
        throw new TruncatedChunkException("Truncated chunk (expected size: %,d; actual size: %,d)", this.chunkSize, this.pos);
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    private void nextChunk() throws IOException {
        if (this.state == Integer.MAX_VALUE) {
            throw new MalformedChunkCodingException("Corrupt data stream");
        }
        try {
            this.chunkSize = this.getChunkSize();
            if (this.chunkSize < 0L) {
                throw new MalformedChunkCodingException("Negative chunk size");
            }
            this.state = 2;
            this.pos = 0L;
            if (this.chunkSize == 0L) {
                this.eof = true;
                this.parseTrailerHeaders();
            }
        } catch (MalformedChunkCodingException malformedChunkCodingException) {
            this.state = Integer.MAX_VALUE;
            throw malformedChunkCodingException;
        }
    }

    private long getChunkSize() throws IOException {
        int n = this.state;
        switch (n) {
            case 3: {
                this.buffer.clear();
                int n2 = this.in.readLine(this.buffer);
                if (n2 == -1) {
                    throw new MalformedChunkCodingException("CRLF expected at end of chunk");
                }
                if (!this.buffer.isEmpty()) {
                    throw new MalformedChunkCodingException("Unexpected content at the end of chunk");
                }
                this.state = 1;
            }
            case 1: {
                this.buffer.clear();
                int n3 = this.in.readLine(this.buffer);
                if (n3 == -1) {
                    throw new ConnectionClosedException("Premature end of chunk coded message body: closing chunk expected");
                }
                int n4 = this.buffer.indexOf(59);
                if (n4 < 0) {
                    n4 = this.buffer.length();
                }
                String string = this.buffer.substringTrimmed(0, n4);
                try {
                    return Long.parseLong(string, 16);
                } catch (NumberFormatException numberFormatException) {
                    throw new MalformedChunkCodingException("Bad chunk header: " + string);
                }
            }
        }
        throw new IllegalStateException("Inconsistent codec state");
    }

    private void parseTrailerHeaders() throws IOException {
        try {
            this.footers = AbstractMessageParser.parseHeaders(this.in, this.constraints.getMaxHeaderCount(), this.constraints.getMaxLineLength(), null);
        } catch (HttpException httpException) {
            MalformedChunkCodingException malformedChunkCodingException = new MalformedChunkCodingException("Invalid footer: " + httpException.getMessage());
            malformedChunkCodingException.initCause(httpException);
            throw malformedChunkCodingException;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        if (!this.closed) {
            try {
                if (!this.eof && this.state != Integer.MAX_VALUE) {
                    byte[] byArray = new byte[2048];
                    while (this.read(byArray) >= 0) {
                    }
                }
            } finally {
                this.eof = true;
                this.closed = true;
            }
        }
    }

    public Header[] getFooters() {
        return (Header[])this.footers.clone();
    }
}

