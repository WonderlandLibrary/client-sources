package com.client.glowclient.modules.movement;

import com.client.glowclient.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Flight extends ModuleContainer
{
    public static final NumberValue speed;
    private int B;
    public static nB mode;
    
    static {
        speed = ValueFactory.M("Flight", "Speed", "Flight Speed", 2.5, 0.5, 1.0, 10.0);
        Flight.mode = ValueFactory.M("Flight", "Mode", "Mode of Flight", "Normal", "Normal", "Vanilla");
    }
    
    public Flight() {
        final int b = 0;
        super(Category.MOVEMENT, "Flight", false, -1, "Fly");
        this.B = b;
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void D(final EventUpdate eventUpdate) {
        if (Flight.mode.e().equals("Normal")) {
            final double n = Flight.speed.k() / 10.0;
            final MovementInput movementInput = Wrapper.mc.player.movementInput;
            final double n2 = movementInput.moveForward;
            final double n3 = movementInput.moveStrafe;
            final float rotationYaw = Wrapper.mc.player.rotationYaw;
            final double n4 = n;
            if (!Wrapper.mc.gameSettings.keyBindJump.isKeyDown() && !Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Wrapper.mc.player.motionY = 0.0;
            }
            if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                Wrapper.mc.player.motionY = 0.5 + n;
            }
            if (Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Wrapper.mc.player.motionY = -(0.5 + n);
            }
            if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown() && Wrapper.mc.gameSettings.keyBindSneak.isKeyDown()) {
                Wrapper.mc.player.motionY = 0.0;
            }
            if (n2 == 0.0 && n3 == 0.0) {
                Wrapper.mc.player.motionX = 0.0;
                Wrapper.mc.player.motionZ = 0.0;
            }
            else if (!Wrapper.mc.player.onGround) {
                Wrapper.mc.player.motionX = n2 * n4 * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + n3 * n4 * Math.sin(Math.toRadians(rotationYaw + 90.0f));
                Wrapper.mc.player.motionZ = n2 * n4 * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - n3 * n4 * Math.cos(Math.toRadians(rotationYaw + 90.0f));
            }
            Wrapper.mc.player.onGround = false;
            Wrapper.mc.player.fallDistance = 0.0f;
        }
        if (Flight.mode.e().equals("Vanilla")) {
            Wrapper.mc.player.capabilities.isFlying = true;
            Wrapper.mc.player.capabilities.setFlySpeed((float)Flight.speed.k() / 100.0f);
        }
        if (Flight.mode.e().equals("Flat")) {
            Wrapper.mc.player.motionY = 0.0;
        }
    }
    
    @Override
    public String M() {
        return String.format("%.2f", Flight.speed.k());
    }
    
    @Override
    public void E() {
        Flight.B.player.capabilities.isFlying = false;
        Flight.B.player.capabilities.setFlySpeed(0.05f);
    }
}
