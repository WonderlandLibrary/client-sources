package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowGolem extends EntityAIBase
{
    private EntityVillager HorizonCode_Horizon_È;
    private EntityIronGolem Â;
    private int Ý;
    private boolean Ø­áŒŠá;
    private static final String Âµá€ = "CL_00001615";
    
    public EntityAIFollowGolem(final EntityVillager p_i1656_1_) {
        this.HorizonCode_Horizon_È = p_i1656_1_;
        this.HorizonCode_Horizon_È(3);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È() {
        if (this.HorizonCode_Horizon_È.à() >= 0) {
            return false;
        }
        if (!this.HorizonCode_Horizon_È.Ï­Ðƒà.ÂµÈ()) {
            return false;
        }
        final List var1 = this.HorizonCode_Horizon_È.Ï­Ðƒà.HorizonCode_Horizon_È(EntityIronGolem.class, this.HorizonCode_Horizon_È.£É().Â(6.0, 2.0, 6.0));
        if (var1.isEmpty()) {
            return false;
        }
        for (final EntityIronGolem var3 : var1) {
            if (var3.ÇŽÅ() > 0) {
                this.Â = var3;
                break;
            }
        }
        return this.Â != null;
    }
    
    @Override
    public boolean Â() {
        return this.Â.ÇŽÅ() > 0;
    }
    
    @Override
    public void Âµá€() {
        this.Ý = this.HorizonCode_Horizon_È.ˆÐƒØ().nextInt(320);
        this.Ø­áŒŠá = false;
        this.Â.Š().à();
    }
    
    @Override
    public void Ý() {
        this.Â = null;
        this.HorizonCode_Horizon_È.Š().à();
    }
    
    @Override
    public void Ø­áŒŠá() {
        this.HorizonCode_Horizon_È.Ñ¢á().HorizonCode_Horizon_È(this.Â, 30.0f, 30.0f);
        if (this.Â.ÇŽÅ() == this.Ý) {
            this.HorizonCode_Horizon_È.Š().HorizonCode_Horizon_È(this.Â, 0.5);
            this.Ø­áŒŠá = true;
        }
        if (this.Ø­áŒŠá && this.HorizonCode_Horizon_È.Âµá€(this.Â) < 4.0) {
            this.Â.HorizonCode_Horizon_È(false);
            this.HorizonCode_Horizon_È.Š().à();
        }
    }
}
