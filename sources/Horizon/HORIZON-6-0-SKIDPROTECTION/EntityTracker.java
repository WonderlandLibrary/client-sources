package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;
import java.util.concurrent.Callable;
import java.util.Iterator;
import com.google.common.collect.Sets;
import org.apache.logging.log4j.LogManager;
import java.util.Set;
import org.apache.logging.log4j.Logger;

public class EntityTracker
{
    private static final Logger HorizonCode_Horizon_È;
    private final WorldServer Â;
    private Set Ý;
    private IntHashMap Ø­áŒŠá;
    private int Âµá€;
    private static final String Ó = "CL_00001431";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public EntityTracker(final WorldServer p_i1516_1_) {
        this.Ý = Sets.newHashSet();
        this.Ø­áŒŠá = new IntHashMap();
        this.Â = p_i1516_1_;
        this.Âµá€ = p_i1516_1_.áˆºáˆºÈ().Œ().Ø­áŒŠá();
    }
    
    public void HorizonCode_Horizon_È(final Entity p_72786_1_) {
        if (p_72786_1_ instanceof EntityPlayerMP) {
            this.HorizonCode_Horizon_È(p_72786_1_, 512, 2);
            final EntityPlayerMP var2 = (EntityPlayerMP)p_72786_1_;
            for (final EntityTrackerEntry var4 : this.Ý) {
                if (var4.HorizonCode_Horizon_È != var2) {
                    var4.Â(var2);
                }
            }
        }
        else if (p_72786_1_ instanceof EntityFishHook) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 5, true);
        }
        else if (p_72786_1_ instanceof EntityArrow) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 20, false);
        }
        else if (p_72786_1_ instanceof EntitySmallFireball) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 10, false);
        }
        else if (p_72786_1_ instanceof EntityFireball) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 10, false);
        }
        else if (p_72786_1_ instanceof EntitySnowball) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityEnderPearl) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityEnderEye) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 4, true);
        }
        else if (p_72786_1_ instanceof EntityEgg) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityPotion) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityExpBottle) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityFireworkRocket) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 10, true);
        }
        else if (p_72786_1_ instanceof EntityItem) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 20, true);
        }
        else if (p_72786_1_ instanceof EntityMinecart) {
            this.HorizonCode_Horizon_È(p_72786_1_, 80, 3, true);
        }
        else if (p_72786_1_ instanceof EntityBoat) {
            this.HorizonCode_Horizon_È(p_72786_1_, 80, 3, true);
        }
        else if (p_72786_1_ instanceof EntitySquid) {
            this.HorizonCode_Horizon_È(p_72786_1_, 64, 3, true);
        }
        else if (p_72786_1_ instanceof EntityWither) {
            this.HorizonCode_Horizon_È(p_72786_1_, 80, 3, false);
        }
        else if (p_72786_1_ instanceof EntityBat) {
            this.HorizonCode_Horizon_È(p_72786_1_, 80, 3, false);
        }
        else if (p_72786_1_ instanceof EntityDragon) {
            this.HorizonCode_Horizon_È(p_72786_1_, 160, 3, true);
        }
        else if (p_72786_1_ instanceof IAnimals) {
            this.HorizonCode_Horizon_È(p_72786_1_, 80, 3, true);
        }
        else if (p_72786_1_ instanceof EntityTNTPrimed) {
            this.HorizonCode_Horizon_È(p_72786_1_, 160, 10, true);
        }
        else if (p_72786_1_ instanceof EntityFallingBlock) {
            this.HorizonCode_Horizon_È(p_72786_1_, 160, 20, true);
        }
        else if (p_72786_1_ instanceof EntityHanging) {
            this.HorizonCode_Horizon_È(p_72786_1_, 160, Integer.MAX_VALUE, false);
        }
        else if (p_72786_1_ instanceof EntityArmorStand) {
            this.HorizonCode_Horizon_È(p_72786_1_, 160, 3, true);
        }
        else if (p_72786_1_ instanceof EntityXPOrb) {
            this.HorizonCode_Horizon_È(p_72786_1_, 160, 20, true);
        }
        else if (p_72786_1_ instanceof EntityEnderCrystal) {
            this.HorizonCode_Horizon_È(p_72786_1_, 256, Integer.MAX_VALUE, false);
        }
    }
    
    public void HorizonCode_Horizon_È(final Entity p_72791_1_, final int p_72791_2_, final int p_72791_3_) {
        this.HorizonCode_Horizon_È(p_72791_1_, p_72791_2_, p_72791_3_, false);
    }
    
    public void HorizonCode_Horizon_È(final Entity p_72785_1_, int p_72785_2_, final int p_72785_3_, final boolean p_72785_4_) {
        if (p_72785_2_ > this.Âµá€) {
            p_72785_2_ = this.Âµá€;
        }
        try {
            if (this.Ø­áŒŠá.Â(p_72785_1_.ˆá())) {
                throw new IllegalStateException("Entity is already tracked!");
            }
            final EntityTrackerEntry var5 = new EntityTrackerEntry(p_72785_1_, p_72785_2_, p_72785_3_, p_72785_4_);
            this.Ý.add(var5);
            this.Ø­áŒŠá.HorizonCode_Horizon_È(p_72785_1_.ˆá(), var5);
            var5.Â(this.Â.Ó);
        }
        catch (Throwable var7) {
            final CrashReport var6 = CrashReport.HorizonCode_Horizon_È(var7, "Adding entity to track");
            final CrashReportCategory var8 = var6.HorizonCode_Horizon_È("Entity To Track");
            var8.HorizonCode_Horizon_È("Tracking range", String.valueOf(p_72785_2_) + " blocks");
            var8.HorizonCode_Horizon_È("Update interval", new Callable() {
                private static final String Â = "CL_00001432";
                
                public String HorizonCode_Horizon_È() {
                    String var1 = "Once per " + p_72785_3_ + " ticks";
                    if (p_72785_3_ == Integer.MAX_VALUE) {
                        var1 = "Maximum (" + var1 + ")";
                    }
                    return var1;
                }
            });
            p_72785_1_.HorizonCode_Horizon_È(var8);
            final CrashReportCategory var9 = var6.HorizonCode_Horizon_È("Entity That Is Already Tracked");
            ((EntityTrackerEntry)this.Ø­áŒŠá.HorizonCode_Horizon_È(p_72785_1_.ˆá())).HorizonCode_Horizon_È.HorizonCode_Horizon_È(var9);
            try {
                throw new ReportedException(var6);
            }
            catch (ReportedException var10) {
                EntityTracker.HorizonCode_Horizon_È.error("\"Silently\" catching entity tracking error.", (Throwable)var10);
            }
        }
    }
    
    public void Â(final Entity p_72790_1_) {
        if (p_72790_1_ instanceof EntityPlayerMP) {
            final EntityPlayerMP var2 = (EntityPlayerMP)p_72790_1_;
            for (final EntityTrackerEntry var4 : this.Ý) {
                var4.HorizonCode_Horizon_È(var2);
            }
        }
        final EntityTrackerEntry var5 = (EntityTrackerEntry)this.Ø­áŒŠá.Ø­áŒŠá(p_72790_1_.ˆá());
        if (var5 != null) {
            this.Ý.remove(var5);
            var5.HorizonCode_Horizon_È();
        }
    }
    
    public void HorizonCode_Horizon_È() {
        final ArrayList var1 = Lists.newArrayList();
        for (final EntityTrackerEntry var3 : this.Ý) {
            var3.HorizonCode_Horizon_È(this.Â.Ó);
            if (var3.£á && var3.HorizonCode_Horizon_È instanceof EntityPlayerMP) {
                var1.add(var3.HorizonCode_Horizon_È);
            }
        }
        for (int var4 = 0; var4 < var1.size(); ++var4) {
            final EntityPlayerMP var5 = var1.get(var4);
            for (final EntityTrackerEntry var7 : this.Ý) {
                if (var7.HorizonCode_Horizon_È != var5) {
                    var7.Â(var5);
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayerMP p_180245_1_) {
        for (final EntityTrackerEntry var3 : this.Ý) {
            if (var3.HorizonCode_Horizon_È == p_180245_1_) {
                var3.Â(this.Â.Ó);
            }
            else {
                var3.Â(p_180245_1_);
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final Entity p_151247_1_, final Packet p_151247_2_) {
        final EntityTrackerEntry var3 = (EntityTrackerEntry)this.Ø­áŒŠá.HorizonCode_Horizon_È(p_151247_1_.ˆá());
        if (var3 != null) {
            var3.HorizonCode_Horizon_È(p_151247_2_);
        }
    }
    
    public void Â(final Entity p_151248_1_, final Packet p_151248_2_) {
        final EntityTrackerEntry var3 = (EntityTrackerEntry)this.Ø­áŒŠá.HorizonCode_Horizon_È(p_151248_1_.ˆá());
        if (var3 != null) {
            var3.Â(p_151248_2_);
        }
    }
    
    public void Â(final EntityPlayerMP p_72787_1_) {
        for (final EntityTrackerEntry var3 : this.Ý) {
            var3.Ø­áŒŠá(p_72787_1_);
        }
    }
    
    public void HorizonCode_Horizon_È(final EntityPlayerMP p_85172_1_, final Chunk p_85172_2_) {
        for (final EntityTrackerEntry var4 : this.Ý) {
            if (var4.HorizonCode_Horizon_È != p_85172_1_ && var4.HorizonCode_Horizon_È.£Õ == p_85172_2_.HorizonCode_Horizon_È && var4.HorizonCode_Horizon_È.Œà == p_85172_2_.Â) {
                var4.Â(p_85172_1_);
            }
        }
    }
}
