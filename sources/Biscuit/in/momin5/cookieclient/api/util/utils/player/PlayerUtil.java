package in.momin5.cookieclient.api.util.utils.player;

import in.momin5.cookieclient.api.util.utils.misc.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class PlayerUtil {

    private static Minecraft mc = Minecraft.getMinecraft();

    public static EntityPlayer findClosestTarget(double rangeMax, EntityPlayer aimTarget) {
        rangeMax *= rangeMax;
        List<EntityPlayer> playerList = mc.world.playerEntities;

        EntityPlayer closestTarget = null;

        for (EntityPlayer entityPlayer : playerList){

            if (EntityUtil.basicChecksEntity(entityPlayer))
                continue;

            if (aimTarget == null && mc.player.getDistanceSq(entityPlayer) <= rangeMax){
                closestTarget = entityPlayer;
                continue;
            }
            if (aimTarget != null && mc.player.getDistanceSq(entityPlayer) <= rangeMax && mc.player.getDistanceSq(entityPlayer) < mc.player.getDistanceSq(aimTarget)){
                closestTarget = entityPlayer;
            }
        }
        return closestTarget;
    }


    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        dirx /= len;
        diry /= len;
        dirz /= len;
        double pitch = Math.asin(diry);
        double yaw = Math.atan2(dirz, dirx);
        pitch = pitch * 180.0D / Math.PI;
        yaw = yaw * 180.0D / Math.PI;
        yaw += 90.0D;
        return new double[] { yaw, pitch };
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }
}
