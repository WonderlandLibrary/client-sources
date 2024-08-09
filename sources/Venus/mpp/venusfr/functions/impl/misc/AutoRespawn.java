/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.client.gui.screen.DeathScreen;

@FunctionRegister(name="AutoRespawn", type=Category.Misc)
public class AutoRespawn
extends Function {
    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (AutoRespawn.mc.player == null || AutoRespawn.mc.world == null) {
            return;
        }
        if (AutoRespawn.mc.currentScreen instanceof DeathScreen && AutoRespawn.mc.player.deathTime > 5) {
            AutoRespawn.mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    }
}

