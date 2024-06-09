package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class BlockRailPowered extends BlockRailBase
{
    public static final PropertyEnum Õ;
    public static final PropertyBool ŠÂµà;
    private static final String ¥à = "CL_00000288";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("shape", BlockRailBase.HorizonCode_Horizon_È.class, (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002080";
            
            public boolean HorizonCode_Horizon_È(final BlockRailBase.HorizonCode_Horizon_È p_180133_1_) {
                return p_180133_1_ != BlockRailBase.HorizonCode_Horizon_È.áˆºÑ¢Õ && p_180133_1_ != BlockRailBase.HorizonCode_Horizon_È.áŒŠÆ && p_180133_1_ != BlockRailBase.HorizonCode_Horizon_È.à && p_180133_1_ != BlockRailBase.HorizonCode_Horizon_È.Ø;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((BlockRailBase.HorizonCode_Horizon_È)p_apply_1_);
            }
        });
        ŠÂµà = PropertyBool.HorizonCode_Horizon_È("powered");
    }
    
    protected BlockRailPowered() {
        super(true);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockRailPowered.Õ, BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockRailPowered.ŠÂµà, false));
    }
    
    protected boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176566_2_, final IBlockState p_176566_3_, final boolean p_176566_4_, final int p_176566_5_) {
        if (p_176566_5_ >= 8) {
            return false;
        }
        int var6 = p_176566_2_.HorizonCode_Horizon_È();
        int var7 = p_176566_2_.Â();
        int var8 = p_176566_2_.Ý();
        boolean var9 = true;
        BlockRailBase.HorizonCode_Horizon_È var10 = (BlockRailBase.HorizonCode_Horizon_È)p_176566_3_.HorizonCode_Horizon_È(BlockRailPowered.Õ);
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[var10.ordinal()]) {
            case 1: {
                if (p_176566_4_) {
                    ++var8;
                    break;
                }
                --var8;
                break;
            }
            case 2: {
                if (p_176566_4_) {
                    --var6;
                    break;
                }
                ++var6;
                break;
            }
            case 3: {
                if (p_176566_4_) {
                    --var6;
                }
                else {
                    ++var6;
                    ++var7;
                    var9 = false;
                }
                var10 = BlockRailBase.HorizonCode_Horizon_È.Â;
                break;
            }
            case 4: {
                if (p_176566_4_) {
                    --var6;
                    ++var7;
                    var9 = false;
                }
                else {
                    ++var6;
                }
                var10 = BlockRailBase.HorizonCode_Horizon_È.Â;
                break;
            }
            case 5: {
                if (p_176566_4_) {
                    ++var8;
                }
                else {
                    --var8;
                    ++var7;
                    var9 = false;
                }
                var10 = BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                break;
            }
            case 6: {
                if (p_176566_4_) {
                    ++var8;
                    ++var7;
                    var9 = false;
                }
                else {
                    --var8;
                }
                var10 = BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                break;
            }
        }
        return this.HorizonCode_Horizon_È(worldIn, new BlockPos(var6, var7, var8), p_176566_4_, p_176566_5_, var10) || (var9 && this.HorizonCode_Horizon_È(worldIn, new BlockPos(var6, var7 - 1, var8), p_176566_4_, p_176566_5_, var10));
    }
    
    protected boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176567_2_, final boolean p_176567_3_, final int p_176567_4_, final BlockRailBase.HorizonCode_Horizon_È p_176567_5_) {
        final IBlockState var6 = worldIn.Â(p_176567_2_);
        if (var6.Ý() != this) {
            return false;
        }
        final BlockRailBase.HorizonCode_Horizon_È var7 = (BlockRailBase.HorizonCode_Horizon_È)var6.HorizonCode_Horizon_È(BlockRailPowered.Õ);
        return (p_176567_5_ != BlockRailBase.HorizonCode_Horizon_È.Â || (var7 != BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È && var7 != BlockRailBase.HorizonCode_Horizon_È.Âµá€ && var7 != BlockRailBase.HorizonCode_Horizon_È.Ó)) && (p_176567_5_ != BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È || (var7 != BlockRailBase.HorizonCode_Horizon_È.Â && var7 != BlockRailBase.HorizonCode_Horizon_È.Ý && var7 != BlockRailBase.HorizonCode_Horizon_È.Ø­áŒŠá)) && (boolean)var6.HorizonCode_Horizon_È(BlockRailPowered.ŠÂµà) && (worldIn.áŒŠà(p_176567_2_) || this.HorizonCode_Horizon_È(worldIn, p_176567_2_, var6, p_176567_3_, p_176567_4_ + 1));
    }
    
    @Override
    protected void Â(final World worldIn, final BlockPos p_176561_2_, final IBlockState p_176561_3_, final Block p_176561_4_) {
        final boolean var5 = (boolean)p_176561_3_.HorizonCode_Horizon_È(BlockRailPowered.ŠÂµà);
        final boolean var6 = worldIn.áŒŠà(p_176561_2_) || this.HorizonCode_Horizon_È(worldIn, p_176561_2_, p_176561_3_, true, 0) || this.HorizonCode_Horizon_È(worldIn, p_176561_2_, p_176561_3_, false, 0);
        if (var6 != var5) {
            worldIn.HorizonCode_Horizon_È(p_176561_2_, p_176561_3_.HorizonCode_Horizon_È(BlockRailPowered.ŠÂµà, var6), 3);
            worldIn.Â(p_176561_2_.Âµá€(), this);
            if (((BlockRailBase.HorizonCode_Horizon_È)p_176561_3_.HorizonCode_Horizon_È(BlockRailPowered.Õ)).Ý()) {
                worldIn.Â(p_176561_2_.Ø­áŒŠá(), this);
            }
        }
    }
    
    @Override
    public IProperty È() {
        return BlockRailPowered.Õ;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockRailPowered.Õ, BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta & 0x7)).HorizonCode_Horizon_È(BlockRailPowered.ŠÂµà, (meta & 0x8) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((BlockRailBase.HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockRailPowered.Õ)).Â();
        if (state.HorizonCode_Horizon_È(BlockRailPowered.ŠÂµà)) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockRailPowered.Õ, BlockRailPowered.ŠÂµà });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002079";
        
        static {
            HorizonCode_Horizon_È = new int[BlockRailBase.HorizonCode_Horizon_È.values().length];
            try {
                BlockRailPowered.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockRailBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockRailPowered.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockRailBase.HorizonCode_Horizon_È.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockRailPowered.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockRailBase.HorizonCode_Horizon_È.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockRailPowered.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockRailBase.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockRailPowered.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockRailBase.HorizonCode_Horizon_È.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BlockRailPowered.HorizonCode_Horizon_È.HorizonCode_Horizon_È[BlockRailBase.HorizonCode_Horizon_È.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
