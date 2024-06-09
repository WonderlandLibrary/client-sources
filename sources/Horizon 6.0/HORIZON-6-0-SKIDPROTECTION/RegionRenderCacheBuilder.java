package HORIZON-6-0-SKIDPROTECTION;

public class RegionRenderCacheBuilder
{
    private final WorldRenderer[] HorizonCode_Horizon_È;
    private static final String Â = "CL_00002564";
    
    public RegionRenderCacheBuilder() {
        (this.HorizonCode_Horizon_È = new WorldRenderer[EnumWorldBlockLayer.values().length])[EnumWorldBlockLayer.HorizonCode_Horizon_È.ordinal()] = new WorldRenderer(2097152);
        this.HorizonCode_Horizon_È[EnumWorldBlockLayer.Ý.ordinal()] = new WorldRenderer(131072);
        this.HorizonCode_Horizon_È[EnumWorldBlockLayer.Â.ordinal()] = new WorldRenderer(131072);
        this.HorizonCode_Horizon_È[EnumWorldBlockLayer.Ø­áŒŠá.ordinal()] = new WorldRenderer(262144);
    }
    
    public WorldRenderer HorizonCode_Horizon_È(final EnumWorldBlockLayer p_179038_1_) {
        return this.HorizonCode_Horizon_È[p_179038_1_.ordinal()];
    }
    
    public WorldRenderer HorizonCode_Horizon_È(final int p_179039_1_) {
        return this.HorizonCode_Horizon_È[p_179039_1_];
    }
}
