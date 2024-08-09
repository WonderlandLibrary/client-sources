/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.autobuy;

import java.util.HashMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;

public final class AutoBuy {
    private final Item item;
    private final int price;
    private final int count;
    private final HashMap<Enchantment, Integer> enchanments;
    private final boolean enchantment;
    private final boolean items;
    private final boolean fake;
    private final boolean don;

    public AutoBuy(Item item, int n, int n2, HashMap<Enchantment, Integer> hashMap, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.item = item;
        this.price = n;
        this.count = n2;
        this.enchanments = hashMap;
        this.enchantment = bl;
        this.items = bl2;
        this.fake = bl3;
        this.don = bl4;
    }

    public Item getItem() {
        return this.item;
    }

    public int getPrice() {
        return this.price;
    }

    public int getCount() {
        return this.count;
    }

    public HashMap<Enchantment, Integer> getEnchanments() {
        return this.enchanments;
    }

    public boolean isEnchantment() {
        return this.enchantment;
    }

    public boolean isItems() {
        return this.items;
    }

    public boolean isFake() {
        return this.fake;
    }

    public boolean isDon() {
        return this.don;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof AutoBuy)) {
            return true;
        }
        AutoBuy autoBuy = (AutoBuy)object;
        if (this.getPrice() != autoBuy.getPrice()) {
            return true;
        }
        if (this.getCount() != autoBuy.getCount()) {
            return true;
        }
        if (this.isEnchantment() != autoBuy.isEnchantment()) {
            return true;
        }
        if (this.isItems() != autoBuy.isItems()) {
            return true;
        }
        if (this.isFake() != autoBuy.isFake()) {
            return true;
        }
        if (this.isDon() != autoBuy.isDon()) {
            return true;
        }
        Item item = this.getItem();
        Item item2 = autoBuy.getItem();
        if (item == null ? item2 != null : !item.equals(item2)) {
            return true;
        }
        HashMap<Enchantment, Integer> hashMap = this.getEnchanments();
        HashMap<Enchantment, Integer> hashMap2 = autoBuy.getEnchanments();
        return hashMap == null ? hashMap2 != null : !((Object)hashMap).equals(hashMap2);
    }

    public int hashCode() {
        int n = 59;
        int n2 = 1;
        n2 = n2 * 59 + this.getPrice();
        n2 = n2 * 59 + this.getCount();
        n2 = n2 * 59 + (this.isEnchantment() ? 79 : 97);
        n2 = n2 * 59 + (this.isItems() ? 79 : 97);
        n2 = n2 * 59 + (this.isFake() ? 79 : 97);
        n2 = n2 * 59 + (this.isDon() ? 79 : 97);
        Item item = this.getItem();
        n2 = n2 * 59 + (item == null ? 43 : item.hashCode());
        HashMap<Enchantment, Integer> hashMap = this.getEnchanments();
        n2 = n2 * 59 + (hashMap == null ? 43 : ((Object)hashMap).hashCode());
        return n2;
    }

    public String toString() {
        return "AutoBuy(item=" + this.getItem() + ", price=" + this.getPrice() + ", count=" + this.getCount() + ", enchanments=" + this.getEnchanments() + ", enchantment=" + this.isEnchantment() + ", items=" + this.isItems() + ", fake=" + this.isFake() + ", don=" + this.isDon() + ")";
    }
}

