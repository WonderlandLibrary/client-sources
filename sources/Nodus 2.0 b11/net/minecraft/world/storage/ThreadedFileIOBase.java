/*  1:   */ package net.minecraft.world.storage;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collections;
/*  5:   */ import java.util.List;
/*  6:   */ 
/*  7:   */ public class ThreadedFileIOBase
/*  8:   */   implements Runnable
/*  9:   */ {
/* 10:10 */   public static final ThreadedFileIOBase threadedIOInstance = new ThreadedFileIOBase();
/* 11:11 */   private List threadedIOQueue = Collections.synchronizedList(new ArrayList());
/* 12:   */   private volatile long writeQueuedCounter;
/* 13:   */   private volatile long savedIOCounter;
/* 14:   */   private volatile boolean isThreadWaiting;
/* 15:   */   private static final String __OBFID = "CL_00000605";
/* 16:   */   
/* 17:   */   private ThreadedFileIOBase()
/* 18:   */   {
/* 19:19 */     Thread var1 = new Thread(this, "File IO Thread");
/* 20:20 */     var1.setPriority(1);
/* 21:21 */     var1.start();
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void run()
/* 25:   */   {
/* 26:   */     for (;;)
/* 27:   */     {
/* 28:28 */       processQueue();
/* 29:   */     }
/* 30:   */   }
/* 31:   */   
/* 32:   */   private void processQueue()
/* 33:   */   {
/* 34:37 */     for (int var1 = 0; var1 < this.threadedIOQueue.size(); var1++)
/* 35:   */     {
/* 36:39 */       IThreadedFileIO var2 = (IThreadedFileIO)this.threadedIOQueue.get(var1);
/* 37:40 */       boolean var3 = var2.writeNextIO();
/* 38:42 */       if (!var3)
/* 39:   */       {
/* 40:44 */         this.threadedIOQueue.remove(var1--);
/* 41:45 */         this.savedIOCounter += 1L;
/* 42:   */       }
/* 43:   */       try
/* 44:   */       {
/* 45:50 */         Thread.sleep(this.isThreadWaiting ? 0L : 10L);
/* 46:   */       }
/* 47:   */       catch (InterruptedException var6)
/* 48:   */       {
/* 49:54 */         var6.printStackTrace();
/* 50:   */       }
/* 51:   */     }
/* 52:58 */     if (this.threadedIOQueue.isEmpty()) {
/* 53:   */       try
/* 54:   */       {
/* 55:62 */         Thread.sleep(25L);
/* 56:   */       }
/* 57:   */       catch (InterruptedException var5)
/* 58:   */       {
/* 59:66 */         var5.printStackTrace();
/* 60:   */       }
/* 61:   */     }
/* 62:   */   }
/* 63:   */   
/* 64:   */   public void queueIO(IThreadedFileIO par1IThreadedFileIO)
/* 65:   */   {
/* 66:76 */     if (!this.threadedIOQueue.contains(par1IThreadedFileIO))
/* 67:   */     {
/* 68:78 */       this.writeQueuedCounter += 1L;
/* 69:79 */       this.threadedIOQueue.add(par1IThreadedFileIO);
/* 70:   */     }
/* 71:   */   }
/* 72:   */   
/* 73:   */   public void waitForFinish()
/* 74:   */     throws InterruptedException
/* 75:   */   {
/* 76:85 */     this.isThreadWaiting = true;
/* 77:87 */     while (this.writeQueuedCounter != this.savedIOCounter) {
/* 78:89 */       Thread.sleep(10L);
/* 79:   */     }
/* 80:92 */     this.isThreadWaiting = false;
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.storage.ThreadedFileIOBase
 * JD-Core Version:    0.7.0.1
 */