package club.strifeclient.util.player;

import club.strifeclient.util.misc.MinecraftUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFalling;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public final class InventoryUtil extends MinecraftUtil {

    public enum ClickType {
        // if mouseButtonClicked is 0 `DROP_ITEM` will drop 1 item from the stack
        // else if it is 1, it will drop the entire stack
        CLICK, SHIFT_CLICK, SWAP_WITH_HOTBAR, PLACEHOLDER, DROP_ITEM;
    }

    public static final int INCLUDE_ARMOR = 5;
    public static final int EXCLUDE_ARMOR = 9;
    public static final int HOTBAR = 36;
    public static final int END = 45;

    public static boolean isHoldingSword() {
        final ItemStack heldItem = mc.thePlayer.getHeldItem();
        return heldItem != null && heldItem.getItem() instanceof ItemSword;
    }

    public static boolean isValidBlock(final ItemStack stack) {
        if (stack == null || !(stack.getItem() instanceof ItemBlock) || stack.stackSize < 0) return false;
        return isValidBlock(Block.getBlockFromItem(stack.getItem()));
    }
    public static boolean isValidBlock(final Block block) {
        if (block instanceof BlockContainer || block instanceof BlockFalling) return false;
        return block.isFullBlock() && block.isFullCube();
    }
    public static int getBestBlockSlot(final boolean inventory) {
        double size = 0;
        int bestSlot = -1;
        for (int i = inventory ? EXCLUDE_ARMOR : HOTBAR; i < END; i++) {
            final ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (isValidBlock(stack) && stack.stackSize > size) {
                size = stack.stackSize;
                bestSlot = inventory ? i : i - HOTBAR;
            }
        }
        return bestSlot;
    }
    public static void windowClick(final int windowId, final int slotId, final int mouseButtonClicked, final ClickType mode) {
        mc.playerController.windowClick(windowId, slotId, mouseButtonClicked, mode.ordinal(), mc.thePlayer);
    }
    public static void windowClick(final int slotId, final int mouseButtonClicked, final ClickType mode) {
        windowClick(mc.thePlayer.inventoryContainer.windowId, slotId, mouseButtonClicked, mode);
    }
    public static boolean isChestEmpty(final IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            if (inventory.getStackInSlot(i) != null)
                return false;
        }
        return true;
    }
    public static boolean isInventoryFull() {
        return isContainerFull(mc.thePlayer.inventoryContainer);
    }
    public static boolean isContainerFull(final Container container) {
        for (int i = EXCLUDE_ARMOR; i < END; i++) {
            if (!container.getSlot(i).getHasStack())
                return false;
        }
        return true;
    }
    public static boolean isGoodPotion(ItemStack stack) {
        ItemPotion potion = (ItemPotion) stack.getItem();
        List<PotionEffect> effects = potion.getEffects(stack);
        for (PotionEffect effect : effects)
            if (Potion.potionTypes[effect.getPotionID()].isBadEffect())
                return false;
        return true;
    }
    public static int getNextEmptySlot() {
        for (int i = HOTBAR; i < END; i++) {
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack())
                return i;
        }
        return -1;
    }
    public static float getItemDamage(ItemStack stack) {
        float damage = 0;
        if (stack == null) return damage;
        if(EnumChatFormatting.stripFormatting(stack.getDisplayName()).contains("(Right Click)")) return damage;
        if (stack.getItem() instanceof ItemSword) {
            ItemSword sword = (ItemSword) stack.getItem();
            damage = 1 + sword.getDamageVsEntity()
                    + EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
        }
        if (stack.getItem() instanceof ItemPickaxe) {
            ItemPickaxe pickaxe = (ItemPickaxe) stack.getItem();
            damage = 1 + pickaxe.getStrVsBlock(stack, Block.getBlockById(4)) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack);
        }
        if (stack.getItem() instanceof ItemSpade) {
            ItemSpade shovel = (ItemSpade) stack.getItem();
            damage = 1 + shovel.getStrVsBlock(stack, Block.getBlockById(3)) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack);
        }
        if (stack.getItem() instanceof ItemAxe) {
            ItemAxe axe = (ItemAxe) stack.getItem();
            damage = 1 + axe.getStrVsBlock(stack, Block.getBlockById(17)) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
        }
        if (stack.getItem() instanceof ItemBow) {
            damage = 1 + EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
        }
        if (stack.getItem() instanceof ItemArmor) {
            ItemArmor armor = (ItemArmor) stack.getItem();
            damage = armor.damageReduceAmount +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack) * 0.33F +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.respiration.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.depthStrider.effectId, stack) +
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.featherFalling.effectId, stack);
        }
        if (stack.getItem() instanceof ItemPotion && isGoodPotion(stack)) {
            ItemPotion potion = (ItemPotion) stack.getItem();
            List<PotionEffect> effects = potion.getEffects(stack);
            damage = effects.get(0).getAmplifier() + stack.stackSize;
        }
        if (stack.getItem() instanceof ItemBlock && isValidBlock(stack))
            damage = 1;
        if (stack.getItem() instanceof ItemFood)
            damage = ((ItemFood) stack.getItem()).getSaturationModifier(stack);
        if (stack.getItem() instanceof ItemSkull) {
            if (!stack.getDisplayName().contains("Frog"))
                damage = 1;
        }
        if (stack.getItem() == Items.ender_pearl)
            damage = 1;
        if (stack.getItem() instanceof ItemTool && ((ItemTool) stack.getItem()).getToolMaterial() == Item.ToolMaterial.GOLD)
            damage -= 0.5f;
        return damage;
    }
}
