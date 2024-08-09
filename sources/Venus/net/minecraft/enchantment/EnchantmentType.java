/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.enchantment;

import net.minecraft.block.Block;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.enchantment.IVanishable;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.TridentItem;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
public enum EnchantmentType {
    ARMOR{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof ArmorItem;
        }
    }
    ,
    ARMOR_FEET{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof ArmorItem && ((ArmorItem)item).getEquipmentSlot() == EquipmentSlotType.FEET;
        }
    }
    ,
    ARMOR_LEGS{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof ArmorItem && ((ArmorItem)item).getEquipmentSlot() == EquipmentSlotType.LEGS;
        }
    }
    ,
    ARMOR_CHEST{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof ArmorItem && ((ArmorItem)item).getEquipmentSlot() == EquipmentSlotType.CHEST;
        }
    }
    ,
    ARMOR_HEAD{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof ArmorItem && ((ArmorItem)item).getEquipmentSlot() == EquipmentSlotType.HEAD;
        }
    }
    ,
    WEAPON{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof SwordItem;
        }
    }
    ,
    DIGGER{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof ToolItem;
        }
    }
    ,
    FISHING_ROD{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof FishingRodItem;
        }
    }
    ,
    TRIDENT{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof TridentItem;
        }
    }
    ,
    BREAKABLE{

        @Override
        public boolean canEnchantItem(Item item) {
            return item.isDamageable();
        }
    }
    ,
    BOW{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof BowItem;
        }
    }
    ,
    WEARABLE{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof IArmorVanishable || Block.getBlockFromItem(item) instanceof IArmorVanishable;
        }
    }
    ,
    CROSSBOW{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof CrossbowItem;
        }
    }
    ,
    VANISHABLE{

        @Override
        public boolean canEnchantItem(Item item) {
            return item instanceof IVanishable || Block.getBlockFromItem(item) instanceof IVanishable || BREAKABLE.canEnchantItem(item);
        }
    };


    public abstract boolean canEnchantItem(Item var1);
}

