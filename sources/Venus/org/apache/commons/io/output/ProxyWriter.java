/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ProxyWriter
extends FilterWriter {
    public ProxyWriter(Writer writer) {
        super(writer);
    }

    @Override
    public Writer append(char c) throws IOException {
        try {
            this.beforeWrite(1);
            this.out.append(c);
            this.afterWrite(1);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence, int n, int n2) throws IOException {
        try {
            this.beforeWrite(n2 - n);
            this.out.append(charSequence, n, n2);
            this.afterWrite(n2 - n);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
        return this;
    }

    @Override
    public Writer append(CharSequence charSequence) throws IOException {
        try {
            int n = 0;
            if (charSequence != null) {
                n = charSequence.length();
            }
            this.beforeWrite(n);
            this.out.append(charSequence);
            this.afterWrite(n);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
        return this;
    }

    @Override
    public void write(int n) throws IOException {
        try {
            this.beforeWrite(1);
            this.out.write(n);
            this.afterWrite(1);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public void write(char[] cArray) throws IOException {
        try {
            int n = 0;
            if (cArray != null) {
                n = cArray.length;
            }
            this.beforeWrite(n);
            this.out.write(cArray);
            this.afterWrite(n);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public void write(char[] cArray, int n, int n2) throws IOException {
        try {
            this.beforeWrite(n2);
            this.out.write(cArray, n, n2);
            this.afterWrite(n2);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public void write(String string) throws IOException {
        try {
            int n = 0;
            if (string != null) {
                n = string.length();
            }
            this.beforeWrite(n);
            this.out.write(string);
            this.afterWrite(n);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public void write(String string, int n, int n2) throws IOException {
        try {
            this.beforeWrite(n2);
            this.out.write(string, n, n2);
            this.afterWrite(n2);
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public void flush() throws IOException {
        try {
            this.out.flush();
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            this.out.close();
        } catch (IOException iOException) {
            this.handleIOException(iOException);
        }
    }

    protected void beforeWrite(int n) throws IOException {
    }

    protected void afterWrite(int n) throws IOException {
    }

    protected void handleIOException(IOException iOException) throws IOException {
        throw iOException;
    }

    @Override
    public Appendable append(char c) throws IOException {
        return this.append(c);
    }

    @Override
    public Appendable append(CharSequence charSequence, int n, int n2) throws IOException {
        return this.append(charSequence, n, n2);
    }

    @Override
    public Appendable append(CharSequence charSequence) throws IOException {
        return this.append(charSequence);
    }
}

