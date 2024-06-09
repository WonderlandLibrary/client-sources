package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class BlockMycelium extends Block
{
    public static final PropertyBool Õ;
    private static final String à¢ = "CL_00000273";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("snowy");
    }
    
    protected BlockMycelium() {
        super(Material.Â);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockMycelium.Õ, false));
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public IBlockState HorizonCode_Horizon_È(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos) {
        final Block var4 = worldIn.Â(pos.Ø­áŒŠá()).Ý();
        return state.HorizonCode_Horizon_È(BlockMycelium.Õ, var4 == Blocks.ˆà¢ || var4 == Blocks.áŒŠá€);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (!worldIn.ŠÄ) {
            if (worldIn.ˆÏ­(pos.Ø­áŒŠá()) < 4 && worldIn.Â(pos.Ø­áŒŠá()).Ý().Â() > 2) {
                worldIn.Â(pos, Blocks.Âµá€.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È));
            }
            else if (worldIn.ˆÏ­(pos.Ø­áŒŠá()) >= 9) {
                for (int var5 = 0; var5 < 4; ++var5) {
                    final BlockPos var6 = pos.Â(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                    final IBlockState var7 = worldIn.Â(var6);
                    final Block var8 = worldIn.Â(var6.Ø­áŒŠá()).Ý();
                    if (var7.Ý() == Blocks.Âµá€ && var7.HorizonCode_Horizon_È(BlockDirt.Õ) == BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È && worldIn.ˆÏ­(var6.Ø­áŒŠá()) >= 4 && var8.Â() <= 2) {
                        worldIn.Â(var6, this.¥à());
                    }
                }
            }
        }
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        super.Ý(worldIn, pos, state, rand);
        if (rand.nextInt(10) == 0) {
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.Šáƒ, pos.HorizonCode_Horizon_È() + rand.nextFloat(), pos.Â() + 1.1f, pos.Ý() + rand.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Blocks.Âµá€.HorizonCode_Horizon_È(Blocks.Âµá€.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È), rand, fortune);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return 0;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockMycelium.Õ });
    }
}
