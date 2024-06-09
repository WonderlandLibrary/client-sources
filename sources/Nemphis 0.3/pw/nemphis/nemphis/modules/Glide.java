/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.modules;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Timer;
import pw.vertexcode.nemphis.events.EventPacket;
import pw.vertexcode.nemphis.events.UpdateEvent;
import pw.vertexcode.nemphis.module.Category;
import pw.vertexcode.util.event.EventListener;
import pw.vertexcode.util.module.ModuleInformation;
import pw.vertexcode.util.module.types.ToggleableModule;

@ModuleInformation(category=Category.MOVEMENT, color=-1250068, name="Glide")
public class Glide
extends ToggleableModule {
    private double startY;
    private int time = 0;
    private double value;

    @Override
    public void onEnabled() {
        this.startY = Glide.mc.thePlayer.posY + 10.0;
        this.value = 0.25;
        EventManager.register(this);
        super.onEnabled();
    }

    @Override
    public void onDisable() {
        EventManager.unregister(this);
        super.onDisable();
    }

    @EventListener
    public void onUpdate(UpdateEvent e) {
        Glide.mc.thePlayer.motionY = 0.0;
        if (Glide.mc.gameSettings.keyBindJump.isPressed()) {
            this.startY += 1.0;
            Glide.mc.thePlayer.setPosition(Glide.mc.thePlayer.posX, this.startY, Glide.mc.thePlayer.posZ);
        }
        if (Glide.mc.gameSettings.keyBindSneak.isPressed()) {
            this.startY -= 1.0;
            Glide.mc.thePlayer.setPosition(Glide.mc.thePlayer.posX, this.startY, Glide.mc.thePlayer.posZ);
        }
        if (Glide.mc.thePlayer.moveForward == 0.0f || Glide.mc.thePlayer.isCollidedHorizontally) {
            Glide.mc.thePlayer.jumpMovementFactor = 0.02f;
            Glide.mc.timer.timerSpeed = 1.0f;
            Glide.mc.thePlayer.motionX = 0.0;
            Glide.mc.thePlayer.motionZ = 0.0;
            return;
        }
        --this.time;
        if (this.time <= 0) {
            this.time = 3;
            this.setSpeed(0.24f);
        } else {
            this.setSpeed(0.5f);
        }
    }

    @EventTarget
    public void on(EventPacket e) {
        if (e.getPacket() instanceof C03PacketPlayer) {
            C03PacketPlayer c = (C03PacketPlayer)e.getPacket();
            c.field_149474_g = true;
            c.y = this.startY + this.value;
            this.value = - this.value;
            if (this.time > 60) {
                this.time = 0;
            }
        }
    }
}

