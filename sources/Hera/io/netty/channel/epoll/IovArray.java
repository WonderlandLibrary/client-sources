/*     */ package io.netty.channel.epoll;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.util.concurrent.FastThreadLocal;
/*     */ import io.netty.util.internal.PlatformDependent;
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
/*     */ 
/*     */ 
/*     */ final class IovArray
/*     */   implements ChannelOutboundBuffer.MessageProcessor
/*     */ {
/*  44 */   private static final int ADDRESS_SIZE = PlatformDependent.addressSize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private static final int IOV_SIZE = 2 * ADDRESS_SIZE;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private static final int CAPACITY = Native.IOV_MAX * IOV_SIZE;
/*     */   
/*  57 */   private static final FastThreadLocal<IovArray> ARRAY = new FastThreadLocal<IovArray>()
/*     */     {
/*     */       protected IovArray initialValue() throws Exception {
/*  60 */         return new IovArray();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected void onRemoval(IovArray value) throws Exception {
/*  66 */         PlatformDependent.freeMemory(value.memoryAddress);
/*     */       }
/*     */     };
/*     */   
/*     */   private final long memoryAddress;
/*     */   private int count;
/*     */   private long size;
/*     */   
/*     */   private IovArray() {
/*  75 */     this.memoryAddress = PlatformDependent.allocateMemory(CAPACITY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean add(ByteBuf buf) {
/*  83 */     if (this.count == Native.IOV_MAX)
/*     */     {
/*  85 */       return false;
/*     */     }
/*     */     
/*  88 */     int len = buf.readableBytes();
/*  89 */     if (len == 0)
/*     */     {
/*     */ 
/*     */       
/*  93 */       return true;
/*     */     }
/*     */     
/*  96 */     long addr = buf.memoryAddress();
/*  97 */     int offset = buf.readerIndex();
/*     */     
/*  99 */     long baseOffset = memoryAddress(this.count++);
/* 100 */     long lengthOffset = baseOffset + ADDRESS_SIZE;
/*     */     
/* 102 */     if (ADDRESS_SIZE == 8) {
/*     */       
/* 104 */       PlatformDependent.putLong(baseOffset, addr + offset);
/* 105 */       PlatformDependent.putLong(lengthOffset, len);
/*     */     } else {
/* 107 */       assert ADDRESS_SIZE == 4;
/* 108 */       PlatformDependent.putInt(baseOffset, (int)addr + offset);
/* 109 */       PlatformDependent.putInt(lengthOffset, len);
/*     */     } 
/*     */     
/* 112 */     this.size += len;
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long processWritten(int index, long written) {
/* 121 */     long baseOffset = memoryAddress(index);
/* 122 */     long lengthOffset = baseOffset + ADDRESS_SIZE;
/* 123 */     if (ADDRESS_SIZE == 8) {
/*     */       
/* 125 */       long l = PlatformDependent.getLong(lengthOffset);
/* 126 */       if (l > written) {
/* 127 */         long offset = PlatformDependent.getLong(baseOffset);
/* 128 */         PlatformDependent.putLong(baseOffset, offset + written);
/* 129 */         PlatformDependent.putLong(lengthOffset, l - written);
/* 130 */         return -1L;
/*     */       } 
/* 132 */       return l;
/*     */     } 
/* 134 */     assert ADDRESS_SIZE == 4;
/* 135 */     long len = PlatformDependent.getInt(lengthOffset);
/* 136 */     if (len > written) {
/* 137 */       int offset = PlatformDependent.getInt(baseOffset);
/* 138 */       PlatformDependent.putInt(baseOffset, (int)(offset + written));
/* 139 */       PlatformDependent.putInt(lengthOffset, (int)(len - written));
/* 140 */       return -1L;
/*     */     } 
/* 142 */     return len;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int count() {
/* 150 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long size() {
/* 157 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long memoryAddress(int offset) {
/* 164 */     return this.memoryAddress + (IOV_SIZE * offset);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processMessage(Object msg) throws Exception {
/* 169 */     return (msg instanceof ByteBuf && add((ByteBuf)msg));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static IovArray get(ChannelOutboundBuffer buffer) throws Exception {
/* 176 */     IovArray array = (IovArray)ARRAY.get();
/* 177 */     array.size = 0L;
/* 178 */     array.count = 0;
/* 179 */     buffer.forEachFlushedMessage(array);
/* 180 */     return array;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\epoll\IovArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */