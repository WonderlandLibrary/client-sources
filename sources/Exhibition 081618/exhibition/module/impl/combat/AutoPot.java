package exhibition.module.impl.combat;

import exhibition.event.Event;
import exhibition.event.EventListener;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import exhibition.util.PlayerUtil;
import exhibition.util.RotationUtils;
import exhibition.util.Timer;
import java.util.Iterator;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AutoPot extends Module {
   private static final String DELAY = "DELAY";
   private static final String HEALTH = "HEALTH";
   private final String PREDICT = "PREDICT";
   private String REGEN = "REGEN";
   private String SPEED = "SPEED";
   private String OVERPOT = "OVERPOT";
   private Timer timer = new Timer();
   public static int haltTicks;
   public static boolean potting;

   public AutoPot(ModuleData data) {
      super(data);
      haltTicks = -1;
      this.settings.put("HEALTH", new Setting("HEALTH", Integer.valueOf(5), "Maximum health before healing.", 0.5D, 0.5D, 10.0D));
      this.settings.put("DELAY", new Setting("DELAY", Integer.valueOf(350), "Delay before healing again.", 50.0D, 100.0D, 1000.0D));
      this.settings.put("PREDICT", new Setting("PREDICT", false, "Predicts where to pot when moving."));
      this.settings.put(this.OVERPOT, new Setting(this.OVERPOT, true, "Pots earlier when armor is broken."));
      this.settings.put(this.REGEN, new Setting(this.REGEN, false, "Uses Regeneration pots."));
      this.settings.put(this.SPEED, new Setting(this.SPEED, false, "Uses Speed pots."));
   }

   public EventListener.Priority getPriority() {
      return EventListener.Priority.MEDIUM;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      if (event instanceof EventMotionUpdate) {
         EventMotionUpdate e = (EventMotionUpdate)event;
         if (mc.currentScreen instanceof GuiInventory) {
            haltTicks = 10;
            this.timer.reset();
            return;
         }

         float delay = (float)((Number)((Setting)this.settings.get("DELAY")).getValue()).intValue();
         if (e.isPre()) {
            if (potting && haltTicks < 0) {
               potting = false;
            }

            if (haltTicks > 6) {
               --haltTicks;
               return;
            }

            float health = ((Number)((Setting)this.settings.get("HEALTH")).getValue()).floatValue() * 2.0F;
            if (mc.thePlayer.getEquipmentInSlot(4) == null && this.hasArmor(mc.thePlayer) && ((Boolean)((Setting)this.settings.get(this.OVERPOT)).getValue()).booleanValue()) {
               health += mc.thePlayer.getEquipmentInSlot(1) == null ? 6.0F : 3.0F;
            }

            int potionSlot = this.getPotionFromInv();
            boolean shouldSplash = mc.thePlayer.getHealth() <= health;
            if (potionSlot != -1) {
               ItemStack is = mc.thePlayer.inventoryContainer.getSlot(potionSlot).getStack();
               Item item = is.getItem();
               if (item instanceof ItemPotion) {
                  ItemPotion potion = (ItemPotion)item;
                  if (potion.getEffects(is) != null) {
                     Iterator var10 = potion.getEffects(is).iterator();

                     while(var10.hasNext()) {
                        Object o = var10.next();
                        PotionEffect effect = (PotionEffect)o;
                        if (effect.getPotionID() == Potion.moveSpeed.id && ((Boolean)((Setting)this.settings.get(this.SPEED)).getValue()).booleanValue() && !mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                           shouldSplash = true;
                        }
                     }
                  }
               }
            }

            if (PlayerUtil.isMoving() && mc.thePlayer.isCollidedVertically) {
               if (shouldSplash && potionSlot != -1 && this.timer.delay(delay)) {
                  haltTicks = 6;
                  this.swap(potionSlot, 6);
                  e.setPitch(88.9F);
                  if (((Boolean)((Setting)this.settings.get("PREDICT")).getValue()).booleanValue()) {
                     double vel = Math.abs(mc.thePlayer.motionX) + Math.abs(mc.thePlayer.motionZ);
                     double movedPosX = mc.thePlayer.posX + mc.thePlayer.motionX * vel > 0.25D ? 100.0D : 16.0D;
                     double movedPosY = mc.thePlayer.boundingBox.minY - 3.6D;
                     double movedPosZ = mc.thePlayer.posZ + mc.thePlayer.motionZ * vel > 0.25D ? 100.0D : 16.0D;
                     float[] predRot = RotationUtils.getRotationFromPosition(movedPosX, movedPosZ, movedPosY);
                     e.setYaw(predRot[0]);
                     e.setPitch(vel > 0.25D ? -15.0F : predRot[1]);
                  }

                  potting = true;
               }
            } else if (shouldSplash && potionSlot != -1 && this.timer.delay(delay) && haltTicks < 0 && mc.thePlayer.isCollidedVertically) {
               mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, -90.0F, true));
               this.swap(potionSlot, 6);
               mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(6));
               mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
               mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
               haltTicks = 5;
               mc.thePlayer.jump();
               potting = true;
            }

            --haltTicks;
         }

         if (e.isPost() && potting && this.timer.delay(delay)) {
            if (PlayerUtil.isMoving()) {
               mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(6));
               mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
               mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            }

            this.timer.reset();
         }
      }

   }

   private boolean hasArmor(EntityPlayer player) {
      ItemStack boots = player.inventory.armorInventory[0];
      ItemStack pants = player.inventory.armorInventory[1];
      ItemStack chest = player.inventory.armorInventory[2];
      ItemStack head = player.inventory.armorInventory[3];
      return boots != null || pants != null || chest != null || head != null;
   }

   protected void swap(int slot, int hotbarNum) {
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
   }

   private int getPotionFromInv() {
      int pot = -1;

      label52:
      for(int i = 0; i < 45; ++i) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if (item instanceof ItemPotion) {
               ItemPotion potion = (ItemPotion)item;
               if (potion.getEffects(is) != null) {
                  Iterator var6 = potion.getEffects(is).iterator();

                  while(true) {
                     PotionEffect effect;
                     do {
                        if (!var6.hasNext()) {
                           continue label52;
                        }

                        Object o = var6.next();
                        effect = (PotionEffect)o;
                     } while(effect.getPotionID() != Potion.heal.id && (effect.getPotionID() != Potion.regeneration.id || !((Boolean)((Setting)this.settings.get(this.REGEN)).getValue()).booleanValue() || mc.thePlayer.isPotionActive(Potion.regeneration)) && (effect.getPotionID() != Potion.moveSpeed.id || !((Boolean)((Setting)this.settings.get(this.SPEED)).getValue()).booleanValue() || mc.thePlayer.isPotionActive(Potion.moveSpeed)));

                     if (ItemPotion.isSplash(is.getItemDamage())) {
                        pot = i;
                     }
                  }
               }
            }
         }
      }

      return pot;
   }
}
