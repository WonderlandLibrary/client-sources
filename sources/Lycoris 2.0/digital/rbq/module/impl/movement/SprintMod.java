/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.movement;

import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.entity.EntityPlayerSP;
import digital.rbq.annotations.Label;
import digital.rbq.events.player.MoveEvent;
import digital.rbq.module.Module;
import digital.rbq.module.ModuleCategory;
import digital.rbq.module.annotations.Category;

@Label(value="Sprint")
@Category(value=ModuleCategory.MOVEMENT)
public final class SprintMod
extends Module {
    @Listener(value=MoveEvent.class)
    public final void onMove() {
        EntityPlayerSP player = SprintMod.mc.thePlayer;
        if (player.isMoving() && player.getFoodStats().getFoodLevel() > 6) {
            player.setSprinting(true);
        }
    }
}

