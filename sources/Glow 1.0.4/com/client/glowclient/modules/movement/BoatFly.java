package com.client.glowclient.modules.movement;

import com.client.glowclient.sponge.mixinutils.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.events.*;
import net.minecraft.entity.item.*;
import com.client.glowclient.*;
import com.client.glowclient.utils.*;
import com.client.glowclient.modules.*;

public class BoatFly extends ModuleContainer
{
    public static final NumberValue speed;
    public static BooleanValue yaw;
    public static final NumberValue upSpeed;
    public static final NumberValue glideSpeed;
    public final boolean B = true;
    public final double b = 105.0;
    
    @Override
    public void E() {
        HookTranslator.v2 = false;
        HookTranslator.v1 = false;
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
    
    @Override
    public String M() {
        return String.format("%.1f", BoatFly.speed.k());
    }
    
    @SubscribeEvent
    public void M(final EventBoat eventBoat) {
        if (EntityUtils.m((Entity)eventBoat.getBoat()) && BoatFly.yaw.M()) {
            eventBoat.setYaw(eventBoat.getBoat().rotationYaw = Wrapper.mc.player.rotationYaw);
        }
    }
    
    @SubscribeEvent
    public void k(final EventUpdate eventUpdate) {
        HookTranslator.v1 = (Ob.M() instanceof EntityBoat);
    }
    
    static {
        speed = ValueFactory.M("BoatFly", "Speed", "Flight Speed", 1.2, 0.1, 0.0, 5.0);
        glideSpeed = ValueFactory.M("BoatFly", "GlideSpeed", "Speed of glide", 0.033, 0.001, 0.0, 1.0);
        upSpeed = ValueFactory.M("BoatFly", "UpSpeed", "how fast to go up", 0.2, 0.1, 0.0, 1.0);
        BoatFly.yaw = ValueFactory.M("BoatFly", "Yaw", "Sets boat yaw to your's", true);
    }
    
    public BoatFly() {
        final double b = 105.0;
        final boolean b2 = true;
        super(Category.MOVEMENT, "BoatFly", false, -1, "Fly with boats");
    }
    
    @SubscribeEvent
    public void A(final EventUpdate eventUpdate) {
        HookTranslator.v2 = true;
        if (Wrapper.mc.player != null && Wrapper.mc.player.getRidingEntity() != null) {
            M(BoatFly.speed.k());
        }
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (Wrapper.mc.player != null && Wrapper.mc.player.getRidingEntity() != null) {
            HookTranslator.v1 = true;
            if (Wrapper.mc.gameSettings.keyBindJump.isKeyDown()) {
                Wrapper.mc.player.getRidingEntity().onGround = false;
                Wrapper.mc.player.getRidingEntity().motionY = BoatFly.upSpeed.k();
            }
            else {
                Wrapper.mc.player.getRidingEntity().motionY = -BoatFly.glideSpeed.k();
            }
            if (Wrapper.mc.player.posY <= 100.0 && Wrapper.mc.player.posY > 95.0) {
                M(BoatFly.speed.k());
            }
        }
    }
}
