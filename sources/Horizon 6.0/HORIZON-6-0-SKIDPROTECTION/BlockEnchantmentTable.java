package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockEnchantmentTable extends BlockContainer
{
    private static final String Õ = "CL_00000235";
    
    protected BlockEnchantmentTable() {
        super(Material.Âµá€);
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.75f, 1.0f);
        this.Ø­áŒŠá(0);
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        super.Ý(worldIn, pos, state, rand);
        for (int var5 = -2; var5 <= 2; ++var5) {
            for (int var6 = -2; var6 <= 2; ++var6) {
                if (var5 > -2 && var5 < 2 && var6 == -1) {
                    var6 = 2;
                }
                if (rand.nextInt(16) == 0) {
                    for (int var7 = 0; var7 <= 1; ++var7) {
                        final BlockPos var8 = pos.Â(var5, var7, var6);
                        if (worldIn.Â(var8).Ý() == Blocks.Ï­à) {
                            if (!worldIn.Ø­áŒŠá(pos.Â(var5 / 2, 0, var6 / 2))) {
                                break;
                            }
                            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ŠÄ, pos.HorizonCode_Horizon_È() + 0.5, pos.Â() + 2.0, pos.Ý() + 0.5, var5 + rand.nextFloat() - 0.5, var7 - rand.nextFloat() - 1.0f, var6 + rand.nextFloat() - 0.5, new int[0]);
                        }
                    }
                }
            }
        }
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
        return new TileEntityEnchantmentTable();
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityPlayer playerIn, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.ŠÄ) {
            return true;
        }
        final TileEntity var9 = worldIn.HorizonCode_Horizon_È(pos);
        if (var9 instanceof TileEntityEnchantmentTable) {
            playerIn.HorizonCode_Horizon_È((IInteractionObject)var9);
        }
        return true;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, placer, stack);
        if (stack.¥Æ()) {
            final TileEntity var6 = worldIn.HorizonCode_Horizon_È(pos);
            if (var6 instanceof TileEntityEnchantmentTable) {
                ((TileEntityEnchantmentTable)var6).HorizonCode_Horizon_È(stack.µà());
            }
        }
    }
}
