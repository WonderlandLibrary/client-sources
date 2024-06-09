package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;
import com.google.common.base.Predicate;

public class BlockSkull extends BlockContainer
{
    public static final PropertyDirection Õ;
    public static final PropertyBool à¢;
    private static final Predicate ŠÂµà;
    private BlockPattern ¥à;
    private BlockPattern Âµà;
    private static final String Ç = "CL_00000307";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing");
        à¢ = PropertyBool.HorizonCode_Horizon_È("nodrop");
        ŠÂµà = (Predicate)new Predicate() {
            private static final String HorizonCode_Horizon_È = "CL_00002065";
            
            public boolean HorizonCode_Horizon_È(final BlockWorldState p_177062_1_) {
                return p_177062_1_.HorizonCode_Horizon_È().Ý() == Blocks.ÇªÈ && p_177062_1_.Â() instanceof TileEntitySkull && ((TileEntitySkull)p_177062_1_.Â()).Â() == 1;
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.HorizonCode_Horizon_È((BlockWorldState)p_apply_1_);
            }
        };
    }
    
    protected BlockSkull() {
        super(Material.µà);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockSkull.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockSkull.à¢, false));
        this.HorizonCode_Horizon_È(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
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
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[((EnumFacing)access.Â(pos).HorizonCode_Horizon_È(BlockSkull.Õ)).ordinal()]) {
            default: {
                this.HorizonCode_Horizon_È(0.25f, 0.0f, 0.25f, 0.75f, 0.5f, 0.75f);
                break;
            }
            case 2: {
                this.HorizonCode_Horizon_È(0.25f, 0.25f, 0.5f, 0.75f, 0.75f, 1.0f);
                break;
            }
            case 3: {
                this.HorizonCode_Horizon_È(0.25f, 0.25f, 0.0f, 0.75f, 0.75f, 0.5f);
                break;
            }
            case 4: {
                this.HorizonCode_Horizon_È(0.5f, 0.25f, 0.25f, 1.0f, 0.75f, 0.75f);
                break;
            }
            case 5: {
                this.HorizonCode_Horizon_È(0.0f, 0.25f, 0.25f, 0.5f, 0.75f, 0.75f);
                break;
            }
        }
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.HorizonCode_Horizon_È(worldIn, pos, state);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockSkull.Õ, placer.ˆà¢()).HorizonCode_Horizon_È(BlockSkull.à¢, false);
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntitySkull();
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.ˆ;
    }
    
    @Override
    public int Ó(final World worldIn, final BlockPos pos) {
        final TileEntity var3 = worldIn.HorizonCode_Horizon_È(pos);
        return (var3 instanceof TileEntitySkull) ? ((TileEntitySkull)var3).Â() : super.Ó(worldIn, pos);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, IBlockState state, final EntityPlayer playerIn) {
        if (playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
            state = state.HorizonCode_Horizon_È(BlockSkull.à¢, true);
            worldIn.HorizonCode_Horizon_È(pos, state, 4);
        }
        super.HorizonCode_Horizon_È(worldIn, pos, state, playerIn);
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.ŠÄ) {
            if (!(boolean)state.HorizonCode_Horizon_È(BlockSkull.à¢)) {
                final TileEntity var4 = worldIn.HorizonCode_Horizon_È(pos);
                if (var4 instanceof TileEntitySkull) {
                    final TileEntitySkull var5 = (TileEntitySkull)var4;
                    final ItemStack var6 = new ItemStack(Items.ˆ, 1, this.Ó(worldIn, pos));
                    if (var5.Â() == 3 && var5.HorizonCode_Horizon_È() != null) {
                        var6.Ø­áŒŠá(new NBTTagCompound());
                        final NBTTagCompound var7 = new NBTTagCompound();
                        NBTUtil.HorizonCode_Horizon_È(var7, var5.HorizonCode_Horizon_È());
                        var6.Å().HorizonCode_Horizon_È("SkullOwner", var7);
                    }
                    Block.HorizonCode_Horizon_È(worldIn, pos, var6);
                }
            }
            super.Ø­áŒŠá(worldIn, pos, state);
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.ˆ;
    }
    
    public boolean Â(final World worldIn, final BlockPos p_176415_2_, final ItemStack p_176415_3_) {
        return p_176415_3_.Ø() == 1 && p_176415_2_.Â() >= 2 && worldIn.ŠÂµà() != EnumDifficulty.HorizonCode_Horizon_È && !worldIn.ŠÄ && this.È().HorizonCode_Horizon_È(worldIn, p_176415_2_) != null;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_180679_2_, final TileEntitySkull p_180679_3_) {
        if (p_180679_3_.Â() == 1 && p_180679_2_.Â() >= 2 && worldIn.ŠÂµà() != EnumDifficulty.HorizonCode_Horizon_È && !worldIn.ŠÄ) {
            final BlockPattern var4 = this.áŠ();
            final BlockPattern.Â var5 = var4.HorizonCode_Horizon_È(worldIn, p_180679_2_);
            if (var5 != null) {
                for (int var6 = 0; var6 < 3; ++var6) {
                    final BlockWorldState var7 = var5.HorizonCode_Horizon_È(var6, 0, 0);
                    worldIn.HorizonCode_Horizon_È(var7.Ý(), var7.HorizonCode_Horizon_È().HorizonCode_Horizon_È(BlockSkull.à¢, true), 2);
                }
                for (int var6 = 0; var6 < var4.Â(); ++var6) {
                    for (int var8 = 0; var8 < var4.HorizonCode_Horizon_È(); ++var8) {
                        final BlockWorldState var9 = var5.HorizonCode_Horizon_È(var6, var8, 0);
                        worldIn.HorizonCode_Horizon_È(var9.Ý(), Blocks.Â.¥à(), 2);
                    }
                }
                final BlockPos var10 = var5.HorizonCode_Horizon_È(1, 0, 0).Ý();
                final EntityWither var11 = new EntityWither(worldIn);
                final BlockPos var12 = var5.HorizonCode_Horizon_È(1, 2, 0).Ý();
                var11.Â(var12.HorizonCode_Horizon_È() + 0.5, var12.Â() + 0.55, var12.Ý() + 0.5, (var5.HorizonCode_Horizon_È().á() == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? 0.0f : 90.0f, 0.0f);
                var11.¥É = ((var5.HorizonCode_Horizon_È().á() == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È) ? 0.0f : 90.0f);
                var11.Ø();
                for (final EntityPlayer var14 : worldIn.HorizonCode_Horizon_È(EntityPlayer.class, var11.£É().Â(50.0, 50.0, 50.0))) {
                    var14.HorizonCode_Horizon_È(AchievementList.áƒ);
                }
                worldIn.HorizonCode_Horizon_È(var11);
                for (int var15 = 0; var15 < 120; ++var15) {
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ˆá, var10.HorizonCode_Horizon_È() + worldIn.Å.nextDouble(), var10.Â() - 2 + worldIn.Å.nextDouble() * 3.9, var10.Ý() + worldIn.Å.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
                }
                for (int var15 = 0; var15 < var4.Â(); ++var15) {
                    for (int var16 = 0; var16 < var4.HorizonCode_Horizon_È(); ++var16) {
                        final BlockWorldState var17 = var5.HorizonCode_Horizon_È(var15, var16, 0);
                        worldIn.HorizonCode_Horizon_È(var17.Ý(), Blocks.Â);
                    }
                }
            }
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockSkull.Õ, EnumFacing.HorizonCode_Horizon_È(meta & 0x7)).HorizonCode_Horizon_È(BlockSkull.à¢, (meta & 0x8) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockSkull.Õ)).Â();
        if (state.HorizonCode_Horizon_È(BlockSkull.à¢)) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockSkull.Õ, BlockSkull.à¢ });
    }
    
    protected BlockPattern È() {
        if (this.¥à == null) {
            this.¥à = FactoryBlockPattern.HorizonCode_Horizon_È().HorizonCode_Horizon_È("   ", "###", "~#~").HorizonCode_Horizon_È('#', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.ŠÕ))).HorizonCode_Horizon_È('~', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.Â))).Â();
        }
        return this.¥à;
    }
    
    protected BlockPattern áŠ() {
        if (this.Âµà == null) {
            this.Âµà = FactoryBlockPattern.HorizonCode_Horizon_È().HorizonCode_Horizon_È("^^^", "###", "~#~").HorizonCode_Horizon_È('#', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.ŠÕ))).HorizonCode_Horizon_È('^', BlockSkull.ŠÂµà).HorizonCode_Horizon_È('~', BlockWorldState.HorizonCode_Horizon_È((Predicate)BlockStateHelper.HorizonCode_Horizon_È(Blocks.Â))).Â();
        }
        return this.Âµà;
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002064";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockSkull.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockSkull.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockSkull.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockSkull.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockSkull.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
        }
    }
}
