/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.apache.commons.io.Charsets;

public class ReversedLinesFileReader
implements Closeable {
    private final int blockSize;
    private final Charset encoding;
    private final RandomAccessFile randomAccessFile;
    private final long totalByteLength;
    private final long totalBlockCount;
    private final byte[][] newLineSequences;
    private final int avoidNewlineSplitBufferSize;
    private final int byteDecrement;
    private FilePart currentFilePart;
    private boolean trailingNewlineOfFileSkipped = false;

    @Deprecated
    public ReversedLinesFileReader(File file) throws IOException {
        this(file, 4096, Charset.defaultCharset());
    }

    public ReversedLinesFileReader(File file, Charset charset) throws IOException {
        this(file, 4096, charset);
    }

    public ReversedLinesFileReader(File file, int n, Charset charset) throws IOException {
        this.blockSize = n;
        this.encoding = charset;
        Charset charset2 = Charsets.toCharset(charset);
        CharsetEncoder charsetEncoder = charset2.newEncoder();
        float f = charsetEncoder.maxBytesPerChar();
        if (f == 1.0f) {
            this.byteDecrement = 1;
        } else if (charset2 == Charsets.UTF_8) {
            this.byteDecrement = 1;
        } else if (charset2 == Charset.forName("Shift_JIS") || charset2 == Charset.forName("windows-31j") || charset2 == Charset.forName("x-windows-949") || charset2 == Charset.forName("gbk") || charset2 == Charset.forName("x-windows-950")) {
            this.byteDecrement = 1;
        } else if (charset2 == Charsets.UTF_16BE || charset2 == Charsets.UTF_16LE) {
            this.byteDecrement = 2;
        } else {
            if (charset2 == Charsets.UTF_16) {
                throw new UnsupportedEncodingException("For UTF-16, you need to specify the byte order (use UTF-16BE or UTF-16LE)");
            }
            throw new UnsupportedEncodingException("Encoding " + charset + " is not supported yet (feel free to " + "submit a patch)");
        }
        this.newLineSequences = new byte[][]{"\r\n".getBytes(charset), "\n".getBytes(charset), "\r".getBytes(charset)};
        this.avoidNewlineSplitBufferSize = this.newLineSequences[0].length;
        this.randomAccessFile = new RandomAccessFile(file, "r");
        this.totalByteLength = this.randomAccessFile.length();
        int n2 = (int)(this.totalByteLength % (long)n);
        if (n2 > 0) {
            this.totalBlockCount = this.totalByteLength / (long)n + 1L;
        } else {
            this.totalBlockCount = this.totalByteLength / (long)n;
            if (this.totalByteLength > 0L) {
                n2 = n;
            }
        }
        this.currentFilePart = new FilePart(this, this.totalBlockCount, n2, null, null);
    }

    public ReversedLinesFileReader(File file, int n, String string) throws IOException {
        this(file, n, Charsets.toCharset(string));
    }

    public String readLine() throws IOException {
        String string = FilePart.access$100(this.currentFilePart);
        while (string == null) {
            this.currentFilePart = FilePart.access$200(this.currentFilePart);
            if (this.currentFilePart == null) break;
            string = FilePart.access$100(this.currentFilePart);
        }
        if ("".equals(string) && !this.trailingNewlineOfFileSkipped) {
            this.trailingNewlineOfFileSkipped = true;
            string = this.readLine();
        }
        return string;
    }

    @Override
    public void close() throws IOException {
        this.randomAccessFile.close();
    }

    static int access$300(ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.blockSize;
    }

    static RandomAccessFile access$400(ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.randomAccessFile;
    }

    static Charset access$500(ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.encoding;
    }

    static int access$600(ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.avoidNewlineSplitBufferSize;
    }

    static int access$700(ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.byteDecrement;
    }

    static byte[][] access$800(ReversedLinesFileReader reversedLinesFileReader) {
        return reversedLinesFileReader.newLineSequences;
    }

    static class 1 {
    }

    private class FilePart {
        private final long no;
        private final byte[] data;
        private byte[] leftOver;
        private int currentLastBytePos;
        final ReversedLinesFileReader this$0;

        private FilePart(ReversedLinesFileReader reversedLinesFileReader, long l, int n, byte[] byArray) throws IOException {
            this.this$0 = reversedLinesFileReader;
            this.no = l;
            int n2 = n + (byArray != null ? byArray.length : 0);
            this.data = new byte[n2];
            long l2 = (l - 1L) * (long)ReversedLinesFileReader.access$300(reversedLinesFileReader);
            if (l > 0L) {
                ReversedLinesFileReader.access$400(reversedLinesFileReader).seek(l2);
                int n3 = ReversedLinesFileReader.access$400(reversedLinesFileReader).read(this.data, 0, n);
                if (n3 != n) {
                    throw new IllegalStateException("Count of requested bytes and actually read bytes don't match");
                }
            }
            if (byArray != null) {
                System.arraycopy(byArray, 0, this.data, n, byArray.length);
            }
            this.currentLastBytePos = this.data.length - 1;
            this.leftOver = null;
        }

        private FilePart rollOver() throws IOException {
            if (this.currentLastBytePos > -1) {
                throw new IllegalStateException("Current currentLastCharPos unexpectedly positive... last readLine() should have returned something! currentLastCharPos=" + this.currentLastBytePos);
            }
            if (this.no > 1L) {
                return new FilePart(this.this$0, this.no - 1L, ReversedLinesFileReader.access$300(this.this$0), this.leftOver);
            }
            if (this.leftOver != null) {
                throw new IllegalStateException("Unexpected leftover of the last block: leftOverOfThisFilePart=" + new String(this.leftOver, ReversedLinesFileReader.access$500(this.this$0)));
            }
            return null;
        }

        private String readLine() throws IOException {
            String string = null;
            boolean bl = this.no == 1L;
            int n = this.currentLastBytePos;
            while (n > -1) {
                if (!bl && n < ReversedLinesFileReader.access$600(this.this$0)) {
                    this.createLeftOver();
                    break;
                }
                int n2 = this.getNewLineMatchByteCount(this.data, n);
                if (n2 > 0) {
                    int n3 = n + 1;
                    int n4 = this.currentLastBytePos - n3 + 1;
                    if (n4 < 0) {
                        throw new IllegalStateException("Unexpected negative line length=" + n4);
                    }
                    byte[] byArray = new byte[n4];
                    System.arraycopy(this.data, n3, byArray, 0, n4);
                    string = new String(byArray, ReversedLinesFileReader.access$500(this.this$0));
                    this.currentLastBytePos = n - n2;
                    break;
                }
                if ((n -= ReversedLinesFileReader.access$700(this.this$0)) >= 0) continue;
                this.createLeftOver();
                break;
            }
            if (bl && this.leftOver != null) {
                string = new String(this.leftOver, ReversedLinesFileReader.access$500(this.this$0));
                this.leftOver = null;
            }
            return string;
        }

        private void createLeftOver() {
            int n = this.currentLastBytePos + 1;
            if (n > 0) {
                this.leftOver = new byte[n];
                System.arraycopy(this.data, 0, this.leftOver, 0, n);
            } else {
                this.leftOver = null;
            }
            this.currentLastBytePos = -1;
        }

        private int getNewLineMatchByteCount(byte[] byArray, int n) {
            for (byte[] byArray2 : ReversedLinesFileReader.access$800(this.this$0)) {
                boolean bl = true;
                for (int i = byArray2.length - 1; i >= 0; --i) {
                    int n2 = n + i - (byArray2.length - 1);
                    bl &= n2 >= 0 && byArray[n2] == byArray2[i];
                }
                if (!bl) continue;
                return byArray2.length;
            }
            return 1;
        }

        FilePart(ReversedLinesFileReader reversedLinesFileReader, long l, int n, byte[] byArray, 1 var6_5) throws IOException {
            this(reversedLinesFileReader, l, n, byArray);
        }

        static String access$100(FilePart filePart) throws IOException {
            return filePart.readLine();
        }

        static FilePart access$200(FilePart filePart) throws IOException {
            return filePart.rollOver();
        }
    }
}

