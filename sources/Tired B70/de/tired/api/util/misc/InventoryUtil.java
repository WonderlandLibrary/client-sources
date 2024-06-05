package de.tired.api.util.misc;

import de.tired.interfaces.IHook;
import com.google.common.collect.Multimap;
import net.minecraft.block.*;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InventoryUtil implements IHook {

    public static int itemSlot = 5;

    public static int findBestTool(BlockPos breakingPos) {
        int bestTool = -1;
        double bestDamage = 1;

        for (int i = 0; i < 9; i++) {
            final ItemStack stack = MC.thePlayer.inventory.getStackInSlot(i);
            if (stack == null) continue;
            final double damage = stack.getStrVsBlock(breakingPos.getBlock());

            if (damage > bestDamage) {
                bestDamage = damage;
                bestTool = i;
            }
        }

        return bestTool;
    }

    public static int getStrongestWeapon(boolean checkSlot, boolean preferSwords) {
        return getStrongestWeapon(MC.thePlayer.inventoryContainer, checkSlot, preferSwords);
    }

    public static int getStrongestWeapon(Container cont, boolean checkSlot, boolean preferSwords) {
        float weaponDamage = 0.0F;
        if (checkSlot && MC.thePlayer.inventory.getStackInSlot(0) != null) {
            ItemStack currentStack = MC.thePlayer.inventory.getStackInSlot(0);
            weaponDamage = getWeaponGoodness(currentStack, preferSwords);
        }

        int bestSlot = -1;

        for(int i = cont == MC.thePlayer.inventoryContainer ? 9 : 0; i < cont.inventorySlots.size(); ++i) {
            if (cont.getSlot(i).getHasStack()) {
                ItemStack stack = cont.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool) {
                    float damage = getWeaponGoodness(stack, preferSwords);
                    if (damage > weaponDamage) {
                        weaponDamage = damage;
                        bestSlot = i;
                    }
                }
            }
        }

        return bestSlot;
    }

    public static float getItemDamage(ItemStack itemStack) {
        Multimap<String, AttributeModifier> multimap = itemStack.getAttributeModifiers();
        if (!multimap.isEmpty()) {
            Iterator<Map.Entry<String, AttributeModifier>> iterator = multimap.entries().iterator();
            if (iterator.hasNext()) {
                Map.Entry<String, AttributeModifier> entry = iterator.next();
                AttributeModifier attributeModifier = entry.getValue();
                double damage;
                if (attributeModifier.getOperation() != 1 && attributeModifier.getOperation() != 2) {
                    damage = attributeModifier.getAmount();
                } else {
                    damage = attributeModifier.getAmount() * 100.0D;
                }

                if (attributeModifier.getAmount() > 1.0D) {
                    return 1.0F + (float)damage;
                }

                return 1.0F;
            }
        }

        return 1.0F;
    }

    public static int getBestBow(boolean checkSlot) {
        return getBestBow(MC.thePlayer.inventoryContainer, checkSlot);
    }

    public static int getBestBow(Container cont, boolean checkSlot) {
        int currentDamage = 0;
        if (checkSlot && MC.thePlayer.inventory.getStackInSlot(2) != null && MC.thePlayer.inventory.getStackInSlot(2).getItem() instanceof ItemBow) {
            ItemStack currentStack = MC.thePlayer.inventory.getStackInSlot(2);
            currentDamage = getBowGoodness(currentStack);
        }

        int bestSlot = -1;

        for(int i = cont == MC.thePlayer.inventoryContainer ? 9 : 0; i < cont.inventorySlots.size(); ++i) {
            if (cont.getSlot(i).getHasStack()) {
                ItemStack stack = cont.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemBow) {
                    int slotDamage = getBowGoodness(stack);
                    if (slotDamage > currentDamage) {
                        currentDamage = slotDamage;
                        bestSlot = i;
                    }
                }
            }
        }

        return bestSlot;
    }

    public static float getFoodGoodness(ItemStack stack) {
        float goodness = 0.0F;
        if (stack.getItem() instanceof ItemFood) {
            goodness = ((ItemFood)stack.getItem()).getSaturationModifier(stack);
        }

        return goodness;
    }

    public static boolean isItemBlock(ItemStack item) {
        if (!(item.getItem() instanceof ItemBlock)) {
            return false;
        } else {
            ItemBlock ib = (ItemBlock)item.getItem();
            return isFullBlock(ib.getBlock());
        }
    }

    public static boolean isFullBlock(Block item) {
        return !item.isPassable(MC.theWorld, MC.thePlayer.playerLocation) && item.isCollidable() && !(item instanceof BlockChest) && !(item instanceof BlockWorkbench) && !(item instanceof BlockNote) && !(item instanceof BlockTNT) && item != Blocks.chest && item != Blocks.noteblock && item != Blocks.sand && item != Blocks.crafting_table && item != Blocks.red_flower && item != Blocks.yellow_flower && item != Blocks.web && item != Blocks.brown_mushroom && item != Blocks.red_mushroom;
    }

    public static boolean isChestEmpty() {
        if (MC.currentScreen instanceof GuiChest) {
            final GuiChest chest = (GuiChest)MC.currentScreen;
            for (int index = 0; index < chest.lowerChestInventory.getSizeInventory(); ++index) {
                final ItemStack stack = chest.lowerChestInventory.getStackInSlot(index);
                if (stack != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int countBlocks(Container inventory) {
        int count = 0;

        for(int i = 9; i < 45; ++i) {
            if (inventory.getSlot(i).getHasStack() && isItemBlock(inventory.getSlot(i).getStack())) {
                count += inventory.getSlot(i).getStack().stackSize;
            }
        }

        return count;
    }


    public static boolean isUsefultem(ItemStack item) {
        return item.getItem() != Items.arrow && !(item.getItem() instanceof ItemFood) && !(item.getItem() instanceof ItemPotion) && !(item.getItem() instanceof ItemBlock) && item.getItem() != Items.ender_pearl && item.getItem() != Items.experience_bottle && item.getItem() != Items.brick && item.getItem() != Items.water_bucket && item.getItem() != Items.iron_ingot && item.getItem() != Items.gold_ingot && !item.getDisplayName().toLowerCase().contains("web") && item.getItem() != Items.diamond && item.getItem() != Items.stick && item.getItem() != Items.bucket && item.getItem() != Items.emerald && item.getItem() != Items.fireworks;
    }

    public static int getBestPickaxe(boolean checkSlot) {
        return getBestPickaxe(MC.thePlayer.inventoryContainer, checkSlot);
    }

    public static int getBestPickaxe(Container cont, boolean checkSlot) {
        float currentEfficence = 0.0F;
        int bestSlot = -1;

        for(int i = cont == MC.thePlayer.inventoryContainer ? 9 : 0; i < cont.inventorySlots.size(); ++i) {
            if (cont.getSlot(i).getHasStack() && (!checkSlot || i != getStrongestWeapon(true, false))) {
                ItemStack stack = cont.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemPickaxe) {
                    float slotEfficence = getToolGoodness(stack);
                    if (slotEfficence > currentEfficence) {
                        currentEfficence = slotEfficence;
                        bestSlot = i;
                    }
                }
            }
        }

        return bestSlot;
    }

    public static float getToolGoodness(ItemStack stack) {
        float goodness = 0.0F;
        if (stack.getItem() instanceof ItemPickaxe) {
            goodness = ((ItemPickaxe)stack.getItem()).getToolMaterial().getEfficiencyOnProperMaterial() + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
        } else if (stack.getItem() instanceof ItemAxe) {
            goodness = (float)(((ItemAxe)stack.getItem()).getToolMaterial().getHarvestLevel() + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack));
        } else if (stack.getItem() instanceof ItemSpade) {
            goodness = (float)(((ItemSpade)stack.getItem()).getToolMaterial().getHarvestLevel() + EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack));
        }

        return goodness;
    }


    public static int getBestAxe(boolean checkSlot) {
        return getBestAxe(MC.thePlayer.inventoryContainer, checkSlot);
    }

    public static int getBestAxe(Container cont, boolean checkSlot) {
        float currentEfficence = 0.0F;
        int bestSlot = -1;

        for(int i = cont == MC.thePlayer.inventoryContainer ? 9 : 0; i < cont.inventorySlots.size(); ++i) {
            if (cont.getSlot(i).getHasStack() && (!checkSlot || i != getStrongestWeapon(true, false))) {
                ItemStack stack = cont.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemAxe) {
                    float slotEfficence = getToolGoodness(stack);
                    if (slotEfficence > currentEfficence) {
                        currentEfficence = slotEfficence;
                        bestSlot = i;
                    }
                }
            }
        }

        return bestSlot;
    }

    public static int getBestSpade(boolean checkSlot) {
        return getBestSpade(MC.thePlayer.inventoryContainer, checkSlot);
    }

    public static int getBestSpade(Container cont, boolean checkSlot) {
        float currentEfficence = 0.0F;
        int bestSlot = -1;

        for (int i = cont == MC.thePlayer.inventoryContainer ? 9 : 0; i < cont.inventorySlots.size(); ++i) {
            if (cont.getSlot(i).getHasStack() && (!checkSlot || i != getStrongestWeapon(true, false))) {
                ItemStack stack = cont.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemSpade) {
                    float slotEfficence = getToolGoodness(stack);
                    if (slotEfficence > currentEfficence) {
                        currentEfficence = slotEfficence;
                        bestSlot = i;
                    }
                }
            }
        }

        return bestSlot;
    }

        public static boolean isUselessItem(ItemStack item) {
        if (item.getItem() instanceof ItemBlock) {
            if (countBlocks(MC.thePlayer.inventoryContainer) > 256) {
                return true;
            }
        }

        return !(item.getItem() instanceof ItemArmor) && (item.getDisplayName().toLowerCase().contains("slab") || item.getDisplayName().toLowerCase().contains("step") || item.getDisplayName().toLowerCase().contains("chest") || item.getDisplayName().toLowerCase().contains("ladder") || item.getDisplayName().toLowerCase().contains("sapling") || item.getDisplayName().toLowerCase().contains("stair") || item.getDisplayName().toLowerCase().contains("cactus") || item.getItem() == Items.flower_pot || item.getDisplayName().toLowerCase().contains("torch") || item.getDisplayName().toLowerCase().contains("mushroom") && !item.getDisplayName().toLowerCase().contains("soup") && !item.getDisplayName().toLowerCase().contains("stew"));
    }

    public static int getBestFood() {
        float currentSaturateFactor = 0.0F;
        if (MC.thePlayer.inventory.getStackInSlot(3) != null && (MC.thePlayer.inventory.getStackInSlot(3).getItem() instanceof ItemFood || MC.thePlayer.inventory.getStackInSlot(3).getItem() instanceof ItemAppleGold)) {
            ItemStack currentStack = MC.thePlayer.inventory.getStackInSlot(3);
            currentSaturateFactor = getFoodGoodness(currentStack);
        }

        int bestSlot = -1;

        for(int i = 9; i < 45; ++i) {
            if (MC.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                ItemStack stack = MC.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem() instanceof ItemFood) {
                    float slotSaturateFactor = getFoodGoodness(stack);
                    if (slotSaturateFactor > currentSaturateFactor) {
                        currentSaturateFactor = slotSaturateFactor;
                        bestSlot = i;
                    }
                }
            }
        }

        return bestSlot;
    }

    public static int getBowGoodness(ItemStack stack) {
        int goodness = 0;
        if (stack.getItem() instanceof ItemBow) {

            int slotDamage = EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack);
            slotDamage += EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            slotDamage += EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
            slotDamage += EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            goodness = slotDamage;
        }

        return goodness;
    }

    public static float getWeaponGoodness(ItemStack stack, boolean preferSwords) {
        float goodness = 0.0F;
        if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemTool) {
            float damage = getItemDamage(stack);
            damage += EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) > 0) {
                damage += (float)(EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 2);
            }

            if (preferSwords && stack.getItem() instanceof ItemSword) {
                ++damage;
            }

            goodness = damage;
        }

        return goodness;
    }

    public static int findArmorHotbar() {
        for(int i = 0; i < 9; ++i) {
            ItemStack itemStack = MC.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
                ARMOR_TYPE type = ARMOR_TYPE.getByID(Item.getIdFromItem(itemStack.getItem()));
                assert type != null;
                int ord = type.ordinal();
                int armorInt = ord == 0 ? 3 : (ord == 1 ? 2 : (ord == 2 ? 1 : (0)));
                if (MC.thePlayer.inventory.armorInventory[armorInt] == null) {
                    return i;
                }
            }
        }

        return -1;
    }

    public static List<Integer> findArmor(ARMOR_TYPE armorType) {
        return findArmor(MC.thePlayer.inventoryContainer, armorType);
    }

    public static List<Integer> findArmor(Container cont, ARMOR_TYPE armorType) {
        int[] itemIdsArray = armorType.ids;
        ArrayList<Integer> availableSlots = new ArrayList<>();

        for(int slots = cont == MC.thePlayer.inventoryContainer ? 9 : 0; slots < cont.getInventory().size(); ++slots) {
            ItemStack itemStack = cont.getSlot(slots).getStack();
            if (itemStack != null) {
                int itemId = Item.getIdFromItem(itemStack.getItem());

                for (int ids : itemIdsArray) {
                    if (itemId == ids) {
                        availableSlots.add(slots);
                    }
                }
            }
        }

        return availableSlots;
    }

    public static int getBestArmorInInventory(ARMOR_TYPE armorType) {
        return getBestArmorInInventory(MC.thePlayer.inventoryContainer, armorType, false);
    }

    public static int getBestArmorInInventory(Container cont, ARMOR_TYPE armorType, boolean countFromFirstSlot) {
        int slot = -1;

        for(int slots = countFromFirstSlot ? 0 : 9; slots < cont.getInventory().size(); ++slots) {
            if (slot == -1) {
                slot = slots;
            }

            if (cont.getSlot(slots).getHasStack() && cont.getSlot(slots).getStack().getItem() instanceof ItemArmor && ARMOR_TYPE.getByID(Item.getIdFromItem(cont.getSlot(slots).getStack().getItem())) == armorType && getArmorGoodness(cont.getSlot(slots).getStack()) > getArmorGoodness(cont.getSlot(slot).getStack())) {
                slot = slots;
            }
        }

        return slot;
    }


    public static int getArmorGoodness(ItemStack itemStack) {
        int goodness = 0;
        if (itemStack == null) {
            return 0;
        } else {
            if (itemStack.getItem() instanceof ItemArmor) {
                ItemArmor armor = (ItemArmor)itemStack.getItem();
                String material = armor.getArmorMaterial().getName();
                if (material.equalsIgnoreCase("leather")) {
                    ++goodness;
                } else if (material.equalsIgnoreCase("gold")) {
                    goodness += 2;
                } else if (material.equalsIgnoreCase("chainmail")) {
                    goodness += 3;
                } else if (material.equalsIgnoreCase("iron")) {
                    goodness += 4;
                } else if (material.equalsIgnoreCase("diamond")) {
                    goodness += 5;
                }

                goodness += EnchantmentHelper.getEnchantmentModifierDamage(new ItemStack[]{itemStack}, DamageSource.generic);
            }

            return goodness;
        }
    }

    public enum ARMOR_TYPE {
        HELMET(5, new int[]{298, 302, 306, 310, 314}),
        CHESTPLATE(6, new int[]{299, 315, 303, 307, 311}),
        LEGGINGS(7, new int[]{300, 316, 304, 308, 312}),
        BOOTS(8, new int[]{301, 305, 309, 313, 317});

        public int slot;
        public int[] ids;

         ARMOR_TYPE(int slot, int[] ids) {
            this.slot = slot;
            this.ids = ids;
        }

        public static ARMOR_TYPE getByID(int itemID) {
            ARMOR_TYPE[] types = values();

            for (ARMOR_TYPE type : types) {
                int[] ids = type.ids;

                for (int armorID : ids) {
                    if (armorID == itemID) {
                        return type;
                    }
                }
            }

            return null;
        }
    }

}
