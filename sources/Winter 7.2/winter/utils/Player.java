/*
 * Decompiled with CFR 0_122.
 */
package winter.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class Player {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static double getDistanceToXYZ(double x2, double y2, double z2) {
        double distX = Player.mc.thePlayer.posX - x2;
        double distY = Player.mc.thePlayer.posY - y2;
        double distZ = Player.mc.thePlayer.posZ - z2;
        double dist = Math.sqrt(Math.pow(distX, 2.0) + Math.pow(distY, 2.0) + Math.pow(distZ, 2.0));
        return dist;
    }

    public static boolean isMoving() {
        if (Player.mc.gameSettings.keyBindForward.pressed || Player.mc.gameSettings.keyBindBack.pressed || Player.mc.gameSettings.keyBindLeft.pressed || Player.mc.gameSettings.keyBindRight.pressed || Player.mc.gameSettings.keyBindJump.pressed || Player.mc.gameSettings.keyBindSneak.pressed) {
            return true;
        }
        return false;
    }
}

