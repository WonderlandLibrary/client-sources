/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;
import winter.event.EventListener;
import winter.event.events.MoveEvent;
import winter.event.events.PacketEvent;
import winter.module.Module;
import winter.utils.value.Value;
import winter.utils.value.types.BooleanValue;
import winter.utils.value.types.NumberValue;

public class Ground
extends Module {
    public NumberValue speed;
    public NumberValue zoomTimer;
    public NumberValue stopTimer;
    public NumberValue zooms;
    public BooleanValue stop;
    public NumberValue jumpHeight;
    public BooleanValue jump;
    public NumberValue jumpTick;
    private int zoomTicks;

    public Ground() {
        super("Custom", Module.Category.Movement, -2986855);
        this.setBind(0);
        this.speed = new NumberValue("Speed", 0.31, 0.01, 5.0, 0.01);
        this.addValue(this.speed);
        this.zoomTimer = new NumberValue("Zoom Timer", 4.4, 0.1, 5.0, 0.1);
        this.addValue(this.zoomTimer);
        this.stopTimer = new NumberValue("Slow Timer", 3.8, 0.1, 5.0, 0.1);
        this.addValue(this.stopTimer);
        this.zooms = new NumberValue("Ticks to Zoom", 5.0, 2.0, 25.0, 1.0);
        this.addValue(this.zooms);
        this.stop = new BooleanValue("Jitter", true);
        this.addValue(this.stop);
        this.jump = new BooleanValue("Jump", false);
        this.addValue(this.jump);
        this.jumpHeight = new NumberValue("Jump Height", 0.4, 0.1, 5.0, 0.1);
        this.addValue(this.jumpHeight);
        this.jumpTick = new NumberValue("Jump Tick", 4.0, 2.0, 25.0, 1.0);
        this.addValue(this.jumpTick);
    }

    @Override
    public void onEnable() {
        this.zoomTicks = 0;
    }

    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }

    @EventListener
    public void onMotion(MoveEvent event) {
        this.mc.timer.timerSpeed = 0.9f;
        double x2 = (double)MovementInput.moveForward * this.speed.getValue() * Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f)) + (double)MovementInput.moveStrafe * this.speed.getValue() * Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
        double z2 = (double)MovementInput.moveForward * this.speed.getValue() * Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f)) - (double)MovementInput.moveStrafe * this.speed.getValue() * Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
        if ((double)this.zoomTicks > this.zooms.getValue()) {
            this.mc.timer.timerSpeed = (float)this.zoomTimer.getValue();
            event.setX(x2);
            event.setZ(z2);
            this.zoomTicks = 0;
        } else if ((double)this.zoomTicks == this.zooms.getValue() - 1.0) {
            this.mc.timer.timerSpeed = (float)this.stopTimer.getValue();
            this.mc.thePlayer.motionX = x2;
            this.mc.thePlayer.motionZ = z2;
            if (this.stop.isEnabled()) {
                event.setCancelled(true);
            }
        } else if ((double)this.zoomTicks == this.jumpTick.getValue() && this.jump.isEnabled() && this.mc.thePlayer.onGround) {
            this.mc.thePlayer.motionY = this.jumpHeight.getValue();
            event.setY(this.mc.thePlayer.motionY);
        }
        ++this.zoomTicks;
    }

    @EventListener
    public void onPacket(PacketEvent event) {
        if ((event.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook || event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook) && (double)this.zoomTicks == this.zooms.getValue() - 1.0) {
            event.setCancelled(true);
        }
    }
}

