package HORIZON-6-0-SKIDPROTECTION;

public class Timer
{
    private static long HorizonCode_Horizon_È;
    
    static {
        Timer.HorizonCode_Horizon_È = -1L;
    }
    
    public static boolean HorizonCode_Horizon_È(final float milliseconds) {
        return Ý() - Timer.HorizonCode_Horizon_È >= milliseconds;
    }
    
    public void HorizonCode_Horizon_È() {
        Timer.HorizonCode_Horizon_È = Ý();
    }
    
    public long Â() {
        return Timer.HorizonCode_Horizon_È;
    }
    
    public static long Ý() {
        return System.nanoTime() / 1000000L;
    }
    
    public static short Â(final float perSecond) {
        return (short)(1000.0f / perSecond);
    }
}
