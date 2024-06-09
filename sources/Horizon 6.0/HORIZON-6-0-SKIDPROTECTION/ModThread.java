package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public abstract class ModThread extends Thread
{
    private boolean HorizonCode_Horizon_È;
    
    public ModThread(final boolean shouldLoop) {
        this.HorizonCode_Horizon_È = true;
        final int i = new Random().nextInt(1337);
        this.setName("Thread " + i);
        this.start();
        this.HorizonCode_Horizon_È = shouldLoop;
    }
    
    public void HorizonCode_Horizon_È(final boolean bool) {
        this.HorizonCode_Horizon_È = bool;
    }
    
    @Override
    public final void run() {
        while (this.HorizonCode_Horizon_È) {
            try {
                Thread.sleep(this.HorizonCode_Horizon_È());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    protected abstract long HorizonCode_Horizon_È();
}
