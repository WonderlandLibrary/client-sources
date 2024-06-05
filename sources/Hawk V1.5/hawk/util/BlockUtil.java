package hawk.util;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class BlockUtil {
   public static void placeHeldItemUnderPlayer() {
      Minecraft var0 = Minecraft.getMinecraft();
      BlockPos var1 = new BlockPos(var0.thePlayer.posX, var0.thePlayer.getEntityBoundingBox().minY - 1.0D, var0.thePlayer.posZ);
      Vec3 var2 = new Vec3(var0.thePlayer.posX, var0.thePlayer.getEntityBoundingBox().minY - 1.0D, var0.thePlayer.posZ);
      var0.playerController.func_178890_a(var0.thePlayer, var0.theWorld, (ItemStack)null, var1, EnumFacing.UP, var2);
   }
}
