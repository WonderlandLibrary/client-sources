package HORIZON-6-0-SKIDPROTECTION;

public final class BlockHelp
{
    private static Minecraft HorizonCode_Horizon_È;
    
    static {
        BlockHelp.HorizonCode_Horizon_È = Minecraft.áŒŠà();
    }
    
    public static void HorizonCode_Horizon_È(final int x, final int y, final int z) {
        final int blockId = Block.HorizonCode_Horizon_È(Minecraft.áŒŠà().áŒŠÆ.Â(new BlockPos(x, y, z)).Ý());
        int bestSlot = 0;
        float f = -1.0f;
        for (int i1 = 36; i1 < 45; ++i1) {
            try {
                final ItemStack curSlot = Minecraft.áŒŠà().á.ŒÂ.HorizonCode_Horizon_È(i1).HorizonCode_Horizon_È();
                if ((curSlot.HorizonCode_Horizon_È() instanceof ItemTool || curSlot.HorizonCode_Horizon_È() instanceof ItemSword || curSlot.HorizonCode_Horizon_È() instanceof ItemShears) && curSlot.HorizonCode_Horizon_È(Block.HorizonCode_Horizon_È(blockId)) > f) {
                    bestSlot = i1 - 36;
                    f = curSlot.HorizonCode_Horizon_È(Block.HorizonCode_Horizon_È(blockId));
                }
            }
            catch (Exception ex) {}
        }
        if (f != -1.0f) {
            Minecraft.áŒŠà().á.Ø­Ñ¢Ï­Ø­áˆº.Ý = bestSlot;
            BlockHelp.HorizonCode_Horizon_È.Âµá€.Âµá€();
        }
    }
    
    public static Block HorizonCode_Horizon_È(final BlockPos inBlockPos) {
        final IBlockState s = BlockHelp.HorizonCode_Horizon_È.áŒŠÆ.Â(inBlockPos);
        return s.Ý();
    }
    
    public static final float[] HorizonCode_Horizon_È(final double posX, final double posY, final double posZ) {
        final double diffX = posX - BlockHelp.HorizonCode_Horizon_È.á.ŒÏ;
        final double diffZ = posZ - BlockHelp.HorizonCode_Horizon_È.á.Ê;
        final double diffY = posY - (BlockHelp.HorizonCode_Horizon_È.á.Çªà¢ + BlockHelp.HorizonCode_Horizon_È.á.Ðƒáƒ());
        final double dist = MathHelper.HorizonCode_Horizon_È(diffX * diffX + diffZ * diffZ);
        final float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0 / 3.141592653589793));
        return new float[] { MathHelper.à(pitch - BlockHelp.HorizonCode_Horizon_È.á.áƒ), MathHelper.à(yaw - BlockHelp.HorizonCode_Horizon_È.á.É) };
    }
    
    public static boolean HorizonCode_Horizon_È() {
        boolean inLiquid = false;
        final int y = (int)BlockHelp.HorizonCode_Horizon_È.á.à¢.Â;
        for (int x = MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ý); z < MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ó) + 1; ++z) {
                final Block block = BlockHelp.HorizonCode_Horizon_È.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }
    
    public static boolean Â() {
        boolean onIce = false;
        final int y = (int)(BlockHelp.HorizonCode_Horizon_È.á.à¢.Â - 1.0);
        for (int x = MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ý); z < MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ó) + 1; ++z) {
                final Block block = BlockHelp.HorizonCode_Horizon_È.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                if (block != null && !(block instanceof BlockAir) && (block instanceof BlockPackedIce || block instanceof BlockIce)) {
                    onIce = true;
                }
            }
        }
        return onIce;
    }
    
    public static boolean Ý() {
        boolean onLadder = false;
        final int y = (int)(BlockHelp.HorizonCode_Horizon_È.á.à¢.Â - 1.0);
        for (int x = MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ý); z < MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ó) + 1; ++z) {
                final Block block = BlockHelp.HorizonCode_Horizon_È.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLadder)) {
                        return false;
                    }
                    onLadder = true;
                }
            }
        }
        return onLadder || BlockHelp.HorizonCode_Horizon_È.á.i_();
    }
    
    public static boolean Ø­áŒŠá() {
        boolean onLiquid = false;
        final int y = (int)(BlockHelp.HorizonCode_Horizon_È.á.à¢.Â - 0.01);
        for (int x = MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ý); z < MathHelper.Ý(BlockHelp.HorizonCode_Horizon_È.á.à¢.Ó) + 1; ++z) {
                final Block block = BlockHelp.HorizonCode_Horizon_È.áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }
}
