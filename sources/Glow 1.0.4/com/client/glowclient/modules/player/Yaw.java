package com.client.glowclient.modules.player;

import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import net.minecraft.entity.*;
import com.client.glowclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.sponge.mixinutils.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.math.*;
import com.client.glowclient.*;

public class Yaw extends ModuleContainer
{
    public static double d;
    public static final NumberValue angle;
    public static double A;
    public static nB mode;
    public float b;
    
    public Yaw() {
        final float b = 0.0f;
        super(Category.PLAYER, "Yaw", false, -1, "Locks YAW to a specific rotation");
        this.b = b;
    }
    
    static {
        Yaw.mode = ValueFactory.M("Yaw", "Mode", "Mode of Yaw", "Diagonal", "Cardinal", "Diagonal", "CoordLock", "Custom");
        angle = ValueFactory.M("Yaw", "Angle", "Custom angle to snap to", 0.0, 1.0, -180.0, 180.0);
        Yaw.A = 0.0;
        Yaw.d = 0.0;
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
    
    @SubscribeEvent
    public void M(final TickEvent tickEvent) {
        HookTranslator.v2 = true;
        if (Wrapper.mc.player != null) {
            if (Yaw.mode.e().equals("Cardinal")) {
                this.b = 90.0f;
            }
            if (Yaw.mode.e().equals("Diagonal")) {
                this.b = 45.0f;
            }
            final Vec3d vec3d = new Vec3d(Yaw.A, Wrapper.mc.player.posY, Yaw.d);
            double k = Math.round((Wrapper.mc.player.rotationYaw + 1.0f) / this.b) * this.b;
            if (Yaw.mode.e().equals("Custom")) {
                k = Yaw.angle.k();
            }
            if (!Yaw.mode.e().equals("CoordLock")) {
                Wrapper.mc.player.rotationYaw = (float)k;
                Wrapper.mc.player.rotationYawHead = (float)k;
                return;
            }
            y.D(vec3d);
        }
    }
    
    @Override
    public String M() {
        if (Yaw.mode.e().equals("Custom")) {
            return String.format("%.1f", Yaw.angle.k());
        }
        return "";
    }
}
