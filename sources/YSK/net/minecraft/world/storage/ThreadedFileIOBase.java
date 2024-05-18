package net.minecraft.world.storage;

import com.google.common.collect.*;
import java.util.*;

public class ThreadedFileIOBase implements Runnable
{
    private long savedIOCounter;
    private static final String[] I;
    private static final ThreadedFileIOBase threadedIOInstance;
    private long writeQueuedCounter;
    private boolean isThreadWaiting;
    private List<IThreadedFileIO> threadedIOQueue;
    
    static {
        I();
        threadedIOInstance = new ThreadedFileIOBase();
    }
    
    @Override
    public void run() {
        do {
            this.processQueue();
            "".length();
        } while (3 != 2);
        throw null;
    }
    
    private ThreadedFileIOBase() {
        this.threadedIOQueue = Collections.synchronizedList((List<IThreadedFileIO>)Lists.newArrayList());
        final Thread thread = new Thread(this, ThreadedFileIOBase.I["".length()]);
        thread.setPriority(" ".length());
        thread.start();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\t+\u0002\u000fx\u0006\rN>0='\u000f\u000e", "OBnjX");
    }
    
    public static ThreadedFileIOBase getThreadedIOInstance() {
        return ThreadedFileIOBase.threadedIOInstance;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void waitForFinish() throws InterruptedException {
        this.isThreadWaiting = (" ".length() != 0);
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (this.writeQueuedCounter != this.savedIOCounter) {
            Thread.sleep(10L);
        }
        this.isThreadWaiting = ("".length() != 0);
    }
    
    private void processQueue() {
        int i = "".length();
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (i < this.threadedIOQueue.size()) {
            if (!this.threadedIOQueue.get(i).writeNextIO()) {
                this.threadedIOQueue.remove(i--);
                ++this.savedIOCounter;
            }
            try {
                long n;
                if (this.isThreadWaiting) {
                    n = 0L;
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                }
                else {
                    n = 10L;
                }
                Thread.sleep(n);
                "".length();
                if (false == true) {
                    throw null;
                }
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            ++i;
        }
        if (this.threadedIOQueue.isEmpty()) {
            try {
                Thread.sleep(25L);
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
            catch (InterruptedException ex2) {
                ex2.printStackTrace();
            }
        }
    }
    
    public void queueIO(final IThreadedFileIO threadedFileIO) {
        if (!this.threadedIOQueue.contains(threadedFileIO)) {
            ++this.writeQueuedCounter;
            this.threadedIOQueue.add(threadedFileIO);
        }
    }
}
