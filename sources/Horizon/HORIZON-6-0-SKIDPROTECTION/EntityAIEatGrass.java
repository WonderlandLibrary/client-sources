package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicates;
import com.google.common.base.Predicate;

public class EntityAIEatGrass extends EntityAIBase
{
    private static final Predicate Â;
    private EntityLiving Ý;
    private World Ø­áŒŠá;
    int HorizonCode_Horizon_È;
    private static final String Âµá€ = "CL_00001582";
    
    static {
        Â = (Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.áƒ).HorizonCode_Horizon_È(BlockTallGrass.Õ, Predicates.equalTo((Object)BlockTallGrass.HorizonCode_Horizon_È.Â));
    }
    
    public EntityAIEatGrass(final EntityLiving p_i45314_1_) {
        this.Ý = p_i45314_1_;
        this.Ø­áŒŠá = p_i45314_1_.Ï­Ðƒà;
        this.HorizonCode_Horizon_È(7);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.Ý.ˆÐƒØ().nextInt(this.Ý.h_() ? 50 : 1000) != 0) {
            return false;
        }
        final BlockPos var1 = new BlockPos(this.Ý.ŒÏ, this.Ý.Çªà¢, this.Ý.Ê);
        return EntityAIEatGrass.Â.apply((Object)this.Ø­áŒŠá.Â(var1)) || this.Ø­áŒŠá.Â(var1.Âµá€()).Ý() == Blocks.Ø­áŒŠá;
    }
    
    @Override
    public void Âµá€() {
        this.HorizonCode_Horizon_È = 40;
        this.Ø­áŒŠá.HorizonCode_Horizon_È(this.Ý, (byte)10);
        this.Ý.Š().à();
    }
    
    @Override
    public void Ý() {
        this.HorizonCode_Horizon_È = 0;
    }
    
    @Override
    public boolean Â() {
        return this.HorizonCode_Horizon_È > 0;
    }
    
    public int Ø() {
        return this.HorizonCode_Horizon_È;
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È = Math.max(0, this.HorizonCode_Horizon_È - 1);
        if (this.HorizonCode_Horizon_È == 4) {
            final BlockPos var1 = new BlockPos(this.Ý.ŒÏ, this.Ý.Çªà¢, this.Ý.Ê);
            if (EntityAIEatGrass.Â.apply((Object)this.Ø­áŒŠá.Â(var1))) {
                if (this.Ø­áŒŠá.Çªà¢().Â("mobGriefing")) {
                    this.Ø­áŒŠá.Â(var1, false);
                }
                this.Ý.Ø­Æ();
            }
            else {
                final BlockPos var2 = var1.Âµá€();
                if (this.Ø­áŒŠá.Â(var2).Ý() == Blocks.Ø­áŒŠá) {
                    if (this.Ø­áŒŠá.Çªà¢().Â("mobGriefing")) {
                        this.Ø­áŒŠá.Â(2001, var2, Block.HorizonCode_Horizon_È(Blocks.Ø­áŒŠá));
                        this.Ø­áŒŠá.HorizonCode_Horizon_È(var2, Blocks.Âµá€.¥à(), 2);
                    }
                    this.Ý.Ø­Æ();
                }
            }
        }
    }
}
