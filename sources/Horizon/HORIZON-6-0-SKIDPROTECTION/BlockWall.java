package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockWall extends Block
{
    public static final PropertyBool Õ;
    public static final PropertyBool à¢;
    public static final PropertyBool ŠÂµà;
    public static final PropertyBool ¥à;
    public static final PropertyBool Âµà;
    public static final PropertyEnum Ç;
    private static final String È = "CL_00000331";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("up");
        à¢ = PropertyBool.HorizonCode_Horizon_È("north");
        ŠÂµà = PropertyBool.HorizonCode_Horizon_È("east");
        ¥à = PropertyBool.HorizonCode_Horizon_È("south");
        Âµà = PropertyBool.HorizonCode_Horizon_È("west");
        Ç = PropertyEnum.HorizonCode_Horizon_È("variant", HorizonCode_Horizon_È.class);
    }
    
    public BlockWall(final Block p_i45435_1_) {
        super(p_i45435_1_.É);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockWall.Õ, false).HorizonCode_Horizon_È(BlockWall.à¢, false).HorizonCode_Horizon_È(BlockWall.ŠÂµà, false).HorizonCode_Horizon_È(BlockWall.¥à, false).HorizonCode_Horizon_È(BlockWall.Âµà, false).HorizonCode_Horizon_È(BlockWall.Ç, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.Ý(p_i45435_1_.µÕ);
        this.Â(p_i45435_1_.Æ / 3.0f);
        this.HorizonCode_Horizon_È(p_i45435_1_.ˆá);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess blockAccess, final BlockPos pos) {
        return false;
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final boolean var3 = this.Âµá€(access, pos.Ó());
        final boolean var4 = this.Âµá€(access, pos.à());
        final boolean var5 = this.Âµá€(access, pos.Ø());
        final boolean var6 = this.Âµá€(access, pos.áŒŠÆ());
        float var7 = 0.25f;
        float var8 = 0.75f;
        float var9 = 0.25f;
        float var10 = 0.75f;
        float var11 = 1.0f;
        if (var3) {
            var9 = 0.0f;
        }
        if (var4) {
            var10 = 1.0f;
        }
        if (var5) {
            var7 = 0.0f;
        }
        if (var6) {
            var8 = 1.0f;
        }
        if (var3 && var4 && !var5 && !var6) {
            var11 = 0.8125f;
            var7 = 0.3125f;
            var8 = 0.6875f;
        }
        else if (!var3 && !var4 && var5 && var6) {
            var11 = 0.8125f;
            var9 = 0.3125f;
            var10 = 0.6875f;
        }
        this.HorizonCode_Horizon_È(var7, 0.0f, var9, var8, var11, var10);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Ý((IBlockAccess)worldIn, pos);
        this.Ê = 1.5;
        return super.HorizonCode_Horizon_È(worldIn, pos, state);
    }
    
    public boolean Âµá€(final IBlockAccess p_176253_1_, final BlockPos p_176253_2_) {
        final Block var3 = p_176253_1_.Â(p_176253_2_).Ý();
        return var3 != Blocks.¥ÇªÅ && (var3 == this || var3 instanceof BlockFenceGate || (var3.É.áˆºÑ¢Õ() && var3.áˆºÑ¢Õ() && var3.É != Material.Çªà¢));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        for (final HorizonCode_Horizon_È var7 : HorizonCode_Horizon_È.values()) {
            list.add(new ItemStack(itemIn, 1, var7.Â()));
        }
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockWall.Ç)).Â();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        return side != EnumFacing.HorizonCode_Horizon_È || super.HorizonCode_Horizon_È(worldIn, pos, side);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockWall.Ç, HorizonCode_Horizon_È.HorizonCode_Horizon_È(meta));
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((HorizonCode_Horizon_È)state.HorizonCode_Horizon_È(BlockWall.Ç)).Â();
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        return state.HorizonCode_Horizon_È(BlockWall.Õ, !worldIn.Ø­áŒŠá(pos.Ø­áŒŠá())).HorizonCode_Horizon_È(BlockWall.à¢, this.Âµá€(worldIn, pos.Ó())).HorizonCode_Horizon_È(BlockWall.ŠÂµà, this.Âµá€(worldIn, pos.áŒŠÆ())).HorizonCode_Horizon_È(BlockWall.¥à, this.Âµá€(worldIn, pos.à())).HorizonCode_Horizon_È(BlockWall.Âµà, this.Âµá€(worldIn, pos.Ø()));
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockWall.Õ, BlockWall.à¢, BlockWall.ŠÂµà, BlockWall.Âµà, BlockWall.¥à, BlockWall.Ç });
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("NORMAL", 0, "NORMAL", 0, 0, "cobblestone", "normal"), 
        Â("MOSSY", 1, "MOSSY", 1, 1, "mossy_cobblestone", "mossy");
        
        private static final HorizonCode_Horizon_È[] Ý;
        private final int Ø­áŒŠá;
        private final String Âµá€;
        private String Ó;
        private static final HorizonCode_Horizon_È[] à;
        private static final String Ø = "CL_00002048";
        
        static {
            áŒŠÆ = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ý = new HorizonCode_Horizon_È[values().length];
            à = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            for (final HorizonCode_Horizon_È var4 : values()) {
                HorizonCode_Horizon_È.Ý[var4.Â()] = var4;
            }
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45673_1_, final int p_i45673_2_, final int p_i45673_3_, final String p_i45673_4_, final String p_i45673_5_) {
            this.Ø­áŒŠá = p_i45673_3_;
            this.Âµá€ = p_i45673_4_;
            this.Ó = p_i45673_5_;
        }
        
        public int Â() {
            return this.Ø­áŒŠá;
        }
        
        @Override
        public String toString() {
            return this.Âµá€;
        }
        
        public static HorizonCode_Horizon_È HorizonCode_Horizon_È(int p_176660_0_) {
            if (p_176660_0_ < 0 || p_176660_0_ >= HorizonCode_Horizon_È.Ý.length) {
                p_176660_0_ = 0;
            }
            return HorizonCode_Horizon_È.Ý[p_176660_0_];
        }
        
        @Override
        public String HorizonCode_Horizon_È() {
            return this.Âµá€;
        }
        
        public String Ý() {
            return this.Ó;
        }
    }
}
