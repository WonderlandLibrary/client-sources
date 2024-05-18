package HORIZON-6-0-SKIDPROTECTION;

public class WorldProviderHell extends WorldProvider
{
    private static final String Ø = "CL_00000387";
    
    public void Â() {
        this.Ý = new WorldChunkManagerHell(BiomeGenBase.Ï­Ðƒà, 0.0f);
        this.Ø­áŒŠá = true;
        this.Âµá€ = true;
        this.à = -1;
    }
    
    @Override
    public Vec3 Â(final float p_76562_1_, final float p_76562_2_) {
        return new Vec3(0.20000000298023224, 0.029999999329447746, 0.029999999329447746);
    }
    
    @Override
    protected void HorizonCode_Horizon_È() {
        final float var1 = 0.1f;
        for (int var2 = 0; var2 <= 15; ++var2) {
            final float var3 = 1.0f - var2 / 15.0f;
            this.Ó[var2] = (1.0f - var3) / (var3 * 3.0f + 1.0f) * (1.0f - var1) + var1;
        }
    }
    
    @Override
    public IChunkProvider Ý() {
        return new ChunkProviderHell(this.Â, this.Â.ŒÏ().ˆà(), this.Â.Æ());
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int x, final int z) {
        return false;
    }
    
    @Override
    public float HorizonCode_Horizon_È(final long p_76563_1_, final float p_76563_3_) {
        return 0.5f;
    }
    
    @Override
    public boolean Âµá€() {
        return false;
    }
    
    @Override
    public boolean Â(final int p_76568_1_, final int p_76568_2_) {
        return true;
    }
    
    @Override
    public String ÂµÈ() {
        return "Nether";
    }
    
    @Override
    public String á() {
        return "_nether";
    }
    
    @Override
    public WorldBorder ˆà() {
        return new WorldBorder() {
            private static final String Â = "CL_00002008";
            
            @Override
            public double HorizonCode_Horizon_È() {
                return super.HorizonCode_Horizon_È() / 8.0;
            }
            
            @Override
            public double Â() {
                return super.Â() / 8.0;
            }
        };
    }
}
