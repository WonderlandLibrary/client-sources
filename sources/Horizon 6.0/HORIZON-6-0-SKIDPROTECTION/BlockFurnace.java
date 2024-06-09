package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.base.Predicate;

public class BlockFurnace extends BlockContainer
{
    public static final PropertyDirection Õ;
    private final boolean à¢;
    private static boolean ŠÂµà;
    private static final String ¥à = "CL_00000248";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
    }
    
    protected BlockFurnace(final boolean p_i45407_1_) {
        super(Material.Âµá€);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockFurnace.Õ, EnumFacing.Ý));
        this.à¢ = p_i45407_1_;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.£Ó);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Âµá€(worldIn, pos, state);
    }
    
    private void Âµá€(final World worldIn, final BlockPos p_176445_2_, final IBlockState p_176445_3_) {
        if (!worldIn.ŠÄ) {
            final Block var4 = worldIn.Â(p_176445_2_.Ó()).Ý();
            final Block var5 = worldIn.Â(p_176445_2_.à()).Ý();
            final Block var6 = worldIn.Â(p_176445_2_.Ø()).Ý();
            final Block var7 = worldIn.Â(p_176445_2_.áŒŠÆ()).Ý();
            EnumFacing var8 = (EnumFacing)p_176445_3_.HorizonCode_Horizon_È(BlockFurnace.Õ);
            if (var8 == EnumFacing.Ý && var4.HorizonCode_Horizon_È() && !var5.HorizonCode_Horizon_È()) {
                var8 = EnumFacing.Ø­áŒŠá;
            }
            else if (var8 == EnumFacing.Ø­áŒŠá && var5.HorizonCode_Horizon_È() && !var4.HorizonCode_Horizon_È()) {
                var8 = EnumFacing.Ý;
            }
            else if (var8 == EnumFacing.Âµá€ && var6.HorizonCode_Horizon_È() && !var7.HorizonCode_Horizon_È()) {
                var8 = EnumFacing.Ó;
            }
            else if (var8 == EnumFacing.Ó && var7.HorizonCode_Horizon_È() && !var6.HorizonCode_Horizon_È()) {
                var8 = EnumFacing.Âµá€;
            }
            worldIn.HorizonCode_Horizon_È(p_176445_2_, p_176445_3_.HorizonCode_Horizon_È(BlockFurnace.Õ, var8), 2);
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (this.à¢) {
            final EnumFacing var5 = (EnumFacing)state.HorizonCode_Horizon_È(BlockFurnace.Õ);
            final double var6 = pos.HorizonCode_Horizon_È() + 0.5;
            final double var7 = pos.Â() + rand.nextDouble() * 6.0 / 16.0;
            final double var8 = pos.Ý() + 0.5;
            final double var9 = 0.52;
            final double var10 = rand.nextDouble() * 0.6 - 0.3;
            switch (HorizonCode_Horizon_È.HorizonCode_Horizon_È[var5.ordinal()]) {
                case 1: {
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.á, var6 - var9, var7, var8 + var10, 0.0, 0.0, 0.0, new int[0]);
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Ñ¢á, var6 - var9, var7, var8 + var10, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
                case 2: {
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.á, var6 + var9, var7, var8 + var10, 0.0, 0.0, 0.0, new int[0]);
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Ñ¢á, var6 + var9, var7, var8 + var10, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
                case 3: {
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.á, var6 + var10, var7, var8 - var9, 0.0, 0.0, 0.0, new int[0]);
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Ñ¢á, var6 + var10, var7, var8 - var9, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
                case 4: {
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.á, var6 + var10, var7, var8 + var9, 0.0, 0.0, 0.0, new int[0]);
                    worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Ñ¢á, var6 + var10, var7, var8 + var9, 0.0, 0.0, 0.0, new int[0]);
                    break;
                }
            }
        }
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        final TileEntity var9 = worldIn.HorizonCode_Horizon_È(pos);
        if (var9 instanceof TileEntityFurnace) {
            playerIn.HorizonCode_Horizon_È((IInventory)var9);
        }
        return true;
    }
    
    public static void HorizonCode_Horizon_È(final boolean p_176446_0_, final World worldIn, final BlockPos p_176446_2_) {
        final IBlockState var3 = worldIn.Â(p_176446_2_);
        final TileEntity var4 = worldIn.HorizonCode_Horizon_È(p_176446_2_);
        BlockFurnace.ŠÂµà = true;
        if (p_176446_0_) {
            worldIn.HorizonCode_Horizon_È(p_176446_2_, Blocks.ˆÐƒØ­à.¥à().HorizonCode_Horizon_È(BlockFurnace.Õ, var3.HorizonCode_Horizon_È(BlockFurnace.Õ)), 3);
            worldIn.HorizonCode_Horizon_È(p_176446_2_, Blocks.ˆÐƒØ­à.¥à().HorizonCode_Horizon_È(BlockFurnace.Õ, var3.HorizonCode_Horizon_È(BlockFurnace.Õ)), 3);
        }
        else {
            worldIn.HorizonCode_Horizon_È(p_176446_2_, Blocks.£Ó.¥à().HorizonCode_Horizon_È(BlockFurnace.Õ, var3.HorizonCode_Horizon_È(BlockFurnace.Õ)), 3);
            worldIn.HorizonCode_Horizon_È(p_176446_2_, Blocks.£Ó.¥à().HorizonCode_Horizon_È(BlockFurnace.Õ, var3.HorizonCode_Horizon_È(BlockFurnace.Õ)), 3);
        }
        BlockFurnace.ŠÂµà = false;
        if (var4 != null) {
            var4.µà();
            worldIn.HorizonCode_Horizon_È(p_176446_2_, var4);
        }
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityFurnace();
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockFurnace.Õ, placer.ˆà¢().Âµá€());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockFurnace.Õ, placer.ˆà¢().Âµá€()), 2);
        if (stack.¥Æ()) {
            final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
            if (var6 instanceof TileEntityFurnace) {
                ((TileEntityFurnace)var6).HorizonCode_Horizon_È(stack.µà());
            }
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!BlockFurnace.ŠÂµà) {
            final TileEntity var4 = worldIn.HorizonCode_Horizon_È(pos);
            if (var4 instanceof TileEntityFurnace) {
                InventoryHelper.HorizonCode_Horizon_È(worldIn, pos, (IInventory)var4);
                worldIn.Âµá€(pos, this);
            }
        }
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    @Override
    public boolean Õ() {
        return true;
    }
    
    @Override
    public int Ø(final World worldIn, final BlockPos pos) {
        return Container.HorizonCode_Horizon_È(worldIn.HorizonCode_Horizon_È(pos));
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.£Ó);
    }
    
    @Override
    public int ÂµÈ() {
        return 3;
    }
    
    @Override
    public IBlockState à(final IBlockState state) {
        return this.¥à().HorizonCode_Horizon_È(BlockFurnace.Õ, EnumFacing.Ø­áŒŠá);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        EnumFacing var2 = EnumFacing.HorizonCode_Horizon_È(meta);
        if (var2.á() == EnumFacing.HorizonCode_Horizon_È.Â) {
            var2 = EnumFacing.Ý;
        }
        return this.¥à().HorizonCode_Horizon_È(BlockFurnace.Õ, var2);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((EnumFacing)state.HorizonCode_Horizon_È(BlockFurnace.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockFurnace.Õ });
    }
    
    static final class HorizonCode_Horizon_È
    {
        static final int[] HorizonCode_Horizon_È;
        private static final String Â = "CL_00002111";
        
        static {
            HorizonCode_Horizon_È = new int[EnumFacing.values().length];
            try {
                BlockFurnace.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Âµá€.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                BlockFurnace.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ó.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                BlockFurnace.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ý.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            try {
                BlockFurnace.HorizonCode_Horizon_È.HorizonCode_Horizon_È[EnumFacing.Ø­áŒŠá.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
        }
    }
}
