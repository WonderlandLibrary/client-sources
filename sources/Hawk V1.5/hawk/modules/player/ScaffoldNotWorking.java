package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.modules.Module;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Vec3;

public class ScaffoldNotWorking extends Module {
   hawk.util.Timer timer = new hawk.util.Timer();
   private int slot;
   boolean placing;
   private ScaffoldNotWorking.BlockData blockData;
   private static List<Block> invalid;

   public void onEvent(Event var1) {
      if (var1 instanceof EventMotion) {
         if (var1.isPre()) {
            int var2 = this.getBlockSlot();
            this.blockData = null;
            this.slot = -1;
            if (!this.mc.thePlayer.isSneaking() && var2 != -1) {
               EventMotion var3 = (EventMotion)var1;
               double var4 = Math.cos(Math.toRadians((double)(this.mc.thePlayer.rotationYaw + 90.0F)));
               double var6 = Math.sin(Math.toRadians((double)(this.mc.thePlayer.rotationYaw + 90.0F)));
               double var8 = (double)MovementInput.moveForward * 0.4D * var4 + (double)MovementInput.moveStrafe * 0.4D * var6;
               double var10 = (double)MovementInput.moveForward * 0.4D * var6 - (double)MovementInput.moveStrafe * 0.4D * var4;
               double var12 = this.mc.thePlayer.posX + var8;
               double var14 = this.mc.thePlayer.posY - 1.0D;
               double var16 = this.mc.thePlayer.posZ + var10;
               BlockPos var18 = new BlockPos(var12, var14, var16);
               if (this.mc.theWorld.getBlockState(var18).getBlock() == Blocks.air) {
                  this.blockData = getBlockData(var18, (List)this.blockData);
                  this.slot = var2;
                  if (this.blockData != null && this.mc.thePlayer != null) {
                     var3.setYaw(ScaffoldHelper.getBlockRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[0]);
                     var3.setPitch(ScaffoldHelper.getBlockRotations(this.blockData.position.getX(), this.blockData.position.getY(), this.blockData.position.getZ(), this.blockData.face)[1]);
                  }
               }
            }
         } else if (var1.isPre() && this.blockData != null && this.timer.hasTimeElapsed(75L, true) && this.slot != -1) {
            this.mc.rightClickDelayTimer = 3;
            if (this.mc.gameSettings.keyBindJump.pressed && MovementInput.moveForward == 0.0F && MovementInput.moveStrafe == 0.0F) {
               this.mc.thePlayer.jump();
               this.mc.thePlayer.motionY = 0.0D;
            }

            boolean var19 = this.mc.thePlayer.inventory.currentItem != this.slot;
            if (var19) {
               this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.slot));
            }

            if (this.mc.playerController.func_178890_a(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventoryContainer.getSlot(36 + this.slot).getStack(), this.blockData.position, this.blockData.face, new Vec3((double)this.blockData.position.getX(), (double)this.blockData.position.getY(), (double)this.blockData.position.getZ()))) {
               this.mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
            }

            if (var19) {
               this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            }
         }
      }

   }

   public static ScaffoldNotWorking.BlockData getBlockData(BlockPos var0, List var1) {
      System.out.println(String.valueOf((new StringBuilder()).append(var1)));
      return !var1.contains(Minecraft.getMinecraft().theWorld.getBlockState(var0.add(0, -1, 0)).getBlock()) ? new ScaffoldNotWorking.BlockData(var0.add(0, -1, 0), EnumFacing.UP) : (!var1.contains(Minecraft.getMinecraft().theWorld.getBlockState(var0.add(-1, 0, 0)).getBlock()) ? new ScaffoldNotWorking.BlockData(var0.add(-1, 0, 0), EnumFacing.EAST) : (!var1.contains(Minecraft.getMinecraft().theWorld.getBlockState(var0.add(1, 0, 0)).getBlock()) ? new ScaffoldNotWorking.BlockData(var0.add(1, 0, 0), EnumFacing.WEST) : (!var1.contains(Minecraft.getMinecraft().theWorld.getBlockState(var0.add(0, 0, -1)).getBlock()) ? new ScaffoldNotWorking.BlockData(var0.add(0, 0, -1), EnumFacing.SOUTH) : (!var1.contains(Minecraft.getMinecraft().theWorld.getBlockState(var0.add(0, 0, 1)).getBlock()) ? new ScaffoldNotWorking.BlockData(var0.add(0, 0, 1), EnumFacing.NORTH) : null))));
   }

   public static Block getBlock(int var0, int var1, int var2) {
      return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(var0, var1, var2)).getBlock();
   }

   public ScaffoldNotWorking() {
      super("Crash the game - Scaffold", 0, Module.Category.PLAYER);
   }

   private int getBlockSlot() {
      for(int var1 = 36; var1 < 45; ++var1) {
         ItemStack var2 = this.mc.thePlayer.inventoryContainer.getSlot(var1).getStack();
         if (var2 != null && var2.getItem() instanceof ItemBlock) {
            return var1 - 36;
         }
      }

      return -1;
   }

   public static class BlockData {
      public BlockPos position;
      public EnumFacing face;

      public BlockData(BlockPos var1, EnumFacing var2) {
         this.position = var1;
         this.face = var2;
      }
   }
}
