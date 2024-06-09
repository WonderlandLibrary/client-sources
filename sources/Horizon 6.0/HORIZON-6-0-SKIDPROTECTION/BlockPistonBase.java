package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class BlockPistonBase extends Block
{
    public static final PropertyDirection Õ;
    public static final PropertyBool à¢;
    private final boolean ŠÂµà;
    private static final String ¥à = "CL_00000366";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing");
        à¢ = PropertyBool.HorizonCode_Horizon_È("extended");
    }
    
    public BlockPistonBase(final boolean p_i45443_1_) {
        super(Material.É);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockPistonBase.Õ, EnumFacing.Ý).HorizonCode_Horizon_È(BlockPistonBase.à¢, false));
        this.ŠÂµà = p_i45443_1_;
        this.HorizonCode_Horizon_È(BlockPistonBase.à);
        this.Ý(0.5f);
        this.HorizonCode_Horizon_È(CreativeTabs.Ø­áŒŠá);
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockPistonBase.Õ, HorizonCode_Horizon_È(worldIn, pos, placer)), 2);
        if (!worldIn.ŠÄ) {
            this.Âµá€(worldIn, pos, state);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.ŠÄ) {
            this.Âµá€(worldIn, pos, state);
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.ŠÄ && worldIn.HorizonCode_Horizon_È(pos) == null) {
            this.Âµá€(worldIn, pos, state);
        }
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockPistonBase.Õ, HorizonCode_Horizon_È(worldIn, pos, placer)).HorizonCode_Horizon_È(BlockPistonBase.à¢, false);
    }
    
    private void Âµá€(final World worldIn, final BlockPos p_176316_2_, final IBlockState p_176316_3_) {
        final EnumFacing var4 = (EnumFacing)p_176316_3_.HorizonCode_Horizon_È(BlockPistonBase.Õ);
        final boolean var5 = this.Â(worldIn, p_176316_2_, var4);
        if (var5 && !(boolean)p_176316_3_.HorizonCode_Horizon_È(BlockPistonBase.à¢)) {
            if (new BlockPistonStructureHelper(worldIn, p_176316_2_, var4, true).HorizonCode_Horizon_È()) {
                worldIn.Ý(p_176316_2_, this, 0, var4.Â());
            }
        }
        else if (!var5 && (boolean)p_176316_3_.HorizonCode_Horizon_È(BlockPistonBase.à¢)) {
            worldIn.HorizonCode_Horizon_È(p_176316_2_, p_176316_3_.HorizonCode_Horizon_È(BlockPistonBase.à¢, false), 2);
            worldIn.Ý(p_176316_2_, this, 1, var4.Â());
        }
    }
    
    private boolean Â(final World worldIn, final BlockPos p_176318_2_, final EnumFacing p_176318_3_) {
        for (final EnumFacing var7 : EnumFacing.values()) {
            if (var7 != p_176318_3_ && worldIn.Â(p_176318_2_.HorizonCode_Horizon_È(var7), var7)) {
                return true;
            }
        }
        if (worldIn.Â(p_176318_2_, EnumFacing.Ý)) {
            return true;
        }
        final BlockPos var8 = p_176318_2_.Ø­áŒŠá();
        for (final EnumFacing var11 : EnumFacing.values()) {
            if (var11 != EnumFacing.HorizonCode_Horizon_È && worldIn.Â(var8.HorizonCode_Horizon_È(var11), var11)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final int eventID, final int eventParam) {
        final EnumFacing var6 = (EnumFacing)state.HorizonCode_Horizon_È(BlockPistonBase.Õ);
        if (!worldIn.ŠÄ) {
            final boolean var7 = this.Â(worldIn, pos, var6);
            if (var7 && eventID == 1) {
                worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockPistonBase.à¢, true), 2);
                return false;
            }
            if (!var7 && eventID == 0) {
                return false;
            }
        }
        if (eventID == 0) {
            if (!this.HorizonCode_Horizon_È(worldIn, pos, var6, true)) {
                return false;
            }
            worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockPistonBase.à¢, true), 2);
            worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, "tile.piston.out", 0.5f, worldIn.Å.nextFloat() * 0.25f + 0.6f);
        }
        else if (eventID == 1) {
            final TileEntity var8 = worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È(var6));
            if (var8 instanceof TileEntityPiston) {
                ((TileEntityPiston)var8).Ó();
            }
            worldIn.HorizonCode_Horizon_È(pos, Blocks.¥à.¥à().HorizonCode_Horizon_È(BlockPistonMoving.Õ, var6).HorizonCode_Horizon_È(BlockPistonMoving.à¢, this.ŠÂµà ? BlockPistonExtension.HorizonCode_Horizon_È.Â : BlockPistonExtension.HorizonCode_Horizon_È.HorizonCode_Horizon_È), 3);
            worldIn.HorizonCode_Horizon_È(pos, BlockPistonMoving.HorizonCode_Horizon_È(this.Ý(eventParam), var6, false, true));
            if (this.ŠÂµà) {
                final BlockPos var9 = pos.Â(var6.Ø() * 2, var6.áŒŠÆ() * 2, var6.áˆºÑ¢Õ() * 2);
                final Block var10 = worldIn.Â(var9).Ý();
                boolean var11 = false;
                if (var10 == Blocks.¥à) {
                    final TileEntity var12 = worldIn.HorizonCode_Horizon_È(var9);
                    if (var12 instanceof TileEntityPiston) {
                        final TileEntityPiston var13 = (TileEntityPiston)var12;
                        if (var13.Ø­áŒŠá() == var6 && var13.Ý()) {
                            var13.Ó();
                            var11 = true;
                        }
                    }
                }
                if (!var11 && var10.Ó() != Material.HorizonCode_Horizon_È && HorizonCode_Horizon_È(var10, worldIn, var9, var6.Âµá€(), false) && (var10.ˆá() == 0 || var10 == Blocks.Õ || var10 == Blocks.ÇŽÕ)) {
                    this.HorizonCode_Horizon_È(worldIn, pos, var6, false);
                }
            }
            else {
                worldIn.Ø(pos.HorizonCode_Horizon_È(var6));
            }
            worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 0.5, pos.Ý() + 0.5, "tile.piston.in", 0.5f, worldIn.Å.nextFloat() * 0.15f + 0.6f);
        }
        return true;
    }
    
    @Override
    public void Ý(final IBlockAccess access, final BlockPos pos) {
        final IBlockState var3 = access.Â(pos);
        if (var3.Ý() == this && (boolean)var3.HorizonCode_Horizon_È(BlockPistonBase.à¢)) {
            final float var4 = 0.25f;
            final EnumFacing var5 = (EnumFacing)var3.HorizonCode_Horizon_È(BlockPistonBase.Õ);
            if (var5 != null) {
                switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[var5.ordinal()]) {
                    case 1: {
                        this.HorizonCode_Horizon_È(0.0f, 0.25f, 0.0f, 1.0f, 1.0f, 1.0f);
                        break;
                    }
                    case 2: {
                        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
                        break;
                    }
                    case 3: {
                        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.25f, 1.0f, 1.0f, 1.0f);
                        break;
                    }
                    case 4: {
                        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.75f);
                        break;
                    }
                    case 5: {
                        this.HorizonCode_Horizon_È(0.25f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                        break;
                    }
                    case 6: {
                        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 0.75f, 1.0f, 1.0f);
                        break;
                    }
                }
            }
        }
        else {
            this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
    }
    
    @Override
    public void ŠÄ() {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Ý((IBlockAccess)worldIn, pos);
        return super.HorizonCode_Horizon_È(worldIn, pos, state);
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    public static EnumFacing Âµá€(final int p_176317_0_) {
        final int var1 = p_176317_0_ & 0x7;
        return (var1 > 5) ? null : EnumFacing.HorizonCode_Horizon_È(var1);
    }
    
    public static EnumFacing HorizonCode_Horizon_È(final World worldIn, final BlockPos p_180695_1_, final EntityLivingBase p_180695_2_) {
        if (MathHelper.Âµá€((float)p_180695_2_.ŒÏ - p_180695_1_.HorizonCode_Horizon_È()) < 2.0f && MathHelper.Âµá€((float)p_180695_2_.Ê - p_180695_1_.Ý()) < 2.0f) {
            final double var3 = p_180695_2_.Çªà¢ + p_180695_2_.Ðƒáƒ();
            if (var3 - p_180695_1_.Â() > 2.0) {
                return EnumFacing.Â;
            }
            if (p_180695_1_.Â() - var3 > 0.0) {
                return EnumFacing.HorizonCode_Horizon_È;
            }
        }
        return p_180695_2_.ˆà¢().Âµá€();
    }
    
    public static boolean HorizonCode_Horizon_È(final Block p_180696_0_, final World worldIn, final BlockPos p_180696_2_, final EnumFacing p_180696_3_, final boolean p_180696_4_) {
        if (p_180696_0_ == Blocks.ÇŽá€) {
            return false;
        }
        if (!worldIn.áŠ().HorizonCode_Horizon_È(p_180696_2_)) {
            return false;
        }
        if (p_180696_2_.Â() < 0 || (p_180696_3_ == EnumFacing.HorizonCode_Horizon_È && p_180696_2_.Â() == 0)) {
            return false;
        }
        if (p_180696_2_.Â() <= worldIn.É() - 1 && (p_180696_3_ != EnumFacing.Â || p_180696_2_.Â() != worldIn.É() - 1)) {
            if (p_180696_0_ != Blocks.Õ && p_180696_0_ != Blocks.ÇŽÕ) {
                if (p_180696_0_.Â(worldIn, p_180696_2_) == -1.0f) {
                    return false;
                }
                if (p_180696_0_.ˆá() == 2) {
                    return false;
                }
                if (p_180696_0_.ˆá() == 1) {
                    return p_180696_4_;
                }
            }
            else if (worldIn.Â(p_180696_2_).HorizonCode_Horizon_È(BlockPistonBase.à¢)) {
                return false;
            }
            return !(p_180696_0_ instanceof ITileEntityProvider);
        }
        return false;
    }
    
    private boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176319_2_, final EnumFacing p_176319_3_, final boolean p_176319_4_) {
        if (!p_176319_4_) {
            worldIn.Ø(p_176319_2_.HorizonCode_Horizon_È(p_176319_3_));
        }
        final BlockPistonStructureHelper var5 = new BlockPistonStructureHelper(worldIn, p_176319_2_, p_176319_3_, p_176319_4_);
        final List var6 = var5.Â();
        final List var7 = var5.Ý();
        if (!var5.HorizonCode_Horizon_È()) {
            return false;
        }
        int var8 = var6.size() + var7.size();
        final Block[] var9 = new Block[var8];
        final EnumFacing var10 = p_176319_4_ ? p_176319_3_ : p_176319_3_.Âµá€();
        for (int var11 = var7.size() - 1; var11 >= 0; --var11) {
            final BlockPos var12 = var7.get(var11);
            final Block var13 = worldIn.Â(var12).Ý();
            var13.HorizonCode_Horizon_È(worldIn, var12, worldIn.Â(var12), 0);
            worldIn.Ø(var12);
            --var8;
            var9[var8] = var13;
        }
        for (int var11 = var6.size() - 1; var11 >= 0; --var11) {
            BlockPos var12 = var6.get(var11);
            final IBlockState var14 = worldIn.Â(var12);
            final Block var15 = var14.Ý();
            var15.Ý(var14);
            worldIn.Ø(var12);
            var12 = var12.HorizonCode_Horizon_È(var10);
            worldIn.HorizonCode_Horizon_È(var12, Blocks.¥à.¥à().HorizonCode_Horizon_È(BlockPistonBase.Õ, p_176319_3_), 4);
            worldIn.HorizonCode_Horizon_È(var12, BlockPistonMoving.HorizonCode_Horizon_È(var14, p_176319_3_, p_176319_4_, false));
            --var8;
            var9[var8] = var15;
        }
        final BlockPos var16 = p_176319_2_.HorizonCode_Horizon_È(p_176319_3_);
        if (p_176319_4_) {
            final BlockPistonExtension.HorizonCode_Horizon_È var17 = this.ŠÂµà ? BlockPistonExtension.HorizonCode_Horizon_È.Â : BlockPistonExtension.HorizonCode_Horizon_È.HorizonCode_Horizon_È;
            final IBlockState var14 = Blocks.à¢.¥à().HorizonCode_Horizon_È(BlockPistonExtension.Õ, p_176319_3_).HorizonCode_Horizon_È(BlockPistonExtension.à¢, var17);
            final IBlockState var18 = Blocks.¥à.¥à().HorizonCode_Horizon_È(BlockPistonMoving.Õ, p_176319_3_).HorizonCode_Horizon_È(BlockPistonMoving.à¢, this.ŠÂµà ? BlockPistonExtension.HorizonCode_Horizon_È.Â : BlockPistonExtension.HorizonCode_Horizon_È.HorizonCode_Horizon_È);
            worldIn.HorizonCode_Horizon_È(var16, var18, 4);
            worldIn.HorizonCode_Horizon_È(var16, BlockPistonMoving.HorizonCode_Horizon_È(var14, p_176319_3_, true, false));
        }
        for (int var19 = var7.size() - 1; var19 >= 0; --var19) {
            worldIn.Â(var7.get(var19), var9[var8++]);
        }
        for (int var19 = var6.size() - 1; var19 >= 0; --var19) {
            worldIn.Â(var6.get(var19), var9[var8++]);
        }
        if (p_176319_4_) {
            worldIn.Â(var16, Blocks.à¢);
            worldIn.Â(p_176319_2_, this);
        }
        return true;
    }
    
    @Override
    public IBlockState à(final IBlockState state) {
        return this.¥à().HorizonCode_Horizon_È(BlockPistonBase.Õ, EnumFacing.Â);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockPistonBase.Õ, Âµá€(meta)).HorizonCode_Horizon_È(BlockPistonBase.à¢, (meta & 0x8) > 0);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        final byte var2 = 0;
        int var3 = var2 | ((EnumFacing)state.HorizonCode_Horizon_È(BlockPistonBase.Õ)).Â();
        if (state.HorizonCode_Horizon_È(BlockPistonBase.à¢)) {
            var3 |= 0x8;
        }
        return var3;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockPistonBase.Õ, BlockPistonBase.à¢ });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002037";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockPistonBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.HorizonCode_Horizon_È.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockPistonBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Â.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockPistonBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockPistonBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                BlockPistonBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                BlockPistonBase.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
        }
    }
}
