/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import io.netty.util.ResourceLeak;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class AdvancedLeakAwareByteBuf
/*     */   extends WrappedByteBuf
/*     */ {
/*     */   private final ResourceLeak leak;
/*     */   
/*     */   AdvancedLeakAwareByteBuf(ByteBuf buf, ResourceLeak leak) {
/*  35 */     super(buf);
/*  36 */     this.leak = leak;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/*  41 */     boolean deallocated = super.release();
/*  42 */     if (deallocated) {
/*  43 */       this.leak.close();
/*     */     } else {
/*  45 */       this.leak.record();
/*     */     } 
/*  47 */     return deallocated;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/*  52 */     boolean deallocated = super.release(decrement);
/*  53 */     if (deallocated) {
/*  54 */       this.leak.close();
/*     */     } else {
/*  56 */       this.leak.record();
/*     */     } 
/*  58 */     return deallocated;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf order(ByteOrder endianness) {
/*  63 */     this.leak.record();
/*  64 */     if (order() == endianness) {
/*  65 */       return this;
/*     */     }
/*  67 */     return new AdvancedLeakAwareByteBuf(super.order(endianness), this.leak);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf slice() {
/*  73 */     this.leak.record();
/*  74 */     return new AdvancedLeakAwareByteBuf(super.slice(), this.leak);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf slice(int index, int length) {
/*  79 */     this.leak.record();
/*  80 */     return new AdvancedLeakAwareByteBuf(super.slice(index, length), this.leak);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf duplicate() {
/*  85 */     this.leak.record();
/*  86 */     return new AdvancedLeakAwareByteBuf(super.duplicate(), this.leak);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readSlice(int length) {
/*  91 */     this.leak.record();
/*  92 */     return new AdvancedLeakAwareByteBuf(super.readSlice(length), this.leak);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf discardReadBytes() {
/*  97 */     this.leak.record();
/*  98 */     return super.discardReadBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf discardSomeReadBytes() {
/* 103 */     this.leak.record();
/* 104 */     return super.discardSomeReadBytes();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf ensureWritable(int minWritableBytes) {
/* 109 */     this.leak.record();
/* 110 */     return super.ensureWritable(minWritableBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public int ensureWritable(int minWritableBytes, boolean force) {
/* 115 */     this.leak.record();
/* 116 */     return super.ensureWritable(minWritableBytes, force);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean(int index) {
/* 121 */     this.leak.record();
/* 122 */     return super.getBoolean(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte(int index) {
/* 127 */     this.leak.record();
/* 128 */     return super.getByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getUnsignedByte(int index) {
/* 133 */     this.leak.record();
/* 134 */     return super.getUnsignedByte(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort(int index) {
/* 139 */     this.leak.record();
/* 140 */     return super.getShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedShort(int index) {
/* 145 */     this.leak.record();
/* 146 */     return super.getUnsignedShort(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMedium(int index) {
/* 151 */     this.leak.record();
/* 152 */     return super.getMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUnsignedMedium(int index) {
/* 157 */     this.leak.record();
/* 158 */     return super.getUnsignedMedium(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt(int index) {
/* 163 */     this.leak.record();
/* 164 */     return super.getInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getUnsignedInt(int index) {
/* 169 */     this.leak.record();
/* 170 */     return super.getUnsignedInt(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong(int index) {
/* 175 */     this.leak.record();
/* 176 */     return super.getLong(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public char getChar(int index) {
/* 181 */     this.leak.record();
/* 182 */     return super.getChar(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat(int index) {
/* 187 */     this.leak.record();
/* 188 */     return super.getFloat(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble(int index) {
/* 193 */     this.leak.record();
/* 194 */     return super.getDouble(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst) {
/* 199 */     this.leak.record();
/* 200 */     return super.getBytes(index, dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int length) {
/* 205 */     this.leak.record();
/* 206 */     return super.getBytes(index, dst, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/* 211 */     this.leak.record();
/* 212 */     return super.getBytes(index, dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst) {
/* 217 */     this.leak.record();
/* 218 */     return super.getBytes(index, dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 223 */     this.leak.record();
/* 224 */     return super.getBytes(index, dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 229 */     this.leak.record();
/* 230 */     return super.getBytes(index, dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 235 */     this.leak.record();
/* 236 */     return super.getBytes(index, out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 241 */     this.leak.record();
/* 242 */     return super.getBytes(index, out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBoolean(int index, boolean value) {
/* 247 */     this.leak.record();
/* 248 */     return super.setBoolean(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setByte(int index, int value) {
/* 253 */     this.leak.record();
/* 254 */     return super.setByte(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setShort(int index, int value) {
/* 259 */     this.leak.record();
/* 260 */     return super.setShort(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setMedium(int index, int value) {
/* 265 */     this.leak.record();
/* 266 */     return super.setMedium(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setInt(int index, int value) {
/* 271 */     this.leak.record();
/* 272 */     return super.setInt(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setLong(int index, long value) {
/* 277 */     this.leak.record();
/* 278 */     return super.setLong(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setChar(int index, int value) {
/* 283 */     this.leak.record();
/* 284 */     return super.setChar(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setFloat(int index, float value) {
/* 289 */     this.leak.record();
/* 290 */     return super.setFloat(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setDouble(int index, double value) {
/* 295 */     this.leak.record();
/* 296 */     return super.setDouble(index, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src) {
/* 301 */     this.leak.record();
/* 302 */     return super.setBytes(index, src);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int length) {
/* 307 */     this.leak.record();
/* 308 */     return super.setBytes(index, src, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 313 */     this.leak.record();
/* 314 */     return super.setBytes(index, src, srcIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src) {
/* 319 */     this.leak.record();
/* 320 */     return super.setBytes(index, src);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 325 */     this.leak.record();
/* 326 */     return super.setBytes(index, src, srcIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 331 */     this.leak.record();
/* 332 */     return super.setBytes(index, src);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 337 */     this.leak.record();
/* 338 */     return super.setBytes(index, in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 343 */     this.leak.record();
/* 344 */     return super.setBytes(index, in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setZero(int index, int length) {
/* 349 */     this.leak.record();
/* 350 */     return super.setZero(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean readBoolean() {
/* 355 */     this.leak.record();
/* 356 */     return super.readBoolean();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte readByte() {
/* 361 */     this.leak.record();
/* 362 */     return super.readByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readUnsignedByte() {
/* 367 */     this.leak.record();
/* 368 */     return super.readUnsignedByte();
/*     */   }
/*     */ 
/*     */   
/*     */   public short readShort() {
/* 373 */     this.leak.record();
/* 374 */     return super.readShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedShort() {
/* 379 */     this.leak.record();
/* 380 */     return super.readUnsignedShort();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readMedium() {
/* 385 */     this.leak.record();
/* 386 */     return super.readMedium();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readUnsignedMedium() {
/* 391 */     this.leak.record();
/* 392 */     return super.readUnsignedMedium();
/*     */   }
/*     */ 
/*     */   
/*     */   public int readInt() {
/* 397 */     this.leak.record();
/* 398 */     return super.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readUnsignedInt() {
/* 403 */     this.leak.record();
/* 404 */     return super.readUnsignedInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public long readLong() {
/* 409 */     this.leak.record();
/* 410 */     return super.readLong();
/*     */   }
/*     */ 
/*     */   
/*     */   public char readChar() {
/* 415 */     this.leak.record();
/* 416 */     return super.readChar();
/*     */   }
/*     */ 
/*     */   
/*     */   public float readFloat() {
/* 421 */     this.leak.record();
/* 422 */     return super.readFloat();
/*     */   }
/*     */ 
/*     */   
/*     */   public double readDouble() {
/* 427 */     this.leak.record();
/* 428 */     return super.readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(int length) {
/* 433 */     this.leak.record();
/* 434 */     return super.readBytes(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst) {
/* 439 */     this.leak.record();
/* 440 */     return super.readBytes(dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int length) {
/* 445 */     this.leak.record();
/* 446 */     return super.readBytes(dst, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuf dst, int dstIndex, int length) {
/* 451 */     this.leak.record();
/* 452 */     return super.readBytes(dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst) {
/* 457 */     this.leak.record();
/* 458 */     return super.readBytes(dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
/* 463 */     this.leak.record();
/* 464 */     return super.readBytes(dst, dstIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(ByteBuffer dst) {
/* 469 */     this.leak.record();
/* 470 */     return super.readBytes(dst);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf readBytes(OutputStream out, int length) throws IOException {
/* 475 */     this.leak.record();
/* 476 */     return super.readBytes(out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/* 481 */     this.leak.record();
/* 482 */     return super.readBytes(out, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf skipBytes(int length) {
/* 487 */     this.leak.record();
/* 488 */     return super.skipBytes(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBoolean(boolean value) {
/* 493 */     this.leak.record();
/* 494 */     return super.writeBoolean(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeByte(int value) {
/* 499 */     this.leak.record();
/* 500 */     return super.writeByte(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeShort(int value) {
/* 505 */     this.leak.record();
/* 506 */     return super.writeShort(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeMedium(int value) {
/* 511 */     this.leak.record();
/* 512 */     return super.writeMedium(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeInt(int value) {
/* 517 */     this.leak.record();
/* 518 */     return super.writeInt(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeLong(long value) {
/* 523 */     this.leak.record();
/* 524 */     return super.writeLong(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeChar(int value) {
/* 529 */     this.leak.record();
/* 530 */     return super.writeChar(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeFloat(float value) {
/* 535 */     this.leak.record();
/* 536 */     return super.writeFloat(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeDouble(double value) {
/* 541 */     this.leak.record();
/* 542 */     return super.writeDouble(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src) {
/* 547 */     this.leak.record();
/* 548 */     return super.writeBytes(src);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src, int length) {
/* 553 */     this.leak.record();
/* 554 */     return super.writeBytes(src, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuf src, int srcIndex, int length) {
/* 559 */     this.leak.record();
/* 560 */     return super.writeBytes(src, srcIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(byte[] src) {
/* 565 */     this.leak.record();
/* 566 */     return super.writeBytes(src);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
/* 571 */     this.leak.record();
/* 572 */     return super.writeBytes(src, srcIndex, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeBytes(ByteBuffer src) {
/* 577 */     this.leak.record();
/* 578 */     return super.writeBytes(src);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(InputStream in, int length) throws IOException {
/* 583 */     this.leak.record();
/* 584 */     return super.writeBytes(in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int writeBytes(ScatteringByteChannel in, int length) throws IOException {
/* 589 */     this.leak.record();
/* 590 */     return super.writeBytes(in, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf writeZero(int length) {
/* 595 */     this.leak.record();
/* 596 */     return super.writeZero(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(int fromIndex, int toIndex, byte value) {
/* 601 */     this.leak.record();
/* 602 */     return super.indexOf(fromIndex, toIndex, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(byte value) {
/* 607 */     this.leak.record();
/* 608 */     return super.bytesBefore(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(int length, byte value) {
/* 613 */     this.leak.record();
/* 614 */     return super.bytesBefore(length, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int bytesBefore(int index, int length, byte value) {
/* 619 */     this.leak.record();
/* 620 */     return super.bytesBefore(index, length, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(ByteBufProcessor processor) {
/* 625 */     this.leak.record();
/* 626 */     return super.forEachByte(processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByte(int index, int length, ByteBufProcessor processor) {
/* 631 */     this.leak.record();
/* 632 */     return super.forEachByte(index, length, processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(ByteBufProcessor processor) {
/* 637 */     this.leak.record();
/* 638 */     return super.forEachByteDesc(processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public int forEachByteDesc(int index, int length, ByteBufProcessor processor) {
/* 643 */     this.leak.record();
/* 644 */     return super.forEachByteDesc(index, length, processor);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy() {
/* 649 */     this.leak.record();
/* 650 */     return super.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 655 */     this.leak.record();
/* 656 */     return super.copy(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 661 */     this.leak.record();
/* 662 */     return super.nioBufferCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer() {
/* 667 */     this.leak.record();
/* 668 */     return super.nioBuffer();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 673 */     this.leak.record();
/* 674 */     return super.nioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers() {
/* 679 */     this.leak.record();
/* 680 */     return super.nioBuffers();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 685 */     this.leak.record();
/* 686 */     return super.nioBuffers(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 691 */     this.leak.record();
/* 692 */     return super.internalNioBuffer(index, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(Charset charset) {
/* 697 */     this.leak.record();
/* 698 */     return super.toString(charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString(int index, int length, Charset charset) {
/* 703 */     this.leak.record();
/* 704 */     return super.toString(index, length, charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain() {
/* 709 */     this.leak.record();
/* 710 */     return super.retain();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf retain(int increment) {
/* 715 */     this.leak.record();
/* 716 */     return super.retain(increment);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf capacity(int newCapacity) {
/* 721 */     this.leak.record();
/* 722 */     return super.capacity(newCapacity);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\AdvancedLeakAwareByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */