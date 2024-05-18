/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.GatheringByteChannel;
/*     */ import java.nio.channels.ScatteringByteChannel;
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
/*     */ final class PooledHeapByteBuf
/*     */   extends PooledByteBuf<byte[]>
/*     */ {
/*  30 */   private static final Recycler<PooledHeapByteBuf> RECYCLER = new Recycler<PooledHeapByteBuf>()
/*     */     {
/*     */       protected PooledHeapByteBuf newObject(Recycler.Handle handle) {
/*  33 */         return new PooledHeapByteBuf(handle, 0);
/*     */       }
/*     */     };
/*     */   
/*     */   static PooledHeapByteBuf newInstance(int maxCapacity) {
/*  38 */     PooledHeapByteBuf buf = (PooledHeapByteBuf)RECYCLER.get();
/*  39 */     buf.setRefCnt(1);
/*  40 */     buf.maxCapacity(maxCapacity);
/*  41 */     return buf;
/*     */   }
/*     */   
/*     */   private PooledHeapByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
/*  45 */     super(recyclerHandle, maxCapacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirect() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte _getByte(int index) {
/*  55 */     return this.memory[idx(index)];
/*     */   }
/*     */ 
/*     */   
/*     */   protected short _getShort(int index) {
/*  60 */     index = idx(index);
/*  61 */     return (short)(this.memory[index] << 8 | this.memory[index + 1] & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int _getUnsignedMedium(int index) {
/*  66 */     index = idx(index);
/*  67 */     return (this.memory[index] & 0xFF) << 16 | (this.memory[index + 1] & 0xFF) << 8 | this.memory[index + 2] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _getInt(int index) {
/*  74 */     index = idx(index);
/*  75 */     return (this.memory[index] & 0xFF) << 24 | (this.memory[index + 1] & 0xFF) << 16 | (this.memory[index + 2] & 0xFF) << 8 | this.memory[index + 3] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected long _getLong(int index) {
/*  83 */     index = idx(index);
/*  84 */     return (this.memory[index] & 0xFFL) << 56L | (this.memory[index + 1] & 0xFFL) << 48L | (this.memory[index + 2] & 0xFFL) << 40L | (this.memory[index + 3] & 0xFFL) << 32L | (this.memory[index + 4] & 0xFFL) << 24L | (this.memory[index + 5] & 0xFFL) << 16L | (this.memory[index + 6] & 0xFFL) << 8L | this.memory[index + 7] & 0xFFL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuf dst, int dstIndex, int length) {
/*  96 */     checkDstIndex(index, length, dstIndex, dst.capacity());
/*  97 */     if (dst.hasMemoryAddress()) {
/*  98 */       PlatformDependent.copyMemory(this.memory, idx(index), dst.memoryAddress() + dstIndex, length);
/*  99 */     } else if (dst.hasArray()) {
/* 100 */       getBytes(index, dst.array(), dst.arrayOffset() + dstIndex, length);
/*     */     } else {
/* 102 */       dst.setBytes(dstIndex, this.memory, idx(index), length);
/*     */     } 
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length) {
/* 109 */     checkDstIndex(index, length, dstIndex, dst.length);
/* 110 */     System.arraycopy(this.memory, idx(index), dst, dstIndex, length);
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, ByteBuffer dst) {
/* 116 */     checkIndex(index);
/* 117 */     dst.put(this.memory, idx(index), Math.min(capacity() - index, dst.remaining()));
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getBytes(int index, OutputStream out, int length) throws IOException {
/* 123 */     checkIndex(index, length);
/* 124 */     out.write(this.memory, idx(index), length);
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBytes(int index, GatheringByteChannel out, int length) throws IOException {
/* 130 */     return getBytes(index, out, length, false);
/*     */   }
/*     */   private int getBytes(int index, GatheringByteChannel out, int length, boolean internal) throws IOException {
/*     */     ByteBuffer tmpBuf;
/* 134 */     checkIndex(index, length);
/* 135 */     index = idx(index);
/*     */     
/* 137 */     if (internal) {
/* 138 */       tmpBuf = internalNioBuffer();
/*     */     } else {
/* 140 */       tmpBuf = ByteBuffer.wrap(this.memory);
/*     */     } 
/* 142 */     return out.write((ByteBuffer)tmpBuf.clear().position(index).limit(index + length));
/*     */   }
/*     */ 
/*     */   
/*     */   public int readBytes(GatheringByteChannel out, int length) throws IOException {
/* 147 */     checkReadableBytes(length);
/* 148 */     int readBytes = getBytes(this.readerIndex, out, length, true);
/* 149 */     this.readerIndex += readBytes;
/* 150 */     return readBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setByte(int index, int value) {
/* 155 */     this.memory[idx(index)] = (byte)value;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setShort(int index, int value) {
/* 160 */     index = idx(index);
/* 161 */     this.memory[index] = (byte)(value >>> 8);
/* 162 */     this.memory[index + 1] = (byte)value;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setMedium(int index, int value) {
/* 167 */     index = idx(index);
/* 168 */     this.memory[index] = (byte)(value >>> 16);
/* 169 */     this.memory[index + 1] = (byte)(value >>> 8);
/* 170 */     this.memory[index + 2] = (byte)value;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setInt(int index, int value) {
/* 175 */     index = idx(index);
/* 176 */     this.memory[index] = (byte)(value >>> 24);
/* 177 */     this.memory[index + 1] = (byte)(value >>> 16);
/* 178 */     this.memory[index + 2] = (byte)(value >>> 8);
/* 179 */     this.memory[index + 3] = (byte)value;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _setLong(int index, long value) {
/* 184 */     index = idx(index);
/* 185 */     this.memory[index] = (byte)(int)(value >>> 56L);
/* 186 */     this.memory[index + 1] = (byte)(int)(value >>> 48L);
/* 187 */     this.memory[index + 2] = (byte)(int)(value >>> 40L);
/* 188 */     this.memory[index + 3] = (byte)(int)(value >>> 32L);
/* 189 */     this.memory[index + 4] = (byte)(int)(value >>> 24L);
/* 190 */     this.memory[index + 5] = (byte)(int)(value >>> 16L);
/* 191 */     this.memory[index + 6] = (byte)(int)(value >>> 8L);
/* 192 */     this.memory[index + 7] = (byte)(int)value;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuf src, int srcIndex, int length) {
/* 197 */     checkSrcIndex(index, length, srcIndex, src.capacity());
/* 198 */     if (src.hasMemoryAddress()) {
/* 199 */       PlatformDependent.copyMemory(src.memoryAddress() + srcIndex, this.memory, idx(index), length);
/* 200 */     } else if (src.hasArray()) {
/* 201 */       setBytes(index, src.array(), src.arrayOffset() + srcIndex, length);
/*     */     } else {
/* 203 */       src.getBytes(srcIndex, this.memory, idx(index), length);
/*     */     } 
/* 205 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, byte[] src, int srcIndex, int length) {
/* 210 */     checkSrcIndex(index, length, srcIndex, src.length);
/* 211 */     System.arraycopy(src, srcIndex, this.memory, idx(index), length);
/* 212 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf setBytes(int index, ByteBuffer src) {
/* 217 */     int length = src.remaining();
/* 218 */     checkIndex(index, length);
/* 219 */     src.get(this.memory, idx(index), length);
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, InputStream in, int length) throws IOException {
/* 225 */     checkIndex(index, length);
/* 226 */     return in.read(this.memory, idx(index), length);
/*     */   }
/*     */ 
/*     */   
/*     */   public int setBytes(int index, ScatteringByteChannel in, int length) throws IOException {
/* 231 */     checkIndex(index, length);
/* 232 */     index = idx(index);
/*     */     try {
/* 234 */       return in.read((ByteBuffer)internalNioBuffer().clear().position(index).limit(index + length));
/* 235 */     } catch (ClosedChannelException ignored) {
/* 236 */       return -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf copy(int index, int length) {
/* 242 */     checkIndex(index, length);
/* 243 */     ByteBuf copy = alloc().heapBuffer(length, maxCapacity());
/* 244 */     copy.writeBytes(this.memory, idx(index), length);
/* 245 */     return copy;
/*     */   }
/*     */ 
/*     */   
/*     */   public int nioBufferCount() {
/* 250 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer[] nioBuffers(int index, int length) {
/* 255 */     return new ByteBuffer[] { nioBuffer(index, length) };
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer nioBuffer(int index, int length) {
/* 260 */     checkIndex(index, length);
/* 261 */     index = idx(index);
/* 262 */     ByteBuffer buf = ByteBuffer.wrap(this.memory, index, length);
/* 263 */     return buf.slice();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer internalNioBuffer(int index, int length) {
/* 268 */     checkIndex(index, length);
/* 269 */     index = idx(index);
/* 270 */     return (ByteBuffer)internalNioBuffer().clear().position(index).limit(index + length);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasArray() {
/* 275 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] array() {
/* 280 */     return this.memory;
/*     */   }
/*     */ 
/*     */   
/*     */   public int arrayOffset() {
/* 285 */     return this.offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMemoryAddress() {
/* 290 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public long memoryAddress() {
/* 295 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ByteBuffer newInternalNioBuffer(byte[] memory) {
/* 300 */     return ByteBuffer.wrap(memory);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Recycler<?> recycler() {
/* 305 */     return RECYCLER;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\PooledHeapByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */