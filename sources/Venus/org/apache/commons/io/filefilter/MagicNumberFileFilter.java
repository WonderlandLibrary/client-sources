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

    public MagicNumberFileFilter(byte[] byArray) {
        this(byArray, 0L);
    }

    public MagicNumberFileFilter(String string) {
        this(string, 0L);
    }

    public MagicNumberFileFilter(String string, long l) {
        if (string == null) {
            throw new IllegalArgumentException("The magic number cannot be null");
        }
        if (string.isEmpty()) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        }
        if (l < 0L) {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
        this.magicNumbers = string.getBytes(Charset.defaultCharset());
        this.byteOffset = l;
    }

    public MagicNumberFileFilter(byte[] byArray, long l) {
        if (byArray == null) {
            throw new IllegalArgumentException("The magic number cannot be null");
        }
        if (byArray.length == 0) {
            throw new IllegalArgumentException("The magic number must contain at least one byte");
        }
        if (l < 0L) {
            throw new IllegalArgumentException("The offset cannot be negative");
        }
        this.magicNumbers = new byte[byArray.length];
        System.arraycopy(byArray, 0, this.magicNumbers, 0, byArray.length);
        this.byteOffset = l;
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
                byte[] byArray;
                RandomAccessFile randomAccessFile;
                block6: {
                    randomAccessFile = null;
                    byArray = new byte[this.magicNumbers.length];
                    randomAccessFile = new RandomAccessFile(file, "r");
                    randomAccessFile.seek(this.byteOffset);
                    int n = randomAccessFile.read(byArray);
                    if (n == this.magicNumbers.length) break block6;
                    boolean bl2 = false;
                    IOUtils.closeQuietly((Closeable)randomAccessFile);
                    return bl2;
                }
                try {
                    bl = Arrays.equals(this.magicNumbers, byArray);
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
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.append("(");
        stringBuilder.append(new String(this.magicNumbers, Charset.defaultCharset()));
        stringBuilder.append(",");
        stringBuilder.append(this.byteOffset);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

