/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed.NewSpeedNCP;

import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP;
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP.SpeedMode;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class Long Bunny Hop (Old NCP)
extends SpeedMode {
    private double moveSpeed;
    private double lastDist;
    public static int stage;

    public Long Bunny Hop (Old NCP)(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = NewSpeedNCP.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            stage = 1;
        }
        return true;
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event)) {
            if (ClientUtils.player().moveForward == 0.0f && ClientUtils.player().moveStrafing == 0.0f) {
                this.moveSpeed = NewSpeedNCP.getBaseMoveSpeed();
            }
            if (stage == 1 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                this.moveSpeed = 0.25 + NewSpeedNCP.getBaseMoveSpeed() - 0.01;
            } else if (stage == 2 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                ClientUtils.player().motionY = 0.4;
                event.setY(0.4);
                this.moveSpeed *= 2.149;
            } else if (stage == 3) {
                double difference = 0.66 * (this.lastDist - NewSpeedNCP.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            } else {
                List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                if ((collidingList.size() > 0 || ClientUtils.player().isCollidedVertically) && stage > 0) {
                    stage = 1.35 * NewSpeedNCP.getBaseMoveSpeed() - 0.01 > this.moveSpeed ? 0 : (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f ? 1 : 0);
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            this.moveSpeed = Math.max(this.moveSpeed, NewSpeedNCP.getBaseMoveSpeed());
            if (stage > 0) {
                ClientUtils.setMoveSpeed(event, this.moveSpeed);
            }
            if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                ++stage;
            }
        }
        return true;
    }

    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
            double xDist = ClientUtils.x() - ClientUtils.player().prevPosX;
            double zDist = ClientUtils.z() - ClientUtils.player().prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        }
        return true;
    }
}

