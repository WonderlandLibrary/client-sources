package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public class EntityAIMoveThroughVillage extends EntityAIBase
{
    private EntityCreature HorizonCode_Horizon_È;
    private double Â;
    private PathEntity Ý;
    private VillageDoorInfo Ø­áŒŠá;
    private boolean Âµá€;
    private List Ó;
    private static final String à = "CL_00001597";
    
    public EntityAIMoveThroughVillage(final EntityCreature p_i1638_1_, final double p_i1638_2_, final boolean p_i1638_4_) {
        this.Ó = Lists.newArrayList();
        this.HorizonCode_Horizon_È = p_i1638_1_;
        this.Â = p_i1638_2_;
        this.Âµá€ = p_i1638_4_;
        this.HorizonCode_Horizon_È(1);
        if (!(p_i1638_1_.Š() instanceof PathNavigateGround)) {
            throw new IllegalArgumentException("Unsupported mob for MoveThroughVillageGoal");
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        this.Ø();
        if (this.Âµá€ && this.HorizonCode_Horizon_È.Ï­Ðƒà.ÂµÈ()) {
            return false;
        }
        final Village var1 = this.HorizonCode_Horizon_È.Ï­Ðƒà.È().HorizonCode_Horizon_È(new BlockPos(this.HorizonCode_Horizon_È), 0);
        if (var1 == null) {
            return false;
        }
        this.Ø­áŒŠá = this.HorizonCode_Horizon_È(var1);
        if (this.Ø­áŒŠá == null) {
            return false;
        }
        final PathNavigateGround var2 = (PathNavigateGround)this.HorizonCode_Horizon_È.Š();
        final boolean var3 = var2.ˆÏ­();
        var2.Â(false);
        this.Ý = var2.HorizonCode_Horizon_È(this.Ø­áŒŠá.Ø­áŒŠá());
        var2.Â(var3);
        if (this.Ý != null) {
            return true;
        }
        final Vec3 var4 = RandomPositionGenerator.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, 10, 7, new Vec3(this.Ø­áŒŠá.Ø­áŒŠá().HorizonCode_Horizon_È(), this.Ø­áŒŠá.Ø­áŒŠá().Â(), this.Ø­áŒŠá.Ø­áŒŠá().Ý()));
        if (var4 == null) {
            return false;
        }
        var2.Â(false);
        this.Ý = this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(var4.HorizonCode_Horizon_È, var4.Â, var4.Ý);
        var2.Â(var3);
        return this.Ý != null;
    }
    
    @Override
    public boolean Â() {
        if (this.HorizonCode_Horizon_È.Š().Ó()) {
            return false;
        }
        final float var1 = this.HorizonCode_Horizon_È.áŒŠ + 4.0f;
        return this.HorizonCode_Horizon_È.Â(this.Ø­áŒŠá.Ø­áŒŠá()) > var1 * var1;
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(this.Ý, this.Â);
    }
    
    @Override
    public void Ý() {
        if (this.HorizonCode_Horizon_È.Š().Ó() || this.HorizonCode_Horizon_È.Â(this.Ø­áŒŠá.Ø­áŒŠá()) < 16.0) {
            this.Ó.add(this.Ø­áŒŠá);
        }
    }
    
    private VillageDoorInfo HorizonCode_Horizon_È(final Village p_75412_1_) {
        VillageDoorInfo var2 = null;
        int var3 = Integer.MAX_VALUE;
        final List var4 = p_75412_1_.Ó();
        for (final VillageDoorInfo var6 : var4) {
            final int var7 = var6.HorizonCode_Horizon_È(MathHelper.Ý(this.HorizonCode_Horizon_È.ŒÏ), MathHelper.Ý(this.HorizonCode_Horizon_È.Çªà¢), MathHelper.Ý(this.HorizonCode_Horizon_È.Ê));
            if (var7 < var3 && !this.HorizonCode_Horizon_È(var6)) {
                var2 = var6;
                var3 = var7;
            }
        }
        return var2;
    }
    
    private boolean HorizonCode_Horizon_È(final VillageDoorInfo p_75413_1_) {
        for (final VillageDoorInfo var3 : this.Ó) {
            if (p_75413_1_.Ø­áŒŠá().equals(var3.Ø­áŒŠá())) {
                return true;
            }
        }
        return false;
    }
    
    private void Ø() {
        if (this.Ó.size() > 15) {
            this.Ó.remove(0);
        }
    }
}
