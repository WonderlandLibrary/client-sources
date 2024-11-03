package xyz.cucumber.base.utils.game;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.lwjgl.input.Keyboard;
import xyz.cucumber.base.utils.Timer;

public class InventoryUtils {
   public static Minecraft mc = Minecraft.getMinecraft();
   public static boolean isInventoryOpen;
   public static Timer timer = new Timer();
   public static List<Block> invalidBlocks = Arrays.asList(
      Blocks.enchanting_table,
      Blocks.carpet,
      Blocks.glass_pane,
      Blocks.ladder,
      Blocks.web,
      Blocks.stained_glass_pane,
      Blocks.iron_bars,
      Blocks.air,
      Blocks.water,
      Blocks.flowing_water,
      Blocks.lava,
      Blocks.ladder,
      Blocks.soul_sand,
      Blocks.ice,
      Blocks.packed_ice,
      Blocks.sand,
      Blocks.flowing_lava,
      Blocks.snow_layer,
      Blocks.chest,
      Blocks.ender_chest,
      Blocks.torch,
      Blocks.anvil,
      Blocks.trapped_chest,
      Blocks.noteblock,
      Blocks.jukebox,
      Blocks.wooden_pressure_plate,
      Blocks.stone_pressure_plate,
      Blocks.light_weighted_pressure_plate,
      Blocks.heavy_weighted_pressure_plate,
      Blocks.stone_button,
      Blocks.tnt,
      Blocks.wooden_button,
      Blocks.lever,
      Blocks.crafting_table,
      Blocks.furnace,
      Blocks.stone_slab,
      Blocks.wooden_slab,
      Blocks.stone_slab2,
      Blocks.brown_mushroom,
      Blocks.red_mushroom,
      Blocks.gold_block,
      Blocks.red_flower,
      Blocks.yellow_flower,
      Blocks.flower_pot
   );
   static KeyBinding[] moveKeys = new KeyBinding[]{
      mc.gameSettings.keyBindForward,
      mc.gameSettings.keyBindBack,
      mc.gameSettings.keyBindLeft,
      mc.gameSettings.keyBindRight,
      mc.gameSettings.keyBindJump,
      mc.gameSettings.keyBindSneak
   };

   public static int getBlockSlot(boolean hypixel) {
      int item = -1;
      int stacksize = 0;
      if (!hypixel
         && mc.thePlayer.getHeldItem() != null
         && mc.thePlayer.getHeldItem().getItem() != null
         && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock
         && !invalidBlocks.contains(((ItemBlock)mc.thePlayer.getHeldItem().getItem()).getBlock())) {
         return mc.thePlayer.inventory.currentItem;
      } else {
         for (int i = 36; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null
               && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock
               && !invalidBlocks.contains(((ItemBlock)mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).getBlock())
               && mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize >= stacksize) {
               item = i - 36;
               stacksize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
            }
         }

         return item;
      }
   }

   public static ItemStack getBlockSlotInventory() {
      ItemStack item = null;
      int stacksize = 0;
      if (mc.thePlayer.getHeldItem() != null
         && mc.thePlayer.getHeldItem().getItem() != null
         && mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock
         && !invalidBlocks.contains(((ItemBlock)mc.thePlayer.getHeldItem().getItem()).getBlock())) {
         return mc.thePlayer.getHeldItem();
      } else {
         for (int i = 9; i < 45; i++) {
            if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null
               && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock
               && !invalidBlocks.contains(((ItemBlock)mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem()).getBlock())
               && mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize >= stacksize) {
               item = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
               stacksize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
            }
         }

         return item;
      }
   }

   public static int getCobwebSlot() {
      int item = -1;
      int stacksize = 0;

      for (int i = 36; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null
            && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBlock) {
            ItemBlock block = (ItemBlock)mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
            if (block.getBlock() == Blocks.web) {
               item = i - 36;
               stacksize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
            }
         }
      }

      return item;
   }

   public static int getBucketSlot() {
      int item = -1;
      int stacksize = 0;

      for (int i = 36; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null
            && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() == Items.water_bucket) {
            item = i - 36;
            stacksize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
         }
      }

      return item;
   }

   public static int getEmptyBucketSlot() {
      int item = -1;
      int stacksize = 0;

      for (int i = 36; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() == Items.bucket) {
            item = i - 36;
            stacksize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
         }
      }

      return item;
   }

   public static ItemStack getBucketSlotInventory() {
      ItemStack item = null;
      int stacksize = 0;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null
            && mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() == Items.water_bucket) {
            item = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            stacksize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
         }
      }

      return item;
   }

   public static int getProjectileSlot() {
      int item = -1;
      int stacksize = 0;

      for (int i = 36; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null
            && (
               mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemSnowball
                  || mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemEgg
                  || mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemFishingRod
            )
            && mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize >= stacksize) {
            item = i - 36;
            stacksize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
         }
      }

      return item;
   }

   public static ItemStack getProjectileSlotInventory() {
      ItemStack item = null;
      int stacksize = 0;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null
            && (
               mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemSnowball
                  || mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemEgg
                  || mc.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemFishingRod
            )
            && mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize >= stacksize) {
            item = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            stacksize = mc.thePlayer.inventoryContainer.getSlot(i).getStack().stackSize;
         }
      }

      return item;
   }

   public static float getProtection(ItemStack stack) {
      float prot = 0.0F;
      if (stack.getItem() instanceof ItemArmor) {
         ItemArmor armor = (ItemArmor)stack.getItem();
         prot = (float)(
            (double)(prot + (float)armor.damageReduceAmount)
               + (double)((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075
         );
         prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0);
         prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0);
         prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0);
         prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0);
         prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) / 100.0);
      }

      return prot;
   }

   public static boolean isBestArmor(ItemStack stack, int type) {
      float prot = getProtection(stack);
      String strType = "";
      if (type == 1) {
         strType = "helmet";
      } else if (type == 2) {
         strType = "chestplate";
      } else if (type == 3) {
         strType = "leggings";
      } else if (type == 4) {
         strType = "boots";
      }

      if (!stack.getUnlocalizedName().contains(strType)) {
         return false;
      } else {
         for (int i = 5; i < 45; i++) {
            if (Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
               ItemStack is = Minecraft.getMinecraft().thePlayer.inventoryContainer.getSlot(i).getStack();
               if (getProtection(is) > prot && is.getUnlocalizedName().contains(strType)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public static void drop(int slot) {
      Minecraft.getMinecraft()
         .playerController
         .windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 1, 4, Minecraft.getMinecraft().thePlayer);
   }

   public static void shiftClick(int slot) {
      Minecraft.getMinecraft()
         .playerController
         .windowClick(Minecraft.getMinecraft().thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.getMinecraft().thePlayer);
   }

   public static boolean isBadStack(ItemStack is, boolean preferSword, boolean keepTools) {
      for (int type = 1; type < 5; type++) {
         String strType = "";
         if (type == 1) {
            strType = "helmet";
         } else if (type == 2) {
            strType = "chestplate";
         } else if (type == 3) {
            strType = "leggings";
         } else if (type == 4) {
            strType = "boots";
         }

         if (is.getItem() instanceof ItemArmor && !isBestArmor(is, type) && is.getUnlocalizedName().contains(strType)) {
            return true;
         }

         if (mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()
            && isBestArmor(mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack(), type)
            && mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack().getUnlocalizedName().contains(strType)
            && is.getUnlocalizedName().contains(strType)) {
            return true;
         }
      }

      if (is.getItem() instanceof ItemSword && is != bestWeapon() && !preferSword) {
         return true;
      } else if (is.getItem() instanceof ItemSword && is != bestSword() && preferSword) {
         return true;
      } else if (is.getItem() instanceof ItemBow && is != bestBow()) {
         return true;
      } else {
         if (keepTools) {
            if (is.getItem() instanceof ItemAxe && is != bestAxe() && (preferSword || is != bestWeapon())) {
               return true;
            }

            if (is.getItem() instanceof ItemPickaxe && is != bestPick() && (preferSword || is != bestWeapon())) {
               return true;
            }

            if (is.getItem() instanceof ItemSpade && is != bestShovel()) {
               return true;
            }
         } else {
            if (is.getItem() instanceof ItemAxe && (preferSword || is != bestWeapon())) {
               return true;
            }

            if (is.getItem() instanceof ItemPickaxe && (preferSword || is != bestWeapon())) {
               return true;
            }

            if (is.getItem() instanceof ItemSpade) {
               return true;
            }
         }

         return false;
      }
   }

   public static boolean isBadStackStealer(ItemStack is, boolean preferSword, boolean keepTools) {
      for (int type = 1; type < 5; type++) {
         String strType = "";
         if (type == 1) {
            strType = "helmet";
         } else if (type == 2) {
            strType = "chestplate";
         } else if (type == 3) {
            strType = "leggings";
         } else if (type == 4) {
            strType = "boots";
         }

         if (is.getItem() instanceof ItemArmor && !isBestArmor(is, type) && is.getUnlocalizedName().contains(strType)) {
            return true;
         }

         if (mc.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()
            && isBestArmor(mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack(), type)
            && mc.thePlayer.inventoryContainer.getSlot(4 + type).getStack().getUnlocalizedName().contains(strType)
            && is.getUnlocalizedName().contains(strType)) {
            return true;
         }
      }

      if (is.getItem() instanceof ItemSword && getWeaponSkill(is) <= bestWeaponSkill() && !preferSword) {
         return true;
      } else if (is.getItem() instanceof ItemSword && getWeaponSkill(is) <= bestSwordSkill() && preferSword) {
         return true;
      } else if (is.getItem() instanceof ItemBow && getBowSkill(is) <= bestBowSkill()) {
         return true;
      } else {
         if (keepTools) {
            if (is.getItem() instanceof ItemAxe && getToolSkill(is) <= bestAxeSkill() && (preferSword || getWeaponSkill(is) <= bestWeaponSkill())) {
               return true;
            }

            if (is.getItem() instanceof ItemPickaxe && getToolSkill(is) <= bestPickSkill() && (preferSword || getWeaponSkill(is) <= bestWeaponSkill())) {
               return true;
            }

            if (is.getItem() instanceof ItemSpade && getToolSkill(is) <= bestShovelSkill() && (preferSword || getWeaponSkill(is) <= bestWeaponSkill())) {
               return true;
            }
         } else {
            if (is.getItem() instanceof ItemAxe && (preferSword || getWeaponSkill(is) <= bestWeaponSkill())) {
               return true;
            }

            if (is.getItem() instanceof ItemPickaxe && (preferSword || getWeaponSkill(is) <= bestWeaponSkill())) {
               return true;
            }

            if (is.getItem() instanceof ItemSpade && (preferSword || getWeaponSkill(is) <= bestWeaponSkill())) {
               return true;
            }
         }

         return false;
      }
   }

   public static float getWeaponSkill(ItemStack is) {
      return getItemDamage(is);
   }

   public static float getBowSkill(ItemStack is) {
      return getBowDamage(is);
   }

   public static float getToolSkill(ItemStack is) {
      return getToolRating(is);
   }

   public static float bestWeaponSkill() {
      float itemDamage = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            float toolDamage = getItemDamage(is);
            if (toolDamage >= itemDamage) {
               itemDamage = getItemDamage(is);
            }
         }
      }

      return itemDamage;
   }

   public static float bestSwordSkill() {
      float itemDamage = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemSword) {
               float swordDamage = getItemDamage(is);
               if (swordDamage >= itemDamage) {
                  itemDamage = getItemDamage(is);
               }
            }
         }
      }

      return itemDamage;
   }

   public static float bestBowSkill() {
      float itemDamage = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemBow) {
               float bowDamage = getBowDamage(is);
               if (bowDamage >= itemDamage) {
                  itemDamage = getBowDamage(is);
               }
            }
         }
      }

      return itemDamage;
   }

   public static float bestAxeSkill() {
      float itemSkill = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemAxe) {
               float toolSkill = getToolRating(is);
               if (toolSkill >= itemSkill) {
                  itemSkill = getToolRating(is);
               }
            }
         }
      }

      return itemSkill;
   }

   public static float bestPickSkill() {
      ItemStack bestTool = null;
      float itemSkill = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemPickaxe) {
               float toolSkill = getToolRating(is);
               if (toolSkill >= itemSkill) {
                  itemSkill = getToolRating(is);
               }
            }
         }
      }

      return itemSkill;
   }

   public static float bestShovelSkill() {
      float itemSkill = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemSpade) {
               float toolSkill = getToolRating(is);
               if (toolSkill >= itemSkill) {
                  itemSkill = getToolRating(is);
               }
            }
         }
      }

      return itemSkill;
   }

   public static ItemStack bestWeapon() {
      ItemStack bestWeapon = null;
      float itemDamage = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemSword || is.getItem() instanceof ItemAxe || is.getItem() instanceof ItemPickaxe) {
               float toolDamage = getItemDamage(is);
               if (toolDamage >= itemDamage) {
                  itemDamage = getItemDamage(is);
                  bestWeapon = is;
               }
            }
         }
      }

      return bestWeapon;
   }

   public static ItemStack bestSword() {
      ItemStack bestSword = null;
      float itemDamage = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemSword) {
               float swordDamage = getItemDamage(is);
               if (swordDamage >= itemDamage) {
                  itemDamage = getItemDamage(is);
                  bestSword = is;
               }
            }
         }
      }

      return bestSword;
   }

   public static ItemStack bestBow() {
      ItemStack bestBow = null;
      float itemDamage = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemBow) {
               float bowDamage = getBowDamage(is);
               if (bowDamage >= itemDamage) {
                  itemDamage = getBowDamage(is);
                  bestBow = is;
               }
            }
         }
      }

      return bestBow;
   }

   public static ItemStack bestAxe() {
      ItemStack bestTool = null;
      float itemSkill = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemAxe) {
               float toolSkill = getToolRating(is);
               if (toolSkill >= itemSkill) {
                  itemSkill = getToolRating(is);
                  bestTool = is;
               }
            }
         }
      }

      return bestTool;
   }

   public static ItemStack bestPick() {
      ItemStack bestTool = null;
      float itemSkill = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemPickaxe) {
               float toolSkill = getToolRating(is);
               if (toolSkill >= itemSkill) {
                  itemSkill = getToolRating(is);
                  bestTool = is;
               }
            }
         }
      }

      return bestTool;
   }

   public static ItemStack bestShovel() {
      ItemStack bestTool = null;
      float itemSkill = -1.0F;

      for (int i = 9; i < 45; i++) {
         if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (is.getItem() instanceof ItemSpade) {
               float toolSkill = getToolRating(is);
               if (toolSkill >= itemSkill) {
                  itemSkill = getToolRating(is);
                  bestTool = is;
               }
            }
         }
      }

      return bestTool;
   }

   public static float getToolRating(ItemStack itemStack) {
      float damage = getToolMaterialRating(itemStack, false);
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack) * 2.0F;
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, itemStack) * 0.5F;
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, itemStack) * 0.5F;
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.1F;
      return damage + (float)(itemStack.getMaxDamage() - itemStack.getItemDamage()) * 1.0E-12F;
   }

   public static float getItemDamage(ItemStack itemStack) {
      float damage = getToolMaterialRating(itemStack, true);
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25F;
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.5F;
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.01F;
      damage += (float)(itemStack.getMaxDamage() - itemStack.getItemDamage()) * 1.0E-12F;
      if (itemStack.getItem() instanceof ItemSword) {
         damage = (float)((double)damage + 0.2);
      }

      return damage;
   }

   public static float getBowDamage(ItemStack itemStack) {
      float damage = 5.0F;
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack) * 1.25F;
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack) * 0.75F;
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) * 0.5F;
      damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.1F;
      return damage + ((float)itemStack.getMaxDamage() - (float)itemStack.getItemDamage() * 0.001F);
   }

   public static float getToolMaterialRating(ItemStack itemStack, boolean checkForDamage) {
      Item is = itemStack.getItem();
      float rating = 0.0F;
      if (is instanceof ItemSword) {
         String var4;
         switch ((var4 = ((ItemSword)is).getToolMaterialName()).hashCode()) {
            case -916080124:
               if (var4.equals("EMERALD")) {
                  rating = 7.0F;
               }
               break;
            case 2193504:
               if (var4.equals("GOLD")) {
                  rating = 4.0F;
               }
               break;
            case 2256072:
               if (var4.equals("IRON")) {
                  rating = 6.0F;
               }
               break;
            case 2670253:
               if (var4.equals("WOOD")) {
                  rating = 4.0F;
               }
               break;
            case 79233093:
               if (var4.equals("STONE")) {
                  rating = 5.0F;
               }
         }
      } else if (is instanceof ItemPickaxe) {
         String var5;
         switch ((var5 = ((ItemPickaxe)is).getToolMaterialName()).hashCode()) {
            case -916080124:
               if (var5.equals("EMERALD")) {
                  rating = (float)(checkForDamage ? 5 : 50);
               }
               break;
            case 2193504:
               if (var5.equals("GOLD")) {
                  rating = 2.0F;
               }
               break;
            case 2256072:
               if (var5.equals("IRON")) {
                  rating = (float)(checkForDamage ? 4 : 40);
               }
               break;
            case 2670253:
               if (var5.equals("WOOD")) {
                  rating = 2.0F;
               }
               break;
            case 79233093:
               if (var5.equals("STONE")) {
                  rating = 3.0F;
               }
         }
      } else if (is instanceof ItemAxe) {
         String var6;
         switch ((var6 = ((ItemAxe)is).getToolMaterialName()).hashCode()) {
            case -916080124:
               if (var6.equals("EMERALD")) {
                  rating = 6.0F;
               }
               break;
            case 2193504:
               if (var6.equals("GOLD")) {
                  rating = 3.0F;
               }
               break;
            case 2256072:
               if (var6.equals("IRON")) {
                  rating = 5.0F;
               }
               break;
            case 2670253:
               if (var6.equals("WOOD")) {
                  rating = 3.0F;
               }
               break;
            case 79233093:
               if (var6.equals("STONE")) {
                  rating = 4.0F;
               }
         }
      } else if (is instanceof ItemSpade) {
         String var7;
         switch ((var7 = ((ItemSpade)is).getToolMaterialName()).hashCode()) {
            case -916080124:
               if (var7.equals("EMERALD")) {
                  rating = 4.0F;
               }
               break;
            case 2193504:
               if (var7.equals("GOLD")) {
                  rating = 1.0F;
               }
               break;
            case 2256072:
               if (var7.equals("IRON")) {
                  rating = 3.0F;
               }
               break;
            case 2670253:
               if (var7.equals("WOOD")) {
                  rating = 1.0F;
               }
               break;
            case 79233093:
               if (var7.equals("STONE")) {
                  rating = 2.0F;
               }
         }
      }

      return rating;
   }

   public static void openInv(String mode) {
      if (mode.equalsIgnoreCase("Spoof") && !isInventoryOpen && !(mc.currentScreen instanceof GuiInventory)) {
         mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
         isInventoryOpen = true;
      }
   }

   public static void closeInv(String mode) {
      if (mode.equalsIgnoreCase("Spoof") && isInventoryOpen && !(mc.currentScreen instanceof GuiInventory)) {
         mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C0DPacketCloseWindow(mc.thePlayer.inventoryContainer.windowId));

         for (KeyBinding bind : moveKeys) {
            KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
         }

         isInventoryOpen = false;
      }
   }
}
