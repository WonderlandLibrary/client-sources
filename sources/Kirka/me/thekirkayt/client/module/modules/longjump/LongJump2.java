/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.longjump;

import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.SpeedNCP;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;

@Module.Mod
public class LongJump2
extends Module {
    private double moveSpeed;
    private double lastDist;
    public static int stage;
    @Option.Op(increment=1.0, min=4.0, max=24.0)
    private double boost = 4.0;

    @Override
    public void enable() {
        stage = 0;
        super.enable();
    }

    @EventTarget
    private void onMove(MoveEvent moveEvent) {
        if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
            if (stage == 0) {
                this.moveSpeed = 1.0 + SpeedNCP.getBaseMoveSpeed() - 0.05;
            } else if (stage == 1) {
                ClientUtils.player().motionY = 0.42;
                moveEvent.setY(0.42);
                this.moveSpeed *= 2.13;
            } else if (stage == 2) {
                double d = 0.66 * (this.lastDist - SpeedNCP.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - d;
            } else {
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            this.moveSpeed = Math.max(SpeedNCP.getBaseMoveSpeed(), this.moveSpeed);
            ClientUtils.setMoveSpeed(moveEvent, this.moveSpeed);
            List list = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0));
            List list2 = ClientUtils.world().getCollidingBlockBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, -0.4, 0.0));
            if (!(ClientUtils.player().isCollidedVertically || list.size() <= 0 && list2.size() <= 0)) {
                ClientUtils.player().motionY = -1.0E-4;
                moveEvent.setY(-1.0E-4);
            }
            ++stage;
        } else if (stage > 0) {
            this.disable();
        }
    }

    @EventTarget
    private void onUpdate(UpdateEvent updateEvent) {
        if (updateEvent.getState() == Event.State.PRE) {
            if (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f) {
                double d = ClientUtils.x() - ClientUtils.player().prevPosX;
                double d2 = ClientUtils.z() - ClientUtils.player().prevPosZ;
                this.lastDist = Math.sqrt(d * d + d2 * d2);
            } else {
                updateEvent.setCancelled(true);
            }
        }
    }
}

