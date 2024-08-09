/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.network.play.client.CConfirmTeleportPacket;

@FunctionRegister(name="PortalGodMode", type=Category.Player)
public class PortalGodMode
extends Function {
    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof CConfirmTeleportPacket) {
            eventPacket.cancel();
        }
    }
}

