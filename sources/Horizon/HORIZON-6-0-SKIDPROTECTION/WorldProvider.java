package HORIZON-6-0-SKIDPROTECTION;

public abstract class WorldProvider
{
    public static final float[] HorizonCode_Horizon_È;
    protected World Â;
    private WorldType Ø;
    private String áŒŠÆ;
    protected WorldChunkManager Ý;
    protected boolean Ø­áŒŠá;
    protected boolean Âµá€;
    protected final float[] Ó;
    protected int à;
    private final float[] áˆºÑ¢Õ;
    private static final String ÂµÈ = "CL_00000386";
    
    static {
        HorizonCode_Horizon_È = new float[] { 1.0f, 0.75f, 0.5f, 0.25f, 0.0f, 0.25f, 0.5f, 0.75f };
    }
    
    public WorldProvider() {
        this.Ó = new float[16];
        this.áˆºÑ¢Õ = new float[4];
    }
    
    public final void HorizonCode_Horizon_È(final World worldIn) {
        this.Â = worldIn;
        this.Ø = worldIn.ŒÏ().Ø­à();
        this.áŒŠÆ = worldIn.ŒÏ().Ñ¢á();
        this.Â();
        this.HorizonCode_Horizon_È();
    }
    
    protected void HorizonCode_Horizon_È() {
        final float var1 = 0.0f;
        for (int var2 = 0; var2 <= 15; ++var2) {
            final float var3 = 1.0f - var2 / 15.0f;
            this.Ó[var2] = (1.0f - var3) / (var3 * 3.0f + 1.0f) * (1.0f - var1) + var1;
        }
    }
    
    protected void Â() {
        final WorldType var1 = this.Â.ŒÏ().Ø­à();
        if (var1 == WorldType.Ø­áŒŠá) {
            final FlatGeneratorInfo var2 = FlatGeneratorInfo.HorizonCode_Horizon_È(this.Â.ŒÏ().Ñ¢á());
            this.Ý = new WorldChunkManagerHell(BiomeGenBase.HorizonCode_Horizon_È(var2.HorizonCode_Horizon_È(), BiomeGenBase.ÇªÓ), 0.5f);
        }
        else if (var1 == WorldType.Ø) {
            this.Ý = new WorldChunkManagerHell(BiomeGenBase.µà, 0.0f);
        }
        else {
            this.Ý = new WorldChunkManager(this.Â);
        }
    }
    
    public IChunkProvider Ý() {
        return (this.Ø == WorldType.Ø­áŒŠá) ? new ChunkProviderFlat(this.Â, this.Â.Æ(), this.Â.ŒÏ().ˆà(), this.áŒŠÆ) : ((this.Ø == WorldType.Ø) ? new ChunkProviderDebug(this.Â) : ((this.Ø == WorldType.à) ? new ChunkProviderGenerate(this.Â, this.Â.Æ(), this.Â.ŒÏ().ˆà(), this.áŒŠÆ) : new ChunkProviderGenerate(this.Â, this.Â.Æ(), this.Â.ŒÏ().ˆà(), this.áŒŠÆ)));
    }
    
    public boolean HorizonCode_Horizon_È(final int x, final int z) {
        return this.Â.Âµá€(new BlockPos(x, 0, z)) == Blocks.Ø­áŒŠá;
    }
    
    public float HorizonCode_Horizon_È(final long p_76563_1_, final float p_76563_3_) {
        final int var4 = (int)(p_76563_1_ % 24000L);
        float var5 = (var4 + p_76563_3_) / 24000.0f - 0.25f;
        if (var5 < 0.0f) {
            ++var5;
        }
        if (var5 > 1.0f) {
            --var5;
        }
        final float var6 = var5;
        var5 = 1.0f - (float)((Math.cos(var5 * 3.141592653589793) + 1.0) / 2.0);
        var5 = var6 + (var5 - var6) / 3.0f;
        return var5;
    }
    
    public int HorizonCode_Horizon_È(final long p_76559_1_) {
        return (int)(p_76559_1_ / 24000L % 8L + 8L) % 8;
    }
    
    public boolean Ø­áŒŠá() {
        return true;
    }
    
    public float[] HorizonCode_Horizon_È(final float p_76560_1_, final float p_76560_2_) {
        final float var3 = 0.4f;
        final float var4 = MathHelper.Â(p_76560_1_ * 3.1415927f * 2.0f) - 0.0f;
        final float var5 = -0.0f;
        if (var4 >= var5 - var3 && var4 <= var5 + var3) {
            final float var6 = (var4 - var5) / var3 * 0.5f + 0.5f;
            float var7 = 1.0f - (1.0f - MathHelper.HorizonCode_Horizon_È(var6 * 3.1415927f)) * 0.99f;
            var7 *= var7;
            this.áˆºÑ¢Õ[0] = var6 * 0.3f + 0.7f;
            this.áˆºÑ¢Õ[1] = var6 * var6 * 0.7f + 0.2f;
            this.áˆºÑ¢Õ[2] = var6 * var6 * 0.0f + 0.2f;
            this.áˆºÑ¢Õ[3] = var7;
            return this.áˆºÑ¢Õ;
        }
        return null;
    }
    
    public Vec3 Â(final float p_76562_1_, final float p_76562_2_) {
        float var3 = MathHelper.Â(p_76562_1_ * 3.1415927f * 2.0f) * 2.0f + 0.5f;
        var3 = MathHelper.HorizonCode_Horizon_È(var3, 0.0f, 1.0f);
        float var4 = 0.7529412f;
        float var5 = 0.84705883f;
        float var6 = 1.0f;
        var4 *= var3 * 0.94f + 0.06f;
        var5 *= var3 * 0.94f + 0.06f;
        var6 *= var3 * 0.91f + 0.09f;
        return new Vec3(var4, var5, var6);
    }
    
    public boolean Âµá€() {
        return true;
    }
    
    public static WorldProvider HorizonCode_Horizon_È(final int dimension) {
        return (dimension == -1) ? new WorldProviderHell() : ((dimension == 0) ? new WorldProviderSurface() : ((dimension == 1) ? new WorldProviderEnd() : null));
    }
    
    public float Ó() {
        return 128.0f;
    }
    
    public boolean à() {
        return true;
    }
    
    public BlockPos Ø() {
        return null;
    }
    
    public int áŒŠÆ() {
        return (this.Ø == WorldType.Ø­áŒŠá) ? 4 : 64;
    }
    
    public double áˆºÑ¢Õ() {
        return (this.Ø == WorldType.Ø­áŒŠá) ? 1.0 : 0.03125;
    }
    
    public boolean Â(final int p_76568_1_, final int p_76568_2_) {
        return false;
    }
    
    public abstract String ÂµÈ();
    
    public abstract String á();
    
    public WorldChunkManager ˆÏ­() {
        return this.Ý;
    }
    
    public boolean £á() {
        return this.Ø­áŒŠá;
    }
    
    public boolean Å() {
        return this.Âµá€;
    }
    
    public float[] £à() {
        return this.Ó;
    }
    
    public int µà() {
        return this.à;
    }
    
    public WorldBorder ˆà() {
        return new WorldBorder();
    }
}
