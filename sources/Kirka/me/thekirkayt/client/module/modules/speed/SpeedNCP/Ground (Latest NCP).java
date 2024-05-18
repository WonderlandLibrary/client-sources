/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed.SpeedNCP;

import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.SpeedNCP;
import me.thekirkayt.client.module.modules.speed.SpeedNCP.SpeedMode;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;

public class Ground (Latest NCP)
extends SpeedMode {
    private double moveSpeed;
    private double lastDist;
    public static double stage;

    public Ground (Latest NCP)(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = SpeedNCP.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            stage = 2.0;
        }
        return true;
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event) && (ClientUtils.player().onGround || stage == 3.0)) {
            if (!ClientUtils.player().isCollidedHorizontally && ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                if (stage == 2.0) {
                    this.moveSpeed *= 1.64;
                    stage = 3.0;
                } else if (stage == 3.0) {
                    stage = 2.0;
                    double difference = 0.99 * (this.lastDist - SpeedNCP.getBaseMoveSpeed());
                    this.moveSpeed = this.lastDist - difference;
                } else {
                    List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                    if (collidingList.size() > 0 || ClientUtils.player().isCollidedVertically) {
                        stage = 1.0;
                    }
                }
            } else {
                Timer.timerSpeed = 1.0f;
            }
            this.moveSpeed = Math.max(this.moveSpeed, SpeedNCP.getBaseMoveSpeed());
            ClientUtils.setMoveSpeed(event, this.moveSpeed);
        }
        return true;
    }

    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
            if (stage == 3.0) {
                event.setY(event.getY() + 0.4);
            }
            double xDist = ClientUtils.x() - ClientUtils.player().prevPosX;
            double zDist = ClientUtils.z() - ClientUtils.player().prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        }
        return true;
    }
}

