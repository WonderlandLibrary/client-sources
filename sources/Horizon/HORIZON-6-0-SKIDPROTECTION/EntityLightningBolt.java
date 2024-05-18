package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class EntityLightningBolt extends EntityWeatherEffect
{
    private int Â;
    public long HorizonCode_Horizon_È;
    private int Ý;
    private static final String Ø­áŒŠá = "CL_00001666";
    
    public EntityLightningBolt(final World worldIn, final double p_i1703_2_, final double p_i1703_4_, final double p_i1703_6_) {
        super(worldIn);
        this.Â(p_i1703_2_, p_i1703_4_, p_i1703_6_, 0.0f, 0.0f);
        this.Â = 2;
        this.HorizonCode_Horizon_È = this.ˆáƒ.nextLong();
        this.Ý = this.ˆáƒ.nextInt(3) + 1;
        if (!worldIn.ŠÄ && worldIn.Çªà¢().Â("doFireTick") && (worldIn.ŠÂµà() == EnumDifficulty.Ý || worldIn.ŠÂµà() == EnumDifficulty.Ø­áŒŠá) && worldIn.Â(new BlockPos(this), 10)) {
            final BlockPos var8 = new BlockPos(this);
            if (worldIn.Â(var8).Ý().Ó() == Material.HorizonCode_Horizon_È && Blocks.Ô.Ø­áŒŠá(worldIn, var8)) {
                worldIn.Â(var8, Blocks.Ô.¥à());
            }
            for (int var9 = 0; var9 < 4; ++var9) {
                final BlockPos var10 = var8.Â(this.ˆáƒ.nextInt(3) - 1, this.ˆáƒ.nextInt(3) - 1, this.ˆáƒ.nextInt(3) - 1);
                if (worldIn.Â(var10).Ý().Ó() == Material.HorizonCode_Horizon_È && Blocks.Ô.Ø­áŒŠá(worldIn, var10)) {
                    worldIn.Â(var10, Blocks.Ô.¥à());
                }
            }
        }
    }
    
    @Override
    public void á() {
        super.á();
        if (this.Â == 2) {
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, "ambient.weather.thunder", 10000.0f, 0.8f + this.ˆáƒ.nextFloat() * 0.2f);
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this.ŒÏ, this.Çªà¢, this.Ê, "random.explode", 2.0f, 0.5f + this.ˆáƒ.nextFloat() * 0.2f);
        }
        --this.Â;
        if (this.Â < 0) {
            if (this.Ý == 0) {
                this.á€();
            }
            else if (this.Â < -this.ˆáƒ.nextInt(10)) {
                --this.Ý;
                this.Â = 1;
                this.HorizonCode_Horizon_È = this.ˆáƒ.nextLong();
                final BlockPos var1 = new BlockPos(this);
                if (!this.Ï­Ðƒà.ŠÄ && this.Ï­Ðƒà.Çªà¢().Â("doFireTick") && this.Ï­Ðƒà.Â(var1, 10) && this.Ï­Ðƒà.Â(var1).Ý().Ó() == Material.HorizonCode_Horizon_È && Blocks.Ô.Ø­áŒŠá(this.Ï­Ðƒà, var1)) {
                    this.Ï­Ðƒà.Â(var1, Blocks.Ô.¥à());
                }
            }
        }
        if (this.Â >= 0) {
            if (this.Ï­Ðƒà.ŠÄ) {
                this.Ï­Ðƒà.Ø­áŒŠá(2);
            }
            else {
                final double var2 = 3.0;
                final List var3 = this.Ï­Ðƒà.Â(this, new AxisAlignedBB(this.ŒÏ - var2, this.Çªà¢ - var2, this.Ê - var2, this.ŒÏ + var2, this.Çªà¢ + 6.0 + var2, this.Ê + var2));
                for (int var4 = 0; var4 < var3.size(); ++var4) {
                    final Entity var5 = var3.get(var4);
                    var5.HorizonCode_Horizon_È(this);
                }
            }
        }
    }
    
    @Override
    protected void ÂµÈ() {
    }
    
    @Override
    protected void Â(final NBTTagCompound tagCompund) {
    }
    
    @Override
    protected void HorizonCode_Horizon_È(final NBTTagCompound tagCompound) {
    }
}
