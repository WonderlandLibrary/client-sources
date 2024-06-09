/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovementInput;
import winter.event.EventListener;
import winter.event.events.TickEvent;
import winter.module.Module;
import winter.utils.Player;

public class Flight
extends Module {
    public Flight() {
        super("Fly", Module.Category.Movement, -131599);
        this.setBind(33);
    }

    @EventListener
    public void onTick(TickEvent event) {
        this.mc.thePlayer.motionY = 0.0;
        if (Player.isMoving()) {
            double speed = 0.5;
            double x2 = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            double z2 = Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            double xOff = (double)MovementInput.moveForward * 0.5 * x2 + (double)MovementInput.moveStrafe * 0.5 * z2;
            double zOff = (double)MovementInput.moveForward * 0.5 * z2 - (double)MovementInput.moveStrafe * 0.5 * x2;
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + xOff, this.mc.thePlayer.posY - (this.mc.gameSettings.keyBindJump.pressed ? -0.5 : (this.mc.gameSettings.keyBindSneak.pressed ? 0.5 : 0.0)), this.mc.thePlayer.posZ + zOff);
        } else {
            this.mc.thePlayer.motionX = 0.0;
            this.mc.thePlayer.motionZ = 0.0;
        }
    }
}

