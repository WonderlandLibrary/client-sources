package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockDeadBush extends BlockBush
{
    private static final String Õ = "CL_00000224";
    
    protected BlockDeadBush() {
        super(Material.á);
        final float var1 = 0.4f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.8f, 0.5f + var1);
    }
    
    @Override
    protected boolean Ý(final Block ground) {
        return ground == Blocks.£á || ground == Blocks.Ø­ || ground == Blocks.Ø­Â || ground == Blocks.Âµá€;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos pos) {
        return true;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final EntityPlayer playerIn, final BlockPos pos, final IBlockState state, final TileEntity te) {
        if (!worldIn.ŠÄ && playerIn.áŒŠá() != null && playerIn.áŒŠá().HorizonCode_Horizon_È() == Items.áˆºà) {
            playerIn.HorizonCode_Horizon_È(StatList.É[Block.HorizonCode_Horizon_È(this)]);
            Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(Blocks.á€, 1, 0));
        }
        else {
            super.HorizonCode_Horizon_È(worldIn, playerIn, pos, state, te);
        }
    }
}
