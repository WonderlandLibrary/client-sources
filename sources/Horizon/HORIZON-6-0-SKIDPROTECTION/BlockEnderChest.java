package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import com.google.common.base.Predicate;

public class BlockEnderChest extends BlockContainer
{
    public static final PropertyDirection Õ;
    private static final String à¢ = "CL_00000238";
    
    static {
        Õ = PropertyDirection.HorizonCode_Horizon_È("facing", (Predicate)EnumFacing.Ý.HorizonCode_Horizon_È);
    }
    
    protected BlockEnderChest() {
        super(Material.Âµá€);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockEnderChest.Õ, EnumFacing.Ý));
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
        this.HorizonCode_Horizon_È(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
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
    public int ÂµÈ() {
        return 2;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.ÇŽá€);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 8;
    }
    
    @Override
    protected boolean Ñ¢á() {
        return true;
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY, final float hitZ, final int meta, final EntityLivingBase placer) {
        return this.¥à().HorizonCode_Horizon_È(BlockEnderChest.Õ, placer.ˆà¢().Âµá€());
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockEnderChest.Õ, placer.ˆà¢().Âµá€()), 2);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final InventoryEnderChest var9 = playerIn.ÇŽ();
        final TileEntity var10 = worldIn.HorizonCode_Horizon_È(pos);
        if (var9 == null || !(var10 instanceof TileEntityEnderChest)) {
            return true;
        }
        if (worldIn.Â(pos.Ø­áŒŠá()).Ý().Ø()) {
            return true;
        }
        if (worldIn.ŠÄ) {
            return true;
        }
        var9.HorizonCode_Horizon_È((TileEntityEnderChest)var10);
        playerIn.HorizonCode_Horizon_È(var9);
        return true;
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityEnderChest();
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        for (int var5 = 0; var5 < 3; ++var5) {
            final int var6 = rand.nextInt(2) * 2 - 1;
            final int var7 = rand.nextInt(2) * 2 - 1;
            final double var8 = pos.HorizonCode_Horizon_È() + 0.5 + 0.25 * var6;
            final double var9 = pos.Â() + rand.nextFloat();
            final double var10 = pos.Ý() + 0.5 + 0.25 * var7;
            final double var11 = rand.nextFloat() * var6;
            final double var12 = (rand.nextFloat() - 0.5) * 0.125;
            final double var13 = rand.nextFloat() * var7;
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.áŒŠà, var8, var9, var10, var11, var12, var13, new int[0]);
        }
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        EnumFacing var2 = EnumFacing.HorizonCode_Horizon_È(meta);
        if (var2.á() == EnumFacing.HorizonCode_Horizon_È.Â) {
            var2 = EnumFacing.Ý;
        }
        return this.¥à().HorizonCode_Horizon_È(BlockEnderChest.Õ, var2);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((EnumFacing)state.HorizonCode_Horizon_È(BlockEnderChest.Õ)).Â();
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockEnderChest.Õ });
    }
}
