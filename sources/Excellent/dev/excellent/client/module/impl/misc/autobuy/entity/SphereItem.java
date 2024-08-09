package dev.excellent.client.module.impl.misc.autobuy.entity;

import dev.excellent.client.module.impl.misc.autobuy.util.HeadAPI;
import lombok.Getter;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

@Getter
public class SphereItem extends DonateItem {
    private final String skin;

    public SphereItem(ItemStack itemStack, int price, String name, String tag, String skin) {
        super(itemStack, new HashMap<>(), price, name, tag);
        this.skin = skin;
        HeadAPI.encodeBase64(this.getItemStack(), skin);
    }
}
