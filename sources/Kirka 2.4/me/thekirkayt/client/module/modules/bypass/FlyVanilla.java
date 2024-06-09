/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.bypass;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MovementInput;

@Module.Mod
public class FlyVanilla
extends Module {
    @Option.Op
    private boolean damage;
    @Option.Op(min=0.0, max=9.0, increment=0.01)
    private double speed = 0.8;

    @EventTarget
    private void onUpdate(UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            ClientUtils.player().motionY = ClientUtils.movementInput().jump ? this.speed : (ClientUtils.movementInput().sneak ? -this.speed : 0.0);
        }
    }

    @EventTarget
    private void onMove(MoveEvent event) {
        ClientUtils.setMoveSpeed(event, this.speed);
    }
}

