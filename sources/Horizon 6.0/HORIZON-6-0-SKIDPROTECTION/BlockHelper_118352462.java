package HORIZON-6-0-SKIDPROTECTION;

public final class BlockHelper_118352462
{
    public static int HorizonCode_Horizon_È(final int x, final int y, final int z) {
        final Block block = Minecraft.áŒŠà().áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
        int slot = 0;
        float dmg = 0.1f;
        for (int index = 36; index < 45; ++index) {
            final ItemStack itemStack = Minecraft.áŒŠà().á.ŒÂ.HorizonCode_Horizon_È(index).HorizonCode_Horizon_È();
            if (itemStack != null && block != null && itemStack.HorizonCode_Horizon_È().HorizonCode_Horizon_È(itemStack, block) > dmg) {
                slot = index - 36;
                dmg = itemStack.HorizonCode_Horizon_È().HorizonCode_Horizon_È(itemStack, block);
            }
        }
        if (dmg > 0.1f) {
            return slot;
        }
        return Minecraft.áŒŠà().á.Ø­Ñ¢Ï­Ø­áˆº.Ý;
    }
    
    public static float[] HorizonCode_Horizon_È(final int x, final int y, final int z, final EnumFacing facing) {
        final Entity temp = new EntitySnowball(Minecraft.áŒŠà().áŒŠÆ);
        temp.ŒÏ = x + 0.5;
        temp.Çªà¢ = y + 0.5;
        temp.Ê = z + 0.5;
        final Entity entity = temp;
        entity.ŒÏ += facing.ˆÏ­().HorizonCode_Horizon_È() * 0.25;
        final Entity entity2 = temp;
        entity2.Çªà¢ += facing.ˆÏ­().Â() * 0.25;
        final Entity entity3 = temp;
        entity3.Ê += facing.ˆÏ­().Ý() * 0.25;
        return EntityHelper.Ø­áŒŠá(temp);
    }
    
    public static Block HorizonCode_Horizon_È(final BlockPos inBlockPos) {
        final IBlockState s = Minecraft.áŒŠà().áŒŠÆ.Â(inBlockPos);
        return s.Ý();
    }
    
    public static float[] HorizonCode_Horizon_È(final Entity entity, final int x, final int y, final int z) {
        final double var4 = x - entity.ŒÏ + 0.5;
        final double var5 = z - entity.Ê + 0.5;
        final double var6 = y - (entity.Çªà¢ + entity.Ðƒáƒ() - 1.0);
        final double var7 = MathHelper.HorizonCode_Horizon_È(var4 * var4 + var5 * var5);
        final float var8 = (float)(Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
        return new float[] { var8, (float)(-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793)) };
    }
    
    public static boolean HorizonCode_Horizon_È() {
        boolean inLiquid = false;
        final int y = (int)Minecraft.áŒŠà().á.à¢.Â;
        for (int x = MathHelper.Ý(Minecraft.áŒŠà().á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ý); z < MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ó) + 1; ++z) {
                final Block block = Minecraft.áŒŠà().áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
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
        final int y = (int)(Minecraft.áŒŠà().á.à¢.Â - 1.0);
        for (int x = MathHelper.Ý(Minecraft.áŒŠà().á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ý); z < MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ó) + 1; ++z) {
                final Block block = Minecraft.áŒŠà().áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                if (block != null && !(block instanceof BlockAir) && (block instanceof BlockPackedIce || block instanceof BlockIce)) {
                    onIce = true;
                }
            }
        }
        return onIce;
    }
    
    public static boolean Ý() {
        boolean onLadder = false;
        final int y = (int)(Minecraft.áŒŠà().á.à¢.Â - 1.0);
        for (int x = MathHelper.Ý(Minecraft.áŒŠà().á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ý); z < MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ó) + 1; ++z) {
                final Block block = Minecraft.áŒŠà().áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
                if (block != null && !(block instanceof BlockAir)) {
                    if (!(block instanceof BlockLadder)) {
                        return false;
                    }
                    onLadder = true;
                }
            }
        }
        return onLadder || Minecraft.áŒŠà().á.i_();
    }
    
    public static boolean Ø­áŒŠá() {
        boolean onLiquid = false;
        final int y = (int)(Minecraft.áŒŠà().á.à¢.Â - 0.01);
        for (int x = MathHelper.Ý(Minecraft.áŒŠà().á.à¢.HorizonCode_Horizon_È); x < MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ø­áŒŠá) + 1; ++x) {
            for (int z = MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ý); z < MathHelper.Ý(Minecraft.áŒŠà().á.à¢.Ó) + 1; ++z) {
                final Block block = Minecraft.áŒŠà().áŒŠÆ.Â(new BlockPos(x, y, z)).Ý();
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
