package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BiomeDecorator
{
    protected World HorizonCode_Horizon_È;
    protected Random Â;
    protected BlockPos Ý;
    protected ChunkProviderSettings Ø­áŒŠá;
    protected WorldGenerator Âµá€;
    protected WorldGenerator Ó;
    protected WorldGenerator à;
    protected WorldGenerator Ø;
    protected WorldGenerator áŒŠÆ;
    protected WorldGenerator áˆºÑ¢Õ;
    protected WorldGenerator ÂµÈ;
    protected WorldGenerator á;
    protected WorldGenerator ˆÏ­;
    protected WorldGenerator £á;
    protected WorldGenerator Å;
    protected WorldGenerator £à;
    protected WorldGenerator µà;
    protected WorldGenerator ˆà;
    protected WorldGenFlowers ¥Æ;
    protected WorldGenerator Ø­à;
    protected WorldGenerator µÕ;
    protected WorldGenerator Æ;
    protected WorldGenerator Šáƒ;
    protected WorldGenerator Ï­Ðƒà;
    protected WorldGenerator áŒŠà;
    protected int ŠÄ;
    protected int Ñ¢á;
    protected int ŒÏ;
    protected int Çªà¢;
    protected int Ê;
    protected int ÇŽÉ;
    protected int ˆá;
    protected int ÇŽÕ;
    protected int É;
    protected int áƒ;
    protected int á€;
    protected int Õ;
    public boolean à¢;
    private static final String ŠÂµà = "CL_00000164";
    
    public BiomeDecorator() {
        this.Âµá€ = new WorldGenClay(4);
        this.Ó = new WorldGenSand(Blocks.£á, 7);
        this.à = new WorldGenSand(Blocks.Å, 6);
        this.¥Æ = new WorldGenFlowers(Blocks.Âµà, BlockFlower.Â.HorizonCode_Horizon_È);
        this.Ø­à = new GeneratorBushFeature(Blocks.È);
        this.µÕ = new GeneratorBushFeature(Blocks.áŠ);
        this.Æ = new WorldGenBigMushroom();
        this.Šáƒ = new WorldGenReed();
        this.Ï­Ðƒà = new WorldGenCactus();
        this.áŒŠà = new WorldGenWaterlily();
        this.ŒÏ = 2;
        this.Çªà¢ = 1;
        this.É = 1;
        this.áƒ = 3;
        this.á€ = 1;
        this.à¢ = true;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final Random p_180292_2_, final BiomeGenBase p_180292_3_, final BlockPos p_180292_4_) {
        if (this.HorizonCode_Horizon_È != null) {
            throw new RuntimeException("Already decorating");
        }
        this.HorizonCode_Horizon_È = worldIn;
        final String var5 = worldIn.ŒÏ().Ñ¢á();
        if (var5 != null) {
            this.Ø­áŒŠá = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var5).Â();
        }
        else {
            this.Ø­áŒŠá = ChunkProviderSettings.HorizonCode_Horizon_È.HorizonCode_Horizon_È("").Â();
        }
        this.Â = p_180292_2_;
        this.Ý = p_180292_4_;
        this.Ø = new WorldGenMinable(Blocks.Âµá€.¥à(), this.Ø­áŒŠá.áƒ);
        this.áŒŠÆ = new WorldGenMinable(Blocks.Å.¥à(), this.Ø­áŒŠá.ŠÂµà);
        this.áˆºÑ¢Õ = new WorldGenMinable(Blocks.Ý.¥à().HorizonCode_Horizon_È(BlockStone.Õ, BlockStone.HorizonCode_Horizon_È.Â), this.Ø­áŒŠá.È);
        this.ÂµÈ = new WorldGenMinable(Blocks.Ý.¥à().HorizonCode_Horizon_È(BlockStone.Õ, BlockStone.HorizonCode_Horizon_È.Ø­áŒŠá), this.Ø­áŒŠá.£ÂµÄ);
        this.á = new WorldGenMinable(Blocks.Ý.¥à().HorizonCode_Horizon_È(BlockStone.Õ, BlockStone.HorizonCode_Horizon_È.Ó), this.Ø­áŒŠá.Ï­à);
        this.ˆÏ­ = new WorldGenMinable(Blocks.ˆà.¥à(), this.Ø­áŒŠá.Ô);
        this.£á = new WorldGenMinable(Blocks.µà.¥à(), this.Ø­áŒŠá.Œ);
        this.Å = new WorldGenMinable(Blocks.£à.¥à(), this.Ø­áŒŠá.Ï­Ï­Ï);
        this.£à = new WorldGenMinable(Blocks.Ñ¢à.¥à(), this.Ø­áŒŠá.£Õ);
        this.µà = new WorldGenMinable(Blocks.£Ï.¥à(), this.Ø­áŒŠá.ˆÏ);
        this.ˆà = new WorldGenMinable(Blocks.áŒŠà.¥à(), this.Ø­áŒŠá.áŒŠáŠ);
        this.HorizonCode_Horizon_È(p_180292_3_);
        this.HorizonCode_Horizon_È = null;
        this.Â = null;
    }
    
    protected void HorizonCode_Horizon_È(final BiomeGenBase p_150513_1_) {
        this.HorizonCode_Horizon_È();
        for (int var2 = 0; var2 < this.áƒ; ++var2) {
            final int var3 = this.Â.nextInt(16) + 8;
            final int var4 = this.Â.nextInt(16) + 8;
            this.Ó.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.HorizonCode_Horizon_È.ˆà(this.Ý.Â(var3, 0, var4)));
        }
        for (int var2 = 0; var2 < this.á€; ++var2) {
            final int var3 = this.Â.nextInt(16) + 8;
            final int var4 = this.Â.nextInt(16) + 8;
            this.Âµá€.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.HorizonCode_Horizon_È.ˆà(this.Ý.Â(var3, 0, var4)));
        }
        for (int var2 = 0; var2 < this.É; ++var2) {
            final int var3 = this.Â.nextInt(16) + 8;
            final int var4 = this.Â.nextInt(16) + 8;
            this.à.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.HorizonCode_Horizon_È.ˆà(this.Ý.Â(var3, 0, var4)));
        }
        int var2 = this.Ñ¢á;
        if (this.Â.nextInt(10) == 0) {
            ++var2;
        }
        for (int var3 = 0; var3 < var2; ++var3) {
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(16) + 8;
            final WorldGenAbstractTree var6 = p_150513_1_.HorizonCode_Horizon_È(this.Â);
            var6.Âµá€();
            final BlockPos var7 = this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5));
            if (var6.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var7)) {
                var6.Â(this.HorizonCode_Horizon_È, this.Â, var7);
            }
        }
        for (int var3 = 0; var3 < this.Õ; ++var3) {
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(16) + 8;
            this.Æ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5)));
        }
        for (int var3 = 0; var3 < this.ŒÏ; ++var3) {
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(16) + 8;
            final int var8 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5)).Â() + 32);
            final BlockPos var7 = this.Ý.Â(var4, var8, var5);
            final BlockFlower.Â var9 = p_150513_1_.HorizonCode_Horizon_È(this.Â, var7);
            final BlockFlower var10 = var9.Â().HorizonCode_Horizon_È();
            if (var10.Ó() != Material.HorizonCode_Horizon_È) {
                this.¥Æ.HorizonCode_Horizon_È(var10, var9);
                this.¥Æ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var7);
            }
        }
        for (int var3 = 0; var3 < this.Çªà¢; ++var3) {
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(16) + 8;
            final int var8 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5)).Â() * 2);
            p_150513_1_.Â(this.Â).HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.Ý.Â(var4, var8, var5));
        }
        for (int var3 = 0; var3 < this.Ê; ++var3) {
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(16) + 8;
            final int var8 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5)).Â() * 2);
            new WorldGenDeadBush().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.Ý.Â(var4, var8, var5));
        }
        for (int var3 = 0; var3 < this.ŠÄ; ++var3) {
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(16) + 8;
            final int var8 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5)).Â() * 2);
            BlockPos var7;
            BlockPos var11;
            for (var7 = this.Ý.Â(var4, var8, var5); var7.Â() > 0; var7 = var11) {
                var11 = var7.Âµá€();
                if (!this.HorizonCode_Horizon_È.Ø­áŒŠá(var11)) {
                    break;
                }
            }
            this.áŒŠà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var7);
        }
        for (int var3 = 0; var3 < this.ÇŽÉ; ++var3) {
            if (this.Â.nextInt(4) == 0) {
                final int var4 = this.Â.nextInt(16) + 8;
                final int var5 = this.Â.nextInt(16) + 8;
                final BlockPos var12 = this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5));
                this.Ø­à.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var12);
            }
            if (this.Â.nextInt(8) == 0) {
                final int var4 = this.Â.nextInt(16) + 8;
                final int var5 = this.Â.nextInt(16) + 8;
                final int var8 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5)).Â() * 2);
                final BlockPos var7 = this.Ý.Â(var4, var8, var5);
                this.µÕ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var7);
            }
        }
        if (this.Â.nextInt(4) == 0) {
            final int var3 = this.Â.nextInt(16) + 8;
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var3, 0, var4)).Â() * 2);
            this.Ø­à.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.Ý.Â(var3, var5, var4));
        }
        if (this.Â.nextInt(8) == 0) {
            final int var3 = this.Â.nextInt(16) + 8;
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var3, 0, var4)).Â() * 2);
            this.µÕ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.Ý.Â(var3, var5, var4));
        }
        for (int var3 = 0; var3 < this.ˆá; ++var3) {
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(16) + 8;
            final int var8 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5)).Â() * 2);
            this.Šáƒ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.Ý.Â(var4, var8, var5));
        }
        for (int var3 = 0; var3 < 10; ++var3) {
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(16) + 8;
            final int var8 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5)).Â() * 2);
            this.Šáƒ.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.Ý.Â(var4, var8, var5));
        }
        if (this.Â.nextInt(32) == 0) {
            final int var3 = this.Â.nextInt(16) + 8;
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var3, 0, var4)).Â() * 2);
            new WorldGenPumpkin().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.Ý.Â(var3, var5, var4));
        }
        for (int var3 = 0; var3 < this.ÇŽÕ; ++var3) {
            final int var4 = this.Â.nextInt(16) + 8;
            final int var5 = this.Â.nextInt(16) + 8;
            final int var8 = this.Â.nextInt(this.HorizonCode_Horizon_È.£á(this.Ý.Â(var4, 0, var5)).Â() * 2);
            this.Ï­Ðƒà.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, this.Ý.Â(var4, var8, var5));
        }
        if (this.à¢) {
            for (int var3 = 0; var3 < 50; ++var3) {
                final BlockPos var13 = this.Ý.Â(this.Â.nextInt(16) + 8, this.Â.nextInt(this.Â.nextInt(248) + 8), this.Â.nextInt(16) + 8);
                new WorldGenLiquids(Blocks.áˆºÑ¢Õ).HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var13);
            }
            for (int var3 = 0; var3 < 20; ++var3) {
                final BlockPos var13 = this.Ý.Â(this.Â.nextInt(16) + 8, this.Â.nextInt(this.Â.nextInt(this.Â.nextInt(240) + 8) + 8), this.Â.nextInt(16) + 8);
                new WorldGenLiquids(Blocks.á).HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var13);
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final int p_76795_1_, final WorldGenerator p_76795_2_, int p_76795_3_, int p_76795_4_) {
        if (p_76795_4_ < p_76795_3_) {
            final int var5 = p_76795_3_;
            p_76795_3_ = p_76795_4_;
            p_76795_4_ = var5;
        }
        else if (p_76795_4_ == p_76795_3_) {
            if (p_76795_3_ < 255) {
                ++p_76795_4_;
            }
            else {
                --p_76795_3_;
            }
        }
        for (int var5 = 0; var5 < p_76795_1_; ++var5) {
            final BlockPos var6 = this.Ý.Â(this.Â.nextInt(16), this.Â.nextInt(p_76795_4_ - p_76795_3_) + p_76795_3_, this.Â.nextInt(16));
            p_76795_2_.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var6);
        }
    }
    
    protected void Â(final int p_76793_1_, final WorldGenerator p_76793_2_, final int p_76793_3_, final int p_76793_4_) {
        for (int var5 = 0; var5 < p_76793_1_; ++var5) {
            final BlockPos var6 = this.Ý.Â(this.Â.nextInt(16), this.Â.nextInt(p_76793_4_) + this.Â.nextInt(p_76793_4_) + p_76793_3_ - p_76793_4_, this.Â.nextInt(16));
            p_76793_2_.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È, this.Â, var6);
        }
    }
    
    protected void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá.á€, this.Ø, this.Ø­áŒŠá.Õ, this.Ø­áŒŠá.à¢);
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá.¥à, this.áŒŠÆ, this.Ø­áŒŠá.Âµà, this.Ø­áŒŠá.Ç);
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá.Ø­Âµ, this.ÂµÈ, this.Ø­áŒŠá.Ä, this.Ø­áŒŠá.Ñ¢Â);
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá.áŠ, this.áˆºÑ¢Õ, this.Ø­áŒŠá.ˆáŠ, this.Ø­áŒŠá.áŒŠ);
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá.áˆºáˆºÈ, this.á, this.Ø­áŒŠá.ÇŽá€, this.Ø­áŒŠá.Ï);
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá.ÇªÓ, this.ˆÏ­, this.Ø­áŒŠá.áˆºÏ, this.Ø­áŒŠá.ˆáƒ);
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá.£Ï, this.£á, this.Ø­áŒŠá.Ø­á, this.Ø­áŒŠá.ˆÉ);
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá.£Â, this.Å, this.Ø­áŒŠá.£Ó, this.Ø­áŒŠá.ˆÐƒØ­à);
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá.Ï­Ô, this.£à, this.Ø­áŒŠá.Œà, this.Ø­áŒŠá.Ðƒá);
        this.HorizonCode_Horizon_È(this.Ø­áŒŠá.áˆºÇŽØ, this.µà, this.Ø­áŒŠá.ÇªÂµÕ, this.Ø­áŒŠá.áŒŠÏ);
        this.Â(this.Ø­áŒŠá.ˆÓ, this.ˆà, this.Ø­áŒŠá.¥Ä, this.Ø­áŒŠá.ÇªÔ);
    }
}
