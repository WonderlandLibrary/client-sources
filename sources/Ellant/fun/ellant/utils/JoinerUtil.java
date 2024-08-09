/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package fun.ellant.utils;

import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import fun.ellant.utils.client.IMinecraft;
import fun.ellant.utils.player.InventoryUtil;

public class JoinerUtil
implements IMinecraft {
    public static void selectCompass() {
        int slot = InventoryUtil.getItemSlot(Items.COMPASS);
        if (slot == -1) {
            return;
        }
        JoinerUtil.mc.player.inventory.currentItem = slot;
        JoinerUtil.mc.player.connection.sendPacket(new CHeldItemChangePacket(slot));
    }
}

