/*    */ package org.spongepowered.asm.mixin.transformer.debug;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.concurrent.BlockingQueue;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RuntimeDecompilerAsync
/*    */   extends RuntimeDecompiler
/*    */   implements Runnable, Thread.UncaughtExceptionHandler
/*    */ {
/* 38 */   private final BlockingQueue<File> queue = new LinkedBlockingQueue<File>();
/*    */   
/*    */   private final Thread thread;
/*    */   
/*    */   private boolean run = true;
/*    */   
/*    */   public RuntimeDecompilerAsync(File outputPath) {
/* 45 */     super(outputPath);
/* 46 */     this.thread = new Thread(this, "Decompiler thread");
/* 47 */     this.thread.setDaemon(true);
/* 48 */     this.thread.setPriority(1);
/* 49 */     this.thread.setUncaughtExceptionHandler(this);
/* 50 */     this.thread.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void decompile(File file) {
/* 55 */     if (this.run) {
/* 56 */       this.queue.offer(file);
/*    */     } else {
/* 58 */       super.decompile(file);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 64 */     while (this.run) {
/*    */       try {
/* 66 */         File file = this.queue.take();
/* 67 */         super.decompile(file);
/* 68 */       } catch (InterruptedException ex) {
/* 69 */         this.run = false;
/* 70 */       } catch (Exception ex) {
/* 71 */         ex.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void uncaughtException(Thread thread, Throwable ex) {
/* 78 */     this.logger.error("Async decompiler encountered an error and will terminate. Further decompile requests will be handled synchronously. {} {}", new Object[] { ex
/* 79 */           .getClass().getName(), ex.getMessage() });
/* 80 */     flush();
/*    */   }
/*    */   
/*    */   private void flush() {
/* 84 */     this.run = false; File file;
/* 85 */     while ((file = this.queue.poll()) != null)
/* 86 */       decompile(file); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\mixin\transformer\debug\RuntimeDecompilerAsync.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */