package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer
{
    private static final String Õ = "CL_00000269";
    
    protected BlockMobSpawner() {
        super(Material.Âµá€);
    }
    
    @Override
    public TileEntity HorizonCode_Horizon_È(final World worldIn, final int meta) {
        return new TileEntityMobSpawner();
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return null;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final Random random) {
        return 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final float chance, final int fortune) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, chance, fortune);
        final int var6 = 15 + worldIn.Å.nextInt(15) + worldIn.Å.nextInt(15);
        this.HorizonCode_Horizon_È(worldIn, pos, var6);
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
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return null;
    }
}
