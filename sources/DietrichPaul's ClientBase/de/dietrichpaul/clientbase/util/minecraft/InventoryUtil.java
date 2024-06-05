package de.dietrichpaul.clientbase.util.minecraft;

import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class InventoryUtil {

    public static float getArmorValue(ArmorItem item, ItemStack stack) {
        DamageSource damageSource = MinecraftClient.getInstance().player.getDamageSources().playerAttack(null);
        Enchantment prot = Enchantments.PROTECTION;
        int lvl = EnchantmentHelper.getLevel(prot, stack);
        float firstStep = DamageUtil.getDamageLeft(1, item.getProtection(), item.getToughness());
        float secondStep = DamageUtil.getInflictedDamage(firstStep, prot.getProtectionAmount(lvl, damageSource));
        return 1.0F / secondStep;
    }

    public static void getBestArmor(BestArmor armor, Inventory inventory) {
        boolean hotbarFix = false;
        if (inventory instanceof PlayerInventory playerInventory) {
            hotbarFix = true;
            for (int type = 0; type < 4; type++) {
                armor.test(new Slot(inventory, 8 - type, 8 - type, 0), playerInventory.getArmorStack(type));
            }
        }
        int lim = inventory instanceof PlayerInventory ? 36 : inventory.size();
        for (int i = 0; i < lim; i++) {
            int slot = i;
            if (hotbarFix && slot < 9) slot += 36;
            armor.test(new Slot(inventory, slot, slot % 9, (int) (slot / 9.0)), inventory.getStack(i));
        }
    }

    public static class BestArmor {
        private Slot[] slots;
        private ItemStack[] stacks;
        private float[] values;

        public BestArmor() {
            this.slots = new Slot[4];
            this.stacks = new ItemStack[4];
            this.values = new float[4];
        }

        public Slot[] getSlots() {
            return slots;
        }

        public float[] getValues() {
            return values;
        }

        public ItemStack[] getStacks() {
            return stacks;
        }

        public void test(Slot slot, ItemStack stack) {
            if (stack.getItem() instanceof ArmorItem item) {
                int type = item.getSlotType().getEntitySlotId();
                float value = getArmorValue(item, stack);

                if (value > values[type]) {
                    values[type] = value;
                    slots[type] = slot;
                    stacks[type] = stack;
                }
            }
        }
    }

}
