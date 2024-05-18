/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump.LongJumpNew;

import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.longjump.LongJumpNew.LongJumpNewMode;
import me.thekirkayt.client.module.modules.longjump.LongJumpNew.NCP;
import me.thekirkayt.client.module.modules.speed.SpeedNCP;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

public class Hypixel
extends LongJumpNewMode {
    private static final double SPEED_BASE = 0.2873;
    private double moveSpeed;
    private double lastDist;
    public static int stage;
    public static int settingUpTicks;

    public Hypixel(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean enable() {
        if (super.enable()) {
            stage = 0;
            NCP.settingUpTicks = 2;
        }
        return true;
    }

    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event)) {
            if (ClientUtils.player().isCollidedHorizontally || ClientUtils.player().moveForward == 0.0f && ClientUtils.player().moveStrafing != 0.0f) {
                stage = 0;
                NCP.settingUpTicks = 5;
            } else {
                if (NCP.settingUpTicks > 0 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                    this.moveSpeed = 0.09;
                    --NCP.settingUpTicks;
                } else if (stage == 1 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                    this.moveSpeed = 0.6 + SpeedNCP.getBaseMoveSpeed() - 0.05;
                } else if (stage == 2 && ClientUtils.player().isCollidedVertically && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                    ClientUtils.player().motionY = 0.415;
                    event.setY(0.415);
                    this.moveSpeed *= 2.13;
                } else if (stage == 3) {
                    double difference = 0.66 * (this.lastDist - SpeedNCP.getBaseMoveSpeed());
                    this.moveSpeed = this.lastDist - difference;
                } else {
                    this.moveSpeed = this.lastDist - this.lastDist / 159.0;
                }
                ClientUtils.setMoveSpeed(event, this.moveSpeed);
                List collidingList = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
                List collidingList2 = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, -0.4, 0.0));
                if (!(ClientUtils.player().isCollidedVertically || collidingList.size() <= 0 && collidingList2.size() <= 0 || stage <= 10)) {
                    if (stage >= 38) {
                        ClientUtils.player().motionY = -0.4;
                        event.setY(-0.4);
                        stage = 0;
                        NCP.settingUpTicks = 5;
                    } else {
                        ClientUtils.player().motionY = -0.001;
                        event.setY(-0.001);
                    }
                }
                if (NCP.settingUpTicks <= 0 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                    ++stage;
                }
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

