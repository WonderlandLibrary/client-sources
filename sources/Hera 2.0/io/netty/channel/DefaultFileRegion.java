/*     */ package io.netty.channel;
/*     */ 
/*     */ import io.netty.util.AbstractReferenceCounted;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.WritableByteChannel;
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
/*     */ public class DefaultFileRegion
/*     */   extends AbstractReferenceCounted
/*     */   implements FileRegion
/*     */ {
/*  34 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultFileRegion.class);
/*     */ 
/*     */   
/*     */   private final FileChannel file;
/*     */ 
/*     */   
/*     */   private final long position;
/*     */ 
/*     */   
/*     */   private final long count;
/*     */   
/*     */   private long transfered;
/*     */ 
/*     */   
/*     */   public DefaultFileRegion(FileChannel file, long position, long count) {
/*  49 */     if (file == null) {
/*  50 */       throw new NullPointerException("file");
/*     */     }
/*  52 */     if (position < 0L) {
/*  53 */       throw new IllegalArgumentException("position must be >= 0 but was " + position);
/*     */     }
/*  55 */     if (count < 0L) {
/*  56 */       throw new IllegalArgumentException("count must be >= 0 but was " + count);
/*     */     }
/*  58 */     this.file = file;
/*  59 */     this.position = position;
/*  60 */     this.count = count;
/*     */   }
/*     */ 
/*     */   
/*     */   public long position() {
/*  65 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public long count() {
/*  70 */     return this.count;
/*     */   }
/*     */ 
/*     */   
/*     */   public long transfered() {
/*  75 */     return this.transfered;
/*     */   }
/*     */ 
/*     */   
/*     */   public long transferTo(WritableByteChannel target, long position) throws IOException {
/*  80 */     long count = this.count - position;
/*  81 */     if (count < 0L || position < 0L) {
/*  82 */       throw new IllegalArgumentException("position out of range: " + position + " (expected: 0 - " + (this.count - 1L) + ')');
/*     */     }
/*     */ 
/*     */     
/*  86 */     if (count == 0L) {
/*  87 */       return 0L;
/*     */     }
/*     */     
/*  90 */     long written = this.file.transferTo(this.position + position, count, target);
/*  91 */     if (written > 0L) {
/*  92 */       this.transfered += written;
/*     */     }
/*  94 */     return written;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/*     */     try {
/* 100 */       this.file.close();
/* 101 */     } catch (IOException e) {
/* 102 */       if (logger.isWarnEnabled())
/* 103 */         logger.warn("Failed to close a file.", e); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\DefaultFileRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */