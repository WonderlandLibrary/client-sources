package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.LinkedList;
import com.google.common.collect.Lists;

public class BlockSponge extends Block
{
    public static final PropertyBool Õ;
    private static final String à¢ = "CL_00000311";
    
    static {
        Õ = PropertyBool.HorizonCode_Horizon_È("wet");
    }
    
    protected BlockSponge() {
        super(Material.ˆÏ­);
        this.Ø(this.á€.Â().HorizonCode_Horizon_È(BlockSponge.Õ, false));
        this.HorizonCode_Horizon_È(CreativeTabs.Â);
    }
    
    @Override
    public int Ø­áŒŠá(final IBlockState state) {
        return ((boolean)state.HorizonCode_Horizon_È(BlockSponge.Õ)) ? 1 : 0;
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state) {
        this.Âµá€(worldIn, pos, state);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final World worldIn, final BlockPos pos, final IBlockState state, final Block neighborBlock) {
        this.Âµá€(worldIn, pos, state);
        super.HorizonCode_Horizon_È(worldIn, pos, state, neighborBlock);
    }
    
    protected void Âµá€(final World worldIn, final BlockPos p_176311_2_, final IBlockState p_176311_3_) {
        if (!(boolean)p_176311_3_.HorizonCode_Horizon_È(BlockSponge.Õ) && this.áŒŠÆ(worldIn, p_176311_2_)) {
            worldIn.HorizonCode_Horizon_È(p_176311_2_, p_176311_3_.HorizonCode_Horizon_È(BlockSponge.Õ, true), 2);
            worldIn.Â(2001, p_176311_2_, Block.HorizonCode_Horizon_È(Blocks.ÂµÈ));
        }
    }
    
    private boolean áŒŠÆ(final World worldIn, final BlockPos p_176312_2_) {
        final LinkedList var3 = Lists.newLinkedList();
        final ArrayList var4 = Lists.newArrayList();
        var3.add(new Tuple(p_176312_2_, 0));
        int var5 = 0;
        while (!var3.isEmpty()) {
            final Tuple var6 = var3.poll();
            final BlockPos var7 = (BlockPos)var6.HorizonCode_Horizon_È();
            final int var8 = (int)var6.Â();
            for (final EnumFacing var12 : EnumFacing.values()) {
                final BlockPos var13 = var7.HorizonCode_Horizon_È(var12);
                if (worldIn.Â(var13).Ý().Ó() == Material.Ø) {
                    worldIn.HorizonCode_Horizon_È(var13, Blocks.Â.¥à(), 2);
                    var4.add(var13);
                    ++var5;
                    if (var8 < 6) {
                        var3.add(new Tuple(var13, var8 + 1));
                    }
                }
            }
            if (var5 > 64) {
                break;
            }
        }
        final Iterator var14 = var4.iterator();
        while (var14.hasNext()) {
            final BlockPos var7 = var14.next();
            worldIn.Â(var7, Blocks.Â);
        }
        return var5 > 0;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List list) {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
    }
    
    @Override
    public IBlockState Ý(final int meta) {
        return this.¥à().HorizonCode_Horizon_È(BlockSponge.Õ, (meta & 0x1) == 0x1);
    }
    
    @Override
    public int Ý(final IBlockState state) {
        return ((boolean)state.HorizonCode_Horizon_È(BlockSponge.Õ)) ? 1 : 0;
    }
    
    @Override
    protected BlockState à¢() {
        return new BlockState(this, new IProperty[] { BlockSponge.Õ });
    }
    
    @Override
    public void Ý(final World worldIn, final BlockPos pos, final IBlockState state, final Random rand) {
        if (state.HorizonCode_Horizon_È(BlockSponge.Õ)) {
            final EnumFacing var5 = EnumFacing.HorizonCode_Horizon_È(rand);
            if (var5 != EnumFacing.Â && !World.HorizonCode_Horizon_È(worldIn, pos.HorizonCode_Horizon_È(var5))) {
                double var6 = pos.HorizonCode_Horizon_È();
                double var7 = pos.Â();
                double var8 = pos.Ý();
                if (var5 == EnumFacing.HorizonCode_Horizon_È) {
                    var7 -= 0.05;
                    var6 += rand.nextDouble();
                    var8 += rand.nextDouble();
                }
                else {
                    var7 += rand.nextDouble() * 0.8;
                    if (var5.á() == EnumFacing.HorizonCode_Horizon_È.HorizonCode_Horizon_È) {
                        var8 += rand.nextDouble();
                        if (var5 == EnumFacing.Ó) {
                            ++var6;
                        }
                        else {
                            var6 += 0.05;
                        }
                    }
                    else {
                        var6 += rand.nextDouble();
                        if (var5 == EnumFacing.Ø­áŒŠá) {
                            ++var8;
                        }
                        else {
                            var8 += 0.05;
                        }
                    }
                }
                worldIn.HorizonCode_Horizon_È(EnumParticleTypes.¥Æ, var6, var7, var8, 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
}
