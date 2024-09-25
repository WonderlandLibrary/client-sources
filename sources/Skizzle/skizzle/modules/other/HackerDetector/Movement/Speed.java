/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other.HackerDetector.Movement;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;
import skizzle.Client;
import skizzle.modules.other.HackerDetector.PlayerData;
import skizzle.util.BlockUtil;

public class Speed {
    public static Minecraft mc = Minecraft.getMinecraft();

    public static String check(EntityPlayer Nigga) {
        double Nigga2;
        double Nigga3 = Math.sqrt((Nigga.posX - Nigga.lastTickPosX) * (Nigga.posX - Nigga.lastTickPosX) + (Nigga.posZ - Nigga.lastTickPosZ) * (Nigga.posZ - Nigga.lastTickPosZ));
        double Nigga4 = 0.0;
        PlayerData Nigga5 = Nigga.playerData;
        double Nigga6 = 0.0;
        double Nigga7 = 6.0 * Nigga6 - (double)Nigga5.ticksOnGround * Nigga6;
        Math.round(Nigga3 * 1000.0);
        if (Nigga5.ticksOnGround > 6) {
            Nigga7 = 0.0;
        }
        if (Nigga.getActivePotionEffect(Potion.moveSpeed) != null) {
            Nigga4 = (double)((Nigga.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1) * 6) / 100.0;
        }
        if ((Nigga2 = BlockUtil.getHorizontalDistance(new Vec3(Nigga.posX, 0.0, Nigga.posZ), new Vec3(Nigga.lastTickPosX, 0.0, Nigga.lastTickPosZ))) >= 0.0 && Nigga2 <= 100.0) {
            ++Nigga5.speedFlags;
            if (Nigga5.speedFlags > 8) {
                return Qprot0.0("\udb6e\u71c5\ue055\u4099\u91ce\u5ed4\u8c3a\ub722\ub07c\u3ad6\ud4e5\uaf1c\u59f2\u9532\ue5e1");
            }
        } else if (Nigga2 >= 0.0 && Nigga5.speedFlags > 0) {
            --Nigga5.speedFlags;
        }
        if (Nigga.onGround && Nigga5.ticksOnGround <= 6 && !Nigga.capabilities.allowFlying) {
            Math.round(Nigga3 * 100.0);
            if (Nigga3 > 0.0 + Nigga4 + Nigga7) {
                return Qprot0.0("\udb6d\u71ca\ue01b\u409a\u91d5\u5ed5\u8c28\ub76c\ub06b\u3a86\ud4f3\uaf09\u59f3\u9577\ue5e7\u4761\u42e0\u6d00\uf07d\u8edd\u22b3\u01c5\ucb33\u0f56\u567d\u6667\u2f0d\u0e53");
            }
        }
        double Nigga8 = Math.abs(Nigga5.moveX - Nigga5.lastTickMoveX);
        double Nigga9 = Math.abs(Nigga5.moveZ - Nigga5.lastTickMoveZ);
        if ((Nigga8 > 0.0 + Nigga4 + Nigga7 / 10.0 + (double)(Client.getPlayerPing(Nigga.getName()) / 100) && Nigga8 < 100.0 || Nigga9 > 0.0 + Nigga4 + Nigga7 / 10.0 + (double)(Client.getPlayerPing(Nigga.getName()) / 100) && Nigga9 < 100.0) && !Nigga.isCollidedHorizontally) {
            return Qprot0.0("\udb62\u71c3\ue014\u4090\u91db\u5ed2\u8c21\ub72b\ub038\u3a85\ud4e6\uaf09\u59f2\u9533\ue5a5\u4770\u42e6\u6d4e\uf07c\u8e9c\u22b4\u01de");
        }
        return null;
    }

    public Speed() {
        Speed Nigga;
    }
}

