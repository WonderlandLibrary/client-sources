package HORIZON-6-0-SKIDPROTECTION;

public class FlatLayerInfo
{
    private final int HorizonCode_Horizon_È;
    private IBlockState Â;
    private int Ý;
    private int Ø­áŒŠá;
    private static final String Âµá€ = "CL_00000441";
    
    public FlatLayerInfo(final int p_i45467_1_, final Block p_i45467_2_) {
        this(3, p_i45467_1_, p_i45467_2_);
    }
    
    public FlatLayerInfo(final int p_i45627_1_, final int p_i45627_2_, final Block p_i45627_3_) {
        this.Ý = 1;
        this.HorizonCode_Horizon_È = p_i45627_1_;
        this.Ý = p_i45627_2_;
        this.Â = p_i45627_3_.¥à();
    }
    
    public FlatLayerInfo(final int p_i45628_1_, final int p_i45628_2_, final Block p_i45628_3_, final int p_i45628_4_) {
        this(p_i45628_1_, p_i45628_2_, p_i45628_3_);
        this.Â = p_i45628_3_.Ý(p_i45628_4_);
    }
    
    public int HorizonCode_Horizon_È() {
        return this.Ý;
    }
    
    public IBlockState Â() {
        return this.Â;
    }
    
    private Block Ø­áŒŠá() {
        return this.Â.Ý();
    }
    
    private int Âµá€() {
        return this.Â.Ý().Ý(this.Â);
    }
    
    public int Ý() {
        return this.Ø­áŒŠá;
    }
    
    public void HorizonCode_Horizon_È(final int p_82660_1_) {
        this.Ø­áŒŠá = p_82660_1_;
    }
    
    @Override
    public String toString() {
        String var3;
        if (this.HorizonCode_Horizon_È >= 3) {
            final ResourceLocation_1975012498 var2 = (ResourceLocation_1975012498)Block.HorizonCode_Horizon_È.Â(this.Ø­áŒŠá());
            var3 = ((var2 == null) ? "null" : var2.toString());
            if (this.Ý > 1) {
                var3 = String.valueOf(this.Ý) + "*" + var3;
            }
        }
        else {
            var3 = Integer.toString(Block.HorizonCode_Horizon_È(this.Ø­áŒŠá()));
            if (this.Ý > 1) {
                var3 = String.valueOf(this.Ý) + "x" + var3;
            }
        }
        final int var4 = this.Âµá€();
        if (var4 > 0) {
            var3 = String.valueOf(var3) + ":" + var4;
        }
        return var3;
    }
}
