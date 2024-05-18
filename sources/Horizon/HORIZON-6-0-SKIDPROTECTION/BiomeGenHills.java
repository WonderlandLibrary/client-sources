package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BiomeGenHills extends BiomeGenBase
{
    private WorldGenerator Ñ¢à;
    private WorldGenTaiga2 ÇªØ­;
    private int £áŒŠá;
    private int áˆº;
    private int Šà;
    private int áŒŠá€;
    private static final String ¥Ï = "CL_00000168";
    
    protected BiomeGenHills(final int p_i45373_1_, final boolean p_i45373_2_) {
        super(p_i45373_1_);
        this.Ñ¢à = new WorldGenMinable(Blocks.ÐƒÂ.¥à().HorizonCode_Horizon_È(BlockSilverfish.Õ, BlockSilverfish.HorizonCode_Horizon_È.HorizonCode_Horizon_È), 9);
        this.ÇªØ­ = new WorldGenTaiga2(false);
        this.£áŒŠá = 0;
        this.áˆº = 1;
        this.Šà = 2;
        this.áŒŠá€ = this.£áŒŠá;
        if (p_i45373_2_) {
            this.ˆÏ.Ñ¢á = 3;
            this.áŒŠá€ = this.áˆº;
        }
    }
    
    @Override
    public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
        return (p_150567_1_.nextInt(3) > 0) ? this.ÇªØ­ : super.HorizonCode_Horizon_È(p_150567_1_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        super.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_);
        for (int var4 = 3 + p_180624_2_.nextInt(6), var5 = 0; var5 < var4; ++var5) {
            final int var6 = p_180624_2_.nextInt(16);
            final int var7 = p_180624_2_.nextInt(28) + 4;
            final int var8 = p_180624_2_.nextInt(16);
            final BlockPos var9 = p_180624_3_.Â(var6, var7, var8);
            if (worldIn.Â(var9).Ý() == Blocks.Ý) {
                worldIn.HorizonCode_Horizon_È(var9, Blocks.µÐƒÓ.¥à(), 2);
            }
        }
        for (int var4 = 0; var4 < 7; ++var4) {
            final int var5 = p_180624_2_.nextInt(16);
            final int var6 = p_180624_2_.nextInt(64);
            final int var7 = p_180624_2_.nextInt(16);
            this.Ñ¢à.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_.Â(var5, var6, var7));
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_, final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
        this.Ï­Ï­Ï = Blocks.Ø­áŒŠá.¥à();
        this.£Â = Blocks.Âµá€.¥à();
        if ((p_180622_6_ < -1.0 || p_180622_6_ > 2.0) && this.áŒŠá€ == this.Šà) {
            this.Ï­Ï­Ï = Blocks.Å.¥à();
            this.£Â = Blocks.Å.¥à();
        }
        else if (p_180622_6_ > 1.0 && this.áŒŠá€ != this.áˆº) {
            this.Ï­Ï­Ï = Blocks.Ý.¥à();
            this.£Â = Blocks.Ý.¥à();
        }
        this.Â(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
    }
    
    private BiomeGenHills Â(final BiomeGenBase p_150633_1_) {
        this.áŒŠá€ = this.Šà;
        this.HorizonCode_Horizon_È(p_150633_1_.Ø­á, true);
        this.HorizonCode_Horizon_È(String.valueOf(p_150633_1_.£Ï) + " M");
        this.HorizonCode_Horizon_È(new HorizonCode_Horizon_È(p_150633_1_.ˆÐƒØ­à, p_150633_1_.£Õ));
        this.HorizonCode_Horizon_È(p_150633_1_.Ï­Ô, p_150633_1_.Œà);
        return this;
    }
    
    @Override
    protected BiomeGenBase Ø­áŒŠá(final int p_180277_1_) {
        return new BiomeGenHills(p_180277_1_, false).Â(this);
    }
}
