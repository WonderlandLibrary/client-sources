package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockRedstoneLight extends Block
{
    private final boolean Õ;
    private static final String à¢ = "CL_00000297";
    
    public BlockRedstoneLight(final boolean p_i45421_1_) {
        super(Material.Ø­à);
        this.Õ = p_i45421_1_;
        if (p_i45421_1_) {
            this.HorizonCode_Horizon_È(1.0f);
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (!worldIn.ŠÄ) {
            if (this.Õ && !worldIn.áŒŠà(pos)) {
                worldIn.HorizonCode_Horizon_È(pos, Blocks.áŒŠÉ.¥à(), 2);
            }
            else if (!this.Õ && worldIn.áŒŠà(pos)) {
                worldIn.HorizonCode_Horizon_È(pos, Blocks.ÇŽØ.¥à(), 2);
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!worldIn.ŠÄ) {
            if (this.Õ && !worldIn.áŒŠà(pos)) {
                worldIn.HorizonCode_Horizon_È(pos, this, 4);
            }
            else if (!this.Õ && worldIn.áŒŠà(pos)) {
                worldIn.HorizonCode_Horizon_È(pos, Blocks.ÇŽØ.¥à(), 2);
            }
        }
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ && this.Õ && !worldIn.áŒŠà(pos)) {
            worldIn.HorizonCode_Horizon_È(pos, Blocks.áŒŠÉ.¥à(), 2);
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.áŒŠÉ);
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.áŒŠÉ);
    }
    
    @Override
    protected ItemStack Ó(final IBlockState state) {
        return new ItemStack(Blocks.áŒŠÉ);
    }
}
