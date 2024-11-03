package net.augustus.modules.player;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import net.augustus.events.EventClick;
import net.augustus.events.EventEarlyTick;
import net.augustus.events.EventWorld;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.StringValue;
import net.augustus.utils.BlockUtil;
import net.augustus.utils.MoveUtil;
import net.augustus.utils.RandomUtil;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.lwjgl.input.Mouse;

public class AutoArmor extends Module {
   private final TimeHelper timeHelper = new TimeHelper();
   private final TimeHelper timeHelper2 = new TimeHelper();
   private final TimeHelper timeHelper3 = new TimeHelper();
   private final TimeHelper timeHelper4 = new TimeHelper();
   public StringValue mode = new StringValue(1, "Mode", this, "OpenInv", new String[]{"OpenInv", "SpoofInv", "Basic"});
   public BooleanValue noMove = new BooleanValue(8, "NoMove", this, false);
   public BooleanValue interactionCheck = new BooleanValue(9, "InteractCheck", this, false);
   public DoubleValue startDelay = new DoubleValue(2, "StartDelay", this, 200.0, 0.0, 1000.0, 0);
   public DoubleValue delay = new DoubleValue(3, "Delay", this, 90.0, 0.0, 1000.0, 0);
   public BooleanValue hotbar = new BooleanValue(4, "Hotbar", this, false);
   public BooleanValue gommeQSG = new BooleanValue(5, "GommeQSG", this, false);
   public DoubleValue hotbarStartDelay = new DoubleValue(6, "HStartDelay", this, 200.0, 0.0, 1000.0, 0);
   public DoubleValue hotbarDelay = new DoubleValue(7, "HotbarDelay", this, 100.0, 0.0, 400.0, 0);
   public BooleanValue display = new BooleanValue(12315, "Display", this, false);
   public BooleanValue onWorld = new BooleanValue(123, "DisableOnWorld", this, false);
   
   public int slot = -1;
   
   @EventTarget
   public void onWorld(EventWorld eventWorld) {
      if(onWorld.getBoolean()) {
         setToggled(false);
      }
   }
   private ArrayList<ItemStack> chestPlates;
   private ArrayList<ItemStack> helmets;
   private ArrayList<ItemStack> boots;
   private ArrayList<ItemStack> trousers;
   private boolean blockInv;
   private int oldSlotID;
   private boolean b1 = true;
   private boolean invManager = true;

   public AutoArmor() {
      super("AutoArmor", new Color(23, 123, 208, 255), Categorys.PLAYER);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (mc.thePlayer != null) {
         mc.thePlayer.setServerInv(mc.currentScreen);
      }
      slot = -1;
   }

   @EventTarget
   public void onEventClick(EventClick eventClick) {
      this.blockInv = false;
      if (this.mode.getSelected().equals("SpoofInv")
         && this.interactionCheck.getBoolean()
         && (Mouse.isButtonDown(1) || Mouse.isButtonDown(0) || mm.killAura.isToggled() && mm.killAura.target != null || BlockUtil.isScaffoldToggled())) {
         this.closeInv();
         this.blockInv = true;
         this.timeHelper2.reset();
      }

      if (this.mode.getSelected().equalsIgnoreCase("OpenInv")) {
         this.newAutoArmorHotbar();
      }
   }

   @EventTarget
   public void onEventTick(EventEarlyTick eventEarlyTick) {
	   if(mm.arrayList.mode.getSelected().equalsIgnoreCase("Default")) {
	      this.setDisplayName(this.getName() + "  " + this.mode.getSelected());
	 }else {
		 this.setDisplayName(this.getName() + this.mode.getSelected());
	 }
      if (!mm.inventoryCleaner.isToggled()) {
         this.newAutoArmor(true);
      }
   }

   public void newAutoArmorHotbar() {
      this.chestPlates = new ArrayList<>();
      this.helmets = new ArrayList<>();
      this.trousers = new ArrayList<>();
      this.boots = new ArrayList<>();
      long random2 = RandomUtil.nextLong(-35L, 35L);
      long random3 = RandomUtil.nextLong(-35L, 35L);
      long delay2 = (long)(this.hotbarDelay.getValue() + (double)random2);
      long delay3 = (long)(this.hotbarStartDelay.getValue() + (double)random3);
      ItemStack helm = null;
      ItemStack boot = null;
      ItemStack chestPlate = null;
      ItemStack trouser = null;
      ItemStack newHelm = null;
      ItemStack newBoot = null;
      ItemStack newChestPlate = null;
      ItemStack newTrouser = null;
      if (this.hotbar.getBoolean() && mc.currentScreen == null) {
         boolean qsg = this.gommeQSG.getBoolean();

         for(int i = 36; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
               if (((ItemArmor)itemStack.getItem()).armorType == 0) {
                  itemStack.setSlotID(i);
                  this.helmets.add(itemStack);
               } else if (((ItemArmor)itemStack.getItem()).armorType == 1) {
                  itemStack.setSlotID(i);
                  this.chestPlates.add(itemStack);
               } else if (((ItemArmor)itemStack.getItem()).armorType == 2) {
                  itemStack.setSlotID(i);
                  this.trousers.add(itemStack);
               } else if (((ItemArmor)itemStack.getItem()).armorType == 3) {
                  itemStack.setSlotID(i);
                  this.boots.add(itemStack);
               }
            }
         }

         for(int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
               if (i == 5) {
                  itemStack.setSlotID(i);
                  helm = itemStack;
               }

               if (i == 6) {
                  itemStack.setSlotID(i);
                  chestPlate = itemStack;
               }

               if (i == 7) {
                  itemStack.setSlotID(i);
                  trouser = itemStack;
               }

               if (i == 8) {
                  itemStack.setSlotID(i);
                  boot = itemStack;
               }
            }
         }

         for(ItemStack itemStack : this.helmets) {
            if (helm == null) {
               if (newHelm != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newHelm)) {
                     newHelm = itemStack;
                  }
               } else {
                  newHelm = itemStack;
               }
            } else if (helm.getItem() instanceof ItemArmor && qsg) {
               if (newHelm != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newHelm)) {
                     newHelm = itemStack;
                  }
               } else if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(helm)) {
                  newHelm = itemStack;
               }
            }
         }

         for(ItemStack itemStack : this.chestPlates) {
            if (chestPlate == null) {
               if (newChestPlate != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newChestPlate)) {
                     newChestPlate = itemStack;
                  }
               } else {
                  newChestPlate = itemStack;
               }
            } else if (chestPlate.getItem() instanceof ItemArmor && qsg) {
               if (newChestPlate != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newChestPlate)) {
                     newChestPlate = itemStack;
                  }
               } else if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(chestPlate)) {
                  newChestPlate = itemStack;
               }
            }
         }

         for(ItemStack itemStack : this.trousers) {
            if (trouser == null) {
               if (newTrouser != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newTrouser)) {
                     newTrouser = itemStack;
                  }
               } else {
                  newTrouser = itemStack;
               }
            } else if (trouser.getItem() instanceof ItemArmor && qsg) {
               if (newTrouser != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newTrouser)) {
                     newTrouser = itemStack;
                  }
               } else if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(trouser)) {
                  newTrouser = itemStack;
               }
            }
         }

         for(ItemStack itemStack : this.boots) {
            if (boot == null) {
               if (newBoot != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newBoot)) {
                     newBoot = itemStack;
                  }
               } else {
                  newBoot = itemStack;
               }
            } else if (boot.getItem() instanceof ItemArmor && qsg) {
               if (newBoot != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newBoot)) {
                     newBoot = itemStack;
                  }
               } else if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(boot)) {
                  newBoot = itemStack;
               }
            }
         }

         if (this.timeHelper3.reached(delay3) && this.timeHelper2.reached(delay2)) {
            if (newBoot == null && newTrouser == null && newChestPlate == null && newHelm == null) {
               this.timeHelper3.reset();
               if (this.b1) {
                  mc.thePlayer.inventory.currentItem = this.oldSlotID;
               }

               this.oldSlotID = mc.thePlayer.inventory.currentItem;
               this.b1 = false;
            } else {
               if (newChestPlate != null) {
                  if (chestPlate == null) {
                     this.rightClick(newChestPlate.getSlotID());
                     this.b1 = true;
                  } else if (qsg) {
                     this.rightClick(newChestPlate.getSlotID());
                     this.b1 = true;
                  }

                  this.timeHelper2.reset();
                  return;
               }

               if (newTrouser != null) {
                  if (trouser == null) {
                     this.rightClick(newTrouser.getSlotID());
                     this.b1 = true;
                  } else if (qsg) {
                     this.rightClick(newTrouser.getSlotID());
                     this.b1 = true;
                  }

                  this.timeHelper2.reset();
                  return;
               }

               if (newHelm != null) {
                  if (helm == null) {
                     this.rightClick(newHelm.getSlotID());
                     this.b1 = true;
                  } else if (qsg) {
                     this.rightClick(newHelm.getSlotID());
                     this.b1 = true;
                  }

                  this.timeHelper2.reset();
                  return;
               }

               if (newBoot != null) {
                  if (boot == null) {
                     this.rightClick(newBoot.getSlotID());
                     this.b1 = true;
                  } else if (qsg) {
                     this.rightClick(newBoot.getSlotID());
                     this.b1 = true;
                  }

                  this.timeHelper2.reset();
                  return;
               }
            }
         }
      }
   }

   public void newAutoArmor(boolean startDelay) {
      this.chestPlates = new ArrayList<>();
      this.helmets = new ArrayList<>();
      this.trousers = new ArrayList<>();
      this.boots = new ArrayList<>();
      long random = RandomUtil.nextLong(-25L, 25L);
      long random2 = RandomUtil.nextLong(-25L, 25L);
      long random3 = RandomUtil.nextLong(-25L, 25L);
      long random4 = RandomUtil.nextLong(-25L, 25L);
      long delay = (long)(this.delay.getValue() + (double)random);
      long delay2 = (long)(this.hotbarDelay.getValue() + (double)random2);
      long delay4 = 0L;
      long delay3 = (long)(this.hotbarStartDelay.getValue() + (double)random3);
      if (startDelay) {
         delay4 = (long)(this.startDelay.getValue() + (double)random4);
      }

      ItemStack helm = null;
      ItemStack boot = null;
      ItemStack chestPlate = null;
      ItemStack trouser = null;
      ItemStack newHelm = null;
      ItemStack newBoot = null;
      ItemStack newChestPlate = null;
      ItemStack newTrouser = null;
      boolean autoArmor = mc.currentScreen instanceof GuiInventory
         || !this.mode.getSelected().equalsIgnoreCase("OpenInv")
            && (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory)
            && (!this.noMove.getBoolean() || this.noMove.getBoolean() && !MoveUtil.isMoving());
      if (autoArmor && !this.blockInv) {
         for(int i = 0; i < mc.thePlayer.inventoryContainer.inventorySlots.size(); ++i) {
            ItemStack itemStack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
               if (((ItemArmor)itemStack.getItem()).armorType == 0) {
                  itemStack.setSlotID(i);
                  this.helmets.add(itemStack);
               } else if (((ItemArmor)itemStack.getItem()).armorType == 1) {
                  itemStack.setSlotID(i);
                  this.chestPlates.add(itemStack);
               } else if (((ItemArmor)itemStack.getItem()).armorType == 2) {
                  itemStack.setSlotID(i);
                  this.trousers.add(itemStack);
               } else if (((ItemArmor)itemStack.getItem()).armorType == 3) {
                  itemStack.setSlotID(i);
                  this.boots.add(itemStack);
               }

               if (itemStack.getSlotID() == 5) {
                  itemStack.setSlotID(i);
                  helm = itemStack;
               }

               if (itemStack.getSlotID() == 6) {
                  itemStack.setSlotID(i);
                  chestPlate = itemStack;
               }

               if (itemStack.getSlotID() == 7) {
                  itemStack.setSlotID(i);
                  trouser = itemStack;
               }

               if (itemStack.getSlotID() == 8) {
                  itemStack.setSlotID(i);
                  boot = itemStack;
               }
            }
         }
         for(ItemStack itemStack : this.helmets) {
            if (helm == null) {
               if (newHelm != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newHelm)) {
                     newHelm = itemStack;
                  } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(newHelm)
                     && itemStack.getItemDamage() < newHelm.getItemDamage()) {
                     newHelm = itemStack;
                  }
               } else {
                  newHelm = itemStack;
               }
            } else if (helm.getItem() instanceof ItemArmor) {
               if (newHelm != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newHelm)) {
                     newHelm = itemStack;
                  } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(newHelm)
                     && itemStack.getItemDamage() < newHelm.getItemDamage()) {
                     newHelm = itemStack;
                  }
               } else if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(helm)) {
                  newHelm = itemStack;
               } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(helm) && itemStack.getItemDamage() < helm.getItemDamage()) {
                  newHelm = itemStack;
               }
            }
         }

         for(ItemStack itemStack : this.chestPlates) {
            if (chestPlate == null) {
               if (newChestPlate != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newChestPlate)) {
                     newChestPlate = itemStack;
                  } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(newChestPlate)
                     && itemStack.getItemDamage() < newChestPlate.getItemDamage()) {
                     newChestPlate = itemStack;
                  }
               } else {
                  newChestPlate = itemStack;
               }
            } else if (chestPlate.getItem() instanceof ItemArmor) {
               if (newChestPlate != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newChestPlate)) {
                     newChestPlate = itemStack;
                  } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(newChestPlate)
                     && itemStack.getItemDamage() < newChestPlate.getItemDamage()) {
                     newChestPlate = itemStack;
                  }
               } else if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(chestPlate)) {
                  newChestPlate = itemStack;
               } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(chestPlate)
                  && itemStack.getItemDamage() < chestPlate.getItemDamage()) {
                  newChestPlate = itemStack;
               }
            }
         }

         for(ItemStack itemStack : this.trousers) {
            if (trouser == null) {
               if (newTrouser != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newTrouser)) {
                     newTrouser = itemStack;
                  } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(newTrouser)
                     && itemStack.getItemDamage() < newTrouser.getItemDamage()) {
                     newTrouser = itemStack;
                  }
               } else {
                  newTrouser = itemStack;
               }
            } else if (trouser.getItem() instanceof ItemArmor) {
               if (newTrouser != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newTrouser)) {
                     newTrouser = itemStack;
                  } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(newTrouser)
                     && itemStack.getItemDamage() < newTrouser.getItemDamage()) {
                     newTrouser = itemStack;
                  }
               } else if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(trouser)) {
                  newTrouser = itemStack;
               } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(trouser) && itemStack.getItemDamage() < trouser.getItemDamage()) {
                  newTrouser = itemStack;
               }
            }
         }

         for(ItemStack itemStack : this.boots) {
            if (boot == null) {
               if (newBoot != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newBoot)) {
                     newBoot = itemStack;
                  } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(newBoot)
                     && itemStack.getItemDamage() > newBoot.getItemDamage()) {
                     newBoot = itemStack;
                  }
               } else {
                  newBoot = itemStack;
               }
            } else if (boot.getItem() instanceof ItemArmor) {
               if (newBoot != null) {
                  if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(newBoot)) {
                     newBoot = itemStack;
                  } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(newBoot)
                     && itemStack.getItemDamage() < newBoot.getItemDamage()) {
                     newBoot = itemStack;
                  }
               } else if (this.getDamageReduceAmount(itemStack) > this.getDamageReduceAmount(boot)) {
                  newBoot = itemStack;
               } else if (this.getDamageReduceAmount(itemStack) == this.getDamageReduceAmount(boot) && itemStack.getItemDamage() < boot.getItemDamage()) {
                  newBoot = itemStack;
               }
            }
         }

         if (this.timeHelper4.reached(delay4) && this.timeHelper.reached(delay)) {
            if (newBoot == null && newTrouser == null && newChestPlate == null && newHelm == null) {
               this.invManager = true;
            }

            if (newChestPlate != null) {
               if (chestPlate == null) {
                  this.shiftClick(newChestPlate.getSlotID());
               } else {
                  this.replaceArmor(6);
               }

               this.timeHelper.reset();
               this.invManager = false;
               if (this.delay.getValue() != 0.0) {
                  return;
               }
            }

            if (newTrouser != null) {
               if (trouser == null) {
                  this.shiftClick(newTrouser.getSlotID());
               } else {
                  this.replaceArmor(7);
               }

               this.timeHelper.reset();
               this.invManager = false;
               if (this.delay.getValue() != 0.0) {
                  return;
               }
            }

            if (newHelm != null) {
               if (helm == null) {
                  this.shiftClick(newHelm.getSlotID());
               } else {
                  this.replaceArmor(5);
               }

               this.timeHelper.reset();
               this.invManager = false;
               if (this.delay.getValue() != 0.0) {
                  return;
               }
            }

            if (newBoot != null) {
               if (boot == null) {
                  this.shiftClick(newBoot.getSlotID());
               } else {
                  this.replaceArmor(8);
               }

               this.timeHelper.reset();
               this.invManager = false;
               if (this.delay.getValue() != 0.0) {
                  return;
               }
            }
         }
      } else {
         this.timeHelper4.reset();
      }

      this.closeInv();
   }

   private void closeInv() {
      if (mc.thePlayer.getServerInv() != null && this.mode.getSelected().equals("SpoofInv") && mc.currentScreen == null) {
         mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));
         mc.thePlayer.setServerInv(null);
         System.out.println("CloseInv");
      }
   }

   private void openInv() {
      if (this.mode.getSelected().equals("SpoofInv") && mc.thePlayer.getServerInv() == null && mc.currentScreen == null) {
         mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
         mc.thePlayer.setServerInv(new GuiInventory(mc.thePlayer));
         System.out.println("OpenInv");
      }
   }

   private double getDamageReduceAmount(ItemStack itemStack) {
      double damageReduceAmount = 0.0;
      if (itemStack.getItem() instanceof ItemArmor) {
         damageReduceAmount += (double)(
            (float)((ItemArmor)itemStack.getItem()).damageReduceAmount
               + (float)(
                     6
                        + EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack)
                           * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, itemStack)
                  )
                  / 3.0F
         );
         damageReduceAmount += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, itemStack) / 11.0;
         damageReduceAmount += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, itemStack) / 11.0;
         damageReduceAmount += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, itemStack) / 11.0;
         damageReduceAmount += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) / 11.0;
         damageReduceAmount += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, itemStack) / 11.0;
         damageReduceAmount += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.featherFalling.effectId, itemStack) / 11.0;
         if (((ItemArmor)itemStack.getItem()).armorType == 0 && ((ItemArmor)itemStack.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.GOLD && ((ItemArmor)itemStack.getItem()).getArmorMaterial() == ItemArmor.ArmorMaterial.CHAIN) {
            damageReduceAmount -= 0.01;
         }
      }

      return damageReduceAmount;
   }

   private void shiftClick(int slotID) {
		  this.slot = slotID;
      this.openInv();
      if (this.mode.getSelected().equalsIgnoreCase("OpenInv")) {
         Slot slot1 = mc.thePlayer.inventoryContainer.getSlot(slotID);

         try {
            GuiInventory guiInventory = (GuiInventory)mc.currentScreen;
            guiInventory.forceShift = true;
            guiInventory.mouseClicked(slot1.xDisplayPosition + 2 + guiInventory.guiLeft, slot1.yDisplayPosition + 2 + guiInventory.guiTop, 0);
         } catch (IOException var4) {
            var4.printStackTrace();
         }
      } else {
         mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotID, 0, 1, mc.thePlayer);
      }

      this.timeHelper.reset();
   }

   private void replaceArmor(int slotID) {
		  this.slot = slotID;
      this.openInv();
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotID, 1, 4, mc.thePlayer);
      this.timeHelper.reset();
   }

   private void rightClick(int slotID) {
		  this.slot = slotID;
      this.openInv();
      mc.thePlayer.inventory.currentItem = slotID - 36;
      mc.rightClickMouse();
      this.timeHelper.reset();
   }

   public boolean isInvManager() {
      return this.invManager;
   }

   public void setInvManager(boolean invManager) {
      this.invManager = invManager;
   }
}
