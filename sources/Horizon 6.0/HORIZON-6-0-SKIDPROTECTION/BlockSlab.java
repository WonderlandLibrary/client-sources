package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.List;

public abstract class BlockSlab extends Block
{
    public static final PropertyEnum Õ;
    private static final String à¢ = "CL_00000253";
    
    static {
        Õ = PropertyEnum.HorizonCode_Horizon_È("half", HorizonCode_Horizon_È.class);
    }
    
    public BlockSlab(final Material p_i45714_1_) {
        super(p_i45714_1_);
        if (this.È()) {
            this.£à = true;
        }
        else {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        this.Ø­áŒŠá(255);
    }
    
    @Override
    protected boolean Ñ¢á() {
        return false;
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        if (this.È()) {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            final IBlockState var3 = access.Â(pos);
            if (var3.Ý() == this) {
                if (var3.HorizonCode_Horizon_È(BlockSlab.Õ) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                    this.HorizonCode_Horizon_È(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
                }
                else {
                    this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
                }
            }
        }
    }
    
    @Override
    public void ŠÄ() {
        if (this.È()) {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        this.Ý((IBlockAccess)worldIn, pos);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
    }
    
    @Override
    public boolean Å() {
        return this.È();
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        final IBlockState var9 = super.HorizonCode_Horizon_È(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).HorizonCode_Horizon_È(BlockSlab.Õ, HorizonCode_Horizon_È.Â);
        return this.È() ? var9 : ((facing != EnumFacing.HorizonCode_Horizon_È && (facing == EnumFacing.Â || hitY <= 0.5)) ? var9 : var9.HorizonCode_Horizon_È(BlockSlab.Õ, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return this.È() ? 2 : 1;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return this.È();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final EnumFacing side) {
        if (this.È()) {
            return super.HorizonCode_Horizon_È(worldIn, pos, side);
        }
        if (side != EnumFacing.Â && side != EnumFacing.HorizonCode_Horizon_È && !super.HorizonCode_Horizon_È(worldIn, pos, side)) {
            return false;
        }
        final BlockPos var4 = pos.HorizonCode_Horizon_È(side.Âµá€());
        final IBlockState var5 = worldIn.Â(pos);
        final IBlockState var6 = worldIn.Â(var4);
        final boolean var7 = Ý(var5.Ý()) && var5.HorizonCode_Horizon_È(BlockSlab.Õ) == HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        final boolean var8 = Ý(var6.Ý()) && var6.HorizonCode_Horizon_È(BlockSlab.Õ) == HorizonCode_Horizon_È.HorizonCode_Horizon_È;
        return var8 ? (side == EnumFacing.HorizonCode_Horizon_È || (side == EnumFacing.Â && super.HorizonCode_Horizon_È(worldIn, pos, side)) || !Ý(var5.Ý()) || !var7) : (side == EnumFacing.Â || (side == EnumFacing.HorizonCode_Horizon_È && super.HorizonCode_Horizon_È(worldIn, pos, side)) || !Ý(var5.Ý()) || var7);
    }
    
    protected static boolean Ý(final Block p_150003_0_) {
        return p_150003_0_ == Blocks.Ø­Âµ || p_150003_0_ == Blocks.ÇŽÊ || p_150003_0_ == Blocks.µØ;
    }
    
    public abstract String Âµá€(final int p0);
    
    @Override
    public int Ó(final World worldIn, final BlockPos pos) {
        return super.Ó(worldIn, pos) & 0x7;
    }
    
    public abstract boolean È();
    
    public abstract IProperty áŠ();
    
    public abstract Object HorizonCode_Horizon_È(final ItemStack p0);
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("TOP", 0, "TOP", 0, "top"), 
        Â("BOTTOM", 1, "BOTTOM", 1, "bottom");
        
        private final String Ý;
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002109";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45713_1_, final int p_i45713_2_, final String p_i45713_3_) {
            this.Ý = p_i45713_3_;
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
}
