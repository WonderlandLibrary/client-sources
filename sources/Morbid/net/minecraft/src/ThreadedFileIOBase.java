package net.minecraft.src;

import java.util.*;

public class ThreadedFileIOBase implements Runnable
{
    public static final ThreadedFileIOBase threadedIOInstance;
    private List threadedIOQueue;
    private volatile long writeQueuedCounter;
    private volatile long savedIOCounter;
    private volatile boolean isThreadWaiting;
    
    static {
        threadedIOInstance = new ThreadedFileIOBase();
    }
    
    private ThreadedFileIOBase() {
        this.threadedIOQueue = Collections.synchronizedList(new ArrayList<Object>());
        this.writeQueuedCounter = 0L;
        this.savedIOCounter = 0L;
        this.isThreadWaiting = false;
        final Thread var1 = new Thread(this, "File IO Thread");
        var1.setPriority(1);
        var1.start();
    }
    
    @Override
    public void run() {
        while (true) {
            this.processQueue();
        }
    }
    
    private void processQueue() {
        for (int var1 = 0; var1 < this.threadedIOQueue.size(); ++var1) {
            final IThreadedFileIO var2 = this.threadedIOQueue.get(var1);
            final boolean var3 = var2.writeNextIO();
            if (!var3) {
                this.threadedIOQueue.remove(var1--);
                ++this.savedIOCounter;
            }
            try {
                Thread.sleep(this.isThreadWaiting ? 0L : 10L);
            }
            catch (InterruptedException var4) {
                var4.printStackTrace();
            }
        }
        if (this.threadedIOQueue.isEmpty()) {
            try {
                Thread.sleep(25L);
            }
            catch (InterruptedException var5) {
                var5.printStackTrace();
            }
        }
    }
    
    public void queueIO(final IThreadedFileIO par1IThreadedFileIO) {
        if (!this.threadedIOQueue.contains(par1IThreadedFileIO)) {
            ++this.writeQueuedCounter;
            this.threadedIOQueue.add(par1IThreadedFileIO);
        }
    }
    
    public void waitForFinish() throws InterruptedException {
        this.isThreadWaiting = true;
        while (this.writeQueuedCounter != this.savedIOCounter) {
            Thread.sleep(10L);
        }
        this.isThreadWaiting = false;
    }
}
