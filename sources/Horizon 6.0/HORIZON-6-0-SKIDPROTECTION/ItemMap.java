package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.HashMultiset;

public class ItemMap extends ItemMapBase
{
    private static final String à = "CL_00000047";
    
    protected ItemMap() {
        this.HorizonCode_Horizon_È(true);
    }
    
    public static MapData HorizonCode_Horizon_È(final int p_150912_0_, final World worldIn) {
        final String var2 = "map_" + p_150912_0_;
        MapData var3 = (MapData)worldIn.HorizonCode_Horizon_È(MapData.class, var2);
        if (var3 == null) {
            var3 = new MapData(var2);
            worldIn.HorizonCode_Horizon_È(var2, var3);
        }
        return var3;
    }
    
    public MapData HorizonCode_Horizon_È(final ItemStack p_77873_1_, final World worldIn) {
        String var3 = "map_" + p_77873_1_.Ø();
        MapData var4 = (MapData)worldIn.HorizonCode_Horizon_È(MapData.class, var3);
        if (var4 == null && !worldIn.ŠÄ) {
            p_77873_1_.Â(worldIn.Â("map"));
            var3 = "map_" + p_77873_1_.Ø();
            var4 = new MapData(var3);
            var4.Âµá€ = 3;
            var4.HorizonCode_Horizon_È(worldIn.ŒÏ().Ý(), worldIn.ŒÏ().Âµá€(), var4.Âµá€);
            var4.Ø­áŒŠá = (byte)worldIn.£à.µà();
            var4.Ø­áŒŠá();
            worldIn.HorizonCode_Horizon_È(var3, var4);
        }
        return var4;
    }
    
    public void HorizonCode_Horizon_È(final World worldIn, final Entity p_77872_2_, final MapData p_77872_3_) {
        if (worldIn.£à.µà() == p_77872_3_.Ø­áŒŠá && p_77872_2_ instanceof EntityPlayer) {
            final int var4 = 1 << p_77872_3_.Âµá€;
            final int var5 = p_77872_3_.Â;
            final int var6 = p_77872_3_.Ý;
            final int var7 = MathHelper.Ý(p_77872_2_.ŒÏ - var5) / var4 + 64;
            final int var8 = MathHelper.Ý(p_77872_2_.Ê - var6) / var4 + 64;
            int var9 = 128 / var4;
            if (worldIn.£à.Å()) {
                var9 /= 2;
            }
            final MapData.HorizonCode_Horizon_È horizonCode_Horizon_È;
            final MapData.HorizonCode_Horizon_È var10 = horizonCode_Horizon_È = p_77872_3_.HorizonCode_Horizon_È((EntityPlayer)p_77872_2_);
            ++horizonCode_Horizon_È.Â;
            boolean var11 = false;
            for (int var12 = var7 - var9 + 1; var12 < var7 + var9; ++var12) {
                if ((var12 & 0xF) == (var10.Â & 0xF) || var11) {
                    var11 = false;
                    double var13 = 0.0;
                    for (int var14 = var8 - var9 - 1; var14 < var8 + var9; ++var14) {
                        if (var12 >= 0 && var14 >= -1 && var12 < 128 && var14 < 128) {
                            final int var15 = var12 - var7;
                            final int var16 = var14 - var8;
                            final boolean var17 = var15 * var15 + var16 * var16 > (var9 - 2) * (var9 - 2);
                            final int var18 = (var5 / var4 + var12 - 64) * var4;
                            final int var19 = (var6 / var4 + var14 - 64) * var4;
                            final HashMultiset var20 = HashMultiset.create();
                            final Chunk var21 = worldIn.à(new BlockPos(var18, 0, var19));
                            if (!var21.Ø()) {
                                final int var22 = var18 & 0xF;
                                final int var23 = var19 & 0xF;
                                int var24 = 0;
                                double var25 = 0.0;
                                if (worldIn.£à.Å()) {
                                    int var26 = var18 + var19 * 231871;
                                    var26 = var26 * var26 * 31287121 + var26 * 11;
                                    if ((var26 >> 20 & 0x1) == 0x0) {
                                        var20.add((Object)Blocks.Âµá€.Â(Blocks.Âµá€.¥à().HorizonCode_Horizon_È(BlockDirt.Õ, BlockDirt.HorizonCode_Horizon_È.HorizonCode_Horizon_È)), 10);
                                    }
                                    else {
                                        var20.add((Object)Blocks.Ý.Â(Blocks.Ý.¥à().HorizonCode_Horizon_È(BlockStone.Õ, BlockStone.HorizonCode_Horizon_È.HorizonCode_Horizon_È)), 100);
                                    }
                                    var25 = 100.0;
                                }
                                else {
                                    for (int var26 = 0; var26 < var4; ++var26) {
                                        for (int var27 = 0; var27 < var4; ++var27) {
                                            int var28 = var21.Â(var26 + var22, var27 + var23) + 1;
                                            IBlockState var29 = Blocks.Â.¥à();
                                            if (var28 > 1) {
                                                do {
                                                    --var28;
                                                    var29 = var21.Ø­áŒŠá(new BlockPos(var26 + var22, var28, var27 + var23));
                                                } while (var29.Ý().Â(var29) == MapColor.Â && var28 > 0);
                                                if (var28 > 0 && var29.Ý().Ó().HorizonCode_Horizon_È()) {
                                                    int var30 = var28 - 1;
                                                    Block var31;
                                                    do {
                                                        var31 = var21.HorizonCode_Horizon_È(var26 + var22, var30--, var27 + var23);
                                                        ++var24;
                                                    } while (var30 > 0 && var31.Ó().HorizonCode_Horizon_È());
                                                }
                                            }
                                            var25 += var28 / (var4 * var4);
                                            var20.add((Object)var29.Ý().Â(var29));
                                        }
                                    }
                                }
                                var24 /= var4 * var4;
                                double var32 = (var25 - var13) * 4.0 / (var4 + 4) + ((var12 + var14 & 0x1) - 0.5) * 0.4;
                                byte var33 = 1;
                                if (var32 > 0.6) {
                                    var33 = 2;
                                }
                                if (var32 < -0.6) {
                                    var33 = 0;
                                }
                                final MapColor var34 = (MapColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)var20), (Object)MapColor.Â);
                                if (var34 == MapColor.£á) {
                                    var32 = var24 * 0.1 + (var12 + var14 & 0x1) * 0.2;
                                    var33 = 1;
                                    if (var32 < 0.5) {
                                        var33 = 2;
                                    }
                                    if (var32 > 0.9) {
                                        var33 = 0;
                                    }
                                }
                                var13 = var25;
                                if (var14 >= 0 && var15 * var15 + var16 * var16 < var9 * var9 && (!var17 || (var12 + var14 & 0x1) != 0x0)) {
                                    final byte var35 = p_77872_3_.Ó[var12 + var14 * 128];
                                    final byte var36 = (byte)(var34.ŠÂµà * 4 + var33);
                                    if (var35 != var36) {
                                        p_77872_3_.Ó[var12 + var14 * 128] = var36;
                                        p_77872_3_.HorizonCode_Horizon_È(var12, var14);
                                        var11 = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        if (!worldIn.ŠÄ) {
            final MapData var6 = this.HorizonCode_Horizon_È(stack, worldIn);
            if (entityIn instanceof EntityPlayer) {
                final EntityPlayer var7 = (EntityPlayer)entityIn;
                var6.HorizonCode_Horizon_È(var7, stack);
            }
            if (isSelected) {
                this.HorizonCode_Horizon_È(worldIn, entityIn, var6);
            }
        }
    }
    
    @Override
    public Packet Ø­áŒŠá(final ItemStack p_150911_1_, final World worldIn, final EntityPlayer p_150911_3_) {
        return this.HorizonCode_Horizon_È(p_150911_1_, worldIn).HorizonCode_Horizon_È(p_150911_1_, worldIn, p_150911_3_);
    }
    
    @Override
    public void Ý(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
        if (stack.£á() && stack.Å().£á("map_is_scaling")) {
            final MapData var4 = Items.ˆØ.HorizonCode_Horizon_È(stack, worldIn);
            stack.Â(worldIn.Â("map"));
            final MapData var5 = new MapData("map_" + stack.Ø());
            var5.Âµá€ = (byte)(var4.Âµá€ + 1);
            if (var5.Âµá€ > 4) {
                var5.Âµá€ = 4;
            }
            var5.HorizonCode_Horizon_È(var4.Â, var4.Ý, var5.Âµá€);
            var5.Ø­áŒŠá = var4.Ø­áŒŠá;
            var5.Ø­áŒŠá();
            worldIn.HorizonCode_Horizon_È("map_" + stack.Ø(), var5);
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        final MapData var5 = this.HorizonCode_Horizon_È(stack, playerIn.Ï­Ðƒà);
        if (advanced) {
            if (var5 == null) {
                tooltip.add("Unknown map");
            }
            else {
                tooltip.add("Scaling at 1:" + (1 << var5.Âµá€));
                tooltip.add("(Level " + var5.Âµá€ + "/" + 4 + ")");
            }
        }
    }
}
