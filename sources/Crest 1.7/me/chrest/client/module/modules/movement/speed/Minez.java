// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement.speed;

import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.event.events.MoveEvent;
import me.chrest.client.module.modules.movement.Speed;
import me.chrest.utils.ClientUtils;
import me.chrest.client.module.Module;

public class Minez extends SpeedMode
{
    private double moveSpeed;
    private double lastDist;
    private int stage;
    public static double yOffset;
    
    public Minez(final String name, final boolean value, final Module module) {
        super(name, value, module);
    }
    
    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = Speed.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            this.stage = 1;
        }
        return true;
    }
    
    @Override
    public boolean onMove(final MoveEvent event) {
        if (super.onMove(event)) {
            switch (this.stage) {
                case 1: {
                    this.moveSpeed = 0.9;
                    break;
                }
                case 2: {
                    this.moveSpeed = 0.95211;
                    break;
                }
                default: {
                    this.moveSpeed = Speed.getBaseMoveSpeed();
                    break;
                }
            }
            ClientUtils.setMoveSpeed(event, this.moveSpeed = Math.max(this.moveSpeed, Speed.getBaseMoveSpeed()));
            ++this.stage;
        }
        return true;
    }
    
    @Override
    public boolean onUpdate(final UpdateEvent event) {
        if (super.onUpdate(event)) {
            final Event.State state = event.getState();
            event.getState();
            if (state == Event.State.PRE) {
                switch (this.stage) {
                    case 1: {
                        event.setY(event.getY() + 1.0E-9);
                        ++this.stage;
                        break;
                    }
                    case 2: {
                        event.setY(event.getY() + 2.0E-9);
                        ++this.stage;
                        break;
                    }
                    default: {
                        this.stage = 1;
                        if (!ClientUtils.player().isSneaking() && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) && !ClientUtils.gamesettings().keyBindJump.isPressed()) {
                            this.stage = 1;
                            break;
                        }
                        this.moveSpeed = Speed.getBaseMoveSpeed();
                        break;
                    }
                }
            }
        }
        return true;
    }
}
