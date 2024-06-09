package HORIZON-6-0-SKIDPROTECTION;

public class WorldProviderEnd extends WorldProvider
{
    private static final String Ø = "CL_00000389";
    
    public void Â() {
        this.Ý = new WorldChunkManagerHell(BiomeGenBase.áŒŠà, 0.0f);
        this.à = 1;
        this.Âµá€ = true;
    }
    
    @Override
    public IChunkProvider Ý() {
        return new ChunkProviderEnd(this.Â, this.Â.Æ());
    }
    
    @Override
    public float HorizonCode_Horizon_È(final long p_76563_1_, final float p_76563_3_) {
        return 0.0f;
    }
    
    @Override
    public float[] HorizonCode_Horizon_È(final float p_76560_1_, final float p_76560_2_) {
        return null;
    }
    
    @Override
    public Vec3 Â(final float p_76562_1_, final float p_76562_2_) {
        final int var3 = 10518688;
        float var4 = MathHelper.Â(p_76562_1_ * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        var4 = MathHelper.HorizonCode_Horizon_È(var4, 0.0f, 1.0f);
        float var5 = (var3 >> 16 & 0xFF) / 255.0f;
        float var6 = (var3 >> 8 & 0xFF) / 255.0f;
        float var7 = (var3 & 0xFF) / 255.0f;
        var5 *= var4 * 0.0f + 0.15f;
        var6 *= var4 * 0.0f + 0.15f;
        var7 *= var4 * 0.0f + 0.15f;
        return new Vec3(var5, var6, var7);
    }
    
    @Override
    public boolean à() {
        return false;
    }
    
    @Override
    public boolean Âµá€() {
        return false;
    }
    
    @Override
    public boolean Ø­áŒŠá() {
        return false;
    }
    
    @Override
    public float Ó() {
        return 8.0f;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final int x, final int z) {
        return this.Â.Âµá€(new BlockPos(x, 0, z)).Ó().Ø­áŒŠá();
    }
    
    @Override
    public BlockPos Ø() {
        return new BlockPos(100, 50, 0);
    }
    
    @Override
    public int áŒŠÆ() {
        return 50;
    }
    
    @Override
    public boolean Â(final int p_76568_1_, final int p_76568_2_) {
        return true;
    }
    
    @Override
    public String ÂµÈ() {
        return "The End";
    }
    
    @Override
    public String á() {
        return "_end";
    }
}
