package HORIZON-6-0-SKIDPROTECTION;

import java.util.Collections;
import com.google.common.collect.Lists;
import java.util.List;

public class ThreadedFileIOBase implements Runnable
{
    private static final ThreadedFileIOBase HorizonCode_Horizon_È;
    private List Â;
    private volatile long Ý;
    private volatile long Ø­áŒŠá;
    private volatile boolean Âµá€;
    private static final String Ó = "CL_00000605";
    
    static {
        HorizonCode_Horizon_È = new ThreadedFileIOBase();
    }
    
    private ThreadedFileIOBase() {
        this.Â = Collections.synchronizedList((List<Object>)Lists.newArrayList());
        final Thread var1 = new Thread(this, "File IO Thread");
        var1.setPriority(1);
        var1.start();
    }
    
    public static ThreadedFileIOBase HorizonCode_Horizon_È() {
        return ThreadedFileIOBase.HorizonCode_Horizon_È;
    }
    
    @Override
    public void run() {
        while (true) {
            this.Ý();
        }
    }
    
    private void Ý() {
        for (int var1 = 0; var1 < this.Â.size(); ++var1) {
            final IThreadedFileIO var2 = this.Â.get(var1);
            final boolean var3 = var2.HorizonCode_Horizon_È();
            if (!var3) {
                this.Â.remove(var1--);
                ++this.Ø­áŒŠá;
            }
            try {
                Thread.sleep(this.Âµá€ ? 0L : 10L);
            }
            catch (InterruptedException var4) {
                var4.printStackTrace();
            }
        }
        if (this.Â.isEmpty()) {
            try {
                Thread.sleep(25L);
            }
            catch (InterruptedException var5) {
                var5.printStackTrace();
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final IThreadedFileIO p_75735_1_) {
        if (!this.Â.contains(p_75735_1_)) {
            ++this.Ý;
            this.Â.add(p_75735_1_);
        }
    }
    
    public void Â() throws InterruptedException {
        this.Âµá€ = true;
        while (this.Ý != this.Ø­áŒŠá) {
            Thread.sleep(10L);
        }
        this.Âµá€ = false;
    }
}
