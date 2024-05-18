package HORIZON-6-0-SKIDPROTECTION;

public class BiomeGenEnd extends BiomeGenBase
{
    private static final String Ñ¢à = "CL_00000187";
    
    public BiomeGenEnd(final int p_i1990_1_) {
        super(p_i1990_1_);
        this.áˆºÇŽØ.clear();
        this.ÇªÂµÕ.clear();
        this.áŒŠÏ.clear();
        this.áŒŠáŠ.clear();
        this.áˆºÇŽØ.add(new Â(EntityEnderman.class, 10, 4, 4));
        this.Ï­Ï­Ï = Blocks.Âµá€.¥à();
        this.£Â = Blocks.Âµá€.¥à();
        this.ˆÏ = new BiomeEndDecorator();
    }
    
    @Override
    public int HorizonCode_Horizon_È(final float p_76731_1_) {
        return 0;
    }
}
