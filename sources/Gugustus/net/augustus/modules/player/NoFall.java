package net.augustus.modules.player;

import java.awt.Color;

import net.augustus.events.*;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.modules.combat.KillAura;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.RotationUtil;
import net.augustus.utils.SigmaMoveUtils;
import net.augustus.utils.TimeHelper;
import net.augustus.utils.skid.rise6.PlayerUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class NoFall extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   private final TimeHelper timeHelper2 = new TimeHelper();
   public StringValue mode = new StringValue(1, "Mode", this, "OnGround", new String[]{"OnGround", "NoGround", "Stop", "Legit", "ACR"});
   public DoubleValue fallDistance = new DoubleValue(2, "MinFallDist", this, 2.0, 1.0, 24.0, 0);
   public DoubleValue legitFallDistance = new DoubleValue(3, "MinFallDist", this, 6.0, 2.0, 24.0, 1);
   public DoubleValue lookRange = new DoubleValue(7, "AimRange", this, 8.0, 5.0, 30.0, 0);
   public DoubleValue yawSpeed = new DoubleValue(5, "YawSpeed", this, 180.0, 0.0, 180.0, 0);
   public DoubleValue pitchSpeed = new DoubleValue(6, "PitchSpeed", this, 180.0, 0.0, 180.0, 0);
   public DoubleValue delay = new DoubleValue(8, "Delay", this, 110.0, 0.0, 2000.0, 0);
   private boolean rotated;
   private boolean clickTimer;
   private BlockPos b;
   private float[] rots = new float[2];
   private float[] lastRots = new float[2];
   private RotationUtil rotationUtil = new RotationUtil();
   private int slotID = 0;
   private BlockPos waterPos;
   private boolean matrixOnGround;
   private boolean fakeUnloaded;
   private boolean should;

   public NoFall() {
      super("NoFall", new Color(222, 80, 95, 255), Categorys.PLAYER);
   }

   @Override
   public void onDisable() {
   }

   @Override
   public void onEnable() {
      this.rotationUtil = new RotationUtil();
      if (mc.thePlayer != null) {
         this.slotID = mc.thePlayer.inventory.currentItem;
         this.setDisplayName(super.getName() + " §8" + this.mode.getSelected());
      }
   }

   @EventTarget
   public void onEventSendPacket(EventSendPacket eventSendPacket) {
      Packet packet = eventSendPacket.getPacket();
      String var3 = this.mode.getSelected();
      byte var4 = -1;
      if (var3.hashCode() == 370287304) {
         if (var3.equals("NoGround")) {
            var4 = 0;
         }
      }
      if (var4 == 0) {
         if (packet instanceof C03PacketPlayer) {
            C03PacketPlayer c03PacketPlayer = (C03PacketPlayer) packet;
            c03PacketPlayer.setOnGround(false);
         }
      }
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      if (mc.thePlayer != null) {
         String var2 = this.mode.getSelected();
         switch(var2) {
            case "OnGround":
               if ((double)mc.thePlayer.fallDistance > this.fallDistance.getValue()) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
               }
               break;
            case "Stop":
               if (mc.thePlayer.fallDistance > 2.0F) {
                  mc.thePlayer.motionY = 0.0;
                  mc.thePlayer.onGround = true;
                  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                  mc.thePlayer.fallDistance = 0.0F;
               }
               break;
            case "ACR":
               if(should && SigmaMoveUtils.isOnGround(3) && mc.thePlayer.fallDistance > 2.0F) {
                  should = false;
                  mc.thePlayer.setPosition(mc.thePlayer.posX + 1, mc.thePlayer.posY, mc.thePlayer.posZ + 1);
               } else {
                  if(mc.thePlayer.onGround && SigmaMoveUtils.isOnGround(0.01)) {
                     should = true;
                  }
               }
         }
      }
   }

   @EventTarget
   public void onEventEarlyTick(EventEarlyTick eventEarlyTick) {
      this.setDisplayName(super.getName() + " §8" + this.mode.getSelected());
      if (this.mode.getSelected().equals("Legit")) {
         if (mc.thePlayer != null) {
            int bestItem = this.getItem();
            if (bestItem != -1) {
               if (mc.thePlayer.inventoryContainer.getSlot(bestItem + 36).getStack().getItem().equals(Item.getItemById(325)) && mc.thePlayer.isInWater()) {
                  if (this.waterPos == null) {
                     this.waterPos = this.getWaterPos();
                  }

                  this.b = this.waterPos;
               } else {
                  if ((double)mc.thePlayer.fallDistance < this.legitFallDistance.getValue()) {
                     this.rotated = false;
                     return;
                  }

                  this.b = this.getBlockPos();
               }

               if (this.b != null) {
                  this.rots = this.rotationUtil
                     .scaffoldRots(
                        (double)this.b.getX() + 0.5,
                             this.b.getY() + 1,
                        (double)this.b.getZ() + 0.5,
                        this.lastRots[0],
                        this.lastRots[1],
                        (float)this.yawSpeed.getValue(),
                        (float)this.pitchSpeed.getValue(),
                        true
                     );
                  if (mc.thePlayer.inventoryContainer.getSlot(bestItem + 36).getStack().getItem().equals(Item.getItemById(325)) && mc.thePlayer.isInWater()) {
                     this.setRotation();
                  } else {
                     this.setPitch();
                  }

                  this.lastRots = this.rots;
               }
            }
         }
      }
   }

   @EventTarget
   public void onEventClick(EventUpdate eventClick) {
      if (this.mode.getSelected().equals("Legit") && this.b != null && this.rotated) {
         int currentItem = mc.thePlayer.inventory.currentItem;
         int bestItem = this.getItem();
         if (bestItem != this.slotID) {
            this.slotID = bestItem;
         }

         if (this.slotID != -1) {
            if (this.clickTimer) {
               if (mc.thePlayer.inventoryContainer.getSlot(this.slotID + 36).getStack().getItem().equals(Item.getItemById(325)) && mc.thePlayer.isInWater()) {
                  if (this.timeHelper.reached((long)this.delay.getValue() + RandomUtil.nextLong(-20L, 20L))
                     && this.timeHelper2.reached(360L + RandomUtil.nextLong(0L, 50L))) {
                     mc.thePlayer.inventory.currentItem = this.slotID;
                     mc.rightClickMouse();
                     mc.thePlayer.inventory.currentItem = currentItem;
                     this.clickTimer = false;
                     this.timeHelper.reset();
                     this.timeHelper2.reset();
                  }
               } else if (!((double)mc.thePlayer.fallDistance < this.legitFallDistance.getValue())
                  && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK
                  && mc.objectMouseOver.getBlockPos().equals(this.b)
                  && !mc.thePlayer.inventoryContainer.getSlot(this.slotID + 36).getStack().getItem().equals(Item.getItemById(325))
                  && this.timeHelper.reached((long)this.delay.getValue() + RandomUtil.nextLong(-20L, 20L))) {
                  if (mc.thePlayer.inventoryContainer.getSlot(this.slotID + 36).getStack().getItem().equals(Item.getItemById(326))) {
                     this.waterPos = new BlockPos(this.b.getX(), this.b.getY() + 1, this.b.getZ());
                  }

                  mc.thePlayer.inventory.currentItem = this.slotID;
                  mc.rightClickMouse();
                  mc.thePlayer.inventory.currentItem = currentItem;
                  this.clickTimer = false;
                  this.timeHelper.reset();
                  this.timeHelper2.reset();
               }
            } else {
               this.clickTimer = true;
            }
         }
      }
   }

   private BlockPos getBlockPos() {
      BlockPos b = new BlockPos(mc.thePlayer.posX + mc.thePlayer.motionX * 2.0, mc.thePlayer.posY, mc.thePlayer.posZ + mc.thePlayer.motionZ * 2.0);

      for(int y = b.getY() - 1; (double)y > (double)b.getY() - this.lookRange.getValue() + 1.0; --y) {
         if (this.isValidBlock(new BlockPos(b.getX(), y, b.getZ()))) {
            return new BlockPos(b.getX(), y, b.getZ());
         }
      }

      return null;
   }

   private BlockPos getWaterPos() {
      BlockPos b = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);

      for(int y = b.getY() + 1; y > b.getY() - 5; --y) {
         if (this.isValidBlock(new BlockPos(b.getX(), y, b.getZ()))) {
            return new BlockPos(b.getX(), y, b.getZ());
         }
      }

      return null;
   }

   private boolean isValidBlock(BlockPos blockPos) {
      Block block = mc.theWorld.getBlockState(blockPos).getBlock();
      return !(block instanceof BlockLiquid) && !(block instanceof BlockAir);
   }

   private int getItem() {
      for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
         ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
         if (itemStack != null
            && (
               (itemStack.getItem() == Item.getItemById(30) || itemStack.getItem() == Item.getItemById(326)) && !mc.thePlayer.isInWater()
                  || itemStack.getItem() == Item.getItemById(325) && mc.thePlayer.fallDistance < 1.0F && mc.thePlayer.isInWater()
            )) {
            return i - 36;
         }
      }

      return -1;
   }

   private void setRotation() {
      if (mc.currentScreen == null && (!mm.killAura.isToggled() || KillAura.target == null)) {
         mc.thePlayer.rotationYaw = this.rots[0];
         mc.thePlayer.rotationPitch = this.rots[1];
         mc.thePlayer.prevRotationYaw = this.lastRots[0];
         mc.thePlayer.prevRotationPitch = this.lastRots[1];
         this.rotated = true;
      }
   }

   private void setPitch() {
      if (mc.currentScreen == null && (!mm.killAura.isToggled() || KillAura.target == null)) {
         mc.thePlayer.rotationPitch = this.rots[1];
         mc.thePlayer.prevRotationPitch = this.lastRots[1];
         this.rotated = true;
      }
   }
}
