package cc.slack.features.modules.impl.world;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.other.MathTimerUtil;
import io.github.nevalackin.radbus.Listen;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockTNT;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

@ModuleInfo(
   name = "InvManager",
   category = Category.WORLD
)
public class InvManager extends Module {
   private final NumberValue<Long> managerDelayvalue = new NumberValue("Delay", 150L, 0L, 300L, 25L);
   private final BooleanValue openInvvalue = new BooleanValue("Open Inventory", true);
   private final BooleanValue autoArmorvalue = new BooleanValue("AutoArmor", false);
   private final BooleanValue noTrashvalue = new BooleanValue("No Trash", true);
   private final BooleanValue noMovevalue = new BooleanValue("No Move", true);
   private final int INVENTORY_ROWS = 4;
   private final int INVENTORY_COLUMNS = 9;
   private final int ARMOR_SLOTS = 4;
   private final int INVENTORY_SLOTS = 40;
   private PlayerControllerMP playerController;
   private final MathTimerUtil timer = new MathTimerUtil(0L);
   private boolean movedItem;
   private boolean inventoryOpen;

   public InvManager() {
      this.addSettings(new Value[]{this.managerDelayvalue, this.openInvvalue, this.autoArmorvalue, this.noTrashvalue, this.noMovevalue});
   }

   public void onEnable() {
      this.timer.reset();
   }

   public void onDisable() {
      this.closeInventory();
   }

   @Listen
   public void onUpdate(UpdateEvent event) {
      if (!this.timer.reached((Long)this.managerDelayvalue.getValue())) {
         this.closeInventory();
      } else if (!(mc.getCurrentScreen() instanceof GuiChest)) {
         if (!mc.getGameSettings().keyBindJump.isKeyDown() && !mc.getGameSettings().keyBindForward.isKeyDown() && !mc.getGameSettings().keyBindLeft.isKeyDown() && !mc.getGameSettings().keyBindBack.isKeyDown() && !mc.getGameSettings().keyBindRight.isKeyDown() || !(Boolean)this.noMovevalue.getValue()) {
            this.movedItem = false;
            this.timer.reset();
            this.timer.reached((Long)this.managerDelayvalue.getValue());
            if (mc.getCurrentScreen() instanceof GuiInventory || !(Boolean)this.openInvvalue.getValue()) {
               this.playerController = mc.getPlayerController();
               if ((Boolean)this.noTrashvalue.getValue()) {
                  for(int i = 0; i < 40; ++i) {
                     ItemStack itemStack = mc.getPlayer().inventory.getStackInSlot(i);
                     if (itemStack != null && itemStack.getItem() != null && !this.itemWhitelisted(itemStack)) {
                        this.throwItem(this.getSlotId(i));
                     }
                  }
               }

               Integer bestHelmet = null;
               Integer bestChestPlate = null;
               Integer bestLeggings = null;
               Integer bestBoots = null;
               Integer bestSword = null;
               Integer bestPickaxe = null;
               Integer bestAxe = null;
               Integer bestBlock = null;
               Integer bestBow = null;
               Integer bestPotion = null;
               Integer bestGaps = null;

               int i;
               ItemStack itemStack;
               Item item;
               ItemArmor armor;
               for(i = 0; i < 40; ++i) {
                  itemStack = mc.getPlayer().inventory.getStackInSlot(i);
                  if (itemStack != null && itemStack.getItem() != null) {
                     item = itemStack.getItem();
                     int potionID;
                     if (item instanceof ItemArmor) {
                        armor = (ItemArmor)item;
                        potionID = this.getArmorDamageReduction(itemStack);
                        if (armor.armorType == 0 && (bestHelmet == null || potionID > this.getArmorDamageReduction(mc.getPlayer().inventory.getStackInSlot(bestHelmet)))) {
                           bestHelmet = i;
                        }

                        if (armor.armorType == 1 && (bestChestPlate == null || potionID > this.getArmorDamageReduction(mc.getPlayer().inventory.getStackInSlot(bestChestPlate)))) {
                           bestChestPlate = i;
                        }

                        if (armor.armorType == 2 && (bestLeggings == null || potionID > this.getArmorDamageReduction(mc.getPlayer().inventory.getStackInSlot(bestLeggings)))) {
                           bestLeggings = i;
                        }

                        if (armor.armorType == 3 && (bestBoots == null || potionID > this.getArmorDamageReduction(mc.getPlayer().inventory.getStackInSlot(bestBoots)))) {
                           bestBoots = i;
                        }
                     }

                     float amountOfGaps;
                     if (item instanceof ItemSword) {
                        amountOfGaps = this.getSwordDamage(itemStack);
                        if (bestSword == null || amountOfGaps > this.getSwordDamage(mc.getPlayer().inventory.getStackInSlot(bestSword))) {
                           bestSword = i;
                        }
                     }

                     if (item instanceof ItemPickaxe) {
                        amountOfGaps = this.getMineSpeed(itemStack);
                        if (bestPickaxe == null || amountOfGaps > this.getMineSpeed(mc.getPlayer().inventory.getStackInSlot(bestPickaxe))) {
                           bestPickaxe = i;
                        }
                     }

                     if (item instanceof ItemAxe) {
                        amountOfGaps = this.getMineSpeed(itemStack);
                        if (bestAxe == null || amountOfGaps > this.getMineSpeed(mc.getPlayer().inventory.getStackInSlot(bestAxe))) {
                           bestAxe = i;
                        }
                     }

                     if (item instanceof ItemBlock && ((ItemBlock)item).getBlock().isFullCube()) {
                        amountOfGaps = (float)itemStack.stackSize;
                        if (bestBlock == null || amountOfGaps > (float)mc.getPlayer().inventory.getStackInSlot(bestBlock).stackSize) {
                           bestBlock = i;
                        }
                     }

                     if (item instanceof ItemBow) {
                        int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
                        if (bestBow == null || level > 1) {
                           bestBow = i;
                        }
                     }

                     if (item instanceof ItemAppleGold) {
                        amountOfGaps = (float)itemStack.stackSize;
                        if (bestGaps == null || amountOfGaps > 1.0F) {
                           bestGaps = i;
                        }
                     }

                     if (item instanceof ItemPotion) {
                        ItemPotion itemPotion = (ItemPotion)item;
                        if (bestPotion == null && ItemPotion.isSplash(itemStack.getMetadata()) && itemPotion.getEffects(itemStack.getMetadata()) != null) {
                           potionID = ((PotionEffect)itemPotion.getEffects(itemStack.getMetadata()).get(0)).getPotionID();
                           boolean isPotionActive = false;
                           Iterator var19 = mc.getPlayer().getActivePotionEffects().iterator();

                           while(var19.hasNext()) {
                              PotionEffect potion = (PotionEffect)var19.next();
                              if (potion.getPotionID() == potionID && potion.getDuration() > 0) {
                                 isPotionActive = true;
                                 break;
                              }
                           }

                           ArrayList<Integer> whitelistedPotions = new ArrayList<Integer>() {
                              {
                                 this.add(1);
                                 this.add(5);
                                 this.add(8);
                                 this.add(14);
                                 this.add(12);
                                 this.add(16);
                              }
                           };
                           if (!isPotionActive && (whitelistedPotions.contains(potionID) || potionID == 10 || potionID == 6)) {
                              bestPotion = i;
                           }
                        }
                     }
                  }
               }

               if ((Boolean)this.noTrashvalue.getValue()) {
                  for(i = 0; i < 40; ++i) {
                     itemStack = mc.getPlayer().inventory.getStackInSlot(i);
                     if (itemStack != null && itemStack.getItem() != null) {
                        item = itemStack.getItem();
                        if (item instanceof ItemArmor) {
                           armor = (ItemArmor)item;
                           if (armor.armorType == 0 && bestHelmet != null && i != bestHelmet || armor.armorType == 1 && bestChestPlate != null && i != bestChestPlate || armor.armorType == 2 && bestLeggings != null && i != bestLeggings || armor.armorType == 3 && bestBoots != null && i != bestBoots) {
                              this.throwItem(this.getSlotId(i));
                           }
                        }

                        if (item instanceof ItemSword && bestSword != null && i != bestSword) {
                           this.throwItem(this.getSlotId(i));
                        }

                        if (item instanceof ItemPickaxe && bestPickaxe != null && i != bestPickaxe) {
                           this.throwItem(this.getSlotId(i));
                        }

                        if (item instanceof ItemAxe && bestAxe != null && i != bestAxe) {
                           this.throwItem(this.getSlotId(i));
                        }

                        if (item instanceof ItemAppleGold && bestGaps != null && i != bestGaps) {
                           this.throwItem(this.getSlotId(i));
                        }

                        if (item instanceof ItemBow && bestBow != null && i != bestBow) {
                           this.throwItem(this.getSlotId(i));
                        }
                     }
                  }
               }

               if ((Boolean)this.autoArmorvalue.getValue()) {
                  if (bestHelmet != null) {
                     this.equipArmor(this.getSlotId(bestHelmet));
                  }

                  if (bestChestPlate != null) {
                     this.equipArmor(this.getSlotId(bestChestPlate));
                  }

                  if (bestLeggings != null) {
                     this.equipArmor(this.getSlotId(bestLeggings));
                  }

                  if (bestBoots != null) {
                     this.equipArmor(this.getSlotId(bestBoots));
                  }
               }

            }
         }
      }
   }

   private float getSwordDamage(ItemStack itemStack) {
      ItemSword sword = (ItemSword)itemStack.getItem();
      int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
      return (float)((double)sword.getDamageVsEntity() + (double)efficiencyLevel * 1.25D);
   }

   private int getArmorDamageReduction(ItemStack itemStack) {
      return ((ItemArmor)itemStack.getItem()).damageReduceAmount + EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
   }

   private void openInventory() {
      if (!this.inventoryOpen) {
         this.inventoryOpen = true;
         mc.getPlayer().sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
      }

   }

   private void closeInventory() {
      if (this.inventoryOpen) {
         this.inventoryOpen = false;
         mc.getPlayer().sendQueue.addToSendQueue(new C0DPacketCloseWindow(0));
      }

   }

   private void throwItem(int slot) {
      try {
         if (!this.movedItem) {
            this.openInventory();
            this.playerController.windowClick(mc.getPlayer().inventoryContainer.windowId, slot, 1, 4, mc.getPlayer());
            this.movedItem = true;
         }
      } catch (IndexOutOfBoundsException var3) {
      }

   }

   private void equipArmor(int slot) {
      try {
         if (slot > 8 && !this.movedItem) {
            this.openInventory();
            this.playerController.windowClick(mc.getPlayer().inventoryContainer.windowId, slot, 0, 1, mc.getPlayer());
            this.movedItem = true;
         }
      } catch (IndexOutOfBoundsException var3) {
      }

   }

   public int getSlotId(int slot) {
      if (slot >= 36) {
         return 8 - (slot - 36);
      } else {
         return slot < 9 ? slot + 36 : slot;
      }
   }

   private boolean itemWhitelisted(ItemStack itemStack) {
      ArrayList<Item> whitelistedItems = new ArrayList<Item>() {
         {
            this.add(Items.ender_pearl);
            this.add(Items.bow);
            this.add(Items.arrow);
            this.add(Items.milk_bucket);
            this.add(Items.water_bucket);
         }
      };
      Item item = itemStack.getItem();
      ArrayList<Integer> whitelistedPotions = new ArrayList<Integer>() {
         {
            this.add(6);
            this.add(1);
            this.add(5);
            this.add(8);
            this.add(14);
            this.add(12);
            this.add(10);
            this.add(16);
         }
      };
      if (item instanceof ItemPotion) {
         int potionID = this.getPotionId(itemStack);
         return whitelistedPotions.contains(potionID);
      } else {
         return item instanceof ItemBlock && !(((ItemBlock)item).getBlock() instanceof BlockTNT) && !(((ItemBlock)item).getBlock() instanceof BlockChest) && !(((ItemBlock)item).getBlock() instanceof BlockFalling) || item instanceof ItemAnvilBlock || item instanceof ItemSword || item instanceof ItemArmor || item instanceof ItemTool || item instanceof ItemFood || whitelistedItems.contains(item) && !item.equals(Items.spider_eye);
      }
   }

   private int getPotionId(ItemStack potion) {
      Item item = potion.getItem();

      try {
         if (item instanceof ItemPotion) {
            ItemPotion p = (ItemPotion)item;
            return ((PotionEffect)p.getEffects(potion.getMetadata()).get(0)).getPotionID();
         }
      } catch (NullPointerException var4) {
      }

      return 0;
   }

   private float getMineSpeed(ItemStack itemStack) {
      Item item = itemStack.getItem();
      int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);
      short efficiencyLevel;
      switch(efficiencyLevel) {
      case 1:
         efficiencyLevel = 30;
         break;
      case 2:
         efficiencyLevel = 69;
         break;
      case 3:
         efficiencyLevel = 120;
         break;
      case 4:
         efficiencyLevel = 186;
         break;
      case 5:
         efficiencyLevel = 271;
         break;
      default:
         efficiencyLevel = 0;
      }

      return !(item instanceof ItemPickaxe) && !(item instanceof ItemAxe) ? 0.0F : this.getToolEfficiency(item) + (float)efficiencyLevel;
   }

   private float getToolEfficiency(Item item) {
      if (item instanceof ItemPickaxe) {
         return ((ItemPickaxe)item).getToolMaterial().getEfficiencyOnProperMaterial();
      } else {
         return item instanceof ItemAxe ? ((ItemAxe)item).getToolMaterial().getEfficiencyOnProperMaterial() : 0.0F;
      }
   }
}
