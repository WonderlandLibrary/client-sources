/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.server.SHeldItemChangePacket;

@FunctionRegister(name="ItemSwapFix", type=Category.Misc)
public class ItemSwapFix
extends Function {
    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        SHeldItemChangePacket sHeldItemChangePacket;
        int n;
        if (ItemSwapFix.mc.player == null) {
            return;
        }
        IPacket<?> iPacket = eventPacket.getPacket();
        if (iPacket instanceof SHeldItemChangePacket && (n = (sHeldItemChangePacket = (SHeldItemChangePacket)iPacket).getHeldItemHotbarIndex()) != ItemSwapFix.mc.player.inventory.currentItem) {
            ItemSwapFix.mc.player.connection.sendPacket(new CHeldItemChangePacket(Math.max(ItemSwapFix.mc.player.inventory.currentItem - 1, 0)));
            ItemSwapFix.mc.player.connection.sendPacket(new CHeldItemChangePacket(ItemSwapFix.mc.player.inventory.currentItem));
            eventPacket.cancel();
        }
    }
}

