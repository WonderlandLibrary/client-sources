/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other.HackerDetector.Movement;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import skizzle.modules.other.HackerDetector.PlayerData;

public class Fly {
    public static Minecraft mc = Minecraft.getMinecraft();

    public Fly() {
        Fly Nigga;
    }

    public static boolean check(EntityPlayer Nigga) {
        double Nigga2 = Math.sqrt((Nigga.posX - Nigga.lastTickPosX) * (Nigga.posX - Nigga.lastTickPosX) + (Nigga.posZ - Nigga.lastTickPosZ) * (Nigga.posZ - Nigga.lastTickPosZ));
        PlayerData Nigga3 = Nigga.playerData;
        return !Nigga.onGround && Nigga3.moveY / Nigga2 > -1.0 && Nigga3.ticksInAir > 16 && Nigga3.differenceMoveY >= 0.0 && Nigga2 != 0.0;
    }
}

