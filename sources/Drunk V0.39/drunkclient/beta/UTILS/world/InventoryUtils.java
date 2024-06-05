/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.world;

import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.AtomicDouble;
import drunkclient.beta.UTILS.world.Wrapper;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

public final class InventoryUtils {
    public static final int INCLUDE_ARMOR_BEGIN = 5;
    public static final int EXCLUDE_ARMOR_BEGIN = 9;
    public static final int ONLY_HOT_BAR_BEGIN = 36;
    public static final int END = 45;
    private static final List<Integer> BAD_EFFECTS_IDS = Arrays.asList(Potion.poison.id, Potion.weakness.id, Potion.wither.id, Potion.blindness.id, Potion.digSlowdown.id, Potion.harm.id);

    private InventoryUtils() {
    }

    public static double getDamageReduction(ItemStack stack) {
        double reduction = 0.0;
        ItemArmor armor = (ItemArmor)stack.getItem();
        if (!stack.isItemEnchanted()) return reduction += (double)armor.damageReduceAmount;
        reduction += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) * 0.25;
        return reduction += (double)armor.damageReduceAmount;
    }

    public static boolean isValid(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        if (stack.getItem() instanceof ItemBlock) {
            return InventoryUtils.isGoodBlockStack(stack);
        }
        if (stack.getItem() instanceof ItemSword) {
            return InventoryUtils.isBestSword(stack);
        }
        if (stack.getItem() instanceof ItemArmor) {
            return InventoryUtils.isBestArmor(stack);
        }
        if (stack.getItem() instanceof ItemPotion) {
            return InventoryUtils.isBuffPotion(stack);
        }
        if (stack.getItem() instanceof ItemFood) {
            return InventoryUtils.isGoodFood(stack);
        }
        if (!(stack.getItem() instanceof ItemBow)) return InventoryUtils.isGoodItem(stack);
        return InventoryUtils.isBestBow(stack);
    }

    public static boolean isBestBow(ItemStack itemStack) {
        AtomicDouble bestBowDmg = new AtomicDouble(-1.0);
        AtomicReference<Object> bestBow = new AtomicReference<Object>(null);
        Wrapper.forEachInventorySlot(9, 45, (slot, stack) -> {
            if (!(stack.getItem() instanceof ItemBow)) return;
            double damage = InventoryUtils.getBowDamage(stack);
            if (!(damage > bestBowDmg.get())) return;
            bestBow.set(stack);
            bestBowDmg.set(damage);
        });
        if (itemStack == bestBow.get()) return true;
        if (InventoryUtils.getBowDamage(itemStack) > bestBowDmg.get()) return true;
        return false;
    }

    public static double getBowDamage(ItemStack stack) {
        double damage = 0.0;
        if (!(stack.getItem() instanceof ItemBow)) return damage;
        if (!stack.isItemEnchanted()) return damage;
        damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
        return damage;
    }

    public static boolean isGoodItem(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof ItemBucket && ((ItemBucket)item).isFull != Blocks.flowing_water) {
            return false;
        }
        if (item instanceof ItemExpBottle) return false;
        if (item instanceof ItemFishingRod) return false;
        if (item instanceof ItemEgg) return false;
        if (item instanceof ItemSnowball) return false;
        if (item instanceof ItemSkull) return false;
        if (item instanceof ItemBucket) return false;
        return true;
    }

    public static boolean isBuffPotion(ItemStack stack) {
        PotionEffect effect;
        ItemPotion potion = (ItemPotion)stack.getItem();
        List<PotionEffect> effects = potion.getEffects(stack);
        if (effects.size() < 1) {
            return false;
        }
        Iterator<PotionEffect> iterator = effects.iterator();
        do {
            if (!iterator.hasNext()) return true;
        } while (!BAD_EFFECTS_IDS.contains((effect = iterator.next()).getPotionID()));
        return false;
    }

    public static boolean isGoodFood(ItemStack stack) {
        ItemFood food = (ItemFood)stack.getItem();
        if (food instanceof ItemAppleGold) {
            return true;
        }
        if (food.getHealAmount(stack) < 4) return false;
        if (!(food.getSaturationModifier(stack) >= 0.3f)) return false;
        return true;
    }

    public static boolean isBestSword(ItemStack itemStack) {
        AtomicDouble damage = new AtomicDouble(0.0);
        AtomicReference<Object> bestStack = new AtomicReference<Object>(null);
        Wrapper.forEachInventorySlot(9, 45, (slot, stack) -> {
            if (!(stack.getItem() instanceof ItemSword)) return;
            double newDamage = InventoryUtils.getItemDamage(stack);
            if (!(newDamage > damage.get())) return;
            damage.set(newDamage);
            bestStack.set(stack);
        });
        if (bestStack.get() == itemStack) return true;
        if (damage.get() < InventoryUtils.getItemDamage(itemStack)) return true;
        return false;
    }

    public static int getToolType(ItemStack stack) {
        ItemTool tool = (ItemTool)stack.getItem();
        if (tool instanceof ItemPickaxe) {
            return 0;
        }
        if (tool instanceof ItemAxe) {
            return 1;
        }
        if (!(tool instanceof ItemSpade)) return -1;
        return 2;
    }

    public static boolean isBestArmor(ItemStack itemStack) {
        ItemArmor itemArmor = (ItemArmor)itemStack.getItem();
        AtomicDouble reduction = new AtomicDouble(0.0);
        AtomicReference<Object> bestStack = new AtomicReference<Object>(null);
        Wrapper.forEachInventorySlot(5, 45, (slot, stack) -> {
            if (!(stack.getItem() instanceof ItemArmor)) return;
            ItemArmor stackArmor = (ItemArmor)stack.getItem();
            if (stackArmor.armorType != itemArmor.armorType) return;
            double newReduction = InventoryUtils.getDamageReduction(stack);
            if (!(newReduction > reduction.get())) return;
            reduction.set(newReduction);
            bestStack.set(stack);
        });
        if (bestStack.get() == itemStack) return true;
        if (reduction.get() < InventoryUtils.getDamageReduction(itemStack)) return true;
        return false;
    }

    public static boolean isGoodBlockStack(ItemStack stack) {
        if (stack.stackSize >= 1) return InventoryUtils.isValidBlock(Block.getBlockFromItem(stack.getItem()), true);
        return false;
    }

    public static boolean isValidBlock(Block block, boolean toPlace) {
        if (block instanceof BlockContainer) return false;
        if (!block.isFullBlock()) return false;
        if (!block.isFullCube()) return false;
        if (toPlace && block instanceof BlockFalling) {
            return false;
        }
        Material material = block.getMaterial();
        if (material.isLiquid()) return false;
        if (!material.isSolid()) return false;
        return true;
    }

    public static boolean isBad(ItemStack item) {
        if (item.getItem() instanceof ItemArmor) return false;
        if (item.getItem() instanceof ItemTool) return false;
        if (item.getItem() instanceof ItemBlock) return false;
        if (item.getItem() instanceof ItemEnderPearl) return false;
        if (item.getItem() instanceof ItemBow) return false;
        if (item.getItem() instanceof ItemSword) return false;
        if (item.getUnlocalizedName().contains("arrow")) return false;
        if (item.getItem() instanceof ItemFood) return false;
        if (item.getItem() instanceof ItemPotion) {
            if (!InventoryUtils.isBadPotion(item)) return false;
        }
        if (item.getDisplayName().toLowerCase().contains((Object)((Object)EnumChatFormatting.GRAY) + "(right click)")) return false;
        return true;
    }

    public static boolean isBadPotion(ItemStack stack) {
        PotionEffect effect;
        if (stack == null) return false;
        if (!(stack.getItem() instanceof ItemPotion)) return false;
        ItemPotion potion = (ItemPotion)stack.getItem();
        if (!ItemPotion.isSplash(stack.getItemDamage())) return false;
        Iterator<PotionEffect> iterator = potion.getEffects(stack).iterator();
        do {
            if (!iterator.hasNext()) return false;
            PotionEffect o = iterator.next();
            effect = o;
            if (effect.getPotionID() == Potion.poison.getId()) return true;
            if (effect.getPotionID() == Potion.harm.getId()) return true;
            if (effect.getPotionID() == Potion.moveSlowdown.getId()) return true;
        } while (effect.getPotionID() != Potion.weakness.getId());
        return true;
    }

    public static double getItemDamage(ItemStack stack) {
        double damage = 0.0;
        Multimap<String, AttributeModifier> attributeModifierMap = stack.getAttributeModifiers();
        for (String attributeName : attributeModifierMap.keySet()) {
            if (!attributeName.equals("generic.attackDamage")) continue;
            Iterator<AttributeModifier> attributeModifiers = attributeModifierMap.get(attributeName).iterator();
            if (!attributeModifiers.hasNext()) break;
            damage += attributeModifiers.next().getAmount();
            break;
        }
        if (!stack.isItemEnchanted()) return damage;
        damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
        damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25;
        return damage;
    }

    private static class Tool {
        private final int slot;
        private final double efficiency;
        private final ItemStack stack;

        public Tool(int slot, double efficiency, ItemStack stack) {
            this.slot = slot;
            this.efficiency = efficiency;
            this.stack = stack;
        }

        public int getSlot() {
            return this.slot;
        }

        public double getEfficiency() {
            return this.efficiency;
        }

        public ItemStack getStack() {
            return this.stack;
        }
    }

    public static enum ClickType {
        CLICK,
        SHIFT_CLICK,
        SWAP_WITH_HOT_BAR_SLOT,
        PLACEHOLDER,
        DROP_ITEM;

    }
}

