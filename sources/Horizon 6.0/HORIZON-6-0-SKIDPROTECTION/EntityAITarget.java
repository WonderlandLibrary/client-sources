package HORIZON-6-0-SKIDPROTECTION;

import org.apache.commons.lang3.StringUtils;

public abstract class EntityAITarget extends EntityAIBase
{
    protected final EntityCreature Âµá€;
    protected boolean Ó;
    private boolean HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private static final String à = "CL_00001626";
    
    public EntityAITarget(final EntityCreature p_i1669_1_, final boolean p_i1669_2_) {
        this(p_i1669_1_, p_i1669_2_, false);
    }
    
    public EntityAITarget(final EntityCreature p_i1670_1_, final boolean p_i1670_2_, final boolean p_i1670_3_) {
        this.Âµá€ = p_i1670_1_;
        this.Ó = p_i1670_2_;
        this.HorizonCode_Horizon_È = p_i1670_3_;
    }
    
    @Override
    public boolean Â() {
        final EntityLivingBase var1 = this.Âµá€.Ñ¢Ó();
        if (var1 == null) {
            return false;
        }
        if (!var1.Œ()) {
            return false;
        }
        final Team var2 = this.Âµá€.Çªáˆºá();
        final Team var3 = var1.Çªáˆºá();
        if (var2 != null && var3 == var2) {
            return false;
        }
        final double var4 = this.Ø();
        if (this.Âµá€.Âµá€(var1) > var4 * var4) {
            return false;
        }
        if (this.Ó) {
            if (this.Âµá€.Ø­Ñ¢á€().HorizonCode_Horizon_È(var1)) {
                this.Ø­áŒŠá = 0;
            }
            else if (++this.Ø­áŒŠá > 60) {
                return false;
            }
        }
        return !(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).áˆºáˆºáŠ.HorizonCode_Horizon_È;
    }
    
    protected double Ø() {
        final IAttributeInstance var1 = this.Âµá€.HorizonCode_Horizon_È(SharedMonsterAttributes.Â);
        return (var1 == null) ? 16.0 : var1.Âµá€();
    }
    
    @Override
    public void Âµá€() {
        this.Â = 0;
        this.Ý = 0;
        this.Ø­áŒŠá = 0;
    }
    
    @Override
    public void Ý() {
        this.Âµá€.Â((EntityLivingBase)null);
    }
    
    public static boolean HorizonCode_Horizon_È(final EntityLiving p_179445_0_, final EntityLivingBase p_179445_1_, final boolean p_179445_2_, final boolean p_179445_3_) {
        if (p_179445_1_ == null) {
            return false;
        }
        if (p_179445_1_ == p_179445_0_) {
            return false;
        }
        if (!p_179445_1_.Œ()) {
            return false;
        }
        if (!p_179445_0_.HorizonCode_Horizon_È(p_179445_1_.getClass())) {
            return false;
        }
        final Team var4 = p_179445_0_.Çªáˆºá();
        final Team var5 = p_179445_1_.Çªáˆºá();
        if (var4 != null && var5 == var4) {
            return false;
        }
        if (p_179445_0_ instanceof IEntityOwnable && StringUtils.isNotEmpty((CharSequence)((IEntityOwnable)p_179445_0_).Â())) {
            if (p_179445_1_ instanceof IEntityOwnable && ((IEntityOwnable)p_179445_0_).Â().equals(((IEntityOwnable)p_179445_1_).Â())) {
                return false;
            }
            if (p_179445_1_ == ((IEntityOwnable)p_179445_0_).y_()) {
                return false;
            }
        }
        else if (p_179445_1_ instanceof EntityPlayer && !p_179445_2_ && ((EntityPlayer)p_179445_1_).áˆºáˆºáŠ.HorizonCode_Horizon_È) {
            return false;
        }
        return !p_179445_3_ || p_179445_0_.Ø­Ñ¢á€().HorizonCode_Horizon_È(p_179445_1_);
    }
    
    protected boolean HorizonCode_Horizon_È(final EntityLivingBase p_75296_1_, final boolean p_75296_2_) {
        if (!HorizonCode_Horizon_È(this.Âµá€, p_75296_1_, p_75296_2_, this.Ó)) {
            return false;
        }
        if (!this.Âµá€.Ø­áŒŠá(new BlockPos(p_75296_1_))) {
            return false;
        }
        if (this.HorizonCode_Horizon_È) {
            if (--this.Ý <= 0) {
                this.Â = 0;
            }
            if (this.Â == 0) {
                this.Â = (this.HorizonCode_Horizon_È(p_75296_1_) ? 1 : 2);
            }
            if (this.Â == 2) {
                return false;
            }
        }
        return true;
    }
    
    private boolean HorizonCode_Horizon_È(final EntityLivingBase p_75295_1_) {
        this.Ý = 10 + this.Âµá€.ˆÐƒØ().nextInt(5);
        final PathEntity var2 = this.Âµá€.Š().HorizonCode_Horizon_È(p_75295_1_);
        if (var2 == null) {
            return false;
        }
        final PathPoint var3 = var2.Ý();
        if (var3 == null) {
            return false;
        }
        final int var4 = var3.HorizonCode_Horizon_È - MathHelper.Ý(p_75295_1_.ŒÏ);
        final int var5 = var3.Ý - MathHelper.Ý(p_75295_1_.Ê);
        return var4 * var4 + var5 * var5 <= 2.25;
    }
}
