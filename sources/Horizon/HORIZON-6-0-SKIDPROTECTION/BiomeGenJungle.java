package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BiomeGenJungle extends BiomeGenBase
{
    private boolean Ñ¢à;
    private static final String ÇªØ­ = "CL_00000175";
    
    public BiomeGenJungle(final int p_i45379_1_, final boolean p_i45379_2_) {
        super(p_i45379_1_);
        this.Ñ¢à = p_i45379_2_;
        if (p_i45379_2_) {
            this.ˆÏ.Ñ¢á = 2;
        }
        else {
            this.ˆÏ.Ñ¢á = 50;
        }
        this.ˆÏ.Çªà¢ = 25;
        this.ˆÏ.ŒÏ = 4;
        if (!p_i45379_2_) {
            this.áˆºÇŽØ.add(new Â(EntityOcelot.class, 2, 1, 1));
        }
        this.ÇªÂµÕ.add(new Â(EntityChicken.class, 10, 4, 4));
    }
    
    @Override
    public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
        return (p_150567_1_.nextInt(10) == 0) ? this.ŠÓ : ((p_150567_1_.nextInt(2) == 0) ? new WorldGenShrub(BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()) : ((!this.Ñ¢à && p_150567_1_.nextInt(3) == 0) ? new WorldGenMegaJungle(false, 10, 20, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â()) : new WorldGenTrees(false, 4 + p_150567_1_.nextInt(7), BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), true)));
    }
    
    @Override
    public WorldGenerator Â(final Random p_76730_1_) {
        return (p_76730_1_.nextInt(4) == 0) ? new WorldGenTallGrass(BlockTallGrass.HorizonCode_Horizon_È.Ý) : new WorldGenTallGrass(BlockTallGrass.HorizonCode_Horizon_È.Â);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        super.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_);
        final int var4 = p_180624_2_.nextInt(16) + 8;
        int var5 = p_180624_2_.nextInt(16) + 8;
        int var6 = p_180624_2_.nextInt(worldIn.£á(p_180624_3_.Â(var4, 0, var5)).Â() * 2);
        new WorldGenMelon().HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_.Â(var4, var6, var5));
        final WorldGenVines var7 = new WorldGenVines();
        for (var5 = 0; var5 < 50; ++var5) {
            var6 = p_180624_2_.nextInt(16) + 8;
            final boolean var8 = true;
            final int var9 = p_180624_2_.nextInt(16) + 8;
            var7.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_.Â(var6, 128, var9));
        }
    }
}
