package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;
import com.google.common.collect.Lists;
import java.util.List;

public class StructureStrongholdPieces
{
    private static final Ó[] Â;
    private static List Ý;
    private static Class Ø­áŒŠá;
    static int HorizonCode_Horizon_È;
    private static final £á Âµá€;
    private static final String Ó = "CL_00000483";
    
    static {
        Â = new Ó[] { new Ó(Å.class, 40, 0), new Ó(Ø.class, 5, 5), new Ó(Ø­áŒŠá.class, 20, 0), new Ó(áŒŠÆ.class, 20, 0), new Ó(áˆºÑ¢Õ.class, 10, 6), new Ó(ˆÏ­.class, 5, 5), new Ó(ÂµÈ.class, 5, 5), new Ó(Ý.class, 5, 4), new Ó(HorizonCode_Horizon_È.class, 5, 4), new Ó(10, 2) {
                private static final String Âµá€ = "CL_00000484";
                
                @Override
                public boolean HorizonCode_Horizon_È(final int p_75189_1_) {
                    return super.HorizonCode_Horizon_È(p_75189_1_) && p_75189_1_ > 4;
                }
            }, new Ó(20, 1) {
                private static final String Âµá€ = "CL_00000485";
                
                @Override
                public boolean HorizonCode_Horizon_È(final int p_75189_1_) {
                    return super.HorizonCode_Horizon_È(p_75189_1_) && p_75189_1_ > 5;
                }
            } };
        Âµá€ = new £á(null);
    }
    
    public static void HorizonCode_Horizon_È() {
        MapGenStructureIO.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class, "SHCC");
        MapGenStructureIO.HorizonCode_Horizon_È(Â.class, "SHFC");
        MapGenStructureIO.HorizonCode_Horizon_È(Ý.class, "SH5C");
        MapGenStructureIO.HorizonCode_Horizon_È(Ø­áŒŠá.class, "SHLT");
        MapGenStructureIO.HorizonCode_Horizon_È(Âµá€.class, "SHLi");
        MapGenStructureIO.HorizonCode_Horizon_È(à.class, "SHPR");
        MapGenStructureIO.HorizonCode_Horizon_È(Ø.class, "SHPH");
        MapGenStructureIO.HorizonCode_Horizon_È(áŒŠÆ.class, "SHRT");
        MapGenStructureIO.HorizonCode_Horizon_È(áˆºÑ¢Õ.class, "SHRC");
        MapGenStructureIO.HorizonCode_Horizon_È(ÂµÈ.class, "SHSD");
        MapGenStructureIO.HorizonCode_Horizon_È(á.class, "SHStart");
        MapGenStructureIO.HorizonCode_Horizon_È(Å.class, "SHS");
        MapGenStructureIO.HorizonCode_Horizon_È(ˆÏ­.class, "SHSSD");
    }
    
    public static void Â() {
        StructureStrongholdPieces.Ý = Lists.newArrayList();
        for (final Ó var4 : StructureStrongholdPieces.Â) {
            var4.Ý = 0;
            StructureStrongholdPieces.Ý.add(var4);
        }
        StructureStrongholdPieces.Ø­áŒŠá = null;
    }
    
    private static boolean Âµá€() {
        boolean var0 = false;
        StructureStrongholdPieces.HorizonCode_Horizon_È = 0;
        for (final Ó var3 : StructureStrongholdPieces.Ý) {
            if (var3.Ø­áŒŠá > 0 && var3.Ý < var3.Ø­áŒŠá) {
                var0 = true;
            }
            StructureStrongholdPieces.HorizonCode_Horizon_È += var3.Â;
        }
        return var0;
    }
    
    private static £à HorizonCode_Horizon_È(final Class p_175954_0_, final List p_175954_1_, final Random p_175954_2_, final int p_175954_3_, final int p_175954_4_, final int p_175954_5_, final EnumFacing p_175954_6_, final int p_175954_7_) {
        Object var8 = null;
        if (p_175954_0_ == Å.class) {
            var8 = Å.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (p_175954_0_ == Ø.class) {
            var8 = Ø.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (p_175954_0_ == Ø­áŒŠá.class) {
            var8 = StructureStrongholdPieces.Ø­áŒŠá.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (p_175954_0_ == áŒŠÆ.class) {
            var8 = StructureStrongholdPieces.Ø­áŒŠá.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (p_175954_0_ == áˆºÑ¢Õ.class) {
            var8 = áˆºÑ¢Õ.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (p_175954_0_ == ˆÏ­.class) {
            var8 = ˆÏ­.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (p_175954_0_ == ÂµÈ.class) {
            var8 = ÂµÈ.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (p_175954_0_ == Ý.class) {
            var8 = StructureStrongholdPieces.Ý.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (p_175954_0_ == HorizonCode_Horizon_È.class) {
            var8 = StructureStrongholdPieces.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (p_175954_0_ == Âµá€.class) {
            var8 = StructureStrongholdPieces.Âµá€.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        else if (p_175954_0_ == à.class) {
            var8 = à.HorizonCode_Horizon_È(p_175954_1_, p_175954_2_, p_175954_3_, p_175954_4_, p_175954_5_, p_175954_6_, p_175954_7_);
        }
        return (£à)var8;
    }
    
    private static £à Â(final á p_175955_0_, final List p_175955_1_, final Random p_175955_2_, final int p_175955_3_, final int p_175955_4_, final int p_175955_5_, final EnumFacing p_175955_6_, final int p_175955_7_) {
        if (!Âµá€()) {
            return null;
        }
        if (StructureStrongholdPieces.Ø­áŒŠá != null) {
            final £à var8 = HorizonCode_Horizon_È(StructureStrongholdPieces.Ø­áŒŠá, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
            StructureStrongholdPieces.Ø­áŒŠá = null;
            if (var8 != null) {
                return var8;
            }
        }
        int var9 = 0;
        while (var9 < 5) {
            ++var9;
            int var10 = p_175955_2_.nextInt(StructureStrongholdPieces.HorizonCode_Horizon_È);
            for (final Ó var12 : StructureStrongholdPieces.Ý) {
                var10 -= var12.Â;
                if (var10 < 0) {
                    if (!var12.HorizonCode_Horizon_È(p_175955_7_)) {
                        break;
                    }
                    if (var12 == p_175955_0_.HorizonCode_Horizon_È) {
                        break;
                    }
                    final £à var13 = HorizonCode_Horizon_È(var12.HorizonCode_Horizon_È, p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_, p_175955_7_);
                    if (var13 != null) {
                        final Ó ó = var12;
                        ++ó.Ý;
                        p_175955_0_.HorizonCode_Horizon_È = var12;
                        if (!var12.HorizonCode_Horizon_È()) {
                            StructureStrongholdPieces.Ý.remove(var12);
                        }
                        return var13;
                    }
                    continue;
                }
            }
        }
        final StructureBoundingBox var14 = StructureStrongholdPieces.Â.HorizonCode_Horizon_È(p_175955_1_, p_175955_2_, p_175955_3_, p_175955_4_, p_175955_5_, p_175955_6_);
        if (var14 != null && var14.Â > 1) {
            return new Â(p_175955_7_, p_175955_2_, var14, p_175955_6_);
        }
        return null;
    }
    
    private static StructureComponent Ý(final á p_175953_0_, final List p_175953_1_, final Random p_175953_2_, final int p_175953_3_, final int p_175953_4_, final int p_175953_5_, final EnumFacing p_175953_6_, final int p_175953_7_) {
        if (p_175953_7_ > 50) {
            return null;
        }
        if (Math.abs(p_175953_3_ - p_175953_0_.Â().HorizonCode_Horizon_È) <= 112 && Math.abs(p_175953_5_ - p_175953_0_.Â().Ý) <= 112) {
            final £à var8 = Â(p_175953_0_, p_175953_1_, p_175953_2_, p_175953_3_, p_175953_4_, p_175953_5_, p_175953_6_, p_175953_7_ + 1);
            if (var8 != null) {
                p_175953_1_.add(var8);
                p_175953_0_.Ý.add(var8);
            }
            return var8;
        }
        return null;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final Class ø­áŒŠá) {
        StructureStrongholdPieces.Ø­áŒŠá = ø­áŒŠá;
    }
    
    public static class HorizonCode_Horizon_È extends £à
    {
        private static final List HorizonCode_Horizon_È;
        private boolean Â;
        private static final String Ý = "CL_00000487";
        
        static {
            HorizonCode_Horizon_È = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.ˆÐƒØ, 0, 1, 1, 10), new WeightedRandomChestContent(Items.áŒŠÆ, 0, 1, 3, 3), new WeightedRandomChestContent(Items.áˆºÑ¢Õ, 0, 1, 5, 10), new WeightedRandomChestContent(Items.ÂµÈ, 0, 1, 3, 5), new WeightedRandomChestContent(Items.ÇŽá, 0, 4, 9, 5), new WeightedRandomChestContent(Items.Ç, 0, 1, 3, 15), new WeightedRandomChestContent(Items.Âµá€, 0, 1, 3, 15), new WeightedRandomChestContent(Items.Â, 0, 1, 1, 5), new WeightedRandomChestContent(Items.á, 0, 1, 1, 5), new WeightedRandomChestContent(Items.áˆºáˆºÈ, 0, 1, 1, 5), new WeightedRandomChestContent(Items.Ï­à, 0, 1, 1, 5), new WeightedRandomChestContent(Items.ÇŽá€, 0, 1, 1, 5), new WeightedRandomChestContent(Items.Ï, 0, 1, 1, 5), new WeightedRandomChestContent(Items.£Õ, 0, 1, 1, 1), new WeightedRandomChestContent(Items.Û, 0, 1, 1, 1), new WeightedRandomChestContent(Items.ÐƒÇŽà, 0, 1, 1, 1), new WeightedRandomChestContent(Items.¥Ê, 0, 1, 1, 1), new WeightedRandomChestContent(Items.ÐƒÓ, 0, 1, 1, 1) });
        }
        
        public HorizonCode_Horizon_È() {
        }
        
        public HorizonCode_Horizon_È(final int p_i45582_1_, final Random p_i45582_2_, final StructureBoundingBox p_i45582_3_, final EnumFacing p_i45582_4_) {
            super(p_i45582_1_);
            this.Ó = p_i45582_4_;
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È(p_i45582_2_);
            this.Âµá€ = p_i45582_3_;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Chest", this.Â);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.Â = p_143011_1_.£á("Chest");
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            this.HorizonCode_Horizon_È((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final List p_175868_0_, final Random p_175868_1_, final int p_175868_2_, final int p_175868_3_, final int p_175868_4_, final EnumFacing p_175868_5_, final int p_175868_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175868_2_, p_175868_3_, p_175868_4_, -1, -1, 0, 5, 5, 7, p_175868_5_);
            return (£à.HorizonCode_Horizon_È(var7) && StructureComponent.HorizonCode_Horizon_È(p_175868_0_, var7) == null) ? new HorizonCode_Horizon_È(p_175868_6_, p_175868_1_, var7, p_175868_5_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, this.Ø­áŒŠá, 1, 1, 0);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, £à.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 1, 1, 6);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, 2, 3, 1, 4, Blocks.£áƒ.¥à(), Blocks.£áƒ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.Ó.Â()), 3, 1, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.Ó.Â()), 3, 1, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.Ó.Â()), 3, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.Ó.Â()), 3, 2, 4, p_74875_3_);
            for (int var4 = 2; var4 <= 4; ++var4) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.Ó.Â()), 2, 1, var4, p_74875_3_);
            }
            if (!this.Â && p_74875_3_.HorizonCode_Horizon_È(new BlockPos(this.HorizonCode_Horizon_È(3, 3), this.HorizonCode_Horizon_È(2), this.Â(3, 3)))) {
                this.Â = true;
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 3, 2, 3, WeightedRandomChestContent.HorizonCode_Horizon_È(StructureStrongholdPieces.HorizonCode_Horizon_È.HorizonCode_Horizon_È, Items.Çªáˆºá.HorizonCode_Horizon_È(p_74875_2_)), 2 + p_74875_2_.nextInt(2));
            }
            return true;
        }
    }
    
    public static class Â extends £à
    {
        private int HorizonCode_Horizon_È;
        private static final String Â = "CL_00000488";
        
        public Â() {
        }
        
        public Â(final int p_i45581_1_, final Random p_i45581_2_, final StructureBoundingBox p_i45581_3_, final EnumFacing p_i45581_4_) {
            super(p_i45581_1_);
            this.Ó = p_i45581_4_;
            this.Âµá€ = p_i45581_3_;
            this.HorizonCode_Horizon_È = ((p_i45581_4_ != EnumFacing.Ý && p_i45581_4_ != EnumFacing.Ø­áŒŠá) ? p_i45581_3_.Ý() : p_i45581_3_.Âµá€());
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Steps", this.HorizonCode_Horizon_È);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.HorizonCode_Horizon_È = p_143011_1_.Ó("Steps");
        }
        
        public static StructureBoundingBox HorizonCode_Horizon_È(final List p_175869_0_, final Random p_175869_1_, final int p_175869_2_, final int p_175869_3_, final int p_175869_4_, final EnumFacing p_175869_5_) {
            final boolean var6 = true;
            StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, 4, p_175869_5_);
            final StructureComponent var8 = StructureComponent.HorizonCode_Horizon_È(p_175869_0_, var7);
            if (var8 == null) {
                return null;
            }
            if (var8.Â().Â == var7.Â) {
                for (int var9 = 3; var9 >= 1; --var9) {
                    var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, var9 - 1, p_175869_5_);
                    if (!var8.Â().HorizonCode_Horizon_È(var7)) {
                        return StructureBoundingBox.HorizonCode_Horizon_È(p_175869_2_, p_175869_3_, p_175869_4_, -1, -1, 0, 5, 5, var9, p_175869_5_);
                    }
                }
            }
            return null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            for (int var4 = 0; var4 < this.HorizonCode_Horizon_È; ++var4) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 0, 0, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 1, 0, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 2, 0, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 3, 0, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 4, 0, var4, p_74875_3_);
                for (int var5 = 1; var5 <= 3; ++var5) {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 0, var5, var4, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 1, var5, var4, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 2, var5, var4, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 3, var5, var4, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 4, var5, var4, p_74875_3_);
                }
                this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 0, 4, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 1, 4, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 2, 4, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 3, 4, var4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 4, 4, var4, p_74875_3_);
            }
            return true;
        }
    }
    
    public static class Ý extends £à
    {
        private boolean HorizonCode_Horizon_È;
        private boolean Â;
        private boolean Ý;
        private boolean Ø;
        private static final String áŒŠÆ = "CL_00000489";
        
        public Ý() {
        }
        
        public Ý(final int p_i45580_1_, final Random p_i45580_2_, final StructureBoundingBox p_i45580_3_, final EnumFacing p_i45580_4_) {
            super(p_i45580_1_);
            this.Ó = p_i45580_4_;
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È(p_i45580_2_);
            this.Âµá€ = p_i45580_3_;
            this.HorizonCode_Horizon_È = p_i45580_2_.nextBoolean();
            this.Â = p_i45580_2_.nextBoolean();
            this.Ý = p_i45580_2_.nextBoolean();
            this.Ø = (p_i45580_2_.nextInt(3) > 0);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("leftLow", this.HorizonCode_Horizon_È);
            p_143012_1_.HorizonCode_Horizon_È("leftHigh", this.Â);
            p_143012_1_.HorizonCode_Horizon_È("rightLow", this.Ý);
            p_143012_1_.HorizonCode_Horizon_È("rightHigh", this.Ø);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.HorizonCode_Horizon_È = p_143011_1_.£á("leftLow");
            this.Â = p_143011_1_.£á("leftHigh");
            this.Ý = p_143011_1_.£á("rightLow");
            this.Ø = p_143011_1_.£á("rightHigh");
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            int var4 = 3;
            int var5 = 5;
            if (this.Ó == EnumFacing.Âµá€ || this.Ó == EnumFacing.Ý) {
                var4 = 8 - var4;
                var5 = 8 - var5;
            }
            this.HorizonCode_Horizon_È((á)p_74861_1_, p_74861_2_, p_74861_3_, 5, 1);
            if (this.HorizonCode_Horizon_È) {
                this.Â((á)p_74861_1_, p_74861_2_, p_74861_3_, var4, 1);
            }
            if (this.Â) {
                this.Â((á)p_74861_1_, p_74861_2_, p_74861_3_, var5, 7);
            }
            if (this.Ý) {
                this.Ý((á)p_74861_1_, p_74861_2_, p_74861_3_, var4, 1);
            }
            if (this.Ø) {
                this.Ý((á)p_74861_1_, p_74861_2_, p_74861_3_, var5, 7);
            }
        }
        
        public static Ý HorizonCode_Horizon_È(final List p_175866_0_, final Random p_175866_1_, final int p_175866_2_, final int p_175866_3_, final int p_175866_4_, final EnumFacing p_175866_5_, final int p_175866_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175866_2_, p_175866_3_, p_175866_4_, -4, -3, 0, 10, 9, 11, p_175866_5_);
            return (£à.HorizonCode_Horizon_È(var7) && StructureComponent.HorizonCode_Horizon_È(p_175866_0_, var7) == null) ? new Ý(p_175866_6_, p_175866_1_, var7, p_175866_5_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 9, 8, 10, true, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, this.Ø­áŒŠá, 4, 3, 0);
            if (this.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 3, 1, 0, 5, 3, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            if (this.Ý) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 3, 1, 9, 5, 3, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            if (this.Â) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 5, 7, 0, 7, 9, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            if (this.Ø) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 5, 7, 9, 7, 9, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 1, 10, 7, 3, 10, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 2, 1, 8, 2, 6, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 5, 4, 4, 9, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 1, 5, 8, 4, 9, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 4, 7, 3, 4, 9, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 3, 5, 3, 3, 6, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 3, 4, 3, 3, 4, Blocks.Ø­Âµ.¥à(), Blocks.Ø­Âµ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 4, 6, 3, 4, 6, Blocks.Ø­Âµ.¥à(), Blocks.Ø­Âµ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 1, 7, 7, 1, 8, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 1, 9, 7, 1, 9, Blocks.Ø­Âµ.¥à(), Blocks.Ø­Âµ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 2, 7, 7, 2, 7, Blocks.Ø­Âµ.¥à(), Blocks.Ø­Âµ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 5, 7, 4, 5, 9, Blocks.Ø­Âµ.¥à(), Blocks.Ø­Âµ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 5, 7, 8, 5, 9, Blocks.Ø­Âµ.¥à(), Blocks.Ø­Âµ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 5, 7, 7, 5, 9, Blocks.£ÂµÄ.¥à(), Blocks.£ÂµÄ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), 6, 5, 6, p_74875_3_);
            return true;
        }
    }
    
    public static class Ø­áŒŠá extends £à
    {
        private static final String HorizonCode_Horizon_È = "CL_00000490";
        
        public Ø­áŒŠá() {
        }
        
        public Ø­áŒŠá(final int p_i45579_1_, final Random p_i45579_2_, final StructureBoundingBox p_i45579_3_, final EnumFacing p_i45579_4_) {
            super(p_i45579_1_);
            this.Ó = p_i45579_4_;
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È(p_i45579_2_);
            this.Âµá€ = p_i45579_3_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            if (this.Ó != EnumFacing.Ý && this.Ó != EnumFacing.Ó) {
                this.Ý((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            }
            else {
                this.Â((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            }
        }
        
        public static Ø­áŒŠá HorizonCode_Horizon_È(final List p_175867_0_, final Random p_175867_1_, final int p_175867_2_, final int p_175867_3_, final int p_175867_4_, final EnumFacing p_175867_5_, final int p_175867_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175867_2_, p_175867_3_, p_175867_4_, -1, -1, 0, 5, 5, 5, p_175867_5_);
            return (£à.HorizonCode_Horizon_È(var7) && StructureComponent.HorizonCode_Horizon_È(p_175867_0_, var7) == null) ? new Ø­áŒŠá(p_175867_6_, p_175867_1_, var7, p_175867_5_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 4, true, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, this.Ø­áŒŠá, 1, 1, 0);
            if (this.Ó != EnumFacing.Ý && this.Ó != EnumFacing.Ó) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            else {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            return true;
        }
    }
    
    public static class Âµá€ extends £à
    {
        private static final List HorizonCode_Horizon_È;
        private boolean Â;
        private static final String Ý = "CL_00000491";
        
        static {
            HorizonCode_Horizon_È = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.Ñ¢Ç, 0, 1, 3, 20), new WeightedRandomChestContent(Items.ˆà¢, 0, 2, 7, 20), new WeightedRandomChestContent(Items.£Ô, 0, 1, 1, 1), new WeightedRandomChestContent(Items.£ÇªÓ, 0, 1, 1, 1) });
        }
        
        public Âµá€() {
        }
        
        public Âµá€(final int p_i45578_1_, final Random p_i45578_2_, final StructureBoundingBox p_i45578_3_, final EnumFacing p_i45578_4_) {
            super(p_i45578_1_);
            this.Ó = p_i45578_4_;
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È(p_i45578_2_);
            this.Âµá€ = p_i45578_3_;
            this.Â = (p_i45578_3_.Ø­áŒŠá() > 6);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Tall", this.Â);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.Â = p_143011_1_.£á("Tall");
        }
        
        public static Âµá€ HorizonCode_Horizon_È(final List p_175864_0_, final Random p_175864_1_, final int p_175864_2_, final int p_175864_3_, final int p_175864_4_, final EnumFacing p_175864_5_, final int p_175864_6_) {
            StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 11, 15, p_175864_5_);
            if (!£à.HorizonCode_Horizon_È(var7) || StructureComponent.HorizonCode_Horizon_È(p_175864_0_, var7) != null) {
                var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175864_2_, p_175864_3_, p_175864_4_, -4, -1, 0, 14, 6, 15, p_175864_5_);
                if (!£à.HorizonCode_Horizon_È(var7) || StructureComponent.HorizonCode_Horizon_È(p_175864_0_, var7) != null) {
                    return null;
                }
            }
            return new Âµá€(p_175864_6_, p_175864_1_, var7, p_175864_5_);
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            byte var4 = 11;
            if (!this.Â) {
                var4 = 6;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 13, var4 - 1, 14, true, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, this.Ø­áŒŠá, 4, 1, 0);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.07f, 2, 1, 1, 11, 4, 13, Blocks.É.¥à(), Blocks.É.¥à(), false);
            final boolean var5 = true;
            final boolean var6 = true;
            for (int var7 = 1; var7 <= 13; ++var7) {
                if ((var7 - 1) % 4 == 0) {
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, var7, 1, 4, var7, Blocks.à.¥à(), Blocks.à.¥à(), false);
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 12, 1, var7, 12, 4, var7, Blocks.à.¥à(), Blocks.à.¥à(), false);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), 2, 3, var7, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), 11, 3, var7, p_74875_3_);
                    if (this.Â) {
                        this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 6, var7, 1, 9, var7, Blocks.à.¥à(), Blocks.à.¥à(), false);
                        this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 12, 6, var7, 12, 9, var7, Blocks.à.¥à(), Blocks.à.¥à(), false);
                    }
                }
                else {
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, var7, 1, 4, var7, Blocks.Ï­à.¥à(), Blocks.Ï­à.¥à(), false);
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 12, 1, var7, 12, 4, var7, Blocks.Ï­à.¥à(), Blocks.Ï­à.¥à(), false);
                    if (this.Â) {
                        this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 6, var7, 1, 9, var7, Blocks.Ï­à.¥à(), Blocks.Ï­à.¥à(), false);
                        this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 12, 6, var7, 12, 9, var7, Blocks.Ï­à.¥à(), Blocks.Ï­à.¥à(), false);
                    }
                }
            }
            for (int var7 = 3; var7 < 12; var7 += 2) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, var7, 4, 3, var7, Blocks.Ï­à.¥à(), Blocks.Ï­à.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 6, 1, var7, 7, 3, var7, Blocks.Ï­à.¥à(), Blocks.Ï­à.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 1, var7, 10, 3, var7, Blocks.Ï­à.¥à(), Blocks.Ï­à.¥à(), false);
            }
            if (this.Â) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 5, 1, 3, 5, 13, Blocks.à.¥à(), Blocks.à.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 10, 5, 1, 12, 5, 13, Blocks.à.¥à(), Blocks.à.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 5, 1, 9, 5, 2, Blocks.à.¥à(), Blocks.à.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 5, 12, 9, 5, 13, Blocks.à.¥à(), Blocks.à.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 9, 5, 11, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 8, 5, 11, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 9, 5, 10, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 6, 2, 3, 6, 12, Blocks.¥É.¥à(), Blocks.¥É.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 10, 6, 2, 10, 6, 10, Blocks.¥É.¥à(), Blocks.¥É.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 6, 2, 9, 6, 2, Blocks.¥É.¥à(), Blocks.¥É.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 6, 12, 8, 6, 12, Blocks.¥É.¥à(), Blocks.¥É.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 9, 6, 11, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 8, 6, 11, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 9, 6, 10, p_74875_3_);
                final int var7 = this.HorizonCode_Horizon_È(Blocks.áŒŠÏ, 3);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var7), 10, 1, 13, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var7), 10, 2, 13, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var7), 10, 3, 13, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var7), 10, 4, 13, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var7), 10, 5, 13, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var7), 10, 6, 13, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(var7), 10, 7, 13, p_74875_3_);
                final byte var8 = 7;
                final byte var9 = 7;
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8 - 1, 9, var9, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8, 9, var9, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8 - 1, 8, var9, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8, 8, var9, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8 - 1, 7, var9, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8, 7, var9, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8 - 2, 7, var9, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8 + 1, 7, var9, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8 - 1, 7, var9 - 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8 - 1, 7, var9 + 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8, 7, var9 - 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), var8, 7, var9 + 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), var8 - 2, 8, var9, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), var8 + 1, 8, var9, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), var8 - 1, 8, var9 - 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), var8 - 1, 8, var9 + 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), var8, 8, var9 - 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), var8, 8, var9 + 1, p_74875_3_);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 3, 3, 5, WeightedRandomChestContent.HorizonCode_Horizon_È(Âµá€.HorizonCode_Horizon_È, Items.Çªáˆºá.HorizonCode_Horizon_È(p_74875_2_, 1, 5, 2)), 1 + p_74875_2_.nextInt(4));
            if (this.Â) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 12, 9, 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 12, 8, 1, WeightedRandomChestContent.HorizonCode_Horizon_È(Âµá€.HorizonCode_Horizon_È, Items.Çªáˆºá.HorizonCode_Horizon_È(p_74875_2_, 1, 5, 2)), 1 + p_74875_2_.nextInt(4));
            }
            return true;
        }
    }
    
    static class Ó
    {
        public Class HorizonCode_Horizon_È;
        public final int Â;
        public int Ý;
        public int Ø­áŒŠá;
        private static final String Âµá€ = "CL_00000492";
        
        public Ó(final Class p_i2076_1_, final int p_i2076_2_, final int p_i2076_3_) {
            this.HorizonCode_Horizon_È = p_i2076_1_;
            this.Â = p_i2076_2_;
            this.Ø­áŒŠá = p_i2076_3_;
        }
        
        public boolean HorizonCode_Horizon_È(final int p_75189_1_) {
            return this.Ø­áŒŠá == 0 || this.Ý < this.Ø­áŒŠá;
        }
        
        public boolean HorizonCode_Horizon_È() {
            return this.Ø­áŒŠá == 0 || this.Ý < this.Ø­áŒŠá;
        }
    }
    
    public static class à extends £à
    {
        private boolean HorizonCode_Horizon_È;
        private static final String Â = "CL_00000493";
        
        public à() {
        }
        
        public à(final int p_i45577_1_, final Random p_i45577_2_, final StructureBoundingBox p_i45577_3_, final EnumFacing p_i45577_4_) {
            super(p_i45577_1_);
            this.Ó = p_i45577_4_;
            this.Âµá€ = p_i45577_3_;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Mob", this.HorizonCode_Horizon_È);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.HorizonCode_Horizon_È = p_143011_1_.£á("Mob");
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            if (p_74861_1_ != null) {
                ((á)p_74861_1_).Â = this;
            }
        }
        
        public static à HorizonCode_Horizon_È(final List p_175865_0_, final Random p_175865_1_, final int p_175865_2_, final int p_175865_3_, final int p_175865_4_, final EnumFacing p_175865_5_, final int p_175865_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175865_2_, p_175865_3_, p_175865_4_, -4, -1, 0, 11, 8, 16, p_175865_5_);
            return (£à.HorizonCode_Horizon_È(var7) && StructureComponent.HorizonCode_Horizon_È(p_175865_0_, var7) == null) ? new à(p_175865_6_, p_175865_1_, var7, p_175865_5_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 10, 7, 15, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, £à.HorizonCode_Horizon_È.Ý, 4, 1, 0);
            final byte var4 = 6;
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, var4, 1, 1, var4, 14, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, var4, 1, 9, var4, 14, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, var4, 1, 8, var4, 2, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, var4, 14, 8, var4, 14, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 1, 2, 1, 4, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 1, 1, 9, 1, 4, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 1, 1, 1, 3, Blocks.á.¥à(), Blocks.á.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 1, 1, 9, 1, 3, Blocks.á.¥à(), Blocks.á.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, 8, 7, 1, 12, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 9, 6, 1, 11, Blocks.á.¥à(), Blocks.á.¥à(), false);
            for (int var5 = 3; var5 < 14; var5 += 2) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 3, var5, 0, 4, var5, Blocks.ÇŽÄ.¥à(), Blocks.ÇŽÄ.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 10, 3, var5, 10, 4, var5, Blocks.ÇŽÄ.¥à(), Blocks.ÇŽÄ.¥à(), false);
            }
            for (int var5 = 2; var5 < 9; var5 += 2) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, var5, 3, 15, var5, 4, 15, Blocks.ÇŽÄ.¥à(), Blocks.ÇŽÄ.¥à(), false);
            }
            int var5 = this.HorizonCode_Horizon_È(Blocks.¥Å, 3);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 5, 6, 1, 7, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 2, 6, 6, 2, 7, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 3, 7, 6, 3, 7, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            for (int var6 = 4; var6 <= 6; ++var6) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥Å.Ý(var5), var6, 1, 4, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥Å.Ý(var5), var6, 2, 5, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.¥Å.Ý(var5), var6, 3, 6, p_74875_3_);
            }
            int var6 = EnumFacing.Ý.Ý();
            int var7 = EnumFacing.Ø­áŒŠá.Ý();
            int var8 = EnumFacing.Ó.Ý();
            int var9 = EnumFacing.Âµá€.Ý();
            if (this.Ó != null) {
                switch (µà.Â[this.Ó.ordinal()]) {
                    case 2: {
                        var6 = EnumFacing.Ø­áŒŠá.Ý();
                        var7 = EnumFacing.Ý.Ý();
                        break;
                    }
                    case 3: {
                        var6 = EnumFacing.Âµá€.Ý();
                        var7 = EnumFacing.Ó.Ý();
                        var8 = EnumFacing.Ø­áŒŠá.Ý();
                        var9 = EnumFacing.Ý.Ý();
                        break;
                    }
                    case 4: {
                        var6 = EnumFacing.Ó.Ý();
                        var7 = EnumFacing.Âµá€.Ý();
                        var8 = EnumFacing.Ø­áŒŠá.Ý();
                        var9 = EnumFacing.Ý.Ý();
                        break;
                    }
                }
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var6).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 4, 3, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var6).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 5, 3, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var6).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 6, 3, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var7).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 4, 3, 12, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var7).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 5, 3, 12, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var7).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 6, 3, 12, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var8).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 3, 3, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var8).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 3, 3, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var8).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 3, 3, 11, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var9).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 7, 3, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var9).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 7, 3, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥áŠ.Ý(var9).HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, p_74875_2_.nextFloat() > 0.9f), 7, 3, 11, p_74875_3_);
            if (!this.HorizonCode_Horizon_È) {
                final int var10 = this.HorizonCode_Horizon_È(3);
                final BlockPos var11 = new BlockPos(this.HorizonCode_Horizon_È(5, 6), var10, this.Â(5, 6));
                if (p_74875_3_.HorizonCode_Horizon_È(var11)) {
                    this.HorizonCode_Horizon_È = true;
                    worldIn.HorizonCode_Horizon_È(var11, Blocks.ÇªÓ.¥à(), 2);
                    final TileEntity var12 = worldIn.HorizonCode_Horizon_È(var11);
                    if (var12 instanceof TileEntityMobSpawner) {
                        ((TileEntityMobSpawner)var12).Â().HorizonCode_Horizon_È("Silverfish");
                    }
                }
            }
            return true;
        }
    }
    
    public static class Ø extends £à
    {
        private static final String HorizonCode_Horizon_È = "CL_00000494";
        
        public Ø() {
        }
        
        public Ø(final int p_i45576_1_, final Random p_i45576_2_, final StructureBoundingBox p_i45576_3_, final EnumFacing p_i45576_4_) {
            super(p_i45576_1_);
            this.Ó = p_i45576_4_;
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È(p_i45576_2_);
            this.Âµá€ = p_i45576_3_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            this.HorizonCode_Horizon_È((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
        
        public static Ø HorizonCode_Horizon_È(final List p_175860_0_, final Random p_175860_1_, final int p_175860_2_, final int p_175860_3_, final int p_175860_4_, final EnumFacing p_175860_5_, final int p_175860_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175860_2_, p_175860_3_, p_175860_4_, -1, -1, 0, 9, 5, 11, p_175860_5_);
            return (£à.HorizonCode_Horizon_È(var7) && StructureComponent.HorizonCode_Horizon_È(p_175860_0_, var7) == null) ? new Ø(p_175860_6_, p_175860_1_, var7, p_175860_5_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 8, 4, 10, true, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, this.Ø­áŒŠá, 1, 1, 0);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 10, 3, 3, 10, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 1, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 3, 4, 3, 3, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 7, 4, 3, 7, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 9, 4, 3, 9, false, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 4, 4, 3, 6, Blocks.ÇŽÄ.¥à(), Blocks.ÇŽÄ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 1, 5, 7, 3, 5, Blocks.ÇŽÄ.¥à(), Blocks.ÇŽÄ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), 4, 3, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), 4, 3, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŠÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ŠÓ, 3)), 4, 1, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŠÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ŠÓ, 3) + 8), 4, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŠÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ŠÓ, 3)), 4, 1, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŠÓ.Ý(this.HorizonCode_Horizon_È(Blocks.ŠÓ, 3) + 8), 4, 2, 8, p_74875_3_);
            return true;
        }
    }
    
    public static class áŒŠÆ extends Ø­áŒŠá
    {
        private static final String HorizonCode_Horizon_È = "CL_00000495";
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            if (this.Ó != EnumFacing.Ý && this.Ó != EnumFacing.Ó) {
                this.Â((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            }
            else {
                this.Ý((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            }
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 4, true, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, this.Ø­áŒŠá, 1, 1, 0);
            if (this.Ó != EnumFacing.Ý && this.Ó != EnumFacing.Ó) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 1, 0, 3, 3, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            else {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 1, 4, 3, 3, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            return true;
        }
    }
    
    public static class áˆºÑ¢Õ extends £à
    {
        private static final List Â;
        protected int HorizonCode_Horizon_È;
        private static final String Ý = "CL_00000496";
        
        static {
            Â = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.áˆºÑ¢Õ, 0, 1, 5, 10), new WeightedRandomChestContent(Items.ÂµÈ, 0, 1, 3, 5), new WeightedRandomChestContent(Items.ÇŽá, 0, 4, 9, 5), new WeightedRandomChestContent(Items.Ø, 0, 3, 8, 10), new WeightedRandomChestContent(Items.Ç, 0, 1, 3, 15), new WeightedRandomChestContent(Items.Âµá€, 0, 1, 3, 15), new WeightedRandomChestContent(Items.Â, 0, 1, 1, 1) });
        }
        
        public áˆºÑ¢Õ() {
        }
        
        public áˆºÑ¢Õ(final int p_i45575_1_, final Random p_i45575_2_, final StructureBoundingBox p_i45575_3_, final EnumFacing p_i45575_4_) {
            super(p_i45575_1_);
            this.Ó = p_i45575_4_;
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È(p_i45575_2_);
            this.Âµá€ = p_i45575_3_;
            this.HorizonCode_Horizon_È = p_i45575_2_.nextInt(5);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Type", this.HorizonCode_Horizon_È);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.HorizonCode_Horizon_È = p_143011_1_.Ó("Type");
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            this.HorizonCode_Horizon_È((á)p_74861_1_, p_74861_2_, p_74861_3_, 4, 1);
            this.Â((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
            this.Ý((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 4);
        }
        
        public static áˆºÑ¢Õ HorizonCode_Horizon_È(final List p_175859_0_, final Random p_175859_1_, final int p_175859_2_, final int p_175859_3_, final int p_175859_4_, final EnumFacing p_175859_5_, final int p_175859_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175859_2_, p_175859_3_, p_175859_4_, -4, -1, 0, 11, 7, 11, p_175859_5_);
            return (£à.HorizonCode_Horizon_È(var7) && StructureComponent.HorizonCode_Horizon_È(p_175859_0_, var7) == null) ? new áˆºÑ¢Õ(p_175859_6_, p_175859_1_, var7, p_175859_5_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 10, 6, 10, true, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, this.Ø­áŒŠá, 4, 1, 0);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 10, 6, 3, 10, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 4, 0, 3, 6, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 10, 1, 4, 10, 3, 6, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            switch (this.HorizonCode_Horizon_È) {
                case 0: {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 5, 1, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 5, 2, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 5, 3, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), 4, 3, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), 6, 3, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), 5, 3, 4, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), 5, 3, 6, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.¥à(), 4, 1, 4, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.¥à(), 4, 1, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.¥à(), 4, 1, 6, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.¥à(), 6, 1, 4, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.¥à(), 6, 1, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.¥à(), 6, 1, 6, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.¥à(), 5, 1, 4, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.¥à(), 5, 1, 6, p_74875_3_);
                    break;
                }
                case 1: {
                    for (int var4 = 0; var4 < 5; ++var4) {
                        this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 3, 1, 3 + var4, p_74875_3_);
                        this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 7, 1, 3 + var4, p_74875_3_);
                        this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 3 + var4, 1, 3, p_74875_3_);
                        this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 3 + var4, 1, 7, p_74875_3_);
                    }
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 5, 1, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 5, 2, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 5, 3, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºÑ¢Õ.¥à(), 5, 4, 5, p_74875_3_);
                    break;
                }
                case 2: {
                    for (int var4 = 1; var4 <= 9; ++var4) {
                        this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 1, 3, var4, p_74875_3_);
                        this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 9, 3, var4, p_74875_3_);
                    }
                    for (int var4 = 1; var4 <= 9; ++var4) {
                        this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), var4, 3, 1, p_74875_3_);
                        this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), var4, 3, 9, p_74875_3_);
                    }
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 5, 1, 4, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 5, 1, 6, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 5, 3, 4, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 5, 3, 6, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, 1, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 6, 1, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, 3, 5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 6, 3, 5, p_74875_3_);
                    for (int var4 = 1; var4 <= 3; ++var4) {
                        this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, var4, 4, p_74875_3_);
                        this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 6, var4, 4, p_74875_3_);
                        this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 4, var4, 6, p_74875_3_);
                        this.HorizonCode_Horizon_È(worldIn, Blocks.Ó.¥à(), 6, var4, 6, p_74875_3_);
                    }
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ï.¥à(), 5, 3, 5, p_74875_3_);
                    for (int var4 = 2; var4 <= 8; ++var4) {
                        this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 2, 3, var4, p_74875_3_);
                        this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 3, 3, var4, p_74875_3_);
                        if (var4 <= 3 || var4 >= 7) {
                            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 4, 3, var4, p_74875_3_);
                            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 5, 3, var4, p_74875_3_);
                            this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 6, 3, var4, p_74875_3_);
                        }
                        this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 7, 3, var4, p_74875_3_);
                        this.HorizonCode_Horizon_È(worldIn, Blocks.à.¥à(), 8, 3, var4, p_74875_3_);
                    }
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(this.HorizonCode_Horizon_È(Blocks.áŒŠÏ, EnumFacing.Âµá€.Â())), 9, 1, 3, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(this.HorizonCode_Horizon_È(Blocks.áŒŠÏ, EnumFacing.Âµá€.Â())), 9, 2, 3, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÏ.Ý(this.HorizonCode_Horizon_È(Blocks.áŒŠÏ, EnumFacing.Âµá€.Â())), 9, 3, 3, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 3, 4, 8, WeightedRandomChestContent.HorizonCode_Horizon_È(áˆºÑ¢Õ.Â, Items.Çªáˆºá.HorizonCode_Horizon_È(p_74875_2_)), 1 + p_74875_2_.nextInt(4));
                    break;
                }
            }
            return true;
        }
    }
    
    public static class ÂµÈ extends £à
    {
        private boolean HorizonCode_Horizon_È;
        private static final String Â = "CL_00000498";
        
        public ÂµÈ() {
        }
        
        public ÂµÈ(final int p_i2081_1_, final Random p_i2081_2_, final int p_i2081_3_, final int p_i2081_4_) {
            super(p_i2081_1_);
            this.HorizonCode_Horizon_È = true;
            this.Ó = EnumFacing.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_i2081_2_);
            this.Ø­áŒŠá = £à.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            switch (µà.Â[this.Ó.ordinal()]) {
                case 1:
                case 2: {
                    this.Âµá€ = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
                    break;
                }
                default: {
                    this.Âµá€ = new StructureBoundingBox(p_i2081_3_, 64, p_i2081_4_, p_i2081_3_ + 5 - 1, 74, p_i2081_4_ + 5 - 1);
                    break;
                }
            }
        }
        
        public ÂµÈ(final int p_i45574_1_, final Random p_i45574_2_, final StructureBoundingBox p_i45574_3_, final EnumFacing p_i45574_4_) {
            super(p_i45574_1_);
            this.HorizonCode_Horizon_È = false;
            this.Ó = p_i45574_4_;
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È(p_i45574_2_);
            this.Âµá€ = p_i45574_3_;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Source", this.HorizonCode_Horizon_È);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.HorizonCode_Horizon_È = p_143011_1_.£á("Source");
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            if (this.HorizonCode_Horizon_È) {
                StructureStrongholdPieces.HorizonCode_Horizon_È(Ý.class);
            }
            this.HorizonCode_Horizon_È((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
        
        public static ÂµÈ HorizonCode_Horizon_È(final List p_175863_0_, final Random p_175863_1_, final int p_175863_2_, final int p_175863_3_, final int p_175863_4_, final EnumFacing p_175863_5_, final int p_175863_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175863_2_, p_175863_3_, p_175863_4_, -1, -7, 0, 5, 11, 5, p_175863_5_);
            return (£à.HorizonCode_Horizon_È(var7) && StructureComponent.HorizonCode_Horizon_È(p_175863_0_, var7) == null) ? new ÂµÈ(p_175863_6_, p_175863_1_, var7, p_175863_5_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 4, 10, 4, true, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, this.Ø­áŒŠá, 1, 7, 0);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, £à.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 1, 1, 4);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 2, 6, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 1, 5, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()), 1, 6, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 1, 5, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 1, 4, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()), 1, 5, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 2, 4, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 3, 3, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()), 3, 4, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 3, 3, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 3, 2, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()), 3, 3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 2, 2, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 1, 1, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()), 1, 2, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 1, 1, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.HorizonCode_Horizon_È.Â()), 1, 1, 3, p_74875_3_);
            return true;
        }
    }
    
    public static class á extends ÂµÈ
    {
        public Ó HorizonCode_Horizon_È;
        public à Â;
        public List Ý;
        private static final String Ø = "CL_00000499";
        
        public á() {
            this.Ý = Lists.newArrayList();
        }
        
        public á(final int p_i2083_1_, final Random p_i2083_2_, final int p_i2083_3_, final int p_i2083_4_) {
            super(0, p_i2083_2_, p_i2083_3_, p_i2083_4_);
            this.Ý = Lists.newArrayList();
        }
        
        @Override
        public BlockPos Ø­áŒŠá() {
            return (this.Â != null) ? this.Â.Ø­áŒŠá() : super.Ø­áŒŠá();
        }
    }
    
    public static class ˆÏ­ extends £à
    {
        private static final String HorizonCode_Horizon_È = "CL_00000501";
        
        public ˆÏ­() {
        }
        
        public ˆÏ­(final int p_i45572_1_, final Random p_i45572_2_, final StructureBoundingBox p_i45572_3_, final EnumFacing p_i45572_4_) {
            super(p_i45572_1_);
            this.Ó = p_i45572_4_;
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È(p_i45572_2_);
            this.Âµá€ = p_i45572_3_;
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            this.HorizonCode_Horizon_È((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
        }
        
        public static ˆÏ­ HorizonCode_Horizon_È(final List p_175861_0_, final Random p_175861_1_, final int p_175861_2_, final int p_175861_3_, final int p_175861_4_, final EnumFacing p_175861_5_, final int p_175861_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175861_2_, p_175861_3_, p_175861_4_, -1, -7, 0, 5, 11, 8, p_175861_5_);
            return (£à.HorizonCode_Horizon_È(var7) && StructureComponent.HorizonCode_Horizon_È(p_175861_0_, var7) == null) ? new ˆÏ­(p_175861_6_, p_175861_1_, var7, p_175861_5_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 4, 10, 7, true, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, this.Ø­áŒŠá, 1, 7, 0);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, £à.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 1, 1, 7);
            final int var4 = this.HorizonCode_Horizon_È(Blocks.ˆÓ, 2);
            for (int var5 = 0; var5 < 6; ++var5) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 1, 6 - var5, 1 + var5, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 2, 6 - var5, 1 + var5, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 3, 6 - var5, 1 + var5, p_74875_3_);
                if (var5 < 5) {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 1, 5 - var5, 1 + var5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 2, 5 - var5, 1 + var5, p_74875_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), 3, 5 - var5, 1 + var5, p_74875_3_);
                }
            }
            return true;
        }
    }
    
    static class £á extends StructureComponent.HorizonCode_Horizon_È
    {
        private static final String Â = "CL_00000497";
        
        private £á() {
        }
        
        @Override
        public void HorizonCode_Horizon_È(final Random p_75062_1_, final int p_75062_2_, final int p_75062_3_, final int p_75062_4_, final boolean p_75062_5_) {
            if (p_75062_5_) {
                final float var6 = p_75062_1_.nextFloat();
                if (var6 < 0.2f) {
                    this.HorizonCode_Horizon_È = Blocks.£áƒ.Ý(BlockStoneBrick.¥à);
                }
                else if (var6 < 0.5f) {
                    this.HorizonCode_Horizon_È = Blocks.£áƒ.Ý(BlockStoneBrick.ŠÂµà);
                }
                else if (var6 < 0.55f) {
                    this.HorizonCode_Horizon_È = Blocks.ÐƒÂ.Ý(BlockSilverfish.HorizonCode_Horizon_È.Ý.Â());
                }
                else {
                    this.HorizonCode_Horizon_È = Blocks.£áƒ.¥à();
                }
            }
            else {
                this.HorizonCode_Horizon_È = Blocks.Â.¥à();
            }
        }
        
        £á(final Object p_i2080_1_) {
            this();
        }
    }
    
    public static class Å extends £à
    {
        private boolean HorizonCode_Horizon_È;
        private boolean Â;
        private static final String Ý = "CL_00000500";
        
        public Å() {
        }
        
        public Å(final int p_i45573_1_, final Random p_i45573_2_, final StructureBoundingBox p_i45573_3_, final EnumFacing p_i45573_4_) {
            super(p_i45573_1_);
            this.Ó = p_i45573_4_;
            this.Ø­áŒŠá = this.HorizonCode_Horizon_È(p_i45573_2_);
            this.Âµá€ = p_i45573_3_;
            this.HorizonCode_Horizon_È = (p_i45573_2_.nextInt(2) == 0);
            this.Â = (p_i45573_2_.nextInt(2) == 0);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Left", this.HorizonCode_Horizon_È);
            p_143012_1_.HorizonCode_Horizon_È("Right", this.Â);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.HorizonCode_Horizon_È = p_143011_1_.£á("Left");
            this.Â = p_143011_1_.£á("Right");
        }
        
        @Override
        public void HorizonCode_Horizon_È(final StructureComponent p_74861_1_, final List p_74861_2_, final Random p_74861_3_) {
            this.HorizonCode_Horizon_È((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 1);
            if (this.HorizonCode_Horizon_È) {
                this.Â((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
            }
            if (this.Â) {
                this.Ý((á)p_74861_1_, p_74861_2_, p_74861_3_, 1, 2);
            }
        }
        
        public static Å HorizonCode_Horizon_È(final List p_175862_0_, final Random p_175862_1_, final int p_175862_2_, final int p_175862_3_, final int p_175862_4_, final EnumFacing p_175862_5_, final int p_175862_6_) {
            final StructureBoundingBox var7 = StructureBoundingBox.HorizonCode_Horizon_È(p_175862_2_, p_175862_3_, p_175862_4_, -1, -1, 0, 5, 5, 7, p_175862_5_);
            return (£à.HorizonCode_Horizon_È(var7) && StructureComponent.HorizonCode_Horizon_È(p_175862_0_, var7) == null) ? new Å(p_175862_6_, p_175862_1_, var7, p_175862_5_) : null;
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (this.HorizonCode_Horizon_È(worldIn, p_74875_3_)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 4, 4, 6, true, p_74875_2_, StructureStrongholdPieces.Âµá€);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, this.Ø­áŒŠá, 1, 1, 0);
            this.HorizonCode_Horizon_È(worldIn, p_74875_2_, p_74875_3_, £à.HorizonCode_Horizon_È.HorizonCode_Horizon_È, 1, 1, 6);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.1f, 1, 2, 1, Blocks.Ï.¥à());
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.1f, 3, 2, 1, Blocks.Ï.¥à());
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.1f, 1, 2, 5, Blocks.Ï.¥à());
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 0.1f, 3, 2, 5, Blocks.Ï.¥à());
            if (this.HorizonCode_Horizon_È) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 1, 2, 0, 3, 4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            if (this.Â) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 2, 4, 3, 4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            return true;
        }
    }
    
    abstract static class £à extends StructureComponent
    {
        protected HorizonCode_Horizon_È Ø­áŒŠá;
        private static final String HorizonCode_Horizon_È = "CL_00000503";
        
        public £à() {
            this.Ø­áŒŠá = £à.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        }
        
        protected £à(final int p_i2087_1_) {
            super(p_i2087_1_);
            this.Ø­áŒŠá = £à.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            p_143012_1_.HorizonCode_Horizon_È("EntryDoor", this.Ø­áŒŠá.name());
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            this.Ø­áŒŠá = £à.HorizonCode_Horizon_È.valueOf(p_143011_1_.áˆºÑ¢Õ("EntryDoor"));
        }
        
        protected void HorizonCode_Horizon_È(final World worldIn, final Random p_74990_2_, final StructureBoundingBox p_74990_3_, final HorizonCode_Horizon_È p_74990_4_, final int p_74990_5_, final int p_74990_6_, final int p_74990_7_) {
            switch (µà.HorizonCode_Horizon_È[p_74990_4_.ordinal()]) {
                default: {
                    this.HorizonCode_Horizon_È(worldIn, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
                    break;
                }
                case 2: {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ï­Ô.¥à(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Ï­Ô.Ý(8), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    break;
                }
                case 3: {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÄ.¥à(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    break;
                }
                case 4: {
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.¥à(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.ŠÓ.¥à(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.ŠÓ.Ý(8), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Šà.Ý(this.HorizonCode_Horizon_È(Blocks.Šà, 4)), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
                    this.HorizonCode_Horizon_È(worldIn, Blocks.Šà.Ý(this.HorizonCode_Horizon_È(Blocks.Šà, 3)), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
                    break;
                }
            }
        }
        
        protected HorizonCode_Horizon_È HorizonCode_Horizon_È(final Random p_74988_1_) {
            final int var2 = p_74988_1_.nextInt(5);
            switch (var2) {
                default: {
                    return £à.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                }
                case 2: {
                    return £à.HorizonCode_Horizon_È.Â;
                }
                case 3: {
                    return £à.HorizonCode_Horizon_È.Ý;
                }
                case 4: {
                    return £à.HorizonCode_Horizon_È.Ø­áŒŠá;
                }
            }
        }
        
        protected StructureComponent HorizonCode_Horizon_È(final á p_74986_1_, final List p_74986_2_, final Random p_74986_3_, final int p_74986_4_, final int p_74986_5_) {
            if (this.Ó != null) {
                switch (µà.Â[this.Ó.ordinal()]) {
                    case 1: {
                        return Ý(p_74986_1_, p_74986_2_, p_74986_3_, this.Âµá€.HorizonCode_Horizon_È + p_74986_4_, this.Âµá€.Â + p_74986_5_, this.Âµá€.Ý - 1, this.Ó, this.Ý());
                    }
                    case 2: {
                        return Ý(p_74986_1_, p_74986_2_, p_74986_3_, this.Âµá€.HorizonCode_Horizon_È + p_74986_4_, this.Âµá€.Â + p_74986_5_, this.Âµá€.Ó + 1, this.Ó, this.Ý());
                    }
                    case 3: {
                        return Ý(p_74986_1_, p_74986_2_, p_74986_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â + p_74986_5_, this.Âµá€.Ý + p_74986_4_, this.Ó, this.Ý());
                    }
                    case 4: {
                        return Ý(p_74986_1_, p_74986_2_, p_74986_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â + p_74986_5_, this.Âµá€.Ý + p_74986_4_, this.Ó, this.Ý());
                    }
                }
            }
            return null;
        }
        
        protected StructureComponent Â(final á p_74989_1_, final List p_74989_2_, final Random p_74989_3_, final int p_74989_4_, final int p_74989_5_) {
            if (this.Ó != null) {
                switch (µà.Â[this.Ó.ordinal()]) {
                    case 1: {
                        return Ý(p_74989_1_, p_74989_2_, p_74989_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â + p_74989_4_, this.Âµá€.Ý + p_74989_5_, EnumFacing.Âµá€, this.Ý());
                    }
                    case 2: {
                        return Ý(p_74989_1_, p_74989_2_, p_74989_3_, this.Âµá€.HorizonCode_Horizon_È - 1, this.Âµá€.Â + p_74989_4_, this.Âµá€.Ý + p_74989_5_, EnumFacing.Âµá€, this.Ý());
                    }
                    case 3: {
                        return Ý(p_74989_1_, p_74989_2_, p_74989_3_, this.Âµá€.HorizonCode_Horizon_È + p_74989_5_, this.Âµá€.Â + p_74989_4_, this.Âµá€.Ý - 1, EnumFacing.Ý, this.Ý());
                    }
                    case 4: {
                        return Ý(p_74989_1_, p_74989_2_, p_74989_3_, this.Âµá€.HorizonCode_Horizon_È + p_74989_5_, this.Âµá€.Â + p_74989_4_, this.Âµá€.Ý - 1, EnumFacing.Ý, this.Ý());
                    }
                }
            }
            return null;
        }
        
        protected StructureComponent Ý(final á p_74987_1_, final List p_74987_2_, final Random p_74987_3_, final int p_74987_4_, final int p_74987_5_) {
            if (this.Ó != null) {
                switch (µà.Â[this.Ó.ordinal()]) {
                    case 1: {
                        return Ý(p_74987_1_, p_74987_2_, p_74987_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â + p_74987_4_, this.Âµá€.Ý + p_74987_5_, EnumFacing.Ó, this.Ý());
                    }
                    case 2: {
                        return Ý(p_74987_1_, p_74987_2_, p_74987_3_, this.Âµá€.Ø­áŒŠá + 1, this.Âµá€.Â + p_74987_4_, this.Âµá€.Ý + p_74987_5_, EnumFacing.Ó, this.Ý());
                    }
                    case 3: {
                        return Ý(p_74987_1_, p_74987_2_, p_74987_3_, this.Âµá€.HorizonCode_Horizon_È + p_74987_5_, this.Âµá€.Â + p_74987_4_, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, this.Ý());
                    }
                    case 4: {
                        return Ý(p_74987_1_, p_74987_2_, p_74987_3_, this.Âµá€.HorizonCode_Horizon_È + p_74987_5_, this.Âµá€.Â + p_74987_4_, this.Âµá€.Ó + 1, EnumFacing.Ø­áŒŠá, this.Ý());
                    }
                }
            }
            return null;
        }
        
        protected static boolean HorizonCode_Horizon_È(final StructureBoundingBox p_74991_0_) {
            return p_74991_0_ != null && p_74991_0_.Â > 10;
        }
        
        public enum HorizonCode_Horizon_È
        {
            HorizonCode_Horizon_È("OPENING", 0, "OPENING", 0), 
            Â("WOOD_DOOR", 1, "WOOD_DOOR", 1), 
            Ý("GRATES", 2, "GRATES", 2), 
            Ø­áŒŠá("IRON_DOOR", 3, "IRON_DOOR", 3);
            
            private static final HorizonCode_Horizon_È[] Âµá€;
            private static final String Ó = "CL_00000504";
            
            static {
                à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
                Âµá€ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá };
            }
            
            private HorizonCode_Horizon_È(final String s, final int n, final String p_i2086_1_, final int p_i2086_2_) {
            }
        }
    }
    
    static final class µà
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        private static final String Ý = "CL_00001970";
        
        static {
            Â = new int[EnumFacing.values().length];
            try {
                µà.Â[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                µà.Â[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                µà.Â[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                µà.Â[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            HorizonCode_Horizon_È = new int[£à.HorizonCode_Horizon_È.values().length];
            try {
                µà.HorizonCode_Horizon_È[£à.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                µà.HorizonCode_Horizon_È[£à.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                µà.HorizonCode_Horizon_È[£à.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                µà.HorizonCode_Horizon_È[£à.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
        }
    }
}
