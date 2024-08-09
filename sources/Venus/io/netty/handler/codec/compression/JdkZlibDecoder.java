/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.compression.ByteBufChecksum;
import io.netty.handler.codec.compression.DecompressionException;
import io.netty.handler.codec.compression.ZlibDecoder;
import io.netty.handler.codec.compression.ZlibWrapper;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class JdkZlibDecoder
extends ZlibDecoder {
    private static final int FHCRC = 2;
    private static final int FEXTRA = 4;
    private static final int FNAME = 8;
    private static final int FCOMMENT = 16;
    private static final int FRESERVED = 224;
    private Inflater inflater;
    private final byte[] dictionary;
    private final ByteBufChecksum crc;
    private final boolean decompressConcatenated;
    private GzipState gzipState = GzipState.HEADER_START;
    private int flags = -1;
    private int xlen = -1;
    private volatile boolean finished;
    private boolean decideZlibOrNone;

    public JdkZlibDecoder() {
        this(ZlibWrapper.ZLIB, null, false);
    }

    public JdkZlibDecoder(byte[] byArray) {
        this(ZlibWrapper.ZLIB, byArray, false);
    }

    public JdkZlibDecoder(ZlibWrapper zlibWrapper) {
        this(zlibWrapper, null, false);
    }

    public JdkZlibDecoder(ZlibWrapper zlibWrapper, boolean bl) {
        this(zlibWrapper, null, bl);
    }

    public JdkZlibDecoder(boolean bl) {
        this(ZlibWrapper.GZIP, null, bl);
    }

    private JdkZlibDecoder(ZlibWrapper zlibWrapper, byte[] byArray, boolean bl) {
        if (zlibWrapper == null) {
            throw new NullPointerException("wrapper");
        }
        this.decompressConcatenated = bl;
        switch (zlibWrapper) {
            case GZIP: {
                this.inflater = new Inflater(true);
                this.crc = ByteBufChecksum.wrapChecksum(new CRC32());
                break;
            }
            case NONE: {
                this.inflater = new Inflater(true);
                this.crc = null;
                break;
            }
            case ZLIB: {
                this.inflater = new Inflater();
                this.crc = null;
                break;
            }
            case ZLIB_OR_NONE: {
                this.decideZlibOrNone = true;
                this.crc = null;
                break;
            }
            default: {
                throw new IllegalArgumentException("Only GZIP or ZLIB is supported, but you used " + (Object)((Object)zlibWrapper));
            }
        }
        this.dictionary = byArray;
    }

    @Override
    public boolean isClosed() {
        return this.finished;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.finished) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }
        int n = byteBuf.readableBytes();
        if (n == 0) {
            return;
        }
        if (this.decideZlibOrNone) {
            if (n < 2) {
                return;
            }
            boolean bl = !JdkZlibDecoder.looksLikeZlib(byteBuf.getShort(byteBuf.readerIndex()));
            this.inflater = new Inflater(bl);
            this.decideZlibOrNone = false;
        }
        if (this.crc != null) {
            switch (this.gzipState) {
                case FOOTER_START: {
                    if (this.readGZIPFooter(byteBuf)) {
                        this.finished = true;
                    }
                    return;
                }
            }
            if (this.gzipState != GzipState.HEADER_END && !this.readGZIPHeader(byteBuf)) {
                return;
            }
            n = byteBuf.readableBytes();
        }
        if (byteBuf.hasArray()) {
            this.inflater.setInput(byteBuf.array(), byteBuf.arrayOffset() + byteBuf.readerIndex(), n);
        } else {
            byte[] byArray = new byte[n];
            byteBuf.getBytes(byteBuf.readerIndex(), byArray);
            this.inflater.setInput(byArray);
        }
        ByteBuf byteBuf2 = channelHandlerContext.alloc().heapBuffer(this.inflater.getRemaining() << 1);
        try {
            boolean bl = false;
            while (!this.inflater.needsInput()) {
                byte[] byArray = byteBuf2.array();
                int n2 = byteBuf2.writerIndex();
                int n3 = byteBuf2.arrayOffset() + n2;
                int n4 = this.inflater.inflate(byArray, n3, byteBuf2.writableBytes());
                if (n4 > 0) {
                    byteBuf2.writerIndex(n2 + n4);
                    if (this.crc != null) {
                        this.crc.update(byArray, n3, n4);
                    }
                } else if (this.inflater.needsDictionary()) {
                    if (this.dictionary == null) {
                        throw new DecompressionException("decompression failure, unable to set dictionary as non was specified");
                    }
                    this.inflater.setDictionary(this.dictionary);
                }
                if (this.inflater.finished()) {
                    if (this.crc == null) {
                        this.finished = true;
                        break;
                    }
                    bl = true;
                    break;
                }
                byteBuf2.ensureWritable(this.inflater.getRemaining() << 1);
            }
            byteBuf.skipBytes(n - this.inflater.getRemaining());
            if (bl) {
                this.gzipState = GzipState.FOOTER_START;
                if (this.readGZIPFooter(byteBuf)) {
                    boolean bl2 = this.finished = !this.decompressConcatenated;
                    if (!this.finished) {
                        this.inflater.reset();
                        this.crc.reset();
                        this.gzipState = GzipState.HEADER_START;
                    }
                }
            }
        } catch (DataFormatException dataFormatException) {
            throw new DecompressionException("decompression failure", dataFormatException);
        } finally {
            if (byteBuf2.isReadable()) {
                list.add(byteBuf2);
            } else {
                byteBuf2.release();
            }
        }
    }

    @Override
    protected void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
        super.handlerRemoved0(channelHandlerContext);
        if (this.inflater != null) {
            this.inflater.end();
        }
    }

    private boolean readGZIPHeader(ByteBuf byteBuf) {
        switch (this.gzipState) {
            case HEADER_START: {
                if (byteBuf.readableBytes() < 10) {
                    return true;
                }
                byte by = byteBuf.readByte();
                byte by2 = byteBuf.readByte();
                if (by != 31) {
                    throw new DecompressionException("Input is not in the GZIP format");
                }
                this.crc.update(by);
                this.crc.update(by2);
                short s = byteBuf.readUnsignedByte();
                if (s != 8) {
                    throw new DecompressionException("Unsupported compression method " + s + " in the GZIP header");
                }
                this.crc.update(s);
                this.flags = byteBuf.readUnsignedByte();
                this.crc.update(this.flags);
                if ((this.flags & 0xE0) != 0) {
                    throw new DecompressionException("Reserved flags are set in the GZIP header");
                }
                this.crc.update(byteBuf, byteBuf.readerIndex(), 4);
                byteBuf.skipBytes(4);
                this.crc.update(byteBuf.readUnsignedByte());
                this.crc.update(byteBuf.readUnsignedByte());
                this.gzipState = GzipState.FLG_READ;
            }
            case FLG_READ: {
                short s;
                if ((this.flags & 4) != 0) {
                    if (byteBuf.readableBytes() < 2) {
                        return true;
                    }
                    s = byteBuf.readUnsignedByte();
                    short s2 = byteBuf.readUnsignedByte();
                    this.crc.update(s);
                    this.crc.update(s2);
                    this.xlen |= s << 8 | s2;
                }
                this.gzipState = GzipState.XLEN_READ;
            }
            case XLEN_READ: {
                if (this.xlen != -1) {
                    if (byteBuf.readableBytes() < this.xlen) {
                        return true;
                    }
                    this.crc.update(byteBuf, byteBuf.readerIndex(), this.xlen);
                    byteBuf.skipBytes(this.xlen);
                }
                this.gzipState = GzipState.SKIP_FNAME;
            }
            case SKIP_FNAME: {
                short s;
                if ((this.flags & 8) != 0) {
                    if (!byteBuf.isReadable()) {
                        return true;
                    }
                    do {
                        s = byteBuf.readUnsignedByte();
                        this.crc.update(s);
                    } while (s != 0 && byteBuf.isReadable());
                }
                this.gzipState = GzipState.SKIP_COMMENT;
            }
            case SKIP_COMMENT: {
                short s;
                if ((this.flags & 0x10) != 0) {
                    if (!byteBuf.isReadable()) {
                        return true;
                    }
                    do {
                        s = byteBuf.readUnsignedByte();
                        this.crc.update(s);
                    } while (s != 0 && byteBuf.isReadable());
                }
                this.gzipState = GzipState.PROCESS_FHCRC;
            }
            case PROCESS_FHCRC: {
                if ((this.flags & 2) != 0) {
                    if (byteBuf.readableBytes() < 4) {
                        return true;
                    }
                    this.verifyCrc(byteBuf);
                }
                this.crc.reset();
                this.gzipState = GzipState.HEADER_END;
            }
            case HEADER_END: {
                return false;
            }
        }
        throw new IllegalStateException();
    }

    private boolean readGZIPFooter(ByteBuf byteBuf) {
        int n;
        if (byteBuf.readableBytes() < 8) {
            return true;
        }
        this.verifyCrc(byteBuf);
        int n2 = 0;
        for (n = 0; n < 4; ++n) {
            n2 |= byteBuf.readUnsignedByte() << n * 8;
        }
        n = this.inflater.getTotalOut();
        if (n2 != n) {
            throw new DecompressionException("Number of bytes mismatch. Expected: " + n2 + ", Got: " + n);
        }
        return false;
    }

    private void verifyCrc(ByteBuf byteBuf) {
        long l = 0L;
        for (int i = 0; i < 4; ++i) {
            l |= (long)byteBuf.readUnsignedByte() << i * 8;
        }
        long l2 = this.crc.getValue();
        if (l != l2) {
            throw new DecompressionException("CRC value mismatch. Expected: " + l + ", Got: " + l2);
        }
    }

    private static boolean looksLikeZlib(short s) {
        return (s & 0x7800) == 30720 && s % 31 == 0;
    }

    private static enum GzipState {
        HEADER_START,
        HEADER_END,
        FLG_READ,
        XLEN_READ,
        SKIP_FNAME,
        SKIP_COMMENT,
        PROCESS_FHCRC,
        FOOTER_START;

    }
}

