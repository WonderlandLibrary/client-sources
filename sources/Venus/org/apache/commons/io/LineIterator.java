/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.io.IOUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class LineIterator
implements Iterator<String> {
    private final BufferedReader bufferedReader;
    private String cachedLine;
    private boolean finished = false;

    public LineIterator(Reader reader) throws IllegalArgumentException {
        if (reader == null) {
            throw new IllegalArgumentException("Reader must not be null");
        }
        this.bufferedReader = reader instanceof BufferedReader ? (BufferedReader)reader : new BufferedReader(reader);
    }

    @Override
    public boolean hasNext() {
        if (this.cachedLine != null) {
            return false;
        }
        if (this.finished) {
            return true;
        }
        try {
            String string;
            do {
                if ((string = this.bufferedReader.readLine()) != null) continue;
                this.finished = true;
                return false;
            } while (!this.isValidLine(string));
            this.cachedLine = string;
            return true;
        } catch (IOException iOException) {
            this.close();
            throw new IllegalStateException(iOException);
        }
    }

    protected boolean isValidLine(String string) {
        return false;
    }

    @Override
    public String next() {
        return this.nextLine();
    }

    public String nextLine() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No more lines");
        }
        String string = this.cachedLine;
        this.cachedLine = null;
        return string;
    }

    public void close() {
        this.finished = true;
        IOUtils.closeQuietly(this.bufferedReader);
        this.cachedLine = null;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove unsupported on LineIterator");
    }

    public static void closeQuietly(LineIterator lineIterator) {
        if (lineIterator != null) {
            lineIterator.close();
        }
    }

    @Override
    public Object next() {
        return this.next();
    }
}

