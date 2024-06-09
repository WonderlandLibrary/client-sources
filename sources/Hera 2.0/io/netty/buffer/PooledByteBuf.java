/*     */ package io.netty.buffer;
/*     */ 
/*     */ import io.netty.util.Recycler;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
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
/*     */ abstract class PooledByteBuf<T>
/*     */   extends AbstractReferenceCountedByteBuf
/*     */ {
/*     */   private final Recycler.Handle recyclerHandle;
/*     */   protected PoolChunk<T> chunk;
/*     */   protected long handle;
/*     */   protected T memory;
/*     */   protected int offset;
/*     */   protected int length;
/*     */   int maxLength;
/*     */   private ByteBuffer tmpNioBuf;
/*     */   
/*     */   protected PooledByteBuf(Recycler.Handle recyclerHandle, int maxCapacity) {
/*  38 */     super(maxCapacity);
/*  39 */     this.recyclerHandle = recyclerHandle;
/*     */   }
/*     */   
/*     */   void init(PoolChunk<T> chunk, long handle, int offset, int length, int maxLength) {
/*  43 */     assert handle >= 0L;
/*  44 */     assert chunk != null;
/*     */     
/*  46 */     this.chunk = chunk;
/*  47 */     this.handle = handle;
/*  48 */     this.memory = chunk.memory;
/*  49 */     this.offset = offset;
/*  50 */     this.length = length;
/*  51 */     this.maxLength = maxLength;
/*  52 */     setIndex(0, 0);
/*  53 */     this.tmpNioBuf = null;
/*     */   }
/*     */   
/*     */   void initUnpooled(PoolChunk<T> chunk, int length) {
/*  57 */     assert chunk != null;
/*     */     
/*  59 */     this.chunk = chunk;
/*  60 */     this.handle = 0L;
/*  61 */     this.memory = chunk.memory;
/*  62 */     this.offset = 0;
/*  63 */     this.length = this.maxLength = length;
/*  64 */     setIndex(0, 0);
/*  65 */     this.tmpNioBuf = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int capacity() {
/*  70 */     return this.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf capacity(int newCapacity) {
/*  75 */     ensureAccessible();
/*     */ 
/*     */     
/*  78 */     if (this.chunk.unpooled) {
/*  79 */       if (newCapacity == this.length) {
/*  80 */         return this;
/*     */       }
/*     */     }
/*  83 */     else if (newCapacity > this.length) {
/*  84 */       if (newCapacity <= this.maxLength) {
/*  85 */         this.length = newCapacity;
/*  86 */         return this;
/*     */       } 
/*  88 */     } else if (newCapacity < this.length) {
/*  89 */       if (newCapacity > this.maxLength >>> 1) {
/*  90 */         if (this.maxLength <= 512) {
/*  91 */           if (newCapacity > this.maxLength - 16) {
/*  92 */             this.length = newCapacity;
/*  93 */             setIndex(Math.min(readerIndex(), newCapacity), Math.min(writerIndex(), newCapacity));
/*  94 */             return this;
/*     */           } 
/*     */         } else {
/*  97 */           this.length = newCapacity;
/*  98 */           setIndex(Math.min(readerIndex(), newCapacity), Math.min(writerIndex(), newCapacity));
/*  99 */           return this;
/*     */         } 
/*     */       }
/*     */     } else {
/* 103 */       return this;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 108 */     this.chunk.arena.reallocate(this, newCapacity, true);
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBufAllocator alloc() {
/* 114 */     return this.chunk.arena.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteOrder order() {
/* 119 */     return ByteOrder.BIG_ENDIAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public final ByteBuf unwrap() {
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   protected final ByteBuffer internalNioBuffer() {
/* 128 */     ByteBuffer tmpNioBuf = this.tmpNioBuf;
/* 129 */     if (tmpNioBuf == null) {
/* 130 */       this.tmpNioBuf = tmpNioBuf = newInternalNioBuffer(this.memory);
/*     */     }
/* 132 */     return tmpNioBuf;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract ByteBuffer newInternalNioBuffer(T paramT);
/*     */   
/*     */   protected final void deallocate() {
/* 139 */     if (this.handle >= 0L) {
/* 140 */       long handle = this.handle;
/* 141 */       this.handle = -1L;
/* 142 */       this.memory = null;
/* 143 */       this.chunk.arena.free(this.chunk, handle, this.maxLength);
/* 144 */       recycle();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void recycle() {
/* 149 */     Recycler.Handle recyclerHandle = this.recyclerHandle;
/* 150 */     if (recyclerHandle != null) {
/* 151 */       recycler().recycle(this, recyclerHandle);
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract Recycler<?> recycler();
/*     */   
/*     */   protected final int idx(int index) {
/* 158 */     return this.offset + index;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\buffer\PooledByteBuf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */