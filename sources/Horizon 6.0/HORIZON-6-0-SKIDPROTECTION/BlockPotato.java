package HORIZON-6-0-SKIDPROTECTION;

public class BlockPotato extends BlockCrops
{
    private static final String à¢ = "CL_00000286";
    
    @Override
    protected Item_1028566121 È() {
        return Items.ˆÂ;
    }
    
    @Override
    protected Item_1028566121 áŠ() {
        return Items.ˆÂ;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, chance, fortune);
        if (!worldIn.ŠÄ && (int)state.HorizonCode_Horizon_È(BlockPotato.Õ) >= 7 && worldIn.Å.nextInt(50) == 0) {
            Block.HorizonCode_Horizon_È(worldIn, pos, new ItemStack(Items.ˆØ­áˆº));
        }
    }
}
