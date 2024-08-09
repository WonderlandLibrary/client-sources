package dev.excellent.client.module.impl.misc.autobuy.entity;

import lombok.Getter;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

@Getter
public class DonateItem extends AutoBuyItem {
    private final String name;
    private final String tag;

    public DonateItem(ItemStack itemStack, HashMap<Enchantment, Boolean> enchants, int price, String name, String tag) {
        super(itemStack, enchants, price);
        this.name = name;
        this.tag = tag;
    }
}
