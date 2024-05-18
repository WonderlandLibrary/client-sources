package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;

public class BlockReed extends Block
{
    public static final PropertyInteger Õ;
    private static final String à¢ = "CL_00000300";
    
    static {
        Õ = PropertyInteger.HorizonCode_Horizon_È("age", 0, 15);
    }
    
    protected BlockReed() {
        super(Material.ÂµÈ);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockReed.Õ, 0));
        final float var1 = 0.375f;
        this.HorizonCode_Horizon_È(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 1.0f, 0.5f + var1);
        this.HorizonCode_Horizon_È(true);
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if ((worldIn.Â(pos.Âµá€()).Ý() == Blocks.Ðƒáƒ || this.Âµá€(worldIn, pos, state)) && worldIn.Ø­áŒŠá(pos.Ø­áŒŠá())) {
            int var5;
            for (var5 = 1; worldIn.Â(pos.Ý(var5)).Ý() == this; ++var5) {}
            if (var5 < 3) {
                final int var6 = (int)state.HorizonCode_Horizon_È(BlockReed.Õ);
                if (var6 == 15) {
                    worldIn.Â(pos.Ø­áŒŠá(), this.¥à());
                    worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockReed.Õ, 0), 4);
                }
                else {
                    worldIn.HorizonCode_Horizon_È(pos, state.HorizonCode_Horizon_È(BlockReed.Õ, var6 + 1), 4);
                }
            }
        }
    }
    
    @Override
    public boolean Ø­áŒŠá(final World worldIn, final BlockPos pos) {
        final Block var3 = worldIn.Â(pos.Âµá€()).Ý();
        if (var3 == this) {
            return true;
        }
        if (var3 != Blocks.Ø­áŒŠá && var3 != Blocks.Âµá€ && var3 != Blocks.£á) {
            return false;
        }
        for (final EnumFacing var5 : EnumFacing.Ý.HorizonCode_Horizon_È) {
            if (worldIn.Â(pos.HorizonCode_Horizon_È(var5).Âµá€()).Ý().Ó() == Material.Ø) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        this.Âµá€(worldIn, pos, state);
    }
    
    protected final boolean Âµá€(final World worldIn, final BlockPos p_176353_2_, final IBlockState p_176353_3_) {
        if (this.áŒŠÆ(worldIn, p_176353_2_)) {
            return true;
        }
        this.HorizonCode_Horizon_È(worldIn, p_176353_2_, p_176353_3_, 0);
        worldIn.Ø(p_176353_2_);
        return false;
    }
    
    public boolean áŒŠÆ(final World worldIn, final BlockPos p_176354_2_) {
        return this.Ø­áŒŠá(worldIn, p_176354_2_);
    }
    
    @Override
    public AxisAlignedBB HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state) {
        return null;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Items.¥Ï;
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
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Items.¥Ï;
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final int renderPass) {
        return worldIn.Ý(pos).Â(pos);
    }
    
    @Override
    public EnumWorldBlockLayer µà() {
        return EnumWorldBlockLayer.Ý;
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockReed.Õ, meta);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return (int)state.HorizonCode_Horizon_È(BlockReed.Õ);
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockReed.Õ });
    }
}
