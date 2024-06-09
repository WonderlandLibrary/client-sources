package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockGrass extends Block implements IGrowable
{
    public static final PropertyBool Õ;
    private static final String à¢ = "CL_00000251";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("snowy");
    }
    
    protected BlockGrass() {
        super(Material.Â);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockGrass.Õ, false));
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        final Block var4 = worldIn.Â(pos.Ø­áŒŠá()).Ý();
        return state.HorizonCode_Horizon_È(BlockGrass.Õ, var4 == Blocks.ˆà¢ || var4 == Blocks.áŒŠá€);
    }
    
    @Override
    public int Ï­Ðƒà() {
        return ColorizerGrass.HorizonCode_Horizon_È(0.5, 1.0);
    }
    
    @Override
    public int Âµá€(final IBlockState state) {
        return this.Ï­Ðƒà();
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        return BiomeColorHelper.HorizonCode_Horizon_È(worldIn, pos);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ) {
            if (worldIn.ˆÏ­(pos.Ø­áŒŠá()) < 4 && worldIn.Â(pos.Ø­áŒŠá()).Ý().Â() > 2) {
                worldIn.Â(pos, Blocks.Âµá€.¥à());
            }
            else if (worldIn.ˆÏ­(pos.Ø­áŒŠá()) >= 9) {
                for (int var5 = 0; var5 < 4; ++var5) {
                    final BlockPos var6 = pos.Â(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                    final Block var7 = worldIn.Â(var6.Ø­áŒŠá()).Ý();
                    final IBlockState var8 = worldIn.Â(var6);
                    if (var8.Ý() == Blocks.Âµá€ && var8.HorizonCode_Horizon_È(BlockDirt.Õ) == BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È && worldIn.ˆÏ­(var6.Ø­áŒŠá()) >= 4 && var7.Â() <= 2) {
                        worldIn.Â(var6, Blocks.Ø­áŒŠá.¥à());
                    }
                }
            }
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Blocks.Âµá€.HorizonCode_Horizon_È(Blocks.Âµá€.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È), rand, fortune);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176473_2_, final IBlockState p_176473_3_, final boolean p_176473_4_) {
        return true;
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180670_2_, final BlockPos p_180670_3_, final IBlockState p_180670_4_) {
        return true;
    }
    
    @Override
    public void Â(final World worldIn, final Random p_176474_2_, final BlockPos p_176474_3_, final IBlockState p_176474_4_) {
        final BlockPos var5 = p_176474_3_.Ø­áŒŠá();
        int var6 = 0;
    Label_0252:
        while (var6 < 128) {
            BlockPos var7 = var5;
            while (true) {
                for (int var8 = 0; var8 < var6 / 16; ++var8) {
                    var7 = var7.Â(p_176474_2_.nextInt(3) - 1, (p_176474_2_.nextInt(3) - 1) * p_176474_2_.nextInt(3) / 2, p_176474_2_.nextInt(3) - 1);
                    if (worldIn.Â(var7.Âµá€()).Ý() != Blocks.Ø­áŒŠá || worldIn.Â(var7).Ý().Ø()) {
                        ++var6;
                        continue Label_0252;
                    }
                }
                if (worldIn.Â(var7).Ý().É != Material.HorizonCode_Horizon_È) {
                    continue;
                }
                if (p_176474_2_.nextInt(8) == 0) {
                    final BlockFlower.Â var9 = worldIn.Ý(var7).HorizonCode_Horizon_È(p_176474_2_, var7);
                    final BlockFlower var10 = var9.Â().HorizonCode_Horizon_È();
                    final IBlockState var11 = var10.¥à().HorizonCode_Horizon_È(var10.áŠ(), var9);
                    if (var10.Ó(worldIn, var7, var11)) {
                        worldIn.HorizonCode_Horizon_È(var7, var11, 3);
                    }
                    continue;
                }
                else {
                    final IBlockState var12 = Blocks.áƒ.¥à().HorizonCode_Horizon_È(BlockTallGrass.Õ, BlockTallGrass.HorizonCode_Horizon_È.Â);
                    if (Blocks.áƒ.Ó(worldIn, var7, var12)) {
                        worldIn.HorizonCode_Horizon_È(var7, var12, 3);
                    }
                    continue;
                }
                break;
            }
        }
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Â;
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return 0;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockGrass.Õ });
    }
}
