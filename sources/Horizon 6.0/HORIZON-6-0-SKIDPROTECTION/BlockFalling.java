package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockFalling extends Block
{
    public static boolean ŠÂµà;
    private static final String Õ = "CL_00000240";
    
    public BlockFalling() {
        super(Material.£à);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    public BlockFalling(final Material materialIn) {
        super(materialIn);
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn));
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn));
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ) {
            this.ÂµÈ(worldIn, pos);
        }
    }
    
    private void ÂµÈ(final World worldIn, final BlockPos pos) {
        if (áˆºÑ¢Õ(worldIn, pos.Âµá€()) && pos.Â() >= 0) {
            final byte var3 = 32;
            if (!BlockFalling.ŠÂµà && worldIn.HorizonCode_Horizon_È(pos.Â(-var3, -var3, -var3), pos.Â(var3, var3, var3))) {
                if (!worldIn.ŠÄ) {
                    final EntityFallingBlock var4 = new EntityFallingBlock(worldIn, pos.HorizonCode_Horizon_È() + 0.5, pos.Â(), pos.Ý() + 0.5, worldIn.Â(pos));
                    this.HorizonCode_Horizon_È(var4);
                    worldIn.HorizonCode_Horizon_È(var4);
                }
            }
            else {
                worldIn.Ø(pos);
                BlockPos var5;
                for (var5 = pos.Âµá€(); áˆºÑ¢Õ(worldIn, var5) && var5.Â() > 0; var5 = var5.Âµá€()) {}
                if (var5.Â() > 0) {
                    worldIn.Â(var5.Ø­áŒŠá(), this.¥à());
                }
            }
        }
    }
    
    protected void HorizonCode_Horizon_È(final EntityFallingBlock fallingEntity) {
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 2;
    }
    
    public static boolean áˆºÑ¢Õ(final World worldIn, final BlockPos pos) {
        final Block var2 = worldIn.Â(pos).Ý();
        final Material var3 = var2.É;
        return var2 == Blocks.Ô || var3 == Material.HorizonCode_Horizon_È || var3 == Material.Ø || var3 == Material.áŒŠÆ;
    }
    
    public void áŒŠÆ(final World worldIn, final BlockPos pos) {
    }
}
