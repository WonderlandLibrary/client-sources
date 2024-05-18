package HORIZON-6-0-SKIDPROTECTION;

import com.google.common.base.Predicate;

public class BlockTrapDoor extends Block
{
    public static final PropertyDirection Õ;
    public static final PropertyBool à¢;
    public static final PropertyEnum ŠÂµà;
    private static final String ¥à = "CL_00000327";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
        à¢ = PropertyBool.HorizonCode_Horizon_È("open");
        ŠÂµà = PropertyEnum.HorizonCode_Horizon_È("half", HorizonCode_Horizon_È.class);
    }
    
    protected BlockTrapDoor(final Material p_i45434_1_) {
        super(p_i45434_1_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockTrapDoor.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockTrapDoor.à¢, false).HorizonCode_Horizon_È(BlockTrapDoor.ŠÂµà, HorizonCode_Horizon_È.Â));
        final float var2 = 0.5f;
        final float var3 = 1.0f;
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
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
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos pos) {
        return !(boolean)blockAccess.Â(pos).HorizonCode_Horizon_È(BlockTrapDoor.à¢);
    }
    
    @Override
    public AxisAlignedBB Ý(final World worldIn, final BlockPos pos) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.Ý(worldIn, pos);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.HorizonCode_Horizon_È(worldIn, pos, state);
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        this.áŒŠÆ(access.Â(pos));
    }
    
    @Override
    public void ŠÄ() {
        final float var1 = 0.1875f;
        this.HorizonCode_Horizon_È(0.0f, 0.40625f, 0.0f, 1.0f, 0.59375f, 1.0f);
    }
    
    public void áŒŠÆ(final IBlockState p_180693_1_) {
        if (p_180693_1_.Ý() == this) {
            final boolean var2 = p_180693_1_.HorizonCode_Horizon_È(BlockTrapDoor.ŠÂµà) == HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            final Boolean var3 = (Boolean)p_180693_1_.HorizonCode_Horizon_È(BlockTrapDoor.à¢);
            final EnumFacing var4 = (EnumFacing)p_180693_1_.HorizonCode_Horizon_È(BlockTrapDoor.Õ);
            final float var5 = 0.1875f;
            if (var2) {
                this.HorizonCode_Horizon_È(0.0f, 0.8125f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            else {
                this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.1875f, 1.0f);
            }
            if (var3) {
                if (var4 == EnumFacing.Ý) {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.8125f, 1.0f, 1.0f, 1.0f);
                }
                if (var4 == EnumFacing.Ø­áŒŠá) {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.1875f);
                }
                if (var4 == EnumFacing.Âµá€) {
                    this.HorizonCode_Horizon_È(0.8125f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                if (var4 == EnumFacing.Ó) {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 0.1875f, 1.0f, 1.0f);
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (this.É == Material.Ó) {
            return true;
        }
        state = state.Â(BlockTrapDoor.à¢);
        worldIn.HorizonCode_Horizon_È(pos, state, 2);
        worldIn.HorizonCode_Horizon_È(playerIn, ((boolean)state.HorizonCode_Horizon_È(BlockTrapDoor.à¢)) ? 1003 : 1006, pos, 0);
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.ŠÄ) {
            final BlockPos var5 = pos.HorizonCode_Horizon_È(((EnumFacing)state.HorizonCode_Horizon_È(BlockTrapDoor.Õ)).Âµá€());
            if (!Ý(worldIn.Â(var5).Ý())) {
                worldIn.Ø(pos);
                this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            }
            else {
                final boolean var6 = worldIn.áŒŠà(pos);
                if (var6 || neighborBlock.áŒŠà()) {
                    final boolean var7 = (boolean)state.HorizonCode_Horizon_È(BlockTrapDoor.à¢);
                    if (var7 != var6) {
                        worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockTrapDoor.à¢, var6), 2);
                        worldIn.HorizonCode_Horizon_È(null, var6 ? 1003 : 1006, pos, 0);
                    }
                }
            }
        }
    }
    
    @Override
    public MovingObjectPosition HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Vec3 start, final Vec3 end) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.HorizonCode_Horizon_È(worldIn, pos, start, end);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        IBlockState var9 = this.¥à();
        if (facing.á().Ø­áŒŠá()) {
            var9 = var9.HorizonCode_Horizon_È(BlockTrapDoor.Õ, facing).HorizonCode_Horizon_È(BlockTrapDoor.à¢, false);
            var9 = var9.HorizonCode_Horizon_È(BlockTrapDoor.ŠÂµà, (hitY > 0.5f) ? HorizonCode_Horizon_È.HorizonCode_Horizon_È : HorizonCode_Horizon_È.Â);
        }
        return var9;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing side) {
        return !side.á().Ý() && Ý(worldIn.Â(pos.HorizonCode_Horizon_È(side.Âµá€())).Ý());
    }
    
    protected static EnumFacing Âµá€(final int p_176281_0_) {
        switch (p_176281_0_ & 0x3) {
            case 0: {
                return EnumFacing.Ý;
            }
            case 1: {
                return EnumFacing.Ø­áŒŠá;
            }
            case 2: {
                return EnumFacing.Âµá€;
            }
            default: {
                return EnumFacing.Ó;
            }
        }
    }
    
    protected static int HorizonCode_Horizon_È(final EnumFacing p_176282_0_) {
        switch (Â.HorizonCode_Horizon_È[p_176282_0_.ordinal()]) {
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: {
                return 2;
            }
            default: {
                return 3;
            }
        }
    }
    
    private static boolean Ý(final Block p_150119_0_) {
        return (p_150119_0_.É.áˆºÑ¢Õ() && p_150119_0_.áˆºÑ¢Õ()) || p_150119_0_ == Blocks.£Ø­à || p_150119_0_ instanceof BlockSlab || p_150119_0_ instanceof BlockStairs;
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockTrapDoor.Õ, Âµá€(meta)).HorizonCode_Horizon_È(BlockTrapDoor.à¢, (meta & 0x4) != 0x0).HorizonCode_Horizon_È(BlockTrapDoor.ŠÂµà, ((meta & 0x8) == 0x0) ? HorizonCode_Horizon_È.Â : HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | HorizonCode_Horizon_È((EnumFacing)state.HorizonCode_Horizon_È(BlockTrapDoor.Õ));
        if (state.HorizonCode_Horizon_È(BlockTrapDoor.à¢)) {
            var3 |= 0x4;
        }
        if (state.HorizonCode_Horizon_È(BlockTrapDoor.ŠÂµà) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockTrapDoor.Õ, BlockTrapDoor.à¢, BlockTrapDoor.ŠÂµà });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("TOP", 0, "TOP", 0, "top"), 
        Â("BOTTOM", 1, "BOTTOM", 1, "bottom");
        
        private final String Ý;
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002051";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45674_1_, final int p_i45674_2_, final String p_i45674_3_) {
            this.Ý = p_i45674_3_;
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
        private static final String Â = "CL_00002052";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockTrapDoor.Â.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockTrapDoor.Â.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockTrapDoor.Â.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockTrapDoor.Â.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
