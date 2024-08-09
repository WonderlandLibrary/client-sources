/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.utils.player.MoveUtils;

@FunctionRegister(name="Parkour", type=Category.Movement)
public class Parkour
extends Function {
    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        if (MoveUtils.isBlockUnder(0.001f) && Parkour.mc.player.isOnGround()) {
            Parkour.mc.player.jump();
        }
    }
}

