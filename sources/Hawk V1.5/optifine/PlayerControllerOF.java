package optifine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PlayerControllerOF extends PlayerControllerMP {
   private boolean acting = false;

   public boolean func_180511_b(BlockPos var1, EnumFacing var2) {
      this.acting = true;
      boolean var3 = super.func_180511_b(var1, var2);
      this.acting = false;
      return var3;
   }

   public boolean func_178890_a(EntityPlayerSP var1, WorldClient var2, ItemStack var3, BlockPos var4, EnumFacing var5, Vec3 var6) {
      this.acting = true;
      boolean var7 = super.func_178890_a(var1, var2, var3, var4, var5, var6);
      this.acting = false;
      return var7;
   }

   public PlayerControllerOF(Minecraft var1, NetHandlerPlayClient var2) {
      super(var1, var2);
   }

   public boolean isActing() {
      return this.acting;
   }

   public boolean sendUseItem(EntityPlayer var1, World var2, ItemStack var3) {
      this.acting = true;
      boolean var4 = super.sendUseItem(var1, var2, var3);
      this.acting = false;
      return var4;
   }

   public boolean func_180512_c(BlockPos var1, EnumFacing var2) {
      this.acting = true;
      boolean var3 = super.func_180512_c(var1, var2);
      this.acting = false;
      return var3;
   }
}
