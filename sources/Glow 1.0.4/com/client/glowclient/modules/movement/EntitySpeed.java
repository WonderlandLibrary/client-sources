package com.client.glowclient.modules.movement;

import com.client.glowclient.modules.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.sponge.mixinutils.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;

public class EntitySpeed extends ModuleContainer
{
    public static final NumberValue speed;
    
    public EntitySpeed() {
        super(Category.MOVEMENT, "EntitySpeed", false, -1, "Go faster on rideable entities.");
    }
    
    public static void M(final double n) {
        if (Wrapper.mc.player != null && Wrapper.mc.player.getRidingEntity() != null) {
            final MovementInput movementInput = Wrapper.mc.player.movementInput;
            double n2 = movementInput.moveForward;
            double n3 = movementInput.moveStrafe;
            float rotationYaw = Wrapper.mc.player.rotationYaw;
            if (n2 == 0.0 && n3 == 0.0) {
                Wrapper.mc.player.getRidingEntity().motionX = 0.0;
                Wrapper.mc.player.getRidingEntity().motionZ = 0.0;
                return;
            }
            if (n2 != 0.0) {
                if (n3 > 0.0) {
                    rotationYaw += ((n2 > 0.0) ? -45 : 45);
                }
                else if (n3 < 0.0) {
                    rotationYaw += ((n2 > 0.0) ? 45 : -45);
                }
                n3 = 0.0;
                if (n2 > 0.0) {
                    n2 = 1.0;
                }
                else if (n2 < 0.0) {
                    n2 = -1.0;
                }
            }
            Wrapper.mc.player.getRidingEntity().motionX = n2 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + n3 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f));
            Wrapper.mc.player.getRidingEntity().motionZ = n2 * n * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - n3 * n * Math.cos(Math.toRadians(rotationYaw + 90.0f));
        }
    }
    
    @SubscribeEvent
    public void M(final EventBoat eventBoat) {
        if (EntityUtils.m((Entity)eventBoat.getBoat())) {
            eventBoat.setYaw(eventBoat.getBoat().rotationYaw = Wrapper.mc.player.rotationYaw);
        }
    }
    
    @Override
    public void E() {
        HookTranslator.v2 = false;
    }
    
    @Override
    public String M() {
        return String.format("%.1f", EntitySpeed.speed.k());
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        HookTranslator.v2 = true;
        if (Wrapper.mc.player != null && Wrapper.mc.player.getRidingEntity() != null) {
            M(EntitySpeed.speed.k());
        }
    }
    
    static {
        speed = ValueFactory.M("EntitySpeed", "Speed", "Speed of Entity", 3.7, 0.1, 0.0, 3.8);
    }
}
