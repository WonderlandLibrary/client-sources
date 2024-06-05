package hawk.modules.player;

import hawk.events.Event;
import hawk.events.listeners.EventMotion;
import hawk.modules.Module;
import hawk.settings.BooleanSetting;
import hawk.settings.NumberSetting;
import hawk.settings.Setting;
import hawk.util.BlockUtils2;
import hawk.util.RotationUtils;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class ScaffoldHopeItWorks extends Module {
   private hawk.util.Timer boosterTimer = new hawk.util.Timer();
   int dorotations = 1;
   private BlockUtils2.BlockData blockData;
   public BooleanSetting stopMovingOnPlace = new BooleanSetting("Stop Moving on place", true, this);
   private int delay;
   int currentItem;
   public BooleanSetting safewalk = new BooleanSetting("Safewalk", true, this);
   private hawk.util.Timer timer = new hawk.util.Timer();
   double oldY;
   public static boolean isEnabled = false;
   public static float yaw;
   public float pitch;
   int delayplace = 0;
   public NumberSetting timerspeed = new NumberSetting("Timer", 1.0D, 0.1D, 4.0D, 0.1D, this);
   public BooleanSetting sneakOnPlace = new BooleanSetting("Sneak on place", false, this);
   public BooleanSetting noSprint = new BooleanSetting("No sprint", false, this);
   int rotating = 0;
   public long LastBuild;
   public static Minecraft mc = Minecraft.getMinecraft();
   int currentSlot;
   public BooleanSetting timerBoost = new BooleanSetting("Timer Boost", true, this);

   public void onEnable() {
      isEnabled = true;
      this.boosterTimer.reset();
      this.oldY = mc.thePlayer.posY;
   }

   public void onDisable() {
      isEnabled = false;
      mc.gameSettings.keyBindSneak.pressed = false;
      mc.timer.timerSpeed = 1.0F;
      this.delayplace = 0;
   }

   public ScaffoldHopeItWorks() {
      super("Scaffold", 44, Module.Category.PLAYER);
      this.addSettings(new Setting[]{this.noSprint, this.safewalk, this.sneakOnPlace, this.timerspeed, this.timerBoost, this.stopMovingOnPlace});
   }

   private void updateHotbarHypixel() {
      ItemStack var1 = new ItemStack(Item.getItemById(261));

      try {
         for(int var2 = 36; var2 < 45; ++var2) {
            int var3 = var2 - 36;
            if (!Container.canAddItemToSlot(mc.thePlayer.inventoryContainer.getSlot(var2), var1, true) && mc.thePlayer.inventoryContainer.getSlot(var2).getStack().getItem() instanceof ItemBlock && mc.thePlayer.inventoryContainer.getSlot(var2).getStack() != null) {
               if (mc.thePlayer.inventory.currentItem != var3) {
                  mc.thePlayer.inventory.currentItem = var3;
                  this.currentItem = var3;
                  mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                  mc.playerController.updateController();
               }
               break;
            }
         }
      } catch (Exception var4) {
      }

   }

   public void onEvent(Event var1) {
      if (var1 instanceof EventMotion) {
         EventMotion var2;
         if (var1.isPre()) {
            var2 = (EventMotion)var1;
            if (this.blockData != null && mc.thePlayer != null && mc.theWorld != null) {
               float[] var3 = RotationUtils.getRotationFromPosition((double)this.blockData.position.getX(), (double)this.blockData.position.getY(), (double)this.blockData.position.getZ());
            }

            mc.thePlayer.rotationYawHead = ((EventMotion)var1).getYaw();
            mc.thePlayer.renderYawOffset = ((EventMotion)var1).getYaw();
         }

         if (this.timerBoost.isEnabled()) {
            if (!this.boosterTimer.hasTimeElapsed(1500L, false)) {
               mc.timer.timerSpeed = 1.75F;
            } else {
               mc.timer.timerSpeed = 1.0F;
            }
         }

         if (this.noSprint.isEnabled()) {
            mc.thePlayer.setSprinting(false);
         }

         if (this.sneakOnPlace.isEnabled() && this.timer.hasTimeElapsed(50L, true)) {
            mc.gameSettings.keyBindSneak.pressed = false;
         }

         if (!this.sneakOnPlace.isEnabled() && mc.gameSettings.keyBindSneak.pressed) {
            mc.gameSettings.keyBindSneak.pressed = false;
         }

         var2 = (EventMotion)var1;
         if (mc.thePlayer.getCurrentEquippedItem() == null) {
            return;
         }

         if (mc.thePlayer.getCurrentEquippedItem().getItem() == Item.getItemById(0)) {
            return;
         }

         if (!(mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock)) {
            return;
         }

         if (var1.isPre()) {
            mc.timer.timerSpeed = (float)this.timerspeed.getValue();
            if (this.sneakOnPlace.isEnabled()) {
               mc.thePlayer.setSneaking(true);
            }

            if (mc.theWorld == null || mc.thePlayer == null) {
               return;
            }

            try {
               this.blockData = null;
               if (mc.thePlayer.getHeldItem() != null) {
                  this.updateHotbarHypixel();
                  BlockPos var7 = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 1.0D, mc.thePlayer.posZ);
                  this.blockData = this.getBlockData(var7);
                  float[] var4 = RotationUtils.getRotationFromPosition((double)this.blockData.position.getX(), (double)this.blockData.position.getY(), (double)this.blockData.position.getZ());
                  mc.thePlayer.rotationYawHead = var2.getYaw();
                  mc.thePlayer.renderYawOffset = var2.getYaw();
                  var2.setYaw(var4[0]);
                  var2.setPitch(85.0F);
                  if (mc.gameSettings.keyBindJump.getIsKeyPressed()) {
                     float var10000 = mc.thePlayer.moveForward;
                  }

                  if (this.blockData == null) {
                     return;
                  }

                  if (mc.theWorld == null || mc.thePlayer == null) {
                     return;
                  }

                  Random var5 = new Random();
                  if (this.timer.hasTimeElapsed((long)(1 + var5.nextInt(9)), this.expanded) && mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3((double)this.blockData.position.getX(), (double)this.blockData.position.getY(), (double)this.blockData.position.getZ()))) {
                     mc.thePlayer.swingItem();
                     if (this.stopMovingOnPlace.isEnabled()) {
                        mc.thePlayer.motionX = 0.0D;
                        mc.thePlayer.motionZ = 0.0D;
                        mc.thePlayer.moveForward = 0.0F;
                     }

                     var2.setYaw(var4[0]);
                     var2.setPitch(85.0F);
                     if (this.sneakOnPlace.enabled) {
                        mc.gameSettings.keyBindSneak.pressed = true;
                     }

                     mc.thePlayer.rotationYawHead = var2.getYaw();
                     mc.thePlayer.renderYawOffset = var2.getYaw();
                  }
               }
            } catch (Exception var6) {
            }
         }

         if (var1.isPost()) {
            if (this.blockData == null) {
               return;
            }

            if (mc.theWorld == null || mc.thePlayer == null) {
               return;
            }

            Random var8 = new Random();
            if (this.timer.hasTimeElapsed((long)(1 + var8.nextInt(9)), this.expanded) && mc.playerController.func_178890_a(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem(), this.blockData.position, this.blockData.face, new Vec3((double)this.blockData.position.getX(), (double)this.blockData.position.getY(), (double)this.blockData.position.getZ()))) {
               if (this.sneakOnPlace.enabled) {
                  mc.gameSettings.keyBindSneak.pressed = true;
               }

               mc.thePlayer.swingItem();
               this.LastBuild = System.currentTimeMillis();
            }
         }
      }

   }

   public static float[] getFacingRotations(int var0, int var1, int var2, EnumFacing var3, boolean var4) {
      Minecraft.getMinecraft();
      EntitySnowball var5 = new EntitySnowball(mc.theWorld);
      if (var4) {
         String var6;
         switch((var6 = var3.getName()).hashCode()) {
         case 3739:
            if (var6.equals("up")) {
               var5.posX = (double)var0 + 0.5D;
               var5.posY = (double)var1 + 0.5D;
               var5.posZ = (double)var2 + 0.5D;
            }
            break;
         case 3105789:
            if (var6.equals("east")) {
               var5.posX = (double)(var0 + 1);
               var5.posY = (double)var1 + 0.5D;
               var5.posZ = (double)var2 + 0.5D;
            }
            break;
         case 3645871:
            if (var6.equals("west")) {
               var5.posX = (double)var0;
               var5.posY = (double)var1 + 0.5D;
               var5.posZ = (double)var2 + 0.5D;
            }
            break;
         case 105007365:
            if (var6.equals("north")) {
               var5.posX = (double)var0 + 0.5D;
               var5.posY = (double)var1 + 0.5D;
               var5.posZ = (double)var2;
            }
            break;
         case 109627853:
            if (var6.equals("south")) {
               var5.posX = (double)var0 + 0.5D;
               var5.posY = (double)var1 + 0.5D;
               var5.posZ = (double)(var2 + 1);
            }
         }

         return RotationUtils.getRotationFromPosition(var5.posX, var5.posY, var5.posZ);
      } else {
         var5.posX = (double)var0 + 0.5D;
         var5.posY = (double)var1 + 0.5D;
         var5.posZ = (double)var2 + 0.5D;
         var5.posX += (double)var3.getDirectionVec().getX() * 0.25D;
         var5.posY += (double)var3.getDirectionVec().getY() * 0.25D;
         var5.posZ += (double)var3.getDirectionVec().getZ() * 0.25D;
         return RotationUtils.getRotationFromPosition(var5.posX, var5.posY, var5.posZ);
      }
   }

   public BlockUtils2.BlockData getBlockData(BlockPos var1) {
      return mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air ? new BlockUtils2.BlockData(var1.add(0, -1, 0), EnumFacing.UP) : (mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air ? new BlockUtils2.BlockData(var1.add(-1, 0, 0), EnumFacing.EAST) : (mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air ? new BlockUtils2.BlockData(var1.add(1, 0, 0), EnumFacing.WEST) : (mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air ? new BlockUtils2.BlockData(var1.add(0, 0, -1), EnumFacing.SOUTH) : (mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air ? new BlockUtils2.BlockData(var1.add(0, 0, 1), EnumFacing.NORTH) : null))));
   }
}
