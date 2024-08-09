/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.player;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;

@FunctionRegister(name="NoJumpDelay", type=Category.Player)
public class NoJumpDelay
extends Function {
    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        NoJumpDelay.mc.player.jumpTicks = 0;
    }
}

