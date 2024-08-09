/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventEntityLeave;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;

@FunctionRegister(name="LeaveTracker", type=Category.Misc)
public class LeaveTracker
extends Function {
    @Subscribe
    private void onEntityLeave(EventEntityLeave eventEntityLeave) {
        Entity entity2 = eventEntityLeave.getEntity();
        if (!this.isEntityValid(entity2)) {
            return;
        }
        String string = "\u0418\u0433\u0440\u043e\u043a " + entity2.getDisplayName().getString() + " \u043b\u0438\u0432\u043d\u0443\u043b \u043d\u0430 " + entity2.getStringPosition();
        this.print(string);
    }

    private boolean isEntityValid(Entity entity2) {
        if (!(entity2 instanceof AbstractClientPlayerEntity) || entity2 instanceof ClientPlayerEntity) {
            return true;
        }
        return !(LeaveTracker.mc.player.getDistance(entity2) < 100.0f);
    }
}

