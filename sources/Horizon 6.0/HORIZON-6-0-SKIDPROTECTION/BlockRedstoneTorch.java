package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.List;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Map;

public class BlockRedstoneTorch extends BlockTorch
{
    private static Map à¢;
    private final boolean ŠÂµà;
    private static final String ¥à = "CL_00000298";
    
    static {
        BlockRedstoneTorch.à¢ = Maps.newHashMap();
    }
    
    private boolean HorizonCode_Horizon_È(final World worldIn, final BlockPos p_176598_2_, final boolean p_176598_3_) {
        if (!BlockRedstoneTorch.à¢.containsKey(worldIn)) {
            BlockRedstoneTorch.à¢.put(worldIn, Lists.newArrayList());
        }
        final List var4 = BlockRedstoneTorch.à¢.get(worldIn);
        if (p_176598_3_) {
            var4.add(new HorizonCode_Horizon_È(p_176598_2_, worldIn.Šáƒ()));
        }
        int var5 = 0;
        for (int var6 = 0; var6 < var4.size(); ++var6) {
            final HorizonCode_Horizon_È var7 = var4.get(var6);
            if (var7.HorizonCode_Horizon_È.equals(p_176598_2_) && ++var5 >= 8) {
                return true;
            }
        }
        return false;
    }
    
    protected BlockRedstoneTorch(final boolean p_i45423_1_) {
        this.ŠÂµà = p_i45423_1_;
        this.HorizonCode_Horizon_È(true);
        this.HorizonCode_Horizon_È((CreativeTabs)null);
    }
    
    @Override
    public int HorizonCode_Horizon_È(final World worldIn) {
        return 2;
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (this.ŠÂµà) {
            for (final EnumFacing var7 : EnumFacing.values()) {
                worldIn.Â(pos.HorizonCode_Horizon_È(var7), this);
            }
        }
    }
    
    @Override
    public void Ø­áŒŠá(final World worldIn, final BlockPos pos, final IBlockState state) {
        if (this.ŠÂµà) {
            for (final EnumFacing var7 : EnumFacing.values()) {
                worldIn.Â(pos.HorizonCode_Horizon_È(var7), this);
            }
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return (this.ŠÂµà && state.HorizonCode_Horizon_È(BlockRedstoneTorch.Õ) != side) ? 15 : 0;
    }
    
    private boolean à(final World worldIn, final BlockPos p_176597_2_, final IBlockState p_176597_3_) {
        final EnumFacing var4 = ((EnumFacing)p_176597_3_.HorizonCode_Horizon_È(BlockRedstoneTorch.Õ)).Âµá€();
        return worldIn.Â(p_176597_2_.HorizonCode_Horizon_È(var4), var4);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Random random) {
    }
    
    @Override
    public void Â(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        final boolean var5 = this.à(worldIn, pos, state);
        final List var6 = BlockRedstoneTorch.à¢.get(worldIn);
        while (var6 != null && !var6.isEmpty() && worldIn.Šáƒ() - var6.get(0).Â > 60L) {
            var6.remove(0);
        }
        if (this.ŠÂµà) {
            if (var5) {
                worldIn.HorizonCode_Horizon_È(pos, Blocks.£áŒŠá.¥à().HorizonCode_Horizon_È(BlockRedstoneTorch.Õ, state.HorizonCode_Horizon_È(BlockRedstoneTorch.Õ)), 3);
                if (this.HorizonCode_Horizon_È(worldIn, pos, true)) {
                    worldIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È() + 0.5f, pos.Â() + 0.5f, pos.Ý() + 0.5f, "random.fizz", 0.5f, 2.6f + (worldIn.Å.nextFloat() - worldIn.Å.nextFloat()) * 0.8f);
                    for (int var7 = 0; var7 < 5; ++var7) {
                        final double var8 = pos.HorizonCode_Horizon_È() + rand.nextDouble() * 0.6 + 0.2;
                        final double var9 = pos.Â() + rand.nextDouble() * 0.6 + 0.2;
                        final double var10 = pos.Ý() + rand.nextDouble() * 0.6 + 0.2;
                        worldIn.HorizonCode_Horizon_È(EnumParticleTypes.á, var8, var9, var10, 0.0, 0.0, 0.0, new int[0]);
                    }
                    worldIn.HorizonCode_Horizon_È(pos, worldIn.Â(pos).Ý(), 160);
                }
            }
        }
        else if (!var5 && !this.HorizonCode_Horizon_È(worldIn, pos, false)) {
            worldIn.HorizonCode_Horizon_È(pos, Blocks.áˆº.¥à().HorizonCode_Horizon_È(BlockRedstoneTorch.Õ, state.HorizonCode_Horizon_È(BlockRedstoneTorch.Õ)), 3);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        if (!this.Âµá€(worldIn, pos, state) && this.ŠÂµà == this.à(worldIn, pos, state)) {
            worldIn.HorizonCode_Horizon_È(pos, this, this.HorizonCode_Horizon_È(worldIn));
        }
    }
    
    @Override
    public int Â(final IBlockAccess worldIn, final BlockPos pos, final IBlockState state, final EnumFacing side) {
        return (side == EnumFacing.HorizonCode_Horizon_È) ? this.HorizonCode_Horizon_È(worldIn, pos, state, side) : 0;
    }
    
    @Override
    public Item_1028566121 HorizonCode_Horizon_È(final IBlockState state, final Random rand, final int fortune) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.áˆº);
    }
    
    @Override
    public boolean áŒŠà() {
        return true;
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (this.ŠÂµà) {
            double var5 = pos.HorizonCode_Horizon_È() + 0.5f + (rand.nextFloat() - 0.5f) * 0.2;
            double var6 = pos.Â() + 0.7f + (rand.nextFloat() - 0.5f) * 0.2;
            double var7 = pos.Ý() + 0.5f + (rand.nextFloat() - 0.5f) * 0.2;
            final EnumFacing var8 = (EnumFacing)state.HorizonCode_Horizon_È(BlockRedstoneTorch.Õ);
            if (var8.á().Ø­áŒŠá()) {
                final EnumFacing var9 = var8.Âµá€();
                final double var10 = 0.27000001072883606;
                var5 += 0.27000001072883606 * var9.Ø();
                var6 += 0.2199999988079071;
                var7 += 0.27000001072883606 * var9.áˆºÑ¢Õ();
            }
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.ÇŽÉ, var5, var6, var7, 0.0, 0.0, 0.0, new int[0]);
        }
    }
    
    @Override
    public Item_1028566121 Âµá€(final World worldIn, final BlockPos pos) {
        return Item_1028566121.HorizonCode_Horizon_È(Blocks.áˆº);
    }
    
    @Override
    public boolean Â(final Block other) {
        return other == Blocks.£áŒŠá || other == Blocks.áˆº;
    }
    
    static class HorizonCode_Horizon_È
    {
        BlockPos HorizonCode_Horizon_È;
        long Â;
        private static final String Ý = "CL_00000299";
        
        public HorizonCode_Horizon_È(final BlockPos p_i45688_1_, final long p_i45688_2_) {
            this.HorizonCode_Horizon_È = p_i45688_1_;
            this.Â = p_i45688_2_;
        }
    }
}
