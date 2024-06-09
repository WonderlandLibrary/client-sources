package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;
import com.google.common.collect.Lists;
import java.util.List;

public class ComponentScatteredFeaturePieces
{
    private static final String HorizonCode_Horizon_È = "CL_00000473";
    
    public static void HorizonCode_Horizon_È() {
        MapGenStructureIO.HorizonCode_Horizon_È(HorizonCode_Horizon_È.class, "TeDP");
        MapGenStructureIO.HorizonCode_Horizon_È(Ý.class, "TeJP");
        MapGenStructureIO.HorizonCode_Horizon_È(Ø­áŒŠá.class, "TeSH");
    }
    
    public static class HorizonCode_Horizon_È extends Â
    {
        private boolean[] Ø;
        private static final List áŒŠÆ;
        private static final String áˆºÑ¢Õ = "CL_00000476";
        
        static {
            áŒŠÆ = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.áŒŠÆ, 0, 1, 3, 3), new WeightedRandomChestContent(Items.áˆºÑ¢Õ, 0, 1, 5, 10), new WeightedRandomChestContent(Items.ÂµÈ, 0, 2, 7, 15), new WeightedRandomChestContent(Items.µ, 0, 1, 3, 2), new WeightedRandomChestContent(Items.ŠÕ, 0, 4, 6, 20), new WeightedRandomChestContent(Items.ŠØ, 0, 3, 7, 16), new WeightedRandomChestContent(Items.Û, 0, 1, 1, 3), new WeightedRandomChestContent(Items.ÐƒÇŽà, 0, 1, 1, 1), new WeightedRandomChestContent(Items.¥Ê, 0, 1, 1, 1), new WeightedRandomChestContent(Items.ÐƒÓ, 0, 1, 1, 1) });
        }
        
        public HorizonCode_Horizon_È() {
            this.Ø = new boolean[4];
        }
        
        public HorizonCode_Horizon_È(final Random p_i2062_1_, final int p_i2062_2_, final int p_i2062_3_) {
            super(p_i2062_1_, p_i2062_2_, 64, p_i2062_3_, 21, 15, 21);
            this.Ø = new boolean[4];
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("hasPlacedChest0", this.Ø[0]);
            p_143012_1_.HorizonCode_Horizon_È("hasPlacedChest1", this.Ø[1]);
            p_143012_1_.HorizonCode_Horizon_È("hasPlacedChest2", this.Ø[2]);
            p_143012_1_.HorizonCode_Horizon_È("hasPlacedChest3", this.Ø[3]);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.Ø[0] = p_143011_1_.£á("hasPlacedChest0");
            this.Ø[1] = p_143011_1_.£á("hasPlacedChest1");
            this.Ø[2] = p_143011_1_.£á("hasPlacedChest2");
            this.Ø[3] = p_143011_1_.£á("hasPlacedChest3");
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, -4, 0, this.HorizonCode_Horizon_È - 1, 0, this.Ý - 1, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            for (int var4 = 1; var4 <= 9; ++var4) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, var4, var4, var4, this.HorizonCode_Horizon_È - 1 - var4, var4, this.Ý - 1 - var4, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, var4 + 1, var4, var4 + 1, this.HorizonCode_Horizon_È - 2 - var4, var4, this.Ý - 2 - var4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            }
            for (int var4 = 0; var4 < this.HorizonCode_Horizon_È; ++var4) {
                for (int var5 = 0; var5 < this.Ý; ++var5) {
                    final byte var6 = -5;
                    this.Â(worldIn, Blocks.ŒÏ.¥à(), var4, var6, var5, p_74875_3_);
                }
            }
            int var4 = this.HorizonCode_Horizon_È(Blocks.µÏ, 3);
            int var5 = this.HorizonCode_Horizon_È(Blocks.µÏ, 2);
            final int var7 = this.HorizonCode_Horizon_È(Blocks.µÏ, 0);
            final int var8 = this.HorizonCode_Horizon_È(Blocks.µÏ, 1);
            final int var9 = ~EnumDyeColor.Â.Ý() & 0xF;
            final int var10 = ~EnumDyeColor.á.Ý() & 0xF;
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 0, 0, 4, 9, 4, Blocks.ŒÏ.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 10, 1, 3, 10, 3, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var4), 2, 10, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var5), 2, 10, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var7), 0, 10, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var8), 4, 10, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.HorizonCode_Horizon_È - 5, 0, 0, this.HorizonCode_Horizon_È - 1, 9, 4, Blocks.ŒÏ.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.HorizonCode_Horizon_È - 4, 10, 1, this.HorizonCode_Horizon_È - 2, 10, 3, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var4), this.HorizonCode_Horizon_È - 3, 10, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var5), this.HorizonCode_Horizon_È - 3, 10, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var7), this.HorizonCode_Horizon_È - 5, 10, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var8), this.HorizonCode_Horizon_È - 1, 10, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 0, 0, 12, 4, 4, Blocks.ŒÏ.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 1, 0, 11, 3, 4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 9, 1, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 9, 2, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 9, 3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 10, 3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 11, 3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 11, 2, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 11, 1, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 1, 8, 3, 3, Blocks.ŒÏ.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 2, 8, 2, 2, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 12, 1, 1, 16, 3, 3, Blocks.ŒÏ.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 12, 1, 2, 16, 2, 2, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 4, 5, this.HorizonCode_Horizon_È - 6, 4, this.Ý - 6, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 4, 9, 11, 4, 11, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 1, 8, 8, 3, 8, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 12, 1, 8, 12, 3, 8, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 1, 12, 8, 3, 12, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 12, 1, 12, 12, 3, 12, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 5, 4, 4, 11, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.HorizonCode_Horizon_È - 5, 1, 5, this.HorizonCode_Horizon_È - 2, 4, 11, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 6, 7, 9, 6, 7, 11, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.HorizonCode_Horizon_È - 7, 7, 9, this.HorizonCode_Horizon_È - 7, 7, 11, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 5, 9, 5, 7, 11, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.HorizonCode_Horizon_È - 6, 5, 9, this.HorizonCode_Horizon_È - 6, 7, 11, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 5, 5, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 5, 6, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 6, 6, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), this.HorizonCode_Horizon_È - 6, 5, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), this.HorizonCode_Horizon_È - 6, 6, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), this.HorizonCode_Horizon_È - 7, 6, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 4, 4, 2, 6, 4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.HorizonCode_Horizon_È - 3, 4, 4, this.HorizonCode_Horizon_È - 3, 6, 4, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var4), 2, 4, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var4), 2, 3, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var4), this.HorizonCode_Horizon_È - 3, 4, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var4), this.HorizonCode_Horizon_È - 3, 3, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 3, 2, 2, 3, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.HorizonCode_Horizon_È - 3, 1, 3, this.HorizonCode_Horizon_È - 2, 2, 3, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.¥à(), 1, 1, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.¥à(), this.HorizonCode_Horizon_È - 2, 1, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.Â.Â()), 1, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Âµ.Ý(BlockStoneSlab.HorizonCode_Horizon_È.Â.Â()), this.HorizonCode_Horizon_È - 2, 2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var8), 2, 1, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.µÏ.Ý(var7), this.HorizonCode_Horizon_È - 3, 1, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 3, 5, 4, 3, 18, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.HorizonCode_Horizon_È - 5, 3, 5, this.HorizonCode_Horizon_È - 5, 3, 17, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, 5, 4, 2, 16, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, this.HorizonCode_Horizon_È - 6, 1, 5, this.HorizonCode_Horizon_È - 5, 2, 16, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            for (int var11 = 5; var11 <= 17; var11 += 2) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 4, 1, var11, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), 4, 2, var11, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), this.HorizonCode_Horizon_È - 5, 1, var11, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), this.HorizonCode_Horizon_È - 5, 2, var11, p_74875_3_);
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 10, 0, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 10, 0, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 9, 0, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 11, 0, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 8, 0, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 12, 0, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 7, 0, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 13, 0, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 9, 0, 11, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 11, 0, 11, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 10, 0, 12, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 10, 0, 13, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var10), 10, 0, 10, p_74875_3_);
            for (int var11 = 0; var11 <= this.HorizonCode_Horizon_È - 1; var11 += this.HorizonCode_Horizon_È - 1) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11, 2, 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 2, 2, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11, 2, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11, 3, 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 3, 2, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11, 3, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 4, 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), var11, 4, 2, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 4, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11, 5, 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 5, 2, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11, 5, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 6, 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), var11, 6, 2, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 6, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 7, 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 7, 2, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 7, 3, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11, 8, 1, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11, 8, 2, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11, 8, 3, p_74875_3_);
            }
            for (int var11 = 2; var11 <= this.HorizonCode_Horizon_È - 3; var11 += this.HorizonCode_Horizon_È - 3 - 2) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11 - 1, 2, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 2, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11 + 1, 2, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11 - 1, 3, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 3, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11 + 1, 3, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11 - 1, 4, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), var11, 4, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11 + 1, 4, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11 - 1, 5, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 5, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11 + 1, 5, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11 - 1, 6, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), var11, 6, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11 + 1, 6, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11 - 1, 7, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11, 7, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), var11 + 1, 7, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11 - 1, 8, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11, 8, 0, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), var11 + 1, 8, 0, p_74875_3_);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, 4, 0, 12, 6, 0, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 8, 6, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 12, 6, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 9, 5, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), 10, 5, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Ø­Â.Ý(var9), 11, 5, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, -14, 8, 12, -11, 12, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, -10, 8, 12, -10, 12, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, -9, 8, 12, -9, 12, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, -8, 8, 12, -1, 12, Blocks.ŒÏ.¥à(), Blocks.ŒÏ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, -11, 9, 11, -1, 11, Blocks.Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Û.¥à(), 10, -11, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, -13, 9, 11, -13, 11, Blocks.Ñ¢Â.¥à(), Blocks.Â.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 8, -11, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 8, -10, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), 7, -10, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 7, -11, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 12, -11, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 12, -10, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), 13, -10, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 13, -11, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 10, -11, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 10, -10, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), 10, -10, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 10, -11, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 10, -11, 12, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 10, -10, 12, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Â.Â()), 10, -10, 13, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŒÏ.Ý(BlockSandStone.HorizonCode_Horizon_È.Ý.Â()), 10, -11, 13, p_74875_3_);
            for (final EnumFacing var13 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                if (!this.Ø[var13.Ý()]) {
                    final int var14 = var13.Ø() * 2;
                    final int var15 = var13.áˆºÑ¢Õ() * 2;
                    this.Ø[var13.Ý()] = this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 10 + var14, -11, 10 + var15, WeightedRandomChestContent.HorizonCode_Horizon_È(HorizonCode_Horizon_È.áŒŠÆ, Items.Çªáˆºá.HorizonCode_Horizon_È(p_74875_2_)), 2 + p_74875_2_.nextInt(5));
                }
            }
            return true;
        }
    }
    
    abstract static class Â extends StructureComponent
    {
        protected int HorizonCode_Horizon_È;
        protected int Â;
        protected int Ý;
        protected int Ø­áŒŠá;
        private static final String Ø = "CL_00000479";
        
        public Â() {
            this.Ø­áŒŠá = -1;
        }
        
        protected Â(final Random p_i2065_1_, final int p_i2065_2_, final int p_i2065_3_, final int p_i2065_4_, final int p_i2065_5_, final int p_i2065_6_, final int p_i2065_7_) {
            super(0);
            this.Ø­áŒŠá = -1;
            this.HorizonCode_Horizon_È = p_i2065_5_;
            this.Â = p_i2065_6_;
            this.Ý = p_i2065_7_;
            this.Ó = EnumFacing.Ý.HorizonCode_Horizon_È.HorizonCode_Horizon_È(p_i2065_1_);
            switch (Âµá€.HorizonCode_Horizon_È[this.Ó.ordinal()]) {
                case 1:
                case 2: {
                    this.Âµá€ = new StructureBoundingBox(p_i2065_2_, p_i2065_3_, p_i2065_4_, p_i2065_2_ + p_i2065_5_ - 1, p_i2065_3_ + p_i2065_6_ - 1, p_i2065_4_ + p_i2065_7_ - 1);
                    break;
                }
                default: {
                    this.Âµá€ = new StructureBoundingBox(p_i2065_2_, p_i2065_3_, p_i2065_4_, p_i2065_2_ + p_i2065_7_ - 1, p_i2065_3_ + p_i2065_6_ - 1, p_i2065_4_ + p_i2065_5_ - 1);
                    break;
                }
            }
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            p_143012_1_.HorizonCode_Horizon_È("Width", this.HorizonCode_Horizon_È);
            p_143012_1_.HorizonCode_Horizon_È("Height", this.Â);
            p_143012_1_.HorizonCode_Horizon_È("Depth", this.Ý);
            p_143012_1_.HorizonCode_Horizon_È("HPos", this.Ø­áŒŠá);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            this.HorizonCode_Horizon_È = p_143011_1_.Ó("Width");
            this.Â = p_143011_1_.Ó("Height");
            this.Ý = p_143011_1_.Ó("Depth");
            this.Ø­áŒŠá = p_143011_1_.Ó("HPos");
        }
        
        protected boolean HorizonCode_Horizon_È(final World worldIn, final StructureBoundingBox p_74935_2_, final int p_74935_3_) {
            if (this.Ø­áŒŠá >= 0) {
                return true;
            }
            int var4 = 0;
            int var5 = 0;
            for (int var6 = this.Âµá€.Ý; var6 <= this.Âµá€.Ó; ++var6) {
                for (int var7 = this.Âµá€.HorizonCode_Horizon_È; var7 <= this.Âµá€.Ø­áŒŠá; ++var7) {
                    final BlockPos var8 = new BlockPos(var7, 64, var6);
                    if (p_74935_2_.HorizonCode_Horizon_È(var8)) {
                        var4 += Math.max(worldIn.ˆà(var8).Â(), worldIn.£à.áŒŠÆ());
                        ++var5;
                    }
                }
            }
            if (var5 == 0) {
                return false;
            }
            this.Ø­áŒŠá = var4 / var5;
            this.Âµá€.HorizonCode_Horizon_È(0, this.Ø­áŒŠá - this.Âµá€.Â + p_74935_3_, 0);
            return true;
        }
    }
    
    public static class Ý extends Â
    {
        private boolean Ø;
        private boolean áŒŠÆ;
        private boolean áˆºÑ¢Õ;
        private boolean ÂµÈ;
        private static final List á;
        private static final List ˆÏ­;
        private static HorizonCode_Horizon_È £á;
        private static final String Å = "CL_00000477";
        
        static {
            á = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.áŒŠÆ, 0, 1, 3, 3), new WeightedRandomChestContent(Items.áˆºÑ¢Õ, 0, 1, 5, 10), new WeightedRandomChestContent(Items.ÂµÈ, 0, 2, 7, 15), new WeightedRandomChestContent(Items.µ, 0, 1, 3, 2), new WeightedRandomChestContent(Items.ŠÕ, 0, 4, 6, 20), new WeightedRandomChestContent(Items.ŠØ, 0, 3, 7, 16), new WeightedRandomChestContent(Items.Û, 0, 1, 1, 3), new WeightedRandomChestContent(Items.ÐƒÇŽà, 0, 1, 1, 1), new WeightedRandomChestContent(Items.¥Ê, 0, 1, 1, 1), new WeightedRandomChestContent(Items.ÐƒÓ, 0, 1, 1, 1) });
            ˆÏ­ = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.à, 0, 2, 7, 30) });
            Ý.£á = new HorizonCode_Horizon_È(null);
        }
        
        public Ý() {
        }
        
        public Ý(final Random p_i2064_1_, final int p_i2064_2_, final int p_i2064_3_) {
            super(p_i2064_1_, p_i2064_2_, 64, p_i2064_3_, 12, 10, 15);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("placedMainChest", this.Ø);
            p_143012_1_.HorizonCode_Horizon_È("placedHiddenChest", this.áŒŠÆ);
            p_143012_1_.HorizonCode_Horizon_È("placedTrap1", this.áˆºÑ¢Õ);
            p_143012_1_.HorizonCode_Horizon_È("placedTrap2", this.ÂµÈ);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.Ø = p_143011_1_.£á("placedMainChest");
            this.áŒŠÆ = p_143011_1_.£á("placedHiddenChest");
            this.áˆºÑ¢Õ = p_143011_1_.£á("placedTrap1");
            this.ÂµÈ = p_143011_1_.£á("placedTrap2");
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (!this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0)) {
                return false;
            }
            final int var4 = this.HorizonCode_Horizon_È(Blocks.ˆÓ, 3);
            final int var5 = this.HorizonCode_Horizon_È(Blocks.ˆÓ, 2);
            final int var6 = this.HorizonCode_Horizon_È(Blocks.ˆÓ, 0);
            final int var7 = this.HorizonCode_Horizon_È(Blocks.ˆÓ, 1);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, -4, 0, this.HorizonCode_Horizon_È - 1, 0, this.Ý - 1, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 1, 2, 9, 2, 2, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 1, 12, 9, 2, 12, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 1, 3, 2, 2, 11, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 1, 3, 9, 2, 11, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 3, 1, 10, 6, 1, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 3, 13, 10, 6, 13, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 3, 2, 1, 6, 12, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 10, 3, 2, 10, 6, 12, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 3, 2, 9, 3, 12, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 6, 2, 9, 6, 12, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 7, 3, 8, 7, 11, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 8, 4, 7, 8, 10, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 3, 1, 3, 8, 2, 11);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 3, 6, 7, 3, 9);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 4, 2, 9, 5, 12);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 6, 5, 7, 6, 9);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 7, 6, 6, 7, 8);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 1, 2, 6, 2, 2);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 2, 12, 6, 2, 12);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 5, 1, 6, 5, 1);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 5, 13, 6, 5, 13);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 1, 5, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 10, 5, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 1, 5, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 10, 5, 9, p_74875_3_);
            for (int var8 = 0; var8 <= 14; var8 += 14) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 4, var8, 2, 5, var8, false, p_74875_2_, Ý.£á);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 4, var8, 4, 5, var8, false, p_74875_2_, Ý.£á);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 7, 4, var8, 7, 5, var8, false, p_74875_2_, Ý.£á);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 4, var8, 9, 5, var8, false, p_74875_2_, Ý.£á);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 6, 0, 6, 6, 0, false, p_74875_2_, Ý.£á);
            for (int var8 = 0; var8 <= 11; var8 += 11) {
                for (int var9 = 2; var9 <= 12; var9 += 2) {
                    this.HorizonCode_Horizon_È(worldIn, p_74875_3_, var8, 4, var9, var8, 5, var9, false, p_74875_2_, Ý.£á);
                }
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, var8, 6, 5, var8, 6, 5, false, p_74875_2_, Ý.£á);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, var8, 6, 9, var8, 6, 9, false, p_74875_2_, Ý.£á);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 7, 2, 2, 9, 2, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 7, 2, 9, 9, 2, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 7, 12, 2, 9, 12, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, 7, 12, 9, 9, 12, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 9, 4, 4, 9, 4, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 7, 9, 4, 7, 9, 4, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 9, 10, 4, 9, 10, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 7, 9, 10, 7, 9, 10, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 9, 7, 6, 9, 7, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 5, 9, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 6, 9, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var5), 5, 9, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var5), 6, 9, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 4, 0, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 5, 0, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 6, 0, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 7, 0, 0, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 4, 1, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 4, 2, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 4, 3, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 7, 1, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 7, 2, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var4), 7, 3, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 9, 4, 1, 9, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 7, 1, 9, 7, 1, 9, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 4, 1, 10, 7, 2, 10, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 4, 5, 6, 4, 5, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var6), 4, 4, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var7), 7, 4, 5, p_74875_3_);
            for (int var8 = 0; var8 < 4; ++var8) {
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var5), 5, 0 - var8, 6 + var8, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÓ.Ý(var5), 6, 0 - var8, 6 + var8, p_74875_3_);
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 0 - var8, 7 + var8, 6, 0 - var8, 9 + var8);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, -3, 12, 10, -1, 13);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, -3, 1, 3, -1, 13);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, -3, 1, 9, -1, 5);
            for (int var8 = 1; var8 <= 13; var8 += 2) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, -3, var8, 1, -2, var8, false, p_74875_2_, Ý.£á);
            }
            for (int var8 = 2; var8 <= 12; var8 += 2) {
                this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, -1, var8, 3, -1, var8, false, p_74875_2_, Ý.£á);
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, -2, 1, 5, -2, 1, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 7, -2, 1, 9, -2, 1, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 6, -3, 1, 6, -3, 1, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 6, -1, 1, 6, -1, 1, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÂ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÂ, EnumFacing.Ó.Ý())).HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà, true), 1, -3, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÂ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÂ, EnumFacing.Âµá€.Ý())).HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà, true), 4, -3, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÈ.¥à().HorizonCode_Horizon_È(BlockTripWire.ŠÂµà, true), 2, -3, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÈ.¥à().HorizonCode_Horizon_È(BlockTripWire.ŠÂµà, true), 3, -3, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 5, -3, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 5, -3, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 5, -3, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 5, -3, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 5, -3, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 5, -3, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 5, -3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 4, -3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 3, -3, 1, p_74875_3_);
            if (!this.áˆºÑ¢Õ) {
                this.áˆºÑ¢Õ = this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 3, -2, 1, EnumFacing.Ý.Â(), Ý.ˆÏ­, 2);
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽà.Ý(15), 3, -2, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÂ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÂ, EnumFacing.Ý.Ý())).HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà, true), 7, -3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÂ.Ý(this.HorizonCode_Horizon_È(Blocks.ˆÂ, EnumFacing.Ø­áŒŠá.Ý())).HorizonCode_Horizon_È(BlockTripWireHook.ŠÂµà, true), 7, -3, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÈ.¥à().HorizonCode_Horizon_È(BlockTripWire.ŠÂµà, true), 7, -3, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÈ.¥à().HorizonCode_Horizon_È(BlockTripWire.ŠÂµà, true), 7, -3, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠÈ.¥à().HorizonCode_Horizon_È(BlockTripWire.ŠÂµà, true), 7, -3, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 8, -3, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 9, -3, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 9, -3, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 9, -3, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 9, -2, 4, p_74875_3_);
            if (!this.ÂµÈ) {
                this.ÂµÈ = this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 9, -2, 3, EnumFacing.Âµá€.Â(), Ý.ˆÏ­, 2);
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽà.Ý(15), 8, -1, 3, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽà.Ý(15), 8, -2, 3, p_74875_3_);
            if (!this.Ø) {
                this.Ø = this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 8, -3, 3, WeightedRandomChestContent.HorizonCode_Horizon_È(Ý.á, Items.Çªáˆºá.HorizonCode_Horizon_È(p_74875_2_)), 2 + p_74875_2_.nextInt(5));
            }
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 9, -3, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 8, -3, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 4, -3, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 5, -2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 5, -1, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 6, -3, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 7, -2, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 7, -1, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 8, -3, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 9, -1, 1, 9, -1, 5, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, -3, 8, 10, -1, 10);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.Ý(BlockStoneBrick.Âµà), 8, -2, 11, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.Ý(BlockStoneBrick.Âµà), 9, -2, 11, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.£áƒ.Ý(BlockStoneBrick.Âµà), 10, -2, 11, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇªÔ.Ý(BlockLever.HorizonCode_Horizon_È(EnumFacing.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(Blocks.ÇªÔ, EnumFacing.Ý.Â())))), 8, -2, 12, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇªÔ.Ý(BlockLever.HorizonCode_Horizon_È(EnumFacing.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(Blocks.ÇªÔ, EnumFacing.Ý.Â())))), 9, -2, 12, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇªÔ.Ý(BlockLever.HorizonCode_Horizon_È(EnumFacing.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(Blocks.ÇªÔ, EnumFacing.Ý.Â())))), 10, -2, 12, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 8, -3, 8, 8, -3, 10, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 10, -3, 8, 10, -3, 10, false, p_74875_2_, Ý.£á);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áˆºáˆºÈ.¥à(), 10, -2, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 8, -2, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 8, -2, 10, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Œ.¥à(), 10, -1, 9, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÕ.Ý(EnumFacing.Â.Â()), 9, -2, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÕ.Ý(this.HorizonCode_Horizon_È(Blocks.ÇŽÕ, EnumFacing.Âµá€.Â())), 10, -2, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇŽÕ.Ý(this.HorizonCode_Horizon_È(Blocks.ÇŽÕ, EnumFacing.Âµá€.Â())), 10, -1, 8, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.áŒŠá.Ý(this.HorizonCode_Horizon_È(Blocks.áŒŠá, EnumFacing.Ý.Ý())), 10, -2, 10, p_74875_3_);
            if (!this.áŒŠÆ) {
                this.áŒŠÆ = this.HorizonCode_Horizon_È(worldIn, p_74875_3_, p_74875_2_, 9, -3, 10, WeightedRandomChestContent.HorizonCode_Horizon_È(Ý.á, Items.Çªáˆºá.HorizonCode_Horizon_È(p_74875_2_)), 2 + p_74875_2_.nextInt(5));
            }
            return true;
        }
        
        static class HorizonCode_Horizon_È extends StructureComponent.HorizonCode_Horizon_È
        {
            private static final String Â = "CL_00000478";
            
            private HorizonCode_Horizon_È() {
            }
            
            @Override
            public void HorizonCode_Horizon_È(final Random p_75062_1_, final int p_75062_2_, final int p_75062_3_, final int p_75062_4_, final boolean p_75062_5_) {
                if (p_75062_1_.nextFloat() < 0.4f) {
                    this.HorizonCode_Horizon_È = Blocks.Ó.¥à();
                }
                else {
                    this.HorizonCode_Horizon_È = Blocks.áˆºáˆºÈ.¥à();
                }
            }
            
            HorizonCode_Horizon_È(final Âµá€ p_i45583_1_) {
                this();
            }
        }
    }
    
    public static class Ø­áŒŠá extends Â
    {
        private boolean Ø;
        private static final String áŒŠÆ = "CL_00000480";
        
        public Ø­áŒŠá() {
        }
        
        public Ø­áŒŠá(final Random p_i2066_1_, final int p_i2066_2_, final int p_i2066_3_) {
            super(p_i2066_1_, p_i2066_2_, 64, p_i2066_3_, 7, 5, 9);
        }
        
        @Override
        protected void HorizonCode_Horizon_È(final NBTTagCompound p_143012_1_) {
            super.HorizonCode_Horizon_È(p_143012_1_);
            p_143012_1_.HorizonCode_Horizon_È("Witch", this.Ø);
        }
        
        @Override
        protected void Â(final NBTTagCompound p_143011_1_) {
            super.Â(p_143011_1_);
            this.Ø = p_143011_1_.£á("Witch");
        }
        
        @Override
        public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_74875_2_, final StructureBoundingBox p_74875_3_) {
            if (!this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0)) {
                return false;
            }
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 1, 1, 5, 1, 7, Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 4, 2, 5, 4, 7, Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 1, 0, 4, 1, 0, Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 2, 2, 3, 3, 2, Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 2, 3, 1, 3, 6, Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 2, 3, 5, 3, 6, Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 2, 2, 7, 4, 3, 7, Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), Blocks.à.Ý(BlockPlanks.HorizonCode_Horizon_È.Â.Â()), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 2, 1, 3, 2, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 0, 2, 5, 3, 2, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 1, 0, 7, 1, 3, 7, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 5, 0, 7, 5, 3, 7, Blocks.¥Æ.¥à(), Blocks.¥Æ.¥à(), false);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 2, 3, 2, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 3, 3, 7, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 1, 3, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 5, 3, 4, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.Â.¥à(), 5, 3, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ŠáŒŠà¢.¥à().HorizonCode_Horizon_È(BlockFlowerPot.à¢, BlockFlowerPot.HorizonCode_Horizon_È.ˆà), 1, 3, 5, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ˆÉ.¥à(), 3, 2, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.ÇªáˆºÕ.¥à(), 4, 2, 6, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 1, 2, 1, p_74875_3_);
            this.HorizonCode_Horizon_È(worldIn, Blocks.¥É.¥à(), 5, 2, 1, p_74875_3_);
            final int var4 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 3);
            final int var5 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 1);
            final int var6 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 0);
            final int var7 = this.HorizonCode_Horizon_È(Blocks.áˆºÏ, 2);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 4, 1, 6, 4, 1, Blocks.£Ô.Ý(var4), Blocks.£Ô.Ý(var4), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 4, 2, 0, 4, 7, Blocks.£Ô.Ý(var6), Blocks.£Ô.Ý(var6), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 6, 4, 2, 6, 4, 7, Blocks.£Ô.Ý(var5), Blocks.£Ô.Ý(var5), false);
            this.HorizonCode_Horizon_È(worldIn, p_74875_3_, 0, 4, 8, 6, 4, 8, Blocks.£Ô.Ý(var7), Blocks.£Ô.Ý(var7), false);
            for (int var8 = 2; var8 <= 7; var8 += 5) {
                for (int var9 = 1; var9 <= 5; var9 += 4) {
                    this.Â(worldIn, Blocks.¥Æ.¥à(), var9, -1, var8, p_74875_3_);
                }
            }
            if (!this.Ø) {
                final int var8 = this.HorizonCode_Horizon_È(2, 5);
                final int var9 = this.HorizonCode_Horizon_È(2);
                final int var10 = this.Â(2, 5);
                if (p_74875_3_.HorizonCode_Horizon_È(new BlockPos(var8, var9, var10))) {
                    this.Ø = true;
                    final EntityWitch var11 = new EntityWitch(worldIn);
                    var11.Â(var8 + 0.5, var9, var10 + 0.5, 0.0f, 0.0f);
                    var11.HorizonCode_Horizon_È(worldIn.Ê(new BlockPos(var8, var9, var10)), null);
                    worldIn.HorizonCode_Horizon_È(var11);
                }
            }
            return true;
        }
    }
    
    static final class Âµá€
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00001971";
        
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
        }
    }
}
