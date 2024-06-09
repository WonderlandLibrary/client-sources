package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import java.util.Random;

public class BlockBrewingStand extends BlockContainer
{
    public static final PropertyBool[] Õ;
    private final Random à¢;
    private static final String ŠÂµà = "CL_00000207";
    
    static {
        Õ = new PropertyBool[] { PropertyBool.HorizonCode_Horizon_È("has_bottle_0"), PropertyBool.HorizonCode_Horizon_È("has_bottle_1"), PropertyBool.HorizonCode_Horizon_È("has_bottle_2") };
    }
    
    public BlockBrewingStand() {
        super(Material.Ó);
        this.à¢ = new Random();
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockBrewingStand.Õ[0], false).HorizonCode_Horizon_È(BlockBrewingStand.Õ[1], false).HorizonCode_Horizon_È(BlockBrewingStand.Õ[2], false));
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public int ÂµÈ() {
        return 3;
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityBrewingStand();
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final AxisAlignedBB mask, final List list, final Entity collidingEntity) {
        this.HorizonCode_Horizon_È(0.4375f, 0.0f, 0.4375f, 0.5625f, 0.875f, 0.5625f);
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
        this.ŠÄ();
        super.HorizonCode_Horizon_È(worldIn, pos, state, mask, list, collidingEntity);
    }
    
    @Override
    public void ŠÄ() {
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        final TileEntity var9 = worldIn.HorizonCode_Horizon_È(pos);
        if (var9 instanceof TileEntityBrewingStand) {
            playerIn.HorizonCode_Horizon_È((IInventory)var9);
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        if (stack.¥Æ()) {
            final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
            if (var6 instanceof TileEntityBrewingStand) {
                ((TileEntityBrewingStand)var6).HorizonCode_Horizon_È(stack.µà());
            }
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final double var5 = pos.HorizonCode_Horizon_È() + 0.4f + rand.nextFloat() * 0.2f;
        final double var6 = pos.Â() + 0.7f + rand.nextFloat() * 0.3f;
        final double var7 = pos.Ý() + 0.4f + rand.nextFloat() * 0.2f;
        worldIn.HorizonCode_Horizon_È(EnumParticleTypes.á, var5, var6, var7, 0.0, 0.0, 0.0, new int[0]);
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        final TileEntity var4 = worldIn.HorizonCode_Horizon_È(pos);
        if (var4 instanceof TileEntityBrewingStand) {
            InventoryHelper.HorizonCode_Horizon_È(worldIn, pos, (IInventory)var4);
        }
        super.Ø­áŒŠá(worldIn, pos, state);
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.ÇªáˆºÕ;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.ÇªáˆºÕ;
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
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        IBlockState var2 = this.¥à();
        for (int var3 = 0; var3 < 3; ++var3) {
            var2 = var2.HorizonCode_Horizon_È(BlockBrewingStand.Õ[var3], (meta & 1 << var3) > 0);
        }
        return var2;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        int var2 = 0;
        for (int var3 = 0; var3 < 3; ++var3) {
            if (state.HorizonCode_Horizon_È(BlockBrewingStand.Õ[var3])) {
                var2 |= 1 << var3;
            }
        }
        return var2;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockBrewingStand.Õ[0], BlockBrewingStand.Õ[1], BlockBrewingStand.Õ[2] });
    }
}
