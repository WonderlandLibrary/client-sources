/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import java.util.concurrent.ThreadLocalRandom;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;

@FunctionRegister(name="AntiAFK", type=Category.Player)
public class AntiAFK
extends Function {
    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        if (AntiAFK.mc.player.ticksExisted % 200 != 0) {
            return;
        }
        if (AntiAFK.mc.player.isOnGround()) {
            AntiAFK.mc.player.jump();
        }
        AntiAFK.mc.player.rotationYaw += ThreadLocalRandom.current().nextFloat(-10.0f, 10.0f);
    }
}

