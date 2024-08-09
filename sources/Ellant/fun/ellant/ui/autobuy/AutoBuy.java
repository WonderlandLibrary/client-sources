/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.ui.autobuy;

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

    public AutoBuy(Item item, int price, int count, HashMap<Enchantment, Integer> enchanments, boolean enchantment, boolean items, boolean fake, boolean don) {
        this.item = item;
        this.price = price;
        this.count = count;
        this.enchanments = enchanments;
        this.enchantment = enchantment;
        this.items = items;
        this.fake = fake;
        this.don = don;
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AutoBuy)) {
            return false;
        }
        AutoBuy other = (AutoBuy)o;
        if (this.getPrice() != other.getPrice()) {
            return false;
        }
        if (this.getCount() != other.getCount()) {
            return false;
        }
        if (this.isEnchantment() != other.isEnchantment()) {
            return false;
        }
        if (this.isItems() != other.isItems()) {
            return false;
        }
        if (this.isFake() != other.isFake()) {
            return false;
        }
        if (this.isDon() != other.isDon()) {
            return false;
        }
        Item this$item = this.getItem();
        Item other$item = other.getItem();
        if (this$item == null ? other$item != null : !this$item.equals(other$item)) {
            return false;
        }
        HashMap<Enchantment, Integer> this$enchanments = this.getEnchanments();
        HashMap<Enchantment, Integer> other$enchanments = other.getEnchanments();
        return !(this$enchanments == null ? other$enchanments != null : !((Object)this$enchanments).equals(other$enchanments));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getPrice();
        result = result * 59 + this.getCount();
        result = result * 59 + (this.isEnchantment() ? 79 : 97);
        result = result * 59 + (this.isItems() ? 79 : 97);
        result = result * 59 + (this.isFake() ? 79 : 97);
        result = result * 59 + (this.isDon() ? 79 : 97);
        Item $item = this.getItem();
        result = result * 59 + ($item == null ? 43 : $item.hashCode());
        HashMap<Enchantment, Integer> $enchanments = this.getEnchanments();
        result = result * 59 + ($enchanments == null ? 43 : ((Object)$enchanments).hashCode());
        return result;
    }

    public String toString() {
        return "AutoBuy(item=" + this.getItem() + ", price=" + this.getPrice() + ", count=" + this.getCount() + ", enchanments=" + this.getEnchanments() + ", enchantment=" + this.isEnchantment() + ", items=" + this.isItems() + ", fake=" + this.isFake() + ", don=" + this.isDon() + ")";
    }
}

