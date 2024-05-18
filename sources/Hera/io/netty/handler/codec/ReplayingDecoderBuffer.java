/*      */ package io.netty.handler.codec;
/*      */ 
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.ByteBufProcessor;
/*      */ import io.netty.buffer.SwappedByteBuf;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import io.netty.util.Signal;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.nio.charset.Charset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ final class ReplayingDecoderBuffer
/*      */   extends ByteBuf
/*      */ {
/*   39 */   private static final Signal REPLAY = ReplayingDecoder.REPLAY;
/*      */   
/*      */   private ByteBuf buffer;
/*      */   
/*      */   private boolean terminated;
/*      */   private SwappedByteBuf swapped;
/*   45 */   static final ReplayingDecoderBuffer EMPTY_BUFFER = new ReplayingDecoderBuffer(Unpooled.EMPTY_BUFFER);
/*      */   
/*      */   static {
/*   48 */     EMPTY_BUFFER.terminate();
/*      */   }
/*      */   
/*      */   ReplayingDecoderBuffer() {}
/*      */   
/*      */   ReplayingDecoderBuffer(ByteBuf buffer) {
/*   54 */     setCumulation(buffer);
/*      */   }
/*      */   
/*      */   void setCumulation(ByteBuf buffer) {
/*   58 */     this.buffer = buffer;
/*      */   }
/*      */   
/*      */   void terminate() {
/*   62 */     this.terminated = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int capacity() {
/*   67 */     if (this.terminated) {
/*   68 */       return this.buffer.capacity();
/*      */     }
/*   70 */     return Integer.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf capacity(int newCapacity) {
/*   76 */     reject();
/*   77 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxCapacity() {
/*   82 */     return capacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*   87 */     return this.buffer.alloc();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*   92 */     return this.buffer.isDirect();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasArray() {
/*   97 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] array() {
/*  102 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/*  107 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasMemoryAddress() {
/*  112 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public long memoryAddress() {
/*  117 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf clear() {
/*  122 */     reject();
/*  123 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/*  128 */     return (this == obj);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(ByteBuf buffer) {
/*  133 */     reject();
/*  134 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy() {
/*  139 */     reject();
/*  140 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy(int index, int length) {
/*  145 */     checkIndex(index, length);
/*  146 */     return this.buffer.copy(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardReadBytes() {
/*  151 */     reject();
/*  152 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf ensureWritable(int writableBytes) {
/*  157 */     reject();
/*  158 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int ensureWritable(int minWritableBytes, boolean force) {
/*  163 */     reject();
/*  164 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf duplicate() {
/*  169 */     reject();
/*  170 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int index) {
/*  175 */     checkIndex(index, 1);
/*  176 */     return this.buffer.getBoolean(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int index) {
/*  181 */     checkIndex(index, 1);
/*  182 */     return this.buffer.getByte(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getUnsignedByte(int index) {
/*  187 */     checkIndex(index, 1);
/*  188 */     return this.buffer.getUnsignedByte(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/*  193 */     checkIndex(index, length);
/*  194 */     this.buffer.getBytes(index, dst, dstIndex, length);
/*  195 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst) {
/*  200 */     checkIndex(index, dst.length);
/*  201 */     this.buffer.getBytes(index, dst);
/*  202 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/*  207 */     reject();
/*  208 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/*  213 */     checkIndex(index, length);
/*  214 */     this.buffer.getBytes(index, dst, dstIndex, length);
/*  215 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/*  220 */     reject();
/*  221 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst) {
/*  226 */     reject();
/*  227 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int index, GatheringByteChannel out, int length) {
/*  232 */     reject();
/*  233 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, OutputStream out, int length) {
/*  238 */     reject();
/*  239 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int index) {
/*  244 */     checkIndex(index, 4);
/*  245 */     return this.buffer.getInt(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedInt(int index) {
/*  250 */     checkIndex(index, 4);
/*  251 */     return this.buffer.getUnsignedInt(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int index) {
/*  256 */     checkIndex(index, 8);
/*  257 */     return this.buffer.getLong(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMedium(int index) {
/*  262 */     checkIndex(index, 3);
/*  263 */     return this.buffer.getMedium(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMedium(int index) {
/*  268 */     checkIndex(index, 3);
/*  269 */     return this.buffer.getUnsignedMedium(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int index) {
/*  274 */     checkIndex(index, 2);
/*  275 */     return this.buffer.getShort(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShort(int index) {
/*  280 */     checkIndex(index, 2);
/*  281 */     return this.buffer.getUnsignedShort(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(int index) {
/*  286 */     checkIndex(index, 2);
/*  287 */     return this.buffer.getChar(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int index) {
/*  292 */     checkIndex(index, 4);
/*  293 */     return this.buffer.getFloat(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int index) {
/*  298 */     checkIndex(index, 8);
/*  299 */     return this.buffer.getDouble(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  304 */     reject();
/*  305 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(int fromIndex, int toIndex, byte value) {
/*  310 */     if (fromIndex == toIndex) {
/*  311 */       return -1;
/*      */     }
/*      */     
/*  314 */     if (Math.max(fromIndex, toIndex) > this.buffer.writerIndex()) {
/*  315 */       throw REPLAY;
/*      */     }
/*      */     
/*  318 */     return this.buffer.indexOf(fromIndex, toIndex, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(byte value) {
/*  323 */     int bytes = this.buffer.bytesBefore(value);
/*  324 */     if (bytes < 0) {
/*  325 */       throw REPLAY;
/*      */     }
/*  327 */     return bytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int length, byte value) {
/*  332 */     int readerIndex = this.buffer.readerIndex();
/*  333 */     return bytesBefore(readerIndex, this.buffer.writerIndex() - readerIndex, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int index, int length, byte value) {
/*  338 */     int writerIndex = this.buffer.writerIndex();
/*  339 */     if (index >= writerIndex) {
/*  340 */       throw REPLAY;
/*      */     }
/*      */     
/*  343 */     if (index <= writerIndex - length) {
/*  344 */       return this.buffer.bytesBefore(index, length, value);
/*      */     }
/*      */     
/*  347 */     int res = this.buffer.bytesBefore(index, writerIndex - index, value);
/*  348 */     if (res < 0) {
/*  349 */       throw REPLAY;
/*      */     }
/*  351 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteBufProcessor processor) {
/*  357 */     int ret = this.buffer.forEachByte(processor);
/*  358 */     if (ret < 0) {
/*  359 */       throw REPLAY;
/*      */     }
/*  361 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByte(int index, int length, ByteBufProcessor processor) {
/*  367 */     int writerIndex = this.buffer.writerIndex();
/*  368 */     if (index >= writerIndex) {
/*  369 */       throw REPLAY;
/*      */     }
/*      */     
/*  372 */     if (index <= writerIndex - length) {
/*  373 */       return this.buffer.forEachByte(index, length, processor);
/*      */     }
/*      */     
/*  376 */     int ret = this.buffer.forEachByte(index, writerIndex - index, processor);
/*  377 */     if (ret < 0) {
/*  378 */       throw REPLAY;
/*      */     }
/*  380 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteBufProcessor processor) {
/*  386 */     if (this.terminated) {
/*  387 */       return this.buffer.forEachByteDesc(processor);
/*      */     }
/*  389 */     reject();
/*  390 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int index, int length, ByteBufProcessor processor) {
/*  396 */     if (index + length > this.buffer.writerIndex()) {
/*  397 */       throw REPLAY;
/*      */     }
/*      */     
/*  400 */     return this.buffer.forEachByteDesc(index, length, processor);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markReaderIndex() {
/*  405 */     this.buffer.markReaderIndex();
/*  406 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markWriterIndex() {
/*  411 */     reject();
/*  412 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteOrder order() {
/*  417 */     return this.buffer.order();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf order(ByteOrder endianness) {
/*  422 */     if (endianness == null) {
/*  423 */       throw new NullPointerException("endianness");
/*      */     }
/*  425 */     if (endianness == order()) {
/*  426 */       return this;
/*      */     }
/*      */     
/*  429 */     SwappedByteBuf swapped = this.swapped;
/*  430 */     if (swapped == null) {
/*  431 */       this.swapped = swapped = new SwappedByteBuf(this);
/*      */     }
/*  433 */     return (ByteBuf)swapped;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable() {
/*  438 */     return this.terminated ? this.buffer.isReadable() : true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable(int size) {
/*  443 */     return this.terminated ? this.buffer.isReadable(size) : true;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readableBytes() {
/*  448 */     if (this.terminated) {
/*  449 */       return this.buffer.readableBytes();
/*      */     }
/*  451 */     return Integer.MAX_VALUE - this.buffer.readerIndex();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean readBoolean() {
/*  457 */     checkReadableBytes(1);
/*  458 */     return this.buffer.readBoolean();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte readByte() {
/*  463 */     checkReadableBytes(1);
/*  464 */     return this.buffer.readByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readUnsignedByte() {
/*  469 */     checkReadableBytes(1);
/*  470 */     return this.buffer.readUnsignedByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/*  475 */     checkReadableBytes(length);
/*  476 */     this.buffer.readBytes(dst, dstIndex, length);
/*  477 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst) {
/*  482 */     checkReadableBytes(dst.length);
/*  483 */     this.buffer.readBytes(dst);
/*  484 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer dst) {
/*  489 */     reject();
/*  490 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/*  495 */     checkReadableBytes(length);
/*  496 */     this.buffer.readBytes(dst, dstIndex, length);
/*  497 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int length) {
/*  502 */     reject();
/*  503 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst) {
/*  508 */     checkReadableBytes(dst.writableBytes());
/*  509 */     this.buffer.readBytes(dst);
/*  510 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(GatheringByteChannel out, int length) {
/*  515 */     reject();
/*  516 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(int length) {
/*  521 */     checkReadableBytes(length);
/*  522 */     return this.buffer.readBytes(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readSlice(int length) {
/*  527 */     checkReadableBytes(length);
/*  528 */     return this.buffer.readSlice(length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(OutputStream out, int length) {
/*  533 */     reject();
/*  534 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readerIndex() {
/*  539 */     return this.buffer.readerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readerIndex(int readerIndex) {
/*  544 */     this.buffer.readerIndex(readerIndex);
/*  545 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt() {
/*  550 */     checkReadableBytes(4);
/*  551 */     return this.buffer.readInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedInt() {
/*  556 */     checkReadableBytes(4);
/*  557 */     return this.buffer.readUnsignedInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLong() {
/*  562 */     checkReadableBytes(8);
/*  563 */     return this.buffer.readLong();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMedium() {
/*  568 */     checkReadableBytes(3);
/*  569 */     return this.buffer.readMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMedium() {
/*  574 */     checkReadableBytes(3);
/*  575 */     return this.buffer.readUnsignedMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShort() {
/*  580 */     checkReadableBytes(2);
/*  581 */     return this.buffer.readShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShort() {
/*  586 */     checkReadableBytes(2);
/*  587 */     return this.buffer.readUnsignedShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public char readChar() {
/*  592 */     checkReadableBytes(2);
/*  593 */     return this.buffer.readChar();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() {
/*  598 */     checkReadableBytes(4);
/*  599 */     return this.buffer.readFloat();
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() {
/*  604 */     checkReadableBytes(8);
/*  605 */     return this.buffer.readDouble();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetReaderIndex() {
/*  610 */     this.buffer.resetReaderIndex();
/*  611 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetWriterIndex() {
/*  616 */     reject();
/*  617 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBoolean(int index, boolean value) {
/*  622 */     reject();
/*  623 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setByte(int index, int value) {
/*  628 */     reject();
/*  629 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/*  634 */     reject();
/*  635 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src) {
/*  640 */     reject();
/*  641 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuffer src) {
/*  646 */     reject();
/*  647 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/*  652 */     reject();
/*  653 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/*  658 */     reject();
/*  659 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src) {
/*  664 */     reject();
/*  665 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, InputStream in, int length) {
/*  670 */     reject();
/*  671 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setZero(int index, int length) {
/*  676 */     reject();
/*  677 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int index, ScatteringByteChannel in, int length) {
/*  682 */     reject();
/*  683 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIndex(int readerIndex, int writerIndex) {
/*  688 */     reject();
/*  689 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setInt(int index, int value) {
/*  694 */     reject();
/*  695 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLong(int index, long value) {
/*  700 */     reject();
/*  701 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMedium(int index, int value) {
/*  706 */     reject();
/*  707 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShort(int index, int value) {
/*  712 */     reject();
/*  713 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setChar(int index, int value) {
/*  718 */     reject();
/*  719 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setFloat(int index, float value) {
/*  724 */     reject();
/*  725 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setDouble(int index, double value) {
/*  730 */     reject();
/*  731 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf skipBytes(int length) {
/*  736 */     checkReadableBytes(length);
/*  737 */     this.buffer.skipBytes(length);
/*  738 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice() {
/*  743 */     reject();
/*  744 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice(int index, int length) {
/*  749 */     checkIndex(index, length);
/*  750 */     return this.buffer.slice(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public int nioBufferCount() {
/*  755 */     return this.buffer.nioBufferCount();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer() {
/*  760 */     reject();
/*  761 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer(int index, int length) {
/*  766 */     checkIndex(index, length);
/*  767 */     return this.buffer.nioBuffer(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/*  772 */     reject();
/*  773 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int index, int length) {
/*  778 */     checkIndex(index, length);
/*  779 */     return this.buffer.nioBuffers(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int index, int length) {
/*  784 */     checkIndex(index, length);
/*  785 */     return this.buffer.internalNioBuffer(index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(int index, int length, Charset charset) {
/*  790 */     checkIndex(index, length);
/*  791 */     return this.buffer.toString(index, length, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(Charset charsetName) {
/*  796 */     reject();
/*  797 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  802 */     return StringUtil.simpleClassName(this) + '(' + "ridx=" + readerIndex() + ", " + "widx=" + writerIndex() + ')';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWritable() {
/*  813 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable(int size) {
/*  818 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writableBytes() {
/*  823 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxWritableBytes() {
/*  828 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBoolean(boolean value) {
/*  833 */     reject();
/*  834 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeByte(int value) {
/*  839 */     reject();
/*  840 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/*  845 */     reject();
/*  846 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src) {
/*  851 */     reject();
/*  852 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer src) {
/*  857 */     reject();
/*  858 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/*  863 */     reject();
/*  864 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int length) {
/*  869 */     reject();
/*  870 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src) {
/*  875 */     reject();
/*  876 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(InputStream in, int length) {
/*  881 */     reject();
/*  882 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel in, int length) {
/*  887 */     reject();
/*  888 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeInt(int value) {
/*  893 */     reject();
/*  894 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLong(long value) {
/*  899 */     reject();
/*  900 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMedium(int value) {
/*  905 */     reject();
/*  906 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeZero(int length) {
/*  911 */     reject();
/*  912 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writerIndex() {
/*  917 */     return this.buffer.writerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writerIndex(int writerIndex) {
/*  922 */     reject();
/*  923 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShort(int value) {
/*  928 */     reject();
/*  929 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeChar(int value) {
/*  934 */     reject();
/*  935 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeFloat(float value) {
/*  940 */     reject();
/*  941 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeDouble(double value) {
/*  946 */     reject();
/*  947 */     return this;
/*      */   }
/*      */   
/*      */   private void checkIndex(int index, int length) {
/*  951 */     if (index + length > this.buffer.writerIndex()) {
/*  952 */       throw REPLAY;
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkReadableBytes(int readableBytes) {
/*  957 */     if (this.buffer.readableBytes() < readableBytes) {
/*  958 */       throw REPLAY;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardSomeReadBytes() {
/*  964 */     reject();
/*  965 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int refCnt() {
/*  970 */     return this.buffer.refCnt();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain() {
/*  975 */     reject();
/*  976 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain(int increment) {
/*  981 */     reject();
/*  982 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release() {
/*  987 */     reject();
/*  988 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release(int decrement) {
/*  993 */     reject();
/*  994 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf unwrap() {
/*  999 */     reject();
/* 1000 */     return this;
/*      */   }
/*      */   
/*      */   private static void reject() {
/* 1004 */     throw new UnsupportedOperationException("not a replayable operation");
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\ReplayingDecoderBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */