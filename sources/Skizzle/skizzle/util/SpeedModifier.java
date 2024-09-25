/*
 * Decompiled with CFR 0.150.
 */
package skizzle.util;

import net.minecraft.client.Minecraft;

public class SpeedModifier {
    public SpeedModifier() {
        SpeedModifier Nigga;
    }

    public static void setSpeed(float Nigga) {
        boolean Nigga2;
        double Nigga3 = Minecraft.getMinecraft().thePlayer.rotationYaw;
        boolean Nigga4 = Minecraft.getMinecraft().thePlayer.moveForward != Float.intBitsToFloat(2.12524851E9f ^ 0x7EACB821) || Minecraft.getMinecraft().thePlayer.moveStrafing != Float.intBitsToFloat(2.1348951E9f ^ 0x7F3FE9FD);
        boolean Nigga5 = Minecraft.getMinecraft().thePlayer.moveForward > Float.intBitsToFloat(2.1350912E9f ^ 0x7F42E825);
        boolean Nigga6 = Minecraft.getMinecraft().thePlayer.moveForward < Float.intBitsToFloat(2.1360329E9f ^ 0x7F514645);
        boolean Nigga7 = Minecraft.getMinecraft().thePlayer.moveStrafing > Float.intBitsToFloat(2.13719744E9f ^ 0x7F630B86);
        boolean Nigga8 = Minecraft.getMinecraft().thePlayer.moveStrafing < Float.intBitsToFloat(2.11726067E9f ^ 0x7E32D593);
        boolean Nigga9 = Nigga8 || Nigga7;
        boolean bl = Nigga2 = Nigga5 || Nigga6;
        if (Nigga4) {
            if (Nigga5 && !Nigga9) {
                Nigga3 += 0.0;
            } else if (Nigga6 && !Nigga9) {
                Nigga3 += 180.0;
            } else if (Nigga5 && Nigga8) {
                Nigga3 += 45.0;
            } else if (Nigga5) {
                Nigga3 -= 45.0;
            } else if (!Nigga2 && Nigga8) {
                Nigga3 += 90.0;
            } else if (!Nigga2 && Nigga7) {
                Nigga3 -= 90.0;
            } else if (Nigga6 && Nigga8) {
                Nigga3 += 135.0;
            } else if (Nigga6) {
                Nigga3 -= 135.0;
            }
            Nigga3 = Math.toRadians(Nigga3);
            Minecraft.getMinecraft().thePlayer.motionX = -Math.sin(Nigga3) * (double)Nigga;
            Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(Nigga3) * (double)Nigga;
        }
    }

    public static {
        throw throwable;
    }
}

