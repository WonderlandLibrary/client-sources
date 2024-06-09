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

public class Bunny Hop (Old NCP)
extends SpeedMode {
    private double moveSpeed;
    private double lastDist;
    private double stage;

    public Bunny Hop (Old NCP)(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = SpeedNCP2.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            this.stage = 4.0;
        }
        return true;
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event)) {
            if (MathUtils.roundToPlace(ClientUtils.player().posY - (double)((int)ClientUtils.player().posY), 3) == MathUtils.roundToPlace(0.138, 3)) {
                EntityPlayerSP player = ClientUtils.player();
                player.motionY -= 0.08;
                event.setY(event.getY() - 0.0931);
                EntityPlayerSP player2 = ClientUtils.player();
                player2.posY -= 0.0931;
            }
            if (this.stage == 2.0 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                ClientUtils.player().motionY = 0.4;
                event.setY(0.4);
                this.moveSpeed *= 2.149;
            } else if (this.stage == 3.0) {
                double difference = 0.66 * (this.lastDist - SpeedNCP2.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            } else {
                List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                if (collidingList.size() > 0 || ClientUtils.player().isCollidedVertically) {
                    this.stage = 1.0;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            this.moveSpeed = Math.max(this.moveSpeed, SpeedNCP2.getBaseMoveSpeed());
            ClientUtils.setMoveSpeed(event, this.moveSpeed);
            this.stage += 1.0;
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

