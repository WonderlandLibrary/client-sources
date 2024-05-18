package HORIZON-6-0-SKIDPROTECTION;

import java.lang.reflect.Array;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;

public class ThreadSafeBoundList
{
    private final Object[] HorizonCode_Horizon_È;
    private final Class Â;
    private final ReadWriteLock Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00001868";
    
    public ThreadSafeBoundList(final Class p_i1126_1_, final int p_i1126_2_) {
        this.Ý = new ReentrantReadWriteLock();
        this.Â = p_i1126_1_;
        this.HorizonCode_Horizon_È = (Object[])Array.newInstance(p_i1126_1_, p_i1126_2_);
    }
    
    public Object HorizonCode_Horizon_È(final Object p_152757_1_) {
        this.Ý.writeLock().lock();
        this.HorizonCode_Horizon_È[this.Âµá€] = p_152757_1_;
        this.Âµá€ = (this.Âµá€ + 1) % this.HorizonCode_Horizon_È();
        if (this.Ø­áŒŠá < this.HorizonCode_Horizon_È()) {
            ++this.Ø­áŒŠá;
        }
        this.Ý.writeLock().unlock();
        return p_152757_1_;
    }
    
    public int HorizonCode_Horizon_È() {
        this.Ý.readLock().lock();
        final int var1 = this.HorizonCode_Horizon_È.length;
        this.Ý.readLock().unlock();
        return var1;
    }
    
    public Object[] Â() {
        final Object[] var1 = (Object[])Array.newInstance(this.Â, this.Ø­áŒŠá);
        this.Ý.readLock().lock();
        for (int var2 = 0; var2 < this.Ø­áŒŠá; ++var2) {
            int var3 = (this.Âµá€ - this.Ø­áŒŠá + var2) % this.HorizonCode_Horizon_È();
            if (var3 < 0) {
                var3 += this.HorizonCode_Horizon_È();
            }
            var1[var2] = this.HorizonCode_Horizon_È[var3];
        }
        this.Ý.readLock().unlock();
        return var1;
    }
}
