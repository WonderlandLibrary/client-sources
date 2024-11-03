package net.augustus.modules.movement;

import java.awt.Color;

import net.augustus.events.EventClick;
import net.augustus.events.EventNoSlow;
import net.augustus.events.EventPostMotion;
import net.augustus.events.EventPreMotion;
import net.augustus.events.EventUpdate;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.BooleansSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.Setting;
import net.augustus.settings.StringValue;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   public BooleanValue startSlow = new BooleanValue(16, "StartSlow", this, false);
   public BooleanValue swordSlowdown = new BooleanValue(1, "Slowdown", this, false);
   public BooleanValue swordSprint = new BooleanValue(2, "Sprint", this, false);
   public BooleanValue foodNoslow = new BooleanValue(796, "IntaveFoodNoSlow", this, false);
   public BooleanValue grimSword = new BooleanValue(7989, "GrimSword", this, false);
   public BooleanValue polarFoodNoSlow = new BooleanValue(7543, "PolarFoodNoSlow", this, false);
   public BooleanValue swordSwitch = new BooleanValue(3, "Switch", this, false);
   public BooleanValue swordToggle = new BooleanValue(4, "Toggle", this, false);
   public BooleanValue swordBug = new BooleanValue(111, "Bug", this, false);
   public BooleanValue swordTimer = new BooleanValue(5, "Timer", this, false);
   public BooleanValue bowSlowdown = new BooleanValue(6, "Slowdown", this, false);
   public BooleanValue bowSprint = new BooleanValue(7, "Sprint", this, false);
   public BooleanValue bowSwitch = new BooleanValue(8, "Switch", this, false);
   public BooleanValue bowToggle = new BooleanValue(9, "Toggle", this, false);
   public BooleanValue bowTimer = new BooleanValue(10, "Timer", this, false);
   public BooleanValue restSlowdown = new BooleanValue(11, "Slowdown", this, false);
   public BooleanValue restSprint = new BooleanValue(12, "Sprint", this, false);
   public BooleanValue restSwitch = new BooleanValue(13, "Switch", this, false);
   public BooleanValue restToggle = new BooleanValue(14, "Toggle", this, false);
   public BooleanValue restBug = new BooleanValue(1111, "Bug", this, false);
   public BooleanValue restTimer = new BooleanValue(15, "Timer", this, false);
   public BooleansSetting sword = new BooleansSetting(
      25, "Sword", this, new Setting[]{this.swordSlowdown, this.swordSprint, this.swordSwitch, this.swordToggle, this.swordBug, this.swordTimer}
   );
   public BooleansSetting bow = new BooleansSetting(
      26, "Bow", this, new Setting[]{this.bowSlowdown, this.bowSprint, this.bowSwitch, this.bowToggle, this.bowTimer}
   );
   public BooleansSetting rest = new BooleansSetting(
      27, "Rest", this, new Setting[]{this.restSlowdown, this.restSprint, this.restToggle, this.restBug, this.restTimer}
   );
   public DoubleValue swordForward = new DoubleValue(16, "SwordForward", this, 0.2, 0.0, 1.0, 2);
   public DoubleValue swordStrafe = new DoubleValue(17, "SwordStrafe", this, 0.2, 0.0, 1.0, 2);
   public DoubleValue bowForward = new DoubleValue(18, "BowForward", this, 0.2, 0.0, 1.0, 2);
   public DoubleValue bowStrafe = new DoubleValue(19, "BowStrafe", this, 0.2, 0.0, 1.0, 2);
   public DoubleValue restForward = new DoubleValue(20, "RestForward", this, 0.2, 0.0, 1.0, 2);
   public DoubleValue restStrafe = new DoubleValue(21, "RestStrafe", this, 0.2, 0.0, 1.0, 2);
   public DoubleValue timerSword = new DoubleValue(22, "TimerSword", this, 0.2, 0.1, 2.0, 2);
   public DoubleValue timerBow = new DoubleValue(23, "TimerBow", this, 0.2, 0.1, 2.0, 2);
   public DoubleValue timerRest = new DoubleValue(24, "TimerRest", this, 0.2, 0.1, 2.0, 2);
   public BooleanValue drop = new BooleanValue(80, "Drop", this, false);
   public StringValue dropTiming = new StringValue(81, "DropTiming", this, "Pre", new String[]{"Pre", "Post"});
   public BooleanValue customPackets = new BooleanValue(50, "CustomPackets", this, false);
   public StringValue packetSendTiming = new StringValue(49, "PacketSendTiming", this, "Pre", new String[]{"Pre", "Post"});
   public BooleanValue C04 = new BooleanValue(51, "C04", this, false);
   public BooleanValue C07 = new BooleanValue(52, "C07", this, false);
   public BooleanValue C09 = new BooleanValue(53, "C09", this, false);
   public BooleansSetting packetsToSend = new BooleansSetting(60, "Packets" ,this, new Setting[] {
		   C04, C07, C09
   });
   private int counter = 0;
   private ItemStack lastItemStack = null;
   private boolean lastUsingRestItem = false;

   public NoSlow() {
      super("NoSlow", new Color(10, 40, 53), Categorys.MOVEMENT);
   }

   @EventTarget
   public void onEventNoSlow(EventNoSlow eventNoSlow) {
      ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
      if (currentItem != null && mc.thePlayer.isUsingItem() && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
         if (this.timeHelper.reached(400L) || !this.startSlow.getBoolean()) {
            if (currentItem.getItem() instanceof ItemSword) {
               eventNoSlow.setSprint(this.swordSprint.getBoolean());
               if (this.swordSlowdown.getBoolean()) {
                  eventNoSlow.setMoveForward((float)this.swordForward.getValue());
                  eventNoSlow.setMoveStrafe((float)this.swordStrafe.getValue());
               }
            } else if (currentItem.getItem() instanceof ItemBow) {
               eventNoSlow.setSprint(this.bowSprint.getBoolean());
               if (this.bowSlowdown.getBoolean()) {
                  eventNoSlow.setMoveForward((float)this.bowForward.getValue());
                  eventNoSlow.setMoveStrafe((float)this.bowStrafe.getValue());
               }
            } else {
               eventNoSlow.setSprint(this.restSprint.getBoolean());
               if (this.restSlowdown.getBoolean()) {
                  eventNoSlow.setMoveForward((float)this.restForward.getValue());
                  eventNoSlow.setMoveStrafe((float)this.restStrafe.getValue());
               }
            }
         }
      } else {
         this.timeHelper.reset();
      }
   }

   private void sendCustomPackets() {
	   if(C04.getBoolean()) {
		   mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
	   }
	   if(C07.getBoolean()) {
		   mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
	   }
	   if(C09.getBoolean()) {
		   int prevslot = mc.thePlayer.inventory.currentItem;
		   mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(1));
		   mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
	   }
   }
   
   @EventTarget
   public void onEventPreMotion(EventPreMotion eventPreMotion) {
      ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
      if (currentItem != null && mc.thePlayer.isUsingItem() && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
          if (currentItem.getItem() instanceof ItemSword) {
        	  if(this.grimSword.getBoolean()) {
                  mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));

                  int slotIDtoSwitch = mc.thePlayer.inventory.currentItem == 7 ? mc.thePlayer.inventory.currentItem - 2 : mc.thePlayer.inventory.currentItem + 2;
                  mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slotIDtoSwitch));
                  mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));

                  mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
                  
                  return;
        	  }
          }
          if(currentItem.getItem() instanceof ItemFood) {
        	  if(this.polarFoodNoSlow.getBoolean()) {
        		  if(mc.thePlayer.getItemInUseDuration() == 1) {
        			  mc.thePlayer.sendQueue.addToSendQueue(new C0CPacketInput(0, 1, false, false));
        			  mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
        		  }
        	  }
          }
         if (currentItem.getItem() instanceof ItemSword) {
            if (this.swordToggle.getBoolean()) {
               mc.thePlayer
                  .sendQueue
                  .addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }

            if (this.swordSwitch.getBoolean()) {
               int slotIDtoSwitch = mc.thePlayer.inventory.currentItem == 7 ? mc.thePlayer.inventory.currentItem - 2 : mc.thePlayer.inventory.currentItem + 2;
               mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slotIDtoSwitch));
               mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            }
         } else if (currentItem.getItem() instanceof ItemBow) {
            if (this.bowToggle.getBoolean()) {
               mc.thePlayer
                  .sendQueue
                  .addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
         } else if (this.restToggle.getBoolean()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
         }
         if(this.foodNoslow.getBoolean()) {
	         if (!lastUsingRestItem) {
	        	 mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
	         }
	         mc.thePlayer.sendQueue.addToSendQueue(new C0CPacketInput(0, 0.82f, false, false));
	         lastUsingRestItem = true;
	     } else {
	         lastUsingRestItem = false;
	
	         if (currentItem.getItem() instanceof ItemSword) {
	        	 mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
	        	 mc.thePlayer.sendQueue.addToSendQueue(new C0CPacketInput(0, 0.82f, false, false));
	         }
	     }
         if(customPackets.getBoolean() && packetSendTiming.getSelected().equals("Pre")) {
		     sendCustomPackets();
	     }
	     if(drop.getBoolean() && dropTiming.getSelected().equals("Pre")) {
	    	 mc.thePlayer.dropOneItem(false);
	     }
     }
   }

   @EventTarget
   public void onEventPostMotion(EventPostMotion eventPostMotion) {
      ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
      if (currentItem != null && mc.thePlayer.isUsingItem() && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
         if(currentItem.getItem() instanceof ItemFood && mc.thePlayer.getItemInUseDuration() < 2) {
      	     mc.thePlayer.sendQueue.addToSendQueue(new C0CPacketInput(0, 1, false, false));
    	     mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
    	     return;
         }
         if (currentItem.getItem() instanceof ItemSword) {
            if (this.swordToggle.getBoolean()) {
               mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            }
         } else if (currentItem.getItem() instanceof ItemBow) {
            if (this.bowToggle.getBoolean()) {
               mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            }
         } else if (this.restToggle.getBoolean()) {
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
         }
         if(customPackets.getBoolean() && packetSendTiming.getSelected().equals("Post")) {
		     this.sendCustomPackets();
	     }
	     if(drop.getBoolean() && dropTiming.getSelected().equals("Post")) {
	    	 mc.thePlayer.dropOneItem(false);
	     }
      }
   }

   @EventTarget
   public void onEventUpdate(EventUpdate eventUpdate) {
      ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
      if (currentItem != null && mc.thePlayer.isUsingItem() && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
         if (currentItem.getItem() instanceof ItemSword) {
            if (this.swordTimer.getBoolean()) {
               mc.getTimer().timerSpeed = (float)this.timerSword.getValue();
            } else {
               mc.getTimer().timerSpeed = 1.0F;
            }
         } else if (currentItem.getItem() instanceof ItemBow) {
            if (this.bowTimer.getBoolean()) {
               mc.getTimer().timerSpeed = (float)this.timerBow.getValue();
            } else {
               mc.getTimer().timerSpeed = 1.0F;
            }

            if (this.bowSwitch.getBoolean()) {
               int slotIDtoSwitch = mc.thePlayer.inventory.currentItem == 7 ? mc.thePlayer.inventory.currentItem - 2 : mc.thePlayer.inventory.currentItem + 2;
               mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slotIDtoSwitch));
               mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            }
         } else {
            if (this.restTimer.getBoolean()) {
               mc.getTimer().timerSpeed = (float)this.timerRest.getValue();
            } else {
               mc.getTimer().timerSpeed = 1.0F;
            }

            if (this.restSwitch.getBoolean()) {
               int slotIDtoSwitch = mc.thePlayer.inventory.currentItem == 7 ? mc.thePlayer.inventory.currentItem - 2 : mc.thePlayer.inventory.currentItem + 2;
               mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slotIDtoSwitch));
               mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            }
         }
      }
   }

   @EventTarget
   public void onEventClick(EventClick eventClick) {
      ItemStack currentItem = mc.thePlayer.getCurrentEquippedItem();
      if (currentItem != null && mc.thePlayer.isUsingItem() && (mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F)) {
         if (this.lastItemStack != null && !this.lastItemStack.equals(currentItem)) {
            this.counter = 0;
         }

         if (currentItem.getItem() instanceof ItemSword) {
            if (this.swordBug.getBoolean()) {
               eventClick.setShouldRightClick(false);
               if (this.counter != 1) {
                  mc.thePlayer.sendQueue.addToSendQueueDirect(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                  mc.thePlayer.stopUsingItem();
                  mc.thePlayer.closeScreen();
                  eventClick.setCanceled(true);
                  this.counter = 1;
               }
            }
         } else if (!(currentItem.getItem() instanceof ItemBow) && this.restBug.getBoolean()) {
            eventClick.setShouldRightClick(false);
            if (this.counter != 3) {
               mc.thePlayer.sendQueue.addToSendQueueDirect(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
               mc.thePlayer.stopUsingItem();
               mc.thePlayer.closeScreen();
               eventClick.setCanceled(true);
               this.counter = 3;
            }
         }

         if (eventClick.isCanceled()) {
            mc.sendClickBlockToController(mc.currentScreen == null && mc.gameSettings.keyBindAttack.isKeyDown() && mc.inGameHasFocus);
         }

         this.lastItemStack = currentItem;
      } else {
         this.counter = 0;
      }
   }

   @Override
   public void onDisable() {
      mc.getTimer().timerSpeed = 1.0F;
   }
}
