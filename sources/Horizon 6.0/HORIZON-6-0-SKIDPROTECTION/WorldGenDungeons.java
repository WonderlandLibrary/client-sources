package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Random;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import java.util.List;
import org.apache.logging.log4j.Logger;

public class WorldGenDungeons extends WorldGenerator
{
    private static final Logger HorizonCode_Horizon_È;
    private static final String[] Â;
    private static final List Ý;
    private static final String Ø­áŒŠá = "CL_00000425";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
        Â = new String[] { "Skeleton", "Zombie", "Zombie", "Spider" };
        Ý = Lists.newArrayList((Object[])new WeightedRandomChestContent[] { new WeightedRandomChestContent(Items.Û, 0, 1, 1, 10), new WeightedRandomChestContent(Items.áˆºÑ¢Õ, 0, 1, 4, 10), new WeightedRandomChestContent(Items.Ç, 0, 1, 1, 10), new WeightedRandomChestContent(Items.Âµà, 0, 1, 4, 10), new WeightedRandomChestContent(Items.É, 0, 1, 4, 10), new WeightedRandomChestContent(Items.ˆá, 0, 1, 4, 10), new WeightedRandomChestContent(Items.áŒŠáŠ, 0, 1, 1, 10), new WeightedRandomChestContent(Items.£Õ, 0, 1, 1, 1), new WeightedRandomChestContent(Items.ÇŽá, 0, 1, 4, 10), new WeightedRandomChestContent(Items.áˆºÉ, 0, 1, 1, 4), new WeightedRandomChestContent(Items.Ø­È, 0, 1, 1, 4), new WeightedRandomChestContent(Items.ŒÐƒà, 0, 1, 1, 10), new WeightedRandomChestContent(Items.¥Ê, 0, 1, 1, 2), new WeightedRandomChestContent(Items.ÐƒÇŽà, 0, 1, 1, 5), new WeightedRandomChestContent(Items.ÐƒÓ, 0, 1, 1, 1) });
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final World worldIn, final Random p_180709_2_, final BlockPos p_180709_3_) {
        final boolean var4 = true;
        final int var5 = p_180709_2_.nextInt(2) + 2;
        final int var6 = -var5 - 1;
        final int var7 = var5 + 1;
        final boolean var8 = true;
        final boolean var9 = true;
        final int var10 = p_180709_2_.nextInt(2) + 2;
        final int var11 = -var10 - 1;
        final int var12 = var10 + 1;
        int var13 = 0;
        for (int var14 = var6; var14 <= var7; ++var14) {
            for (int var15 = -1; var15 <= 4; ++var15) {
                for (int var16 = var11; var16 <= var12; ++var16) {
                    final BlockPos var17 = p_180709_3_.Â(var14, var15, var16);
                    final Material var18 = worldIn.Â(var17).Ý().Ó();
                    final boolean var19 = var18.Â();
                    if (var15 == -1 && !var19) {
                        return false;
                    }
                    if (var15 == 4 && !var19) {
                        return false;
                    }
                    if ((var14 == var6 || var14 == var7 || var16 == var11 || var16 == var12) && var15 == 0 && worldIn.Ø­áŒŠá(var17) && worldIn.Ø­áŒŠá(var17.Ø­áŒŠá())) {
                        ++var13;
                    }
                }
            }
        }
        if (var13 >= 1 && var13 <= 5) {
            for (int var14 = var6; var14 <= var7; ++var14) {
                for (int var15 = 3; var15 >= -1; --var15) {
                    for (int var16 = var11; var16 <= var12; ++var16) {
                        final BlockPos var17 = p_180709_3_.Â(var14, var15, var16);
                        if (var14 != var6 && var15 != -1 && var16 != var11 && var14 != var7 && var15 != 4 && var16 != var12) {
                            if (worldIn.Â(var17).Ý() != Blocks.ˆáƒ) {
                                worldIn.Ø(var17);
                            }
                        }
                        else if (var17.Â() >= 0 && !worldIn.Â(var17.Âµá€()).Ý().Ó().Â()) {
                            worldIn.Ø(var17);
                        }
                        else if (worldIn.Â(var17).Ý().Ó().Â() && worldIn.Â(var17).Ý() != Blocks.ˆáƒ) {
                            if (var15 == -1 && p_180709_2_.nextInt(4) != 0) {
                                worldIn.HorizonCode_Horizon_È(var17, Blocks.áˆºáˆºÈ.¥à(), 2);
                            }
                            else {
                                worldIn.HorizonCode_Horizon_È(var17, Blocks.Ó.¥à(), 2);
                            }
                        }
                    }
                }
            }
            for (int var14 = 0; var14 < 2; ++var14) {
                for (int var15 = 0; var15 < 3; ++var15) {
                    final int var16 = p_180709_3_.HorizonCode_Horizon_È() + p_180709_2_.nextInt(var5 * 2 + 1) - var5;
                    final int var20 = p_180709_3_.Â();
                    final int var21 = p_180709_3_.Ý() + p_180709_2_.nextInt(var10 * 2 + 1) - var10;
                    final BlockPos var22 = new BlockPos(var16, var20, var21);
                    if (worldIn.Ø­áŒŠá(var22)) {
                        int var23 = 0;
                        for (final EnumFacing var25 : EnumFacing.Ý.HorizonCode_Horizon_È) {
                            if (worldIn.Â(var22.HorizonCode_Horizon_È(var25)).Ý().Ó().Â()) {
                                ++var23;
                            }
                        }
                        if (var23 == 1) {
                            worldIn.HorizonCode_Horizon_È(var22, Blocks.ˆáƒ.Ó(worldIn, var22, Blocks.ˆáƒ.¥à()), 2);
                            final List var26 = WeightedRandomChestContent.HorizonCode_Horizon_È(WorldGenDungeons.Ý, Items.Çªáˆºá.HorizonCode_Horizon_È(p_180709_2_));
                            final TileEntity var27 = worldIn.HorizonCode_Horizon_È(var22);
                            if (var27 instanceof TileEntityChest) {
                                WeightedRandomChestContent.HorizonCode_Horizon_È(p_180709_2_, var26, (IInventory)var27, 8);
                                break;
                            }
                            break;
                        }
                    }
                }
            }
            worldIn.HorizonCode_Horizon_È(p_180709_3_, Blocks.ÇªÓ.¥à(), 2);
            final TileEntity var28 = worldIn.HorizonCode_Horizon_È(p_180709_3_);
            if (var28 instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner)var28).Â().HorizonCode_Horizon_È(this.HorizonCode_Horizon_È(p_180709_2_));
            }
            else {
                WorldGenDungeons.HorizonCode_Horizon_È.error("Failed to fetch mob spawner entity at (" + p_180709_3_.HorizonCode_Horizon_È() + ", " + p_180709_3_.Â() + ", " + p_180709_3_.Ý() + ")");
            }
            return true;
        }
        return false;
    }
    
    private String HorizonCode_Horizon_È(final Random p_76543_1_) {
        return WorldGenDungeons.Â[p_76543_1_.nextInt(WorldGenDungeons.Â.length)];
    }
}
