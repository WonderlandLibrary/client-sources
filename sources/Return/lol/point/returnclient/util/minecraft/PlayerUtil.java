package lol.point.returnclient.util.minecraft;

import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

import static lol.point.returnclient.util.MinecraftInstance.mc;

public class PlayerUtil {
    public static List<EntityPlayer> getPlayersWithin(double distance) {
        List<EntityPlayer> targets = new ArrayList<>();
        for (EntityPlayer entity : mc.theWorld.playerEntities) {
            double tempDistance = mc.thePlayer.getDistanceToEntity(entity);
            if (entity != mc.thePlayer && tempDistance <= distance) {
                targets.add(entity);
            }
        }
        return targets;
    }
}
