package wtf.diablo.client.util.mc.player;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.*;
import net.minecraft.util.BlockPos;

public final class InventoryUtil {
    private InventoryUtil() {
    }

    public static int getBestTool(final BlockPos pos) {
        final int originalSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;

        final Minecraft mc = Minecraft.getMinecraft();
        final Block block = mc.theWorld.getBlockState(pos).getBlock();

        float strength = block.getBlockHardness(mc.theWorld, pos);
        int bestItemIndex = -1;

        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getStrVsBlock(block) > strength) {
                if (itemStack.getItem() instanceof ItemSword)
                    continue;

                strength = itemStack.getStrVsBlock(block);
                bestItemIndex = i;
            }
        }
        return bestItemIndex != -1 ? bestItemIndex : originalSlot;
    }


    public static float getMineSpeed(final ItemStack itemStack) {
        final Item item = itemStack.getItem();
        int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, itemStack);

        //Percentage of Efficiency per Level
        switch (level) {
            case 1:
                level = 30;
                break;
            case 2:
                level = 69;
                break;
            case 3:
                level = 120;
                break;
            case 4:
                level = 186;
                break;
            case 5:
                level = 271;
                break;
            default:
                level = 0;
                break;
        }

        if (item instanceof ItemPickaxe) {
            return ((ItemPickaxe) item).getToolMaterial().getEfficiencyOnProperMaterial() + level;
        } else if (item instanceof ItemSpade) {
            return ((ItemSpade) item).getToolMaterial().getEfficiencyOnProperMaterial() + level;
        } else if (item instanceof ItemAxe) {
            return ((ItemAxe) item).getToolMaterial().getEfficiencyOnProperMaterial() + level;
        }
        return 0;
    }

    public static int getSlotId(final int slot) {
        if (slot >= 36) return 8 - (slot - 36);
        if (slot < 9) return slot + 36;
        return slot;
    }

    public static float getSwordDamage(final ItemStack itemStack) {
        final ItemSword sword = (ItemSword) itemStack.getItem();
        final int level = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack);
        return (float) (sword.getDamageVsEntity() + level * 1.25);
    }

}
