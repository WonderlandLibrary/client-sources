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
import me.thekirkayt.utils.Timer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;

public class Boost LongJump
extends SpeedMode {
    public static double yOffset;
    private int stage;
    private double moveSpeed;
    private double lastDist;
    private boolean glide;
    Timer timer = new Timer();

    public Boost LongJump(String string, boolean bl, Module module) {
        super(string, bl, module);
    }

    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = NewSpeedNCP.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
        }
        return true;
    }

    @Override
    public boolean onMove(MoveEvent moveEvent) {
        if (super.onMove(moveEvent)) {
            Block block;
            if (!ClientUtils.player().onGround) {
                this.timer.reset();
            }
            if (MathUtils.roundToPlace(ClientUtils.player().posY - (double)((int)ClientUtils.player().posY), 3) == MathUtils.roundToPlace(0.481, 3)) {
                block = ClientUtils.world().getBlock((int)ClientUtils.x(), (int)ClientUtils.y() - 1, (int)ClientUtils.z());
                if (!(block instanceof BlockAir)) {
                    EntityPlayerSP entityPlayerSP = ClientUtils.player();
                    entityPlayerSP.motionY -= 0.075;
                    moveEvent.setY(-0.075);
                }
            } else if (MathUtils.roundToPlace(ClientUtils.player().posY - (double)((int)ClientUtils.player().posY), 3) == MathUtils.roundToPlace(0.40599999999999997, 3)) {
                block = ClientUtils.world().getBlock((int)ClientUtils.x(), (int)ClientUtils.y() - 1, (int)ClientUtils.z());
                if (!(block instanceof BlockAir)) {
                    ClientUtils.player().motionY = -0.1;
                    moveEvent.setY(-0.1);
                }
            } else if (MathUtils.roundToPlace(ClientUtils.player().posY - (double)((int)ClientUtils.player().posY), 3) == MathUtils.roundToPlace(0.306, 3) && !ClientUtils.player().isCollidedHorizontally && this.stage != 0) {
                block = ClientUtils.world().getBlock((int)ClientUtils.x(), (int)ClientUtils.y() - 1, (int)ClientUtils.z());
                if (!(block instanceof BlockAir || ClientUtils.player().moveForward == 0.0f && ClientUtils.player().moveStrafing == 0.0f)) {
                    ClientUtils.player().motionY = -6.0E-6;
                    moveEvent.setY(-6.0E-6);
                }
            } else if (MathUtils.roundToPlace(ClientUtils.player().posY - (double)((int)ClientUtils.player().posY), 3) == MathUtils.roundToPlace(0.305, 3) || ClientUtils.player().moveForward == 0.0f && ClientUtils.player().moveStrafing == 0.0f || ClientUtils.player().isCollidedHorizontally) {
                this.stage = 0;
            }
            if (this.stage == 1 && (ClientUtils.player().moveForward != 0.0f || ClientUtils.player().moveStrafing != 0.0f)) {
                this.stage = 2;
                this.moveSpeed = ClientUtils.player().isPotionActive(Potion.moveSpeed) ? 3.5 * NewSpeedNCP.getBaseMoveSpeed() : NewSpeedNCP.boost * NewSpeedNCP.getBaseMoveSpeed();
            } else if (this.stage == 2) {
                this.stage = 3;
                ClientUtils.setMoveSpeed(moveEvent, 0.15);
                ClientUtils.player().motionY = 0.42;
                moveEvent.setY(0.42);
                this.moveSpeed *= 2.147;
            } else if (this.stage == 3) {
                this.stage = 4;
                double d = 0.66 * (this.lastDist - NewSpeedNCP.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - d;
            } else {
                if (ClientUtils.world().getCollidingBoundingBoxes(ClientUtils.player(), ClientUtils.player().boundingBox.offset(0.0, ClientUtils.player().motionY, 0.0)).size() > 0 || ClientUtils.player().isCollidedVertically) {
                    this.stage = this.timer.delay(265.0f) ? 1 : 0;
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            ClientUtils.setMoveSpeed(moveEvent, this.stage != 0 ? (this.moveSpeed = Math.max(this.moveSpeed, NewSpeedNCP.getBaseMoveSpeed())) : 0.15);
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

