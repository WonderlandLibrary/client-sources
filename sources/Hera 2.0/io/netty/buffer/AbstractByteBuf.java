/*      */ package io.netty.buffer;
/*      */ 
/*      */ import io.netty.util.IllegalReferenceCountException;
/*      */ import io.netty.util.ResourceLeakDetector;
/*      */ import io.netty.util.internal.PlatformDependent;
/*      */ import io.netty.util.internal.StringUtil;
/*      */ import java.io.IOException;
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
/*      */ 
/*      */ 
/*      */ public abstract class AbstractByteBuf
/*      */   extends ByteBuf
/*      */ {
/*   38 */   static final ResourceLeakDetector<ByteBuf> leakDetector = new ResourceLeakDetector(ByteBuf.class);
/*      */   
/*      */   int readerIndex;
/*      */   
/*      */   int writerIndex;
/*      */   
/*      */   private int markedReaderIndex;
/*      */   private int markedWriterIndex;
/*      */   private int maxCapacity;
/*      */   private SwappedByteBuf swappedBuf;
/*      */   
/*      */   protected AbstractByteBuf(int maxCapacity) {
/*   50 */     if (maxCapacity < 0) {
/*   51 */       throw new IllegalArgumentException("maxCapacity: " + maxCapacity + " (expected: >= 0)");
/*      */     }
/*   53 */     this.maxCapacity = maxCapacity;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxCapacity() {
/*   58 */     return this.maxCapacity;
/*      */   }
/*      */   
/*      */   protected final void maxCapacity(int maxCapacity) {
/*   62 */     this.maxCapacity = maxCapacity;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readerIndex() {
/*   67 */     return this.readerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readerIndex(int readerIndex) {
/*   72 */     if (readerIndex < 0 || readerIndex > this.writerIndex) {
/*   73 */       throw new IndexOutOfBoundsException(String.format("readerIndex: %d (expected: 0 <= readerIndex <= writerIndex(%d))", new Object[] { Integer.valueOf(readerIndex), Integer.valueOf(this.writerIndex) }));
/*      */     }
/*      */     
/*   76 */     this.readerIndex = readerIndex;
/*   77 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writerIndex() {
/*   82 */     return this.writerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writerIndex(int writerIndex) {
/*   87 */     if (writerIndex < this.readerIndex || writerIndex > capacity()) {
/*   88 */       throw new IndexOutOfBoundsException(String.format("writerIndex: %d (expected: readerIndex(%d) <= writerIndex <= capacity(%d))", new Object[] { Integer.valueOf(writerIndex), Integer.valueOf(this.readerIndex), Integer.valueOf(capacity()) }));
/*      */     }
/*      */ 
/*      */     
/*   92 */     this.writerIndex = writerIndex;
/*   93 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIndex(int readerIndex, int writerIndex) {
/*   98 */     if (readerIndex < 0 || readerIndex > writerIndex || writerIndex > capacity()) {
/*   99 */       throw new IndexOutOfBoundsException(String.format("readerIndex: %d, writerIndex: %d (expected: 0 <= readerIndex <= writerIndex <= capacity(%d))", new Object[] { Integer.valueOf(readerIndex), Integer.valueOf(writerIndex), Integer.valueOf(capacity()) }));
/*      */     }
/*      */ 
/*      */     
/*  103 */     this.readerIndex = readerIndex;
/*  104 */     this.writerIndex = writerIndex;
/*  105 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf clear() {
/*  110 */     this.readerIndex = this.writerIndex = 0;
/*  111 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable() {
/*  116 */     return (this.writerIndex > this.readerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable(int numBytes) {
/*  121 */     return (this.writerIndex - this.readerIndex >= numBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable() {
/*  126 */     return (capacity() > this.writerIndex);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable(int numBytes) {
/*  131 */     return (capacity() - this.writerIndex >= numBytes);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readableBytes() {
/*  136 */     return this.writerIndex - this.readerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writableBytes() {
/*  141 */     return capacity() - this.writerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxWritableBytes() {
/*  146 */     return maxCapacity() - this.writerIndex;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markReaderIndex() {
/*  151 */     this.markedReaderIndex = this.readerIndex;
/*  152 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetReaderIndex() {
/*  157 */     readerIndex(this.markedReaderIndex);
/*  158 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markWriterIndex() {
/*  163 */     this.markedWriterIndex = this.writerIndex;
/*  164 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetWriterIndex() {
/*  169 */     this.writerIndex = this.markedWriterIndex;
/*  170 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardReadBytes() {
/*  175 */     ensureAccessible();
/*  176 */     if (this.readerIndex == 0) {
/*  177 */       return this;
/*      */     }
/*      */     
/*  180 */     if (this.readerIndex != this.writerIndex) {
/*  181 */       setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
/*  182 */       this.writerIndex -= this.readerIndex;
/*  183 */       adjustMarkers(this.readerIndex);
/*  184 */       this.readerIndex = 0;
/*      */     } else {
/*  186 */       adjustMarkers(this.readerIndex);
/*  187 */       this.writerIndex = this.readerIndex = 0;
/*      */     } 
/*  189 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardSomeReadBytes() {
/*  194 */     ensureAccessible();
/*  195 */     if (this.readerIndex == 0) {
/*  196 */       return this;
/*      */     }
/*      */     
/*  199 */     if (this.readerIndex == this.writerIndex) {
/*  200 */       adjustMarkers(this.readerIndex);
/*  201 */       this.writerIndex = this.readerIndex = 0;
/*  202 */       return this;
/*      */     } 
/*      */     
/*  205 */     if (this.readerIndex >= capacity() >>> 1) {
/*  206 */       setBytes(0, this, this.readerIndex, this.writerIndex - this.readerIndex);
/*  207 */       this.writerIndex -= this.readerIndex;
/*  208 */       adjustMarkers(this.readerIndex);
/*  209 */       this.readerIndex = 0;
/*      */     } 
/*  211 */     return this;
/*      */   }
/*      */   
/*      */   protected final void adjustMarkers(int decrement) {
/*  215 */     int markedReaderIndex = this.markedReaderIndex;
/*  216 */     if (markedReaderIndex <= decrement) {
/*  217 */       this.markedReaderIndex = 0;
/*  218 */       int markedWriterIndex = this.markedWriterIndex;
/*  219 */       if (markedWriterIndex <= decrement) {
/*  220 */         this.markedWriterIndex = 0;
/*      */       } else {
/*  222 */         this.markedWriterIndex = markedWriterIndex - decrement;
/*      */       } 
/*      */     } else {
/*  225 */       this.markedReaderIndex = markedReaderIndex - decrement;
/*  226 */       this.markedWriterIndex -= decrement;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf ensureWritable(int minWritableBytes) {
/*  232 */     if (minWritableBytes < 0) {
/*  233 */       throw new IllegalArgumentException(String.format("minWritableBytes: %d (expected: >= 0)", new Object[] { Integer.valueOf(minWritableBytes) }));
/*      */     }
/*      */ 
/*      */     
/*  237 */     if (minWritableBytes <= writableBytes()) {
/*  238 */       return this;
/*      */     }
/*      */     
/*  241 */     if (minWritableBytes > this.maxCapacity - this.writerIndex) {
/*  242 */       throw new IndexOutOfBoundsException(String.format("writerIndex(%d) + minWritableBytes(%d) exceeds maxCapacity(%d): %s", new Object[] { Integer.valueOf(this.writerIndex), Integer.valueOf(minWritableBytes), Integer.valueOf(this.maxCapacity), this }));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  248 */     int newCapacity = calculateNewCapacity(this.writerIndex + minWritableBytes);
/*      */ 
/*      */     
/*  251 */     capacity(newCapacity);
/*  252 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int ensureWritable(int minWritableBytes, boolean force) {
/*  257 */     if (minWritableBytes < 0) {
/*  258 */       throw new IllegalArgumentException(String.format("minWritableBytes: %d (expected: >= 0)", new Object[] { Integer.valueOf(minWritableBytes) }));
/*      */     }
/*      */ 
/*      */     
/*  262 */     if (minWritableBytes <= writableBytes()) {
/*  263 */       return 0;
/*      */     }
/*      */     
/*  266 */     if (minWritableBytes > this.maxCapacity - this.writerIndex && 
/*  267 */       force) {
/*  268 */       if (capacity() == maxCapacity()) {
/*  269 */         return 1;
/*      */       }
/*      */       
/*  272 */       capacity(maxCapacity());
/*  273 */       return 3;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  278 */     int newCapacity = calculateNewCapacity(this.writerIndex + minWritableBytes);
/*      */ 
/*      */     
/*  281 */     capacity(newCapacity);
/*  282 */     return 2;
/*      */   }
/*      */   
/*      */   private int calculateNewCapacity(int minNewCapacity) {
/*  286 */     int maxCapacity = this.maxCapacity;
/*  287 */     int threshold = 4194304;
/*      */     
/*  289 */     if (minNewCapacity == 4194304) {
/*  290 */       return 4194304;
/*      */     }
/*      */ 
/*      */     
/*  294 */     if (minNewCapacity > 4194304) {
/*  295 */       int i = minNewCapacity / 4194304 * 4194304;
/*  296 */       if (i > maxCapacity - 4194304) {
/*  297 */         i = maxCapacity;
/*      */       } else {
/*  299 */         i += 4194304;
/*      */       } 
/*  301 */       return i;
/*      */     } 
/*      */ 
/*      */     
/*  305 */     int newCapacity = 64;
/*  306 */     while (newCapacity < minNewCapacity) {
/*  307 */       newCapacity <<= 1;
/*      */     }
/*      */     
/*  310 */     return Math.min(newCapacity, maxCapacity);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf order(ByteOrder endianness) {
/*  315 */     if (endianness == null) {
/*  316 */       throw new NullPointerException("endianness");
/*      */     }
/*  318 */     if (endianness == order()) {
/*  319 */       return this;
/*      */     }
/*      */     
/*  322 */     SwappedByteBuf swappedBuf = this.swappedBuf;
/*  323 */     if (swappedBuf == null) {
/*  324 */       this.swappedBuf = swappedBuf = newSwappedByteBuf();
/*      */     }
/*  326 */     return swappedBuf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SwappedByteBuf newSwappedByteBuf() {
/*  333 */     return new SwappedByteBuf(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int index) {
/*  338 */     checkIndex(index);
/*  339 */     return _getByte(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int index) {
/*  346 */     return (getByte(index) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getUnsignedByte(int index) {
/*  351 */     return (short)(getByte(index) & 0xFF);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int index) {
/*  356 */     checkIndex(index, 2);
/*  357 */     return _getShort(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUnsignedShort(int index) {
/*  364 */     return getShort(index) & 0xFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMedium(int index) {
/*  369 */     checkIndex(index, 3);
/*  370 */     return _getUnsignedMedium(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMedium(int index) {
/*  377 */     int value = getUnsignedMedium(index);
/*  378 */     if ((value & 0x800000) != 0) {
/*  379 */       value |= 0xFF000000;
/*      */     }
/*  381 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int index) {
/*  386 */     checkIndex(index, 4);
/*  387 */     return _getInt(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getUnsignedInt(int index) {
/*  394 */     return getInt(index) & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int index) {
/*  399 */     checkIndex(index, 8);
/*  400 */     return _getLong(index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char getChar(int index) {
/*  407 */     return (char)getShort(index);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int index) {
/*  412 */     return Float.intBitsToFloat(getInt(index));
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int index) {
/*  417 */     return Double.longBitsToDouble(getLong(index));
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, byte[] dst) {
/*  422 */     getBytes(index, dst, 0, dst.length);
/*  423 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst) {
/*  428 */     getBytes(index, dst, dst.writableBytes());
/*  429 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/*  434 */     getBytes(index, dst, dst.writerIndex(), length);
/*  435 */     dst.writerIndex(dst.writerIndex() + length);
/*  436 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setByte(int index, int value) {
/*  441 */     checkIndex(index);
/*  442 */     _setByte(index, value);
/*  443 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setBoolean(int index, boolean value) {
/*  450 */     setByte(index, value ? 1 : 0);
/*  451 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShort(int index, int value) {
/*  456 */     checkIndex(index, 2);
/*  457 */     _setShort(index, value);
/*  458 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setChar(int index, int value) {
/*  465 */     setShort(index, value);
/*  466 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMedium(int index, int value) {
/*  471 */     checkIndex(index, 3);
/*  472 */     _setMedium(index, value);
/*  473 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setInt(int index, int value) {
/*  480 */     checkIndex(index, 4);
/*  481 */     _setInt(index, value);
/*  482 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setFloat(int index, float value) {
/*  489 */     setInt(index, Float.floatToRawIntBits(value));
/*  490 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLong(int index, long value) {
/*  495 */     checkIndex(index, 8);
/*  496 */     _setLong(index, value);
/*  497 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ByteBuf setDouble(int index, double value) {
/*  504 */     setLong(index, Double.doubleToRawLongBits(value));
/*  505 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, byte[] src) {
/*  510 */     setBytes(index, src, 0, src.length);
/*  511 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src) {
/*  516 */     setBytes(index, src, src.readableBytes());
/*  517 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/*  522 */     checkIndex(index, length);
/*  523 */     if (src == null) {
/*  524 */       throw new NullPointerException("src");
/*      */     }
/*  526 */     if (length > src.readableBytes()) {
/*  527 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", new Object[] { Integer.valueOf(length), Integer.valueOf(src.readableBytes()), src }));
/*      */     }
/*      */ 
/*      */     
/*  531 */     setBytes(index, src, src.readerIndex(), length);
/*  532 */     src.readerIndex(src.readerIndex() + length);
/*  533 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setZero(int index, int length) {
/*  538 */     if (length == 0) {
/*  539 */       return this;
/*      */     }
/*      */     
/*  542 */     checkIndex(index, length);
/*      */     
/*  544 */     int nLong = length >>> 3;
/*  545 */     int nBytes = length & 0x7; int i;
/*  546 */     for (i = nLong; i > 0; i--) {
/*  547 */       setLong(index, 0L);
/*  548 */       index += 8;
/*      */     } 
/*  550 */     if (nBytes == 4) {
/*  551 */       setInt(index, 0);
/*  552 */     } else if (nBytes < 4) {
/*  553 */       for (i = nBytes; i > 0; i--) {
/*  554 */         setByte(index, 0);
/*  555 */         index++;
/*      */       } 
/*      */     } else {
/*  558 */       setInt(index, 0);
/*  559 */       index += 4;
/*  560 */       for (i = nBytes - 4; i > 0; i--) {
/*  561 */         setByte(index, 0);
/*  562 */         index++;
/*      */       } 
/*      */     } 
/*  565 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public byte readByte() {
/*  570 */     checkReadableBytes(1);
/*  571 */     int i = this.readerIndex;
/*  572 */     byte b = getByte(i);
/*  573 */     this.readerIndex = i + 1;
/*  574 */     return b;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean readBoolean() {
/*  579 */     return (readByte() != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public short readUnsignedByte() {
/*  584 */     return (short)(readByte() & 0xFF);
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShort() {
/*  589 */     checkReadableBytes(2);
/*  590 */     short v = _getShort(this.readerIndex);
/*  591 */     this.readerIndex += 2;
/*  592 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShort() {
/*  597 */     return readShort() & 0xFFFF;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMedium() {
/*  602 */     int value = readUnsignedMedium();
/*  603 */     if ((value & 0x800000) != 0) {
/*  604 */       value |= 0xFF000000;
/*      */     }
/*  606 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMedium() {
/*  611 */     checkReadableBytes(3);
/*  612 */     int v = _getUnsignedMedium(this.readerIndex);
/*  613 */     this.readerIndex += 3;
/*  614 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt() {
/*  619 */     checkReadableBytes(4);
/*  620 */     int v = _getInt(this.readerIndex);
/*  621 */     this.readerIndex += 4;
/*  622 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedInt() {
/*  627 */     return readInt() & 0xFFFFFFFFL;
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLong() {
/*  632 */     checkReadableBytes(8);
/*  633 */     long v = _getLong(this.readerIndex);
/*  634 */     this.readerIndex += 8;
/*  635 */     return v;
/*      */   }
/*      */ 
/*      */   
/*      */   public char readChar() {
/*  640 */     return (char)readShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() {
/*  645 */     return Float.intBitsToFloat(readInt());
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() {
/*  650 */     return Double.longBitsToDouble(readLong());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(int length) {
/*  655 */     checkReadableBytes(length);
/*  656 */     if (length == 0) {
/*  657 */       return Unpooled.EMPTY_BUFFER;
/*      */     }
/*      */ 
/*      */     
/*  661 */     ByteBuf buf = Unpooled.buffer(length, this.maxCapacity);
/*  662 */     buf.writeBytes(this, this.readerIndex, length);
/*  663 */     this.readerIndex += length;
/*  664 */     return buf;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readSlice(int length) {
/*  669 */     ByteBuf slice = slice(this.readerIndex, length);
/*  670 */     this.readerIndex += length;
/*  671 */     return slice;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/*  676 */     checkReadableBytes(length);
/*  677 */     getBytes(this.readerIndex, dst, dstIndex, length);
/*  678 */     this.readerIndex += length;
/*  679 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] dst) {
/*  684 */     readBytes(dst, 0, dst.length);
/*  685 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst) {
/*  690 */     readBytes(dst, dst.writableBytes());
/*  691 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int length) {
/*  696 */     if (length > dst.writableBytes()) {
/*  697 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds dst.writableBytes(%d) where dst is: %s", new Object[] { Integer.valueOf(length), Integer.valueOf(dst.writableBytes()), dst }));
/*      */     }
/*      */     
/*  700 */     readBytes(dst, dst.writerIndex(), length);
/*  701 */     dst.writerIndex(dst.writerIndex() + length);
/*  702 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/*  707 */     checkReadableBytes(length);
/*  708 */     getBytes(this.readerIndex, dst, dstIndex, length);
/*  709 */     this.readerIndex += length;
/*  710 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer dst) {
/*  715 */     int length = dst.remaining();
/*  716 */     checkReadableBytes(length);
/*  717 */     getBytes(this.readerIndex, dst);
/*  718 */     this.readerIndex += length;
/*  719 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/*  725 */     checkReadableBytes(length);
/*  726 */     int readBytes = getBytes(this.readerIndex, out, length);
/*  727 */     this.readerIndex += readBytes;
/*  728 */     return readBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/*  733 */     checkReadableBytes(length);
/*  734 */     getBytes(this.readerIndex, out, length);
/*  735 */     this.readerIndex += length;
/*  736 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf skipBytes(int length) {
/*  741 */     checkReadableBytes(length);
/*  742 */     this.readerIndex += length;
/*  743 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBoolean(boolean value) {
/*  748 */     writeByte(value ? 1 : 0);
/*  749 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeByte(int value) {
/*  754 */     ensureAccessible();
/*  755 */     ensureWritable(1);
/*  756 */     _setByte(this.writerIndex++, value);
/*  757 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShort(int value) {
/*  762 */     ensureAccessible();
/*  763 */     ensureWritable(2);
/*  764 */     _setShort(this.writerIndex, value);
/*  765 */     this.writerIndex += 2;
/*  766 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMedium(int value) {
/*  771 */     ensureAccessible();
/*  772 */     ensureWritable(3);
/*  773 */     _setMedium(this.writerIndex, value);
/*  774 */     this.writerIndex += 3;
/*  775 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeInt(int value) {
/*  780 */     ensureAccessible();
/*  781 */     ensureWritable(4);
/*  782 */     _setInt(this.writerIndex, value);
/*  783 */     this.writerIndex += 4;
/*  784 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLong(long value) {
/*  789 */     ensureAccessible();
/*  790 */     ensureWritable(8);
/*  791 */     _setLong(this.writerIndex, value);
/*  792 */     this.writerIndex += 8;
/*  793 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeChar(int value) {
/*  798 */     writeShort(value);
/*  799 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeFloat(float value) {
/*  804 */     writeInt(Float.floatToRawIntBits(value));
/*  805 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeDouble(double value) {
/*  810 */     writeLong(Double.doubleToRawLongBits(value));
/*  811 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/*  816 */     ensureAccessible();
/*  817 */     ensureWritable(length);
/*  818 */     setBytes(this.writerIndex, src, srcIndex, length);
/*  819 */     this.writerIndex += length;
/*  820 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] src) {
/*  825 */     writeBytes(src, 0, src.length);
/*  826 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src) {
/*  831 */     writeBytes(src, src.readableBytes());
/*  832 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int length) {
/*  837 */     if (length > src.readableBytes()) {
/*  838 */       throw new IndexOutOfBoundsException(String.format("length(%d) exceeds src.readableBytes(%d) where src is: %s", new Object[] { Integer.valueOf(length), Integer.valueOf(src.readableBytes()), src }));
/*      */     }
/*      */     
/*  841 */     writeBytes(src, src.readerIndex(), length);
/*  842 */     src.readerIndex(src.readerIndex() + length);
/*  843 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/*  848 */     ensureAccessible();
/*  849 */     ensureWritable(length);
/*  850 */     setBytes(this.writerIndex, src, srcIndex, length);
/*  851 */     this.writerIndex += length;
/*  852 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer src) {
/*  857 */     ensureAccessible();
/*  858 */     int length = src.remaining();
/*  859 */     ensureWritable(length);
/*  860 */     setBytes(this.writerIndex, src);
/*  861 */     this.writerIndex += length;
/*  862 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int writeBytes(InputStream in, int length) throws IOException {
/*  868 */     ensureAccessible();
/*  869 */     ensureWritable(length);
/*  870 */     int writtenBytes = setBytes(this.writerIndex, in, length);
/*  871 */     if (writtenBytes > 0) {
/*  872 */       this.writerIndex += writtenBytes;
/*      */     }
/*  874 */     return writtenBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
/*  879 */     ensureAccessible();
/*  880 */     ensureWritable(length);
/*  881 */     int writtenBytes = setBytes(this.writerIndex, in, length);
/*  882 */     if (writtenBytes > 0) {
/*  883 */       this.writerIndex += writtenBytes;
/*      */     }
/*  885 */     return writtenBytes;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeZero(int length) {
/*  890 */     if (length == 0) {
/*  891 */       return this;
/*      */     }
/*      */     
/*  894 */     ensureWritable(length);
/*  895 */     checkIndex(this.writerIndex, length);
/*      */     
/*  897 */     int nLong = length >>> 3;
/*  898 */     int nBytes = length & 0x7; int i;
/*  899 */     for (i = nLong; i > 0; i--) {
/*  900 */       writeLong(0L);
/*      */     }
/*  902 */     if (nBytes == 4) {
/*  903 */       writeInt(0);
/*  904 */     } else if (nBytes < 4) {
/*  905 */       for (i = nBytes; i > 0; i--) {
/*  906 */         writeByte(0);
/*      */       }
/*      */     } else {
/*  909 */       writeInt(0);
/*  910 */       for (i = nBytes - 4; i > 0; i--) {
/*  911 */         writeByte(0);
/*      */       }
/*      */     } 
/*  914 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy() {
/*  919 */     return copy(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf duplicate() {
/*  924 */     return new DuplicatedByteBuf(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice() {
/*  929 */     return slice(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice(int index, int length) {
/*  934 */     if (length == 0) {
/*  935 */       return Unpooled.EMPTY_BUFFER;
/*      */     }
/*      */     
/*  938 */     return new SlicedByteBuf(this, index, length);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer() {
/*  943 */     return nioBuffer(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/*  948 */     return nioBuffers(this.readerIndex, readableBytes());
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(Charset charset) {
/*  953 */     return toString(this.readerIndex, readableBytes(), charset);
/*      */   }
/*      */   
/*      */   public String toString(int index, int length, Charset charset) {
/*      */     ByteBuffer nioBuffer;
/*  958 */     if (length == 0) {
/*  959 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  963 */     if (nioBufferCount() == 1) {
/*  964 */       nioBuffer = nioBuffer(index, length);
/*      */     } else {
/*  966 */       nioBuffer = ByteBuffer.allocate(length);
/*  967 */       getBytes(index, nioBuffer);
/*  968 */       nioBuffer.flip();
/*      */     } 
/*      */     
/*  971 */     return ByteBufUtil.decodeString(nioBuffer, charset);
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(int fromIndex, int toIndex, byte value) {
/*  976 */     return ByteBufUtil.indexOf(this, fromIndex, toIndex, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(byte value) {
/*  981 */     return bytesBefore(readerIndex(), readableBytes(), value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int length, byte value) {
/*  986 */     checkReadableBytes(length);
/*  987 */     return bytesBefore(readerIndex(), length, value);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int index, int length, byte value) {
/*  992 */     int endIndex = indexOf(index, index + length, value);
/*  993 */     if (endIndex < 0) {
/*  994 */       return -1;
/*      */     }
/*  996 */     return endIndex - index;
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteBufProcessor processor) {
/* 1001 */     int index = this.readerIndex;
/* 1002 */     int length = this.writerIndex - index;
/* 1003 */     ensureAccessible();
/* 1004 */     return forEachByteAsc0(index, length, processor);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(int index, int length, ByteBufProcessor processor) {
/* 1009 */     checkIndex(index, length);
/* 1010 */     return forEachByteAsc0(index, length, processor);
/*      */   }
/*      */   
/*      */   private int forEachByteAsc0(int index, int length, ByteBufProcessor processor) {
/* 1014 */     if (processor == null) {
/* 1015 */       throw new NullPointerException("processor");
/*      */     }
/*      */     
/* 1018 */     if (length == 0) {
/* 1019 */       return -1;
/*      */     }
/*      */     
/* 1022 */     int endIndex = index + length;
/* 1023 */     int i = index;
/*      */     try {
/*      */       do {
/* 1026 */         if (processor.process(_getByte(i))) {
/* 1027 */           i++;
/*      */         } else {
/* 1029 */           return i;
/*      */         } 
/* 1031 */       } while (i < endIndex);
/* 1032 */     } catch (Exception e) {
/* 1033 */       PlatformDependent.throwException(e);
/*      */     } 
/*      */     
/* 1036 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteBufProcessor processor) {
/* 1041 */     int index = this.readerIndex;
/* 1042 */     int length = this.writerIndex - index;
/* 1043 */     ensureAccessible();
/* 1044 */     return forEachByteDesc0(index, length, processor);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int index, int length, ByteBufProcessor processor) {
/* 1049 */     checkIndex(index, length);
/*      */     
/* 1051 */     return forEachByteDesc0(index, length, processor);
/*      */   }
/*      */ 
/*      */   
/*      */   private int forEachByteDesc0(int index, int length, ByteBufProcessor processor) {
/* 1056 */     if (processor == null) {
/* 1057 */       throw new NullPointerException("processor");
/*      */     }
/*      */     
/* 1060 */     if (length == 0) {
/* 1061 */       return -1;
/*      */     }
/*      */     
/* 1064 */     int i = index + length - 1;
/*      */     try {
/*      */       do {
/* 1067 */         if (processor.process(_getByte(i))) {
/* 1068 */           i--;
/*      */         } else {
/* 1070 */           return i;
/*      */         } 
/* 1072 */       } while (i >= index);
/* 1073 */     } catch (Exception e) {
/* 1074 */       PlatformDependent.throwException(e);
/*      */     } 
/*      */     
/* 1077 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1082 */     return ByteBufUtil.hashCode(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object o) {
/* 1087 */     if (this == o) {
/* 1088 */       return true;
/*      */     }
/* 1090 */     if (o instanceof ByteBuf) {
/* 1091 */       return ByteBufUtil.equals(this, (ByteBuf)o);
/*      */     }
/* 1093 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(ByteBuf that) {
/* 1098 */     return ByteBufUtil.compare(this, that);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1103 */     if (refCnt() == 0) {
/* 1104 */       return StringUtil.simpleClassName(this) + "(freed)";
/*      */     }
/*      */     
/* 1107 */     StringBuilder buf = new StringBuilder();
/* 1108 */     buf.append(StringUtil.simpleClassName(this));
/* 1109 */     buf.append("(ridx: ");
/* 1110 */     buf.append(this.readerIndex);
/* 1111 */     buf.append(", widx: ");
/* 1112 */     buf.append(this.writerIndex);
/* 1113 */     buf.append(", cap: ");
/* 1114 */     buf.append(capacity());
/* 1115 */     if (this.maxCapacity != Integer.MAX_VALUE) {
/* 1116 */       buf.append('/');
/* 1117 */       buf.append(this.maxCapacity);
/*      */     } 
/*      */     
/* 1120 */     ByteBuf unwrapped = unwrap();
/* 1121 */     if (unwrapped != null) {
/* 1122 */       buf.append(", unwrapped: ");
/* 1123 */       buf.append(unwrapped);
/*      */     } 
/* 1125 */     buf.append(')');
/* 1126 */     return buf.toString();
/*      */   }
/*      */   
/*      */   protected final void checkIndex(int index) {
/* 1130 */     ensureAccessible();
/* 1131 */     if (index < 0 || index >= capacity()) {
/* 1132 */       throw new IndexOutOfBoundsException(String.format("index: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(index), Integer.valueOf(capacity()) }));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void checkIndex(int index, int fieldLength) {
/* 1138 */     ensureAccessible();
/* 1139 */     if (fieldLength < 0) {
/* 1140 */       throw new IllegalArgumentException("length: " + fieldLength + " (expected: >= 0)");
/*      */     }
/* 1142 */     if (index < 0 || index > capacity() - fieldLength) {
/* 1143 */       throw new IndexOutOfBoundsException(String.format("index: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(index), Integer.valueOf(fieldLength), Integer.valueOf(capacity()) }));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void checkSrcIndex(int index, int length, int srcIndex, int srcCapacity) {
/* 1149 */     checkIndex(index, length);
/* 1150 */     if (srcIndex < 0 || srcIndex > srcCapacity - length) {
/* 1151 */       throw new IndexOutOfBoundsException(String.format("srcIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(srcIndex), Integer.valueOf(length), Integer.valueOf(srcCapacity) }));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void checkDstIndex(int index, int length, int dstIndex, int dstCapacity) {
/* 1157 */     checkIndex(index, length);
/* 1158 */     if (dstIndex < 0 || dstIndex > dstCapacity - length) {
/* 1159 */       throw new IndexOutOfBoundsException(String.format("dstIndex: %d, length: %d (expected: range(0, %d))", new Object[] { Integer.valueOf(dstIndex), Integer.valueOf(length), Integer.valueOf(dstCapacity) }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void checkReadableBytes(int minimumReadableBytes) {
/* 1170 */     ensureAccessible();
/* 1171 */     if (minimumReadableBytes < 0) {
/* 1172 */       throw new IllegalArgumentException("minimumReadableBytes: " + minimumReadableBytes + " (expected: >= 0)");
/*      */     }
/* 1174 */     if (this.readerIndex > this.writerIndex - minimumReadableBytes) {
/* 1175 */       throw new IndexOutOfBoundsException(String.format("readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s", new Object[] { Integer.valueOf(this.readerIndex), Integer.valueOf(minimumReadableBytes), Integer.valueOf(this.writerIndex), this }));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void ensureAccessible() {
/* 1186 */     if (refCnt() == 0)
/* 1187 */       throw new IllegalReferenceCountException(0); 
/*      */   }
/*      */   
/*      */   protected abstract byte _getByte(int paramInt);
/*      */   
/*      */   protected abstract short _getShort(int paramInt);
/*      */   
/*      */   protected abstract int _getUnsignedMedium(int paramInt);
/*      */   
/*      */   protected abstract int _getInt(int paramInt);
/*      */   
/*      */   protected abstract long _getLong(int paramInt);
/*      */   
/*      */   protected abstract void _setByte(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setShort(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setMedium(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setInt(int paramInt1, int paramInt2);
/*      */   
/*      */   protected abstract void _setLong(int paramInt, long paramLong);
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\AbstractByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */