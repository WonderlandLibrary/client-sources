/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.filefilter;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.AbstractFileFilter;

public class MagicNumberFileFilter
extends AbstractFileFilter
implements Serializable {
    private static final long serialVersionUID = -547733176983104172L;
    private final byte[] magicNumbers;
    private final long byteOffset;

    public MagicNumberFileFilter(byte[] magicNumber) {
        this(magicNumber, 0L);
    }

    public MagicNumberFileFilter(String magicNumber) {
        this(magicNumber, 0L);
    }

    public MagicNumberFileFilter(String magicNumber, long offset) {
        if (magicNumber == null) {
            throw new IllegalArgumentException("The magic number cannot be null");
        }
        if (magicNumber.isEmpty()) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        }
        if (offset < 0L) {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
        this.magicNumbers = magicNumber.getBytes(Charset.defaultCharset());
        this.byteOffset = offset;
    }

    public MagicNumberFileFilter(byte[] magicNumber, long offset) {
        if (magicNumber == null) {
            throw new IllegalArgumentException("The magic number cannot be null");
        }
        if (magicNumber.length == 0) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        }
        if (offset < 0L) {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
        this.magicNumbers = new byte[magicNumber.length];
        System.arraycopy(magicNumber, 0, this.magicNumbers, 0, magicNumber.length);
        this.byteOffset = offset;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    @Override
    public boolean accept(File file) {
        block7: {
            if (file != null && file.isFile() && file.canRead()) {
                boolean bl;
                byte[] fileBytes;
                RandomAccessFile randomAccessFile;
                block6: {
                    randomAccessFile = null;
                    fileBytes = new byte[this.magicNumbers.length];
                    randomAccessFile = new RandomAccessFile(file, "r");
                    randomAccessFile.seek(this.byteOffset);
                    int read = randomAccessFile.read(fileBytes);
                    if (read == this.magicNumbers.length) break block6;
                    boolean bl2 = false;
                    IOUtils.closeQuietly((Closeable)randomAccessFile);
                    return bl2;
                }
                try {
                    bl = Arrays.equals(this.magicNumbers, fileBytes);
                } catch (IOException iOException) {
                    IOUtils.closeQuietly(randomAccessFile);
                    break block7;
                    catch (Throwable throwable) {
                        IOUtils.closeQuietly(randomAccessFile);
                        throw throwable;
                    }
                }
                IOUtils.closeQuietly((Closeable)randomAccessFile);
                return bl;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append("(");
        builder.append(new String(this.magicNumbers, Charset.defaultCharset()));
        builder.append(",");
        builder.append(this.byteOffset);
        builder.append(")");
        return builder.toString();
    }
}

