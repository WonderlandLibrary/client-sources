package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class BlockPistonExtension extends Block
{
    public static final PropertyDirection Õ;
    public static final PropertyEnum à¢;
    public static final PropertyBool ŠÂµà;
    private static final String ¥à = "CL_00000367";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing");
        à¢ = PropertyEnum.HorizonCode_Horizon_È("type", HorizonCode_Horizon_È.class);
        ŠÂµà = PropertyBool.HorizonCode_Horizon_È("short");
    }
    
    public BlockPistonExtension() {
        super(Material.É);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockPistonExtension.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockPistonExtension.à¢, HorizonCode_Horizon_È.HorizonCode_Horizon_È).HorizonCode_Horizon_È(BlockPistonExtension.ŠÂµà, false));
        this.HorizonCode_Horizon_È(BlockPistonExtension.à);
        this.Ý(0.5f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn) {
        if (playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(BlockPistonExtension.Õ);
            if (var5 != null) {
                final BlockPos var6 = pos.HorizonCode_Horizon_È(var5.Âµá€());
                final Block var7 = worldIn.Â(var6).Ý();
                if (var7 == Blocks.Õ || var7 == Blocks.ÇŽÕ) {
                    worldIn.Ø(var6);
                }
            }
        }
        super.HorizonCode_Horizon_È(worldIn, pos, state, playerIn);
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, BlockPos pos, final IBlockState state) {
        super.Ø­áŒŠá(worldIn, pos, state);
        final EnumFacing var4 = ((EnumFacing)state.HorizonCode_Horizon_È(BlockPistonExtension.Õ)).Âµá€();
        pos = pos.HorizonCode_Horizon_È(var4);
        final IBlockState var5 = worldIn.Â(pos);
        if ((var5.Ý() == Blocks.Õ || var5.Ý() == Blocks.ÇŽÕ) && (boolean)var5.HorizonCode_Horizon_È(BlockPistonBase.à¢)) {
            var5.Ý().HorizonCode_Horizon_È(worldIn, pos, var5, 0);
            worldIn.Ø(pos);
        }
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing side) {
        return false;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        this.áŒŠÆ(state);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.áˆºÑ¢Õ(state);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void áˆºÑ¢Õ(final IBlockState p_176323_1_) {
        final float var2 = 0.25f;
        final float var3 = 0.375f;
        final float var4 = 0.625f;
        final float var5 = 0.25f;
        final float var6 = 0.75f;
        switch (Â.HorizonCode_Horizon_È[((EnumFacing)p_176323_1_.HorizonCode_Horizon_È(BlockPistonExtension.Õ)).ordinal()]) {
            case 1: {
                this.HorizonCode_Horizon_È(0.375f, 0.25f, 0.375f, 0.625f, 1.0f, 0.625f);
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(0.375f, 0.0f, 0.375f, 0.625f, 0.75f, 0.625f);
                break;
            }
            case 3: {
                this.HorizonCode_Horizon_È(0.25f, 0.375f, 0.25f, 0.75f, 0.625f, 1.0f);
                break;
            }
            case 4: {
                this.HorizonCode_Horizon_È(0.25f, 0.375f, 0.0f, 0.75f, 0.625f, 0.75f);
                break;
            }
            case 5: {
                this.HorizonCode_Horizon_È(0.375f, 0.25f, 0.25f, 0.625f, 0.75f, 1.0f);
                break;
            }
            case 6: {
                this.HorizonCode_Horizon_È(0.0f, 0.375f, 0.25f, 0.75f, 0.625f, 0.75f);
                break;
            }
        }
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.áŒŠÆ(access.Â(pos));
    }
    
    public void áŒŠÆ(final IBlockState p_176324_1_) {
        final float var2 = 0.25f;
        final EnumFacing var3 = (EnumFacing)p_176324_1_.HorizonCode_Horizon_È(BlockPistonExtension.Õ);
        if (var3 != null) {
            switch (Â.HorizonCode_Horizon_È[var3.ordinal()]) {
                case 1: {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                    break;
                }
                case 2: {
                    this.HorizonCode_Horizon_È(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 3: {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                    break;
                }
                case 4: {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                    break;
                }
                case 5: {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                    break;
                }
                case 6: {
                    this.HorizonCode_Horizon_È(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                    break;
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(BlockPistonExtension.Õ);
        final BlockPos var6 = pos.HorizonCode_Horizon_È(var5.Âµá€());
        final IBlockState var7 = worldIn.Â(var6);
        if (var7.Ý() != Blocks.Õ && var7.Ý() != Blocks.ÇŽÕ) {
            worldIn.Ø(pos);
        }
        else {
            var7.Ý().HorizonCode_Horizon_È(worldIn, var6, var7, neighborBlock);
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    public static EnumFacing Âµá€(final int p_176322_0_) {
        final int var1 = p_176322_0_ & 0x7;
        return (var1 > 5) ? null : EnumFacing.HorizonCode_Horizon_È(var1);
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return (worldIn.Â(pos).HorizonCode_Horizon_È(BlockPistonExtension.à¢) == HorizonCode_Horizon_È.Â) ? Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇŽÕ) : Item_1028566121.HorizonCode_Horizon_È(Blocks.Õ);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockPistonExtension.Õ, Âµá€(meta)).HorizonCode_Horizon_È(BlockPistonExtension.à¢, ((meta & 0x8) > 0) ? HorizonCode_Horizon_È.Â : HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockPistonExtension.Õ)).Â();
        if (state.HorizonCode_Horizon_È(BlockPistonExtension.à¢) == HorizonCode_Horizon_È.Â) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockPistonExtension.Õ, BlockPistonExtension.à¢, BlockPistonExtension.ŠÂµà });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("DEFAULT", 0, "DEFAULT", 0, "normal"), 
        Â("STICKY", 1, "STICKY", 1, "sticky");
        
        private final String Ý;
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002035";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45666_1_, final int p_i45666_2_, final String p_i45666_3_) {
            this.Ý = p_i45666_3_;
        }
        
        @Override
        public String toString() {
            return this.Ý;
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Ý;
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002036";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockPistonExtension.Â.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockPistonExtension.Â.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockPistonExtension.Â.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockPistonExtension.Â.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockPistonExtension.Â.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BlockPistonExtension.Â.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
