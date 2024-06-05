package net.minecraft.world.storage;

import com.google.common.collect.*;
import java.util.*;

public class ThreadedFileIOBase implements Runnable
{
    private static final ThreadedFileIOBase threadedIOInstance;
    private List threadedIOQueue;
    private volatile long writeQueuedCounter;
    private volatile long savedIOCounter;
    private volatile boolean isThreadWaiting;
    
    private ThreadedFileIOBase() {
        this.threadedIOQueue = Collections.synchronizedList((List<Object>)Lists.newArrayList());
        final Thread var1 = new Thread(this, "File IO Thread");
        var1.setPriority(1);
        var1.start();
    }
    
    public static ThreadedFileIOBase func_178779_a() {
        return ThreadedFileIOBase.threadedIOInstance;
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
    
    public void queueIO(final IThreadedFileIO p_75735_1_) {
        if (!this.threadedIOQueue.contains(p_75735_1_)) {
            ++this.writeQueuedCounter;
            this.threadedIOQueue.add(p_75735_1_);
        }
    }
    
    public void waitForFinish() throws InterruptedException {
        this.isThreadWaiting = true;
        while (this.writeQueuedCounter != this.savedIOCounter) {
            Thread.sleep(10L);
        }
        this.isThreadWaiting = false;
    }
    
    static {
        threadedIOInstance = new ThreadedFileIOBase();
    }
}
