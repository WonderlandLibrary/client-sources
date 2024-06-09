package exhibition.module.impl.player;

import exhibition.event.Event;
import exhibition.event.RegisterEvent;
import exhibition.event.impl.EventMotionUpdate;
import exhibition.module.Module;
import exhibition.module.data.ModuleData;
import exhibition.module.data.Setting;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class InventoryCleaner extends Module {
   private final String TOGGLE = "TOGGLE";
   private final String BLOCKCAP = "BLOCKCAP";
   private final String ARCHERY = "ARCHERY";
   private final String FOOD = "FOOD";
   private final String TOOLS = "TOOLS";
   private Random random = new Random();
   private static boolean isCleaning;

   public InventoryCleaner(ModuleData data) {
      super(data);
      this.settings.put("TOGGLE", new Setting("TOGGLE", false, "Turn off when finished."));
      this.settings.put("ARCHERY", new Setting("ARCHERY", false, "Clean bows and arrows."));
      this.settings.put("FOOD", new Setting("FOOD", false, "Clean food. Keeps Golden Apples."));
      this.settings.put("TOOLS", new Setting("TOOLS", false, "Clean tools."));
      this.settings.put("BLOCKCAP", new Setting("BLOCKCAP", Integer.valueOf(128), "Max blocks allowed in your inventory.", 8.0D, 0.0D, 256.0D));
   }

   public static boolean isCleaning() {
      return isCleaning;
   }

   public void onEnable() {
      isCleaning = false;
   }

   public void onDisable() {
      isCleaning = false;
   }

   @RegisterEvent(
      events = {EventMotionUpdate.class}
   )
   public void onEvent(Event event) {
      EventMotionUpdate em = (EventMotionUpdate)event;
      if (em.isPre() && mc.thePlayer != null && (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory) && this.random.nextInt(2) == 0) {
         for(int i = 9; i < 45; ++i) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
               ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
               if (this.isBad(is) && is != mc.thePlayer.getCurrentEquippedItem()) {
                  if (!isCleaning) {
                     isCleaning = true;
                     mc.thePlayer.sendQueue.addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
                  }

                  mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, 0, 0, mc.thePlayer);
                  mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, -999, 0, 0, mc.thePlayer);
                  break;
               }
            }

            if (i == 44 && isCleaning) {
               isCleaning = false;
               mc.thePlayer.sendQueue.addToSendQueue(new C0DPacketCloseWindow(0));
               if (((Boolean)((Setting)this.settings.get("TOGGLE")).getValue()).booleanValue()) {
                  this.toggle();
               }
            }
         }
      }

   }

   private boolean isBad(ItemStack item) {
      int swordCount = 0;

      for(int i = 0; i < 45; ++i) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemSword) {
               ++swordCount;
            }
         }
      }

      if (swordCount > 1 && item != null && item.getItem() instanceof ItemSword) {
         return this.getDamage(item) <= this.bestDamage();
      } else if (item == null || !(item.getItem() instanceof ItemArmor) || this.canEquip(item) || this.betterCheck(item) && !this.canEquip(item)) {
         return item != null && !(item.getItem() instanceof ItemArmor) && (item.getItem().getUnlocalizedName().contains("tnt") || item.getItem().getUnlocalizedName().contains("stick") || item.getItem().getUnlocalizedName().contains("egg") || item.getItem().getUnlocalizedName().contains("string") || item.getItem().getUnlocalizedName().contains("flint") || item.getItem().getUnlocalizedName().contains("compass") || item.getItem().getUnlocalizedName().contains("feather") || item.getItem().getUnlocalizedName().contains("bucket") || item.getItem().getUnlocalizedName().contains("chest") && !item.getDisplayName().toLowerCase().contains("collect") || item.getItem().getUnlocalizedName().contains("snow") || item.getItem().getUnlocalizedName().contains("fish") || item.getItem().getUnlocalizedName().contains("enchant") || item.getItem().getUnlocalizedName().contains("exp") || item.getItem().getUnlocalizedName().contains("shears") || item.getItem().getUnlocalizedName().contains("anvil") || item.getItem().getUnlocalizedName().contains("torch") || item.getItem().getUnlocalizedName().contains("seeds") || item.getItem().getUnlocalizedName().contains("leather") || item.getItem() instanceof ItemPickaxe && ((Boolean)((Setting)this.settings.get("TOOLS")).getValue()).booleanValue() || item.getItem() instanceof ItemGlassBottle || item.getItem() instanceof ItemTool && ((Boolean)((Setting)this.settings.get("TOOLS")).getValue()).booleanValue() || item.getItem().getUnlocalizedName().contains("piston") || item.getItem().getUnlocalizedName().contains("potion") && this.isBadPotion(item) || item.getItem() instanceof ItemBlock && this.getBlockCount() > ((Number)((Setting)this.settings.get("BLOCKCAP")).getValue()).intValue() || item.getItem() instanceof ItemFood && ((Boolean)((Setting)this.settings.get("FOOD")).getValue()).booleanValue() && !(item.getItem() instanceof ItemAppleGold) || (item.getItem() instanceof ItemBow || item.getItem().getUnlocalizedName().contains("arrow")) && ((Boolean)((Setting)this.settings.get("ARCHERY")).getValue()).booleanValue());
      } else {
         return true;
      }
   }

   private double getProtectionValue(ItemStack stack) {
      return stack.getItem() instanceof ItemArmor ? (double)((ItemArmor)stack.getItem()).damageReduceAmount + (double)((100 - ((ItemArmor)stack.getItem()).damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D : 0.0D;
   }

   private boolean betterCheck(ItemStack stack) {
      try {
         if (stack.getItem() instanceof ItemArmor) {
            if (mc.thePlayer.getEquipmentInSlot(1) != null && stack.getUnlocalizedName().contains("boots")) {
               assert mc.thePlayer.getEquipmentInSlot(1).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(1)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(1).getItem()).damageReduceAmount) {
                  return true;
               }
            }

            if (mc.thePlayer.getEquipmentInSlot(2) != null && stack.getUnlocalizedName().contains("leggings")) {
               assert mc.thePlayer.getEquipmentInSlot(2).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(2)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(2).getItem()).damageReduceAmount) {
                  return true;
               }
            }

            if (mc.thePlayer.getEquipmentInSlot(3) != null && stack.getUnlocalizedName().contains("chestplate")) {
               assert mc.thePlayer.getEquipmentInSlot(3).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(3)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(3).getItem()).damageReduceAmount) {
                  return true;
               }
            }

            if (mc.thePlayer.getEquipmentInSlot(4) != null && stack.getUnlocalizedName().contains("helmet")) {
               assert mc.thePlayer.getEquipmentInSlot(4).getItem() instanceof ItemArmor;

               if (this.getProtectionValue(stack) + (double)((ItemArmor)stack.getItem()).damageReduceAmount > this.getProtectionValue(mc.thePlayer.getEquipmentInSlot(4)) + (double)((ItemArmor)mc.thePlayer.getEquipmentInSlot(4).getItem()).damageReduceAmount) {
                  return true;
               }
            }
         }

         return false;
      } catch (Exception var3) {
         return false;
      }
   }

   private float bestDamage() {
      float bestDamage = 0.0F;

      for(int i = 0; i < 45; ++i) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemSword && this.getDamage(is) > bestDamage) {
               bestDamage = this.getDamage(is);
            }
         }
      }

      return bestDamage;
   }

   private boolean canEquip(ItemStack stack) {
      assert stack.getItem() instanceof ItemArmor;

      return mc.thePlayer.getEquipmentInSlot(1) == null && stack.getUnlocalizedName().contains("boots") || mc.thePlayer.getEquipmentInSlot(2) == null && stack.getUnlocalizedName().contains("leggings") || mc.thePlayer.getEquipmentInSlot(3) == null && stack.getUnlocalizedName().contains("chestplate") || mc.thePlayer.getEquipmentInSlot(4) == null && stack.getUnlocalizedName().contains("helmet");
   }

   private int getBlockCount() {
      int blockCount = 0;

      for(int i = 0; i < 45; ++i) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if (is.getItem() instanceof ItemBlock && !Scaffold.getBlacklistedBlocks().contains(((ItemBlock)item).getBlock())) {
               blockCount += is.stackSize;
            }
         }
      }

      return blockCount;
   }

   private boolean isBadPotion(ItemStack stack) {
      if (stack != null && stack.getItem() instanceof ItemPotion) {
         ItemPotion potion = (ItemPotion)stack.getItem();
         if (ItemPotion.isSplash(stack.getItemDamage())) {
            Iterator var3 = potion.getEffects(stack).iterator();

            while(var3.hasNext()) {
               Object o = var3.next();
               PotionEffect effect = (PotionEffect)o;
               if (effect.getPotionID() == Potion.poison.getId() || effect.getPotionID() == Potion.harm.getId() || effect.getPotionID() == Potion.moveSlowdown.getId() || effect.getPotionID() == Potion.weakness.getId()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private float getDamage(ItemStack stack) {
      return !(stack.getItem() instanceof ItemSword) ? -1.0F : (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25F + ((ItemSword)stack.getItem()).getDamageGiven() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01F;
   }
}
