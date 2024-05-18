package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.base.Predicate;
import java.util.Random;

public class BlockRedstoneComparator extends BlockRedstoneDiode implements ITileEntityProvider
{
    public static final PropertyBool Õ;
    public static final PropertyEnum à¢;
    private static final String Âµà = "CL_00000220";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("powered");
        à¢ = PropertyEnum.HorizonCode_Horizon_È("mode", HorizonCode_Horizon_È.class);
    }
    
    public BlockRedstoneComparator(final boolean p_i45399_1_) {
        super(p_i45399_1_);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockRedstoneComparator.ŠÂµà, EnumFacing.Ý).HorizonCode_Horizon_È(BlockRedstoneComparator.Õ, false).HorizonCode_Horizon_È(BlockRedstoneComparator.à¢, HorizonCode_Horizon_È.HorizonCode_Horizon_È));
        this.áŒŠà = true;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.ˆÕ;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.ˆÕ;
    }
    
    @Override
    protected int áŒŠÆ(final IBlockState p_176403_1_) {
        return 2;
    }
    
    @Override
    protected IBlockState áˆºÑ¢Õ(final IBlockState p_180674_1_) {
        final Boolean var2 = (Boolean)p_180674_1_.HorizonCode_Horizon_È(BlockRedstoneComparator.Õ);
        final HorizonCode_Horizon_È var3 = (HorizonCode_Horizon_È)p_180674_1_.HorizonCode_Horizon_È(BlockRedstoneComparator.à¢);
        final EnumFacing var4 = (EnumFacing)p_180674_1_.HorizonCode_Horizon_È(BlockRedstoneComparator.ŠÂµà);
        return Blocks.¥Ê.¥à().HorizonCode_Horizon_È(BlockRedstoneComparator.ŠÂµà, var4).HorizonCode_Horizon_È(BlockRedstoneComparator.Õ, var2).HorizonCode_Horizon_È(BlockRedstoneComparator.à¢, var3);
    }
    
    @Override
    protected IBlockState ÂµÈ(final IBlockState p_180675_1_) {
        final Boolean var2 = (Boolean)p_180675_1_.HorizonCode_Horizon_È(BlockRedstoneComparator.Õ);
        final HorizonCode_Horizon_È var3 = (HorizonCode_Horizon_È)p_180675_1_.HorizonCode_Horizon_È(BlockRedstoneComparator.à¢);
        final EnumFacing var4 = (EnumFacing)p_180675_1_.HorizonCode_Horizon_È(BlockRedstoneComparator.ŠÂµà);
        return Blocks.ÐƒÇŽà.¥à().HorizonCode_Horizon_È(BlockRedstoneComparator.ŠÂµà, var4).HorizonCode_Horizon_È(BlockRedstoneComparator.Õ, var2).HorizonCode_Horizon_È(BlockRedstoneComparator.à¢, var3);
    }
    
    @Override
    protected boolean á(final IBlockState p_176406_1_) {
        return this.¥à || (boolean)p_176406_1_.HorizonCode_Horizon_È(BlockRedstoneComparator.Õ);
    }
    
    @Override
    protected int HorizonCode_Horizon_È(final IBlockAccess p_176408_1_, final BlockPos p_176408_2_, final IBlockState p_176408_3_) {
        final TileEntity var4 = p_176408_1_.HorizonCode_Horizon_È(p_176408_2_);
        return (var4 instanceof TileEntityComparator) ? ((TileEntityComparator)var4).HorizonCode_Horizon_È() : 0;
    }
    
    private int áˆºÑ¢Õ(final World worldIn, final BlockPos p_176460_2_, final IBlockState p_176460_3_) {
        return (p_176460_3_.HorizonCode_Horizon_È(BlockRedstoneComparator.à¢) == HorizonCode_Horizon_È.Â) ? Math.max(this.Ó(worldIn, p_176460_2_, p_176460_3_) - this.Ý((IBlockAccess)worldIn, p_176460_2_, p_176460_3_), 0) : this.Ó(worldIn, p_176460_2_, p_176460_3_);
    }
    
    @Override
    protected boolean Âµá€(final World worldIn, final BlockPos p_176404_2_, final IBlockState p_176404_3_) {
        final int var4 = this.Ó(worldIn, p_176404_2_, p_176404_3_);
        if (var4 >= 15) {
            return true;
        }
        if (var4 == 0) {
            return false;
        }
        final int var5 = this.Ý((IBlockAccess)worldIn, p_176404_2_, p_176404_3_);
        return var5 == 0 || var4 >= var5;
    }
    
    @Override
    protected int Ó(final World worldIn, final BlockPos p_176397_2_, final IBlockState p_176397_3_) {
        int var4 = super.Ó(worldIn, p_176397_2_, p_176397_3_);
        final EnumFacing var5 = (EnumFacing)p_176397_3_.HorizonCode_Horizon_È(BlockRedstoneComparator.ŠÂµà);
        BlockPos var6 = p_176397_2_.HorizonCode_Horizon_È(var5);
        Block var7 = worldIn.Â(var6).Ý();
        if (var7.Õ()) {
            var4 = var7.Ø(worldIn, var6);
        }
        else if (var4 < 15 && var7.Ø()) {
            var6 = var6.HorizonCode_Horizon_È(var5);
            var7 = worldIn.Â(var6).Ý();
            if (var7.Õ()) {
                var4 = var7.Ø(worldIn, var6);
            }
            else if (var7.Ó() == Material.HorizonCode_Horizon_È) {
                final EntityItemFrame var8 = this.HorizonCode_Horizon_È(worldIn, var5, var6);
                if (var8 != null) {
                    var4 = var8.¥Æ();
                }
            }
        }
        return var4;
    }
    
    private EntityItemFrame HorizonCode_Horizon_È(final World worldIn, final EnumFacing p_176461_2_, final BlockPos p_176461_3_) {
        final List var4 = worldIn.HorizonCode_Horizon_È(EntityItemFrame.class, new AxisAlignedBB(p_176461_3_.HorizonCode_Horizon_È(), p_176461_3_.Â(), p_176461_3_.Ý(), p_176461_3_.HorizonCode_Horizon_È() + 1, p_176461_3_.Â() + 1, p_176461_3_.Ý() + 1), (Predicate)new Predicate() {
            private static final String Â = "CL_00002129";
            
            public boolean HorizonCode_Horizon_È(final Entity p_180416_1_) {
                return p_180416_1_ != null && p_180416_1_.ˆà¢() == p_176461_2_;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((Entity)p_apply_1_);
            }
        });
        return (var4.size() == 1) ? var4.get(0) : null;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!playerIn.áˆºáˆºáŠ.Âµá€) {
            return false;
        }
        state = state.Â(BlockRedstoneComparator.à¢);
        worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, "random.click", 0.3f, (state.HorizonCode_Horizon_È(BlockRedstoneComparator.à¢) == HorizonCode_Horizon_È.Â) ? 0.55f : 0.5f);
        worldIn.HorizonCode_Horizon_È(pos, state, 2);
        this.ÂµÈ(worldIn, pos, state);
        return true;
    }
    
    @Override
    protected void à(final World worldIn, final BlockPos p_176398_2_, final IBlockState p_176398_3_) {
        if (!worldIn.Ø­áŒŠá(p_176398_2_, this)) {
            final int var4 = this.áˆºÑ¢Õ(worldIn, p_176398_2_, p_176398_3_);
            final TileEntity var5 = worldIn.HorizonCode_Horizon_È(p_176398_2_);
            final int var6 = (var5 instanceof TileEntityComparator) ? ((TileEntityComparator)var5).HorizonCode_Horizon_È() : 0;
            if (var4 != var6 || this.á(p_176398_3_) != this.Âµá€(worldIn, p_176398_2_, p_176398_3_)) {
                if (this.áŒŠÆ(worldIn, p_176398_2_, p_176398_3_)) {
                    worldIn.HorizonCode_Horizon_È(p_176398_2_, this, 2, -1);
                }
                else {
                    worldIn.HorizonCode_Horizon_È(p_176398_2_, this, 2, 0);
                }
            }
        }
    }
    
    private void ÂµÈ(final World worldIn, final BlockPos p_176462_2_, final IBlockState p_176462_3_) {
        final int var4 = this.áˆºÑ¢Õ(worldIn, p_176462_2_, p_176462_3_);
        final TileEntity var5 = worldIn.HorizonCode_Horizon_È(p_176462_2_);
        int var6 = 0;
        if (var5 instanceof TileEntityComparator) {
            final TileEntityComparator var7 = (TileEntityComparator)var5;
            var6 = var7.HorizonCode_Horizon_È();
            var7.HorizonCode_Horizon_È(var4);
        }
        if (var6 != var4 || p_176462_3_.HorizonCode_Horizon_È(BlockRedstoneComparator.à¢) == HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
            final boolean var8 = this.Âµá€(worldIn, p_176462_2_, p_176462_3_);
            final boolean var9 = this.á(p_176462_3_);
            if (var9 && !var8) {
                worldIn.HorizonCode_Horizon_È(p_176462_2_, p_176462_3_.HorizonCode_Horizon_È(BlockRedstoneComparator.Õ, false), 2);
            }
            else if (!var9 && var8) {
                worldIn.HorizonCode_Horizon_È(p_176462_2_, p_176462_3_.HorizonCode_Horizon_È(BlockRedstoneComparator.Õ, true), 2);
            }
            this.Ø(worldIn, p_176462_2_, p_176462_3_);
        }
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (this.¥à) {
            worldIn.HorizonCode_Horizon_È(pos, this.ÂµÈ(state).HorizonCode_Horizon_È(BlockRedstoneComparator.Õ, true), 4);
        }
        this.ÂµÈ(worldIn, pos, state);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.Ý(worldIn, pos, state);
        worldIn.HorizonCode_Horizon_È(pos, this.HorizonCode_Horizon_È(worldIn, 0));
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        super.Ø­áŒŠá(worldIn, pos, state);
        worldIn.¥Æ(pos);
        this.Ø(worldIn, pos, state);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final int eventID, final int eventParam) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, eventID, eventParam);
        final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
        return var6 != null && var6.Ý(eventID, eventParam);
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityComparator();
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockRedstoneComparator.ŠÂµà, EnumFacing.Â(meta)).HorizonCode_Horizon_È(BlockRedstoneComparator.Õ, (meta & 0x8) > 0).HorizonCode_Horizon_È(BlockRedstoneComparator.à¢, ((meta & 0x4) > 0) ? HorizonCode_Horizon_È.Â : HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockRedstoneComparator.ŠÂµà)).Ý();
        if (state.HorizonCode_Horizon_È(BlockRedstoneComparator.Õ)) {
            var3 |= 0x8;
        }
        if (state.HorizonCode_Horizon_È(BlockRedstoneComparator.à¢) == HorizonCode_Horizon_È.Â) {
            var3 |= 0x4;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockRedstoneComparator.ŠÂµà, BlockRedstoneComparator.à¢, BlockRedstoneComparator.Õ });
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockRedstoneComparator.ŠÂµà, placer.ˆà¢().Âµá€()).HorizonCode_Horizon_È(BlockRedstoneComparator.Õ, false).HorizonCode_Horizon_È(BlockRedstoneComparator.à¢, HorizonCode_Horizon_È.HorizonCode_Horizon_È);
    }
    
    public enum HorizonCode_Horizon_È implements IStringSerializable
    {
        HorizonCode_Horizon_È("COMPARE", 0, "COMPARE", 0, "compare"), 
        Â("SUBTRACT", 1, "SUBTRACT", 1, "subtract");
        
        private final String Ý;
        private static final HorizonCode_Horizon_È[] Ø­áŒŠá;
        private static final String Âµá€ = "CL_00002128";
        
        static {
            Ó = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
            Ø­áŒŠá = new HorizonCode_Horizon_È[] { HorizonCode_Horizon_È.HorizonCode_Horizon_È, HorizonCode_Horizon_È.Â };
        }
        
        private HorizonCode_Horizon_È(final String s, final int n, final String p_i45731_1_, final int p_i45731_2_, final String p_i45731_3_) {
            this.Ý = p_i45731_3_;
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
