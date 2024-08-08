package org.spongepowered.asm.mixin.transformer.debug;

import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RuntimeDecompilerAsync extends RuntimeDecompiler implements Runnable, UncaughtExceptionHandler {
   private final BlockingQueue queue = new LinkedBlockingQueue();
   private final Thread thread = new Thread(this, "Decompiler thread");
   private boolean run = true;

   public RuntimeDecompilerAsync(File var1) {
      super(var1);
      this.thread.setDaemon(true);
      this.thread.setPriority(1);
      this.thread.setUncaughtExceptionHandler(this);
      this.thread.start();
   }

   public void decompile(File var1) {
      if (this.run) {
         this.queue.offer(var1);
      } else {
         super.decompile(var1);
      }

   }

   public void run() {
      while(this.run) {
         try {
            File var1 = (File)this.queue.take();
            super.decompile(var1);
         } catch (InterruptedException var2) {
            this.run = false;
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

   }

   public void uncaughtException(Thread var1, Throwable var2) {
      this.logger.error("Async decompiler encountered an error and will terminate. Further decompile requests will be handled synchronously. {} {}", new Object[]{var2.getClass().getName(), var2.getMessage()});
      this.flush();
   }

   private void flush() {
      this.run = false;

      File var1;
      while((var1 = (File)this.queue.poll()) != null) {
         this.decompile(var1);
      }

   }
}
