/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.combat;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import skizzle.Client;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.modules.Module;
import skizzle.settings.NumberSetting;
import skizzle.util.Timer;

public class Aimbot
extends Module {
    public NumberSetting yawSense;
    public NumberSetting pitchSense;
    public Timer timer = new Timer();
    public NumberSetting range = new NumberSetting(Qprot0.0("\ub85b\u71ca\u8313\ua7e3\u93bb"), 4.0, 1.0, 30.0, 1.0);
    public NumberSetting motionTracking;

    public Aimbot() {
        super(Qprot0.0("\ub848\u71c2\u8310\ua7e6\u93b1\u3dc7"), 0, Module.Category.COMBAT);
        Aimbot Nigga;
        Nigga.pitchSense = new NumberSetting(Qprot0.0("\ub859\u71c2\u8309\ua7e7\u93b6\u3d93\u8c1c\ud421\u570c\u38e7\ub7f7\uaf18\u3af6\u725b\ue78e\u2478\u42f0"), 1.0, 0.0, 1.0, 0.0);
        Nigga.yawSense = new NumberSetting(Qprot0.0("\ub850\u71ca\u830a\ua7a4\u938d\u3dd6\u8c21\ud437\u570b\u38e0\ub7f7\uaf1a\u3af6\u7259\ue79e"), 1.0, 0.0, 1.0, 0.0);
        Nigga.motionTracking = new NumberSetting(Qprot0.0("\ub85d\u71d9\u831c\ua7e7\u93b5\u3dda\u8c21\ud423\u5742\u38c7\ub7ee\uaf09\u3afa\u7249"), 1.0, 0.0, 2.0, 0.0);
        Nigga.addSettings(Nigga.range);
    }

    public static {
        throw throwable;
    }

    public float[] getRotations(Entity Nigga) {
        Aimbot Nigga2;
        float Nigga3 = Float.intBitsToFloat(1.09940262E9f ^ 0x7E078D87) - (float)Nigga2.yawSense.getValue();
        float Nigga4 = Float.intBitsToFloat(1.09706765E9f ^ 0x7EE3EC97) - (float)Nigga2.pitchSense.getValue();
        float Nigga5 = Float.intBitsToFloat(1.06616038E9f ^ 0x7F2C50FF) - (float)Nigga2.motionTracking.getValue() * Float.intBitsToFloat(1.04670899E9f ^ 0x7EC382F9);
        double Nigga6 = Nigga.posX - Nigga2.mc.thePlayer.posX - Nigga.motionX * (double)Nigga5 - (Nigga2.mc.thePlayer.posX - Nigga2.mc.thePlayer.lastTickPosX) * (double)Nigga5 / 2.0;
        double Nigga7 = Nigga.posZ - Nigga2.mc.thePlayer.posZ - Nigga.motionZ * (double)Nigga5 - (Nigga2.mc.thePlayer.posZ - Nigga2.mc.thePlayer.lastTickPosZ) * (double)Nigga5 / 2.0;
        double Nigga8 = Nigga.posY - 0.0 + (double)Nigga.getEyeHeight();
        double Nigga9 = Nigga2.mc.thePlayer.posY + (double)Nigga2.mc.thePlayer.getEyeHeight() - Nigga8 + Nigga.motionY * (double)Nigga5 + (Nigga2.mc.thePlayer.posY - Nigga2.mc.thePlayer.lastTickPosY) * (double)Nigga5 / 2.0;
        double Nigga10 = MathHelper.sqrt_double(Nigga6 * Nigga6 + Nigga7 * Nigga7);
        float Nigga11 = (float)(Math.atan2(Nigga7, Nigga6) * 180.0 / Math.PI) - Float.intBitsToFloat(1.03272173E9f ^ 0x7F3A1545);
        float Nigga12 = (float)(Math.atan2(Nigga9, Nigga10) * 180.0 / Math.PI);
        float Nigga13 = Nigga11 - Nigga2.mc.thePlayer.rotationYaw;
        float Nigga14 = Nigga12 - Nigga2.mc.thePlayer.rotationPitch;
        Nigga13 = MathHelper.wrapAngleTo180_float(Nigga13);
        Nigga14 = MathHelper.wrapAngleTo180_float(Nigga14);
        Nigga11 -= Nigga13 * Nigga3;
        Nigga12 -= Nigga14 * Nigga4;
        if (Nigga11 < Float.intBitsToFloat(2.13328563E9f ^ 0x7F275AD0)) {
            Nigga11 += Float.intBitsToFloat(1.02792902E9f ^ 0x7EF0F3B3);
        }
        return new float[]{Nigga11, Nigga12};
    }

    public double lambda$2(EntityLivingBase Nigga) {
        Aimbot Nigga2;
        return Nigga.getDistanceToEntity(Nigga2.mc.thePlayer);
    }

    @Override
    public void onEvent(Event Nigga) {
        if (Nigga instanceof EventMotion && !Client.ghostMode && Nigga.isPre()) {
            Aimbot Nigga2;
            EventMotion Nigga3 = (EventMotion)Nigga;
            List Nigga4 = Minecraft.theWorld.loadedEntityList.stream().filter(EntityLivingBase.class::isInstance).collect(Collectors.toList());
            Nigga4 = Nigga4.stream().filter(Nigga2::lambda$1).collect(Collectors.toList());
            Nigga4.sort(Comparator.comparingDouble(Nigga2::lambda$2));
            if (!Nigga4.isEmpty()) {
                EntityLivingBase Nigga5 = (EntityLivingBase)Nigga4.get(0);
                Nigga2.mc.thePlayer.rotationYaw = Nigga2.getRotations(Nigga5)[0];
                Nigga2.mc.thePlayer.rotationPitch = Nigga2.getRotations(Nigga5)[1];
            }
        }
    }

    public boolean lambda$1(EntityLivingBase Nigga) {
        Aimbot Nigga2;
        return (double)Nigga.getDistanceToEntity(Nigga2.mc.thePlayer) < Nigga2.range.getValue() && Nigga != Nigga2.mc.thePlayer && !Nigga.isDead && Nigga.getHealth() > Float.intBitsToFloat(2.13819891E9f ^ 0x7F725361);
    }
}

