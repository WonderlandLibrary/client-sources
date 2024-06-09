package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;
import com.google.common.collect.Lists;
import java.util.List;

public class StructureMineshaftPieces
{
    private static final List HorizonCode_Horizon_È;
    private static final String Â = "CL_00000444";
    
    static {
        HorizonCode_Horizon_È = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.áˆºÑ¢Õ, 0, 1, 5, 10), new WeightedRandomChestContent(Items.ÂµÈ, 0, 1, 3, 5), new WeightedRandomChestContent(Items.ÇŽá, 0, 4, 9, 5), new WeightedRandomChestContent(Items.áŒŠÔ, EnumDyeColor.á.Ý(), 4, 9, 5), new WeightedRandomChestContent(Items.áŒŠÆ, 0, 1, 2, 3), new WeightedRandomChestContent(Items.Ø, 0, 3, 8, 10), new WeightedRandomChestContent(Items.Ç, 0, 1, 3, 15), new WeightedRandomChestContent(Items.Â, 0, 1, 1, 1), new WeightedRandomChestContent(Item_1028566121.HorizonCode_Horizon_È(Blocks.áŒŠáŠ), 0, 4, 8, 1), new WeightedRandomChestContent(Items.Ï­áˆºÓ, 0, 2, 4, 10), new WeightedRandomChestContent(Items.£áƒ, 0, 2, 4, 10), new WeightedRandomChestContent(Items.Û, 0, 1, 1, 3), new WeightedRandomChestContent(Items.ÐƒÇŽà, 0, 1, 1, 1) });
    }
    
    public static void HorizonCode_Horizon_È() {
        MapGenStructureIO.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class, "MSCorridor");
        MapGenStructureIO.HorizonCode_Horizon_È(Â.class, "MSCrossing");
        MapGenStructureIO.HorizonCode_Horizon_È(Ý.class, "MSRoom");
        MapGenStructureIO.HorizonCode_Horizon_È(Ø­áŒŠá.class, "MSStairs");
    }
    
    private static StructureComponent HorizonCode_Horizon_È(final List p_175892_0_, final Random p_175892_1_, final int p_175892_2_, final int p_175892_3_, final int p_175892_4_, final EnumFacing p_175892_5_, final int p_175892_6_) {
        final int var7 = p_175892_1_.nextInt(100);
        if (var7 >= 80) {
            final StructureBoundingBox var8 = StructureMineshaftPieces.Â.HorizonCode_Horizon_È(p_175892_0_, p_175892_1_, p_175892_2_, p_175892_3_, p_175892_4_, p_175892_5_);
            if (var8 != null) {
                return new Â(p_175892_6_, p_175892_1_, var8, p_175892_5_);
            }
        }
        else if (var7 >= 70) {
            final StructureBoundingBox var8 = Ø­áŒŠá.HorizonCode_Horizon_È(p_175892_0_, p_175892_1_, p_175892_2_, p_175892_3_, p_175892_4_, p_175892_5_);
            if (var8 != null) {
                return new Ø­áŒŠá(p_175892_6_, p_175892_1_, var8, p_175892_5_);
            }
        }
        else {
            final StructureBoundingBox var8 = StructureMineshaftPieces.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_175892_0_, p_175892_1_, p_175892_2_, p_175892_3_, p_175892_4_, p_175892_5_);
            if (var8 != null) {
                return new HorizonCode_Horizon_È(p_175892_6_, p_175892_1_, var8, p_175892_5_);
            }
        }
        return null;
    }
    
    private static StructureComponent Â(final StructureComponent p_175890_0_, final List p_175890_1_, final Random p_175890_2_, final int p_175890_3_, final int p_175890_4_, final int p_175890_5_, final EnumFacing p_175890_6_, final int p_175890_7_) {
        if (p_175890_7_ > 8) {
            return null;
        }
        if (Math.abs(p_175890_3_ - p_175890_0_.Â().HorizonCode_Horizon_È) <= 80 && Math.abs(p_175890_5_ - p_175890_0_.Â().Ý) <= 80) {
            final StructureComponent var8 = HorizonCode_Horizon_È(p_175890_1_, p_175890_2_, p_175890_3_, p_175890_4_, p_175890_5_, p_175890_6_, p_175890_7_ + 1);
            if (var8 != null) {
                p_175890_1_.add(var8);
                var8.HorizonCode_Horizon_È(p_175890_0_, p_175890_1_, p_175890_2_);
            }
            return var8;
        }
        return null;
    }
    
    public static class HorizonCode_Horizon_È extends StructureComponent
    {
        private boolean HorizonCode_Horizon_È;
        private boolean Â;
        private boolean Ý;
        private int Ø­áŒŠá;
        private static final String Ø = "CL_00000445";
        
        public HorizonCode_Horizon_È() {
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            p_143012_1_.HorizonCode_Horizon_È("hr", this.HorizonCode_Horizon_È);
            p_143012_1_.HorizonCode_Horizon_È("sc", this.Â);
            p_143012_1_.HorizonCode_Horizon_È("hps", this.Ý);
            p_143012_1_.HorizonCode_Horizon_È("Num", this.Ø­áŒŠá);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            this.HorizonCode_Horizon_È = p_143011_1_.£á("hr");
            this.Â = p_143011_1_.£á("sc");
            this.Ý = p_143011_1_.£á("hps");
            this.Ø­áŒŠá = p_143011_1_.Ó("Num");
        }
        
        public HorizonCode_Horizon_È(final int p_i45625_1_, final Random p_i45625_2_, final StructureBoundingBox p_i45625_3_, final EnumFacing p_i45625_4_) {
            super(p_i45625_1_);
            this.Ó = p_i45625_4_;
            this.Âµá€ = p_i45625_3_;
            this.HorizonCode_Horizon_È = (p_i45625_2_.nextInt(3) == 0);
            this.Â = (!this.HorizonCode_Horizon_È && p_i45625_2_.nextInt(23) == 0);
            if (this.Ó != EnumFacing.Ý && this.Ó != EnumFacing.Ø­áŒŠá) {
                this.Ø­áŒŠá = p_i45625_3_.Ý() / 5;
            }
            else {
                this.Ø­áŒŠá = p_i45625_3_.Âµá€() / 5;
            }
        }
        
        public static StructureBoundingBox HorizonCode_Horizon_È(final List p_175814_0_, final Random p_175814_1_, final int p_175814_2_, final int p_175814_3_, final int p_175814_4_, final EnumFacing p_175814_5_) {
            final StructureBoundingBox var6 = new StructureBoundingBox(p_175814_2_, p_175814_3_, p_175814_4_, p_175814_2_, p_175814_3_ + 2, p_175814_4_);
            int var7;
            for (var7 = p_175814_1_.nextInt(3) + 2; var7 > 0; --var7) {
                final int var8 = var7 * 5;
                switch (Âµá€.HorizonCode_Horizon_È[p_175814_5_.ordinal()]) {
                    case 1: {
                        var6.Ø­áŒŠá = p_175814_2_ + 2;
                        var6.Ý = p_175814_4_ - (var8 - 1);
                        break;
                    }
                    case 2: {
                        var6.Ø­áŒŠá = p_175814_2_ + 2;
                        var6.Ó = p_175814_4_ + (var8 - 1);
                        break;
                    }
                    case 3: {
                        var6.HorizonCode_Horizon_È = p_175814_2_ - (var8 - 1);
                        var6.Ó = p_175814_4_ + 2;
                        break;
                    }
                    case 4: {
                        var6.Ø­áŒŠá = p_175814_2_ + (var8 - 1);
                        var6.Ó = p_175814_4_ + 2;
                        break;
                    }
                }
                if (StructureComponent.HorizonCode_Horizon_È(p_175814_0_, var6) == null) {
                    break;
                }
            }
            return (var7 > 0) ? var6 : null;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            final int var4 = this.Ý();
            final int var5 = p_74861_3_.nextInt(4);
            if (this.Ó != null) {
                switch (StructureMineshaftPieces.Âµá€.HorizonCode_Horizon_È[this.Ó.ordinal()]) {
                    case 1: {
                        if (var5 <= 1) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ý - 1, this.Ó, var4);
                            break;
                        }
                        if (var5 == 2) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ý, EnumFacing.Âµá€, var4);
                            break;
                        }
                        Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ý, EnumFacing.Ó, var4);
                        break;
                    }
                    case 2: {
                        if (var5 <= 1) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ó + 1, this.Ó, var4);
                            break;
                        }
                        if (var5 == 2) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ó - 3, EnumFacing.Âµá€, var4);
                            break;
                        }
                        Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ó - 3, EnumFacing.Ó, var4);
                        break;
                    }
                    case 3: {
                        if (var5 <= 1) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ý, this.Ó, var4);
                            break;
                        }
                        if (var5 == 2) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ý - 1, EnumFacing.Ý, var4);
                            break;
                        }
                        Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, var4);
                        break;
                    }
                    case 4: {
                        if (var5 <= 1) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ý, this.Ó, var4);
                            break;
                        }
                        if (var5 == 2) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá - 3, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ý - 1, EnumFacing.Ý, var4);
                            break;
                        }
                        Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá - 3, this.Âµá€.Â - 1 + p_74861_3_.nextInt(3), this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, var4);
                        break;
                    }
                }
            }
            if (var4 < 8) {
                if (this.Ó != EnumFacing.Ý && this.Ó != EnumFacing.Ø­áŒŠá) {
                    for (int var6 = this.Âµá€.HorizonCode_Horizon_È + 3; var6 + 3 <= this.Âµá€.Ø­áŒŠá; var6 += 5) {
                        final int var7 = p_74861_3_.nextInt(5);
                        if (var7 == 0) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, var6, this.Âµá€.Â, this.Âµá€.Ý - 1, EnumFacing.Ý, var4 + 1);
                        }
                        else if (var7 == 1) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, var6, this.Âµá€.Â, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, var4 + 1);
                        }
                    }
                }
                else {
                    for (int var6 = this.Âµá€.Ý + 3; var6 + 3 <= this.Âµá€.Ó; var6 += 5) {
                        final int var7 = p_74861_3_.nextInt(5);
                        if (var7 == 0) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â, var6, EnumFacing.Âµá€, var4 + 1);
                        }
                        else if (var7 == 1) {
                            Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â, var6, EnumFacing.Ó, var4 + 1);
                        }
                    }
                }
            }
        }
        
        @Override
        protected boolean HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_180778_2_, final Random p_180778_3_, final int p_180778_4_, final int p_180778_5_, final int p_180778_6_, final List p_180778_7_, final int p_180778_8_) {
            final BlockPos var9 = new BlockPos(this.HorizonCode_Horizon_È(p_180778_4_, p_180778_6_), this.HorizonCode_Horizon_È(p_180778_5_), this.Â(p_180778_4_, p_180778_6_));
            if (p_180778_2_.HorizonCode_Horizon_È(var9) && worldIn.Â(var9).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                final int var10 = p_180778_3_.nextBoolean() ? 1 : 0;
                worldIn.HorizonCode_Horizon_È(var9, Blocks.áŒŠáŠ.Ý(this.HorizonCode_Horizon_È(Blocks.áŒŠáŠ, var10)), 2);
                final EntityMinecartChest var11 = new EntityMinecartChest(worldIn, var9.HorizonCode_Horizon_È() + 0.5f, var9.Â() + 0.5f, var9.Ý() + 0.5f);
                WeightedRandomChestContent.HorizonCode_Horizon_È(p_180778_3_, p_180778_7_, var11, p_180778_8_);
                worldIn.HorizonCode_Horizon_È(var11);
                return true;
            }
            return false;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            final boolean var4 = false;
            final boolean var5 = true;
            final boolean var6 = false;
            final boolean var7 = true;
            final int var8 = this.Ø­áŒŠá * 5 - 1;
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 2, 1, var8, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.8f, 0, 2, 0, 2, 2, var8, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            if (this.Â) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.6f, 0, 0, 0, 2, 1, var8, Blocks.É.¥à(), Blocks.Â.¥à(), false);
            }
            for (int var9 = 0; var9 < this.Ø­áŒŠá; ++var9) {
                final int var10 = 2 + var9 * 5;
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, var10, 0, 1, var10, Blocks.¥É.¥à(), Blocks.Â.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 0, var10, 2, 1, var10, Blocks.¥É.¥à(), Blocks.Â.¥à(), false);
                if (p_74875_2_.nextInt(4) == 0) {
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 2, var10, 0, 2, var10, Blocks.à.¥à(), Blocks.Â.¥à(), false);
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 2, var10, 2, 2, var10, Blocks.à.¥à(), Blocks.Â.¥à(), false);
                }
                else {
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 2, var10, 2, 2, var10, Blocks.à.¥à(), Blocks.Â.¥à(), false);
                }
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.1f, 0, 2, var10 - 1, Blocks.É.¥à());
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.1f, 2, 2, var10 - 1, Blocks.É.¥à());
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.1f, 0, 2, var10 + 1, Blocks.É.¥à());
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.1f, 2, 2, var10 + 1, Blocks.É.¥à());
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.05f, 0, 2, var10 - 2, Blocks.É.¥à());
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.05f, 2, 2, var10 - 2, Blocks.É.¥à());
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.05f, 0, 2, var10 + 2, Blocks.É.¥à());
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.05f, 2, 2, var10 + 2, Blocks.É.¥à());
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.05f, 1, 2, var10 - 1, Blocks.Ï.Ý(EnumFacing.Â.Â()));
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.05f, 1, 2, var10 + 1, Blocks.Ï.Ý(EnumFacing.Â.Â()));
                if (p_74875_2_.nextInt(100) == 0) {
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 2, 0, var10 - 1, WeightedRandomChestContent.HorizonCode_Horizon_È(StructureMineshaftPieces.HorizonCode_Horizon_È, Items.Çªáˆºá.HorizonCode_Horizon_È(p_74875_2_)), 3 + p_74875_2_.nextInt(4));
                }
                if (p_74875_2_.nextInt(100) == 0) {
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0, 0, var10 + 1, WeightedRandomChestContent.HorizonCode_Horizon_È(StructureMineshaftPieces.HorizonCode_Horizon_È, Items.Çªáˆºá.HorizonCode_Horizon_È(p_74875_2_)), 3 + p_74875_2_.nextInt(4));
                }
                if (this.Â && !this.Ý) {
                    final int var11 = this.HorizonCode_Horizon_È(0);
                    int var12 = var10 - 1 + p_74875_2_.nextInt(3);
                    final int var13 = this.HorizonCode_Horizon_È(1, var12);
                    var12 = this.Â(1, var12);
                    final BlockPos var14 = new BlockPos(var13, var11, var12);
                    if (p_74875_3_.HorizonCode_Horizon_È(var14)) {
                        this.Ý = true;
                        worldIn.HorizonCode_Horizon_È(var14, Blocks.ÇªÓ.¥à(), 2);
                        final TileEntity var15 = worldIn.HorizonCode_Horizon_È(var14);
                        if (var15 instanceof TileEntityMobSpawner) {
                            ((TileEntityMobSpawner)var15).Â().HorizonCode_Horizon_È("CaveSpider");
                        }
                    }
                }
            }
            for (int var9 = 0; var9 <= 2; ++var9) {
                for (int var10 = 0; var10 <= var8; ++var10) {
                    final byte var16 = -1;
                    final IBlockState var17 = this.HorizonCode_Horizon_È(worldIn, var9, var16, var10, p_74875_3_);
                    if (var17.Ý().Ó() == Material.HorizonCode_Horizon_È) {
                        final byte var18 = -1;
                        this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), var9, var18, var10, p_74875_3_);
                    }
                }
            }
            if (this.HorizonCode_Horizon_È) {
                for (int var9 = 0; var9 <= var8; ++var9) {
                    final IBlockState var19 = this.HorizonCode_Horizon_È(worldIn, 1, -1, var9, p_74875_3_);
                    if (var19.Ý().Ó() != Material.HorizonCode_Horizon_È && var19.Ý().HorizonCode_Horizon_È()) {
                        this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.7f, 1, 0, var9, Blocks.áŒŠáŠ.Ý(this.HorizonCode_Horizon_È(Blocks.áŒŠáŠ, 0)));
                    }
                }
            }
            return true;
        }
    }
    
    public static class Â extends StructureComponent
    {
        private EnumFacing HorizonCode_Horizon_È;
        private boolean Â;
        private static final String Ý = "CL_00000446";
        
        public Â() {
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            p_143012_1_.HorizonCode_Horizon_È("tf", this.Â);
            p_143012_1_.HorizonCode_Horizon_È("D", this.HorizonCode_Horizon_È.Ý());
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            this.Â = p_143011_1_.£á("tf");
            this.HorizonCode_Horizon_È = EnumFacing.Â(p_143011_1_.Ó("D"));
        }
        
        public Â(final int p_i45624_1_, final Random p_i45624_2_, final StructureBoundingBox p_i45624_3_, final EnumFacing p_i45624_4_) {
            super(p_i45624_1_);
            this.HorizonCode_Horizon_È = p_i45624_4_;
            this.Âµá€ = p_i45624_3_;
            this.Â = (p_i45624_3_.Ø­áŒŠá() > 3);
        }
        
        public static StructureBoundingBox HorizonCode_Horizon_È(final List p_175813_0_, final Random p_175813_1_, final int p_175813_2_, final int p_175813_3_, final int p_175813_4_, final EnumFacing p_175813_5_) {
            final StructureBoundingBox var6 = new StructureBoundingBox(p_175813_2_, p_175813_3_, p_175813_4_, p_175813_2_, p_175813_3_ + 2, p_175813_4_);
            if (p_175813_1_.nextInt(4) == 0) {
                final StructureBoundingBox structureBoundingBox = var6;
                structureBoundingBox.Âµá€ += 4;
            }
            switch (Âµá€.HorizonCode_Horizon_È[p_175813_5_.ordinal()]) {
                case 1: {
                    var6.HorizonCode_Horizon_È = p_175813_2_ - 1;
                    var6.Ø­áŒŠá = p_175813_2_ + 3;
                    var6.Ý = p_175813_4_ - 4;
                    break;
                }
                case 2: {
                    var6.HorizonCode_Horizon_È = p_175813_2_ - 1;
                    var6.Ø­áŒŠá = p_175813_2_ + 3;
                    var6.Ó = p_175813_4_ + 4;
                    break;
                }
                case 3: {
                    var6.HorizonCode_Horizon_È = p_175813_2_ - 4;
                    var6.Ý = p_175813_4_ - 1;
                    var6.Ó = p_175813_4_ + 3;
                    break;
                }
                case 4: {
                    var6.Ø­áŒŠá = p_175813_2_ + 4;
                    var6.Ý = p_175813_4_ - 1;
                    var6.Ó = p_175813_4_ + 3;
                    break;
                }
            }
            return (StructureComponent.HorizonCode_Horizon_È(p_175813_0_, var6) != null) ? null : var6;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            final int var4 = this.Ý();
            switch (StructureMineshaftPieces.Âµá€.HorizonCode_Horizon_È[this.HorizonCode_Horizon_È.ordinal()]) {
                case 1: {
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â, this.Âµá€.Ý - 1, EnumFacing.Ý, var4);
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â, this.Âµá€.Ý + 1, EnumFacing.Âµá€, var4);
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â, this.Âµá€.Ý + 1, EnumFacing.Ó, var4);
                    break;
                }
                case 2: {
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, var4);
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â, this.Âµá€.Ý + 1, EnumFacing.Âµá€, var4);
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â, this.Âµá€.Ý + 1, EnumFacing.Ó, var4);
                    break;
                }
                case 3: {
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â, this.Âµá€.Ý - 1, EnumFacing.Ý, var4);
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, var4);
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â, this.Âµá€.Ý + 1, EnumFacing.Âµá€, var4);
                    break;
                }
                case 4: {
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â, this.Âµá€.Ý - 1, EnumFacing.Ý, var4);
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, var4);
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â, this.Âµá€.Ý + 1, EnumFacing.Ó, var4);
                    break;
                }
            }
            if (this.Â) {
                if (p_74861_3_.nextBoolean()) {
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â + 3 + 1, this.Âµá€.Ý - 1, EnumFacing.Ý, var4);
                }
                if (p_74861_3_.nextBoolean()) {
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â + 3 + 1, this.Âµá€.Ý + 1, EnumFacing.Âµá€, var4);
                }
                if (p_74861_3_.nextBoolean()) {
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â + 3 + 1, this.Âµá€.Ý + 1, EnumFacing.Ó, var4);
                }
                if (p_74861_3_.nextBoolean()) {
                    Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â + 3 + 1, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, var4);
                }
            }
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            if (this.Â) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â, this.Âµá€.Ý, this.Âµá€.Ø­áŒŠá - 1, this.Âµá€.Â + 3 - 1, this.Âµá€.Ó, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â, this.Âµá€.Ý + 1, this.Âµá€.Ø­áŒŠá, this.Âµá€.Â + 3 - 1, this.Âµá€.Ó - 1, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Âµá€ - 2, this.Âµá€.Ý, this.Âµá€.Ø­áŒŠá - 1, this.Âµá€.Âµá€, this.Âµá€.Ó, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Âµá€ - 2, this.Âµá€.Ý + 1, this.Âµá€.Ø­áŒŠá, this.Âµá€.Âµá€, this.Âµá€.Ó - 1, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â + 3, this.Âµá€.Ý + 1, this.Âµá€.Ø­áŒŠá - 1, this.Âµá€.Â + 3, this.Âµá€.Ó - 1, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            else {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â, this.Âµá€.Ý, this.Âµá€.Ø­áŒŠá - 1, this.Âµá€.Âµá€, this.Âµá€.Ó, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â, this.Âµá€.Ý + 1, this.Âµá€.Ø­áŒŠá, this.Âµá€.Âµá€, this.Âµá€.Ó - 1, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â, this.Âµá€.Ý + 1, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Âµá€, this.Âµá€.Ý + 1, Blocks.à.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Â, this.Âµá€.Ó - 1, this.Âµá€.HorizonCode_Horizon_È + 1, this.Âµá€.Âµá€, this.Âµá€.Ó - 1, Blocks.à.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.Ø­áŒŠá - 1, this.Âµá€.Â, this.Âµá€.Ý + 1, this.Âµá€.Ø­áŒŠá - 1, this.Âµá€.Âµá€, this.Âµá€.Ý + 1, Blocks.à.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.Ø­áŒŠá - 1, this.Âµá€.Â, this.Âµá€.Ó - 1, this.Âµá€.Ø­áŒŠá - 1, this.Âµá€.Âµá€, this.Âµá€.Ó - 1, Blocks.à.¥à(), Blocks.Â.¥à(), false);
            for (int var4 = this.Âµá€.HorizonCode_Horizon_È; var4 <= this.Âµá€.Ø­áŒŠá; ++var4) {
                for (int var5 = this.Âµá€.Ý; var5 <= this.Âµá€.Ó; ++var5) {
                    if (this.HorizonCode_Horizon_È(worldIn, var4, this.Âµá€.Â - 1, var5, p_74875_3_).Ý().Ó() == Material.HorizonCode_Horizon_È) {
                        this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), var4, this.Âµá€.Â - 1, var5, p_74875_3_);
                    }
                }
            }
            return true;
        }
    }
    
    public static class Ý extends StructureComponent
    {
        private List HorizonCode_Horizon_È;
        private static final String Â = "CL_00000447";
        
        public Ý() {
            this.HorizonCode_Horizon_È = Lists.newLinkedList();
        }
        
        public Ý(final int p_i2037_1_, final Random p_i2037_2_, final int p_i2037_3_, final int p_i2037_4_) {
            super(p_i2037_1_);
            this.HorizonCode_Horizon_È = Lists.newLinkedList();
            this.Âµá€ = new StructureBoundingBox(p_i2037_3_, 50, p_i2037_4_, p_i2037_3_ + 7 + p_i2037_2_.nextInt(6), 54 + p_i2037_2_.nextInt(6), p_i2037_4_ + 7 + p_i2037_2_.nextInt(6));
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            final int var4 = this.Ý();
            int var5 = this.Âµá€.Ø­áŒŠá() - 3 - 1;
            if (var5 <= 0) {
                var5 = 1;
            }
            for (int var6 = 0; var6 < this.Âµá€.Ý(); var6 += 4) {
                var6 += p_74861_3_.nextInt(this.Âµá€.Ý());
                if (var6 + 3 > this.Âµá€.Ý()) {
                    break;
                }
                final StructureComponent var7 = Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + var6, this.Âµá€.Â + p_74861_3_.nextInt(var5) + 1, this.Âµá€.Ý - 1, EnumFacing.Ý, var4);
                if (var7 != null) {
                    final StructureBoundingBox var8 = var7.Â();
                    this.HorizonCode_Horizon_È.add(new StructureBoundingBox(var8.HorizonCode_Horizon_È, var8.Â, this.Âµá€.Ý, var8.Ø­áŒŠá, var8.Âµá€, this.Âµá€.Ý + 1));
                }
            }
            for (int var6 = 0; var6 < this.Âµá€.Ý(); var6 += 4) {
                var6 += p_74861_3_.nextInt(this.Âµá€.Ý());
                if (var6 + 3 > this.Âµá€.Ý()) {
                    break;
                }
                final StructureComponent var7 = Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È + var6, this.Âµá€.Â + p_74861_3_.nextInt(var5) + 1, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, var4);
                if (var7 != null) {
                    final StructureBoundingBox var8 = var7.Â();
                    this.HorizonCode_Horizon_È.add(new StructureBoundingBox(var8.HorizonCode_Horizon_È, var8.Â, this.Âµá€.Ó - 1, var8.Ø­áŒŠá, var8.Âµá€, this.Âµá€.Ó));
                }
            }
            for (int var6 = 0; var6 < this.Âµá€.Âµá€(); var6 += 4) {
                var6 += p_74861_3_.nextInt(this.Âµá€.Âµá€());
                if (var6 + 3 > this.Âµá€.Âµá€()) {
                    break;
                }
                final StructureComponent var7 = Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â + p_74861_3_.nextInt(var5) + 1, this.Âµá€.Ý + var6, EnumFacing.Âµá€, var4);
                if (var7 != null) {
                    final StructureBoundingBox var8 = var7.Â();
                    this.HorizonCode_Horizon_È.add(new StructureBoundingBox(this.Âµá€.HorizonCode_Horizon_È, var8.Â, var8.Ý, this.Âµá€.HorizonCode_Horizon_È + 1, var8.Âµá€, var8.Ó));
                }
            }
            for (int var6 = 0; var6 < this.Âµá€.Âµá€(); var6 += 4) {
                var6 += p_74861_3_.nextInt(this.Âµá€.Âµá€());
                if (var6 + 3 > this.Âµá€.Âµá€()) {
                    break;
                }
                final StructureComponent var7 = Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â + p_74861_3_.nextInt(var5) + 1, this.Âµá€.Ý + var6, EnumFacing.Ó, var4);
                if (var7 != null) {
                    final StructureBoundingBox var8 = var7.Â();
                    this.HorizonCode_Horizon_È.add(new StructureBoundingBox(this.Âµá€.Ø­áŒŠá - 1, var8.Â, var8.Ý, this.Âµá€.Ø­áŒŠá, var8.Âµá€, var8.Ó));
                }
            }
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â, this.Âµá€.Ý, this.Âµá€.Ø­áŒŠá, this.Âµá€.Â, this.Âµá€.Ó, Blocks.Âµá€.¥à(), Blocks.Â.¥à(), true);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â + 1, this.Âµá€.Ý, this.Âµá€.Ø­áŒŠá, Math.min(this.Âµá€.Â + 3, this.Âµá€.Âµá€), this.Âµá€.Ó, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            for (final StructureBoundingBox var5 : this.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, var5.HorizonCode_Horizon_È, var5.Âµá€ - 2, var5.Ý, var5.Ø­áŒŠá, var5.Âµá€, var5.Ó, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â + 4, this.Âµá€.Ý, this.Âµá€.Ø­áŒŠá, this.Âµá€.Âµá€, this.Âµá€.Ó, Blocks.Â.¥à(), false);
            return true;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            final NBTTagList var2 = new NBTTagList();
            for (final StructureBoundingBox var4 : this.HorizonCode_Horizon_È) {
                var2.HorizonCode_Horizon_È(var4.à());
            }
            p_143012_1_.HorizonCode_Horizon_È("Entrances", var2);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            final NBTTagList var2 = p_143011_1_.Ý("Entrances", 11);
            for (int var3 = 0; var3 < var2.Âµá€(); ++var3) {
                this.HorizonCode_Horizon_È.add(new StructureBoundingBox(var2.Ý(var3)));
            }
        }
    }
    
    public static class Ø­áŒŠá extends StructureComponent
    {
        private static final String HorizonCode_Horizon_È = "CL_00000449";
        
        public Ø­áŒŠá() {
        }
        
        public Ø­áŒŠá(final int p_i45623_1_, final Random p_i45623_2_, final StructureBoundingBox p_i45623_3_, final EnumFacing p_i45623_4_) {
            super(p_i45623_1_);
            this.Ó = p_i45623_4_;
            this.Âµá€ = p_i45623_3_;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
        }
        
        public static StructureBoundingBox HorizonCode_Horizon_È(final List p_175812_0_, final Random p_175812_1_, final int p_175812_2_, final int p_175812_3_, final int p_175812_4_, final EnumFacing p_175812_5_) {
            final StructureBoundingBox var6 = new StructureBoundingBox(p_175812_2_, p_175812_3_ - 5, p_175812_4_, p_175812_2_, p_175812_3_ + 2, p_175812_4_);
            switch (Âµá€.HorizonCode_Horizon_È[p_175812_5_.ordinal()]) {
                case 1: {
                    var6.Ø­áŒŠá = p_175812_2_ + 2;
                    var6.Ý = p_175812_4_ - 8;
                    break;
                }
                case 2: {
                    var6.Ø­áŒŠá = p_175812_2_ + 2;
                    var6.Ó = p_175812_4_ + 8;
                    break;
                }
                case 3: {
                    var6.HorizonCode_Horizon_È = p_175812_2_ - 8;
                    var6.Ó = p_175812_4_ + 2;
                    break;
                }
                case 4: {
                    var6.Ø­áŒŠá = p_175812_2_ + 8;
                    var6.Ó = p_175812_4_ + 2;
                    break;
                }
            }
            return (StructureComponent.HorizonCode_Horizon_È(p_175812_0_, var6) != null) ? null : var6;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            final int var4 = this.Ý();
            if (this.Ó != null) {
                switch (StructureMineshaftPieces.Âµá€.HorizonCode_Horizon_È[this.Ó.ordinal()]) {
                    case 1: {
                        Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â, this.Âµá€.Ý - 1, EnumFacing.Ý, var4);
                        break;
                    }
                    case 2: {
                        Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È, this.Âµá€.Â, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, var4);
                        break;
                    }
                    case 3: {
                        Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â, this.Âµá€.Ý, EnumFacing.Âµá€, var4);
                        break;
                    }
                    case 4: {
                        Â(p_74861_1_, p_74861_2_, p_74861_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â, this.Âµá€.Ý, EnumFacing.Ó, var4);
                        break;
                    }
                }
            }
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 5, 0, 2, 7, 1, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 7, 2, 2, 8, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            for (int var4 = 0; var4 < 5; ++var4) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 5 - var4 - ((var4 < 4) ? 1 : 0), 2 + var4, 2, 7 - var4, 2 + var4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            return true;
        }
    }
    
    static final class Âµá€
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00001998";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                Âµá€.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                Âµá€.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                Âµá€.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                Âµá€.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
