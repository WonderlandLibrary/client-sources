/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump.LongJumpBypass2;

import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.longjump.LongJumpBypass2.LongJumpBypassMode;
import me.thekirkayt.client.module.modules.speed.SpeedNCP;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class EnvyLongJump
extends LongJumpBypassMode {
    private double moveSpeed;
    private double lastDist;
    public static int stage;

    public EnvyLongJump(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean enable() {
        if (super.enable()) {
            stage = 0;
        }
        return true;
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event)) {
            boolean setY = false;
            if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                if (stage == 0) {
                    this.moveSpeed = 1.0 + SpeedNCP.getBaseMoveSpeed() - 0.05;
                } else if (stage == 1) {
                    ClientUtils.player().motionY = 0.42;
                    event.setY(0.42);
                    this.moveSpeed *= 2.13;
                } else if (stage == 2) {
                    double difference = 0.66 * (this.lastDist - SpeedNCP.getBaseMoveSpeed());
                    this.moveSpeed = this.lastDist - difference;
                } else {
                    this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                }
                this.moveSpeed = Math.max(SpeedNCP.getBaseMoveSpeed(), this.moveSpeed);
                ClientUtils.setMoveSpeed(event, this.moveSpeed);
                List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                List collidingList2 = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, -0.4, 0.0));
                ++stage;
            } else if (stage > 1) {
                stage = 0;
            }
        }
        return true;
    }

    @Override
    public boolean onUpdate(UpdateEvent event) {
        if (super.onUpdate(event) && event.getState() == Event.State.PRE) {
            if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                double xDist = ClientUtils.x() - ClientUtils.player().prevPosX;
                double zDist = ClientUtils.z() - ClientUtils.player().prevPosZ;
                this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
            } else {
                event.setCancelled(true);
            }
        }
        return true;
    }

    @Override
    public boolean disable() {
        if (super.disable()) {
            stage = 1;
        }
        return true;
    }
}

