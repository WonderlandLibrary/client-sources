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

@FunctionRegister(name="Jesus", type=Category.Movement)
public class Jesus
extends Function {
    @Subscribe
    private void onUpdate(EventUpdate eventUpdate) {
        if (Jesus.mc.player.isInWater()) {
            float f = 10.0f;
            double d = Jesus.mc.player.getForward().x * (double)(f /= 100.0f);
            double d2 = Jesus.mc.player.getForward().z * (double)f;
            Jesus.mc.player.motion.y = 0.0;
            if (MoveUtils.isMoving() && MoveUtils.getMotion() < (double)0.9f) {
                Jesus.mc.player.motion.x *= 1.25;
                Jesus.mc.player.motion.z *= 1.25;
            }
        }
    }
}

