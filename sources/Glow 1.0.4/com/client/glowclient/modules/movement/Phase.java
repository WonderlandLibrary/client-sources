package com.client.glowclient.modules.movement;

import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class Phase extends ModuleContainer
{
    public Timer b;
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (!this.b.hasBeenSet()) {
            this.b.reset();
        }
        final MovementInput movementInput = Wrapper.mc.player.movementInput;
        final double n = movementInput.moveForward;
        final double n2 = movementInput.moveStrafe;
        final float rotationYaw = Wrapper.mc.player.rotationYaw;
        double n3 = 1.0;
        if (this.b.delay(1.0)) {
            final EntityPlayerSP player = Wrapper.mc.player;
            player.posY += 0.05;
            n3 = 0.5;
        }
        if (this.b.delay(150.0)) {
            n3 = -0.1;
        }
        if (this.b.delay(200.0)) {
            final EntityPlayerSP player2 = Wrapper.mc.player;
            player2.posY -= 0.05;
            this.b.reset();
        }
        if (!Wrapper.mc.gameSettings.keyBindSneak.isKeyDown() || !Wrapper.mc.player.collidedHorizontally) {
            Wrapper.mc.player.noClip = false;
            return;
        }
        Wrapper.mc.player.motionY = 0.0;
        Wrapper.mc.player.noClip = true;
        if (n == 0.0 && n2 == 0.0) {
            Wrapper.mc.player.motionX = 0.0;
            Wrapper.mc.player.motionZ = 0.0;
            return;
        }
        Wrapper.mc.player.motionX = n * n3 * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + n2 * n3 * Math.sin(Math.toRadians(rotationYaw + 90.0f));
        Wrapper.mc.player.motionZ = n * n3 * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - n2 * n3 * Math.cos(Math.toRadians(rotationYaw + 90.0f));
    }
    
    @Override
    public void E() {
        Wrapper.mc.player.noClip = false;
    }
    
    public Phase() {
        super(Category.MOVEMENT, "Phase", false, -1, "Very Shit PhaseMod");
        this.b = new Timer();
    }
}
