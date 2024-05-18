package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;

public class BlockFarmland extends Block
{
    public static final PropertyInteger Õ;
    private static final String à¢ = "CL_00000241";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("moisture", 0, 7);
    }
    
    protected BlockFarmland() {
        super(Material.Ý);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockFarmland.Õ, 0));
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È(0.0f, 0.0f, 0.0f, 1.0f, 0.9375f, 1.0f);
        this.Ø­áŒŠá(255);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return new AxisAlignedBB(pos.HorizonCode_Horizon_È(), pos.Â(), pos.Ý(), pos.HorizonCode_Horizon_È() + 1, pos.Â() + 1, pos.Ý() + 1);
    }
    
    @Override
    public boolean Å() {
        return false;
    }
    
    @Override
    public boolean áˆºÑ¢Õ() {
        return false;
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final int var5 = (int)state.HorizonCode_Horizon_È(BlockFarmland.Õ);
        if (!this.áˆºÑ¢Õ(worldIn, pos) && !worldIn.ŒÏ(pos.Ø­áŒŠá())) {
            if (var5 > 0) {
                worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockFarmland.Õ, var5 - 1), 2);
            }
            else if (!this.áŒŠÆ(worldIn, pos)) {
                worldIn.Â(pos, Blocks.Âµá€.¥à());
            }
        }
        else if (var5 < 7) {
            worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockFarmland.Õ, 7), 2);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final Entity entityIn, final float fallDistance) {
        if (entityIn instanceof EntityLivingBase) {
            if (!worldIn.ŠÄ && worldIn.Å.nextFloat() < fallDistance - 0.5f) {
                if (!(entityIn instanceof EntityPlayer) && !worldIn.Çªà¢().Â("mobGriefing")) {
                    return;
                }
                worldIn.Â(pos, Blocks.Âµá€.¥à());
            }
            super.HorizonCode_Horizon_È(worldIn, pos, entityIn, fallDistance);
        }
    }
    
    private boolean áŒŠÆ(final World worldIn, final BlockPos p_176529_2_) {
        final Block var3 = worldIn.Â(p_176529_2_.Ø­áŒŠá()).Ý();
        return var3 instanceof BlockCrops || var3 instanceof BlockStem;
    }
    
    private boolean áˆºÑ¢Õ(final World worldIn, final BlockPos p_176530_2_) {
        for (final BlockPos.HorizonCode_Horizon_È var4 : BlockPos.Ý(p_176530_2_.Â(-4, 0, -4), p_176530_2_.Â(4, 1, 4))) {
            if (worldIn.Â(var4).Ý().Ó() == Material.Ø) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        super.HorizonCode_Horizon_È(worldIn, pos, state, neighborBlock);
        if (worldIn.Â(pos.Ø­áŒŠá()).Ý().Ó().Â()) {
            worldIn.Â(pos, Blocks.Âµá€.¥à());
        }
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Blocks.Âµá€.HorizonCode_Horizon_È(Blocks.Âµá€.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È), rand, fortune);
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.Âµá€);
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockFarmland.Õ, meta & 0x7);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockFarmland.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockFarmland.Õ });
    }
}
