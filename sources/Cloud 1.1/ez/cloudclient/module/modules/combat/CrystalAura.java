package ez.cloudclient.module.modules.combat;

import ez.cloudclient.module.Module;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

import java.util.Comparator;

public class CrystalAura extends Module {
    private static double yaw;
    private static double pitch;

    public CrystalAura() {
        super("CrystalAura", Category.COMBAT, "Automatically places crystals.");
    }

    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;

        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);

        dirx /= len;
        diry /= len;
        dirz /= len;

        // change to degrees
        pitch = Math.asin(diry) * 180.0d / Math.PI;
        yaw = Math.atan2(dirz, dirx) * 180.0d / Math.PI;

        yaw += 90f;

        return new double[]{yaw, pitch};
    }

    private void lookAtPacket(final double px, final double py, final double pz, final EntityPlayer me) {
        final double[] v = calculateLookAt(px, py, pz, me);
        yaw = v[0];
        pitch = v[1];
    }

    @Override
    public void onTick() {
        final EntityEnderCrystal crystal = (EntityEnderCrystal) mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).min(Comparator.comparing(c -> mc.player.getDistance(c))).orElse(null);
        if (crystal != null) {
            if (mc.player.getDistance(crystal) < 4) {
                if (Math.round(mc.player.lastTickPosY) > crystal.lastTickPosY - 1) return;
                this.lookAtPacket(crystal.posX, crystal.posY, crystal.posZ, mc.player);
                mc.playerController.attackEntity(mc.player, crystal);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }
}
