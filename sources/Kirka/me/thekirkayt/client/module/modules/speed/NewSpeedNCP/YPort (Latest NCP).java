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
import me.thekirkayt.utils.MathUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class YPort (Latest NCP)
extends SpeedMode {
    private double moveSpeed;
    private double lastDist;
    public static double stage;

    public YPort (Latest NCP)(String string, boolean bl, Module module) {
        super(string, bl, module);
    }

    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = NewSpeedNCP.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            stage = 4.0;
        }
        return true;
    }

    @Override
    public boolean onMove(MoveEvent moveEvent) {
        if (super.onMove(moveEvent)) {
            Object object;
            if (MathUtils.roundToPlace(ClientUtils.player().posY - (double)((int)ClientUtils.player().posY), 3) == MathUtils.roundToPlace(0.138, 3)) {
                EntityPlayerSP entityPlayerSP;
                object = ClientUtils.player();
                EntityPlayerSP entityPlayerSP2 = object;
                EntityPlayerSP entityPlayerSP3 = object;
                EntityPlayerSP entityPlayerSP4 = object;
                entityPlayerSP3.motionY -= 1.0;
                moveEvent.setY(moveEvent.getY() - 0.05);
                EntityPlayerSP entityPlayerSP5 = entityPlayerSP = ClientUtils.player();
                EntityPlayerSP entityPlayerSP6 = entityPlayerSP;
                EntityPlayerSP entityPlayerSP7 = entityPlayerSP;
                entityPlayerSP6.posY -= 0.0531;
            }
            if (stage == 2.0 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 1.0f)) {
                ClientUtils.player().motionY = 0.4;
                moveEvent.setY(0.4);
                this.moveSpeed *= 1.49;
            } else if (stage == 3.0) {
                double d = 0.66 * (this.lastDist - NewSpeedNCP.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - d;
            } else {
                object = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                if (object.size() > 0 || ClientUtils.player().isCollidedVertically) {
                    stage = 1.0;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 150.0;
            }
            this.moveSpeed = Math.max(this.moveSpeed, NewSpeedNCP.getBaseMoveSpeed());
            ClientUtils.setMoveSpeed(moveEvent, this.moveSpeed);
            stage += 1.0;
        }
        return true;
    }

    @Override
    public boolean onUpdate(UpdateEvent updateEvent) {
        if (super.onUpdate(updateEvent) && updateEvent.getState() == Event.State.PRE) {
            double d = ClientUtils.x() - ClientUtils.player().prevPosX;
            double d2 = ClientUtils.z() - ClientUtils.player().prevPosZ;
            this.lastDist = Math.sqrt(d * d + d2 * d2);
        }
        return true;
    }
}

