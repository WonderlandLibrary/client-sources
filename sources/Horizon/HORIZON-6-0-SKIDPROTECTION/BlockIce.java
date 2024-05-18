package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockIce extends BlockBreakable
{
    private static final String Õ = "CL_00000259";
    
    public BlockIce() {
        super(Material.Šáƒ, false);
        this.áƒ = 0.98f;
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ø­áŒŠá;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        playerIn.HorizonCode_Horizon_È(StatList.É[Block.HorizonCode_Horizon_È(this)]);
        playerIn.Ý(0.025f);
        if (this.Ñ¢á() && EnchantmentHelper.Ø­áŒŠá(playerIn)) {
            final ItemStack var8 = this.Ó(state);
            if (var8 != null) {
                Block.HorizonCode_Horizon_È(worldIn, pos, var8);
            }
        }
        else {
            if (worldIn.£à.£á()) {
                worldIn.Ø(pos);
                return;
            }
            final int var9 = EnchantmentHelper.Âµá€(playerIn);
            this.HorizonCode_Horizon_È(worldIn, pos, state, var9);
            final Material var10 = worldIn.Â(pos.Âµá€()).Ý().Ó();
            if (var10.Ø­áŒŠá() || var10.HorizonCode_Horizon_È()) {
                worldIn.Â(pos, Blocks.áˆºÑ¢Õ.¥à());
            }
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (worldIn.Â(EnumSkyBlock.Â, pos) > 11 - this.Â()) {
            if (worldIn.£à.£á()) {
                worldIn.Ø(pos);
            }
            else {
                this.HorizonCode_Horizon_È(worldIn, pos, worldIn.Â(pos), 0);
                worldIn.Â(pos, Blocks.ÂµÈ.¥à());
            }
        }
    }
    
    @Override
    public int ˆá() {
        return 0;
    }
}
