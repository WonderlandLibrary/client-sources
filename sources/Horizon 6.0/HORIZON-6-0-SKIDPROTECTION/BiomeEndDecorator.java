package HORIZON-6-0-SKIDPROTECTION;

public class BiomeEndDecorator extends BiomeDecorator
{
    protected WorldGenerator ŠÂµà;
    private static final String ¥à = "CL_00000188";
    
    public BiomeEndDecorator() {
        this.ŠÂµà = new WorldGenSpikes(Blocks.µÊ);
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final BiomeGenBase p_150513_1_) {
        this.HorizonCode_Horizon_È();
        if (this.Â.nextInt(5) == 0) {
            final int var2 = this.Â.nextInt(16) + 8;
            final int var3 = this.Â.nextInt(16) + 8;
            this.ŠÂµà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.HorizonCode_Horizon_È.ˆà(this.Ý.Â(var2, 0, var3)));
        }
        if (this.Ý.HorizonCode_Horizon_È() == 0 && this.Ý.Ý() == 0) {
            final EntityDragon var4 = new EntityDragon(this.HorizonCode_Horizon_È);
            var4.Â(0.0, 128.0, 0.0, this.Â.nextFloat() * 360.0f, 0.0f);
            this.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var4);
        }
    }
}
