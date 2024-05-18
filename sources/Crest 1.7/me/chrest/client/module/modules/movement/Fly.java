// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement;

import me.chrest.event.events.MoveEvent;
import me.chrest.event.EventTarget;
import me.chrest.utils.ClientUtils;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Fly")
public class Fly extends Module
{
    @Option.Op(name = "Glide")
    private boolean glide;
    @Option.Op(min = 0.0, max = 1.0, increment = 0.05, name = "Fall Speed")
    private double glideSpeed;
    @Option.Op(min = 0.0, max = 9.0, increment = 0.01, name = "Speed")
    private double speed;
    
    public Fly() {
        this.speed = 0.8;
    }
    
    @EventTarget
    private void onUpdate(final UpdateEvent event) {
        if (event.getState() == Event.State.PRE) {
            if (!this.glide) {
                if (ClientUtils.movementInput().jump) {
                    ClientUtils.player().motionY = this.speed;
                }
                else if (ClientUtils.movementInput().sneak) {
                    ClientUtils.player().motionY = -this.speed;
                }
                else {
                    ClientUtils.player().motionY = 0.0;
                }
            }
            else {
                ClientUtils.player().motionY = -this.glideSpeed;
            }
        }
    }
    
    @EventTarget
    private void onMove(final MoveEvent event) {
        ClientUtils.setMoveSpeed(event, this.speed);
    }
}
