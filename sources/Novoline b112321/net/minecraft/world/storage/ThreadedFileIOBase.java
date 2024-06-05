package net.minecraft.world.storage;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.storage.IThreadedFileIO;

public class ThreadedFileIOBase implements Runnable {
   private static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();
   private List threadedIOQueue = Collections.synchronizedList(Lists.newArrayList());
   private volatile long writeQueuedCounter;
   private volatile long savedIOCounter;
   private volatile boolean isThreadWaiting;

   private ThreadedFileIOBase() {
      Thread var1 = new Thread(this, "File IO Thread");
      var1.setPriority(1);
      var1.start();
   }

   public static ThreadedFileIOBase getThreadedIOInstance() {
      return threadedIOInstance;
   }

   public void run() {
      while(true) {
         this.processQueue();
      }
   }

   private void processQueue() {
      // $FF: Couldn't be decompiled
   }

   public void queueIO(IThreadedFileIO var1) {
      if(!this.threadedIOQueue.contains(var1)) {
         ++this.writeQueuedCounter;
         this.threadedIOQueue.add(var1);
      }

   }

   public void waitForFinish() throws InterruptedException {
      this.isThreadWaiting = true;

      while(this.writeQueuedCounter != this.savedIOCounter) {
         Thread.sleep(10L);
      }

      this.isThreadWaiting = false;
   }

   private static InterruptedException a(InterruptedException var0) {
      return var0;
   }
}
