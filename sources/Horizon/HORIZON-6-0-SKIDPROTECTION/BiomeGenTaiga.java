package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BiomeGenTaiga extends BiomeGenBase
{
    private static final WorldGenTaiga1 Ñ¢à;
    private static final WorldGenTaiga2 ÇªØ­;
    private static final WorldGenMegaPineTree £áŒŠá;
    private static final WorldGenMegaPineTree áˆº;
    private static final WorldGenBlockBlob Šà;
    private int áŒŠá€;
    private static final String ¥Ï = "CL_00000186";
    
    static {
        Ñ¢à = new WorldGenTaiga1();
        ÇªØ­ = new WorldGenTaiga2(false);
        £áŒŠá = new WorldGenMegaPineTree(false, false);
        áˆº = new WorldGenMegaPineTree(false, true);
        Šà = new WorldGenBlockBlob(Blocks.áˆºáˆºÈ, 0);
    }
    
    public BiomeGenTaiga(final int p_i45385_1_, final int p_i45385_2_) {
        super(p_i45385_1_);
        this.áŒŠá€ = p_i45385_2_;
        this.ÇªÂµÕ.add(new Â(EntityWolf.class, 8, 4, 4));
        this.ˆÏ.Ñ¢á = 10;
        if (p_i45385_2_ != 1 && p_i45385_2_ != 2) {
            this.ˆÏ.Çªà¢ = 1;
            this.ˆÏ.ÇŽÉ = 1;
        }
        else {
            this.ˆÏ.Çªà¢ = 7;
            this.ˆÏ.Ê = 1;
            this.ˆÏ.ÇŽÉ = 3;
        }
    }
    
    @Override
    public WorldGenAbstractTree HorizonCode_Horizon_È(final Random p_150567_1_) {
        return ((this.áŒŠá€ == 1 || this.áŒŠá€ == 2) && p_150567_1_.nextInt(3) == 0) ? ((this.áŒŠá€ != 2 && p_150567_1_.nextInt(13) != 0) ? BiomeGenTaiga.£áŒŠá : BiomeGenTaiga.áˆº) : ((p_150567_1_.nextInt(3) == 0) ? BiomeGenTaiga.Ñ¢à : BiomeGenTaiga.ÇªØ­);
    }
    
    @Override
    public WorldGenerator Â(final Random p_76730_1_) {
        return (p_76730_1_.nextInt(5) > 0) ? new WorldGenTallGrass(BlockTallGrass.HorizonCode_Horizon_È.Ý) : new WorldGenTallGrass(BlockTallGrass.HorizonCode_Horizon_È.Â);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180624_2_, final BlockPos p_180624_3_) {
        if (this.áŒŠá€ == 1 || this.áŒŠá€ == 2) {
            for (int var4 = p_180624_2_.nextInt(3), var5 = 0; var5 < var4; ++var5) {
                final int var6 = p_180624_2_.nextInt(16) + 8;
                final int var7 = p_180624_2_.nextInt(16) + 8;
                final BlockPos var8 = worldIn.£á(p_180624_3_.Â(var6, 0, var7));
                BiomeGenTaiga.Šà.HorizonCode_Horizon_È(worldIn, p_180624_2_, var8);
            }
        }
        BiomeGenTaiga.Œ.HorizonCode_Horizon_È(BlockDoublePlant.Â.Ø­áŒŠá);
        for (int var4 = 0; var4 < 7; ++var4) {
            final int var5 = p_180624_2_.nextInt(16) + 8;
            final int var6 = p_180624_2_.nextInt(16) + 8;
            final int var7 = p_180624_2_.nextInt(worldIn.£á(p_180624_3_.Â(var5, 0, var6)).Â() + 32);
            BiomeGenTaiga.Œ.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_.Â(var5, var7, var6));
        }
        super.HorizonCode_Horizon_È(worldIn, p_180624_2_, p_180624_3_);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180622_2_, final ChunkPrimer p_180622_3_, final int p_180622_4_, final int p_180622_5_, final double p_180622_6_) {
        if (this.áŒŠá€ == 1 || this.áŒŠá€ == 2) {
            this.Ï­Ï­Ï = Blocks.Ø­áŒŠá.¥à();
            this.£Â = Blocks.Âµá€.¥à();
            if (p_180622_6_ > 1.75) {
                this.Ï­Ï­Ï = Blocks.Âµá€.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, BlockDirt.HorizonCode_Horizon_È.Â);
            }
            else if (p_180622_6_ > -0.95) {
                this.Ï­Ï­Ï = Blocks.Âµá€.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, BlockDirt.HorizonCode_Horizon_È.Ý);
            }
        }
        this.Â(worldIn, p_180622_2_, p_180622_3_, p_180622_4_, p_180622_5_, p_180622_6_);
    }
    
    @Override
    protected BiomeGenBase Ø­áŒŠá(final int p_180277_1_) {
        return (this.ÇªÔ == BiomeGenBase.Ø­Âµ.ÇªÔ) ? new BiomeGenTaiga(p_180277_1_, 2).HorizonCode_Horizon_È(5858897, true).HorizonCode_Horizon_È("Mega Spruce Taiga").HorizonCode_Horizon_È(5159473).HorizonCode_Horizon_È(0.25f, 0.8f).HorizonCode_Horizon_È(new HorizonCode_Horizon_È(this.ˆÐƒØ­à, this.£Õ)) : super.Ø­áŒŠá(p_180277_1_);
    }
}
