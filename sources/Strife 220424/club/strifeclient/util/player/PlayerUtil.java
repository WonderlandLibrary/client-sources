package club.strifeclient.util.player;

import club.strifeclient.util.misc.MinecraftUtil;
import club.strifeclient.util.networking.PacketUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;

public final class PlayerUtil extends MinecraftUtil {
    public static double getEffectiveHealth(EntityLivingBase entity) {
        return entity.getHealth() * (entity.getMaxHealth() / entity.getTotalArmorValue());
    }
    public static boolean isTeammate(EntityPlayer entityPlayer) {
        if (entityPlayer != null) {
            String text = entityPlayer.getDisplayName().getFormattedText();
            String playerText = mc.thePlayer.getDisplayName().getFormattedText();
            if (text.length() < 2 || playerText.length() < 2) return false;
            if (!text.startsWith("\u00A7") || !playerText.startsWith("\u00A7")) return false;
            return text.charAt(1) == playerText.charAt(1);
        }
        return false;
    }
    public static void zaneDamage(final EntityPlayerSP player) {
        final double x = player.posX;
        final double y = player.posY;
        final double z = player.posZ;
        float minDmgDist = MovementUtil.getMinFallDist(3);
        final double inc = 0.0625;
        while (minDmgDist > 0.0F) {
            double low = Math.random() * 0.001F;
            double high = inc - low;
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + high, z, false));
            PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + low, z, false));
            minDmgDist -= high - low;
        }
        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
    }
    public static double getBPS() {
        return Math.hypot(mc.thePlayer.posX - mc.thePlayer.prevPosX, mc.thePlayer.posZ - mc.thePlayer.prevPosZ) * mc.timer.timerSpeed * 20;
    }
}
