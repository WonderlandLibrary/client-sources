package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class BlockSapling extends BlockBush implements IGrowable
{
    public static final PropertyEnum Õ;
    public static final PropertyInteger à¢;
    private static final String ŠÂµà = "CL_00000305";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("type", BlockPlanks.HorizonCode_Horizon_È.class);
        à¢ = PropertyInteger.HorizonCode_Horizon_È("stage", 0, 1);
    }
    
    protected BlockSapling() {
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockSapling.Õ, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockSapling.à¢, 0));
        final float var1 = 0.4f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, var1 * 2.0f, 0.5f + var1);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ) {
            super.Â(worldIn, pos, state, rand);
            if (worldIn.ˆÏ­(pos.Ø­áŒŠá()) >= 9 && rand.nextInt(7) == 0) {
                this.Ø­áŒŠá(worldIn, pos, state, rand);
            }
        }
    }
    
    public void Ø­áŒŠá(final World worldIn, final BlockPos p_176478_2_, final IBlockState p_176478_3_, final Random p_176478_4_) {
        if ((int)p_176478_3_.HorizonCode_Horizon_È(BlockSapling.à¢) == 0) {
            worldIn.HorizonCode_Horizon_È(p_176478_2_, p_176478_3_.Â(BlockSapling.à¢), 4);
        }
        else {
            this.Âµá€(worldIn, p_176478_2_, p_176478_3_, p_176478_4_);
        }
    }
    
    public void Âµá€(final World worldIn, final BlockPos p_176476_2_, final IBlockState p_176476_3_, final Random p_176476_4_) {
        Object var5 = (p_176476_4_.nextInt(10) == 0) ? new WorldGenBigTree(true) : new WorldGenTrees(true);
        int var6 = 0;
        int var7 = 0;
        boolean var8 = false;
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[((BlockPlanks.HorizonCode_Horizon_È)p_176476_3_.HorizonCode_Horizon_È(BlockSapling.Õ)).ordinal()]) {
            case 1: {
            Label_0235:
                for (var6 = 0; var6 >= -1; --var6) {
                    for (var7 = 0; var7 >= -1; --var7) {
                        if (this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6, 0, var7), BlockPlanks.HorizonCode_Horizon_È.Â) && this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6 + 1, 0, var7), BlockPlanks.HorizonCode_Horizon_È.Â) && this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6, 0, var7 + 1), BlockPlanks.HorizonCode_Horizon_È.Â) && this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6 + 1, 0, var7 + 1), BlockPlanks.HorizonCode_Horizon_È.Â)) {
                            var5 = new WorldGenMegaPineTree(false, p_176476_4_.nextBoolean());
                            var8 = true;
                            break Label_0235;
                        }
                    }
                }
                if (!var8) {
                    var7 = 0;
                    var6 = 0;
                    var5 = new WorldGenTaiga2(true);
                    break;
                }
                break;
            }
            case 2: {
                var5 = new WorldGenForest(true, false);
                break;
            }
            case 3: {
            Label_0423:
                for (var6 = 0; var6 >= -1; --var6) {
                    for (var7 = 0; var7 >= -1; --var7) {
                        if (this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6, 0, var7), BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá) && this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6 + 1, 0, var7), BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá) && this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6, 0, var7 + 1), BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá) && this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6 + 1, 0, var7 + 1), BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá)) {
                            var5 = new WorldGenMegaJungle(true, 10, 20, BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â());
                            var8 = true;
                            break Label_0423;
                        }
                    }
                }
                if (!var8) {
                    var7 = 0;
                    var6 = 0;
                    var5 = new WorldGenTrees(true, 4 + p_176476_4_.nextInt(7), BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.Â(), false);
                    break;
                }
                break;
            }
            case 4: {
                var5 = new WorldGenSavannaTree(true);
                break;
            }
            case 5: {
            Label_0616:
                for (var6 = 0; var6 >= -1; --var6) {
                    for (var7 = 0; var7 >= -1; --var7) {
                        if (this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6, 0, var7), BlockPlanks.HorizonCode_Horizon_È.Ó) && this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6 + 1, 0, var7), BlockPlanks.HorizonCode_Horizon_È.Ó) && this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6, 0, var7 + 1), BlockPlanks.HorizonCode_Horizon_È.Ó) && this.HorizonCode_Horizon_È(worldIn, p_176476_2_.Â(var6 + 1, 0, var7 + 1), BlockPlanks.HorizonCode_Horizon_È.Ó)) {
                            var5 = new WorldGenCanopyTree(true);
                            var8 = true;
                            break Label_0616;
                        }
                    }
                }
                if (!var8) {
                    return;
                }
                break;
            }
        }
        final IBlockState var9 = Blocks.Â.¥à();
        if (var8) {
            worldIn.HorizonCode_Horizon_È(p_176476_2_.Â(var6, 0, var7), var9, 4);
            worldIn.HorizonCode_Horizon_È(p_176476_2_.Â(var6 + 1, 0, var7), var9, 4);
            worldIn.HorizonCode_Horizon_È(p_176476_2_.Â(var6, 0, var7 + 1), var9, 4);
            worldIn.HorizonCode_Horizon_È(p_176476_2_.Â(var6 + 1, 0, var7 + 1), var9, 4);
        }
        else {
            worldIn.HorizonCode_Horizon_È(p_176476_2_, var9, 4);
        }
        if (!((WorldGenerator)var5).HorizonCode_Horizon_È(worldIn, p_176476_4_, p_176476_2_.Â(var6, 0, var7))) {
            if (var8) {
                worldIn.HorizonCode_Horizon_È(p_176476_2_.Â(var6, 0, var7), p_176476_3_, 4);
                worldIn.HorizonCode_Horizon_È(p_176476_2_.Â(var6 + 1, 0, var7), p_176476_3_, 4);
                worldIn.HorizonCode_Horizon_È(p_176476_2_.Â(var6, 0, var7 + 1), p_176476_3_, 4);
                worldIn.HorizonCode_Horizon_È(p_176476_2_.Â(var6 + 1, 0, var7 + 1), p_176476_3_, 4);
            }
            else {
                worldIn.HorizonCode_Horizon_È(p_176476_2_, p_176476_3_, 4);
            }
        }
    }
    
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176477_2_, final BlockPlanks.HorizonCode_Horizon_È p_176477_3_) {
        final IBlockState var4 = worldIn.Â(p_176477_2_);
        return var4.Ý() == this && var4.HorizonCode_Horizon_È(BlockSapling.Õ) == p_176477_3_;
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockSapling.Õ)).Â();
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final BlockPlanks.HorizonCode_Horizon_È var7 : BlockPlanks.HorizonCode_Horizon_È.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_, final boolean p_176473_4_) {
        return true;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_, final IBlockState p_180670_4_) {
        return worldIn.Å.nextFloat() < 0.45;
    }
    
    @Override
    public void Â(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_, final IBlockState p_176474_4_) {
        this.Ø­áŒŠá(worldIn, p_176474_3_, p_176474_4_, p_176474_2_);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockSapling.Õ, BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta & 0x7)).HorizonCode_Horizon_È(BlockSapling.à¢, (meta & 0x8) >> 3);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((BlockPlanks.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockSapling.Õ)).Â();
        var3 |= (int)state.HorizonCode_Horizon_È(BlockSapling.à¢) << 3;
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockSapling.Õ, BlockSapling.à¢ });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002067";
        
        static {
            HorizonCode_Horizon_È = new int[BlockPlanks.HorizonCode_Horizon_È.values().length];
            try {
                BlockSapling.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockSapling.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockSapling.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockSapling.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockSapling.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.Ó.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BlockSapling.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockPlanks.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
