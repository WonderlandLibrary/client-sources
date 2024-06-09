package HORIZON-6-0-SKIDPROTECTION;

public class ItemEnderEye extends Item_1028566121
{
    private static final String à = "CL_00000026";
    
    public ItemEnderEye() {
        this.HorizonCode_Horizon_È(CreativeTabs.Ó);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        final IBlockState var9 = worldIn.Â(pos);
        if (!playerIn.HorizonCode_Horizon_È(pos.HorizonCode_Horizon_È(side), side, stack) || var9.Ý() != Blocks.¥áŠ || (boolean)var9.HorizonCode_Horizon_È(BlockEndPortalFrame.à¢)) {
            return false;
        }
        if (worldIn.ŠÄ) {
            return true;
        }
        worldIn.HorizonCode_Horizon_È(pos, var9.HorizonCode_Horizon_È(BlockEndPortalFrame.à¢, true), 2);
        worldIn.Âµá€(pos, Blocks.¥áŠ);
        --stack.Â;
        for (int var10 = 0; var10 < 16; ++var10) {
            final double var11 = pos.HorizonCode_Horizon_È() + (5.0f + ItemEnderEye.Ý.nextFloat() * 6.0f) / 16.0f;
            final double var12 = pos.Â() + 0.8125f;
            final double var13 = pos.Ý() + (5.0f + ItemEnderEye.Ý.nextFloat() * 6.0f) / 16.0f;
            final double var14 = 0.0;
            final double var15 = 0.0;
            final double var16 = 0.0;
            worldIn.HorizonCode_Horizon_È(EnumParticleTypes.á, var11, var12, var13, var14, var15, var16, new int[0]);
        }
        final EnumFacing var17 = (EnumFacing)var9.HorizonCode_Horizon_È(BlockEndPortalFrame.Õ);
        int var18 = 0;
        int var19 = 0;
        boolean var20 = false;
        boolean var21 = true;
        final EnumFacing var22 = var17.Ó();
        for (int var23 = -2; var23 <= 2; ++var23) {
            final BlockPos var24 = pos.HorizonCode_Horizon_È(var22, var23);
            final IBlockState var25 = worldIn.Â(var24);
            if (var25.Ý() == Blocks.¥áŠ) {
                if (!(boolean)var25.HorizonCode_Horizon_È(BlockEndPortalFrame.à¢)) {
                    var21 = false;
                    break;
                }
                var19 = var23;
                if (!var20) {
                    var18 = var23;
                    var20 = true;
                }
            }
        }
        if (var21 && var19 == var18 + 2) {
            BlockPos var26 = pos.HorizonCode_Horizon_È(var17, 4);
            for (int var27 = var18; var27 <= var19; ++var27) {
                final BlockPos var28 = var26.HorizonCode_Horizon_È(var22, var27);
                final IBlockState var29 = worldIn.Â(var28);
                if (var29.Ý() != Blocks.¥áŠ || !(boolean)var29.HorizonCode_Horizon_È(BlockEndPortalFrame.à¢)) {
                    var21 = false;
                    break;
                }
            }
            for (int var27 = var18 - 1; var27 <= var19 + 1; var27 += 4) {
                var26 = pos.HorizonCode_Horizon_È(var22, var27);
                for (int var30 = 1; var30 <= 3; ++var30) {
                    final BlockPos var31 = var26.HorizonCode_Horizon_È(var17, var30);
                    final IBlockState var32 = worldIn.Â(var31);
                    if (var32.Ý() != Blocks.¥áŠ || !(boolean)var32.HorizonCode_Horizon_È(BlockEndPortalFrame.à¢)) {
                        var21 = false;
                        break;
                    }
                }
            }
            if (var21) {
                for (int var27 = var18; var27 <= var19; ++var27) {
                    var26 = pos.HorizonCode_Horizon_È(var22, var27);
                    for (int var30 = 1; var30 <= 3; ++var30) {
                        final BlockPos var31 = var26.HorizonCode_Horizon_È(var17, var30);
                        worldIn.HorizonCode_Horizon_È(var31, Blocks.Ï­Ä.¥à(), 2);
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final MovingObjectPosition var4 = this.HorizonCode_Horizon_È(worldIn, playerIn, false);
        if (var4 != null && var4.HorizonCode_Horizon_È == MovingObjectPosition.HorizonCode_Horizon_È.Â && worldIn.Â(var4.HorizonCode_Horizon_È()).Ý() == Blocks.¥áŠ) {
            return itemStackIn;
        }
        if (!worldIn.ŠÄ) {
            final BlockPos var5 = worldIn.HorizonCode_Horizon_È("Stronghold", new BlockPos(playerIn));
            if (var5 != null) {
                final EntityEnderEye var6 = new EntityEnderEye(worldIn, playerIn.ŒÏ, playerIn.Çªà¢, playerIn.Ê);
                var6.HorizonCode_Horizon_È(var5);
                worldIn.HorizonCode_Horizon_È(var6);
                worldIn.HorizonCode_Horizon_È((Entity)playerIn, "random.bow", 0.5f, 0.4f / (ItemEnderEye.Ý.nextFloat() * 0.4f + 0.8f));
                worldIn.HorizonCode_Horizon_È(null, 1002, new BlockPos(playerIn), 0);
                if (!playerIn.áˆºáˆºáŠ.Ø­áŒŠá) {
                    --itemStackIn.Â;
                }
                playerIn.HorizonCode_Horizon_È(StatList.á€[Item_1028566121.HorizonCode_Horizon_È(this)]);
            }
        }
        return itemStackIn;
    }
}
