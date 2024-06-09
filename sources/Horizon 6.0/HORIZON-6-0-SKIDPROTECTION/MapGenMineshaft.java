package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Map;

public class MapGenMineshaft extends MapGenStructure
{
    private double Âµá€;
    private static final String Ó = "CL_00000443";
    
    public MapGenMineshaft() {
        this.Âµá€ = 0.004;
    }
    
    @Override
    public String HorizonCode_Horizon_È() {
        return "Mineshaft";
    }
    
    public MapGenMineshaft(final Map p_i2034_1_) {
        this.Âµá€ = 0.004;
        for (final Map.Entry var3 : p_i2034_1_.entrySet()) {
            if (var3.getKey().equals("chance")) {
                this.Âµá€ = MathHelper.HorizonCode_Horizon_È(var3.getValue(), this.Âµá€);
            }
        }
    }
    
    @Override
    protected boolean HorizonCode_Horizon_È(final int p_75047_1_, final int p_75047_2_) {
        return this.Â.nextDouble() < this.Âµá€ && this.Â.nextInt(80) < Math.max(Math.abs(p_75047_1_), Math.abs(p_75047_2_));
    }
    
    @Override
    protected StructureStart Â(final int p_75049_1_, final int p_75049_2_) {
        return new StructureMineshaftStart(this.Ý, this.Â, p_75049_1_, p_75049_2_);
    }
}
