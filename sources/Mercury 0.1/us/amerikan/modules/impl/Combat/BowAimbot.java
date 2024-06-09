/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.modules.impl.Combat;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import us.amerikan.modules.Category;
import us.amerikan.modules.Module;
import us.amerikan.utils.Location3D;

public class BowAimbot
extends Module {
    public boolean a = false;

    public BowAimbot() {
        super("BowAimbot", "BowAimbot", 0, Category.COMBAT);
    }

    private EntityPlayer getNearest() {
        Location3D p2 = new Location3D(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ);
        EntityPlayer best = null;
        double distance = 50.0;
        for (int i2 = 0; i2 < BowAimbot.mc.theWorld.playerEntities.size(); ++i2) {
            double newDis;
            Location3D epl;
            EntityPlayer ep2 = (EntityPlayer)BowAimbot.mc.theWorld.playerEntities.get(i2);
            if (ep2.equals(Minecraft.thePlayer)) continue;
            if (!Minecraft.thePlayer.canEntityBeSeen(ep2) || !((newDis = (epl = new Location3D(ep2.posX, ep2.posY, ep2.posZ)).distance(p2)) <= distance)) continue;
            distance = newDis;
            best = ep2;
        }
        return best;
    }
}

