package xyz.cucumber.base.module.feat.player;

import java.util.HashMap;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventPriority;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventMotion;
import xyz.cucumber.base.events.ext.EventMoveButton;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.game.InventoryUtils;

@ModuleInfo(
   category = Category.PLAYER,
   description = "Automatically heals you",
   name = "Auto Heal"
)
public class AutoHealModule extends Mod {
   public ModeSettings mode = new ModeSettings("Mode", new String[]{"Open Inv", "Spoof"});
   public BooleanSettings intave = new BooleanSettings("Intave", () -> this.mode.getMode().equalsIgnoreCase("Spoof"), true);
   public NumberSettings health = new NumberSettings("Health", 15.0, 5.0, 20.0, 1.0);
   public NumberSettings headHealth = new NumberSettings("Head Health", 4.0, 1.0, 20.0, 1.0);
   public NumberSettings speed = new NumberSettings("Speed", 150.0, 0.0, 500.0, 1.0);
   public BooleanSettings soups = new BooleanSettings("Soups", true);
   public BooleanSettings instantDrop = new BooleanSettings("Instant drop", true);
   public BooleanSettings potions = new BooleanSettings("Potions", true);
   public BooleanSettings heads = new BooleanSettings("Heads", true);
   public int oldSlot;
   public boolean shouldSwitch;
   public boolean cancelAura;
   public Timer timer = new Timer();
   private HashMap<Integer, Integer> goodPotions = new HashMap<Integer, Integer>() {
      {
         this.put(Integer.valueOf(6), Integer.valueOf(1));
         this.put(Integer.valueOf(10), Integer.valueOf(2));
         this.put(Integer.valueOf(11), Integer.valueOf(3));
         this.put(Integer.valueOf(21), Integer.valueOf(4));
         this.put(Integer.valueOf(22), Integer.valueOf(5));
         this.put(Integer.valueOf(23), Integer.valueOf(6));
         this.put(Integer.valueOf(5), Integer.valueOf(7));
         this.put(Integer.valueOf(1), Integer.valueOf(8));
         this.put(Integer.valueOf(12), Integer.valueOf(9));
         this.put(Integer.valueOf(14), Integer.valueOf(10));
         this.put(Integer.valueOf(3), Integer.valueOf(11));
         this.put(Integer.valueOf(13), Integer.valueOf(12));
      }
   };

   public AutoHealModule() {
      this.addSettings(
         new ModuleSettings[]{this.mode, this.intave, this.health, this.headHealth, this.speed, this.soups, this.instantDrop, this.potions, this.heads}
      );
   }

   @Override
   public void onDisable() {
      InventoryUtils.closeInv(this.mode.getMode());
   }

   @EventListener
   public void onMoveButton(EventMoveButton event) {
      if (InventoryUtils.isInventoryOpen && this.intave.isEnabled()) {
         event.forward = false;
         event.backward = false;
         event.left = false;
         event.right = false;
         event.jump = false;
         event.sneak = false;
      }
   }

   @EventListener(EventPriority.HIGHEST)
   public void onUpdate(EventMotion e) {
      this.setInfo(this.mode.getMode());
      if (this.soups.isEnabled()) {
         int soupInInventory = this.findSoup(9, 36);
         int soupInHotbar = this.findSoup(36, 45);
         Container inventoryContainer = this.mc.thePlayer.inventoryContainer;
         if (soupInInventory != -1 || soupInHotbar != -1) {
            for (int i = 9; i < 45; i++) {
               ItemStack stack = inventoryContainer.getSlot(i).getStack();
               if (stack != null && stack.getItem() == Items.bowl) {
                  if (stack != null && stack.getItem() == Items.bowl) {
                     if ((double)InventoryUtils.timer.getTime() > (i >= 36 ? 0.0 : this.speed.getValue())) {
                        if (this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen == null) {
                           InventoryUtils.openInv(this.mode.getMode());
                        }

                        if (this.mode.getMode().equalsIgnoreCase("Spoof") || this.mc.currentScreen instanceof GuiInventory || i >= 36) {
                           if (i >= 36) {
                              this.mc.thePlayer.inventory.currentItem = i - 36;
                              this.mc.thePlayer.dropOneItem(false);
                           } else {
                              InventoryUtils.drop(i);
                           }

                           InventoryUtils.timer.reset();
                           return;
                        }
                     }

                     if (InventoryUtils.timer.hasTimeElapsed(55.0, false) && this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen == null) {
                        InventoryUtils.closeInv(this.mode.getMode());
                     }
                  }

                  if (this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen != null) {
                     InventoryUtils.closeInv(this.mode.getMode());
                     return;
                  }
               }
            }

            if (soupInHotbar != -1
               && !(this.mc.currentScreen instanceof GuiInventory)
               && (double)this.mc.thePlayer.getHealth() <= this.health.getValue()
               && InventoryUtils.timer.hasTimeElapsed(this.speed.getValue(), false)) {
               this.oldSlot = this.mc.thePlayer.inventory.currentItem;
               this.shouldSwitch = true;
               this.mc.thePlayer.inventory.currentItem = soupInHotbar - 36;
               this.mc.playerController.updateController();
               this.mc
                  .thePlayer
                  .sendQueue
                  .addToSendQueue(
                     new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1, inventoryContainer.getSlot(soupInHotbar).getStack(), 0.0F, 0.0F, 0.0F)
                  );
               if (this.instantDrop.isEnabled()) {
                  this.mc.thePlayer.dropOneItem(false);
               }

               InventoryUtils.timer.reset();
               return;
            }

            if (!this.isFull() && soupInInventory != -1) {
               KillAuraModule ka = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
               EntityLivingBase target = EntityUtils.getTarget(
                  6.0,
                  ka.Targets.getMode(),
                  ka.switchMode.getMode(),
                  400,
                  Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
                  ka.TroughWalls.isEnabled(),
                  ka.attackInvisible.isEnabled(),
                  ka.attackDead.isEnabled()
               );
               if (target != null && this.mode.getMode().equalsIgnoreCase("Spoof")) {
                  InventoryUtils.closeInv(this.mode.getMode());
                  return;
               }

               if (InventoryUtils.timer.hasTimeElapsed(this.speed.getValue(), false)) {
                  if (this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen == null) {
                     InventoryUtils.openInv(this.mode.getMode());
                  }

                  if (this.mode.getMode().equalsIgnoreCase("Spoof") || this.mc.currentScreen instanceof GuiInventory) {
                     this.mc.playerController.windowClick(0, soupInInventory, 0, 1, this.mc.thePlayer);
                     if (this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen == null && !this.intave.isEnabled()) {
                        InventoryUtils.closeInv(this.mode.getMode());
                     }

                     InventoryUtils.timer.reset();
                  }

                  return;
               }

               if (InventoryUtils.timer.hasTimeElapsed(55.0, false) && this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen == null) {
                  InventoryUtils.closeInv(this.mode.getMode());
               }
            }
         }
      }

      if (this.potions.isEnabled()) {
         int potionInInventory = this.findPotion(9, 36);
         int potionInHotbar = this.findPotion(36, 45);
         if (potionInInventory != -1 || potionInHotbar != -1) {
            Container inventoryContainer = this.mc.thePlayer.inventoryContainer;
            if (potionInHotbar != -1
               && !(this.mc.currentScreen instanceof GuiInventory)
               && InventoryUtils.timer.hasTimeElapsed(this.speed.getValue() + 200.0, false)) {
               ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(potionInHotbar).getStack();
               ItemPotion itemPotion = (ItemPotion)stack.getItem();
               PotionEffect effect = itemPotion.getEffects(stack).get(0);
               if (effect.getPotionID() != Potion.regeneration.id && effect.getPotionID() != Potion.heal.id && effect.getPotionID() != Potion.healthBoost.id
                  || !((double)this.mc.thePlayer.getHealth() > this.health.getValue()) && !this.mc.thePlayer.isPotionActive(effect.getPotionID())) {
                  this.oldSlot = this.mc.thePlayer.inventory.currentItem;
                  this.shouldSwitch = true;
                  if (e.getType() == EventType.POST) {
                     this.mc.thePlayer.inventory.currentItem = potionInHotbar - 36;
                  }

                  this.mc.playerController.updateController();
                  if (e.getType() == EventType.PRE) {
                     e.setPitch(90.0F);
                     this.cancelAura = true;
                  }

                  if (e.getType() == EventType.POST) {
                     this.mc
                        .thePlayer
                        .sendQueue
                        .addToSendQueue(
                           new C08PacketPlayerBlockPlacement(
                              new BlockPos(-1, -1, -1), -1, inventoryContainer.getSlot(potionInHotbar).getStack(), 0.0F, 0.0F, 0.0F
                           )
                        );
                     InventoryUtils.timer.reset();
                     this.cancelAura = false;
                     return;
                  }
               }
            }

            if (!this.isFull() && potionInInventory != -1) {
               KillAuraModule kax = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
               EntityLivingBase targetx = EntityUtils.getTarget(
                  6.0,
                  kax.Targets.getMode(),
                  kax.switchMode.getMode(),
                  400,
                  Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
                  kax.TroughWalls.isEnabled(),
                  kax.attackInvisible.isEnabled(),
                  kax.attackDead.isEnabled()
               );
               if (targetx != null && this.mode.getMode().equalsIgnoreCase("Spoof")) {
                  InventoryUtils.closeInv(this.mode.getMode());
                  return;
               }

               if (InventoryUtils.timer.hasTimeElapsed(this.speed.getValue(), false)) {
                  if (this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen == null) {
                     InventoryUtils.openInv(this.mode.getMode());
                  }

                  if (this.mode.getMode().equalsIgnoreCase("Spoof") || this.mc.currentScreen instanceof GuiInventory) {
                     this.mc.playerController.windowClick(0, potionInInventory, 0, 1, this.mc.thePlayer);
                     if (this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen == null && !this.intave.isEnabled()) {
                        InventoryUtils.closeInv(this.mode.getMode());
                     }

                     InventoryUtils.timer.reset();
                     return;
                  }
               }
            } else if (InventoryUtils.timer.hasTimeElapsed(55.0, false) && this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen == null) {
               InventoryUtils.closeInv(this.mode.getMode());
            }
         }
      }

      if (this.heads.isEnabled()) {
         int headInInventory = this.findHead(9, 36);
         int headInHotbar = this.findHead(36, 45);
         if (headInInventory != -1 || headInHotbar != -1) {
            Container inventoryContainerx = this.mc.thePlayer.inventoryContainer;
            if (headInHotbar != -1
               && (double)this.mc.thePlayer.getHealth() <= this.headHealth.getValue()
               && !(this.mc.currentScreen instanceof GuiInventory)
               && (double)this.mc.thePlayer.getHealth() <= this.headHealth.getValue()
               && InventoryUtils.timer.hasTimeElapsed(this.speed.getValue(), false)) {
               this.oldSlot = this.mc.thePlayer.inventory.currentItem;
               this.shouldSwitch = true;
               this.mc.thePlayer.inventory.currentItem = headInHotbar - 36;
               this.mc.playerController.updateController();
               e.setPitch(90.0F);
               this.mc
                  .thePlayer
                  .sendQueue
                  .addToSendQueue(
                     new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1, inventoryContainerx.getSlot(headInHotbar).getStack(), 0.0F, 0.0F, 0.0F)
                  );
               InventoryUtils.timer.reset();
               return;
            }

            if (!this.isFull() && headInInventory != -1) {
               KillAuraModule kaxx = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
               EntityLivingBase targetxx = EntityUtils.getTarget(
                  6.0,
                  kaxx.Targets.getMode(),
                  kaxx.switchMode.getMode(),
                  400,
                  Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(),
                  kaxx.TroughWalls.isEnabled(),
                  kaxx.attackInvisible.isEnabled(),
                  kaxx.attackDead.isEnabled()
               );
               if (targetxx != null && this.mode.getMode().equalsIgnoreCase("Spoof")) {
                  InventoryUtils.closeInv(this.mode.getMode());
                  return;
               }

               if (InventoryUtils.timer.hasTimeElapsed(this.speed.getValue(), false)) {
                  if (this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen == null) {
                     InventoryUtils.openInv(this.mode.getMode());
                  }

                  if (this.mode.getMode().equalsIgnoreCase("Spoof") || this.mc.currentScreen instanceof GuiInventory) {
                     this.mc.playerController.windowClick(0, headInInventory, 0, 1, this.mc.thePlayer);
                     if (this.mode.getMode().equalsIgnoreCase("Spoof") && this.mc.currentScreen == null && !this.intave.isEnabled()) {
                        InventoryUtils.closeInv(this.mode.getMode());
                     }

                     InventoryUtils.timer.reset();
                     return;
                  }
               }

               if ((double)InventoryUtils.timer.getTime() > this.speed.getValue() + 75.0
                  && this.mode.getMode().equalsIgnoreCase("Spoof")
                  && this.mc.currentScreen == null) {
                  InventoryUtils.closeInv(this.mode.getMode());
               }
            }
         }
      }
   }

   public int findPotion(int startSlot, int endSlot) {
      for (int i = startSlot; i < endSlot; i++) {
         ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
         if (stack != null && stack.getItem() == Items.potionitem) {
            ItemPotion itemPotion = (ItemPotion)stack.getItem();
            PotionEffect effect = itemPotion.getEffects(stack).get(0);
            if (ItemPotion.isSplash(stack.getMetadata()) && this.goodPotions.containsKey(effect.getPotionID())) {
               return i;
            }
         }
      }

      return -1;
   }

   public int findHead(int startSlot, int endSlot) {
      for (int i = startSlot; i < endSlot; i++) {
         ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
         if (stack != null && (stack.getItem() == Items.skull || stack.getItem() == Items.dye)) {
            return i;
         }
      }

      return -1;
   }

   public int findSoup(int startSlot, int endSlot) {
      for (int i = startSlot; i < endSlot; i++) {
         ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
         if (stack != null && stack.getItem() == Items.mushroom_stew) {
            return i;
         }
      }

      return -1;
   }

   public boolean isFull() {
      for (int i = 36; i < 45; i++) {
         ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
         if (stack == null) {
            return false;
         }
      }

      return true;
   }
}
