/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.ProxyInputStream;

public class BOMInputStream
extends ProxyInputStream {
    private final boolean include;
    private final List<ByteOrderMark> boms;
    private ByteOrderMark byteOrderMark;
    private int[] firstBytes;
    private int fbLength;
    private int fbIndex;
    private int markFbIndex;
    private boolean markedAtStart;
    private static final Comparator<ByteOrderMark> ByteOrderMarkLengthComparator = new Comparator<ByteOrderMark>(){

        @Override
        public int compare(ByteOrderMark byteOrderMark, ByteOrderMark byteOrderMark2) {
            int n;
            int n2 = byteOrderMark.length();
            if (n2 > (n = byteOrderMark2.length())) {
                return 1;
            }
            if (n > n2) {
                return 0;
            }
            return 1;
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((ByteOrderMark)object, (ByteOrderMark)object2);
        }
    };

    public BOMInputStream(InputStream inputStream) {
        this(inputStream, false, ByteOrderMark.UTF_8);
    }

    public BOMInputStream(InputStream inputStream, boolean bl) {
        this(inputStream, bl, ByteOrderMark.UTF_8);
    }

    public BOMInputStream(InputStream inputStream, ByteOrderMark ... byteOrderMarkArray) {
        this(inputStream, false, byteOrderMarkArray);
    }

    public BOMInputStream(InputStream inputStream, boolean bl, ByteOrderMark ... byteOrderMarkArray) {
        super(inputStream);
        if (byteOrderMarkArray == null || byteOrderMarkArray.length == 0) {
            throw new IllegalArgumentException("No BOMs specified");
        }
        this.include = bl;
        Arrays.sort(byteOrderMarkArray, ByteOrderMarkLengthComparator);
        this.boms = Arrays.asList(byteOrderMarkArray);
    }

    public boolean hasBOM() throws IOException {
        return this.getBOM() != null;
    }

    public boolean hasBOM(ByteOrderMark byteOrderMark) throws IOException {
        if (!this.boms.contains(byteOrderMark)) {
            throw new IllegalArgumentException("Stream not configure to detect " + byteOrderMark);
        }
        return this.byteOrderMark != null && this.getBOM().equals(byteOrderMark);
    }

    public ByteOrderMark getBOM() throws IOException {
        if (this.firstBytes == null) {
            this.fbLength = 0;
            int n = this.boms.get(0).length();
            this.firstBytes = new int[n];
            for (int i = 0; i < this.firstBytes.length; ++i) {
                this.firstBytes[i] = this.in.read();
                ++this.fbLength;
                if (this.firstBytes[i] < 0) break;
            }
            this.byteOrderMark = this.find();
            if (this.byteOrderMark != null && !this.include) {
                if (this.byteOrderMark.length() < this.firstBytes.length) {
                    this.fbIndex = this.byteOrderMark.length();
                } else {
                    this.fbLength = 0;
                }
            }
        }
        return this.byteOrderMark;
    }

    public String getBOMCharsetName() throws IOException {
        this.getBOM();
        return this.byteOrderMark == null ? null : this.byteOrderMark.getCharsetName();
    }

    private int readFirstBytes() throws IOException {
        this.getBOM();
        return this.fbIndex < this.fbLength ? this.firstBytes[this.fbIndex++] : -1;
    }

    private ByteOrderMark find() {
        for (ByteOrderMark byteOrderMark : this.boms) {
            if (!this.matches(byteOrderMark)) continue;
            return byteOrderMark;
        }
        return null;
    }

    private boolean matches(ByteOrderMark byteOrderMark) {
        for (int i = 0; i < byteOrderMark.length(); ++i) {
            if (byteOrderMark.get(i) == this.firstBytes[i]) continue;
            return true;
        }
        return false;
    }

    @Override
    public int read() throws IOException {
        int n = this.readFirstBytes();
        return n >= 0 ? n : this.in.read();
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = 0;
        int n4 = 0;
        while (n2 > 0 && n4 >= 0) {
            n4 = this.readFirstBytes();
            if (n4 < 0) continue;
            byArray[n++] = (byte)(n4 & 0xFF);
            --n2;
            ++n3;
        }
        int n5 = this.in.read(byArray, n, n2);
        return n5 < 0 ? (n3 > 0 ? n3 : -1) : n3 + n5;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.read(byArray, 0, byArray.length);
    }

    @Override
    public synchronized void mark(int n) {
        this.markFbIndex = this.fbIndex;
        this.markedAtStart = this.firstBytes == null;
        this.in.mark(n);
    }

    @Override
    public synchronized void reset() throws IOException {
        this.fbIndex = this.markFbIndex;
        if (this.markedAtStart) {
            this.firstBytes = null;
        }
        this.in.reset();
    }

    @Override
    public long skip(long l) throws IOException {
        int n = 0;
        while (l > (long)n && this.readFirstBytes() >= 0) {
            ++n;
        }
        return this.in.skip(l - (long)n) + (long)n;
    }
}

