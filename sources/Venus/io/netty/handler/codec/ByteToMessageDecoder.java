/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.handler.codec.CodecOutputList;
import io.netty.handler.codec.DecoderException;
import io.netty.util.internal.StringUtil;
import java.util.List;

public abstract class ByteToMessageDecoder
extends ChannelInboundHandlerAdapter {
    public static final Cumulator MERGE_CUMULATOR = new Cumulator(){

        @Override
        public ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
            ByteBuf byteBuf3 = byteBuf.writerIndex() > byteBuf.maxCapacity() - byteBuf2.readableBytes() || byteBuf.refCnt() > 1 || byteBuf.isReadOnly() ? ByteToMessageDecoder.expandCumulation(byteBufAllocator, byteBuf, byteBuf2.readableBytes()) : byteBuf;
            byteBuf3.writeBytes(byteBuf2);
            byteBuf2.release();
            return byteBuf3;
        }
    };
    public static final Cumulator COMPOSITE_CUMULATOR = new Cumulator(){

        @Override
        public ByteBuf cumulate(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
            ByteBuf byteBuf3;
            if (byteBuf.refCnt() > 1) {
                byteBuf3 = ByteToMessageDecoder.expandCumulation(byteBufAllocator, byteBuf, byteBuf2.readableBytes());
                byteBuf3.writeBytes(byteBuf2);
                byteBuf2.release();
            } else {
                CompositeByteBuf compositeByteBuf;
                if (byteBuf instanceof CompositeByteBuf) {
                    compositeByteBuf = (CompositeByteBuf)byteBuf;
                } else {
                    compositeByteBuf = byteBufAllocator.compositeBuffer(Integer.MAX_VALUE);
                    compositeByteBuf.addComponent(true, byteBuf);
                }
                compositeByteBuf.addComponent(true, byteBuf2);
                byteBuf3 = compositeByteBuf;
            }
            return byteBuf3;
        }
    };
    private static final byte STATE_INIT = 0;
    private static final byte STATE_CALLING_CHILD_DECODE = 1;
    private static final byte STATE_HANDLER_REMOVED_PENDING = 2;
    ByteBuf cumulation;
    private Cumulator cumulator = MERGE_CUMULATOR;
    private boolean singleDecode;
    private boolean decodeWasNull;
    private boolean first;
    private byte decodeState = 0;
    private int discardAfterReads = 16;
    private int numReads;

    protected ByteToMessageDecoder() {
        this.ensureNotSharable();
    }

    public void setSingleDecode(boolean bl) {
        this.singleDecode = bl;
    }

    public boolean isSingleDecode() {
        return this.singleDecode;
    }

    public void setCumulator(Cumulator cumulator) {
        if (cumulator == null) {
            throw new NullPointerException("cumulator");
        }
        this.cumulator = cumulator;
    }

    public void setDiscardAfterReads(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("discardAfterReads must be > 0");
        }
        this.discardAfterReads = n;
    }

    protected int actualReadableBytes() {
        return this.internalBuffer().readableBytes();
    }

    protected ByteBuf internalBuffer() {
        if (this.cumulation != null) {
            return this.cumulation;
        }
        return Unpooled.EMPTY_BUFFER;
    }

    @Override
    public final void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.decodeState == 1) {
            this.decodeState = (byte)2;
            return;
        }
        ByteBuf byteBuf = this.cumulation;
        if (byteBuf != null) {
            this.cumulation = null;
            int n = byteBuf.readableBytes();
            if (n > 0) {
                ByteBuf byteBuf2 = byteBuf.readBytes(n);
                byteBuf.release();
                channelHandlerContext.fireChannelRead(byteBuf2);
            } else {
                byteBuf.release();
            }
            this.numReads = 0;
            channelHandlerContext.fireChannelReadComplete();
        }
        this.handlerRemoved0(channelHandlerContext);
    }

    protected void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (!(object instanceof ByteBuf)) {
            channelHandlerContext.fireChannelRead(object);
            return;
        }
        CodecOutputList codecOutputList = CodecOutputList.newInstance();
        try {
            ByteBuf byteBuf = (ByteBuf)object;
            this.first = this.cumulation == null;
            this.cumulation = this.first ? byteBuf : this.cumulator.cumulate(channelHandlerContext.alloc(), this.cumulation, byteBuf);
            this.callDecode(channelHandlerContext, this.cumulation, codecOutputList);
        } catch (DecoderException decoderException) {
            try {
                throw decoderException;
                catch (Exception exception) {
                    throw new DecoderException(exception);
                }
            } catch (Throwable throwable) {
                if (this.cumulation != null && !this.cumulation.isReadable()) {
                    this.numReads = 0;
                    this.cumulation.release();
                    this.cumulation = null;
                } else if (++this.numReads >= this.discardAfterReads) {
                    this.numReads = 0;
                    this.discardSomeReadBytes();
                }
                int n = codecOutputList.size();
                this.decodeWasNull = !codecOutputList.insertSinceRecycled();
                ByteToMessageDecoder.fireChannelRead(channelHandlerContext, codecOutputList, n);
                codecOutputList.recycle();
                throw throwable;
            }
        }
        if (this.cumulation != null && !this.cumulation.isReadable()) {
            this.numReads = 0;
            this.cumulation.release();
            this.cumulation = null;
        } else if (++this.numReads >= this.discardAfterReads) {
            this.numReads = 0;
            this.discardSomeReadBytes();
        }
        int n = codecOutputList.size();
        this.decodeWasNull = !codecOutputList.insertSinceRecycled();
        ByteToMessageDecoder.fireChannelRead(channelHandlerContext, codecOutputList, n);
        codecOutputList.recycle();
        return;
    }

    static void fireChannelRead(ChannelHandlerContext channelHandlerContext, List<Object> list, int n) {
        if (list instanceof CodecOutputList) {
            ByteToMessageDecoder.fireChannelRead(channelHandlerContext, (CodecOutputList)list, n);
        } else {
            for (int i = 0; i < n; ++i) {
                channelHandlerContext.fireChannelRead(list.get(i));
            }
        }
    }

    static void fireChannelRead(ChannelHandlerContext channelHandlerContext, CodecOutputList codecOutputList, int n) {
        for (int i = 0; i < n; ++i) {
            channelHandlerContext.fireChannelRead(codecOutputList.getUnsafe(i));
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.numReads = 0;
        this.discardSomeReadBytes();
        if (this.decodeWasNull) {
            this.decodeWasNull = false;
            if (!channelHandlerContext.channel().config().isAutoRead()) {
                channelHandlerContext.read();
            }
        }
        channelHandlerContext.fireChannelReadComplete();
    }

    protected final void discardSomeReadBytes() {
        if (this.cumulation != null && !this.first && this.cumulation.refCnt() == 1) {
            this.cumulation.discardSomeReadBytes();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.channelInputClosed(channelHandlerContext, true);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (object instanceof ChannelInputShutdownEvent) {
            this.channelInputClosed(channelHandlerContext, false);
        }
        super.userEventTriggered(channelHandlerContext, object);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void channelInputClosed(ChannelHandlerContext channelHandlerContext, boolean bl) throws Exception {
        CodecOutputList codecOutputList = CodecOutputList.newInstance();
        try {
            this.channelInputClosed(channelHandlerContext, codecOutputList);
        } catch (DecoderException decoderException) {
            throw decoderException;
        } catch (Exception exception) {
            throw new DecoderException(exception);
        } finally {
            try {
                if (this.cumulation != null) {
                    this.cumulation.release();
                    this.cumulation = null;
                }
                int n = codecOutputList.size();
                ByteToMessageDecoder.fireChannelRead(channelHandlerContext, codecOutputList, n);
                if (n > 0) {
                    channelHandlerContext.fireChannelReadComplete();
                }
                if (bl) {
                    channelHandlerContext.fireChannelInactive();
                }
            } finally {
                codecOutputList.recycle();
            }
        }
    }

    void channelInputClosed(ChannelHandlerContext channelHandlerContext, List<Object> list) throws Exception {
        if (this.cumulation != null) {
            this.callDecode(channelHandlerContext, this.cumulation, list);
            this.decodeLast(channelHandlerContext, this.cumulation, list);
        } else {
            this.decodeLast(channelHandlerContext, Unpooled.EMPTY_BUFFER, list);
        }
    }

    protected void callDecode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        try {
            while (byteBuf.isReadable()) {
                int n = list.size();
                if (n > 0) {
                    ByteToMessageDecoder.fireChannelRead(channelHandlerContext, list, n);
                    list.clear();
                    if (channelHandlerContext.isRemoved()) break;
                    n = 0;
                }
                int n2 = byteBuf.readableBytes();
                this.decodeRemovalReentryProtection(channelHandlerContext, byteBuf, list);
                if (!channelHandlerContext.isRemoved()) {
                    if (n == list.size()) {
                        if (n2 != byteBuf.readableBytes()) continue;
                    } else {
                        if (n2 == byteBuf.readableBytes()) {
                            throw new DecoderException(StringUtil.simpleClassName(this.getClass()) + ".decode() did not read anything but decoded a message.");
                        }
                        if (!this.isSingleDecode()) continue;
                    }
                }
                break;
            }
        } catch (DecoderException decoderException) {
            throw decoderException;
        } catch (Exception exception) {
            throw new DecoderException(exception);
        }
    }

    protected abstract void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final void decodeRemovalReentryProtection(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        boolean bl;
        this.decodeState = 1;
        try {
            this.decode(channelHandlerContext, byteBuf, list);
            bl = this.decodeState == 2;
        } catch (Throwable throwable) {
            boolean bl2 = this.decodeState == 2;
            this.decodeState = 0;
            if (bl2) {
                this.handlerRemoved(channelHandlerContext);
            }
            throw throwable;
        }
        this.decodeState = 0;
        if (bl) {
            this.handlerRemoved(channelHandlerContext);
        }
    }

    protected void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.isReadable()) {
            this.decodeRemovalReentryProtection(channelHandlerContext, byteBuf, list);
        }
    }

    static ByteBuf expandCumulation(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, int n) {
        ByteBuf byteBuf2 = byteBuf;
        byteBuf = byteBufAllocator.buffer(byteBuf2.readableBytes() + n);
        byteBuf.writeBytes(byteBuf2);
        byteBuf2.release();
        return byteBuf;
    }

    public static interface Cumulator {
        public ByteBuf cumulate(ByteBufAllocator var1, ByteBuf var2, ByteBuf var3);
    }
}

