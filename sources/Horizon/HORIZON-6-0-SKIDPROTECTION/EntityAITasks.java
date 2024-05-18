package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class EntityAITasks
{
    private static final Logger HorizonCode_Horizon_È;
    private List Â;
    private List Ý;
    private final Profiler Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private static final String à = "CL_00001588";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public EntityAITasks(final Profiler p_i1628_1_) {
        this.Â = Lists.newArrayList();
        this.Ý = Lists.newArrayList();
        this.Ó = 3;
        this.Ø­áŒŠá = p_i1628_1_;
    }
    
    public void HorizonCode_Horizon_È(final int p_75776_1_, final EntityAIBase p_75776_2_) {
        this.Â.add(new HorizonCode_Horizon_È(p_75776_1_, p_75776_2_));
    }
    
    public void HorizonCode_Horizon_È(final EntityAIBase p_85156_1_) {
        final Iterator var2 = this.Â.iterator();
        while (var2.hasNext()) {
            final HorizonCode_Horizon_È var3 = var2.next();
            final EntityAIBase var4 = var3.HorizonCode_Horizon_È;
            if (var4 == p_85156_1_) {
                if (this.Ý.contains(var3)) {
                    var4.Ý();
                    this.Ý.remove(var3);
                }
                var2.remove();
            }
        }
    }
    
    public void HorizonCode_Horizon_È() {
        this.Ø­áŒŠá.HorizonCode_Horizon_È("goalSetup");
        if (this.Âµá€++ % this.Ó == 0) {
            for (final HorizonCode_Horizon_È var2 : this.Â) {
                final boolean var3 = this.Ý.contains(var2);
                if (var3) {
                    if (this.Â(var2) && this.HorizonCode_Horizon_È(var2)) {
                        continue;
                    }
                    var2.HorizonCode_Horizon_È.Ý();
                    this.Ý.remove(var2);
                }
                if (this.Â(var2) && var2.HorizonCode_Horizon_È.HorizonCode_Horizon_È()) {
                    var2.HorizonCode_Horizon_È.Âµá€();
                    this.Ý.add(var2);
                }
            }
        }
        else {
            final Iterator var1 = this.Ý.iterator();
            while (var1.hasNext()) {
                final HorizonCode_Horizon_È var2 = var1.next();
                if (!this.HorizonCode_Horizon_È(var2)) {
                    var2.HorizonCode_Horizon_È.Ý();
                    var1.remove();
                }
            }
        }
        this.Ø­áŒŠá.Â();
        this.Ø­áŒŠá.HorizonCode_Horizon_È("goalTick");
        final Iterator var1 = this.Ý.iterator();
        while (var1.hasNext()) {
            final HorizonCode_Horizon_È var2 = var1.next();
            var2.HorizonCode_Horizon_È.Ø­áŒŠá();
        }
        this.Ø­áŒŠá.Â();
    }
    
    private boolean HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_75773_1_) {
        final boolean var2 = p_75773_1_.HorizonCode_Horizon_È.Â();
        return var2;
    }
    
    private boolean Â(final HorizonCode_Horizon_È p_75775_1_) {
        for (final HorizonCode_Horizon_È var3 : this.Â) {
            if (var3 != p_75775_1_) {
                if (p_75775_1_.Â >= var3.Â) {
                    if (!this.HorizonCode_Horizon_È(p_75775_1_, var3) && this.Ý.contains(var3)) {
                        return false;
                    }
                    continue;
                }
                else {
                    if (!var3.HorizonCode_Horizon_È.Ó() && this.Ý.contains(var3)) {
                        return false;
                    }
                    continue;
                }
            }
        }
        return true;
    }
    
    private boolean HorizonCode_Horizon_È(final HorizonCode_Horizon_È p_75777_1_, final HorizonCode_Horizon_È p_75777_2_) {
        return (p_75777_1_.HorizonCode_Horizon_È.à() & p_75777_2_.HorizonCode_Horizon_È.à()) == 0x0;
    }
    
    class HorizonCode_Horizon_È
    {
        public EntityAIBase HorizonCode_Horizon_È;
        public int Â;
        private static final String Ø­áŒŠá = "CL_00001589";
        
        public HorizonCode_Horizon_È(final int p_i1627_2_, final EntityAIBase p_i1627_3_) {
            this.Â = p_i1627_2_;
            this.HorizonCode_Horizon_È = p_i1627_3_;
        }
    }
}
