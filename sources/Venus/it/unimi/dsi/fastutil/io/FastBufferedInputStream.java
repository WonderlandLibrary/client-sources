/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.io;

import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.io.MeasurableInputStream;
import it.unimi.dsi.fastutil.io.MeasurableStream;
import it.unimi.dsi.fastutil.io.RepositionableStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileChannel;
import java.util.EnumSet;

public class FastBufferedInputStream
extends MeasurableInputStream
implements RepositionableStream {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final EnumSet<LineTerminator> ALL_TERMINATORS = EnumSet.allOf(LineTerminator.class);
    protected InputStream is;
    protected byte[] buffer;
    protected int pos;
    protected long readBytes;
    protected int avail;
    private FileChannel fileChannel;
    private RepositionableStream repositionableStream;
    private MeasurableStream measurableStream;

    private static int ensureBufferSize(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal buffer size: " + n);
        }
        return n;
    }

    public FastBufferedInputStream(InputStream inputStream, byte[] byArray) {
        this.is = inputStream;
        FastBufferedInputStream.ensureBufferSize(byArray.length);
        this.buffer = byArray;
        if (inputStream instanceof RepositionableStream) {
            this.repositionableStream = (RepositionableStream)((Object)inputStream);
        }
        if (inputStream instanceof MeasurableStream) {
            this.measurableStream = (MeasurableStream)((Object)inputStream);
        }
        if (this.repositionableStream == null) {
            try {
                this.fileChannel = (FileChannel)inputStream.getClass().getMethod("getChannel", new Class[0]).invoke(inputStream, new Object[0]);
            } catch (IllegalAccessException illegalAccessException) {
            } catch (IllegalArgumentException illegalArgumentException) {
            } catch (NoSuchMethodException noSuchMethodException) {
            } catch (InvocationTargetException invocationTargetException) {
            } catch (ClassCastException classCastException) {
                // empty catch block
            }
        }
    }

    public FastBufferedInputStream(InputStream inputStream, int n) {
        this(inputStream, new byte[FastBufferedInputStream.ensureBufferSize(n)]);
    }

    public FastBufferedInputStream(InputStream inputStream) {
        this(inputStream, 8192);
    }

    protected boolean noMoreCharacters() throws IOException {
        if (this.avail == 0) {
            this.avail = this.is.read(this.buffer);
            if (this.avail <= 0) {
                this.avail = 0;
                return false;
            }
            this.pos = 0;
        }
        return true;
    }

    @Override
    public int read() throws IOException {
        if (this.noMoreCharacters()) {
            return 1;
        }
        --this.avail;
        ++this.readBytes;
        return this.buffer[this.pos++] & 0xFF;
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        if (n2 <= this.avail) {
            System.arraycopy(this.buffer, this.pos, byArray, n, n2);
            this.pos += n2;
            this.avail -= n2;
            this.readBytes += (long)n2;
            return n2;
        }
        int n3 = this.avail;
        System.arraycopy(this.buffer, this.pos, byArray, n, n3);
        this.avail = 0;
        this.pos = 0;
        this.readBytes += (long)n3;
        if (n2 > this.buffer.length) {
            int n4 = this.is.read(byArray, n + n3, n2 - n3);
            if (n4 > 0) {
                this.readBytes += (long)n4;
            }
            return n4 < 0 ? (n3 == 0 ? -1 : n3) : n4 + n3;
        }
        if (this.noMoreCharacters()) {
            return n3 == 0 ? -1 : n3;
        }
        int n5 = Math.min(n2 - n3, this.avail);
        this.readBytes += (long)n5;
        System.arraycopy(this.buffer, 0, byArray, n + n3, n5);
        this.pos = n5;
        this.avail -= n5;
        return n5 + n3;
    }

    public int readLine(byte[] byArray) throws IOException {
        return this.readLine(byArray, 0, byArray.length, ALL_TERMINATORS);
    }

    public int readLine(byte[] byArray, EnumSet<LineTerminator> enumSet) throws IOException {
        return this.readLine(byArray, 0, byArray.length, enumSet);
    }

    public int readLine(byte[] byArray, int n, int n2) throws IOException {
        return this.readLine(byArray, n, n2, ALL_TERMINATORS);
    }

    /*
     * Unable to fully structure code
     */
    public int readLine(byte[] var1_1, int var2_2, int var3_3, EnumSet<LineTerminator> var4_4) throws IOException {
        ByteArrays.ensureOffsetLength(var1_1, var2_2, var3_3);
        if (var3_3 == 0) {
            return 1;
        }
        if (this.noMoreCharacters()) {
            return 1;
        }
        var6_5 = 0;
        var7_6 = var3_3;
        var8_7 = 0;
        while (true) lbl-1000:
        // 6 sources

        {
            for (var5_8 = 0; var5_8 < this.avail && var5_8 < var7_6 && (var6_5 = this.buffer[this.pos + var5_8]) != 10 && var6_5 != 13; ++var5_8) {
            }
            System.arraycopy(this.buffer, this.pos, var1_1, var2_2 + var8_7, var5_8);
            this.pos += var5_8;
            this.avail -= var5_8;
            var8_7 += var5_8;
            if ((var7_6 -= var5_8) == 0) {
                this.readBytes += (long)var8_7;
                return var8_7;
            }
            if (this.avail > 0) {
                if (var6_5 == 10) {
                    ++this.pos;
                    --this.avail;
                    if (var4_4.contains((Object)LineTerminator.LF)) {
                        this.readBytes += (long)(var8_7 + 1);
                        return var8_7;
                    }
                    var1_1[var2_2 + var8_7++] = 10;
                    --var7_6;
                    ** continue;
                }
                if (var6_5 != 13) ** continue;
                ++this.pos;
                --this.avail;
                if (var4_4.contains((Object)LineTerminator.CR_LF)) {
                    if (this.avail > 0) {
                        if (this.buffer[this.pos] == 10) {
                            ++this.pos;
                            --this.avail;
                            this.readBytes += (long)(var8_7 + 2);
                            return var8_7;
                        }
                    } else {
                        if (this.noMoreCharacters()) {
                            if (!var4_4.contains((Object)LineTerminator.CR)) {
                                var1_1[var2_2 + var8_7++] = 13;
                                --var7_6;
                                this.readBytes += (long)var8_7;
                            } else {
                                this.readBytes += (long)(var8_7 + 1);
                            }
                            return var8_7;
                        }
                        if (this.buffer[0] == 10) {
                            ++this.pos;
                            --this.avail;
                            this.readBytes += (long)(var8_7 + 2);
                            return var8_7;
                        }
                    }
                }
                if (var4_4.contains((Object)LineTerminator.CR)) {
                    this.readBytes += (long)(var8_7 + 1);
                    return var8_7;
                }
                var1_1[var2_2 + var8_7++] = 13;
                --var7_6;
                ** continue;
            }
            if (this.noMoreCharacters()) break;
        }
        this.readBytes += (long)var8_7;
        return var8_7;
    }

    @Override
    public void position(long l) throws IOException {
        long l2 = this.readBytes;
        if (l <= l2 + (long)this.avail && l >= l2 - (long)this.pos) {
            this.pos = (int)((long)this.pos + (l - l2));
            this.avail = (int)((long)this.avail - (l - l2));
            this.readBytes = l;
            return;
        }
        if (this.repositionableStream != null) {
            this.repositionableStream.position(l);
        } else if (this.fileChannel != null) {
            this.fileChannel.position(l);
        } else {
            throw new UnsupportedOperationException("position() can only be called if the underlying byte stream implements the RepositionableStream interface or if the getChannel() method of the underlying byte stream exists and returns a FileChannel");
        }
        this.readBytes = l;
        this.pos = 0;
        this.avail = 0;
    }

    @Override
    public long position() throws IOException {
        return this.readBytes;
    }

    @Override
    public long length() throws IOException {
        if (this.measurableStream != null) {
            return this.measurableStream.length();
        }
        if (this.fileChannel != null) {
            return this.fileChannel.size();
        }
        throw new UnsupportedOperationException();
    }

    private long skipByReading(long l) throws IOException {
        long l2;
        int n;
        for (l2 = l; l2 > 0L && (n = this.is.read(this.buffer, 0, (int)Math.min((long)this.buffer.length, l2))) > 0; l2 -= (long)n) {
        }
        return l - l2;
    }

    @Override
    public long skip(long l) throws IOException {
        if (l <= (long)this.avail) {
            int n = (int)l;
            this.pos += n;
            this.avail -= n;
            this.readBytes += l;
            return l;
        }
        long l2 = l - (long)this.avail;
        long l3 = 0L;
        this.avail = 0;
        while (l2 != 0L && (l3 = this.is == System.in ? this.skipByReading(l2) : this.is.skip(l2)) < l2) {
            if (l3 == 0L) {
                if (this.is.read() == -1) break;
                --l2;
                continue;
            }
            l2 -= l3;
        }
        long l4 = l - (l2 - l3);
        this.readBytes += l4;
        return l4;
    }

    @Override
    public int available() throws IOException {
        return (int)Math.min((long)this.is.available() + (long)this.avail, Integer.MAX_VALUE);
    }

    @Override
    public void close() throws IOException {
        if (this.is == null) {
            return;
        }
        if (this.is != System.in) {
            this.is.close();
        }
        this.is = null;
        this.buffer = null;
    }

    public void flush() {
        if (this.is == null) {
            return;
        }
        this.readBytes += (long)this.avail;
        this.pos = 0;
        this.avail = 0;
    }

    @Override
    @Deprecated
    public void reset() {
        this.flush();
    }

    public static enum LineTerminator {
        CR,
        LF,
        CR_LF;

    }
}

