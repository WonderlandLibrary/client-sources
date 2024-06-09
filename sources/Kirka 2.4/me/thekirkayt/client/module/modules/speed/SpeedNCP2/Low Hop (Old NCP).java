/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed.SpeedNCP2;

import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.SpeedNCP2;
import me.thekirkayt.client.module.modules.speed.SpeedNCP2.SpeedMode;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.MathUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class Low Hop (Old NCP)
extends SpeedMode {
    private double moveSpeed;
    private double lastDist;
    public static int stage;

    public Low Hop (Old NCP)(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = SpeedNCP2.getBaseMoveSpeed();
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
                this.moveSpeed = SpeedNCP2.getBaseMoveSpeed();
            }
            if (MathUtils.roundToPlace(ClientUtils.player().posY - (double)((int)ClientUtils.player().posY), 3) == MathUtils.roundToPlace(0.4, 3)) {
                ClientUtils.player().motionY = 0.31;
                event.setY(0.31);
            } else if (MathUtils.roundToPlace(ClientUtils.player().posY - (double)((int)ClientUtils.player().posY), 3) == MathUtils.roundToPlace(0.71, 3)) {
                ClientUtils.player().motionY = 0.04;
                event.setY(0.04);
            } else if (MathUtils.roundToPlace(ClientUtils.player().posY - (double)((int)ClientUtils.player().posY), 3) == MathUtils.roundToPlace(0.75, 3)) {
                ClientUtils.player().motionY = -0.2;
                event.setY(-0.2);
            }
            List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, -0.56, 0.0));
            if (collidingList.size() > 0 && MathUtils.roundToPlace(ClientUtils.player().posY - (double)((int)ClientUtils.player().posY), 3) == MathUtils.roundToPlace(0.55, 3)) {
                ClientUtils.player().motionY = -0.14;
                event.setY(-0.14);
            }
            if (stage == 1 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                this.moveSpeed = 2.0 * SpeedNCP2.getBaseMoveSpeed() - 0.01;
            } else if (stage == 2 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                ClientUtils.player().motionY = 0.4;
                event.setY(0.4);
                this.moveSpeed *= 2.149;
            } else if (stage == 3) {
                double difference = 0.66 * (this.lastDist - SpeedNCP2.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            } else {
                collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                if ((collidingList.size() > 0 || ClientUtils.player().isCollidedVertically) && stage > 0) {
                    stage = 1.35 * SpeedNCP2.getBaseMoveSpeed() - 0.01 > this.moveSpeed ? 0 : (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f ? 1 : 0);
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            if (stage > 8) {
                this.moveSpeed = SpeedNCP2.getBaseMoveSpeed();
            }
            this.moveSpeed = Math.max(this.moveSpeed, SpeedNCP2.getBaseMoveSpeed());
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

