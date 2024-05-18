/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.speed.NewSpeedNCP2;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2;
import me.thekirkayt.client.module.modules.speed.NewSpeedNCP2.SpeedMode;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.MoveEvent;
import me.thekirkayt.event.events.PacketSendEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;

public class Low Hop (Latest NCP)
extends SpeedMode {
    private double moveSpeed;
    private double lastDist;
    public static int stage;
    private int ticks;

    public Low Hop (Latest NCP)(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @EventTarget
    public void onPacket(PacketSendEvent eventPacketSend) {
        if (eventPacketSend.packet instanceof C03PacketPlayer.C04PacketPlayerPosition) {
            C03PacketPlayer.C04PacketPlayerPosition pos = (C03PacketPlayer.C04PacketPlayerPosition)eventPacketSend.packet;
            if (this.ticks == 2) {
                C03PacketPlayer.C04PacketPlayerPosition.y += 0.4;
            }
        }
        if (eventPacketSend.packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
            C03PacketPlayer.C06PacketPlayerPosLook pos2 = (C03PacketPlayer.C06PacketPlayerPosLook)eventPacketSend.packet;
            if (this.ticks == 2) {
                C03PacketPlayer.C06PacketPlayerPosLook.y += 0.4;
            }
        }
    }

    @Override
    public boolean enable() {
        if (super.enable()) {
            if (ClientUtils.player() != null) {
                this.moveSpeed = NewSpeedNCP2.getBaseMoveSpeed();
            }
            this.lastDist = 0.0;
            stage = 1;
        }
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean onMove(MoveEvent event) {
        if (super.onMove(event) == false) return false;
        if (ClientUtils.player().moveForward == 0.0f && ClientUtils.player().moveStrafing == 0.0f) {
            this.moveSpeed = NewSpeedNCP2.getBaseMoveSpeed();
        }
        if (++Low Hop (Latest NCP).stage != 2 || !ClientUtils.player().onGround) ** GOTO lbl-1000
        movementInput = ClientUtils.player().movementInput;
        if (MovementInput.moveStrafe != 0.0f) ** GOTO lbl-1000
        movementInput2 = ClientUtils.player().movementInput;
        if (MovementInput.moveForward != 0.0f) lbl-1000: // 2 sources:
        {
            ClientUtils.player().motionY = 0.4;
            event.setY(0.4);
            this.moveSpeed *= 1.642092;
        } else if (Low Hop (Latest NCP).stage == 3) {
            difference = 0.69 * (this.lastDist - NewSpeedNCP2.getBaseMoveSpeed());
            this.moveSpeed = this.lastDist - difference;
        } else {
            if (ClientUtils.player().isCollidedVertically) {
                Low Hop (Latest NCP).stage = 1;
            }
            this.moveSpeed = this.lastDist - this.lastDist / 100.0;
        }
        if (Low Hop (Latest NCP).stage > 2 && ClientUtils.player().posY - (double)((long)ClientUtils.player().posY) < 0.2) {
            player = player3 = ClientUtils.player();
            player3.motionY -= 0.0734529234;
            event.setY(event.getY() - 0.0866);
            player2 = player4 = ClientUtils.player();
            player4.posY -= 0.0866;
        }
        this.moveSpeed = Math.max(this.moveSpeed, NewSpeedNCP2.getBaseMoveSpeed());
        ClientUtils.setMoveSpeed(event, this.moveSpeed);
        return false;
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

