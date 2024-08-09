/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.network.play.client.CCloseWindowPacket;

@FunctionRegister(name="xCarry", type=Category.Misc)
public class xCarry
extends Function {
    @Subscribe
    public void onPacket(EventPacket eventPacket) {
        if (xCarry.mc.player == null) {
            return;
        }
        if (eventPacket.getPacket() instanceof CCloseWindowPacket) {
            eventPacket.cancel();
        }
    }
}

