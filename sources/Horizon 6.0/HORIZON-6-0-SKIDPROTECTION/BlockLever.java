package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public class BlockLever extends Block
{
    public static final PropertyEnum Õ;
    public static final PropertyBool à¢;
    private static final String ŠÂµà = "CL_00000264";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("facing", HorizonCode_Horizon_È.class);
        à¢ = PropertyBool.HorizonCode_Horizon_È("powered");
    }
    
    protected BlockLever() {
        super(Material.µà);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockLever.Õ, HorizonCode_Horizon_È.Âµá€).HorizonCode_Horizon_È(BlockLever.à¢, false));
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
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
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing side) {
        return (side == EnumFacing.Â && World.HorizonCode_Horizon_È(worldIn, pos.Âµá€())) || this.áŒŠÆ(worldIn, pos.HorizonCode_Horizon_È(side.Âµá€()));
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        return this.áŒŠÆ(worldIn, pos.Ø()) || this.áŒŠÆ(worldIn, pos.áŒŠÆ()) || this.áŒŠÆ(worldIn, pos.Ó()) || this.áŒŠÆ(worldIn, pos.à()) || World.HorizonCode_Horizon_È(worldIn, pos.Âµá€()) || this.áŒŠÆ(worldIn, pos.Ø­áŒŠá());
    }
    
    protected boolean áŒŠÆ(final World worldIn, final BlockPos p_176358_2_) {
        return worldIn.Â(p_176358_2_).Ý().Ø();
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        final IBlockState var9 = this.¥à().HorizonCode_Horizon_È(BlockLever.à¢, false);
        if (this.áŒŠÆ(worldIn, pos.HorizonCode_Horizon_È(facing.Âµá€()))) {
            return var9.HorizonCode_Horizon_È(BlockLever.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(facing, placer.ˆà¢()));
        }
        for (final EnumFacing var11 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            if (var11 != facing && this.áŒŠÆ(worldIn, pos.HorizonCode_Horizon_È(var11.Âµá€()))) {
                return var9.HorizonCode_Horizon_È(BlockLever.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(var11, placer.ˆà¢()));
            }
        }
        if (World.HorizonCode_Horizon_È(worldIn, pos.Âµá€())) {
            return var9.HorizonCode_Horizon_È(BlockLever.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(EnumFacing.Â, placer.ˆà¢()));
        }
        return var9;
    }
    
    public static int HorizonCode_Horizon_È(final EnumFacing p_176357_0_) {
        switch (Â.HorizonCode_Horizon_È[p_176357_0_.ordinal()]) {
            case 1: {
                return 0;
            }
            case 2: {
                return 5;
            }
            case 3: {
                return 4;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return 2;
            }
            case 6: {
                return 1;
            }
            default: {
                return -1;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (this.áˆºÑ¢Õ(worldIn, pos) && !this.áŒŠÆ(worldIn, pos.HorizonCode_Horizon_È(((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockLever.Õ)).Ý().Âµá€()))) {
            this.HorizonCode_Horizon_È(worldIn, pos, state, 0);
            worldIn.Ø(pos);
        }
    }
    
    private boolean áˆºÑ¢Õ(final World worldIn, final BlockPos p_176356_2_) {
        if (this.Ø­áŒŠá(worldIn, p_176356_2_)) {
            return true;
        }
        this.HorizonCode_Horizon_È(worldIn, p_176356_2_, worldIn.Â(p_176356_2_), 0);
        worldIn.Ø(p_176356_2_);
        return false;
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        float var3 = 0.1875f;
        switch (Â.Â[((HorizonCode_Horizon_È)access.Â(pos).HorizonCode_Horizon_È(BlockLever.Õ)).ordinal()]) {
            case 1: {
                this.HorizonCode_Horizon_È(0.0f, 0.2f, 0.5f - var3, var3 * 2.0f, 0.8f, 0.5f + var3);
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(1.0f - var3 * 2.0f, 0.2f, 0.5f - var3, 1.0f, 0.8f, 0.5f + var3);
                break;
            }
            case 3: {
                this.HorizonCode_Horizon_È(0.5f - var3, 0.2f, 0.0f, 0.5f + var3, 0.8f, var3 * 2.0f);
                break;
            }
            case 4: {
                this.HorizonCode_Horizon_È(0.5f - var3, 0.2f, 1.0f - var3 * 2.0f, 0.5f + var3, 0.8f, 1.0f);
                break;
            }
            case 5:
            case 6: {
                var3 = 0.25f;
                this.HorizonCode_Horizon_È(0.5f - var3, 0.0f, 0.5f - var3, 0.5f + var3, 0.6f, 0.5f + var3);
                break;
            }
            case 7:
            case 8: {
                var3 = 0.25f;
                this.HorizonCode_Horizon_È(0.5f - var3, 0.4f, 0.5f - var3, 0.5f + var3, 1.0f, 0.5f + var3);
                break;
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        state = state.Â(BlockLever.à¢);
        worldIn.HorizonCode_Horizon_È(pos, state, 3);
        worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, "random.click", 0.3f, ((boolean)state.HorizonCode_Horizon_È(BlockLever.à¢)) ? 0.6f : 0.5f);
        worldIn.Â(pos, this);
        final EnumFacing var9 = ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockLever.Õ)).Ý();
        worldIn.Â(pos.HorizonCode_Horizon_È(var9.Âµá€()), this);
        return true;
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (state.HorizonCode_Horizon_È(BlockLever.à¢)) {
            worldIn.Â(pos, this);
            final EnumFacing var4 = ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockLever.Õ)).Ý();
            worldIn.Â(pos.HorizonCode_Horizon_È(var4.Âµá€()), this);
        }
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return state.HorizonCode_Horizon_È(BlockLever.à¢) ? 15 : 0;
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return state.HorizonCode_Horizon_È(BlockLever.à¢) ? ((((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockLever.Õ)).Ý() == side) ? 15 : 0) : 0;
    }
    
    @Override
    public boolean áŒŠà() {
        return true;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockLever.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta & 0x7)).HorizonCode_Horizon_È(BlockLever.à¢, (meta & 0x8) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockLever.Õ)).Â();
        if (state.HorizonCode_Horizon_È(BlockLever.à¢)) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockLever.Õ, BlockLever.à¢ });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("DOWN_X", 0, "DOWN_X", 0, 0, "down_x", EnumFacing.HorizonCode_Horizon_È), 
        Â("EAST", 1, "EAST", 1, 1, "east", EnumFacing.Ó), 
        Ý("WEST", 2, "WEST", 2, 2, "west", EnumFacing.Âµá€), 
        Ø­áŒŠá("SOUTH", 3, "SOUTH", 3, 3, "south", EnumFacing.Ø­áŒŠá), 
        Âµá€("NORTH", 4, "NORTH", 4, 4, "north", EnumFacing.Ý), 
        Ó("UP_Z", 5, "UP_Z", 5, 5, "up_z", EnumFacing.Â), 
        à("UP_X", 6, "UP_X", 6, 6, "up_x", EnumFacing.Â), 
        Ø("DOWN_Z", 7, "DOWN_Z", 7, 7, "down_z", EnumFacing.HorizonCode_Horizon_È);
        
        private static final HorizonCode_Horizon_È[] áŒŠÆ;
        private final int áˆºÑ¢Õ;
        private final String ÂµÈ;
        private final EnumFacing á;
        private static final HorizonCode_Horizon_È[] ˆÏ­;
        private static final String £á = "CL_00002102";
        
        static {
            Å = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø };
            áŒŠÆ = new HorizonCode_Horizon_È[values().length];
            ˆÏ­ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â, HorizonCode_Horizon_È.Ý, HorizonCode_Horizon_È.Ø­áŒŠá, HorizonCode_Horizon_È.Âµá€, HorizonCode_Horizon_È.Ó, HorizonCode_Horizon_È.à, HorizonCode_Horizon_È.Ø };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.áŒŠÆ[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45709_1_, final int p_i45709_2_, final int p_i45709_3_, final String p_i45709_4_, final EnumFacing p_i45709_5_) {
            this.áˆºÑ¢Õ = p_i45709_3_;
            this.ÂµÈ = p_i45709_4_;
            this.á = p_i45709_5_;
        }
        
        public int Â() {
            return this.áˆºÑ¢Õ;
        }
        
        public EnumFacing Ý() {
            return this.á;
        }
        
        @Override
        public String toString() {
            return this.ÂµÈ;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176853_0_) {
            if (p_176853_0_ < 0 || p_176853_0_ >= HorizonCode_Horizon_È.áŒŠÆ.length) {
                p_176853_0_ = 0;
            }
            return HorizonCode_Horizon_È.áŒŠÆ[p_176853_0_];
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(final EnumFacing p_176856_0_, final EnumFacing p_176856_1_) {
            switch (BlockLever.Â.HorizonCode_Horizon_È[p_176856_0_.ordinal()]) {
                case 1: {
                    switch (BlockLever.Â.Ý[p_176856_1_.á().ordinal()]) {
                        case 1: {
                            return HorizonCode_Horizon_È.HorizonCode_Horizon_È;
                        }
                        case 2: {
                            return HorizonCode_Horizon_È.Ø;
                        }
                        default: {
                            throw new IllegalArgumentException("Invalid entityFacing " + p_176856_1_ + " for facing " + p_176856_0_);
                        }
                    }
                    break;
                }
                case 2: {
                    switch (BlockLever.Â.Ý[p_176856_1_.á().ordinal()]) {
                        case 1: {
                            return HorizonCode_Horizon_È.à;
                        }
                        case 2: {
                            return HorizonCode_Horizon_È.Ó;
                        }
                        default: {
                            throw new IllegalArgumentException("Invalid entityFacing " + p_176856_1_ + " for facing " + p_176856_0_);
                        }
                    }
                    break;
                }
                case 3: {
                    return HorizonCode_Horizon_È.Âµá€;
                }
                case 4: {
                    return HorizonCode_Horizon_È.Ø­áŒŠá;
                }
                case 5: {
                    return HorizonCode_Horizon_È.Ý;
                }
                case 6: {
                    return HorizonCode_Horizon_È.Â;
                }
                default: {
                    throw new IllegalArgumentException("Invalid facing: " + p_176856_0_);
                }
            }
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.ÂµÈ;
        }
    }
    
    static final class Â
    {
        static final int[] HorizonCode_Horizon_È;
        static final int[] Â;
        static final int[] Ý;
        private static final String Ø­áŒŠá = "CL_00002103";
        
        static {
            Ý = new int[EnumFacing.HorizonCode_Horizon_È.values().length];
            try {
                BlockLever.Â.Ý[EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockLever.Â.Ý[EnumFacing.HorizonCode_Horizon_È.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            Â = new int[BlockLever.HorizonCode_Horizon_È.values().length];
            try {
                BlockLever.Â.Â[BlockLever.HorizonCode_Horizon_È.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockLever.Â.Â[BlockLever.HorizonCode_Horizon_È.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockLever.Â.Â[BlockLever.HorizonCode_Horizon_È.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BlockLever.Â.Â[BlockLever.HorizonCode_Horizon_È.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                BlockLever.Â.Â[BlockLever.HorizonCode_Horizon_È.Ó.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
            try {
                BlockLever.Â.Â[BlockLever.HorizonCode_Horizon_È.à.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError8) {}
            try {
                BlockLever.Â.Â[BlockLever.HorizonCode_Horizon_È.HorizonCode_Horizon_È.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError9) {}
            try {
                BlockLever.Â.Â[BlockLever.HorizonCode_Horizon_È.Ø.ordinal()] = 8;
            }
            catch (NoSuchFieldError noSuchFieldError10) {}
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockLever.Â.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError11) {}
            try {
                BlockLever.Â.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError12) {}
            try {
                BlockLever.Â.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError13) {}
            try {
                BlockLever.Â.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError14) {}
            try {
                BlockLever.Â.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError15) {}
            try {
                BlockLever.Â.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError16) {}
        }
    }
}
